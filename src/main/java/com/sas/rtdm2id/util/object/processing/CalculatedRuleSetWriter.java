package com.sas.rtdm2id.util.object.processing;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.CodeFileCollection;
import com.sas.rtdm2id.model.id.decision.RuleSetStep;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.id.rules.RuleSet;
import com.sas.rtdm2id.model.id.rules.Signature;
import com.sas.rtdm2id.util.ViyaApi;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.Map;

final class CalculatedRuleSetWriter {
    static final String DESCRIPTION = "rtdm2id calculated preparation v1";
    private final RestTemplate restTemplate;
    private final MapStorage storage;
    // Viya rule representations carry links and other fields the Rule model does not declare.
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    CalculatedRuleSetWriter(RestTemplate restTemplate, MapStorage storage) {
        this.restTemplate = restTemplate;
        this.storage = storage;
    }

    static String fingerprint(String consumerId, RuleSet ruleSet, Rule rule) {
        try {
            Map<String, Object> content = new LinkedHashMap<>();
            content.put("version", DESCRIPTION);
            content.put("consumer", consumerId);
            content.put("signature", ruleSet.getSignature());
            content.put("actions", rule.getActions());
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(new ObjectMapper().writeValueAsBytes(content));
            return HexFormat.of().formatHex(digest).substring(0, 16);
        } catch (Exception exception) {
            throw new IllegalStateException("Cannot identify calculated preparation", exception);
        }
    }

    void write(RuleSet expected, Rule rule, Step step) {
        try {
            String path = "/businessRules/ruleSets";
            ResponseEntity<CodeFileCollection> lookup = restTemplate.exchange(
                ViyaApi.createUriWithParams(storage.getBaseIp(), path, "name", expected.getName(), storage.getProtocol()),
                HttpMethod.GET, ViyaApi.createGetByNameForCodeFiles(storage.getAccessToken()), CodeFileCollection.class);
            require(lookup.getStatusCode().value() == 200 && lookup.getBody() != null, "RuleSet lookup failed");
            CodeFileCollection collection = lookup.getBody();
            String id;
            if (collection.getCount() == 0) {
                RuleSet shell = new RuleSet();
                shell.setName(expected.getName());
                shell.setDescription(DESCRIPTION);
                shell.setRuleSetType("assignment");
                shell.setSignature(new ArrayList<>());
                ResponseEntity<RuleSet> created = restTemplate.postForEntity(uri(path + "?parentFolderUri=" + ViyaApi.encodeQueryParam(storage.getParentFolderUri())),
                    ViyaApi.createRule(shell, storage.getAccessToken()), RuleSet.class);
                require(created.getStatusCode().value() == 201 && created.getBody() != null
                    && created.getBody().getId() != null && created.getBody().getModifiedTimeStamp() != null,
                    "RuleSet shell creation failed");
                id = created.getBody().getId();
                ResponseEntity<RuleSet> updated = restTemplate.exchange(uri(path + "/" + id), HttpMethod.PUT,
                    ViyaApi.createPutRule(expected, storage.getAccessToken(), created.getBody().getModifiedTimeStamp()), RuleSet.class);
                require(updated.getStatusCode().value() == 200, "RuleSet signature update failed");
                ResponseEntity<Rule> populated = restTemplate.exchange(uri(path + "/" + id + "/rules"), HttpMethod.POST,
                    ViyaApi.createRuleWithAction(rule, storage.getAccessToken()), Rule.class);
                require(populated.getStatusCode().value() == 201, "RuleSet action creation failed");
            } else {
                require(collection.getCount() == 1 && collection.getItems() != null && collection.getItems().size() == 1,
                    "Ambiguous existing grouped RuleSet");
                id = collection.getItems().get(0).getId();
                require(id != null, "Existing grouped RuleSet has no ID");
                verifyExisting(path + "/" + id, expected, rule);
            }
            RuleSetStep reference = new RuleSetStep();
            reference.setName(expected.getName());
            reference.setId(id);
            step.setRuleSet(reference);
            storage.getRuleSetStepMap().put(expected.getName(), step);
            storage.getRuleSetIsUpdated().put(expected.getName(), true);
            storage.getRuleSetIsActionUpdated().put(expected.getName(), true);
        } catch (Exception exception) {
            throw new IllegalStateException("Could not prepare grouped RuleSet " + expected.getName()
                + "; verify remote contents before retrying", exception);
        }
    }

