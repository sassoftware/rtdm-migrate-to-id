package com.sas.rtdm2id.util.tree.impl;

import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.model.rtdm.CellNodeDataDO;
import com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO;
import com.sas.rtdm2id.model.rtdm.StartNodeDataDO;
import com.sas.rtdm2id.util.model.RTDMObject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TreeUtilTest {

	@Test
	void buildsBothFanInPathsAndResolvesNodeIdsWithWhitespaceAroundComma() {
		TreeUtil treeUtil = new TreeUtil();
		Batch.LogicalUnit logicalUnit = fanInDiagram("campaign");

		GenericTreeNode<Short> root = treeUtil.createTree(logicalUnit).getRoot();

		assertEquals(Short.valueOf((short) 1), root.getData());
		assertEquals(Arrays.asList((short) 2, (short) 3), root.getChildren().stream()
				.map(GenericTreeNode::getData).collect(Collectors.toList()));
		GenericTreeNode<Short> firstReply = root.getChildren().get(0).getChildren().get(0);
		GenericTreeNode<Short> secondReply = root.getChildren().get(1).getChildren().get(0);
		assertEquals(Short.valueOf((short) 4), firstReply.getData());
		assertEquals(firstReply.getData(), secondReply.getData());
		// The fan-in node is shared by both parents instead of being copied per path
		assertSame(firstReply, secondReply);
		assertTrue(firstReply.getChildren().isEmpty());
		assertEquals(4, treeUtil.getNodeIdObjectHashMap().size());
		assertEquals("campaign-reply", treeUtil.getObjIdToNodeIdMap().get((short) 4));
		assertEquals(Short.valueOf((short) 4), treeUtil.getNodeIdObjIdMap().get("campaign-reply"));
		assertEquals("campaign-left,campaign-right", treeUtil.getNodeIdInputNodesMap().get("campaign-reply"));
		assertEquals("", treeUtil.getObjIdOutputNodesMap().get((short) 4));
		assertSame(logicalUnit.getReplyNodeDataDOs().get(0),
				treeUtil.getRTDMObjectByObjId((short) 4).getObject());
		assertSame(logicalUnit.getStartNodeDataDO(), treeUtil.getRTDMObjectByObjId((short) 1).getObject());
		assertSame(logicalUnit.getCellNodeDataDOs().get(0), treeUtil.getRTDMObjectByObjId((short) 2).getObject());
	}

	@Test
	void clearsEveryGraphLookupAndCanBeClearedTwice() {
		TreeUtil treeUtil = new TreeUtil();
		treeUtil.createTree(fanInDiagram("campaign"));
		assertFalse(treeUtil.getObjIdOutputNodesMap().isEmpty());
		assertFalse(treeUtil.getObjIdToNodeIdMap().isEmpty());
		assertFalse(treeUtil.getNodeIdObjectHashMap().isEmpty());
		assertFalse(treeUtil.getNodeIdObjIdMap().isEmpty());
		assertFalse(treeUtil.getNodeIdInputNodesMap().isEmpty());

		treeUtil.clearMaps();
		treeUtil.clearMaps();

		assertTrue(treeUtil.getObjIdOutputNodesMap().isEmpty());
		assertTrue(treeUtil.getObjIdToNodeIdMap().isEmpty());
		assertTrue(treeUtil.getNodeIdObjectHashMap().isEmpty());
		assertTrue(treeUtil.getNodeIdObjIdMap().isEmpty());
		assertTrue(treeUtil.getNodeIdInputNodesMap().isEmpty());
	}

	@Test
	void buildsNextCampaignWithoutStaleNodeIdsAfterCleanup() {
		TreeUtil treeUtil = new TreeUtil();
		treeUtil.createTree(fanInDiagram("first"));
		treeUtil.clearMaps();
		Batch.LogicalUnit secondCampaign = fanInDiagram("second");

		GenericTree<Short> tree = treeUtil.createTree(secondCampaign);

		assertEquals(2, tree.getRoot().getChildren().size());
		assertEquals(4, treeUtil.getNodeIdObjIdMap().size());
		assertTrue(treeUtil.getNodeIdObjIdMap().keySet().stream().allMatch(name -> name.startsWith("second-")));
		assertTrue(treeUtil.getNodeIdObjectHashMap().keySet().stream().allMatch(name -> name.startsWith("second-")));
		assertTrue(treeUtil.getNodeIdInputNodesMap().keySet().stream().allMatch(name -> name.startsWith("second-")));
		assertSame(secondCampaign.getReplyNodeDataDOs().get(0), treeUtil.getRTDMObjectByObjId((short) 4).getObject());
	}

	@Test
	void trimsNodeListsAndDropsEmptyEntries() {
		TreeUtil treeUtil = new TreeUtil();
		Batch.LogicalUnit logicalUnit = fanInDiagram("campaign");
		logicalUnit.getStartNodeDataDO().getOutputNodes().setValue(" campaign-left ,, campaign-right , ");
		logicalUnit.getCellNodeDataDOs().get(0).getOutputNodes().setValue("campaign-reply,");
		logicalUnit.getReplyNodeDataDOs().get(0).getInputNodes().setValue(" campaign-left,campaign-right ,");
		logicalUnit.getReplyNodeDataDOs().get(0).setOutputNodes(null);

		GenericTreeNode<Short> root = treeUtil.createTree(logicalUnit).getRoot();

		assertEquals(Arrays.asList((short) 2, (short) 3), root.getChildren().stream()
				.map(GenericTreeNode::getData).collect(Collectors.toList()));
		assertEquals(1, root.getChildAt(0).getNumberOfChildren());
		assertEquals("campaign-left,campaign-right", treeUtil.getObjIdOutputNodesMap().get((short) 1));
		assertEquals("campaign-reply", treeUtil.getObjIdOutputNodesMap().get((short) 2));
		assertEquals("", treeUtil.getObjIdOutputNodesMap().get((short) 4));
		assertEquals("campaign-left,campaign-right", treeUtil.getNodeIdInputNodesMap().get("campaign-reply"));
	}

	@Test
	void buildsStartWithoutOutputsAsRootWithoutChildren() {
		TreeUtil treeUtil = new TreeUtil();
		Batch.LogicalUnit logicalUnit = fanInDiagram("campaign");
		logicalUnit.getStartNodeDataDO().getOutputNodes().setValue(" ");

		GenericTreeNode<Short> root = treeUtil.createTree(logicalUnit).getRoot();

		assertFalse(root.hasChildren());
	}

	@Test
	void rejectsReferenceToUnknownNode() {
		TreeUtil treeUtil = new TreeUtil();
		Batch.LogicalUnit logicalUnit = fanInDiagram("campaign");
		logicalUnit.getCellNodeDataDOs().get(1).getOutputNodes().setValue("campaign-reply, campaign-missing");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> treeUtil.createTree(logicalUnit));

		assertEquals("RTDM node 'campaign-right' references unknown node 'campaign-missing' in its OutputNodes",
				exception.getMessage());
	}

	@Test
	void rejectsCycleAndNamesTheNodesInvolved() {
		TreeUtil treeUtil = new TreeUtil();
		Batch.LogicalUnit logicalUnit = fanInDiagram("campaign");
		logicalUnit.getReplyNodeDataDOs().get(0).getOutputNodes().setValue("campaign-left");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> treeUtil.createTree(logicalUnit));

		assertEquals("RTDM diagram contains a cycle, which is not supported: "
				+ "'campaign-left' -> 'campaign-reply' -> 'campaign-left'", exception.getMessage());
	}

	@Test
	void rejectsSelfLoop() {
		TreeUtil treeUtil = new TreeUtil();
		Batch.LogicalUnit logicalUnit = fanInDiagram("campaign");
		logicalUnit.getReplyNodeDataDOs().get(0).getOutputNodes().setValue("campaign-reply");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> treeUtil.createTree(logicalUnit));

		assertTrue(exception.getMessage().endsWith("'campaign-reply' -> 'campaign-reply'"), exception.getMessage());
	}

	@Test
	void sharesNodesOfSequentialDiamondsInsteadOfExpandingEveryPath() {
		TreeUtil treeUtil = new TreeUtil();
		Batch.LogicalUnit logicalUnit = new Batch.LogicalUnit();
		StartNodeDataDO start = new StartNodeDataDO();
		start.setObjid((short) 1);
		start.setNodeId("start");
		StartNodeDataDO.OutputNodes startOutputs = new StartNodeDataDO.OutputNodes();
		startOutputs.setValue("l1, r1");
		start.setOutputNodes(startOutputs);
		logicalUnit.setStartNodeDataDO(start);
		int diamonds = 40;
		short objId = 2;
		for (int i = 1; i <= diamonds; i++) {
			String merge = "m" + i;
			String next = i == diamonds ? "" : "l" + (i + 1) + ", r" + (i + 1);
			String previous = i == 1 ? "start" : "m" + (i - 1);
			logicalUnit.getCellNodeDataDOs().add(cell(objId++, "l" + i, previous, merge));
			logicalUnit.getCellNodeDataDOs().add(cell(objId++, "r" + i, previous, merge));
			logicalUnit.getCellNodeDataDOs().add(cell(objId++, merge, "l" + i + ", r" + i, next));
		}

		GenericTreeNode<Short> root = treeUtil.createTree(logicalUnit).getRoot();

		java.util.Set<GenericTreeNode<Short>> distinct = java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<>());
		java.util.Deque<GenericTreeNode<Short>> pending = new java.util.ArrayDeque<>(java.util.List.of(root));
		while (!pending.isEmpty()) {
			GenericTreeNode<Short> node = pending.pop();
			if (distinct.add(node)) {
				pending.addAll(node.getChildren());
			}
		}
		assertEquals(1 + 3 * diamonds, distinct.size());
		assertSame(root.getChildAt(0).getChildAt(0), root.getChildAt(1).getChildAt(0));
	}

	private Batch.LogicalUnit fanInDiagram(String prefix) {
		Batch.LogicalUnit logicalUnit = new Batch.LogicalUnit();
		StartNodeDataDO start = new StartNodeDataDO();
		start.setObjid((short) 1);
		start.setNodeId(prefix + "-start");
		StartNodeDataDO.OutputNodes startOutputs = new StartNodeDataDO.OutputNodes();
		startOutputs.setValue(prefix + "-left , " + prefix + "-right");
		start.setOutputNodes(startOutputs);
		logicalUnit.setStartNodeDataDO(start);
		logicalUnit.getCellNodeDataDOs().add(cell((short) 2, prefix + "-left", prefix + "-start", prefix + "-reply"));
		logicalUnit.getCellNodeDataDOs().add(cell((short) 3, prefix + "-right", prefix + "-start", prefix + "-reply"));
		ReplyNodeDataDO reply = new ReplyNodeDataDO();
		reply.setObjid((short) 4);
		reply.setNodeId(prefix + "-reply");
		ReplyNodeDataDO.InputNodes replyInputs = new ReplyNodeDataDO.InputNodes();
		replyInputs.setValue(prefix + "-left, " + prefix + "-right");
		reply.setInputNodes(replyInputs);
		ReplyNodeDataDO.OutputNodes replyOutputs = new ReplyNodeDataDO.OutputNodes();
		replyOutputs.setValue("");
		reply.setOutputNodes(replyOutputs);
		logicalUnit.getReplyNodeDataDOs().add(reply);
		return logicalUnit;
	}

	private CellNodeDataDO cell(short objId, String nodeId, String inputNode, String outputNode) {
		CellNodeDataDO cell = new CellNodeDataDO();
		cell.setObjid(objId);
		cell.setNodeId(nodeId);
		CellNodeDataDO.InputNodes inputs = new CellNodeDataDO.InputNodes();
		inputs.setValue(inputNode);
		cell.setInputNodes(inputs);
		CellNodeDataDO.OutputNodes outputs = new CellNodeDataDO.OutputNodes();
		outputs.setValue(outputNode);
		cell.setOutputNodes(outputs);
		return cell;
	}

	@Test
	void countsUpstreamPathsThroughCellsThatMerge() {
		// Customer shape: a split case cell and a process both feed one shared cell, which feeds the reply. The reply
		// has a single input node but two upstream paths, so it is a fan-in.
		TreeUtil treeUtil = new TreeUtil();
		treeUtil.getNodeIdObjectHashMap().put("caseCell", new RTDMObject("cell", null));
		treeUtil.getNodeIdObjectHashMap().put("sharedCell", new RTDMObject("cell", null));
		treeUtil.getNodeIdObjectHashMap().put("process", new RTDMObject("process", null));
		treeUtil.getNodeIdInputNodesMap().put("caseCell", "split");
		treeUtil.getNodeIdInputNodesMap().put("process", "otherCaseCell");
		treeUtil.getNodeIdInputNodesMap().put("sharedCell", "caseCell,process");
		treeUtil.getNodeIdInputNodesMap().put("reply", "sharedCell");
		treeUtil.getNodeIdInputNodesMap().put("single", "caseCell");
		treeUtil.getNodeIdInputNodesMap().put("twoCases", "caseCell,process");

		assertEquals(2, treeUtil.upstreamPathCount("reply"));
		// A node behind a single split case stays a single path; direct multiple inputs are counted as before
		assertEquals(1, treeUtil.upstreamPathCount("single"));
		assertEquals(2, treeUtil.upstreamPathCount("twoCases"));
		assertEquals(0, treeUtil.upstreamPathCount("unknown"));
	}
}
