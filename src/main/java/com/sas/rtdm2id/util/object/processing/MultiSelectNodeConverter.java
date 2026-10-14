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
import com.sas.rtdm2id.model.rtdm.extension.Values;
import com.sas.rtdm2id.model.rtdm.extension.VarRef;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.*;

@Component
public class MultiSelectNodeConverter {
    private static final String TODAYS_DATE_AND_TIME_LABEL = "Today's Date and Time";

    // RTDM filter operators. Only equals, lessThanEquals, greaterThanEquals, like, inList and isMissing are evidenced by
    // the extracts/tests in this repository; the remaining names follow the same naming scheme. Any other operator is
    // rejected (see convertOperator) rather than silently treated as equality.
    static final String EQUALS = "equals";
    static final String NOT_EQUALS = "notEquals";
    static final String NOT_EQUAL = "notEqual";
    static final String LESS_THAN = "lessThan";
    static final String GREATER_THAN = "greaterThan";
    static final String NOT_LIKE = "notLike";
    static final String NOT_IN_LIST = "notInList";
    static final String NOT_MISSING = "notMissing";
    static final String IS_NOT_MISSING = "isNotMissing";

    private static final Set<String> STRING_TYPES = Set.of(CHAR_CONSTANT, STRING_CONSTANT, STRING_LIST_CONSTANT);
    private static final Set<String> DATE_TYPES = Set.of(DATE_CONSTANT, DATETIME_CONSTANT, DATE_LIST_CONSTANT);

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
                String varInfoId = filterNodeVarRef.getVarInfoId() == null ? "" : filterNodeVarRef.getVarInfoId();
                commonProcessing.checkForCalcVariable(varInfoId, filterNodeVarRef.getType(), stepList, step, filterNodeVarRef.getVarName(), nodeId);
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
        if (isSingleFilterRoot(rootNode)) {
            return buildFilterExpression(createFilterNode(rootNode));
        }

