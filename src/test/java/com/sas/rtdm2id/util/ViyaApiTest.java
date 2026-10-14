package com.sas.rtdm2id.util;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ViyaApiTest {

    @Test
    void keepsSimpleFilterReadable() throws Exception {
        assertEquals(URI.create("https://viya.example/businessRules/ruleSets?filter=eq(name,'My%20Rules')"),
                ViyaApi.createUriWithParams("viya.example", "/businessRules/ruleSets", "name", "My Rules", "https"));
    }

    @Test
    void doublesSingleQuotesInFilterValue() throws Exception {
        URI uri = ViyaApi.createUriWithParams("viya.example", "/decisions/flows", "name", "Bob's Offer", "http");

        assertEquals("eq(name,'Bob''s Offer')", filterOf(uri));
    }

    @Test
    void encodesReservedCharactersInFilterValue() throws Exception {
        URI uri = ViyaApi.createUriWithParams("viya.example", "/decisions/flows", "description", "a&b=c+d%e#f", "http");

        assertEquals("eq(description,'a&b=c+d%e#f')", filterOf(uri));
        assertEquals("/decisions/flows", uri.getPath());
    }

    @Test
    void encodesQueryParameterValues() {
        assertEquals("/folders/folders/@myFolder", ViyaApi.encodeQueryParam("/folders/folders/@myFolder"));
        assertEquals("a%20b%26c%3Dd%2Be%25f%23g", ViyaApi.encodeQueryParam("a b&c=d+e%f#g"));
    }

    @Test
    void formEncodesCredentials() {
        HttpEntity<String> entity = ViyaApi.createGetAccessTokenHTTPEntity("user&admin=1", "p+ss%w&rd é");

        assertEquals("grant_type=password&username=user%26admin%3D1&password=p%2Bss%25w%26rd+%C3%A9", entity.getBody());
    }

    private static String filterOf(URI uri) {
        String raw = UriComponentsBuilder.fromUri(uri).build(true).getQueryParams().getFirst("filter");
        // decode as the server does, where an unencoded '+' would become a space
        return URLDecoder.decode(raw, StandardCharsets.UTF_8);
    }
}
