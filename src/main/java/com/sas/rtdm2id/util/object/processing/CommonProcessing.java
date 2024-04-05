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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.*;

@Component
public class CommonProcessing {
    Logger log = LoggerFactory.getLogger(CommonProcessing.class);

    private final RestTemplate restTemplate;
    private final MapStorage mapStorage;
    private final TreeUtil treeUtil;

    private static final String UNDERSCORE = "_";
    private static final String EMPTY_STRING = "";

    public CommonProcessing(RestTemplate restTemplate, MapStorage mapStorage, TreeUtil treeUtil) {
        this.restTemplate = restTemplate;
        this.mapStorage = mapStorage;
        this.treeUtil = treeUtil;
    }

    public Map<String, Step> createRuleSets(Batch batch) {
        // Rulesets are unique so we must append the Node ID to the name. This applies anywhere we need to create a ruleset
        // to represent the input/output variables of a RTDM node including calculated data items since they're created on a
        // per node basis.
        // Each node in a RTDM campaign has its own unique set of input/output variable assignments so we must create a
        // unique ruleset for each node.
        List<CalculatedItemDO> calculatedItemDOs = batch.getLogicalUnit().getFlowDO().getCalculatedDataItemList().getCalculatedItemDOs();
        if (!calculatedItemDOs.isEmpty()) {
            for (CalculatedItemDO calculatedItemDO : calculatedItemDOs) {
                addRulesetForCalculatedItem(calculatedItemDO);
            }
        }
        List<SplitNodeDataDO> splitNodeDataDOS = batch.getLogicalUnit().getSplitNodeDataDOs();
        splitNodeDataDOS.stream().filter(splitNodeDataDO -> splitNodeDataDO.getSplitType().equals("percents")).forEach(splitNodeDataDO -> {
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
                    findOrAddRuleSet(makeProcessNodeRuleSetName(processNodeDataDO) + "_input_values", processNodeDataDO.getNodeDescription());
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

                    ResponseEntity<RuleSet> response = restTemplate.postForEntity(ViyaApi.createUri(mapStorage.getBaseIp(), "/businessRules/ruleSets?parentFolderUri=" + mapStorage.getParentFolderUri(), mapStorage.getProtocol()),
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
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
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

    public boolean findDecision(String decisionName) {
        boolean decisionExists = false;
        try {
            CodeFileCollection codeFileCollection
                    = getCodeFileCollection(mapStorage.getBaseIp(), decisionName, mapStorage.getAccessToken(), "/decisions/flows");
            if (codeFileCollection != null) {
                if (codeFileCollection.getCount() > 0) {
                    decisionExists = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decisionExists;
    }

    public SignatureTerm.DataTypeEnum getDatatypeOfVar(String typeDescription) {
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
                dataTypeEnum = SignatureTerm.DataTypeEnum.DATAGRID;
                break;
            case DATE_CONSTANT:
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

            if (direction.equals(INPUT_DIRECTION)
                        && SignatureTerm.DirectionEnum.OUTPUT.equals(optionalSignatureTerm.get().getDirection())

                        || direction.equals(OUTPUT_DIRECTION) && SignatureTerm.DirectionEnum.INPUT
                                .equals(optionalSignatureTerm.get().getDirection())

                        || direction.equals(NONE_DIRECTION) && SignatureTerm.DirectionEnum.INPUT
                                .equals(optionalSignatureTerm.get().getDirection())) {
                optionalSignatureTerm.get().setDirection(SignatureTerm.DirectionEnum.INOUT);
                signatureItem.setDirection(SignatureTerm.DirectionEnum.INOUT);
            }
        } else {
            signatureItem.setDirection(SignatureTerm.DirectionEnum.fromValue(direction));
        }

        if (!decision.getSignature().contains(signatureItem)) {
            decision.addSignatureItem(signatureItem);
        } else if (signatureItem.getDataType().equals(SignatureTerm.DataTypeEnum.DECIMAL)) {
            int i = decision.getSignature().indexOf(signatureItem);
            SignatureTerm signatureTermThatExist = decision.getSignature().get(i);
            if (!signatureTermThatExist.getDataType().equals(SignatureTerm.DataTypeEnum.DECIMAL)) {
                signatureTermThatExist.setDataType(SignatureTerm.DataTypeEnum.DECIMAL);
            }
        }
    }

    public void checkSignatureTermForDuplicate(String direction, List<Signature> signatureList, Signature signature) {
        handleIncomingSignatureUpdate(direction, signatureList, signature);
        if (!signatureList.contains(signature)) {
            signatureList.add(signature);
        } else {
            if (signature.getDataType().equals(SignatureTerm.DataTypeEnum.DECIMAL.getValue())) {
                int i = signatureList.indexOf(signature);
                Signature signatureThatExist = signatureList.get(i);
                if (!signatureThatExist.getDataType().equals(SignatureTerm.DataTypeEnum.DECIMAL.getValue())) {
                    signatureThatExist.setDataType(SignatureTerm.DataTypeEnum.DECIMAL.toString());
                }
            }
        }
    }

    public void handleIncomingSignatureUpdate(String direction, List<Signature> signatureList, Signature signature) {
        Optional<Signature> optionalSignature = signatureList.stream()
                .filter(optSignature -> optSignature.getName().equalsIgnoreCase(signature.getName())).findFirst();

        if (optionalSignature.isPresent()) {
            // Make sure names match exactly on case
            signature.setName(optionalSignature.get().getName());

            if (direction.equals(INPUT_DIRECTION)
                        && SignatureTerm.DirectionEnum.OUTPUT.equals(optionalSignature.get().getDirection())

                        || direction.equals(OUTPUT_DIRECTION) && SignatureTerm.DirectionEnum.INPUT
                                .equals(optionalSignature.get().getDirection())

                        || direction.equals(NONE_DIRECTION) && SignatureTerm.DirectionEnum.INPUT
                                .equals(optionalSignature.get().getDirection())) {

                // If signature already exists with a direction of input/output and a new
                // direction of input/output has come in then update direction to be inOut
                // Also, if signature already exists with a direction of input and a new
                // direction of none has come in then update direction to be inOut
                optionalSignature.get().setDirection(IN_OUT_DIRECTION);
                signature.setDirection(IN_OUT_DIRECTION);
            }

            if (optionalSignature.get().getDirection().equals(IN_OUT_DIRECTION)) {
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
        if (!varInfoId.isEmpty()) {
            String source = varInfoId.substring(0, varInfoId.indexOf("."));
            if (CALCULATED_DATA_ITEMS_FOLDER.equals(source)) {
                String calculatedVariableName = varInfoId.substring(varInfoId.indexOf(".") + 1);
                String variableName = sanitizeVariableName(SOURCE_CALCULATED_DATA_ITEM + "." + calculatedVariableName);

                Step step = mapStorage.getRuleSetStepMap().get(calculatedVariableName+"_"+mapStorage.getCalculatedVariableMap().get(calculatedVariableName));
                if (step != null) {
                    addMapping(variableName, varName, originStep, true);
                    if (!mapStorage.getCalculatedVariableRulesetNodes().contains(step)){
                        updateRuleSet(variableName, type, step);
                        addStep(stepList, step, nodeId);
                        mapStorage.getCalculatedVariableRulesetNodes().add(step);
                    }
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
        String varInfoId = varRef.getVarInfoId();
        if (!varInfoId.isEmpty()) {
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
        if (Boolean.FALSE.equals(mapStorage.getRuleSetIsUpdated().get(ruleSetForUpdate.getName()))) {
            Mapping mapping = new Mapping();
            mapping.setStepTermName(sanitizeAndReduceVariableName(calculatedVariableName));
            mapping.setTargetDecisionTermName(sanitizeVariableName(calculatedVariableName));
            mapping.setDirection(Mapping.DirectionEnum.OUTPUT);
            checkMappingForDuplicate(mapping, step);

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
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

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
                    signature.setDataType(getDatatypeOfVar(ibVariableDO.getTypeDescription()).getValue());
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
                        globalVariable = activateGlobalVariable(globalVariableName, globalVariable, response);
                        if (globalVariable != null) return globalVariable;
                    }
                } else {
                    CodeFile codeFile = codeFileCollection.getItems().get(0);
                    GlobalVariable globalVariable = new GlobalVariable(codeFile.getId(), sanitizeVariableName(globalVariableName)
                            , codeFile.getDatatype(), codeFile.getDefaultValue());
                    mapStorage.getGlobalVariableMap().put(globalVariableName
                            , globalVariable);
                    return globalVariable;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private GlobalVariable activateGlobalVariable(String globalVariableName, GlobalVariable globalVariable, ResponseEntity<CodeFile> response) throws URISyntaxException {
        HttpHeaders httpHeaders = response.getHeaders();
        String etag = httpHeaders.getFirst(HttpHeaders.ETAG);
        CodeFile codeFile = response.getBody();
        assert codeFile != null;
        globalVariable.setId(codeFile.getId());
        ResponseEntity<GlobalVariableCollection> responseEntityForGlobals =
                restTemplate.exchange(
                        ViyaApi.createUri(mapStorage.getBaseIp(), "/referenceData/globalVariables/" + codeFile.getId() + "/revisions", mapStorage.getProtocol())
                        , HttpMethod.GET,
                        ViyaApi.createGetDecisionCollectionOrGlobalVariableRevision(mapStorage.getAccessToken()), GlobalVariableCollection.class);
        if (responseEntityForGlobals.getStatusCode().value() == 200) {
            GlobalVariableCollection globalVariableCollection = responseEntityForGlobals.getBody();
            if (globalVariableCollection != null) {
                Item item = globalVariableCollection.getItems().get(0);
                ResponseEntity<String> putResp
                        = restTemplate.exchange(ViyaApi.createUri(mapStorage.getBaseIp(), "/referenceData/globalVariables/" + codeFile.getId() + "/active", mapStorage.getProtocol())
                        , HttpMethod.PUT,
                        ViyaApi.activateGlobalVariable(mapStorage.getAccessToken(), etag, item.getId()), String.class);
                if (putResp.getStatusCode().value() == 200) {
                    mapStorage.getGlobalVariableMap().put(globalVariableName
                            , globalVariable);
                    return globalVariable;
                }
            }
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

    public void addNewSignatureItemForDataGridExtension(IBVariableDO ibVariableDO, String direction, Decision decision) {
        SignatureTerm signatureItem = new SignatureTerm();
        signatureItem.setDataType(getDatatypeOfVar(ibVariableDO.getTypeDescription()));
        if (signatureItem.getDataType().equals(SignatureTerm.DataTypeEnum.DATAGRID)) {
            signatureItem.setDataGridExtension(new ArrayList<>());
        }
        signatureItem.setName(sanitizeVariableName(changeVariableNameToFormat(ibVariableDO.getName())));
        checkSignatureTermForDuplicate(direction, decision, signatureItem);
    }

    public void addNewSignatureItem(IBVariableDO ibVariableDO, String direction, Decision decision, String varInfoSource) {
        SignatureTerm signatureItem = new SignatureTerm();
        signatureItem.setDataType(getDatatypeOfVar(ibVariableDO.getTypeDescription()));
        if (signatureItem.getDataType().equals(SignatureTerm.DataTypeEnum.DATAGRID)) {
            signatureItem.setDataGridExtension(new ArrayList<>());
        }
        String variableName = buildVariableName(ibVariableDO, varInfoSource);
        signatureItem.setName(sanitizeVariableName(changeVariableNameToFormat(variableName)));
        checkSignatureTermForDuplicate(direction, decision, signatureItem);
    }

    public void addNewSignatureItem(VarRef varRef, String direction, Decision decision) {
        addNewSignatureItem(varRef, direction, decision, varRef.getType());
    }

    public void addNewSignatureItem(VarRef varRef, String direction, Decision decision, String varType) {
        SignatureTerm signatureItem = new SignatureTerm();

        signatureItem.setDataType(getDatatypeOfVar(varType));
        if (signatureItem.getDataType().equals(SignatureTerm.DataTypeEnum.DATAGRID)) {
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
        if (isReservedWord(name)) {
            // Some reserved words start with an underscore so to avoid confusion prefix with a letter
            name = "r" + UNDERSCORE + name;
        }

        // Check if the string starts with a numeric character
        boolean startsWithNumeric = Character.isDigit(name.charAt(0));

        // If it starts with a numeric character, prefix with an underscore
        if (startsWithNumeric) {
            name = UNDERSCORE + name;
        }

        if (name.matches("(?i)[a-z][a-z0-9_]*")) {
            return name;
        } else {
            // signature item only allows alphanumeric and underscore
            return name.replaceAll("[^a-zA-Z0-9_]", EMPTY_STRING);
        }
    }

    private boolean isReservedWord(String value) {
        return value != null && ReservedWords.isReservedWord(value.toUpperCase() );
    }

    private void setDefaultValueByDataType(GlobalVariable globalVariable, String dataType) {
        if (dataType.equals(INTEGER_CONSTANT) || dataType.equals(DECIMAL_CONSTANT)) {
            globalVariable.setDefaultValue(0);
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
                if (!RTDM2IDConstants.SOURCE_GLOBAL.equals(ibVariableDO.getValue().getValueTypeVarInfoDO().getVarInfoSource())){
                    mapping.setTargetDecisionTermName(sanitizeVariableName(changeVariableNameToFormat(buildVariableName(ibVariableDO.getValue().getValueTypeVarInfoDO()))));
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

    public void addOutputMappingForProcess(IBVariableDO ibVariableDO, String direction, Step step, boolean isReplyVar, Decision decision) {
        Mapping mapping = new Mapping();
        mapping.setDirection(Mapping.DirectionEnum.fromValue(direction));
        mapping.setTargetDecisionTermName(sanitizeVariableName(changeVariableNameToFormat(ibVariableDO.getName())));
        mapping.setStepTermName(sanitizeAndReduceVariableName(ibVariableDO.getName()));
        if (!isReplyVar) {
            mapping.setTargetDecisionTermName(sanitizeVariableName(ibVariableDO.getName()));
        }
        checkMappingForDuplicate(mapping, step);
    }

    public void addMapping(String targetDecisionTermName, String stepTermName, Step step, boolean isRuleSet) {
        Mapping mapping = new Mapping();
        String targetDecisionTermNameChanged = sanitizeVariableName(changeVariableNameToFormat(targetDecisionTermName));
        mapping.setTargetDecisionTermName(targetDecisionTermNameChanged);
        mapping.setDirection(Mapping.DirectionEnum.INPUT);
        mapping.setStepTermName(sanitizeAndReduceVariableName(stepTermName));
        if (isRuleSet) {
            mapping.setStepTermName(targetDecisionTermNameChanged);
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

    public boolean isProcessDataProcess(String processTypeDescription) {
        return READ_DATA_CONSTANT.equals(processTypeDescription)
                || INSERT_DATA_CONSTANT.equals(processTypeDescription)
                || UPDATE_DATA_CONSTANT.equals(processTypeDescription)
                ;
    }

    public ConditionBranch createConditionBranch(String nodeId, boolean needToGetNextNode) {
        String nextNodeIdForCell = getNextNodeIdForCell(nodeId);

        ConditionBranch conditionBranch = new ConditionBranch();
        List<Step> innerStep = mapStorage.getNodeIdStepMap().get(needToGetNextNode ? nextNodeIdForCell : nodeId);
        if (innerStep == null) {
            conditionBranch.setSteps(Collections.emptyList());
        } else {
            mapStorage.getExistingNodeIds().add(getNextNodeIdForCell(nodeId));

            // If more than one branch case shares the same downstream node then use cross-branch links
            String linkLabel = mapStorage.getNodeIdLinkLabelMap().get(nextNodeIdForCell);
            if (mapStorage.isUseCrossBranchLinks() && linkLabel!=null) {
                // The downstream RTDM node contains a link label which means it has multiple input nodes

                if (!mapStorage.getCrossBranchLinksAdded().contains(linkLabel)) {
                    // Whatever input node gets processed first will contain the original set of inner steps to the downstream node
                    conditionBranch.setSteps(innerStep);

                    // All subsequent input nodes will use a cross-branch link step
                    mapStorage.getCrossBranchLinksAdded().add(linkLabel);
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
                // Find out how many input nodes this RTDM node has. This is different than the comments above. What we're checking
                // here is if the RTDM node has multiple input nodes. If it does then we need to utilize cross-branch link steps
                // otherwise the migration tool will create duplicate paths in the Decision (one for each input node).
                List<String> nodes = Arrays.asList(treeUtil.getNodeIdInputNodesMap().get(nodeId).split(REGEXP));
            
                if (nodes.size() > 1) {
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
        return makeNameValid(calculatedItemDO.getName() + "_" + calculatedItemDO.getNodeId());
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
        try {
            String ruleSetName = makeCalculatedDataItemRuleSetName(calculatedItemDO);



            String expression = calculatedItemDO.getExpression();
            findOrAddRuleSet(ruleSetName, calculatedItemDO.getDescription());

            RuleSet ruleSet = new RuleSet();
            ruleSet.setName(ruleSetName);
            mapStorage.getCalculatedVariableMap().put(calculatedItemDO.getName(), calculatedItemDO.getNodeId());
            Rule rule = new Rule();
            Step step = mapStorage.getRuleSetStepMap().get(ruleSetName);
            RuleSetStep ruleSetForUpdate = step.getRuleSet();
            List<Signature> signatureList = new ArrayList<>();
            List<Action> actionList = new ArrayList<>();
            Action action = new Action();
            action.setType("complex");
            String value = "";
            String dataType = getCalculatedItemDataType(calculatedItemDO.getCalculatedItemType());
            Set<String> varsInExpresion = new HashSet<String>(); 
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

            // We do not know the variable data types used in expression so set to "any".
            // These will appear with a Data Type of "Unknown" in ID UI.
            // For example:
            // Calculated Variable Type: Numeric (Nominal)
            // Calculated Variable Expression: TABLEUTILS.GETFLOAT(<<events.Grid Input>>,<<events.CustomerID>>,1)
            //
            // We know the GETFLOAT will return a Double (that's the calculated item data type) but we don't know the
            // data type of events.CustomerID
            varsInExpresion.forEach(item->{
                Signature _signature = new Signature();
                _signature.setName(sanitizeAndReduceVariableName(changeVariableNameToFormat(item)));
                _signature.setDataType("any");
                _signature.setDirection(INPUT_DIRECTION);
                checkSignatureTermForDuplicate(INPUT_DIRECTION, signatureList, _signature);
            });
            ruleSet.setSignature(signatureList);
            ruleSet.setDescription(calculatedItemDO.getDescription());
            
            if (!(mapStorage.getRuleSetIsActionUpdated().get(ruleSetForUpdate.getName()) && mapStorage.getRuleSetIsUpdated().get(ruleSetForUpdate.getName()))) {
                callPutRuleSet(ruleSetForUpdate, ruleSet);
                addActionToRuleSet(ruleSetForUpdate, rule);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private String transferInSIDFormat(String expression,Set<String> varSet) {
        String pattern = "<<.*?\\.(.*?)>>";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(expression);
        StringBuffer outputBuffer = new StringBuffer();
        while (matcher.find()) {
            String matchedVar = matcher.group(1);

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
                    Iterator<Map.Entry<Short, CustomObjectStep>> it = mapStorage.getCustomObjectStepHashMap().entrySet().iterator();
                    while (it.hasNext()) {
                        // Iterate the custom object step map to find the node name used in the calculated variable expression
                        Map.Entry<Short, CustomObjectStep> customObjectStep = it.next();
                        CustomObjectStep customStepValue = customObjectStep.getValue();
                        if (matchedVar.startsWith(customStepValue.getName())) {
                            String stepName = customStepValue.getName();
                            int idx = matchedVar.indexOf(stepName);
                            if (idx > -1) {
                                // Hash step name to reduce length
                                String termName = matchedVar.substring(stepName.length());
                                matchedVar = stepName.hashCode() + "_" + termName;
                                if (matchedVar.length() > 32) {
                                    // It's possible the name is still too long so hash the variable name
                                    matchedVar = stepName.hashCode() + "_" + termName.hashCode();
                                }
                            }
                        }
                    }
                }
            }

            String modifiedVar  = changeVariableNameToFormat(matchedVar); 
            varSet.add(modifiedVar);
            matcher.appendReplacement(outputBuffer, modifiedVar);
        }
        matcher.appendTail(outputBuffer);
        return outputBuffer.toString();
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
            String hashCodeValue = String.valueOf(Math.abs(reducedVariableName.hashCode()));

            // Subtract hash code length from 32 and substring remaining characters from variable name
            String amountOfCharactersToExtract = reducedVariableName.substring(0, 32 - hashCodeValue.length());
            
            // Append hash code onto end of variable name to retain variable name uniqueness,
            // this gives us a variable name with a length of 32 characters
            reducedVariableName = amountOfCharactersToExtract + hashCodeValue;          
        }

        return reducedVariableName;
    }

    public void processAssignmentVariables(Step step, Decision decision, RuleSet ruleSet, RuleSetStep ruleSetForUpdate,List<com.sas.rtdm2id.model.rtdm.extension.IBVariableDO> ibVariableDOs) {
        List<Signature> signatureList = new ArrayList<>();
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
            addActionToRuleSet(ruleSetForUpdate, rule);

        }

        ruleSet.setSignature(signatureList);
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
        String variableName = null;

        switch (valueTypeVarInfoDO.getVarInfoSource()) {
            case SOURCE_NODE:
                // Upstream Nodes folder (return Node Name + "." + Output Variable Name)
                // Example: Read Database.AGE
                String varName = valueTypeVarInfoDO.getVarName() !=null 
                    ? valueTypeVarInfoDO.getVarName() 
                    : valueTypeVarInfoDO.getVarInfoPhysicalName();
                variableName = valueTypeVarInfoDO.getVarInfoSourceName() + "." + varName;
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
        // Use display value if available otherwise fallback to varInfoId
        String variableName = mapStorage.getVarInfoIdToVariableNameMap().get(varRef.getVarInfoId());
        if (variableName==null) variableName = varRef.getVarInfoId();
        return variableName;
    }

    public String buildVariableName(VarRefDO varRefDO) {
        // Use display value if available otherwise fallback to varInfoId
        String variableName = mapStorage.getVarInfoIdToVariableNameMap().get(varRefDO.getVarInfoId());
        if (variableName==null) variableName = varRefDO.getVarInfoId();
        return variableName;
    }

}
