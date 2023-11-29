/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.exception.ClientIdException;
import com.sas.rtdm2id.exception.OAuthTokenException;
import com.sas.rtdm2id.model.dto.rtdm.model.ClientIdResponse;
import com.sas.rtdm2id.model.dto.rtdm.model.OAuthTokenResponse;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.util.Converter;
import com.sas.rtdm2id.util.MarshallerWrapper;
import com.sas.rtdm2id.util.ViyaApi;
import com.sas.rtdm2id.util.object.processing.CommonProcessing;
import com.sas.rtdm2id.util.object.processing.ProcessNodeConverter;
import com.sas.rtdm2id.util.object.processing.SubDiagramNodeConverter;
import com.sas.rtdm2id.util.tree.impl.GenericTree;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApplicationService {
    Logger log = LoggerFactory.getLogger(ApplicationService.class);
    private final RestTemplate restTemplate;
    private final MarshallerWrapper marshallerWrapper;
    private final Converter converter;
    private final ProcessNodeConverter processNodeConverter;
    private final SubDiagramNodeConverter subDiagramNodeConverter;
    private final CommonProcessing commonProcessing;
    private final TreeUtil treeUtil;
    private final MapStorage mapStorage;
    Map<String, OAuthTokenResponse> oAuthTokenResponseMap = new HashMap<>();
    Map<String, Instant> expirationTimeMap = new HashMap<>();

    public ApplicationService(RestTemplate restTemplate, MarshallerWrapper marshallerWrapper, Converter converter, ProcessNodeConverter processNodeConverter, SubDiagramNodeConverter subDiagramNodeConverter, CommonProcessing commonProcessing, TreeUtil treeUtil, MapStorage mapStorage) {
        this.restTemplate = restTemplate;
        this.marshallerWrapper = marshallerWrapper;
        this.converter = converter;
        this.processNodeConverter = processNodeConverter;
        this.subDiagramNodeConverter = subDiagramNodeConverter;
        this.commonProcessing = commonProcessing;
        this.treeUtil = treeUtil;
        this.mapStorage = mapStorage;
    }

    public OAuthTokenResponse getAccessToken(String baseIp, String login, String password, String protocol) {
        if (oAuthTokenResponseMap.get(baseIp) != null && Instant.now().isBefore(expirationTimeMap.get(baseIp))) {
            return oAuthTokenResponseMap.get(baseIp);
        }
        try {
            ResponseEntity<OAuthTokenResponse> response = restTemplate.postForEntity(ViyaApi.createUri(baseIp, "/SASLogon/oauth/token", protocol),
                    ViyaApi.createGetAccessTokenHTTPEntity(login, password),
                    OAuthTokenResponse.class);
            if (response.getStatusCode().value() != 200) {
                throw new OAuthTokenException("Could not get OAuth token: invalid user");
            }
            OAuthTokenResponse oAuthTokenResponse = response.getBody();
            oAuthTokenResponseMap.put(baseIp, oAuthTokenResponse);

            Instant expirationTime = Instant.now().plusSeconds(oAuthTokenResponse.getExpiresIn());
            expirationTimeMap.put(baseIp, expirationTime);
            mapStorage.setBaseIp(baseIp);
            mapStorage.setAccessToken(oAuthTokenResponse.getAccessToken());
            mapStorage.setProtocol(protocol);
            return oAuthTokenResponse;
        } catch (URISyntaxException | HttpClientErrorException e) {
            throw new OAuthTokenException("Could not get OAuth token: invalid user");
        }
    }

    public String createDiagram(String baseIp, Batch batch, String login, String password, String protocol, String parentFolderUri) {
        OAuthTokenResponse oAuthTokenResponse = getAccessToken(baseIp, login, password, protocol);
        if (oAuthTokenResponse == null) {
            return "Could not get access token for ip = " + baseIp + " try to get access token again!";
        }

        Decision decision = null;
        ResponseEntity<String> response = null;
        String decisionJson = null;

        mapStorage.setParentFolderUri(parentFolderUri);

        try {
            if (!commonProcessing.findDecision(batch.getLogicalUnit().getCampaignDO().getName())) {
                GenericTree<Short> tree = treeUtil.createTree(batch.getLogicalUnit());
                String accessToken = oAuthTokenResponse.getAccessToken();
                processNodeConverter.createCustomNodes(batch);
                commonProcessing.createRuleSets(batch);
                subDiagramNodeConverter.getSubdiagrams(batch);
                decision = converter.createDecision(batch, tree);
                
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                decisionJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(decision);

                // TODO: Remove later, useful for debugging very large json files too big for console
                objectMapper.writeValue(new File("decision.json"), decision);  

                URI uri = ViyaApi.createUri(baseIp, "/decisions/flows?parentFolderUri=" + mapStorage.getParentFolderUri(), protocol);
                HttpEntity<String> diagramHTTPEntity = ViyaApi.createDiagramHTTPEntity(decisionJson, accessToken);
                response = restTemplate.postForEntity(uri, diagramHTTPEntity, String.class);

                if (response.getStatusCode().value() == 201) {
                    return decision.getName() + " migrated to ID successfully";
                } else {
                    log.error(decision.getName() + " failed to migrate with HTTP response code " + response.getStatusCode());
                    log.error(response.getBody());
                    log.error(decisionJson);
                    return decision.getName() + " failed to migrate";
                }
            } else {
                return "Decision already exists, existing decision was not overwritten";
            }

        } catch (Exception e) {
            if (decision!=null) {
                log.error(decision.getName() + " failed to migrate");
            }

            if (response!=null) {
                log.error(response.getBody());
            }
            
            if (decisionJson!=null) {
                log.error(decisionJson);
            }

            e.printStackTrace();

            return e.getMessage();
        }
        finally {
            treeUtil.clearMaps();
            mapStorage.clearListsAndMaps();
        }
    }

    public ClientIdResponse registerClient(String baseIp, String consulToken, String clientId, String clientSecret, String protocol) {
        try {
            String registrationAccessToken = getRegistrationToken(baseIp, consulToken, clientId, protocol).getAccessToken();
            ResponseEntity<ClientIdResponse> response = restTemplate.postForEntity(ViyaApi.createUri(baseIp, "/SASLogon/oauth/clients",mapStorage.getProtocol()),
                    ViyaApi.createRegisterNewClientIdHttpEntity(registrationAccessToken, clientId, clientSecret),
                    ClientIdResponse.class);
            if (response.getStatusCode().value() != 201) {
                throw new ClientIdException();
            }
            return response.getBody();
        } catch (Exception e) {
            throw new ClientIdException(e);
        }
    }

    public OAuthTokenResponse getRegistrationToken(String baseIp, String consulToken, String serviceId, String protocol) {
        try {
            ResponseEntity<OAuthTokenResponse> response = restTemplate.postForEntity(ViyaApi.createUri(baseIp, "/SASLogon/oauth/clients/consul?callback=false&serviceId=" + serviceId, protocol),
                    ViyaApi.createGetRegistrationTokenHTTPEntity(consulToken),
                    OAuthTokenResponse.class);
            if (response.getStatusCode().value() != 200) {
                throw new OAuthTokenException();
            }
            mapStorage.setBaseIp(baseIp);
            mapStorage.setAccessToken(response.getBody().getAccessToken());
            mapStorage.setProtocol(protocol);
            return response.getBody();
        } catch (Exception e) {
            throw new OAuthTokenException(e);
        }
    }
}
