package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.RuleSetStep;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.rtdm.AssignmentNodeDataDO;
import com.sas.rtdm2id.model.rtdm.extension.IBVariableDO;
import com.sas.rtdm2id.model.rtdm.extension.Value;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class AssignmentNodeConverterTest {

    private static AssignmentNodeDataDO assignmentNode() {
        AssignmentNodeDataDO node = new AssignmentNodeDataDO();
        node.setNodeId("assign");
        node.setNodeName("Assign");
        AssignmentNodeDataDO.Assignments assignments = new AssignmentNodeDataDO.Assignments();
        IBVariableDO variable = new IBVariableDO();
        variable.setName("Score");
        variable.setTypeDescription("double");
        Value value = new Value();
        value.setDoubleValue("5");
        variable.setValue(value);
        assignments.getIbVariableDOs().add(variable);
        node.setAssignments(assignments);
        return node;
    }

    private static void ruleSetStep(MapStorage storage, String name, boolean existing) {
        Step step = new Step();
        RuleSetStep ruleSet = new RuleSetStep();
        ruleSet.setName(name);
        ruleSet.setId("assign-id");
        step.setRuleSet(ruleSet);
        storage.getRuleSetStepMap().put(name, step);
        storage.getRuleSetModifiedDate().put(name, new Date());
        storage.getRuleSetIsUpdated().put(name, existing);
        storage.getRuleSetIsActionUpdated().put(name, existing);
        storage.setBaseIp("viya.example");
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void putsTheSignatureBeforePostingTheRules() {
        MapStorage storage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing processing = new CommonProcessing(restTemplate, storage, new TreeUtil());
        AssignmentNodeDataDO node = assignmentNode();
        ruleSetStep(storage, processing.makeAssignmentNodeRuleSetName(node), false);

        List<Step> steps = new AssignmentNodeConverter(processing, storage).createAssignment(node, new Decision(), (short) 1);

        assertEquals(1, steps.size());
        ArgumentCaptor<HttpEntity> put = ArgumentCaptor.forClass(HttpEntity.class);
        InOrder order = inOrder(restTemplate);
        order.verify(restTemplate).exchange(any(URI.class), eq(HttpMethod.PUT), put.capture(), eq(RuleSet.class));
        order.verify(restTemplate).exchange(any(URI.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(Rule.class));
        RuleSet signature = (RuleSet) put.getValue().getBody();
        assertTrue(signature.getSignature().stream().anyMatch(term -> "Score".equalsIgnoreCase(term.getName())
            && "inOut".equals(term.getDirection())), String.valueOf(signature.getSignature()));
    }

    @Test
    void doesNotResignAnExistingRuleSet() {
        MapStorage storage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing processing = new CommonProcessing(restTemplate, storage, new TreeUtil());
        AssignmentNodeDataDO node = assignmentNode();
        ruleSetStep(storage, processing.makeAssignmentNodeRuleSetName(node), true);

        new AssignmentNodeConverter(processing, storage).createAssignment(node, new Decision(), (short) 1);

        verify(restTemplate, never()).exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(RuleSet.class));
    }
}
