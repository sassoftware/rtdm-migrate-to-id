/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.mapper.IBVariableDOMapperImpl;
import com.sas.rtdm2id.mapper.VarRefMapper;
import com.sas.rtdm2id.model.id.decision.BranchCase;
import com.sas.rtdm2id.model.id.decision.ConditionBranch;
import com.sas.rtdm2id.model.id.decision.ConditionDecisionStep;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.Mapping;
import com.sas.rtdm2id.model.id.decision.SignatureTerm;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.rtdm.*;
import com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO;
import com.sas.rtdm2id.model.rtdm.extension.SubDiagramNodeDataDO;
import com.sas.rtdm2id.model.rtdm.extension.VarRef;
import com.sas.rtdm2id.util.model.IBVariableDO;
import com.sas.rtdm2id.util.model.RTDM2IDConstants;
import com.sas.rtdm2id.util.model.RTDMObject;
import com.sas.rtdm2id.util.object.processing.*;
import com.sas.rtdm2id.util.tree.impl.GenericTree;
import com.sas.rtdm2id.util.tree.impl.GenericTreeNode;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.CUSTOM_CONSTANT;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.DATAGRID_CONSTANT;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.INPUT_DIRECTION;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.OUTPUT_DIRECTION;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.REGEXP;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.SOURCE_EVENT;

@Component
public class Converter {
    private final ProcessNodeConverter processNodeConverter;
    private final SubDiagramNodeConverter subDiagramNodeConverter;
    private final ReplyNodeConverter replyNodeConverter;
    private final MultiSelectNodeConverter multiSelectNodeConverter;
    private final SplitNodeConverter splitNodeConverter;
    private final ConfirmContactNodeConverter confirmContactNodeConverter;
    private final CommonProcessing commonProcessing;
    private final AssignmentNodeConverter assignmentNodeConverter;
    private final CrossTableNodeConverter crossTableNodeConverter;
    private final StagedTreatmentConverter stagedTreatmentConverter;
    private final TreeUtil treeUtil;
    private final MapStorage mapStorage;

    public Converter(ProcessNodeConverter processNodeConverter, SubDiagramNodeConverter subDiagramNodeConverter,
            ReplyNodeConverter replyNodeConverter, MultiSelectNodeConverter multiSelectNodeConverter,
            SplitNodeConverter splitNodeConverter, ConfirmContactNodeConverter confirmContactNodeConverter,
            CommonProcessing commonProcessing, AssignmentNodeConverter assignmentNodeConverter,
            CrossTableNodeConverter crossTableNodeConverter, StagedTreatmentConverter stagedTreatmentConverter,
            TreeUtil treeUtil, MapStorage mapStorage) {
        this.processNodeConverter = processNodeConverter;
        this.subDiagramNodeConverter = subDiagramNodeConverter;
        this.replyNodeConverter = replyNodeConverter;
        this.multiSelectNodeConverter = multiSelectNodeConverter;
        this.splitNodeConverter = splitNodeConverter;
        this.confirmContactNodeConverter = confirmContactNodeConverter;
        this.commonProcessing = commonProcessing;
        this.assignmentNodeConverter = assignmentNodeConverter;
        this.crossTableNodeConverter = crossTableNodeConverter;
        this.stagedTreatmentConverter = stagedTreatmentConverter;
        this.treeUtil = treeUtil;
        this.mapStorage = mapStorage;
    }

