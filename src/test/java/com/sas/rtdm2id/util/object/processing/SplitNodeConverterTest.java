package com.sas.rtdm2id.util.object.processing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.ConditionBranch;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.SignatureTerm;
import com.sas.rtdm2id.model.id.decision.SimpleBranchCondition;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.model.rtdm.SplitNodeDataDO;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import jakarta.xml.bind.JAXBContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class SplitNodeConverterTest {

    @ParameterizedTest
    @CsvSource(value = {
        "Numeric, Boolean, true, false, 1, 0, DECIMAL",
        "boolean, Boolean, true, false, True, False, BOOLEAN",
        "boolean, Boolean, 1, 0, True, False, BOOLEAN",
        "string, String, true, false, 'true', 'false', STRING",
        "Numeric, Double, 1.5, 0.5, 1.5, 0.5, DECIMAL",
        "Char, String, (Missing), OPEN, '', 'OPEN', STRING",
        "string, String, OPEN, (Missing), 'OPEN', '', STRING",
        "integer, Long, (Missing), 2.0, 1, 2, INTEGER",
        "Numeric, Double, (Missing), 1.5, 1, 1.5, DECIMAL",
        "double, Double, 1.5, (Missing), 1.5, 1, DECIMAL",
        "boolean, Boolean, (Missing), true, 1, True, BOOLEAN",
        "date, String, (Missing), (Missing), 1, 1, DATETIME"
    }, quoteCharacter = '"')
    void convertsDiscreteConstantsUsingTheirStoredType(String type, String storedType,
            String firstValue, String secondValue, String firstExpected, String secondExpected,
            SignatureTerm.DataTypeEnum expectedType) throws Exception {
        String xml = "<SplitNodeDataDO>"
            + "<NodeId>split-node</NodeId><NodeName>checkAppIdMissing</NodeName>"
            + "<SplitType>dataItem</SplitType><LineItems>"
            + "<SplitNodeLineItemDO><CellId>has-app-id</CellId><Value>Yes</Value></SplitNodeLineItemDO>"
            + "<SplitNodeLineItemDO><CellId>no-app-id</CellId><Value>No</Value></SplitNodeLineItemDO>"
            + "<SplitNodeLineItemDO><CellId>other</CellId><Value>(Remainder)</Value></SplitNodeLineItemDO>"
            + "</LineItems><SplitOnNodeDataDO>"
            + "<VarRefDO><Type>" + type + "</Type><SubType>Boolean</SubType>"
            + "<VarInfoId>events.Tablerowcount</VarInfoId><VarName>Tablerowcount</VarName></VarRefDO>"
            + "<LineItems><DiscreteLineItemDO>"
            + discreteValueXml(storedType, firstValue) + "</DiscreteLineItemDO>"
            + "<DiscreteLineItemDO>"
            + discreteValueXml(storedType, secondValue) + "</DiscreteLineItemDO>"
            + "</LineItems></SplitOnNodeDataDO></SplitNodeDataDO>";
        SplitNodeDataDO source = JAXBContext.newInstance(SplitNodeDataDO.class).createUnmarshaller()
            .unmarshal(new StreamSource(new StringReader(xml)), SplitNodeDataDO.class).getValue();
        MapStorage storage = new MapStorage();
        CommonProcessing processing = spy(new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
        doReturn(new ConditionBranch()).when(processing).createConditionBranch("other", true);
        Decision decision = new Decision();

        List<Step> steps = new SplitNodeConverter(processing, storage)
            .createBranch(source, decision, "checkAppIdMissing");

        assertEquals(1, steps.size());
        Step branch = steps.get(0);
        assertEquals(Step.TypeEnum.BRANCH, branch.getType());
        assertEquals(2, branch.getBranchCases().size());
        assertEquals(firstExpected, branch.getBranchCases().get(0).getSimpleCondition().getRhsConstant());
        assertEquals(secondExpected, branch.getBranchCases().get(1).getSimpleCondition().getRhsConstant());
        assertEquals("has-app-id", branch.getBranchCases().get(0).getLabel());
        assertEquals("no-app-id", branch.getBranchCases().get(1).getLabel());
        assertEquals("=", branch.getBranchCases().get(0).getSimpleCondition().getOperator());
        assertEquals(expectedType, decision.getSignature().get(0).getDataType());
        for (int caseIndex = 0; caseIndex < 2; caseIndex++) {
            SimpleBranchCondition condition = branch.getBranchCases().get(caseIndex).getSimpleCondition();
            String sourceValue = caseIndex == 0 ? firstValue : secondValue;
            if ("(Missing)".equals(sourceValue) && expectedType != SignatureTerm.DataTypeEnum.STRING) {
                assertNumericMissingCondition(condition, decision.getSignature().get(0).getName());
            } else {
                assertEquals(decision.getSignature().get(0).getName(), condition.getLhsTerm().getName());
                assertNull(condition.getLhsConditionExpression());
            }
        }
        assertNotNull(branch.getDefaultCase());
    }

    @ParameterizedTest
    @CsvSource(value = {
        "Numeric, 1, true, false",
        "double, 1, true, false",
        "integer, 1, true, false",
        "date, 1, true, false",
        "Char, '', true, false",
        "string, '', true, false",
        "Numeric, 1, true, true",
        "Numeric, 1, false, true"
    }, quoteCharacter = '"')
    void convertsMissingIntervalsToEqualityWithoutUsingUnsetBounds(String type, String expectedMissing,
            boolean missingFirst, boolean includeRange) throws Exception {
        String missingInterval = "<IntervalLineItemDO><MissingGroup>true</MissingGroup>"
            + "<LowerBound StoredType=\"String\">unset</LowerBound><LowerBoundInclusive>false</LowerBoundInclusive>"
            + "<UpperBound StoredType=\"String\">unset</UpperBound><UpperBoundInclusive>false</UpperBoundInclusive>"
            + "</IntervalLineItemDO>";
        String rangeInterval = "<IntervalLineItemDO><MissingGroup>false</MissingGroup>"
            + "<LowerBound StoredType=\"Double\">0</LowerBound><LowerBoundInclusive>true</LowerBoundInclusive>"
            + "<UpperBound StoredType=\"Double\">10</UpperBound><UpperBoundInclusive>true</UpperBoundInclusive>"
            + "</IntervalLineItemDO>";
        String missingCell = "<SplitNodeLineItemDO><CellId>missing</CellId><Value>(Missing)</Value></SplitNodeLineItemDO>";
        String rangeCell = "<SplitNodeLineItemDO><CellId>range</CellId><Value>0-10</Value></SplitNodeLineItemDO>";
        String xml = "<SplitNodeDataDO><NodeId>missing-split</NodeId><NodeName>Partner check</NodeName>"
            + "<SplitType>dataItem</SplitType><LineItems>"
            + (includeRange ? (missingFirst ? missingCell + rangeCell : rangeCell + missingCell) : missingCell)
            + "<SplitNodeLineItemDO><CellId>other</CellId><Value>(Remainder)</Value></SplitNodeLineItemDO>"
            + "</LineItems><SplitOnNodeDataDO><VarRefDO><Type>" + type + "</Type>"
            + "<VarInfoId>events.PartnerId</VarInfoId><VarName>PartnerId</VarName></VarRefDO><LineItems>"
            + (includeRange ? (missingFirst ? missingInterval + rangeInterval : rangeInterval + missingInterval) : missingInterval)
            + "</LineItems></SplitOnNodeDataDO></SplitNodeDataDO>";
        SplitNodeDataDO source = JAXBContext.newInstance(SplitNodeDataDO.class).createUnmarshaller()
            .unmarshal(new StreamSource(new StringReader(xml)), SplitNodeDataDO.class).getValue();
        MapStorage storage = new MapStorage();
        CommonProcessing processing = spy(new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
        doReturn(new ConditionBranch()).when(processing).createConditionBranch("other", true);
        Decision decision = new Decision();

        Step branch = new SplitNodeConverter(processing, storage).createBranch(source, decision, "PartnerCheck").get(0);

        int missingIndex = includeRange && !missingFirst ? 1 : 0;
        com.sas.rtdm2id.model.id.decision.BranchCase missingCase = branch.getBranchCases().get(missingIndex);
        assertEquals("missing", missingCase.getLabel());
        assertNull(missingCase.getCompoundCondition());
        assertEquals("=", missingCase.getSimpleCondition().getOperator());
        assertEquals(expectedMissing, missingCase.getSimpleCondition().getRhsConstant());
        if (decision.getSignature().get(0).getDataType() == SignatureTerm.DataTypeEnum.STRING) {
            assertEquals(decision.getSignature().get(0).getName(), missingCase.getSimpleCondition().getLhsTerm().getName());
            assertNull(missingCase.getSimpleCondition().getLhsConditionExpression());
        } else {
            assertNumericMissingCondition(missingCase.getSimpleCondition(), decision.getSignature().get(0).getName());
        }
        assertEquals(includeRange ? "range" : "equals", branch.getBranchType());
        assertEquals("false", source.getSplitOnNodeDataDO().getLineItems().getIntervalLineItemDOs().get(missingIndex).getLowerBoundInclusive());
        assertEquals("false", source.getSplitOnNodeDataDO().getLineItems().getIntervalLineItemDOs().get(missingIndex).getUpperBoundInclusive());
        assertNotNull(branch.getDefaultCase());
        if (includeRange) {
            com.sas.rtdm2id.model.id.decision.CompoundBranchCondition range =
                branch.getBranchCases().get(1 - missingIndex).getCompoundCondition();
            assertEquals(">=", range.getLhsSimpleCondition().getOperator());
            assertEquals("0", range.getLhsSimpleCondition().getRhsConstant());
            assertEquals("<=", range.getRhsSimpleCondition().getOperator());
            assertEquals("10", range.getRhsSimpleCondition().getRhsConstant());
        }
    }

    @ParameterizedTest
    @CsvSource({"0.0, 0.0", "0, 0.0", "1, 1.00"})
    void convertsInclusiveSingleValueIntervalsToEquality(String lower, String upper) throws Exception {
        String xml = "<SplitNodeDataDO><NodeId>status-split</NodeId><NodeName>ApplicationDataError</NodeName>"
            + "<SplitType>dataItem</SplitType><LineItems>"
            + "<SplitNodeLineItemDO><CellId>success</CellId><Value>OK</Value></SplitNodeLineItemDO>"
            + "<SplitNodeLineItemDO><CellId>error</CellId><Value>(Remainder)</Value></SplitNodeLineItemDO>"
            + "</LineItems><SplitOnNodeDataDO><VarRefDO><Type>Numeric</Type>"
            + "<VarInfoId>events.statusCd</VarInfoId><VarName>statusCd</VarName></VarRefDO><LineItems>"
            + "<IntervalLineItemDO><MissingGroup>false</MissingGroup>"
            + "<LowerBound StoredType=\"Double\">" + lower + "</LowerBound><LowerBoundInclusive>true</LowerBoundInclusive>"
            + "<UpperBound StoredType=\"Double\">" + upper + "</UpperBound><UpperBoundInclusive>true</UpperBoundInclusive>"
            + "</IntervalLineItemDO></LineItems></SplitOnNodeDataDO></SplitNodeDataDO>";
        SplitNodeDataDO source = JAXBContext.newInstance(SplitNodeDataDO.class).createUnmarshaller()
            .unmarshal(new StreamSource(new StringReader(xml)), SplitNodeDataDO.class).getValue();
        MapStorage storage = new MapStorage();
        CommonProcessing processing = spy(new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
        doReturn(new ConditionBranch()).when(processing).createConditionBranch("error", true);
        Decision decision = new Decision();

        Step branch = new SplitNodeConverter(processing, storage).createBranch(source, decision, source.getNodeName()).get(0);

        assertEquals("equals", branch.getBranchType());
        assertEquals("success", branch.getBranchCases().get(0).getLabel());
        assertNull(branch.getBranchCases().get(0).getCompoundCondition());
        SimpleBranchCondition condition = branch.getBranchCases().get(0).getSimpleCondition();
        assertEquals("=", condition.getOperator());
        assertEquals(new BigDecimal(lower), new BigDecimal(condition.getRhsConstant()));
        assertEquals(decision.getSignature().get(0).getName(), condition.getLhsTerm().getName());
        assertNotNull(branch.getDefaultCase());
    }

    private String discreteValueXml(String storedType, String value) {
        if ("(Missing)".equals(value)) {
            return "<FormattedValue>(Missing)</FormattedValue>";
        }
        return "<Value StoredType=\"" + storedType + "\">" + value + "</Value>";
    }

    @Test
    void convertsMissingIntervalGroupsFromExtract() throws Exception {
        String extractPath = System.getProperty("splitExtract");
        assumeTrue(extractPath != null, "Set -DsplitExtract to an RTDM extract containing missing interval groups");
        Batch batch = (Batch) JAXBContext.newInstance(Batch.class).createUnmarshaller().unmarshal(new File(extractPath));
        TreeUtil treeUtil = new TreeUtil();
        treeUtil.createTree(batch.getLogicalUnit());
        MapStorage storage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing processing = new CommonProcessing(restTemplate, storage, treeUtil);
        SplitNodeConverter converter = new SplitNodeConverter(processing, storage);
        int missingGroups = 0;
        for (SplitNodeDataDO source : batch.getLogicalUnit().getSplitNodeDataDOs()) {
            if (source.getSplitOnNodeDataDO() == null || source.getSplitOnNodeDataDO().getLineItems() == null
                    || source.getSplitOnNodeDataDO().getLineItems().getIntervalLineItemDOs().stream()
                        .noneMatch(interval -> Boolean.parseBoolean(interval.getMissingGroup()))) {
                continue;
            }
            List<Step> steps = converter.createBranch(source, new Decision(), source.getNodeName());
            Step branch = steps.get(steps.size() - 1);
            int caseIndex = 0;
            for (int sourceIndex = 0; sourceIndex < source.getLineItems().getSplitNodeLineItemDOs().size(); sourceIndex++) {
                SplitNodeDataDO.LineItems.SplitNodeLineItemDO line = source.getLineItems().getSplitNodeLineItemDOs().get(sourceIndex);
                if ("(Remainder)".equals(line.getValue())) {
                    continue;
                }
                com.sas.rtdm2id.model.id.decision.BranchCase branchCase = branch.getBranchCases().get(caseIndex++);
                if (Boolean.parseBoolean(source.getSplitOnNodeDataDO().getLineItems().getIntervalLineItemDOs()
                        .get(sourceIndex).getMissingGroup())) {
                    missingGroups++;
                    assertEquals(line.getCellId(), branchCase.getLabel());
                    assertNull(branchCase.getCompoundCondition(), source.getNodeName());
                    assertEquals("=", branchCase.getSimpleCondition().getOperator(), source.getNodeName());
                    if (SignatureTerm.DataTypeEnum.STRING.equals(
                            processing.getDatatypeOfVar(source.getSplitOnNodeDataDO().getVarRefDO().getType()))) {
                        assertEquals("''", branchCase.getSimpleCondition().getRhsConstant(), source.getNodeName());
                    } else {
                        assertNumericMissingCondition(branchCase.getSimpleCondition(), processing.sanitizeVariableName(
                            processing.buildVariableName(source.getSplitOnNodeDataDO().getVarRefDO())));
                    }
                }
            }
            assertNotNull(branch.getDefaultCase(), source.getNodeName());
        }
        assertTrue(missingGroups > 0, "Expected at least one missing interval group in the extract");
        org.mockito.Mockito.verifyNoInteractions(restTemplate);
    }

    @Test
    void convertsReportedHabofBranchesFromExtract() throws Exception {
        String extractPath = System.getProperty("calculatedExtract");
        assumeTrue(extractPath != null, "Set -DcalculatedExtract to the HABOF extract");
        Batch batch = (Batch) JAXBContext.newInstance(Batch.class).createUnmarshaller().unmarshal(new File(extractPath));
        TreeUtil treeUtil = new TreeUtil();
        treeUtil.createTree(batch.getLogicalUnit());
        MapStorage storage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing processing = new CommonProcessing(restTemplate, storage, treeUtil);
        batch.getLogicalUnit().getFlowDO().getCalculatedDataItemList().getCalculatedItemDOs().forEach(item -> storage.getCalculatedVariableDataTypeMap().put(
            "calculatedDataItems." + item.getName(), processing.getCalculatedItemDataType(item.getCalculatedItemType())));
        SplitNodeConverter converter = new SplitNodeConverter(processing, storage);
        Set<String> remaining = new HashSet<>(Set.of("ApplicationDataError", "IB_SZLAZAR_IG_ELL", "Branch(20)", "IsEmployerNotNull"));
        for (SplitNodeDataDO source : batch.getLogicalUnit().getSplitNodeDataDOs()) {
            if (!remaining.remove(source.getNodeName())) {
                continue;
            }
            List<Step> steps = converter.createBranch(source, new Decision(), source.getNodeName());
            Step branch = steps.get(steps.size() - 1);
            assertEquals("equals", branch.getBranchType(), source.getNodeName());
            String variableName = processing.sanitizeVariableName(processing.buildVariableName(source.getSplitOnNodeDataDO().getVarRefDO()));
            for (com.sas.rtdm2id.model.id.decision.BranchCase branchCase : branch.getBranchCases()) {
                assertNull(branchCase.getCompoundCondition(), source.getNodeName());
                assertEquals("=", branchCase.getSimpleCondition().getOperator(), source.getNodeName());
                assertEquals(variableName, branchCase.getSimpleCondition().getLhsTerm().getName(), source.getNodeName());
            }
            if ("ApplicationDataError".equals(source.getNodeName())) {
                assertEquals(1, branch.getBranchCases().size());
                assertEquals(0, BigDecimal.ZERO.compareTo(new BigDecimal(branch.getBranchCases().get(0).getSimpleCondition().getRhsConstant())));
                assertNotNull(branch.getDefaultCase());
            } else {
                assertEquals("Numeric", source.getSplitOnNodeDataDO().getVarRefDO().getType());
                assertEquals("boolean", storage.getCalculatedVariableDataTypeMap().get(source.getSplitOnNodeDataDO().getVarRefDO().getVarInfoId()));
                assertEquals(2, branch.getBranchCases().size());
                JsonNode json = new ObjectMapper().valueToTree(branch);
                assertEquals("True", json.get("branchCases").get(0).get("simpleCondition").get("rhsConstant").asText(), source.getNodeName());
                assertEquals("False", json.get("branchCases").get(1).get("simpleCondition").get("rhsConstant").asText(), source.getNodeName());
            }
        }
        assertTrue(remaining.isEmpty(), "Missing reported branches: " + remaining);
        org.mockito.Mockito.verifyNoInteractions(restTemplate);
    }

    private void assertNumericMissingCondition(SimpleBranchCondition condition, String variableName) throws Exception {
        assertNull(condition.getLhsTerm());
        assertNull(condition.getRhsTerm());
        assertEquals("missing(" + variableName + ")", condition.getLhsConditionExpression());
        assertEquals("decimal", condition.getLhsConditionExpressionType());
        assertEquals("=", condition.getOperator());
        assertEquals(BigDecimal.ONE, new BigDecimal(condition.getRhsConstant()));
        JsonNode json = new ObjectMapper().valueToTree(condition);
        assertTrue(json.get("lhsTerm").isNull());
        assertEquals(condition.getLhsConditionExpression(), json.get("lhsConditionExpression").asText());
        assertEquals("decimal", json.get("lhsConditionExpressionType").asText());
        assertEquals("1", json.get("rhsConstant").asText());
        assertEquals(condition, new ObjectMapper().treeToValue(json, SimpleBranchCondition.class));
    }
    private static List<int[]> ranges(String... percentages) {
        List<BigDecimal> values = new java.util.ArrayList<>();
        for (String percentage : percentages) {
            values.add(new BigDecimal(percentage));
        }
        return SplitNodeConverter.percentRanges(values);
    }

    private static void assertRanges(List<int[]> actual, int... bounds) {
        assertEquals(bounds.length / 2, actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals(bounds[2 * i], actual.get(i)[0], "lower bound of branch " + i);
            assertEquals(bounds[2 * i + 1], actual.get(i)[1], "upper bound of branch " + i);
        }
    }

    @Test
    void percentRangesCoverTheWholeZeroToNinetyNineDomain() {
        assertRanges(ranges("50", "50"), 0, 49, 50, 99);
        assertRanges(ranges("100"), 0, 99);
        assertRanges(ranges("12.5", "87.5"), 0, 12, 13, 99);
        assertRanges(ranges("33.3", "33.3", "33.4"), 0, 32, 33, 66, 67, 99);
        assertRanges(ranges("33.3", "33.3", "33.3"), 0, 32, 33, 66, 67, 99);
        assertRanges(ranges("10", "20", "30", "40"), 0, 9, 10, 29, 30, 59, 60, 99);
        // A branch that rounds to nothing gets an empty range; the remaining branches still cover 0..99
        assertRanges(ranges("0.2", "99.8"), 0, -1, 0, 99);
    }

    private SplitNodeDataDO percentSplit(String enableSeed, String seedXml, String... percentages) throws Exception {
        StringBuilder lineItems = new StringBuilder();
        for (int i = 0; i < percentages.length; i++) {
            lineItems.append("<SplitNodeLineItemDO><CellId>cell-").append(i).append("</CellId>")
                .append("<RequiredPercent StoredType=\"Double\">").append(percentages[i]).append("</RequiredPercent>")
                .append("</SplitNodeLineItemDO>");
        }
        String xml = "<SplitNodeDataDO><NodeId>pct</NodeId><NodeName>Percent split</NodeName>"
            + "<EnableSeed>" + enableSeed + "</EnableSeed>" + seedXml
            + "<SplitType>percents</SplitType><LineItems>" + lineItems + "</LineItems></SplitNodeDataDO>";
        return JAXBContext.newInstance(SplitNodeDataDO.class).createUnmarshaller()
            .unmarshal(new StreamSource(new StringReader(xml)), SplitNodeDataDO.class).getValue();
    }

    private Step randomNumberStep(MapStorage storage, String name, boolean existing) {
        Step step = new Step();
        com.sas.rtdm2id.model.id.decision.RuleSetStep ruleSet = new com.sas.rtdm2id.model.id.decision.RuleSetStep();
        ruleSet.setName(name);
        ruleSet.setId("rs-id");
        step.setRuleSet(ruleSet);
        storage.getRuleSetStepMap().put(name, step);
        storage.getRuleSetModifiedDate().put(name, new java.util.Date());
        storage.getRuleSetIsUpdated().put(name, existing);
        storage.getRuleSetIsActionUpdated().put(name, existing);
        storage.setBaseIp("viya.example");
        return step;
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void percentSplitBranchesOnZeroBasedRangesAndWritesSignatureBeforeRule() throws Exception {
        SplitNodeDataDO source = percentSplit("true",
            "<SeedVarRefDO><Type>Numeric</Type><VarInfoId>events.Customer Id</VarInfoId><VarName>Customer Id</VarName></SeedVarRefDO>",
            "12.5", "87.5");
        MapStorage storage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing processing = new CommonProcessing(restTemplate, storage, new TreeUtil());
        Step randomNumber = randomNumberStep(storage, "pctnode_random-number", false);

        List<Step> steps = new SplitNodeConverter(processing, storage).createBranch(source, new Decision(), "pctnode");

        assertEquals(2, steps.size());
        assertTrue(steps.get(0) == randomNumber);
        Step branch = steps.get(1);
        assertEquals("0", branch.getBranchCases().get(0).getCompoundCondition().getLhsSimpleCondition().getRhsConstant());
        assertEquals("12", branch.getBranchCases().get(0).getCompoundCondition().getRhsSimpleCondition().getRhsConstant());
        assertEquals("13", branch.getBranchCases().get(1).getCompoundCondition().getLhsSimpleCondition().getRhsConstant());
        assertEquals("99", branch.getBranchCases().get(1).getCompoundCondition().getRhsSimpleCondition().getRhsConstant());
        String seed = processing.sanitizeAndReduceVariableName("Customer Id");
        assertEquals(seed, randomNumber.getMappings().get(0).getStepTermName());

        org.mockito.ArgumentCaptor<org.springframework.http.HttpEntity> rule =
            org.mockito.ArgumentCaptor.forClass(org.springframework.http.HttpEntity.class);
        org.mockito.InOrder order = org.mockito.Mockito.inOrder(restTemplate);
        order.verify(restTemplate).exchange(org.mockito.ArgumentMatchers.any(java.net.URI.class),
            org.mockito.ArgumentMatchers.eq(org.springframework.http.HttpMethod.PUT),
            org.mockito.ArgumentMatchers.any(org.springframework.http.HttpEntity.class),
            org.mockito.ArgumentMatchers.eq(com.sas.rtdm2id.model.id.rules.RuleSet.class));
        order.verify(restTemplate).exchange(org.mockito.ArgumentMatchers.any(java.net.URI.class),
            org.mockito.ArgumentMatchers.eq(org.springframework.http.HttpMethod.POST), rule.capture(),
            org.mockito.ArgumentMatchers.eq(com.sas.rtdm2id.model.id.rules.Rule.class));
        String expression = ((com.sas.rtdm2id.model.id.rules.Rule) rule.getValue().getBody()).getActions().get(0).getExpression();
        assertEquals("random_number = FLOOR(MODZ(ABS(" + seed + ") * 811, 100))", expression);
    }

    @Test
    void percentSplitWithoutSeedUsesSameIntegerDomainAndReusesExistingRuleSet() throws Exception {
        SplitNodeDataDO source = percentSplit("false", "", "50", "50");
        MapStorage storage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing processing = new CommonProcessing(restTemplate, storage, new TreeUtil());
        randomNumberStep(storage, "pctnode_random-number", true);

        List<Step> steps = new SplitNodeConverter(processing, storage).createBranch(source, new Decision(), "pctnode");

        assertEquals("49", steps.get(1).getBranchCases().get(0).getCompoundCondition().getRhsSimpleCondition().getRhsConstant());
        org.mockito.Mockito.verifyNoInteractions(restTemplate);
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void percentSplitWithoutSeedDrawsAnIntegerBetweenZeroAndNinetyNine() throws Exception {
        SplitNodeDataDO source = percentSplit("false", "", "100");
        MapStorage storage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        randomNumberStep(storage, "pctnode_random-number", false);

        new SplitNodeConverter(new CommonProcessing(restTemplate, storage, new TreeUtil()), storage)
            .createBranch(source, new Decision(), "pctnode");

        org.mockito.ArgumentCaptor<org.springframework.http.HttpEntity> rule =
            org.mockito.ArgumentCaptor.forClass(org.springframework.http.HttpEntity.class);
        org.mockito.Mockito.verify(restTemplate).exchange(org.mockito.ArgumentMatchers.any(java.net.URI.class),
            org.mockito.ArgumentMatchers.eq(org.springframework.http.HttpMethod.POST), rule.capture(),
            org.mockito.ArgumentMatchers.eq(com.sas.rtdm2id.model.id.rules.Rule.class));
        assertEquals("random_number = FLOOR(RAND('uniform') * 100)",
            ((com.sas.rtdm2id.model.id.rules.Rule) rule.getValue().getBody()).getActions().get(0).getExpression());
    }

    @Test
    void percentSplitStepsAreCrossBranchLinked() throws Exception {
        SplitNodeDataDO source = percentSplit("false", "", "100");
        MapStorage storage = new MapStorage();
        storage.setUseCrossBranchLinks(true);
        TreeUtil treeUtil = new TreeUtil();
        treeUtil.getNodeIdInputNodesMap().put("pct", "a, b");
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, treeUtil);
        Step randomNumber = randomNumberStep(storage, "pctnode_random-number", true);

        new SplitNodeConverter(processing, storage).createBranch(source, new Decision(), "pctnode");

        assertNotNull(randomNumber.getLinkLabel());
        assertEquals(randomNumber.getLinkLabel(), storage.getNodeIdLinkLabelMap().get("pct"));
    }

    private Step intervalSplit(String type, String intervals, String cells) throws Exception {
        String xml = "<SplitNodeDataDO><NodeId>interval-split</NodeId><NodeName>Bands</NodeName>"
            + "<SplitType>dataItem</SplitType><LineItems>" + cells
            + "<SplitNodeLineItemDO><CellId>other</CellId><Value>(Remainder)</Value></SplitNodeLineItemDO>"
            + "</LineItems><SplitOnNodeDataDO><VarRefDO><Type>" + type + "</Type>"
            + "<VarInfoId>events.Score</VarInfoId><VarName>Score</VarName></VarRefDO><LineItems>" + intervals
            + "</LineItems></SplitOnNodeDataDO></SplitNodeDataDO>";
        SplitNodeDataDO source = JAXBContext.newInstance(SplitNodeDataDO.class).createUnmarshaller()
            .unmarshal(new StreamSource(new StringReader(xml)), SplitNodeDataDO.class).getValue();
        MapStorage storage = new MapStorage();
        CommonProcessing processing = spy(new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
        doReturn(new ConditionBranch()).when(processing).createConditionBranch("other", true);
        return new SplitNodeConverter(processing, storage).createBranch(source, new Decision(), "Bands").get(0);
    }

    private static String interval(String lower, String lowerInclusive, String upper, String upperInclusive) {
        return "<IntervalLineItemDO><MissingGroup>false</MissingGroup>"
            + (lower == null ? "" : "<LowerBound StoredType=\"Double\">" + lower + "</LowerBound>")
            + "<LowerBoundInclusive>" + lowerInclusive + "</LowerBoundInclusive>"
            + (upper == null ? "" : "<UpperBound StoredType=\"Double\">" + upper + "</UpperBound>")
            + "<UpperBoundInclusive>" + upperInclusive + "</UpperBoundInclusive></IntervalLineItemDO>";
    }

    private static String cell(String id) {
        return "<SplitNodeLineItemDO><CellId>" + id + "</CellId><Value>" + id + "</Value></SplitNodeLineItemDO>";
    }

    @Test
    void preservesRtdmBoundInclusivityForEveryInterval() throws Exception {
        Step branch = intervalSplit("Numeric",
            interval("0", "true", "10", "false") + interval("10", "true", "20", "false") + interval("20", "false", "30", "true"),
            cell("low") + cell("mid") + cell("high"));

        assertEquals("range", branch.getBranchType());
        for (int i = 0; i < 2; i++) {
            com.sas.rtdm2id.model.id.decision.CompoundBranchCondition condition = branch.getBranchCases().get(i).getCompoundCondition();
            assertEquals(">=", condition.getLhsSimpleCondition().getOperator());
            assertEquals("<", condition.getRhsSimpleCondition().getOperator());
        }
        com.sas.rtdm2id.model.id.decision.CompoundBranchCondition last = branch.getBranchCases().get(2).getCompoundCondition();
        assertEquals(">", last.getLhsSimpleCondition().getOperator());
        assertEquals("<=", last.getRhsSimpleCondition().getOperator());
    }

    @Test
    void convertsOpenEndedIntervalsToOneSidedConditions() throws Exception {
        Step branch = intervalSplit("Numeric",
            interval(null, "false", "10", "false") + interval("10", "true", null, "false"),
            cell("below") + cell("above"));

        assertNull(branch.getBranchCases().get(0).getCompoundCondition());
        assertEquals("<", branch.getBranchCases().get(0).getSimpleCondition().getOperator());
        assertEquals("10", branch.getBranchCases().get(0).getSimpleCondition().getRhsConstant());
        assertEquals(">=", branch.getBranchCases().get(1).getSimpleCondition().getOperator());
        assertEquals("10", branch.getBranchCases().get(1).getSimpleCondition().getRhsConstant());
    }

    @ParameterizedTest
    @CsvSource(value = {
        "date| 2023-01-15T00:00:00| '15JAN2023:00:00:00'dt",
        "Date| 2023-1-5| '05JAN2023:00:00:00'dt",
        // RTDM's own format, with the narrow no-break space recent JDKs put before AM/PM
        "Date| 9/7/26, 7:25 AM| '07SEP2026:07:25:00'dt",
        "Date| 1/2/70, 1:05 PM| '02JAN1970:13:05:00'dt",
        "STRING| O'Brien| 'O''Brien'",
        "CHAR| x| 'x'"
    }, delimiter = '|', quoteCharacter = '"')
    void quotesDiscreteValuesByCaseInsensitiveType(String type, String value, String expected) throws Exception {
        String xml = "<SplitNodeDataDO><NodeId>typed</NodeId><NodeName>Typed</NodeName>"
            + "<SplitType>dataItem</SplitType><LineItems>" + cell("value")
            + "</LineItems><SplitOnNodeDataDO><VarRefDO><Type>" + type + "</Type>"
            + "<VarInfoId>events.Item</VarInfoId><VarName>Item</VarName></VarRefDO><LineItems>"
            + "<DiscreteLineItemDO><Value StoredType=\"String\">" + value + "</Value></DiscreteLineItemDO>"
            + "</LineItems></SplitOnNodeDataDO></SplitNodeDataDO>";
        SplitNodeDataDO source = JAXBContext.newInstance(SplitNodeDataDO.class).createUnmarshaller()
            .unmarshal(new StreamSource(new StringReader(xml)), SplitNodeDataDO.class).getValue();
        MapStorage storage = new MapStorage();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil());

        Step branch = new SplitNodeConverter(processing, storage).createBranch(source, new Decision(), "Typed").get(0);

        assertEquals(expected, branch.getBranchCases().get(0).getSimpleCondition().getRhsConstant());
    }

    // Bound and value shapes from the RTDM sample extracts RTDMBranchByDataItemIntervalAdvanced.xml and
    // RTDMBranchByDataItemDiscreteAdvanced.xml: a VarInfoBriefDO instead of a literal
    private static String reference(String element, String id, String name, String varType) {
        return "<" + element + " ConcreteClass=\"com.sas.analytics.crm.custdata.client.VarInfoBriefDO\" objid=\"489\">"
            + "<Aggregate>false</Aggregate><Id>" + id + "</Id><Level>Interval</Level><Name>" + name + "</Name>"
            + "<NameWithSourceContext>Request." + name + "</NameWithSourceContext><Source>Request</Source>"
            + "<VarType>" + varType + "</VarType></" + element + ">";
    }

    @Test
    void namesRequestSplitVariableAsTheStartNodeDeclaresIt() throws Exception {
        // events.Score is declared by the Start node as RequestScore; the split must test that term regardless of
        // whether another node happened to resolve the same variable earlier
        Step branch = intervalSplit("Numeric", interval("0", "true", "10", "true"), cell("low"));

        assertEquals("RequestScore",
            branch.getBranchCases().get(0).getCompoundCondition().getLhsSimpleCondition().getLhsTerm().getName());
    }

    @Test
    void comparesIntervalBoundsThatReferenceVariables() throws Exception {
        String bounds = "<IntervalLineItemDO><MissingGroup>false</MissingGroup>"
            + reference("LowerBound", "events.pubDouble", "pubDouble", "Numeric")
            + "<LowerBoundInclusive>true</LowerBoundInclusive>"
            + reference("UpperBound", "events.pubInt64", "pubInt64", "Numeric")
            + "<UpperBoundInclusive>false</UpperBoundInclusive></IntervalLineItemDO>";

        Step branch = intervalSplit("Numeric", bounds + interval("2.0", "true", "3.0", "true"), cell("between") + cell("fixed"));

        assertEquals("range", branch.getBranchType());
        com.sas.rtdm2id.model.id.decision.CompoundBranchCondition condition = branch.getBranchCases().get(0).getCompoundCondition();
        assertEquals(">=", condition.getLhsSimpleCondition().getOperator());
        assertNull(condition.getLhsSimpleCondition().getRhsConstant());
        assertTrue(condition.getLhsSimpleCondition().getRhsTerm().getName().contains("pubDouble"));
        assertEquals("<", condition.getRhsSimpleCondition().getOperator());
        assertTrue(condition.getRhsSimpleCondition().getRhsTerm().getName().contains("pubInt64"));
        assertEquals("2", branch.getBranchCases().get(1).getCompoundCondition().getLhsSimpleCondition().getRhsConstant()
            .replace(".0", ""));
    }

    @Test
    void comparesDiscreteValuesThatReferenceVariables() throws Exception {
        String xml = "<SplitNodeDataDO><NodeId>typed</NodeId><NodeName>Typed</NodeName>"
            + "<SplitType>dataItem</SplitType><LineItems>" + cell("value")
            + "</LineItems><SplitOnNodeDataDO><VarRefDO><Type>Char</Type>"
            + "<VarInfoId>events.Item</VarInfoId><VarName>Item</VarName></VarRefDO><LineItems>"
            + "<DiscreteLineItemDO><FormattedValue>Request.pubStr</FormattedValue>"
            + reference("Value", "events.pubStr", "pubStr", "Char") + "</DiscreteLineItemDO>"
            + "</LineItems></SplitOnNodeDataDO></SplitNodeDataDO>";
        SplitNodeDataDO source = JAXBContext.newInstance(SplitNodeDataDO.class).createUnmarshaller()
            .unmarshal(new StreamSource(new StringReader(xml)), SplitNodeDataDO.class).getValue();
        MapStorage storage = new MapStorage();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil());
        Decision decision = new Decision();

        Step branch = new SplitNodeConverter(processing, storage).createBranch(source, decision, "Typed").get(0);

        SimpleBranchCondition condition = branch.getBranchCases().get(0).getSimpleCondition();
        assertEquals("=", condition.getOperator());
        assertNull(condition.getRhsConstant());
        assertTrue(condition.getRhsTerm().getName().contains("pubStr"));
        assertTrue(decision.getSignature().stream().anyMatch(term -> term.getName().contains("pubStr")));
    }

    @Test
    void reportsSplitWithoutDataItemClearly() {
        SplitNodeDataDO source = new SplitNodeDataDO();
        source.setNodeId("broken");
        source.setNodeName("Broken");
        MapStorage storage = new MapStorage();
        SplitNodeConverter converter = new SplitNodeConverter(
            new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()), storage);

        IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
            () -> converter.createBranch(source, new Decision(), "Broken"));
        assertTrue(exception.getMessage().contains("Broken"));
    }
}
