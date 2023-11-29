/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm.extension;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "listTypes",
        "text"
})
public class Values implements Serializable {
    private final static long serialVersionUID = -1L;
    @XmlAttribute(name = "ListTypes")
    private String listTypes;
    @XmlValue
    private String text;

    public String getListTypes() {
        return listTypes;
    }

    public void setListTypes(String listTypes) {
        this.listTypes = listTypes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
