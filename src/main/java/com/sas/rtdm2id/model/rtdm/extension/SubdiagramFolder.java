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
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "firstOccurrenceToPublish",
        "loadedFromOldDavLocation",
        "loading",
        "name",
        "needToPersist",
        "oldVersionNumber",
        "owner",
        "parentFolder",
        "persisting",
        "publishState",
        "versionNumber"
})
@Getter
@Setter
public class SubdiagramFolder implements Serializable {
    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ArchivedPublishState")
    private String archivedPublishState;
    @XmlElement(name = "CodeChangeUnpublished")
    private String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished")
    private String codeEverBeenPublished;
    @XmlElement(name = "FirstOccurrenceToPublish")
    private String firstOccurrenceToPublish;
    @XmlElement(name = "LoadedFromOldDavLocation")
    private String loadedFromOldDavLocation;
    @XmlElement(name = "Loading")
    private String loading;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "NeedToPersist")
    private String needToPersist;
    @XmlElement(name = "OldVersionNumber")
    private String oldVersionNumber;
    @XmlElement(name = "Owner")
    private String owner;
    @XmlElement(name = "ParentFolder")
    private String parentFolder;
    @XmlElement(name = "Persisting")
    private String persisting;
    @XmlElement(name = "PublishState")
    private String publishState;
    @XmlElement(name = "VersionNumber")
    private String versionNumber;
    @XmlAttribute(name = "ConcreteClass")
    private String concreteClass;
    @XmlAttribute(name = "objid")
    private Short objid;
}
