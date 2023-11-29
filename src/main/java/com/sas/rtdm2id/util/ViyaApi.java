/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util;

import com.sas.rtdm2id.model.dto.rtdm.model.ClientIdRegisterBody;
import com.sas.rtdm2id.model.id.files.DecisionCodeFile;
import com.sas.rtdm2id.model.id.files.File;
import com.sas.rtdm2id.model.id.globals.GlobalVariable;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.SCALAR_CONSTANT;

@Component
public class ViyaApi {
    private static final String BEARER_START = "Bearer ";

    private ViyaApi() {
    }

    public static URI createUri(String baseIp, String contextPath, String protocol) throws URISyntaxException {
        String protocolPrefix = "http://";
        if ("https".equalsIgnoreCase(protocol)){
            protocolPrefix = "https://";
        }
        String baseUrl = protocolPrefix + baseIp + contextPath;
        return new URI(baseUrl);
    }

    public static URI createUriWithParams(String baseIp, String contextPath, String parameter, String filter, String protocol) throws URISyntaxException {
        String protocolPrefix = "http://";
        if ("https".equalsIgnoreCase(protocol)){
            protocolPrefix = "https://";
        }
        String baseUrl = protocolPrefix + baseIp + contextPath;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("filter", "eq(" + parameter + ",'" + filter + "')");
        return new URI(builder.toUriString());
    }

    public static HttpEntity<String> createGetAccessTokenHTTPEntity(String login, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic c2FzLmVjOg==");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String valor = "grant_type=password&username=" + login + "&password=" + password;
        return new HttpEntity<>(valor, headers);
    }

    public static HttpEntity<String> createDiagramHTTPEntity(String decision, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.sas.decision+json");
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_START + accessToken);
        return new HttpEntity<>(decision, headers);
    }

    public static HttpEntity<String> createFilesFilesHTTPEntity(String ds2code, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_START + accessToken);
        return new HttpEntity<>(ds2code, headers);
    }

    public static HttpEntity<File> createPatchFileRequestHTTPEntity(String name, String etag, String lastModified, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.sas.file+json");
        headers.setIfMatch(etag);
        headers.set(HttpHeaders.IF_UNMODIFIED_SINCE, lastModified);
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_START + accessToken);
        File file = new File();
        file.setName(name);
        file.getProperties().put("outputType", SCALAR_CONSTANT);
        return new HttpEntity<>(file, headers);
    }

    public static HttpEntity<Void> createGetFilesFilesHTTPEntity(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "text/plain");
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_START + accessToken);
        return new HttpEntity<>(headers);
    }

    public static HttpEntity<GlobalVariable> createGlobalVariables(GlobalVariable globalVariable, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_START + accessToken);
        return new HttpEntity<>(globalVariable, headers);
    }

    public static HttpEntity<File> createPutFilesFiles(String etag, String accessToken, ProcessNodeDataDO.Process process) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.sas.file+json");
        headers.setIfMatch(etag);
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_START + accessToken);
        File file = new File();
        file.setName(process.getName());
        file.setDescription(process.getId());
        return new HttpEntity<>(file, headers);
    }

    public static HttpEntity<DecisionCodeFile> createDecisionCodeFiles(String location, String type, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_START + accessToken);
        DecisionCodeFile decisionCodeFile = new DecisionCodeFile(location, type);
        return new HttpEntity<>(decisionCodeFile, headers);
    }

    public static HttpEntity<RuleSet> createRule(RuleSet ruleSet, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.sas.business.rule.set+json");
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_START + accessToken);
        return new HttpEntity<>(ruleSet, headers);
    }

    public static HttpEntity<Rule> createRuleWithAction(Rule rule, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_START + accessToken);
        return new HttpEntity<>(rule, headers);
    }

    public static HttpEntity<RuleSet> createPutRule(RuleSet ruleSet, String accessToken, Date modifiedDate) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.sas.business.rule.set+json");
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_START + accessToken);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String format = sdf.format(modifiedDate);
        headers.set(HttpHeaders.IF_UNMODIFIED_SINCE, format);
        return new HttpEntity<>(ruleSet, headers);
    }

    public static HttpEntity<Rule> createRuleInsideRuleSet(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.sas.business.rule+json");
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_START + accessToken);
        Rule rule = new Rule();
        return new HttpEntity<>(rule, headers);
    }

    public static HttpEntity<Object> createGetByNameForCodeFiles(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.sas.collection+json");
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_START + accessToken);
        return new HttpEntity<>(headers);
    }

    public static HttpEntity<Object> createGetDecisionCollectionOrGlobalVariableRevision(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_START + accessToken);
        return new HttpEntity<>(headers);
    }

    public static HttpEntity<String> activateGlobalVariable(String accessToken, String ifMatch, String globalVariableRevisionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, BEARER_START + accessToken);
        headers.setIfMatch(ifMatch);
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new HttpEntity<>(globalVariableRevisionId, headers);
    }

    public static HttpEntity<String> createGetRegistrationTokenHTTPEntity(String consulToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Consul-Token", consulToken);
        return new HttpEntity<>(headers);
    }

    public static HttpEntity<ClientIdRegisterBody> createRegisterNewClientIdHttpEntity(String registrationAccessToken, String clientId, String clientSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(registrationAccessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        ClientIdRegisterBody body = ClientIdRegisterBody.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope(Collections.singletonList("openid"))
                .resourceIds("none")
                .authorities(Collections.singletonList("uaa.none"))
                .authorizedGrantTypes(Collections.singletonList("password"))
                .build();
        return new HttpEntity<>(body, headers);
    }
}
