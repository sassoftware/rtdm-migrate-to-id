package com.sas.rtdm2id.util.tree.impl;

import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.model.rtdm.CellNodeDataDO;
import com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO;
import com.sas.rtdm2id.model.rtdm.StartNodeDataDO;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
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
		assertNotSame(firstReply, secondReply);
		assertTrue(firstReply.getChildren().isEmpty());
		assertTrue(secondReply.getChildren().isEmpty());
		assertEquals(4, treeUtil.getNodeIdObjectHashMap().size());
		assertEquals("campaign-reply", treeUtil.getObjIdToNodeIdMap().get((short) 4));
		assertEquals(Short.valueOf((short) 4), treeUtil.getNodeIdObjIdMap().get("campaign-reply"));
		assertEquals("campaign-left, campaign-right", treeUtil.getNodeIdInputNodesMap().get("campaign-reply"));
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
}
