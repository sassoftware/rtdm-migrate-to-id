/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.RuleSetStep;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.rtdm.AssignmentNodeDataDO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssignmentNodeConverter {
    private final CommonProcessing commonProcessing;
    private final MapStorage mapStorage;

    public AssignmentNodeConverter(CommonProcessing commonProcessing, MapStorage mapStorage) {
        this.commonProcessing = commonProcessing;
        this.mapStorage = mapStorage;
    }

    public List<Step> createAssignment(AssignmentNodeDataDO assignmentNodeDataDO, Decision decision, Short objId) {
        List<Step> stepList = new ArrayList<>();
        Step step = mapStorage.getRuleSetStepMap().get(commonProcessing.makeAssignmentNodeRuleSetName(assignmentNodeDataDO));
        assignmentNodeDataDO.getAssignments().getIBVariableDOs().forEach(variable->{
           String varOriginalName = variable.getName();
           if (variable.getValue() != null){
              com.sas.rtdm2id.model.rtdm.extension.ValueTypeVarInfoDO valueTypeVarInfoDO =  variable.getValue().getValueTypeVarInfoDO();
              final String varInfo = valueTypeVarInfoDO != null ? valueTypeVarInfoDO.getVarInfoId() : "";
              commonProcessing.checkForCalcVariable(varInfo, variable.getTypeDescription(), stepList, step, varOriginalName);
           }
        });
        updateRuleSet(assignmentNodeDataDO, step, decision);
        stepList.add(step);
        return stepList;
    }

    public void updateRuleSet(AssignmentNodeDataDO assignmentNodeDataDO, Step step, Decision decision) {
        RuleSet ruleSet = new RuleSet();
        RuleSetStep ruleSetForUpdate = step.getRuleSet();
        List<com.sas.rtdm2id.model.rtdm.extension.IBVariableDO> ibVariableDOs = assignmentNodeDataDO.getAssignments().getIBVariableDOs();
        if (ibVariableDOs != null && !ibVariableDOs.isEmpty()) {
            commonProcessing.processAssignmentVariables(step, decision, ruleSet, ruleSetForUpdate, ibVariableDOs);
        }
        if (!(mapStorage.getRuleSetIsActionUpdated().get(ruleSetForUpdate.getName()) &&
        mapStorage.getRuleSetIsUpdated().get(ruleSetForUpdate.getName()))) {
           ruleSet.setName(commonProcessing.changeVariableNameToFormat(ruleSetForUpdate.getName()));
           commonProcessing.callPutRuleSet(ruleSetForUpdate, ruleSet);
        }

    }
}