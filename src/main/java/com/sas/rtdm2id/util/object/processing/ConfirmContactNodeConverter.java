package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.*;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.rtdm.CHandRHNodeDataDO;
import com.sas.rtdm2id.model.rtdm.extension.IBVariableDO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConfirmContactNodeConverter {
    
    private final MapStorage mapStorage;

    private final CommonProcessing commonProcessing;

    public ConfirmContactNodeConverter(MapStorage mapStorage, CommonProcessing commonProcessing) {
        this.mapStorage = mapStorage;
        this.commonProcessing = commonProcessing;
    }

    public Step createRecordContactStep(CHandRHNodeDataDO cHandRHNodeDataDO, Decision decision) {
        Step step = new Step();
        step.type(Step.TypeEnum.RECORD_CONTACT);
        final RecordContactStep recordContactStep = new RecordContactStep();
        recordContactStep.setName(cHandRHNodeDataDO.getNodeName());

        final List<AuditTerm> auditTerms = cHandRHNodeDataDO.getVariables().getIBVariableDO()
                .stream()
                .map(ibVariableDO -> new AuditTerm(commonProcessing.sanitizeVariableName(ibVariableDO.getName())))
                .collect(Collectors.toList());

        recordContactStep.setAuditTerms(auditTerms);
        step.setRecordContact(recordContactStep);

        return step;
    }

    public Step addCustomObjectStep(CHandRHNodeDataDO cHandRHNodeDataDO, Decision decision, Short objId, String nodeName) {
        Step inputValuesStep = mapStorage.getRuleSetStepMap().get(nodeName + "_input_values");
        if (inputValuesStep != null) {
            List<IBVariableDO> ibVariableDOs = cHandRHNodeDataDO.getVariables().getIBVariableDO();
            if (ibVariableDOs != null && !ibVariableDOs.isEmpty()) {
                updateRuleSet(ibVariableDOs, inputValuesStep, decision);
            }
        }

        return inputValuesStep;
    }

    public void updateRuleSet(List<IBVariableDO> ibVariableDOs, Step step, Decision decision) {
        RuleSet ruleSet = new RuleSet();
        RuleSetStep ruleSetForUpdate = step.getRuleSet();
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
