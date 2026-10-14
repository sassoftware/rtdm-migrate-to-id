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

        assertEquals("RequestIntInput = ApplicationScore", steps.get(0).getConditionExpression());
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

        assertEquals("RequestIntInput = 1 and RequestDoubleInput = 0.1"
                + " and RequestBooleanInput = 1 and DATEPART(RequestDateInput) = to_double(date '2026-09-17')"
                + " and RequestStringInput = 'value'",
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

            // RTDM dates are ID datetimes: date parts and whole-date comparisons work on DATEPART
            assertEquals("DAY(DATEPART(RequestDateInput)) = 1 and MONTH(DATEPART(RequestDateInput)) = 2"
                    + " and YEAR(DATEPART(RequestDateInput)) = 2026 and DATEPART(RequestDateInput) = to_double(date '2026-09-17')",
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

        assertEquals("RequestDoubleInput >= RequestBooleanInput and RequestDoubleInput <= RequestIntInput",
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

        assertEquals("DATEPART(RequestDateInput) <= DATEPART(TodaysDateAndTime)", steps.get(0).getConditionExpression());
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
    private MultiSelectNodeConverter passThroughConverter() {
        CommonProcessing commonProcessing = mock(CommonProcessing.class);
        when(commonProcessing.sanitizeVariableName(anyString())).thenAnswer(invocation -> invocation.getArgument(0));
        doAnswer(invocation -> {
            List<Step> steps = invocation.getArgument(0);
            steps.add(invocation.getArgument(1));
            return null;
        }).when(commonProcessing).addStep(anyList(), any(Step.class), anyString());
        return new MultiSelectNodeConverter(commonProcessing);
    }

    private String render(RootNode rootNode) {
        MultiSelectNodeDataDO multiSelectNode = new MultiSelectNodeDataDO();
        multiSelectNode.setNodeId("multi-select");
        SQLExpression sqlExpression = new SQLExpression();
        sqlExpression.setRootNode(rootNode);
        multiSelectNode.setSqlExpression(sqlExpression);
        return passThroughConverter().createConditionByFilter(multiSelectNode, new Decision()).get(0).getConditionExpression();
    }

    private RootNode andRoot(FilterNodeDO... filters) {
        RootNode rootNode = new RootNode();
        rootNode.setType("and");
        Children children = new Children();
        children.getFilterNodeDOs().addAll(java.util.Arrays.asList(filters));
        rootNode.setChildren(children);
        return rootNode;
    }

    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource({
        "equals, =", "notEquals, ^=", "lessThan, <", "lessThanEquals, <=", "greaterThan, >",
        "greaterThanEquals, >="
    })
    void mapsComparisonOperatorsExplicitly(String operator, String expected) {
        assertEquals("DoubleInput " + expected + " 5", render(andRoot(createFilter("f", operator, "5"))));
    }

    @Test
    void mapsNegatedListLikeAndMissingOperators() {
        assertEquals("StringInput NOT IN ('a','b')",
            render(andRoot(createFilter("f", "notInList", "a,b", CHAR_CONSTANT))));
        assertEquals("(StringInput NOT LIKE 'a%' and StringInput NOT LIKE 'b%')",
            render(andRoot(createFilter("f", "notLike", "a%,b%", CHAR_CONSTANT))));
        assertEquals("DoubleInput ^= .", render(andRoot(createFilter("f", "notMissing", null))));
        assertEquals("StringInput ^= ''", render(andRoot(createFilter("f", "notMissing", null, CHAR_CONSTANT))));
    }

    @Test
    void failsOnUnknownOperatorInsteadOfUsingEquality() {
        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
            () -> render(andRoot(createFilter("f", "contains", "5"))));
        assertTrue(exception.getMessage().contains("'contains'"));
    }

    @Test
    void quotesInListValuesByVariableType() {
        assertEquals("DoubleInput IN (1,2,3)", render(andRoot(createFilter("f", "inList", "1, 2,3"))));
        assertEquals("StringInput IN ('O''Brien','x')",
            render(andRoot(createFilter("f", "inList", "O'Brien,x", CHAR_CONSTANT))));
        assertEquals("DATEPART(StringInput) IN (to_double(date '2026-01-01'))",
            render(andRoot(createFilter("f", "inList", "2026-01-01", DATE_CONSTANT))));
    }

    // The shapes below are taken from the RTDM sample extract filter_nodes.xml

    @Test
    void namesGlobalAndCalculatedFilterVariablesAsTheSignatureDeclaresThem() {
        // Shapes from customer campaigns: the first filters test a global and a calculated item. The sanitizer is
        // mocked as the identity here, so the source-qualified names keep their dot (the real one removes it).
        FilterNodeDO testMode = createFilter("f", "equals", "true");
        testMode.getVarRef().setVarInfoId("globals.TEST_MODE");
        testMode.getVarRef().setVarName("TEST_MODE");
        testMode.getVarRef().setSubType("Boolean");
        FilterNodeDO interaction = createFilter("g", "equals", "true");
        interaction.getVarRef().setVarInfoId("calculatedDataItems.checkInteractionID");
        interaction.getVarRef().setVarName("checkInteractionID");
        interaction.getVarRef().setSubType("Boolean");
        FilterNodeDO processOutput = createFilter("h", "equals", "0.0");
        processOutput.getVarRef().setVarInfoId("Valida Enriquecimento.Groovy_Validacao_v1cdRetorno");
        processOutput.getVarRef().setVarName("cdRetorno");

        assertEquals("Global.TEST_MODE = 1 and CalculatedDataItem.checkInteractionID = 1 and cdRetorno = 0.0",
            render(andRoot(testMode, interaction, processOutput)));
    }

    @Test
    void comparesBooleanFiltersAsNumbersUsingRawValues() {
        FilterNodeDO inList = createFilter("f", "inList", "true,false");
        inList.setFormattedValues("Yes,No");
        inList.getVarRef().setSubType("Boolean");
        FilterNodeDO equals = createFilter("g", "equals", "true");
        equals.setFormattedValues("Yes");
        equals.getVarRef().setSubType("Boolean");

        assertEquals("DoubleInput IN (1,0) and DoubleInput = 1", render(andRoot(inList, equals)));
    }

    @Test
    void readsListsFromRawValuesWithTheirDelimiter() {
        FilterNodeDO numbers = createFilter("f", "inList", "1000.0,2000.0");
        numbers.setFormattedValues("1,000,2,000");
        numbers.getValues().setListDelimiter(",");

        assertEquals("DoubleInput IN (1000.0,2000.0)", render(andRoot(numbers)));
    }

    @Test
    void comparesWholeDatesByCalendarDay() {
        FilterNodeDO from = createFilter("f", GREATER_THAN_OR_EQUALS_TO, "9/7/26, 7:25 AM", DATE_CONSTANT);
        from.setFormattedValues("Sep 7, 2026");
        from.getValues().setListDelimiter(",!");
        from.setDateType("whole");
        FilterNodeDO list = createFilter("g", "inList", "1/2/70, 7:25 AM,!9/11/26, 7:26 AM", DATE_CONSTANT);
        list.setFormattedValues("Jan 2, 1970, Sep 11, 2026");
        list.getValues().setListDelimiter(",!");
        list.setDateType("whole");

        assertEquals("DATEPART(StringInput) >= to_double(date '2026-09-07')"
            + " and DATEPART(StringInput) IN (to_double(date '1970-01-02'),to_double(date '2026-09-11'))",
            render(andRoot(from, list)));
    }

    @Test
    void comparesWholeDatesWithTodaysDateByCalendarDay() {
        FilterNodeDO today = createFilter("f", LESS_THAN_OR_EQUALS_TO, null, DATE_CONSTANT);
        today.setFormattedValues("Today's Date and Time");
        today.setValues(createValues(null));
        today.setDateType("whole");

        assertTrue(render(andRoot(today)).matches("DATEPART\\(StringInput\\) <= DATEPART\\([^)]+\\)"), render(andRoot(today)));
    }

    @Test
    void appliesTheDateTypeOfASingleFilterRoot() {
        RootNode root = new RootNode();
        root.setOperator("equals");
        root.setValues(createValues("3.0"));
        root.setFormattedValues("3");
        root.setVarRef(createVarRef(DATE_CONSTANT));
        root.setDateType("month");

        assertEquals("MONTH(DATEPART(StringInput)) = 3", render(root));
    }

    @Test
    void failsOnUnreadableWholeDate() {
        FilterNodeDO filter = createFilter("f", "equals", "not a date", DATE_CONSTANT);
        filter.setDateType("whole");

        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
            () -> render(andRoot(filter)));
        assertTrue(exception.getMessage().contains("not a date"), exception.getMessage());
    }

    @Test
    void escapesSingleQuotesInStringLiterals() {
        assertEquals("StringInput = 'O''Brien'", render(andRoot(createFilter("f", "equals", "O'Brien", CHAR_CONSTANT))));
        assertEquals("StringInput LIKE 'O''B%'", render(andRoot(createFilter("f", LIKE, "O'B%", CHAR_CONSTANT))));
    }

    @Test
    void usesNumericMissingValueForDates() {
        assertEquals("StringInput = .", render(andRoot(createFilter("f", IS_MISSING, null, DATE_CONSTANT))));
        assertEquals("StringInput = ''", render(andRoot(createFilter("f", IS_MISSING, null, "string"))));
    }

    @Test
    void handlesLeafRootWithoutChildren() {
        RootNode rootNode = new RootNode();
        rootNode.setOperator("greaterThan");
        rootNode.setValues(createValues("3"));
        rootNode.setFormattedValues("3");
        rootNode.setVarRef(createVarRef(NUMERIC_CONSTANT));
        MultiSelectNodeDataDO multiSelectNode = new MultiSelectNodeDataDO();
        SQLExpression sqlExpression = new SQLExpression();
        sqlExpression.setRootNode(rootNode);
        multiSelectNode.setSqlExpression(sqlExpression);

        assertEquals(1, MultiSelectNodeConverter.getFilterNodesFromMultiSelectNode(multiSelectNode).size());
        assertEquals("DoubleInput > 3", render(rootNode));
    }

    @Test
    void handlesLogicNodeWithoutChildren() {
        RootNode rootNode = andRoot(createFilter("f", "equals", "1"));
        LogicNodeDO emptyLogicNode = new LogicNodeDO();
        emptyLogicNode.setType("or");
        rootNode.getChildren().getLogicNodeDOs().add(emptyLogicNode);
        MultiSelectNodeDataDO multiSelectNode = new MultiSelectNodeDataDO();
        SQLExpression sqlExpression = new SQLExpression();
        sqlExpression.setRootNode(rootNode);
        multiSelectNode.setSqlExpression(sqlExpression);

        assertEquals(1, MultiSelectNodeConverter.getFilterNodesFromMultiSelectNode(multiSelectNode).size());
        assertEquals("DoubleInput = 1", render(rootNode));
    }
}
