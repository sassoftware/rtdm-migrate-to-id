/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import com.sas.rtdm2id.model.rtdm.extension.InputNodes;
import com.sas.rtdm2id.model.rtdm.extension.SQLExpression;
import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "sqlExpression",
        "archivedPublishState",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "controlKey",
        "createRemainderOutputCell",
        "dateModified",
        "droppedCount",
        "excludeFlag",
        "excludeIconPath",
        "firstOccurrenceToPublish",
        "iconPath",
        "inputNodes",
        "isMigrated",
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
        "remainderOutputCellID",
        "subjectID",
        "totalCount",
        "validInputSubject",
        "validOutputSubject",
        "versionNumber",
        "x",
        "y",
        "objid"
})
@Getter
@Setter
public class MultiSelectNodeDataDO implements Serializable {
    private final static long serialVersionUID = -1L;
    @XmlElement(name = "SQLExpression")
    private SQLExpression sqlExpression;
    @XmlElement(name = "ArchivedPublishState")
    private String archivedPublishState;
    @XmlElement(name = "CodeChangeUnpublished")
    private String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished")
    private String codeEverBeenPublished;
    @XmlElement(name = "ControlKey")
    private String controlKey;
    @XmlElement(name = "CreateRemainderOutputCell")
    private String createRemainderOutputCell;
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
    @XmlElement(name = "IsMigrated")
    private String isMigrated;
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
    @XmlElement(name = "OutputNodes", required = true)
    protected MultiSelectNodeDataDO.OutputNodes outputNodes;
    @XmlElement(name = "Persisting")
    private String persisting;
    @XmlElement(name = "PublishState")
    private String publishState;
    @XmlElement(name = "RefreshNodeDataDO")
    private String refreshNodeDataDO;
    @XmlElement(name = "RemainderOutputCellID")
    private String remainderOutputCellID;
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

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    @Getter
    @Setter
    public static class OutputNodes implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "ListTypes")
        protected String listTypes;

    }
}