/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.model.id.segmentation.SegmentationTree;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.ActualValue;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.ColumnCriteria;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.CrossTableLineItemDO;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.IBVariableValueDO;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.ListOfValues;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.OutputVariable;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.RowCriteria;
import com.sas.rtdm2id.model.rtdm.SplitNodeDataDO.SplitOnNodeDataDO.VarRefDO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Component
public class SegmentationTreeConverter {
    private static final int VALUE_TYPE_DOUBLE = 1;
    private static final int VALUE_TYPE_STRING = 2;
    private static final int VALUE_TYPE_DATE = 3;
    private static final int VALUE_TYPE_BOOLEAN = 4;
    private static final int VALUE_TYPE_INTEGER = 5;
    private static final int VALUE_TYPE_DEFAULT = 14;

    private static final String TYPE_BOOLEAN_EXPRESSION = "booleanExpression";
    private static final String TYPE_MATRIX = "matrix";
    private static final String TYPE_VARIABLE = "variable";
    private static final String VALUE_TYPE_STATIC = "static";
    private static final String ACTION_VARIABLE_ASSIGNMENT = "variableAssignment";
    private static final String MISSING_MARKER = "__&MISSING&__";
    private static final String OTHER_MARKER = "__&OTHER&__";

    private final CommonProcessing commonProcessing;

    public SegmentationTreeConverter(CommonProcessing commonProcessing) {
        this.commonProcessing = commonProcessing;
    }

    public SegmentationTree convert(CrossTableNodeDataDO crossTableNodeDataDO) {
        if (crossTableNodeDataDO == null) {
            throw new IllegalArgumentException("Cross-Table node is required");
        }

        if (crossTableNodeDataDO.getRowCriteria() == null && crossTableNodeDataDO.getColumnCriteria() == null) {
            throw new IllegalArgumentException("Cross-Table row or column criteria is required");
        }
        Axis rowAxis = crossTableNodeDataDO.getRowCriteria() == null
                ? null : createAxis(crossTableNodeDataDO.getRowCriteria());
        Axis columnAxis = crossTableNodeDataDO.getColumnCriteria() == null
                ? null : createAxis(crossTableNodeDataDO.getColumnCriteria());
        int rowCount = rowAxis == null ? 1 : rowAxis.items.size();
        int columnCount = columnAxis == null ? 1 : columnAxis.items.size();
        List<ListOfValues> tableRows = crossTableNodeDataDO.getCrossTable() == null
                ? Collections.<ListOfValues>emptyList()
                : crossTableNodeDataDO.getCrossTable().getLists();
        validateTable(rowCount, columnCount, tableRows);

        SegmentationTree tree = new SegmentationTree();
        tree.setName(commonProcessing.makeCrossTableNodeName(crossTableNodeDataDO));
        tree.setDescription(crossTableNodeDataDO.getLabel());
        tree.setLocalValueLists(new ArrayList<SegmentationTree.LocalValueList>());
        tree.setOutcomes(new ArrayList<SegmentationTree.Outcome>());
        tree.setMatrices(new ArrayList<SegmentationTree.Matrix>());
        tree.setBooleanExpressions(new ArrayList<SegmentationTree.BooleanExpression>());

        if (rowAxis != null && columnAxis != null && rowAxis.matrixCompatible && columnAxis.matrixCompatible) {
            Set<String> localValueListNames = new LinkedHashSet<>();
            addAxisLocalValueList(tree, rowAxis, "down", localValueListNames);
            addAxisLocalValueList(tree, columnAxis, "across", localValueListNames);
        }
        tree.setSignature(createSignature(rowAxis, columnAxis, crossTableNodeDataDO.getOutputVariable()));

        CellTable cellTable = createCellTable(crossTableNodeDataDO.getOutputVariable(), tableRows,
                rowCount, columnCount, tree.getOutcomes());
        if (rowAxis != null && columnAxis != null && rowAxis.matrixCompatible && columnAxis.matrixCompatible) {
            tree.setNode(rowAxis.like || columnAxis.like
                ? createPatternMatrixTree(rowAxis, columnAxis, cellTable, tree,
                    allIndexes(rowAxis), allIndexes(columnAxis), false, false)
                : createHybridMatrixTree(rowAxis, columnAxis, cellTable, tree));
        } else {
            tree.setNode(createBooleanTree(rowAxis, columnAxis, cellTable, tree));
        }
        return tree;
    }

    private void addAxisLocalValueList(SegmentationTree tree, Axis axis, String position,
            Set<String> usedNames) {
        List<Criterion> valueCriteria = new ArrayList<>();
        boolean hasRange = false;
        for (Integer index : nonMissingIndexes(axis)) {
            Criterion criterion = axis.items.get(index);
            if (criterion.remainder) {
                continue;
            }
            valueCriteria.add(criterion);
            hasRange |= !criterion.discrete;
        }
        if (valueCriteria.isEmpty()) {
            return;
        }

        String suffix = hasRange ? "_ranges" : "_values";
        String baseName = axis.variableName.toLowerCase(Locale.ROOT) + suffix;
        String name = localValueListName(baseName);
        if (usedNames.contains(name.toLowerCase(Locale.ROOT))) {
            name = localValueListName(baseName + "_" + position);
        }
        int duplicateSuffix = 2;
        while (usedNames.contains(name.toLowerCase(Locale.ROOT))) {
            name = localValueListName(baseName + "_" + position + "_" + duplicateSuffix++);
        }
        usedNames.add(name.toLowerCase(Locale.ROOT));
        axis.localValueListName = name;

        SegmentationTree.LocalValueList localValueList = new SegmentationTree.LocalValueList();
        localValueList.setName(name);
        localValueList.setItems(new ArrayList<SegmentationTree.LocalValueList.Item>());
        SegmentationTree.LocalValueList.Properties properties = new SegmentationTree.LocalValueList.Properties();
        properties.setDataType(localValueListDataType(axis));
        localValueList.setProperties(properties);

        int order = 1;
        for (Criterion criterion : valueCriteria) {
            SegmentationTree.LocalValueList.Item item = new SegmentationTree.LocalValueList.Item();
            item.setKey(String.valueOf(matrixAxisValue(axis, criterion)));
            item.setValue(criterionLabel(axis, criterion));
            item.setOrder(order++);
            localValueList.getItems().add(item);
        }
        tree.getLocalValueLists().add(localValueList);
    }

