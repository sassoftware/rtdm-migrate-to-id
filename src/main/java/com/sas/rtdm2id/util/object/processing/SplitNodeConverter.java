/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.mapper.VarRefMapper;
import com.sas.rtdm2id.model.id.decision.*;
import com.sas.rtdm2id.model.id.rules.Action;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.id.rules.Signature;
import com.sas.rtdm2id.model.rtdm.SplitNodeDataDO;
import com.sas.rtdm2id.model.rtdm.extension.DiscreteLineItemDO;
import com.sas.rtdm2id.model.rtdm.extension.IntervalLineItemDO;
import com.sas.rtdm2id.model.rtdm.extension.ReferenceableValue;
import com.sas.rtdm2id.util.model.RTDM2IDConstants;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.*;

@Component
public class SplitNodeConverter {
    static final String PERCENTS_SPLIT_TYPE = "percents";

    /** Number of buckets produced by the random number expression: the generated value is an integer in 0..99. */
    static final int PERCENT_BUCKETS = 100;

    private static final String UNSET_BOUND = "unset";

    private final CommonProcessing commonProcessing;
    private final MapStorage mapStorage;

    public SplitNodeConverter(CommonProcessing commonProcessing, MapStorage mapStorage) {
        this.commonProcessing = commonProcessing;
        this.mapStorage = mapStorage;
    }

