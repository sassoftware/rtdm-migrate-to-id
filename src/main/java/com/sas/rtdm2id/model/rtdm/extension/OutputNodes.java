/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm.extension;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "value"
})
@Getter
@Setter
public class OutputNodes implements Serializable {
    private final static long serialVersionUID = -1L;
    
    @XmlValue
    protected String value;
    @XmlAttribute(name = "ListTypes")
    protected String listTypes;

}