/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.object.processing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.mapper.IBVariableDOMapperImpl;
import com.sas.rtdm2id.mapper.VarRefMapper;
import com.sas.rtdm2id.model.id.decision.CodeFile;
import com.sas.rtdm2id.model.id.decision.CodeFileCollection;
import com.sas.rtdm2id.model.id.decision.ConditionBranch;
import com.sas.rtdm2id.model.id.decision.CustomObjectStep;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.Mapping;
import com.sas.rtdm2id.model.id.decision.RuleSetStep;
import com.sas.rtdm2id.model.id.decision.SignatureTerm;
import com.sas.rtdm2id.model.id.decision.SignatureTermExtension;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.globals.GlobalVariable;
import com.sas.rtdm2id.model.id.globals.GlobalVariableCollection;
import com.sas.rtdm2id.model.id.globals.Item;
import com.sas.rtdm2id.model.id.rules.Action;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.id.rules.Signature;
import com.sas.rtdm2id.model.rtdm.*;
import com.sas.rtdm2id.model.rtdm.SplitNodeDataDO.SplitOnNodeDataDO.VarRefDO;
import com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO;
import com.sas.rtdm2id.model.rtdm.extension.SubDiagramNodeDataDO;
import com.sas.rtdm2id.model.rtdm.extension.VarRef;
import com.sas.rtdm2id.util.DateUtil;
import com.sas.rtdm2id.util.ViyaApi;
import com.sas.rtdm2id.util.model.IBVariableDO;
import com.sas.rtdm2id.util.model.RTDM2IDConstants;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.*;

@Component
public class CommonProcessing {
    Logger log = LoggerFactory.getLogger(CommonProcessing.class);

    private final RestTemplate restTemplate;
    private final MapStorage mapStorage;
    private final TreeUtil treeUtil;

    private static final String UNDERSCORE = "_";
    private static final String EMPTY_STRING = "";
    private static final String SAFE_EMPTY_NAME = "v";
    private static final Pattern CALCULATED_REFERENCE = Pattern.compile("<<(.*?)\\.(.*?)>>");

    // Per-migration lookups that are rebuilt at the start of every createRuleSets call (MapStorage does not hold them).
    // Calculated item definitions are needed to schedule the calculated items an expression depends on, and declared
    // source types are used to type RuleSet input terms by their source rather than by the assigned variable.
    private final Map<String, CalculatedItemDO> calculatedItemsByName = new HashMap<>();
    private final Map<String, String> sourceDataTypes = new HashMap<>();

    @Value("${rtdm2id.consolidate-calculated-rulesets:true}")
    private boolean consolidateCalculatedRuleSets = true;

    public CommonProcessing(RestTemplate restTemplate, MapStorage mapStorage, TreeUtil treeUtil) {
        this.restTemplate = restTemplate;
        this.mapStorage = mapStorage;
        this.treeUtil = treeUtil;
    }

    public Map<String, Step> createRuleSets(Batch batch) {
        // Output names are needed by calculated expressions below and by every node converted afterwards
        registerNodeOutputs(batch);
        // Rulesets are unique so we must append the Node ID to the name. This applies anywhere we need to create a ruleset
        // to represent the input/output variables of a RTDM node including calculated data items since they're created on a
        // per node basis.
        // Each node in a RTDM campaign has its own unique set of input/output variable assignments so we must create a
        // unique ruleset for each node.
        List<CalculatedItemDO> calculatedItemDOs = batch.getLogicalUnit().getFlowDO().getCalculatedDataItemList().getCalculatedItemDOs();
        calculatedItemsByName.clear();
        sourceDataTypes.clear();
        Map<String, String> requestDataTypes = sourceDataTypes;
        StartNodeDataDO start = batch.getLogicalUnit().getStartNodeDataDO();
        if (start != null) {
            List<StartNodeDataDO.Event.RequestVars.IBVariableDO> requestVars = Collections.emptyList();
            if (start.getEvent() != null && start.getEvent().getRequestVars() != null) {
                requestVars = start.getEvent().getRequestVars().getIbVariableDOs();
            } else if (start.getEvent() == null && start.getInputVariables() != null) {
                requestVars = start.getInputVariables().getIbVariableDOs();
            }
            for (StartNodeDataDO.Event.RequestVars.IBVariableDO requestVar : requestVars) {
                String name = requestVar.getName() != null && !requestVar.getName().isEmpty()
                    ? requestVar.getName() : requestVar.getPhysicalName();
                if (name != null && requestVar.getTypeDescription() != null) {
                    requestDataTypes.put(sanitizeVariableName(SOURCE_EVENT + "." + name).toLowerCase(Locale.ROOT),
                        getDatatypeOfVar(requestVar.getTypeDescription()).getValue());
                }
            }
        }
        if (!calculatedItemDOs.isEmpty()) {
            IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();
            for (ProcessNodeDataDO processNode : batch.getLogicalUnit().getProcessNodeDataDOs()) {
                if (processNode.getProcess() != null && processNode.getProcess().getInputVariableList() != null) {
                    for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO variable
                            : processNode.getProcess().getInputVariableList().getIbVariableDOs()) {
                        addGlobalReferenceType(mapper.ibVariableDoGet(variable), requestDataTypes);
                    }
                }
            }
            for (ReplyNodeDataDO replyNode : batch.getLogicalUnit().getReplyNodeDataDOs()) {
                List<ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO> variables = Collections.emptyList();
                if (replyNode.getEventReply() != null && replyNode.getEventReply().getReplyVars() != null) {
                    variables = replyNode.getEventReply().getReplyVars().getIbVariableDOs();
                } else if (replyNode.getTreatmentReplyVariables() != null) {
                    variables = replyNode.getTreatmentReplyVariables().getIbVariableDOs();
                }
                for (ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO variable : variables) {
                    addGlobalReferenceType(mapper.ibVariableDoGet(variable), requestDataTypes);
                }
            }
            for (CalculatedItemDO calculatedItemDO : calculatedItemDOs) {
                if (calculatedItemDO.getName() != null) {
                    calculatedItemsByName.putIfAbsent(calculatedItemDO.getName(), calculatedItemDO);
                }
                requestDataTypes.put(sanitizeVariableName(SOURCE_CALCULATED_DATA_ITEM + "."
                    + calculatedItemDO.getName()).toLowerCase(Locale.ROOT),
                    getCalculatedItemDataType(calculatedItemDO.getCalculatedItemType()));
            }
            for (CalculatedItemDO calculatedItemDO : calculatedItemDOs) {
                if (consolidateCalculatedRuleSets) {
                    // Each consumer node gets one RuleSet computing all of its calculated items, written once the
                    // flow is converted (writeCalculatedNodeGroups), so no per-item RuleSet is created
                    mapStorage.getCalculatedVariableMap().put(calculatedItemDO.getName(), calculatedItemDO.getNodeId());
                    mapStorage.getCalculatedVariableDataTypeMap().put(CALCULATED_DATA_ITEMS_FOLDER + "." + calculatedItemDO.getName(),
                        getCalculatedItemDataType(calculatedItemDO.getCalculatedItemType()));
                } else {
                    addRulesetForCalculatedItem(calculatedItemDO, requestDataTypes);
                }
            }
        }
        List<SplitNodeDataDO> splitNodeDataDOS = batch.getLogicalUnit().getSplitNodeDataDOs();
        splitNodeDataDOS.stream().filter(splitNodeDataDO -> "percents".equals(splitNodeDataDO.getSplitType())).forEach(splitNodeDataDO -> {
            findOrAddRuleSet(makeSplitNodeRuleSetName(splitNodeDataDO) + "_random-number", "");
        });

        List<ReplyNodeDataDO> replyNodeDataDOs = batch.getLogicalUnit().getReplyNodeDataDOs();
        if (!replyNodeDataDOs.isEmpty()) {
            for (ReplyNodeDataDO replyNodeDataDO : replyNodeDataDOs) {
                findOrAddRuleSet(makeReplyNodeRuleSetName(replyNodeDataDO), "");
            }
        }
        List<ProcessNodeDataDO> processNodeDataDOs = batch.getLogicalUnit().getProcessNodeDataDOs();
        if (!processNodeDataDOs.isEmpty()) {
            for (ProcessNodeDataDO processNodeDataDO : processNodeDataDOs) {
                ProcessNodeDataDO.Process process = processNodeDataDO.getProcess();
                if (process != null) {
                    if (hasInputValuesRuleSet(process)) {
                        findOrAddRuleSet(makeProcessNodeRuleSetName(processNodeDataDO) + "_input_values", processNodeDataDO.getNodeDescription());
                    }
                    if (!isProcessNotDataProcess(process.getProcessTypeDescription()) && Boolean.parseBoolean(process.getReturnDatagridFlag())) {
                        findOrAddRuleSet(makeProcessNodeRuleSetName(processNodeDataDO) + "_extract_values", process.getDescription());
                    }
                }
            }
        }
        List<SubDiagramNodeDataDO> subDiagramNodeDataDOs = batch.getLogicalUnit().getSubDiagramNodeDataDOs();
        if (!subDiagramNodeDataDOs.isEmpty()) {
            for (SubDiagramNodeDataDO subDiagramNodeDataDO : subDiagramNodeDataDOs) {
                findOrAddRuleSet(makeSubdiagramNodeRuleSetName(subDiagramNodeDataDO) + "_input_values", "");
            }
        }

        List<CHandRHNodeDataDO> cHandRHNodeDataDOS = batch.getLogicalUnit().getCHandRHNodeDataDOs();
        if (!cHandRHNodeDataDOS.isEmpty()) {
            for (CHandRHNodeDataDO cHandRHNodeDataDO : cHandRHNodeDataDOS) {
                findOrAddRuleSet(makeConfirmContactNodeRuleSetName(cHandRHNodeDataDO) + "_input_values", "");
            }
        }

