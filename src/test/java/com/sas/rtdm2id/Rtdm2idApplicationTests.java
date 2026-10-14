/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id;

import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.service.ApplicationService;
import com.sas.rtdm2id.util.MarshallerWrapper;
import com.sas.rtdm2id.util.object.processing.CommonProcessing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Rtdm2idApplicationTests {

    @Autowired
    private MarshallerWrapper marshallerWrapper;

    @Autowired
    private CommonProcessing commonProcessing;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private HttpComponentsClientHttpRequestFactory viyaRequestFactory;

    @Test
    void contextLoads() {
        assertEquals(true, ReflectionTestUtils.getField(commonProcessing, "consolidateCalculatedRuleSets"));
    }

    @Test
    void appliesDebugAndTimeoutDefaults() {
        assertEquals(false, ReflectionTestUtils.getField(applicationService, "writeDecisionJson"));
        assertEquals("decision.json", ReflectionTestUtils.getField(applicationService, "decisionJsonPath"));
        assertEquals(Duration.ofMinutes(10).toMillis(), ReflectionTestUtils.getField(viyaRequestFactory, "readTimeout"));
    }

    @Test
    void defaultsConsolidationOnAndAllowsExplicitOptOut() {
        assertEquals(true, ReflectionTestUtils.getField(new CommonProcessing(null, null, null), "consolidateCalculatedRuleSets"));
        ApplicationContextRunner runner = new ApplicationContextRunner()
            .withBean(CommonProcessing.class, () -> new CommonProcessing(null, null, null));
        runner.run(context -> assertEquals(true,
            ReflectionTestUtils.getField(context.getBean(CommonProcessing.class), "consolidateCalculatedRuleSets")));
        runner.withPropertyValues("rtdm2id.consolidate-calculated-rulesets=false")
            .run(context -> assertEquals(false,
                ReflectionTestUtils.getField(context.getBean(CommonProcessing.class), "consolidateCalculatedRuleSets")));
    }

    @Test
    void roundTripsRtdmXmlWithJakartaJaxb() {
        String xml = "<Batch><LogicalUnit/></Batch>";
        Batch batch = marshallerWrapper.unmarshallXml(
                new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
        assertNotNull(batch.getLogicalUnit());

        String marshalled = marshallerWrapper.marshallXml(batch);
        Batch roundTrip = marshallerWrapper.unmarshallXml(
                new ByteArrayInputStream(marshalled.getBytes(StandardCharsets.UTF_8)));
        assertNotNull(roundTrip.getLogicalUnit());
    }

}
