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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "ancestorLinkNodes",
        "archivedPublishState",
        "cellAvailableForLinking",
        "cellControlGroupNumber",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "controlGroup",
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
        "marketingCell",
        "needToPersist",
        "nodeCode",
        "nodeId",
        "nodeName",
        "nodeState",
        "nodeTags",
        "nodeType",
        "oldVersionNumber",
        "outputNodes",
        "persisting",
        "publishState",
        "remainderCell",
        "sortByList",
        "splitters",
        "subjectID",
        "surrogateKeyNbr",
        "totalCount",
        "validInputSubject",
        "validOutputSubject",
        "versionNumber",
        "x",
        "y",
        "systemExtraValues"
})
public class CellNodeDataDO
        implements Serializable {

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "AncestorLinkNodes", required = true)
    protected CellNodeDataDO.AncestorLinkNodes ancestorLinkNodes;
    @XmlElement(name = "ArchivedPublishState")
    protected byte archivedPublishState;
    @XmlElement(name = "CellAvailableForLinking", required = true)
    protected String cellAvailableForLinking;
    @XmlElement(name = "CellControlGroupNumber")
    protected byte cellControlGroupNumber;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    protected String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    protected String codeEverBeenPublished;
    @XmlElement(name = "ControlGroup", required = true)
    protected String controlGroup;
    @XmlElement(name = "ControlKey")
    protected byte controlKey;
    @XmlElement(name = "DateModified", required = true)
    protected String dateModified;
    @XmlElement(name = "DroppedCount")
    protected byte droppedCount;
    @XmlElement(name = "ExcludeFlag", required = true)
    protected String excludeFlag;
    @XmlElement(name = "ExcludeIconPath", required = true)
    protected String excludeIconPath;
    @XmlElement(name = "FirstOccurrenceToPublish")
    protected byte firstOccurrenceToPublish;
    @XmlElement(name = "IconPath", required = true)
    protected String iconPath;
    @XmlElement(name = "InputNodes", required = true)
    protected CellNodeDataDO.InputNodes inputNodes;
    @XmlElement(name = "Label", required = true)
    protected String label;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    protected String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    protected String loading;
    @XmlElement(name = "MarketingCell", required = true)
    protected String marketingCell;
    @XmlElement(name = "NeedToPersist", required = true)
    protected String needToPersist;
    @XmlElement(name = "NodeCode", required = true)
    protected String nodeCode;
    @XmlElement(name = "NodeId", required = true)
    protected String nodeId;
    @XmlElement(name = "NodeName", required = true)
    protected String nodeName;
    @XmlElement(name = "NodeState")
    protected byte nodeState;
    @XmlElement(name = "NodeTags", required = true)
    protected CellNodeDataDO.NodeTags nodeTags;
    @XmlElement(name = "NodeType")
    protected byte nodeType;
    @XmlElement(name = "OldVersionNumber", required = true)
    protected String oldVersionNumber;
    @XmlElement(name = "OutputNodes", required = true)
    protected CellNodeDataDO.OutputNodes outputNodes;
    @XmlElement(name = "Persisting", required = true)
    protected String persisting;
    @XmlElement(name = "PublishState")
    protected byte publishState;
    @XmlElement(name = "RemainderCell", required = true)
    protected String remainderCell;
    @XmlElement(name = "SortByList", required = true)
    protected CellNodeDataDO.SortByList sortByList;
    @XmlElement(name = "Splitters", required = true)
    protected CellNodeDataDO.Splitters splitters;
    @XmlElement(name = "SubjectID", required = true)
    protected String subjectID;
    @XmlElement(name = "SurrogateKeyNbr")
    protected Short surrogateKeyNbr;
    @XmlElement(name = "TotalCount")
    protected byte totalCount;
    @XmlElement(name = "ValidInputSubject", required = true)
    protected String validInputSubject;
    @XmlElement(name = "ValidOutputSubject", required = true)
    protected String validOutputSubject;
    @XmlElement(name = "VersionNumber")
    protected float versionNumber;
    @XmlElement(name = "X")
    protected short x;
    @XmlElement(name = "Y")
    protected short y;
    @XmlElement(name = "SystemExtraValues", required = true)
    protected CellNodeDataDO.SystemExtraValues systemExtraValues;
    @XmlAttribute(name = "objid")
    protected Short objid;

       
    public CellNodeDataDO.AncestorLinkNodes getAncestorLinkNodes() {
        return ancestorLinkNodes;
    }

       
    public void setAncestorLinkNodes(CellNodeDataDO.AncestorLinkNodes value) {
        this.ancestorLinkNodes = value;
    }

       
    public byte getArchivedPublishState() {
        return archivedPublishState;
    }

       
    public void setArchivedPublishState(byte value) {
        this.archivedPublishState = value;
    }

       
    public String getCellAvailableForLinking() {
        return cellAvailableForLinking;
    }

       
    public void setCellAvailableForLinking(String value) {
        this.cellAvailableForLinking = value;
    }

       
    public byte getCellControlGroupNumber() {
        return cellControlGroupNumber;
    }

       
    public void setCellControlGroupNumber(byte value) {
        this.cellControlGroupNumber = value;
    }

       
    public String getCodeChangeUnpublished() {
        return codeChangeUnpublished;
    }

       
    public void setCodeChangeUnpublished(String value) {
        this.codeChangeUnpublished = value;
    }

       
    public String getCodeEverBeenPublished() {
        return codeEverBeenPublished;
    }

       
    public void setCodeEverBeenPublished(String value) {
        this.codeEverBeenPublished = value;
    }

       
    public String getControlGroup() {
        return controlGroup;
    }

       
    public void setControlGroup(String value) {
        this.controlGroup = value;
    }

       
    public byte getControlKey() {
        return controlKey;
    }

       
    public void setControlKey(byte value) {
        this.controlKey = value;
    }

       
    public String getDateModified() {
        return dateModified;
    }

       
    public void setDateModified(String value) {
        this.dateModified = value;
    }

       
    public byte getDroppedCount() {
        return droppedCount;
    }

       
    public void setDroppedCount(byte value) {
        this.droppedCount = value;
    }

       
    public String getExcludeFlag() {
        return excludeFlag;
    }

       
    public void setExcludeFlag(String value) {
        this.excludeFlag = value;
    }

       
    public String getExcludeIconPath() {
        return excludeIconPath;
    }

       
    public void setExcludeIconPath(String value) {
        this.excludeIconPath = value;
    }

       
    public byte getFirstOccurrenceToPublish() {
        return firstOccurrenceToPublish;
    }

       
    public void setFirstOccurrenceToPublish(byte value) {
        this.firstOccurrenceToPublish = value;
    }

       
    public String getIconPath() {
        return iconPath;
    }

       
    public void setIconPath(String value) {
        this.iconPath = value;
    }

       
    public CellNodeDataDO.InputNodes getInputNodes() {
        return inputNodes;
    }

       
    public void setInputNodes(CellNodeDataDO.InputNodes value) {
        this.inputNodes = value;
    }

       
    public String getLabel() {
        return label;
    }

       
    public void setLabel(String value) {
        this.label = value;
    }

       
    public String getLoadedFromOldDavLocation() {
        return loadedFromOldDavLocation;
    }

       
    public void setLoadedFromOldDavLocation(String value) {
        this.loadedFromOldDavLocation = value;
    }

       
    public String getLoading() {
        return loading;
    }

       
    public void setLoading(String value) {
        this.loading = value;
    }

       
    public String getMarketingCell() {
        return marketingCell;
    }

       
    public void setMarketingCell(String value) {
        this.marketingCell = value;
    }

       
    public String getNeedToPersist() {
        return needToPersist;
    }

       
    public void setNeedToPersist(String value) {
        this.needToPersist = value;
    }

       
    public String getNodeCode() {
        return nodeCode;
    }

       
    public void setNodeCode(String value) {
        this.nodeCode = value;
    }

       
    public String getNodeId() {
        return nodeId;
    }

       
    public void setNodeId(String value) {
        this.nodeId = value;
    }

       
    public String getNodeName() {
        return nodeName;
    }

       
    public void setNodeName(String value) {
        this.nodeName = value;
    }

       
    public byte getNodeState() {
        return nodeState;
    }

       
    public void setNodeState(byte value) {
        this.nodeState = value;
    }

       
    public CellNodeDataDO.NodeTags getNodeTags() {
        return nodeTags;
    }

       
    public void setNodeTags(CellNodeDataDO.NodeTags value) {
        this.nodeTags = value;
    }

       
    public byte getNodeType() {
        return nodeType;
    }

       
    public void setNodeType(byte value) {
        this.nodeType = value;
    }

       
    public String getOldVersionNumber() {
        return oldVersionNumber;
    }

       
    public void setOldVersionNumber(String value) {
        this.oldVersionNumber = value;
    }

       
    public CellNodeDataDO.OutputNodes getOutputNodes() {
        return outputNodes;
    }

       
    public void setOutputNodes(CellNodeDataDO.OutputNodes value) {
        this.outputNodes = value;
    }

       
    public String getPersisting() {
        return persisting;
    }

       
    public void setPersisting(String value) {
        this.persisting = value;
    }

       
    public byte getPublishState() {
        return publishState;
    }

       
    public void setPublishState(byte value) {
        this.publishState = value;
    }

       
    public String getRemainderCell() {
        return remainderCell;
    }

       
    public void setRemainderCell(String value) {
        this.remainderCell = value;
    }

       
    public CellNodeDataDO.SortByList getSortByList() {
        return sortByList;
    }

       
    public void setSortByList(CellNodeDataDO.SortByList value) {
        this.sortByList = value;
    }

       
    public CellNodeDataDO.Splitters getSplitters() {
        return splitters;
    }

       
    public void setSplitters(CellNodeDataDO.Splitters value) {
        this.splitters = value;
    }

       
    public String getSubjectID() {
        return subjectID;
    }

       
    public void setSubjectID(String value) {
        this.subjectID = value;
    }

       
    public Short getSurrogateKeyNbr() {
        return surrogateKeyNbr;
    }

       
    public void setSurrogateKeyNbr(Short value) {
        this.surrogateKeyNbr = value;
    }

       
    public byte getTotalCount() {
        return totalCount;
    }

       
    public void setTotalCount(byte value) {
        this.totalCount = value;
    }

       
    public String getValidInputSubject() {
        return validInputSubject;
    }

       
    public void setValidInputSubject(String value) {
        this.validInputSubject = value;
    }

       
    public String getValidOutputSubject() {
        return validOutputSubject;
    }

       
    public void setValidOutputSubject(String value) {
        this.validOutputSubject = value;
    }

       
    public float getVersionNumber() {
        return versionNumber;
    }

       
    public void setVersionNumber(float value) {
        this.versionNumber = value;
    }

       
    public short getX() {
        return x;
    }

       
    public void setX(short value) {
        this.x = value;
    }

       
    public short getY() {
        return y;
    }

       
    public void setY(short value) {
        this.y = value;
    }

       
    public CellNodeDataDO.SystemExtraValues getSystemExtraValues() {
        return systemExtraValues;
    }

       
    public void setSystemExtraValues(CellNodeDataDO.SystemExtraValues value) {
        this.systemExtraValues = value;
    }

       
    public Short getObjid() {
        return objid;
    }

       
    public void setObjid(Short value) {
        this.objid = value;
    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class AncestorLinkNodes
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

           
        public String getValue() {
            return value;
        }

           
        public void setValue(String value) {
            this.value = value;
        }

           
        public String getEmptyList() {
            return emptyList;
        }

           
        public void setEmptyList(String value) {
            this.emptyList = value;
        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class InputNodes
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


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class NodeTags
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

           
        public String getValue() {
            return value;
        }

           
        public void setValue(String value) {
            this.value = value;
        }

           
        public String getEmptyList() {
            return emptyList;
        }

           
        public void setEmptyList(String value) {
            this.emptyList = value;
        }

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


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class SortByList
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

           
        public String getValue() {
            return value;
        }

           
        public void setValue(String value) {
            this.value = value;
        }

           
        public String getEmptyList() {
            return emptyList;
        }

           
        public void setEmptyList(String value) {
            this.emptyList = value;
        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class Splitters
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

           
        public String getValue() {
            return value;
        }

           
        public void setValue(String value) {
            this.value = value;
        }

           
        public String getEmptyList() {
            return emptyList;
        }

           
        public void setEmptyList(String value) {
            this.emptyList = value;
        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "outputNodeName"
    })
    public static class SystemExtraValues
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "OutputNodeName", required = true)
        protected String outputNodeName;

           
        public String getOutputNodeName() {
            return outputNodeName;
        }

           
        public void setOutputNodeName(String value) {
            this.outputNodeName = value;
        }

    }

}