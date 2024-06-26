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
import java.util.stream.Collectors;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.CUSTOM_CONSTANT;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.DATAGRID_CONSTANT;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.INPUT_DIRECTION;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.OUTPUT_DIRECTION;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.REGEXP;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.SOURCE_EVENT;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.SOURCE_REPLY;

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
    private final TreeUtil treeUtil;
    private final MapStorage mapStorage;

    public Converter(ProcessNodeConverter processNodeConverter, SubDiagramNodeConverter subDiagramNodeConverter, ReplyNodeConverter replyNodeConverter, MultiSelectNodeConverter multiSelectNodeConverter, SplitNodeConverter splitNodeConverter, ConfirmContactNodeConverter confirmContactNodeConverter, CommonProcessing commonProcessing, AssignmentNodeConverter assignmentNodeConverter,TreeUtil treeUtil, MapStorage mapStorage) {
        this.processNodeConverter = processNodeConverter;
        this.subDiagramNodeConverter = subDiagramNodeConverter;
        this.replyNodeConverter = replyNodeConverter;
        this.multiSelectNodeConverter = multiSelectNodeConverter;
        this.splitNodeConverter = splitNodeConverter;
        this.confirmContactNodeConverter = confirmContactNodeConverter;
        this.commonProcessing = commonProcessing;
        this.assignmentNodeConverter = assignmentNodeConverter;
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

        GenericTreeNode<Short> root = tree.getRoot();
        walkTree(root, decision);
        connectConditionsInsideMap();
        ConditionDecisionStep conditionDecisionStep = new ConditionDecisionStep();
        fillConditionDecisionStep(root.getChildren().get(0).getData(), conditionDecisionStep);
        Deque<Short> objIDStack = new ArrayDeque<>();
        finalConnectionOfNodes(root, objIDStack);

        decision.setFlow(conditionDecisionStep);
        addMissingVars(decision);
        return decision;
    }

    private void addMissingVars(Decision decision) {
        Set<String> varNames = new HashSet<String>();
        List<SignatureTerm> signatureTerms = decision.getSignature();
        signatureTerms.forEach(
                signatureTerm -> {
                    varNames.add(signatureTerm.getName());
                }
        );
        ConditionDecisionStep decisionFlow = decision.getFlow();
        if (decisionFlow!=null && decisionFlow.getFlow()!=null){
            decisionFlow.getFlow().forEach(
                    step -> {
                        if(step.getMappings()!=null){
                            step.getMappings().forEach(
                                mapping -> {
                                    String decisionVarName = mapping.getTargetDecisionTermName();
                                    if(!varNames.contains(decisionVarName)){
                                        SignatureTerm newTerm = new SignatureTerm();
                                        newTerm.setName(decisionVarName);
                                        newTerm.setDirection(SignatureTerm.DirectionEnum.fromValue(mapping.getDirection().toString()));
                                        newTerm.setDataType(SignatureTerm.DataTypeEnum.UNKNOWN);
                                        signatureTerms.add(newTerm);
                                        varNames.add(decisionVarName);
                                    }
                                }
                        );
                        }
                    }
            );
        }
    }

    private void processEventVars(Decision decision
            , IBVariableDO ibVariableDO, String direction
            , ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value) {
        commonProcessing.addNewSignatureItem(ibVariableDO, direction, decision, direction.equals(INPUT_DIRECTION) ? SOURCE_EVENT : null);
        if (value != null) {
            VarRef varRef = VarRefMapper.VAR_REF_MAPPER.varRefGet(ibVariableDO.getValue().getValueTypeVarInfoDO());
            if (commonProcessing.checkForGlobalVariable(varRef, null, ibVariableDO.getName(), decision, false)) {
                commonProcessing.checkForAdditionalVariables(ibVariableDO.getName(), varRef, "none", decision);
            }
        }
    }

    private void walkTree(GenericTreeNode<Short> node, Decision decision) {
        for (GenericTreeNode<Short> innerNode : node.getChildren()) {
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

            walkTree(innerNode, decision);
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
                commonProcessing.addStep(stepList, confirmContactNodeConverter.addCustomObjectStep(cHandRHNodeDataDO, decision, objId, commonProcessing.makeConfirmContactNodeRuleSetName(cHandRHNodeDataDO)), cHandRHNodeDataDO.getNodeId());
                commonProcessing.addStep(stepList, confirmContactNodeConverter.createRecordContactStep(cHandRHNodeDataDO, decision), cHandRHNodeDataDO.getNodeId());
                break;
            case "stagedTreatment":
                StagedTreatmentsNodeDataDO stagedTreatmentsNodeDataDO = (StagedTreatmentsNodeDataDO) rtdmObject.getObject();
                stepList = processNodeConverter.addCustomObjectStep(null, decision, objId, commonProcessing.makeStagedTreatmentName(stagedTreatmentsNodeDataDO));
                break;  
            case "removeStagedTreatment":
                RemoveStagedTreatmentsNodeDataDO removeStagedTreatmentsNodeDataDO = (RemoveStagedTreatmentsNodeDataDO) rtdmObject.getObject();
                stepList = processNodeConverter.addCustomObjectStep(null, decision, objId, commonProcessing.makeRemoveStagedTreatmentName(removeStagedTreatmentsNodeDataDO));
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

    private void connectConditionsInsideMap() {
        Map<String, List<Step>> nodeIdStepMap = mapStorage.getNodeIdStepMap();
        for (Map.Entry<String, List<Step>> entry : nodeIdStepMap.entrySet()) {
            List<Step> stepList = entry.getValue();
            Short objId = treeUtil.getNodeIdObjIdMap().get(entry.getKey());
            List<String> nodes = Arrays.asList(treeUtil.getObjIdOutputNodesMap().get(objId).split(REGEXP));
            if (stepList != null && !stepList.isEmpty()) {
                Step step = stepList.get(stepList.size() - 1);
                if (step != null) {
                    if (Step.TypeEnum.CONDITION.equals(step.getType())) {
                        fillConditionStepWithInnerSteps(step, entry.getKey(), nodes);
                    } else if (Step.TypeEnum.BRANCH.equals(step.getType())) {
                        fillBranchStepWithInnerSteps(step, entry.getKey());
                    }
                }
            }
        }
    }

    private void finalConnectionOfNodes(GenericTreeNode<Short> node, Deque<Short> objIDStack) {
        for (GenericTreeNode<Short> innerNode : node.getChildren()) {
            Short objId = innerNode.getData();
            addStep(objId, objIDStack);
            if (node.getNumberOfChildren() > 1) {
                objIDStack.push(objId);
            }
            finalConnectionOfNodes(innerNode, objIDStack);
        }
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
    
                        // Prevent a decisionNodeLinkTarget being added onto a step list containing a linkLabel
                        if (!conditionBranch.getSteps().isEmpty()) {
                            Step firstConditionBranchStep = conditionBranch.getSteps().get(0);
                            if (firstConditionBranchStep.getDecisionNodeLinkTarget()!=null) {
                                if (!stepList.isEmpty()) {
                                    Step firstStep = stepList.get(0);
                                    if (firstStep.getLinkLabel()!=null) {
                                        conditionBranch.setSteps(stepList);
                                    }
                                }
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

    private void processStepList(ConditionBranch conditionBranch, List<Step> stepList) {
        for (Step step : stepList) {
            List<Step> copy = new LinkedList<>(conditionBranch.getSteps());
            int indexOf = copy.indexOf(step);

            if (mapStorage.isUseCrossBranchLinks()) {
                if (!copy.isEmpty() && !stepList.isEmpty()) {
                    for (Step copyStep : copy) {
                        String copyStepLinkLabel = copyStep.getLinkLabel();
    
                        if (copyStepLinkLabel!=null) {
                            for (Step step1 : stepList) {
                                String stepDecisionNodeLinkTarget = step1.getDecisionNodeLinkTarget();
                                
                                if (stepDecisionNodeLinkTarget!=null) {
                                    if (copyStepLinkLabel.equals(stepDecisionNodeLinkTarget)) {
                                        // Avoid a cross-branch link that links to itself
                                        // This can occur when two immediate upstream nodes feed into the same reply node
                                        conditionBranch.setSteps(copy);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (indexOf == -1) {
                copy.add(step);
                conditionBranch.setSteps(copy);
            } else {
                Step stepCopy = copy.get(indexOf);
                if (stepCopy.getMappings() != null) {
                    List<Mapping> differences = stepCopy.getMappings().stream()
                            .filter(element -> !step.getMappings().contains(element))
                            .collect(Collectors.toList());
                    if (!differences.isEmpty()) {
                        copy.add(step);
                        conditionBranch.setSteps(copy);
                    }
                }
            }
        }
    }

    private void fillConditionStepWithInnerSteps(Step step, String stepNodeId, List<String> outputNodes) {
        MultiSelectNodeDataDO multiSelectNodeDataDO
                = (MultiSelectNodeDataDO) treeUtil.getNodeIdObjectHashMap().get(stepNodeId).getObject();
        String remainderOutputCellID = multiSelectNodeDataDO.getRemainderOutputCellID();
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
                ConditionBranch conditionBranch = commonProcessing.createConditionBranch(splitNodeLineItemDO.getCellId(), true);
                mapStorage.getBranchNodeIdConditionBranchMap().put(splitNodeLineItemDO.getCellId(), conditionBranch);             
                if (RTDM2IDConstants.REMAINDER_VALUE.equals(splitNodeLineItemDO.getValue())) {
                    step.setDefaultCase(conditionBranch);
                }
            }
        }

        for (BranchCase branchCase : step.getBranchCases()) {
            ConditionBranch conditionBranch = commonProcessing.createConditionBranch(branchCase.getLabel(), true);
            mapStorage.getBranchNodeIdConditionBranchMap().put(branchCase.getLabel(), conditionBranch);
            branchCase.setOnTrue(conditionBranch); // this is also updating the nodeIdStepMap entry since the calling method (connectConditionsInsideMap) is iterating the steps from the nodeIdStepMap
        }
    }
}
