/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm.extension;

import lombok.Getter;
import lombok.Setter;

import jakarta.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "listDelimiter",
        "listTypes",
        "text"
})
@Getter
@Setter
public class Values implements Serializable {

    private final static long serialVersionUID = -1L;

    // Separator between list entries, e.g. "," or ",!" (used when entries such as formatted dates contain commas)
    @XmlAttribute(name = "ListDelimiter")
    private String listDelimiter;
    @XmlAttribute(name = "ListTypes")
    private String listTypes;
    @XmlValue
    private String text;

}