    public Decision createDecision(Batch batch,
                                   GenericTree<Short> tree) {
        Decision decision = new Decision();
        decision.setName(batch.getLogicalUnit().getCampaignDO().getName());
        decision.setDescription(batch.getLogicalUnit().getCampaignDO().getId());

        IBVariableDOMapperImpl mapper = new IBVariableDOMapperImpl();

        if(batch.getLogicalUnit().getStartNodeDataDO().getEvent()!=null) {
            // Decision Campaign (DC)
            StartNodeDataDO.Event.RequestVars requestVars = batch.getLogicalUnit().getStartNodeDataDO().getEvent().getRequestVars();
            if (requestVars != null) {
                for (StartNodeDataDO.Event.RequestVars.IBVariableDO ibVariableDO : requestVars.getIbVariableDOs()) {
                    processEventVars(decision, mapper.ibVariableDoGet(ibVariableDO), INPUT_DIRECTION, ibVariableDO.getValue());
                }
            }

            StartNodeDataDO.Event.ReplyVars replyVars = batch.getLogicalUnit().getStartNodeDataDO().getEvent().getReplyVars();
            if (replyVars != null) {
                for (StartNodeDataDO.Event.ReplyVars.IBVariableDO ibVariableDO : replyVars.getIbVariableDOs()) {
                    processEventVars(decision, mapper.ibVariableDoGet(ibVariableDO), OUTPUT_DIRECTION, ibVariableDO.getValue());
                }
            }
        }else {
            // Decision Treatment Campaign (DTC)
           
            // The Start node has no Event, instead it contains a List of Input Variables (IBVariableDO)
            StartNodeDataDO.InputVariables inputVars = batch.getLogicalUnit().getStartNodeDataDO().getInputVariables();
            if (inputVars!=null) {
                // We can leverage the existing IBVariableDO data structure defined for an event
                for (StartNodeDataDO.Event.RequestVars.IBVariableDO ibVariableDO : inputVars.getIbVariableDOs()) {
                    processEventVars(decision, mapper.ibVariableDoGet(ibVariableDO), INPUT_DIRECTION, ibVariableDO.getValue());
                }
            }

            // DTC has single Reply Variable of type Data Grid that returns Treatments
            // This variable is defined on the Reply Node itself rather than the Start Node like in a DC
            IBVariableDO replyOutVariable = new IBVariableDO();
            replyOutVariable.setPhysicalName("Treatments");
            replyOutVariable.setTypeDescription(DATAGRID_CONSTANT);
            processEventVars(decision, replyOutVariable, OUTPUT_DIRECTION, replyOutVariable.getValue());
        }
        // The Decision's interface is what RTDM defines on the Start node: request variables in, reply variables out
        Set<String> interfaceTerms = new HashSet<>();
        for (SignatureTerm term : decision.getSignature()) {
            if (term.getName() != null && !SignatureTerm.DirectionEnum.NONE.equals(term.getDirection())) {
                interfaceTerms.add(term.getName().toLowerCase(Locale.ROOT));
            }
        }

        // The structure built by TreeUtil is a DAG: nodes with several input nodes are shared by all of their
        // parents. Each walk below therefore remembers what it has already expanded so that the work stays
        // proportional to the size of the RTDM diagram instead of the number of paths through it.
        GenericTreeNode<Short> root = tree.getRoot();
        walkTree(root, decision, Collections.newSetFromMap(new IdentityHashMap<>()));
        commonProcessing.writeCalculatedNodeGroups();
        connectConditionsInsideMap(root);
        ConditionDecisionStep conditionDecisionStep = new ConditionDecisionStep();
        if (root.hasChildren()) {
            fillConditionDecisionStep(root.getChildren().get(0).getData(), conditionDecisionStep);
        }
        Deque<Short> objIDStack = new ArrayDeque<>();
        finalConnectionOfNodes(root, objIDStack);

        decision.setFlow(conditionDecisionStep);
        labelBranchCasesWithCellNames(decision);
        addCalculatedItemSignatureTerms(batch, decision);
        addMissingVars(decision);
        keepOnlyInterfaceTermsPublic(decision, interfaceTerms);
        return decision;
    }

    /**
     * Every other term is internal to the flow (direction none), however steps read or write it. A Decision used as a
     * sub-decision exposes its input/output terms to the calling step, which must map each of them; exposing the
     * flow's internal terms made ID reject the parent with hundreds of "Mapping ... is invalid" errors. Global variable
     * terms keep their own direction handling (see updateSignatureDirection).
     */
    private void keepOnlyInterfaceTermsPublic(Decision decision, Set<String> interfaceTerms) {
        for (SignatureTerm term : decision.getSignature()) {
            if (term.getName() != null && term.getGlobalVariableId() == null
                    && !interfaceTerms.contains(term.getName().toLowerCase(Locale.ROOT))) {
                term.setDirection(SignatureTerm.DirectionEnum.NONE);
            }
        }
    }

