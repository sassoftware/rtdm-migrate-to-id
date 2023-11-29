/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm.extension;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "value"
})
public class Splitters implements Serializable {
    private static final long serialVersionUID = -1L;
    
    @XmlValue
    protected String value;
    @XmlAttribute(name = "EmptyList")
    protected String emptyList;
       
    public String getValue() {
        return value;
    }
       
    public void setValue(String value) {
        this.value = value;
    }
       
    public String getEmptyList() {
        return emptyList;
    }
       
    public void setEmptyList(String value) {
        this.emptyList = value;
    }

}
