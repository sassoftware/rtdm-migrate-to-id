/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.model.id.decision.*;
import com.sas.rtdm2id.model.rtdm.MultiSelectNodeDataDO;
import com.sas.rtdm2id.model.rtdm.extension.Children;
import com.sas.rtdm2id.model.rtdm.extension.FilterNodeDO;
import com.sas.rtdm2id.model.rtdm.extension.LogicNodeDO;
import com.sas.rtdm2id.model.rtdm.extension.RootNode;
import com.sas.rtdm2id.model.rtdm.extension.VarRef;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.*;

@Component
public class MultiSelectNodeConverter {
    private static final String TODAYS_DATE_AND_TIME_LABEL = "Today's Date and Time";

    private final CommonProcessing commonProcessing;

    public MultiSelectNodeConverter(CommonProcessing commonProcessing) {
        this.commonProcessing = commonProcessing;
    }

    public List<Step> createConditionByFilter(MultiSelectNodeDataDO multiSelectNodeDataDO, Decision decision) {
        final RootNode rootNode = multiSelectNodeDataDO.getSqlExpression().getRootNode();
        final List<FilterNodeDO> filterNodeDOs = getFilterNodesFromMultiSelectNode(multiSelectNodeDataDO);
        final String filterExpression = buildFilterExpression(rootNode);

        List<Step> stepList = new LinkedList<>();
        Step step = new Step();
        step.setType(Step.TypeEnum.CONDITION);
        ConditionStep conditionStep = new ConditionStep();
        createDecisionVariables(filterNodeDOs, stepList, step, decision,  multiSelectNodeDataDO.getNodeId());
        step.setName(multiSelectNodeDataDO.getNodeName());
        step.setConditionExpression(filterExpression);
        step.setCondition(conditionStep);
        commonProcessing.addStep(stepList, step, multiSelectNodeDataDO.getNodeId());

        return stepList;
    }

    private void createDecisionVariables(List<FilterNodeDO> filterNodeDOs, List<Step> stepList, Step step, Decision decision, String nodeId) {
            for (FilterNodeDO filterNodeDO : filterNodeDOs) {
                final VarRef filterNodeVarRef = filterNodeDO.getVarRef();
                commonProcessing.checkForCalcVariable(filterNodeVarRef.getVarInfoId(),filterNodeVarRef.getType(), stepList, step, filterNodeVarRef.getVarName(), nodeId);
                String globalVariableName = getGlobalVariableName(filterNodeDO);
                if (globalVariableName != null) {
                    commonProcessing.createGlobalVariableForDataPicker(
                            GLOBALS_FOLDER + "." + globalVariableName,
                            globalVariableName,
                            filterNodeVarRef.getType(),
                            decision);
                }
                if (commonProcessing.checkForGlobalVariable(filterNodeVarRef, step, filterNodeVarRef.getVarName(), decision, false)) {
                    commonProcessing.addNewSignatureItem(filterNodeVarRef, "none", decision);
                }
                if (SOURCE_TODAYS_DATE_AND_TIME.equals(getVariableReferenceName(filterNodeDO.getFormattedValues()))) {
                    addTodaysDateAndTimeSignature(decision);
                }
            }
    }

    private void addTodaysDateAndTimeSignature(Decision decision) {
        VarRef varRef = new VarRef();
        varRef.setVarInfoId(SOURCE_TODAYS_DATE_AND_TIME);
        varRef.setVarName(SOURCE_TODAYS_DATE_AND_TIME);
        varRef.setType(DATE_CONSTANT);
        commonProcessing.addNewSignatureItem(varRef, NONE_DIRECTION, decision, DATE_CONSTANT);
    }

    private String buildFilterExpression(RootNode rootNode) {
        Children rootNodeChildren = rootNode.getChildren();
        if (rootNodeChildren == null
                || (rootNodeChildren.getFilterNodeDOs().isEmpty() && rootNodeChildren.getLogicNodeDOs().isEmpty())) {
            return buildFilterExpression(createFilterNode(rootNode));
        }

        return buildLogicExpression(rootNode.getType(), rootNodeChildren, false);
    }

    private String buildLogicExpression(String operator, Children children, boolean wrap) {
        List<String> childExpressions = new ArrayList<>();
        if (children != null) {
            for (FilterNodeDO filterNodeDO : children.getFilterNodeDOs()) {
                childExpressions.add(buildFilterExpression(filterNodeDO));
            }
            for (LogicNodeDO logicNodeDO : children.getLogicNodeDOs()) {
                String expression = buildLogicExpression(logicNodeDO.getType(), logicNodeDO.getChildren(), true);
                if (!expression.isEmpty()) {
                    childExpressions.add(expression);
                }
            }
        }

        String expression = String.join(" " + operator + " ", childExpressions);
        return wrap && !expression.isEmpty() ? "(" + expression + ")" : expression;
    }

