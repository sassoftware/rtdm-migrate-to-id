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
        "archivedPublishState",
        "calculatedItemType",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "description",
        "expression",
        "firstOccurrenceToPublish",
        "forced",
        "hidden",
        "id",
        "imapId",
        "loadedFromOldDavLocation",
        "loading",
        "lockedBy",
        "metadataType",
        "name",
        "needToPersist",
        "noWritePermission",
        "nodeId",
        "oldVersionNumber",
        "persisting",
        "publishState",
        "readOnly",
        "usageCount",
        "versionNumber",
        "whoModified"
})
@Getter
@Setter
public class CalculatedItemDO implements Serializable {
    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ArchivedPublishState", required = true)
    private String archivedPublishState;
    @XmlElement(name = "CalculatedItemType", required = true)
    private String calculatedItemType;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    private String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    private String codeEverBeenPublished;
    @XmlElement(name = "Description", required = true)
    private String description;
    @XmlElement(name = "Expression", required = true)
    private String expression;
    @XmlElement(name = "FirstOccurrenceToPublish", required = true)
    private String firstOccurrenceToPublish;
    @XmlElement(name = "Forced", required = true)
    private String forced;
    @XmlElement(name = "Hidden", required = true)
    private String hidden;
    @XmlElement(name = "Id", required = true)
    private String id;
    @XmlElement(name = "ImapId", required = true)
    private String imapId;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    private String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    private String loading;
    @XmlElement(name = "LockedBy", required = true)
    private String lockedBy;
    @XmlElement(name = "MetadataType", required = true)
    private String metadataType;
    @XmlElement(name = "Name", required = true)
    private String name;
    @XmlElement(name = "NeedToPersist", required = true)
    private String needToPersist;
    @XmlElement(name = "NoWritePermission", required = true)
    private String noWritePermission;
    @XmlElement(name = "NodeId", required = true)
    private String nodeId;
    @XmlElement(name = "OldVersionNumber", required = true)
    private String oldVersionNumber;
    @XmlElement(name = "Persisting", required = true)
    private String persisting;
    @XmlElement(name = "PublishState", required = true)
    private String publishState;
    @XmlElement(name = "ReadOnly", required = true)
    private String readOnly;
    @XmlElement(name = "UsageCount", required = true)
    private String usageCount;
    @XmlElement(name = "VersionNumber", required = true)
    private String versionNumber;
    @XmlElement(name = "WhoModified", required = true)
    private String whoModified;
    @XmlAttribute(name = "ConcreteClass")
    private String concreteClass;
    @XmlAttribute(name = "listpos")
    private String listpos;
    @XmlAttribute(name = "objid")
    private Short objid;
}