        List<AssignmentNodeDataDO> assignmentNodeDataDOs = batch.getLogicalUnit().getAssignmentNodeDataDOs();
        if (!assignmentNodeDataDOs.isEmpty()){
            for (AssignmentNodeDataDO assignmentNodeDataDO : assignmentNodeDataDOs) {
                findOrAddRuleSet(makeAssignmentNodeRuleSetName(assignmentNodeDataDO),"");
            }
        }
        return mapStorage.getRuleSetStepMap();
    }

    public void findOrAddRuleSet(String ruleSetName, String description) {
        RuleSet ruleSet = null;
        String ruleSetJson = null;

        try {
            Step step = new Step();
            step.setType(Step.TypeEnum.RULESET);
            step.setMappings(new ArrayList<>());
            CodeFileCollection codeFileCollection
                    = getCodeFileCollection(mapStorage.getBaseIp(), ruleSetName, mapStorage.getAccessToken(), "/businessRules/ruleSets");
            if (codeFileCollection != null) {
                if (codeFileCollection.getCount() == 0) {
                    ruleSet = new RuleSet();
                    ruleSet.setName(ruleSetName);
                    ruleSet.setRuleSetType("assignment");
                    List<Signature> signature = new ArrayList<>();
                    ruleSet.setSignature(signature);
                    ruleSet.setDescription(description);

                    ResponseEntity<RuleSet> response = restTemplate.postForEntity(ViyaApi.createUri(mapStorage.getBaseIp(), "/businessRules/ruleSets?parentFolderUri=" + ViyaApi.encodeQueryParam(mapStorage.getParentFolderUri()), mapStorage.getProtocol()),
                            ViyaApi.createRule(ruleSet, mapStorage.getAccessToken()), RuleSet.class);
                    if (response.getStatusCode().value() == 201) {
                        RuleSet responseBody = response.getBody();
                        if (responseBody != null) {
                            RuleSetStep ruleSetStep = new RuleSetStep();
                            ruleSetStep.setName(responseBody.getName());
                            ruleSetStep.setId(responseBody.getId());
                            step.setRuleSet(ruleSetStep);
                            mapStorage.getRuleSetStepMap().put(responseBody.getName(), step);
                            mapStorage.getRuleSetModifiedDate().put(responseBody.getName(), responseBody.getModifiedTimeStamp());
                            mapStorage.getRuleSetIsUpdated().put(responseBody.getName(), false);
                            mapStorage.getRuleSetIsActionUpdated().put(responseBody.getName(), false);
                        }
                    }
                } else {
                    RuleSetStep ruleSetStep = new RuleSetStep();
                    CodeFile codeFile = codeFileCollection.getItems().get(0);
                    ruleSetStep.setName(codeFile.getName());
                    ruleSetStep.setId(codeFile.getId());
                    step.setRuleSet(ruleSetStep);
                    mapStorage.getRuleSetStepMap().put(codeFile.getName(), step);
                    mapStorage.getRuleSetModifiedDate().put(codeFile.getName(), new Date(codeFile.getModifiedTimeStamp().toInstant().toEpochMilli()));
                    mapStorage.getRuleSetIsUpdated().put(codeFile.getName(), true);
                    mapStorage.getRuleSetIsActionUpdated().put(codeFile.getName(), true);
                }
            }
        } catch (Exception e) {
            try {
                // Log ruleset json to help with debugging
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
                ruleSetJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ruleSet);

                log.error("Exception occurred creating RuleSet");
                log.error("\nRuleSet:");
                log.error(ruleSetJson);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * Returns whether a Decision with the given name exists on the target. A failed lookup is not treated as
     * "does not exist": it would bypass the duplicate-Decision guard and report subdiagrams as missing, so it throws.
     */
    public boolean findDecision(String decisionName) {
        CodeFileCollection codeFileCollection;
        try {
            codeFileCollection
                    = getCodeFileCollection(mapStorage.getBaseIp(), decisionName, mapStorage.getAccessToken(), "/decisions/flows");
        } catch (Exception e) {
            throw new IllegalStateException("Unable to look up Decision '" + decisionName + "' on the target Viya: "
                    + e.getMessage(), e);
        }
        if (codeFileCollection == null) {
            throw new IllegalStateException("Unable to look up Decision '" + decisionName
                    + "' on the target Viya: unexpected response");
        }
        return codeFileCollection.getCount() > 0;
    }

    public SignatureTerm.DataTypeEnum getDatatypeOfVar(String typeDescription) {
        if (typeDescription == null) {
            return SignatureTerm.DataTypeEnum.ANY;
        }
        SignatureTerm.DataTypeEnum dataTypeEnum;
        switch (typeDescription.toLowerCase(Locale.ROOT)) {
            case CHAR_CONSTANT:
            case STRING_CONSTANT:
            case BOOLEAN_LIST_CONSTANT:
            case STRING_LIST_CONSTANT:
                dataTypeEnum = SignatureTerm.DataTypeEnum.STRING;
                break;
            case INTEGER_CONSTANT:
                dataTypeEnum = SignatureTerm.DataTypeEnum.INTEGER;
                break;
            case BOOLEAN_CONSTANT:
                dataTypeEnum = SignatureTerm.DataTypeEnum.BOOLEAN;
                break;
            case DECIMAL_CONSTANT:
            case DOUBLE_CONSTANT:
            case NUMERIC_CONSTANT:
            case DOUBLE_LIST_CONSTANT:
                dataTypeEnum = SignatureTerm.DataTypeEnum.DECIMAL;
                break;
            case INTEGER_LIST_CONSTANT:
                dataTypeEnum = SignatureTerm.DataTypeEnum.INTEGER;
                break;
            case DATAGRID_CONSTANT:
            case "datagrid": // ID "dataGrid" (DATAGRID_CONSTANT_SID), lower-cased
                dataTypeEnum = SignatureTerm.DataTypeEnum.DATAGRID;
                break;
            case DATE_CONSTANT:
            case DATETIME_CONSTANT:
            case DATE_LIST_CONSTANT:
                dataTypeEnum = SignatureTerm.DataTypeEnum.DATETIME;
                break;
            default:
                dataTypeEnum = SignatureTerm.DataTypeEnum.ANY;
                break;
        }
        return dataTypeEnum;
    }

    public void checkSignatureTermForDuplicate(String direction, Decision decision, SignatureTerm signatureItem) {

        Optional<SignatureTerm> optionalSignatureTerm = decision.getSignature().stream()
                .filter(signatureTerm -> signatureTerm.getName().equalsIgnoreCase(signatureItem.getName())).findFirst();

        // If signatureItem already exists with a direction of input/output and a new
        // direction of input/output has come in then update direction to be inOut
        // Also, if signatureItem already exists with a direction of input and a new
        // direction of none has come in then update direction to be inOut
        if (optionalSignatureTerm.isPresent()) {
            // Make sure names match exactly on case as contains() statement below seems to be case sensitive
            signatureItem.setName(optionalSignatureTerm.get().getName());

            if (INPUT_DIRECTION.equals(direction)
                        && SignatureTerm.DirectionEnum.OUTPUT.equals(optionalSignatureTerm.get().getDirection())

                        || OUTPUT_DIRECTION.equals(direction) && SignatureTerm.DirectionEnum.INPUT
                                .equals(optionalSignatureTerm.get().getDirection())

                        || NONE_DIRECTION.equals(direction) && SignatureTerm.DirectionEnum.INPUT
                                .equals(optionalSignatureTerm.get().getDirection())) {
                optionalSignatureTerm.get().setDirection(SignatureTerm.DirectionEnum.INOUT);
                signatureItem.setDirection(SignatureTerm.DirectionEnum.INOUT);
            }
        } else {
            signatureItem.setDirection(SignatureTerm.DirectionEnum.fromValue(direction));
        }

        if (!decision.getSignature().contains(signatureItem)) {
            decision.addSignatureItem(signatureItem);
        } else if (SignatureTerm.DataTypeEnum.DECIMAL.equals(signatureItem.getDataType())) {
            int i = decision.getSignature().indexOf(signatureItem);
            SignatureTerm signatureTermThatExist = decision.getSignature().get(i);
            if (!SignatureTerm.DataTypeEnum.DECIMAL.equals(signatureTermThatExist.getDataType())) {
                signatureTermThatExist.setDataType(SignatureTerm.DataTypeEnum.DECIMAL);
            }
        }
    }

    public void checkSignatureTermForDuplicate(String direction, List<Signature> signatureList, Signature signature) {
        handleIncomingSignatureUpdate(direction, signatureList, signature);
        // RuleSet term names must be unique (case-insensitively); a duplicate name makes the signature PUT fail.
        // The same term can arrive with different types (e.g. one source assigned to integer and decimal variables),
        // so dedupe by name and merge the types instead of adding a second term.
        Optional<Signature> existing = signatureList.stream()
                .filter(term -> term.getName() != null && term.getName().equalsIgnoreCase(signature.getName())).findFirst();
        if (existing.isEmpty()) {
            signatureList.add(signature);
        } else {
            Signature signatureThatExist = existing.get();
            signatureThatExist.setDataType(mergeDataTypes(signatureThatExist.getDataType(), signature.getDataType()));
        }
    }

    /**
     * Merges the data type of a duplicate RuleSet term into the existing one: the first concrete type wins, except
     * that integer and decimal widen to decimal.
     */
    static String mergeDataTypes(String existingType, String incomingType) {
        if (existingType == null || SignatureTerm.DataTypeEnum.ANY.getValue().equals(existingType)) {
            return incomingType != null ? incomingType : existingType;
        }
        if (incomingType == null || existingType.equals(incomingType)) {
            return existingType;
        }
        Set<String> numeric = Set.of(SignatureTerm.DataTypeEnum.INTEGER.getValue(), SignatureTerm.DataTypeEnum.DECIMAL.getValue());
        if (numeric.contains(existingType) && numeric.contains(incomingType)) {
            return SignatureTerm.DataTypeEnum.DECIMAL.getValue();
        }
        return existingType;
    }

    public void handleIncomingSignatureUpdate(String direction, List<Signature> signatureList, Signature signature) {
        Optional<Signature> optionalSignature = signatureList.stream()
                .filter(optSignature -> optSignature.getName().equalsIgnoreCase(signature.getName())).findFirst();

        if (optionalSignature.isPresent()) {
            // Make sure names match exactly on case
            signature.setName(optionalSignature.get().getName());

            if (INPUT_DIRECTION.equals(direction)
                        && SignatureTerm.DirectionEnum.OUTPUT.equals(optionalSignature.get().getDirection())

                        || OUTPUT_DIRECTION.equals(direction) && SignatureTerm.DirectionEnum.INPUT
                                .equals(optionalSignature.get().getDirection())

                        || NONE_DIRECTION.equals(direction) && SignatureTerm.DirectionEnum.INPUT
                                .equals(optionalSignature.get().getDirection())) {

                // If signature already exists with a direction of input/output and a new
                // direction of input/output has come in then update direction to be inOut
                // Also, if signature already exists with a direction of input and a new
                // direction of none has come in then update direction to be inOut
                optionalSignature.get().setDirection(IN_OUT_DIRECTION);
                signature.setDirection(IN_OUT_DIRECTION);
            }

            if (IN_OUT_DIRECTION.equals(optionalSignature.get().getDirection())) {
                // An input/output signature with the same name already exists, upgrade signature direction to match.
                // This ensures we don't end up with duplicate signature names. We already have code to handle case
                // but we don't have code to handle the scenario where a signature direction of "input" is added to
                // signature list where a signature with same name and direction of "inOut" already exists.

                // Here is an example.
                //
                //   "name" : "DS2Process_CGCFQWNY4VFOUDOV_input_values",
                //    "signature" : [ {
                //        "name" : "inputString",
                //        "dataType" : "string",
                //        "direction" : "inOut"
                //    }, {
                //        "name" : "APPLICATIONID",
                //        "dataType" : "decimal",
                //        "direction" : "inOut"
                //    }, {
                //        "name" : "applicationId",
                //        "dataType" : "decimal",
                //        "direction" : "input"
                //    } ]
                //    }
                //
                // We need to set direction of applicationId to "inOut" otherwise you end up with
                // duplicate signature names.
                signature.setDirection(IN_OUT_DIRECTION);
            }
        }
    }

    public CodeFileCollection getCodeFileCollection(String baseIp, String name, String accessToken, String contextPath) throws URISyntaxException {
        ResponseEntity<CodeFileCollection> getResponse = restTemplate.exchange(
                ViyaApi.createUriWithParams(baseIp, contextPath, "name", name, mapStorage.getProtocol())
                , HttpMethod.GET,
                ViyaApi.createGetByNameForCodeFiles(accessToken), CodeFileCollection.class);
        if (getResponse.getStatusCode().value() == 200) {
            return getResponse.getBody();
        }
        return null;
    }

    public CodeFileCollection getCodeFileCollectionByDescription(String baseIp, String id, String accessToken, String contextPath) throws URISyntaxException {
        ResponseEntity<CodeFileCollection> getResponse = restTemplate.exchange(
                ViyaApi.createUriWithParams(baseIp, contextPath, "description", id, mapStorage.getProtocol())
                , HttpMethod.GET,
                ViyaApi.createGetByNameForCodeFiles(accessToken), CodeFileCollection.class);
        if (getResponse.getStatusCode().value() == 200) {
            return getResponse.getBody();
        }
        return null;
    }

    public boolean checkForCalcVariable(String varInfoId, String type, List<Step> stepList, Step originStep, String varName, String nodeId) {
        if (varInfoId != null && varInfoId.indexOf('.') >= 0) {
            String source = varInfoId.substring(0, varInfoId.indexOf("."));
            if (CALCULATED_DATA_ITEMS_FOLDER.equals(source)) {
                String calculatedVariableName = varInfoId.substring(varInfoId.indexOf(".") + 1);
                String variableName = sanitizeVariableName(SOURCE_CALCULATED_DATA_ITEM + "." + calculatedVariableName);

                Step step = consolidateCalculatedRuleSets
                    ? addToCalculatedNodeGroup(calculatedVariableName, stepList, nodeId)
                    : scheduleCalculatedItem(calculatedVariableName, type, stepList, nodeId, new HashSet<>());
                if (step != null && originStep != null) {
                    addMapping(variableName, varName, originStep, true);
                }

                if (mapStorage.getVarInfoIdToVariableNameMap().get(varInfoId)==null) {
                    // Store the display name value
                    mapStorage.getVarInfoIdToVariableNameMap().put(varInfoId, variableName);
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Adds a calculated item, preceded by the calculated items its expression depends on, to the one RuleSet step
     * that computes the calculated items of node {@code nodeId}. The step is inserted (empty) the first time the node
     * references a calculated item; its RuleSet is written by {@link #writeCalculatedNodeGroups} once the whole flow is
     * converted. Like RTDM, which evaluates a calculated item where it is used, every node computes the items it uses
     * itself, so the result does not depend on which node the flow reaches first.
     */
    private Step addToCalculatedNodeGroup(String calculatedVariableName, List<Step> stepList, String nodeId) {
        if (!calculatedItemsByName.containsKey(calculatedVariableName)) {
            log.error("Calculated data item '{}' referenced by node {} is not defined in the extract; "
                + "it will not be computed in the migrated Decision", calculatedVariableName, nodeId);
            return null;
        }
        MapStorage.CalculatedNodeGroup group = mapStorage.getCalculatedNodeGroups().get(nodeId);
        if (group == null) {
            Step step = new Step();
            step.setType(Step.TypeEnum.RULESET);
            step.setMappings(new ArrayList<>());
            group = new MapStorage.CalculatedNodeGroup(step, new LinkedHashSet<>());
            mapStorage.getCalculatedNodeGroups().put(nodeId, group);
            addStep(stepList, step, nodeId);
            mapStorage.getCalculatedVariableRulesetNodes().add(step);
        }
        addWithCalculatedDependencies(calculatedVariableName, group.items(), new HashSet<>());
        return group.step();
    }

    /**
     * Without consolidation: inserts the RuleSet step that computes a calculated item, preceded by the steps of the
     * calculated items its expression depends on (in dependency order). Every step is inserted at most once per
     * migration ({@code calculatedVariableRulesetNodes}), exactly as for direct references.
     */
    private Step scheduleCalculatedItem(String calculatedVariableName, String type, List<Step> stepList, String nodeId,
            Set<String> visiting) {
        String ruleSetName = calculatedItemRuleSetName(calculatedVariableName);
        Step step = ruleSetName == null ? null : mapStorage.getRuleSetStepMap().get(ruleSetName);
        if (step == null) {
            log.error("No RuleSet step exists for calculated data item '{}' (RuleSet {}) referenced by node {}; "
                + "the item will not be computed in the migrated Decision", calculatedVariableName, ruleSetName, nodeId);
            return null;
        }
        if (!mapStorage.getCalculatedVariableRulesetNodes().contains(step) && visiting.add(calculatedVariableName)) {
            for (String dependency : getCalculatedDependencies(calculatedVariableName)) {
                String dependencyType = mapStorage.getCalculatedVariableDataTypeMap()
                    .get(CALCULATED_DATA_ITEMS_FOLDER + "." + dependency);
                scheduleCalculatedItem(dependency, dependencyType, stepList, nodeId, visiting);
            }
            if (!mapStorage.getCalculatedVariableRulesetNodes().contains(step)) {
                updateRuleSet(sanitizeVariableName(SOURCE_CALCULATED_DATA_ITEM + "." + calculatedVariableName), type, step);
                addStep(stepList, step, nodeId);
                mapStorage.getCalculatedVariableRulesetNodes().add(step);
            }
        }
        return step;
    }

    private void addWithCalculatedDependencies(String calculatedVariableName, Set<String> ordered, Set<String> visiting) {
        if (ordered.contains(calculatedVariableName) || !visiting.add(calculatedVariableName)) {
            return;
        }
        for (String dependency : getCalculatedDependencies(calculatedVariableName)) {
            if (calculatedItemsByName.containsKey(dependency)) {
                addWithCalculatedDependencies(dependency, ordered, visiting);
            } else {
                log.error("Calculated data item '{}' used by '{}' is not defined in the extract; "
                    + "it will not be computed in the migrated Decision", dependency, calculatedVariableName);
            }
        }
        ordered.add(calculatedVariableName);
    }

    /**
     * Writes the RuleSet of every node's calculated item step (see {@link #addToCalculatedNodeGroup}): one rule whose
     * actions compute the items in dependency order. Items are read and written under their Decision terms
     * ({@code CalculatedDataItem<name>}), so a later action reads the value an earlier one assigned. The name carries a
     * content fingerprint; an existing RuleSet is reused only if its signature and actions match exactly.
     */
    public void writeCalculatedNodeGroups() {
        CalculatedRuleSetWriter writer = new CalculatedRuleSetWriter(restTemplate, mapStorage);
        mapStorage.getCalculatedNodeGroups().forEach((nodeId, group) -> {
            Step step = group.step();
            RuleSet ruleSet = new RuleSet();
            ruleSet.setRuleSetType("assignment");
            ruleSet.setDescription(CalculatedRuleSetWriter.DESCRIPTION);
            Map<String, String> variableTargets = new LinkedHashMap<>();
            Map<String, String> calculatedTerms = new HashMap<>();
            for (String item : group.items()) {
                String target = SOURCE_CALCULATED_DATA_ITEM + "." + item;
                String term = uniqueExpressionTerm(
                    changeVariableNameToFormat(sanitizeAndReduceVariableName(target)), target, variableTargets);
                variableTargets.put(term, target);
                calculatedTerms.put(item, term);
            }
            Set<String> assigned = new HashSet<>();
            Set<String> readBeforeAssigned = new HashSet<>();
            List<Action> actions = new ArrayList<>();
            for (String item : group.items()) {
                CalculatedItemDO calculatedItemDO = calculatedItemsByName.get(item);
                String expression = calculatedItemDO.getExpression();
                Matcher references = CALCULATED_REFERENCE.matcher(expression == null ? "" : expression);
                while (references.find()) {
                    String reference = references.group(2);
                    if (CALCULATED_DATA_ITEMS_FOLDER.equals(references.group(1)) && calculatedTerms.containsKey(reference)
                            && !assigned.contains(reference)) {
                        // Only in a cyclic definition: the value the item had before this step is read
                        readBeforeAssigned.add(calculatedTerms.get(reference));
                    }
                }
                String value = expression == null || "Missing".equals(expression)
                    ? getMissingValue(getCalculatedItemDataType(calculatedItemDO.getCalculatedItemType()))
                    : transferInSIDFormat(expression, variableTargets, calculatedTerms);
                Action action = new Action();
                action.setType("complex");
                action.setExpression(calculatedTerms.get(item) + "=" + value);
                actions.add(action);
                assigned.add(item);
            }
            Map<String, String> outputTypes = new HashMap<>();
            group.items().forEach(item -> outputTypes.put(calculatedTerms.get(item),
                getCalculatedItemDataType(calculatedItemsByName.get(item).getCalculatedItemType())));
            List<Signature> signature = new ArrayList<>();
            step.setMappings(new ArrayList<>());
            variableTargets.forEach((term, target) -> {
                String targetName = sanitizeVariableName(target);
                String outputType = outputTypes.get(term);
                Signature signatureTerm = new Signature();
                signatureTerm.setName(term);
                if (outputType != null) {
                    signatureTerm.setDataType(outputType);
                    signatureTerm.setDirection(readBeforeAssigned.contains(term) ? INOUT_DIRECTION : OUTPUT_DIRECTION);
                } else {
                    // Upstream process/subdiagram outputs are typed by their producer (see registerNodeOutputType)
                    String targetKey = targetName.toLowerCase(Locale.ROOT);
                    signatureTerm.setDataType(sourceDataTypes.getOrDefault(targetKey,
                        mapStorage.getNodeOutputDataTypes().getOrDefault(targetKey, "any")));
                    signatureTerm.setDirection(INPUT_DIRECTION);
                }
                signature.add(signatureTerm);
                Mapping mapping = new Mapping();
                mapping.setStepTermName(term);
                mapping.setTargetDecisionTermName(targetName);
                mapping.setDirection(INPUT_DIRECTION.equals(signatureTerm.getDirection()) ? Mapping.DirectionEnum.INPUT
                    : OUTPUT_DIRECTION.equals(signatureTerm.getDirection()) ? Mapping.DirectionEnum.OUTPUT
                    : Mapping.DirectionEnum.INOUT);
                step.getMappings().add(mapping);
            });
            ruleSet.setSignature(signature);
            Rule rule = new Rule();
            rule.setConditional("if");
            rule.setRuleFiredTrackingEnabled(false);
            rule.setActions(actions);
            String fingerprint = CalculatedRuleSetWriter.fingerprint(nodeId, ruleSet, rule);
            ruleSet.setName(makeNameValid("Calculated_" + nodeId + "_" + fingerprint));
            rule.setName("Calculated_" + fingerprint);
            writer.write(ruleSet, rule, step);
        });
    }

    /**
     * Name of the ungrouped RuleSet created for a calculated item, computed exactly as at creation
     * ({@link #makeCalculatedDataItemRuleSetName}), or null if no RuleSet was created for the item.
     */
    private String calculatedItemRuleSetName(String calculatedVariableName) {
        if (!mapStorage.getCalculatedVariableMap().containsKey(calculatedVariableName)) {
            return null;
        }
        return makeCalculatedDataItemRuleSetName(calculatedVariableName,
            mapStorage.getCalculatedVariableMap().get(calculatedVariableName));
    }

    /** Calculated items referenced by a calculated item's expression, in order of first reference. */
    private List<String> getCalculatedDependencies(String calculatedVariableName) {
        CalculatedItemDO item = calculatedItemsByName.get(calculatedVariableName);
        if (item == null || item.getExpression() == null
                || !mapStorage.getCalculatedVariableMap().containsKey(calculatedVariableName)) {
            return Collections.emptyList();
        }
        Set<String> dependencies = new LinkedHashSet<>();
        Matcher references = CALCULATED_REFERENCE.matcher(item.getExpression());
        while (references.find()) {
            if (CALCULATED_DATA_ITEMS_FOLDER.equals(references.group(1))) {
                dependencies.add(references.group(2));
            }
        }
        dependencies.remove(calculatedVariableName);
        return new ArrayList<>(dependencies);
    }

    public void checkForAdditionalVariables(String varName, VarRef varRef, String direction, Decision decision) {
        // The history of this method is unknown but you can have an input variable (varName) that is assigned a
        // variable value (varRef) which has the same name. An example would be a process input variable called
        // foo assigned a value from a calculated variable called foo.
        // The old code would result in missing signature item in this scenario.
        //if (!varName.equals(varRef.getVarName()) && !"Missing".equals(varRef.getVarName())) {
        if (!"Missing".equals(varRef.getVarName())) {
            addNewSignatureItem(varRef, direction, decision);
        }
    }

    public void checkForAdditionalVariables(String varName, VarRef varRef, String direction, Decision decision, String varType) {
        if (!"Missing".equals(varRef.getVarName()) || !varName.equals(varRef.getVarName())) {
            addNewSignatureItem(varRef, direction, decision, varType);
        }
    }

    public boolean checkForGlobalVariable(VarRef varRef, Step originStep, String varName, Decision decision, boolean isRuleSet) {
        // Returns false when the reference was handled here (or there is nothing to reference), true when the
        // caller should treat it as a non-global variable.
        if (varRef == null || varRef.getVarInfoId() == null || varRef.getVarInfoId().isEmpty()) {
            return false;
        }
        String varInfoId = varRef.getVarInfoId();
        if (varInfoId.indexOf('.') >= 0) {
            String source = varInfoId.substring(0, varInfoId.indexOf("."));
            if (GLOBALS_FOLDER.equals(source)) {
                String name = varRef.getVarInfoId().substring(varRef.getVarInfoId().indexOf(".") + 1);
                String globalVariableName = sanitizeVariableName(SOURCE_GLOBAL + "." + name);
                GlobalVariable globalVariable = createGlobalVariable(globalVariableName, varRef.getType());
                if (globalVariable != null) {
                    addNewSignatureItemGlobalVariable(globalVariableName, "none", decision);
                    if (originStep != null) {
                    //    addMapping(globalVariableName, varName, originStep, isRuleSet);
                    }
                }

                if (mapStorage.getVarInfoIdToVariableNameMap().get(varInfoId)==null) {
                    // Store the display name value
                    mapStorage.getVarInfoIdToVariableNameMap().put(varInfoId, globalVariableName);
                }

                return false;
            }
        }

        return true;
    }

    public String createGlobalVariableForDataPicker(String varInfoId, String globalVariableName, String type, Decision decision) {
        String sanitizedGlobalVariableName = sanitizeVariableName(globalVariableName);
        GlobalVariable globalVariable = createGlobalVariable(sanitizedGlobalVariableName, type);
        if (globalVariable != null) {
            addNewSignatureItemGlobalVariable(sanitizedGlobalVariableName, NONE_DIRECTION, decision);
        }

        if (varInfoId != null && !varInfoId.isEmpty()) {
            mapStorage.getVarInfoIdToVariableNameMap().put(varInfoId, sanitizedGlobalVariableName);
        }

        return sanitizedGlobalVariableName;
    }

    public GlobalVariable checkForGlobalVariableRuleSet(ProcessNodeDataDO.Process.InputVariableList.IBVariableDO ibVariableDO) {
        if (ibVariableDO!=null) {
            if (ibVariableDO.getValue()!=null) {
                ValueTypeVarInfoDO valueTypeVarInfoDO = ibVariableDO.getValue().getValueTypeVarInfoDO();
                if (valueTypeVarInfoDO!=null) {
                    String varInfoId = valueTypeVarInfoDO.getVarInfoId();
                    String varInfoSource = valueTypeVarInfoDO.getVarInfoSource();
                    if (RTDM2IDConstants.SOURCE_GLOBAL.equals(varInfoSource)) {
                        // Create Global Variable
                        String name = varInfoId.substring(varInfoId.indexOf(".") + 1);
                        String globalVariableName = sanitizeVariableName(SOURCE_GLOBAL + "." + name);
                        GlobalVariable globalVariable = createGlobalVariable(globalVariableName, valueTypeVarInfoDO.getType());
                        if (globalVariable != null) {
                            return globalVariable;
                        }
                    }
                }
            }
        }
        return null;
    }

    public void checkMappingForDuplicate(Mapping mapping, Step step) {
        if (step.getMappings() != null) {
            Optional<Mapping> optionalMapping = step.getMappings().stream()
                    .filter(m -> m.getStepTermName().equalsIgnoreCase(mapping.getStepTermName())).findFirst();
            if (!optionalMapping.isPresent()) {
                step.addMappingsItem(mapping);
            }
        } else {
            step.addMappingsItem(mapping);
        }
    }

    public void updateRuleSet(String calculatedVariableName, String type, Step step) {
        calculatedVariableName = changeVariableNameToFormat(calculatedVariableName);
        RuleSet ruleSet = new RuleSet();
        RuleSetStep ruleSetForUpdate = step.getRuleSet();
        Mapping mapping = new Mapping();
        mapping.setStepTermName(sanitizeAndReduceVariableName(calculatedVariableName));
        mapping.setTargetDecisionTermName(sanitizeVariableName(calculatedVariableName));
        mapping.setDirection(Mapping.DirectionEnum.OUTPUT);
        checkMappingForDuplicate(mapping, step);

        if (Boolean.FALSE.equals(mapStorage.getRuleSetIsUpdated().get(ruleSetForUpdate.getName()))) {
            ruleSet.setName(ruleSetForUpdate.getName());
            Signature signature = new Signature();
            signature.setDataType(getDatatypeOfVar(type).getValue());
            signature.setName(sanitizeAndReduceVariableName(calculatedVariableName));
            signature.setDirection(OUTPUT_DIRECTION);
            ruleSet.setSignature(Collections.singletonList(signature));

            callPutRuleSet(ruleSetForUpdate, ruleSet);
        }
    }

    public void addActionToRuleSet(RuleSetStep ruleSetForUpdate, Rule rule) {
        try {
            ResponseEntity<Rule> responseEntity = restTemplate.exchange(
                    ViyaApi.createUri(mapStorage.getBaseIp(), "/businessRules/ruleSets/" + ruleSetForUpdate.getId() + "/rules", mapStorage.getProtocol()), HttpMethod.POST,
                    ViyaApi.createRuleWithAction(rule, mapStorage.getAccessToken()), Rule.class);
            if (responseEntity.getStatusCode().value() == 201) {
                mapStorage.getRuleSetIsActionUpdated().put(ruleSetForUpdate.getName(), true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callPutRuleSet(RuleSetStep ruleSetForUpdate, RuleSet ruleSet) {
        try {
            Date modifiedDate = mapStorage.getRuleSetModifiedDate().get(ruleSetForUpdate.getName());
            ResponseEntity<RuleSet> responseEntity = restTemplate.exchange(
                    ViyaApi.createUri(mapStorage.getBaseIp(), "/businessRules/ruleSets/" + ruleSetForUpdate.getId(), mapStorage.getProtocol()), HttpMethod.PUT,
                    ViyaApi.createPutRule(ruleSet, mapStorage.getAccessToken(), modifiedDate), RuleSet.class);
            if (responseEntity.getStatusCode().value() == 200) {
                mapStorage.getRuleSetIsUpdated().put(ruleSetForUpdate.getName(), true);
            }
        } catch (Exception e) {
            try {
                // Log ruleset json to help with debugging
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);

                log.info("\n*** Exception occurred creating RuleSet ***\n");

                log.info("RuleSetStep:\n");
                String ruleSetStepJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ruleSetForUpdate);
                log.info(ruleSetStepJson);

                log.info("\nRuleSet:\n");
                String ruleSetJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ruleSet);
                log.info(ruleSetJson);
            } catch (Exception e1) {
                    e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void processVariableToCreateRuleSet(Step step, Decision decision, RuleSet ruleSet, Rule rule
            , List<ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO> ibVariableDOs) {
        List<Signature> signatureList = new ArrayList<>();
        List<Action> actionList = new ArrayList<>();

        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();

        for (ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO ibVariableDO : ibVariableDOs) {
            IBVariableDO ibVariableDOInner = mapper.ibVariableDoGet(ibVariableDO);
            addMapping(ibVariableDOInner, OUTPUT_DIRECTION, step, true, decision);
            addAction(ibVariableDOInner, actionList, signatureList, step, decision);

            Signature signature = new Signature();
            signature.setDataType(getDatatypeOfVar(ibVariableDO.getTypeDescription()).getValue());
            signature.setName(sanitizeAndReduceVariableName(changeVariableNameToFormat(ibVariableDOInner.getName())));
            signature.setDirection(OUTPUT_DIRECTION);

            handleIncomingSignatureUpdate(OUTPUT_DIRECTION, signatureList, signature);

            if (!signatureList.contains(signature)) {
                signatureList.add(signature);
            } else {
                Signature signatureInner = signatureList.get(signatureList.indexOf(signature));
                if (!signatureInner.getDirection().equals(signature.getDirection())) {
                    signatureInner.setDirection(IN_OUT_DIRECTION);
                }
            }

            if (ibVariableDOInner.getValue() != null) {
                VarRef varRef = VarRefMapper.VAR_REF_MAPPER.varRefGet(ibVariableDOInner.getValue().getValueTypeVarInfoDO());
                if (varRef != null
                        && checkForGlobalVariable(varRef, step, ibVariableDOInner.getName(), decision, true)) {
                    checkForAdditionalVariables(ibVariableDOInner.getName(), varRef
                            , OUTPUT_DIRECTION, decision, ibVariableDOInner.getTypeDescription());
                }
            }
        }
        rule.setActions(actionList);
        ruleSet.setSignature(signatureList);
    }

    public void addAction(IBVariableDO ibVariableDO, List<Action> actionList, List<Signature> signatureList, Step step, Decision decision) {
        Action action = new Action();
        action.setType("complex");
        String value = "";
        if (ibVariableDO.getValue() != null) {
            if (ibVariableDO.getValue().getValueTypeVarInfoDO() != null) {
                String varInfoName = ibVariableDO.getValue().getValueTypeVarInfoDO().getVarName();
                if ("Missing".equals(varInfoName)) {
                    value = "''";
                } else {
                    value = sanitizeAndReduceVariableName(buildVariableName(ibVariableDO.getValue().getValueTypeVarInfoDO()));
                    Signature signature = new Signature();
                    ValueTypeVarInfoDO reference = ibVariableDO.getValue().getValueTypeVarInfoDO();
                    // Type the input term by its source, not by the variable it is assigned to, so the same source
                    // referenced from variables of different types yields one consistently typed term.
                    String sourceType = getDatatypeOfVar(ibVariableDO.getTypeDescription()).getValue();
                    String declaredType = sourceDataTypes.get(
                        sanitizeVariableName(buildVariableName(reference)).toLowerCase(Locale.ROOT));
                    if (reference.getVarInfoId() != null && reference.getVarInfoId().startsWith(GLOBALS_FOLDER + ".")
                            && reference.getType() != null) {
                        GlobalVariable global = mapStorage.getGlobalVariableMap().get(sanitizeVariableName(buildVariableName(reference)));
                        sourceType = getDatatypeOfVar(global != null ? global.getDataType() : reference.getType()).getValue();
                    } else if (declaredType != null && !SignatureTerm.DataTypeEnum.ANY.getValue().equals(declaredType)) {
                        sourceType = declaredType;
                    }
                    signature.setDataType(reference.getVarInfoId() == null ? sourceType
                        : mapStorage.getCalculatedVariableDataTypeMap().getOrDefault(reference.getVarInfoId(), sourceType));
                    signature.setName(value);
                    signature.setDirection(INPUT_DIRECTION);

                    checkSignatureTermForDuplicate(INPUT_DIRECTION, signatureList, signature);
                    addMapping(ibVariableDO, INPUT_DIRECTION, step, true, decision);
                }
            } else if (ibVariableDO.getValue().getDoubleValue() != null) {
                value = ibVariableDO.getValue().getDoubleValue();
            } else if (ibVariableDO.getValue().getBooleanValue() != null) {
                if ("true".equalsIgnoreCase(ibVariableDO.getValue().getBooleanValue())){
                    value = "1";
                }else{
                    value = "0";
                }
            } else if (ibVariableDO.getValue().getDateValue() != null) {
                String dateValueString = ibVariableDO.getValue().getDateValueString();
                value = DateUtil.getInformateDate(dateValueString);
                if(value.isEmpty()){
                    value = ibVariableDO.getValue().getDateValue();
                }
            } else if (ibVariableDO.getValue().getLongValue() != null) {
                value = ibVariableDO.getValue().getLongValue();
            } else if (ibVariableDO.getValue().getListValue() != null) {
                value = ibVariableDO.getValue().getListValue();
            }
            else {
                value = "'" + ibVariableDO.getValue().getStringValue() + "'";
            }
        }
        if (value.isEmpty()) {
            // No value in RTDM (an unset parameter): pass it as missing rather than emitting "x = ". A data grid
            // cannot be assigned a missing value, so it is simply left unassigned.
            SignatureTerm.DataTypeEnum dataType = getDatatypeOfVar(ibVariableDO.getTypeDescription());
            if (SignatureTerm.DataTypeEnum.DATAGRID.equals(dataType)) {
                return;
            }
            value = SignatureTerm.DataTypeEnum.STRING.equals(dataType) ? "''" : ".";
        }
        action.setExpression(sanitizeAndReduceVariableName(ibVariableDO.getName()) + " = " + value);
        actionList.add(action);
    }

    private GlobalVariable createGlobalVariable(String globalVariableName, String type) {
        try {
            CodeFileCollection codeFileCollection
                    = getCodeFileCollection(mapStorage.getBaseIp(), globalVariableName, mapStorage.getAccessToken(), "/referenceData/globalVariables");
            if (codeFileCollection != null) {
                if (codeFileCollection.getCount() == 0) {
                    GlobalVariable globalVariable = new GlobalVariable();
                    String dataType = getDatatypeOfVar(type).toString();
                    globalVariable.setDataType(dataType);
                    globalVariable.setName(sanitizeVariableName(globalVariableName));
                    setDefaultValueByDataType(globalVariable, dataType);
                    ResponseEntity<CodeFile> response = restTemplate.postForEntity(
                            ViyaApi.createUri(mapStorage.getBaseIp(), "/referenceData/globalVariables", mapStorage.getProtocol()),
                            ViyaApi.createGlobalVariables(globalVariable, mapStorage.getAccessToken()), CodeFile.class);
                    if (response.getStatusCode().value() == 201) {
                        CodeFile codeFile = response.getBody();
                        if (codeFile == null || codeFile.getId() == null) {
                            return null;
                        }
                        globalVariable.setId(codeFile.getId());
                        return activateGlobalVariable(globalVariableName, globalVariable,
                                response.getHeaders().getFirst(HttpHeaders.ETAG));
                    }
                } else {
                    CodeFile codeFile = codeFileCollection.getItems().get(0);
                    ResponseEntity<GlobalVariable> response = restTemplate.exchange(
                            ViyaApi.createUri(mapStorage.getBaseIp(), "/referenceData/globalVariables/" + codeFile.getId(), mapStorage.getProtocol()),
                            HttpMethod.GET,
                            ViyaApi.createGetDecisionCollectionOrGlobalVariableRevision(mapStorage.getAccessToken()),
                            GlobalVariable.class);
                    GlobalVariable globalVariable = response.getBody();
                    if (response.getStatusCode().value() == 200 && globalVariable != null) {
                        if (globalVariable.getActiveVersionId() != null && !globalVariable.getActiveVersionId().isEmpty()
                                && "developing".equalsIgnoreCase(globalVariable.getStatus()) && !globalVariable.isLocked()) {
                            mapStorage.getGlobalVariableMap().put(globalVariableName, globalVariable);
                            return globalVariable;
                        }
                        if (globalVariable.getActiveVersionId() == null || globalVariable.getActiveVersionId().isEmpty()) {
                            return activateGlobalVariable(globalVariableName, globalVariable,
                                    response.getHeaders().getFirst(HttpHeaders.ETAG));
                        }
                        return createDevelopingRevision(globalVariableName, globalVariable);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private GlobalVariable activateGlobalVariable(String globalVariableName, GlobalVariable globalVariable, String etag)
            throws URISyntaxException {
        if (globalVariable.getId() == null || etag == null) {
            return null;
        }

        ResponseEntity<GlobalVariableCollection> revisionsResponse = restTemplate.exchange(
                ViyaApi.createUri(mapStorage.getBaseIp(), "/referenceData/globalVariables/" + globalVariable.getId() + "/revisions", mapStorage.getProtocol()),
                HttpMethod.GET,
                ViyaApi.createGetDecisionCollectionOrGlobalVariableRevision(mapStorage.getAccessToken()),
                GlobalVariableCollection.class);
        GlobalVariableCollection revisions = revisionsResponse.getBody();
        if (!revisionsResponse.getStatusCode().is2xxSuccessful()
                || revisions == null || revisions.getItems() == null || revisions.getItems().isEmpty()) {
            return null;
        }

        Item currentRevision = revisions.getItems().get(0);
        if (currentRevision.getId() == null) {
            return null;
        }
        ResponseEntity<String> activationResponse = restTemplate.exchange(
                ViyaApi.createUri(mapStorage.getBaseIp(), "/referenceData/globalVariables/" + globalVariable.getId() + "/active", mapStorage.getProtocol()),
                HttpMethod.PUT,
                ViyaApi.activateGlobalVariable(mapStorage.getAccessToken(), etag, currentRevision.getId()),
                String.class);
        if (activationResponse.getStatusCode().is2xxSuccessful()) {
            globalVariable.setActiveVersionId(currentRevision.getId());
            return createDevelopingRevision(globalVariableName, globalVariable);
        }
        return null;
    }

    private GlobalVariable createDevelopingRevision(String globalVariableName, GlobalVariable globalVariable)
            throws URISyntaxException {
        ResponseEntity<GlobalVariable> revisionResponse = restTemplate.postForEntity(
                ViyaApi.createUri(mapStorage.getBaseIp(), "/referenceData/globalVariables/" + globalVariable.getId() + "/revisions", mapStorage.getProtocol()),
                ViyaApi.createGlobalVariableRevision(globalVariable, mapStorage.getAccessToken()),
                GlobalVariable.class);
        if (revisionResponse.getStatusCode().value() == 201) {
            globalVariable.setStatus("developing");
            globalVariable.setLocked(false);
            mapStorage.getGlobalVariableMap().put(globalVariableName, globalVariable);
            return globalVariable;
        }
        return null;
    }

    /*
     * Add new signature item for a global variable
     */
    public void addNewSignatureItemGlobalVariable(String globalVariableName, String direction, Decision decision) {
        SignatureTerm signatureItem = new SignatureTerm();
        GlobalVariable globalVariable = mapStorage.getGlobalVariableMap().get(globalVariableName);
        signatureItem.setDataType(getDatatypeOfVar(globalVariable.getDataType()));
        signatureItem.setName(sanitizeVariableName(changeVariableNameToFormat(globalVariable.getName())));
        signatureItem.setGlobalVariableId(globalVariable.getId());
        checkSignatureTermForDuplicate(direction, decision, signatureItem);
    }


    public void addNewSignatureItem(IBVariableDO ibVariableDO, String direction, Decision decision, String varInfoSource) {
        SignatureTerm signatureItem = new SignatureTerm();
        signatureItem.setDataType(getDatatypeOfVar(ibVariableDO.getTypeDescription()));
        if (SignatureTerm.DataTypeEnum.DATAGRID.equals(signatureItem.getDataType())) {
            signatureItem.setDataGridExtension(new ArrayList<>());
        }
        String variableName = buildVariableName(ibVariableDO, varInfoSource);
        signatureItem.setName(sanitizeVariableName(changeVariableNameToFormat(variableName)));
        checkSignatureTermForDuplicate(direction, decision, signatureItem);
    }

    /**
     * The Decision term a Start request variable is declared as (see Converter.createDecision), e.g. {@code RequestX}.
     * A parent's sub-decision step maps the child's inputs under these names.
     */
    public String requestTermName(String rtdmName) {
        return sanitizeVariableName(changeVariableNameToFormat(SOURCE_EVENT + "." + rtdmName));
    }

    public void addNewSignatureItem(VarRef varRef, String direction, Decision decision) {
        addNewSignatureItem(varRef, direction, decision, varRef.getType());
    }

    public void addNewSignatureItem(VarRef varRef, String direction, Decision decision, String varType) {
        SignatureTerm signatureItem = new SignatureTerm();

        String producerType = nodeOutputDataType(varRef.getVarInfoId());
        signatureItem.setDataType(getDatatypeOfVar(producerType != null ? producerType : varType));
        if (SignatureTerm.DataTypeEnum.DATAGRID.equals(signatureItem.getDataType())) {
            signatureItem.setDataGridExtension(new ArrayList<>());
        }

        String variableName = buildVariableName(varRef);

        signatureItem.setName(sanitizeVariableName(changeVariableNameToFormat(variableName)));
        checkSignatureTermForDuplicate(direction, decision, signatureItem);
    }

    public String changeVariableNameToFormat(String variableName) {
        while (variableName.contains(":")) {
            variableName = variableName.substring(variableName.indexOf(":") + 1);
        }
        return makeNameValid(variableName);
    }

    public String sanitizeAndReduceVariableName(String name) {
        return reduceVariableName(name);  // name is sanitized when reduced 
    }

    public String sanitizeVariableName(String name) {
        return makeNameValid(name);
    }

    private String makeNameValid(String name) {
        // Signature items only allow alphanumeric and underscore. Strip invalid characters first so the reserved-word
        // and leading-digit checks see the final name (e.g. "Date " must not become the reserved word "Date").
        // Names that were already valid produce exactly the same result as before.
        name = name.replaceAll("[^a-zA-Z0-9_]", EMPTY_STRING);

        if (name.isEmpty()) {
            return SAFE_EMPTY_NAME;
        }

        if (isReservedWord(name)) {
            // Some reserved words start with an underscore so to avoid confusion prefix with a letter
            name = "r" + UNDERSCORE + name;
        }

        // If it starts with a numeric character, prefix with an underscore
        if (Character.isDigit(name.charAt(0))) {
            name = UNDERSCORE + name;
        }

        return name;
    }

    private boolean isReservedWord(String value) {
        return value != null && ReservedWords.isReservedWord(value.toUpperCase() );
    }

    private void setDefaultValueByDataType(GlobalVariable globalVariable, String dataType) {
        if (INTEGER_CONSTANT.equals(dataType) || DECIMAL_CONSTANT.equals(dataType)) {
            globalVariable.setDefaultValue(0);
        } else if (STRING_CONSTANT.equals(dataType)) {
            globalVariable.setDefaultValue("");
        } else {
            globalVariable.setDefaultValue("''");
        }
    }

    public void addMapping(IBVariableDO ibVariableDO, String direction, Step step, boolean isReplyVar, Decision decision) {
        Mapping mapping = new Mapping();
        mapping.setDirection(Mapping.DirectionEnum.fromValue(direction));
        String termName = (ibVariableDO.getName()!=null && !ibVariableDO.getName().isEmpty()) ? ibVariableDO.getName() : ibVariableDO.getPhysicalName();
        mapping.setTargetDecisionTermName(sanitizeVariableName(changeVariableNameToFormat(termName)));
        mapping.setStepTermName(sanitizeAndReduceVariableName(termName));
        if (ibVariableDO.getValue() != null && ibVariableDO.getValue().getValueTypeVarInfoDO() != null) {
            if (!isReplyVar) {
                ValueTypeVarInfoDO reference = ibVariableDO.getValue().getValueTypeVarInfoDO();
                if (!RTDM2IDConstants.SOURCE_GLOBAL.equals(reference.getVarInfoSource())){
                    mapping.setTargetDecisionTermName(sanitizeVariableName(changeVariableNameToFormat(buildVariableName(reference))));
                } else {
                    // Read the global itself: checkForGlobalVariable (via addDataPickerSignatureItems below) declares it
                    // as Global<name>, which ID populates from the Viya global variable
                    String globalName = sourceQualifiedName(reference.getVarInfoId());
                    mapping.setTargetDecisionTermName(sanitizeVariableName(globalName != null ? globalName
                        : SOURCE_GLOBAL + "." + reference.getVarName()));
                }
                // If the value is a variable assignment like a RTDM global variable or identifier, etc. then we
                // need to create a signature item for it.
                addDataPickerSignatureItems(ibVariableDO, direction, decision, step);
            } else {
                if (INPUT_DIRECTION.equals(direction)) {
                    setMappingNameFields(mapping, ibVariableDO);
                }
            }
        }

        checkMappingForDuplicate(mapping, step);
    }

    public void addGlobalVariableMappingForSubDecision(IBVariableDO ibVariableDO, String direction, Step step, Decision decision) {
        Mapping mapping = new Mapping();
        mapping.setDirection(Mapping.DirectionEnum.fromValue(direction));
        mapping.setTargetDecisionTermName(sanitizeVariableName(changeVariableNameToFormat(ibVariableDO.getName())));
        mapping.setStepTermName(sanitizeVariableName(ibVariableDO.getName()));
        addDataPickerSignatureItems(ibVariableDO, direction, decision, null);
        checkMappingForDuplicate(mapping, step);
    }

    public void addMapping(String targetDecisionTermName, String stepTermName, Step step, boolean isRuleSet) {
        Mapping mapping = new Mapping();
        String targetDecisionTermNameChanged = sanitizeVariableName(changeVariableNameToFormat(targetDecisionTermName));
        mapping.setTargetDecisionTermName(targetDecisionTermNameChanged);
        mapping.setDirection(Mapping.DirectionEnum.INPUT);
        mapping.setStepTermName(sanitizeAndReduceVariableName(stepTermName));
        if (isRuleSet) {
            // RuleSet terms are reduced to at most 32 characters, so the mapping must use the reduced name too
            mapping.setStepTermName(sanitizeAndReduceVariableName(targetDecisionTermNameChanged));
        }
        checkMappingForDuplicate(mapping, step);
    }

    public boolean isProcessNotDataProcess(String processTypeDescription) {
        return CUSTOM_CONSTANT.equals(processTypeDescription)
                || BUSINESS_RULES_CONSTANT.equals(processTypeDescription)
                || GROOVY_CONSTANT.equals(processTypeDescription)
                || WEB_SERVICE_CONSTANT.equals(processTypeDescription)
                || MODEL_CONSTANT.equals(processTypeDescription)
                ;
    }

    /**
     * Whether a process input is assigned in the node's {@code _input_values} RuleSet. Data grid and list inputs (lists
     * are one-column data grids) are read by the code file step straight from their source (see ProcessNodeConverter),
     * so the RuleSet has nothing to assign for them; a grid term declared there would also be left unmapped, which ID
     * rejects ("Mapping ... is invalid"). The exception is a calculated data item, whose value is passed through the
     * RuleSet.
     */
    public static boolean isAssignedInInputValues(ProcessNodeDataDO.Process.InputVariableList.IBVariableDO input) {
        String type = input.getTypeDescription();
        boolean grid = type != null && (type.contains("list") || DATAGRID_CONSTANT.equalsIgnoreCase(type)
            || DATAGRID_CONSTANT_SID.equalsIgnoreCase(type));
        if (!grid) {
            return true;
        }
        ValueTypeVarInfoDO reference = input.getValue() == null ? null : input.getValue().getValueTypeVarInfoDO();
        return reference != null && reference.getVarInfoId() != null
            && reference.getVarInfoId().startsWith(CALCULATED_DATA_ITEMS_FOLDER + ".");
    }

    public static boolean hasInputValuesRuleSet(ProcessNodeDataDO.Process process) {
        return process.getInputVariableList() != null
            && process.getInputVariableList().getIbVariableDOs().stream().anyMatch(CommonProcessing::isAssignedInInputValues);
    }

    public boolean isProcessDataProcess(String processTypeDescription) {
        return READ_DATA_CONSTANT.equals(processTypeDescription)
                || INSERT_DATA_CONSTANT.equals(processTypeDescription)
                || UPDATE_DATA_CONSTANT.equals(processTypeDescription)
                ;
    }

    public ConditionBranch createConditionBranch(String nodeId, boolean needToGetNextNode) {
        List<Step> nodeSteps = mapStorage.getNodeIdStepMap().get(nodeId);
        String nodeIdToProcess = nodeId;
        if (needToGetNextNode && (nodeSteps == null || nodeSteps.isEmpty())) {
            nodeIdToProcess = getNextNodeIdForCell(nodeId);
        }

        ConditionBranch conditionBranch = new ConditionBranch();
        List<Step> innerStep = mapStorage.getNodeIdStepMap().get(nodeIdToProcess);
        if (innerStep == null) {
            conditionBranch.setSteps(Collections.emptyList());
        } else {
            mapStorage.getExistingNodeIds().add(nodeIdToProcess);

            // If more than one branch case shares the same downstream node then use cross-branch links
            String linkLabel = mapStorage.getNodeIdLinkLabelMap().get(nodeIdToProcess);
            if (mapStorage.isUseCrossBranchLinks() && linkLabel!=null) {
                // The downstream RTDM node contains a link label which means it has multiple input nodes

                if (!mapStorage.getCrossBranchLinksAdded().contains(linkLabel)) {
                    // Whatever input node gets processed first will contain the original set of inner steps to the downstream node
                    conditionBranch.setSteps(innerStep);

                    // All subsequent input nodes will use a cross-branch link step
                    mapStorage.getCrossBranchLinksAdded().add(linkLabel);
                    mapStorage.getDecisionNodeLinkMap().add(linkLabel);
                    return conditionBranch;
                } else {
                    // Create a new set of inner steps that cross-branch link to the step containing the link label
                    List<Step> crossBranchLinkSteps = mapStorage.getCrossBranchLinkSteps().get(linkLabel);
                    List<Step> newInnerStep = new ArrayList<>();
                    newInnerStep.addAll(crossBranchLinkSteps);
                    conditionBranch.setSteps(newInnerStep);
                }
            } else {
                // No cross-branch link required, use the original inner steps
                conditionBranch.setSteps(innerStep);
            }
        }
        return conditionBranch;
    }

    public void addStep(List<Step> stepList, Step step, String nodeId) {
        // Some RTDM nodes convert to multiple nodes in ID. These can include the following:
        // 1. A ruleset containing a calculated variable expression (if the RTDM node references a calculated variable)
        // 2. An _input_values ruleset to represent the input variable assignments to the node
        // 3. The actual node itself
        //
        // This means the ID step list for a given node can consist up to 3 steps.
        // 
        // If a cross-branch link is required then add it to the 1st step in the list. Since addStep for a given node
        // can be called up to 3 times we need to check if the link label has been added to the step list yet. This is
        // done by checking getNodeIdLinkLabelMap for the presence of a link label for this node.

        if (mapStorage.isUseCrossBranchLinks()) {
            if (mapStorage.getNodeIdLinkLabelMap().get(nodeId)==null) {
                // Find out how many upstream paths lead into this RTDM node (counting through cells, which can merge paths
                // without steps of their own). If there are several then we need to utilize cross-branch link steps
                // otherwise the migration tool will create duplicate paths in the Decision (one for each input path).
                if (treeUtil.upstreamPathCount(nodeId) > 1) {
                    // Multiple inputs nodes so this node requires a linkLabel
                    String linkLabel = UUID.randomUUID().toString();
                    step.setLinkLabel(linkLabel);
    
                    mapStorage.getNodeIdLinkLabelMap().put(nodeId, linkLabel);
    
                    List<Step> crossBranchLinkSteps = createCrossBranchLinkSteps(linkLabel);
                    mapStorage.getCrossBranchLinkSteps().put(linkLabel, crossBranchLinkSteps);
                }
            }
        }

        stepList.add(step);

    }

    private List<Step> createCrossBranchLinkSteps(String linkLabel) {
        Step crossBranchLinkStep = createCrossBranchLinkStep(linkLabel);

        List<Step> crossBranchLinkSteps = new ArrayList<>();
        crossBranchLinkSteps.add(crossBranchLinkStep);

        return crossBranchLinkSteps;
    }

    private Step createCrossBranchLinkStep(String linkLabel) {
        Step crossBranchLinkStep = new Step();
        crossBranchLinkStep.setType(Step.TypeEnum.NODE_LINK);
        crossBranchLinkStep.setDecisionNodeLinkTarget(linkLabel);
        return crossBranchLinkStep;
    }

    public String getNextNodeIdForCell(String cellNodeId) {
        Short objId = treeUtil.getNodeIdObjIdMap().get(cellNodeId);
        String[] nodes = treeUtil.getObjIdOutputNodesMap().get(objId).split(REGEXP);
        return nodes[0];
    }

    public void setMappingNameFields(Mapping mapping, IBVariableDO ibVariableDO) {
        String name = buildVariableName(ibVariableDO.getValue().getValueTypeVarInfoDO());
        mapping.setStepTermName(sanitizeAndReduceVariableName(changeVariableNameToFormat(name)));
        mapping.setTargetDecisionTermName(sanitizeVariableName(changeVariableNameToFormat(name)));
    }

    public String makeCalculatedDataItemRuleSetName(CalculatedItemDO calculatedItemDO) {
        return makeCalculatedDataItemRuleSetName(calculatedItemDO.getName(), calculatedItemDO.getNodeId());
    }

    public String makeCalculatedDataItemRuleSetName(String calculatedItemName, String calculatedItemNodeId) {
        return makeNameValid(calculatedItemName + "_" + calculatedItemNodeId);
    }

    public String makeReplyNodeRuleSetName(ReplyNodeDataDO replyNodeDataDO) {
        return makeNameValid(replyNodeDataDO.getNodeName() + "_" + replyNodeDataDO.getNodeId());
    }

    public String makeSplitNodeRuleSetName(SplitNodeDataDO splitNodeDataDO) {
        return makeNameValid(splitNodeDataDO.getNodeName() + "_" + splitNodeDataDO.getNodeId());
    }

    public String makeProcessNodeRuleSetName(ProcessNodeDataDO processNodeDataDO) {
        return makeNameValid(processNodeDataDO.getNodeName() + "_" + processNodeDataDO.getNodeId());
    }

    public String makeSubdiagramNodeRuleSetName(SubDiagramNodeDataDO subDiagramNodeDataDO) {
        return makeNameValid(subDiagramNodeDataDO.getNodeName() + "_" + subDiagramNodeDataDO.getNodeId());
    }

    public String makeConfirmContactNodeRuleSetName(CHandRHNodeDataDO cHandRHNodeDataDO) {
        return makeNameValid(cHandRHNodeDataDO.getNodeName() + "_" + cHandRHNodeDataDO.getNodeId());
    }

    public String makeAssignmentNodeRuleSetName(AssignmentNodeDataDO assignmentNodeDataDO) {
        return makeNameValid(assignmentNodeDataDO.getNodeName() + "_" + assignmentNodeDataDO.getNodeId());
    }

    public String makeStagedTreatmentName(StagedTreatmentsNodeDataDO stagedTreatmentsNodeDataDO) {
        return makeNameValid(stagedTreatmentsNodeDataDO.getNodeName() + "_" + stagedTreatmentsNodeDataDO.getNodeId());
    }

    public String makeRemoveStagedTreatmentName(RemoveStagedTreatmentsNodeDataDO removeStagedTreatmentsNodeDataDO) {
        return makeNameValid(removeStagedTreatmentsNodeDataDO.getNodeName() + "_" + removeStagedTreatmentsNodeDataDO.getNodeId());
    }

    public String makeCrossTableNodeName(CrossTableNodeDataDO crossTableNodeDataDO) {
        return makeNameValid(crossTableNodeDataDO.getNodeName() + "_" + crossTableNodeDataDO.getNodeId());
    }

    private void addDataPickerSignatureItems(IBVariableDO ibVariableDO, String direction, Decision decision, Step step) {
        // Null check has already been done in the calling method.
        String varInfoSource = ibVariableDO.getValue().getValueTypeVarInfoDO().getVarInfoSource();
        switch (varInfoSource) {
            case SOURCE_GLOBAL:
                VarRef varRef = VarRefMapper.VAR_REF_MAPPER.varRefGet(ibVariableDO.getValue().getValueTypeVarInfoDO());
                checkForGlobalVariable(varRef, step, ibVariableDO.getName(), decision, false);
                break;

            // Following variable sources not supported in ID but we still need to add them to the signature.
            // This will allow ID user to identify these variables and map them to something else post migration.
            case SOURCE_CAMPAIGN:
            case SOURCE_CAMPAIGN_TAG:
            case SOURCE_CELL:
            case SOURCE_IDENTIFIER:
            case SOURCE_PACKAGE:
            case SOURCE_PACKAGE_TAG:
            case SOURCE_REPLY:
            case SOURCE_REPLY_TAG:
            case SOURCE_RUNTIME:
            case SOURCE_TODAYS_DATE_AND_TIME:
                // These are not part of the payload (neither input or output)
                addNewSignatureItem(ibVariableDO, "none", decision, null);
                break;
        }
    }

    public void addRulesetForCalculatedItem(CalculatedItemDO calculatedItemDO) {
        addRulesetForCalculatedItem(calculatedItemDO, Collections.emptyMap());
    }

    private void addGlobalReferenceType(IBVariableDO variable, Map<String, String> inputDataTypes) {
        ValueTypeVarInfoDO reference = variable.getValue() == null ? null : variable.getValue().getValueTypeVarInfoDO();
        String prefix = GLOBALS_FOLDER + ".";
        if (reference != null && reference.getVarInfoId() != null && reference.getVarInfoId().startsWith(prefix)
                && reference.getType() != null) {
            String targetName = SOURCE_GLOBAL + "." + reference.getVarInfoId().substring(prefix.length());
            inputDataTypes.put(sanitizeVariableName(targetName).toLowerCase(Locale.ROOT),
                getDatatypeOfVar(reference.getType()).getValue());
        }
    }

    private void addRulesetForCalculatedItem(CalculatedItemDO calculatedItemDO, Map<String, String> requestDataTypes) {
        try {
            String ruleSetName = makeCalculatedDataItemRuleSetName(calculatedItemDO);
            if (calculatedItemDO.getName() != null) {
                calculatedItemsByName.putIfAbsent(calculatedItemDO.getName(), calculatedItemDO);
            }

            String expression = calculatedItemDO.getExpression();
            findOrAddRuleSet(ruleSetName, calculatedItemDO.getDescription());

            RuleSet ruleSet = new RuleSet();
            ruleSet.setName(ruleSetName);
            mapStorage.getCalculatedVariableMap().put(calculatedItemDO.getName(), calculatedItemDO.getNodeId());
            Rule rule = new Rule();
            Step step = mapStorage.getRuleSetStepMap().get(ruleSetName);
            if (step == null || step.getRuleSet() == null) {
                // findOrAddRuleSet logs and swallows lookup/creation failures; say clearly what that means here.
                mapStorage.getCalculatedVariableDataTypeMap().put(CALCULATED_DATA_ITEMS_FOLDER + "." + calculatedItemDO.getName(),
                    getCalculatedItemDataType(calculatedItemDO.getCalculatedItemType()));
                log.error("RuleSet {} for calculated data item '{}' could not be found or created; "
                    + "the item will not be computed in the migrated Decision", ruleSetName, calculatedItemDO.getName());
                return;
            }
            RuleSetStep ruleSetForUpdate = step.getRuleSet();
            List<Signature> signatureList = new ArrayList<>();
            List<Action> actionList = new ArrayList<>();
            Action action = new Action();
            action.setType("complex");
            String value = "";
            String dataType = getCalculatedItemDataType(calculatedItemDO.getCalculatedItemType());
            mapStorage.getCalculatedVariableDataTypeMap().put(
                CALCULATED_DATA_ITEMS_FOLDER + "." + calculatedItemDO.getName(), dataType);
            Map<String, String> varsInExpresion = new LinkedHashMap<>();
            if ("Missing".equals(expression)){
                value = getMissingValue(dataType);
            }else{
                value = transferInSIDFormat(expression,varsInExpresion);
            }

            String calculatedVariableName = sanitizeAndReduceVariableName(SOURCE_CALCULATED_DATA_ITEM + "." + calculatedItemDO.getName());

            action.setExpression(changeVariableNameToFormat(calculatedVariableName) + "=" + value);
            actionList.add(action);
            rule.setActions(actionList);
            rule.setConditional("if");
            rule.setRuleFiredTrackingEnabled(false);
            rule.setName(calculatedItemDO.getId());
            Signature signature = new Signature();
            signature.setName(changeVariableNameToFormat(calculatedVariableName));
            signature.setDataType(dataType);
            signature.setDirection(OUTPUT_DIRECTION);
            signatureList.add(signature);
            ruleSet.setSignature(signatureList);
                Mapping outputMapping = new Mapping();
                outputMapping.setStepTermName(signature.getName());
                outputMapping.setTargetDecisionTermName(sanitizeVariableName(
                    SOURCE_CALCULATED_DATA_ITEM + "." + calculatedItemDO.getName()));
                outputMapping.setDirection(Mapping.DirectionEnum.OUTPUT);
                checkMappingForDuplicate(outputMapping, step);

            varsInExpresion.forEach((item, targetName)->{
                Signature _signature = new Signature();
                _signature.setName(sanitizeAndReduceVariableName(changeVariableNameToFormat(item)));
                String targetKey = sanitizeVariableName(targetName).toLowerCase(Locale.ROOT);
                // Upstream process/subdiagram outputs are typed by their producer (see registerNodeOutputType)
                _signature.setDataType(requestDataTypes.getOrDefault(targetKey,
                    mapStorage.getNodeOutputDataTypes().getOrDefault(targetKey, "any")));
                _signature.setDirection(INPUT_DIRECTION);
                checkSignatureTermForDuplicate(INPUT_DIRECTION, signatureList, _signature);
                Mapping inputMapping = new Mapping();
                inputMapping.setStepTermName(_signature.getName());
                inputMapping.setTargetDecisionTermName(sanitizeVariableName(targetName));
                inputMapping.setDirection(Mapping.DirectionEnum.INPUT);
                checkMappingForDuplicate(inputMapping, step);
            });
            ruleSet.setSignature(signatureList);
            ruleSet.setDescription(calculatedItemDO.getDescription());
            
            if (!(Boolean.TRUE.equals(mapStorage.getRuleSetIsActionUpdated().get(ruleSetForUpdate.getName()))
                    && Boolean.TRUE.equals(mapStorage.getRuleSetIsUpdated().get(ruleSetForUpdate.getName())))) {
                callPutRuleSet(ruleSetForUpdate, ruleSet);
                addActionToRuleSet(ruleSetForUpdate, rule);
            }
        } catch (Exception e) {
            log.error("Could not prepare the RuleSet for calculated data item '{}'", calculatedItemDO.getName(), e);
        }
    }

    private String getMissingValue(String dataType) {
        String missingValue = "''";
        switch (dataType) {
            case STRING_CONSTANT:
                missingValue = "''";
                break;
            case DECIMAL_CONSTANT:
            case DATE_CONSTANT:
            case DATETIME_CONSTANT:
            case DATAGRID_CONSTANT_SID:
            case BOOLEAN_CONSTANT:
                missingValue = ".";
                break;
            default:
                missingValue = "''";
                break;
        }
        return missingValue;
    }

    private static final String HASHED_TERM_PREFIX = "h";

    private String transferInSIDFormat(String expression, Map<String, String> variableTargets) {
        return transferInSIDFormat(expression, variableTargets, Collections.emptyMap());
    }

    /**
     * @param calculatedTerms calculated items computed by the same RuleSet -> the term they are assigned to, which
     *                        references to them must use
     */
    private String transferInSIDFormat(String expression, Map<String, String> variableTargets,
            Map<String, String> calculatedTerms) {
        String pattern = "<<(.*?)\\.(.*?)>>";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(expression);
        StringBuffer outputBuffer = new StringBuffer();
        while (matcher.find()) {
            String source = matcher.group(1);
            String referencedName = matcher.group(2);
            String matchedVar = referencedName;

            if (matchedVar.length() > 32) {
                // Variables referenced inside a RTDM calculated variable expression can exceed 32 characters.
                // For example: HBOUND(<<getUrbisGWBOpeningTime.DP_getUrbisGWBOpeningTimeEMAIL_ADDR_UPDATABLE>>)
                // In this example the variable name inside the expression consists of 3 parts.
                // 1. Campaign node name: getUrbisGWBOpeningTime
                // 2. SAS Process referenced by node: DP_getUrbisGWBOpeningTime
                // 3. SAS Process output variable being referenced: EMAIL_ADDR_UPDATABLE
                //
                // Items #1 or #2 are used to uniquely identify the variable name in the flow and cannot be removed.
                // In RTDM you can have multiple campaign nodes referencing either the same SAS process or different
                // SAS processes that happen to have the same output variable names.
                //
                // ID cannot contain ruleset term names > 32 character so use hash codes to reduce the variable name length.
                if (mapStorage.getCustomObjectStepHashMap()!=null && !mapStorage.getCustomObjectStepHashMap().isEmpty()) {
                    // Find the custom object step (node) name used in the calculated variable expression. The longest
                    // matching name wins so the result does not depend on HashMap iteration order.
                    String stepName = null;
                    for (CustomObjectStep customStepValue : mapStorage.getCustomObjectStepHashMap().values()) {
                        String candidate = customStepValue == null ? null : customStepValue.getName();
                        if (candidate != null && !candidate.isEmpty() && matchedVar.startsWith(candidate)
                                && (stepName == null || candidate.length() > stepName.length())) {
                            stepName = candidate;
                        }
                    }
                    if (stepName != null) {
                        // Hash step name to reduce length (unsigned so a negative hash cannot leave a leading '-'). The
                        // 'h' prefix makes the term start with a letter: a leading digit would otherwise be turned into
                        // a leading '_' by makeNameValid, which ID rejected ("Mapping _3066298432_... is invalid").
                        String termName = matchedVar.substring(stepName.length());
                        matchedVar = HASHED_TERM_PREFIX + Integer.toUnsignedString(stepName.hashCode()) + "_" + termName;
                        if (matchedVar.length() > 32) {
                            // It's possible the name is still too long so hash the variable name
                            matchedVar = HASHED_TERM_PREFIX + Integer.toUnsignedString(stepName.hashCode()) + "_"
                                + Integer.toUnsignedString(termName.hashCode());
                        }
                    }
                }
            }

            // Use the same (reduced) name in the expression as in the RuleSet signature
            String modifiedVar = sanitizeAndReduceVariableName(changeVariableNameToFormat(matchedVar));
            String targetName = modifiedVar;
            switch (source) {
                case "inputVariables":
                case EVENTS_FOLDER:
                    targetName = SOURCE_EVENT + "." + referencedName;
                    break;
                case CALCULATED_DATA_ITEMS_FOLDER:
                    targetName = SOURCE_CALCULATED_DATA_ITEM + "." + referencedName;
                    modifiedVar = calculatedTerms.getOrDefault(referencedName, modifiedVar);
                    break;
                case GLOBALS_FOLDER:
                    targetName = SOURCE_GLOBAL + "." + referencedName;
                    break;
                case IDENTIFIERS_FOLDER:
                    targetName = Objects.requireNonNullElse(nodeOutputReference(source + "." + referencedName),
                        SOURCE_IDENTIFIER + "." + referencedName);
                    break;
                default:
                    // An upstream process/subdiagram output, read under the name its producer writes
                    String nodeOutput = nodeOutputReference(source + "." + referencedName);
                    if (nodeOutput != null) {
                        targetName = nodeOutput;
                    }
                    break;
            }
            modifiedVar = uniqueExpressionTerm(modifiedVar, targetName, variableTargets);
            variableTargets.put(modifiedVar, targetName);
            matcher.appendReplacement(outputBuffer, Matcher.quoteReplacement(modifiedVar));
        }
        matcher.appendTail(outputBuffer);
        return outputBuffer.toString();
    }

    /**
     * Returns the RuleSet term used for a reference inside a calculated expression. The bare variable name is kept
     * (as before) unless another source with the same name was already referenced in the expression, e.g.
     * {@code <<events.Amt>> - <<globals.Amt>>}; then the source-qualified name keeps the two inputs distinct.
     */
    private String uniqueExpressionTerm(String term, String targetName, Map<String, String> variableTargets) {
        String candidate = term;
        int suffix = 1;
        while (true) {
            String existingKey = null;
            for (String key : variableTargets.keySet()) {
                if (key.equalsIgnoreCase(candidate)) {
                    existingKey = key;
                    break;
                }
            }
            if (existingKey == null) {
                return candidate;
            }
            if (sanitizeVariableName(variableTargets.get(existingKey)).equalsIgnoreCase(sanitizeVariableName(targetName))) {
                return existingKey;
            }
            candidate = suffix == 1 ? sanitizeAndReduceVariableName(targetName)
                : sanitizeAndReduceVariableName(targetName + UNDERSCORE + suffix);
            suffix++;
        }
    }

    public String getCalculatedItemDataType(String type){
        String dataType;
        switch (type) {
            case CALCULATED_DATA_TYPE_CHARACTER:
                dataType = STRING_CONSTANT;
                break;
            case CALCULATED_DATA_TYPE_NUMERIC_NOMINAL:
            case CALCULATED_DATA_TYPE_NUMERIC_INTERVAL:
                dataType = DECIMAL_CONSTANT;
                break;
            case CALCULATED_DATA_TYPE_TIMESTAMP:
                // A timestamp holds date and time, like RTDM date variables (see getDatatypeOfVar)
                dataType = DATETIME_CONSTANT;
                break;
            case CALCULATED_DATA_TYPE_DATE:
                dataType = DATE_CONSTANT;
                break;
            case CALCULATED_DATA_TYPE_DATAGRID:
                dataType =  DATAGRID_CONSTANT_SID;
                break;
            case CALCULATED_DATA_TYPE_BOOLEAN:       
                dataType = BOOLEAN_CONSTANT;
                break;
            default:
                dataType = "any";
                break;
        }
        return dataType;
    }

    private String reduceVariableName(String variableName) {
        String reducedVariableName = makeNameValid(variableName);

        if (reducedVariableName.length() > 32) {
            // Algorithm to reduce variable name to 32 characters whilst still retain some recognizable characters
            // Example:
            // _00_scoreValueWith3_85Downpay_POS_FEBBSPVLUNHKWBSW
            // This would be reduced to something like this:
            // _00_scoreValueWith3_852147483647

            // Generate hash code of variable name
            // (widened to long so Integer.MIN_VALUE does not stay negative and leave a '-' in the name)
            String hashCodeValue = String.valueOf(Math.abs((long) reducedVariableName.hashCode()));

            // Subtract hash code length from 32 and substring remaining characters from variable name
            String amountOfCharactersToExtract = reducedVariableName.substring(0, 32 - hashCodeValue.length());
            
            // Append hash code onto end of variable name to retain variable name uniqueness,
            // this gives us a variable name with a length of 32 characters
            reducedVariableName = amountOfCharactersToExtract + hashCodeValue;          
        }

        return reducedVariableName;
    }

    public void processAssignmentVariables(Step step, Decision decision, RuleSet ruleSet, RuleSetStep ruleSetForUpdate,List<com.sas.rtdm2id.model.rtdm.extension.IBVariableDO> ibVariableDOs) {
        postAssignmentRules(ruleSetForUpdate, buildAssignmentRules(step, decision, ruleSet, ibVariableDOs));
    }

    /**
     * Builds one rule per assignment, adds the step mappings and Decision signature terms, and sets the RuleSet
     * signature on {@code ruleSet} — without writing anything to Viya, so callers can PUT the signature before the
     * rules that reference it are posted.
     */
    public List<Rule> buildAssignmentRules(Step step, Decision decision, RuleSet ruleSet, List<com.sas.rtdm2id.model.rtdm.extension.IBVariableDO> ibVariableDOs) {
        List<Signature> signatureList = new ArrayList<>();
        List<Rule> rules = new ArrayList<>();
        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();

        for (com.sas.rtdm2id.model.rtdm.extension.IBVariableDO ibVariableDO : ibVariableDOs) {
            Rule rule = new Rule();
            List<Action> actionList = new ArrayList<>();
            IBVariableDO ibVariableDOInner = mapper.ibVariableDoGet(ibVariableDO);
            addMapping(ibVariableDOInner, IN_OUT_DIRECTION, step, true, decision);
            addAction(ibVariableDOInner, actionList, signatureList, step, decision);
            processEventVars(decision, ibVariableDOInner, OUTPUT_DIRECTION, ibVariableDOInner.getValue());
            Signature signature = new Signature();
            signature.setDataType(getDatatypeOfVar(ibVariableDO.getTypeDescription()).getValue());
            signature.setName(sanitizeAndReduceVariableName(changeVariableNameToFormat(ibVariableDOInner.getName())));
            signature.setDirection(IN_OUT_DIRECTION);
            handleIncomingSignatureUpdate(IN_OUT_DIRECTION, signatureList, signature);
            
            if (!signatureList.contains(signature)) {
                signatureList.add(signature);
            } else {
                Signature signatureInner = signatureList.get(signatureList.indexOf(signature));
                if (!signatureInner.getDirection().equals(signature.getDirection())) {
                    signatureInner.setDirection(IN_OUT_DIRECTION);
                }
            }

            if (ibVariableDOInner.getValue() != null) {
                VarRef varRef = VarRefMapper.VAR_REF_MAPPER.varRefGet(ibVariableDOInner.getValue().getValueTypeVarInfoDO());
                if (varRef != null
                        && checkForGlobalVariable(varRef, step, ibVariableDOInner.getName(), decision, true)) {
                    checkForAdditionalVariables(ibVariableDOInner.getName(), varRef
                            , OUTPUT_DIRECTION, decision, ibVariableDOInner.getTypeDescription());
                }
            }
            rule.setActions(actionList);
            rule.setName(UUID.randomUUID().toString());
            rule.setConditional("if");
            rule.setRuleFiredTrackingEnabled(false);
            rules.add(rule);
        }

        ruleSet.setSignature(signatureList);
        return rules;
    }

    /** Posts assignment rules unless the RuleSet is reused and therefore already has them. */
    public void postAssignmentRules(RuleSetStep ruleSetForUpdate, List<Rule> rules) {
        // Decided once, before any rule is posted (each successful post marks the RuleSet as action-updated): a reused
        // RuleSet already has its rules, so posting them again would append duplicates on every re-run.
        if (ruleSetForUpdate == null
                || Boolean.TRUE.equals(mapStorage.getRuleSetIsActionUpdated().get(ruleSetForUpdate.getName()))) {
            return;
        }
        rules.forEach(rule -> addActionToRuleSet(ruleSetForUpdate, rule));
    }

    private void processEventVars(Decision decision, IBVariableDO ibVariableDO, String direction, ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value) {
        addNewSignatureItem(ibVariableDO, direction, decision, null);
        if (value != null) {
           VarRef varRef = VarRefMapper.VAR_REF_MAPPER.varRefGet(ibVariableDO.getValue().getValueTypeVarInfoDO());
           if (varRef != null){
               if (checkForGlobalVariable(varRef, null, ibVariableDO.getName(), decision, false)) {
                  checkForAdditionalVariables(ibVariableDO.getName(), varRef, "none", decision);
                }
           }

        }
    }

    private String buildVariableName(ValueTypeVarInfoDO valueTypeVarInfoDO) {
        // Upstream node outputs and identifiers: the term their producer writes (see registerNodeOutputs)
        String registered = nodeOutputReference(valueTypeVarInfoDO.getVarInfoId());
        if (registered != null) {
            return registered;
        }
        String variableName = null;

        switch (Objects.requireNonNullElse(valueTypeVarInfoDO.getVarInfoSource(), EMPTY_STRING)) {
            case SOURCE_NODE:
                // Upstream Nodes folder (return Node Name + "." + Output Variable Name)
                // Example: Read Database.AGE
                String varName = valueTypeVarInfoDO.getVarName() !=null 
                    ? valueTypeVarInfoDO.getVarName() 
                    : valueTypeVarInfoDO.getVarInfoPhysicalName();
                //variableName = valueTypeVarInfoDO.getVarInfoSourceName() + "." + varName;
                // VarInfoId is the only way to uniquely identify an upstream node variable in RTDM
                variableName = valueTypeVarInfoDO.getVarInfoId();
                break;
            case SOURCE_CALCULATED_DATA_ITEM:
            case SOURCE_CELL:
            case SOURCE_EVENT:
            case SOURCE_GLOBAL:
            case SOURCE_IDENTIFIER:
            case SOURCE_RUNTIME:
                // Examples: Request.ClientId, Global.MAX_SEGMENTS, Identifier.CustId, Cell.Code, Runtime.Event Identity,
                // CalculatedDataItem.CalculateCost
                variableName = getVariableNameFromValueTypeVarInfoDO(valueTypeVarInfoDO);
                break;
            case SOURCE_PACKAGE:
                variableName = buildVariableNameFromPackageSource(valueTypeVarInfoDO);
                break;
            case SOURCE_CAMPAIGN:
                variableName = buildVariableNameFromCampaignSource(valueTypeVarInfoDO);
                break;
            case SOURCE_REPLY:
                variableName = buildVariableNameFromReplySource(valueTypeVarInfoDO);
                break;
            case SOURCE_TODAYS_DATE_AND_TIME:
                // Example: DateAndTime.today
                variableName = valueTypeVarInfoDO.getVarInfoId();
                break;
            default:
                variableName = valueTypeVarInfoDO.getVarInfoId();
        }

        if (mapStorage.getVarInfoIdToVariableNameMap().get(valueTypeVarInfoDO.getVarInfoId())==null) {
            // Store the display name value
            mapStorage.getVarInfoIdToVariableNameMap().put(valueTypeVarInfoDO.getVarInfoId(), variableName);
        }

        return variableName;
    }

    private String buildVariableNameFromPackageSource(ValueTypeVarInfoDO valueTypeVarInfoDO) {
        String variableName = null;

        switch (valueTypeVarInfoDO.getVarInfoSourceName()) {
            case PACKAGE_FOLDER:
                // Examples: Package.Code
                variableName = getVariableNameFromValueTypeVarInfoDO(valueTypeVarInfoDO);
                break;
            case PACKAGE_TAGS_FOLDER:
                // Example: Package.Tag.Channel
                variableName = valueTypeVarInfoDO.getVarInfoSource() + "." + 
                    TAG_CONSTANT + "." + 
                     getVariableNameFromValueTypeVarInfoDO(valueTypeVarInfoDO);
                break;
        }

        return variableName;
    }

    private String buildVariableNameFromCampaignSource(ValueTypeVarInfoDO valueTypeVarInfoDO) {
        String variableName = null;

        switch (valueTypeVarInfoDO.getVarInfoSourceName()) {
            case CAMPAIGN_FOLDER:
                // Example: Campaign.Code
                variableName = valueTypeVarInfoDO.getVarInfoSource() + "." + getVariableNameFromValueTypeVarInfoDO(valueTypeVarInfoDO);
                break;
            case CAMPAIGN_BRIEF_CUSTOM_DETAILS_SOURCE:
                // Example: Campaign.Brief Custom Details.Offer
                variableName = valueTypeVarInfoDO.getVarInfoSource() + "." + 
                    valueTypeVarInfoDO.getVarInfoSourceName() + "." +
                    getVariableNameFromValueTypeVarInfoDO(valueTypeVarInfoDO);
                break;
            case CAMPAIGN_TAGS_FOLDER:
                // Example: Package.Tag.Channel
                variableName = valueTypeVarInfoDO.getVarInfoSource() + "." + 
                    TAG_CONSTANT + "." + 
                    getVariableNameFromValueTypeVarInfoDO(valueTypeVarInfoDO);
                break;
        }

        return variableName;
    }

    private String buildVariableNameFromReplySource(ValueTypeVarInfoDO valueTypeVarInfoDO) {
        String variableName = null;

        switch (valueTypeVarInfoDO.getVarInfoSourceName()) {
            case REPLY_FOLDER:
                // Example: Reply.Code
                variableName = valueTypeVarInfoDO.getVarInfoSource() + "." + getVariableNameFromValueTypeVarInfoDO(valueTypeVarInfoDO);
                break;
            case REPLY_TAGS_FOLDER:
                // Example: Reply.Tag.OfferType
                variableName = valueTypeVarInfoDO.getVarInfoSource() + "." + 
                    TAG_CONSTANT + "." + 
                    getVariableNameFromValueTypeVarInfoDO(valueTypeVarInfoDO);
                break;
        }

        return variableName;
    }

    private String getVariableNameFromValueTypeVarInfoDO(ValueTypeVarInfoDO valueTypeVarInfoDO) {
        return getSourceName(valueTypeVarInfoDO) + "." + 
            (valueTypeVarInfoDO.getVarName() !=null 
                ? valueTypeVarInfoDO.getVarName() 
                : valueTypeVarInfoDO.getVarInfoPhysicalName());
    }

    private String getSourceName(ValueTypeVarInfoDO valueTypeVarInfoDO) {
        switch (valueTypeVarInfoDO.getVarInfoSource()) {
            case SOURCE_CALCULATED_DATA_ITEM:
            case SOURCE_CELL:
            case SOURCE_EVENT:
            case SOURCE_GLOBAL:
            case SOURCE_IDENTIFIER:
            case SOURCE_RUNTIME:
                return valueTypeVarInfoDO.getVarInfoSource();
            default:
                return valueTypeVarInfoDO.getVarInfoSourceName() != null
                    ? valueTypeVarInfoDO.getVarInfoSourceName()
                    : valueTypeVarInfoDO.getVarInfoSource();
        }
    }

    private String buildVariableName(IBVariableDO ibVariableDO, String varInfoSource) {
        // Build a display value for the variable name the same way that RTDM does
        String name = (ibVariableDO.getName() !=null && !ibVariableDO.getName().isEmpty()) ? ibVariableDO.getName() : ibVariableDO.getPhysicalName();
        return varInfoSource!=null ? varInfoSource + "." + name : name;
    }

    private String buildVariableName(VarRef varRef) {
        return variableNameFor(varRef.getVarInfoId());
    }

    public String buildVariableName(VarRefDO varRefDO) {
        return variableNameFor(varRefDO.getVarInfoId());
    }

    private String variableNameFor(String varInfoId) {
        // Request, global, calculated and identifier items are named after their source, exactly as the Decision
        // signature declares them; process/subdiagram outputs by the name their producer writes; otherwise use the
        // display value recorded for the id if available, else the id itself.
        String variableName = nodeOutputReference(varInfoId);
        if (variableName == null) variableName = sourceQualifiedName(varInfoId);
        if (variableName == null) variableName = mapStorage.getVarInfoIdToVariableNameMap().get(varInfoId);
        if (variableName == null) variableName = varInfoId;
        return variableName;
    }

    /**
     * Records, for every process and subdiagram output in the extract, the name it is written under in the Decision.
     * RTDM refers to an output as {@code <NodeName>.<ProcessName><var>} (subdiagrams: {@code <NodeName>.<var>}). An
     * output linked to an identifier is written to that identifier ({@code Identifier.<name>}), because in RTDM the
     * value lives there and later nodes read it from there; any other output is written under its qualified id.
     * Producers ({@link #addProducedOutput}) and readers ({@link #nodeOutputReference}) both resolve names here.
     */
    public void registerNodeOutputs(Batch batch) {
        Map<String, String> outputs = mapStorage.getNodeOutputReferences();
        outputs.clear();
        mapStorage.getNodeOutputDataTypes().clear();
        if (batch == null || batch.getLogicalUnit() == null) {
            return;
        }
        // An identifier fed by a Start request variable lives in that request variable's term: the caller supplies the
        // initial value and processes linked to the identifier overwrite it, as RTDM does. Other identifiers have their
        // own Identifier.<name> term. Readers of identifiers.<name> resolve through the same entries.
        for (StartNodeDataDO.Event.RequestVars.IBVariableDO variable : startRequestVariables(batch.getLogicalUnit().getStartNodeDataDO())) {
            String identifier = variable.getIdentifier() == null ? null : variable.getIdentifier().getName();
            String name = variable.getName() != null && !variable.getName().isEmpty() ? variable.getName() : variable.getPhysicalName();
            if (identifier != null && !identifier.isBlank() && name != null) {
                outputs.putIfAbsent(IDENTIFIERS_FOLDER + "." + identifier, SOURCE_EVENT + "." + name);
            }
        }
        for (ProcessNodeDataDO node : batch.getLogicalUnit().getProcessNodeDataDOs()) {
            ProcessNodeDataDO.Process process = node.getProcess();
            if (process == null || process.getOutputVariableList() == null) {
                continue;
            }
            // The columns of a data process's returned grid are lists in RTDM but are extracted as scalars (see the
            // _extract_values RuleSet), so they are typed by their element type; other list outputs are data grids
            boolean gridColumns = !isProcessNotDataProcess(process.getProcessTypeDescription())
                && Boolean.parseBoolean(process.getReturnDatagridFlag());
            String prefix = outputReferencePrefix(process);
            process.getOutputVariableList().getIbVariableDOs().forEach(output -> {
                registerNodeOutput(outputs, node.getNodeName(), prefix, output);
                registerNodeOutputType(nodeOutputId(node.getNodeName(), prefix, output.getName()),
                    output.getTypeDescription(), !gridColumns);
            });
            // A data process returning a data grid: the grid is referenced as <NodeName>.<ProcessName><DatagridName>
            // and may be copied to an identifier (DatagridIdentifier)
            if (Boolean.parseBoolean(process.getReturnDatagridFlag()) && process.getDatagridName() != null
                    && !process.getDatagridName().isBlank()) {
                String identifier = process.getDatagridIdentifier() == null ? null : process.getDatagridIdentifier().getName();
                String gridId = nodeOutputId(node.getNodeName(), process.getName(), process.getDatagridName());
                registerNodeOutput(outputs, gridId, identifier);
                registerNodeOutputType(gridId, DATAGRID_CONSTANT, true);
            }
        }
        for (SubDiagramNodeDataDO node : batch.getLogicalUnit().getSubDiagramNodeDataDOs()) {
            if (node.getSubdiagramEvent() != null && node.getSubdiagramEvent().getReplyVars() != null) {
                node.getSubdiagramEvent().getReplyVars().getIbVariableDOs().forEach(output -> {
                    registerNodeOutput(outputs, node.getNodeName(), null, output);
                    registerNodeOutputType(nodeOutputId(node.getNodeName(), null, output.getName()),
                        output.getTypeDescription(), true);
                });
            }
        }
        // A Cross-Table writes its output variable, read as <NodeName>.<Output> or, when linked, as the identifier
        for (CrossTableNodeDataDO node : batch.getLogicalUnit().getCrossTableNodeDataDOs()) {
            CrossTableNodeDataDO.OutputVariable output = node.getOutputVariable();
            if (output == null || output.getName() == null || output.getName().isEmpty()) {
                continue;
            }
            String outputId = nodeOutputId(node.getNodeName(), null, output.getName());
            registerNodeOutput(outputs, outputId, output.getIdentifier() == null ? null : output.getIdentifier().getName());
            registerNodeOutputType(outputId, output.getTypeDescription(), true);
        }
        // Assignment nodes write a term named after the variable, which is also how calculated expressions read it
        // (<<Node Name.Variable>>)
        for (AssignmentNodeDataDO node : batch.getLogicalUnit().getAssignmentNodeDataDOs()) {
            if (node.getAssignments() == null || node.getAssignments().getIbVariableDOs() == null) {
                continue;
            }
            for (com.sas.rtdm2id.model.rtdm.extension.IBVariableDO variable : node.getAssignments().getIbVariableDOs()) {
                SignatureTerm.DataTypeEnum type = getDatatypeOfVar(variable.getTypeDescription());
                if (variable.getName() != null && !SignatureTerm.DataTypeEnum.ANY.equals(type)) {
                    mapStorage.getNodeOutputDataTypes().putIfAbsent(
                        sanitizeVariableName(changeVariableNameToFormat(variable.getName())).toLowerCase(Locale.ROOT),
                        type.getValue());
                }
            }
        }
    }

    /**
     * Records the ID data type of the term an output is written under, so terms that read it before its producer is
     * converted (e.g. calculated item RuleSets, built first) are declared with the producer's type rather than "any",
     * which ID rejects when the term is mapped ("Mapped term ... has an invalid data type"). Lists are written as data
     * grids when {@code listsAreGrids}. When several producers write the same term (an identifier) the first type is
     * kept.
     */
    private void registerNodeOutputType(String outputId, String typeDescription, boolean listsAreGrids) {
        if (outputId == null || typeDescription == null || nodeOutputReference(outputId) == null) {
            return;
        }
        SignatureTerm.DataTypeEnum type = listsAreGrids && typeDescription.contains("list")
            ? SignatureTerm.DataTypeEnum.DATAGRID : getDatatypeOfVar(typeDescription);
        if (!SignatureTerm.DataTypeEnum.ANY.equals(type)) {
            mapStorage.getNodeOutputDataTypes().putIfAbsent(nodeOutputTermName(outputId).toLowerCase(Locale.ROOT), type.getValue());
        }
    }

    private static List<StartNodeDataDO.Event.RequestVars.IBVariableDO> startRequestVariables(StartNodeDataDO start) {
        if (start == null) {
            return List.of();
        }
        if (start.getEvent() != null && start.getEvent().getRequestVars() != null) {
            return start.getEvent().getRequestVars().getIbVariableDOs();
        }
        if (start.getInputVariables() != null) {
            return start.getInputVariables().getIbVariableDOs();
        }
        return List.of();
    }

    private static void registerNodeOutput(Map<String, String> outputs, String nodeName, String processName,
            ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO output) {
        if (output.getName() == null) {
            return;
        }
        String identifier = output.getIdentifier() == null ? null : output.getIdentifier().getName();
        registerNodeOutput(outputs, nodeOutputId(nodeName, processName, output.getName()), identifier);
    }

    private static void registerNodeOutput(Map<String, String> outputs, String id, String identifier) {
        if (identifier == null || identifier.isBlank()) {
            outputs.put(id, id);
            return;
        }
        // Linked outputs write the identifier's term (a Start request variable's term when it feeds the identifier)
        String identifierKey = IDENTIFIERS_FOLDER + "." + identifier;
        outputs.putIfAbsent(identifierKey, SOURCE_IDENTIFIER + "." + identifier);
        outputs.put(id, outputs.get(identifierKey));
    }

    /**
     * The process name RTDM puts in front of a process output's name when other nodes refer to it, see
     * {@link #nodeOutputId}. Outputs of a rules group are referred to without it ({@code <NodeName>.<var>}, every one of
     * the 159 references in the customer extracts); those of other processes with it.
     */
    public static String outputReferencePrefix(ProcessNodeDataDO.Process process) {
        return BUSINESS_RULES_CONSTANT.equals(process.getProcessTypeDescription()) ? null : process.getName();
    }

    /**
     * RTDM's id for a node output: {@code <NodeName>.<ProcessName><var>}; subdiagram and rules group outputs have no
     * process name.
     */
    public static String nodeOutputId(String nodeName, String processName, String variableName) {
        return Objects.toString(nodeName, "") + "." + Objects.toString(processName, "") + variableName;
    }

    /** The (unsanitized) name a process/subdiagram output is written under, or null if the id is not one. */
    public String nodeOutputReference(String varInfoId) {
        return varInfoId == null ? null : mapStorage.getNodeOutputReferences().get(varInfoId);
    }

    /**
     * Maps a process/subdiagram output to the Decision term it is written under (see {@link #registerNodeOutputs})
     * and declares that term.
     */
    public void addProducedOutput(String outputId, String stepTermName, String typeDescription, Step step, Decision decision) {
        addProducedOutput(outputId, stepTermName, getDatatypeOfVar(typeDescription), null, step, decision);
    }

    /**
     * As {@link #addProducedOutput(String, String, String, Step, Decision)} for an explicit type; a data grid term is
     * declared with its columns. Returns the Decision term name.
     */
    public String addProducedOutput(String outputId, String stepTermName, SignatureTerm.DataTypeEnum dataType,
            List<SignatureTermExtension> gridColumns, Step step, Decision decision) {
        String targetName = nodeOutputTermName(outputId);
        Mapping mapping = new Mapping();
        mapping.setDirection(Mapping.DirectionEnum.OUTPUT);
        mapping.setStepTermName(sanitizeAndReduceVariableName(stepTermName));
        mapping.setTargetDecisionTermName(targetName);
        checkMappingForDuplicate(mapping, step);

        SignatureTerm term = new SignatureTerm();
        term.setName(targetName);
        term.setDataType(dataType);
        if (SignatureTerm.DataTypeEnum.DATAGRID.equals(dataType)) {
            term.setDataGridExtension(gridColumns != null ? new ArrayList<>(gridColumns) : new ArrayList<>());
        }
        checkSignatureTermForDuplicate(NONE_DIRECTION, decision, term);
        return targetName;
    }

    /**
     * The ID data type its producer writes a process/subdiagram/assignment output under, or null when the reference is
     * not a registered output. Readers use it over the type on their own variable reference: a reference without
     * metadata (NoMetadata) can carry a default type (e.g. Numeric for a string output), which would declare the
     * Decision term with a type the producer's mapping and the consuming RuleSets contradict.
     */
    public String nodeOutputDataType(String varInfoId) {
        if (varInfoId == null || nodeOutputReference(varInfoId) == null) {
            return null;
        }
        return mapStorage.getNodeOutputDataTypes().get(nodeOutputTermName(varInfoId).toLowerCase(Locale.ROOT));
    }

    /** The (sanitized) Decision term a process/subdiagram output is written under (see {@link #registerNodeOutputs}). */
    public String nodeOutputTermName(String outputId) {
        String reference = nodeOutputReference(outputId);
        return sanitizeVariableName(changeVariableNameToFormat(reference != null ? reference : outputId));
    }

    /**
     * The (unsanitized) name a variable reference is read under: a registered process/subdiagram output or identifier,
     * else a request/global/calculated/identifier source name; null for anything else.
     */
    public String resolveReference(String varInfoId) {
        String reference = nodeOutputReference(varInfoId);
        return reference != null ? reference : sourceQualifiedName(varInfoId);
    }

    /**
     * The source-qualified name the Decision signature uses for a request ({@code events.X} / {@code inputVariables.X}
     * -> {@code Request.X}), global ({@code globals.X} -> {@code Global.X}) or calculated item
     * ({@code calculatedDataItems.X} -> {@code CalculatedDataItem.X}) variable id; null for any other id. Callers
     * sanitize the result (e.g. {@code RequestX}). Resolving these from the id alone keeps names independent of the
     * order in which nodes are converted.
     */
    public static String sourceQualifiedName(String varInfoId) {
        if (varInfoId == null || varInfoId.indexOf('.') <= 0) {
            return null;
        }
        String folder = varInfoId.substring(0, varInfoId.indexOf('.'));
        String name = varInfoId.substring(varInfoId.indexOf('.') + 1);
        String source = switch (folder) {
            case EVENTS_FOLDER, "inputVariables" -> SOURCE_EVENT;
            case GLOBALS_FOLDER -> SOURCE_GLOBAL;
            case CALCULATED_DATA_ITEMS_FOLDER -> SOURCE_CALCULATED_DATA_ITEM;
            // Written by the process/subdiagram outputs linked to the identifier (see registerNodeOutputs)
            case IDENTIFIERS_FOLDER -> SOURCE_IDENTIFIER;
            default -> null;
        };
        return source == null || name.isEmpty() ? null : source + "." + name;
    }

    public List<String> getSubDiagramNodeNames(final Batch batch) {
        return batch.getLogicalUnit().getSubDiagramNodeDataDOs().stream()
                .map(SubDiagramNodeDataDO::getSubdiagramName)
                .distinct()
                .map(subDiagramName -> !findDecision(subDiagramName) ? subDiagramName : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