    public List<Step> createBranch(SplitNodeDataDO splitNodeDataDO, Decision decision, String nodeName) {
        List<Step> stepList = new LinkedList<>();

        // Must match the check CommonProcessing.createRuleSets uses to create the random number RuleSet
        if (PERCENTS_SPLIT_TYPE.equals(splitNodeDataDO.getSplitType())) {
            return createStepsForBranchByPercentages(splitNodeDataDO, decision, nodeName, splitNodeDataDO.getNodeId());
        } else {
            Step step = new Step();
            step.setType(Step.TypeEnum.BRANCH);
            step.setName(splitNodeDataDO.getNodeName());

            SplitNodeDataDO.SplitOnNodeDataDO splitOnNodeDataDO = splitNodeDataDO.getSplitOnNodeDataDO();
            SplitNodeDataDO.SplitOnNodeDataDO.VarRefDO varRefDO = splitOnNodeDataDO == null ? null : splitOnNodeDataDO.getVarRefDO();
            if (varRefDO == null) {
                throw new IllegalArgumentException("Split node '" + splitNodeDataDO.getNodeName() + "' (" + splitNodeDataDO.getNodeId()
                        + ") of split type '" + splitNodeDataDO.getSplitType() + "' has no split data item (SplitOnNodeDataDO/VarRefDO)");
            }

            // Split by data item
            String varName = varRefDO.getVarName();
            String varInfoId = varRefDO.getVarInfoId();
            String varType = varRefDO.getType();
            commonProcessing.checkForCalcVariable(varInfoId == null ? "" : varInfoId, varType, stepList, step, varName, splitNodeDataDO.getNodeId());
            commonProcessing.addNewSignatureItem(VarRefMapper.VAR_REF_MAPPER.varRefGet(varRefDO), "none", decision);

            List<BranchCase> branchCases = new ArrayList<>();
            List<SplitNodeDataDO.LineItems.SplitNodeLineItemDO> splitNodeLineItemDOs = splitNodeDataDO.getLineItems() == null
                    ? Collections.emptyList() : splitNodeDataDO.getLineItems().getSplitNodeLineItemDOs();
            List<IntervalLineItemDO> intervalLineItemDOs = splitOnNodeDataDO.getLineItems() == null
                    ? Collections.emptyList() : splitOnNodeDataDO.getLineItems().getIntervalLineItemDOs();
            List<DiscreteLineItemDO> discreteLineItemDOs = splitOnNodeDataDO.getLineItems() == null
                    ? Collections.emptyList() : splitOnNodeDataDO.getLineItems().getDiscreteLineItemDOs();
            String producerType = commonProcessing.nodeOutputDataType(varInfoId);
            String dataType = producerType != null ? producerType : varInfoId == null ? varType
                    : mapStorage.getCalculatedVariableDataTypeMap().getOrDefault(varInfoId, varType);
            String intervalBranchType = intervalLineItemDOs.stream()
                .allMatch(interval -> Boolean.parseBoolean(interval.getMissingGroup()) || isSingleValueInterval(interval, dataType))
                ? EQUALS_BRANCH_TYPE : RANGE_BRANCH_TYPE;
            String variableName = commonProcessing.buildVariableName(varRefDO);
            ReferenceContext references = new ReferenceContext(stepList, step, decision, splitNodeDataDO.getNodeId());

        for (int i = 0; i < splitNodeLineItemDOs.size(); i++) {
            if (RTDM2IDConstants.REMAINDER_VALUE.equals(splitNodeLineItemDOs.get(i).getValue())) {
                step.setDefaultCase(commonProcessing.createConditionBranch(splitNodeLineItemDOs.get(i).getCellId(), true));
                if (!intervalLineItemDOs.isEmpty()) {
                    step.setBranchType(intervalBranchType);
                } else if (!discreteLineItemDOs.isEmpty()) {
                    step.setBranchType(EQUALS_BRANCH_TYPE);
                }
            } else {
                BranchCase branchCase = new BranchCase();
                branchCase.setLabel(splitNodeLineItemDOs.get(i).getCellId());
                if (!intervalLineItemDOs.isEmpty()) {
                    IntervalLineItemDO interval = lineItem(intervalLineItemDOs, i, splitNodeDataDO);
                    if (Boolean.parseBoolean(interval.getMissingGroup())) {
                        branchCase.setSimpleCondition(createMissingBranchCondition(variableName, dataType));
                    } else if (isSingleValueInterval(interval, dataType)) {
                        Term2 lhsTerm = new Term2();
                        lhsTerm.setName(commonProcessing.sanitizeVariableName(variableName));
                        branchCase.setSimpleCondition(createSimpleBranchCondition(lhsTerm,
                            interval.getLowerBoundObject().getText(), "false", "=", dataType));
                    } else {
                        // RTDM bound inclusivity is preserved: ID branch conditions support < and > as well as <= and >=
                        setIntervalCondition(branchCase, interval, variableName, dataType, splitNodeDataDO, references);
                    }
                    step.setBranchType(intervalBranchType);
                } else if (!discreteLineItemDOs.isEmpty()) {
                    DiscreteLineItemDO discreteLineItemDO = lineItem(discreteLineItemDOs, i, splitNodeDataDO);
                    Term2 lhsTerm = new Term2();
                    String textValue;
                    if (discreteLineItemDO.getValue() != null && discreteLineItemDO.getValue().getReferenceId() != null) {
                        // The case value is another variable (RTDM VarInfoBriefDO), not a constant
                        lhsTerm.setName(commonProcessing.sanitizeVariableName(variableName));
                        branchCase.setSimpleCondition(new SimpleBranchCondition().lhsTerm(lhsTerm).operator("=")
                            .rhsTerm(referenceTerm(discreteLineItemDO.getValue(), references)));
                    } else if (discreteLineItemDO.getValue()!=null) {
                        textValue = discreteLineItemDO.getValue().getText();
                        if (BOOLEAN_CONSTANT.equalsIgnoreCase(discreteLineItemDO.getValue().getStoredType())
                                && !SignatureTerm.DataTypeEnum.BOOLEAN.equals(datatypeOf(dataType))) {
                            if ("true".equalsIgnoreCase(textValue)) {
                                textValue = "1";
                            } else if ("false".equalsIgnoreCase(textValue)) {
                                textValue = "0";
                            }
                        }
                        lhsTerm.setName(commonProcessing.sanitizeVariableName(variableName));
                        branchCase.setSimpleCondition(createSimpleBranchCondition(lhsTerm, textValue, "false", "=", dataType));
                    } else {
                        branchCase.setSimpleCondition(createMissingBranchCondition(variableName, dataType));
                    }
                    step.setBranchType(EQUALS_BRANCH_TYPE);
                }
                branchCases.add(branchCase);
            }
        }
        step.setBranchCases(branchCases);
        commonProcessing.addStep(stepList, step, splitNodeDataDO.getNodeId());
        return stepList;
        }
    }

