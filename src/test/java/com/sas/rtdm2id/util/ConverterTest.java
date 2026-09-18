package com.sas.rtdm2id.util;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.ConditionBranch;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.util.object.processing.AssignmentNodeConverter;
import com.sas.rtdm2id.util.object.processing.CommonProcessing;
import com.sas.rtdm2id.util.object.processing.ConfirmContactNodeConverter;
import com.sas.rtdm2id.util.object.processing.CrossTableNodeConverter;
import com.sas.rtdm2id.util.object.processing.MultiSelectNodeConverter;
import com.sas.rtdm2id.util.object.processing.ProcessNodeConverter;
import com.sas.rtdm2id.util.object.processing.ReplyNodeConverter;
import com.sas.rtdm2id.util.object.processing.SplitNodeConverter;
import com.sas.rtdm2id.util.object.processing.StagedTreatmentConverter;
import com.sas.rtdm2id.util.object.processing.SubDiagramNodeConverter;
import com.sas.rtdm2id.util.tree.impl.GenericTreeNode;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConverterTest {

    @Test
    void restoresParentBranchBeforeProcessingSibling() throws Exception {
        MapStorage mapStorage = mock(MapStorage.class);
        TreeUtil treeUtil = mock(TreeUtil.class);
        Map<Short, String> nodeIdByObjectId = new HashMap<>();
        nodeIdByObjectId.put((short) 1, "branch-a");
        nodeIdByObjectId.put((short) 2, "branch-b");
        nodeIdByObjectId.put((short) 3, "node-a");
        nodeIdByObjectId.put((short) 4, "node-b");

        Step stepA = new Step();
        Step stepB = new Step();
        Map<String, java.util.List<Step>> nodeSteps = new HashMap<>();
        nodeSteps.put("node-a", Collections.singletonList(stepA));
        nodeSteps.put("node-b", Collections.singletonList(stepB));

        ConditionBranch branchA = new ConditionBranch();
        branchA.setSteps(new ArrayList<>());
        ConditionBranch branchB = new ConditionBranch();
        branchB.setSteps(new ArrayList<>());
        Map<String, ConditionBranch> branches = new HashMap<>();
        branches.put("branch-a", branchA);
        branches.put("branch-b", branchB);

        when(treeUtil.getObjIdToNodeIdMap()).thenReturn(nodeIdByObjectId);
        when(mapStorage.getNodeIdStepMap()).thenReturn(nodeSteps);
        when(mapStorage.getBranchNodeIdConditionBranchMap()).thenReturn(branches);
        when(mapStorage.isUseCrossBranchLinks()).thenReturn(false);

        Converter converter = new Converter(
                mock(ProcessNodeConverter.class),
                mock(SubDiagramNodeConverter.class),
                mock(ReplyNodeConverter.class),
                mock(MultiSelectNodeConverter.class),
                mock(SplitNodeConverter.class),
                mock(ConfirmContactNodeConverter.class),
                mock(CommonProcessing.class),
                mock(AssignmentNodeConverter.class),
                mock(CrossTableNodeConverter.class),
                mock(StagedTreatmentConverter.class),
                treeUtil,
                mapStorage);

        GenericTreeNode<Short> root = new GenericTreeNode<>((short) 0);
        GenericTreeNode<Short> branchNodeA = new GenericTreeNode<>((short) 1);
        GenericTreeNode<Short> branchNodeB = new GenericTreeNode<>((short) 2);
        branchNodeA.addChild(new GenericTreeNode<>((short) 3));
        branchNodeB.addChild(new GenericTreeNode<>((short) 4));
        root.addChild(branchNodeA);
        root.addChild(branchNodeB);

        Method finalConnectionOfNodes = Converter.class.getDeclaredMethod(
                "finalConnectionOfNodes", GenericTreeNode.class, Deque.class);
        finalConnectionOfNodes.setAccessible(true);
        finalConnectionOfNodes.invoke(converter, root, new ArrayDeque<Short>());

        assertEquals(Collections.singletonList(stepA), branchA.getSteps());
        assertEquals(Collections.singletonList(stepB), branchB.getSteps());
    }

    @Test
    void doesNotAppendNestedConditionToParentBranch() throws Exception {
        MapStorage mapStorage = new MapStorage();
        TreeUtil treeUtil = new TreeUtil();
        Converter converter = createConverter(treeUtil, mapStorage);

        treeUtil.getObjIdToNodeIdMap().put((short) 1, "outer-branch");
        treeUtil.getObjIdToNodeIdMap().put((short) 2, "parent-condition");
        treeUtil.getObjIdToNodeIdMap().put((short) 3, "nested-condition");

        Step parentCondition = new Step();
        parentCondition.setType(Step.TypeEnum.CONDITION);
        Step nestedCondition = new Step();
        nestedCondition.setType(Step.TypeEnum.CONDITION);
        mapStorage.getNodeIdStepMap().put("parent-condition", Collections.singletonList(parentCondition));
        mapStorage.getNodeIdStepMap().put("nested-condition", Collections.singletonList(nestedCondition));

        ConditionBranch outerBranch = new ConditionBranch();
        outerBranch.setSteps(new ArrayList<>());
        mapStorage.getBranchNodeIdConditionBranchMap().put("outer-branch", outerBranch);

        GenericTreeNode<Short> parentNode = new GenericTreeNode<>((short) 2);
        parentNode.addChild(new GenericTreeNode<>((short) 3));
        Deque<Short> branchStack = new ArrayDeque<>();
        branchStack.push((short) 1);

        invokeFinalConnection(converter, parentNode, branchStack);

        assertTrue(outerBranch.getSteps().isEmpty());
    }

    private Converter createConverter(TreeUtil treeUtil, MapStorage mapStorage) {
        return new Converter(
                mock(ProcessNodeConverter.class),
                mock(SubDiagramNodeConverter.class),
                mock(ReplyNodeConverter.class),
                mock(MultiSelectNodeConverter.class),
                mock(SplitNodeConverter.class),
                mock(ConfirmContactNodeConverter.class),
                mock(CommonProcessing.class),
                mock(AssignmentNodeConverter.class),
                mock(CrossTableNodeConverter.class),
                mock(StagedTreatmentConverter.class),
                treeUtil,
                mapStorage);
    }

    private void invokeFinalConnection(Converter converter, GenericTreeNode<Short> node,
                                       Deque<Short> branchStack) throws Exception {
        Method finalConnectionOfNodes = Converter.class.getDeclaredMethod(
                "finalConnectionOfNodes", GenericTreeNode.class, Deque.class);
        finalConnectionOfNodes.setAccessible(true);
        finalConnectionOfNodes.invoke(converter, node, branchStack);
    }
}