/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.mapper.VarRefMapper;
import com.sas.rtdm2id.model.id.decision.*;
import com.sas.rtdm2id.model.rtdm.SplitNodeDataDO;
import com.sas.rtdm2id.model.rtdm.extension.DiscreteLineItemDO;
import com.sas.rtdm2id.model.rtdm.extension.IntervalLineItemDO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.*;

@Component
public class SplitNodeConverter {
    private final CommonProcessing commonProcessing;

    public SplitNodeConverter(CommonProcessing commonProcessing) {
        this.commonProcessing = commonProcessing;
    }

    public List<Step> createBranch(SplitNodeDataDO splitNodeDataDO, Decision decision) {
        // TODO: The createBranch method does not support split by percentages
        List<Step> stepList = new LinkedList<>();
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
            commonProcessing.checkForCalcVariable(varInfoId, varType, stepList, step, varName);
            commonProcessing.addNewSignatureItem(VarRefMapper.VAR_REF_MAPPER.varRefGet(splitOnNodeDataDO.getVarRefDO()), "none", decision);
        }

        List<BranchCase> branchCases = new ArrayList<>();
        List<SplitNodeDataDO.LineItems.SplitNodeLineItemDO> splitNodeLineItemDOs
                = splitNodeDataDO.getLineItems().getSplitNodeLineItemDOs();
        List<IntervalLineItemDO> intervalLineItemDOs = splitOnNodeDataDO.getLineItems().getIntervalLineItemDOs();
        List<DiscreteLineItemDO> discreteLineItemDOs = splitOnNodeDataDO.getLineItems().getDiscreteLineItemDOs();

        String dataType = splitNodeDataDO.getSplitOnNodeDataDO().getVarRefDO().getType();
        for (int i = 0; i < splitNodeLineItemDOs.size(); i++) {
            if ("(Missing)".equals(splitNodeLineItemDOs.get(i).getValue()) || "(Remainder)".equals(splitNodeLineItemDOs.get(i).getValue())) {
                step.setDefaultCase(commonProcessing.createConditionBranch(splitNodeLineItemDOs.get(i).getCellId(), true));
            } else {
                String variableName = commonProcessing.buildVariableName(splitOnNodeDataDO.getVarRefDO());

                BranchCase branchCase = new BranchCase();
                branchCase.setLabel(splitNodeLineItemDOs.get(i).getCellId());
                if (!intervalLineItemDOs.isEmpty()) {
                    branchCase.setCompoundCondition(createCompoundBranchCondition(intervalLineItemDOs.get(i), variableName, dataType));
                    step.setBranchType("range");
                } else if (!discreteLineItemDOs.isEmpty()) {
                    Term2 lhsTerm = new Term2();
                    String textValue = discreteLineItemDOs.get(i).getValue().getText();
                    lhsTerm.setName(commonProcessing.sanitizeVariableName(variableName));
                    branchCase.setSimpleCondition(createSimpleBranchCondition(lhsTerm, textValue, "false", "=", dataType));
                    step.setBranchType("equals");
                }
                branchCases.add(branchCase);
            }
        }
        step.setBranchCases(branchCases);
        stepList.add(step);
        return stepList;
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
            default:
                rhsConstantModified = rhsConstant;
                break;
        }
        return rhsConstantModified;
    }
}
