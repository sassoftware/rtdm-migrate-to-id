package com.sas.rtdm2id.controller;

import com.sas.rtdm2id.exception.ClientIdException;
import com.sas.rtdm2id.exception.OAuthTokenException;
import com.sas.rtdm2id.model.dto.rtdm.model.ClientIdResponse;
import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApplicationControllerTest {
    private ApplicationService service;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        service = mock(ApplicationService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ApplicationController(service))
                .setControllerAdvice(new RestControllerAdvisor()).build();
    }

    @Test
    void unmarshalsXmlAndAppliesMigrationDefaults() throws Exception {
        when(service.createDiagram(anyString(), any(Batch.class), anyString(), anyString(),
                anyString(), anyString(), anyString())).thenReturn("Campaign migrated to ID successfully");

        mockMvc.perform(diagramRequest())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.meta.code").value(0))
                .andExpect(jsonPath("$.meta.description").value("OK"))
                .andExpect(jsonPath("$.dataBlock").value("Campaign migrated to ID successfully"));

        ArgumentCaptor<Batch> batch = ArgumentCaptor.forClass(Batch.class);
        verify(service).createDiagram(eq("viya.example"), batch.capture(), eq("login"), eq("password"),
                eq("http"), eq("/folders/folders/@myFolder"), eq("true"));
        assertNotNull(batch.getValue().getLogicalUnit());
    }

    @Test
    void forwardsExplicitMigrationOptions() throws Exception {
        mockMvc.perform(diagramRequest().param("protocol", "https")
                .param("parentFolderUri", "/folders/folders/custom").param("useCrossBranchLinks", "false"))
                .andExpect(status().isCreated());

        verify(service).createDiagram(eq("viya.example"), any(Batch.class), eq("login"), eq("password"),
                eq("https"), eq("/folders/folders/custom"), eq("false"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"baseIp", "login", "password"})
    void rejectsMissingRequiredMigrationParameter(String missingParameter) throws Exception {
        MockHttpServletRequestBuilder request = post("/api/rtdm2id/create-diagram")
                .contentType(MediaType.APPLICATION_XML).content("<Batch><LogicalUnit/></Batch>");
        for (String parameter : new String[] {"baseIp", "login", "password"}) {
            if (!parameter.equals(missingParameter)) {
                request.param(parameter, "test-value");
            }
        }

        mockMvc.perform(request).andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void rejectsMalformedXmlBeforeCallingService() throws Exception {
        mockMvc.perform(diagramRequest().content("<Batch>"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void rejectsJsonForXmlMigrationEndpoint() throws Exception {
        mockMvc.perform(diagramRequest().contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isUnsupportedMediaType());

        verifyNoInteractions(service);
    }

    @Test
    void returnsErrorEnvelopeWhenAuthenticationFails() throws Exception {
        when(service.createDiagram(anyString(), any(Batch.class), anyString(), anyString(),
                anyString(), anyString(), anyString())).thenThrow(new OAuthTokenException("Authentication failed"));

        mockMvc.perform(diagramRequest())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meta.code").value(1))
                .andExpect(jsonPath("$.meta.description").value("Authentication failed"))
                .andExpect(jsonPath("$.dataBlock").doesNotExist());
    }

    @Test
    void registersClientWithDefaultProtocolAndReturnsClientEnvelope() throws Exception {
        ClientIdResponse client = ClientIdResponse.builder().clientId("migration-client").build();
        when(service.registerClient("viya.example", "test-consul-token", "migration-client", "test-client-secret", "http"))
                .thenReturn(client);

        mockMvc.perform(registrationRequest())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.meta.code").value(0))
                .andExpect(jsonPath("$.dataBlock.client_id").value("migration-client"));

        verify(service).registerClient("viya.example", "test-consul-token", "migration-client", "test-client-secret", "http");
    }

    @Test
    void forwardsExplicitRegistrationProtocol() throws Exception {
        mockMvc.perform(registrationRequest().param("protocol", "https"))
                .andExpect(status().isCreated());

        verify(service).registerClient("viya.example", "test-consul-token", "migration-client", "test-client-secret", "https");
    }

    @Test
    void rejectsRegistrationWithoutClientSecret() throws Exception {
        mockMvc.perform(post("/api/rtdm2id/register-client").param("baseIp", "viya.example")
                .param("consulToken", "test-consul-token")
                .param("clientId", "migration-client"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void rejectsLegacyRegistrationParameterNames() throws Exception {
        mockMvc.perform(post("/api/rtdm2id/register-client").param("baseIp", "viya.example")
                .param("Consul token got from Viya server", "test-consul-token")
                .param("Id of new user", "migration-client").param("Secret of new user", "test-client-secret"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }

    @Test
    void returnsErrorEnvelopeWhenClientRegistrationFails() throws Exception {
        when(service.registerClient(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenThrow(new ClientIdException());

        mockMvc.perform(registrationRequest())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meta.code").value(1))
                .andExpect(jsonPath("$.meta.description").value("Client ID cannot be created"))
                .andExpect(jsonPath("$.dataBlock").doesNotExist());
    }

    private MockHttpServletRequestBuilder diagramRequest() {
        return post("/api/rtdm2id/create-diagram").param("baseIp", "viya.example")
                .param("login", "login").param("password", "password")
                .contentType(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_JSON)
                .content("<Batch><LogicalUnit/></Batch>");
    }

    private MockHttpServletRequestBuilder registrationRequest() {
        return post("/api/rtdm2id/register-client").param("baseIp", "viya.example")
                .param("consulToken", "test-consul-token")
                .param("clientId", "migration-client").param("clientSecret", "test-client-secret")
                .accept(MediaType.APPLICATION_JSON);
    }
}