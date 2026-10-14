package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.CodeFileCollection;
import com.sas.rtdm2id.model.id.decision.CustomObjectStep;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.Mapping;
import com.sas.rtdm2id.model.id.decision.RuleSetStep;
import com.sas.rtdm2id.model.id.decision.SignatureTerm;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.rules.Action;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.id.rules.Signature;
import com.sas.rtdm2id.model.rtdm.AssignmentNodeDataDO;
import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO;
import com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO;
import com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO;
import com.sas.rtdm2id.model.rtdm.extension.VarRef;
import com.sas.rtdm2id.util.model.IBVariableDO;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import jakarta.xml.bind.JAXBContext;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * Regression tests for calculated-item scheduling, RuleSet signatures and name sanitization fixes.
 */
class CommonProcessingRegressionTest {

    private static final String VALID_IDENTIFIER = "[A-Za-z_][A-Za-z0-9_]*";

    @Test
    void namesRequestGlobalAndCalculatedVariablesFromTheirIdAlone() {
        assertEquals("Request.assetSegment", CommonProcessing.sourceQualifiedName("events.assetSegment"));
        assertEquals("Request.x", CommonProcessing.sourceQualifiedName("inputVariables.x"));
        assertEquals("Global.TEST_MODE", CommonProcessing.sourceQualifiedName("globals.TEST_MODE"));
        assertEquals("CalculatedDataItem.check", CommonProcessing.sourceQualifiedName("calculatedDataItems.check"));
        assertEquals("Identifier.cdRetorno", CommonProcessing.sourceQualifiedName("identifiers.cdRetorno"));
        // Upstream node outputs are resolved through the registered producers instead (registerNodeOutputs)
        assertNull(CommonProcessing.sourceQualifiedName("Valida TID.Groovy_v1cdRetorno"));
        assertNull(CommonProcessing.sourceQualifiedName("events."));
        assertNull(CommonProcessing.sourceQualifiedName(null));
    }

