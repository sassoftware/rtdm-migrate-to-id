/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.mapper.IBVariableDOMapperImpl;
import com.sas.rtdm2id.mapper.VarRefMapper;
import com.sas.rtdm2id.model.id.decision.*;
import com.sas.rtdm2id.model.id.rules.Action;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.id.rules.Signature;
import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO;
import com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO;
import com.sas.rtdm2id.model.rtdm.extension.SubDiagramNodeDataDO;
import com.sas.rtdm2id.model.rtdm.extension.SubdiagramEvent;
import com.sas.rtdm2id.model.rtdm.extension.VarRef;
import com.sas.rtdm2id.util.ViyaApi;
import com.sas.rtdm2id.util.model.IBVariableDO;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.INPUT_DIRECTION;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.OUTPUT_DIRECTION;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.IN_OUT_DIRECTION;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.SOURCE_GLOBAL;

@Component
public class SubDiagramNodeConverter {
    private final CommonProcessing commonProcessing;
    private final RestTemplate restTemplate;
    private final MapStorage mapStorage;

    public SubDiagramNodeConverter(CommonProcessing commonProcessing, RestTemplate restTemplate, MapStorage mapStorage) {
        this.commonProcessing = commonProcessing;
        this.restTemplate = restTemplate;
        this.mapStorage = mapStorage;
    }