    private void addCalculatedItemSignatureTerms(Batch batch, Decision decision) {
        FlowDO sourceFlow = batch.getLogicalUnit().getFlowDO();
        if (sourceFlow == null || sourceFlow.getCalculatedDataItemList() == null) {
            return;
        }
        for (CalculatedItemDO item : sourceFlow.getCalculatedDataItemList().getCalculatedItemDOs()) {
            String name = commonProcessing.sanitizeVariableName(RTDM2IDConstants.SOURCE_CALCULATED_DATA_ITEM
                + "." + item.getName());
            SignatureTerm term = decision.getSignature().stream()
                .filter(existing -> name.equalsIgnoreCase(existing.getName())).findFirst().orElseGet(() -> {
                    SignatureTerm added = new SignatureTerm();
                    added.setName(name);
                    added.setDirection(SignatureTerm.DirectionEnum.NONE);
                    decision.addSignatureItem(added);
                    return added;
                });
            term.setDataType(SignatureTerm.DataTypeEnum.fromValue(
                commonProcessing.getCalculatedItemDataType(item.getCalculatedItemType())));
            if (SignatureTerm.DataTypeEnum.DATAGRID.equals(term.getDataType()) && term.getDataGridExtension() == null) {
                term.setDataGridExtension(new ArrayList<>());
            }
        }
    }

    private void addMissingVars(Decision decision) {
        List<SignatureTerm> signatureTerms = decision.getSignature();
        Map<String, SignatureTerm> signatureTermsByName = new HashMap<>();
        for (SignatureTerm signatureTerm : signatureTerms) {
            if (signatureTerm.getName() != null) {
                signatureTermsByName.put(signatureTerm.getName().toLowerCase(Locale.ROOT), signatureTerm);
            }
        }
        ConditionDecisionStep decisionFlow = decision.getFlow();
        if (decisionFlow!=null && decisionFlow.getFlow()!=null){
            Deque<Step> pendingSteps = new LinkedList<>(decisionFlow.getFlow());
            Set<Step> visitedSteps = Collections.newSetFromMap(new IdentityHashMap<>());
            while (!pendingSteps.isEmpty()) {
                Step step = pendingSteps.removeFirst();
                if (step == null || !visitedSteps.add(step)) {
                    continue;
                }
                addBranchSteps(step.getOnTrue(), pendingSteps);
                addBranchSteps(step.getOnFalse(), pendingSteps);
                addBranchSteps(step.getDefaultCase(), pendingSteps);
                if (step.getBranchCases() != null) {
                    for (BranchCase branchCase : step.getBranchCases()) {
                        if (branchCase != null) {
                            addBranchSteps(branchCase.getOnTrue(), pendingSteps);
                        }
                    }
                }
                if (step.getMappings() == null) {
                    continue;
                }
                for (Mapping mapping : step.getMappings()) {
                    if (mapping == null) {
                        continue;
                    }
                    String decisionVarName = mapping.getTargetDecisionTermName();
                    if (decisionVarName == null || mapping.getDirection() == null) {
                        continue;
                    }
                    String variableKey = decisionVarName.toLowerCase(Locale.ROOT);
                    SignatureTerm signatureTerm = signatureTermsByName.get(variableKey);
                    SignatureTerm.DirectionEnum mappingDirection = SignatureTerm.DirectionEnum
                            .fromValue(mapping.getDirection().toString());
                    if (signatureTerm == null) {
                        signatureTerm = new SignatureTerm();
                        signatureTerm.setName(decisionVarName);
                        signatureTerm.setDataType(SignatureTerm.DataTypeEnum.UNKNOWN);
                        signatureTerms.add(signatureTerm);
                        signatureTermsByName.put(variableKey, signatureTerm);
                    }
                    updateSignatureDirection(signatureTerm, mappingDirection);
                }
            }
        }
    }

    /**
     * Branch cases are labelled with the RTDM cell node id while the flow is wired (fillBranchStepWithInnerSteps uses
     * the label as that key). Once wiring is complete the label is replaced with the cell's name as shown in the RTDM
     * diagram (e.g. "1 - CONTROLE"), so the ID branch reads like the original. Labels that are not cell ids are kept.
     */
    private void labelBranchCasesWithCellNames(Decision decision) {
        ConditionDecisionStep decisionFlow = decision.getFlow();
        if (decisionFlow == null || decisionFlow.getFlow() == null) {
            return;
        }
        Deque<Step> pendingSteps = new LinkedList<>(decisionFlow.getFlow());
        Set<Step> visitedSteps = Collections.newSetFromMap(new IdentityHashMap<>());
        while (!pendingSteps.isEmpty()) {
            Step step = pendingSteps.pop();
            if (step == null || !visitedSteps.add(step)) {
                continue;
            }
            addBranchSteps(step.getOnTrue(), pendingSteps);
            addBranchSteps(step.getOnFalse(), pendingSteps);
            addBranchSteps(step.getDefaultCase(), pendingSteps);
            if (step.getBranchCases() != null) {
                for (BranchCase branchCase : step.getBranchCases()) {
                    addBranchSteps(branchCase.getOnTrue(), pendingSteps);
                    String cellName = cellName(branchCase.getLabel());
                    if (cellName != null) {
                        branchCase.setLabel(cellName);
                    }
                }
            }
        }
    }

