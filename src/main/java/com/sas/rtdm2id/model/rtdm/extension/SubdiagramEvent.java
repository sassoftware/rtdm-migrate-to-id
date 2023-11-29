/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm.extension;

import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "archivedPublishState",
        "attachments",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "complexType",
        "dateModified",
        "defaultTimeout",
        "description",
        "firstOccurrenceToPublish",
        "forceOverwrite",
        "forced",
        "hidden",
        "id",
        "loadedFromOldDavLocation",
        "loading",
        "lockedBy",
        "metadataType",
        "name",
        "needToPersist",
        "noWritePermission",
        "oldVersionNumber",
        "persistState",
        "persisting",
        "physicalName",
        "publishState",
        "rdmDateModified",
        "readOnly",
        "replyVars",
        "requestVars",
        "shared",
        "timeout",
        "timeoutEnabled",
        "type",
        "typeDescription",
        "versionNumber",
        "whoModified"
})
@Getter
@Setter
public class SubdiagramEvent implements Serializable {
    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ArchivedPublishState")
    private String archivedPublishState;
    @XmlElement(name = "Attachments")
    private String attachments;
    @XmlElement(name = "CodeChangeUnpublished")
    private String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished")
    private String codeEverBeenPublished;
    @XmlElement(name = "ComplexType")
    private String complexType;
    @XmlElement(name = "DateModified")
    private String dateModified;
    @XmlElement(name = "DefaultTimeout")
    private String defaultTimeout;
    @XmlElement(name = "Description")
    private String description;
    @XmlElement(name = "FirstOccurrenceToPublish")
    private String firstOccurrenceToPublish;
    @XmlElement(name = "ForceOverwrite")
    private String forceOverwrite;
    @XmlElement(name = "Forced")
    private String forced;
    @XmlElement(name = "Hidden")
    private String hidden;
    @XmlElement(name = "Id")
    private String id;
    @XmlElement(name = "LoadedFromOldDavLocation")
    private String loadedFromOldDavLocation;
    @XmlElement(name = "Loading")
    private String loading;
    @XmlElement(name = "LockedBy")
    private String lockedBy;
    @XmlElement(name = "MetadataType")
    private String metadataType;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "NeedToPersist")
    private String needToPersist;
    @XmlElement(name = "NoWritePermission")
    private String noWritePermission;
    @XmlElement(name = "OldVersionNumber")
    private String oldVersionNumber;
    @XmlElement(name = "PersistState")
    private String persistState;
    @XmlElement(name = "Persisting")
    private String persisting;
    @XmlElement(name = "PhysicalName")
    private String physicalName;
    @XmlElement(name = "PublishState")
    private String publishState;
    @XmlElement(name = "RdmDateModified")
    private String rdmDateModified;
    @XmlElement(name = "ReadOnly")
    private String readOnly;
    @XmlElement(name = "ReplyVars")
    ProcessNodeDataDO.Process.OutputVariableList replyVars;
    @XmlElement(name = "RequestVars")
    ProcessNodeDataDO.Process.InputVariableList requestVars;
    @XmlElement(name = "Shared")
    private String shared;
    @XmlElement(name = "Timeout")
    private String timeout;
    @XmlElement(name = "TimeoutEnabled")
    private String timeoutEnabled;
    @XmlElement(name = "Type")
    private String type;
    @XmlElement(name = "TypeDescription")
    private String typeDescription;
    @XmlElement(name = "VersionNumber")
    private String versionNumber;
    @XmlElement(name = "WhoModified")
    private String whoModified;
    @XmlAttribute(name = "concreteClass")
    private String concreteClass;
    @XmlAttribute(name = "objid")
    private Short objid;
}
