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
        "conditionalCount",
        "conditionalPercent",
        "count",
        "firstOccurrenceToPublish",
        "loadedFromOldDavLocation",
        "loading",
        "lowerBoundObject",
        "lowerBoundInclusive",
        "missingGroup",
        "needToPersist",
        "oldVersionNumber",
        "percent",
        "persisting",
        "priority",
        "publishState",
        "upperBoundObject",
        "upperBoundInclusive",
        "versionNumber"
})
@Getter
@Setter
public class IntervalLineItemDO implements Serializable {
    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ArchivedPublishState", required = true)
    private String archivedPublishState;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    private String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    private String codeEverBeenPublished;
    @XmlElement(name = "ConditionalCount", required = true)
    private String conditionalCount;
    @XmlElement(name = "conditionalPercent", required = true)
    private String conditionalPercent;
    @XmlElement(name = "Count", required = true)
    private String count;
    @XmlElement(name = "firstOccurrenceToPublish", required = true)
    private String firstOccurrenceToPublish;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    private String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    private String loading;
    @XmlElement(name = "LowerBound", required = true)
    LowerBound lowerBoundObject;
    @XmlElement(name = "LowerBoundInclusive", required = true)
    private String lowerBoundInclusive;
    @XmlElement(name = "MissingGroup", required = true)
    private String missingGroup;
    @XmlElement(name = "NeedToPersist", required = true)
    private String needToPersist;
    @XmlElement(name = "OldVersionNumber", required = true)
    private String oldVersionNumber;
    @XmlElement(name = "Percent", required = true)
    private String percent;
    @XmlElement(name = "Persisting", required = true)
    private String persisting;
    @XmlElement(name = "Priority", required = true)
    private String priority;
    @XmlElement(name = "PublishState", required = true)
    private String publishState;
    @XmlElement(name = "UpperBound", required = true)
    UpperBound upperBoundObject;
    @XmlElement(name = "UpperBoundInclusive", required = true)
    private String upperBoundInclusive;
    @XmlElement(name = "VersionNumber", required = true)
    private String versionNumber;
    @XmlAttribute(name = "listpos")
    private String listpos;
    @XmlAttribute(name = "objid")
    private String objid;
}