    private String cellName(String nodeId) {
        RTDMObject rtdmObject = nodeId == null ? null : treeUtil.getNodeIdObjectHashMap().get(nodeId);
        if (rtdmObject != null && rtdmObject.getObject() instanceof CellNodeDataDO cell
                && cell.getNodeName() != null && !cell.getNodeName().isBlank()) {
            return cell.getNodeName().trim();
        }
        return null;
    }

    private void addBranchSteps(ConditionBranch branch, Deque<Step> pendingSteps) {
        if (branch != null && branch.getSteps() != null) {
            pendingSteps.addAll(branch.getSteps());
        }
    }

    private void updateSignatureDirection(SignatureTerm signatureTerm,
            SignatureTerm.DirectionEnum mappingDirection) {
        if (signatureTerm.getGlobalVariableId() != null) {
            if (SignatureTerm.DirectionEnum.OUTPUT.equals(mappingDirection)
                    || SignatureTerm.DirectionEnum.OUTPUT.equals(signatureTerm.getDirection())) {
                signatureTerm.setDirection(SignatureTerm.DirectionEnum.OUTPUT);
            } else {
                signatureTerm.setDirection(SignatureTerm.DirectionEnum.NONE);
            }
            return;
        }

        SignatureTerm.DirectionEnum existingDirection = signatureTerm.getDirection();
        if (existingDirection == null || SignatureTerm.DirectionEnum.NONE.equals(existingDirection)) {
            signatureTerm.setDirection(mappingDirection);
        } else if (!SignatureTerm.DirectionEnum.INOUT.equals(existingDirection)
                && !existingDirection.equals(mappingDirection)) {
            signatureTerm.setDirection(SignatureTerm.DirectionEnum.INOUT);
        }
    }

    private void processEventVars(Decision decision
            , IBVariableDO ibVariableDO, String direction
            , ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value) {
        commonProcessing.addNewSignatureItem(ibVariableDO, direction, decision,
                INPUT_DIRECTION.equals(direction) ? SOURCE_EVENT : null);
        // Only variable references can point at a global variable; literal values have no ValueTypeVarInfoDO
        if (value != null && ibVariableDO.getValue() != null
                && ibVariableDO.getValue().getValueTypeVarInfoDO() != null) {
            VarRef varRef = VarRefMapper.VAR_REF_MAPPER.varRefGet(ibVariableDO.getValue().getValueTypeVarInfoDO());
            if (commonProcessing.checkForGlobalVariable(varRef, null, ibVariableDO.getName(), decision, false)) {
                commonProcessing.checkForAdditionalVariables(ibVariableDO.getName(), varRef, "none", decision);
            }
        }
    }

    private void walkTree(GenericTreeNode<Short> node, Decision decision, Set<GenericTreeNode<Short>> walkedNodes) {
        for (GenericTreeNode<Short> innerNode : node.getChildren()) {
            // A shared (fan-in) node and everything below it was fully processed the first time it was reached
            if (!walkedNodes.add(innerNode)) {
                continue;
            }
            Short objId = innerNode.getData();

            // Make sure the node is only processed once
            String nodeId = treeUtil.getObjIdToNodeIdMap().get(objId);
            if (mapStorage.getNodeIdStepMap().get(nodeId)==null) {
                RTDMObject rtdmObject = treeUtil.getRTDMObjectByObjId(objId);
                if (rtdmObject!=null) {
                    List<Step> idObjectList = processRTDMObject(rtdmObject, decision, objId);
                    mapStorage.getNodeIdStepMap().put(nodeId, idObjectList);
                }
            }

            walkTree(innerNode, decision, walkedNodes);
        }
    }

