package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.*;
import com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
// Used for both Add and Remove Staged Treatment nodes
public class StagedTreatmentConverter {

    private final MapStorage mapStorage;
    private final CommonProcessing commonProcessing;
    private final TreeUtil treeUtil;

    @Autowired
    public StagedTreatmentConverter(MapStorage mapStorage, CommonProcessing commonProcessing, TreeUtil treeUtil) {
        this.mapStorage = mapStorage;
        this.commonProcessing = commonProcessing;
        this.treeUtil = treeUtil;
    }

    public StagedTreatmentConverter(MapStorage mapStorage, CommonProcessing commonProcessing) {
        this(mapStorage, commonProcessing, null);
    }

    public List<Step> addCustomObjectStep(ProcessNodeDataDO.Process process, Decision decision, Short objId,
            String nodeName) {
        String nodeId = treeUtil == null ? null : treeUtil.getObjIdToNodeIdMap().get(objId);
        return addCustomObjectStep(process, decision, objId, nodeName, nodeId);
    }

    public List<Step> addCustomObjectStep(ProcessNodeDataDO.Process process, Decision decision, Short objId,
            String nodeName, String nodeId) {
        // No _input_values RuleSet is created for staged treatment nodes (see CommonProcessing.createRuleSets), so the
        // placeholder code file step is the only step.
        List<Step> stepList = new LinkedList<>();
        Step step = new Step();
        step.setType(Step.TypeEnum.CUSTOM_OBJECT);
        step.setCustomObject(mapStorage.getCustomObjectStepHashMap().get(objId));
        if (nodeId != null) {
            // Registers a cross-branch link label when the node has more than one input node
            commonProcessing.addStep(stepList, step, nodeId);
        } else {
            stepList.add(step);
        }
        return stepList;
    }

    // TODO: Add and Remove Staged Treatment nodes are currently placeholder nodes.
    // If in the future we convert to a real node, we will need to implement the
    // methods below.
}
