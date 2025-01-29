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
        "campaignId",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "controlKey",
        "dateModified",
        "droppedCount",
        "excludeFlag",
        "excludeIconPath",
        "firstOccurrenceToPublish",
        "iconPath",
        "inputNodes",
        "label",
        "loadedFromOldDavLocation",
        "loading",
        "needToPersist",
        "nodeDescription",
        "nodeId",
        "nodeName",
        "nodeState",
        "nodeType",
        "oldVersionNumber",
        "outputNodes",
        "persisting",
        "publishState",
        "refreshNodeDataDO",
        "subdiagramEvent",
        "subdiagramFolder",
        "subdiagramId",
        "subdiagramName",
        "subjectID",
        "totalCount",
        "validInputSubject",
        "validOutputSubject",
        "versionNumber",
        "x",
        "y"
})
@Getter
@Setter
public class SubDiagramNodeDataDO implements Serializable {
    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ArchivedPublishState")
    private String archivedPublishState;
    @XmlElement(name = "CampaignId")
    private String campaignId;
    @XmlElement(name = "CodeChangeUnpublished")
    private String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished")
    private String codeEverBeenPublished;
    @XmlElement(name = "ControlKey")
    private String controlKey;
    @XmlElement(name = "DateModified")
    private String dateModified;
    @XmlElement(name = "DroppedCount")
    private String droppedCount;
    @XmlElement(name = "ExcludeFlag")
    private String excludeFlag;
    @XmlElement(name = "ExcludeIconPath")
    private String excludeIconPath;
    @XmlElement(name = "FirstOccurrenceToPublish")
    private String firstOccurrenceToPublish;
    @XmlElement(name = "IconPath")
    private String iconPath;
    @XmlElement(name = "InputNodes")
    private InputNodes inputNodes;
    @XmlElement(name = "Label")
    private String label;
    @XmlElement(name = "LoadedFromOldDavLocation")
    private String loadedFromOldDavLocation;
    @XmlElement(name = "Loading")
    private String loading;
    @XmlElement(name = "NeedToPersist")
    private String needToPersist;
    @XmlElement(name = "NodeDescription")
    private String nodeDescription;
    @XmlElement(name = "NodeId")
    private String nodeId;
    @XmlElement(name = "NodeName")
    private String nodeName;
    @XmlElement(name = "NodeState")
    private String nodeState;
    @XmlElement(name = "NodeType")
    private String nodeType;
    @XmlElement(name = "OldVersionNumber")
    private String oldVersionNumber;
    @XmlElement(name = "OutputNodes")
    private OutputNodes outputNodes;
    @XmlElement(name = "Persisting")
    private String persisting;
    @XmlElement(name = "PublishState")
    private String publishState;
    @XmlElement(name = "RefreshNodeDataDO")
    private String refreshNodeDataDO;
    @XmlElement(name = "SubdiagramEvent")
    private SubdiagramEvent subdiagramEvent;
    @XmlElement(name = "SubdiagramFolder")
    private SubdiagramFolder subdiagramFolder;
    @XmlElement(name = "SubdiagramId")
    private String subdiagramId;
    @XmlElement(name = "SubdiagramName")
    private String subdiagramName;
    @XmlElement(name = "SubjectID")
    private String subjectID;
    @XmlElement(name = "TotalCount")
    private String totalCount;
    @XmlElement(name = "ValidInputSubject")
    private String validInputSubject;
    @XmlElement(name = "ValidOutputSubject")
    private String validOutputSubject;
    @XmlElement(name = "VersionNumber")
    private String versionNumber;
    @XmlElement(name = "X")
    private String x;
    @XmlElement(name = "Y")
    private String y;
    @XmlAttribute(name = "objid")
    private Short objid;
}