    private List<Step> processRTDMObject(RTDMObject rtdmObject, Decision decision, Short objId) {
        List<Step> stepList = new ArrayList<>();
        switch (rtdmObject.getObjectType()) {
            case CUSTOM_CONSTANT:
                ProcessNodeDataDO processNodeDataDO = (ProcessNodeDataDO) rtdmObject.getObject();
                stepList = processNodeConverter.addCustomObjectStep(processNodeDataDO, decision, objId, commonProcessing.makeProcessNodeRuleSetName(processNodeDataDO));
                break;
            case "multiSelect":
                MultiSelectNodeDataDO multiSelectNodeDataDO = (MultiSelectNodeDataDO) rtdmObject.getObject();
                stepList = multiSelectNodeConverter.createConditionByFilter(multiSelectNodeDataDO, decision);
                break;
            case "split":
                SplitNodeDataDO splitNodeDataDO = (SplitNodeDataDO) rtdmObject.getObject();
                stepList = splitNodeConverter.createBranch(splitNodeDataDO, decision, commonProcessing.makeSplitNodeRuleSetName(splitNodeDataDO));
                break;
            case "subdiagram":
                SubDiagramNodeDataDO subDiagramNodeDataDO = (SubDiagramNodeDataDO) rtdmObject.getObject();
                stepList = subDiagramNodeConverter.createSubDiagram(subDiagramNodeDataDO, decision);

                stepList = subDiagramNodeConverter.addCustomObjectStep(subDiagramNodeDataDO, decision, objId, commonProcessing.makeSubdiagramNodeRuleSetName(subDiagramNodeDataDO));

                break;
            case "reply":
                ReplyNodeDataDO replyNodeDataDO = (ReplyNodeDataDO) rtdmObject.getObject();
                stepList = replyNodeConverter.createReply(replyNodeDataDO, decision);
                break;
            case "assignment":
                AssignmentNodeDataDO assignmentNodeDataDO = (AssignmentNodeDataDO) rtdmObject.getObject();
                stepList = assignmentNodeConverter.createAssignment(assignmentNodeDataDO,decision, objId);
                break;
            // Below this line are unsupported nodes
            // Unsupported nodes will be converted into Code File nodes
            case "cHandRH":
                CHandRHNodeDataDO cHandRHNodeDataDO = (CHandRHNodeDataDO) rtdmObject.getObject();
                Step contactInputsStep = confirmContactNodeConverter.addCustomObjectStep(cHandRHNodeDataDO, decision, objId, commonProcessing.makeConfirmContactNodeRuleSetName(cHandRHNodeDataDO));
                if (contactInputsStep != null) {
                    commonProcessing.addStep(stepList, contactInputsStep, cHandRHNodeDataDO.getNodeId());
                }
                commonProcessing.addStep(stepList, confirmContactNodeConverter.createRecordContactStep(cHandRHNodeDataDO, decision), cHandRHNodeDataDO.getNodeId());
                break;
            case "stagedTreatment":
                StagedTreatmentsNodeDataDO stagedTreatmentsNodeDataDO = (StagedTreatmentsNodeDataDO) rtdmObject.getObject();
                stepList = stagedTreatmentConverter.addCustomObjectStep(null, decision, objId,
                        commonProcessing.makeStagedTreatmentName(stagedTreatmentsNodeDataDO));
                break;  
            case "removeStagedTreatment":
                RemoveStagedTreatmentsNodeDataDO removeStagedTreatmentsNodeDataDO = (RemoveStagedTreatmentsNodeDataDO) rtdmObject.getObject();
                stepList = stagedTreatmentConverter.addCustomObjectStep(null, decision, objId,
                        commonProcessing.makeRemoveStagedTreatmentName(removeStagedTreatmentsNodeDataDO));
                break;
            case "crossTable":
                CrossTableNodeDataDO crossTableNodeDataDO = (CrossTableNodeDataDO) rtdmObject.getObject();
                stepList = crossTableNodeConverter.addSegmentationTreeStep(crossTableNodeDataDO, decision, objId,
                        commonProcessing.makeCrossTableNodeName(crossTableNodeDataDO));
                break;
            default:
                break;
        }
        return stepList;
    }

