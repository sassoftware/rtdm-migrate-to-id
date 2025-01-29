package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.*;
import com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
// Used for both Add and Remove Staged Treatment nodes
public class StagedTreatmentConverter {

    private final MapStorage mapStorage;

    public StagedTreatmentConverter(MapStorage mapStorage, CommonProcessing commonProcessing) {
        this.mapStorage = mapStorage;
    }

    public List<Step> addCustomObjectStep(ProcessNodeDataDO.Process process, Decision decision, Short objId,
            String nodeName) {
        List<Step> stepList = new LinkedList<>();
        Step step = new Step();
        step.setType(Step.TypeEnum.CUSTOM_OBJECT);
        Step inputValuesStep = mapStorage.getRuleSetStepMap().get(nodeName + "_input_values");
        if (inputValuesStep != null) {
            stepList.add(inputValuesStep);
        }

        step.setCustomObject(mapStorage.getCustomObjectStepHashMap().get(objId));
        stepList.add(step);
        return stepList;
    }

    // TODO: Add and Remove Staged Treatment nodes are currently placeholder nodes.
    // If in the future we convert to a real node, we will need to implement the
    // methods below.
}
