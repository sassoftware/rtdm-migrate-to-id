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
public class MultiSelectNodeDataDO
        implements Serializable {
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

    public SQLExpression getSqlExpression() {
        return sqlExpression;
    }

    public void setSqlExpression(SQLExpression sqlExpression) {
        this.sqlExpression = sqlExpression;
    }

    public InputNodes getInputNodes() {
        return inputNodes;
    }

    public void setInputNodes(InputNodes inputNodes) {
        this.inputNodes = inputNodes;
    }

    public String getArchivedPublishState() {
        return archivedPublishState;
    }

    public void setArchivedPublishState(String archivedPublishState) {
        this.archivedPublishState = archivedPublishState;
    }

    public String getCodeChangeUnpublished() {
        return codeChangeUnpublished;
    }

    public void setCodeChangeUnpublished(String codeChangeUnpublished) {
        this.codeChangeUnpublished = codeChangeUnpublished;
    }

    public String getCodeEverBeenPublished() {
        return codeEverBeenPublished;
    }

    public void setCodeEverBeenPublished(String codeEverBeenPublished) {
        this.codeEverBeenPublished = codeEverBeenPublished;
    }

    public String getControlKey() {
        return controlKey;
    }

    public void setControlKey(String controlKey) {
        this.controlKey = controlKey;
    }

    public String getCreateRemainderOutputCell() {
        return createRemainderOutputCell;
    }

    public void setCreateRemainderOutputCell(String createRemainderOutputCell) {
        this.createRemainderOutputCell = createRemainderOutputCell;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDroppedCount() {
        return droppedCount;
    }

    public void setDroppedCount(String droppedCount) {
        this.droppedCount = droppedCount;
    }

    public String getExcludeFlag() {
        return excludeFlag;
    }

    public void setExcludeFlag(String excludeFlag) {
        this.excludeFlag = excludeFlag;
    }

    public String getExcludeIconPath() {
        return excludeIconPath;
    }

    public void setExcludeIconPath(String excludeIconPath) {
        this.excludeIconPath = excludeIconPath;
    }

    public String getFirstOccurrenceToPublish() {
        return firstOccurrenceToPublish;
    }

    public void setFirstOccurrenceToPublish(String firstOccurrenceToPublish) {
        this.firstOccurrenceToPublish = firstOccurrenceToPublish;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIsMigrated() {
        return isMigrated;
    }

    public void setIsMigrated(String isMigrated) {
        this.isMigrated = isMigrated;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLoadedFromOldDavLocation() {
        return loadedFromOldDavLocation;
    }

    public void setLoadedFromOldDavLocation(String loadedFromOldDavLocation) {
        this.loadedFromOldDavLocation = loadedFromOldDavLocation;
    }

    public String getLoading() {
        return loading;
    }

    public void setLoading(String loading) {
        this.loading = loading;
    }

    public String getNeedToPersist() {
        return needToPersist;
    }

    public void setNeedToPersist(String needToPersist) {
        this.needToPersist = needToPersist;
    }

    public String getNodeDescription() {
        return nodeDescription;
    }

    public void setNodeDescription(String nodeDescription) {
        this.nodeDescription = nodeDescription;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeState() {
        return nodeState;
    }

    public void setNodeState(String nodeState) {
        this.nodeState = nodeState;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getOldVersionNumber() {
        return oldVersionNumber;
    }

    public void setOldVersionNumber(String oldVersionNumber) {
        this.oldVersionNumber = oldVersionNumber;
    }

    public MultiSelectNodeDataDO.OutputNodes getOutputNodes() {
        return outputNodes;
    }

    public void setOutputNodes(MultiSelectNodeDataDO.OutputNodes value) {
        this.outputNodes = value;
    }

    public String getPersisting() {
        return persisting;
    }

    public void setPersisting(String persisting) {
        this.persisting = persisting;
    }

    public String getPublishState() {
        return publishState;
    }

    public void setPublishState(String publishState) {
        this.publishState = publishState;
    }

    public String getRefreshNodeDataDO() {
        return refreshNodeDataDO;
    }

    public void setRefreshNodeDataDO(String refreshNodeDataDO) {
        this.refreshNodeDataDO = refreshNodeDataDO;
    }

    public String getRemainderOutputCellID() {
        return remainderOutputCellID;
    }

    public void setRemainderOutputCellID(String remainderOutputCellID) {
        this.remainderOutputCellID = remainderOutputCellID;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getValidInputSubject() {
        return validInputSubject;
    }

    public void setValidInputSubject(String validInputSubject) {
        this.validInputSubject = validInputSubject;
    }

    public String getValidOutputSubject() {
        return validOutputSubject;
    }

    public void setValidOutputSubject(String validOutputSubject) {
        this.validOutputSubject = validOutputSubject;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public Short getObjid() {
        return objid;
    }

    public void setObjid(Short objid) {
        this.objid = objid;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class OutputNodes
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "ListTypes")
        protected String listTypes;

           
        public String getValue() {
            return value;
        }

           
        public void setValue(String value) {
            this.value = value;
        }

           
        public String getListTypes() {
            return listTypes;
        }

           
        public void setListTypes(String value) {
            this.listTypes = value;
        }

    }
}