    private void fillConditionDecisionStep(Short objId, ConditionDecisionStep conditionDecisionStep) {
        if (objId==null) return;
        
        String nodeId = treeUtil.getObjIdToNodeIdMap().get(objId);
        Map<String, List<Step>> nodeIdStepMap = mapStorage.getNodeIdStepMap();
        List<String> nodes = Arrays.asList(treeUtil.getObjIdOutputNodesMap().get(objId).split(REGEXP));
        if (nodes.size() <= 1) {
            if (nodeIdStepMap.get(nodeId) != null) {
                for (Step step : nodeIdStepMap.get(nodeId)) {
                    if (!mapStorage.getExistingNodeIds().contains(nodeId)) {
                        conditionDecisionStep.addFlowItem(step);
                    }
                }
                String key = nodes.get(0);
                fillConditionDecisionStep(treeUtil.getNodeIdObjIdMap().get(key), conditionDecisionStep);
            } else {
                // For simple RTDM campaigns like Start -> Cell -> Reply the root object (objId) will be
                // the RTDM cell node which is not in nodeIdStepMap. This means the Reply ruleset is not
                // added to the migrated Decision as nodeIdStepMap.get(nodeId) != null is false. To fix
                // this get the output node from the Cell which is the Reply node. 
                String key = nodes.get(0);
                fillConditionDecisionStep(treeUtil.getNodeIdObjIdMap().get(key), conditionDecisionStep);
            }
        } else {
            for (Step step : nodeIdStepMap.get(nodeId)) {
                if (!mapStorage.getExistingNodeIds().contains(nodeId)) {
                    conditionDecisionStep.addFlowItem(step);
                }
            }
        }
    }

    private void connectConditionsInsideMap(GenericTreeNode<Short> root) {
        connectConditionsInsideMap(root, new HashSet<String>(), Collections.newSetFromMap(new IdentityHashMap<>()));
    }

    private void connectConditionsInsideMap(GenericTreeNode<Short> node, Set<String> connectedNodeIds,
                                            Set<GenericTreeNode<Short>> expandedNodes) {
        for (GenericTreeNode<Short> child : node.getChildren()) {
            String nodeId = treeUtil.getObjIdToNodeIdMap().get(child.getData());
            if (connectedNodeIds.add(nodeId)) {
                connectConditionNode(nodeId);
            }
            // Everything below a shared node was connected the first time the node was reached
            if (expandedNodes.add(child)) {
                connectConditionsInsideMap(child, connectedNodeIds, expandedNodes);
            }
        }
    }

    private void connectConditionNode(String nodeId) {
        List<Step> stepList = mapStorage.getNodeIdStepMap().get(nodeId);
        Short objId = treeUtil.getNodeIdObjIdMap().get(nodeId);
        List<String> nodes = Arrays.asList(treeUtil.getObjIdOutputNodesMap().get(objId).split(REGEXP));
        if (stepList != null && !stepList.isEmpty()) {
            Step step = stepList.get(stepList.size() - 1);
            if (step != null) {
                if (Step.TypeEnum.CONDITION.equals(step.getType())) {
                    fillConditionStepWithInnerSteps(step, nodeId, nodes);
                } else if (Step.TypeEnum.BRANCH.equals(step.getType())) {
                    fillBranchStepWithInnerSteps(step, nodeId);
                }
            }
        }
    }

    private void finalConnectionOfNodes(GenericTreeNode<Short> node, Deque<Short> objIDStack) {
        finalConnectionOfNodes(node, objIDStack, new IdentityHashMap<>());
    }

