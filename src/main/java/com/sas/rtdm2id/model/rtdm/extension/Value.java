/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm.extension;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessorType;
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
        "booleanValue",
        "dateValue",
        "dateValueString",
        "doubleValue",
        "longValue",
        "stringValue",
        "listValue",
        "type",
        "valueTypeVarInfoDO",
        "versionNumber"
})
@Getter
@Setter
public class Value implements Serializable {

    private static final long serialVersionUID = -1L;

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
    @XmlElement(name = "BooleanValue")
    protected String booleanValue;
    @XmlElement(name = "DateValue")
    protected String dateValue;
    @XmlElement(name = "DateValueString")
    protected String dateValueString;    
    @XmlElement(name = "DoubleValue")
    protected String doubleValue;
    @XmlElement(name = "LongValue")
    protected String longValue;
    @XmlElement(name = "StringValue")
    protected String stringValue;
    @XmlElement(name = "ListValue")
    protected String listValue;
    @XmlElement(name = "Type")
    protected byte type;
    @XmlElement(name = "ValueTypeVarInfoDO", required = true)
    protected ValueTypeVarInfoDO valueTypeVarInfoDO;
    @XmlElement(name = "VersionNumber")
    protected float versionNumber;
    @XmlAttribute(name = "ConcreteClass")
    protected String concreteClass;
    @XmlAttribute(name = "objid")
    protected Short objid;

}