    private String localValueListName(String name) {
        String sanitized = commonProcessing.sanitizeAndReduceVariableName(name);
        return sanitized == null || sanitized.isEmpty() ? name : sanitized;
    }

    private String localValueListDataType(Axis axis) {
        if ("integer".equals(axis.dataType) || "decimal".equals(axis.dataType)) {
            return "numeric";
        }
        return axis.dataType;
    }

    private List<SegmentationTree.SignatureTerm> createSignature(Axis rowAxis, Axis columnAxis,
            OutputVariable outputVariable) {
        List<SegmentationTree.SignatureTerm> signature = new ArrayList<>();
        Set<String> names = new LinkedHashSet<>();
        if (rowAxis != null) {
            addSignature(signature, names, rowAxis.variableName, rowAxis.dataType, "input",
                    rowAxis.localValueListName);
        }
        if (columnAxis != null) {
            addSignature(signature, names, columnAxis.variableName, columnAxis.dataType, "input",
                    columnAxis.localValueListName);
        }
        for (SegmentationTree.SignatureTerm term : signature) {
            Axis axis = rowAxis != null && term.getName().equals(rowAxis.variableName) ? rowAxis : columnAxis;
            term.setSourceVariableName(axis.sourceVariableName);
            term.setSourceDataType(axis.sourceDataType);
            term.setDerivationExpression(axis.derivationExpression);
        }

        if (outputVariable != null) {
            String outputName = outputVariableName(outputVariable);
            addSignature(signature, names, outputName, dataType(outputVariable.getTypeDescription()), "output", null);
        }
        return signature;
    }

    private void addSignature(List<SegmentationTree.SignatureTerm> signature, Set<String> names, String name,
            String dataType, String direction, String localValueListName) {
        if (name == null || name.isEmpty() || !names.add(name)) {
            return;
        }
        SegmentationTree.SignatureTerm term = new SegmentationTree.SignatureTerm();
        term.setName(name);
        term.setDataType(dataType);
        term.setDirection(direction);
        term.setLocalValueListName(localValueListName);
        if ("string".equals(dataType)) {
            term.setLength(1000);
        }
        signature.add(term);
    }

    private Axis createAxis(RowCriteria criteria) {
        if (criteria == null) {
            throw new IllegalArgumentException("Cross-Table row criteria is required");
        }
        return createAxis(criteria.getVarRefDO(), criteria.isLike(), criteria.getDateType(),
                criteria.getLineItems() == null ? null : criteria.getLineItems().getCrossTableLineItemDOs());
    }

    private Axis createAxis(ColumnCriteria criteria) {
        if (criteria == null) {
            throw new IllegalArgumentException("Cross-Table column criteria is required");
        }
        return createAxis(criteria.getVarRefDO(), criteria.isLike(), criteria.getDateType(),
                criteria.getLineItems() == null ? null : criteria.getLineItems().getCrossTableLineItemDOs());
    }

    private Axis createAxis(VarRefDO varRefDO, boolean like, String dateType,
            List<CrossTableLineItemDO> lineItems) {
        if (varRefDO == null) {
            throw new IllegalArgumentException("Cross-Table criteria variable is required");
        }
        Axis axis = new Axis();
        axis.variableName = variableName(varRefDO);
        axis.dataType = "boolean".equalsIgnoreCase(varRefDO.getSubType())
            ? "boolean" : dataType(varRefDO.getType());
        axis.like = like;
        axis.dateType = dateType;
        if (!isWholeDateAxis(dateType)) {
            axis.sourceVariableName = axis.variableName;
            axis.sourceDataType = axis.dataType;
            boolean day = dateType.toLowerCase(Locale.ROOT).contains("day");
                axis.derivationExpression = day && "datetime".equals(axis.dataType)
                    ? "day(datepart(" + axis.variableName + "))"
                    : (day ? "day(" : "timepart(") + axis.variableName + ")";
            axis.variableName = commonProcessing.sanitizeVariableName(axis.variableName + (day ? "_day" : "_time"));
            axis.dataType = day ? "integer" : "decimal";
        }
        if ("datetime".equals(axis.dataType) && isWholeDateAxis(dateType)) {
            axis.dataType = "date";
        }
        axis.items = new ArrayList<>();
        if (lineItems != null) {
            for (CrossTableLineItemDO lineItem : lineItems) {
                axis.items.add(createCriterion(lineItem));
            }
        }
        axis.matrixCompatible = supportsMatrixValues(axis.items);
        return axis;
    }