    private static <T> T lineItem(List<T> lineItems, int index, SplitNodeDataDO splitNodeDataDO) {
        if (index >= lineItems.size()) {
            throw new IllegalArgumentException("Split node '" + splitNodeDataDO.getNodeName() + "' (" + splitNodeDataDO.getNodeId()
                    + ") has no split criterion for line item " + (index + 1));
        }
        return lineItems.get(index);
    }

    private List<Step> createStepsForBranchByPercentages(final SplitNodeDataDO splitNodeDataDO, final Decision decision, final String nodeName, final String nodeId) {
        final List<Step> steps = new ArrayList<>();

        Step randomNumberGeneratorStep = mapStorage.getRuleSetStepMap().get(nodeName + "_random-number");
        if (randomNumberGeneratorStep == null || randomNumberGeneratorStep.getRuleSet() == null) {
            throw new IllegalStateException("Random number RuleSet '" + nodeName + "_random-number' was not created for percentage split node '"
                    + splitNodeDataDO.getNodeName() + "' (" + nodeId + ")");
        }
        updateRuleset(randomNumberGeneratorStep, decision, splitNodeDataDO);
        commonProcessing.addStep(steps, randomNumberGeneratorStep, nodeId);

        Step branchstep = new Step();
        branchstep.setType(Step.TypeEnum.BRANCH);
        branchstep.setBranchType(RANGE_BRANCH_TYPE);
        branchstep.setName(splitNodeDataDO.getNodeName());

        List<SplitNodeDataDO.LineItems.SplitNodeLineItemDO> lineItems = splitNodeDataDO.getLineItems() == null
                ? Collections.emptyList() : splitNodeDataDO.getLineItems().getSplitNodeLineItemDOs();
        List<BigDecimal> percentages = new ArrayList<>();
        for (SplitNodeDataDO.LineItems.SplitNodeLineItemDO splitNodeLineItemDO : lineItems) {
            percentages.add(requiredPercent(splitNodeDataDO, splitNodeLineItemDO));
        }
        List<int[]> ranges = percentRanges(percentages);

        List<BranchCase> branchCases = new ArrayList<>();
        for (int i = 0; i < lineItems.size(); i++) {
            CompoundBranchCondition compoundBranchCondition = new CompoundBranchCondition();
            compoundBranchCondition.setBooleanOperator(CompoundBranchCondition.BooleanOperatorEnum.AND);

            SimpleBranchCondition lhsSimpleBranchCondition = new SimpleBranchCondition();
            lhsSimpleBranchCondition.setOperator(">=");
            lhsSimpleBranchCondition.setRhsConstant(String.valueOf(ranges.get(i)[0]));
            Term2 lhsTerm = new Term2();
            lhsTerm.setName(RANDOM_NUMBER_VARIABLE + "_" + nodeId);
            lhsSimpleBranchCondition.setLhsTerm(lhsTerm);
            compoundBranchCondition.setLhsSimpleCondition(lhsSimpleBranchCondition);

            SimpleBranchCondition rhsSimpleBranchCondition = new SimpleBranchCondition();
            rhsSimpleBranchCondition.setOperator("<=");
            rhsSimpleBranchCondition.setRhsConstant(String.valueOf(ranges.get(i)[1]));
            Term2 lhsTerm2 = new Term2();
            lhsTerm2.setName(RANDOM_NUMBER_VARIABLE + "_" + nodeId);
            rhsSimpleBranchCondition.setLhsTerm(lhsTerm2);
            compoundBranchCondition.setRhsSimpleCondition(rhsSimpleBranchCondition);

            BranchCase branchCase = new BranchCase();
            branchCase.setLabel(lineItems.get(i).getCellId());
            branchCase.setCompoundCondition(compoundBranchCondition);
            branchCases.add(branchCase);
        }

        branchstep.setBranchCases(branchCases);
        commonProcessing.addStep(steps, branchstep, nodeId);

        return steps;
    }