    /**
     * Appends the steps of every node to the condition branch it belongs to (the branch of the innermost enclosing
     * split/filter cell, i.e. the top of {@code objIDStack}).
     * <p>
     * The work done for a node depends only on the node and the branch on top of the stack. Repeating a visit for
     * the same (node, branch) pair is a no-op: every step is already in the branch (steps are only ever appended),
     * a cross-branch link step is not added next to its own link label, and a branch ending in a link never
     * receives more steps. {@code visitedBranchesByNode} records the visited pairs so that a shared (fan-in) node
     * reached along many paths is only expanded once per branch.
     */
    private void finalConnectionOfNodes(GenericTreeNode<Short> node, Deque<Short> objIDStack,
                                        Map<GenericTreeNode<Short>, Set<Short>> visitedBranchesByNode) {
        if (!visitedBranchesByNode.computeIfAbsent(node, key -> new HashSet<>()).add(objIDStack.peek())) {
            return;
        }
        if (mapStorage.isUseCrossBranchLinks() && !objIDStack.isEmpty()) {
            String branchNodeId = treeUtil.getObjIdToNodeIdMap().get(objIDStack.peek());
            ConditionBranch branch = mapStorage.getBranchNodeIdConditionBranchMap().get(branchNodeId);
            if (branch != null && branch.getSteps() != null && !branch.getSteps().isEmpty()
                    && branch.getSteps().get(branch.getSteps().size() - 1).getDecisionNodeLinkTarget() != null) {
                return;
            }
        }
        boolean branchNode = hasConnectedBranches(node) || node.getNumberOfChildren() > 1;
        for (GenericTreeNode<Short> innerNode : node.getChildren()) {
            Short objId = innerNode.getData();
            if (branchNode) {
                objIDStack.push(objId);
            } else {
                addStep(objId, objIDStack);
            }
            finalConnectionOfNodes(innerNode, objIDStack, visitedBranchesByNode);
            if (branchNode) {
                objIDStack.pop();
            }
        }
    }

    private boolean hasConnectedBranches(GenericTreeNode<Short> node) {
        String nodeId = treeUtil.getObjIdToNodeIdMap().get(node.getData());
        List<Step> steps = mapStorage.getNodeIdStepMap().get(nodeId);
        if (steps == null || steps.isEmpty()) {
            return false;
        }

        Step lastStep = steps.get(steps.size() - 1);
        return Step.TypeEnum.CONDITION.equals(lastStep.getType())
                || Step.TypeEnum.BRANCH.equals(lastStep.getType());
    }

    private void addStep(Short objId, Deque<Short> objIDStack) {
        if (!objIDStack.isEmpty()) {
            Short lastObjId = objIDStack.peek();
            String nodeIdInsideBranchOrCondition = treeUtil.getObjIdToNodeIdMap().get(lastObjId);
            ConditionBranch conditionBranch = mapStorage.getBranchNodeIdConditionBranchMap().get(nodeIdInsideBranchOrCondition);
            
            String nodeIdToInsert = treeUtil.getObjIdToNodeIdMap().get(objId);

            if (mapStorage.isUseCrossBranchLinks()) {
                boolean skipDownstreamNodes = false;
                List<Step> downstreamSteps = conditionBranch.getSteps();
                if (downstreamSteps != null && !downstreamSteps.isEmpty()) {
                    Step finalStep = downstreamSteps.get(downstreamSteps.size() - 1);
                    if (finalStep.getDecisionNodeLinkTarget()!=null) {
                        // Do not add any downstream nodes beyond the cross-branch link
                        skipDownstreamNodes = true;
                    }
                }
    
                if (skipDownstreamNodes == false) {
                    List<Step> stepList = mapStorage.getNodeIdStepMap().get(nodeIdToInsert);
                    if (stepList != null && !stepList.isEmpty()) {
                        // Check if a link label exists on the first step
                        Step initialStep = stepList.get(0);
                        String linkLabel = initialStep.getLinkLabel();
                        if (linkLabel!=null) {
                            if (mapStorage.getDecisionNodeLinkMap().contains(linkLabel)) {
                                // If path has already been processed then use a cross-branch link step
                                stepList = mapStorage.getCrossBranchLinkSteps().get(linkLabel); 
                            } else {
                                // First time this link label has been processed
                                // The processStepList method below will be called with the original stepList (with the link label) 
                                // Next time we find a step list with this link label we'll use a cross-branch link step
                                mapStorage.getDecisionNodeLinkMap().add(linkLabel);
                            }
                        }

                        processStepList(conditionBranch, stepList);
                    }
                }
            } else {
                List<Step> stepList = mapStorage.getNodeIdStepMap().get(nodeIdToInsert);
                if (stepList != null && !stepList.isEmpty()) {
                    processStepList(conditionBranch, stepList);
                }
            }
        }
    }

