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
import com.sas.rtdm2id.model.id.files.CustomNodeRequest;
import com.sas.rtdm2id.model.id.files.DecisionCodeFileResponse;
import com.sas.rtdm2id.model.id.rules.Action;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.id.rules.Signature;
import com.sas.rtdm2id.model.rtdm.*;
import com.sas.rtdm2id.model.rtdm.extension.Value;
import com.sas.rtdm2id.model.rtdm.extension.VarRef;
import com.sas.rtdm2id.util.ViyaApi;
import com.sas.rtdm2id.util.model.IBVariableDO;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.*;

@Component
public class ProcessNodeConverter {
    private static final String NEW_LINE_STRING = "\n";
    private final RestTemplate restTemplate;
    private final CommonProcessing commonProcessing;
    private final MapStorage mapStorage;

    private static final String PACKAGE_NAME_REGEX = "(\n\\s*package\\s+)([^\\s/;]+)(.*)";//"(?<!dcl\\s{1,1000}|declare\\s{1,1000}|returns\\s{1,1000}|in_out\\s{1,1000}|end|[,\\(]\\s{0,10000})package\\s+[^\\s/;]+";
    private static final Pattern packageNamePattern = Pattern.compile(PACKAGE_NAME_REGEX,Pattern.CASE_INSENSITIVE);

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

    public ProcessNodeConverter(RestTemplate restTemplate, CommonProcessing commonProcessing, MapStorage mapStorage) {
        this.restTemplate = restTemplate;
        this.commonProcessing = commonProcessing;
        this.mapStorage = mapStorage;
    }

    public List<Step> addCustomObjectStep(ProcessNodeDataDO.Process process, Decision decision, Short objId,
                                          String nodeName) {
        List<Step> stepList = new LinkedList<>();
        Step step = new Step();
        Step extractDataStep = null;
        step.setType(Step.TypeEnum.CUSTOM_OBJECT);
        Step inputValuesStep = mapStorage.getRuleSetStepMap().get(nodeName + "_input_values");
        if (process != null) {
            if (process.getInputVariableList() != null) {
                processCustomObjectInputMapping(process, decision, stepList, step);
                updateRuleSet(process.getInputVariableList(), inputValuesStep, decision);
            }
            if (process.getOutputVariableList() != null) {
                if (commonProcessing.isProcessNotDataProcess(process.getProcessTypeDescription()) || !isDataProcessReturnDataGrid(process)) {
                    processCustomObjectMapping(process, step, decision);
                } else {
                    SignatureTerm signatureTermDatagrid
                            = processDataProcessMapping(process.getOutputVariableList().getIBVariableDOs()
                            , step, decision, process.getName());

                    extractDataStep = mapStorage.getRuleSetStepMap().get(nodeName + "_extract_values");
 
                    updateRuleSet(signatureTermDatagrid, extractDataStep);
                }
            }
        }
        if (inputValuesStep!=null) {
            // Unsupported nodes are converted into DS2 code files but we don't create a ruleset node to
            // represent the custom node inputs so inputValuesStep is null in this scenario
            stepList.add(inputValuesStep);
        }
        step.setCustomObject(mapStorage.getCustomObjectStepHashMap().get(objId));
        stepList.add(step);
        if (extractDataStep != null) stepList.add(extractDataStep);
        return stepList;
    }

