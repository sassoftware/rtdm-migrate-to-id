package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.Decision2;
import com.sas.rtdm2id.model.id.decision.DecisionCollection;
import com.sas.rtdm2id.model.id.decision.Mapping;
import com.sas.rtdm2id.model.id.decision.RuleSetStep;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.rtdm.Batch;

import com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO;
import com.sas.rtdm2id.model.rtdm.extension.SubDiagramNodeDataDO;
import com.sas.rtdm2id.model.rtdm.extension.SubdiagramEvent;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class SubDiagramNodeConverterTest {

    private static SubDiagramNodeDataDO subdiagram(String nodeId, String campaignId, boolean withVariables) {
        SubDiagramNodeDataDO node = new SubDiagramNodeDataDO();
        node.setNodeId(nodeId);
        node.setNodeName("Child " + campaignId);
        node.setCampaignId(campaignId);
        node.setObjid((short) 5);
        SubdiagramEvent event = new SubdiagramEvent();
        if (withVariables) {
            ProcessNodeDataDO.Process.InputVariableList requestVars = new ProcessNodeDataDO.Process.InputVariableList();
            ProcessNodeDataDO.Process.InputVariableList.IBVariableDO input = new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO();
            input.setName("Amount");
            input.setTypeDescription("double");
            ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value = new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value();
            value.setDoubleValue("1");
            input.setValue(value);
            requestVars.getIbVariableDOs().add(input);
            event.setRequestVars(requestVars);
            event.setReplyVars(new ProcessNodeDataDO.Process.OutputVariableList());
        }
        node.setSubdiagramEvent(event);
        return node;
    }

    private static Step inputValuesStep(MapStorage storage, String name, boolean existing) {
        Step step = new Step();
        RuleSetStep ruleSet = new RuleSetStep();
        ruleSet.setName(name);
        ruleSet.setId("input-values");
        step.setRuleSet(ruleSet);
        storage.getRuleSetStepMap().put(name, step);
        storage.getRuleSetModifiedDate().put(name, new Date());
        storage.getRuleSetIsUpdated().put(name, existing);
        storage.getRuleSetIsActionUpdated().put(name, existing);
        storage.setBaseIp("viya.example");
        return step;
    }

    @Test
    void crossBranchLinkLabelLandsOnTheFirstStepTheConverterKeeps() {
        MapStorage storage = new MapStorage();
        storage.setUseCrossBranchLinks(true);
        TreeUtil treeUtil = new TreeUtil();
        treeUtil.getNodeIdInputNodesMap().put("sub", "left, right");
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, treeUtil);
        SubDiagramNodeConverter converter = new SubDiagramNodeConverter(processing, mock(RestTemplate.class), storage);
        SubDiagramNodeDataDO node = subdiagram("sub", "c1", true);
        Step inputValues = inputValuesStep(storage, "child_input_values", true);
        Decision decision = new Decision();

        // Same call sequence as Converter.processRTDMObject: the first result is discarded
        converter.createSubDiagram(node, decision);
        assertNull(storage.getNodeIdLinkLabelMap().get("sub"));
        List<Step> kept = converter.addCustomObjectStep(node, decision, (short) 5, "child");

        assertTrue(kept.get(0) == inputValues);
        assertNotNull(kept.get(0).getLinkLabel());
        assertEquals(storage.getNodeIdLinkLabelMap().get("sub"), kept.get(0).getLinkLabel());
        assertNotNull(storage.getCrossBranchLinkSteps().get(kept.get(0).getLinkLabel()));
        assertTrue(decision.getSignature().stream().anyMatch(term -> "Amount".equalsIgnoreCase(term.getName())));
    }

    @Test
    void mapsReplyVariablesOnTheCustomObjectStepTheConverterKeeps() {
        MapStorage storage = new MapStorage();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil());
        SubDiagramNodeConverter converter = new SubDiagramNodeConverter(processing, mock(RestTemplate.class), storage);
        SubDiagramNodeDataDO node = subdiagram("sub", "c1", true);
        ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO output = new ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO();
        output.setName("Score");
        output.setTypeDescription("double");
        node.getSubdiagramEvent().getReplyVars().getIbVariableDOs().add(output);
        inputValuesStep(storage, "child_input_values", true);
        Decision decision = new Decision();

        converter.createSubDiagram(node, decision);
        List<Step> kept = converter.addCustomObjectStep(node, decision, (short) 5, "child");

        Step subDecision = kept.get(kept.size() - 1);
        assertEquals(Step.TypeEnum.CUSTOM_OBJECT, subDecision.getType());
        assertTrue(subDecision.getMappings().stream().anyMatch(mapping -> "Score".equalsIgnoreCase(mapping.getStepTermName())
            && Mapping.DirectionEnum.OUTPUT.equals(mapping.getDirection())));
    }

    @Test
    void mapsEveryRequestVariableToTheChildsInputName() {
        // The _input_values RuleSet declares every request variable (an unset one is assigned missing); each needs a
        // Decision term and a sub-decision mapping or ID rejects the RuleSet's mapping (customer case: OUTER_WITTNESS*)
        MapStorage storage = new MapStorage();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil());
        SubDiagramNodeConverter converter = new SubDiagramNodeConverter(processing, mock(RestTemplate.class), storage);
        SubDiagramNodeDataDO node = subdiagram("sub", "c1", true);
        ProcessNodeDataDO.Process.InputVariableList.IBVariableDO unset = new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO();
        unset.setName("WitnessAddress");
        unset.setTypeDescription("string");
        node.getSubdiagramEvent().getRequestVars().getIbVariableDOs().add(unset);
        inputValuesStep(storage, "child_input_values", true);
        Decision decision = new Decision();

        converter.createSubDiagram(node, decision);
        List<Step> kept = converter.addCustomObjectStep(node, decision, (short) 5, "child");

        Step subDecision = kept.get(kept.size() - 1);
        assertTrue(decision.getSignature().stream().anyMatch(term -> "WitnessAddress".equals(term.getName())));
        // Step terms are the child's inputs, which it declares as Request<name> (its Start request variables)
        assertTrue(subDecision.getMappings().stream().anyMatch(mapping -> "RequestWitnessAddress".equals(mapping.getStepTermName())
            && "WitnessAddress".equals(mapping.getTargetDecisionTermName())
            && Mapping.DirectionEnum.INPUT.equals(mapping.getDirection())), subDecision.getMappings().toString());
        assertTrue(subDecision.getMappings().stream().anyMatch(mapping -> "RequestAmount".equals(mapping.getStepTermName())
            && Mapping.DirectionEnum.INPUT.equals(mapping.getDirection())), subDecision.getMappings().toString());
    }

    @Test
    void toleratesSubdiagramWithoutRequestOrReplyVariables() {
        MapStorage storage = new MapStorage();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil());
        SubDiagramNodeConverter converter = new SubDiagramNodeConverter(processing, mock(RestTemplate.class), storage);
        SubDiagramNodeDataDO node = subdiagram("sub", "c1", false);
        inputValuesStep(storage, "child_input_values", true);

        assertEquals(1, converter.createSubDiagram(node, new Decision()).size());
        List<Step> kept = converter.addCustomObjectStep(node, new Decision(), (short) 5, "child");

        assertEquals(Step.TypeEnum.CUSTOM_OBJECT, kept.get(kept.size() - 1).getType());
    }

    @Test
    void writesInputRuleSetSignatureBeforeRuleAndSkipsReusedRuleSet() {
        MapStorage storage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing processing = new CommonProcessing(restTemplate, storage, new TreeUtil());
        SubDiagramNodeConverter converter = new SubDiagramNodeConverter(processing, mock(RestTemplate.class), storage);
        SubDiagramNodeDataDO node = subdiagram("sub", "c1", true);
        Step step = inputValuesStep(storage, "child_input_values", false);

        converter.updateRuleSet(node.getSubdiagramEvent().getRequestVars(), step, new Decision());

        InOrder order = inOrder(restTemplate);
        order.verify(restTemplate).exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(RuleSet.class));
        order.verify(restTemplate).exchange(any(URI.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(Rule.class));

        RestTemplate reusedTemplate = mock(RestTemplate.class);
        MapStorage reusedStorage = new MapStorage();
        Step reused = inputValuesStep(reusedStorage, "child_input_values", true);
        new SubDiagramNodeConverter(new CommonProcessing(reusedTemplate, reusedStorage, new TreeUtil()), mock(RestTemplate.class), reusedStorage)
            .updateRuleSet(node.getSubdiagramEvent().getRequestVars(), reused, new Decision());
        verifyNoInteractions(reusedTemplate);
    }

    @Test
    void resolvesEverySubdiagramAndReportsTheOnesThatFail() {
        MapStorage storage = new MapStorage();
        storage.setBaseIp("viya.example");
        RestTemplate restTemplate = mock(RestTemplate.class);
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(DecisionCollection.class)))
            .thenAnswer(invocation -> {
                String uri = invocation.getArgument(0).toString();
                DecisionCollection collection = new DecisionCollection();
                if (!uri.contains("missing")) {
                    Decision2 item = new Decision2();
                    item.setId(uri.contains("/revisions") ? "rev-1" : "flow-1");
                    item.setName("Child flow");
                    collection.setItems(Collections.singletonList(item));
                    collection.setCount(1L);
                } else {
                    collection.setCount(0L);
                }
                return ResponseEntity.ok(collection);
            });
        SubDiagramNodeConverter converter = new SubDiagramNodeConverter(
            new CommonProcessing(restTemplate, storage, new TreeUtil()), restTemplate, storage);
        SubDiagramNodeDataDO missing = subdiagram("first", "missing", false);
        missing.setObjid((short) 1);
        SubDiagramNodeDataDO found = subdiagram("second", "c1", false);
        found.setObjid((short) 2);
        Batch batch = new Batch();
        Batch.LogicalUnit logicalUnit = new Batch.LogicalUnit();
        logicalUnit.getSubDiagramNodeDataDOs().add(missing);
        logicalUnit.getSubDiagramNodeDataDOs().add(found);
        batch.setLogicalUnit(logicalUnit);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> converter.getSubdiagrams(batch));

        assertTrue(exception.getMessage().contains("Child missing"), exception.getMessage());
        assertEquals("/decisions/flows/flow-1/revisions/rev-1", storage.getCustomObjectStepHashMap().get((short) 2).getUri());
        assertNull(storage.getCustomObjectStepHashMap().get((short) 1));
    }
}
