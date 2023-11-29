/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.mapper.IBVariableDOMapperImpl;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.RuleSetStep;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO;
import com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO.Value.ValueTypeVarInfoDO;
import com.sas.rtdm2id.util.model.IBVariableDO;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ReplyNodeConverter {
    private final CommonProcessing commonProcessing;
    private final MapStorage mapStorage;

    public ReplyNodeConverter(CommonProcessing commonProcessing, MapStorage mapStorage) {
        this.commonProcessing = commonProcessing;
        this.mapStorage = mapStorage;
    }

    public List<Step> createReply(ReplyNodeDataDO replyNodeDataDO, Decision decision) {
        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();
        List<Step> stepList = new ArrayList<>();
        Step step = mapStorage.getRuleSetStepMap().get(commonProcessing.makeReplyNodeRuleSetName(replyNodeDataDO));
        replyNodeDataDO.getEventReply().getReplyVars().getIBVariableDOs().forEach(variable->{
           IBVariableDO ibVariableDOMapped = mapper.ibVariableDoGet(variable);
           String varOriginalName = ibVariableDOMapped.getName();
           if (variable.getValue() != null) {
              ValueTypeVarInfoDO valueTypeVarInfoDO =  variable.getValue().getValueTypeVarInfoDO();
              final String varInfo = valueTypeVarInfoDO != null ? valueTypeVarInfoDO.getVarInfoId() : "";
              commonProcessing.checkForCalcVariable(varInfo, ibVariableDOMapped.getTypeDescription(), stepList, step, varOriginalName);
           }
        });
        updateRuleSet(replyNodeDataDO, step, decision);
        stepList.add(step);
        return stepList;
    }

    public void updateRuleSet(ReplyNodeDataDO replyNodeDataDO, Step step, Decision decision) {
        RuleSet ruleSet = new RuleSet();
        Rule rule = new Rule();
        RuleSetStep ruleSetForUpdate = step.getRuleSet();

        List<ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO> ibVariableDOs = getIbVariableDOS(replyNodeDataDO);
        if (ibVariableDOs != null && !ibVariableDOs.isEmpty()) {
            commonProcessing.processVariableToCreateRuleSet(step, decision, ruleSet, rule, ibVariableDOs);
        }

        if (!(mapStorage.getRuleSetIsActionUpdated().get(ruleSetForUpdate.getName()) && mapStorage.getRuleSetIsUpdated().get(ruleSetForUpdate.getName()))) {
            ruleSet.setName(commonProcessing.changeVariableNameToFormat(ruleSetForUpdate.getName()));
            rule.setName(UUID.randomUUID().toString());
            rule.setConditional("if");
            rule.setRuleFiredTrackingEnabled(false);

            commonProcessing.callPutRuleSet(ruleSetForUpdate, ruleSet);
            commonProcessing.addActionToRuleSet(ruleSetForUpdate, rule);
        }

    }

    private List<ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO> getIbVariableDOS(ReplyNodeDataDO replyNodeDataDO) {
        return replyNodeDataDO.getEventReply() != null ? replyNodeDataDO.getEventReply().getReplyVars().getIBVariableDOs()
                : replyNodeDataDO.getTreatmentReplyVariables().getIBVariableDOs();
    }
}