    private String buildFilterExpression(FilterNodeDO filterNodeDO) {
        String variableName = getFilterVariableName(filterNodeDO);
        if (LIKE.equals(filterNodeDO.getOperator()) && filterNodeDO.getFormattedValues() != null) {
            String[] likeValues = filterNodeDO.getFormattedValues().split(REGEXP);
            if (likeValues.length > 1) {
                return Arrays.stream(likeValues)
                        .map(value -> variableName + " LIKE '" + value + "'")
                        .collect(Collectors.joining(" or ", "(", ")"));
            }
        }

        return variableName
                + " "
                + convertOperator(filterNodeDO.getOperator())
                + " "
                + getFilterValue(filterNodeDO);
    }

    private String getFilterVariableName(FilterNodeDO filterNodeDO) {
        VarRef varRef = filterNodeDO.getVarRef();
        String variableName = varRef.getVarName();
        String varInfoId = varRef.getVarInfoId();
        if (varInfoId != null && varInfoId.startsWith(EVENTS_FOLDER + ".")) {
            variableName = varInfoId;
        }

        variableName = commonProcessing.sanitizeVariableName(variableName);
        String dateType = filterNodeDO.getDateType();
        if (dateType == null || dateType.trim().isEmpty() || "whole".equalsIgnoreCase(dateType)) {
            return variableName;
        }

        switch (dateType.toLowerCase(Locale.ROOT)) {
            case "day":
                return "DAY(" + variableName + ")";
            case "month":
                return "MONTH(" + variableName + ")";
            case "year":
                return "YEAR(" + variableName + ")";
            default:
                return variableName;
        }
    }

    private FilterNodeDO createFilterNode(RootNode rootNode) {
        final FilterNodeDO filterNodeDO = new FilterNodeDO();
        filterNodeDO.setValues(rootNode.getValues());
        filterNodeDO.setVarRef(rootNode.getVarRef());
        filterNodeDO.setOperator(rootNode.getOperator());
        filterNodeDO.setChildren(rootNode.getChildren());
        filterNodeDO.setFormattedValues(rootNode.getFormattedValues());
        return filterNodeDO;
    }

    private String getFilterValue(final FilterNodeDO filterNodeDO) {
        String filterValue = filterNodeDO.getValues() == null ? null : filterNodeDO.getValues().getText();
        String formattedFilterValue = filterNodeDO.getFormattedValues();
        String globalVariableName = getGlobalVariableName(filterNodeDO);

        if (globalVariableName != null) {
            return commonProcessing.sanitizeVariableName(globalVariableName);
        }

        if (filterValue == null || filterValue.trim().isEmpty()) {
            String variableReferenceName = getVariableReferenceName(formattedFilterValue);
            if (variableReferenceName != null) {
                return commonProcessing.sanitizeVariableName(variableReferenceName);
            }
        }

        if (filterNodeDO.getOperator().equals("inList")) {
            return Arrays.stream(formattedFilterValue.split(","))
                    .map(value -> "'" + value + "'")
                    .collect(Collectors.joining(",", "(", ")"));
        }

        if (filterNodeDO.getOperator().equals(IS_MISSING) && filterNodeDO.getVarRef().getType().equalsIgnoreCase(NUMERIC_CONSTANT)) {
            return ".";
        }

        if (filterNodeDO.getOperator().equals(IS_MISSING)) {
            return "''";
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

        return filterValue;
    }

    private String getGlobalVariableName(FilterNodeDO filterNodeDO) {
        String formattedFilterValue = filterNodeDO.getFormattedValues();
        String globalPrefix = SOURCE_GLOBAL + ".";
        if (formattedFilterValue != null && formattedFilterValue.startsWith(globalPrefix)) {
            return formattedFilterValue.substring(globalPrefix.length());
        }
        return null;
    }

    private String getVariableReferenceName(String formattedFilterValue) {
        if (formattedFilterValue == null) {
            return null;
        }

        if (isTodaysDateAndTime(formattedFilterValue)) {
            return SOURCE_TODAYS_DATE_AND_TIME;
        }

        int separatorIndex = formattedFilterValue.indexOf('.');
        if (separatorIndex <= 0) {
            return null;
        }

        String source = formattedFilterValue.substring(0, separatorIndex);
        if (SOURCE_EVENT.equals(source)) {
            return EVENTS_FOLDER + formattedFilterValue.substring(separatorIndex);
        }
        if (SOURCE_GLOBAL.equals(source)) {
            return formattedFilterValue.substring(separatorIndex + 1);
        }

        return null;
    }

    private boolean isTodaysDateAndTime(String formattedFilterValue) {
        return DATE_AND_TIME_TODAY.equals(formattedFilterValue)
                || SOURCE_TODAYS_DATE_AND_TIME.equals(formattedFilterValue)
                || TODAYS_DATE_AND_TIME_LABEL.equals(formattedFilterValue);
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
            filterNodeDOs.add(createFilterNode(rootNode));
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