    public void getSubdiagrams(Batch batch) {
        try {
            for (SubDiagramNodeDataDO subDiagramNodeDataDO : batch.getLogicalUnit().getSubDiagramNodeDataDOs()) {
                CustomObjectStep codeStep = new CustomObjectStep();
                DecisionCollection decisionCollection
                        = getDecisionCollection(mapStorage.getBaseIp(), subDiagramNodeDataDO.getCampaignId(), mapStorage.getAccessToken(),mapStorage.getProtocol());
                if (decisionCollection != null && decisionCollection.getCount() != 0) {
                    Decision2 decisionCodeFile = decisionCollection.getItems().get(0);
                    DecisionCollection decisionCollectionRevision
                            = getDecisionCollectionRevision(mapStorage.getBaseIp(), decisionCodeFile.getId(), mapStorage.getAccessToken(), subDiagramNodeDataDO.getCampaignId(), mapStorage.getProtocol());
                    assert decisionCollectionRevision != null;
                    Decision2 decisionCodeFileRevision = decisionCollectionRevision.getItems().get(0);
                    codeStep.setUri("/decisions/flows/" + decisionCodeFile.getId() + "/revisions/" + decisionCodeFileRevision.getId());
                    codeStep.setName(decisionCodeFile.getName());
                    codeStep.setType("decision");
                    mapStorage.getCustomObjectStepHashMap().put(subDiagramNodeDataDO.getObjid(), codeStep);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private DecisionCollection getDecisionCollection(String baseIp, String id, String accessToken, String protocol) throws URISyntaxException {
        ResponseEntity<DecisionCollection> getResponse = restTemplate.exchange(
                ViyaApi.createUriWithParams(baseIp, "/decisions/flows", "description", id, protocol)
                , HttpMethod.GET,
                ViyaApi.createGetDecisionCollectionOrGlobalVariableRevision(accessToken), DecisionCollection.class);
        if (getResponse.getStatusCode().value() == 200) {
            return getResponse.getBody();
        }
        return null;
    }

    private DecisionCollection getDecisionCollectionRevision(String baseIp, String id, String accessToken, String campaignId, String protocol) throws URISyntaxException {
        ResponseEntity<DecisionCollection> getResponse = restTemplate.exchange(
                ViyaApi.createUriWithParams(baseIp, "/decisions/flows/" + id + "/revisions"
                        , "description", campaignId, protocol)
                , HttpMethod.GET,
                ViyaApi.createGetDecisionCollectionOrGlobalVariableRevision(accessToken), DecisionCollection.class);
        if (getResponse.getStatusCode().value() == 200) {
            return getResponse.getBody();
        }
        return null;
    }

    public List<Step> createSubDiagram(SubDiagramNodeDataDO subDiagramNodeDataDO, Decision decision) {
        List<Step> stepList = new ArrayList<>();
        Step step = new Step();
        step.setType(Step.TypeEnum.CUSTOM_OBJECT);
        step.setCustomObject(mapStorage.getCustomObjectStepHashMap().get(subDiagramNodeDataDO.getObjid()));

        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();

        for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO ibVariableDO
                : subDiagramNodeDataDO.getSubdiagramEvent().getRequestVars().getIBVariableDOs()) {
            IBVariableDO ibVariableDOInner = mapper.ibVariableDoGet(ibVariableDO);
            
            ValueTypeVarInfoDO valueTypeVarInfoDO = ibVariableDOInner.getValue().getValueTypeVarInfoDO();
            final String varInfoSource = valueTypeVarInfoDO != null ? valueTypeVarInfoDO.getVarInfoSource() : "";
            if (SOURCE_GLOBAL.equals(varInfoSource)) {
                commonProcessing.addGlobalVariableMappingForSubDecision(ibVariableDOInner, INPUT_DIRECTION, step, decision);
            } else {
                commonProcessing.addMapping(ibVariableDOInner, INPUT_DIRECTION, step, false, decision);
            }
            commonProcessing.addNewSignatureItem(ibVariableDOInner, "none", decision, null);
        }
        for (ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO ibVariableDO
                : subDiagramNodeDataDO.getSubdiagramEvent().getReplyVars().getIBVariableDOs()) {
            IBVariableDO ibVariableDOInner = mapper.ibVariableDoGet(ibVariableDO);
            commonProcessing.addMapping(ibVariableDOInner, OUTPUT_DIRECTION, step, false, decision);
            commonProcessing.addNewSignatureItem(ibVariableDOInner, "none", decision, null);
        }
        stepList.add(step);
        return stepList;
    }

    public List<Step> addCustomObjectStep(SubDiagramNodeDataDO subDiagramNodeDataDO, Decision decision, Short objId, String nodeName) {
        List<Step> stepList = new LinkedList<>();
        Step step = new Step();
        step.setType(Step.TypeEnum.CUSTOM_OBJECT);
        Step inputValuesStep = mapStorage.getRuleSetStepMap().get(nodeName + "_input_values");
        if (inputValuesStep!=null) {
            // Unsupported nodes are converted into DS2 code files but we don't create a ruleset node to
            // represent the custom node inputs so inputValuesStep is null in this scenario
            stepList.add(inputValuesStep);
        }

        SubdiagramEvent subdiagramEvent = subDiagramNodeDataDO.getSubdiagramEvent();
        if (subdiagramEvent.getRequestVars()!=null) {
            processCustomObjectInputMapping(subdiagramEvent, decision, stepList, step);
            updateRuleSet(subdiagramEvent.getRequestVars(), inputValuesStep, decision);
        }

        step.setCustomObject(mapStorage.getCustomObjectStepHashMap().get(objId));
        stepList.add(step);
        return stepList;
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
            for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO ibVariableDO : ibVariableDOs) {
                Signature signature = new Signature();
                signature.setDataType(commonProcessing.getDatatypeOfVar(ibVariableDO.getTypeDescription()).getValue());

                signature.setName(commonProcessing.sanitizeVariableName(ibVariableDO.getName()));
                signature.setDirection(IN_OUT_DIRECTION);
                if (!signatureList.contains(signature)) {
                    signatureList.add(signature);
                }

                if (!(mapStorage.getRuleSetIsActionUpdated().get(ruleSetForUpdate.getName()) &&
                        mapStorage.getRuleSetIsUpdated().get(ruleSetForUpdate.getName()))) {
                    ruleSet.setName(ruleSetForUpdate.getName());
                    rule.setName(UUID.randomUUID().toString());
                    rule.setConditional("if");
                    rule.setRuleFiredTrackingEnabled(false);

                    commonProcessing.addAction(mapper.ibVariableDoGet(ibVariableDO), actionList, signatureList, step, decision);
                    rule.setActions(actionList);
                }

            }
            ruleSet.setSignature(signatureList);

            commonProcessing.callPutRuleSet(ruleSetForUpdate, ruleSet);
            commonProcessing.addActionToRuleSet(ruleSetForUpdate, rule);
        }
    }

    private void processCustomObjectInputMapping(SubdiagramEvent subdiagramEvent, Decision decision, List<Step> stepList, Step step) {
        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();

        for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO ibVariableDO : subdiagramEvent.getRequestVars().getIBVariableDOs()) {
            IBVariableDO ibVariableDOMapped = mapper.ibVariableDoGet(ibVariableDO);
            String varOriginalName = ibVariableDOMapped.getName();
            if (ibVariableDOMapped.getValue() != null) {
                ValueTypeVarInfoDO valueTypeVarInfoDO = ibVariableDOMapped.getValue().getValueTypeVarInfoDO();
                String varName = ibVariableDO.getName();
                final String varInfo = valueTypeVarInfoDO != null ? valueTypeVarInfoDO.getVarInfoId() : "";
                if (!commonProcessing.checkForCalcVariable(varInfo,ibVariableDOMapped.getTypeDescription(), stepList, step, varOriginalName)) {
                    final String varInfoSource = valueTypeVarInfoDO != null ? valueTypeVarInfoDO.getVarInfoSource() : "";
                    if (SOURCE_GLOBAL.equals(varInfoSource)) {
                        commonProcessing.addGlobalVariableMappingForSubDecision(ibVariableDOMapped, INPUT_DIRECTION, step, decision);
                    } else {
                        commonProcessing.addMapping(ibVariableDOMapped, INPUT_DIRECTION, step, false, decision);
                    }
                    commonProcessing.addNewSignatureItem(ibVariableDOMapped, "none", decision, null);
                }
                if (valueTypeVarInfoDO != null) {
                    VarRef varRef = VarRefMapper.VAR_REF_MAPPER.varRefGet(valueTypeVarInfoDO);
                    commonProcessing.checkForAdditionalVariables(varName, varRef, "none", decision);
                }
            }
        }
    }
}
