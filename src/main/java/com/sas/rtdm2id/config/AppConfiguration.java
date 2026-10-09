/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.config;

import com.sas.rtdm2id.model.rtdm.Batch;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Map;

@Configuration
public class AppConfiguration {
    @Bean
    public HttpComponentsClientHttpRequestFactory viyaRequestFactory() {
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
            .setConnectTimeout(Timeout.ofMilliseconds(1000000))
            .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
            HttpClients.custom().useSystemProperties()
                .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                    .useSystemProperties()
                    .setDefaultConnectionConfig(connectionConfig)
                    .build())
                .build());
        requestFactory.setReadTimeout(Duration.ofMillis(1000000));
        return requestFactory;
    }

    @Bean
    public RestTemplate restTemplate(HttpComponentsClientHttpRequestFactory viyaRequestFactory) {
        return new RestTemplate(viyaRequestFactory);
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(//all the classes the context needs to know about
                Batch.class);

        marshaller.setMarshallerProperties(Map.of(jakarta.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true));

        return marshaller;
    }
}