    @Test
    void readsGlobalVariableForAProcessInputValuedFromAGlobal() {
        // Customer shape: a Groovy process input (urlRestMW) whose value is the global globals.urlRestMW
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), new MapStorage(), new TreeUtil());
        ValueTypeVarInfoDO global = new ValueTypeVarInfoDO();
        global.setVarInfoId("globals.urlRestMW");
        global.setVarInfoSource("Global");
        global.setVarName("urlRestMw");
        global.setType("Char");
        IBVariableDO input = new IBVariableDO();
        input.setName("urlRestMW");
        input.setTypeDescription("string");
        input.setValue(new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value());
        input.getValue().setValueTypeVarInfoDO(global);
        Step step = new Step();

        processing.addMapping(input, "input", step, false, new Decision());

        assertEquals("urlRestMW", step.getMappings().get(0).getStepTermName());
        // Named from the id (not the differently-cased VarName), exactly as checkForGlobalVariable declares it
        assertEquals("GlobalurlRestMW", step.getMappings().get(0).getTargetDecisionTermName());
    }

    @Test
    void assignsMissingInsteadOfAnEmptyValueForUnsetProcessInputs() {
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), new MapStorage(), new TreeUtil());
        List<Action> actions = new ArrayList<>();
        for (String type : List.of("double", "string", "datagrid")) {
            IBVariableDO unset = new IBVariableDO();
            unset.setName("param_" + type.replace(' ', '_'));
            unset.setTypeDescription(type);
            processing.addAction(unset, actions, new ArrayList<>(), new Step(), new Decision());
        }

        assertEquals(List.of("param_double = .", "param_string = ''"), actions.stream().map(Action::getExpression).toList());
    }

    @Test
    void mapsRuleSetTermsByTheirReducedName() {
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), new MapStorage(), new TreeUtil());
        Step step = new Step();
        String longName = "CalculatedDataItem.descricaoerrosegmentoparaocliente";

        processing.addMapping(longName, "unused", step, true);

        String stepTerm = step.getMappings().get(0).getStepTermName();
        assertTrue(stepTerm.length() <= 32, stepTerm);
        assertEquals(processing.sanitizeAndReduceVariableName(processing.sanitizeVariableName(longName)), stepTerm);
    }

    @Test
    void schedulesCalculatedItemWhoseNameNeedsSanitizing() {
        MapStorage storage = new MapStorage();
        CommonProcessing processing = localProcessing(storage);
        for (String name : List.of("Total Score", "Date", "1st-score")) {
            CalculatedItemDO item = item(name, "calc-" + name.length(), "4", "1");
            processing.addRulesetForCalculatedItem(item);
            Step created = storage.getRuleSetStepMap().get(processing.makeCalculatedDataItemRuleSetName(item));
            List<Step> steps = new ArrayList<>();
            Step consumer = new Step();

            assertTrue(processing.checkForCalcVariable("calculatedDataItems." + name, "double", steps, consumer, "target", "node"));

            assertEquals(List.of(created), steps, name);
            assertEquals(1, consumer.getMappings().size());
        }
    }

    @Test
    void schedulesCalculatedDependenciesBeforeTheirDependentOnce() {
        MapStorage storage = new MapStorage();
        CommonProcessing processing = localProcessing(storage);
        CalculatedItemDO a = item("A", "calcA", "4", "<<calculatedDataItems.B>>*2");
        CalculatedItemDO b = item("B", "calcB", "4", "<<calculatedDataItems.C>>+<<events.number>>");
        CalculatedItemDO c = item("C", "calcC", "4", "1");
        for (CalculatedItemDO item : List.of(a, b, c)) {
            processing.addRulesetForCalculatedItem(item);
        }
        Step stepA = storage.getRuleSetStepMap().get("A_calcA");
        Step stepB = storage.getRuleSetStepMap().get("B_calcB");
        Step stepC = storage.getRuleSetStepMap().get("C_calcC");
        List<Step> steps = new ArrayList<>();

        processing.checkForCalcVariable("calculatedDataItems.A", "double", steps, null, "A", "consumer");
        processing.checkForCalcVariable("calculatedDataItems.B", "double", steps, null, "B", "consumer");
        assertEquals(List.of(stepC, stepB, stepA), steps);

        // Migration-wide suppression still applies to dependencies
        List<Step> other = new ArrayList<>();
        processing.checkForCalcVariable("calculatedDataItems.C", "double", other, null, "C", "other");
        assertTrue(other.isEmpty());
        assertTrue(stepB.getMappings().stream().anyMatch(mapping -> "CalculatedDataItemB".equals(mapping.getTargetDecisionTermName())
            && Mapping.DirectionEnum.OUTPUT.equals(mapping.getDirection())));
    }

    @Test
    void toleratesCyclicCalculatedDependencies() {
        MapStorage storage = new MapStorage();
        CommonProcessing processing = localProcessing(storage);
        processing.addRulesetForCalculatedItem(item("A", "calcA", "4", "<<calculatedDataItems.B>>"));
        processing.addRulesetForCalculatedItem(item("B", "calcB", "4", "<<calculatedDataItems.A>>"));
        List<Step> steps = new ArrayList<>();

        processing.checkForCalcVariable("calculatedDataItems.A", "double", steps, null, "A", "consumer");

        assertEquals(List.of(storage.getRuleSetStepMap().get("B_calcB"), storage.getRuleSetStepMap().get("A_calcA")), steps);
    }

    @Test
    void mapsAdditionalDatatypes() {
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), new MapStorage(), new TreeUtil());
        assertEquals(SignatureTerm.DataTypeEnum.DATETIME, processing.getDatatypeOfVar("datetime"));
        assertEquals(SignatureTerm.DataTypeEnum.DATAGRID, processing.getDatatypeOfVar("dataGrid"));
        assertEquals(SignatureTerm.DataTypeEnum.DATAGRID, processing.getDatatypeOfVar("data grid"));
        assertEquals(SignatureTerm.DataTypeEnum.ANY, processing.getDatatypeOfVar(null));
        assertEquals("datetime", processing.getCalculatedItemDataType("2"));
        assertEquals("date", processing.getCalculatedItemDataType("1"));
    }

    @Test
    void writesMissingTimestampCalculationAsDatetime() {
        MapStorage storage = new MapStorage();
        CommonProcessing processing = localProcessing(storage);
        processing.addRulesetForCalculatedItem(item("stamp", "calc", "2", "Missing"));

        ArgumentCaptor<RuleSet> ruleSet = ArgumentCaptor.forClass(RuleSet.class);
        ArgumentCaptor<Rule> rule = ArgumentCaptor.forClass(Rule.class);
        verify(processing).callPutRuleSet(any(), ruleSet.capture());
        verify(processing).addActionToRuleSet(any(), rule.capture());
        assertEquals("datetime", ruleSet.getValue().getSignature().get(0).getDataType());
        assertEquals("CalculatedDataItemstamp=.", rule.getValue().getActions().get(0).getExpression());
    }

    @Test
    void dedupesRuleSetTermsByNameAndWidensNumericTypes() {
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), new MapStorage(), new TreeUtil());
        List<Signature> signatures = new ArrayList<>();
        processing.checkSignatureTermForDuplicate("input", signatures, signature("source", "integer", "input"));
        processing.checkSignatureTermForDuplicate("input", signatures, signature("SOURCE", "decimal", "input"));
        processing.checkSignatureTermForDuplicate("input", signatures, signature("text", "string", "input"));
        processing.checkSignatureTermForDuplicate("input", signatures, signature("text", "integer", "input"));
        processing.checkSignatureTermForDuplicate("input", signatures, signature("text", "decimal", "input"));

        assertEquals(2, signatures.size());
        assertEquals("decimal", signatures.get(0).getDataType());
        assertEquals("string", signatures.get(1).getDataType());
    }

    @Test
    void typesAssignmentSourceTermsByTheDeclaredRequestType() throws Exception {
        MapStorage storage = new MapStorage();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil());
        processing.createRuleSets(batch("<StartNodeDataDO><NodeId>start</NodeId><Event><RequestVars><IBVariableDO>"
            + "<Name>score</Name><TypeDescription>integer</TypeDescription></IBVariableDO></RequestVars></Event></StartNodeDataDO>"
            + "<FlowDO><CalculatedDataItemList/></FlowDO>"));
        List<Signature> signatures = new ArrayList<>();
        List<Action> actions = new ArrayList<>();
        for (String type : List.of("double", "string")) {
            IBVariableDO variable = new IBVariableDO();
            variable.setName("assigned_" + type);
            variable.setTypeDescription(type);
            variable.setValue(new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value());
            ValueTypeVarInfoDO reference = new ValueTypeVarInfoDO();
            reference.setVarInfoId("events.score");
            reference.setVarInfoSource("Request");
            reference.setVarName("score");
            variable.getValue().setValueTypeVarInfoDO(reference);

            processing.addAction(variable, actions, signatures, new Step(), new Decision());
        }

        assertEquals(1, signatures.size());
        assertEquals("Requestscore", signatures.get(0).getName());
        assertEquals("integer", signatures.get(0).getDataType());
    }

    @Test
    void postsAssignmentRulesOnlyForRuleSetsWithoutActions() throws Exception {
        AssignmentNodeDataDO node = batch("<AssignmentNodeDataDO><NodeId>assign</NodeId><Assignments>"
            + "<IBVariableDO><Name>first</Name><TypeDescription>string</TypeDescription><Value><StringValue>a</StringValue></Value></IBVariableDO>"
            + "<IBVariableDO><Name>second</Name><TypeDescription>string</TypeDescription><Value><StringValue>b</StringValue></Value></IBVariableDO>"
            + "</Assignments></AssignmentNodeDataDO>").getLogicalUnit().getAssignmentNodeDataDOs().get(0);
        for (boolean reused : List.of(false, true)) {
            MapStorage storage = new MapStorage();
            CommonProcessing processing = spy(new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
            doNothing().when(processing).addActionToRuleSet(any(), any());
            RuleSetStep ruleSetStep = new RuleSetStep().name("assign_rules");
            storage.getRuleSetIsActionUpdated().put("assign_rules", reused);
            Step step = new Step();
            step.setRuleSet(ruleSetStep);

            processing.processAssignmentVariables(step, new Decision(), new RuleSet(), ruleSetStep,
                node.getAssignments().getIbVariableDOs());

            verify(processing, times(reused ? 0 : 2)).addActionToRuleSet(eq(ruleSetStep), any(Rule.class));
        }
    }

    @Test
    void propagatesDecisionLookupFailures() throws Exception {
        RestTemplate template = mock(RestTemplate.class);
        CommonProcessing processing = new CommonProcessing(template, remoteStorage(), new TreeUtil());
        when(template.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
            .thenThrow(new RestClientException("unavailable"));

        IllegalStateException failure = assertThrows(IllegalStateException.class, () -> processing.findDecision("Campaign"));
        assertTrue(failure.getMessage().contains("Campaign"));

        Batch batch = batch("<SubDiagramNodeDataDO><NodeId>sub</NodeId><SubdiagramName>Child</SubdiagramName></SubDiagramNodeDataDO>");
        assertThrows(IllegalStateException.class, () -> processing.getSubDiagramNodeNames(batch));

        CodeFileCollection found = new CodeFileCollection();
        found.setCount(1L);
        CodeFileCollection none = new CodeFileCollection();
        none.setCount(0L);
        when(template.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
            .thenReturn(ResponseEntity.ok(found), ResponseEntity.ok(none));
        assertTrue(processing.findDecision("Campaign"));
        assertFalse(processing.findDecision("Campaign"));
    }

    @Test
    void sanitizesNamesAfterStrippingInvalidCharacters() {
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), new MapStorage(), new TreeUtil());
        assertEquals("v", processing.sanitizeVariableName(""));
        assertEquals("v", processing.sanitizeVariableName("$ -"));
        assertEquals("r_Date", processing.sanitizeVariableName("Date "));
        assertEquals("_1a", processing.sanitizeVariableName("$1a"));
        // Previously valid names are unchanged
        assertEquals("r_Date", processing.sanitizeVariableName("Date"));
        assertEquals("_1a", processing.sanitizeVariableName("1a"));
        assertEquals("_1a", processing.sanitizeVariableName("1 a"));
        assertEquals("TotalScore", processing.sanitizeVariableName("Total Score"));
        assertEquals("r__N_", processing.sanitizeVariableName("_N_"));
        assertEquals("Score_1", processing.sanitizeVariableName("Score_1"));
    }

    @Test
    void reducesNamesWhoseHashIsIntegerMinValue() {
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), new MapStorage(), new TreeUtil());
        String name = "polygenelubricants" + "f5a5a608" + "f5a5a608";
        assertEquals(Integer.MIN_VALUE, name.hashCode());

        String reduced = processing.sanitizeAndReduceVariableName(name);

        assertEquals(32, reduced.length());
        assertTrue(reduced.matches(VALID_IDENTIFIER), reduced);
        assertTrue(reduced.endsWith("2147483648"), reduced);
    }

    @Test
    void keepsSameNamedExpressionInputsFromDifferentSourcesDistinct() {
        MapStorage storage = new MapStorage();
        CommonProcessing processing = localProcessing(storage);
        processing.addRulesetForCalculatedItem(item("diff", "calc", "4",
            "<<events.Amt>> - <<globals.Amt>> + <<events.Amt>>"));

        ArgumentCaptor<RuleSet> ruleSet = ArgumentCaptor.forClass(RuleSet.class);
        ArgumentCaptor<Rule> rule = ArgumentCaptor.forClass(Rule.class);
        verify(processing).callPutRuleSet(any(), ruleSet.capture());
        verify(processing).addActionToRuleSet(any(), rule.capture());
        assertEquals("CalculatedDataItemdiff=Amt - GlobalAmt + Amt", rule.getValue().getActions().get(0).getExpression());
        assertEquals(List.of("CalculatedDataItemdiff", "Amt", "GlobalAmt"),
            ruleSet.getValue().getSignature().stream().map(Signature::getName).toList());
        Step step = storage.getRuleSetStepMap().get("diff_calc");
        assertTrue(step.getMappings().stream().anyMatch(mapping -> "Amt".equals(mapping.getStepTermName())
            && "RequestAmt".equals(mapping.getTargetDecisionTermName())));
        assertTrue(step.getMappings().stream().anyMatch(mapping -> "GlobalAmt".equals(mapping.getStepTermName())
            && "GlobalAmt".equals(mapping.getTargetDecisionTermName())));
    }

    @Test
    void hashesLongNodeOutputReferencesDeterministicallyAndValidly() {
        int index = 0;
        while (("Node" + index + "ReadCustomer").hashCode() >= 0) {
            index++;
        }
        String shortName = "Node" + index;
        String longName = shortName + "ReadCustomer";
        for (List<String> order : List.of(List.of(shortName, longName), List.of(longName, shortName))) {
            MapStorage storage = new MapStorage();
            short key = 0;
            for (String name : order) {
                CustomObjectStep customStep = new CustomObjectStep();
                customStep.setName(name);
                storage.getCustomObjectStepHashMap().put(key++, customStep);
            }
            CommonProcessing processing = localProcessing(storage);
            String variable = longName + "AN_OUTPUT_VARIABLE_WITH_A_LONG_NAME";
            processing.addRulesetForCalculatedItem(item("calc", "calc", "4", "<<upstream." + variable + ">>"));

            ArgumentCaptor<Rule> rule = ArgumentCaptor.forClass(Rule.class);
            verify(processing).addActionToRuleSet(any(), rule.capture());
            String term = rule.getValue().getActions().get(0).getExpression().substring("CalculatedDataItemcalc=".length());
            assertTrue(term.matches(VALID_IDENTIFIER), term);
            // Starts with a letter: a leading '_' (from a leading digit) was rejected by ID
            assertTrue(term.startsWith("h" + Integer.toUnsignedString(longName.hashCode()) + "_"), term);
        }
        assertTrue(longName.hashCode() < 0);
    }

    @Test
    void reportsMissingCalculatedRuleSetsWithoutFailing() {
        MapStorage storage = new MapStorage();
        CommonProcessing processing = spy(new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
        setField(processing, "consolidateCalculatedRuleSets", false);
        doNothing().when(processing).findOrAddRuleSet(any(), any());

        processing.addRulesetForCalculatedItem(item("lost", "calc", "4", "1"));

        verify(processing, never()).callPutRuleSet(any(), any());
        assertEquals("decimal", storage.getCalculatedVariableDataTypeMap().get("calculatedDataItems.lost"));
        List<Step> steps = new ArrayList<>();
        Step consumer = new Step();
        assertTrue(processing.checkForCalcVariable("calculatedDataItems.lost", "double", steps, consumer, "x", "node"));
        assertTrue(steps.isEmpty());
        assertTrue(consumer.getMappings() == null || consumer.getMappings().isEmpty());
    }

    @Test
    void ignoresMalformedVariableReferences() {
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), new MapStorage(), new TreeUtil());
        List<Step> steps = new ArrayList<>();
        assertFalse(processing.checkForCalcVariable(null, "double", steps, null, "x", "node"));
        assertFalse(processing.checkForCalcVariable("", "double", steps, null, "x", "node"));
        assertFalse(processing.checkForCalcVariable("noSource", "double", steps, null, "x", "node"));
        assertFalse(processing.checkForGlobalVariable(null, null, "x", new Decision(), true));
        VarRef reference = new VarRef();
        assertFalse(processing.checkForGlobalVariable(reference, null, "x", new Decision(), true));
        reference.setVarInfoId("noSource");
        assertTrue(processing.checkForGlobalVariable(reference, null, "x", new Decision(), true));
        assertTrue(steps.isEmpty());
    }

    private static CommonProcessing localProcessing(MapStorage storage) {
        CommonProcessing processing = spy(new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
        // One RuleSet per calculated item, as with rtdm2id.consolidate-calculated-rulesets=false
        setField(processing, "consolidateCalculatedRuleSets", false);
        doAnswer(invocation -> {
            String name = invocation.getArgument(0);
            Step step = new Step();
            step.setType(Step.TypeEnum.RULESET);
            step.setMappings(new ArrayList<>());
            step.setRuleSet(new RuleSetStep().name(name));
            storage.getRuleSetStepMap().put(name, step);
            storage.getRuleSetIsUpdated().put(name, false);
            storage.getRuleSetIsActionUpdated().put(name, false);
            return null;
        }).when(processing).findOrAddRuleSet(any(), any());
        doNothing().when(processing).callPutRuleSet(any(), any());
        doNothing().when(processing).addActionToRuleSet(any(), any());
        return processing;
    }

    private static MapStorage remoteStorage() {
        MapStorage storage = new MapStorage();
        storage.setBaseIp("viya.example");
        storage.setProtocol("http");
        storage.setParentFolderUri("/folders/folders/@myFolder");
        return storage;
    }

    private static CalculatedItemDO item(String name, String nodeId, String type, String expression) {
        CalculatedItemDO item = new CalculatedItemDO();
        item.setName(name);
        item.setNodeId(nodeId);
        item.setId(nodeId + "-id");
        item.setCalculatedItemType(type);
        item.setExpression(expression);
        return item;
    }

    private static Signature signature(String name, String dataType, String direction) {
        Signature signature = new Signature();
        signature.setName(name);
        signature.setDataType(dataType);
        signature.setDirection(direction);
        return signature;
    }

    private static Batch batch(String content) throws Exception {
        return (Batch) JAXBContext.newInstance(Batch.class).createUnmarshaller()
            .unmarshal(new StringReader("<Batch><LogicalUnit>" + content + "</LogicalUnit></Batch>"));
    }
}