    private static BigDecimal requiredPercent(SplitNodeDataDO splitNodeDataDO, SplitNodeDataDO.LineItems.SplitNodeLineItemDO lineItem) {
        String value = lineItem.getRequiredPercent() == null ? null : lineItem.getRequiredPercent().getValue();
        try {
            return new BigDecimal(value.trim());
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Percentage split node '" + splitNodeDataDO.getNodeName() + "' (" + splitNodeDataDO.getNodeId()
                    + ") has an invalid required percent '" + value + "' for cell " + lineItem.getCellId(), e);
        }
    }

    /**
     * Converts the required percentages of a percentage split into inclusive, contiguous ranges over the integer
     * random number domain 0..99. Boundaries are the rounded (half up) cumulative percentages, so the bucket counts
     * always add up to exactly 100 and no rounding error accumulates (e.g. 33.3/33.3/33.4 gives 0-32, 33-66, 67-99 and
     * 12.5/87.5 gives 0-12, 13-99). The last branch always extends to 99 so no random number is left unrouted.
     * A branch whose percentage rounds to nothing gets an empty range (lower bound greater than upper bound).
     */
    static List<int[]> percentRanges(List<BigDecimal> percentages) {
        List<int[]> ranges = new ArrayList<>();
        BigDecimal cumulative = BigDecimal.ZERO;
        int lower = 0;
        for (int i = 0; i < percentages.size(); i++) {
            cumulative = cumulative.add(percentages.get(i));
            int upperExclusive = cumulative.setScale(0, RoundingMode.HALF_UP).intValue();
            upperExclusive = Math.max(lower, Math.min(PERCENT_BUCKETS, upperExclusive));
            if (i == percentages.size() - 1) {
                upperExclusive = PERCENT_BUCKETS;
            }
            ranges.add(new int[] {lower, upperExclusive - 1});
            lower = upperExclusive;
        }
        return ranges;
    }