        return buildLogicExpression(rootNode.getType(), rootNode.getChildren(), false);
    }

    private static boolean isSingleFilterRoot(RootNode rootNode) {
        Children rootNodeChildren = rootNode.getChildren();
        return rootNodeChildren == null
                || (rootNodeChildren.getFilterNodeDOs().isEmpty() && rootNodeChildren.getLogicNodeDOs().isEmpty());
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
        String operator = filterNodeDO.getOperator();
        String convertedOperator = convertOperator(operator, variableName);

        if ((LIKE.equals(operator) || NOT_LIKE.equals(operator)) && getGlobalVariableName(filterNodeDO) == null) {
            List<String> likeValues = listEntries(filterNodeDO);
            if (likeValues.size() > 1) {
                return likeValues.stream()
                        .map(value -> variableName + " " + convertedOperator + " " + quote(value))
                        .collect(Collectors.joining(LIKE.equals(operator) ? " or " : " and ", "(", ")"));
            }
        }

        return variableName
                + " "
                + convertedOperator
                + " "
                + getFilterValue(filterNodeDO);
    }

    private String getFilterVariableName(FilterNodeDO filterNodeDO) {
        VarRef varRef = filterNodeDO.getVarRef();
        // Request, global, calculated and identifier items use the name the Decision signature declares (RequestX,
        // GlobalX, CalculatedDataItemX, IdentifierX); upstream process/subdiagram outputs the name their producer
        // writes; anything else keeps its variable name.
        String qualifiedName = commonProcessing.nodeOutputReference(varRef.getVarInfoId());
        if (qualifiedName == null) {
            qualifiedName = CommonProcessing.sourceQualifiedName(varRef.getVarInfoId());
        }
        String variableName = qualifiedName != null ? qualifiedName : varRef.getVarName();

        variableName = commonProcessing.sanitizeVariableName(variableName);
        String dateType = filterNodeDO.getDateType();
        if (isMissingOperator(filterNodeDO.getOperator())) {
            return variableName;
        }
        // RTDM date items are ID datetime terms (CommonProcessing.getDatatypeOfVar) while RTDM date filters compare
        // calendar dates, so the date part is taken first; DAY/MONTH/YEAR also expect a SAS date, not a datetime.
        String date = isDateType(filterNodeDO.getVarRef().getType()) ? "DATEPART(" + variableName + ")" : variableName;
        if (isWholeDate(dateType)) {
            return date;
        }

        switch (dateType.toLowerCase(Locale.ROOT)) {
            case "day":
                return "DAY(" + date + ")";
            case "month":
                return "MONTH(" + date + ")";
            case "year":
                return "YEAR(" + date + ")";
            default:
                return date;
        }
    }

    private static boolean isWholeDate(String dateType) {
        return dateType == null || dateType.trim().isEmpty() || "whole".equalsIgnoreCase(dateType);
    }

    private static boolean isMissingOperator(String operator) {
        return IS_MISSING.equals(operator) || NOT_MISSING.equals(operator) || IS_NOT_MISSING.equals(operator);
    }

    private static FilterNodeDO createFilterNode(RootNode rootNode) {
        final FilterNodeDO filterNodeDO = new FilterNodeDO();
        filterNodeDO.setValues(rootNode.getValues());
        filterNodeDO.setVarRef(rootNode.getVarRef());
        filterNodeDO.setOperator(rootNode.getOperator());
        filterNodeDO.setChildren(rootNode.getChildren());
        filterNodeDO.setFormattedValues(rootNode.getFormattedValues());
        filterNodeDO.setDateType(rootNode.getDateType());
        return filterNodeDO;
    }

    /**
     * The entries of a list-valued filter. The raw Values are used (split on their declared ListDelimiter, "," or
     * ",!" when entries such as formatted dates contain commas) because FormattedValues are display text: booleans
     * appear as Yes/No and numbers may carry grouping separators.
     */
    private static List<String> listEntries(FilterNodeDO filterNodeDO) {
        Values values = filterNodeDO.getValues();
        String raw = values == null ? null : values.getText();
        if (raw != null && !raw.isBlank()) {
            String delimiter = values.getListDelimiter() == null || values.getListDelimiter().isEmpty() ? "," : values.getListDelimiter();
            return Arrays.stream(raw.split(Pattern.quote(delimiter))).map(String::trim).toList();
        }
        String formatted = filterNodeDO.getFormattedValues();
        return formatted == null ? List.of() : Arrays.stream(formatted.split(",")).map(String::trim).toList();
    }

    private static boolean isBooleanVariable(FilterNodeDO filterNodeDO) {
        VarRef varRef = filterNodeDO.getVarRef();
        Values values = filterNodeDO.getValues();
        return "boolean".equalsIgnoreCase(varRef.getSubType())
                || values != null && values.getListTypes() != null && values.getListTypes().toLowerCase(Locale.ROOT).startsWith("boolean");
    }

    /** RTDM booleans are numeric items with values true/false; numeric terms compare them as 1/0. */
    private static String booleanAsNumber(String value) {
        if ("true".equalsIgnoreCase(value)) {
            return "1";
        }
        if ("false".equalsIgnoreCase(value)) {
            return "0";
        }
        return value;
    }

    /** A filter date (Values such as "9/7/26, 7:25 AM" or FormattedValues "Sep 7, 2026") as an ID DS2 date value. */
    private static String dateValue(FilterNodeDO filterNodeDO, String value) {
        return RtdmDates.parseDateTime(value)
                .map(dateTime -> "to_double(date '" + dateTime.toLocalDate() + "')")
                .orElseThrow(() -> new IllegalArgumentException("Filter on '" + filterNodeDO.getVarRef().getVarName()
                        + "' has a date value that cannot be read: '" + value + "'"));
    }

    private String getFilterValue(final FilterNodeDO filterNodeDO) {
        String filterValue = filterNodeDO.getValues() == null ? null : filterNodeDO.getValues().getText();
        String formattedFilterValue = filterNodeDO.getFormattedValues();
        String globalVariableName = getGlobalVariableName(filterNodeDO);
        String operator = filterNodeDO.getOperator();
        String type = filterNodeDO.getVarRef().getType();

        if (isMissingOperator(operator)) {
            // Missing is "." for numeric (including date/datetime) values and '' for character values. RTDM keeps the
            // values entered for a previously selected operator, so any Values are ignored.
            return isStringType(type) ? "''" : ".";
        }

        // Whole-date filters compare calendar dates (see getFilterVariableName), including against another variable
        boolean wholeDate = isDateType(type) && isWholeDate(filterNodeDO.getDateType());

        if (globalVariableName != null) {
            return datePartIf(wholeDate, commonProcessing.sanitizeVariableName(globalVariableName));
        }

        if (filterValue == null || filterValue.trim().isEmpty()) {
            String variableReferenceName = getVariableReferenceName(formattedFilterValue);
            if (variableReferenceName != null) {
                return datePartIf(wholeDate, commonProcessing.sanitizeVariableName(variableReferenceName));
            }
        }

        if (IN_LIST.equals(operator) || NOT_IN_LIST.equals(operator)) {
            List<String> entries = listEntries(filterNodeDO);
            if (entries.isEmpty()) {
                throw new IllegalArgumentException("Filter on '" + filterNodeDO.getVarRef().getVarName()
                        + "' uses operator '" + operator + "' without any values");
            }
            return entries.stream()
                    .map(value -> listValue(filterNodeDO, type, wholeDate, value))
                    .collect(Collectors.joining(",", "(", ")"));
        }

        if (wholeDate) {
            // FormattedValues ("Sep 7, 2026") has a four-digit year; Values ("9/7/26, 7:25 AM") only two
            return dateValue(filterNodeDO, formattedFilterValue != null ? formattedFilterValue : filterValue);
        }

        if (isDateType(type)) {
            // Day/month/year filters compare numbers, e.g. MONTH(DATEPART(x)) >= 3
            return formattedFilterValue != null ? formattedFilterValue : filterValue;
        }

        if (isStringType(type)) {
            return quote(filterValue == null ? "" : filterValue);
        }

        return isBooleanVariable(filterNodeDO) ? booleanAsNumber(filterValue) : filterValue;
    }

    private static String listValue(FilterNodeDO filterNodeDO, String type, boolean wholeDate, String value) {
        if (isStringType(type)) {
            return quote(value);
        }
        if (wholeDate) {
            return dateValue(filterNodeDO, value);
        }
        return isBooleanVariable(filterNodeDO) ? booleanAsNumber(value) : value;
    }

    private static String datePartIf(boolean wholeDate, String variableName) {
        return wholeDate ? "DATEPART(" + variableName + ")" : variableName;
    }

    private static boolean isStringType(String type) {
        return type != null && STRING_TYPES.contains(type.toLowerCase(Locale.ROOT));
    }

    private static boolean isDateType(String type) {
        return type != null && DATE_TYPES.contains(type.toLowerCase(Locale.ROOT));
    }

    private static String quote(String value) {
        return "'" + value.replace("'", "''") + "'";
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
            // "Request.X" is already the signature's name for the request variable
            return formattedFilterValue;
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

    static String convertOperator(final String operator, final String variableName) {
        if (operator == null) {
            throw new IllegalArgumentException("Filter on '" + variableName + "' has no operator");
        }
        switch (operator) {
            case EQUALS:
                return "=";
            case NOT_EQUALS:
            case NOT_EQUAL:
                return "^=";
            case LESS_THAN:
                return "<";
            case LESS_THAN_OR_EQUALS_TO:
                return "<=";
            case GREATER_THAN:
                return ">";
            case GREATER_THAN_OR_EQUALS_TO:
                return ">=";
            case LIKE:
                return "LIKE";
            case NOT_LIKE:
                return "NOT LIKE";
            case IN_LIST:
                return "IN";
            case NOT_IN_LIST:
                return "NOT IN";
            case IS_MISSING:
                return "=";
            case NOT_MISSING:
            case IS_NOT_MISSING:
                return "^=";
            default:
                throw new IllegalArgumentException("Unsupported RTDM filter operator '" + operator + "' on '" + variableName
                        + "'; the multi-select node cannot be migrated without an explicit mapping for this operator");
        }
    }

    static List<FilterNodeDO> getFilterNodesFromMultiSelectNode(MultiSelectNodeDataDO multiSelectNodeDataDO) {
        final List<FilterNodeDO> filterNodeDOs = new ArrayList<>();
        final RootNode rootNode = multiSelectNodeDataDO.getSqlExpression().getRootNode();

        if (isSingleFilterRoot(rootNode)) {
            // In this case the RootNode is the FilterNode so convert it to a FilterNode for consistent processing of conditionExpression
            filterNodeDOs.add(createFilterNode(rootNode));
        } else {
            // recursively search through the rootNode looking for instances of FilterNodeDO
            findAllInstancesOfFilterNodeDO(rootNode.getChildren(), filterNodeDOs);
        }

        return filterNodeDOs;
    }

    private static void findAllInstancesOfFilterNodeDO(Children children, List<FilterNodeDO> filterNodeDOs) {
        if (children == null) {
            return;
        }
        filterNodeDOs.addAll(children.getFilterNodeDOs());

        children.getLogicNodeDOs().forEach(logicNodeDO -> findAllInstancesOfFilterNodeDO(logicNodeDO.getChildren(), filterNodeDOs));
    }

}
