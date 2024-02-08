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
import com.sas.rtdm2id.util.model.RTDM2IDConstants;

import org.springframework.stereotype.Component;

import java.util.*;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.*;

@Component
public class SplitNodeConverter {
    private final CommonProcessing commonProcessing;
    private final MapStorage mapStorage;

    public SplitNodeConverter(CommonProcessing commonProcessing, MapStorage mapStorage) {
        this.commonProcessing = commonProcessing;
        this.mapStorage = mapStorage;
    }

    public List<Step> createBranch(SplitNodeDataDO splitNodeDataDO, Decision decision, String nodeName) {
        List<Step> stepList = new LinkedList<>();

        if (splitNodeDataDO.getSplitType().equals("percents")) {
            return createStepsForBranchByPercentages(splitNodeDataDO, decision, nodeName, splitNodeDataDO.getNodeId());
        } else {
            Step step = new Step();
            step.setType(Step.TypeEnum.BRANCH);

            SplitNodeDataDO.SplitOnNodeDataDO splitOnNodeDataDO = splitNodeDataDO.getSplitOnNodeDataDO();

            String varName = null;
            SplitNodeDataDO.SplitOnNodeDataDO.VarRefDO varRefDO = splitOnNodeDataDO.getVarRefDO();
            if (varRefDO!=null) {
                // Split by data item
                varName = splitOnNodeDataDO.getVarRefDO().getVarName();
                String varInfoId = splitOnNodeDataDO.getVarRefDO().getVarInfoId();
                String varType = splitOnNodeDataDO.getVarRefDO().getType();
                commonProcessing.checkForCalcVariable(varInfoId, varType, stepList, step, varName, splitNodeDataDO.getNodeId());
                commonProcessing.addNewSignatureItem(VarRefMapper.VAR_REF_MAPPER.varRefGet(splitOnNodeDataDO.getVarRefDO()), "none", decision);
            }

            List<BranchCase> branchCases = new ArrayList<>();
            List<SplitNodeDataDO.LineItems.SplitNodeLineItemDO> splitNodeLineItemDOs
                    = splitNodeDataDO.getLineItems().getSplitNodeLineItemDOs();
            List<IntervalLineItemDO> intervalLineItemDOs = splitOnNodeDataDO.getLineItems().getIntervalLineItemDOs();
            List<DiscreteLineItemDO> discreteLineItemDOs = splitOnNodeDataDO.getLineItems().getDiscreteLineItemDOs();

        String dataType = splitNodeDataDO.getSplitOnNodeDataDO().getVarRefDO().getType();
        for (int i = 0; i < splitNodeLineItemDOs.size(); i++) {
            if (RTDM2IDConstants.REMAINDER_VALUE.equals(splitNodeLineItemDOs.get(i).getValue())) {
                step.setDefaultCase(commonProcessing.createConditionBranch(splitNodeLineItemDOs.get(i).getCellId(), true));
                if (!intervalLineItemDOs.isEmpty()) {
                    step.setBranchType(RANGE_BRANCH_TYPE);
                } else if (!discreteLineItemDOs.isEmpty()) {
                    step.setBranchType(EQUALS_BRANCH_TYPE);
                }
            } else {
                String variableName = commonProcessing.buildVariableName(splitOnNodeDataDO.getVarRefDO());

                BranchCase branchCase = new BranchCase();
                branchCase.setLabel(splitNodeLineItemDOs.get(i).getCellId());
                if (!intervalLineItemDOs.isEmpty()) {
                    branchCase.setCompoundCondition(createCompoundBranchCondition(intervalLineItemDOs.get(i), variableName, dataType));
                    step.setBranchType(RANGE_BRANCH_TYPE);
                } else if (!discreteLineItemDOs.isEmpty()) {
                    Term2 lhsTerm = new Term2();
                    String textValue;
                    if (discreteLineItemDOs.get(i).getValue()!=null) {
                        textValue = discreteLineItemDOs.get(i).getValue().getText();
                    } else {
                        // (Missing) values in RTDM
                        textValue = MISSING_CONSTANT;
                        dataType = MISSING_CONSTANT;
                    }
                    lhsTerm.setName(commonProcessing.sanitizeVariableName(variableName));
                    branchCase.setSimpleCondition(createSimpleBranchCondition(lhsTerm, textValue, "false", "=", dataType));
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

    private List<Step> createStepsForBranchByPercentages(final SplitNodeDataDO splitNodeDataDO, final Decision decision, final String nodeName, final String nodeId) {
        final List<Step> steps = new ArrayList<>();

        Step randomNumberGeneratorStep = mapStorage.getRuleSetStepMap().get(nodeName + "_random-number");
        steps.add(randomNumberGeneratorStep);

        updateRuleset(randomNumberGeneratorStep, decision, splitNodeDataDO);

        Step branchstep = new Step();
        branchstep.setType(Step.TypeEnum.BRANCH);
        branchstep.setBranchType(RANGE_BRANCH_TYPE);

        List<BranchCase> branchCases = new ArrayList<>();
        int percentCounter = 1;

        for (SplitNodeDataDO.LineItems.SplitNodeLineItemDO  splitNodeLineItemDO : splitNodeDataDO.getLineItems().getSplitNodeLineItemDOs()) {

            final String requiredPercent  = splitNodeLineItemDO.getRequiredPercent().getValue();
            CompoundBranchCondition compoundBranchCondition = new CompoundBranchCondition();
            compoundBranchCondition.setBooleanOperator(CompoundBranchCondition.BooleanOperatorEnum.AND);

            SimpleBranchCondition lhsSimpleBranchCondition = new SimpleBranchCondition();
            lhsSimpleBranchCondition.setOperator(">=");
            lhsSimpleBranchCondition.setRhsConstant(String.valueOf(percentCounter));
            Term2 lhsTerm = new Term2();
            lhsTerm.setName(RANDOM_NUMBER_VARIABLE + "_" + nodeId);
            lhsSimpleBranchCondition.setLhsTerm(lhsTerm);
            compoundBranchCondition.setLhsSimpleCondition(lhsSimpleBranchCondition);

            SimpleBranchCondition rhsSimpleBranchCondition = new SimpleBranchCondition();
            rhsSimpleBranchCondition.setOperator("<=");
            final String topPercent = String.valueOf((percentCounter + (int) Double.parseDouble(requiredPercent)) - 1);
            rhsSimpleBranchCondition.setRhsConstant(topPercent);
            Term2 lhsTerm2 = new Term2();
            lhsTerm2.setName(RANDOM_NUMBER_VARIABLE + "_" + nodeId);
            rhsSimpleBranchCondition.setLhsTerm(lhsTerm2);
            compoundBranchCondition.setRhsSimpleCondition(rhsSimpleBranchCondition);

            BranchCase branchCase = new BranchCase();
            branchCase.setLabel(splitNodeLineItemDO.getCellId());
            branchCase.setCompoundCondition(compoundBranchCondition);
            branchCases.add(branchCase);

            percentCounter = percentCounter + (int) Double.parseDouble(requiredPercent);
        }

        branchstep.setBranchCases(branchCases);
        steps.add(branchstep);

        return steps;
    }

    private void updateRuleset(final Step randomNumberGeneratorStep, final Decision decision, final SplitNodeDataDO splitNodeDataDO) {
        RuleSet ruleSet = new RuleSet();
        RuleSetStep ruleSetForUpdate = randomNumberGeneratorStep.getRuleSet();

        final boolean pathKeyEnabled = Boolean.parseBoolean(splitNodeDataDO.getEnableSeed());
        final String pathKey = splitNodeDataDO.getSeedVarRefDO().getVarName();
        final String pathKeyDataType = splitNodeDataDO.getSeedVarRefDO().getType();

        List<Signature> signatureList = new ArrayList<>();

        Signature randomNumberSignature = new Signature();
        randomNumberSignature.setDataType(SignatureTerm.DataTypeEnum.INTEGER.getValue());
        randomNumberSignature.setName(commonProcessing.sanitizeAndReduceVariableName(RANDOM_NUMBER_VARIABLE));
        randomNumberSignature.setDirection(OUTPUT_DIRECTION);
        signatureList.add(randomNumberSignature);

        if (pathKeyEnabled) {
            Signature pathKeySignature = new Signature();
            pathKeySignature.setDataType(String.valueOf(commonProcessing.getDatatypeOfVar(pathKeyDataType)));
            pathKeySignature.setName(commonProcessing.sanitizeAndReduceVariableName(pathKey));
            pathKeySignature.setDirection(INPUT_DIRECTION);
            signatureList.add(pathKeySignature);

            Mapping pathKeyMapping = new Mapping();
            pathKeyMapping.setStepTermName(commonProcessing.sanitizeAndReduceVariableName(pathKey));
            pathKeyMapping.setTargetDecisionTermName(commonProcessing.sanitizeAndReduceVariableName(pathKey));
            pathKeyMapping.setDirection(Mapping.DirectionEnum.INPUT);
            commonProcessing.checkMappingForDuplicate(pathKeyMapping, randomNumberGeneratorStep);
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

        final Action action = new Action();
        action.setExpression(pathKeyEnabled ? RANDOM_NUMBER_VARIABLE + " = MODZ(ABS(" + pathKey + ") * 811, 100)" : RANDOM_NUMBER_VARIABLE + " = RAND('uniform') * 100");
        action.setStatus("valid");
        action.setType("complex");
        rule.setActions(Collections.singletonList(action));

        commonProcessing.addActionToRuleSet(ruleSetForUpdate, rule);
        ruleSet.setSignature(signatureList);

        if (Boolean.FALSE.equals(mapStorage.getRuleSetIsUpdated().get(ruleSetForUpdate.getName()))) {
            commonProcessing.callPutRuleSet(ruleSetForUpdate, ruleSet);
        }
    }


    private CompoundBranchCondition createCompoundBranchCondition(IntervalLineItemDO intervalLineItemDO
            , String varName, String dataType) {
        CompoundBranchCondition compoundBranchCondition = new CompoundBranchCondition();
        compoundBranchCondition.setBooleanOperator(CompoundBranchCondition.BooleanOperatorEnum.AND);

        Term2 lhsTerm = new Term2();
        lhsTerm.setName(commonProcessing.sanitizeVariableName(varName));
        String lowerBoundInclusive = intervalLineItemDO.getLowerBoundInclusive();
        String upperBoundInclusive = intervalLineItemDO.getUpperBoundInclusive();

        compoundBranchCondition.setLhsSimpleCondition(createSimpleBranchCondition(lhsTerm, intervalLineItemDO.getLowerBoundObject().getText()
                , lowerBoundInclusive, ">", dataType));
        compoundBranchCondition.setRhsSimpleCondition(createSimpleBranchCondition(lhsTerm, intervalLineItemDO.getUpperBoundObject().getText()
                , upperBoundInclusive, "<", dataType));

        return compoundBranchCondition;
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
        String rhsConstantModified;
        switch (type) {
            case STRING_CONSTANT:
            case STRING_LIST_CONSTANT:
            case DATAGRID_CONSTANT:
            case "Char":
                rhsConstantModified = "'" + rhsConstant + "'";
                break;
            case INTEGER_CONSTANT:
                int round = (int) Math.round(Double.parseDouble(rhsConstant));
                rhsConstantModified = Integer.toString(round);
                break;
            case MISSING_CONSTANT:
                rhsConstantModified = rhsConstant;
                break;
            default:
                rhsConstantModified = rhsConstant;
                break;
        }
        return rhsConstantModified;
    }
}
