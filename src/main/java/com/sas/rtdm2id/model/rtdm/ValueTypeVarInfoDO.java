/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "archivedPublishState",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "firstOccurrenceToPublish",
        "loadedFromOldDavLocation",
        "loading",
        "needToPersist",
        "oldVersionNumber",
        "persisting",
        "publishState",
        "varInfoArray",
        "varInfoId",
        "varName",
        "varInfoSourceNodeId",
        "varInfoPhysicalName",
        "varInfoSource",
        "varInfoSourceName",
        "varInfoSubtype",
        "type",
        "versionNumber",
        "stringValue"
})
@Getter
@Setter
public class ValueTypeVarInfoDO implements Serializable {

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ArchivedPublishState")
    protected byte archivedPublishState;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    protected String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    protected String codeEverBeenPublished;
    @XmlElement(name = "FirstOccurrenceToPublish")
    protected byte firstOccurrenceToPublish;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    protected String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    protected String loading;
    @XmlElement(name = "NeedToPersist", required = true)
    protected String needToPersist;
    @XmlElement(name = "OldVersionNumber", required = true)
    protected String oldVersionNumber;
    @XmlElement(name = "Persisting", required = true)
    protected String persisting;
    @XmlElement(name = "PublishState")
    protected byte publishState;
    @XmlElement(name = "VarInfoArray", required = true)
    protected String varInfoArray;
    @XmlElement(name = "VarInfoId", required = true)
    protected String varInfoId;
    @XmlElement(name = "VarInfoName", required = true)
    protected String varName;
    @XmlElement(name = "VarInfoSourceNodeId", required = false)
    protected String varInfoSourceNodeId;
    @XmlElement(name = "VarInfoPhysicalName", required = true)
    protected String varInfoPhysicalName;
    @XmlElement(name = "VarInfoSource", required = true)
    protected String varInfoSource;
    @XmlElement(name = "VarInfoSourceName", required = true)
    protected String varInfoSourceName;
    @XmlElement(name = "VarInfoSubtype", required = true)
    protected String varInfoSubtype;
    @XmlElement(name = "VarInfoType", required = true)
    protected String type;
    @XmlElement(name = "VersionNumber")
    protected float versionNumber;
    @XmlElement(name = "StringValue")
    protected String stringValue;
    @XmlAttribute(name = "ConcreteClass")
    protected String concreteClass;
    @XmlAttribute(name = "objid")
    protected Short objid;

}