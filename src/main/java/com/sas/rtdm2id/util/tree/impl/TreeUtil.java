/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.tree.impl;

import com.sas.rtdm2id.model.rtdm.*;
import com.sas.rtdm2id.model.rtdm.extension.SubDiagramNodeDataDO;
import com.sas.rtdm2id.util.model.RTDMObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TreeUtil {
    private static final String NODE_LIST_SEPARATOR = ",";

    private final Map<Short, String> objIdOutputNodesMap = new HashMap<>();
    private final Map<Short, String> objIdToNodeIdMap = new HashMap<>();
    private final Map<String, RTDMObject> nodeIdObjectHashMap = new HashMap<>();
    private final Map<String, Short> nodeIdObjIdMap = new HashMap<>();
    private final Map<String, String> nodeIdInputNodesMap = new HashMap<>();

    /**
     * Indexes every node of the logical unit and builds the diagram structure rooted at the Start node.
     * <p>
     * The result is a directed acyclic graph rather than a strict tree: a node with several input nodes (fan-in)
     * is represented by a single {@link GenericTreeNode} shared by all of its parents, so the structure stays
     * linear in the size of the RTDM diagram. ({@link GenericTreeNode#getParent()} of a shared node is the last
     * parent it was attached to.)
     * <p>
     * InputNodes/OutputNodes lists are stored normalized: entries are trimmed, empty entries are dropped and the
     * remaining ids are joined with a plain comma, so splitting them with {@code RTDM2IDConstants.REGEXP} is safe.
     *
     * @throws IllegalArgumentException if a node reachable from Start references an unknown output node or the
     *                                  diagram contains a cycle
     */
    public GenericTree<Short> createTree(Batch.LogicalUnit logicalUnit) {
        StartNodeDataDO startNodeDataDO = logicalUnit.getStartNodeDataDO();
        Short rootObjId = startNodeDataDO.getObjid();
        objIdOutputNodesMap.put(rootObjId, normalizeNodeList(valueOf(startNodeDataDO.getOutputNodes(), h -> h.getValue())));
        nodeIdObjIdMap.put(startNodeDataDO.getNodeId(), rootObjId);
        objIdToNodeIdMap.put(rootObjId, startNodeDataDO.getNodeId());
        nodeIdObjectHashMap.put(startNodeDataDO.getNodeId(), new RTDMObject("start", startNodeDataDO));
        for (ReplyNodeDataDO node : logicalUnit.getReplyNodeDataDOs()) {
            register("reply", node, node.getObjid(), node.getNodeId(),
                    valueOf(node.getInputNodes(), h -> h.getValue()), valueOf(node.getOutputNodes(), h -> h.getValue()));
        }
        for (SplitNodeDataDO node : logicalUnit.getSplitNodeDataDOs()) {
            register("split", node, node.getObjid(), node.getNodeId(),
                    valueOf(node.getInputNodes(), h -> h.getValue()), valueOf(node.getOutputNodes(), h -> h.getValue()));
        }
        for (CellNodeDataDO node : logicalUnit.getCellNodeDataDOs()) {
            register("cell", node, node.getObjid(), node.getNodeId(),
                    valueOf(node.getInputNodes(), h -> h.getValue()), valueOf(node.getOutputNodes(), h -> h.getValue()));
        }
        for (MultiSelectNodeDataDO node : logicalUnit.getMultiSelectNodeDataDOs()) {
            register("multiSelect", node, node.getObjid(), node.getNodeId(),
                    valueOf(node.getInputNodes(), h -> h.getValue()), valueOf(node.getOutputNodes(), h -> h.getValue()));
        }
        for (ProcessNodeDataDO node : logicalUnit.getProcessNodeDataDOs()) {
            register("custom", node, node.getObjid(), node.getNodeId(),
                    valueOf(node.getInputNodes(), h -> h.getValue()), valueOf(node.getOutputNodes(), h -> h.getValue()));
        }
        for (SubDiagramNodeDataDO node : logicalUnit.getSubDiagramNodeDataDOs()) {
            register("subdiagram", node, node.getObjid(), node.getNodeId(),
                    valueOf(node.getInputNodes(), h -> h.getValue()), valueOf(node.getOutputNodes(), h -> h.getValue()));
        }

        // Handle unsupported nodes
        for (AssignmentNodeDataDO node : logicalUnit.getAssignmentNodeDataDOs()) {
            register("assignment", node, node.getObjid(), node.getNodeId(),
                    valueOf(node.getInputNodes(), h -> h.getValue()), valueOf(node.getOutputNodes(), h -> h.getValue()));
        }
        for (CHandRHNodeDataDO node : logicalUnit.getCHandRHNodeDataDOs()) {
            register("cHandRH", node, node.getObjid(), node.getNodeId(),
                    valueOf(node.getInputNodes(), h -> h.getValue()), valueOf(node.getOutputNodes(), h -> h.getValue()));
        }
        for (StagedTreatmentsNodeDataDO node : logicalUnit.getAddStagedTreatmentsNodeDataDOs()) {
            register("stagedTreatment", node, node.getObjid(), node.getNodeId(),
                    valueOf(node.getInputNodes(), h -> h.getValue()), valueOf(node.getOutputNodes(), h -> h.getValue()));
        }
        for (RemoveStagedTreatmentsNodeDataDO node : logicalUnit.getRemoveStagedTreatmentsNodeDataDOs()) {
            register("removeStagedTreatment", node, node.getObjid(), node.getNodeId(),
                    valueOf(node.getInputNodes(), h -> h.getValue()), valueOf(node.getOutputNodes(), h -> h.getValue()));
        }
        for (CrossTableNodeDataDO node : logicalUnit.getCrossTableNodeDataDOs()) {
            register("crossTable", node, node.getObjid(), node.getNodeId(),
                    valueOf(node.getInputNodes(), h -> h.getValue()), valueOf(node.getOutputNodes(), h -> h.getValue()));
        }

        return generateTree(rootObjId);
    }

    private void register(String objectType, Object node, Short objId, String nodeId,
                          String inputNodes, String outputNodes) {
        objIdOutputNodesMap.put(objId, normalizeNodeList(outputNodes));
        nodeIdObjIdMap.put(nodeId, objId);
        nodeIdObjectHashMap.put(nodeId, new RTDMObject(objectType, node));
        objIdToNodeIdMap.put(objId, nodeId);
        nodeIdInputNodesMap.put(nodeId, normalizeNodeList(inputNodes));
    }

    private static <T> String valueOf(T holder, Function<T, String> getter) {
        return holder == null ? null : getter.apply(holder);
    }

    /**
     * Trims every entry of a comma separated node id list and drops empty entries.
     */
    static List<String> parseNodeList(String nodeList) {
        List<String> nodeIds = new ArrayList<>();
        if (nodeList != null) {
            for (String nodeId : nodeList.split(NODE_LIST_SEPARATOR)) {
                String trimmed = nodeId.trim();
                if (!trimmed.isEmpty()) {
                    nodeIds.add(trimmed);
                }
            }
        }
        return nodeIds;
    }

    private static String normalizeNodeList(String nodeList) {
        return String.join(NODE_LIST_SEPARATOR, parseNodeList(nodeList));
    }

    private GenericTree<Short> generateTree(Short rootObjId) {
        GenericTree<Short> tree = new GenericTree<>();
        GenericTreeNode<Short> root = new GenericTreeNode<>(rootObjId);
        Map<Short, GenericTreeNode<Short>> builtNodes = new HashMap<>();
        builtNodes.put(rootObjId, root);
        Set<Short> path = new LinkedHashSet<>();
        path.add(rootObjId);
        addChildrenToTree(root, builtNodes, path);
        tree.setRoot(root);
        return tree;
    }

    /**
     * Depth-first construction that creates one node per RTDM objId. {@code path} holds the objIds on the current
     * DFS path (in order) and is used to detect cycles; {@code builtNodes} holds every node whose children have been
     * attached already.
     */
    private void addChildrenToTree(GenericTreeNode<Short> parent, Map<Short, GenericTreeNode<Short>> builtNodes,
                                   Set<Short> path) {
        Short parentObjId = parent.getData();
        for (String childNodeId : parseNodeList(objIdOutputNodesMap.get(parentObjId))) {
            Short childObjId = nodeIdObjIdMap.get(childNodeId);
            if (childObjId == null) {
                throw new IllegalArgumentException("RTDM node '" + objIdToNodeIdMap.get(parentObjId)
                        + "' references unknown node '" + childNodeId + "' in its OutputNodes");
            }
            if (path.contains(childObjId)) {
                throw new IllegalArgumentException("RTDM diagram contains a cycle, which is not supported: "
                        + describeCycle(path, childObjId));
            }
            GenericTreeNode<Short> child = builtNodes.get(childObjId);
            if (child == null) {
                child = new GenericTreeNode<>(childObjId);
                path.add(childObjId);
                addChildrenToTree(child, builtNodes, path);
                path.remove(childObjId);
                builtNodes.put(childObjId, child);
            }
            parent.addChild(child);
        }
    }

    private String describeCycle(Set<Short> path, Short repeatedObjId) {
        List<String> cycle = new ArrayList<>();
        boolean inCycle = false;
        for (Short objId : path) {
            inCycle = inCycle || objId.equals(repeatedObjId);
            if (inCycle) {
                cycle.add(objIdToNodeIdMap.get(objId));
            }
        }
        cycle.add(objIdToNodeIdMap.get(repeatedObjId));
        return cycle.stream().map(nodeId -> "'" + nodeId + "'").collect(Collectors.joining(" -> "));
    }

    public Map<String, String> getNodeIdInputNodesMap() {
        return nodeIdInputNodesMap;
    }

    /**
     * Number of upstream paths into a node, used to decide whether it is a fan-in that needs cross-branch links.
     * Cells have no steps of their own, so a merge can be expressed through a shared cell (two paths into a cell that
     * leads to the node): an input that is a cell counts as however many paths reach that cell, and at least one (a
     * cell fed by a single split case is one path).
     */
    public int upstreamPathCount(String nodeId) {
        return upstreamPathCount(nodeId, new HashSet<>());
    }

    private int upstreamPathCount(String nodeId, Set<String> visiting) {
        String inputs = nodeIdInputNodesMap.get(nodeId);
        if (inputs == null || inputs.isBlank() || !visiting.add(nodeId)) {
            return 0;
        }
        int paths = 0;
        for (String input : inputs.split(",")) {
            RTDMObject inputObject = nodeIdObjectHashMap.get(input.trim());
            paths += inputObject != null && "cell".equals(inputObject.getObjectType())
                    ? Math.max(1, upstreamPathCount(input.trim(), visiting)) : 1;
        }
        visiting.remove(nodeId);
        return paths;
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
        nodeIdInputNodesMap.clear();
        objIdOutputNodesMap.clear();
        nodeIdObjectHashMap.clear();
        objIdToNodeIdMap.clear();
    }
}