    private void verifyExisting(String path, RuleSet expected, Rule rule) throws Exception {
        ResponseEntity<RuleSet> response = restTemplate.exchange(uri(path), HttpMethod.GET,
            ViyaApi.createGetDecisionCollectionOrGlobalVariableRevision(storage.getAccessToken()), RuleSet.class);
        RuleSet actual = response.getBody();
        require(response.getStatusCode().value() == 200 && actual != null
            && expected.getName().equals(actual.getName()) && DESCRIPTION.equals(actual.getDescription())
            && "assignment".equals(actual.getRuleSetType()) && actual.getSignature() != null
            && actual.getSignature().size() == expected.getSignature().size(), "Incomplete grouped RuleSet signature");
        for (Signature term : expected.getSignature()) {
            long matches = actual.getSignature().stream().filter(candidate -> term.getName().equals(candidate.getName())
                && term.getDataType().equals(candidate.getDataType()) && term.getDirection().equals(candidate.getDirection())).count();
            require(matches == 1, "Incompatible grouped RuleSet signature");
        }
        ResponseEntity<String> rules = restTemplate.exchange(uri(path + "/rules?limit=2"), HttpMethod.GET,
            ViyaApi.createGetByNameForCodeFiles(storage.getAccessToken()), String.class);
        require(rules.getStatusCode().value() == 200 && rules.getBody() != null, "Grouped RuleSet rule lookup failed");
        JsonNode collection = mapper.readTree(rules.getBody());
        JsonNode items = collection.path("items");
        require(collection.path("count").asInt(-1) == 1 && items.isArray() && items.size() == 1,
            "Incomplete grouped RuleSet actions");
        JsonNode item = items.get(0);
        if (!item.has("actions")) {
            // Collection items may be summaries; read the full rule before comparing its actions.
            String ruleId = item.path("id").asText("");
            require(!ruleId.isEmpty(), "Grouped RuleSet rule has no ID");
            ResponseEntity<String> fullRule = restTemplate.exchange(uri(path + "/rules/" + ruleId), HttpMethod.GET,
                ViyaApi.createGetDecisionCollectionOrGlobalVariableRevision(storage.getAccessToken()), String.class);
            require(fullRule.getStatusCode().value() == 200 && fullRule.getBody() != null, "Grouped RuleSet rule lookup failed");
            item = mapper.readTree(fullRule.getBody());
        }
        Rule actualRule = mapper.treeToValue(item, Rule.class);
        // Fields Viya omits when they hold their defaults are treated as those defaults.
        require(rule.getName().equals(actualRule.getName())
            && (actualRule.getConditional() == null || "if".equals(actualRule.getConditional()))
            && !Boolean.TRUE.equals(actualRule.getRuleFiredTrackingEnabled())
            && (actualRule.getConditions() == null || actualRule.getConditions().isEmpty())
            && actualRule.getActions() != null && actualRule.getActions().size() == rule.getActions().size(),
            "Incompatible grouped RuleSet rule");
        for (int index = 0; index < rule.getActions().size(); index++) {
            String type = actualRule.getActions().get(index).getType();
            require(rule.getActions().get(index).getExpression().equals(actualRule.getActions().get(index).getExpression())
                && (type == null || "complex".equals(type)), "Incompatible grouped action order");
        }
    }

    private URI uri(String path) throws Exception {
        return ViyaApi.createUri(storage.getBaseIp(), path, storage.getProtocol());
    }

    private static void require(boolean valid, String message) {
        if (!valid) {
            throw new IllegalStateException(message);
        }
    }
}