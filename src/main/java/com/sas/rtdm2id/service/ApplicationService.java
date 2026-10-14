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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
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
    Map<List<String>, OAuthTokenResponse> oAuthTokenResponseMap = new HashMap<>();
    Map<List<String>, Instant> expirationTimeMap = new HashMap<>();

    static final long TOKEN_REFRESH_MARGIN_SECONDS = 300;

    @Value("${rtdm2id.debug.write-decision-json:false}")
    boolean writeDecisionJson;

    @Value("${rtdm2id.debug.decision-json-path:decision.json}")
    String decisionJsonPath = "decision.json";

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
        String effectiveProtocol = normalizeProtocol(protocol);
        List<String> cacheKey = tokenCacheKey(baseIp, effectiveProtocol, login, password);
        Instant now = Instant.now();
        evictExpiredTokens(now);
        OAuthTokenResponse oAuthTokenResponse = oAuthTokenResponseMap.get(cacheKey);
        Instant expirationTime = expirationTimeMap.get(cacheKey);
        if (oAuthTokenResponse == null || expirationTime == null
                || !now.isBefore(expirationTime.minusSeconds(refreshMarginSeconds(oAuthTokenResponse.getExpiresIn())))) {
            try {
                ResponseEntity<OAuthTokenResponse> response = restTemplate.postForEntity(ViyaApi.createUri(baseIp, "/SASLogon/oauth/token", effectiveProtocol),
                        ViyaApi.createGetAccessTokenHTTPEntity(login, password),
                        OAuthTokenResponse.class);
                if (response.getStatusCode().value() != 200 || response.getBody() == null) {
                    throw new OAuthTokenException("Could not get OAuth token: invalid user");
                }
                oAuthTokenResponse = response.getBody();
                expirationTime = Instant.now().plusSeconds(oAuthTokenResponse.getExpiresIn());
                oAuthTokenResponseMap.put(cacheKey, oAuthTokenResponse);
                expirationTimeMap.put(cacheKey, expirationTime);
            } catch (URISyntaxException | HttpClientErrorException e) {
                throw new OAuthTokenException("Could not get OAuth token: invalid user");
            } catch (RestClientException e) {
                // connection, TLS and 5xx failures; the message never contains the credentials (they are sent in the body)
                throw new OAuthTokenException("Could not get OAuth token from " + effectiveProtocol + "://" + baseIp
                        + ": " + e.getMessage(), e);
            }
        }
        mapStorage.setBaseIp(baseIp);
        mapStorage.setAccessToken(oAuthTokenResponse.getAccessToken());
        mapStorage.setProtocol(effectiveProtocol);
        return oAuthTokenResponse;
    }

    private static String normalizeProtocol(String protocol) {
        return "https".equalsIgnoreCase(protocol) ? "https" : "http";
    }

    /**
     * Token cache key. The password is included as a SHA-256 hash so that a request with a different
     * (e.g. wrong) password never reuses a token, without keeping the plain password in memory.
     */
    static List<String> tokenCacheKey(String baseIp, String effectiveProtocol, String login, String password) {
        return Arrays.asList(baseIp, effectiveProtocol, login, sha256(password));
    }

    private static String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(String.valueOf(value).getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }

    /**
     * Seconds before expiry at which a cached token is refreshed: five minutes, but at most half of
     * the token lifetime so that short-lived tokens are still reused.
     */
    static long refreshMarginSeconds(long expiresIn) {
        return Math.max(0, Math.min(TOKEN_REFRESH_MARGIN_SECONDS, expiresIn / 2));
    }

    private void evictExpiredTokens(Instant now) {
        expirationTimeMap.entrySet().removeIf(entry -> {
            if (now.isBefore(entry.getValue())) {
                return false;
            }
            oAuthTokenResponseMap.remove(entry.getKey());
            return true;
        });
    }

    public String createDiagram(String baseIp, Batch batch, String login, String password, String protocol, String parentFolderUri, String useCrossBranchLinks) {
        try {
            return migrateDiagram(baseIp, batch, login, password, protocol, parentFolderUri, useCrossBranchLinks);
        } finally {
            treeUtil.clearMaps();
            mapStorage.clearListsAndMaps();
        }
    }

    private String migrateDiagram(String baseIp, Batch batch, String login, String password, String protocol, String parentFolderUri, String useCrossBranchLinks) {
        OAuthTokenResponse oAuthTokenResponse = getAccessToken(baseIp, login, password, protocol);
        if (oAuthTokenResponse == null) {
            return "Could not get access token for ip = " + baseIp + " try to get access token again!";
        }

        Decision decision = null;
        ResponseEntity<String> response = null;
        String decisionJson = null;

        mapStorage.setParentFolderUri(parentFolderUri);
        mapStorage.setUseCrossBranchLinks(Boolean.parseBoolean(useCrossBranchLinks));

        List<String> subDiagramsThatDoNotExist = commonProcessing.getSubDiagramNodeNames(batch);

        if (!subDiagramsThatDoNotExist.isEmpty()) {
            return "Please ensure that all subdiagrams are migrated before proceeding with the migration of this campaign. The following campaigns need migrating first: "
                    + String.join(" and ", subDiagramsThatDoNotExist);
        }

        try {
            String decisionName = batch.getLogicalUnit().getCampaignDO().getName();
            if (!commonProcessing.findDecision(decisionName)) {
                GenericTree<Short> tree = treeUtil.createTree(batch.getLogicalUnit());
                String accessToken = oAuthTokenResponse.getAccessToken();
                // Update columns become SET inputs before anything (SQL, RuleSets, steps, planning) reads the inputs
                ProcessNodeConverter.normalizeUpdateDataProcesses(batch);
                processNodeConverter.createCustomNodes(batch);
                commonProcessing.createRuleSets(batch);
                subDiagramNodeConverter.getSubdiagrams(batch);
                decision = converter.createDecision(batch, tree);

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
                decisionJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(decision);

                if (writeDecisionJson) {
                    // useful for debugging very large json files too big for console
                    writeDecisionJsonFile(decisionJson);
                }

                URI uri = ViyaApi.createUri(baseIp, "/decisions/flows?parentFolderUri="
                        + ViyaApi.encodeQueryParam(mapStorage.getParentFolderUri()), protocol);
                HttpEntity<String> diagramHTTPEntity = ViyaApi.createDiagramHTTPEntity(decisionJson, accessToken);
                response = restTemplate.postForEntity(uri, diagramHTTPEntity, String.class);

                if (response.getStatusCode().value() == 201) {
                    return decision.getName() + " migrated to ID successfully";
                } else {
                    log.error("{} failed to migrate with HTTP response code {}: {}", decision.getName(),
                            response.getStatusCode(), response.getBody());
                    log.debug("Decision JSON for {}: {}", decision.getName(), decisionJson);
                    return decision.getName() + " failed to migrate";
                }
            } else {
                return "Decision '" + decisionName + "' already exists, existing decision was not overwritten";
            }

        } catch (Exception e) {
            String name = decision != null ? decision.getName() : "Campaign";
            if (response != null) {
                log.error("{} failed to migrate, Viya response: {}", name, response.getBody(), e);
            } else {
                log.error("{} failed to migrate", name, e);
            }
            if (decisionJson != null) {
                log.debug("Decision JSON for {}: {}", name, decisionJson);
            }

            return e.getMessage();
        }
    }

    private void writeDecisionJsonFile(String decisionJson) {
        // a debug aid only: failures must never abort a migration whose Viya artifacts already exist
        try {
            Path path = Path.of(decisionJsonPath);
            Files.writeString(path, decisionJson, StandardCharsets.UTF_8);
            log.info("Decision JSON written to {}", path.toAbsolutePath());
        } catch (IOException | RuntimeException e) {
            log.warn("Could not write decision JSON to {}: {}", decisionJsonPath, e.toString());
        }
    }

    public ClientIdResponse registerClient(String baseIp, String consulToken, String clientId, String clientSecret, String protocol) {
        try {
            String registrationAccessToken = getRegistrationToken(baseIp, consulToken, clientId, protocol).getAccessToken();
            ResponseEntity<ClientIdResponse> response = restTemplate.postForEntity(ViyaApi.createUri(baseIp, "/SASLogon/oauth/clients", normalizeProtocol(protocol)),
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
            ResponseEntity<OAuthTokenResponse> response = restTemplate.postForEntity(ViyaApi.createUri(baseIp,
                            "/SASLogon/oauth/clients/consul?callback=false&serviceId=" + ViyaApi.encodeQueryParam(serviceId),
                            normalizeProtocol(protocol)),
                    ViyaApi.createGetRegistrationTokenHTTPEntity(consulToken),
                    OAuthTokenResponse.class);
            if (response.getStatusCode().value() != 200 || response.getBody() == null) {
                throw new OAuthTokenException();
            }
            // the registration token is only used by registerClient, so it is not kept in the
            // shared MapStorage request context
            return response.getBody();
        } catch (Exception e) {
            throw new OAuthTokenException(e);
        }
    }
}