    public void updateRuleSet(SignatureTerm signatureTermDataGrid, Step step) {
        RuleSet ruleSet = new RuleSet();
        RuleSetStep ruleSetForUpdate = step.getRuleSet();

        List<Signature> signatureList = new ArrayList<>();
        Mapping mapping = new Mapping();
        mapping.setStepTermName(commonProcessing.sanitizeAndReduceVariableName(signatureTermDataGrid.getName()));
        mapping.setTargetDecisionTermName(commonProcessing.sanitizeVariableName(signatureTermDataGrid.getName()));
        mapping.setDirection(Mapping.DirectionEnum.INPUT);
        commonProcessing.checkMappingForDuplicate(mapping, step);

        ruleSet.setName(ruleSetForUpdate.getName());
        Signature signature = new Signature();
        signature.setDataType(SignatureTerm.DataTypeEnum.DATAGRID.getValue());
        signature.setName(commonProcessing.sanitizeAndReduceVariableName(signatureTermDataGrid.getName()));
        signature.setDirection(INPUT_DIRECTION);
        signatureList.add(signature);

        for (SignatureTermExtension signatureTermExtension : signatureTermDataGrid.getDataGridExtension()) {
            Mapping innerMapping = new Mapping();

            innerMapping.setStepTermName(commonProcessing.sanitizeAndReduceVariableName(signatureTermExtension.getName()));
            innerMapping.setTargetDecisionTermName(commonProcessing.sanitizeVariableName(signatureTermExtension.getName()));
            innerMapping.setDirection(Mapping.DirectionEnum.OUTPUT);
            commonProcessing.checkMappingForDuplicate(innerMapping, step);

            Signature innerSignature = new Signature();
            innerSignature.setDataType(signatureTermExtension.getDataType().getValue());
            innerSignature.setName(commonProcessing.sanitizeAndReduceVariableName(signatureTermExtension.getName()));
            innerSignature.setDirection(OUTPUT_DIRECTION);
            commonProcessing.checkSignatureTermForDuplicate(OUTPUT_DIRECTION, signatureList, innerSignature);

            Rule rule = new Rule();
            rule.setName(UUID.randomUUID().toString());
            rule.setConditional("if");
            rule.setRuleFiredTrackingEnabled(false);

            rule.setActions(Collections.singletonList(addAction(signatureTermExtension.getName(),
                    signatureTermDataGrid.getName(), signatureTermExtension.getDataType().getValue())));

            commonProcessing.addActionToRuleSet(ruleSetForUpdate, rule);
        }
        ruleSet.setSignature(signatureList);

        if (Boolean.FALSE.equals(mapStorage.getRuleSetIsUpdated().get(ruleSetForUpdate.getName()))) {
            commonProcessing.callPutRuleSet(ruleSetForUpdate, ruleSet);
        }
    }

