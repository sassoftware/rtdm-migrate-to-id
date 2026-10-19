/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.mapper.IBVariableDOMapperImpl;
import com.sas.rtdm2id.mapper.VarRefMapper;
import com.sas.rtdm2id.model.id.decision.CodeFile;
import com.sas.rtdm2id.model.id.decision.CodeFileCollection;
import com.sas.rtdm2id.model.id.decision.CustomObjectStep;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.Mapping;
import com.sas.rtdm2id.model.id.decision.RuleSetStep;
import com.sas.rtdm2id.model.id.decision.SignatureTerm;
import com.sas.rtdm2id.model.id.decision.SignatureTermExtension;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.files.CodeFileSignature;
import com.sas.rtdm2id.model.id.files.CustomNodeRequest;
import com.sas.rtdm2id.model.id.files.DecisionCodeFileResponse;
import com.sas.rtdm2id.model.id.globals.GlobalVariable;
import com.sas.rtdm2id.model.id.rules.Action;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.id.rules.Signature;
import com.sas.rtdm2id.model.rtdm.*;
import com.sas.rtdm2id.model.rtdm.extension.Value;
import com.sas.rtdm2id.model.rtdm.extension.VarRef;
import com.sas.rtdm2id.util.ViyaApi;
import com.sas.rtdm2id.util.model.IBVariableDO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.*;

@Component
public class ProcessNodeConverter {
    private static final Logger log = LoggerFactory.getLogger(ProcessNodeConverter.class);
    /** Outputs mapped on every data process (SQL code file) step. */
    private static final List<String> STANDARD_DATA_PROCESS_OUTPUTS = List.of("returnCode", "rowCount");
    private final RestTemplate restTemplate;
    private final CommonProcessing commonProcessing;
    private final MapStorage mapStorage;
    private final SegmentationTreeService segmentationTreeService;

