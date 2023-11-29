/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.StringWriter;

@Component
public class MarshallerWrapper {
    @Autowired
    private Jaxb2Marshaller marshaller;

    // marshalls one object (of your bound classes) into a String.
    public <T> String marshallXml(final T obj) {
        StringWriter sw = new StringWriter();
        Result result = new StreamResult(sw);
        marshaller.marshal(obj, result);
        return sw.toString();
    }

    // (tries to) unmarshall(s) an InputStream to the desired object.
    @SuppressWarnings("unchecked")
    public <T> T unmarshallXml(final InputStream xml) {
        return (T) marshaller.unmarshal(new StreamSource(xml));
    }
}
