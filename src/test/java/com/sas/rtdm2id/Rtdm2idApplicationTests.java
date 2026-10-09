/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id;

import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.util.MarshallerWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class Rtdm2idApplicationTests {

    @Autowired
    private MarshallerWrapper marshallerWrapper;

    @Test
    void contextLoads() {
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
