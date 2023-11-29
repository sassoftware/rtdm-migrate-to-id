/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.model.dto.rtdm.model.LogicNode;
import com.sas.rtdm2id.model.id.decision.*;
import com.sas.rtdm2id.model.rtdm.MultiSelectNodeDataDO;
import com.sas.rtdm2id.model.rtdm.extension.Children;
import com.sas.rtdm2id.model.rtdm.extension.FilterNodeDO;
import com.sas.rtdm2id.model.rtdm.extension.RootNode;
import com.sas.rtdm2id.model.rtdm.extension.VarRef;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.*;

@Component
public class MultiSelectNodeConverter {
    private final CommonProcessing commonProcessing;

    public MultiSelectNodeConverter(CommonProcessing commonProcessing) {
        this.commonProcessing = commonProcessing;
    }

    public List<Step> createConditionByFilter(MultiSelectNodeDataDO multiSelectNodeDataDO, Decision decision) {
        final List<LogicNode> logicNodeChildrenAndOperators = getLogicNodeChildrenAndOperators(multiSelectNodeDataDO);

        final List<FilterNodeDO> filterNodeDOs = getFilterNodesFromMultiSelectNode(multiSelectNodeDataDO);
        final String filterExpression = buildFilterExpression(filterNodeDOs, logicNodeChildrenAndOperators);

        List<Step> stepList = new LinkedList<>();
        Step step = new Step();
        step.setType(Step.TypeEnum.CONDITION);
        ConditionStep conditionStep = new ConditionStep();
        createDecisionVariables(filterNodeDOs, stepList, step, decision);
        step.setName(multiSelectNodeDataDO.getNodeName());
        step.setConditionExpression(filterExpression);
        step.setCondition(conditionStep);
        stepList.add(step);

        return stepList;
    }

    private List<LogicNode> getLogicNodeChildrenAndOperators(final MultiSelectNodeDataDO multiSelectNodeDataDO) {
        final RootNode rootNode = multiSelectNodeDataDO.getSqlExpression().getRootNode();
        final Children rootNodeChildren = rootNode.getChildren();

        final List<LogicNode> logicNodeChildrenAndOperators = new ArrayList<>();

        logicNodeChildrenAndOperators.add(new LogicNode(rootNode.getObjid(), getChildObjIds(rootNodeChildren), rootNode.getType()));
        findAllLogicNodesAndOperators(rootNodeChildren, logicNodeChildrenAndOperators);

        return logicNodeChildrenAndOperators;
    }

    private void findAllLogicNodesAndOperators(Children children, List<LogicNode> logicNodeChildrenAndOperators) {
        children.getLogicNodeDOs().forEach(logicNodeDO -> {
            logicNodeChildrenAndOperators.add(new LogicNode(logicNodeDO.getObjid(), getChildObjIds(logicNodeDO.getChildren()), logicNodeDO.getType()));
            findAllLogicNodesAndOperators(logicNodeDO.getChildren(), logicNodeChildrenAndOperators);
        });
    }

    private List<String> getChildObjIds(Children children) {
        final List<String> childObjIds = new ArrayList<>();

        children.getFilterNodeDOs().forEach(filterNodeDO -> {
            childObjIds.add(filterNodeDO.getObjid());
        });
        children.getLogicNodeDOs().forEach(logicNodeDO -> {
            childObjIds.add(logicNodeDO.getObjid());
        });


        return childObjIds;
    }

    private void createDecisionVariables(List<FilterNodeDO> filterNodeDOs, List<Step> stepList, Step step, Decision decision) {
            for (FilterNodeDO filterNodeDO : filterNodeDOs) {
                final VarRef filterNodeVarRef = filterNodeDO.getVarRef();
                commonProcessing.checkForCalcVariable(filterNodeVarRef.getVarInfoId(),filterNodeVarRef.getType(), stepList, step, filterNodeVarRef.getVarName());
                if (commonProcessing.checkForGlobalVariable(filterNodeVarRef, step, filterNodeVarRef.getVarName(), decision, false)) {
                    commonProcessing.addNewSignatureItem(filterNodeVarRef, "none", decision);
                }
            }
    }

    private String buildFilterExpression(List<FilterNodeDO> allFilterNodesGroupedByVariable, List<LogicNode> logicNodeChildrenAndOperators) {
        StringBuilder filterExpression = new StringBuilder();

        for (FilterNodeDO filterNodeDO : allFilterNodesGroupedByVariable) {
            filterExpression.append(commonProcessing.sanitizeVariableName(filterNodeDO.getVarRef().getVarName()))
                .append(" ")
                .append(convertOperator(filterNodeDO.getOperator()))
                .append(" ")
                .append(getFilterValue(filterNodeDO))
                .append(" ")
                .append(getFilterOperator(logicNodeChildrenAndOperators, filterNodeDO.getObjid()))
                .append(" ");
        }

        return filterExpression.toString();
    }

