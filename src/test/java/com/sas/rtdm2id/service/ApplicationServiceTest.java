package com.sas.rtdm2id.service;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.exception.ClientIdException;
import com.sas.rtdm2id.exception.OAuthTokenException;
import com.sas.rtdm2id.model.dto.rtdm.model.ClientIdResponse;
import com.sas.rtdm2id.model.dto.rtdm.model.OAuthTokenResponse;
import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.model.rtdm.CampaignDO;
import com.sas.rtdm2id.util.Converter;
import com.sas.rtdm2id.util.MarshallerWrapper;
import com.sas.rtdm2id.util.object.processing.CommonProcessing;
import com.sas.rtdm2id.util.object.processing.ProcessNodeConverter;
import com.sas.rtdm2id.util.object.processing.SubDiagramNodeConverter;
import com.sas.rtdm2id.util.tree.impl.GenericTree;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
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

	TreeUtil treeUtil = mock(TreeUtil.class);
	MapStorage mapStorage = new MapStorage();
	mapStorage.getNodeIdStepMap().put("stale-node", Collections.emptyList());
	ApplicationService service = new ApplicationService(
		restTemplate,
		mock(MarshallerWrapper.class),
		mock(Converter.class),
		mock(ProcessNodeConverter.class),
		mock(SubDiagramNodeConverter.class),
		commonProcessing,
		treeUtil,
		mapStorage);

	assertEquals("Decision 'Existing Decision' already exists, existing decision was not overwritten",
		service.createDiagram("viya.example", batch, "login", "password", "http",
			"/folders/folders/@myFolder", "true"));
	verify(treeUtil).clearMaps();
	assertTrue(mapStorage.getNodeIdStepMap().isEmpty());
	verify(restTemplate, never()).postForEntity(any(URI.class), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void clearsRequestStateAndStopsMigrationWhenRuleSetCreationFails() {
	RestTemplate restTemplate = mock(RestTemplate.class);
	CommonProcessing commonProcessing = mock(CommonProcessing.class);
	ProcessNodeConverter processNodeConverter = mock(ProcessNodeConverter.class);
	SubDiagramNodeConverter subDiagramNodeConverter = mock(SubDiagramNodeConverter.class);
	Converter converter = mock(Converter.class);
	TreeUtil treeUtil = spy(new TreeUtil());
	MapStorage mapStorage = new MapStorage();
	Batch batch = batchWithCampaignName("Failed Decision");
	doReturn(new GenericTree<Short>()).when(treeUtil).createTree(batch.getLogicalUnit());
	treeUtil.getObjIdToNodeIdMap().put((short) 1, "stale-node");
	OAuthTokenResponse token = OAuthTokenResponse.builder()
		.accessToken("access-token").expiresIn(3600).build();
	when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(OAuthTokenResponse.class)))
		.thenReturn(ResponseEntity.ok(token));
	when(commonProcessing.getSubDiagramNodeNames(batch)).thenReturn(Collections.emptyList());
	doThrow(new IllegalStateException("RuleSet creation failed"))
		.when(commonProcessing).createRuleSets(batch);
	mapStorage.getNodeIdStepMap().put("stale-node", Collections.emptyList());
	ApplicationService service = new ApplicationService(restTemplate, mock(MarshallerWrapper.class),
		converter, processNodeConverter, subDiagramNodeConverter, commonProcessing, treeUtil, mapStorage);

	assertEquals("RuleSet creation failed", service.createDiagram("viya.example", batch,
		"login", "password", "http", "/folders/folders/@myFolder", "false"));

	assertTrue(mapStorage.getNodeIdStepMap().isEmpty());
	assertTrue(treeUtil.getObjIdToNodeIdMap().isEmpty());
	verify(subDiagramNodeConverter, never()).getSubdiagrams(any(Batch.class));
	verify(converter, never()).createDecision(any(Batch.class), any());
	verify(restTemplate, never()).postForEntity(any(URI.class), any(HttpEntity.class), eq(String.class));
    }

    @Test
    void requestsTokenOverHttpsAndStoresRequestContext() {
	RestTemplate restTemplate = mock(RestTemplate.class);
	MapStorage mapStorage = new MapStorage();
	OAuthTokenResponse token = stubToken(restTemplate);
	ApplicationService service = serviceWith(restTemplate, mapStorage);
	Instant beforeRequest = Instant.now();

	assertSame(token, service.getAccessToken("viya.example", "login", "password", "https"));

	ArgumentCaptor<HttpEntity<?>> entity = ArgumentCaptor.forClass(HttpEntity.class);
	verify(restTemplate).postForEntity(eq(URI.create("https://viya.example/SASLogon/oauth/token")),
		entity.capture(), eq(OAuthTokenResponse.class));
	assertEquals(MediaType.APPLICATION_FORM_URLENCODED, entity.getValue().getHeaders().getContentType());
	assertEquals("grant_type=password&username=login&password=password", entity.getValue().getBody());
	assertEquals("access-token", mapStorage.getAccessToken());
	assertEquals("viya.example", mapStorage.getBaseIp());
	assertEquals("https", mapStorage.getProtocol());
	assertSame(token, service.oAuthTokenResponseMap.get(Arrays.asList("viya.example", "https", "login")));
	assertTrue(!service.expirationTimeMap.get(Arrays.asList("viya.example", "https", "login")).isBefore(beforeRequest.plusSeconds(3600)));
	assertTrue(!service.expirationTimeMap.get(Arrays.asList("viya.example", "https", "login")).isAfter(Instant.now().plusSeconds(3600)));
    }

    @Test
    void reusesUnexpiredTokenWithoutAnotherHttpRequest() {
	RestTemplate restTemplate = mock(RestTemplate.class);
	OAuthTokenResponse token = stubToken(restTemplate);
	ApplicationService service = serviceWith(restTemplate, new MapStorage());

	assertSame(token, service.getAccessToken("viya.example", "login", "password", "http"));
	assertSame(token, service.getAccessToken("viya.example", "login", "password", "http"));

	verify(restTemplate, times(1)).postForEntity(any(URI.class), any(HttpEntity.class), eq(OAuthTokenResponse.class));
    }

    @Test
    void refreshesExpiredTokenWithoutSleeping() {
	RestTemplate restTemplate = mock(RestTemplate.class);
	MapStorage mapStorage = new MapStorage();
	OAuthTokenResponse replacement = stubToken(restTemplate);
	ApplicationService service = serviceWith(restTemplate, mapStorage);
	service.oAuthTokenResponseMap.put(Arrays.asList("viya.example", "https", "login"), OAuthTokenResponse.builder().accessToken("expired").build());
	service.expirationTimeMap.put(Arrays.asList("viya.example", "https", "login"), Instant.EPOCH);

	assertSame(replacement, service.getAccessToken("viya.example", "login", "password", "https"));

	assertSame(replacement, service.oAuthTokenResponseMap.get(Arrays.asList("viya.example", "https", "login")));
	assertEquals("access-token", mapStorage.getAccessToken());
	assertTrue(service.expirationTimeMap.get(Arrays.asList("viya.example", "https", "login")).isAfter(Instant.now()));
	verify(restTemplate).postForEntity(any(URI.class), any(HttpEntity.class), eq(OAuthTokenResponse.class));
    }

    @Test
    void cachesTokensSeparatelyForDifferentHosts() {
	RestTemplate restTemplate = mock(RestTemplate.class);
	OAuthTokenResponse first = OAuthTokenResponse.builder().accessToken("first").expiresIn(3600).build();
	OAuthTokenResponse second = OAuthTokenResponse.builder().accessToken("second").expiresIn(3600).build();
	when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(OAuthTokenResponse.class)))
		.thenReturn(ResponseEntity.ok(first)).thenReturn(ResponseEntity.ok(second));
	MapStorage mapStorage = new MapStorage();
	ApplicationService service = serviceWith(restTemplate, mapStorage);

	assertSame(first, service.getAccessToken("first.example", "login", "password", "http"));
	assertSame(second, service.getAccessToken("second.example", "login", "password", "http"));
	assertSame(first, service.getAccessToken("first.example", "login", "password", "http"));
	assertEquals("first.example", mapStorage.getBaseIp());
	assertEquals("first", mapStorage.getAccessToken());
	assertEquals("http", mapStorage.getProtocol());
	verify(restTemplate, times(2)).postForEntity(any(URI.class), any(HttpEntity.class), eq(OAuthTokenResponse.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"protocol", "login"})
    void isolatesCachedTokensByProtocolAndLogin(String changedField) {
	RestTemplate restTemplate = mock(RestTemplate.class);
	OAuthTokenResponse first = OAuthTokenResponse.builder().accessToken("first").expiresIn(3600).build();
	OAuthTokenResponse second = OAuthTokenResponse.builder().accessToken("second").expiresIn(3600).build();
	when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(OAuthTokenResponse.class)))
		.thenReturn(ResponseEntity.ok(first)).thenReturn(ResponseEntity.ok(second));
	MapStorage mapStorage = new MapStorage();
	ApplicationService service = serviceWith(restTemplate, mapStorage);
	String secondProtocol = "protocol".equals(changedField) ? "https" : "http";
	String secondLogin = "login".equals(changedField) ? "another-login" : "login";

	assertSame(first, service.getAccessToken("viya.example", "login", "password", "http"));
	assertSame(second, service.getAccessToken("viya.example", secondLogin, "password", secondProtocol));
	assertSame(first, service.getAccessToken("viya.example", "login", "password", "http"));
	assertEquals("first", mapStorage.getAccessToken());
	assertEquals("http", mapStorage.getProtocol());
	assertEquals(2, service.oAuthTokenResponseMap.size());
	verify(restTemplate, times(2)).postForEntity(any(URI.class), any(HttpEntity.class), eq(OAuthTokenResponse.class));
    }

    @Test
    void reusesHttpsTokenForCaseInsensitiveProtocol() {
	RestTemplate restTemplate = mock(RestTemplate.class);
	OAuthTokenResponse token = stubToken(restTemplate);
	MapStorage mapStorage = new MapStorage();
	ApplicationService service = serviceWith(restTemplate, mapStorage);

	assertSame(token, service.getAccessToken("viya.example", "login", "password", "https"));
	mapStorage.setProtocol("http");
	mapStorage.setAccessToken("stale-token");
	assertSame(token, service.getAccessToken("viya.example", "login", "password", "HTTPS"));

	assertEquals("https", mapStorage.getProtocol());
	assertEquals("access-token", mapStorage.getAccessToken());
	verify(restTemplate, times(1)).postForEntity(any(URI.class), any(HttpEntity.class), eq(OAuthTokenResponse.class));
    }

    @ParameterizedTest
    @ValueSource(ints = {201, 401, 500})
    void rejectsUnexpectedTokenStatusWithoutCaching(int status) {
	RestTemplate restTemplate = mock(RestTemplate.class);
	OAuthTokenResponse token = OAuthTokenResponse.builder().accessToken("invalid").expiresIn(3600).build();
	when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(OAuthTokenResponse.class)))
		.thenReturn(ResponseEntity.status(status).body(token));
	ApplicationService service = serviceWith(restTemplate, new MapStorage());

	assertThrows(OAuthTokenException.class,
		() -> service.getAccessToken("viya.example", "login", "password", "http"));
	assertTrue(service.oAuthTokenResponseMap.isEmpty());
	assertTrue(service.expirationTimeMap.isEmpty());
    }

    @Test
    void translatesUnauthorizedTokenResponseToOAuthException() {
	RestTemplate restTemplate = mock(RestTemplate.class);
	when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(OAuthTokenResponse.class)))
		.thenThrow(new HttpClientErrorException(HttpStatus.UNAUTHORIZED));
	ApplicationService service = serviceWith(restTemplate, new MapStorage());

	OAuthTokenException exception = assertThrows(OAuthTokenException.class,
		() -> service.getAccessToken("viya.example", "login", "password", "http"));
	assertEquals("Could not get OAuth token: invalid user", exception.getMessage());
	assertTrue(service.oAuthTokenResponseMap.isEmpty());
    }

    @Test
    void rejectsMalformedHostBeforeMakingTokenRequest() {
	RestTemplate restTemplate = mock(RestTemplate.class);
	ApplicationService service = serviceWith(restTemplate, new MapStorage());

	assertThrows(OAuthTokenException.class,
		() -> service.getAccessToken("invalid host", "login", "password", "http"));
	verifyNoInteractions(restTemplate);
    }

    @Test
    void registersClientUsingRegistrationTokenAndHttps() {
	RestTemplate restTemplate = mock(RestTemplate.class);
	MapStorage mapStorage = new MapStorage();
	stubToken(restTemplate);
	ClientIdResponse client = ClientIdResponse.builder().clientId("migration-client").build();
	when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(ClientIdResponse.class)))
		.thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(client));
	ApplicationService service = serviceWith(restTemplate, mapStorage);

	assertSame(client, service.registerClient("viya.example", "test-consul-token", "migration-client",
		"test-client-secret", "https"));

	verify(restTemplate).postForEntity(
		eq(URI.create("https://viya.example/SASLogon/oauth/clients/consul?callback=false&serviceId=migration-client")),
		any(HttpEntity.class), eq(OAuthTokenResponse.class));
	ArgumentCaptor<HttpEntity<?>> entity = ArgumentCaptor.forClass(HttpEntity.class);
	verify(restTemplate).postForEntity(eq(URI.create("https://viya.example/SASLogon/oauth/clients")),
		entity.capture(), eq(ClientIdResponse.class));
	assertEquals("Bearer access-token", entity.getValue().getHeaders().getFirst("Authorization"));
	assertEquals("https", mapStorage.getProtocol());
    }

    @ParameterizedTest
    @ValueSource(ints = {200, 400, 500})
    void rejectsClientRegistrationWithoutCreatedStatus(int status) {
	RestTemplate restTemplate = mock(RestTemplate.class);
	stubToken(restTemplate);
	when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(ClientIdResponse.class)))
		.thenReturn(ResponseEntity.status(status).build());
	ApplicationService service = serviceWith(restTemplate, new MapStorage());

	assertThrows(ClientIdException.class, () -> service.registerClient("viya.example", "test-consul-token",
		"migration-client", "test-client-secret", "http"));
    }

    @Test
    void doesNotRegisterClientWhenRegistrationTokenRequestFails() {
	RestTemplate restTemplate = mock(RestTemplate.class);
	when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(OAuthTokenResponse.class)))
		.thenReturn(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
	ApplicationService service = serviceWith(restTemplate, new MapStorage());

	ClientIdException exception = assertThrows(ClientIdException.class,
		() -> service.registerClient("viya.example", "test-consul-token", "migration-client", "test-client-secret", "http"));
	assertTrue(exception.getCause() instanceof OAuthTokenException);
	verify(restTemplate, never()).postForEntity(any(URI.class), any(HttpEntity.class), eq(ClientIdResponse.class));
    }

    @Test
    void listsMissingSubdiagramsAndDoesNotStartConversion() {
	RestTemplate restTemplate = mock(RestTemplate.class);
	stubToken(restTemplate);
	CommonProcessing commonProcessing = mock(CommonProcessing.class);
	TreeUtil treeUtil = spy(new TreeUtil());
	treeUtil.getObjIdToNodeIdMap().put((short) 1, "stale-node");
	MapStorage mapStorage = new MapStorage();
	mapStorage.getNodeIdStepMap().put("stale-node", Collections.emptyList());
	Converter converter = mock(Converter.class);
	ProcessNodeConverter processNodeConverter = mock(ProcessNodeConverter.class);
	SubDiagramNodeConverter subDiagramNodeConverter = mock(SubDiagramNodeConverter.class);
	Batch batch = batchWithCampaignName("Parent Decision");
	when(commonProcessing.getSubDiagramNodeNames(batch)).thenReturn(Arrays.asList("Child One", "Child Two"));
	ApplicationService service = new ApplicationService(restTemplate, mock(MarshallerWrapper.class),
		converter, processNodeConverter, subDiagramNodeConverter, commonProcessing, treeUtil, mapStorage);

	assertEquals("Please ensure that all subdiagrams are migrated before proceeding with the migration of this campaign. "
		+ "The following campaigns need migrating first: Child One and Child Two",
		service.createDiagram("viya.example", batch, "login", "password", "http", "/folder", "true"));
	assertTrue(treeUtil.getObjIdToNodeIdMap().isEmpty());
	assertTrue(mapStorage.getNodeIdStepMap().isEmpty());
	verify(treeUtil).clearMaps();
	verify(treeUtil, never()).createTree(any(Batch.LogicalUnit.class));
	verifyNoInteractions(converter, processNodeConverter, subDiagramNodeConverter);
	verify(commonProcessing, never()).findDecision(any(String.class));
	verify(restTemplate, never()).postForEntity(any(URI.class), any(HttpEntity.class), eq(String.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"authentication", "precheck"})
    void clearsRequestStateAndPreservesExceptionBeforeConversion(String failureStage) {
	RestTemplate restTemplate = mock(RestTemplate.class);
	CommonProcessing commonProcessing = mock(CommonProcessing.class);
	TreeUtil treeUtil = spy(new TreeUtil());
	treeUtil.getObjIdToNodeIdMap().put((short) 1, "stale-node");
	MapStorage mapStorage = new MapStorage();
	mapStorage.getNodeIdStepMap().put("stale-node", Collections.emptyList());
	Batch batch = batchWithCampaignName("Parent Decision");
	RuntimeException failure;
	if ("authentication".equals(failureStage)) {
		failure = new OAuthTokenException("Authentication failed");
		when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(OAuthTokenResponse.class)))
			.thenThrow(failure);
	} else {
		stubToken(restTemplate);
		failure = new IllegalStateException("Subdiagram precheck failed");
		when(commonProcessing.getSubDiagramNodeNames(batch)).thenThrow(failure);
	}
	Converter converter = mock(Converter.class);
	ApplicationService service = new ApplicationService(restTemplate, mock(MarshallerWrapper.class),
		converter, mock(ProcessNodeConverter.class), mock(SubDiagramNodeConverter.class), commonProcessing,
		treeUtil, mapStorage);

	assertSame(failure, assertThrows(RuntimeException.class, () -> service.createDiagram("viya.example", batch,
		"login", "password", "http", "/folder", "true")));

	assertTrue(treeUtil.getObjIdToNodeIdMap().isEmpty());
	assertTrue(mapStorage.getNodeIdStepMap().isEmpty());
	verify(treeUtil).clearMaps();
	verify(treeUtil, never()).createTree(any(Batch.LogicalUnit.class));
	verify(commonProcessing, never()).findDecision(any(String.class));
	verifyNoInteractions(converter);
    }

    private OAuthTokenResponse stubToken(RestTemplate restTemplate) {
	OAuthTokenResponse token = OAuthTokenResponse.builder().accessToken("access-token").expiresIn(3600).build();
	when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(OAuthTokenResponse.class)))
		.thenReturn(ResponseEntity.ok(token));
	return token;
    }

    private ApplicationService serviceWith(RestTemplate restTemplate, MapStorage mapStorage) {
	return new ApplicationService(restTemplate, mock(MarshallerWrapper.class), mock(Converter.class),
		mock(ProcessNodeConverter.class), mock(SubDiagramNodeConverter.class), mock(CommonProcessing.class),
		mock(TreeUtil.class), mapStorage);
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
