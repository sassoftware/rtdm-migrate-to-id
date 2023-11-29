/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm.extension;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "value"
})
public class InputNodes
        implements Serializable {

    private final static long serialVersionUID = -1L;
    @XmlValue
    protected String value;
    @XmlAttribute(name = "ListTypes")
    protected String listTypes;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getListTypes() {
        return listTypes;
    }

    public void setListTypes(String listTypes) {
        this.listTypes = listTypes;
    }
}