    public void updateRuleSet(ProcessNodeDataDO.Process.InputVariableList inputVariableList, Step step, Decision decision) {
        RuleSet ruleSet = new RuleSet();
        Rule rule = new Rule();

        RuleSetStep ruleSetForUpdate = step.getRuleSet();
        List<ProcessNodeDataDO.Process.InputVariableList.IBVariableDO> ibVariableDOs = inputVariableList.getIBVariableDOs();
        List<Signature> signatureList = new ArrayList<>();
        List<Action> actionList = new ArrayList<>();

        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();

        if (ibVariableDOs != null && !ibVariableDOs.isEmpty()) {
            boolean ruleUpdated = false;

            for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO ibVariableDO : ibVariableDOs) {
                Signature signature = new Signature();
                signature.setDataType(commonProcessing.getDatatypeOfVar(ibVariableDO.getTypeDescription()).getValue());

                signature.setName(commonProcessing.sanitizeAndReduceVariableName(ibVariableDO.getName()));
                signature.setDirection(IN_OUT_DIRECTION);
                commonProcessing.checkSignatureTermForDuplicate(IN_OUT_DIRECTION, signatureList, signature);

                if (!(mapStorage.getRuleSetIsActionUpdated().get(ruleSetForUpdate.getName()) &&
                        mapStorage.getRuleSetIsUpdated().get(ruleSetForUpdate.getName()))) {
                    ruleSet.setName(ruleSetForUpdate.getName());
                    rule.setName(UUID.randomUUID().toString());
                    rule.setConditional("if");
                    rule.setRuleFiredTrackingEnabled(false);

                    commonProcessing.addAction(mapper.ibVariableDoGet(ibVariableDO), actionList, signatureList, step, decision);
                    
                    if (!commonProcessing.checkForGlobalVariableRuleSet(ibVariableDO)) {
                        commonProcessing.addMapping(mapper.ibVariableDoGet(ibVariableDO), INPUT_DIRECTION, step, false, decision);
                    }
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

    private Action addAction(String variableName, String dataGridName, String type) {
        Action action = new Action();
        action.setType("complex");
        action.setExpression(variableName + " = " + getAppropriateDataGridGet(type) + "(" + dataGridName + ",'" + variableName + "', 0 )");
        return action;
    }

    private String getAppropriateDataGridGet(String type) {
        if (DECIMAL_CONSTANT.equals(type)) {
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

                // Search for SQL nodes by name rather than description 
                if (PROCESS_TYPE_READ_DATA == process.getProcess().getProcessType()) {
                    codeFileCollection
                        = commonProcessing.getCodeFileCollection(mapStorage.getBaseIp(), process.getProcess().getPhysicalName(), accessToken, "/decisions/codeFiles");

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
                                stagedTreatmentsNodeDataDO.getSubjectVariables().getIbVariableDO(), stagedTreatmentsNodeDataDO.getDateModified(), stagedTreatmentsNodeDataDO.getLabel(), stagedTreatmentsNodeDataDO.getNodeId(),
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
                                removeStagedTreatmentsNodeDataDO.getSubjectVariables().getIbVariableDO(), removeStagedTreatmentsNodeDataDO.getDateModified(), removeStagedTreatmentsNodeDataDO.getLabel(), removeStagedTreatmentsNodeDataDO.getNodeId(),
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

        if (variablesObject != null) {
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
            customNodeRequest.setBody(modifyDS2CodeToIDFormat(process.getProcess().getDs2code(), process.getProcess().getProcessTypeDescription(), process.getProcess().getName()));
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
                ResponseEntity<DecisionCodeFileResponse> responseCodeFile = restTemplate.postForEntity(ViyaApi.createUri(baseIp, "/decisions/codeFiles?parentFolderUri=" + mapStorage.getParentFolderUri(), mapStorage.getProtocol()),
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
                    }
                }
            }
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

    private String modifyDS2CodeToIDFormat(String ds2code, String processTypeDescription, String modelName) {
        // This is not reliable as there could be a space after the semi-colon
        //int startIndex = ds2code.indexOf("package");
        //int lastIndexOf = ds2code.lastIndexOf("=yes;") + 4;
        //ds2code = ds2code.replace(ds2code.substring(startIndex, lastIndexOf), "package \"${PACKAGE_NAME}\" /inline;");

        // TODO: Use CodeRegexParser from dcm commons
        String originalPackageName = extractPackageNameFromDS2Code(ds2code);

        ds2code = ds2code.replace(originalPackageName, "\"${PACKAGE_NAME}\"");
        ds2code = ds2code.replace("/overwrite=yes", "/inline");

        if (MODEL_CONSTANT.equals(processTypeDescription)) {
            ds2code = "// Model from RTDM: " + modelName + NEW_LINE_STRING + ds2code;
        }

        ds2code = convertIntToDouble(ds2code);
        ds2code = convertTapPackages(ds2code);

        ds2code = convertExecuteListsToDatagrid(ds2code);
        int lastIndexOf = ds2code.indexOf("endpackage;") + 11;
        ds2code = ds2code.substring(0, lastIndexOf);

        return ds2code;
    }

    private String convertExecuteListsToDatagrid(String ds2code) {
        if (ds2code.contains(TAP_FLOAT_ARRAY_CONSTANT) || ds2code.contains(TAP_STRING_ARRAY_CONSTANT)
                || ds2code.contains(TAP_INT_ARRAY_CONSTANT) || ds2code.contains(TAP_DATETIME_ARRAY_CONSTANT)
                || ds2code.contains(TAP_BOOLEAN_ARRAY_CONSTANT)) {
            ds2code = ds2code.replaceAll(TAP_FLOAT_ARRAY_CONSTANT, DS2_DATAGRID);
            ds2code = ds2code.replaceAll(TAP_STRING_ARRAY_CONSTANT, DS2_DATAGRID);
            ds2code = ds2code.replaceAll(TAP_INT_ARRAY_CONSTANT, DS2_DATAGRID);
            ds2code = ds2code.replaceAll(TAP_DATETIME_ARRAY_CONSTANT, DS2_DATAGRID);
            ds2code = ds2code.replaceAll(TAP_BOOLEAN_ARRAY_CONSTANT, DS2_DATAGRID);
            int innerExecuteStartIndex = ds2code.indexOf("(") + 1;
            int innerExecuteEndIndex = ds2code.indexOf(");");
            String executeContent = ds2code.substring(innerExecuteStartIndex, innerExecuteEndIndex);
            String[] splittedString = executeContent.split(",");
            StringBuilder replacement = new StringBuilder();
            for (String substring : splittedString) {
                if (substring.contains("datagrid")) {
                    substring += "_dgo";
                }
                replacement.append(substring).append(",");
            }
            replacement = new StringBuilder(replacement.substring(0, replacement.length() - 1));
            ds2code = ds2code.replace(executeContent, replacement.toString());
        }
        return ds2code;
    }

    private String convertIntToDouble(String ds2code) {
        if (ds2code.contains(DS2_INT_CONSTANT)) {
            ds2code = ds2code.replaceAll(DS2_INT_CONSTANT, DS2_DOUBLE_CONSTANT);
        }
        return ds2code;
    }

    private String convertTapPackages(String ds2code) {
        if (ds2code.contains(PACKAGE_TAP_DATETIME_CONSTANT)) {
            ds2code = ds2code.replaceAll(PACKAGE_TAP_DATETIME_CONSTANT, DS2_DOUBLE_CONSTANT);
        }
        if (ds2code.contains(PACKAGE_TAP_HASH_CONSTANT)) {
            ds2code = ds2code.replaceAll(PACKAGE_TAP_HASH_CONSTANT, DS2_HASH_CONSTANT);
        }
        if (ds2code.contains(PACKAGE_TAP_LOGGER_CONSTANT)) {
            ds2code = ds2code.replaceAll(PACKAGE_TAP_LOGGER_CONSTANT, DS2_LOGGER_CONSTANT);
        }
        if (ds2code.contains(PACKAGE_TAP_SQLTABLE_CONSTANT)) {
            ds2code = ds2code.replaceAll(PACKAGE_TAP_SQLTABLE_CONSTANT, DS2_DATAGRID);
        }
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
        // select CLIENTID AS {:CLIENTID:decimal}, HHID AS {:HHID:decimal}, RANDNOM5000 AS {:RANDNOM5000:string:1000}
        // from "oralib"."CUSTOMER"
        // where CLIENTID={?:CLIENTID:decimal}
        StringBuilder stb = new StringBuilder("select ");

        for (ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO outputVars :
                process.getOutputVariableList().getIBVariableDOs()) {
            String datatypeOfVarForSQLNode = getDatatypeOfVarForSQLNode(outputVars.getTypeDescription());
            String var = outputVars.getName() + " AS {:" + outputVars.getName()
                    + ":" + datatypeOfVarForSQLNode + "}";
            var = modifyIfStringDatatype(var, datatypeOfVarForSQLNode);
            stb.append(var);
            stb.append(", ");
        }
        stb.delete(stb.length() - 2, stb.length());

        stb.append(" from ");
        quoteTableName(stb, process.getLibName(), process.getTableName());
        
        if (!process.getInputVariableList().getIBVariableDOs().isEmpty()) {
            stb.append(" where ");
            for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO inputVars : process.getInputVariableList().getIBVariableDOs()) {
                String datatypeOfVarForSQLNode = getDatatypeOfVarForSQLNode(inputVars.getTypeDescription());
                String var = inputVars.getName() + "={?:" + inputVars.getName() + ":"
                        + datatypeOfVarForSQLNode + "}";
                var = modifyIfStringDatatype(var, datatypeOfVarForSQLNode);
                stb.append(var);
                stb.append(" and ");
            }
            stb.delete(stb.length() - 5, stb.length());
        }

        return stb.toString();
    }

    private String buildInsertSql(ProcessNodeDataDO.Process process) {
        // Example:
        // insert into "oralib"."CUSTOMER" (CLIENTID, HHID, RANDNOM5000)
        // values ({?:CLIENTID:decimal}, {?:HHID:decimal}, {?:RANDNOM5000:string:1000})
        StringBuilder stb = new StringBuilder("insert into ");
        quoteTableName(stb, process.getLibName(), process.getTableName());

        stb.append(" (");
        if (!process.getInputVariableList().getIBVariableDOs().isEmpty()) {
            for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO inputVar : process.getInputVariableList().getIBVariableDOs()) {
                stb.append(inputVar.getName());
                stb.append(", ");
            }
            stb.delete(stb.length() - 2, stb.length());
        }
        stb.append(")");

        stb.append(" values ");
        stb.append("(");

        if (!process.getInputVariableList().getIBVariableDOs().isEmpty()) {
            for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO inputVars : process.getInputVariableList().getIBVariableDOs()) {
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
        // update "oralib"."CUSTOMER"
        // set HHID = {?:HHID:decimal}, RANDNOM5000={?:RANDNOM5000:string:1000}
        // where CLIENTID={?:CLIENTID:decimal}
        StringBuilder stb = new StringBuilder("update ");
        quoteTableName(stb, process.getLibName(), process.getTableName());

        stb.append(" set ");

        if (!process.getInputVariableList().getIBVariableDOs().isEmpty()) {
            for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO inputVars : process.getInputVariableList().getIBVariableDOs()) {
                String datatypeOfVarForSQLNode = getDatatypeOfVarForSQLNode(inputVars.getTypeDescription());
                String var = inputVars.getName() + "={?:" + inputVars.getName() + ":" + datatypeOfVarForSQLNode + "}";
                var = modifyIfStringDatatype(var, datatypeOfVarForSQLNode);
                stb.append(var);
                stb.append(", ");
            }
            stb.delete(stb.length() - 2, stb.length());
        }

        if (!process.getInputVariableList().getIBVariableDOs().isEmpty()) {
            stb.append(" where ");
            for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO inputVars : process.getInputVariableList().getIBVariableDOs()) {
                String datatypeOfVarForSQLNode = getDatatypeOfVarForSQLNode(inputVars.getTypeDescription());
                String var = inputVars.getName() + "={?:" + inputVars.getName() + ":"
                        + datatypeOfVarForSQLNode + "}";
                var = modifyIfStringDatatype(var, datatypeOfVarForSQLNode);
                stb.append(var);
                stb.append(" and ");
            }
            stb.delete(stb.length() - 5, stb.length());
        }

        return stb.toString();
    }

    private StringBuilder quoteTableName(StringBuilder stb, String libraryName, String tableName) {
        stb.append("\"").append(libraryName).append("\"")
                    .append(".").append("\"").append(tableName).append("\"");
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
        ds2Code.append(process.getName()).append(" Groovy process not supported").append(NEW_LINE_STRING);
        ds2Code.append(NEW_LINE_STRING);

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
        if (datatypeOfVar.equals(SignatureTerm.DataTypeEnum.DATAGRID)) {
            datatypeOfVarString = "package datagrid";
        } else if (datatypeOfVar.equals(SignatureTerm.DataTypeEnum.DECIMAL)
                || datatypeOfVar.equals(SignatureTerm.DataTypeEnum.INTEGER)) {
            datatypeOfVarString = DOUBLE_CONSTANT;
        } else {
            datatypeOfVarString = "varchar(100)";
        }
        if (direction.equals(OUTPUT_DIRECTION)) variable.append("in_out ");
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

    private SignatureTerm processDataProcessMapping(List<ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO> ibVariableDOList
            , Step step, Decision decision, String processName) {
        List<SignatureTermExtension> signatureTermExtensionList = new ArrayList<>();

        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();

        for (ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO ibVariableDO
                : ibVariableDOList) {
            if (!DATAGRID_CONSTANT.equals(ibVariableDO.getTypeDescription())) {
                IBVariableDO ibVariableDOInner = mapper.ibVariableDoGet(ibVariableDO);
                commonProcessing.addNewSignatureItemForDataGridExtension(ibVariableDOInner, "none", decision);
                signatureTermExtensionList.add(createDataGridExtension(ibVariableDOInner));
            }
        }
        SignatureTerm signatureTerm = new SignatureTerm();
        signatureTerm.setName(commonProcessing.sanitizeVariableName(processName + "_out"));
        signatureTerm.setDataType(SignatureTerm.DataTypeEnum.DATAGRID);
        signatureTerm.setDirection(SignatureTerm.DirectionEnum.NONE);
        signatureTerm.setDataGridExtension(signatureTermExtensionList);
        commonProcessing.checkSignatureTermForDuplicate("none", decision, signatureTerm);
        addMapping(signatureTerm, step);
        return signatureTerm;
    }

    private void addMapping(SignatureTerm signatureTerm, Step step) {
        Mapping mapping = new Mapping();
        mapping.setTargetDecisionTermName(commonProcessing.sanitizeVariableName(signatureTerm.getName()));
        mapping.setDirection(Mapping.DirectionEnum.OUTPUT);
        mapping.setStepTermName(commonProcessing.sanitizeVariableName(signatureTerm.getName().replace("_out", "_dgo")));
        commonProcessing.checkMappingForDuplicate(mapping, step);
    }

    private void processCustomObjectInputMapping(ProcessNodeDataDO.Process process, Decision decision, List<Step> stepList, Step step) {
        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();

        for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO ibVariableDO : process.getInputVariableList().getIBVariableDOs()) {
            IBVariableDO ibVariableDOMapped = mapper.ibVariableDoGet(ibVariableDO);
            String varOriginalName = ibVariableDOMapped.getName();
            if (ibVariableDOMapped.getValue() != null) {
                ValueTypeVarInfoDO valueTypeVarInfoDO = ibVariableDOMapped.getValue().getValueTypeVarInfoDO();
                String varName = ibVariableDO.getName();
                final String varInfo = valueTypeVarInfoDO != null ? valueTypeVarInfoDO.getVarInfoId() : "";
                if (!commonProcessing.checkForCalcVariable(varInfo,ibVariableDOMapped.getTypeDescription(), stepList, step, varOriginalName)) {
                    if (ibVariableDO.getTypeDescription().contains("list")) {
                        List<SignatureTermExtension> signatureTermExtensionList = new ArrayList<>();

                        IBVariableDO ibVariableDOInner = mapper.ibVariableDoGet(ibVariableDO);
                        commonProcessing.addNewSignatureItem(ibVariableDOInner, "none", decision, null);
                        signatureTermExtensionList.add(createDataGridExtension(ibVariableDOInner));

                        SignatureTerm signatureTerm = new SignatureTerm();
                        signatureTerm.setName(commonProcessing.sanitizeVariableName(ibVariableDO.getName() + "_dgo"));
                        signatureTerm.setDataType(SignatureTerm.DataTypeEnum.DATAGRID);
                        signatureTerm.setDirection(SignatureTerm.DirectionEnum.NONE);
                        signatureTerm.setDataGridExtension(signatureTermExtensionList);
                        commonProcessing.checkSignatureTermForDuplicate("none", decision, signatureTerm);
                        addMapping(signatureTerm, step);
                    } else {
                        commonProcessing.addMapping(ibVariableDOMapped, INPUT_DIRECTION, step, false, decision);
                        commonProcessing.addNewSignatureItem(ibVariableDOMapped, "none", decision, null);
                    }
                }
                if (valueTypeVarInfoDO != null) {
                    VarRef varRef = VarRefMapper.VAR_REF_MAPPER.varRefGet(valueTypeVarInfoDO);
                    commonProcessing.checkForAdditionalVariables(varName, varRef, "none", decision);
                }
            } else {
                // Even if no value assigned we need the mappings and signature items
                if (ibVariableDO.getTypeDescription().contains("list")) {
                    List<SignatureTermExtension> signatureTermExtensionList = new ArrayList<>();

                    IBVariableDO ibVariableDOInner = mapper.ibVariableDoGet(ibVariableDO);
                    commonProcessing.addNewSignatureItem(ibVariableDOInner, "none", decision, null);
                    signatureTermExtensionList.add(createDataGridExtension(ibVariableDOInner));

                    SignatureTerm signatureTerm = new SignatureTerm();
                    signatureTerm.setName(commonProcessing.sanitizeVariableName(ibVariableDO.getName() + "_dgo"));
                    signatureTerm.setDataType(SignatureTerm.DataTypeEnum.DATAGRID);
                    signatureTerm.setDirection(SignatureTerm.DirectionEnum.NONE);
                    signatureTerm.setDataGridExtension(signatureTermExtensionList);
                    commonProcessing.checkSignatureTermForDuplicate("none", decision, signatureTerm);
                    addMapping(signatureTerm, step);
                } else {
                    commonProcessing.addMapping(ibVariableDOMapped, INPUT_DIRECTION, step, false, decision);
                    commonProcessing.addNewSignatureItem(ibVariableDOMapped, "none", decision, null);
                }
            }
        }
    }

    private void processCustomObjectMapping(ProcessNodeDataDO.Process process, Step step, Decision decision) {
        List<ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO> ibVariableDOList = process.getOutputVariableList().getIBVariableDOs();

        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();

        for (ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO ibVariableDO : ibVariableDOList) {
            if (ibVariableDO.getTypeDescription().contains("list")) {
                List<SignatureTermExtension> signatureTermExtensionList = new ArrayList<>();

                IBVariableDO ibVariableDOInner = mapper.ibVariableDoGet(ibVariableDO);
                commonProcessing.addNewSignatureItem(ibVariableDOInner, "none", decision, null);
                signatureTermExtensionList.add(createDataGridExtension(ibVariableDOInner));

                SignatureTerm signatureTerm = new SignatureTerm();
                signatureTerm.setName(commonProcessing.sanitizeVariableName(ibVariableDO.getName() + "_dgo"));
                signatureTerm.setDataType(SignatureTerm.DataTypeEnum.DATAGRID);
                signatureTerm.setDirection(SignatureTerm.DirectionEnum.NONE);
                signatureTerm.setDataGridExtension(signatureTermExtensionList);
                commonProcessing.checkSignatureTermForDuplicate("none", decision, signatureTerm);
                addMapping(signatureTerm, step);
            } else {
                IBVariableDO ibVariableDOInner = mapper.ibVariableDoGet(ibVariableDO);
                if (commonProcessing.isProcessDataProcess(process.getProcessTypeDescription()) && !isDataProcessReturnDataGrid(process)) {
                    commonProcessing.addOutputMappingForProcess(ibVariableDOInner, OUTPUT_DIRECTION, step, false, decision);
                } else {
                    commonProcessing.addMapping(ibVariableDOInner, OUTPUT_DIRECTION, step, false, decision);
                }
                commonProcessing.addNewSignatureItem(ibVariableDOInner, "none", decision, null);
            }
        }
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
        if (type.equals(SignatureTerm.DataTypeEnum.DECIMAL)
                || type.equals(SignatureTerm.DataTypeEnum.INTEGER)) {
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

    private static String extractPackageNameFromDS2Code(String ds2Code)
    {
        //Add a new line in case the code starts with package on the first line.
        Matcher m = packageNamePattern.matcher("\n"+ds2Code);
        if (m.find())
        {
            return(m.group(2));
        }
        return null;
    }
}