    private String getFilterOperator(final List<LogicNode> logicNodeChildrenAndOperators, final String objId) {
        Optional<LogicNode> optionalLogicNode = getLogicNodeOperator(logicNodeChildrenAndOperators, objId);

        if (optionalLogicNode.isPresent()) {
            return optionalLogicNode.get().getOperator();
        } else {
            return "";
        }
    }

    private Optional<LogicNode> getLogicNodeOperator(List<LogicNode> logicNodeChildrenAndOperators, String objId) {
        Optional<LogicNode> optionalLogicNode = logicNodeChildrenAndOperators.stream()
                .filter(logicNode -> logicNode.getChildNodeIds().contains(objId))
                .filter(logicNode -> logicNode.getChildNodeIds().indexOf(objId) < (logicNode.getChildNodeIds().size() - 1))
                .findFirst();

        Optional<LogicNode> optionalLogicNode1 = logicNodeChildrenAndOperators.stream()
                .filter(logicNode -> logicNode.getChildNodeIds().contains(objId))
                .findFirst();

        if (optionalLogicNode.isPresent()) {
            return optionalLogicNode;
        } else {
            if (optionalLogicNode1.isPresent()) {
                return getLogicNodeOperator(logicNodeChildrenAndOperators, optionalLogicNode1.get().getLogicNodeId());
            } else {
                return Optional.empty();
            }
        }
    }

    private String getFilterValue(final FilterNodeDO filterNodeDO) {
        String filterValue = filterNodeDO.getValues().getText();
        String formattedFilterValue = filterNodeDO.getFormattedValues();

        if (filterNodeDO.getOperator().equals("inList")) {
            return Arrays.stream(formattedFilterValue.split(","))
                    .map(value -> "'" + value + "'")
                    .collect(Collectors.joining(",", "(", ")"));
        }

        if (filterNodeDO.getOperator().equals(IS_MISSING) && filterNodeDO.getVarRef().getType().equalsIgnoreCase(NUMERIC_CONSTANT)) {
            return ".";
        }

        if (filterNodeDO.getVarRef().getType().equalsIgnoreCase(DATE_CONSTANT) && formattedFilterValue != null) {
            if ("whole".equals(filterNodeDO.getDateType())) {
                return "'" + formattedFilterValue + "'";
            }
            return formattedFilterValue;
        }

        if (filterNodeDO.getVarRef().getType().equalsIgnoreCase(CHAR_CONSTANT)) {
            return "'" + filterValue + "'";
        }

        if (filterNodeDO.getOperator().equals(IS_MISSING)) {
            return "''";
        }

        return filterValue;
    }

    private String convertOperator(final String operator) {
        switch (operator) {
            case LESS_THAN_OR_EQUALS_TO:
                return "<=";
            case GREATER_THAN_OR_EQUALS_TO:
                return ">=";
            case LIKE:
                return "LIKE";
            case IN_LIST:
                return "IN";
            default:
                return "=";
        }

    }

    private List<FilterNodeDO> getFilterNodesFromMultiSelectNode(MultiSelectNodeDataDO multiSelectNodeDataDO) {
        final List<FilterNodeDO> filterNodeDOs = new ArrayList<>();
        final RootNode rootNode = multiSelectNodeDataDO.getSqlExpression().getRootNode();
        final Children rootNodeChildren = rootNode.getChildren();

        if (rootNodeChildren.getLogicNodeDOs().isEmpty() && rootNodeChildren.getFilterNodeDOs().isEmpty()) {
            // In this case the RootNode is the FilterNode so convert it to a FilterNode for consistent processing of conditionExpression
            final FilterNodeDO filterNodeDO = new FilterNodeDO();
            filterNodeDO.setValues(rootNode.getValues());
            filterNodeDO.setVarRef(rootNode.getVarRef());
            filterNodeDO.setOperator(rootNode.getOperator());
            filterNodeDO.setChildren(rootNodeChildren);
            filterNodeDO.setFormattedValues(rootNode.getFormattedValues());

            filterNodeDOs.add(filterNodeDO);
        } else {
            // recursively search through the rootNode looking for instances of FilterNodeDO
            findAllInstancesOfFilterNodeDO(rootNode.getChildren(), filterNodeDOs);
        }

        return filterNodeDOs;
    }

    private void findAllInstancesOfFilterNodeDO(Children children, List<FilterNodeDO> filterNodeDOs) {
        filterNodeDOs.addAll(children.getFilterNodeDOs());

        children.getLogicNodeDOs().forEach(logicNodeDO -> findAllInstancesOfFilterNodeDO(logicNodeDO.getChildren(), filterNodeDOs));
    }

}