    private boolean supportsMatrixValues(List<Criterion> criteria) {
        for (Criterion criterion : criteria) {
            if (!criterion.discrete && !criterion.missing && !criterion.remainder) {
                boolean hasMinimum = criterion.minValue != null;
                boolean hasMaximum = criterion.maxValue != null;
                if (!hasMinimum && !hasMaximum) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isWholeDateAxis(String dateType) {
        if (dateType == null || dateType.isEmpty()) {
            return true;
        }
        String normalizedDateType = dateType.toLowerCase(Locale.ROOT);
        return !normalizedDateType.contains("day") && !normalizedDateType.contains("time");
    }

    private Criterion createCriterion(CrossTableLineItemDO lineItem) {
        if (lineItem == null) {
            throw new IllegalArgumentException("Cross-Table criterion cannot be null");
        }
        Criterion criterion = new Criterion();
        criterion.discrete = lineItem.isDiscrete();
        criterion.missing = lineItem.isMissing();
        criterion.remainder = lineItem.isRemainder();
        criterion.minInclusive = lineItem.isMinInclusive();
        criterion.maxInclusive = lineItem.isMaxInclusive();
        criterion.formattedValue = lineItem.getFormattedValue();
        criterion.actualValue = scalarValue(lineItem.getActualValue(), lineItem.getFormattedValue());
        criterion.actualStoredType = lineItem.getActualValue() == null
            ? null : lineItem.getActualValue().getStoredType();
        criterion.minValue = lineItem.getMinValue() == null ? null : lineItem.getMinValue().getValue();
        criterion.maxValue = lineItem.getMaxValue() == null ? null : lineItem.getMaxValue().getValue();
        criterion.minStoredType = lineItem.getMinValue() == null ? null : lineItem.getMinValue().getStoredType();
        criterion.maxStoredType = lineItem.getMaxValue() == null ? null : lineItem.getMaxValue().getStoredType();
        return criterion;
    }

    private CellTable createCellTable(OutputVariable outputVariable, List<ListOfValues> tableRows,
            int rowCount, int columnCount, List<SegmentationTree.Outcome> outcomes) {
        CellTable cellTable = new CellTable();
        cellTable.outcomeNames = new ArrayList<>();
        cellTable.outcomeValues = new LinkedHashMap<>();

        for (int row = 0; row < rowCount; row++) {
            List<IBVariableValueDO> values = tableRows.get(row).getValues();
            List<String> rowOutcomes = new ArrayList<>();
            for (int column = 0; column < columnCount; column++) {
                Object value = scalarValue(values.get(column));
                if (values.get(column).getType() == VALUE_TYPE_DEFAULT) {
                    value = defaultValue(outputVariable);
                }
                String outcomeName = findOrCreateOutcome(value, outputVariable, outcomes, cellTable.outcomeValues);
                rowOutcomes.add(outcomeName);
            }
            cellTable.outcomeNames.add(rowOutcomes);
        }
        return cellTable;
    }

    private String findOrCreateOutcome(Object value, OutputVariable outputVariable,
            List<SegmentationTree.Outcome> outcomes, Map<String, Object> outcomeValues) {
        String valueKey = value == null ? "null" : value.getClass().getName() + ":" + String.valueOf(value);
        String existingName = null;
        for (Map.Entry<String, Object> entry : outcomeValues.entrySet()) {
            String existingKey = entry.getValue() == null
                    ? "null" : entry.getValue().getClass().getName() + ":" + String.valueOf(entry.getValue());
            if (valueKey.equals(existingKey)) {
                existingName = entry.getKey();
                break;
            }
        }
        if (existingName != null) {
            return existingName;
        }

        String outcomeValueName = value == null ? "missing" : String.valueOf(value);
        if (outcomeValueName.isEmpty()) {
            outcomeValueName = "empty";
        }
        String outcomeName = commonProcessing.sanitizeAndReduceVariableName("set_" + outcomeValueName);
        int duplicateSuffix = 2;
        while (containsOutcomeName(outcomeValues, outcomeName)) {
            outcomeName = commonProcessing.sanitizeAndReduceVariableName(
                    "set_" + outcomeValueName + "_" + duplicateSuffix++);
        }
        Object assignmentValue = value == null ? MISSING_MARKER : value;
        SegmentationTree.VariableAssignment assignment = new SegmentationTree.VariableAssignment();
        assignment.setType(ACTION_VARIABLE_ASSIGNMENT);
        assignment.setVariableName(outputVariableName(outputVariable));
        assignment.setValueType(VALUE_TYPE_STATIC);
        assignment.setValue(assignmentValue);

        SegmentationTree.Outcome outcome = new SegmentationTree.Outcome();
        outcome.setName(outcomeName);
        outcome.setActions(Collections.singletonList(assignment));
        outcomes.add(outcome);
        outcomeValues.put(outcomeName, value);
        return outcomeName;
    }

    private boolean containsOutcomeName(Map<String, Object> outcomeValues, String outcomeName) {
        for (String existingName : outcomeValues.keySet()) {
            if (existingName.equalsIgnoreCase(outcomeName)) {
                return true;
            }
        }
        return false;
    }

    private SegmentationTree.Node createMatrixTree(Axis rowAxis, Axis columnAxis, CellTable cellTable,
            SegmentationTree tree, List<Integer> rowIndexes, List<Integer> columnIndexes) {
        SegmentationTree.Matrix matrix = new SegmentationTree.Matrix();
        String baseName = rowAxis.variableName + "_by_" + columnAxis.variableName;
        if (rowIndexes.size() == 1 && rowAxis.items.get(rowIndexes.get(0)).missing) {
            baseName += "_missing_" + rowAxis.variableName;
        }
        Set<String> usedNames = new LinkedHashSet<>();
        for (SegmentationTree.Matrix existing : tree.getMatrices()) {
            usedNames.add(existing.getName().toLowerCase(Locale.ROOT));
        }
        String matrixName = commonProcessing.sanitizeVariableName(baseName);
        int suffix = 2;
        while (usedNames.contains(matrixName.toLowerCase(Locale.ROOT))) {
            matrixName = commonProcessing.sanitizeVariableName(baseName + "_" + suffix++);
        }
        matrix.setName(matrixName);
        matrix.setDownVariableName(rowAxis.variableName);
        matrix.setDownVariableLocalValueListName(rowAxis.localValueListName);
        matrix.setDownVariableValues(matrixValues(rowAxis, rowIndexes));
        matrix.setDownVariableLabels(matrixLabels(rowAxis, rowIndexes));
        matrix.setAcrossVariableName(columnAxis.variableName);
        matrix.setAcrossVariableLocalValueListName(columnAxis.localValueListName);
        matrix.setAcrossVariableValues(matrixValues(columnAxis, columnIndexes));
        matrix.setAcrossVariableLabels(matrixLabels(columnAxis, columnIndexes));

        List<List<Object>> matrixOutcomes = new ArrayList<>();
        for (Integer rowIndex : rowIndexes) {
            List<Object> matrixRow = new ArrayList<>();
            for (Integer columnIndex : columnIndexes) {
                String outcomeName = cellTable.outcomeNames.get(rowIndex).get(columnIndex);
                matrixRow.add(matrixOutcomeValue(cellTable, outcomeName));
            }
            matrixOutcomes.add(matrixRow);
        }
        matrix.setOutcomes(matrixOutcomes);
        tree.getMatrices().add(matrix);

        SegmentationTree.Node node = new SegmentationTree.Node();
        node.setType(TYPE_MATRIX);
        node.setLabel(matrix.getName());
        node.setMatrixName(matrix.getName());
        node.setValueNodes(createOutputValueNodes(cellTable, matrix));
        return node;
    }

    private List<Integer> allIndexes(Axis axis) {
        List<Integer> indexes = new ArrayList<>();
        for (int index = 0; index < axis.items.size(); index++) {
            indexes.add(index);
        }
        return indexes;
    }

    private int guardedIndex(Axis axis, List<Integer> indexes, boolean resolved) {
        if (resolved) {
            return -1;
        }
        for (Integer index : indexes) {
            if (axis.items.get(index).missing) {
                return index;
            }
        }
        for (Integer index : indexes) {
            Criterion criterion = axis.items.get(index);
            if (axis.like && criterion.discrete && !criterion.remainder) {
                return index;
            }
        }
        return -1;
    }

    private SegmentationTree.Node createPatternMatrixTree(Axis rowAxis, Axis columnAxis, CellTable cellTable,
            SegmentationTree tree, List<Integer> rows, List<Integer> columns,
            boolean rowResolved, boolean columnResolved) {
        int columnIndex = guardedIndex(columnAxis, columns, columnResolved);
        int rowIndex = guardedIndex(rowAxis, rows, rowResolved);
        if (columnIndex < 0 && rowIndex < 0) {
            return createMatrixTree(rowAxis, columnAxis, cellTable, tree, rows, columns);
        }
        boolean columnGuard = columnIndex >= 0;
        Axis axis = columnGuard ? columnAxis : rowAxis;
        int index = columnGuard ? columnIndex : rowIndex;
        Criterion criterion = axis.items.get(index);
        String name = criterion.missing ? missingExpressionName(axis)
                : commonProcessing.sanitizeAndReduceVariableName(axis.variableName + "_matches_" + index);
        addBooleanExpression(tree, name, criterionExpression(axis, criterion));
        SegmentationTree.Node node = new SegmentationTree.Node();
        node.setType(TYPE_BOOLEAN_EXPRESSION);
        node.setLabel(name);
        node.setBooleanExpressionName(name);
        node.setValueNodes(new ArrayList<SegmentationTree.ValueNode>());

        List<Integer> remaining = new ArrayList<>(columnGuard ? columns : rows);
        remaining.remove(Integer.valueOf(index));
        node.getValueNodes().add(patternBranch(rowAxis, columnAxis, cellTable, tree,
                columnGuard ? rows : Collections.singletonList(index),
                columnGuard ? Collections.singletonList(index) : columns,
                rowResolved || !columnGuard, columnResolved || columnGuard,
                "true", criterionLabel(criterion)));
        if (!remaining.isEmpty()) {
            node.getValueNodes().add(patternBranch(rowAxis, columnAxis, cellTable, tree,
                    columnGuard ? rows : remaining, columnGuard ? remaining : columns,
                    rowResolved, columnResolved, "false", "Not " + criterionLabel(criterion)));
        }
        return node;
    }

    private SegmentationTree.ValueNode patternBranch(Axis rowAxis, Axis columnAxis, CellTable cellTable,
            SegmentationTree tree, List<Integer> rows, List<Integer> columns,
            boolean rowResolved, boolean columnResolved, String value, String label) {
        String outcome = cellTable.outcomeNames.get(rows.get(0)).get(columns.get(0));
        boolean uniform = coversAxis(rowAxis, rows, rowResolved)
            && coversAxis(columnAxis, columns, columnResolved);
        for (Integer row : rows) {
            for (Integer column : columns) {
                uniform &= outcome.equals(cellTable.outcomeNames.get(row).get(column));
            }
        }
        return uniform ? valueNode(value, label, outcome, null, null)
                : valueNode(value, label, null, createPatternMatrixTree(rowAxis, columnAxis, cellTable, tree,
                        rows, columns, rowResolved, columnResolved), null);
    }

    private boolean coversAxis(Axis axis, List<Integer> indexes, boolean resolved) {
        if (resolved) {
            return true;
        }
        for (Integer index : indexes) {
            if (axis.items.get(index).remainder) {
                return true;
            }
        }
        return false;
    }

    private SegmentationTree.Node createHybridMatrixTree(Axis rowAxis, Axis columnAxis, CellTable cellTable,
            SegmentationTree tree) {
        int missingRowIndex = missingIndex(rowAxis);
        int missingColumnIndex = missingIndex(columnAxis);
        List<Integer> rowIndexes = nonMissingIndexes(rowAxis);
        List<Integer> columnIndexes = nonMissingIndexes(columnAxis);
        SegmentationTree.Node mainMatrix = rowIndexes.isEmpty() || columnIndexes.isEmpty() ? null
                : createMatrixTree(rowAxis, columnAxis, cellTable, tree, rowIndexes, columnIndexes);

        SegmentationTree.Node rowPresentBranch = mainMatrix;
        if (missingColumnIndex >= 0) {
            SegmentationTree.Node missingColumnDispatch = createVariableNode(rowAxis, cellTable,
                    rowIndexes, missingColumnIndex, false);
            rowPresentBranch = createMissingGuard(tree, columnAxis, missingColumnDispatch, mainMatrix);
        }

        if (missingRowIndex >= 0) {
            List<String> missingOutcomes = cellTable.outcomeNames.get(missingRowIndex);
            String missingOutcome = missingOutcomes.get(0);
            if (missingOutcomes.stream().allMatch(missingOutcome::equals)) {
                return createMissingGuard(tree, rowAxis, missingOutcome, rowPresentBranch);
            }
            SegmentationTree.Node missingRowDispatch = createVariableNode(columnAxis, cellTable,
                    columnIndexes, missingRowIndex, true);
            SegmentationTree.Node missingRowBranch;
            if (missingColumnIndex >= 0) {
                String bothMissingOutcome = cellTable.outcomeNames.get(missingRowIndex).get(missingColumnIndex);
                missingRowBranch = createMissingGuard(tree, columnAxis, bothMissingOutcome, missingRowDispatch);
            } else {
                missingRowBranch = missingRowDispatch;
            }

            return createMissingGuard(tree, rowAxis, missingRowBranch, rowPresentBranch);
        }

        return rowPresentBranch;
    }

    private SegmentationTree.Node createMissingGuard(SegmentationTree tree, Axis axis,
            SegmentationTree.Node missingBranch, SegmentationTree.Node notMissingBranch) {
        return createMissingGuard(tree, axis, missingBranch, notMissingBranch, null);
    }

    private SegmentationTree.Node createMissingGuard(SegmentationTree tree, Axis axis,
            SegmentationTree.Node missingBranch, SegmentationTree.Node notMissingBranch,
            String missingOutcomeName) {
        String name = missingExpressionName(axis);
        addBooleanExpression(tree, name, "missing(" + axis.variableName + ")");
        SegmentationTree.Node node = new SegmentationTree.Node();
        node.setType(TYPE_BOOLEAN_EXPRESSION);
        node.setLabel(name);
        node.setBooleanExpressionName(name);
        node.setValueNodes(new ArrayList<SegmentationTree.ValueNode>());
        node.getValueNodes().add(valueNode("true", "Missing", missingOutcomeName, missingBranch, null));
        node.getValueNodes().add(valueNode("false", "Not missing", null, notMissingBranch, null));
        return node;
    }

    private SegmentationTree.Node createMissingGuard(SegmentationTree tree, Axis axis,
            String missingOutcomeName, SegmentationTree.Node notMissingBranch) {
        return createMissingGuard(tree, axis, null, notMissingBranch, missingOutcomeName);
    }

    private String missingExpressionName(Axis axis) {
        return commonProcessing.sanitizeVariableName(axis.variableName + "_is_missing");
    }

    private SegmentationTree.Node createVariableNode(Axis axis, CellTable cellTable, List<Integer> axisIndexes,
            int fixedIndex, boolean variableIsColumn) {
        if (axisIndexes.isEmpty()) {
            return null;
        }
        SegmentationTree.Node node = new SegmentationTree.Node();
        node.setType(TYPE_VARIABLE);
        node.setLabel(axis.variableName);
        node.setVariableName(axis.variableName);
        node.setValueNodes(new ArrayList<SegmentationTree.ValueNode>());
        for (Integer axisIndex : axisIndexes) {
            int rowIndex = variableIsColumn ? fixedIndex : axisIndex;
            int columnIndex = variableIsColumn ? axisIndex : fixedIndex;
            String outcomeName = cellTable.outcomeNames.get(rowIndex).get(columnIndex);
            Criterion criterion = axis.items.get(axisIndex);
            node.getValueNodes().add(valueNode(matrixAxisValue(axis, criterion), criterionLabel(axis, criterion),
                    outcomeName, null, null));
        }
        return node;
    }

    private SegmentationTree.ValueNode valueNode(Object value, String label, String outcomeName,
            SegmentationTree.Node next, List<Object> actionOverrides) {
        SegmentationTree.ValueNode valueNode = new SegmentationTree.ValueNode();
        valueNode.setValue(value);
        valueNode.setLabel(label);
        valueNode.setOutcomeName(outcomeName);
        valueNode.setNext(next);
        valueNode.setVariableAssignmentOutcomeActionOverrides(
                actionOverrides == null ? Collections.emptyList() : actionOverrides);
        return valueNode;
    }

    private int missingIndex(Axis axis) {
        for (int index = 0; index < axis.items.size(); index++) {
            if (axis.items.get(index).missing) {
                return index;
            }
        }
        return -1;
    }

    private List<Integer> nonMissingIndexes(Axis axis) {
        List<Integer> indexes = new ArrayList<>();
        for (int index = 0; index < axis.items.size(); index++) {
            if (!axis.items.get(index).missing && !axis.items.get(index).remainder) {
                indexes.add(index);
            }
        }
        for (int index = 0; index < axis.items.size(); index++) {
            if (!axis.items.get(index).missing && axis.items.get(index).remainder) {
                indexes.add(index);
            }
        }
        return indexes;
    }

    private SegmentationTree.Node createBooleanTree(Axis rowAxis, Axis columnAxis, CellTable cellTable,
            SegmentationTree tree) {
        if (rowAxis == null) {
            return createColumnNode(columnAxis, cellTable, tree, 0, 0);
        }
        return createRowNode(rowAxis, columnAxis, cellTable, tree, 0);
    }

    private SegmentationTree.Node createRowNode(Axis rowAxis, Axis columnAxis, CellTable cellTable,
            SegmentationTree tree, int rowIndex) {
        if (rowIndex >= rowAxis.items.size()) {
            return null;
        }
        Criterion criterion = rowAxis.items.get(rowIndex);
        String expressionName = criterion.missing ? missingExpressionName(rowAxis) : "row_" + rowIndex;
        addBooleanExpression(tree, expressionName, criterionExpression(rowAxis, criterion));

        SegmentationTree.Node node = new SegmentationTree.Node();
        node.setType(TYPE_BOOLEAN_EXPRESSION);
        node.setLabel(expressionName);
        node.setBooleanExpressionName(expressionName);

        SegmentationTree.ValueNode match = new SegmentationTree.ValueNode();
        match.setValue("true");
        match.setLabel(criterionLabel(criterion));
        if (columnAxis == null) {
            match.setOutcomeName(cellTable.outcomeNames.get(rowIndex).get(0));
        } else {
            match.setNext(createColumnNode(columnAxis, cellTable, tree, rowIndex, 0));
        }
        node.setValueNodes(new ArrayList<SegmentationTree.ValueNode>());
        node.getValueNodes().add(match);

        SegmentationTree.Node next = createRowNode(rowAxis, columnAxis, cellTable, tree, rowIndex + 1);
        if (next != null) {
            SegmentationTree.ValueNode noMatch = new SegmentationTree.ValueNode();
            noMatch.setValue("false");
            noMatch.setLabel("false");
            noMatch.setNext(next);
            node.getValueNodes().add(noMatch);
        }
        return node;
    }

    private SegmentationTree.Node createColumnNode(Axis columnAxis, CellTable cellTable,
            SegmentationTree tree, int rowIndex, int columnIndex) {
        if (columnIndex >= columnAxis.items.size()) {
            return null;
        }
        Criterion criterion = columnAxis.items.get(columnIndex);
        String expressionName = criterion.missing
            ? missingExpressionName(columnAxis) : "column_" + rowIndex + "_" + columnIndex;
        addBooleanExpression(tree, expressionName, criterionExpression(columnAxis, criterion));

        SegmentationTree.Node node = new SegmentationTree.Node();
        node.setType(TYPE_BOOLEAN_EXPRESSION);
        node.setLabel(expressionName);
        node.setBooleanExpressionName(expressionName);

        SegmentationTree.ValueNode match = new SegmentationTree.ValueNode();
        match.setValue("true");
        match.setLabel(criterionLabel(criterion));
        match.setOutcomeName(cellTable.outcomeNames.get(rowIndex).get(columnIndex));
        node.setValueNodes(new ArrayList<SegmentationTree.ValueNode>());
        node.getValueNodes().add(match);

        SegmentationTree.Node next = createColumnNode(columnAxis, cellTable, tree, rowIndex,
                columnIndex + 1);
        if (next != null) {
            SegmentationTree.ValueNode noMatch = new SegmentationTree.ValueNode();
            noMatch.setValue("false");
            noMatch.setLabel("false");
            noMatch.setNext(next);
            node.getValueNodes().add(noMatch);
        }
        return node;
    }

    private void addBooleanExpression(SegmentationTree tree, String name, String expression) {
        for (SegmentationTree.BooleanExpression existing : tree.getBooleanExpressions()) {
            if (name.equals(existing.getName())) {
                return;
            }
        }
        SegmentationTree.BooleanExpression booleanExpression = new SegmentationTree.BooleanExpression();
        booleanExpression.setName(name);
        booleanExpression.setExpression(expression);
        tree.getBooleanExpressions().add(booleanExpression);
    }

    private List<SegmentationTree.ValueNode> createOutputValueNodes(CellTable cellTable,
            SegmentationTree.Matrix matrix) {
        Set<Object> matrixResults = new LinkedHashSet<>();
        for (List<Object> row : matrix.getOutcomes()) {
            matrixResults.addAll(row);
        }
        List<SegmentationTree.ValueNode> valueNodes = new ArrayList<>();
        for (Map.Entry<String, Object> entry : cellTable.outcomeValues.entrySet()) {
            Object resultValue = entry.getValue() == null ? MISSING_MARKER : String.valueOf(entry.getValue());
            if (!matrixResults.contains(resultValue)) {
                continue;
            }
            valueNodes.add(valueNode(resultValue, entry.getKey(), entry.getKey(), null, null));
        }
        return valueNodes;
    }

    private Object matrixOutcomeValue(CellTable cellTable, String outcomeName) {
        Object value = cellTable.outcomeValues.get(outcomeName);
        return value == null ? MISSING_MARKER : String.valueOf(value);
    }

    private List<Object> matrixValues(Axis axis, List<Integer> indexes) {
        List<Object> values = new ArrayList<>();
        for (Integer index : indexes) {
            Criterion criterion = axis.items.get(index);
            values.add(axis.like && criterion.discrete ? OTHER_MARKER : matrixAxisValue(axis, criterion));
        }
        return values;
    }

    private Object matrixAxisValue(Axis axis, Criterion criterion) {
        if (criterion.remainder) {
            return OTHER_MARKER;
        }
        if (criterion.missing) {
            return OTHER_MARKER;
        }
        if (!criterion.discrete) {
            return rangeValueSpecification(axis, criterion);
        }
        Object value = normalizeValue(criterion.actualValue, axis.dataType);
        if (axis.derivationExpression != null && "date".equalsIgnoreCase(criterion.actualStoredType)) {
            return matrixRangeBound(axis, criterion.actualValue, criterion.actualStoredType);
        }
        return "boolean".equals(axis.dataType) && value instanceof Boolean ? String.valueOf(value) : value;
    }

    private String rangeValueSpecification(Axis axis, Criterion criterion) {
        if (criterion.minValue != null && criterion.maxValue != null) {
            String minimumOperator = criterion.minInclusive ? " <= " : " < ";
            String maximumOperator = criterion.maxInclusive ? " <= " : " < ";
            return matrixRangeBound(axis, criterion.minValue, criterion.minStoredType) + minimumOperator + "v"
                    + maximumOperator + matrixRangeBound(axis, criterion.maxValue, criterion.maxStoredType);
        }
        if (criterion.minValue != null) {
            return (criterion.minInclusive ? ">=" : ">")
                    + matrixRangeBound(axis, criterion.minValue, criterion.minStoredType);
        }
        if (criterion.maxValue != null) {
            return (criterion.maxInclusive ? "<=" : "<")
                    + matrixRangeBound(axis, criterion.maxValue, criterion.maxStoredType);
        }
        return "";
    }

    private String matrixRangeBound(Axis axis, Object value, String storedType) {
        if (axis.dateType != null && axis.dateType.toLowerCase(Locale.ROOT).contains("time")
                && "date".equalsIgnoreCase(storedType)) {
            return String.valueOf(parseDateTimeBound(value).toLocalTime().toSecondOfDay());
        }
        if ("date".equalsIgnoreCase(storedType) && "date".equals(axis.dataType)) {
            return parseDateBound(value).format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        return rangeBound(value, storedType);
    }

    private String rangeBound(Object value, String storedType) {
        if ("date".equalsIgnoreCase(storedType)) {
            LocalDate date = parseDateBound(value);
            long sasDate = date.toEpochDay() - LocalDate.of(1960, 1, 1).toEpochDay();
            return String.valueOf(sasDate);
        }
        return literal(value, dataType(storedType));
    }

    private LocalDate parseDateBound(Object value) {
        return parseDateTimeBound(value).toLocalDate();
    }

    private LocalDateTime parseDateTimeBound(Object value) {
        String normalizedValue = String.valueOf(value).replace('\u202F', ' ').replace('\u00A0', ' ');
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("M/d/yy, h:mm a", Locale.US);
        return LocalDateTime.parse(normalizedValue, dateTimeFormat);
    }

    private Object normalizeValue(Object value, String dataType) {
        if (value instanceof Boolean && ("integer".equals(dataType) || "decimal".equals(dataType))) {
            return ((Boolean) value) ? 1 : 0;
        }
        return value;
    }

    private List<String> matrixLabels(Axis axis, List<Integer> indexes) {
        List<String> labels = new ArrayList<>();
        for (Integer index : indexes) {
            labels.add(criterionLabel(axis, axis.items.get(index)));
        }
        return labels;
    }

    private String criterionExpression(Axis axis, Criterion criterion) {
        if (criterion.remainder) {
            return "true";
        }
        if (criterion.missing) {
            return "missing(" + axis.variableName + ")";
        }
        String expressionVariable = expressionVariable(axis);
        String expressionDataType = expressionDataType(axis);
        if (criterion.discrete) {
            String literal = criterionLiteral(axis, criterion.actualValue, criterion.actualStoredType,
                    expressionDataType);
            if (axis.like) {
                return expressionVariable + " LIKE " + literal;
            }
            return expressionVariable + " = " + literal;
        }

        List<String> conditions = new ArrayList<>();
        if (criterion.minValue != null && !"infinite".equalsIgnoreCase(criterion.minValue)
                && !"-infinite".equalsIgnoreCase(criterion.minValue)) {
            String boundType = isWholeDateAxis(axis.dateType)
                    ? dataType(criterion.minStoredType) : expressionDataType;
            conditions.add(expressionVariable + (criterion.minInclusive ? " >= " : " > ")
                    + criterionLiteral(axis, criterion.minValue, criterion.minStoredType, boundType));
        }
        if (criterion.maxValue != null && !"infinite".equalsIgnoreCase(criterion.maxValue)) {
            String boundType = isWholeDateAxis(axis.dateType)
                    ? dataType(criterion.maxStoredType) : expressionDataType;
            conditions.add(expressionVariable + (criterion.maxInclusive ? " <= " : " < ")
                    + criterionLiteral(axis, criterion.maxValue, criterion.maxStoredType, boundType));
        }
        return conditions.isEmpty() ? "true" : String.join(" and ", conditions);
    }

    private String criterionLiteral(Axis axis, Object value, String storedType, String expressionDataType) {
        if (axis.derivationExpression != null && "date".equalsIgnoreCase(storedType)) {
            return matrixRangeBound(axis, value, storedType);
        }
        if (isWholeDateAxis(axis.dateType) && "date".equalsIgnoreCase(storedType)) {
            return rangeBound(value, storedType);
        }
        return literal(value, expressionDataType);
    }

    private String expressionVariable(Axis axis) {
        if (axis.dateType == null || axis.derivationExpression != null) {
            return axis.variableName;
        }
        String dateType = axis.dateType.toLowerCase(Locale.ROOT);
        if (dateType.contains("day")) {
            return "day(" + axis.variableName + ")";
        }
        if (dateType.contains("time")) {
            return "timepart(" + axis.variableName + ")";
        }
        return axis.variableName;
    }

    private String expressionDataType(Axis axis) {
        if (axis.dateType != null && axis.dateType.toLowerCase(Locale.ROOT).contains("day")) {
            return "integer";
        }
        if (axis.dateType != null && axis.dateType.toLowerCase(Locale.ROOT).contains("time")) {
            return "decimal";
        }
        return axis.dataType;
    }

    private String criterionLabel(Axis axis, Criterion criterion) {
        if (axis.dateType != null && axis.dateType.toLowerCase(Locale.ROOT).contains("time")
                && !criterion.missing && !criterion.remainder) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
            if (criterion.discrete && "date".equalsIgnoreCase(criterion.actualStoredType)) {
                return parseDateTimeBound(criterion.actualValue).format(format);
            }
            if ("date".equalsIgnoreCase(criterion.minStoredType) || "date".equalsIgnoreCase(criterion.maxStoredType)) {
                String minimum = criterion.minValue == null ? "" : parseDateTimeBound(criterion.minValue).format(format);
                String maximum = criterion.maxValue == null ? "" : parseDateTimeBound(criterion.maxValue).format(format);
                return minimum + " - " + maximum;
            }
        }
        return criterionLabel(criterion);
    }

    private String criterionLabel(Criterion criterion) {
        if (criterion.missing) {
            return "Missing";
        }
        if (criterion.remainder) {
            return "Remainder";
        }
        if (criterion.formattedValue != null && !criterion.formattedValue.isEmpty()) {
            return criterion.formattedValue;
        }
        if (criterion.discrete) {
            return String.valueOf(criterion.actualValue);
        }
        return String.valueOf(criterion.minValue) + " - " + String.valueOf(criterion.maxValue);
    }

    private String literal(Object value, String dataType) {
        Object normalizedValue = normalizeValue(value, dataType);
        if (normalizedValue == null) {
            return "null";
        }
        if ("boolean".equals(dataType) || "integer".equals(dataType) || "decimal".equals(dataType)) {
            return String.valueOf(normalizedValue);
        }
        return "'" + String.valueOf(normalizedValue).replace("'", "''") + "'";
    }

    private Object scalarValue(ActualValue actualValue, String fallback) {
        if (actualValue == null || actualValue.getValue() == null || actualValue.getValue().isEmpty()) {
            return fallback;
        }
        return scalarValue(actualValue.getValue(), actualValue.getStoredType());
    }

    private Object scalarValue(IBVariableValueDO value) {
        if (value == null) {
            return null;
        }
        switch (value.getType()) {
            case VALUE_TYPE_DOUBLE:
                return parseDecimal(value.getDoubleValue());
            case VALUE_TYPE_STRING:
                return value.getStringValue();
            case VALUE_TYPE_DATE:
                return firstNonEmpty(value.getDateValue(), value.getDateValueString());
            case VALUE_TYPE_BOOLEAN:
                return parseBoolean(value.getBooleanValue());
            case VALUE_TYPE_INTEGER:
                return parseInteger(value.getLongValue());
            case VALUE_TYPE_DEFAULT:
                return null;
            default:
                return firstNonEmpty(value.getStringValue(), value.getDoubleValue(), value.getLongValue(),
                        value.getBooleanValue(), value.getDateValue(), value.getDateValueString());
        }
    }

    private Object defaultValue(OutputVariable outputVariable) {
        if (outputVariable == null) {
            return null;
        }
        if ("true".equalsIgnoreCase(outputVariable.getDefaultValueIsMissing())) {
            return null;
        }
        return scalarValue(outputVariable.getDefaultValue());
    }

    private Object scalarValue(String value, String storedType) {
        if (value == null) {
            return null;
        }
        if (storedType == null) {
            return value;
        }
        switch (storedType.toLowerCase(Locale.ROOT)) {
            case "integer":
                return parseInteger(value);
            case "double":
            case "numeric":
                return parseDecimal(value);
            case "boolean":
                return parseBoolean(value);
            default:
                return value;
        }
    }

    private Object parseInteger(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return Long.valueOf(value);
        }
    }

    private Object parseDecimal(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return value;
        }
    }

    private Boolean parseBoolean(String value) {
        return value == null || value.isEmpty() ? null : Boolean.valueOf(value);
    }

    private String firstNonEmpty(String... values) {
        for (String value : values) {
            if (value != null && !value.isEmpty()) {
                return value;
            }
        }
        return null;
    }

    private String variableName(VarRefDO varRefDO) {
        String resolvedName = commonProcessing.buildVariableName(varRefDO);
        if (resolvedName != null && !resolvedName.isEmpty()) {
            return commonProcessing.sanitizeVariableName(commonProcessing.changeVariableNameToFormat(resolvedName));
        }
        String name = firstNonEmpty(varRefDO.getVarName(), varRefDO.getVarInfoId());
        String varInfoId = varRefDO.getVarInfoId();
        if (varInfoId != null && varInfoId.startsWith("globals.")) {
            name = "Global." + name;
        } else if (varInfoId != null && varInfoId.startsWith("calculatedDataItems.")) {
            name = "CalculatedDataItem." + name;
        }
        return commonProcessing.sanitizeVariableName(commonProcessing.changeVariableNameToFormat(name));
    }

    private String outputVariableName(OutputVariable outputVariable) {
        if (outputVariable == null) {
            return "crossTableOutput";
        }
        String name = firstNonEmpty(outputVariable.getName(), outputVariable.getPhysicalName(),
                outputVariable.getProcessVariableName());
        if (name == null && outputVariable.getIdentifier() != null) {
            name = firstNonEmpty(outputVariable.getIdentifier().getName(), outputVariable.getIdentifier().getId());
        }
        return commonProcessing.sanitizeVariableName(commonProcessing.changeVariableNameToFormat(
                name == null ? "crossTableOutput" : name));
    }

    private String dataType(String type) {
        if (type == null || type.isEmpty()) {
            return "string";
        }
        String normalized = type.toLowerCase(Locale.ROOT);
        if (normalized.contains("bool")) {
            return "boolean";
        }
        if (normalized.contains("int") || normalized.equals("long")) {
            return "integer";
        }
        if (normalized.contains("double") || normalized.contains("numeric")
                || normalized.contains("decimal") || normalized.contains("float")) {
            return "decimal";
        }
        if (normalized.contains("date") || normalized.contains("time")) {
            return "datetime";
        }
        return "string";
    }

    private void validateTable(int rowCount, int columnCount, List<ListOfValues> tableRows) {
        if (rowCount == 0 || columnCount == 0) {
            throw new IllegalArgumentException("Cross-Table criteria must contain row and column items");
        }
        if (tableRows == null || tableRows.size() != rowCount) {
            throw new IllegalArgumentException("Cross-Table matrix row count does not match row criteria");
        }
        for (ListOfValues row : tableRows) {
            if (row == null || row.getValues() == null || row.getValues().size() != columnCount) {
                throw new IllegalArgumentException("Cross-Table matrix column count does not match column criteria");
            }
        }
    }

    private static class Axis {
        private String variableName;
        private String sourceVariableName;
        private String sourceDataType;
        private String derivationExpression;
        private String dataType;
        private String localValueListName;
        private boolean like;
        private String dateType;
        private boolean matrixCompatible;
        private List<Criterion> items;
    }

    private static class Criterion {
        private boolean discrete;
        private boolean missing;
        private boolean remainder;
        private boolean minInclusive;
        private boolean maxInclusive;
        private String formattedValue;
        private Object actualValue;
        private String actualStoredType;
        private String minValue;
        private String maxValue;
        private String minStoredType;
        private String maxStoredType;
    }

    private static class CellTable {
        private List<List<String>> outcomeNames;
        private Map<String, Object> outcomeValues;
    }
}