    private void updateRuleset(final Step randomNumberGeneratorStep, final Decision decision, final SplitNodeDataDO splitNodeDataDO) {
        RuleSet ruleSet = new RuleSet();
        RuleSetStep ruleSetForUpdate = randomNumberGeneratorStep.getRuleSet();

        final boolean pathKeyEnabled = Boolean.parseBoolean(splitNodeDataDO.getEnableSeed());

        List<Signature> signatureList = new ArrayList<>();

        Signature randomNumberSignature = new Signature();
        randomNumberSignature.setDataType(SignatureTerm.DataTypeEnum.INTEGER.getValue());
        randomNumberSignature.setName(commonProcessing.sanitizeAndReduceVariableName(RANDOM_NUMBER_VARIABLE));
        randomNumberSignature.setDirection(OUTPUT_DIRECTION);
        signatureList.add(randomNumberSignature);

        final Action action = new Action();

        // Both expressions produce an integer in 0..PERCENT_BUCKETS-1, the domain percentRanges() partitions
        if (pathKeyEnabled) {
            if (splitNodeDataDO.getSeedVarRefDO() == null || splitNodeDataDO.getSeedVarRefDO().getVarName() == null) {
                throw new IllegalArgumentException("Percentage split node '" + splitNodeDataDO.getNodeName() + "' (" + splitNodeDataDO.getNodeId()
                        + ") has a seed enabled but no seed data item (SeedVarRefDO)");
            }
            final String pathKey = commonProcessing.sanitizeAndReduceVariableName(splitNodeDataDO.getSeedVarRefDO().getVarName());
            final String pathKeyDataType = splitNodeDataDO.getSeedVarRefDO().getType();

            Signature pathKeySignature = new Signature();
            pathKeySignature.setDataType(String.valueOf(datatypeOf(pathKeyDataType)));
            pathKeySignature.setName(pathKey);
            pathKeySignature.setDirection(INPUT_DIRECTION);
            signatureList.add(pathKeySignature);

            Mapping pathKeyMapping = new Mapping();
            pathKeyMapping.setStepTermName(pathKey);
            pathKeyMapping.setTargetDecisionTermName(pathKey);
            pathKeyMapping.setDirection(Mapping.DirectionEnum.INPUT);
            commonProcessing.checkMappingForDuplicate(pathKeyMapping, randomNumberGeneratorStep);

            action.setExpression(RANDOM_NUMBER_VARIABLE + " = FLOOR(MODZ(ABS(" + pathKey + ") * 811, " + PERCENT_BUCKETS + "))");
        } else {
            action.setExpression(RANDOM_NUMBER_VARIABLE + " = FLOOR(RAND('uniform') * " + PERCENT_BUCKETS + ")");
        }

        SignatureTerm signatureTerm = new SignatureTerm();
        signatureTerm.setName(RANDOM_NUMBER_VARIABLE + "_" + splitNodeDataDO.getNodeId());
        signatureTerm.setDataType(SignatureTerm.DataTypeEnum.INTEGER);
        signatureTerm.setDirection(SignatureTerm.DirectionEnum.NONE);
        commonProcessing.checkSignatureTermForDuplicate(NONE_DIRECTION, decision, signatureTerm);

        Mapping mapping = new Mapping();
        mapping.setStepTermName(RANDOM_NUMBER_VARIABLE);
        mapping.setTargetDecisionTermName(RANDOM_NUMBER_VARIABLE + "_" + splitNodeDataDO.getNodeId());
        mapping.setDirection(Mapping.DirectionEnum.OUTPUT);
        commonProcessing.checkMappingForDuplicate(mapping, randomNumberGeneratorStep);

        ruleSet.setName(ruleSetForUpdate.getName());
        Rule rule = new Rule();
        rule.setName(UUID.randomUUID().toString());
        rule.setConditional("if");
        rule.setRuleFiredTrackingEnabled(false);

        action.setStatus("valid");
        action.setType("complex");
        rule.setActions(Collections.singletonList(action));

        ruleSet.setSignature(signatureList);

        // Like the Reply node: put the signature in place before posting the rule that references it, and leave an
        // existing (reused) RuleSet alone so re-running a migration does not add duplicate rules.
        if (!Boolean.TRUE.equals(mapStorage.getRuleSetIsUpdated().get(ruleSetForUpdate.getName()))) {
            commonProcessing.callPutRuleSet(ruleSetForUpdate, ruleSet);
        }
        if (!Boolean.TRUE.equals(mapStorage.getRuleSetIsActionUpdated().get(ruleSetForUpdate.getName()))) {
            commonProcessing.addActionToRuleSet(ruleSetForUpdate, rule);
        }
    }

    private SignatureTerm.DataTypeEnum datatypeOf(String dataType) {
        return dataType == null ? SignatureTerm.DataTypeEnum.ANY : commonProcessing.getDatatypeOfVar(dataType);
    }

    private static boolean hasBound(ReferenceableValue bound, String text) {
        return bound != null && (bound.getReferenceId() != null
                || text != null && !text.trim().isEmpty() && !UNSET_BOUND.equalsIgnoreCase(text.trim()));
    }

    private static boolean isReference(ReferenceableValue bound) {
        return bound != null && bound.getReferenceId() != null;
    }

    /** Where the variables referenced by split values are registered (signature, calculated-item scheduling). */
    private record ReferenceContext(List<Step> stepList, Step step, Decision decision, String nodeId) { }

    /**
     * Term for a split value that references another variable. The variable is registered the same way the split
     * variable itself is: calculated items are scheduled before the node and the variable is added to the signature.
     */
    private Term2 referenceTerm(ReferenceableValue value, ReferenceContext context) {
        SplitNodeDataDO.SplitOnNodeDataDO.VarRefDO reference = new SplitNodeDataDO.SplitOnNodeDataDO.VarRefDO();
        reference.setVarInfoId(value.getReferenceId());
        reference.setVarName(value.getReferenceName() != null ? value.getReferenceName() : value.getReferenceId());
        reference.setType(value.getReferenceType());
        commonProcessing.checkForCalcVariable(reference.getVarInfoId(), reference.getType(), context.stepList(), context.step(),
                reference.getVarName(), context.nodeId());
        commonProcessing.addNewSignatureItem(VarRefMapper.VAR_REF_MAPPER.varRefGet(reference), "none", context.decision());
        Term2 term = new Term2();
        term.setName(commonProcessing.sanitizeVariableName(commonProcessing.buildVariableName(reference)));
        return term;
    }

