/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm.extension;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "storedType",
        "text"
})
@Getter
@Setter
public class LowerBound {
    @XmlAttribute(name = "StoredType")
    private String storedType;
    @XmlValue
    private String text;
}
