/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm.extension;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "rootNode",
        "versionNumber"
})
@Getter
@Setter
public class SQLExpression implements Serializable {
    private final static long serialVersionUID = -1L;
    @XmlElement(name = "RootNode", required = true)
    RootNode rootNode;
    @XmlElement(name = "VersionNumber", required = true)
    private String versionNumber;

}
