package com.sas.rtdm2id.util.object.processing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.BranchCase;
import com.sas.rtdm2id.model.id.decision.CompoundBranchCondition;
import com.sas.rtdm2id.model.id.decision.ConditionBranch;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.Mapping;
import com.sas.rtdm2id.model.id.decision.RuleSetStep;
import com.sas.rtdm2id.model.id.decision.SignatureTerm;
import com.sas.rtdm2id.model.id.decision.SimpleBranchCondition;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.model.rtdm.SplitNodeDataDO;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import jakarta.xml.bind.JAXBContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

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
        "Numeric, 1, false, true",
        "integer, 1, true, true",
        "Char, '', true, true"
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
        stubRuleSetWrites(processing, storage);
        Decision decision = new Decision();

        lastSteps = new SplitNodeConverter(processing, storage).createBranch(source, decision, "PartnerCheck");
        Step branch = lastSteps.get(lastSteps.size() - 1);

        assertBranchOperatorContract(branch);
        assertNotNull(branch.getDefaultCase());
        if (includeRange) {
            // A missing group cannot be a range case, so the matching interval is numbered by a RuleSet
            String missingNumber = missingFirst ? "1" : "2";
            String rangeNumber = missingFirst ? "2" : "1";
            assertIntervalNumberBranch(branch, "missingsplit", missingFirst ? "missing" : "range",
                missingFirst ? missingNumber : rangeNumber, missingFirst ? "range" : "missing",
                missingFirst ? rangeNumber : missingNumber);
            String bound = "Char".equals(type) ? "'%s'" : "%s";
            assertEquals("ifn(missing(RequestPartnerId), " + missingNumber + ", ifn(RequestPartnerId >= "
                + String.format(bound, "0") + " and RequestPartnerId <= " + String.format(bound, "10") + ", "
                + rangeNumber + ", 0))", intervalNumberExpression("missingsplit"));
            return;
        }
        assertEquals(1, lastSteps.size());
        BranchCase missingCase = branch.getBranchCases().get(0);
        assertEquals("missing", missingCase.getLabel());
        assertNull(missingCase.getCompoundCondition());
        if (decision.getSignature().get(0).getDataType() == SignatureTerm.DataTypeEnum.STRING) {
            assertEquals("=", missingCase.getSimpleCondition().getOperator());
            assertEquals(expectedMissing, missingCase.getSimpleCondition().getRhsConstant());
            assertEquals(decision.getSignature().get(0).getName(), missingCase.getSimpleCondition().getLhsTerm().getName());
            assertNull(missingCase.getSimpleCondition().getLhsConditionExpression());
        } else {
            assertNumericMissingCondition(missingCase.getSimpleCondition(), decision.getSignature().get(0).getName());
        }
        assertEquals("equals", branch.getBranchType());
        assertEquals("false", source.getSplitOnNodeDataDO().getLineItems().getIntervalLineItemDOs().get(0).getLowerBoundInclusive());
        assertEquals("false", source.getSplitOnNodeDataDO().getLineItems().getIntervalLineItemDOs().get(0).getUpperBoundInclusive());
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
        CommonProcessing processing = spy(new CommonProcessing(restTemplate, storage, treeUtil));
        stubRuleSetWrites(processing, storage);
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
            assertBranchOperatorContract(branch);
            assertTrue(new ObjectMapper().valueToTree(branch).findValuesAsText("rhsConstant").stream()
                .noneMatch(value -> "infinite".equalsIgnoreCase(value.trim()) || "-infinite".equalsIgnoreCase(value.trim())),
                source.getNodeName());
            int caseIndex = 0;
            for (int sourceIndex = 0; sourceIndex < source.getLineItems().getSplitNodeLineItemDOs().size(); sourceIndex++) {
                SplitNodeDataDO.LineItems.SplitNodeLineItemDO line = source.getLineItems().getSplitNodeLineItemDOs().get(sourceIndex);
                if ("(Remainder)".equals(line.getValue())) {
                    continue;
                }
                BranchCase branchCase = branch.getBranchCases().get(caseIndex++);
                if (Boolean.parseBoolean(source.getSplitOnNodeDataDO().getLineItems().getIntervalLineItemDOs()
                        .get(sourceIndex).getMissingGroup())) {
                    missingGroups++;
                    assertEquals(line.getCellId(), branchCase.getLabel());
                    assertNull(branchCase.getCompoundCondition(), source.getNodeName());
                    assertEquals("equals", branch.getBranchType(), source.getNodeName());
                    if (branchCase.getSimpleCondition().getLhsTerm() != null
                            && branchCase.getSimpleCondition().getLhsTerm().getName().startsWith("interval_number_")) {
                        // Numbered by the interval RuleSet preceding the branch
                        assertEquals(String.valueOf(sourceIndex + 1), branchCase.getSimpleCondition().getRhsConstant(),
                            source.getNodeName());
                        assertEquals(Step.TypeEnum.RULESET, steps.get(steps.size() - 2).getType(), source.getNodeName());
                    } else if (SignatureTerm.DataTypeEnum.STRING.equals(
                            processing.getDatatypeOfVar(source.getSplitOnNodeDataDO().getVarRefDO().getType()))) {
                        assertEquals("=", branchCase.getSimpleCondition().getOperator(), source.getNodeName());
                        assertEquals("''", branchCase.getSimpleCondition().getRhsConstant(), source.getNodeName());
                    } else {
                        assertNumericMissingCondition(branchCase.getSimpleCondition(), processing.sanitizeVariableName(
                            processing.buildVariableName(source.getSplitOnNodeDataDO().getVarRefDO())));
                    }
                }
            }
            assertEquals(source.getLineItems().getSplitNodeLineItemDOs().stream()
                .anyMatch(line -> "(Remainder)".equals(line.getValue())),
                branch.getDefaultCase() != null, source.getNodeName());
        }
        assertTrue(missingGroups > 0, "Expected at least one missing interval group in the extract");
        verifyNoInteractions(restTemplate);
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
            for (BranchCase branchCase : branch.getBranchCases()) {
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
        verifyNoInteractions(restTemplate);
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

    /**
     * What ID accepts (viya-decisions-framework DecisionValidateStructureVisitor): range cases are ">=" and/or "<="
     * (AND-ed when both) with no condition expression; equals cases use "=".
     */
    private void assertBranchOperatorContract(Step branch) {
        boolean range = "range".equals(branch.getBranchType());
        Set<String> allowedOperators = range ? Set.of("<=", ">=") : Set.of("=");
        JsonNode json = new ObjectMapper().valueToTree(branch);
        for (JsonNode branchCase : json.get("branchCases")) {
            for (String operator : branchCase.findValuesAsText("operator")) {
                assertTrue(allowedOperators.contains(operator),
                    branch.getName() + ": " + branch.getBranchType() + " branch contains operator " + operator);
            }
            if (range) {
                assertTrue(branchCase.findValues("lhsConditionExpression").stream().allMatch(JsonNode::isNull),
                    branch.getName() + ": range branch case uses a condition expression");
            }
        }
    }
    private static List<int[]> ranges(String... percentages) {
        List<BigDecimal> values = new ArrayList<>();
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
        RuleSetStep ruleSet = new RuleSetStep();
        ruleSet.setName(name);
        ruleSet.setId("rs-id");
        step.setRuleSet(ruleSet);
        storage.getRuleSetStepMap().put(name, step);
        storage.getRuleSetModifiedDate().put(name, new Date());
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

        ArgumentCaptor<org.springframework.http.HttpEntity> rule =
            ArgumentCaptor.forClass(org.springframework.http.HttpEntity.class);
        InOrder order = inOrder(restTemplate);
        order.verify(restTemplate).exchange(any(java.net.URI.class),
            eq(org.springframework.http.HttpMethod.PUT),
            any(org.springframework.http.HttpEntity.class),
            eq(RuleSet.class));
        order.verify(restTemplate).exchange(any(java.net.URI.class),
            eq(org.springframework.http.HttpMethod.POST), rule.capture(),
            eq(Rule.class));
        String expression = ((Rule) rule.getValue().getBody()).getActions().get(0).getExpression();
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
        verifyNoInteractions(restTemplate);
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

        ArgumentCaptor<org.springframework.http.HttpEntity> rule =
            ArgumentCaptor.forClass(org.springframework.http.HttpEntity.class);
        verify(restTemplate).exchange(any(java.net.URI.class),
            eq(org.springframework.http.HttpMethod.POST), rule.capture(),
            eq(Rule.class));
        assertEquals("random_number = FLOOR(RAND('uniform') * 100)",
            ((Rule) rule.getValue().getBody()).getActions().get(0).getExpression());
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
        stubRuleSetWrites(processing, storage);
        lastSteps = new SplitNodeConverter(processing, storage).createBranch(source, new Decision(), "Bands");
        return lastSteps.get(lastSteps.size() - 1);
    }

    private List<Step> lastSteps;
    private RuleSet writtenRuleSet;
    private Rule writtenRule;

    /** Stands in for Viya: findOrAddRuleSet registers a new RuleSet step; the signature and rule writes are captured. */
    private void stubRuleSetWrites(CommonProcessing processing, MapStorage storage) {
        doAnswer(invocation -> {
            String name = invocation.getArgument(0);
            RuleSetStep ruleSet = new RuleSetStep();
            ruleSet.setName(name);
            ruleSet.setId("id-" + name);
            Step step = new Step();
            step.setType(Step.TypeEnum.RULESET);
            step.setMappings(new ArrayList<>());
            step.setRuleSet(ruleSet);
            storage.getRuleSetStepMap().put(name, step);
            return null;
        }).when(processing).findOrAddRuleSet(anyString(), any());
        doAnswer(invocation -> {
            writtenRuleSet = invocation.getArgument(1);
            return null;
        }).when(processing).callPutRuleSet(any(), any());
        doAnswer(invocation -> {
            writtenRule = invocation.getArgument(1);
            return null;
        }).when(processing).addActionToRuleSet(any(), any());
    }

    /** Asserts an equals branch on the interval number term with one case per RTDM cell, numbered by line position. */
    private void assertIntervalNumberBranch(Step branch, String nodeId, String... cellsAndNumbers) {
        assertEquals("equals", branch.getBranchType());
        assertBranchOperatorContract(branch);
        assertEquals(cellsAndNumbers.length / 2, branch.getBranchCases().size());
        for (int i = 0; i < cellsAndNumbers.length; i += 2) {
            BranchCase branchCase = branch.getBranchCases().get(i / 2);
            assertEquals(cellsAndNumbers[i], branchCase.getLabel());
            assertNull(branchCase.getCompoundCondition());
            assertEquals("interval_number_" + nodeId, branchCase.getSimpleCondition().getLhsTerm().getName());
            assertEquals("=", branchCase.getSimpleCondition().getOperator());
            assertEquals(cellsAndNumbers[i + 1], branchCase.getSimpleCondition().getRhsConstant());
        }
    }

    /** Asserts the interval RuleSet step precedes the branch and maps its terms, and returns the written expression. */
    private String intervalNumberExpression(String nodeId) {
        Step ruleSetStep = lastSteps.get(lastSteps.size() - 2);
        assertEquals(Step.TypeEnum.RULESET, ruleSetStep.getType());
        assertTrue(ruleSetStep.getRuleSet().getName().endsWith("_interval"), ruleSetStep.getRuleSet().getName());
        assertTrue(ruleSetStep.getMappings().stream().anyMatch(mapping -> "interval_number".equals(mapping.getStepTermName())
            && ("interval_number_" + nodeId).equals(mapping.getTargetDecisionTermName())
            && mapping.getDirection() == Mapping.DirectionEnum.OUTPUT));
        assertTrue(writtenRuleSet.getSignature().stream().anyMatch(term -> "interval_number".equals(term.getName())
            && "integer".equals(term.getDataType()) && "output".equals(term.getDirection())));
        assertEquals(1, writtenRule.getActions().size());
        assertEquals("complex", writtenRule.getActions().get(0).getType());
        String expression = writtenRule.getActions().get(0).getExpression();
        assertTrue(expression.startsWith("interval_number = "), expression);
        return expression.substring("interval_number = ".length());
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
    void numbersIntervalsWithExclusiveBoundsInARuleSet() throws Exception {
        Step branch = intervalSplit("Numeric",
            interval("0", "true", "10", "false") + interval("10", "true", "20", "false") + interval("20", "false", "30", "true"),
            cell("low") + cell("mid") + cell("high"));

        // ID range cases only accept >= and <=, so RTDM's exclusive bounds are evaluated by the RuleSet
        assertIntervalNumberBranch(branch, "intervalsplit", "low", "1", "mid", "2", "high", "3");
        assertEquals("ifn(missing(RequestScore), 0, ifn(RequestScore >= 0 and RequestScore < 10, 1, "
            + "ifn(RequestScore >= 10 and RequestScore < 20, 2, ifn(RequestScore > 20 and RequestScore <= 30, 3, 0))))",
            intervalNumberExpression("intervalsplit"));
        assertNotNull(branch.getDefaultCase());
    }

    @Test
    void keepsInclusiveIntervalsAsNativeRangeBranches() throws Exception {
        Step branch = intervalSplit("Numeric",
            interval(null, "false", "0", "true") + interval("0", "true", "10", "true") + interval("10", "true", "infinite", "false"),
            cell("negative") + cell("low") + cell("high"));

        assertEquals(1, lastSteps.size());
        assertNull(writtenRule);
        assertEquals("range", branch.getBranchType());
        assertBranchOperatorContract(branch);
        assertEquals("<=", branch.getBranchCases().get(0).getSimpleCondition().getOperator());
        assertEquals("0", branch.getBranchCases().get(0).getSimpleCondition().getRhsConstant());
        CompoundBranchCondition low = branch.getBranchCases().get(1).getCompoundCondition();
        assertEquals(">=", low.getLhsSimpleCondition().getOperator());
        assertEquals("<=", low.getRhsSimpleCondition().getOperator());
        assertNull(branch.getBranchCases().get(2).getCompoundCondition());
        assertEquals(">=", branch.getBranchCases().get(2).getSimpleCondition().getOperator());
        assertEquals("10", branch.getBranchCases().get(2).getSimpleCondition().getRhsConstant());
    }

    @Test
    void numbersOpenEndedExclusiveIntervalsInARuleSet() throws Exception {
        Step branch = intervalSplit("Numeric",
            interval(null, "false", "10", "false") + interval("10", "true", null, "false"),
            cell("below") + cell("above"));

        assertIntervalNumberBranch(branch, "intervalsplit", "below", "1", "above", "2");
        assertEquals("ifn(missing(RequestScore), 0, ifn(RequestScore < 10, 1, ifn(RequestScore >= 10, 2, 0)))",
            intervalNumberExpression("intervalsplit"));
    }

    @ParameterizedTest
    @CsvSource({
        "Numeric, 0, 0.0, true",
        "Numeric, 0, 0.0, false",
        "integer, 1, 1, true",
        "date, 2026-01-01, 2026-01-01, false"
    })
    void keepsSingleValueCasesCompatibleWithRangeBranches(String type, String lower, String upper,
            boolean singleFirst) throws Exception {
        String single = interval(lower, "true", upper, "true");
        String range = interval(lower, "true", null, "false");
        Step branch = intervalSplit(type, singleFirst ? single + range : range + single,
            singleFirst ? cell("single") + cell("range") : cell("range") + cell("single"));

        assertEquals("range", branch.getBranchType());
        assertBranchOperatorContract(branch);
        BranchCase singleCase = branch.getBranchCases().get(singleFirst ? 0 : 1);
        assertEquals("single", singleCase.getLabel());
        assertNull(singleCase.getSimpleCondition());
        CompoundBranchCondition condition = singleCase.getCompoundCondition();
        assertNotNull(condition);
        assertEquals(CompoundBranchCondition.BooleanOperatorEnum.AND,
            condition.getBooleanOperator());
        assertEquals(">=", condition.getLhsSimpleCondition().getOperator());
        assertEquals("<=", condition.getRhsSimpleCondition().getOperator());
        if ("date".equals(type)) {
            assertEquals("'01JAN2026:00:00:00'dt", condition.getLhsSimpleCondition().getRhsConstant());
            assertEquals("'01JAN2026:00:00:00'dt", condition.getRhsSimpleCondition().getRhsConstant());
        } else {
            assertEquals(0, new BigDecimal(lower).compareTo(new BigDecimal(condition.getLhsSimpleCondition().getRhsConstant())));
            assertEquals(0, new BigDecimal(upper).compareTo(new BigDecimal(condition.getRhsSimpleCondition().getRhsConstant())));
        }
        assertNotNull(branch.getDefaultCase());
    }

    private static final String MISSING_GROUP = "<IntervalLineItemDO><MissingGroup>true</MissingGroup>"
        + "<LowerBound StoredType=\"String\">unset</LowerBound><LowerBoundInclusive>true</LowerBoundInclusive>"
        + "<UpperBound StoredType=\"String\">unset</UpperBound><UpperBoundInclusive>true</UpperBoundInclusive>"
        + "</IntervalLineItemDO>";

    // Interval shape from customer extracts: missing, [-infinite, 31), [31, infinite]
    @ParameterizedTest
    @CsvSource({"infinite", "-infinite", "' InFiNiTe '"})
    void numbersMissingAndInfiniteIntervalsInARuleSet(String lowerInfinity) throws Exception {
        Step branch = intervalSplit("Numeric", MISSING_GROUP
                + interval(lowerInfinity, "true", "31.0", "false")
                + interval("31.0", "true", "infinite", "true"),
            cell("missing") + cell("below31") + cell("atLeast31"));

        assertIntervalNumberBranch(branch, "intervalsplit", "missing", "1", "below31", "2", "atLeast31", "3");
        assertEquals("ifn(missing(RequestScore), 1, ifn(RequestScore < 31.0, 2, ifn(RequestScore >= 31.0, 3, 0)))",
            intervalNumberExpression("intervalsplit"));
        assertNotNull(branch.getDefaultCase());
    }

    // Shape from customer extracts: a missing group and an "all <variable> values" group
    @ParameterizedTest
    @CsvSource({"infinite, infinite", "-infinite, infinite", "' InFiNiTe ', INFINITE"})
    void numbersAllValuesIntervalAsAnyNonMissingValue(String lower, String upper) throws Exception {
        Step branch = intervalSplit("Numeric", MISSING_GROUP + interval(lower, "true", upper, "true"),
            cell("missing") + cell("allValues"));

        assertIntervalNumberBranch(branch, "intervalsplit", "missing", "1", "allValues", "2");
        assertEquals("ifn(missing(RequestScore), 1, ifn(1, 2, 0))", intervalNumberExpression("intervalsplit"));
        assertTrue(writtenRuleSet.getSignature().stream().anyMatch(term -> "RequestScore".equals(term.getName())
            && "decimal".equals(term.getDataType()) && "input".equals(term.getDirection())));
        assertTrue(lastSteps.get(0).getMappings().stream().anyMatch(mapping -> "RequestScore".equals(mapping.getStepTermName())
            && "RequestScore".equals(mapping.getTargetDecisionTermName())
            && mapping.getDirection() == Mapping.DirectionEnum.INPUT));
    }

    @Test
    void stillRejectsIntervalWithUnsetBoundsOutsideMissingGroup() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> intervalSplit("Numeric",
            interval("unset", "true", "unset", "true"), cell("broken")));
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

        // The exclusive upper bound needs the interval RuleSet; the referenced variables become its inputs
        assertIntervalNumberBranch(branch, "intervalsplit", "between", "1", "fixed", "2");
        String expression = intervalNumberExpression("intervalsplit");
        Matcher matcher = Pattern.compile(
            "ifn\\(missing\\(RequestScore\\), 0, ifn\\(RequestScore >= (\\w*pubDouble\\w*) and RequestScore < (\\w*pubInt64\\w*), 1, "
            + "ifn\\(RequestScore >= 2\\.0 and RequestScore <= 3\\.0, 2, 0\\)\\)\\)").matcher(expression);
        assertTrue(matcher.matches(), expression);
        for (String term : new String[] {matcher.group(1), matcher.group(2)}) {
            assertTrue(writtenRuleSet.getSignature().stream().anyMatch(signature -> term.equals(signature.getName())
                && "input".equals(signature.getDirection())), term);
            assertTrue(lastSteps.get(lastSteps.size() - 2).getMappings().stream().anyMatch(mapping ->
                term.equals(mapping.getStepTermName())
                    && mapping.getDirection() == Mapping.DirectionEnum.INPUT), term);
        }
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

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> converter.createBranch(source, new Decision(), "Broken"));
        assertTrue(exception.getMessage().contains("Broken"));
    }
}