    private static String lowerText(IntervalLineItemDO interval) {
        return interval.getLowerBoundObject() == null ? null : interval.getLowerBoundObject().getText();
    }

    private static String upperText(IntervalLineItemDO interval) {
        return interval.getUpperBoundObject() == null ? null : interval.getUpperBoundObject().getText();
    }

    private boolean isSingleValueInterval(IntervalLineItemDO interval, String dataType) {
        if (!Boolean.parseBoolean(interval.getLowerBoundInclusive()) || !Boolean.parseBoolean(interval.getUpperBoundInclusive())) {
            return false;
        }
        String lower = lowerText(interval);
        String upper = upperText(interval);
        if (!hasBound(interval.getLowerBoundObject(), lower) || !hasBound(interval.getUpperBoundObject(), upper)
                || isReference(interval.getLowerBoundObject()) || isReference(interval.getUpperBoundObject())) {
            return false;
        }
        SignatureTerm.DataTypeEnum type = datatypeOf(dataType);
        if (type == SignatureTerm.DataTypeEnum.DECIMAL || type == SignatureTerm.DataTypeEnum.INTEGER) {
            try {
                return new BigDecimal(lower.trim()).compareTo(new BigDecimal(upper.trim())) == 0;
            } catch (NumberFormatException e) {
                return Objects.equals(lower, upper);
            }
        }
        return Objects.equals(lower, upper);
    }

    private void setIntervalCondition(BranchCase branchCase, IntervalLineItemDO intervalLineItemDO, String varName,
            String dataType, SplitNodeDataDO splitNodeDataDO, ReferenceContext references) {
        Term2 lhsTerm = new Term2();
        lhsTerm.setName(commonProcessing.sanitizeVariableName(varName));
        String lower = lowerText(intervalLineItemDO);
        String upper = upperText(intervalLineItemDO);
        boolean hasLower = hasBound(intervalLineItemDO.getLowerBoundObject(), lower);
        boolean hasUpper = hasBound(intervalLineItemDO.getUpperBoundObject(), upper);

        if (hasLower && hasUpper) {
            CompoundBranchCondition compoundBranchCondition = new CompoundBranchCondition();
            compoundBranchCondition.setBooleanOperator(CompoundBranchCondition.BooleanOperatorEnum.AND);
            compoundBranchCondition.setLhsSimpleCondition(boundCondition(lhsTerm, intervalLineItemDO.getLowerBoundObject(), lower,
                    intervalLineItemDO.getLowerBoundInclusive(), ">", dataType, references));
            compoundBranchCondition.setRhsSimpleCondition(boundCondition(lhsTerm, intervalLineItemDO.getUpperBoundObject(), upper,
                    intervalLineItemDO.getUpperBoundInclusive(), "<", dataType, references));
            branchCase.setCompoundCondition(compoundBranchCondition);
        } else if (hasLower) {
            // Open-ended interval (no upper bound)
            branchCase.setSimpleCondition(boundCondition(lhsTerm, intervalLineItemDO.getLowerBoundObject(), lower,
                    intervalLineItemDO.getLowerBoundInclusive(), ">", dataType, references));
        } else if (hasUpper) {
            // Open-ended interval (no lower bound)
            branchCase.setSimpleCondition(boundCondition(lhsTerm, intervalLineItemDO.getUpperBoundObject(), upper,
                    intervalLineItemDO.getUpperBoundInclusive(), "<", dataType, references));
        } else {
            throw new IllegalArgumentException("Split node '" + splitNodeDataDO.getNodeName() + "' (" + splitNodeDataDO.getNodeId()
                    + ") has an interval without lower or upper bound for cell " + branchCase.getLabel());
        }
    }

