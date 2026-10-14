package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.*;
import com.sas.rtdm2id.model.id.segmentation.SegmentationTree;
import com.sas.rtdm2id.model.id.rules.Action;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.id.rules.Signature;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
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
        return addSegmentationTreeStep(null, decision, objId, nodeName);
        }

        public List<Step> addSegmentationTreeStep(CrossTableNodeDataDO source, Decision decision, Short objId,
            String nodeName) {
        // No _input_values RuleSet is created for Cross-Table nodes (see CommonProcessing.createRuleSets)
        List<Step> stepList = new LinkedList<>();
        Step step = new Step();
        step.setType(Step.TypeEnum.CUSTOM_OBJECT);
        String nodeId = source == null ? null : source.getNodeId();

        step.setCustomObject(mapStorage.getCustomObjectStepHashMap().get(objId));
        addSegmentationTreeMappings(decision, objId, step, source);
        List<SegmentationTree.SignatureTerm> signature = mapStorage.getSegmentationTreeSignatureMap().get(objId);
        if (signature.stream().anyMatch(term -> term.getDerivationExpression() != null)) {
            addStep(stepList, createDerivedInputStep(signature, decision, nodeName), nodeId);
        }
        addStep(stepList, step, nodeId);
        return stepList;
    }

    private void addStep(List<Step> stepList, Step step, String nodeId) {
        if (nodeId != null) {
            // Registers a cross-branch link label on the first step when the node has more than one input node
            commonProcessing.addStep(stepList, step, nodeId);
        } else {
            stepList.add(step);
        }
    }

    private Step createDerivedInputStep(List<SegmentationTree.SignatureTerm> signature, Decision decision,
            String nodeName) {
        String ruleSetName = commonProcessing.sanitizeAndReduceVariableName(nodeName + "_date_values");
        commonProcessing.findOrAddRuleSet(ruleSetName, "Date-function inputs for " + nodeName);
        Step step = mapStorage.getRuleSetStepMap().get(ruleSetName);
        if (step == null || step.getRuleSet() == null) {
            throw new IllegalStateException("Date-function input RuleSet is unavailable for " + nodeName);
        }
        Map<String, Signature> ruleSignature = new LinkedHashMap<>();
        List<Action> actions = new ArrayList<>();
        for (SegmentationTree.SignatureTerm term : signature) {
            if (term.getDerivationExpression() == null) {
                continue;
            }
            String inputName = commonProcessing.sanitizeAndReduceVariableName(term.getSourceVariableName());
            String outputName = commonProcessing.sanitizeAndReduceVariableName(term.getName());
            for (boolean input : new boolean[] {true, false}) {
                String name = input ? inputName : outputName;
                Signature ruleTerm = new Signature();
                ruleTerm.setName(name);
                ruleTerm.setDataType(input ? term.getSourceDataType() : term.getDataType());
                ruleTerm.setDirection(input ? "input" : "output");
                ruleSignature.put(name, ruleTerm);
                Mapping mapping = new Mapping();
                mapping.setStepTermName(name);
                mapping.setTargetDecisionTermName(input ? term.getSourceVariableName() : term.getName());
                mapping.setDirection(input ? Mapping.DirectionEnum.INPUT : Mapping.DirectionEnum.OUTPUT);
                commonProcessing.checkMappingForDuplicate(mapping, step);
            }
            SegmentationTree.SignatureTerm sourceTerm = new SegmentationTree.SignatureTerm();
            sourceTerm.setName(term.getSourceVariableName());
            sourceTerm.setDataType(term.getSourceDataType());
            addDecisionSignatureTerm(decision, sourceTerm, sourceTerm.getName(),
                    SignatureTerm.DataTypeEnum.fromValue(sourceTerm.getDataType()), SignatureTerm.DirectionEnum.INPUT);
            Action action = new Action();
            action.setType("complex");
            action.setExpression(outputName + " = " + term.getDerivationExpression()
                    .replace("(" + term.getSourceVariableName() + ")", "(" + inputName + ")"));
            actions.add(action);
        }
        RuleSet ruleSet = new RuleSet();
        ruleSet.setName(ruleSetName);
        ruleSet.setSignature(new ArrayList<>(ruleSignature.values()));
        if (!Boolean.TRUE.equals(mapStorage.getRuleSetIsUpdated().get(ruleSetName))) {
            commonProcessing.callPutRuleSet(step.getRuleSet(), ruleSet);
        }
        if (!Boolean.TRUE.equals(mapStorage.getRuleSetIsActionUpdated().get(ruleSetName))) {
            Rule rule = new Rule();
            rule.setName("derive_date_inputs");
            rule.setConditional("if");
            rule.setRuleFiredTrackingEnabled(false);
            rule.setActions(actions);
            commonProcessing.addActionToRuleSet(step.getRuleSet(), rule);
        }
        return step;
    }

    private void addSegmentationTreeMappings(Decision decision, Short objId, Step step, CrossTableNodeDataDO source) {
        List<SegmentationTree.SignatureTerm> treeSignature = mapStorage.getSegmentationTreeSignatureMap().get(objId);
        if (treeSignature == null || treeSignature.isEmpty()) {
            throw new IllegalStateException("Segmentation Tree signature is unavailable for Cross-Table node " + objId);
        }

        for (SegmentationTree.SignatureTerm treeTerm : treeSignature) {
            Mapping.DirectionEnum mappingDirection = Mapping.DirectionEnum.fromValue(treeTerm.getDirection());
            SignatureTerm.DataTypeEnum dataType = SignatureTerm.DataTypeEnum.fromValue(treeTerm.getDataType());
            if (treeTerm.getName() == null || mappingDirection == null || dataType == null) {
                throw new IllegalStateException("Invalid Segmentation Tree signature term for Cross-Table node " + objId);
            }

                String targetDecisionTermName;
                if (source != null && mappingDirection == Mapping.DirectionEnum.OUTPUT) {
                    String outputName = source.getOutputVariable() == null ? null : source.getOutputVariable().getName();
                    if (outputName == null || outputName.isEmpty()) {
                        outputName = treeTerm.getName();
                    }
                targetDecisionTermName = commonProcessing.sanitizeVariableName(commonProcessing.changeVariableNameToFormat(
                            source.getNodeName() + "." + outputName));
                } else {
                targetDecisionTermName = resolveTargetDecisionTermName(decision, treeTerm,
                    mappingDirection, dataType, objId);
                }
            Mapping mapping = new Mapping();
            mapping.setStepTermName(treeTerm.getName());
            mapping.setTargetDecisionTermName(targetDecisionTermName);
            mapping.setDirection(mappingDirection);
            step.addMappingsItem(mapping);

                addDecisionSignatureTerm(decision, treeTerm, targetDecisionTermName, dataType,
                    treeTerm.getDerivationExpression() == null
                            ? SignatureTerm.DirectionEnum.fromValue(mappingDirection.getValue())
                            : SignatureTerm.DirectionEnum.NONE);
        }
    }

    private String resolveTargetDecisionTermName(Decision decision, SegmentationTree.SignatureTerm treeTerm,
            Mapping.DirectionEnum mappingDirection, SignatureTerm.DataTypeEnum dataType, Short objId) {
        String termName = treeTerm.getName();
        SignatureTerm existingTerm = findDecisionSignatureTerm(decision, termName);
        if (mappingDirection != Mapping.DirectionEnum.OUTPUT
                || existingTerm == null || existingTerm.getDataType() == dataType) {
            return termName;
        }

        String uniqueTermName = commonProcessing.sanitizeAndReduceVariableName(termName + "_" + objId);
        int suffix = 2;
        while (findDecisionSignatureTerm(decision, uniqueTermName) != null) {
            uniqueTermName = commonProcessing.sanitizeAndReduceVariableName(
                    termName + "_" + objId + "_" + suffix++);
        }
        return uniqueTermName;
    }

    private void addDecisionSignatureTerm(Decision decision, SegmentationTree.SignatureTerm treeTerm,
            String decisionTermName, SignatureTerm.DataTypeEnum dataType, SignatureTerm.DirectionEnum direction) {
        SignatureTerm decisionTerm = findDecisionSignatureTerm(decision, decisionTermName);
        if (decisionTerm == null) {
            decisionTerm = new SignatureTerm();
            decisionTerm.setName(decisionTermName);
            decisionTerm.setDataType(dataType);
            decisionTerm.setLength(treeTerm.getLength());
            decisionTerm.setDirection(direction);
            decision.addSignatureItem(decisionTerm);
        } else if (decisionTerm.getDataType() != dataType) {
            decisionTerm.setDataType(SignatureTerm.DataTypeEnum.UNKNOWN);
            decisionTerm.setLength(null);
        } else if (dataType == SignatureTerm.DataTypeEnum.STRING) {
            decisionTerm.setLength(treeTerm.getLength());
        }
    }

    private SignatureTerm findDecisionSignatureTerm(Decision decision, String termName) {
        return decision.getSignature().stream()
                .filter(term -> term.getName() != null && term.getName().equalsIgnoreCase(termName))
                .findFirst()
                .orElse(null);
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
            ds2Code.append("    Level:     ").append(outputVariable.getLevel()).append(NEW_LINE_STRING);
                ds2Code.append("    Default Value Is Missing: ")
                    .append(outputVariable.getDefaultValueIsMissing()).append(NEW_LINE_STRING);
            appendIdentifier(ds2Code, outputVariable);
            outputVariable.getPossibleValuesList().getPossibleValues().forEach(value -> {
                Stream.of(value.getBooleanValue(), value.getDateValue(),
                        value.getDoubleValue(), value.getLongValue(),
                        value.getStringValue())
                        .filter(Objects::nonNull)
                        .forEach(v -> appendPossibleValue(ds2Code, v));
            });
        }
    }

    private void appendIdentifier(StringBuilder ds2Code, OutputVariable outputVariable) {
        if (outputVariable.getIdentifier() != null) {
            String identifierName = outputVariable.getIdentifier().getName();
            if (identifierName == null || identifierName.isEmpty()) {
                identifierName = outputVariable.getIdentifier().getId();
            }
            ds2Code.append("    Identifier: ").append(identifierName).append(NEW_LINE_STRING);
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
            ds2Code.append("    Like:      ").append(columnCriteria.isLike()).append(NEW_LINE_STRING);
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
            ds2Code.append("    Like:      ").append(rowCriteria.isLike()).append(NEW_LINE_STRING);
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