    /**
     * Appends the steps of {@code stepList} that are not already in the branch. Presence is checked by identity:
     * Step.equals is a deep, value based comparison (through branch cases and their nested branches), which is
     * both expensive and not what is meant here - the same step object must not be added twice.
     */
    private void processStepList(ConditionBranch conditionBranch, List<Step> stepList) {
        List<Step> branchSteps = new ArrayList<>(conditionBranch.getSteps());
        Set<Step> stepsInBranch = Collections.newSetFromMap(new IdentityHashMap<>());
        stepsInBranch.addAll(branchSteps);

        Set<String> linkTargets = new HashSet<>();
        if (mapStorage.isUseCrossBranchLinks()) {
            for (Step step : stepList) {
                if (step.getDecisionNodeLinkTarget() != null) {
                    linkTargets.add(step.getDecisionNodeLinkTarget());
                }
            }
        }

        boolean changed = false;
        for (Step step : stepList) {
            if (!linkTargets.isEmpty() && containsLinkLabel(branchSteps, linkTargets)) {
                // Avoid a cross-branch link that links to itself
                // This can occur when two immediate upstream nodes feed into the same reply node
                conditionBranch.setSteps(branchSteps);
                return;
            }

            if (stepsInBranch.add(step)) {
                branchSteps.add(step);
                changed = true;
            }
        }
        if (changed) {
            conditionBranch.setSteps(branchSteps);
        }
    }

    private boolean containsLinkLabel(List<Step> steps, Set<String> linkLabels) {
        for (Step step : steps) {
            if (step.getLinkLabel() != null && linkLabels.contains(step.getLinkLabel())) {
                return true;
            }
        }
        return false;
    }

    private void fillConditionStepWithInnerSteps(Step step, String stepNodeId, List<String> outputNodes) {
        MultiSelectNodeDataDO multiSelectNodeDataDO
                = (MultiSelectNodeDataDO) treeUtil.getNodeIdObjectHashMap().get(stepNodeId).getObject();
        String remainderOutputCellID = multiSelectNodeDataDO.getRemainderOutputCellID();
        // An ID condition step only has true/false branches, so a second selected output would silently replace the
        // first one (and any cross-branch links into it) rather than migrate.
        long selectedOutputs = outputNodes.stream().filter(node -> !node.equals(remainderOutputCellID)).count();
        if (selectedOutputs > 1) {
            throw new IllegalStateException("Filter node '" + multiSelectNodeDataDO.getNodeName() + "' (" + stepNodeId
                + ") has " + selectedOutputs + " selected outputs; only one selected output plus an optional remainder is supported");
        }
        for (String node : outputNodes) {
            ConditionBranch conditionBranch = commonProcessing.createConditionBranch(node, true);
            mapStorage.getBranchNodeIdConditionBranchMap().put(node, conditionBranch);
            if (node.equals(remainderOutputCellID)) {
                step.setOnFalse(conditionBranch);
            } else {
                step.setOnTrue(conditionBranch);
            }
        }
    }

    private void fillBranchStepWithInnerSteps(Step step, String stepNodeId) {
        SplitNodeDataDO splitNodeDataDO
                = (SplitNodeDataDO) treeUtil.getNodeIdObjectHashMap().get(stepNodeId).getObject();

        for (SplitNodeDataDO.LineItems.SplitNodeLineItemDO splitNodeLineItemDO
                : splitNodeDataDO.getLineItems().getSplitNodeLineItemDOs()) {
            if (RTDM2IDConstants.REMAINDER_VALUE.equals(splitNodeLineItemDO.getValue())) {
                ConditionBranch conditionBranch = step.getDefaultCase();
                if (conditionBranch == null || conditionBranch.getSteps() == null || conditionBranch.getSteps().isEmpty()) {
                    conditionBranch = commonProcessing.createConditionBranch(splitNodeLineItemDO.getCellId(), true);
                }
                mapStorage.getBranchNodeIdConditionBranchMap().putIfAbsent(splitNodeLineItemDO.getCellId(), conditionBranch);
                step.setDefaultCase(conditionBranch);
            }
        }

        for (BranchCase branchCase : step.getBranchCases()) {
            ConditionBranch conditionBranch = mapStorage.isUseCrossBranchLinks()
                    ? null : mapStorage.getBranchNodeIdConditionBranchMap().get(branchCase.getLabel());
            if (conditionBranch == null) {
                conditionBranch = commonProcessing.createConditionBranch(branchCase.getLabel(), true);
            }
            mapStorage.getBranchNodeIdConditionBranchMap().putIfAbsent(branchCase.getLabel(), conditionBranch);
            branchCase.setOnTrue(conditionBranch); // this is also updating the nodeIdStepMap entry since the calling method (connectConditionsInsideMap) is iterating the steps from the nodeIdStepMap
        }
    }
}