    /** A bound is either a constant (rhsConstant) or a variable reference (rhsTerm). */
    private SimpleBranchCondition boundCondition(Term2 lhsTerm, ReferenceableValue bound, String text, String inclusive,
            String operator, String dataType, ReferenceContext references) {
        if (isReference(bound)) {
            return new SimpleBranchCondition().lhsTerm(lhsTerm).operator(operator + ("true".equals(inclusive) ? "=" : ""))
                .rhsTerm(referenceTerm(bound, references));
        }
        return createSimpleBranchCondition(lhsTerm, text, inclusive, operator, dataType);
    }

    private SimpleBranchCondition createMissingBranchCondition(String variableName, String dataType) {
        String name = commonProcessing.sanitizeVariableName(variableName);
        if (SignatureTerm.DataTypeEnum.STRING.equals(datatypeOf(dataType))) {
            Term2 lhsTerm = new Term2();
            lhsTerm.setName(name);
            return createSimpleBranchCondition(lhsTerm, "''", "false", "=", MISSING_CONSTANT);
        }
        return new SimpleBranchCondition()
            .lhsConditionExpression("missing(" + name + ")")
            .lhsConditionExpressionType(SignatureTerm.DataTypeEnum.DECIMAL.getValue())
            .operator("=")
            .rhsConstant("1");
    }

    private SimpleBranchCondition createSimpleBranchCondition(Term2 lhsTerm, String rhsConstant, String inclusive, String operator
            , String dataType) {
        SimpleBranchCondition simpleBranchCondition = new SimpleBranchCondition();
        simpleBranchCondition.setOperator(operator + ("true".equals(inclusive) ? "=" : ""));
        String rhsConstantModified = modifyRhsConstantByType(dataType, rhsConstant);
        simpleBranchCondition.setRhsConstant(rhsConstantModified);
        simpleBranchCondition.setLhsTerm(lhsTerm);
        return simpleBranchCondition;
    }

    private String modifyRhsConstantByType(String type, String rhsConstant) {
        if (type == null || rhsConstant == null || MISSING_CONSTANT.equalsIgnoreCase(type)) {
            return rhsConstant;
        }
        if (DATAGRID_CONSTANT.equalsIgnoreCase(type)) {
            return quote(rhsConstant);
        }
        // Type names are matched case-insensitively (e.g. "Char", "Numeric", "string")
        switch (commonProcessing.getDatatypeOfVar(type)) {
            case STRING:
                return quote(rhsConstant);
            case INTEGER:
                try {
                    return new BigDecimal(rhsConstant.trim()).setScale(0, RoundingMode.HALF_UP).toPlainString();
                } catch (NumberFormatException e) {
                    return rhsConstant;
                }
            case BOOLEAN:
                if ("true".equalsIgnoreCase(rhsConstant) || "1".equals(rhsConstant)) {
                    return "True";
                } else if ("false".equalsIgnoreCase(rhsConstant) || "0".equals(rhsConstant)) {
                    return "False";
                }
                return rhsConstant;
            case DATETIME:
                return dateTimeLiteral(rhsConstant);
            default:
                return rhsConstant;
        }
    }

    private static String quote(String value) {
        return "'" + value.replace("'", "''") + "'";
    }

    /**
     * RTDM date items are ID datetime terms (see CommonProcessing.getDatatypeOfVar), so date constants are emitted as
     * SAS datetime literals, e.g. '15JAN2023:00:00:00'dt. Values already written as SAS date/datetime literals are kept;
     * any other unparseable value is quoted rather than emitted as a bare token.
     */
    static String dateTimeLiteral(String value) {
        String text = value.trim();
        if (RtdmDates.isSasDateLiteral(text)) {
            return text;
        }
        return RtdmDates.parseDateTime(text).map(RtdmDates::sasDateTimeLiteral).orElseGet(() -> quote(text));
    }
}
