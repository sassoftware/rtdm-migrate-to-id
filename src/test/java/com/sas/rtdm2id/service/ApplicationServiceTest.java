package com.sas.rtdm2id.service;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.dto.rtdm.model.OAuthTokenResponse;
import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.model.rtdm.CampaignDO;
import com.sas.rtdm2id.util.Converter;
import com.sas.rtdm2id.util.MarshallerWrapper;
import com.sas.rtdm2id.util.object.processing.CommonProcessing;
import com.sas.rtdm2id.util.object.processing.ProcessNodeConverter;
import com.sas.rtdm2id.util.object.processing.SubDiagramNodeConverter;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApplicationServiceTest {

    @Test
    void includesExistingDecisionNameInResponse() {
	RestTemplate restTemplate = mock(RestTemplate.class);
	CommonProcessing commonProcessing = mock(CommonProcessing.class);
	Batch batch = batchWithCampaignName("Existing Decision");
	OAuthTokenResponse token = OAuthTokenResponse.builder()
		.accessToken("access-token")
		.expiresIn(3600)
		.build();

	when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(OAuthTokenResponse.class)))
		.thenReturn(ResponseEntity.ok(token));
	when(commonProcessing.getSubDiagramNodeNames(batch)).thenReturn(Collections.emptyList());
	when(commonProcessing.findDecision("Existing Decision")).thenReturn(true);

	ApplicationService service = new ApplicationService(
		restTemplate,
		mock(MarshallerWrapper.class),
		mock(Converter.class),
		mock(ProcessNodeConverter.class),
		mock(SubDiagramNodeConverter.class),
		commonProcessing,
		mock(TreeUtil.class),
		new MapStorage());

	assertEquals("Decision 'Existing Decision' already exists, existing decision was not overwritten",
		service.createDiagram("viya.example", batch, "login", "password", "http",
			"/folders/folders/@myFolder", "true"));
    }

    private Batch batchWithCampaignName(String name) {
	CampaignDO campaign = new CampaignDO();
	campaign.setName(name);
	Batch.LogicalUnit logicalUnit = new Batch.LogicalUnit();
	logicalUnit.setCampaignDO(campaign);
	Batch batch = new Batch();
	batch.setLogicalUnit(logicalUnit);
	return batch;
    }
}
