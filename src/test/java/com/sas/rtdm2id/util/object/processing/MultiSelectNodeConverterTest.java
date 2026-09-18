package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.SignatureTerm;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.rtdm.MultiSelectNodeDataDO;
import com.sas.rtdm2id.model.rtdm.extension.Children;
import com.sas.rtdm2id.model.rtdm.extension.FilterNodeDO;
import com.sas.rtdm2id.model.rtdm.extension.LogicNodeDO;
import com.sas.rtdm2id.model.rtdm.extension.RootNode;
import com.sas.rtdm2id.model.rtdm.extension.SQLExpression;
import com.sas.rtdm2id.model.rtdm.extension.Values;
import com.sas.rtdm2id.model.rtdm.extension.VarRef;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.GREATER_THAN_OR_EQUALS_TO;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.CHAR_CONSTANT;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.DATE_CONSTANT;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.IS_MISSING;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.LIKE;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.LESS_THAN_OR_EQUALS_TO;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.NUMERIC_CONSTANT;
import static com.sas.rtdm2id.util.model.RTDM2IDConstants.NONE_DIRECTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class MultiSelectNodeConverterTest {

    @Test
    void rendersParenthesesFromNestedLogicNodes() {
        CommonProcessing commonProcessing = mock(CommonProcessing.class);
        when(commonProcessing.sanitizeVariableName(anyString())).thenAnswer(invocation -> invocation.getArgument(0));
        doAnswer(invocation -> {
            List<Step> steps = invocation.getArgument(0);
            steps.add(invocation.getArgument(1));
            return null;
        }).when(commonProcessing).addStep(anyList(), any(Step.class), anyString());

        MultiSelectNodeDataDO multiSelectNode = new MultiSelectNodeDataDO();
        multiSelectNode.setNodeId("multi-select");
        SQLExpression sqlExpression = new SQLExpression();
        sqlExpression.setRootNode(createRootNode());
        multiSelectNode.setSqlExpression(sqlExpression);

        List<Step> steps = new MultiSelectNodeConverter(commonProcessing)
                .createConditionByFilter(multiSelectNode, new Decision());

        assertEquals(1, steps.size());
        assertEquals("DoubleInput = . or (DoubleInput >= 0.1 and DoubleInput <= 0.2)"
                        + " or (DoubleInput >= 0.3 and DoubleInput <= 0.4)"
                        + " or (DoubleInput >= 0.5 and DoubleInput <= 0.6)",
                steps.get(0).getConditionExpression());
    }

    @Test
    void rendersStringMissingFilterWithoutListValues() {
        CommonProcessing commonProcessing = mock(CommonProcessing.class);
        when(commonProcessing.sanitizeVariableName(anyString())).thenAnswer(invocation -> invocation.getArgument(0));
        doAnswer(invocation -> {
            List<Step> steps = invocation.getArgument(0);
            steps.add(invocation.getArgument(1));
            return null;
        }).when(commonProcessing).addStep(anyList(), any(Step.class), anyString());

        MultiSelectNodeDataDO multiSelectNode = new MultiSelectNodeDataDO();
        multiSelectNode.setNodeId("multi-select");
        SQLExpression sqlExpression = new SQLExpression();
        sqlExpression.setRootNode(createStringInputRootNode());
        multiSelectNode.setSqlExpression(sqlExpression);

        List<Step> steps = new MultiSelectNodeConverter(commonProcessing)
                .createConditionByFilter(multiSelectNode, new Decision());

        assertEquals("StringInput = '' or StringInput IN ('a','b','c')",
                steps.get(0).getConditionExpression());
    }

    @Test
    void rendersStringLikeValuesAsSeparateLikeClauses() {
        CommonProcessing commonProcessing = mock(CommonProcessing.class);
        when(commonProcessing.sanitizeVariableName(anyString())).thenAnswer(invocation -> invocation.getArgument(0));
        doAnswer(invocation -> {
            List<Step> steps = invocation.getArgument(0);
            steps.add(invocation.getArgument(1));
            return null;
        }).when(commonProcessing).addStep(anyList(), any(Step.class), anyString());

        MultiSelectNodeDataDO multiSelectNode = new MultiSelectNodeDataDO();
        multiSelectNode.setNodeId("multi-select");
        SQLExpression sqlExpression = new SQLExpression();
        sqlExpression.setRootNode(createStringLikeRootNode());
        multiSelectNode.setSqlExpression(sqlExpression);

        List<Step> steps = new MultiSelectNodeConverter(commonProcessing)
                .createConditionByFilter(multiSelectNode, new Decision());

        assertEquals("StringInput = '' or (StringInput LIKE 'a' or StringInput LIKE 'b' or StringInput LIKE 'c')",
                steps.get(0).getConditionExpression());
    }

    @Test
    void rendersDataPickerGlobalAndRegistersIt() {
        CommonProcessing commonProcessing = mock(CommonProcessing.class);
        when(commonProcessing.sanitizeVariableName(anyString())).thenAnswer(invocation ->
            ((String) invocation.getArgument(0)).replace(" ", "").replace(".", ""));
        doAnswer(invocation -> {
            List<Step> steps = invocation.getArgument(0);
            steps.add(invocation.getArgument(1));
            return null;
        }).when(commonProcessing).addStep(anyList(), any(Step.class), anyString());

        MultiSelectNodeDataDO multiSelectNode = new MultiSelectNodeDataDO();
        multiSelectNode.setNodeId("multi-select");
        SQLExpression sqlExpression = new SQLExpression();
        sqlExpression.setRootNode(createDataPickerRootNode());
        multiSelectNode.setSqlExpression(sqlExpression);

        Decision decision = new Decision();
        List<Step> steps = new MultiSelectNodeConverter(commonProcessing)
                .createConditionByFilter(multiSelectNode, decision);

        assertEquals("eventsIntInput = ApplicationScore", steps.get(0).getConditionExpression());
        verify(commonProcessing).createGlobalVariableForDataPicker(
                "globals.ApplicationScore", "ApplicationScore", NUMERIC_CONSTANT, decision);
    }

        @Test
        void rendersRequestVariablesWithEventsPrefix() {
        CommonProcessing commonProcessing = mock(CommonProcessing.class);
        when(commonProcessing.sanitizeVariableName(anyString())).thenAnswer(invocation ->
            ((String) invocation.getArgument(0)).replace(" ", "").replace(".", ""));
        doAnswer(invocation -> {
            List<Step> steps = invocation.getArgument(0);
            steps.add(invocation.getArgument(1));
            return null;
        }).when(commonProcessing).addStep(anyList(), any(Step.class), anyString());

        RootNode rootNode = new RootNode();
        rootNode.setType("and");
        Children rootChildren = new Children();
        rootChildren.getFilterNodeDOs().add(createRequestFilter(
            "int", "events.Int Input", "Int Input", NUMERIC_CONSTANT, "1"));
        rootChildren.getFilterNodeDOs().add(createRequestFilter(
            "double", "events.Double Input", "Double Input", NUMERIC_CONSTANT, "0.1"));
        rootChildren.getFilterNodeDOs().add(createRequestFilter(
            "boolean", "events.Boolean Input", "Boolean Input", NUMERIC_CONSTANT, "1"));
        rootChildren.getFilterNodeDOs().add(createRequestFilter(
            "date", "events.Date Input", "Date Input", DATE_CONSTANT, "2026-09-17"));
        rootChildren.getFilterNodeDOs().add(createRequestFilter(
            "string", "events.String Input", "String Input", CHAR_CONSTANT, "value"));
        rootNode.setChildren(rootChildren);

        MultiSelectNodeDataDO multiSelectNode = new MultiSelectNodeDataDO();
        multiSelectNode.setNodeId("multi-select");
        SQLExpression sqlExpression = new SQLExpression();
        sqlExpression.setRootNode(rootNode);
        multiSelectNode.setSqlExpression(sqlExpression);

        List<Step> steps = new MultiSelectNodeConverter(commonProcessing)
            .createConditionByFilter(multiSelectNode, new Decision());

        assertEquals("eventsIntInput = 1 and eventsDoubleInput = 0.1"
                + " and eventsBooleanInput = 1 and eventsDateInput = 2026-09-17"
                + " and eventsStringInput = 'value'",
            steps.get(0).getConditionExpression());
        }

            @Test
            void rendersDatePartFiltersWithDateFunctions() {
            CommonProcessing commonProcessing = mock(CommonProcessing.class);
            when(commonProcessing.sanitizeVariableName(anyString())).thenAnswer(invocation ->
                ((String) invocation.getArgument(0)).replace(" ", "").replace(".", ""));
            doAnswer(invocation -> {
                List<Step> steps = invocation.getArgument(0);
                steps.add(invocation.getArgument(1));
                return null;
            }).when(commonProcessing).addStep(anyList(), any(Step.class), anyString());

            RootNode rootNode = new RootNode();
            rootNode.setType("and");
            Children rootChildren = new Children();
            rootChildren.getFilterNodeDOs().add(createDatePartFilter("day", "1"));
            rootChildren.getFilterNodeDOs().add(createDatePartFilter("month", "2"));
            rootChildren.getFilterNodeDOs().add(createDatePartFilter("year", "2026"));
            rootChildren.getFilterNodeDOs().add(createDatePartFilter("whole", "2026-09-17"));
            rootNode.setChildren(rootChildren);

            MultiSelectNodeDataDO multiSelectNode = new MultiSelectNodeDataDO();
            multiSelectNode.setNodeId("multi-select");
            SQLExpression sqlExpression = new SQLExpression();
            sqlExpression.setRootNode(rootNode);
            multiSelectNode.setSqlExpression(sqlExpression);

            List<Step> steps = new MultiSelectNodeConverter(commonProcessing)
                .createConditionByFilter(multiSelectNode, new Decision());

            assertEquals("DAY(eventsDateInput) = 1 and MONTH(eventsDateInput) = 2"
                    + " and YEAR(eventsDateInput) = 2026 and eventsDateInput = '2026-09-17'",
                steps.get(0).getConditionExpression());
            }

    @Test
    void rendersVariableRangeDataPickerValues() {
        CommonProcessing commonProcessing = mock(CommonProcessing.class);
        when(commonProcessing.sanitizeVariableName(anyString())).thenAnswer(invocation ->
            ((String) invocation.getArgument(0)).replace(" ", "").replace(".", ""));
        doAnswer(invocation -> {
            List<Step> steps = invocation.getArgument(0);
            steps.add(invocation.getArgument(1));
            return null;
        }).when(commonProcessing).addStep(anyList(), any(Step.class), anyString());

        MultiSelectNodeDataDO multiSelectNode = new MultiSelectNodeDataDO();
        multiSelectNode.setNodeId("multi-select");
        SQLExpression sqlExpression = new SQLExpression();
        sqlExpression.setRootNode(createVariableRangeRootNode());
        multiSelectNode.setSqlExpression(sqlExpression);

        List<Step> steps = new MultiSelectNodeConverter(commonProcessing)
                .createConditionByFilter(multiSelectNode, new Decision());

        assertEquals("eventsDoubleInput >= eventsBooleanInput and eventsDoubleInput <= eventsIntInput",
                steps.get(0).getConditionExpression());
    }

    @Test
    void rendersTodaysDateAndTimeAndAddsSignatureTerm() {
        CommonProcessing commonProcessing = new CommonProcessing(new RestTemplate(), new MapStorage(), new TreeUtil());

        RootNode rootNode = new RootNode();
        rootNode.setOperator("lessThanEquals");
        rootNode.setFormattedValues("Today's Date and Time");
        rootNode.setValues(createValues(null));
        rootNode.setChildren(new Children());
        VarRef varRef = createVarRef(DATE_CONSTANT);
        varRef.setVarInfoId("events.Date Input");
        varRef.setVarName("Date Input");
        rootNode.setVarRef(varRef);

        MultiSelectNodeDataDO multiSelectNode = new MultiSelectNodeDataDO();
        multiSelectNode.setNodeId("multi-select");
        SQLExpression sqlExpression = new SQLExpression();
        sqlExpression.setRootNode(rootNode);
        multiSelectNode.setSqlExpression(sqlExpression);

        Decision decision = new Decision();
        List<Step> steps = new MultiSelectNodeConverter(commonProcessing)
                .createConditionByFilter(multiSelectNode, decision);

        assertEquals("eventsDateInput <= TodaysDateAndTime", steps.get(0).getConditionExpression());
        assertEquals(2, decision.getSignature().size());
        assertTrue(decision.getSignature().stream()
            .anyMatch(signature -> "TodaysDateAndTime".equals(signature.getName())));
        SignatureTerm signatureTerm = decision.getSignature().stream()
            .filter(signature -> "TodaysDateAndTime".equals(signature.getName()))
            .findFirst()
            .get();
        assertEquals("TodaysDateAndTime", signatureTerm.getName());
        assertEquals(SignatureTerm.DataTypeEnum.DATETIME, signatureTerm.getDataType());
        assertEquals(SignatureTerm.DirectionEnum.fromValue(NONE_DIRECTION), signatureTerm.getDirection());
    }

    private RootNode createRootNode() {
        RootNode rootNode = new RootNode();
        rootNode.setType("or");
        Children rootChildren = new Children();
        rootChildren.getFilterNodeDOs().add(createFilter("missing", IS_MISSING, null));
        rootChildren.getLogicNodeDOs().add(createRange("range-one", "0.1", "0.2"));
        rootChildren.getLogicNodeDOs().add(createRange("range-two", "0.3", "0.4"));
        rootChildren.getLogicNodeDOs().add(createRange("range-three", "0.5", "0.6"));
        rootNode.setChildren(rootChildren);
        return rootNode;
    }

    private RootNode createStringInputRootNode() {
        RootNode rootNode = new RootNode();
        rootNode.setType("or");
        Children rootChildren = new Children();
        rootChildren.getFilterNodeDOs().add(createFilter("missing", IS_MISSING, "a,b,c", CHAR_CONSTANT));
        rootChildren.getFilterNodeDOs().add(createFilter("values", "inList", "a,b,c", CHAR_CONSTANT));
        rootNode.setChildren(rootChildren);
        return rootNode;
    }

    private RootNode createStringLikeRootNode() {
        RootNode rootNode = new RootNode();
        rootNode.setType("or");
        Children rootChildren = new Children();
        rootChildren.getFilterNodeDOs().add(createFilter("missing", IS_MISSING, "a,b,c", CHAR_CONSTANT));
        rootChildren.getFilterNodeDOs().add(createFilter("values", LIKE, "a,b,c", CHAR_CONSTANT));
        rootNode.setChildren(rootChildren);
        return rootNode;
    }

    private RootNode createDataPickerRootNode() {
        RootNode rootNode = new RootNode();
        rootNode.setOperator("equals");
        rootNode.setFormattedValues("Global.ApplicationScore");
        rootNode.setValues(createValues(null));
        rootNode.setChildren(new Children());
        VarRef varRef = createVarRef(NUMERIC_CONSTANT);
        varRef.setVarInfoId("events.Int Input");
        varRef.setVarName("Int Input");
        rootNode.setVarRef(varRef);
        return rootNode;
    }

    private RootNode createVariableRangeRootNode() {
        RootNode rootNode = new RootNode();
        rootNode.setType("and");
        Children rootChildren = new Children();
        rootChildren.getFilterNodeDOs().add(createVariableFilter(
                "minimum", GREATER_THAN_OR_EQUALS_TO, "Request.Boolean Input"));
        rootChildren.getFilterNodeDOs().add(createVariableFilter(
                "maximum", LESS_THAN_OR_EQUALS_TO, "Request.Int Input"));
        rootNode.setChildren(rootChildren);
        return rootNode;
    }

    private FilterNodeDO createVariableFilter(String objId, String operator, String variableReference) {
        FilterNodeDO filterNode = createFilter(objId, operator, null);
        filterNode.setFormattedValues(variableReference);
        filterNode.setValues(createValues(null));
        filterNode.getVarRef().setVarInfoId("events.Double Input");
        return filterNode;
    }

    private FilterNodeDO createRequestFilter(String objId, String varInfoId, String varName,
                                             String type, String value) {
        FilterNodeDO filterNode = createFilter(objId, "equals", value, type);
        filterNode.getVarRef().setVarInfoId(varInfoId);
        filterNode.getVarRef().setVarName(varName);
        return filterNode;
    }

    private FilterNodeDO createDatePartFilter(String dateType, String value) {
        FilterNodeDO filterNode = createRequestFilter(
                dateType, "events.Date Input", "Date Input", DATE_CONSTANT, value);
        filterNode.setDateType(dateType);
        return filterNode;
    }

    private LogicNodeDO createRange(String objId, String minimum, String maximum) {
        LogicNodeDO logicNode = new LogicNodeDO();
        logicNode.setObjid(objId);
        logicNode.setType("and");
        Children children = new Children();
        children.getFilterNodeDOs().add(createFilter(objId + "-minimum", GREATER_THAN_OR_EQUALS_TO, minimum));
        children.getFilterNodeDOs().add(createFilter(objId + "-maximum", LESS_THAN_OR_EQUALS_TO, maximum));
        logicNode.setChildren(children);
        return logicNode;
    }

    private FilterNodeDO createFilter(String objId, String operator, String value) {
        return createFilter(objId, operator, value, NUMERIC_CONSTANT);
    }

    private FilterNodeDO createFilter(String objId, String operator, String value, String type) {
        FilterNodeDO filterNode = new FilterNodeDO();
        filterNode.setObjid(objId);
        filterNode.setOperator(operator);
        filterNode.setFormattedValues(value);
        filterNode.setValues(createValues(value));
        filterNode.setVarRef(createVarRef(type));
        return filterNode;
    }

    private Values createValues(String value) {
        Values values = new Values();
        values.setText(value);
        return values;
    }

    private VarRef createVarRef(String type) {
        VarRef varRef = new VarRef();
        varRef.setVarInfoId("input.DoubleInput");
        varRef.setVarName(NUMERIC_CONSTANT.equals(type) ? "DoubleInput" : "StringInput");
        varRef.setType(type);
        return varRef;
    }
}