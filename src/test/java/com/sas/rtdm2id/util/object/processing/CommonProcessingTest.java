package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.CodeFile;
import com.sas.rtdm2id.model.id.decision.CodeFileCollection;
import com.sas.rtdm2id.model.id.decision.ConditionBranch;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.decision.SignatureTerm;
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

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.NUMERIC_CONSTANT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommonProcessingTest {

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
    void createsDataPickerGlobalAndAddsGlobalSignatureTerm() throws Exception {
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

        MapStorage mapStorage = new MapStorage();
        mapStorage.setBaseIp("localhost:8080");
        mapStorage.setProtocol("http");
        mapStorage.setAccessToken("access-token");
        CommonProcessing commonProcessing = new CommonProcessing(restTemplate, mapStorage, new TreeUtil());
        Decision decision = new Decision();

        String globalVariableName = commonProcessing.createGlobalVariableForDataPicker(
            "globals.ApplicationScore", "ApplicationScore", NUMERIC_CONSTANT, decision);

        assertEquals("ApplicationScore", globalVariableName);
        assertEquals(1, decision.getSignature().size());
        SignatureTerm signatureTerm = decision.getSignature().get(0);
        assertEquals("ApplicationScore", signatureTerm.getName());
        assertEquals("global-id", signatureTerm.getGlobalVariableId());
        verify(restTemplate, never()).exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(String.class));
    }
}