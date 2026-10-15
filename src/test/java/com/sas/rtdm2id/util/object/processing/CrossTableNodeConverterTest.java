package com.sas.rtdm2id.util.object.processing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.CustomObjectStep;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.Mapping;
import com.sas.rtdm2id.model.id.decision.SignatureTerm;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.segmentation.SegmentationTree;
import com.sas.rtdm2id.model.id.decision.CodeFileCollection;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.ActualValue;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.ColumnCriteria;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.CrossTableLineItemDO;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.IBVariableValueDO;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.ListOfValues;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.OutputVariable;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO.RowCriteria;
import com.sas.rtdm2id.model.rtdm.extension.IBVariableDO;
import com.sas.rtdm2id.model.rtdm.SplitNodeDataDO.SplitOnNodeDataDO.VarRefDO;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class CrossTableNodeConverterTest {

    @Test
    void convertsColumnOnlyDiscreteCriteriaInCellOrder() {
        CrossTableNodeDataDO node = columnOnlyNode();

        SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(node)).convert(node);

        assertEquals(List.of("ColumnInput", "Result"),
                tree.getSignature().stream().map(SegmentationTree.SignatureTerm::getName).toList());
        assertEquals("input", tree.getSignature().get(0).getDirection());
        assertEquals("string", tree.getSignature().get(0).getDataType());
        assertEquals("output", tree.getSignature().get(1).getDirection());
        assertTrue(tree.getMatrices().isEmpty());
        assertTrue(tree.getLocalValueLists().isEmpty());
        assertEquals(List.of("ColumnInput = 'foo'", "ColumnInput = 'bar'"),
                tree.getBooleanExpressions().stream().map(SegmentationTree.BooleanExpression::getExpression).toList());
        assertSingleAxisOutcomes(tree, List.of("a", "b"));
    }

    @Test
    void convertsColumnOnlyMissingPatternsAndDefaultOutput() {
        CrossTableNodeDataDO node = columnOnlyNode();
        node.getColumnCriteria().setLike(true);
        node.getColumnCriteria().getLineItems().setCrossTableLineItemDOs(
                List.of(missingCriterion(), discreteItem("AB%"), remainderCriterion()));
        IBVariableValueDO useDefault = new IBVariableValueDO();
        useDefault.setType(14);
        ListOfValues row = values("missing", "pattern");
        row.getValues().add(useDefault);
        node.getCrossTable().setLists(List.of(row));
        node.getOutputVariable().setDefaultValue(cell("default"));

        SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(node)).convert(node);

        assertEquals(List.of("missing(ColumnInput)", "ColumnInput LIKE 'AB%'", "true"),
                tree.getBooleanExpressions().stream().map(SegmentationTree.BooleanExpression::getExpression).toList());
        assertSingleAxisOutcomes(tree, List.of("missing", "pattern", "default"));
    }

    @Test
    void convertsColumnOnlyRangesWithInfiniteBounds() {
        CrossTableNodeDataDO node = columnOnlyNode();
        node.getColumnCriteria().getVarRefDO().setType("Numeric");
        CrossTableLineItemDO lower = new CrossTableLineItemDO();
        lower.setMinValue(endpoint("String", "infinite"));
        lower.setMaxValue(maxEndpoint("Double", "10.5"));
        lower.setMaxInclusive(true);
        CrossTableLineItemDO upper = new CrossTableLineItemDO();
        upper.setMinValue(endpoint("Double", "10.5"));
        upper.setMaxValue(maxEndpoint("String", "infinite"));
        node.getColumnCriteria().getLineItems().setCrossTableLineItemDOs(List.of(lower, upper));

        SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(node)).convert(node);

        assertEquals("decimal", tree.getSignature().get(0).getDataType());
        assertEquals(List.of("ColumnInput <= 10.5", "ColumnInput > 10.5"),
                tree.getBooleanExpressions().stream().map(SegmentationTree.BooleanExpression::getExpression).toList());
        assertSingleAxisOutcomes(tree, List.of("a", "b"));
    }

    @Test
    void rejectsMalformedColumnOnlyTablesAndExplicitEmptyRowCriteria() {
        CrossTableNodeDataDO node = columnOnlyNode();
        SegmentationTreeConverter converter = new SegmentationTreeConverter(mockCommonProcessing(node));
        node.getCrossTable().setLists(List.of(values("a", "b"), values("c", "d")));
        assertThrows(IllegalArgumentException.class, () -> converter.convert(node));
        node.getCrossTable().setLists(List.of(singleValue("a")));
        assertThrows(IllegalArgumentException.class, () -> converter.convert(node));
        ListOfValues extraCells = values("a", "b");
        extraCells.getValues().add(cell("c"));
        node.getCrossTable().setLists(List.of(extraCells));
        assertThrows(IllegalArgumentException.class, () -> converter.convert(node));
        node.getCrossTable().setLists(List.of(values("a", "b")));
        node.setRowCriteria(new RowCriteria());
        assertThrows(IllegalArgumentException.class, () -> converter.convert(node));
        node.setRowCriteria(null);
        node.getColumnCriteria().getLineItems().setCrossTableLineItemDOs(List.of());
        assertThrows(IllegalArgumentException.class, () -> converter.convert(node));
    }

    private CrossTableNodeDataDO columnOnlyNode() {
        CrossTableNodeDataDO node = createNode(false, false);
        node.setRowCriteria(null);
        node.getCrossTable().setLists(List.of(values("a", "b")));
        return node;
    }

    private void assertSingleAxisOutcomes(SegmentationTree tree, List<String> expectedValues) {
        SegmentationTree.Node current = tree.getNode();
        for (String expected : expectedValues) {
            SegmentationTree.ValueNode match = current.getValueNodes().get(0);
            assertNull(match.getNext());
            assertEquals(expected, tree.getOutcomes().stream()
                    .filter(outcome -> match.getOutcomeName().equals(outcome.getName()))
                    .findFirst().orElseThrow().getActions().get(0).getValue());
            current = current.getValueNodes().size() == 2
                    ? current.getValueNodes().get(1).getNext() : null;
        }
        assertNull(current);
    }

    @Test
    void convertsRowOnlyRangesWithRemainderAndDefaultOutput() {
        CrossTableNodeDataDO node = rowOnlyNode();
        node.getRowCriteria().getVarRefDO().setType("Numeric");
        CrossTableLineItemDO bounded = new CrossTableLineItemDO();
        bounded.setMinValue(endpoint("Double", "22.4999"));
        bounded.setMaxValue(maxEndpoint("Double", "35.4999"));
        bounded.setMinInclusive(true);
        bounded.setMaxInclusive(true);
        CrossTableLineItemDO unbounded = new CrossTableLineItemDO();
        unbounded.setMinValue(endpoint("Double", "35.4999"));
        unbounded.setMaxValue(maxEndpoint("String", "infinite"));
        unbounded.setMaxInclusive(true);
        node.getRowCriteria().getLineItems().setCrossTableLineItemDOs(
                Arrays.asList(bounded, unbounded, remainderCriterion()));
        IBVariableValueDO useDefault = new IBVariableValueDO();
        useDefault.setType(14);
        ListOfValues remainder = new ListOfValues();
        remainder.setValues(Collections.singletonList(useDefault));
        node.getOutputVariable().setDefaultValue(cell("&&&&"));
        node.getCrossTable().setLists(Arrays.asList(
                singleValue("ABCF"), singleValue("BCDE"), remainder));

        SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(node)).convert(node);

        assertEquals(2, tree.getSignature().size());
        assertEquals("RowInput", tree.getSignature().get(0).getName());
        assertEquals("decimal", tree.getSignature().get(0).getDataType());
        assertEquals("output", tree.getSignature().get(1).getDirection());
        assertTrue(tree.getMatrices().isEmpty());
        assertTrue(tree.getLocalValueLists().isEmpty());
        assertEquals(Arrays.asList(
                "RowInput >= 22.4999 and RowInput <= 35.4999",
                "RowInput > 35.4999", "true"),
                tree.getBooleanExpressions().stream().map(SegmentationTree.BooleanExpression::getExpression).toList());
        SegmentationTree.Node current = tree.getNode();
        for (String expected : Arrays.asList("ABCF", "BCDE", "&&&&")) {
            SegmentationTree.ValueNode match = current.getValueNodes().get(0);
            assertNull(match.getNext());
            String outcomeName = match.getOutcomeName();
            assertEquals(expected, tree.getOutcomes().stream()
                    .filter(outcome -> outcomeName.equals(outcome.getName()))
                    .findFirst().orElseThrow().getActions().get(0).getValue());
            current = current.getValueNodes().size() == 2
                    ? current.getValueNodes().get(1).getNext() : null;
        }
        assertNull(current);
    }

    @Test
    void convertsRowOnlyInfiniteLowerBound() {
        CrossTableNodeDataDO node = rowOnlyNode();
        CrossTableLineItemDO range = new CrossTableLineItemDO();
        range.setMinValue(endpoint("String", "infinite"));
        range.setMaxValue(maxEndpoint("Integer", "10"));
        range.setMaxInclusive(true);
        node.getRowCriteria().getLineItems().setCrossTableLineItemDOs(
                Arrays.asList(range, remainderCriterion()));

        SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(node)).convert(node);

        assertEquals("RowInput <= 10", tree.getBooleanExpressions().get(0).getExpression());
    }

    @Test
    void convertsRowOnlyMissingAndPatternCriteria() {
        CrossTableNodeDataDO node = rowOnlyNode();
        node.getRowCriteria().getVarRefDO().setType("String");
        node.getRowCriteria().setLike(true);
        node.getRowCriteria().getLineItems().setCrossTableLineItemDOs(
                Arrays.asList(missingCriterion(), discreteItem("AB%"), remainderCriterion()));
        node.getCrossTable().setLists(Arrays.asList(
                singleValue("missing"), singleValue("pattern"), singleValue("other")));

        SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(node)).convert(node);

        assertEquals(Arrays.asList("missing(RowInput)", "RowInput LIKE 'AB%'", "true"),
                tree.getBooleanExpressions().stream().map(SegmentationTree.BooleanExpression::getExpression).toList());
        assertEquals(3, tree.getOutcomes().size());
    }

    @Test
    void rejectsMalformedRowOnlyTablesAndExplicitEmptyColumnCriteria() {
        CrossTableNodeDataDO node = rowOnlyNode();
        node.getCrossTable().getLists().get(0).getValues().add(cell("extra"));
        SegmentationTreeConverter converter = new SegmentationTreeConverter(mockCommonProcessing(node));
        assertThrows(IllegalArgumentException.class, () -> converter.convert(node));
        node.getCrossTable().setLists(Collections.singletonList(singleValue("a")));
        assertThrows(IllegalArgumentException.class, () -> converter.convert(node));
        node.getCrossTable().setLists(Arrays.asList(singleValue("a"), singleValue("b")));
        node.setColumnCriteria(new ColumnCriteria());
        node.getColumnCriteria().setVarRefDO(varRef("ColumnInput", "events.ColumnInput", "String"));
        assertThrows(IllegalArgumentException.class, () -> converter.convert(node));
        node.setColumnCriteria(null);
        node.setRowCriteria(null);
        assertThrows(IllegalArgumentException.class, () -> converter.convert(node));
    }

    private CrossTableNodeDataDO rowOnlyNode() {
        CrossTableNodeDataDO node = createNode(false, false);
        node.setColumnCriteria(null);
        node.getCrossTable().setLists(new ArrayList<>(Arrays.asList(singleValue("a"), singleValue("b"))));
        return node;
    }

    private ListOfValues singleValue(String value) {
        ListOfValues row = new ListOfValues();
        row.setValues(new ArrayList<>(Collections.singletonList(cell(value))));
        return row;
    }

    @Test
    @EnabledIfSystemProperty(named = "rowOnlyCrossTableExtract", matches = ".+")
    void convertsRowOnlyNodesInSuppliedExtract() throws Exception {
        javax.xml.stream.XMLInputFactory factory = javax.xml.stream.XMLInputFactory.newFactory();
        factory.setProperty(javax.xml.stream.XMLInputFactory.SUPPORT_DTD, false);
        factory.setProperty(javax.xml.stream.XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        Unmarshaller unmarshaller = JAXBContext.newInstance(CrossTableNodeDataDO.class).createUnmarshaller();
        int converted = 0;
        try (java.io.InputStream input = java.nio.file.Files.newInputStream(
                java.nio.file.Path.of(System.getProperty("rowOnlyCrossTableExtract")))) {
            javax.xml.stream.XMLStreamReader reader = factory.createXMLStreamReader(input);
            try {
                while (reader.hasNext()) {
                    if (reader.isStartElement() && "CrossTableNodeDataDO".equals(reader.getLocalName())) {
                        CrossTableNodeDataDO source = unmarshaller.unmarshal(reader, CrossTableNodeDataDO.class).getValue();
                        if (source.getColumnCriteria() != null) {
                            continue;
                        }
                        SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(source)).convert(source);
                        assertTrue(tree.getMatrices().isEmpty(), source.getNodeName());
                        assertEquals(2, tree.getSignature().size(), source.getNodeName());
                        SegmentationTree.Node current = tree.getNode();
                        for (ListOfValues row : source.getCrossTable().getLists()) {
                            SegmentationTree.ValueNode match = current.getValueNodes().get(0);
                            String outcomeName = match.getOutcomeName();
                            IBVariableValueDO value = row.getValues().get(0);
                            String expected = value.getType() == 14
                                    ? source.getOutputVariable().getDefaultValue().getStringValue()
                                    : value.getStringValue();
                            assertEquals(expected, tree.getOutcomes().stream()
                                    .filter(outcome -> outcomeName.equals(outcome.getName())).findFirst()
                                    .orElseThrow().getActions().get(0).getValue(), source.getNodeName());
                            current = current.getValueNodes().size() == 2
                                    ? current.getValueNodes().get(1).getNext() : null;
                        }
                        assertNull(current, source.getNodeName());
                        assertTrue(tree.getBooleanExpressions().stream()
                                .noneMatch(expression -> expression.getExpression().contains("infinite")),
                                source.getNodeName());
                        converted++;
                        continue;
                    }
                    reader.next();
                }
            } finally {
                reader.close();
            }
        }
        assertTrue(converted > 0);
        System.out.println("Converted and checked all row outcomes in " + converted + " row-only Cross-Table nodes");
    }

    @Test
    void mapsSegmentationTreeSignatureToDecisionStep() {
        MapStorage mapStorage = new MapStorage();
        mapStorage.getSegmentationTreeSignatureMap().put((short) 7, Arrays.asList(
                signatureTerm("DoubleInput", "decimal", "input"),
                signatureTerm("OutputDouble", "decimal", "output")));
        CustomObjectStep customObject = new CustomObjectStep();
        customObject.setName("cross_table");
        customObject.setType("segmentationTree");
        customObject.setUri("/decisions/segmentationTrees/tree/revisions/revision");
        mapStorage.getCustomObjectStepHashMap().put((short) 7, customObject);

        CrossTableNodeConverter converter = new CrossTableNodeConverter(mapStorage, mock(CommonProcessing.class));
        Decision decision = new Decision();
        Step step = converter.addCustomObjectStep(null, decision, (short) 7, "cross_table").get(0);

        assertEquals(2, step.getMappings().size());
        assertEquals("DoubleInput", step.getMappings().get(0).getStepTermName());
        assertEquals("DoubleInput", step.getMappings().get(0).getTargetDecisionTermName());
        assertEquals(Mapping.DirectionEnum.INPUT, step.getMappings().get(0).getDirection());
        assertEquals("OutputDouble", step.getMappings().get(1).getStepTermName());
        assertEquals(Mapping.DirectionEnum.OUTPUT, step.getMappings().get(1).getDirection());
        assertEquals(SignatureTerm.DataTypeEnum.DECIMAL, decision.getSignature().get(0).getDataType());
        assertEquals(SignatureTerm.DirectionEnum.INPUT, decision.getSignature().get(0).getDirection());
        assertEquals(SignatureTerm.DataTypeEnum.DECIMAL, decision.getSignature().get(1).getDataType());
        assertEquals(SignatureTerm.DirectionEnum.OUTPUT, decision.getSignature().get(1).getDirection());
    }

    @Test
    void usesUniqueDecisionTermsForTreesWithConflictingOutputTypes() {
        MapStorage mapStorage = new MapStorage();
        mapStorage.getSegmentationTreeSignatureMap().put((short) 7,
                Arrays.asList(signatureTerm("r_Output", "string", "output")));
        mapStorage.getSegmentationTreeSignatureMap().put((short) 8,
                Arrays.asList(signatureTerm("r_Output", "integer", "output")));
        Decision decision = new Decision();
        CommonProcessing commonProcessing = mock(CommonProcessing.class);
        when(commonProcessing.sanitizeAndReduceVariableName(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CrossTableNodeConverter converter = new CrossTableNodeConverter(mapStorage, commonProcessing);
        Step upstreamOutputs = converter.addCustomObjectStep(null, decision, (short) 7, "upstream_outputs").get(0);
        Step identifiers = converter.addCustomObjectStep(null, decision, (short) 8, "identifiers").get(0);

        assertEquals(2, decision.getSignature().size());
        assertEquals("r_Output", decision.getSignature().get(0).getName());
        assertEquals(SignatureTerm.DataTypeEnum.STRING, decision.getSignature().get(0).getDataType());
        assertEquals("r_Output_8", decision.getSignature().get(1).getName());
        assertEquals(SignatureTerm.DataTypeEnum.INTEGER, decision.getSignature().get(1).getDataType());
        assertEquals("r_Output", upstreamOutputs.getMappings().get(0).getTargetDecisionTermName());
        assertEquals("r_Output", upstreamOutputs.getMappings().get(0).getStepTermName());
        assertEquals("r_Output_8", identifiers.getMappings().get(0).getTargetDecisionTermName());
        assertEquals("r_Output", identifiers.getMappings().get(0).getStepTermName());
    }

    @Test
    void segmentationTreeReferenceSerializesAsCustomObjectStep() throws Exception {
    CustomObjectStep customObjectStep = new CustomObjectStep()
        .name("EvenChecker")
        .uri("/decisions/segmentationTrees/tree/revisions/revision")
        .type("segmentationTree")
        .restDNT(true);
    Step step = new Step()
        .type(Step.TypeEnum.CUSTOM_OBJECT);
    step.setCustomObject(customObjectStep);

    ObjectMapper objectMapper = new ObjectMapper()
        .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
    String json = objectMapper.writeValueAsString(step);

    assertTrue(json.contains("\"type\":\"application/vnd.sas.decision.step.custom.object\""));
    assertTrue(json.contains("\"type\":\"segmentationTree\""));
    assertTrue(json.contains("\"isRestDNT\":true"));
    assertEquals("/decisions/segmentationTrees/tree/revisions/revision",
        customObjectStep.getUri());
    }

    @Test
    void placeholderIncludesLikeSettingForColumnAndRowCriteria() throws Exception {
        MapStorage mapStorage = mock(MapStorage.class);
        CommonProcessing commonProcessing = mock(CommonProcessing.class);
        CrossTableNodeDataDO crossTableNodeDataDO = unmarshalCrossTableNode();
        when(commonProcessing.makeCrossTableNodeName(crossTableNodeDataDO)).thenReturn("cross_table");

        CrossTableNodeDataDO.OutputVariable outputVariable = crossTableNodeDataDO.getOutputVariable();
        IBVariableDO.Identifier identifier = outputVariable.getIdentifier();
        assertTrue(identifier != null);
        assertTrue("G_IN_STRING".equals(identifier.getName()));

        CrossTableNodeConverter converter = new CrossTableNodeConverter(mapStorage, commonProcessing);
        String ds2Code = converter.createCrossTablePlaceholderNode(crossTableNodeDataDO)
                .getProcess()
                .getDs2code();

        assertTrue(ds2Code.contains("Level:     Nominal"));
        assertTrue(ds2Code.contains("Default Value Is Missing: false"));
        assertTrue(ds2Code.contains("Identifier: G_IN_STRING"));

        int columnCriteriaStart = ds2Code.indexOf("Column Criteria:");
        int rowCriteriaStart = ds2Code.indexOf("Row Criteria:");
        String columnCriteriaCode = ds2Code.substring(columnCriteriaStart, rowCriteriaStart);
        String rowCriteriaCode = ds2Code.substring(rowCriteriaStart);

        assertTrue(columnCriteriaCode.contains("Like:      true"));
        assertTrue(rowCriteriaCode.contains("Like:      false"));
    }

    @Test
    void convertsTypedDiscreteValuesAndRowMajorMatrix() {
        CrossTableNodeDataDO node = createNode(false, false);
        CrossTableNodeDataDO.RowCriteria rowCriteria = node.getRowCriteria();
        rowCriteria.getLineItems().getCrossTableLineItemDOs().get(0).setActualValue(actualValue("Integer", "1"));
        rowCriteria.getLineItems().getCrossTableLineItemDOs().get(1).setActualValue(actualValue("Integer", "2"));
        node.getColumnCriteria().getVarRefDO().setType("Numeric");
        node.getColumnCriteria().getLineItems().getCrossTableLineItemDOs().get(0)
                .setActualValue(actualValue("Boolean", "true"));
        node.getColumnCriteria().getLineItems().getCrossTableLineItemDOs().get(1)
                .setActualValue(actualValue("Boolean", "false"));

        CommonProcessing commonProcessing = mockCommonProcessing(node);
        SegmentationTree tree = new SegmentationTreeConverter(commonProcessing).convert(node);

        assertEquals("matrix", tree.getNode().getType());
        SegmentationTree.Matrix matrix = tree.getMatrices().get(0);
        assertEquals("RowInput_by_ColumnInput", matrix.getName());
        assertEquals(matrix.getName(), tree.getNode().getLabel());
        assertEquals(matrix.getName(), tree.getNode().getMatrixName());
        assertEquals(Arrays.asList(1, 2), matrix.getDownVariableValues());
        assertEquals(Arrays.asList(1, 0), matrix.getAcrossVariableValues());
        assertEquals(tree.getSignature().get(0).getLocalValueListName(),
            matrix.getDownVariableLocalValueListName());
        assertEquals(tree.getSignature().get(1).getLocalValueListName(),
            matrix.getAcrossVariableLocalValueListName());
        assertEquals("a", matrix.getOutcomes().get(0).get(0));
        assertEquals("c", matrix.getOutcomes().get(1).get(0));
        assertEquals("a", tree.getNode().getValueNodes().get(0).getValue());
        assertEquals("set_a", tree.getNode().getValueNodes().get(0).getOutcomeName());
        assertEquals("set_a", tree.getOutcomes().get(0).getName());
        assertEquals("a", tree.getOutcomes().get(0).getActions().get(0).getValue());
    }

    @Test
    void labelsMatrixExitsWithOutcomeNamesWithoutChangingValues() {
        CrossTableNodeDataDO source = createNode(false, false);
        List<CrossTableLineItemDO> columns = source.getColumnCriteria().getLineItems().getCrossTableLineItemDOs();
        for (int column = 0; column < columns.size(); column++) {
            CrossTableLineItemDO criterion = columns.get(column);
            criterion.setDiscrete(false);
            criterion.setFormattedValue(null);
            criterion.setMinValue(endpoint("Double", String.valueOf(column * 2)));
            criterion.setMaxValue(maxEndpoint("Double", String.valueOf(column * 2 + 1)));
        }
        source.getCrossTable().getLists().set(1, values("a", "a"));
        source.getRowCriteria().getLineItems().getCrossTableLineItemDOs().add(missingCriterion());
        source.getCrossTable().getLists().add(values("c", "d"));

        SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(source)).convert(source);

        assertEquals(2, tree.getNode().getValueNodes().get(1).getNext().getValueNodes().size());
        SegmentationTree.Node matrixNode = tree.getNode().getValueNodes().get(1).getNext();
        assertEquals("a", matrixNode.getValueNodes().get(0).getValue());
        assertEquals("set_a", matrixNode.getValueNodes().get(0).getOutcomeName());
        assertEquals("set_a", matrixNode.getValueNodes().get(0).getLabel());
        assertEquals("set_b", matrixNode.getValueNodes().get(1).getLabel());
        assertEquals("a", tree.getOutcomes().get(0).getActions().get(0).getValue());
    }

    @Test
    void namesOutcomesFromValuesAndDisambiguatesSanitizedCollisions() {
        CrossTableNodeDataDO node = createNode(false, false);
        List<IBVariableValueDO> firstRow = node.getCrossTable().getLists().get(0).getValues();
        firstRow.get(0).setStringValue("foo-bar");
        firstRow.get(1).setStringValue("foobar");

        CommonProcessing commonProcessing = mockCommonProcessing(node);
        when(commonProcessing.sanitizeAndReduceVariableName(anyString()))
                .thenAnswer(invocation -> ((String) invocation.getArgument(0)).replaceAll("[^a-zA-Z0-9_]", ""));
        SegmentationTree tree = new SegmentationTreeConverter(commonProcessing).convert(node);

        assertEquals("set_foobar", tree.getOutcomes().get(0).getName());
        assertEquals("set_foobar_2", tree.getOutcomes().get(1).getName());
        assertEquals("set_foobar", tree.getNode().getValueNodes().get(0).getOutcomeName());
        assertEquals("foo-bar", tree.getOutcomes().get(0).getActions().get(0).getValue());
    }

    @Test
    void createsMatricesForBoundedRangesAndMissingAxes() {
        CrossTableNodeDataDO node = createNode(false, false);
        node.getRowCriteria().getLineItems().getCrossTableLineItemDOs().get(1).setMissing(true);
        node.getColumnCriteria().getVarRefDO().setType("Decimal");
        CrossTableLineItemDO range = node.getColumnCriteria().getLineItems().getCrossTableLineItemDOs().get(0);
        range.setDiscrete(false);
        range.setFormattedValue("0 to 10");
        range.setMinValue(endpoint("Decimal", "0"));
        range.setMaxValue(maxEndpoint("Decimal", "10"));
        range.setMinInclusive(true);
        range.setMaxInclusive(true);
        node.getColumnCriteria().getLineItems().getCrossTableLineItemDOs().get(1).setMissing(true);

        SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(node)).convert(node);

        assertEquals(1, tree.getMatrices().size());
        assertEquals("RowInput_by_ColumnInput", tree.getMatrices().get(0).getName());
        assertEquals(Arrays.asList("0 <= v <= 10"), tree.getMatrices().get(0).getAcrossVariableValues());
        SegmentationTree.Node missingRowDispatch = tree.getNode().getValueNodes().get(0).getNext()
            .getValueNodes().get(1).getNext();
        assertEquals("variable", missingRowDispatch.getType());
        assertEquals("0 <= v <= 10", missingRowDispatch.getValueNodes().get(0).getValue());
        assertEquals("numeric", tree.getLocalValueLists().get(1).getProperties().getDataType());
        assertEquals("0 <= v <= 10", tree.getLocalValueLists().get(1).getItems().get(0).getKey());
        assertEquals("0 to 10", tree.getLocalValueLists().get(1).getItems().get(0).getValue());
        assertEquals(1, tree.getLocalValueLists().get(1).getItems().get(0).getOrder());
        assertEquals(2, tree.getBooleanExpressions().size());
        assertTrue(tree.getBooleanExpressions().stream().anyMatch(expression ->
            "RowInput_is_missing".equals(expression.getName())
                && "missing(RowInput)".equals(expression.getExpression())));
        assertTrue(tree.getBooleanExpressions().stream().anyMatch(expression ->
            "ColumnInput_is_missing".equals(expression.getName())
                && "missing(ColumnInput)".equals(expression.getExpression())));
        assertEquals("RowInput_is_missing", tree.getNode().getLabel());
    }

    @Test
    void createsMatricesForSingleBoundNumericRanges() {
        CrossTableNodeDataDO node = createNode(false, false);
        CrossTableLineItemDO upperRange = node.getColumnCriteria().getLineItems().getCrossTableLineItemDOs().get(0);
        upperRange.setDiscrete(false);
        upperRange.setMaxValue(maxEndpoint("Integer", "700"));
        upperRange.setMaxInclusive(false);
        CrossTableLineItemDO lowerRange = node.getColumnCriteria().getLineItems().getCrossTableLineItemDOs().get(1);
        lowerRange.setDiscrete(false);
        lowerRange.setMinValue(endpoint("Integer", "700"));
        lowerRange.setMinInclusive(true);

        SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(node)).convert(node);

        assertEquals(1, tree.getMatrices().size());
        assertEquals(Arrays.asList("<700", ">=700"), tree.getMatrices().get(0).getAcrossVariableValues());
        assertEquals("<700", tree.getLocalValueLists().get(1).getItems().get(0).getKey());
        assertEquals(">=700", tree.getLocalValueLists().get(1).getItems().get(1).getKey());
    }

    @Test
    void convertsWholeDateRangesToIsoDateMatrixValues() {
        CrossTableNodeDataDO node = createNode(false, false);
        node.getRowCriteria().getVarRefDO().setType("Date");
        node.getRowCriteria().setDateType("whole");
        CrossTableLineItemDO firstRange = node.getRowCriteria().getLineItems().getCrossTableLineItemDOs().get(0);
        firstRange.setDiscrete(false);
        firstRange.setMinValue(endpoint("Date", "9/7/26, 12:00\u202fAM"));
        firstRange.setMaxValue(maxEndpoint("Date", "9/11/26, 12:00\u202fAM"));
        firstRange.setMinInclusive(true);
        firstRange.setMaxInclusive(true);
        CrossTableLineItemDO secondRange = node.getRowCriteria().getLineItems().getCrossTableLineItemDOs().get(1);
        secondRange.setDiscrete(false);
        secondRange.setMinValue(endpoint("Date", "9/14/26, 12:00\u202fAM"));
        secondRange.setMaxValue(maxEndpoint("Date", "9/18/26, 12:00\u202fAM"));
        secondRange.setMinInclusive(true);
        secondRange.setMaxInclusive(true);

        SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(node)).convert(node);

    assertEquals("matrix", tree.getNode().getType());
    assertEquals(1, tree.getMatrices().size());
    assertEquals("date", tree.getSignature().get(0).getDataType());
    assertEquals("date", tree.getLocalValueLists().get(0).getProperties().getDataType());
    assertEquals(Arrays.asList("2026-09-07 <= v <= 2026-09-11", "2026-09-14 <= v <= 2026-09-18"),
        tree.getMatrices().get(0).getDownVariableValues());
    assertEquals("2026-09-07 <= v <= 2026-09-11",
        tree.getLocalValueLists().get(0).getItems().get(0).getKey());
    }

    @Test
    void usesOneMatrixAndVariableDispatchForBooleanDateRangesWithMissingAndRemainder() {
    CrossTableNodeDataDO node = createNode(false, false);
    node.getRowCriteria().getVarRefDO().setType("Numeric");
    node.getRowCriteria().getVarRefDO().setSubType("Boolean");
    node.getRowCriteria().getVarRefDO().setVarName("BooleanInput");
    node.getRowCriteria().getLineItems().getCrossTableLineItemDOs().get(0)
        .setActualValue(actualValue("Boolean", "true"));
    node.getRowCriteria().getLineItems().getCrossTableLineItemDOs().get(0).setFormattedValue("Yes");
    node.getRowCriteria().getLineItems().getCrossTableLineItemDOs().get(1)
        .setActualValue(actualValue("Boolean", "false"));
    node.getRowCriteria().getLineItems().getCrossTableLineItemDOs().get(1).setFormattedValue("No");
    node.getRowCriteria().getLineItems().getCrossTableLineItemDOs().add(missingCriterion());

    node.getColumnCriteria().getVarRefDO().setType("Date");
    node.getColumnCriteria().getVarRefDO().setVarName("DateInput");
    node.getColumnCriteria().setDateType("whole");
    List<CrossTableLineItemDO> dateCriteria = node.getColumnCriteria().getLineItems()
        .getCrossTableLineItemDOs();
    dateCriteria.clear();
    dateCriteria.add(rangeCriterion("9/1/26, 12:00\u202fAM", "9/4/26, 12:00\u202fAM",
        "Sep 1, 2026 to Sep 4, 2026"));
    dateCriteria.add(rangeCriterion("9/7/26, 12:00\u202fAM", "9/11/26, 12:00\u202fAM",
        "Sep 7, 2026 to Sep 11, 2026"));
    dateCriteria.add(rangeCriterion("9/14/26, 12:00\u202fAM", "9/18/26, 12:00\u202fAM",
        "Sep 14, 2026 to Sep 18, 2026"));
    dateCriteria.add(missingCriterion());
    dateCriteria.add(remainderCriterion());

    node.getOutputVariable().setName("output_boolean");
    node.getOutputVariable().setTypeDescription("boolean");
    node.getCrossTable().setLists(new ArrayList<ListOfValues>());
    node.getCrossTable().getLists().add(booleanValues("true", "false", "false", "false", "false"));
    node.getCrossTable().getLists().add(booleanValues("true", "true", "false", "false", "false"));
    node.getCrossTable().getLists().add(booleanValues("true", "true", "true", "false", "false"));

    SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(node)).convert(node);

    assertEquals("booleanExpression", tree.getNode().getType());
    assertEquals(1, tree.getMatrices().size());
    assertEquals("boolean", tree.getSignature().get(0).getDataType());
    assertEquals("date", tree.getSignature().get(1).getDataType());
    assertEquals("boolean", tree.getLocalValueLists().get(0).getProperties().getDataType());
    assertEquals("date", tree.getLocalValueLists().get(1).getProperties().getDataType());
    assertEquals(Arrays.asList("true", "false"), tree.getMatrices().get(0).getDownVariableValues());
    assertEquals(Arrays.asList("2026-09-01 <= v <= 2026-09-04", "2026-09-07 <= v <= 2026-09-11",
        "2026-09-14 <= v <= 2026-09-18", "__&OTHER&__"),
        tree.getMatrices().get(0).getAcrossVariableValues());
    assertEquals(Arrays.asList(Arrays.asList("true", "false", "false", "false"),
        Arrays.asList("true", "true", "false", "false")), tree.getMatrices().get(0).getOutcomes());
    SegmentationTree.Node missingDateDispatch = tree.getNode().getValueNodes().get(0).getNext()
        .getValueNodes().get(1).getNext();
    assertEquals("variable", missingDateDispatch.getType());
    assertEquals("DateInput", missingDateDispatch.getVariableName());
    assertEquals("set_true", missingDateDispatch.getValueNodes().get(0).getOutcomeName());
    assertEquals("set_false", missingDateDispatch.getValueNodes().get(3).getOutcomeName());
    assertTrue(tree.getBooleanExpressions().stream().anyMatch(expression ->
        "BooleanInput_is_missing".equals(expression.getName())
            && "missing(BooleanInput)".equals(expression.getExpression())));
    assertTrue(tree.getBooleanExpressions().stream().anyMatch(expression ->
        "DateInput_is_missing".equals(expression.getName())
            && "missing(DateInput)".equals(expression.getExpression())));
    }

    @Test
    void usesBooleanExpressionsForMissingAndRemainderCriteria() {
        CrossTableNodeDataDO node = createNode(false, false);
        node.getRowCriteria().getLineItems().getCrossTableLineItemDOs().get(1).setMissing(true);
        node.getColumnCriteria().getVarRefDO().setType("Numeric");
        node.getColumnCriteria().getLineItems().getCrossTableLineItemDOs().get(0)
            .setActualValue(actualValue("Boolean", "true"));
        node.getColumnCriteria().getLineItems().getCrossTableLineItemDOs().get(1).setRemainder(true);

        SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(node)).convert(node);

        assertEquals("booleanExpression", tree.getNode().getType());
        assertEquals(1, tree.getMatrices().size());
        assertTrue(tree.getBooleanExpressions().stream()
            .anyMatch(expression -> "RowInput_is_missing".equals(expression.getName())
                && "missing(RowInput)".equals(expression.getExpression())));
        assertEquals("__&OTHER&__", tree.getMatrices().get(0).getAcrossVariableValues().get(1));
        assertEquals(Arrays.asList("a", "b"), tree.getMatrices().get(0).getOutcomes().get(0));
        assertEquals("variable", tree.getNode().getValueNodes().get(0).getNext().getType());
        assertEquals(1, tree.getLocalValueLists().get(1).getItems().size());
        assertEquals("1", tree.getLocalValueLists().get(1).getItems().get(0).getKey());
        assertEquals(1, tree.getLocalValueLists().get(1).getItems().get(0).getOrder());
    }

    @Test
    void convertsRangesAndLikeCriteriaIntoHybridMatrices() {
        CrossTableNodeDataDO node = createNode(true, true);
        CrossTableLineItemDO rowItem = node.getRowCriteria().getLineItems().getCrossTableLineItemDOs().get(0);
        rowItem.setDiscrete(false);
        rowItem.setMinValue(endpoint("Integer", "0"));
        rowItem.setMaxValue(maxEndpoint("Integer", "10"));
        rowItem.setMinInclusive(true);
        rowItem.setMaxInclusive(false);

        CrossTableLineItemDO columnItem = node.getColumnCriteria().getLineItems().getCrossTableLineItemDOs().get(0);
        columnItem.setActualValue(actualValue("String", "foo%"));

        CommonProcessing commonProcessing = mockCommonProcessing(node);
        SegmentationTree tree = new SegmentationTreeConverter(commonProcessing).convert(node);

        assertEquals("booleanExpression", tree.getNode().getType());
        assertEquals("ColumnInput LIKE 'foo%'", tree.getBooleanExpressions().get(0).getExpression());
        assertTrue(tree.getBooleanExpressions().stream()
            .anyMatch(expression -> "ColumnInput LIKE 'foo%'".equals(expression.getExpression())));
        assertEquals("RowInput_by_ColumnInput", tree.getMatrices().get(0).getName());
        assertEquals("RowInput_by_ColumnInput_2", tree.getMatrices().get(1).getName());
        assertEquals("0 <= v < 10", tree.getMatrices().get(0).getDownVariableValues().get(0));
        assertEquals(Collections.singletonList("__&OTHER&__"), tree.getMatrices().get(0).getAcrossVariableValues());
        assertEquals("matrix", tree.getNode().getValueNodes().get(0).getNext().getType());
        assertEquals("set_a", tree.getNode().getValueNodes().get(0).getNext()
                .getValueNodes().get(0).getOutcomeName());
    }

        @Test
        void usesRemainderMatrixAfterLikeAndMissingGuards() {
        CrossTableNodeDataDO node = createNode(false, true);
            node.getColumnCriteria().getVarRefDO().setVarName("GlobalCharacter_GV");
        node.getRowCriteria().getVarRefDO().setType("Char");
        List<CrossTableLineItemDO> rows = node.getRowCriteria().getLineItems().getCrossTableLineItemDOs();
        rows.clear();
        rows.add(discreteItem("a"));
        rows.add(discreteItem("b"));
        rows.add(discreteItem("c"));
        rows.add(missingCriterion());
        rows.add(remainderCriterion());
        List<CrossTableLineItemDO> columns = node.getColumnCriteria().getLineItems().getCrossTableLineItemDOs();
        columns.clear();
        columns.add(discreteItem("foo%"));
        columns.add(discreteItem("bar%xyz"));
        columns.add(discreteItem("_xyz%"));
        columns.add(missingCriterion());
        columns.add(remainderCriterion());
        node.getCrossTable().getLists().clear();
        for (String remainder : Arrays.asList("1.04", "2.04", "3.04", "1.23", "3.08")) {
            ListOfValues row = new ListOfValues();
            row.setValues(Arrays.asList(cell("1.23"), cell("1.23"), cell("1.23"), cell("1.23"), cell(remainder)));
            node.getCrossTable().getLists().add(row);
        }

        SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(node)).convert(node);

        assertEquals(1, tree.getMatrices().size());
        assertEquals(Arrays.asList("a", "b", "c", "__&OTHER&__"),
            tree.getMatrices().get(0).getDownVariableValues());
        assertEquals(Collections.singletonList("__&OTHER&__"),
            tree.getMatrices().get(0).getAcrossVariableValues());
        assertEquals(Arrays.asList(Collections.singletonList("1.04"), Collections.singletonList("2.04"),
            Collections.singletonList("3.04"), Collections.singletonList("3.08")),
            tree.getMatrices().get(0).getOutcomes());
        assertEquals(5, tree.getBooleanExpressions().size());
        assertEquals("RowInput_by_GlobalCharacter_GV", tree.getMatrices().get(0).getName());
        SegmentationTree.Node matrixNode = tree.getNode();
        while (!"matrix".equals(matrixNode.getType())) {
            matrixNode = matrixNode.getValueNodes().get(1).getNext();
        }
        assertEquals("RowInput_by_GlobalCharacter_GV", matrixNode.getLabel());
        assertEquals("RowInput_by_GlobalCharacter_GV", matrixNode.getMatrixName());
        assertEquals("missing(GlobalCharacter_GV)", tree.getBooleanExpressions().get(0).getExpression());
        assertEquals("GlobalCharacter_GV LIKE 'foo%'", tree.getBooleanExpressions().get(1).getExpression());
        assertEquals("set_1.23", tree.getNode().getValueNodes().get(0).getOutcomeName());
        assertEquals(3, tree.getLocalValueLists().get(1).getItems().size());
        }

    @Test
    @EnabledIfSystemProperty(named = "crossTableExtract", matches = ".+")
    void convertsEveryCellInSuppliedExtract() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        org.w3c.dom.Document document = factory.newDocumentBuilder()
                .parse(new File(System.getProperty("crossTableExtract")));
        org.w3c.dom.NodeList nodes = document.getElementsByTagName("CrossTableNodeDataDO");
        assertEquals(8, nodes.getLength());
        Unmarshaller unmarshaller = JAXBContext.newInstance(CrossTableNodeDataDO.class).createUnmarshaller();
        MapStorage storage = new MapStorage();
        storage.setBaseIp("viya.example");
        storage.setProtocol("https");
        storage.setAccessToken("token");
        storage.setParentFolderUri("/folders/folders/target");
        RestTemplate restTemplate = mock(RestTemplate.class);
        CodeFileCollection emptyRules = new CodeFileCollection();
        emptyRules.setCount(0L);
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
            .thenReturn(ResponseEntity.ok(emptyRules));
        when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(RuleSet.class)))
            .thenAnswer(invocation -> {
                RuleSet ruleSet = (RuleSet) ((HttpEntity<?>) invocation.getArgument(1)).getBody();
                ruleSet.setId("derived-inputs");
                ruleSet.setModifiedTimeStamp(new Date());
                return ResponseEntity.status(201).body(ruleSet);
            });
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(RuleSet.class)))
            .thenReturn(ResponseEntity.ok(new RuleSet()));
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(Rule.class)))
            .thenReturn(ResponseEntity.status(201).body(new Rule()));
        CommonProcessing processing = new CommonProcessing(restTemplate, storage, mock(TreeUtil.class));
        Decision decision = new Decision();
        CrossTableNodeConverter stepConverter = new CrossTableNodeConverter(storage, processing);
        List<Step> convertedSteps = new ArrayList<>();
        for (int nodeIndex = 0; nodeIndex < nodes.getLength(); nodeIndex++) {
            CrossTableNodeDataDO source = unmarshaller.unmarshal(nodes.item(nodeIndex), CrossTableNodeDataDO.class)
                    .getValue();
            SegmentationTree tree = new SegmentationTreeConverter(processing).convert(source);
            storage.getSegmentationTreeSignatureMap().put(source.getObjid(), tree.getSignature());
            storage.getCustomObjectStepHashMap().put(source.getObjid(), new CustomObjectStep().name(source.getNodeName()));
                List<Step> steps = stepConverter.addSegmentationTreeStep(source, decision, source.getObjid(), tree.getName());
                convertedSteps.add(steps.get(steps.size() - 1));
            boolean dateFunctions = "Date Functions".equals(source.getNodeName());
                assertEquals(1, tree.getMatrices().size(), source.getNodeName());
                assertEquals(2, tree.getLocalValueLists().size(), source.getNodeName());
                if (dateFunctions) {
                assertTrue(tree.getBooleanExpressions().isEmpty());
                assertEquals("matrix", tree.getNode().getType());
                assertEquals(Arrays.asList("7 <= v <= 11", "14 <= v <= 20", "24 <= v <= 28"),
                    tree.getMatrices().get(0).getDownVariableValues());
                assertEquals(Arrays.asList("28800 <= v <= 43200", "46800 <= v <= 57600", "61200 <= v <= 79200"),
                    tree.getMatrices().get(0).getAcrossVariableValues());
                assertEquals(Arrays.asList("8:00 AM - 12:00 PM", "1:00 PM - 4:00 PM", "5:00 PM - 10:00 PM"),
                    tree.getMatrices().get(0).getAcrossVariableLabels());
                assertEquals(2, steps.size());
                assertEquals(Step.TypeEnum.RULESET, steps.get(0).getType());
                assertEquals(4, steps.get(0).getMappings().size());
                assertEquals(Mapping.DirectionEnum.INPUT, steps.get(0).getMappings().get(0).getDirection());
                assertEquals(Mapping.DirectionEnum.OUTPUT, steps.get(0).getMappings().get(1).getDirection());
                assertEquals(tree.getSignature().get(0).getName(), steps.get(0).getMappings().get(1).getTargetDecisionTermName());
                for (SegmentationTree.SignatureTerm term : tree.getSignature()) {
                    if (term.getDerivationExpression() != null) {
                    assertTrue(steps.get(0).getMappings().stream().anyMatch(mapping ->
                        mapping.getDirection() == Mapping.DirectionEnum.INPUT
                            && term.getSourceVariableName().equals(mapping.getTargetDecisionTermName())));
                    assertTrue(steps.get(0).getMappings().stream().anyMatch(mapping ->
                        mapping.getDirection() == Mapping.DirectionEnum.OUTPUT
                            && term.getName().equals(mapping.getTargetDecisionTermName())));
                    assertTrue(steps.get(1).getMappings().stream().anyMatch(mapping ->
                        mapping.getDirection() == Mapping.DirectionEnum.INPUT
                            && term.getName().equals(mapping.getTargetDecisionTermName())));
                    assertEquals(SignatureTerm.DirectionEnum.NONE, decision.getSignature().stream()
                        .filter(input -> term.getName().equals(input.getName())).findFirst().get().getDirection());
                    }
                }
                }
            if ("Event_Integer_Double".equals(source.getNodeName())) {
                SegmentationTree.Node matrixNode = tree.getNode().getValueNodes().get(1).getNext()
                        .getValueNodes().get(1).getNext();
                assertEquals("eventsIntInput_by_eventsDoubleInput", matrixNode.getLabel());
                assertEquals(matrixNode.getLabel(), tree.getMatrices().get(0).getName());
                assertEquals(matrixNode.getLabel(), matrixNode.getMatrixName());
                assertEquals(5, matrixNode.getValueNodes().size());
                for (SegmentationTree.ValueNode exit : matrixNode.getValueNodes()) {
                    assertEquals(exit.getOutcomeName(), exit.getLabel());
                }
                assertTrue(matrixNode.getValueNodes().stream().noneMatch(value -> "d".equals(value.getValue())));
            }
            if ("Event_Character_LIKE".equals(source.getNodeName())) {
                assertEquals(1, tree.getMatrices().size());
                assertEquals(Arrays.asList(Collections.singletonList("1.04"), Collections.singletonList("2.04"),
                        Collections.singletonList("3.04"), Collections.singletonList("3.08")),
                        tree.getMatrices().get(0).getOutcomes());
            }
            if ("UpstreamNodeOutputs".equals(source.getNodeName())) {
                assertEquals(1, tree.getMatrices().size());
                assertEquals("Event_Integer_DoubleOutputCharacterNominal_by_Event_Boolean_DateOutputBoolean",
                    tree.getMatrices().get(0).getName());
                assertEquals("Event_Integer_DoubleOutputCharacterNominal", tree.getSignature().get(0).getName());
                assertEquals("Event_Boolean_DateOutputBoolean", tree.getSignature().get(1).getName());
                assertEquals("r_Output", tree.getSignature().get(2).getName());
                assertEquals("Event_Integer_DoubleOutputCharacterNominal_is_missing", tree.getNode().getLabel());
                assertEquals("set_x", tree.getNode().getValueNodes().get(0).getOutcomeName());
                assertEquals(2, tree.getBooleanExpressions().size());
                assertEquals(Arrays.asList(Arrays.asList("x", "y"), Arrays.asList("z", "x"),
                        Arrays.asList("y", "z"), Arrays.asList("z", "z")), tree.getMatrices().get(0).getOutcomes());
            }
            List<CrossTableLineItemDO> rows = source.getRowCriteria().getLineItems().getCrossTableLineItemDOs();
            List<CrossTableLineItemDO> columns = source.getColumnCriteria().getLineItems().getCrossTableLineItemDOs();
            for (int row = 0; row < rows.size(); row++) {
                for (int column = 0; column < columns.size(); column++) {
                    String outcomeName = evaluateCell(tree, source, row, column);
                    Object actual = tree.getOutcomes().stream().filter(outcome -> outcomeName.equals(outcome.getName()))
                            .findFirst().get().getActions().get(0).getValue();
                    IBVariableValueDO cell = source.getCrossTable().getLists().get(row).getValues().get(column);
                    if (cell.getType() == 14) {
                        cell = source.getOutputVariable().getDefaultValue();
                    }
                    String expected = "true".equalsIgnoreCase(source.getOutputVariable().getDefaultValueIsMissing())
                            && source.getCrossTable().getLists().get(row).getValues().get(column).getType() == 14
                            ? "__&MISSING&__" : expectedCellValue(cell);
                    assertEquals(expected, String.valueOf(actual), source.getNodeName() + " [" + row + "," + column + "]");
                }
            }
            assertTrue(new ObjectMapper().writeValueAsString(tree).contains("\"matrices\""));
        }
            Step upstream = convertedSteps.stream().filter(step -> "UpstreamNodeOutputs".equals(step.getCustomObject().getName()))
                .findFirst().get();
            for (String sourceNode : Arrays.asList("Event_Integer_Double", "Event_Boolean_Date")) {
                Step producer = convertedSteps.stream().filter(step -> sourceNode.equals(step.getCustomObject().getName()))
                    .findFirst().get();
                Mapping output = producer.getMappings().stream().filter(mapping -> mapping.getDirection() == Mapping.DirectionEnum.OUTPUT)
                    .findFirst().get();
                assertTrue(upstream.getMappings().stream().anyMatch(mapping -> mapping.getDirection() == Mapping.DirectionEnum.INPUT
                    && output.getTargetDecisionTermName().equals(mapping.getTargetDecisionTermName())), sourceNode);
            }
                for (Mapping mapping : upstream.getMappings()) {
                assertTrue(decision.getSignature().stream().anyMatch(term ->
                    mapping.getTargetDecisionTermName().equals(term.getName())
                        && term.getDataType() != SignatureTerm.DataTypeEnum.UNKNOWN));
                }
    }

    private String expectedCellValue(IBVariableValueDO cell) {
        if (cell == null) {
            return "__&MISSING&__";
        }
        switch (cell.getType()) {
            case 1: return new BigDecimal(cell.getDoubleValue()).toString();
            case 2: return cell.getStringValue();
            case 4: return cell.getBooleanValue();
            case 5: return cell.getLongValue();
            default: throw new IllegalArgumentException("Unexpected source cell type: " + cell.getType());
        }
    }

    private String evaluateCell(SegmentationTree tree, CrossTableNodeDataDO source, int row, int column) {
        CrossTableLineItemDO rowItem = source.getRowCriteria().getLineItems().getCrossTableLineItemDOs().get(row);
        CrossTableLineItemDO columnItem = source.getColumnCriteria().getLineItems().getCrossTableLineItemDOs().get(column);
        String rowName = tree.getSignature().get(0).getName();
        SegmentationTree.Node node = tree.getNode();
        while (node != null) {
            SegmentationTree.ValueNode selected = null;
            if ("matrix".equals(node.getType())) {
                String matrixName = node.getMatrixName();
                SegmentationTree.Matrix matrix = tree.getMatrices().stream()
                        .filter(candidate -> matrixName.equals(candidate.getName())).findFirst().get();
                Object result = matrix.getOutcomes().get(matrix.getDownVariableLabels().indexOf(sourceLabel(rowItem)))
                    .get(matrix.getAcrossVariableLabels().indexOf(sourceLabel(columnItem,
                        source.getColumnCriteria().getDateType())));
                selected = node.getValueNodes().stream().filter(value -> result.equals(value.getValue())).findFirst().get();
            } else if ("variable".equals(node.getType())) {
                String label = sourceLabel(rowName.equals(node.getVariableName()) ? rowItem : columnItem);
                selected = node.getValueNodes().stream().filter(value -> label.equals(value.getLabel())).findFirst().get();
            } else {
                String name = node.getBooleanExpressionName();
                boolean matches;
                if (name.endsWith("_is_missing")) {
                    matches = name.equals(rowName + "_is_missing") ? rowItem.isMissing() : columnItem.isMissing();
                } else if (name.contains("_matches_")) {
                    int index = Integer.parseInt(name.substring(name.lastIndexOf('_') + 1));
                    matches = name.startsWith(rowName + "_matches_") ? row == index : column == index;
                } else if (name.startsWith("row_")) {
                    matches = row == Integer.parseInt(name.substring(4));
                } else {
                    matches = column == Integer.parseInt(name.substring(name.lastIndexOf('_') + 1));
                }
                String value = String.valueOf(matches);
                selected = node.getValueNodes().stream().filter(branch -> value.equals(branch.getValue())).findFirst().get();
            }
            if (selected.getOutcomeName() != null) {
                return selected.getOutcomeName();
            }
            node = selected.getNext();
        }
        throw new IllegalStateException("No outcome for source cell");
    }

    private String sourceLabel(CrossTableLineItemDO criterion, String dateType) {
        if (dateType != null && dateType.equalsIgnoreCase("time") && !criterion.isMissing() && !criterion.isRemainder()
                && criterion.getMinValue() != null && criterion.getMaxValue() != null
                && "date".equalsIgnoreCase(criterion.getMinValue().getStoredType())) {
            DateTimeFormatter sourceFormat = DateTimeFormatter.ofPattern("M/d/yy, h:mm a", Locale.US);
            DateTimeFormatter labelFormat = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
            return LocalDateTime.parse(criterion.getMinValue().getValue().replace('\u202f', ' '), sourceFormat).format(labelFormat)
                    + " - " + LocalDateTime.parse(criterion.getMaxValue().getValue().replace('\u202f', ' '), sourceFormat).format(labelFormat);
        }
        return sourceLabel(criterion);
    }

    private String sourceLabel(CrossTableLineItemDO criterion) {
        if (criterion.isMissing()) {
            return "Missing";
        }
        if (criterion.isRemainder()) {
            return "Remainder";
        }
        if (criterion.getFormattedValue() != null && !criterion.getFormattedValue().isEmpty()) {
            return criterion.getFormattedValue();
        }
        if (criterion.isDiscrete()) {
            return criterion.getActualValue().getValue();
        }
        return (criterion.getMinValue() == null ? null : criterion.getMinValue().getValue()) + " - "
                + (criterion.getMaxValue() == null ? null : criterion.getMaxValue().getValue());
    }

        @Test
        void usesDerivedDateAxesAndPreservesMissingGuards() {
        CrossTableNodeDataDO node = createNode(false, false);
        node.getRowCriteria().setDateType("Day");
        node.getColumnCriteria().setDateType("Time");
        node.getRowCriteria().getLineItems().getCrossTableLineItemDOs().get(1).setMissing(true);
        node.getColumnCriteria().getLineItems().getCrossTableLineItemDOs().get(1).setMissing(true);
        CrossTableLineItemDO rowRange = node.getRowCriteria().getLineItems().getCrossTableLineItemDOs().get(0);
        rowRange.setDiscrete(false);
        rowRange.setMinValue(endpoint("Integer", "7"));
        rowRange.setMaxValue(maxEndpoint("Integer", "11"));
        rowRange.setMinInclusive(true);
        rowRange.setMaxInclusive(true);
        CrossTableLineItemDO columnRange = node.getColumnCriteria().getLineItems().getCrossTableLineItemDOs().get(0);
        columnRange.setDiscrete(false);
        columnRange.setMinValue(endpoint("Double", "8"));
        columnRange.setMaxValue(maxEndpoint("Double", "12"));
        columnRange.setMinInclusive(true);
        columnRange.setMaxInclusive(true);

        SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(node)).convert(node);

        assertEquals(1, tree.getMatrices().size());
        assertEquals("7 <= v <= 11", tree.getMatrices().get(0).getDownVariableValues().get(0));
        assertEquals("8 <= v <= 12", tree.getMatrices().get(0).getAcrossVariableValues().get(0));
        assertTrue(tree.getBooleanExpressions().stream().anyMatch(expression ->
            "RowInput_day_is_missing".equals(expression.getName())
                    && "missing(RowInput_day)".equals(expression.getExpression())));
        assertTrue(tree.getBooleanExpressions().stream().anyMatch(expression ->
            "ColumnInput_time_is_missing".equals(expression.getName())
                    && "missing(ColumnInput_time)".equals(expression.getExpression())));
        }

        @Test
        void preservesUpstreamInputsAndCollapsesUniformMissingRow() {
        CrossTableNodeDataDO source = createNode(false, false);
        source.getRowCriteria().setVarRefDO(varRef("OutputCharacterNominal",
            "Event_Integer_Double.OutputCharacterNominal", "Char"));
        source.getColumnCriteria().setVarRefDO(varRef("OutputBoolean",
            "Event_Boolean_Date.OutputBoolean", "Boolean"));
        source.getRowCriteria().getLineItems().getCrossTableLineItemDOs().add(missingCriterion());
        source.getColumnCriteria().getLineItems().getCrossTableLineItemDOs().add(missingCriterion());
        source.getCrossTable().getLists().get(0).getValues().add(cell("z"));
        source.getCrossTable().getLists().get(1).getValues().add(cell("y"));
        ListOfValues missingRow = new ListOfValues();
        missingRow.setValues(Arrays.asList(cell("x"), cell("x"), cell("x")));
        source.getCrossTable().getLists().add(missingRow);
        source.getOutputVariable().setName("OutputCharacterNominal");
        CommonProcessing processing = mockCommonProcessing(source);
        when(processing.buildVariableName(source.getRowCriteria().getVarRefDO()))
            .thenReturn("Event_Integer_Double.OutputCharacterNominal");
        when(processing.buildVariableName(source.getColumnCriteria().getVarRefDO()))
            .thenReturn("Event_Boolean_Date.OutputBoolean");
        when(processing.changeVariableNameToFormat(anyString()))
            .thenAnswer(invocation -> ((String) invocation.getArgument(0)).replace('.', '_'));

        SegmentationTree tree = new SegmentationTreeConverter(processing).convert(source);

        assertEquals(1, tree.getMatrices().size());
        assertEquals(2, tree.getBooleanExpressions().size());
        assertEquals("Event_Integer_Double_OutputCharacterNominal_is_missing", tree.getNode().getLabel());
        assertEquals("set_x", tree.getNode().getValueNodes().get(0).getOutcomeName());
        assertEquals("Event_Integer_Double_OutputCharacterNominal", tree.getSignature().get(0).getName());
        assertEquals("Event_Boolean_Date_OutputBoolean", tree.getSignature().get(1).getName());
        assertEquals("OutputCharacterNominal", tree.getSignature().get(2).getName());
        SegmentationTree.Node columnGuard = tree.getNode().getValueNodes().get(1).getNext();
        assertEquals("Event_Boolean_Date_OutputBoolean_is_missing", columnGuard.getLabel());
        assertEquals("variable", columnGuard.getValueNodes().get(0).getNext().getType());
        assertEquals("matrix", columnGuard.getValueNodes().get(1).getNext().getType());
        MapStorage storage = new MapStorage();
        storage.getSegmentationTreeSignatureMap().put((short) 7, tree.getSignature());
        storage.getCustomObjectStepHashMap().put((short) 7, new CustomObjectStep());
        Decision decision = new Decision();
        source.setNodeName("UpstreamNodeOutputs");
        Step step = new CrossTableNodeConverter(storage, processing)
            .addSegmentationTreeStep(source, decision, (short) 7, "upstream").get(0);
        assertEquals("Event_Integer_Double_OutputCharacterNominal", step.getMappings().get(0).getTargetDecisionTermName());
        assertEquals("UpstreamNodeOutputs_OutputCharacterNominal", step.getMappings().get(2).getTargetDecisionTermName());
        // Cross-Table steps go through addStep so fan-in into the node is cross-branch linked
        org.mockito.Mockito.verify(processing).addStep(org.mockito.ArgumentMatchers.anyList(), eq(step), eq(source.getNodeId()));
        }

        @Test
        void convertsDateFunctionsToDerivedNumericMatrixAxes() throws Exception {
            CrossTableNodeDataDO source = createNode(false, false);
            source.getRowCriteria().setDateType("day");
            source.getRowCriteria().getVarRefDO().setType("Date");
            source.getColumnCriteria().setDateType("time");
            source.getColumnCriteria().getVarRefDO().setType("Date");
            CrossTableLineItemDO day = source.getRowCriteria().getLineItems().getCrossTableLineItemDOs().get(0);
            day.setDiscrete(false);
            day.setMinValue(endpoint("Integer", "7"));
            day.setMaxValue(maxEndpoint("Integer", "11"));
            day.setMinInclusive(true);
            day.setMaxInclusive(true);
            CrossTableLineItemDO time = source.getColumnCriteria().getLineItems().getCrossTableLineItemDOs().get(0);
            time.setDiscrete(false);
            time.setMinValue(endpoint("Date", "1/1/70, 8:00\u202fAM"));
            time.setMaxValue(maxEndpoint("Date", "1/1/70, 12:00\u202fPM"));
            time.setMinInclusive(true);
            time.setMaxInclusive(true);

            SegmentationTree tree = new SegmentationTreeConverter(mockCommonProcessing(source)).convert(source);

            assertEquals("matrix", tree.getNode().getType());
            assertEquals(1, tree.getMatrices().size());
            assertEquals(2, tree.getLocalValueLists().size());
            assertTrue(tree.getBooleanExpressions().isEmpty());
            assertEquals("RowInput_day", tree.getMatrices().get(0).getDownVariableName());
            assertEquals("ColumnInput_time", tree.getMatrices().get(0).getAcrossVariableName());
            assertEquals("7 <= v <= 11", tree.getMatrices().get(0).getDownVariableValues().get(0));
            assertEquals("28800 <= v <= 43200", tree.getMatrices().get(0).getAcrossVariableValues().get(0));
            assertEquals("day(datepart(RowInput))", tree.getSignature().get(0).getDerivationExpression());
            assertEquals("timepart(ColumnInput)", tree.getSignature().get(1).getDerivationExpression());
            assertEquals("datetime", tree.getSignature().get(0).getSourceDataType());
            assertTrue(!new ObjectMapper().writeValueAsString(tree).contains("derivationExpression"));
            MapStorage storage = new MapStorage();
            storage.getSegmentationTreeSignatureMap().put((short) 7, tree.getSignature());
            storage.getCustomObjectStepHashMap().put((short) 7, new CustomObjectStep());
            CommonProcessing processing = mockCommonProcessing(source);
            Step preprocessing = new Step().type(Step.TypeEnum.RULESET);
            preprocessing.setRuleSet(new com.sas.rtdm2id.model.id.decision.RuleSetStep().name("date_values"));
            storage.getRuleSetStepMap().put("date_values_date_values", preprocessing);
            Decision decision = new Decision();
            List<Step> steps = new CrossTableNodeConverter(storage, processing)
                .addSegmentationTreeStep(source, decision, (short) 7, "date_values");
            assertEquals(preprocessing, steps.get(0));
            assertEquals(Step.TypeEnum.CUSTOM_OBJECT, steps.get(1).getType());
            org.mockito.ArgumentCaptor<com.sas.rtdm2id.model.id.rules.Rule> rules = org.mockito.ArgumentCaptor
                .forClass(com.sas.rtdm2id.model.id.rules.Rule.class);
            org.mockito.Mockito.verify(processing).addActionToRuleSet(org.mockito.ArgumentMatchers.any(), rules.capture());
            assertEquals("RowInput_day = day(datepart(RowInput))", rules.getValue().getActions().get(0).getExpression());
            assertEquals("ColumnInput_time = timepart(ColumnInput)", rules.getValue().getActions().get(1).getExpression());
        }

        private CommonProcessing mockCommonProcessing(CrossTableNodeDataDO node) {
        CommonProcessing commonProcessing = mock(CommonProcessing.class);
        when(commonProcessing.makeCrossTableNodeName(node)).thenReturn("cross_table");
        when(commonProcessing.changeVariableNameToFormat(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(commonProcessing.sanitizeVariableName(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(commonProcessing.sanitizeAndReduceVariableName(anyString()))
            .thenAnswer(invocation -> invocation.getArgument(0));
        addStepsToList(commonProcessing);
        return commonProcessing;
    }

    private static void addStepsToList(CommonProcessing commonProcessing) {
        org.mockito.Mockito.doAnswer(invocation -> {
            List<Step> steps = invocation.getArgument(0);
            steps.add(invocation.getArgument(1));
            return null;
        }).when(commonProcessing).addStep(org.mockito.ArgumentMatchers.anyList(), any(Step.class), anyString());
    }

    private SegmentationTree.SignatureTerm signatureTerm(String name, String dataType, String direction) {
        SegmentationTree.SignatureTerm term = new SegmentationTree.SignatureTerm();
        term.setName(name);
        term.setDataType(dataType);
        term.setDirection(direction);
        return term;
    }

    private CrossTableNodeDataDO createNode(boolean rangeRow, boolean likeColumn) {
        CrossTableNodeDataDO node = new CrossTableNodeDataDO();
        node.setNodeName("Cross Table");
        node.setNodeId("1");
        node.setLabel("Cross Table");

        RowCriteria rowCriteria = new RowCriteria();
        rowCriteria.setVarRefDO(varRef("RowInput", "events.RowInput", "Integer"));
        rowCriteria.setLineItems(new CrossTableNodeDataDO.LineItems());
        rowCriteria.getLineItems().setCrossTableLineItemDOs(new ArrayList<>());
        rowCriteria.getLineItems().getCrossTableLineItemDOs().add(discreteItem("1"));
        rowCriteria.getLineItems().getCrossTableLineItemDOs().add(discreteItem("2"));
        node.setRowCriteria(rowCriteria);

        ColumnCriteria columnCriteria = new ColumnCriteria();
        columnCriteria.setLike(likeColumn);
        columnCriteria.setVarRefDO(varRef("ColumnInput", "events.ColumnInput", "String"));
        columnCriteria.setLineItems(new CrossTableNodeDataDO.LineItems());
        columnCriteria.getLineItems().setCrossTableLineItemDOs(new ArrayList<>());
        CrossTableLineItemDO firstColumn = discreteItem("foo");
        CrossTableLineItemDO secondColumn = discreteItem("bar");
        columnCriteria.getLineItems().getCrossTableLineItemDOs().add(firstColumn);
        columnCriteria.getLineItems().getCrossTableLineItemDOs().add(secondColumn);
        node.setColumnCriteria(columnCriteria);

        OutputVariable outputVariable = new OutputVariable();
        outputVariable.setName("Result");
        outputVariable.setTypeDescription("string");
        outputVariable.setDefaultValueIsMissing("false");
        node.setOutputVariable(outputVariable);

        CrossTableNodeDataDO.CrossTable crossTable = new CrossTableNodeDataDO.CrossTable();
        crossTable.setLists(new ArrayList<>());
        crossTable.getLists().add(values("a", "b"));
        crossTable.getLists().add(values("c", "d"));
        node.setCrossTable(crossTable);
        return node;
    }

    private CrossTableLineItemDO discreteItem(String value) {
        CrossTableLineItemDO item = new CrossTableLineItemDO();
        item.setDiscrete(true);
        item.setFormattedValue(value);
        item.setActualValue(actualValue("String", value));
        return item;
    }

    private CrossTableLineItemDO rangeCriterion(String minimum, String maximum, String label) {
        CrossTableLineItemDO item = new CrossTableLineItemDO();
        item.setDiscrete(false);
        item.setFormattedValue(label);
        item.setMinValue(endpoint("Date", minimum));
        item.setMaxValue(maxEndpoint("Date", maximum));
        item.setMinInclusive(true);
        item.setMaxInclusive(true);
        return item;
    }

    private CrossTableLineItemDO missingCriterion() {
        CrossTableLineItemDO item = new CrossTableLineItemDO();
        item.setDiscrete(true);
        item.setMissing(true);
        item.setFormattedValue("(Missing)");
        return item;
    }

    private CrossTableLineItemDO remainderCriterion() {
        CrossTableLineItemDO item = new CrossTableLineItemDO();
        item.setDiscrete(true);
        item.setRemainder(true);
        item.setFormattedValue("(Remainder)");
        return item;
    }

    private ActualValue actualValue(String storedType, String value) {
        ActualValue actualValue = new ActualValue();
        actualValue.setStoredType(storedType);
        actualValue.setValue(value);
        return actualValue;
    }

    private CrossTableNodeDataDO.MinValue endpoint(String storedType, String value) {
        CrossTableNodeDataDO.MinValue endpoint = new CrossTableNodeDataDO.MinValue();
        endpoint.setStoredType(storedType);
        endpoint.setValue(value);
        return endpoint;
    }

    private CrossTableNodeDataDO.MaxValue maxEndpoint(String storedType, String value) {
        CrossTableNodeDataDO.MaxValue endpoint = new CrossTableNodeDataDO.MaxValue();
        endpoint.setStoredType(storedType);
        endpoint.setValue(value);
        return endpoint;
    }

    private VarRefDO varRef(String name, String varInfoId, String type) {
        VarRefDO varRefDO = new VarRefDO();
        varRefDO.setVarName(name);
        varRefDO.setVarInfoId(varInfoId);
        varRefDO.setType(type);
        return varRefDO;
    }

    private ListOfValues values(String first, String second) {
        ListOfValues values = new ListOfValues();
        values.setValues(new ArrayList<IBVariableValueDO>());
        values.getValues().add(cell(first));
        values.getValues().add(cell(second));
        return values;
    }

    private ListOfValues booleanValues(String... values) {
        ListOfValues row = new ListOfValues();
        row.setValues(new ArrayList<IBVariableValueDO>());
        for (String value : values) {
            IBVariableValueDO cell = new IBVariableValueDO();
            cell.setType(4);
            cell.setBooleanValue(value);
            row.getValues().add(cell);
        }
        return row;
    }

    private IBVariableValueDO cell(String value) {
        IBVariableValueDO cell = new IBVariableValueDO();
        cell.setType(2);
        cell.setStringValue(value);
        return cell;
    }

    private CrossTableNodeDataDO unmarshalCrossTableNode() throws Exception {
        String xml = "<CrossTableNodeDataDO>"
                + "<ColumnCriteria><Like>true</Like><LineItems/></ColumnCriteria>"
                + "<CrossTable/>"
            + "<OutputVariable>"
            + "<Identifier><Id>G_IN_STRING</Id><Name>G_IN_STRING</Name></Identifier>"
            + "<DefaultValueIsMissing>false</DefaultValueIsMissing>"
            + "<Level>Nominal</Level><Name>OutputCharacterNominal</Name>"
            + "<PossibleValues/><Type>1</Type>"
            + "</OutputVariable>"
                + "<RowCriteria><Like>false</Like><LineItems/></RowCriteria>"
                + "</CrossTableNodeDataDO>";
        JAXBContext context = JAXBContext.newInstance(CrossTableNodeDataDO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        JAXBElement<CrossTableNodeDataDO> result = unmarshaller.unmarshal(
                new StreamSource(new StringReader(xml)), CrossTableNodeDataDO.class);
        return result.getValue();
    }
}