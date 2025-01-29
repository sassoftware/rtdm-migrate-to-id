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
        "formattedValue",
        "isMetadata",
        "loadedFromOldDavLocation",
        "loading",
        "needToPersist",
        "oldVersionNumber",
        "percent",
        "persisting",
        "priority",
        "publishState",
        "selected",
        "value",
        "versionNumber"
})
@Getter
@Setter
public class DiscreteLineItemDO implements Serializable {
    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ArchivedPublishState", required = true)
    private String archivedPublishState;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    private String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    private String codeEverBeenPublished;
    @XmlElement(name = "ConditionalCount", required = true)
    private String conditionalCount;
    @XmlElement(name = "ConditionalPercent", required = true)
    private String conditionalPercent;
    @XmlElement(name = "Count", required = true)
    private String count;
    @XmlElement(name = "FirstOccurrenceToPublish", required = true)
    private String firstOccurrenceToPublish;
    @XmlElement(name = "FormattedValue", required = true)
    private String formattedValue;
    @XmlElement(name = "IsMetadata", required = true)
    private String isMetadata;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    private String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    private String loading;
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
    @XmlElement(name = "Selected", required = true)
    private String selected;
    @XmlElement(name = "Value", required = true)
    private DiscreteLineItemValue value;
    @XmlElement(name = "VersionNumber", required = true)
    private String versionNumber;
    @XmlAttribute(name = "ConcreteClass")
    private String concreteClass;
}