    // DS2 package declaration: "package <name>" at the start of a line (declarations such as "dcl package hash h"
    // do not start with the keyword). Group 2 is the package name, optionally quoted.
    private static final Pattern PACKAGE_DECLARATION_PATTERN = Pattern.compile(
            "^(\\s*package\\s+)(\"[^\"]*\"|[^\\s/;(]+)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
    private static final Pattern OVERWRITE_OPTION_PATTERN = Pattern.compile(
            "/\\s*overwrite\\s*=\\s*yes\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern END_PACKAGE_PATTERN = Pattern.compile("\\bendpackage\\s*;", Pattern.CASE_INSENSITIVE);
    private static final Pattern EXECUTE_METHOD_PATTERN = Pattern.compile("\\bmethod\\s+execute\\s*\\(", Pattern.CASE_INSENSITIVE);
    private static final Pattern INT_TYPE_PATTERN = Pattern.compile("(?<![\\w.])int(?=\\s)", Pattern.CASE_INSENSITIVE);
    private static final Pattern TAP_DATETIME_PACKAGE_PATTERN = Pattern.compile("\\bpackage\\s+tap_datetime\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern TAP_HASH_PACKAGE_PATTERN = Pattern.compile("\\bpackage\\s+tap_hash\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern TAP_LOGGER_PACKAGE_PATTERN = Pattern.compile("\\bpackage\\s+tap_logger\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern TAP_SQLTABLE_PACKAGE_PATTERN = Pattern.compile("\\bpackage\\s+tap_sqltable\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern TAP_ARRAY_PATTERN = Pattern.compile("\\b(" + TAP_FLOAT_ARRAY_CONSTANT + "|" + TAP_STRING_ARRAY_CONSTANT
            + "|" + TAP_INT_ARRAY_CONSTANT + "|" + TAP_DATETIME_ARRAY_CONSTANT + "|" + TAP_BOOLEAN_ARRAY_CONSTANT + ")\\b",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern DATAGRID_WORD_PATTERN = Pattern.compile("\\b" + DS2_DATAGRID + "\\b", Pattern.CASE_INSENSITIVE);

    private static final String JAVA_COMMENT_TYPE_1_REGEX = "\\/\\*([\\S\\s]+?)\\*\\/";
    private static final Pattern javaComment1Pattern = Pattern.compile(JAVA_COMMENT_TYPE_1_REGEX,Pattern.CASE_INSENSITIVE);

    private static final String JAVA_COMMENT_TYPE_2_REGEX = "(?s)/\\*.*?\\*/";
    private static final Pattern javaComment2Pattern = Pattern.compile(JAVA_COMMENT_TYPE_2_REGEX,Pattern.CASE_INSENSITIVE);

    private static final String EMPTY_STRING = "";

    // RTDM IBVariableValueDO variable type constants
    public static final int VAR_VALUE_TYPE_DOUBLE = 1;
    public static final int VAR_VALUE_TYPE_STRING = 2;
    public static final int VAR_VALUE_TYPE_DATE = 3;
    public static final int VAR_VALUE_TYPE_BOOLEAN = 4;
    public static final int VAR_VALUE_TYPE_INTEGER = 5;
    public static final int VAR_VALUE_TYPE_DOUBLE_LIST = 6;
    public static final int VAR_VALUE_TYPE_STRING_LIST = 7;
    public static final int VAR_VALUE_TYPE_DATE_LIST = 8;
    public static final int VAR_VALUE_TYPE_INTEGER_LIST = 9;
    public static final int VAR_VALUE_TYPE_BOOLEAN_LIST = 10;
    public static final int VAR_VALUE_TYPE_VARINFO = 11;
    public static final int VAR_VALUE_TYPE_EXTERNAL_TABLE_REF = 12;
    public static final int VAR_VALUE_TYPE_DATA_GRID = 13;
    public static final int VAR_VALUE_TYPE_DEFAULT = 14;

    public static final String FALSE_STRING = "false";

    @Autowired
    public ProcessNodeConverter(RestTemplate restTemplate, CommonProcessing commonProcessing, MapStorage mapStorage,
            SegmentationTreeService segmentationTreeService) {
        this.restTemplate = restTemplate;
        this.commonProcessing = commonProcessing;
        this.mapStorage = mapStorage;
        this.segmentationTreeService = segmentationTreeService;
    }

    public ProcessNodeConverter(RestTemplate restTemplate, CommonProcessing commonProcessing, MapStorage mapStorage) {
        this(restTemplate, commonProcessing, mapStorage,
                new SegmentationTreeService(restTemplate, mapStorage, new SegmentationTreeConverter(commonProcessing)));
    }

    public List<Step> addCustomObjectStep(ProcessNodeDataDO processNodeDataDO, Decision decision, Short objId, String nodeName) {
        
        ProcessNodeDataDO.Process process = processNodeDataDO.getProcess();
        
        List<Step> stepList = new LinkedList<>();
        Step step = new Step();
        Step extractDataStep = null;
        step.setType(Step.TypeEnum.CUSTOM_OBJECT);
        Step inputValuesStep = mapStorage.getRuleSetStepMap().get(nodeName + "_input_values");
        boolean hasInputs = process != null && process.getInputVariableList() != null
                && !process.getInputVariableList().getIbVariableDOs().isEmpty();
        boolean hasInputValues = hasInputs && CommonProcessing.hasInputValuesRuleSet(process);
        if (process != null) {
            if (hasInputs) {
                processCustomObjectInputMapping(processNodeDataDO, decision, stepList, step);
            }
            // In/out parameters are loaded into their output term by the _input_values RuleSet, so it must exist
            Map<String, String> inOutTerms = hasInputValues && inputValuesStep != null
                ? inOutOutputTerms(processNodeDataDO.getNodeName(), process) : Map.of();
            if (hasInputValues) {
                updateRuleSet(process.getInputVariableList(), inputValuesStep, decision, inOutTerms);
            }
            if (process.getOutputVariableList() != null) {
                if (commonProcessing.isProcessNotDataProcess(process.getProcessTypeDescription()) || !isDataProcessReturnDataGrid(process)) {
                    processCustomObjectMapping(processNodeDataDO.getNodeName(), process, step, decision, inOutTerms);
                } else {
                    SignatureTerm signatureTermDatagrid
                            = processDataProcessMapping(processNodeDataDO.getNodeName(), process, step, decision);

                    extractDataStep = mapStorage.getRuleSetStepMap().get(nodeName + "_extract_values");

                    updateRuleSet(signatureTermDatagrid, extractDataStep, decision, processNodeDataDO.getNodeName(), process.getName());
                }
            }
        }
        if (hasInputValues && inputValuesStep != null) {
            // Unsupported nodes are converted into DS2 code files but we don't create a ruleset node to
            // represent the custom node inputs so inputValuesStep is null in this scenario
            commonProcessing.addStep(stepList, inputValuesStep, processNodeDataDO.getNodeId());
        }
        step.setCustomObject(mapStorage.getCustomObjectStepHashMap().get(objId));
        alignWithCodeFileSignature(step, mapStorage.getCodeFileSignatures().get(objId), decision, nodeName);
        commonProcessing.addStep(stepList, step, processNodeDataDO.getNodeId());
        if (extractDataStep != null) {
            commonProcessing.addStep(stepList, extractDataStep, processNodeDataDO.getNodeId());
        }
        return stepList;
    }

    /**
     * Writes the extraction RuleSet that copies the first row of a data process's data grid into one Decision term per
     * column. Each column is written under the name later nodes read it by: RTDM's output id
     * {@code <NodeName>.<ProcessName><column>}, or the identifier the column is linked to (see
     * CommonProcessing.registerNodeOutputs), which also keeps same-named columns of different nodes apart.
     */
    public void updateRuleSet(SignatureTerm signatureTermDataGrid, Step step, Decision decision, String nodeName, String processName) {
        RuleSet ruleSet = new RuleSet();
        RuleSetStep ruleSetForUpdate = step.getRuleSet();

        // The RuleSet's own (at most 32 character) name for the grid; actions must use this name, not the Decision's
        String gridTerm = commonProcessing.sanitizeAndReduceVariableName(signatureTermDataGrid.getName());
        List<Signature> signatureList = new ArrayList<>();
        Mapping mapping = new Mapping();
        mapping.setStepTermName(gridTerm);
        mapping.setTargetDecisionTermName(commonProcessing.sanitizeVariableName(signatureTermDataGrid.getName()));
        mapping.setDirection(Mapping.DirectionEnum.INPUT);
        commonProcessing.checkMappingForDuplicate(mapping, step);

        ruleSet.setName(ruleSetForUpdate.getName());
        Signature signature = new Signature();
        signature.setDataType(SignatureTerm.DataTypeEnum.DATAGRID.getValue());
        signature.setName(gridTerm);
        signature.setDirection(INPUT_DIRECTION);
        signatureList.add(signature);

        List<Rule> rules = new ArrayList<>();
        for (SignatureTermExtension column : signatureTermDataGrid.getDataGridExtension()) {
            String columnTerm = commonProcessing.sanitizeAndReduceVariableName(column.getName());
            commonProcessing.addProducedOutput(CommonProcessing.nodeOutputId(nodeName, processName, column.getName()), column.getName(),
                    SignatureTerm.DataTypeEnum.fromValue(column.getDataType().getValue()), null, step, decision);

            Signature innerSignature = new Signature();
            innerSignature.setDataType(column.getDataType().getValue());
            innerSignature.setName(columnTerm);
            innerSignature.setDirection(OUTPUT_DIRECTION);
            commonProcessing.checkSignatureTermForDuplicate(OUTPUT_DIRECTION, signatureList, innerSignature);

            Rule rule = new Rule();
            rule.setName(UUID.randomUUID().toString());
            rule.setConditional("if");
            rule.setRuleFiredTrackingEnabled(false);
            rule.setActions(Collections.singletonList(addAction(columnTerm, column.getName(), gridTerm, column.getDataType().getValue())));
            rules.add(rule);
        }
        ruleSet.setSignature(signatureList);

        // The signature must be in place before the rules that reference it are posted; a reused RuleSet keeps both
        if (Boolean.FALSE.equals(mapStorage.getRuleSetIsUpdated().get(ruleSetForUpdate.getName()))) {
            commonProcessing.callPutRuleSet(ruleSetForUpdate, ruleSet);
        }
        if (!Boolean.TRUE.equals(mapStorage.getRuleSetIsActionUpdated().get(ruleSetForUpdate.getName()))) {
            rules.forEach(rule -> commonProcessing.addActionToRuleSet(ruleSetForUpdate, rule));
        }
    }

    /**
     * Scalar outputs of a (non-data) process with the same name as one of its inputs, by name, with the Decision term
     * the output is written under. The code file has a single in_out parameter for both (e.g. the rules group
     * parameters VD11_ThmCheckDecisionCd, "in_out varchar" in its DS2), so the step maps it once, both ways, on the
     * output term, and the _input_values RuleSet first copies the input value into that term.
     */
    private Map<String, String> inOutOutputTerms(String nodeName, ProcessNodeDataDO.Process process) {
        Map<String, String> terms = new LinkedHashMap<>();
        if (!commonProcessing.isProcessNotDataProcess(process.getProcessTypeDescription())
                || process.getInputVariableList() == null || process.getOutputVariableList() == null) {
            return terms;
        }
        Set<String> inputs = new HashSet<>();
        process.getInputVariableList().getIbVariableDOs().stream()
            .filter(input -> input.getName() != null && CommonProcessing.isAssignedInInputValues(input)
                && !isGrid(input.getTypeDescription()))
            .forEach(input -> inputs.add(input.getName()));
        for (ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO output : process.getOutputVariableList().getIbVariableDOs()) {
            if (output.getName() != null && inputs.contains(output.getName()) && !isGrid(output.getTypeDescription())) {
                terms.put(output.getName(), commonProcessing.nodeOutputTermName(CommonProcessing.nodeOutputId(nodeName,
                    CommonProcessing.outputReferencePrefix(process), output.getName())));
            }
        }
        return terms;
    }

    private boolean isGrid(String typeDescription) {
        return typeDescription == null || typeDescription.contains("list")
            || SignatureTerm.DataTypeEnum.DATAGRID.equals(commonProcessing.getDatatypeOfVar(typeDescription));
    }

    public void updateRuleSet(ProcessNodeDataDO.Process.InputVariableList inputVariableList, Step step, Decision decision) {
        updateRuleSet(inputVariableList, step, decision, Map.of());
    }

    /**
     * @param inOutTerms in/out parameters (see {@link #inOutOutputTerms}): after its input is assigned, each is copied
     *                   into {@code <name>_out}, which is written to the parameter's output term
     */
    private void updateRuleSet(ProcessNodeDataDO.Process.InputVariableList inputVariableList, Step step, Decision decision,
            Map<String, String> inOutTerms) {
        RuleSet ruleSet = new RuleSet();
        Rule rule = new Rule();

        RuleSetStep ruleSetForUpdate = step.getRuleSet();
        List<ProcessNodeDataDO.Process.InputVariableList.IBVariableDO> ibVariableDOs = inputVariableList.getIbVariableDOs();
        List<Signature> signatureList = new ArrayList<>();
        List<Action> actionList = new ArrayList<>();

        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();

        if (ibVariableDOs != null && !ibVariableDOs.isEmpty()) {
            boolean ruleUpdated = false;

            for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO ibVariableDO : ibVariableDOs) {
                if (!CommonProcessing.isAssignedInInputValues(ibVariableDO)) {
                    continue;
                }
                Signature signature = new Signature();
                signature.setDataType(commonProcessing.getDatatypeOfVar(ibVariableDO.getTypeDescription()).getValue());

                signature.setName(commonProcessing.sanitizeAndReduceVariableName(ibVariableDO.getName()));
                signature.setDirection(IN_OUT_DIRECTION);
                commonProcessing.checkSignatureTermForDuplicate(IN_OUT_DIRECTION, signatureList, signature);

                commonProcessing.addAction(mapper.ibVariableDoGet(ibVariableDO), actionList, signatureList, step, decision);
                String inOutTerm = inOutTerms.get(ibVariableDO.getName());
                if (inOutTerm != null) {
                    Signature result = new Signature();
                    result.setName(commonProcessing.sanitizeAndReduceVariableName(ibVariableDO.getName() + "_out"));
                    result.setDataType(signature.getDataType());
                    result.setDirection(OUTPUT_DIRECTION);
                    commonProcessing.checkSignatureTermForDuplicate(OUTPUT_DIRECTION, signatureList, result);
                    Action copy = new Action();
                    copy.setType("complex");
                    copy.setExpression(result.getName() + " = " + signature.getName());
                    actionList.add(copy);
                    Mapping resultMapping = new Mapping();
                    resultMapping.setStepTermName(result.getName());
                    resultMapping.setTargetDecisionTermName(inOutTerm);
                    resultMapping.setDirection(Mapping.DirectionEnum.OUTPUT);
                    commonProcessing.checkMappingForDuplicate(resultMapping, step);
                }
                ValueTypeVarInfoDO reference = ibVariableDO.getValue() == null ? null
                    : ibVariableDO.getValue().getValueTypeVarInfoDO();
                boolean scalarAssignment = !ibVariableDO.getTypeDescription().contains("list")
                    && !SignatureTerm.DataTypeEnum.DATAGRID.equals(
                        commonProcessing.getDatatypeOfVar(ibVariableDO.getTypeDescription()));
                boolean calculatedReference = reference != null && reference.getVarInfoId() != null
                    && reference.getVarInfoId().startsWith(CALCULATED_DATA_ITEMS_FOLDER + ".");
                if (scalarAssignment || calculatedReference) {
                    Mapping assignedValueMapping = new Mapping();
                    assignedValueMapping.setStepTermName(signature.getName());
                    String assignedValueName = reference != null && !calculatedReference
                        ? ruleSetForUpdate.getName() + "_" + ibVariableDO.getName() : ibVariableDO.getName();
                    assignedValueMapping.setTargetDecisionTermName(commonProcessing.sanitizeVariableName(assignedValueName));
                    assignedValueMapping.setDirection(Mapping.DirectionEnum.INOUT);
                    commonProcessing.checkMappingForDuplicate(assignedValueMapping, step);
                    SignatureTerm assignedValue = new SignatureTerm();
                    assignedValue.setName(assignedValueMapping.getTargetDecisionTermName());
                    assignedValue.setDataType(commonProcessing.getDatatypeOfVar(ibVariableDO.getTypeDescription()));
                    commonProcessing.checkSignatureTermForDuplicate(NONE_DIRECTION, decision, assignedValue);
                }
                GlobalVariable globalVariable = commonProcessing.checkForGlobalVariableRuleSet(ibVariableDO);
                if (globalVariable != null) {
                    commonProcessing.addMapping(mapper.ibVariableDoGet(ibVariableDO), INPUT_DIRECTION, step, false, decision);
                    commonProcessing.addNewSignatureItemGlobalVariable(globalVariable.getName(), "none", decision);
                }

                if (!(mapStorage.getRuleSetIsActionUpdated().get(ruleSetForUpdate.getName()) &&
                        mapStorage.getRuleSetIsUpdated().get(ruleSetForUpdate.getName()))) {
                    ruleSet.setName(ruleSetForUpdate.getName());
                    rule.setName(UUID.randomUUID().toString());
                    rule.setConditional("if");
                    rule.setRuleFiredTrackingEnabled(false);
                    rule.setActions(actionList);
                    ruleUpdated = true;
                }

            }
            ruleSet.setSignature(signatureList);

            if (ruleUpdated) {
                commonProcessing.callPutRuleSet(ruleSetForUpdate, ruleSet);
                commonProcessing.addActionToRuleSet(ruleSetForUpdate, rule);
            }
        }
    }

    private Action addAction(String variableName, String columnName, String dataGridName, String type) {
        Action action = new Action();
        action.setType("complex");
        action.setExpression(variableName + " = " + getAppropriateDataGridGet(type) + "(" + dataGridName + ",'" + columnName + "', 0 )");
        return action;
    }

    private String getAppropriateDataGridGet(String type) {
        // Data grid extension columns are only ever decimal, integer, datetime or string (see getDataTypeForExtension).
        // Integer and datetime values are numeric in DS2, so they are read with the numeric getter rather than as text.
        // TODO: the row index passed to the getter (0) has not been verified against the ID data grid functions,
        // which may be 1-based.
        if (DECIMAL_CONSTANT.equals(type) || INTEGER_CONSTANT.equals(type) || DATETIME_CONSTANT.equals(type)) {
            return "DATAGRID_GETDOUBLE";
        }
        return "DATAGRID_GETSTRING";
    }

    public Map<Short, CustomObjectStep> createCustomNodes(Batch batch) {
        String baseIp = mapStorage.getBaseIp();
        String accessToken = mapStorage.getAccessToken();
        try {
            for (ProcessNodeDataDO process : batch.getLogicalUnit().getProcessNodeDataDOs()) {
                CustomObjectStep codeStep = new CustomObjectStep();
                CodeFileCollection codeFileCollection = null;

                // Search for SQL and custom nodes by name rather than description 
                if (PROCESS_TYPE_READ_DATA == process.getProcess().getProcessType() || 
                        PROCESS_TYPE_INSERT_DATA == process.getProcess().getProcessType()  || 
                        PROCESS_TYPE_UPDATE_DATA == process.getProcess().getProcessType() ||
                        PROCESS_TYPE_CUSTOM == process.getProcess().getProcessType()) {
                    codeFileCollection
                        = commonProcessing.getCodeFileCollection(mapStorage.getBaseIp(), process.getProcess().getName()!=null ? process.getProcess().getName() : process.getProcess().getPhysicalName(), accessToken, "/decisions/codeFiles");

                } else {
                    codeFileCollection
                        = commonProcessing.getCodeFileCollectionByDescription(baseIp, process.getProcess().getId(), accessToken, "/decisions/codeFiles");
                }
                
                if (codeFileCollection != null) {
                    if (codeFileCollection.getCount() == 0) {
                        createNewCodeFile(baseIp, accessToken, process, codeStep);
                    } else {
                        CodeFile codeFile = codeFileCollection.getItems().get(0);
                        codeStep.setUri("/decisions/codeFiles/" + codeFile.getId());
                        codeStep.setName(codeFile.getName());
                        codeStep.setType(codeFile.getType().getValue());
                        mapStorage.getCustomObjectStepHashMap().put(process.getObjid(), codeStep);
                        // A reused code file may have been created from another campaign's version of the process
                        recordCodeFileSignature(process.getObjid(), codeFile.getId());
                    }
                }
            }

            // Handle unsupported nodes
            // Unsupported nodes will be converted into Code Files
            for (StagedTreatmentsNodeDataDO stagedTreatmentsNodeDataDO : batch.getLogicalUnit().getAddStagedTreatmentsNodeDataDOs()) {
                CustomObjectStep codeStep = new CustomObjectStep();
                CodeFileCollection codeFileCollection
                        = commonProcessing.getCodeFileCollectionByDescription(baseIp, stagedTreatmentsNodeDataDO.getNodeId(), accessToken, "/decisions/codeFiles");
                if (codeFileCollection != null) {
                    if (codeFileCollection.getCount() == 0) {
                        ProcessNodeDataDO process = createProcessNodeDataDO(commonProcessing.makeStagedTreatmentName(stagedTreatmentsNodeDataDO), stagedTreatmentsNodeDataDO.getObjid(), STAGED_TREATMENTS, stagedTreatmentsNodeDataDO.getSubjectVariables(),
                                stagedTreatmentsNodeDataDO.getSubjectVariables() == null ? null : stagedTreatmentsNodeDataDO.getSubjectVariables().getIbVariableDO(), stagedTreatmentsNodeDataDO.getDateModified(), stagedTreatmentsNodeDataDO.getLabel(), stagedTreatmentsNodeDataDO.getNodeId(),
                                stagedTreatmentsNodeDataDO.getVersionNumber());
                        createNewCodeFile(baseIp, accessToken, process, codeStep);
                    } else {
                        CodeFile codeFile = codeFileCollection.getItems().get(0);
                        codeStep.setUri("/decisions/codeFiles/" + codeFile.getId());
                        codeStep.setName(codeFile.getName());
                        codeStep.setType(codeFile.getType().getValue());
                        mapStorage.getCustomObjectStepHashMap().put(stagedTreatmentsNodeDataDO.getObjid(), codeStep);
                    }
                }
            }

            for (RemoveStagedTreatmentsNodeDataDO removeStagedTreatmentsNodeDataDO : batch.getLogicalUnit().getRemoveStagedTreatmentsNodeDataDOs()) {
                CustomObjectStep codeStep = new CustomObjectStep();
                CodeFileCollection codeFileCollection
                        = commonProcessing.getCodeFileCollectionByDescription(baseIp, removeStagedTreatmentsNodeDataDO.getNodeId(), accessToken, "/decisions/codeFiles");
                if (codeFileCollection != null) {
                    if (codeFileCollection.getCount() == 0) {
                        ProcessNodeDataDO process = createProcessNodeDataDO(commonProcessing.makeRemoveStagedTreatmentName(removeStagedTreatmentsNodeDataDO), removeStagedTreatmentsNodeDataDO.getObjid(), REMOVE_STAGED_TREATMENTS, removeStagedTreatmentsNodeDataDO.getSubjectVariables(),
                                removeStagedTreatmentsNodeDataDO.getSubjectVariables() == null ? null : removeStagedTreatmentsNodeDataDO.getSubjectVariables().getIbVariableDO(), removeStagedTreatmentsNodeDataDO.getDateModified(), removeStagedTreatmentsNodeDataDO.getLabel(), removeStagedTreatmentsNodeDataDO.getNodeId(),
                                removeStagedTreatmentsNodeDataDO.getVersionNumber());
                        createNewCodeFile(baseIp, accessToken, process, codeStep);
                    } else {
                        CodeFile codeFile = codeFileCollection.getItems().get(0);
                        codeStep.setUri("/decisions/codeFiles/" + codeFile.getId());
                        codeStep.setName(codeFile.getName());
                        codeStep.setType(codeFile.getType().getValue());
                        mapStorage.getCustomObjectStepHashMap().put(removeStagedTreatmentsNodeDataDO.getObjid(), codeStep);
                    }
                }
            }

            segmentationTreeService.createOrReuse(batch.getLogicalUnit().getCrossTableNodeDataDOs());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return mapStorage.getCustomObjectStepHashMap();
    }

    private ProcessNodeDataDO createProcessNodeDataDO(String nodeName, Short objid, String ds2NodeName, Object variablesObject, List<com.sas.rtdm2id.model.rtdm.extension.IBVariableDO> variables,
                                                      String dateModified, String label, String nodeId, float versionNumber) {
        ProcessNodeDataDO processNodeDataDO = new ProcessNodeDataDO();
        ProcessNodeDataDO.Process process = new  ProcessNodeDataDO.Process();
        process.setProcessTypeDescription(CUSTOM_CONSTANT);
        process.setName(nodeName);
        process.setId(UUID.randomUUID().toString());
        process.setObjid(objid);

        StringBuilder ds2Code = new StringBuilder();

        // Create DS2 package for custom node
        ds2Code.append("package ").append(ds2NodeName).append(" /overwrite=yes ;").append(NEW_LINE_STRING);
        ds2Code.append("/*          */").append(NEW_LINE_STRING);
        ds2Code.append("   method execute();").append(NEW_LINE_STRING);

        // Insert comment inside execute method containing node properties
        ds2Code.append("/*").append(NEW_LINE_STRING);
        ds2Code.append(ds2NodeName).append(" Node migrated from RTDM").append(NEW_LINE_STRING);

        if (variablesObject != null && variables != null) {
            ds2Code.append(">>> ").append(ds2NodeName).append(" Variables").append(NEW_LINE_STRING);
            for (com.sas.rtdm2id.model.rtdm.extension.IBVariableDO ibVariableDO : variables) {
                ds2Code.append("    Description:").append(ibVariableDO.getDescription()).append(NEW_LINE_STRING);
                ds2Code.append("    Level:").append(ibVariableDO.getLevel()).append(NEW_LINE_STRING);
                ds2Code.append("    Name:").append(ibVariableDO.getName()).append(NEW_LINE_STRING);
                ds2Code.append("    Type Description:").append(ibVariableDO.getTypeDescription()).append(NEW_LINE_STRING);

                ds2Code.append("    >>> Value").append(NEW_LINE_STRING);
                Value value = ibVariableDO.getValue();
                if (value!=null) {
                    if (value.getValueTypeVarInfoDO()!=null) {
                        ds2Code.append("        VarInfoId: ").append(value.getValueTypeVarInfoDO().getVarInfoId()).append(NEW_LINE_STRING);
                        ds2Code.append("        VarInfoName: ").append(value.getValueTypeVarInfoDO().getVarName()).append(NEW_LINE_STRING);
                        ds2Code.append("        VarInfoSource: ").append(value.getValueTypeVarInfoDO().getVarInfoSource()).append(NEW_LINE_STRING);
                        if (value.getValueTypeVarInfoDO().getVarInfoSourceName()!=null) {
                            ds2Code.append("        VarInfoSourceName: ").append(value.getValueTypeVarInfoDO().getVarInfoSourceName()).append(NEW_LINE_STRING);
                        }
                        ds2Code.append("        VarInfoType: ").append(value.getValueTypeVarInfoDO().getType()).append(NEW_LINE_STRING);
                    } else {
                        ds2Code.append("        Value: ").append(getValue(value)).append(NEW_LINE_STRING);
                    }
                }

                ds2Code.append("    <<< Value").append(NEW_LINE_STRING);
            }
            ds2Code.append("<<< ").append(ds2NodeName).append(" Variables").append(NEW_LINE_STRING);
        }

        ds2Code.append("Date Modified: ").append(dateModified).append(NEW_LINE_STRING);
        ds2Code.append("Label: ").append(label).append(NEW_LINE_STRING);
        ds2Code.append("Node ID: ").append(nodeId).append(NEW_LINE_STRING);
        ds2Code.append("Node Name: ").append(nodeName).append(NEW_LINE_STRING);
        ds2Code.append("RTDM Version Number: ").append(versionNumber).append(NEW_LINE_STRING);

        ds2Code.append("*/");

        // Complete remainder of execute method
        ds2Code.append("   end;").append(NEW_LINE_STRING);
        ds2Code.append("endpackage;").append(NEW_LINE_STRING);
        ds2Code.append("run;").append(NEW_LINE_STRING);

        process.setDs2code(ds2Code.toString());

        processNodeDataDO.setProcess(process);
        return processNodeDataDO;
    }

    private static String getValue(Value value) {
        if (value!=null) {
            switch (value.getType()) {
                case VAR_VALUE_TYPE_BOOLEAN:
                    return value.getBooleanValue();
                case VAR_VALUE_TYPE_DATE:
                    return value.getDateValue();
                case VAR_VALUE_TYPE_DOUBLE:
                    return value.getDoubleValue();
                case VAR_VALUE_TYPE_INTEGER:
                    return value.getLongValue();
                case VAR_VALUE_TYPE_STRING:
                    return value.getStringValue();
                case VAR_VALUE_TYPE_BOOLEAN_LIST:
                case VAR_VALUE_TYPE_DATE_LIST:
                case VAR_VALUE_TYPE_DOUBLE_LIST:
                case VAR_VALUE_TYPE_INTEGER_LIST:
                case VAR_VALUE_TYPE_STRING_LIST:
                    return value.getListValue();
                default:
                    return "";
            }
        } else {
            return "";
        }
    }

    private void createNewCodeFile(String baseIp, String accessToken, ProcessNodeDataDO process, CustomObjectStep codeStep) throws URISyntaxException {
        CustomNodeRequest customNodeRequest = new CustomNodeRequest();
        boolean isDataGridOut = false;
        if (CUSTOM_CONSTANT.equals(process.getProcess().getProcessTypeDescription())
                || MODEL_CONSTANT.equals(process.getProcess().getProcessTypeDescription())) {
            customNodeRequest.setBody(modifyDS2CodeToIDFormat(process));
            customNodeRequest.setType(DECISION_DS2_CODE_FILE_CONSTANT);
        } else if (GROOVY_CONSTANT.equals(process.getProcess().getProcessTypeDescription())
                || WEB_SERVICE_CONSTANT.equals(process.getProcess().getProcessTypeDescription())
                || BUSINESS_RULES_CONSTANT.equals(process.getProcess().getProcessTypeDescription())) {
            customNodeRequest.setBody(processJavaCodeOrWebServiceOrBusinessRulesForId(process));
            customNodeRequest.setType(DECISION_DS2_CODE_FILE_CONSTANT);
        } else {
            customNodeRequest.setBody(modifyDataProcessToSqlNode(process.getProcess()));
            customNodeRequest.setType("decisionSQLCodeFile");
            isDataGridOut = isDataProcessReturnDataGrid(process.getProcess());
        }

        ResponseEntity<String> response = restTemplate.postForEntity(ViyaApi.createUri(baseIp, "/files/files", mapStorage.getProtocol()),
                ViyaApi.createFilesFilesHTTPEntity(customNodeRequest.getBody(), accessToken), String.class);
        if (response.getStatusCode().value() == 201) {
            HttpHeaders httpHeaders = response.getHeaders();
            String etag = httpHeaders.getFirst(HttpHeaders.ETAG);
            String location = httpHeaders.getFirst(HttpHeaders.LOCATION);

            ResponseEntity<String> putResp = restTemplate.exchange(ViyaApi.createUri(baseIp, location, mapStorage.getProtocol()), HttpMethod.PUT,
                    ViyaApi.createPutFilesFiles(etag, accessToken, process.getProcess()), String.class);
            if (putResp.getStatusCode().value() == 200) {
                ResponseEntity<DecisionCodeFileResponse> responseCodeFile = restTemplate.postForEntity(ViyaApi.createUri(baseIp, "/decisions/codeFiles?parentFolderUri=" + ViyaApi.encodeQueryParam(mapStorage.getParentFolderUri()), mapStorage.getProtocol()),
                        ViyaApi.createDecisionCodeFiles(location, customNodeRequest.getType(), accessToken), DecisionCodeFileResponse.class);
                if (responseCodeFile.getStatusCode().value() == 201
                        && responseCodeFile.getBody() != null) {
                    DecisionCodeFileResponse decisionCodeFileResponse = responseCodeFile.getBody();
                    if (decisionCodeFileResponse != null) {
                        String fileName = decisionCodeFileResponse.getName();
                        Short objId = process.getObjid();
                        if (fileName==null) {
                            // Unsupported nodes are created as DS2 code files
                            fileName = process.getProcess().getName();
                        }
                        patchFile(baseIp, accessToken, fileName, isDataGridOut, location);
                        
                        if (objId == null){
                            objId = process.getProcess().getObjid();
                        }
                        codeStep.setUri("/decisions/codeFiles/" + decisionCodeFileResponse.getId());
                        codeStep.setName(decisionCodeFileResponse.getName());
                        codeStep.setType(customNodeRequest.getType());
                        mapStorage.getCustomObjectStepHashMap().put(objId, codeStep);
                        recordCodeFileSignature(objId, decisionCodeFileResponse.getId());
                    }
                }
            }
        }
    }

    /**
     * Reads the signature ID derived for a code file, so the step can be aligned with it (see
     * {@link #alignWithCodeFileSignature}). Without it the step keeps the mappings built from the extract.
     */
    private void recordCodeFileSignature(Short objId, String codeFileId) {
        if (objId == null || codeFileId == null) {
            return;
        }
        try {
            ResponseEntity<CodeFileSignature> response = restTemplate.exchange(
                ViyaApi.createUri(mapStorage.getBaseIp(), "/decisions/codeFiles/" + codeFileId, mapStorage.getProtocol()),
                HttpMethod.GET, ViyaApi.createGetDecisionCollectionOrGlobalVariableRevision(mapStorage.getAccessToken()),
                CodeFileSignature.class);
            CodeFileSignature body = response == null ? null : response.getBody();
            if (body != null && body.getSignature() != null && !body.getSignature().isEmpty()) {
                mapStorage.getCodeFileSignatures().put(objId, body.getSignature());
            }
        } catch (RestClientException | URISyntaxException e) {
            log.warn("Could not read the signature of code file {}; its step mappings are not checked against it: {}",
                codeFileId, e.getMessage());
        }
    }

    /**
     * The name ID gives an SQL code file's output data grid: the code file's file name (the process name, see
     * ViyaApi.createPutFilesFiles) with '.' and whitespace replaced by '_', plus {@code _dgo}
     * (viya-decisions-framework CodeFileHelper.getAdjustedTermName). E.g. "READ - X" -> {@code READ_-_X_dgo}.
     */
    static String sqlOutputGridTerm(String processName) {
        String fileName = processName.endsWith(".sql") ? processName.substring(0, processName.length() - ".sql".length())
            : processName;
        return fileName.replaceAll("[.\\s]", "_") + "_dgo";
    }

    /**
     * Maps the code file inputs a step does not set. A code file is found by name, so it may have been created from
     * another campaign's version of the same RTDM process that has more input columns; ID rejects a step that leaves a
     * declared input unmapped ("Mapping ... is invalid"). Such an input is passed an unset value, as RTDM leaves an
     * unmapped process input unset. Existing mappings are never changed or removed (returnCode and rowCount in
     * particular are essential outputs); a mapping to a term the code file does not declare is only reported.
     */
    void alignWithCodeFileSignature(Step step, List<CodeFileSignature.Term> signature, Decision decision, String nodeName) {
        if (signature == null || signature.isEmpty() || step.getMappings() == null) {
            return;
        }
        Map<String, CodeFileSignature.Term> declared = new LinkedHashMap<>();
        for (CodeFileSignature.Term term : signature) {
            if (term.getName() != null) {
                declared.putIfAbsent(term.getName().toLowerCase(Locale.ROOT), term);
            }
        }
        Set<String> mapped = new HashSet<>();
        for (Mapping mapping : step.getMappings()) {
            String stepTerm = mapping.getStepTermName();
            if (stepTerm == null) {
                continue;
            }
            mapped.add(stepTerm.toLowerCase(Locale.ROOT));
            if (!declared.containsKey(stepTerm.toLowerCase(Locale.ROOT)) && !STANDARD_DATA_PROCESS_OUTPUTS.contains(stepTerm)) {
                log.warn("Node '{}': the code file does not declare '{}' (mapped to '{}')", nodeName, stepTerm,
                    mapping.getTargetDecisionTermName());
            }
        }
        for (CodeFileSignature.Term term : declared.values()) {
            boolean input = INPUT_DIRECTION.equals(term.getDirection()) || IN_OUT_DIRECTION.equals(term.getDirection());
            if (!input || mapped.contains(term.getName().toLowerCase(Locale.ROOT))) {
                continue;
            }
            log.warn("Node '{}': code file input '{}' is not set by this campaign's version of the process; it is passed"
                + " an unset value", nodeName, term.getName());
            Mapping mapping = new Mapping();
            mapping.setStepTermName(term.getName());
            mapping.setTargetDecisionTermName(commonProcessing.sanitizeVariableName(term.getName()));
            mapping.setDirection(Mapping.DirectionEnum.INPUT);
            step.getMappings().add(mapping);
            SignatureTerm unset = new SignatureTerm();
            unset.setName(mapping.getTargetDecisionTermName());
            SignatureTerm.DataTypeEnum type = SignatureTerm.DataTypeEnum.fromValue(term.getDataType());
            unset.setDataType(type != null ? type : SignatureTerm.DataTypeEnum.UNKNOWN);
            if (SignatureTerm.DataTypeEnum.DATAGRID.equals(unset.getDataType())) {
                unset.setDataGridExtension(new ArrayList<>());
            }
            commonProcessing.checkSignatureTermForDuplicate(NONE_DIRECTION, decision, unset);
        }
    }

    private void patchFile(String baseIp, String accessToken, String processName, boolean isDataGridOut, String location) throws URISyntaxException {
        if (!isDataGridOut) {
            HttpEntity<String> response = restTemplate.exchange(ViyaApi.createUri(baseIp, location, mapStorage.getProtocol()), HttpMethod.GET,
                    ViyaApi.createGetFilesFilesHTTPEntity(accessToken), String.class);
            HttpHeaders headers = response.getHeaders();
            String etag = headers.getFirst(HttpHeaders.ETAG);
            String lastModified = headers.getFirst(HttpHeaders.LAST_MODIFIED);
            ResponseEntity<DecisionCodeFileResponse> patchResponse = restTemplate.exchange(ViyaApi.createUri(baseIp, location, mapStorage.getProtocol()), HttpMethod.PATCH,
                    ViyaApi.createPatchFileRequestHTTPEntity(processName, etag, lastModified, accessToken),
                    DecisionCodeFileResponse.class);
        }
    }

    private String modifyDS2CodeToIDFormat(ProcessNodeDataDO process) {
        // This is not reliable as there could be a space after the semi-colon
        //int startIndex = ds2code.indexOf("package");
        //int lastIndexOf = ds2code.lastIndexOf("=yes;") + 4;
        //ds2code = ds2code.replace(ds2code.substring(startIndex, lastIndexOf), "package \"${PACKAGE_NAME}\" /inline;");

        String ds2code = process.getProcess().getDs2code();
        String processTypeDescription = process.getProcess().getProcessTypeDescription();
        String modelName = process.getProcess().getName();

        if (ds2code != null && ds2code.contains("sqlstmt")) {
            // Certain sqlstmt commands do not compile in ID
            // For example: sqlstmt_pkg = _NEW_ sqlstmt(sqlQuery,[TEMP_TRX_P]);
            // For now enclose DS2 code in quotes to allow migration code to continue
            // Use of sqlstmt can be evaluated later in another story
            return processJavaCodeOrWebServiceOrBusinessRulesForId(process);
        }

        ds2code = convertDs2PackageToIdFormat(ds2code, modelName);
        if (MODEL_CONSTANT.equals(processTypeDescription)) {
            ds2code = "// Model from RTDM: " + modelName + NEW_LINE_STRING + ds2code;
        }
        return ds2code;
    }

    /**
     * Rewrites an RTDM DS2 package into the form ID expects for a DS2 code file: the package declaration is renamed to
     * the ID placeholder and made inline, RTDM-only types are replaced, list parameters of the execute method become
     * data grids, and anything after endpackage is dropped. Throws IllegalArgumentException when the code does not
     * have the expected package structure.
     */
    static String convertDs2PackageToIdFormat(String ds2code, String processName) {
        if (ds2code == null || ds2code.trim().isEmpty()) {
            throw new IllegalArgumentException("DS2 code of process '" + processName + "' is empty");
        }
        ds2code = splitNestedCommentOpeners(ds2code);

        Matcher packageMatcher = PACKAGE_DECLARATION_PATTERN.matcher(ds2code);
        if (!packageMatcher.find()) {
            throw new IllegalArgumentException("DS2 code of process '" + processName + "' has no package declaration");
        }
        int declarationEnd = ds2code.indexOf(';', packageMatcher.end());
        if (declarationEnd < 0) {
            throw new IllegalArgumentException("DS2 package declaration of process '" + processName + "' is not terminated by ';'");
        }
        String options = OVERWRITE_OPTION_PATTERN.matcher(ds2code.substring(packageMatcher.end(), declarationEnd))
                .replaceAll("/inline");
        ds2code = ds2code.substring(0, packageMatcher.start(2)) + "\"${PACKAGE_NAME}\"" + options
                + ds2code.substring(declarationEnd);

        Matcher endPackageMatcher = END_PACKAGE_PATTERN.matcher(ds2code);
        if (!endPackageMatcher.find()) {
            throw new IllegalArgumentException("DS2 code of process '" + processName + "' has no endpackage statement");
        }
        ds2code = ds2code.substring(0, endPackageMatcher.end());

        ds2code = convertIntToDouble(ds2code);
        ds2code = convertTapPackages(ds2code);
        return convertExecuteListsToDatagrid(ds2code, processName);
    }

    /**
     * DS2 block comments end at the first "*&#47;", but ID's DS2 parser (MAS StrippedDS2PreLexer, used by the codeFiles
     * API) treats "/*" inside a comment as a nested comment. A banner such as "/* ***" followed by "/*****&#47;" then
     * never closes for ID, which hides endpackage and fails with 'The package with the name "${PACKAGE_NAME}" was not
     * found'. Each "/*" inside a comment becomes "/ *" (and a comment ending in "/" gets a space before its "*&#47;"),
     * so ID ends every comment where DS2 does. String literals and code outside comments are unchanged.
     */
    static String splitNestedCommentOpeners(String ds2code) {
        StringBuilder result = new StringBuilder(ds2code.length());
        int length = ds2code.length();
        int i = 0;
        while (i < length) {
            char c = ds2code.charAt(i);
            if (c == '/' && i + 1 < length && ds2code.charAt(i + 1) == '*') {
                int close = ds2code.indexOf("*/", i + 2);
                if (close < 0) {
                    result.append(ds2code, i, length);
                    break;
                }
                String body = ds2code.substring(i + 2, close).replace("/*", "/ *");
                if (body.endsWith("/")) {
                    body += " ";
                }
                result.append("/*").append(body).append("*/");
                i = close + 2;
            } else if (c == '\'' || c == '"') {
                // A quote inside a literal is written twice, which this scan reads as two adjacent literals
                int close = ds2code.indexOf(c, i + 1);
                int end = close < 0 ? length : close + 1;
                result.append(ds2code, i, end);
                i = end;
            } else {
                result.append(c);
                i++;
            }
        }
        return result.toString();
    }

    private static String convertExecuteListsToDatagrid(String ds2code, String processName) {
        if (!TAP_ARRAY_PATTERN.matcher(ds2code).find()) {
            return ds2code;
        }
        ds2code = TAP_ARRAY_PATTERN.matcher(ds2code).replaceAll(DS2_DATAGRID);

        Matcher executeMatcher = EXECUTE_METHOD_PATTERN.matcher(ds2code);
        if (!executeMatcher.find()) {
            throw new IllegalArgumentException("DS2 code of process '" + processName + "' has no execute method");
        }
        int parametersStart = executeMatcher.end();
        int parametersEnd = findClosingParenthesis(ds2code, parametersStart - 1);
        if (parametersEnd < 0) {
            throw new IllegalArgumentException("Parameter list of the execute method of process '" + processName + "' is not closed");
        }

        // Split the parameter list on top-level commas only (types such as varchar(100) contain parentheses)
        String parameters = ds2code.substring(parametersStart, parametersEnd);
        StringBuilder replacement = new StringBuilder();
        int depth = 0;
        int parameterStart = 0;
        for (int i = 0; i <= parameters.length(); i++) {
            char c = i < parameters.length() ? parameters.charAt(i) : ',';
            if (c == '(') {
                depth++;
            } else if (c == ')') {
                depth--;
            } else if (c == ',' && depth == 0) {
                String parameter = parameters.substring(parameterStart, i);
                if (DATAGRID_WORD_PATTERN.matcher(parameter).find()) {
                    String trimmed = parameter.replaceAll("\\s+$", "");
                    parameter = trimmed + "_dgo" + parameter.substring(trimmed.length());
                }
                replacement.append(parameter);
                if (i < parameters.length()) {
                    replacement.append(',');
                }
                parameterStart = i + 1;
            }
        }
        return ds2code.substring(0, parametersStart) + replacement + ds2code.substring(parametersEnd);
    }

    private static int findClosingParenthesis(String text, int openIndex) {
        int depth = 0;
        for (int i = openIndex; i < text.length(); i++) {
            if (text.charAt(i) == '(') {
                depth++;
            } else if (text.charAt(i) == ')') {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static String convertIntToDouble(String ds2code) {
        // Only the stand-alone DS2 type keyword "int", not identifiers that merely end in "int" (e.g. print, myint)
        return INT_TYPE_PATTERN.matcher(ds2code).replaceAll(DOUBLE_CONSTANT);
    }

    private static String convertTapPackages(String ds2code) {
        ds2code = TAP_DATETIME_PACKAGE_PATTERN.matcher(ds2code).replaceAll(DOUBLE_CONSTANT);
        ds2code = TAP_HASH_PACKAGE_PATTERN.matcher(ds2code).replaceAll(DS2_HASH_CONSTANT.trim());
        ds2code = TAP_LOGGER_PACKAGE_PATTERN.matcher(ds2code).replaceAll(DS2_LOGGER_CONSTANT.trim());
        ds2code = TAP_SQLTABLE_PACKAGE_PATTERN.matcher(ds2code).replaceAll(DS2_DATAGRID);
        return ds2code;
    }

    private String modifyDataProcessToSqlNode(ProcessNodeDataDO.Process process) {
        switch (process.getProcessType()) {
            case PROCESS_TYPE_READ_DATA:
                return buildSelectSql(process);
            case PROCESS_TYPE_INSERT_DATA:
                return buildInsertSql(process);
            case PROCESS_TYPE_UPDATE_DATA:
                return buildUpdateSql(process);
            default:
                // Should never get here
                return buildSelectSql(process);
        }
    }

    private String buildSelectSql(ProcessNodeDataDO.Process process) {
        // Example:
        // /* include sqlReturnInfo */
        // select CLIENTID AS {:CLIENTID:decimal}, HHID AS {:HHID:decimal}, RANDNOM5000 AS {:RANDNOM5000:string:1000}
        // from "CUSTOMER"
        // where CLIENTID={?:CLIENTID:decimal}
        if (outputVariables(process).isEmpty()) {
            throw new IllegalArgumentException("Read data process '" + process.getName() + "' has no output variables to select");
        }
        StringBuilder stb = new StringBuilder("/* include sqlReturnInfo */\n");
        stb.append("select ");

        for (ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO outputVars :
                outputVariables(process)) {
            String datatypeOfVarForSQLNode = getDatatypeOfVarForSQLNode(outputVars.getTypeDescription());
            String var = outputVars.getName() + " AS {:" + outputVars.getName()
                    + ":" + datatypeOfVarForSQLNode + "}";
            var = modifyIfStringDatatype(var, datatypeOfVarForSQLNode);
            stb.append(var);
            stb.append(", ");
        }
        stb.delete(stb.length() - 2, stb.length());

        stb.append(" from ");
        quoteTableName(stb, process.getTableName());
        
        appendWhereClause(stb, process, inputVariables(process));
        return stb.toString();
    }

    private static List<ProcessNodeDataDO.Process.InputVariableList.IBVariableDO> inputVariables(ProcessNodeDataDO.Process process) {
        if (process.getInputVariableList() == null || process.getInputVariableList().getIbVariableDOs() == null) {
            return Collections.emptyList();
        }
        return process.getInputVariableList().getIbVariableDOs();
    }

    private static List<ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO> outputVariables(ProcessNodeDataDO.Process process) {
        if (process.getOutputVariableList() == null || process.getOutputVariableList().getIbVariableDOs() == null) {
            return Collections.emptyList();
        }
        return process.getOutputVariableList().getIbVariableDOs();
    }

    private String buildInsertSql(ProcessNodeDataDO.Process process) {
        // Example:
        // insert into "CUSTOMER" (CLIENTID, HHID, RANDNOM5000)
        // values ({?:CLIENTID:decimal}, {?:HHID:decimal}, {?:RANDNOM5000:string:1000})
        StringBuilder stb = new StringBuilder("insert into ");
        quoteTableName(stb, process.getTableName());

        stb.append(" (");
        if (!inputVariables(process).isEmpty()) {
            for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO inputVar : inputVariables(process)) {
                stb.append(inputVar.getName());
                stb.append(", ");
            }
            stb.delete(stb.length() - 2, stb.length());
        }
        stb.append(")");

        stb.append(" values ");
        stb.append("(");

        if (!inputVariables(process).isEmpty()) {
            for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO inputVars : inputVariables(process)) {
                String datatypeOfVarForSQLNode = getDatatypeOfVarForSQLNode(inputVars.getTypeDescription());
                String var = "{?:" + inputVars.getName() + ":" + datatypeOfVarForSQLNode + "}";
                var = modifyIfStringDatatype(var, datatypeOfVarForSQLNode);
                stb.append(var);
                stb.append(", ");
            }
            stb.delete(stb.length() - 2, stb.length());
        }
        stb.append(")");

        return stb.toString();
    }

    private String buildUpdateSql(ProcessNodeDataDO.Process process) {
        // Example:
        // update "CUSTOMER"
        // set AGE_UPDATABLE={?:AGE_UPDATABLE_set:decimal}, HHID={?:HHID_set:decimal}
        // where CLIENTID={?:CLIENTID:decimal}
        //
        // In an RTDM update-data process the input variables are the criteria (WHERE) columns and the output
        // variables are the columns to update, each carrying its new value. normalizeUpdateDataProcesses turns the
        // update columns into SET parameters (inputs marked with updateColumn) so their values flow in through the
        // node's input-values RuleSet like any other input; a column may be both a criterion and updated.
        normalizeUpdateDataProcess(process);
        List<ProcessNodeDataDO.Process.InputVariableList.IBVariableDO> setVariables = inputVariables(process).stream()
                .filter(variable -> variable.getUpdateColumn() != null).toList();
        if (setVariables.isEmpty()) {
            throw new IllegalArgumentException("Update data process '" + process.getName() + "' has no columns to update");
        }
        StringBuilder stb = new StringBuilder("update ");
        quoteTableName(stb, process.getTableName());

        stb.append(" set ");
        for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO setVariable : setVariables) {
            String datatypeOfVarForSQLNode = getDatatypeOfVarForSQLNode(setVariable.getTypeDescription());
            String var = setVariable.getUpdateColumn() + "={?:" + setVariable.getName() + ":" + datatypeOfVarForSQLNode + "}";
            var = modifyIfStringDatatype(var, datatypeOfVarForSQLNode);
            stb.append(var);
            stb.append(", ");
        }
        stb.delete(stb.length() - 2, stb.length());

        appendWhereClause(stb, process, inputVariables(process).stream()
                .filter(variable -> variable.getUpdateColumn() == null).toList());
        return stb.toString();
    }

    /**
     * WhereClauseOp (column op value) to SQL. RTDM does not document the encoding; it is inferred from customer
     * extracts: 0 is equality almost everywhere, validity windows use VALID_FROM_DTTM 5 and VALID_TO_DTTM 3 (only
     * "FROM <= date and TO >= date" makes sense, which fixes 5 as <= and 3 as >=), and a status-code exclusion uses 1
     * (<>). That is the ordering =, <>, >, >=, <, <=; 2 and 4 follow from it but have not been seen.
     */
    private static final List<String> WHERE_CLAUSE_OPERATORS = List.of("=", "<>", ">", ">=", "<", "<=");

    /**
     * Appends {@code where COL={?:COL:type} and ...} for the criteria variables, using each criterion's WhereClauseOp.
     * An operator outside the known range fails rather than being guessed.
     */
    private void appendWhereClause(StringBuilder stb, ProcessNodeDataDO.Process process,
            List<ProcessNodeDataDO.Process.InputVariableList.IBVariableDO> criteria) {
        if (criteria.isEmpty()) {
            return;
        }
        stb.append(" where ");
        for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO criterion : criteria) {
            int op = criterion.getWhereClauseOp();
            if (op < 0 || op >= WHERE_CLAUSE_OPERATORS.size()) {
                throw new IllegalArgumentException("Data process '" + process.getName() + "' uses unsupported WhereClauseOp "
                        + op + " for column " + criterion.getName() + "; known values are 0-5 (=, <>, >, >=, <, <=)");
            }
            String datatypeOfVarForSQLNode = getDatatypeOfVarForSQLNode(criterion.getTypeDescription());
            String var = criterion.getName() + WHERE_CLAUSE_OPERATORS.get(op) + "{?:" + criterion.getName() + ":" + datatypeOfVarForSQLNode + "}";
            var = modifyIfStringDatatype(var, datatypeOfVarForSQLNode);
            stb.append(var);
            stb.append(" and ");
        }
        stb.delete(stb.length() - 5, stb.length());
    }

    /**
     * Rewrites every update-data process so its update columns become SET parameters in the input variable list.
     * Must run before custom nodes, RuleSets and steps are created, so that the SQL, the input-values RuleSet, the
     * step mappings and calculated-item scheduling all see the same inputs. Idempotent.
     */
    public static void normalizeUpdateDataProcesses(Batch batch) {
        if (batch == null || batch.getLogicalUnit() == null || batch.getLogicalUnit().getProcessNodeDataDOs() == null) {
            return;
        }
        for (ProcessNodeDataDO processNode : batch.getLogicalUnit().getProcessNodeDataDOs()) {
            if (processNode.getProcess() != null) {
                normalizeUpdateDataProcess(processNode.getProcess());
            }
        }
    }

    static void normalizeUpdateDataProcess(ProcessNodeDataDO.Process process) {
        if (process.getProcessType() != PROCESS_TYPE_UPDATE_DATA
                && !UPDATE_DATA_CONSTANT.equals(process.getProcessTypeDescription())) {
            return;
        }
        if (outputVariables(process).isEmpty()) {
            return;
        }
        if (process.getInputVariableList() == null) {
            process.setInputVariableList(new ProcessNodeDataDO.Process.InputVariableList());
        }
        List<ProcessNodeDataDO.Process.InputVariableList.IBVariableDO> inputs = process.getInputVariableList().getIbVariableDOs();
        Set<String> usedNames = new HashSet<>();
        inputs.forEach(input -> usedNames.add(input.getName().toLowerCase(Locale.ROOT)));
        outputVariables(process).forEach(output -> usedNames.add(output.getName().toLowerCase(Locale.ROOT)));
        for (ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO column : outputVariables(process)) {
            String parameterName = column.getName() + "_set";
            while (!usedNames.add(parameterName.toLowerCase(Locale.ROOT))) {
                parameterName = parameterName + "_";
            }
            ProcessNodeDataDO.Process.InputVariableList.IBVariableDO setVariable = new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO();
            setVariable.setName(parameterName);
            setVariable.setPhysicalName(parameterName);
            setVariable.setDescription(column.getDescription());
            setVariable.setType(column.getType());
            setVariable.setTypeDescription(column.getTypeDescription());
            setVariable.setLevel(column.getLevel());
            setVariable.setValue(column.getValue());
            setVariable.setUpdateColumn(column.getName());
            inputs.add(setVariable);
        }
        // The update columns are not outputs of the generated SQL (it only returns returnCode/rowCount)
        process.getOutputVariableList().getIbVariableDOs().clear();
    }

    private StringBuilder quoteTableName(StringBuilder stb, String tableName) {
        stb.append("\"").append(tableName).append("\"");
        return stb;
    }

    private String processJavaCodeOrWebServiceOrBusinessRulesForId(ProcessNodeDataDO processNodeDataDO) {
        ProcessNodeDataDO.Process process = processNodeDataDO.getProcess();

        StringBuilder ds2Code = new StringBuilder();

        // Create DS2 package for custom node
        ds2Code.append("package \"${PACKAGE_NAME}\" /overwrite=yes ;").append(NEW_LINE_STRING);
        ds2Code.append("/*          */").append(NEW_LINE_STRING);
        ds2Code.append("   method execute();").append(NEW_LINE_STRING);

        // Insert comment inside execute method containing node properties
        ds2Code.append("/*").append(NEW_LINE_STRING);
        ds2Code.append(processNodeDataDO.getNodeName()).append(" Node migrated from RTDM").append(NEW_LINE_STRING);
        ds2Code.append(NEW_LINE_STRING);

        if (PROCESS_TYPE_WEB_SERVICE==process.getProcessType()) {
            // Web processes don't have DS2 code, it's a web service
            ds2Code.append("Web Process node is not supported.").append(NEW_LINE_STRING);
            ds2Code.append("    Description: ").append(process.getDescription()).append(NEW_LINE_STRING);
            ds2Code.append("    Name :").append(process.getName()).append(NEW_LINE_STRING);

            // Escape backslashes in URL
            ds2Code.append("    Web Service: ").append(process.getPhysicalActivityMethodName().replaceAll("\"", "\\\\\"")).append(NEW_LINE_STRING);
        } else {
            ds2Code.append("Groovy Process node is not supported.").append(NEW_LINE_STRING);
            // Existing code could contain quotes
            // Since we're embedding the entire execute method code in quotes we need to escape these quotes
            String replacementCode = process.getDs2code().replaceAll("\"", "\\\\\"");

            //Remove any existing comments from code
            Matcher matcher1 = javaComment1Pattern.matcher(replacementCode);
            Matcher matcher2 = javaComment2Pattern.matcher(replacementCode);
            
            replacementCode = matcher1.replaceAll(EMPTY_STRING);
            replacementCode = matcher2.replaceAll(EMPTY_STRING);

            replacementCode = replacementCode.replaceAll("//*", EMPTY_STRING);
            ds2Code.append(replacementCode);
        }

        ds2Code.append("*/");

        // Complete remainder of execute method
        ds2Code.append("   end;").append(NEW_LINE_STRING);
        ds2Code.append("endpackage;").append(NEW_LINE_STRING);
        ds2Code.append("run;").append(NEW_LINE_STRING);

        process.setDs2code(ds2Code.toString());

        return ds2Code.toString();
    }

    private void createVariablesForExecuteMethod(IBVariableDO ibVariableDO, StringBuilder variable, String direction) {
        SignatureTerm.DataTypeEnum datatypeOfVar = commonProcessing.getDatatypeOfVar(ibVariableDO.getTypeDescription());
        String datatypeOfVarString;
        if (SignatureTerm.DataTypeEnum.DATAGRID.equals(datatypeOfVar)) {
            datatypeOfVarString = "package datagrid";
        } else if (SignatureTerm.DataTypeEnum.DECIMAL.equals(datatypeOfVar)
                || SignatureTerm.DataTypeEnum.INTEGER.equals(datatypeOfVar)) {
            datatypeOfVarString = DOUBLE_CONSTANT;
        } else {
            datatypeOfVarString = "varchar(100)";
        }
        if (OUTPUT_DIRECTION.equals(direction))
            variable.append("in_out ");
        variable
                .append(datatypeOfVarString)
                .append(" ")
                .append(ibVariableDO.getName())
                .append(", ");
    }

    private String getDatatypeOfVarForSQLNode(String typeDescription) {
        String dataType;
        switch (typeDescription) {
            case STRING_CONSTANT:
            case STRING_LIST_CONSTANT:
            case DATAGRID_CONSTANT:
            case BOOLEAN_CONSTANT:
                dataType = STRING_CONSTANT;
                break;
            case INTEGER_CONSTANT:
                dataType = INTEGER_CONSTANT;
                break;
            case DOUBLE_CONSTANT:
            case DOUBLE_LIST_CONSTANT:
                dataType = DECIMAL_CONSTANT;
                break;
            case DATE_CONSTANT:
            case DATE_LIST_CONSTANT:
                dataType = DATETIME_CONSTANT;
                break;
            default:
                dataType = "any?";
                break;
        }
        return dataType;
    }

    private String modifyIfStringDatatype(String query, String datatypeOfVarForSQLNode) {
        if (STRING_CONSTANT.equals(datatypeOfVarForSQLNode)) {
            return query.replace("}", ":1000}");
        }
        return query;
    }

    /**
     * Maps the data grid a data process returns. The grid is written under the name later nodes read it by: RTDM's
     * output id {@code <NodeName>.<ProcessName><DatagridName>}, or the identifier it is copied to (DatagridIdentifier).
     * The extraction RuleSet then copies its columns out (see {@link #updateRuleSet(SignatureTerm, Step, Decision, String, String)}).
     */
    private SignatureTerm processDataProcessMapping(String nodeName, ProcessNodeDataDO.Process process, Step step, Decision decision) {
        List<SignatureTermExtension> signatureTermExtensionList = new ArrayList<>();
        String processName = process.getName();

        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();

        for (ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO ibVariableDO
                : process.getOutputVariableList().getIbVariableDOs()) {
            if (!DATAGRID_CONSTANT.equals(ibVariableDO.getTypeDescription())) {
                signatureTermExtensionList.add(createDataGridExtension(mapper.ibVariableDoGet(ibVariableDO)));
            }
        }
        String gridName = process.getDatagridName() == null || process.getDatagridName().isBlank() ? "_out" : process.getDatagridName();
        SignatureTerm signatureTerm = new SignatureTerm();
        signatureTerm.setName(commonProcessing.nodeOutputTermName(CommonProcessing.nodeOutputId(nodeName, processName, gridName)));
        signatureTerm.setDataType(SignatureTerm.DataTypeEnum.DATAGRID);
        signatureTerm.setDirection(SignatureTerm.DirectionEnum.NONE);
        signatureTerm.setDataGridExtension(signatureTermExtensionList);
        commonProcessing.checkSignatureTermForDuplicate("none", decision, signatureTerm);
        Mapping mapping = new Mapping();
        mapping.setTargetDecisionTermName(signatureTerm.getName());
        mapping.setDirection(Mapping.DirectionEnum.OUTPUT);
        // The SQL code file's output data grid, named by ID after the code file (see sqlOutputGridTerm)
        mapping.setStepTermName(sqlOutputGridTerm(processName));
        commonProcessing.checkMappingForDuplicate(mapping, step);
        STANDARD_DATA_PROCESS_OUTPUTS.forEach(output -> addStandardDataProcessOutputMapping(output, step, decision));
        return signatureTerm;
    }


    /**
     * A list input is a one-column data grid parameter of the code file ({@code <var>_dgo}). It reads the data grid its
     * value references (an upstream list or grid output, an identifier, a request or a global variable); without a
     * reference it reads an (unset) term named after the parameter, as before.
     */
    private void addListInputMapping(ProcessNodeDataDO.Process.InputVariableList.IBVariableDO input, Step step, Decision decision) {
        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();
        String target = null;
        ValueTypeVarInfoDO reference = input.getValue() == null ? null : input.getValue().getValueTypeVarInfoDO();
        if (reference != null) {
            String resolved = commonProcessing.resolveReference(reference.getVarInfoId());
            if (resolved != null) {
                target = commonProcessing.sanitizeVariableName(commonProcessing.changeVariableNameToFormat(resolved));
            }
        }
        if (target == null) {
            target = commonProcessing.sanitizeVariableName(input.getName() + "_dgo");
            String literal = input.getValue() == null ? null : input.getValue().getListValue();
            if (literal != null && !literal.isBlank()) {
                // TODO: no verified ID expression builds a data grid from constants, so the RTDM literal is not migrated
                log.warn("List input '{}' has the constant value '{}' in RTDM; set data grid '{}' manually in the migrated Decision",
                    input.getName(), literal, target);
            }
        }
        Mapping mapping = new Mapping();
        mapping.setStepTermName(commonProcessing.sanitizeAndReduceVariableName(input.getName() + "_dgo"));
        mapping.setTargetDecisionTermName(target);
        mapping.setDirection(Mapping.DirectionEnum.INPUT);
        commonProcessing.checkMappingForDuplicate(mapping, step);

        SignatureTerm term = new SignatureTerm();
        term.setName(target);
        term.setDataType(SignatureTerm.DataTypeEnum.DATAGRID);
        term.setDirection(SignatureTerm.DirectionEnum.NONE);
        term.setDataGridExtension(new ArrayList<>(List.of(createDataGridExtension(mapper.ibVariableDoGet(input)))));
        commonProcessing.checkSignatureTermForDuplicate("none", decision, term);
    }

    private void processCustomObjectInputMapping(ProcessNodeDataDO processNodeDataDO, Decision decision, List<Step> stepList, Step step) {
        ProcessNodeDataDO.Process process = processNodeDataDO.getProcess();
        
        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();

        for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO ibVariableDO : process.getInputVariableList().getIbVariableDOs()) {
            IBVariableDO ibVariableDOMapped = mapper.ibVariableDoGet(ibVariableDO);
            String varOriginalName = ibVariableDOMapped.getName();
            if (ibVariableDOMapped.getValue() != null) {
                ValueTypeVarInfoDO valueTypeVarInfoDO = ibVariableDOMapped.getValue().getValueTypeVarInfoDO();
                String varName = ibVariableDO.getName();
                final String varInfo = valueTypeVarInfoDO != null ? valueTypeVarInfoDO.getVarInfoId() : "";
                if (!commonProcessing.checkForCalcVariable(varInfo,ibVariableDOMapped.getTypeDescription(), stepList, null, varOriginalName, processNodeDataDO.getNodeId())) {
                    if (ibVariableDO.getTypeDescription().contains("list")) {
                        addListInputMapping(ibVariableDO, step, decision);
                    } else {
                        commonProcessing.addMapping(ibVariableDOMapped, INPUT_DIRECTION, step, false, decision);
                        commonProcessing.addNewSignatureItem(ibVariableDOMapped, "none", decision, null);
                    }
                } else {
                    commonProcessing.addMapping(varOriginalName, varOriginalName, step, false);
                }
                if (valueTypeVarInfoDO != null) {
                    VarRef varRef = VarRefMapper.VAR_REF_MAPPER.varRefGet(valueTypeVarInfoDO);
                    commonProcessing.checkForAdditionalVariables(varName, varRef, "none", decision, ibVariableDOMapped.getTypeDescription());
                }
            } else {
                // Even if no value assigned we need the mappings and signature items
                if (ibVariableDO.getTypeDescription().contains("list")) {
                    addListInputMapping(ibVariableDO, step, decision);
                } else {
                    commonProcessing.addMapping(ibVariableDOMapped, INPUT_DIRECTION, step, false, decision);
                    commonProcessing.addNewSignatureItem(ibVariableDOMapped, "none", decision, null);
                }
            }
        }
    }

    private void processCustomObjectMapping(String nodeName, ProcessNodeDataDO.Process process, Step step, Decision decision,
            Map<String, String> inOutTerms) {
        List<ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO> ibVariableDOList = process.getOutputVariableList().getIbVariableDOs();

        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();
        String prefix = CommonProcessing.outputReferencePrefix(process);

        for (ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO ibVariableDO : ibVariableDOList) {
            if (ibVariableDO.getTypeDescription().contains("list")) {
                // A list output is a one-column data grid (the code file's <var>_dgo parameter), written like any other
                // output under the name later nodes read it by: its identifier when linked to one, else the output id
                commonProcessing.addProducedOutput(CommonProcessing.nodeOutputId(nodeName, prefix, ibVariableDO.getName()),
                        ibVariableDO.getName() + "_dgo", SignatureTerm.DataTypeEnum.DATAGRID,
                        List.of(createDataGridExtension(mapper.ibVariableDoGet(ibVariableDO))), step, decision);
            } else {
                // Written under the name later nodes read it by: its identifier when linked to one, otherwise the RTDM
                // output id "<NodeName>.<ProcessName><var>", "<NodeName>.<var>" for a rules group (see
                // CommonProcessing.registerNodeOutputs)
                commonProcessing.addProducedOutput(CommonProcessing.nodeOutputId(nodeName, prefix, ibVariableDO.getName()),
                        ibVariableDO.getName(), ibVariableDO.getTypeDescription(), step, decision);
                String inOutTerm = inOutTerms.get(ibVariableDO.getName());
                if (inOutTerm != null) {
                    // The parameter's input mapping (which kept addProducedOutput from adding an output mapping) becomes
                    // in/out on the output term, which the _input_values RuleSet loaded with the input value
                    String stepTerm = commonProcessing.sanitizeAndReduceVariableName(ibVariableDO.getName());
                    step.getMappings().stream().filter(mapping -> stepTerm.equalsIgnoreCase(mapping.getStepTermName()))
                        .findFirst().ifPresent(mapping -> {
                            mapping.setDirection(Mapping.DirectionEnum.INOUT);
                            mapping.setTargetDecisionTermName(inOutTerm);
                        });
                }
            }
        }

        if (commonProcessing.isProcessDataProcess(process.getProcessTypeDescription())
                && !isDataProcessReturnDataGrid(process)) {
            STANDARD_DATA_PROCESS_OUTPUTS.forEach(output -> addStandardDataProcessOutputMapping(output, step, decision));
        }
    }

    private void addStandardDataProcessOutputMapping(String variableName, Step step, Decision decision) {
        IBVariableDO outputVariable = new IBVariableDO();
        outputVariable.setName(variableName);
        outputVariable.setTypeDescription(INTEGER_CONSTANT);
        commonProcessing.addMapping(outputVariable, OUTPUT_DIRECTION, step, false, decision);
        commonProcessing.addNewSignatureItem(outputVariable, OUTPUT_DIRECTION, decision, null);
    }

    private SignatureTermExtension createDataGridExtension(IBVariableDO ibVariableDO) {
        SignatureTermExtension signatureTermExtension = new SignatureTermExtension();
        signatureTermExtension.setName(ibVariableDO.getName());

        SignatureTerm.DataTypeEnum datatypeOfVar = commonProcessing.getDatatypeOfVar(ibVariableDO.getTypeDescription());
        SignatureTermExtension.DataTypeEnum dataTypeForExtension = getDataTypeForExtension(datatypeOfVar);
        signatureTermExtension.setDataType(dataTypeForExtension);

        if (SignatureTermExtension.DataTypeEnum.STRING.equals(signatureTermExtension.getDataType())) {
            signatureTermExtension.setLength(1000);
        }
        return signatureTermExtension;
    }


    private SignatureTermExtension.DataTypeEnum getDataTypeForExtension(SignatureTerm.DataTypeEnum type) {
        if (SignatureTerm.DataTypeEnum.DECIMAL.equals(type)
                || SignatureTerm.DataTypeEnum.INTEGER.equals(type)
                || SignatureTerm.DataTypeEnum.DATETIME.equals(type)) {
            return SignatureTermExtension.DataTypeEnum.fromValue(type.getValue());
        }
        return SignatureTermExtension.DataTypeEnum.STRING;
    }

    private boolean isDataProcessReturnDataGrid(ProcessNodeDataDO.Process process) {
        boolean isDataProcessReturnDataGrid = false;

        if (process.getReturnDatagridFlag() != null) {
            isDataProcessReturnDataGrid = Boolean.parseBoolean(process.getReturnDatagridFlag());
        }

        return isDataProcessReturnDataGrid;
    }
}
