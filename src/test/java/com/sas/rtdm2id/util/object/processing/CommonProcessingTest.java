package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.CodeFile;
import com.sas.rtdm2id.model.id.decision.CodeFileCollection;
import com.sas.rtdm2id.model.id.decision.ConditionBranch;
import com.sas.rtdm2id.model.id.decision.CustomObjectStep;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.decision.SignatureTerm;
import com.sas.rtdm2id.model.id.globals.GlobalVariable;
import com.sas.rtdm2id.model.id.globals.GlobalVariableCollection;
import com.sas.rtdm2id.model.id.globals.Item;
import com.sas.rtdm2id.model.rtdm.extension.VarRef;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import org.mockito.ArgumentCaptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sas.rtdm2id.model.id.decision.Mapping;
import com.sas.rtdm2id.model.id.decision.RuleSetStep;
import com.sas.rtdm2id.model.id.rules.Action;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.Signature;
import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO;
import com.sas.rtdm2id.model.rtdm.FlowDO;
import com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO;
import com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO;
import com.sas.rtdm2id.model.rtdm.StartNodeDataDO;
import com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO;
import com.sas.rtdm2id.util.model.IBVariableDO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import java.io.File;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.STRING_CONSTANT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class CommonProcessingTest {

    @Test
    void computesANodesCalculatedItemsAndTheirDependenciesInOneRuleSetAndReusesAnIdenticalOne() throws Exception {
        for (boolean links : List.of(false, true)) {
            RestTemplate template = mock(RestTemplate.class);
            MapStorage storage = groupedStorage();
            storage.setUseCrossBranchLinks(links);
            TreeUtil tree = new TreeUtil();
            tree.getNodeIdInputNodesMap().put("consumer", "left,right");
            CommonProcessing processing = groupedProcessing(template, storage, tree);

            processing.createRuleSets(groupedBatch());
            // No RuleSet is created per calculated item
            assertTrue(storage.getRuleSetStepMap().isEmpty());
            verifyNoInteractions(template);
            stubRuleSetCreation(template);
            List<Step> steps = new ArrayList<>();
            // "second" is computed from "first", which the node also uses directly
            assertTrue(processing.checkForCalcVariable("calculatedDataItems.second", "double", steps, null, "second", "consumer"));
            assertTrue(processing.checkForCalcVariable("calculatedDataItems.first", "double", steps, null, "first", "consumer"));
            assertEquals(1, steps.size());
            assertEquals(links, steps.get(0).getLinkLabel() != null);
            processing.writeCalculatedNodeGroups();

            ArgumentCaptor<HttpEntity<RuleSet>> signatures = ArgumentCaptor.forClass(HttpEntity.class);
            ArgumentCaptor<HttpEntity<Rule>> rules = ArgumentCaptor.forClass(HttpEntity.class);
            verify(template).exchange(eq(URI.create("http://viya.example/businessRules/ruleSets/group-id")),
                eq(HttpMethod.PUT), signatures.capture(), eq(RuleSet.class));
            verify(template).exchange(eq(URI.create("http://viya.example/businessRules/ruleSets/group-id/rules")),
                eq(HttpMethod.POST), rules.capture(), eq(Rule.class));
            RuleSet expected = signatures.getValue().getBody();
            Rule rule = rules.getValue().getBody();
            assertEquals(List.of("CalculatedDataItemfirst=number+1", "CalculatedDataItemsecond=CalculatedDataItemfirst+1"),
                rule.getActions().stream().map(Action::getExpression).toList());
            assertEquals(List.of("CalculatedDataItemfirst output decimal", "CalculatedDataItemsecond output decimal",
                    "number input decimal"),
                expected.getSignature().stream().map(term -> term.getName() + " " + term.getDirection() + " "
                    + term.getDataType()).toList());
            assertEquals(List.of("CalculatedDataItemfirst->CalculatedDataItemfirst output",
                    "CalculatedDataItemsecond->CalculatedDataItemsecond output", "number->Requestnumber input"),
                steps.get(0).getMappings().stream().map(mapping -> mapping.getStepTermName() + "->"
                    + mapping.getTargetDecisionTermName() + " " + mapping.getDirection().getValue()).toList());
            assertSame(steps.get(0), storage.getRuleSetStepMap().get(expected.getName()));
            assertEquals(expected.getName(), steps.get(0).getRuleSet().getName());

            // A second migration finds the identical RuleSet and reuses it without writing
            storage.clearListsAndMaps();
            storage.setBaseIp("viya.example");
            storage.setProtocol("http");
            CodeFile existing = new CodeFile();
            existing.setId("group-id");
            CodeFileCollection found = new CodeFileCollection();
            found.setCount(1L);
            found.setItems(List.of(existing));
            when(template.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
                .thenReturn(ResponseEntity.ok(found));
            when(template.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RuleSet.class)))
                .thenReturn(ResponseEntity.ok(expected));
            when(template.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok(new ObjectMapper()
                    .writeValueAsString(Map.of("count", 1, "items", List.of(rule)))));
            processing.createRuleSets(groupedBatch());
            List<Step> rerun = new ArrayList<>();
            processing.checkForCalcVariable("calculatedDataItems.second", "double", rerun, null, "second", "consumer");
            processing.writeCalculatedNodeGroups();

            assertEquals("group-id", rerun.get(0).getRuleSet().getId());
            assertEquals(3, rerun.get(0).getMappings().size());
            verify(template).postForEntity(any(URI.class), any(HttpEntity.class), eq(RuleSet.class));
            verify(template).exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(RuleSet.class));
            verify(template).exchange(any(URI.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(Rule.class));
        }
    }

    @Test
    void propagatesGroupedWriteFailuresAndRejectsPartialArtifactsOnRetry() throws Exception {
        for (String failure : List.of("shell", "signature", "actions")) {
            RestTemplate template = mock(RestTemplate.class);
            MapStorage storage = groupedStorage();
            CommonProcessing processing = groupedProcessing(template, storage, new TreeUtil());
            CodeFileCollection empty = new CodeFileCollection();
            empty.setCount(0L);
            when(template.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
                .thenReturn(ResponseEntity.ok(empty));
            RuleSet shell = new RuleSet();
            shell.setId("partial-id");
            shell.setModifiedTimeStamp(new Date());
            when(template.postForEntity(any(URI.class), any(HttpEntity.class), eq(RuleSet.class)))
                .thenReturn(new ResponseEntity<>(shell, "shell".equals(failure) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED));
            when(template.exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(RuleSet.class)))
                .thenReturn(new ResponseEntity<>(shell, "signature".equals(failure) ? HttpStatus.BAD_REQUEST : HttpStatus.OK));
            when(template.exchange(any(URI.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(Rule.class)))
                .thenReturn(new ResponseEntity<>(new Rule(), HttpStatus.BAD_REQUEST));
            processing.createRuleSets(groupedBatch());
            processing.checkForCalcVariable("calculatedDataItems.second", "double", new ArrayList<>(), null, "second", "consumer");
            assertThrows(IllegalStateException.class, processing::writeCalculatedNodeGroups);
            assertTrue(storage.getRuleSetStepMap().isEmpty());

            clearInvocations(template);
            CodeFile file = new CodeFile();
            file.setId("partial-id");
            CodeFileCollection found = new CodeFileCollection();
            found.setCount(1L);
            found.setItems(List.of(file));
            when(template.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
                .thenReturn(ResponseEntity.ok(found));
            when(template.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RuleSet.class)))
                .thenReturn(ResponseEntity.ok(shell));

            assertThrows(IllegalStateException.class, processing::writeCalculatedNodeGroups);
            verify(template, never()).postForEntity(any(URI.class), any(HttpEntity.class), eq(RuleSet.class));
            verify(template, never()).exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(RuleSet.class));
            verify(template, never()).exchange(any(URI.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(Rule.class));
        }
    }

    private static MapStorage groupedStorage() {
        MapStorage storage = new MapStorage();
        storage.setBaseIp("viya.example");
        storage.setProtocol("http");
        storage.setParentFolderUri("/folders/folders/@myFolder");
        return storage;
    }

    @Test
    void rejectsIncompleteOrReorderedGroupedActionsWithoutWrites() throws Exception {
        for (String invalid : List.of("empty", "partial", "reordered", "changed", "tracking")) {
            RestTemplate template = mock(RestTemplate.class);
            MapStorage storage = groupedStorage();
            RuleSet expected = new RuleSet();
            expected.setName("grouped");
            expected.setDescription(CalculatedRuleSetWriter.DESCRIPTION);
            expected.setRuleSetType("assignment");
            Signature term = new Signature();
            term.setName("result");
            term.setDataType("decimal");
            term.setDirection("output");
            expected.setSignature(List.of(term));
            Rule rule = new Rule();
            rule.setName("ordered");
            rule.setConditional("if");
            rule.setRuleFiredTrackingEnabled(false);
            Action first = new Action();
            first.setType("complex");
            first.setExpression("result=1");
            Action second = new Action();
            second.setType("complex");
            second.setExpression("result=result+1");
            rule.setActions(List.of(first, second));
            ObjectMapper mapper = new ObjectMapper();
            Rule actual = mapper.readValue(mapper.writeValueAsString(rule), rule.getClass());
            if ("partial".equals(invalid)) {
                actual.setActions(List.of(first));
            } else if ("reordered".equals(invalid)) {
                actual.setActions(List.of(second, first));
            } else if ("changed".equals(invalid)) {
                actual.getActions().get(0).setExpression("result=99");
            } else if ("tracking".equals(invalid)) {
                actual.setRuleFiredTrackingEnabled(true);
            }
            CodeFile file = new CodeFile();
            file.setId("existing");
            CodeFileCollection found = new CodeFileCollection();
            found.setCount(1L);
            found.setItems(List.of(file));
            when(template.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
                .thenReturn(ResponseEntity.ok(found));
            when(template.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(RuleSet.class)))
                .thenReturn(ResponseEntity.ok(expected));
            when(template.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(ResponseEntity.ok(mapper.writeValueAsString(Map.of("count", "empty".equals(invalid) ? 0 : 1,
                    "items", "empty".equals(invalid) ? List.of() : List.of(actual)))));

            assertThrows(IllegalStateException.class,
                () -> new CalculatedRuleSetWriter(template, storage).write(expected, rule, new Step()));
            assertTrue(storage.getRuleSetStepMap().isEmpty());
            verify(template, never()).postForEntity(any(URI.class), any(HttpEntity.class), eq(expected.getClass()));
            verify(template, never()).exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(expected.getClass()));
            verify(template, never()).exchange(any(URI.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(rule.getClass()));
        }
    }

    private static CommonProcessing groupedProcessing(RestTemplate template, MapStorage storage, TreeUtil tree) {
        CommonProcessing processing = spy(new CommonProcessing(template, storage, tree));
        setField(processing, "consolidateCalculatedRuleSets", true);
        doNothing().when(processing).findOrAddRuleSet(any(), any());
        return processing;
    }

    private static Batch groupedBatch() throws Exception {
        return calculatedBatch("""
            <StartNodeDataDO><NodeId>start</NodeId><Event><RequestVars><IBVariableDO>
              <Name>number</Name><TypeDescription>double</TypeDescription>
            </IBVariableDO></RequestVars></Event></StartNodeDataDO>
            <FlowDO><CalculatedDataItemList>
              <CalculatedItemDO><Name>second</Name><NodeId>calc2</NodeId><CalculatedItemType>4</CalculatedItemType>
                <Expression>&lt;&lt;calculatedDataItems.first&gt;&gt;+1</Expression></CalculatedItemDO>
              <CalculatedItemDO><Name>first</Name><NodeId>calc1</NodeId><CalculatedItemType>4</CalculatedItemType>
                <Expression>&lt;&lt;events.number&gt;&gt;+1</Expression></CalculatedItemDO>
            </CalculatedDataItemList></FlowDO>
            """ + calculatedConsumerXml("consumer", "first", "second"));
    }

    @Test
    void everyNodeComputesTheCalculatedItemsItUsesInDependencyOrder() throws Exception {
        RestTemplate template = mock(RestTemplate.class);
        MapStorage storage = groupedStorage();
        CommonProcessing processing = groupedProcessing(template, storage, new TreeUtil());
        processing.createRuleSets(calculatedBatch("""
            <FlowDO><CalculatedDataItemList>
              <CalculatedItemDO><Name>third</Name><NodeId>calc3</NodeId><CalculatedItemType>4</CalculatedItemType>
                <Expression>&lt;&lt;calculatedDataItems.second&gt;&gt; + &lt;&lt;calculatedDataItems.first&gt;&gt;</Expression></CalculatedItemDO>
              <CalculatedItemDO><Name>second</Name><NodeId>calc2</NodeId><CalculatedItemType>4</CalculatedItemType>
                <Expression>&lt;&lt;calculatedDataItems.first&gt;&gt; + 1</Expression></CalculatedItemDO>
              <CalculatedItemDO><Name>first</Name><NodeId>calc1</NodeId><CalculatedItemType>4</CalculatedItemType>
                <Expression>1</Expression></CalculatedItemDO>
              <CalculatedItemDO><Name>loopA</Name><NodeId>calc4</NodeId><CalculatedItemType>4</CalculatedItemType>
                <Expression>&lt;&lt;calculatedDataItems.loopB&gt;&gt;</Expression></CalculatedItemDO>
              <CalculatedItemDO><Name>loopB</Name><NodeId>calc5</NodeId><CalculatedItemType>4</CalculatedItemType>
                <Expression>&lt;&lt;calculatedDataItems.loopA&gt;&gt; + &lt;&lt;calculatedDataItems.undefined&gt;&gt;</Expression></CalculatedItemDO>
            </CalculatedDataItemList></FlowDO>
            """));
        List<Step> consumer = new ArrayList<>();
        List<Step> other = new ArrayList<>();
        processing.checkForCalcVariable("calculatedDataItems.third", "double", consumer, null, "third", "consumer");
        // An item another node also uses is computed again in that node, as RTDM evaluates it where it is used
        processing.checkForCalcVariable("calculatedDataItems.first", "double", other, null, "first", "other");
        processing.checkForCalcVariable("calculatedDataItems.loopA", "double", other, null, "loopA", "other");
        processing.checkForCalcVariable("calculatedDataItems.undefined", "double", other, null, "undefined", "other");

        assertEquals(1, consumer.size());
        assertEquals(1, other.size());
        assertEquals(List.of("first", "second", "third"), List.copyOf(storage.getCalculatedNodeGroups().get("consumer").items()));
        assertEquals(List.of("first", "loopB", "loopA"), List.copyOf(storage.getCalculatedNodeGroups().get("other").items()));
        verifyNoInteractions(template);

        stubRuleSetCreation(template);
        processing.writeCalculatedNodeGroups();

        ArgumentCaptor<HttpEntity<RuleSet>> signatures = ArgumentCaptor.forClass(HttpEntity.class);
        ArgumentCaptor<HttpEntity<Rule>> rules = ArgumentCaptor.forClass(HttpEntity.class);
        verify(template, atLeastOnce()).exchange(any(URI.class), eq(HttpMethod.PUT), signatures.capture(), eq(RuleSet.class));
        verify(template, atLeastOnce()).exchange(any(URI.class), eq(HttpMethod.POST), rules.capture(), eq(Rule.class));
        assertEquals(List.of("CalculatedDataItemfirst=1", "CalculatedDataItemsecond=CalculatedDataItemfirst + 1",
                "CalculatedDataItemthird=CalculatedDataItemsecond + CalculatedDataItemfirst"),
            rules.getAllValues().get(0).getBody().getActions().stream().map(Action::getExpression).toList());
        assertEquals(List.of("CalculatedDataItemfirst=1",
                "CalculatedDataItemloopB=CalculatedDataItemloopA + undefined", "CalculatedDataItemloopA=CalculatedDataItemloopB"),
            rules.getAllValues().get(1).getBody().getActions().stream().map(Action::getExpression).toList());
        // In a cyclic definition the item is read before it is assigned, so the step passes its current value in
        assertEquals(List.of("CalculatedDataItemfirst output", "CalculatedDataItemloopB output",
                "CalculatedDataItemloopA inOut", "undefined input"),
            signatures.getAllValues().get(1).getBody().getSignature().stream()
                .map(term -> term.getName() + " " + term.getDirection()).toList());
    }

    private static Batch calculatedBatch(String content) throws Exception {
        return (Batch) JAXBContext
            .newInstance(Batch.class).createUnmarshaller()
            .unmarshal(new StringReader("<Batch><LogicalUnit>" + content + "</LogicalUnit></Batch>"));
    }

    private static String calculatedConsumerXml(String nodeId, String... names) {
        return "<ProcessNodeDataDO><NodeId>" + nodeId + "</NodeId><NodeName>" + nodeId
            + "</NodeName><Process><Name>process</Name><ProcessTypeDescription>Custom</ProcessTypeDescription><InputVariableList>"
            + calculatedVariablesXml(names) + "</InputVariableList></Process></ProcessNodeDataDO>";
    }

    @Test
    void reusesGroupedRuleSetFromViyaSummaryAndFullRuleRepresentations() throws Exception {
        RestTemplate template = mock(RestTemplate.class);
        MapStorage storage = groupedStorage();
        RuleSet expected = new RuleSet();
        expected.setName("grouped");
        expected.setDescription(CalculatedRuleSetWriter.DESCRIPTION);
        expected.setRuleSetType("assignment");
        Signature term = new Signature();
        term.setName("result");
        term.setDataType("decimal");
        term.setDirection("output");
        expected.setSignature(List.of(term));
        Rule rule = new Rule();
        rule.setName("ordered");
        rule.setConditional("if");
        rule.setRuleFiredTrackingEnabled(false);
        Action action = new Action();
        action.setType("complex");
        action.setExpression("result=1");
        rule.setActions(List.of(action));
        CodeFile file = new CodeFile();
        file.setId("existing");
        CodeFileCollection found = new CodeFileCollection();
        found.setCount(1L);
        found.setItems(List.of(file));
        when(template.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
            .thenReturn(ResponseEntity.ok(found));
        when(template.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class),
                eq(RuleSet.class)))
            .thenReturn(ResponseEntity.ok(expected));
        when(template.exchange(eq(URI.create("http://viya.example/businessRules/ruleSets/existing/rules?limit=2")),
                eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
            .thenReturn(ResponseEntity.ok("""
                {"count":1,"items":[{"id":"rule-1","name":"ordered","links":[{"rel":"self"}]}]}"""));
        when(template.exchange(eq(URI.create("http://viya.example/businessRules/ruleSets/existing/rules/rule-1")),
                eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
            .thenReturn(ResponseEntity.ok("""
                {"id":"rule-1","name":"ordered","conditional":"if","ruleSetId":"existing","links":[],
                 "actions":[{"expression":"result=1","status":"valid"}]}"""));
        Step step = new Step();

        new CalculatedRuleSetWriter(template, storage).write(expected, rule, step);

        assertSame(step, storage.getRuleSetStepMap().get("grouped"));
        assertEquals("existing", step.getRuleSet().getId());
        verify(template, never()).exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(expected.getClass()));
        verify(template, never()).exchange(any(URI.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(rule.getClass()));
    }

    private static String calculatedVariablesXml(String... names) {
        StringBuilder variables = new StringBuilder();
        for (String name : names) {
            variables.append("<IBVariableDO><Name>").append(name)
                .append("</Name><TypeDescription>double</TypeDescription><Value><ValueTypeVarInfoDO><VarInfoId>")
                .append("calculatedDataItems.").append(name)
                .append("</VarInfoId></ValueTypeVarInfoDO></Value></IBVariableDO>");
        }
        return variables.toString();
    }

    @Test
    void typesCalculatedAssignmentSourcesIndependentlyOfListDestinations() {
        MapStorage storage = new MapStorage();
        CommonProcessing processing = spy(
            new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
        doNothing().when(processing).findOrAddRuleSet(any(), any());
        doNothing().when(processing).callPutRuleSet(any(), any());
        doNothing().when(processing).addActionToRuleSet(any(), any());
        Batch batch = new Batch();
        batch.setLogicalUnit(new Batch.LogicalUnit());
        FlowDO flow = mock(
            FlowDO.class, RETURNS_DEEP_STUBS);
        batch.getLogicalUnit().setFlowDO(flow);
        List<CalculatedItemDO> items = new ArrayList<>();
        String[][] cases = {
            {"isCodebtorInvolved", "5", "boolean list", "boolean"},
            {"022_EmpRoutYLFlgLvCodebtor", "3", "boolean list", "decimal"},
            {"CV_manualIncomeChkResultCd_Codebtor", "0", "double list", "string"}
        };
        for (String[] itemCase : cases) {
            CalculatedItemDO item =
                new CalculatedItemDO();
            item.setName(itemCase[0]);
            item.setCalculatedItemType(itemCase[1]);
            item.setNodeId("source-node");
            item.setExpression("Missing");
            items.add(item);
            String name = processing.makeCalculatedDataItemRuleSetName(item);
            Step producer = new Step();
            producer.setRuleSet(new RuleSetStep().name(name));
            storage.getRuleSetStepMap().put(name, producer);
            storage.getRuleSetIsUpdated().put(name, true);
            storage.getRuleSetIsActionUpdated().put(name, true);
        }
        when(flow.getCalculatedDataItemList().getCalculatedItemDOs()).thenReturn(items);
        processing.createRuleSets(batch);

        for (String[] itemCase : cases) {
            IBVariableDO input = new IBVariableDO();
            input.setName("assignedValue");
            input.setTypeDescription(itemCase[2]);
            input.setValue(new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value());
            ValueTypeVarInfoDO reference = new ValueTypeVarInfoDO();
            reference.setVarInfoId("calculatedDataItems." + itemCase[0]);
            reference.setVarInfoSource("CalculatedDataItem");
            reference.setVarName(itemCase[0]);
            reference.setType("0".equals(itemCase[1]) ? "Char" : "Numeric");
            input.getValue().setValueTypeVarInfoDO(reference);
            List<Signature> signatures = new ArrayList<>();
            List<Action> actions = new ArrayList<>();

            processing.addAction(input, actions, signatures, new Step(), new Decision());

            assertEquals(1, signatures.size());
            assertEquals(itemCase[3], signatures.get(0).getDataType(), itemCase[0]);
            assertEquals(processing.sanitizeAndReduceVariableName("CalculatedDataItem." + itemCase[0]),
                signatures.get(0).getName());
            assertEquals("input", signatures.get(0).getDirection());
        }
        storage.clearListsAndMaps();
        assertTrue(storage.getCalculatedVariableDataTypeMap().isEmpty());
    }

    @Test
    void typesCalculatedSourcesInHabofProcessInputRuleSets() throws Exception {
        String extractPath = System.getProperty("calculatedExtract");
        assumeTrue(extractPath != null,
            "Set -DcalculatedExtract to DC_getDataForHabof_v7.xml");
        Batch batch = (Batch)
            JAXBContext.newInstance(Batch.class)
                .createUnmarshaller().unmarshal(new File(extractPath));
        MapStorage storage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing processing = spy(new CommonProcessing(restTemplate, storage, new TreeUtil()));
        doAnswer(invocation -> {
            String name = invocation.getArgument(0);
            Step step = new Step();
            step.setRuleSet(new RuleSetStep().name(name));
            storage.getRuleSetStepMap().put(name, step);
            storage.getRuleSetIsUpdated().put(name, false);
            storage.getRuleSetIsActionUpdated().put(name, false);
            return null;
        }).when(processing).findOrAddRuleSet(any(), any());
        Map<String, RuleSet> written = new HashMap<>();
        doAnswer(invocation -> {
            RuleSetStep step = invocation.getArgument(0);
            written.put(step.getName(), invocation.getArgument(1));
            return null;
        }).when(processing).callPutRuleSet(any(), any());
        doNothing().when(processing).addActionToRuleSet(any(), any());
        processing.createRuleSets(batch);
        ProcessNodeConverter converter = new ProcessNodeConverter(restTemplate, processing, storage);
        Set<String> reportedNodes = Set.of("CBEGXXEQRNENQ3LX", "GHDAXTVZVZBQRGJN",
            "FCAAJQTE5ZAKU1XY", "AGFG1DSUOJEYVTZ3", "BGHCYXS3ANF1XPMG", "ABEFUEQGPFESVCGL",
            "AGCAMGKHYFAJW14V", "HHHDIQ0NARECRVYZ", "ACGF5I44QFBMQAAP", "GEFCK2WTKBCJVY0Z");
        int checkedNodes = 0;
        for (ProcessNodeDataDO node : batch.getLogicalUnit().getProcessNodeDataDOs()) {
            if (!reportedNodes.contains(node.getNodeId())) {
                continue;
            }
            String name = processing.makeProcessNodeRuleSetName(node) + "_input_values";
            Step step = storage.getRuleSetStepMap().get(name);

            converter.updateRuleSet(node.getProcess().getInputVariableList(), step, new Decision());

            for (ProcessNodeDataDO.Process.InputVariableList.IBVariableDO input
                    : node.getProcess().getInputVariableList().getIbVariableDOs()) {
                ValueTypeVarInfoDO reference =
                    input.getValue() == null ? null : input.getValue().getValueTypeVarInfoDO();
                if (reference == null || reference.getVarInfoId() == null
                        || !reference.getVarInfoId().startsWith("calculatedDataItems.")) {
                    continue;
                }
                String sourceName = processing.sanitizeAndReduceVariableName("CalculatedDataItem."
                    + reference.getVarInfoId().substring("calculatedDataItems.".length()));
                Signature source = written.get(name).getSignature().stream()
                    .filter(item -> sourceName.equals(item.getName())).findFirst().orElseThrow();
                assertEquals(storage.getCalculatedVariableDataTypeMap().get(reference.getVarInfoId()),
                    source.getDataType(), name + ": " + sourceName);
                assertEquals("input", source.getDirection());
            }
            checkedNodes++;
        }
        assertEquals(reportedNodes.size(), checkedNodes);
        RuleSet codebtor = written.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith("CV_incomeCheckCodebtorFlg_"))
            .findFirst().orElseThrow().getValue();
        for (String suffix : List.of("CV_manualIncomeChkResultCd_Codebtor", "CV_automaticIncomeChkResult_Codebtor")) {
            String sourceName = processing.sanitizeAndReduceVariableName(suffix);
            assertTrue(codebtor.getSignature().stream().anyMatch(item -> sourceName.equals(item.getName())
                && "string".equals(item.getDataType()) && "input".equals(item.getDirection())));
        }
        verifyNoInteractions(restTemplate);
    }

    // Shapes from customer campaigns: process outputs are referenced as "<NodeName>.<ProcessName><var>", and outputs
    // linked to an identifier are what later nodes read through "identifiers.<name>"
    private static final String NODE_OUTPUTS_XML = """
        <Batch><LogicalUnit>
          <StartNodeDataDO><NodeId>start</NodeId><Event><RequestVars>
            <IBVariableDO><Name>interactionID</Name><TypeDescription>string</TypeDescription>
              <Identifier><Id>interactionId</Id><Name>interactionId</Name></Identifier></IBVariableDO>
          </RequestVars></Event></StartNodeDataDO>
          <ProcessNodeDataDO><NodeId>p1</NodeId><NodeName>Expor DataGrid</NodeName><Process>
            <Name>Groovy_Exposicao_v2.0.1</Name><OutputVariableList>
              <IBVariableDO><Name>cdRetorno</Name><TypeDescription>integer</TypeDescription>
                <Identifier><Id>cdRetorno</Id><Name>cdRetorno</Name></Identifier></IBVariableDO>
              <IBVariableDO><Name>dsRetorno</Name><TypeDescription>string</TypeDescription></IBVariableDO>
              <IBVariableDO><Name>tid</Name><TypeDescription>string</TypeDescription>
                <Identifier><Id>interactionId</Id><Name>interactionId</Name></Identifier></IBVariableDO>
            </OutputVariableList></Process></ProcessNodeDataDO>
        </LogicalUnit></Batch>
        """;

    @Test
    void writesProcessOutputsUnderTheNameLaterNodesReadThemBy() throws Exception {
        MapStorage storage = new MapStorage();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil());
        Batch batch = (Batch) JAXBContext
            .newInstance(Batch.class).createUnmarshaller()
            .unmarshal(new StringReader(NODE_OUTPUTS_XML));
        processing.registerNodeOutputs(batch);
        Step step = new Step();
        Decision decision = new Decision();

        processing.addProducedOutput("Expor DataGrid.Groovy_Exposicao_v2.0.1cdRetorno", "cdRetorno", "integer", step, decision);
        processing.addProducedOutput("Expor DataGrid.Groovy_Exposicao_v2.0.1dsRetorno", "dsRetorno", "string", step, decision);

        // Linked to an identifier: written to the identifier, which is how "identifiers.cdRetorno" is read
        assertEquals("IdentifiercdRetorno", step.getMappings().get(0).getTargetDecisionTermName());
        assertEquals("cdRetorno", step.getMappings().get(0).getStepTermName());
        assertEquals(Mapping.DirectionEnum.OUTPUT, step.getMappings().get(0).getDirection());
        // Not linked: written under the qualified output id, which is how direct references read it
        assertEquals("ExporDataGridGroovy_Exposicao_v201dsRetorno", step.getMappings().get(1).getTargetDecisionTermName());
        assertTrue(decision.getSignature().stream().anyMatch(term -> "ExporDataGridGroovy_Exposicao_v201dsRetorno".equals(term.getName())
            && SignatureTerm.DataTypeEnum.STRING.equals(term.getDataType())));
        assertEquals("Identifier.cdRetorno", processing.nodeOutputReference("Expor DataGrid.Groovy_Exposicao_v2.0.1cdRetorno"));
        assertEquals("Identifier.cdRetorno", CommonProcessing.sourceQualifiedName("identifiers.cdRetorno"));

        // An identifier fed by a Start request variable lives in the request term: the caller supplies the initial
        // value, a linked process output overwrites it, and readers of identifiers.interactionId read it
        processing.addProducedOutput("Expor DataGrid.Groovy_Exposicao_v2.0.1tid", "tid", "string", step, decision);
        assertEquals("RequestinteractionID", step.getMappings().get(2).getTargetDecisionTermName());
        assertEquals("Request.interactionID", processing.nodeOutputReference("identifiers.interactionId"));
    }

    @Test
    void readsRulesGroupOutputsUnderTheNodeNameAlone() throws Exception {
        // Customer shape: <<RF_Check.DecisionCd>> reads an output of the rules group process RF_Check_v4 in node RF_Check
        MapStorage storage = new MapStorage();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil());
        Batch batch = (Batch) JAXBContext.newInstance(Batch.class).createUnmarshaller().unmarshal(new StringReader("""
            <Batch><LogicalUnit>
              <ProcessNodeDataDO><NodeId>rules</NodeId><NodeName>RF_Check</NodeName><Process><Name>RF_Check_v4</Name>
                <ProcessTypeDescription>rules group</ProcessTypeDescription><OutputVariableList><IBVariableDO>
                <Name>DecisionCd</Name><TypeDescription>string</TypeDescription></IBVariableDO></OutputVariableList>
              </Process></ProcessNodeDataDO>
              <ProcessNodeDataDO><NodeId>java</NodeId><NodeName>Calc</NodeName><Process><Name>SP_calc</Name>
                <ProcessTypeDescription>Java code</ProcessTypeDescription><OutputVariableList><IBVariableDO>
                <Name>DecisionCd</Name><TypeDescription>string</TypeDescription></IBVariableDO></OutputVariableList>
              </Process></ProcessNodeDataDO>
            </LogicalUnit></Batch>
            """));
        processing.registerNodeOutputs(batch);

        assertEquals("RF_Check.DecisionCd", processing.nodeOutputReference("RF_Check.DecisionCd"));
        assertEquals("string", processing.nodeOutputDataType("RF_Check.DecisionCd"));
        assertNull(processing.nodeOutputReference("RF_Check.RF_Check_v4DecisionCd"));
        assertEquals("Calc.SP_calcDecisionCd", processing.nodeOutputReference("Calc.SP_calcDecisionCd"));
        assertEquals("RF_CheckDecisionCd", processing.nodeOutputTermName(CommonProcessing.nodeOutputId("RF_Check",
            CommonProcessing.outputReferencePrefix(batch.getLogicalUnit().getProcessNodeDataDOs().get(0).getProcess()), "DecisionCd")));
    }

    @Test
    void declaresReaderOfProcessOutputWithProducersType() throws Exception {
        // Customer shape: a Split on a string process output whose VarRefDO has no metadata and a default Numeric type
        MapStorage storage = new MapStorage();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil());
        Batch batch = (Batch) JAXBContext
            .newInstance(Batch.class).createUnmarshaller()
            .unmarshal(new StringReader(NODE_OUTPUTS_XML));
        processing.registerNodeOutputs(batch);
        Decision decision = new Decision();
        VarRef reader = new VarRef();
        reader.setVarInfoId("Expor DataGrid.Groovy_Exposicao_v2.0.1dsRetorno");
        reader.setVarName("dsRetorno");
        reader.setType("Numeric");

        processing.addNewSignatureItem(reader, "none", decision);

        assertEquals("string", processing.nodeOutputDataType(reader.getVarInfoId()));
        assertEquals(1, decision.getSignature().size());
        assertEquals("ExporDataGridGroovy_Exposicao_v201dsRetorno", decision.getSignature().get(0).getName());
        assertEquals(SignatureTerm.DataTypeEnum.STRING, decision.getSignature().get(0).getDataType());
        assertNull(processing.nodeOutputDataType("events.interactionID"));
    }

    @Test
    void tracksCalculatedStepsByIdentityAfterMappingsAndLabelsChange() {
        MapStorage storage = new MapStorage();
        Step step = new Step();
        storage.getCalculatedVariableRulesetNodes().add(step);
        step.setLinkLabel("shared-target");
        step.setMappings(new ArrayList<>());
        step.getMappings().add(new Mapping());

        assertTrue(storage.getCalculatedVariableRulesetNodes().contains(step));
        assertTrue(storage.getCalculatedVariableRulesetNodes().add(new Step()));
        assertEquals(2, storage.getCalculatedVariableRulesetNodes().size());
        storage.clearListsAndMaps();
        assertTrue(storage.getCalculatedVariableRulesetNodes().isEmpty());
    }

    @Test
    void usesDeclaredRequestTypesForCalculatedExpressionInputs() {
        for (boolean treatmentCampaign : List.of(false, true)) {
            MapStorage storage = new MapStorage();
            CommonProcessing processing = spy(
                new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
            setField(processing, "consolidateCalculatedRuleSets", false);
            Batch batch = new Batch();
            Batch.LogicalUnit unit = new Batch.LogicalUnit();
            batch.setLogicalUnit(unit);
            StartNodeDataDO start = new StartNodeDataDO();
            unit.setStartNodeDataDO(start);
            List<StartNodeDataDO.Event.RequestVars.IBVariableDO> variables;
            if (treatmentCampaign) {
                start.setInputVariables(new StartNodeDataDO.InputVariables());
                variables = start.getInputVariables().getIbVariableDOs();
            } else {
                start.setEvent(new StartNodeDataDO.Event());
                start.getEvent().setRequestVars(new StartNodeDataDO.Event.RequestVars());
                variables = start.getEvent().getRequestVars().getIbVariableDOs();
            }
            for (String name : List.of("Reference Event Name", "Offer Type", "Customer Age")) {
                StartNodeDataDO.Event.RequestVars.IBVariableDO variable =
                    new StartNodeDataDO.Event.RequestVars.IBVariableDO();
                variable.setName(name);
                variable.setTypeDescription("Customer Age".equals(name) ? "integer" : "string");
                variables.add(variable);
            }
            CalculatedItemDO item =
                new CalculatedItemDO();
            item.setName("OfferTypeVar");
            item.setNodeId("HEHE3MFYENF1RNHW");
            item.setCalculatedItemType("0");
            item.setExpression("IIF(<<events.Reference Event Name>> EQ 'Postpaid_Post2Pree_PS_Offers'"
                + " & <<events.Offer Type>> EQ 'AutoReg' & <<inputVariables.Customer Age>> > 18"
                + " & MISSING(<<events.Unknown Input>>) & <<globals.GV_STATUS_MSG_SUCCESS>> EQ 'SUCCESS'"
                + " & <<calculatedDataItems.isCodebtorInvolved>> EQ 1, 'Regular', <<events.Offer Type>>)");
            CalculatedItemDO dependency =
                new CalculatedItemDO();
            dependency.setName("isCodebtorInvolved");
            dependency.setNodeId("CBEGXXEQRNENQ3LX");
            dependency.setCalculatedItemType("5");
            dependency.setExpression("1");
            ProcessNodeDataDO globalConsumer = new ProcessNodeDataDO();
            globalConsumer.setNodeId("global-consumer");
            globalConsumer.setProcess(new ProcessNodeDataDO.Process());
            globalConsumer.getProcess().setInputVariableList(
                new ProcessNodeDataDO.Process.InputVariableList());
            ProcessNodeDataDO.Process.InputVariableList.IBVariableDO globalInput =
                new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO();
            globalInput.setName("status");
            globalInput.setTypeDescription("string");
            globalInput.setValue(new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value());
            ValueTypeVarInfoDO globalReference = new ValueTypeVarInfoDO();
            globalReference.setVarInfoId("globals.GV_STATUS_MSG_SUCCESS");
            globalReference.setType("Char");
            globalInput.getValue().setValueTypeVarInfoDO(globalReference);
            globalConsumer.getProcess().getInputVariableList().getIbVariableDOs().add(globalInput);
            unit.getProcessNodeDataDOs().add(globalConsumer);
            FlowDO flow = mock(
                FlowDO.class, RETURNS_DEEP_STUBS);
            when(flow.getCalculatedDataItemList().getCalculatedItemDOs()).thenReturn(List.of(item, dependency));
            unit.setFlowDO(flow);
            String name = processing.makeCalculatedDataItemRuleSetName(item);
            Step step = new Step();
            RuleSetStep ruleSetStep =
                new RuleSetStep();
            ruleSetStep.setName(name);
            step.setRuleSet(ruleSetStep);
            storage.getRuleSetStepMap().put(name, step);
            storage.getRuleSetIsUpdated().put(name, false);
            storage.getRuleSetIsActionUpdated().put(name, false);
            String dependencyName = processing.makeCalculatedDataItemRuleSetName(dependency);
            Step dependencyStep = new Step();
            RuleSetStep dependencyRuleSet =
                new RuleSetStep();
            dependencyRuleSet.setName(dependencyName);
            dependencyStep.setRuleSet(dependencyRuleSet);
            storage.getRuleSetStepMap().put(dependencyName, dependencyStep);
            storage.getRuleSetIsUpdated().put(dependencyName, true);
            storage.getRuleSetIsActionUpdated().put(dependencyName, true);
            doNothing().when(processing).findOrAddRuleSet(any(), any());
            doNothing().when(processing).callPutRuleSet(any(), any());
            doNothing().when(processing).addActionToRuleSet(any(), any());

            processing.createRuleSets(batch);

            ArgumentCaptor<RuleSet> captor =
                ArgumentCaptor.forClass(RuleSet.class);
            verify(processing).callPutRuleSet(eq(ruleSetStep), captor.capture());
            for (String[] expected : new String[][] {
                {"ReferenceEventName", "string"}, {"OfferType", "string"},
                {"CustomerAge", "integer"}, {"UnknownInput", "any"}, {"GV_STATUS_MSG_SUCCESS", "string"},
                {"isCodebtorInvolved", processing.getCalculatedItemDataType("5")}
            }) {
                assertTrue(captor.getValue().getSignature().stream().anyMatch(signature ->
                    expected[0].equals(signature.getName()) && expected[1].equals(signature.getDataType())
                        && "input".equals(signature.getDirection())));
                assertTrue(step.getMappings().stream().anyMatch(mapping ->
                    expected[0].equals(mapping.getStepTermName())
                        && (("GV_STATUS_MSG_SUCCESS".equals(expected[0]) ? "Global"
                            : "isCodebtorInvolved".equals(expected[0]) ? "CalculatedDataItem" : "Request") + expected[0])
                            .equals(mapping.getTargetDecisionTermName())));
            }
        }
    }

    @Test
    void typesCalculatedExpressionInputsReadFromNodeOutputsByTheirProducer() throws Exception {
        // Customer shape: TABLEROWCOUNT(<<Node.ProcessGrid>>) over the data grid a read-data process returns. The input
        // was declared "any" under a hashed name with a leading '_', which ID rejected on open ("Mapped term ... has
        // an invalid data type", "Mapping _3066298432_... is invalid").
        MapStorage storage = new MapStorage();
        CommonProcessing processing = spy(new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
        setField(processing, "consolidateCalculatedRuleSets", false);
        Batch batch = new Batch();
        Batch.LogicalUnit unit = new Batch.LogicalUnit();
        batch.setLogicalUnit(unit);
        unit.setStartNodeDataDO(new StartNodeDataDO());
        ProcessNodeDataDO reader = new ProcessNodeDataDO();
        reader.setNodeId("reader");
        reader.setNodeName("GetMatrixNode");
        ProcessNodeDataDO.Process process = new ProcessNodeDataDO.Process();
        process.setName("GetMatrixProcessName");
        process.setProcessTypeDescription("read data");
        process.setReturnDatagridFlag("true");
        process.setDatagridName("Matrix_Grid_Variable");
        process.setOutputVariableList(new ProcessNodeDataDO.Process.OutputVariableList());
        ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO column = new ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO();
        column.setName("CNT");
        // RTDM declares the columns of a returned grid as lists; they are extracted as scalars
        column.setTypeDescription("double list");
        process.getOutputVariableList().getIbVariableDOs().add(column);
        reader.setProcess(process);
        unit.getProcessNodeDataDOs().add(reader);
        CustomObjectStep codeFile = new CustomObjectStep();
        codeFile.setName("GetMatrixProcessName");
        storage.getCustomObjectStepHashMap().put((short) 1, codeFile);
        CalculatedItemDO item = new CalculatedItemDO();
        item.setName("CountVar");
        item.setNodeId("COUNTVARNODEID");
        item.setCalculatedItemType("5");
        // An assignment node's variable is read as <<Node Name.Variable>> and typed by the assignment
        Batch assignmentBatch = (Batch) JAXBContext.newInstance(Batch.class).createUnmarshaller().unmarshal(new StringReader("""
            <Batch><LogicalUnit><AssignmentNodeDataDO><NodeId>assignment</NodeId>
              <NodeName>Default Template Assignment</NodeName><Assignments><IBVariableDO><Name>Default_Template</Name>
              <TypeDescription>string</TypeDescription></IBVariableDO></Assignments></AssignmentNodeDataDO>
            </LogicalUnit></Batch>
            """));
        unit.getAssignmentNodeDataDOs().addAll(assignmentBatch.getLogicalUnit().getAssignmentNodeDataDOs());
        item.setExpression("TABLEROWCOUNT(<<GetMatrixNode.GetMatrixProcessNameMatrix_Grid_Variable>>)"
            + " + <<GetMatrixNode.GetMatrixProcessNameCNT>> + LENGTH(<<Default Template Assignment.Default_Template>>)");
        FlowDO flow = mock(FlowDO.class, RETURNS_DEEP_STUBS);
        when(flow.getCalculatedDataItemList().getCalculatedItemDOs()).thenReturn(List.of(item));
        unit.setFlowDO(flow);
        String name = processing.makeCalculatedDataItemRuleSetName(item);
        Step step = new Step();
        RuleSetStep ruleSetStep = new RuleSetStep();
        ruleSetStep.setName(name);
        step.setRuleSet(ruleSetStep);
        storage.getRuleSetStepMap().put(name, step);
        storage.getRuleSetIsUpdated().put(name, false);
        storage.getRuleSetIsActionUpdated().put(name, false);
        doNothing().when(processing).findOrAddRuleSet(any(), any());
        doNothing().when(processing).callPutRuleSet(any(), any());
        doNothing().when(processing).addActionToRuleSet(any(), any());

        processing.createRuleSets(batch);

        ArgumentCaptor<RuleSet> captor = ArgumentCaptor.forClass(RuleSet.class);
        verify(processing).callPutRuleSet(eq(ruleSetStep), captor.capture());
        Map<String, String> inputTypes = new HashMap<>();
        captor.getValue().getSignature().stream().filter(signature -> "input".equals(signature.getDirection()))
            .forEach(signature -> inputTypes.put(signature.getName(), signature.getDataType()));
        assertEquals(3, inputTypes.size(), inputTypes.toString());
        assertEquals("decimal", inputTypes.get("GetMatrixProcessNameCNT"), inputTypes.toString());
        assertEquals("string", inputTypes.get("Default_Template"), inputTypes.toString());
        String gridTerm = inputTypes.keySet().stream()
            .filter(term -> !"GetMatrixProcessNameCNT".equals(term) && !"Default_Template".equals(term))
            .findFirst().orElseThrow();
        assertEquals("dataGrid", inputTypes.get(gridTerm));
        assertTrue(Character.isLetter(gridTerm.charAt(0)) && gridTerm.length() <= 32, gridTerm);
        assertTrue(step.getMappings().stream().anyMatch(mapping -> gridTerm.equals(mapping.getStepTermName())
            && "GetMatrixNodeGetMatrixProcessNameMatrix_Grid_Variable".equals(mapping.getTargetDecisionTermName())),
            step.getMappings().toString());

        // The RuleSet computing a node's calculated items types the same inputs the same way
        RestTemplate template = mock(RestTemplate.class);
        MapStorage groupStorage = groupedStorage();
        groupStorage.getCustomObjectStepHashMap().put((short) 1, codeFile);
        CommonProcessing grouping = groupedProcessing(template, groupStorage, new TreeUtil());
        stubRuleSetCreation(template);
        grouping.createRuleSets(batch);
        List<Step> steps = new ArrayList<>();
        grouping.checkForCalcVariable("calculatedDataItems.CountVar", "double", steps, null, "CountVar", "consumer");
        grouping.writeCalculatedNodeGroups();
        ArgumentCaptor<HttpEntity<RuleSet>> signatures = ArgumentCaptor.forClass(HttpEntity.class);
        verify(template).exchange(any(URI.class), eq(HttpMethod.PUT), signatures.capture(), eq(RuleSet.class));
        Map<String, String> groupedInputTypes = new HashMap<>();
        signatures.getValue().getBody().getSignature().stream().filter(signature -> "input".equals(signature.getDirection()))
            .forEach(signature -> groupedInputTypes.put(signature.getName(), signature.getDataType()));
        assertEquals(inputTypes, groupedInputTypes);
        assertTrue(steps.get(0).getMappings().stream().anyMatch(mapping -> gridTerm.equals(mapping.getStepTermName())
            && "GetMatrixNodeGetMatrixProcessNameMatrix_Grid_Variable".equals(mapping.getTargetDecisionTermName())));
    }

    private static void stubRuleSetCreation(RestTemplate template) {
        CodeFileCollection empty = new CodeFileCollection();
        empty.setCount(0L);
        when(template.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
            .thenReturn(ResponseEntity.ok(empty));
        RuleSet created = new RuleSet();
        created.setId("group-id");
        created.setModifiedTimeStamp(new Date());
        when(template.postForEntity(any(URI.class), any(HttpEntity.class), eq(RuleSet.class)))
            .thenReturn(new ResponseEntity<>(created, HttpStatus.CREATED));
        when(template.exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(RuleSet.class)))
            .thenReturn(ResponseEntity.ok(created));
        when(template.exchange(any(URI.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(Rule.class)))
            .thenReturn(new ResponseEntity<>(new Rule(), HttpStatus.CREATED));
    }

    @Test
    void mapsAssignedProcessValuesForNewAndReusedRuleSets() {
        for (boolean existing : List.of(false, true)) {
            MapStorage storage = new MapStorage();
            CommonProcessing processing = spy(
                new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
            doNothing().when(processing).callPutRuleSet(any(), any());
            doNothing().when(processing).addActionToRuleSet(any(), any());
            String name = "process_input_values";
            storage.getRuleSetIsUpdated().put(name, existing);
            storage.getRuleSetIsActionUpdated().put(name, existing);
            Step step = new Step();
            RuleSetStep ruleSet =
                new RuleSetStep();
            ruleSet.setName(name);
            step.setRuleSet(ruleSet);
            Decision decision = new Decision();
            ProcessNodeDataDO.Process.InputVariableList inputs =
                new ProcessNodeDataDO.Process.InputVariableList();
            String[][] cases = {
                {"where expression", "whereUwDetail", "string"},
                {"where expression", "whereMultiRef", "string"},
                {"where expression", "whereOfferSaveOffers", "string"},
                {"reqSubprodCd", "00_reqSubprodCd", "string"},
                {"uwBranchFlg", "00_uwBranchFlg", "integer"},
                {"afterBudgetFlg", "afterBudgetFlg", "integer"},
                {"otpCustFlg", "01_otpCustFlgToInt", "integer"},
                {"cardproductIdIn", "00_cardproductId", "integer"},
                {"offerTypCd", null, "string"}
            };
            for (String[] inputCase : cases) {
                ProcessNodeDataDO.Process.InputVariableList.IBVariableDO input =
                    new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO();
                input.setName(inputCase[0]);
                input.setTypeDescription(inputCase[2]);
                ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value =
                    new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value();
                ValueTypeVarInfoDO reference =
                    new ValueTypeVarInfoDO();
                if (inputCase[1] == null) {
                    value.setStringValue("ELFOGADOTT");
                } else {
                    reference.setVarInfoSource("CalculatedDataItem");
                    reference.setVarInfoId("calculatedDataItems." + inputCase[1]);
                    reference.setVarName(inputCase[1]);
                    value.setValueTypeVarInfoDO(reference);
                }
                input.setValue(value);
                inputs.getIbVariableDOs().add(input);
            }

            new ProcessNodeConverter(mock(RestTemplate.class), processing, storage)
                .updateRuleSet(inputs, step, decision);

            for (String[] inputCase : cases) {
                String assignedName = processing.sanitizeVariableName(inputCase[0]);
                assertTrue(step.getMappings().stream().anyMatch(mapping ->
                    assignedName.equals(mapping.getStepTermName())
                        && assignedName.equals(mapping.getTargetDecisionTermName())
                        && Mapping.DirectionEnum.INOUT.equals(mapping.getDirection())));
                assertTrue(decision.getSignature().stream().anyMatch(term ->
                    assignedName.equals(term.getName())
                        && processing.getDatatypeOfVar(inputCase[2]).equals(term.getDataType())));
            }
            if (existing) {
                verify(processing, never()).callPutRuleSet(any(), any());
                verify(processing, never()).addActionToRuleSet(any(), any());
            } else {
                ArgumentCaptor<Rule> ruleCaptor =
                    ArgumentCaptor.forClass(Rule.class);
                verify(processing).addActionToRuleSet(eq(ruleSet), ruleCaptor.capture());
                assertTrue(ruleCaptor.getValue().getActions().stream().anyMatch(action ->
                    "offerTypCd = 'ELFOGADOTT'".equals(action.getExpression())));
            }
        }
    }

    @Test
    void mapsCalculatedItemInputsAndOutputsForNewAndExistingRuleSets() {
        for (boolean existing : List.of(false, true)) {
            MapStorage mapStorage = new MapStorage();
            CommonProcessing commonProcessing = spy(
                new CommonProcessing(mock(RestTemplate.class), mapStorage, new TreeUtil()));
            CalculatedItemDO item =
                new CalculatedItemDO();
            item.setName("BD_NEW_NUM_4");
            item.setId("BD_NEW_NUM_4");
            item.setNodeId("GCFELW2ZB3CQSIKM");
            item.setCalculatedItemType("4");
            item.setExpression("COALESCE(<<inputVariables.BD_NEW_NUM_4>>,0)");
            String ruleSetName = commonProcessing.makeCalculatedDataItemRuleSetName(item);
            Step step = new Step();
            RuleSetStep ruleSetStep =
                new RuleSetStep();
            ruleSetStep.setName(ruleSetName);
            step.setRuleSet(ruleSetStep);
            mapStorage.getRuleSetStepMap().put(ruleSetName, step);
            mapStorage.getRuleSetIsUpdated().put(ruleSetName, existing);
            mapStorage.getRuleSetIsActionUpdated().put(ruleSetName, existing);
            doNothing().when(commonProcessing).findOrAddRuleSet(any(), any());
            doNothing().when(commonProcessing).callPutRuleSet(any(), any());
            doNothing().when(commonProcessing).addActionToRuleSet(any(), any());

            commonProcessing.addRulesetForCalculatedItem(item);
            if (!existing) {
                ArgumentCaptor<RuleSet> ruleSetCaptor =
                    ArgumentCaptor.forClass(RuleSet.class);
                verify(commonProcessing).callPutRuleSet(eq(ruleSetStep), ruleSetCaptor.capture());
                assertEquals(2, ruleSetCaptor.getValue().getSignature().size());
                assertTrue(ruleSetCaptor.getValue().getSignature().stream().anyMatch(signature ->
                    "BD_NEW_NUM_4".equals(signature.getName()) && "input".equals(signature.getDirection())));
                ArgumentCaptor<Rule> ruleCaptor =
                    ArgumentCaptor.forClass(Rule.class);
                verify(commonProcessing).addActionToRuleSet(eq(ruleSetStep), ruleCaptor.capture());
                assertEquals("CalculatedDataItemBD_NEW_NUM_4=COALESCE(BD_NEW_NUM_4,0)",
                    ruleCaptor.getValue().getActions().get(0).getExpression());
                mapStorage.getRuleSetIsUpdated().put(ruleSetName, true);
            }
            commonProcessing.updateRuleSet("CalculatedDataItemBD_NEW_NUM_4", "double", step);

            assertEquals(2, step.getMappings().size());
            assertTrue(step.getMappings().stream().anyMatch(mapping ->
                "BD_NEW_NUM_4".equals(mapping.getStepTermName())
                    && "RequestBD_NEW_NUM_4".equals(mapping.getTargetDecisionTermName())
                    && Mapping.DirectionEnum.INPUT.equals(mapping.getDirection())));
            assertTrue(step.getMappings().stream().anyMatch(mapping ->
                "CalculatedDataItemBD_NEW_NUM_4".equals(mapping.getStepTermName())
                    && "CalculatedDataItemBD_NEW_NUM_4".equals(mapping.getTargetDecisionTermName())
                    && Mapping.DirectionEnum.OUTPUT.equals(mapping.getDirection())));
            if (existing) {
                verify(commonProcessing, never()).callPutRuleSet(any(), any());
                verify(commonProcessing, never()).addActionToRuleSet(any(), any());
            }
        }
    }

    @Test
    void createsProcessInputRuleSetsOnlyWhenInputsExist() {
    RestTemplate restTemplate = mock(RestTemplate.class);
    MapStorage mapStorage = new MapStorage();
    CommonProcessing commonProcessing = spy(
        new CommonProcessing(restTemplate, mapStorage, new TreeUtil()));
    doNothing().when(commonProcessing).findOrAddRuleSet(any(), any());
    Batch batch = new Batch();
    Batch.LogicalUnit logicalUnit =
        new Batch.LogicalUnit();
    FlowDO flow = mock(
        FlowDO.class, RETURNS_DEEP_STUBS);
    when(flow.getCalculatedDataItemList().getCalculatedItemDOs()).thenReturn(Collections.emptyList());
    logicalUnit.setFlowDO(flow);
    batch.setLogicalUnit(logicalUnit);
    ProcessNodeDataDO node = new ProcessNodeDataDO();
    node.setNodeId("FEDF1VQNB3HKRAYZ");
    ProcessNodeDataDO.Process process =
        new ProcessNodeDataDO.Process();
    process.setProcessTypeDescription("Java code");
    node.setProcess(process);
    logicalUnit.getProcessNodeDataDOs().add(node);

    commonProcessing.createRuleSets(batch);
    process.setInputVariableList(new ProcessNodeDataDO.Process.InputVariableList());
    commonProcessing.createRuleSets(batch);

    verify(commonProcessing, never()).findOrAddRuleSet(any(), any());
    process.getInputVariableList().getIbVariableDOs().add(
        new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO());
    commonProcessing.createRuleSets(batch);

    verify(commonProcessing).findOrAddRuleSet(
        commonProcessing.makeProcessNodeRuleSetName(node) + "_input_values", node.getNodeDescription());
    verifyNoInteractions(restTemplate);
    }

    @Test
    void rebuildsInputMappingsWhenProcessRuleSetAlreadyExists() throws Exception {
    RestTemplate restTemplate = mock(RestTemplate.class);
    MapStorage mapStorage = new MapStorage();
    mapStorage.setBaseIp("localhost:8080");
    mapStorage.setProtocol("http");
    mapStorage.setAccessToken("access-token");
    String ruleSetName = "process_input_values";
    mapStorage.getRuleSetIsUpdated().put(ruleSetName, true);
    mapStorage.getRuleSetIsActionUpdated().put(ruleSetName, true);
    CodeFile existingGlobal = new CodeFile();
    existingGlobal.setId("global-id");
    CodeFileCollection collection = new CodeFileCollection();
    collection.setCount(1L);
    collection.setItems(Collections.singletonList(existingGlobal));
    when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
        .thenReturn(new ResponseEntity<>(collection, HttpStatus.OK));
    GlobalVariable global = new GlobalVariable();
    global.setId("global-id");
    global.setName("GlobalurlConsultQuotaInformationWsdl");
    global.setDataType("string");
    global.setStatus("developing");
    global.setActiveVersionId("active-id");
    when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(GlobalVariable.class)))
        .thenReturn(new ResponseEntity<>(global, HttpStatus.OK));
    CommonProcessing commonProcessing = new CommonProcessing(restTemplate, mapStorage, new TreeUtil());
    ProcessNodeConverter converter = new ProcessNodeConverter(restTemplate, commonProcessing, mapStorage);
    ProcessNodeDataDO.Process.InputVariableList inputs =
        new ProcessNodeDataDO.Process.InputVariableList();
    ProcessNodeDataDO.Process.InputVariableList.IBVariableDO input =
        new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO();
    input.setName("urlWsdlMw");
    input.setTypeDescription(STRING_CONSTANT);
    ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value =
        new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value();
    ValueTypeVarInfoDO reference = new ValueTypeVarInfoDO();
    reference.setVarInfoSource("Global");
    reference.setVarInfoId("globals.urlConsultQuotaInformationWsdl");
    reference.setVarName("urlConsultQuotaInformationWsdl");
    reference.setType(STRING_CONSTANT);
    value.setValueTypeVarInfoDO(reference);
    input.setValue(value);
    inputs.getIbVariableDOs().add(input);
    Step step = new Step();
    RuleSetStep ruleSet = new RuleSetStep();
    ruleSet.setId("rule-set-id");
    ruleSet.setName(ruleSetName);
    step.setRuleSet(ruleSet);
    Decision decision = new Decision();

    converter.updateRuleSet(inputs, step, decision);

    assertTrue(step.getMappings().stream().anyMatch(mapping ->
        "GlobalurlConsultQuotaIn795250248".equals(mapping.getStepTermName())
            && global.getName().equals(mapping.getTargetDecisionTermName())));
    assertTrue(decision.getSignature().stream().anyMatch(term -> "global-id".equals(term.getGlobalVariableId())));
    verify(restTemplate, atLeastOnce()).exchange(
        any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class));
    verify(restTemplate, atLeastOnce()).exchange(
        any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(GlobalVariable.class));
    verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void keepsReplySourceTypesIndependentOfIntegerOutputs() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        MapStorage storage = new MapStorage();
        storage.getCalculatedVariableDataTypeMap().put("calculatedDataItems.calcMiniDecReadyflg", "decimal");
        CommonProcessing processing = spy(new CommonProcessing(restTemplate, storage, new TreeUtil()));
        doReturn(false).when(processing).checkForGlobalVariable(any(VarRef.class), any(), any(), any(), eq(true));
        String xml = "<ReplyNodeDataDO><NodeId>CFDCKGCUZ3CRTMPC</NodeId><NodeName>Reply</NodeName>"
            + "<EventReply><ReplyVars>"
            + "<IBVariableDO><Name>miniDecReadyFlg</Name><TypeDescription>integer</TypeDescription><Value><ValueTypeVarInfoDO>"
            + "<VarInfoId>calculatedDataItems.calcMiniDecReadyflg</VarInfoId><VarInfoSource>CalculatedDataItem</VarInfoSource>"
            + "<VarInfoName>calcMiniDecReadyflg</VarInfoName><VarInfoType>Numeric</VarInfoType></ValueTypeVarInfoDO></Value></IBVariableDO>"
            + "<IBVariableDO><Name>statusCd</Name><TypeDescription>integer</TypeDescription><Value><ValueTypeVarInfoDO>"
            + "<VarInfoId>globals.GV_STATUS_CD_SUCCESS</VarInfoId><VarInfoSource>Global</VarInfoSource>"
            + "<VarInfoName>GV_STATUS_CD_SUCCESS</VarInfoName><VarInfoType>Numeric</VarInfoType></ValueTypeVarInfoDO></Value></IBVariableDO>"
            + "</ReplyVars></EventReply></ReplyNodeDataDO>";
        try {
            ReplyNodeDataDO reply = JAXBContext.newInstance(
                ReplyNodeDataDO.class).createUnmarshaller().unmarshal(
                new javax.xml.transform.stream.StreamSource(new StringReader(xml)),
                ReplyNodeDataDO.class).getValue();
            String extractPath = System.getProperty("replyExtract");
            if (extractPath != null) {
                Batch batch = (Batch) JAXBContext.newInstance(
                    Batch.class).createUnmarshaller().unmarshal(new File(extractPath));
                reply = batch.getLogicalUnit().getReplyNodeDataDOs().stream()
                    .filter(node -> "CFDCKGCUZ3CRTMPC".equals(node.getNodeId())).findFirst().orElseThrow();
                batch.getLogicalUnit().getFlowDO().getCalculatedDataItemList().getCalculatedItemDOs().forEach(item ->
                    storage.getCalculatedVariableDataTypeMap().put("calculatedDataItems." + item.getName(),
                        processing.getCalculatedItemDataType(item.getCalculatedItemType())));
            }
            Step step = new Step();
            RuleSet ruleSet = new RuleSet();
            Rule rule = new Rule();

            processing.processVariableToCreateRuleSet(step, new Decision(), ruleSet, rule, reply.getEventReply().getReplyVars().getIbVariableDOs());

            for (String source : List.of("CalculatedDataItem.calcMiniDecReadyflg", "Global.GV_STATUS_CD_SUCCESS")) {
                String alias = processing.sanitizeAndReduceVariableName(source);
                Signature input = ruleSet.getSignature().stream().filter(term -> alias.equals(term.getName())).findFirst().orElseThrow();
                assertEquals("decimal", input.getDataType(), source);
                assertEquals("input", input.getDirection());
                assertTrue(step.getMappings().stream().anyMatch(mapping -> alias.equals(mapping.getStepTermName())
                    && processing.sanitizeVariableName(source).equals(mapping.getTargetDecisionTermName())
                    && Mapping.DirectionEnum.INPUT.equals(mapping.getDirection())));
            }
            for (String output : List.of("miniDecReadyFlg", "statusCd")) {
                Signature term = ruleSet.getSignature().stream().filter(signature -> output.equals(signature.getName())).findFirst().orElseThrow();
                assertEquals("integer", term.getDataType());
                assertEquals("output", term.getDirection());
            }
            verifyNoInteractions(restTemplate);
        } catch (JAXBException exception) {
            throw new AssertionError(exception);
        }
    }

    @Test
    void globalInputMappingsUseTheSameReducedNamesAsRuleSetSignatures() {
        CommonProcessing commonProcessing = new CommonProcessing(new RestTemplate(), new MapStorage(), new TreeUtil());
        for (String name : List.of("Global.urlConsultQuotaInformationWsdl",
                "Global.GlobalConsultaSaldoUserWsdlMw", "Global.GlobalConsultaSaldoPswWsdlMw")) {
            IBVariableDO variable = new IBVariableDO();
                ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value =
                    new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value();
                ValueTypeVarInfoDO reference =
                    new ValueTypeVarInfoDO();
            reference.setVarInfoSource("Global");
            reference.setVarInfoId("globals." + name.substring(name.indexOf('.') + 1));
            reference.setVarName(name.substring(name.indexOf('.') + 1));
            value.setValueTypeVarInfoDO(reference);
            variable.setValue(value);
            Mapping mapping = new Mapping();

            commonProcessing.setMappingNameFields(mapping, variable);

                assertEquals(commonProcessing.sanitizeAndReduceVariableName(name),
                    mapping.getStepTermName());
                assertEquals(commonProcessing.sanitizeVariableName(name),
                    mapping.getTargetDecisionTermName());
        }
    }

    @Test
    void keepsConvertedNodeWhenConditionOutputIsAnotherCondition() {
        MapStorage mapStorage = new MapStorage();
        TreeUtil treeUtil = new TreeUtil();
        CommonProcessing commonProcessing = new CommonProcessing(new RestTemplate(), mapStorage, treeUtil);

        Step nestedCondition = new Step();
        nestedCondition.setType(Step.TypeEnum.CONDITION);
        mapStorage.getNodeIdStepMap().put("nested-condition", Collections.singletonList(nestedCondition));
        treeUtil.getNodeIdObjIdMap().put("nested-condition", (short) 1);
        treeUtil.getObjIdOutputNodesMap().put((short) 1, "downstream-cell");

        ConditionBranch branch = commonProcessing.createConditionBranch("nested-condition", true);

        assertSame(nestedCondition, branch.getSteps().get(0));
    }

    @Test
    void linksTerminalFanInNodeForSharedReply() {
        MapStorage mapStorage = new MapStorage();
        TreeUtil treeUtil = new TreeUtil();
        CommonProcessing commonProcessing = new CommonProcessing(new RestTemplate(), mapStorage, treeUtil);
        mapStorage.setUseCrossBranchLinks(true);
        treeUtil.getNodeIdInputNodesMap().put("reply", "first,second");
        treeUtil.getNodeIdObjIdMap().put("reply", (short) 2);
        treeUtil.getObjIdOutputNodesMap().put((short) 2, "");

        Step replyStep = new Step();
        ArrayList<Step> steps = new ArrayList<>();
        commonProcessing.addStep(steps, replyStep, "reply");

        assertSame(replyStep, steps.get(0));
        assertNotNull(replyStep.getLinkLabel());
        assertSame(replyStep.getLinkLabel(), mapStorage.getNodeIdLinkLabelMap().get("reply"));
        assertTrue(mapStorage.getCrossBranchLinkSteps().containsKey(replyStep.getLinkLabel()));
    }

    @Test
    void createsReferencedGlobalAndActivatesItsRevision() throws Exception {
        RestTemplate restTemplate = mock(RestTemplate.class);
        CodeFileCollection missingGlobal = new CodeFileCollection();
        missingGlobal.setCount(0L);
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
            .thenReturn(new ResponseEntity<>(missingGlobal, HttpStatus.OK));

        CodeFile createdGlobal = new CodeFile();
        createdGlobal.setId("global-id");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setETag("\"etag\"");
        when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(CodeFile.class)))
            .thenReturn(new ResponseEntity<>(createdGlobal, responseHeaders, HttpStatus.CREATED));
        when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(GlobalVariable.class)))
            .thenReturn(new ResponseEntity<>(new GlobalVariable(), HttpStatus.CREATED));

        Item revision = new Item();
        revision.setId("revision-id");
        GlobalVariableCollection revisions = new GlobalVariableCollection();
        revisions.setItems(Collections.singletonList(revision));
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(GlobalVariableCollection.class)))
            .thenReturn(new ResponseEntity<>(revisions, HttpStatus.OK));
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(String.class)))
            .thenReturn(new ResponseEntity<>("revision-id", HttpStatus.OK));

        MapStorage mapStorage = new MapStorage();
        mapStorage.setBaseIp("localhost:8080");
        mapStorage.setProtocol("http");
        mapStorage.setAccessToken("access-token");
        CommonProcessing commonProcessing = new CommonProcessing(restTemplate, mapStorage, new TreeUtil());
        Decision decision = new Decision();

        VarRef globalVariableRef = new VarRef();
        globalVariableRef.setVarInfoId("globals.ApplicationScore");
        globalVariableRef.setType(STRING_CONSTANT);
        assertFalse(commonProcessing.checkForGlobalVariable(
            globalVariableRef, null, "ApplicationScore", decision, false));

        assertEquals(1, decision.getSignature().size());
        SignatureTerm signatureTerm = decision.getSignature().get(0);
        assertEquals("global-id", signatureTerm.getGlobalVariableId());
        ArgumentCaptor<HttpEntity> requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).postForEntity(any(URI.class), requestCaptor.capture(), eq(CodeFile.class));
        Map<?, ?> requestBody = (Map<?, ?>) requestCaptor.getValue().getBody();
        assertEquals(3, requestBody.size());
        assertEquals("GlobalApplicationScore", requestBody.get("name"));
        assertEquals("string", requestBody.get("dataType"));
        assertEquals("", requestBody.get("defaultValue"));
        ArgumentCaptor<URI> activationUriCaptor = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<HttpEntity> activationRequestCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).exchange(activationUriCaptor.capture(), eq(HttpMethod.PUT), activationRequestCaptor.capture(), eq(String.class));
        assertEquals("http://localhost:8080/referenceData/globalVariables/global-id/active", activationUriCaptor.getValue().toString());
        assertEquals("revision-id", activationRequestCaptor.getValue().getBody());
        assertEquals("\"etag\"", activationRequestCaptor.getValue().getHeaders().getFirst(HttpHeaders.IF_MATCH));
        ArgumentCaptor<URI> draftUriCaptor = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<HttpEntity> draftRequestCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).postForEntity(draftUriCaptor.capture(), draftRequestCaptor.capture(), eq(GlobalVariable.class));
        assertEquals("http://localhost:8080/referenceData/globalVariables/global-id/revisions", draftUriCaptor.getValue().toString());
        assertEquals(3, ((Map<?, ?>) draftRequestCaptor.getValue().getBody()).size());
        GlobalVariable migratedGlobal = mapStorage.getGlobalVariableMap().values().iterator().next();
        assertEquals("revision-id", migratedGlobal.getActiveVersionId());
        assertEquals("developing", migratedGlobal.getStatus());
        assertFalse(migratedGlobal.isLocked());
    }

    @Test
    void activatesExistingGlobalWhenItHasNoActiveVersion() throws Exception {
        RestTemplate restTemplate = mock(RestTemplate.class);
        CodeFile existingGlobal = new CodeFile();
        existingGlobal.setId("global-id");
        CodeFileCollection existingGlobals = new CodeFileCollection();
        existingGlobals.setCount(1L);
        existingGlobals.setItems(Collections.singletonList(existingGlobal));
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
            .thenReturn(new ResponseEntity<>(existingGlobals, HttpStatus.OK));

        GlobalVariable globalDetails = new GlobalVariable();
        globalDetails.setId("global-id");
        globalDetails.setName("GlobalApplicationScore");
        globalDetails.setDataType("string");
        globalDetails.setDefaultValue("");
        HttpHeaders globalHeaders = new HttpHeaders();
        globalHeaders.setETag("\"global-etag\"");
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(GlobalVariable.class)))
            .thenReturn(new ResponseEntity<>(globalDetails, globalHeaders, HttpStatus.OK));

        Item revision = new Item();
        revision.setId("revision-id");
        GlobalVariableCollection revisions = new GlobalVariableCollection();
        revisions.setItems(Collections.singletonList(revision));
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(GlobalVariableCollection.class)))
            .thenReturn(new ResponseEntity<>(revisions, HttpStatus.OK));
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(String.class)))
            .thenReturn(new ResponseEntity<>("revision-id", HttpStatus.OK));
        when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(GlobalVariable.class)))
            .thenReturn(new ResponseEntity<>(new GlobalVariable(), HttpStatus.CREATED));

        MapStorage mapStorage = new MapStorage();
        mapStorage.setBaseIp("localhost:8080");
        mapStorage.setProtocol("http");
        mapStorage.setAccessToken("access-token");
        CommonProcessing commonProcessing = new CommonProcessing(restTemplate, mapStorage, new TreeUtil());
        Decision decision = new Decision();
        VarRef globalVariableRef = new VarRef();
        globalVariableRef.setVarInfoId("globals.ApplicationScore");
        globalVariableRef.setType(STRING_CONSTANT);

        assertFalse(commonProcessing.checkForGlobalVariable(
            globalVariableRef, null, "ApplicationScore", decision, false));

        verify(restTemplate).exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(String.class));
        GlobalVariable migratedGlobal = mapStorage.getGlobalVariableMap().values().iterator().next();
        assertEquals("revision-id", migratedGlobal.getActiveVersionId());
        assertEquals("developing", migratedGlobal.getStatus());
        assertFalse(migratedGlobal.isLocked());
    }

    @Test
    void createsEditableRevisionForExistingProductionGlobal() throws Exception {
        RestTemplate restTemplate = mock(RestTemplate.class);
        CodeFile existingGlobal = new CodeFile();
        existingGlobal.setId("global-id");
        CodeFileCollection existingGlobals = new CodeFileCollection();
        existingGlobals.setCount(1L);
        existingGlobals.setItems(Collections.singletonList(existingGlobal));
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
            .thenReturn(new ResponseEntity<>(existingGlobals, HttpStatus.OK));

        GlobalVariable globalDetails = new GlobalVariable();
        globalDetails.setId("global-id");
        globalDetails.setName("GlobalApplicationScore");
        globalDetails.setDataType("string");
        globalDetails.setDefaultValue("");
        globalDetails.setActiveVersionId("active-revision-id");
        globalDetails.setStatus("production");
        globalDetails.setLocked(true);
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(GlobalVariable.class)))
            .thenReturn(new ResponseEntity<>(globalDetails, HttpStatus.OK));
        when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(GlobalVariable.class)))
            .thenReturn(new ResponseEntity<>(new GlobalVariable(), HttpStatus.CREATED));

        MapStorage mapStorage = new MapStorage();
        mapStorage.setBaseIp("localhost:8080");
        mapStorage.setProtocol("http");
        mapStorage.setAccessToken("access-token");
        CommonProcessing commonProcessing = new CommonProcessing(restTemplate, mapStorage, new TreeUtil());
        Decision decision = new Decision();
        VarRef globalVariableRef = new VarRef();
        globalVariableRef.setVarInfoId("globals.ApplicationScore");
        globalVariableRef.setType(STRING_CONSTANT);

        assertFalse(commonProcessing.checkForGlobalVariable(
            globalVariableRef, null, "ApplicationScore", decision, false));

        verify(restTemplate).postForEntity(any(URI.class), any(HttpEntity.class), eq(GlobalVariable.class));
        verify(restTemplate, never()).exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(String.class));
        GlobalVariable migratedGlobal = mapStorage.getGlobalVariableMap().values().iterator().next();
        assertEquals("active-revision-id", migratedGlobal.getActiveVersionId());
        assertEquals("developing", migratedGlobal.getStatus());
        assertFalse(migratedGlobal.isLocked());
    }
}