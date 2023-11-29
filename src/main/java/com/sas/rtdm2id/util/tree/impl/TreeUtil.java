/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.tree.impl;

import com.sas.rtdm2id.model.rtdm.*;
import com.sas.rtdm2id.model.rtdm.extension.SubDiagramNodeDataDO;
import com.sas.rtdm2id.util.model.RTDMObject;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TreeUtil {
    private final Map<Short, String> objIdOutputNodesMap = new HashMap<>();
    private final Map<Short, String> objIdToNodeIdMap = new HashMap<>();
    private final Map<String, RTDMObject> nodeIdObjectHashMap = new HashMap<>();
    private final Map<String, Short> nodeIdObjIdMap = new HashMap<>();

    public GenericTree<Short> createTree(Batch.LogicalUnit logicalUnit) {
        StartNodeDataDO startNodeDataDO = logicalUnit.getStartNodeDataDO();
        Short rootObjId = startNodeDataDO.getObjid();
        objIdOutputNodesMap.put(rootObjId, startNodeDataDO.getOutputNodes().getValue());
        nodeIdObjIdMap.put(startNodeDataDO.getNodeId(), rootObjId);
        objIdToNodeIdMap.put(rootObjId, startNodeDataDO.getNodeId());
        nodeIdObjectHashMap.put(startNodeDataDO.getNodeId(), new RTDMObject("start", startNodeDataDO));
        for (ReplyNodeDataDO replyNodeDataDO : logicalUnit.getReplyNodeDataDOs()) {
            objIdOutputNodesMap.put(replyNodeDataDO.getObjid(), replyNodeDataDO.getOutputNodes().getValue());
            nodeIdObjIdMap.put(replyNodeDataDO.getNodeId(), replyNodeDataDO.getObjid());
            nodeIdObjectHashMap.put(replyNodeDataDO.getNodeId(), new RTDMObject("reply", replyNodeDataDO));
            objIdToNodeIdMap.put(replyNodeDataDO.getObjid(), replyNodeDataDO.getNodeId());
        }
        for (SplitNodeDataDO splitNodeDataDO : logicalUnit.getSplitNodeDataDOs()) {
            objIdOutputNodesMap.put(splitNodeDataDO.getObjid(), splitNodeDataDO.getOutputNodes().getValue());
            nodeIdObjIdMap.put(splitNodeDataDO.getNodeId(), splitNodeDataDO.getObjid());
            nodeIdObjectHashMap.put(splitNodeDataDO.getNodeId(), new RTDMObject("split", splitNodeDataDO));
            objIdToNodeIdMap.put(splitNodeDataDO.getObjid(), splitNodeDataDO.getNodeId());
        }
        for (CellNodeDataDO cellNodeDataDO : logicalUnit.getCellNodeDataDOs()) {
            objIdOutputNodesMap.put(cellNodeDataDO.getObjid(), cellNodeDataDO.getOutputNodes().getValue());
            nodeIdObjIdMap.put(cellNodeDataDO.getNodeId(), cellNodeDataDO.getObjid());
            nodeIdObjectHashMap.put(cellNodeDataDO.getNodeId(), new RTDMObject("cell", cellNodeDataDO));
            objIdToNodeIdMap.put(cellNodeDataDO.getObjid(), cellNodeDataDO.getNodeId());
        }
        for (MultiSelectNodeDataDO multiSelectNodeDataDO : logicalUnit.getMultiSelectNodeDataDOs()) {
            objIdOutputNodesMap.put(multiSelectNodeDataDO.getObjid(), multiSelectNodeDataDO.getOutputNodes().getValue());
            nodeIdObjIdMap.put(multiSelectNodeDataDO.getNodeId(), multiSelectNodeDataDO.getObjid());
            nodeIdObjectHashMap.put(multiSelectNodeDataDO.getNodeId(), new RTDMObject("multiSelect", multiSelectNodeDataDO));
            objIdToNodeIdMap.put(multiSelectNodeDataDO.getObjid(), multiSelectNodeDataDO.getNodeId());
        }
        for (ProcessNodeDataDO processNodeDataDO : logicalUnit.getProcessNodeDataDOs()) {
            objIdOutputNodesMap.put(processNodeDataDO.getObjid(), processNodeDataDO.getOutputNodes().getValue());
            nodeIdObjIdMap.put(processNodeDataDO.getNodeId(), processNodeDataDO.getObjid());
            nodeIdObjectHashMap.put(processNodeDataDO.getNodeId(), new RTDMObject("custom", processNodeDataDO));
            objIdToNodeIdMap.put(processNodeDataDO.getObjid(), processNodeDataDO.getNodeId());
        }
        for (SubDiagramNodeDataDO subDiagramNodeDataDO : logicalUnit.getSubDiagramNodeDataDOs()) {
            objIdOutputNodesMap.put(subDiagramNodeDataDO.getObjid(), subDiagramNodeDataDO.getOutputNodes().getValue());
            nodeIdObjIdMap.put(subDiagramNodeDataDO.getNodeId(), subDiagramNodeDataDO.getObjid());
            nodeIdObjectHashMap.put(subDiagramNodeDataDO.getNodeId(), new RTDMObject("subdiagram", subDiagramNodeDataDO));
            objIdToNodeIdMap.put(subDiagramNodeDataDO.getObjid(), subDiagramNodeDataDO.getNodeId());
        }

        // Handle unsupported nodes
        for (AssignmentNodeDataDO assignmentNodeDataDO : logicalUnit.getAssignmentNodeDataDOs()) {
            objIdOutputNodesMap.put(assignmentNodeDataDO.getObjid(), assignmentNodeDataDO.getOutputNodes().getValue());
            nodeIdObjIdMap.put(assignmentNodeDataDO.getNodeId(), assignmentNodeDataDO.getObjid());
            nodeIdObjectHashMap.put(assignmentNodeDataDO.getNodeId(), new RTDMObject("assignment", assignmentNodeDataDO));
            objIdToNodeIdMap.put(assignmentNodeDataDO.getObjid(), assignmentNodeDataDO.getNodeId());
        }

        for (CHandRHNodeDataDO cHandRHNodeDataDO : logicalUnit.getCHandRHNodeDataDOs()) {
            objIdOutputNodesMap.put(cHandRHNodeDataDO.getObjid(), cHandRHNodeDataDO.getOutputNodes().getValue());
            nodeIdObjIdMap.put(cHandRHNodeDataDO.getNodeId(), cHandRHNodeDataDO.getObjid());
            nodeIdObjectHashMap.put(cHandRHNodeDataDO.getNodeId(), new RTDMObject("cHandRH", cHandRHNodeDataDO));
            objIdToNodeIdMap.put(cHandRHNodeDataDO.getObjid(), cHandRHNodeDataDO.getNodeId());
        }

        // TODO - refactor so not duplicating code
        for (StagedTreatmentsNodeDataDO stagedTreatmentsNodeDataDO : logicalUnit.getAddStagedTreatmentsNodeDataDOs()) {
            objIdOutputNodesMap.put(stagedTreatmentsNodeDataDO.getObjid(), stagedTreatmentsNodeDataDO.getOutputNodes().getValue());
            nodeIdObjIdMap.put(stagedTreatmentsNodeDataDO.getNodeId(), stagedTreatmentsNodeDataDO.getObjid());
            nodeIdObjectHashMap.put(stagedTreatmentsNodeDataDO.getNodeId(), new RTDMObject("stagedTreatment", stagedTreatmentsNodeDataDO));
            objIdToNodeIdMap.put(stagedTreatmentsNodeDataDO.getObjid(), stagedTreatmentsNodeDataDO.getNodeId());
        }

        for (RemoveStagedTreatmentsNodeDataDO removeStagedTreatmentsNodeDataDO : logicalUnit.getRemoveStagedTreatmentsNodeDataDOs()) {
            objIdOutputNodesMap.put(removeStagedTreatmentsNodeDataDO.getObjid(), removeStagedTreatmentsNodeDataDO.getOutputNodes().getValue());
            nodeIdObjIdMap.put(removeStagedTreatmentsNodeDataDO.getNodeId(), removeStagedTreatmentsNodeDataDO.getObjid());
            nodeIdObjectHashMap.put(removeStagedTreatmentsNodeDataDO.getNodeId(), new RTDMObject("removeStagedTreatment", removeStagedTreatmentsNodeDataDO));
            objIdToNodeIdMap.put(removeStagedTreatmentsNodeDataDO.getObjid(), removeStagedTreatmentsNodeDataDO.getNodeId());
        }

        return generateTree(objIdOutputNodesMap, nodeIdObjIdMap, rootObjId);
    }

    private GenericTree<Short> generateTree(Map<Short, String> objIdNextNodesMap, Map<String, Short> nodeIdObjIdMap, Short rootObjId) {
        GenericTree<Short> tree = new GenericTree<>();
        GenericTreeNode<Short> root = new GenericTreeNode<>();
        root.setData(rootObjId);
        String outPutNodes = objIdNextNodesMap.get(rootObjId);
        List<String> nodes = Arrays.asList(outPutNodes.split("\\s*,\\s*"));
        addChildToTree(nodes, objIdNextNodesMap, nodeIdObjIdMap, root);
        tree.setRoot(root);
        return tree;
    }

    private void addChildToTree(List<String> nodes, Map<Short, String> objIdNextNodesMap,
                                Map<String, Short> nodeIdObjIdMap, GenericTreeNode<Short> root) {
        for (String node : nodes) {
            Short objId = nodeIdObjIdMap.get(node);
            GenericTreeNode<Short> child = new GenericTreeNode<>(objId);
            root.addChild(child);
            String outPutNodes = objIdNextNodesMap.get(objId);
            if (outPutNodes!=null && !outPutNodes.isEmpty()) {
                List<String> innerNodes = Arrays.asList(outPutNodes.split("\\s*,\\s*"));
                addChildToTree(innerNodes, objIdNextNodesMap, nodeIdObjIdMap, child);
            }
        }
    }

    public Map<Short, String> getObjIdOutputNodesMap() {
        return objIdOutputNodesMap;
    }

    public Map<Short, String> getObjIdToNodeIdMap() {
        return objIdToNodeIdMap;
    }

    public Map<String, RTDMObject> getNodeIdObjectHashMap() {
        return nodeIdObjectHashMap;
    }

    public RTDMObject getRTDMObjectByObjId(Short objId) {
        String nodeId = objIdToNodeIdMap.get(objId);
        return nodeIdObjectHashMap.get(nodeId);
    }

    public Map<String, Short> getNodeIdObjIdMap() {
        return nodeIdObjIdMap;
    }

    public void clearMaps() {
        nodeIdObjIdMap.clear();
        objIdOutputNodesMap.clear();
        nodeIdObjectHashMap.clear();
        objIdToNodeIdMap.clear();
    }
}
