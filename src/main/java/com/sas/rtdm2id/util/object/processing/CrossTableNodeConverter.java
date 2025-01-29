package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.*;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.ColumnCriteria;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.CrossTable;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.CrossTableLineItemDO;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.IBVariableValueDO;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.ListOfValues;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.MaxValue;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.MinValue;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.OutputVariable;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.RowCriteria;
import com.sas.rtdm2id.model.rtdm.SplitNodeDataDO.SplitOnNodeDataDO.VarRefDO;
import com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO;
import org.springframework.stereotype.Component;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.CROSS_TABLE;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.CUSTOM_CONSTANT;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.NEW_LINE_STRING;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.REMAINDER_VALUE;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class CrossTableNodeConverter {

    private static final String MIN_VALUE_LABEL = "    Minimum Value: ";
    private static final String MAX_VALUE_LABEL = "    Maximum Value: ";
    private static final String SINGLE_VALUE_LABEL = "    Value: ";

    private final CommonProcessing commonProcessing;
    private final MapStorage mapStorage;

    public CrossTableNodeConverter(MapStorage mapStorage, CommonProcessing commonProcessing) {
        this.commonProcessing = commonProcessing;
        this.mapStorage = mapStorage;
    }

    public List<Step> addCustomObjectStep(ProcessNodeDataDO.Process process, Decision decision, Short objId,
            String nodeName) {
        List<Step> stepList = new LinkedList<>();
        Step step = new Step();
        step.setType(Step.TypeEnum.CUSTOM_OBJECT);
        Step inputValuesStep = mapStorage.getRuleSetStepMap().get(nodeName + "_input_values");
        if (inputValuesStep != null) {
            stepList.add(inputValuesStep);
        }

        step.setCustomObject(mapStorage.getCustomObjectStepHashMap().get(objId));
        stepList.add(step);
        return stepList;
    }

    public ProcessNodeDataDO createCrossTablePlaceholderNode(CrossTableNodeDataDO crossTableNodeDataDO) {
        ProcessNodeDataDO processNodeDataDO = new ProcessNodeDataDO();
        ProcessNodeDataDO.Process process = new ProcessNodeDataDO.Process();
        process.setProcessTypeDescription(CUSTOM_CONSTANT);
        process.setName(commonProcessing.makeCrossTableNodeName(crossTableNodeDataDO));
        process.setId(UUID.randomUUID().toString());
        process.setObjid(crossTableNodeDataDO.getObjid());

        StringBuilder ds2Code = new StringBuilder();
        appendDs2Header(ds2Code);
        appendOutputVariable(ds2Code, crossTableNodeDataDO.getOutputVariable());
        appendColumnCriteria(ds2Code, crossTableNodeDataDO.getColumnCriteria());
        appendRowCriteria(ds2Code, crossTableNodeDataDO.getRowCriteria());
        appendCrossTableRows(ds2Code, crossTableNodeDataDO.getCrossTable());
        appendFooter(ds2Code, crossTableNodeDataDO);

        process.setDs2code(ds2Code.toString());
        processNodeDataDO.setProcess(process);
        return processNodeDataDO;
    }

    private void appendDs2Header(StringBuilder ds2Code) {
        ds2Code.append("package ").append(CROSS_TABLE).append(" /overwrite=yes ;").append(NEW_LINE_STRING);
        ds2Code.append("/*          */").append(NEW_LINE_STRING);
        ds2Code.append("   method execute();").append(NEW_LINE_STRING);
        ds2Code.append("/*").append(NEW_LINE_STRING);
        ds2Code.append("Cross-Table Node migrated from RTDM").append(NEW_LINE_STRING);
    }

    private void appendOutputVariable(StringBuilder ds2Code, OutputVariable outputVariable) {
        if (outputVariable != null) {
            ds2Code.append("Output Variable: ").append(NEW_LINE_STRING);
            ds2Code.append("    Name:      ").append(outputVariable.getName()).append(NEW_LINE_STRING);
            ds2Code.append("    Type:      ").append(outputVariable.getType()).append(NEW_LINE_STRING);
            outputVariable.getPossibleValuesList().getPossibleValues().forEach(value -> {
                Stream.of(value.getBooleanValue(), value.getDateValue(),
                        value.getDoubleValue(), value.getLongValue(),
                        value.getStringValue())
                        .filter(Objects::nonNull)
                        .forEach(v -> appendPossibleValue(ds2Code, v));
            });
        }
    }

    private void appendPossibleValue(StringBuilder ds2Code, String value) {
        if (value != null) {
            ds2Code.append("    Possible Value: ").append(value).append(NEW_LINE_STRING);
        }
    }

    private void appendColumnCriteria(StringBuilder ds2Code, ColumnCriteria columnCriteria) {
        if (columnCriteria != null) {
            VarRefDO varRefDO = columnCriteria.getVarRefDO();
            if (varRefDO != null) {
                ds2Code.append("Column Variable: ").append(NEW_LINE_STRING);
                ds2Code.append("    Name:      ").append(varRefDO.getVarName()).append(NEW_LINE_STRING);
                ds2Code.append("    Type:      ").append(varRefDO.getType()).append(NEW_LINE_STRING);
                ds2Code.append("    Sub Type:  ").append(varRefDO.getSubType()).append(NEW_LINE_STRING);
                ds2Code.append("    VarInfoId: ").append(varRefDO.getVarInfoId()).append(NEW_LINE_STRING);
            }
            ds2Code.append("Column Criteria: ").append(NEW_LINE_STRING);
            columnCriteria.getLineItems().getCrossTableLineItemDOs().forEach(lineItem -> {
                appendValues(ds2Code, lineItem);
            });
        }
    }

    private void appendRowCriteria(StringBuilder ds2Code, RowCriteria rowCriteria) {
        if (rowCriteria != null) {
            VarRefDO varRefDO = rowCriteria.getVarRefDO();
            if (varRefDO != null) {
                ds2Code.append("Row Variable: ").append(NEW_LINE_STRING);
                ds2Code.append("    Name:      ").append(varRefDO.getVarName()).append(NEW_LINE_STRING);
                ds2Code.append("    Type:      ").append(varRefDO.getType()).append(NEW_LINE_STRING);
                ds2Code.append("    Sub Type:  ").append(varRefDO.getSubType()).append(NEW_LINE_STRING);
                ds2Code.append("    VarInfoId: ").append(varRefDO.getVarInfoId()).append(NEW_LINE_STRING);
            }
            ds2Code.append("Row Criteria: ").append(NEW_LINE_STRING);
            rowCriteria.getLineItems().getCrossTableLineItemDOs().forEach(lineItem -> {
                appendValues(ds2Code, lineItem);
            });
        }
    }

    private void appendValues(StringBuilder ds2Code, CrossTableLineItemDO lineItem) {
        String minValue = formatMinValue(lineItem);
        String maxValue = formatMaxValue(lineItem);

        if (minValue.equals(maxValue)) {
            ds2Code.append(SINGLE_VALUE_LABEL).append(minValue).append(NEW_LINE_STRING);
        } else {
            ds2Code.append(MIN_VALUE_LABEL).append(minValue).append(NEW_LINE_STRING)
                    .append(MAX_VALUE_LABEL).append(maxValue).append(NEW_LINE_STRING);
        }
    }

    private String formatMinValue(CrossTableLineItemDO lineItem) {
        MinValue minValue = lineItem.getMinValue();
        if (minValue != null && minValue.getValue() != null) {
            return minValue.getValue();
        }
        return lineItem.getFormattedValue() != null ? lineItem.getFormattedValue() : REMAINDER_VALUE;
    }

    private String formatMaxValue(CrossTableLineItemDO lineItem) {
        MaxValue maxValue = lineItem.getMaxValue();
        if (maxValue != null && maxValue.getValue() != null) {
            return maxValue.getValue();
        }
        return lineItem.getFormattedValue() != null ? lineItem.getFormattedValue() : REMAINDER_VALUE;
    }

    private void appendCrossTableRows(StringBuilder ds2Code, CrossTable crossTable) {
        List<ListOfValues> listOfValues = crossTable.getLists();
        if (listOfValues != null && !listOfValues.isEmpty()) {
            ds2Code.append("Cross-Table Rows: ").append(NEW_LINE_STRING);
            for (ListOfValues listOfValue : listOfValues) {
                ds2Code.append("    Row Values: ").append(NEW_LINE_STRING);

                listOfValue.getValues().forEach(value -> {
                    Stream.of(value.getBooleanValue(), value.getDateValue(),
                            value.getDoubleValue(), value.getLongValue(),
                            value.getStringValue())
                            .filter(Objects::nonNull)
                            .forEach(v -> appendValue(ds2Code, v));
                });
            }
        }
    }

    private void appendValue(StringBuilder ds2Code, String value) {
        if (value != null) {
            ds2Code.append("        Value: ").append(value).append(NEW_LINE_STRING);
        }
    }

    private void appendFooter(StringBuilder ds2Code, CrossTableNodeDataDO crossTableNodeDataDO) {
        ds2Code.append("Date Modified: ").append(crossTableNodeDataDO.getDateModified()).append(NEW_LINE_STRING);
        ds2Code.append("Label: ").append(crossTableNodeDataDO.getLabel()).append(NEW_LINE_STRING);
        ds2Code.append("Node ID: ").append(crossTableNodeDataDO.getNodeId()).append(NEW_LINE_STRING);
        ds2Code.append("Node Name: ").append(crossTableNodeDataDO.getNodeName()).append(NEW_LINE_STRING);
        ds2Code.append("RTDM Version Number: ").append(crossTableNodeDataDO.getVersionNumber()).append(NEW_LINE_STRING);
        ds2Code.append("*/");
        ds2Code.append("   end;").append(NEW_LINE_STRING);
        ds2Code.append("endpackage;").append(NEW_LINE_STRING);
        ds2Code.append("run;").append(NEW_LINE_STRING);
    }

    // TODO: Cross-Table node is currently a placeholder node.
    // If in the future we convert to a real node, we will need to implement the
    // methods below.
}
