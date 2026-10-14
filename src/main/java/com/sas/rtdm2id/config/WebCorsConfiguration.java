/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * Cross-origin access to the migration API. No origins are allowed unless they are listed in
 * {@code rtdm2id.cors.allowed-origins} (comma-separated), because requests carry Viya credentials.
 */
@Configuration
public class WebCorsConfiguration implements WebMvcConfigurer {
    private final String[] allowedOrigins;

    public WebCorsConfiguration(@Value("${rtdm2id.cors.allowed-origins:}") String[] allowedOrigins) {
        this.allowedOrigins = Arrays.stream(allowedOrigins)
                .map(String::trim)
                .filter(origin -> !origin.isEmpty())
                .toArray(String[]::new);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (allowedOrigins.length == 0) {
            return;
        }
        registry.addMapping("/api/rtdm2id/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("POST");
    }
}
