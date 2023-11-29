/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import com.sas.rtdm2id.model.rtdm.extension.DiscreteLineItemDO;
import com.sas.rtdm2id.model.rtdm.extension.IntervalLineItemDO;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "archivedPublishState",
        "assignmentFunction",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "controlGroupCreated",
        "controlGroupPercent",
        "controlKey",
        "dateModified",
        "droppedCount",
        "enableSeed",
        "excludeFlag",
        "excludeIconPath",
        "firstOccurrenceToPublish",
        "haveSplitCriteria",
        "iconPath",
        "initialNodeDataUpdateDone",
        "inputNodes",
        "label",
        "limitGroups",
        "lineItems",
        "loadedFromOldDavLocation",
        "loading",
        "maxGroups",
        "needToPersist",
        "nodeDescription",
        "nodeId",
        "nodeName",
        "nodeState",
        "nodeTags",
        "nodeType",
        "numGroups",
        "oldVersionNumber",
        "outputCellsAvailableForLinking",
        "outputNodes",
        "persisting",
        "publishState",
        "readyToUpdateCounts",
        "rebuildCells",
        "sortByList",
        "splitCriteriaList",
        "splitOnNodeDataDO",
        "splitOnNodeID",
        "splitType",
        "splitters",
        "subjectID",
        "totalCount",
        "validInputSubject",
        "validOutputSubject",
        "versionNumber",
        "x",
        "y",
        "systemExtraValues"
})
public class SplitNodeDataDO
        implements Serializable {

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ArchivedPublishState")
    protected byte archivedPublishState;
    @XmlElement(name = "AssignmentFunction", required = true)
    protected String assignmentFunction;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    protected String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    protected String codeEverBeenPublished;
    @XmlElement(name = "ControlGroupCreated", required = true)
    protected String controlGroupCreated;
    @XmlElement(name = "ControlGroupPercent", required = true)
    protected String controlGroupPercent;
    @XmlElement(name = "ControlKey")
    protected int controlKey;
    @XmlElement(name = "DateModified", required = true)
    protected String dateModified;
    @XmlElement(name = "DroppedCount")
    protected byte droppedCount;
    @XmlElement(name = "EnableSeed", required = true)
    protected String enableSeed;
    @XmlElement(name = "ExcludeFlag", required = true)
    protected String excludeFlag;
    @XmlElement(name = "ExcludeIconPath", required = true)
    protected String excludeIconPath;
    @XmlElement(name = "FirstOccurrenceToPublish")
    protected byte firstOccurrenceToPublish;
    @XmlElement(name = "HaveSplitCriteria", required = true)
    protected String haveSplitCriteria;
    @XmlElement(name = "IconPath", required = true)
    protected String iconPath;
    @XmlElement(name = "InitialNodeDataUpdateDone", required = true)
    protected String initialNodeDataUpdateDone;
    @XmlElement(name = "InputNodes", required = true)
    protected SplitNodeDataDO.InputNodes inputNodes;
    @XmlElement(name = "Label", required = true)
    protected String label;
    @XmlElement(name = "LimitGroups", required = true)
    protected String limitGroups;
    @XmlElement(name = "LineItems", required = true)
    protected SplitNodeDataDO.LineItems lineItems;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    protected String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    protected String loading;
    @XmlElement(name = "MaxGroups")
    protected byte maxGroups;
    @XmlElement(name = "NeedToPersist", required = true)
    protected String needToPersist;
    @XmlElement(name = "NodeDescription", required = true)
    protected String nodeDescription;
    @XmlElement(name = "NodeId", required = true)
    protected String nodeId;
    @XmlElement(name = "NodeName", required = true)
    protected String nodeName;
    @XmlElement(name = "NodeState")
    protected byte nodeState;
    @XmlElement(name = "NodeTags", required = true)
    protected SplitNodeDataDO.NodeTags nodeTags;
    @XmlElement(name = "NodeType")
    protected byte nodeType;
    @XmlElement(name = "NumGroups")
    protected byte numGroups;
    @XmlElement(name = "OldVersionNumber", required = true)
    protected String oldVersionNumber;
    @XmlElement(name = "OutputCellsAvailableForLinking", required = true)
    protected String outputCellsAvailableForLinking;
    @XmlElement(name = "OutputNodes", required = true)
    protected SplitNodeDataDO.OutputNodes outputNodes;
    @XmlElement(name = "Persisting", required = true)
    protected String persisting;
    @XmlElement(name = "PublishState")
    protected byte publishState;
    @XmlElement(name = "ReadyToUpdateCounts", required = true)
    protected String readyToUpdateCounts;
    @XmlElement(name = "RebuildCells", required = true)
    protected String rebuildCells;
    @XmlElement(name = "SortByList", required = true)
    protected SplitNodeDataDO.SortByList sortByList;
    @XmlElement(name = "SplitCriteriaList", required = true)
    protected SplitNodeDataDO.SplitCriteriaList splitCriteriaList;
    @XmlElement(name = "SplitOnNodeDataDO", required = true)
    protected SplitNodeDataDO.SplitOnNodeDataDO splitOnNodeDataDO;
    @XmlElement(name = "SplitOnNodeID", required = true)
    protected String splitOnNodeID;
    @XmlElement(name = "SplitType", required = true)
    protected String splitType;
    @XmlElement(name = "Splitters", required = true)
    protected SplitNodeDataDO.Splitters splitters;
    @XmlElement(name = "SubjectID", required = true)
    protected String subjectID;
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
    protected SplitNodeDataDO.SystemExtraValues systemExtraValues;
    @XmlAttribute(name = "objid")
    protected Short objid;

       
    public byte getArchivedPublishState() {
        return archivedPublishState;
    }

       
    public void setArchivedPublishState(byte value) {
        this.archivedPublishState = value;
    }

       
    public String getAssignmentFunction() {
        return assignmentFunction;
    }

       
    public void setAssignmentFunction(String value) {
        this.assignmentFunction = value;
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

       
    public String getControlGroupCreated() {
        return controlGroupCreated;
    }

       
    public void setControlGroupCreated(String value) {
        this.controlGroupCreated = value;
    }

       
    public String getControlGroupPercent() {
        return controlGroupPercent;
    }

       
    public void setControlGroupPercent(String value) {
        this.controlGroupPercent = value;
    }

       
    public int getControlKey() {
        return controlKey;
    }

       
    public void setControlKey(int value) {
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

       
    public String getEnableSeed() {
        return enableSeed;
    }

       
    public void setEnableSeed(String value) {
        this.enableSeed = value;
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

       
    public String getHaveSplitCriteria() {
        return haveSplitCriteria;
    }

       
    public void setHaveSplitCriteria(String value) {
        this.haveSplitCriteria = value;
    }

       
    public String getIconPath() {
        return iconPath;
    }

       
    public void setIconPath(String value) {
        this.iconPath = value;
    }

       
    public String getInitialNodeDataUpdateDone() {
        return initialNodeDataUpdateDone;
    }

       
    public void setInitialNodeDataUpdateDone(String value) {
        this.initialNodeDataUpdateDone = value;
    }

       
    public SplitNodeDataDO.InputNodes getInputNodes() {
        return inputNodes;
    }

       
    public void setInputNodes(SplitNodeDataDO.InputNodes value) {
        this.inputNodes = value;
    }

       
    public String getLabel() {
        return label;
    }

       
    public void setLabel(String value) {
        this.label = value;
    }

       
    public String getLimitGroups() {
        return limitGroups;
    }

       
    public void setLimitGroups(String value) {
        this.limitGroups = value;
    }

       
    public SplitNodeDataDO.LineItems getLineItems() {
        return lineItems;
    }

       
    public void setLineItems(SplitNodeDataDO.LineItems value) {
        this.lineItems = value;
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

       
    public byte getMaxGroups() {
        return maxGroups;
    }

       
    public void setMaxGroups(byte value) {
        this.maxGroups = value;
    }

       
    public String getNeedToPersist() {
        return needToPersist;
    }

       
    public void setNeedToPersist(String value) {
        this.needToPersist = value;
    }

       
    public String getNodeDescription() {
        return nodeDescription;
    }

       
    public void setNodeDescription(String value) {
        this.nodeDescription = value;
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

       
    public SplitNodeDataDO.NodeTags getNodeTags() {
        return nodeTags;
    }

       
    public void setNodeTags(SplitNodeDataDO.NodeTags value) {
        this.nodeTags = value;
    }

       
    public byte getNodeType() {
        return nodeType;
    }

       
    public void setNodeType(byte value) {
        this.nodeType = value;
    }

       
    public byte getNumGroups() {
        return numGroups;
    }

       
    public void setNumGroups(byte value) {
        this.numGroups = value;
    }

       
    public String getOldVersionNumber() {
        return oldVersionNumber;
    }

       
    public void setOldVersionNumber(String value) {
        this.oldVersionNumber = value;
    }

       
    public String getOutputCellsAvailableForLinking() {
        return outputCellsAvailableForLinking;
    }

       
    public void setOutputCellsAvailableForLinking(String value) {
        this.outputCellsAvailableForLinking = value;
    }

       
    public SplitNodeDataDO.OutputNodes getOutputNodes() {
        return outputNodes;
    }

       
    public void setOutputNodes(SplitNodeDataDO.OutputNodes value) {
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

       
    public String getReadyToUpdateCounts() {
        return readyToUpdateCounts;
    }

       
    public void setReadyToUpdateCounts(String value) {
        this.readyToUpdateCounts = value;
    }

       
    public String getRebuildCells() {
        return rebuildCells;
    }

       
    public void setRebuildCells(String value) {
        this.rebuildCells = value;
    }

       
    public SplitNodeDataDO.SortByList getSortByList() {
        return sortByList;
    }

       
    public void setSortByList(SplitNodeDataDO.SortByList value) {
        this.sortByList = value;
    }

       
    public SplitNodeDataDO.SplitCriteriaList getSplitCriteriaList() {
        return splitCriteriaList;
    }

       
    public void setSplitCriteriaList(SplitNodeDataDO.SplitCriteriaList value) {
        this.splitCriteriaList = value;
    }

       
    public SplitNodeDataDO.SplitOnNodeDataDO getSplitOnNodeDataDO() {
        return splitOnNodeDataDO;
    }

       
    public void setSplitOnNodeDataDO(SplitNodeDataDO.SplitOnNodeDataDO value) {
        this.splitOnNodeDataDO = value;
    }

       
    public String getSplitOnNodeID() {
        return splitOnNodeID;
    }

       
    public void setSplitOnNodeID(String value) {
        this.splitOnNodeID = value;
    }

       
    public String getSplitType() {
        return splitType;
    }

       
    public void setSplitType(String value) {
        this.splitType = value;
    }

       
    public SplitNodeDataDO.Splitters getSplitters() {
        return splitters;
    }

       
    public void setSplitters(SplitNodeDataDO.Splitters value) {
        this.splitters = value;
    }

       
    public String getSubjectID() {
        return subjectID;
    }

       
    public void setSubjectID(String value) {
        this.subjectID = value;
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

       
    public SplitNodeDataDO.SystemExtraValues getSystemExtraValues() {
        return systemExtraValues;
    }

       
    public void setSystemExtraValues(SplitNodeDataDO.SystemExtraValues value) {
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
            "splitNodeLineItemDOs"
    })
    public static class LineItems
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "SplitNodeLineItemDO")
        protected List<LineItems.SplitNodeLineItemDO> splitNodeLineItemDOs;

           
        public List<LineItems.SplitNodeLineItemDO> getSplitNodeLineItemDOs() {
            if (splitNodeLineItemDOs == null) {
                splitNodeLineItemDOs = new ArrayList<LineItems.SplitNodeLineItemDO>();
            }
            return this.splitNodeLineItemDOs;
        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "actualCount",
                "actualPercent",
                "archivedPublishState",
                "cellCode",
                "cellId",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "controlGroup",
                "firstOccurrenceToPublish",
                "groupName",
                "lineItemId",
                "loadedFromOldDavLocation",
                "loading",
                "needToPersist",
                "oldVersionNumber",
                "persisting",
                "priority",
                "publishState",
                "remainder",
                "requiredCount",
                "requiredPercent",
                "uniqueCount",
                "value",
                "versionNumber"
        })
        public static class SplitNodeLineItemDO
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ActualCount")
            protected byte actualCount;
            @XmlElement(name = "ActualPercent")
            protected float actualPercent;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "CellCode", required = true)
            protected String cellCode;
            @XmlElement(name = "CellId", required = true)
            protected String cellId;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
            @XmlElement(name = "ControlGroup", required = true)
            protected String controlGroup;
            @XmlElement(name = "FirstOccurrenceToPublish")
            protected byte firstOccurrenceToPublish;
            @XmlElement(name = "GroupName", required = true)
            protected String groupName;
            @XmlElement(name = "LineItemId", required = true)
            protected String lineItemId;
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
            @XmlElement(name = "Priority")
            protected byte priority;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "Remainder", required = true)
            protected String remainder;
            @XmlElement(name = "RequiredCount", required = true)
            protected SplitNodeLineItemDO.RequiredCount requiredCount;
            @XmlElement(name = "RequiredPercent", required = true)
            protected SplitNodeLineItemDO.RequiredPercent requiredPercent;
            @XmlElement(name = "UniqueCount")
            protected byte uniqueCount;
            @XmlElement(name = "Value", required = true)
            protected String value;
            @XmlElement(name = "VersionNumber")
            protected float versionNumber;
            @XmlAttribute(name = "ConcreteClass")
            protected String concreteClass;
            @XmlAttribute(name = "listpos")
            protected Byte listpos;
            @XmlAttribute(name = "objid")
            protected Short objid;

               
            public byte getActualCount() {
                return actualCount;
            }

               
            public void setActualCount(byte value) {
                this.actualCount = value;
            }

               
            public float getActualPercent() {
                return actualPercent;
            }

               
            public void setActualPercent(float value) {
                this.actualPercent = value;
            }

               
            public byte getArchivedPublishState() {
                return archivedPublishState;
            }

               
            public void setArchivedPublishState(byte value) {
                this.archivedPublishState = value;
            }

               
            public String getCellCode() {
                return cellCode;
            }

               
            public void setCellCode(String value) {
                this.cellCode = value;
            }

               
            public String getCellId() {
                return cellId;
            }

               
            public void setCellId(String value) {
                this.cellId = value;
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

               
            public byte getFirstOccurrenceToPublish() {
                return firstOccurrenceToPublish;
            }

               
            public void setFirstOccurrenceToPublish(byte value) {
                this.firstOccurrenceToPublish = value;
            }

               
            public String getGroupName() {
                return groupName;
            }

               
            public void setGroupName(String value) {
                this.groupName = value;
            }

               
            public String getLineItemId() {
                return lineItemId;
            }

               
            public void setLineItemId(String value) {
                this.lineItemId = value;
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

               
            public String getNeedToPersist() {
                return needToPersist;
            }

               
            public void setNeedToPersist(String value) {
                this.needToPersist = value;
            }

               
            public String getOldVersionNumber() {
                return oldVersionNumber;
            }

               
            public void setOldVersionNumber(String value) {
                this.oldVersionNumber = value;
            }

               
            public String getPersisting() {
                return persisting;
            }

               
            public void setPersisting(String value) {
                this.persisting = value;
            }

               
            public byte getPriority() {
                return priority;
            }

               
            public void setPriority(byte value) {
                this.priority = value;
            }

               
            public byte getPublishState() {
                return publishState;
            }

               
            public void setPublishState(byte value) {
                this.publishState = value;
            }

               
            public String getRemainder() {
                return remainder;
            }

               
            public void setRemainder(String value) {
                this.remainder = value;
            }

               
            public SplitNodeLineItemDO.RequiredCount getRequiredCount() {
                return requiredCount;
            }

               
            public void setRequiredCount(SplitNodeLineItemDO.RequiredCount value) {
                this.requiredCount = value;
            }

               
            public SplitNodeLineItemDO.RequiredPercent getRequiredPercent() {
                return requiredPercent;
            }

               
            public void setRequiredPercent(SplitNodeLineItemDO.RequiredPercent value) {
                this.requiredPercent = value;
            }

               
            public byte getUniqueCount() {
                return uniqueCount;
            }

               
            public void setUniqueCount(byte value) {
                this.uniqueCount = value;
            }

               
            public String getValue() {
                return value;
            }

               
            public void setValue(String value) {
                this.value = value;
            }

               
            public float getVersionNumber() {
                return versionNumber;
            }

               
            public void setVersionNumber(float value) {
                this.versionNumber = value;
            }

               
            public String getConcreteClass() {
                return concreteClass;
            }

               
            public void setConcreteClass(String value) {
                this.concreteClass = value;
            }

               
            public Byte getListpos() {
                return listpos;
            }

               
            public void setListpos(Byte value) {
                this.listpos = value;
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
            public static class RequiredCount
                    implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlValue
                protected String value;
                @XmlAttribute(name = "StoredType")
                protected String storedType;

                   
                public String getValue() {
                    return value;
                }

                   
                public void setValue(String value) {
                    this.value = value;
                }

                   
                public String getStoredType() {
                    return storedType;
                }

                   
                public void setStoredType(String value) {
                    this.storedType = value;
                }

            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "value"
            })
            public static class RequiredPercent
                    implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlValue
                protected String value;
                @XmlAttribute(name = "StoredType")
                protected String storedType;

                   
                public String getValue() {
                    return value;
                }

                   
                public void setValue(String value) {
                    this.value = value;
                }

                   
                public String getStoredType() {
                    return storedType;
                }

                   
                public void setStoredType(String value) {
                    this.storedType = value;
                }

            }

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
        @XmlAttribute(name = "ListDelimiter")
        protected String listDelimiter;
        @XmlAttribute(name = "ListTypes")
        protected String listTypes;

           
        public String getValue() {
            return value;
        }

           
        public void setValue(String value) {
            this.value = value;
        }

           
        public String getListDelimiter() {
            return listDelimiter;
        }

           
        public void setListDelimiter(String value) {
            this.listDelimiter = value;
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
    public static class SplitCriteriaList
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
            "archivedPublishState",
            "codeChangeUnpublished",
            "codeEverBeenPublished",
            "controlKey",
            "createMissingGroup",
            "dateType",
            "discreteVariable",
            "distanceCriteria",
            "droppedCount",
            "excludeFlag",
            "excludeIconPath",
            "firstOccurrenceToPublish",
            "hasNoMetaData",
            "iconPath",
            "label",
            "lineItems",
            "loadedFromOldDavLocation",
            "loading",
            "needToPersist",
            "needToSaveLineItems",
            "nodeId",
            "nodeName",
            "nodeState",
            "nodeTags",
            "nodeType",
            "numberOfGroups",
            "oldVersionNumber",
            "outOfSynch",
            "pattern",
            "persisting",
            "publishState",
            "selectedItemsCache",
            "selectedRule",
            "selectionMethod",
            "sortByList",
            "splitters",
            "subjectID",
            "totalCount",
            "validInputSubject",
            "validOutputSubject",
            "varInfoId",
            "varRefDO",
            "versionNumber",
            "wildCardUseAvailable",
            "x",
            "y"
    })
    public static class SplitOnNodeDataDO
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;
        @XmlElement(name = "CodeChangeUnpublished", required = true)
        protected String codeChangeUnpublished;
        @XmlElement(name = "CodeEverBeenPublished", required = true)
        protected String codeEverBeenPublished;
        @XmlElement(name = "ControlKey")
        protected byte controlKey;
        @XmlElement(name = "CreateMissingGroup", required = true)
        protected String createMissingGroup;
        @XmlElement(name = "DateType", required = true)
        protected String dateType;
        @XmlElement(name = "DiscreteVariable", required = true)
        protected String discreteVariable;
        @XmlElement(name = "DistanceCriteria", required = true)
        protected String distanceCriteria;
        @XmlElement(name = "DroppedCount")
        protected byte droppedCount;
        @XmlElement(name = "ExcludeFlag", required = true)
        protected String excludeFlag;
        @XmlElement(name = "ExcludeIconPath", required = true)
        protected String excludeIconPath;
        @XmlElement(name = "FirstOccurrenceToPublish")
        protected byte firstOccurrenceToPublish;
        @XmlElement(name = "HasNoMetaData", required = true)
        protected String hasNoMetaData;
        @XmlElement(name = "IconPath", required = true)
        protected String iconPath;
        @XmlElement(name = "Label", required = true)
        protected String label;
        @XmlElement(name = "LineItems", required = true)
        protected SplitOnNodeDataDO.LineItems lineItems;
        @XmlElement(name = "LoadedFromOldDavLocation", required = true)
        protected String loadedFromOldDavLocation;
        @XmlElement(name = "Loading", required = true)
        protected String loading;
        @XmlElement(name = "NeedToPersist", required = true)
        protected String needToPersist;
        @XmlElement(name = "NeedToSaveLineItems", required = true)
        protected String needToSaveLineItems;
        @XmlElement(name = "NodeId", required = true)
        protected String nodeId;
        @XmlElement(name = "NodeName", required = true)
        protected String nodeName;
        @XmlElement(name = "NodeState")
        protected byte nodeState;
        @XmlElement(name = "NodeTags", required = true)
        protected SplitOnNodeDataDO.NodeTags nodeTags;
        @XmlElement(name = "NodeType")
        protected byte nodeType;
        @XmlElement(name = "NumberOfGroups")
        protected byte numberOfGroups;
        @XmlElement(name = "OldVersionNumber", required = true)
        protected String oldVersionNumber;
        @XmlElement(name = "OutOfSynch", required = true)
        protected String outOfSynch;
        @XmlElement(name = "Pattern", required = true)
        protected String pattern;
        @XmlElement(name = "Persisting", required = true)
        protected String persisting;
        @XmlElement(name = "PublishState")
        protected byte publishState;
        @XmlElement(name = "SelectedItemsCache", required = true)
        protected SplitOnNodeDataDO.SelectedItemsCache selectedItemsCache;
        @XmlElement(name = "SelectedRule", required = true)
        protected String selectedRule;
        @XmlElement(name = "SelectionMethod", required = true)
        protected String selectionMethod;
        @XmlElement(name = "SortByList", required = true)
        protected SplitOnNodeDataDO.SortByList sortByList;
        @XmlElement(name = "Splitters", required = true)
        protected SplitOnNodeDataDO.Splitters splitters;
        @XmlElement(name = "SubjectID", required = true)
        protected String subjectID;
        @XmlElement(name = "TotalCount")
        protected byte totalCount;
        @XmlElement(name = "ValidInputSubject", required = true)
        protected String validInputSubject;
        @XmlElement(name = "ValidOutputSubject", required = true)
        protected String validOutputSubject;
        @XmlElement(name = "VarInfoId", required = true)
        protected String varInfoId;
        @XmlElement(name = "VarRefDO", required = true)
        protected SplitOnNodeDataDO.VarRefDO varRefDO;
        @XmlElement(name = "VersionNumber")
        protected float versionNumber;
        @XmlElement(name = "WildCardUseAvailable", required = true)
        protected String wildCardUseAvailable;
        @XmlElement(name = "X")
        protected byte x;
        @XmlElement(name = "Y")
        protected byte y;
        @XmlAttribute(name = "ConcreteClass")
        protected String concreteClass;
        @XmlAttribute(name = "objid")
        protected Short objid;

           
        public byte getArchivedPublishState() {
            return archivedPublishState;
        }

           
        public void setArchivedPublishState(byte value) {
            this.archivedPublishState = value;
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

           
        public byte getControlKey() {
            return controlKey;
        }

           
        public void setControlKey(byte value) {
            this.controlKey = value;
        }

           
        public String getCreateMissingGroup() {
            return createMissingGroup;
        }

           
        public void setCreateMissingGroup(String value) {
            this.createMissingGroup = value;
        }

           
        public String getDateType() {
            return dateType;
        }

           
        public void setDateType(String value) {
            this.dateType = value;
        }

           
        public String getDiscreteVariable() {
            return discreteVariable;
        }

           
        public void setDiscreteVariable(String value) {
            this.discreteVariable = value;
        }

           
        public String getDistanceCriteria() {
            return distanceCriteria;
        }

           
        public void setDistanceCriteria(String value) {
            this.distanceCriteria = value;
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

           
        public String getHasNoMetaData() {
            return hasNoMetaData;
        }

           
        public void setHasNoMetaData(String value) {
            this.hasNoMetaData = value;
        }

           
        public String getIconPath() {
            return iconPath;
        }

           
        public void setIconPath(String value) {
            this.iconPath = value;
        }

           
        public String getLabel() {
            return label;
        }

           
        public void setLabel(String value) {
            this.label = value;
        }

           
        public SplitOnNodeDataDO.LineItems getLineItems() {
            return lineItems;
        }

           
        public void setLineItems(SplitOnNodeDataDO.LineItems value) {
            this.lineItems = value;
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

           
        public String getNeedToPersist() {
            return needToPersist;
        }

           
        public void setNeedToPersist(String value) {
            this.needToPersist = value;
        }

           
        public String getNeedToSaveLineItems() {
            return needToSaveLineItems;
        }

           
        public void setNeedToSaveLineItems(String value) {
            this.needToSaveLineItems = value;
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

           
        public SplitOnNodeDataDO.NodeTags getNodeTags() {
            return nodeTags;
        }

           
        public void setNodeTags(SplitOnNodeDataDO.NodeTags value) {
            this.nodeTags = value;
        }

           
        public byte getNodeType() {
            return nodeType;
        }

           
        public void setNodeType(byte value) {
            this.nodeType = value;
        }

           
        public byte getNumberOfGroups() {
            return numberOfGroups;
        }

           
        public void setNumberOfGroups(byte value) {
            this.numberOfGroups = value;
        }

           
        public String getOldVersionNumber() {
            return oldVersionNumber;
        }

           
        public void setOldVersionNumber(String value) {
            this.oldVersionNumber = value;
        }

           
        public String getOutOfSynch() {
            return outOfSynch;
        }

           
        public void setOutOfSynch(String value) {
            this.outOfSynch = value;
        }

           
        public String getPattern() {
            return pattern;
        }

           
        public void setPattern(String value) {
            this.pattern = value;
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

           
        public SplitOnNodeDataDO.SelectedItemsCache getSelectedItemsCache() {
            return selectedItemsCache;
        }

           
        public void setSelectedItemsCache(SplitOnNodeDataDO.SelectedItemsCache value) {
            this.selectedItemsCache = value;
        }

           
        public String getSelectedRule() {
            return selectedRule;
        }

           
        public void setSelectedRule(String value) {
            this.selectedRule = value;
        }

           
        public String getSelectionMethod() {
            return selectionMethod;
        }

           
        public void setSelectionMethod(String value) {
            this.selectionMethod = value;
        }

           
        public SplitOnNodeDataDO.SortByList getSortByList() {
            return sortByList;
        }

           
        public void setSortByList(SplitOnNodeDataDO.SortByList value) {
            this.sortByList = value;
        }

           
        public SplitOnNodeDataDO.Splitters getSplitters() {
            return splitters;
        }

           
        public void setSplitters(SplitOnNodeDataDO.Splitters value) {
            this.splitters = value;
        }

           
        public String getSubjectID() {
            return subjectID;
        }

           
        public void setSubjectID(String value) {
            this.subjectID = value;
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

           
        public String getVarInfoId() {
            return varInfoId;
        }

           
        public void setVarInfoId(String value) {
            this.varInfoId = value;
        }

           
        public SplitOnNodeDataDO.VarRefDO getVarRefDO() {
            return varRefDO;
        }

           
        public void setVarRefDO(SplitOnNodeDataDO.VarRefDO value) {
            this.varRefDO = value;
        }

           
        public float getVersionNumber() {
            return versionNumber;
        }

           
        public void setVersionNumber(float value) {
            this.versionNumber = value;
        }

           
        public String getWildCardUseAvailable() {
            return wildCardUseAvailable;
        }

           
        public void setWildCardUseAvailable(String value) {
            this.wildCardUseAvailable = value;
        }

           
        public byte getX() {
            return x;
        }

           
        public void setX(byte value) {
            this.x = value;
        }

           
        public byte getY() {
            return y;
        }

           
        public void setY(byte value) {
            this.y = value;
        }

           
        public String getConcreteClass() {
            return concreteClass;
        }

           
        public void setConcreteClass(String value) {
            this.concreteClass = value;
        }

           
        public Short getObjid() {
            return objid;
        }

           
        public void setObjid(Short value) {
            this.objid = value;
        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "intervalLineItemDOs",
                "discreteLineItemDOs"
        })
        public static class LineItems
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "IntervalLineItemDO")
            protected List<IntervalLineItemDO> intervalLineItemDOs;
            @XmlElement(name = "DiscreteLineItemDO")
            protected List<DiscreteLineItemDO> discreteLineItemDOs;

            public List<IntervalLineItemDO> getIntervalLineItemDOs() {
                if (intervalLineItemDOs == null) {
                    intervalLineItemDOs = new ArrayList<>();
                }
                return this.intervalLineItemDOs;
            }

            public List<DiscreteLineItemDO> getDiscreteLineItemDOs() {
                if (discreteLineItemDOs == null) {
                    discreteLineItemDOs = new ArrayList<>();
                }
                return this.discreteLineItemDOs;
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
        public static class SelectedItemsCache
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
                "archivedPublishState",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "discrete",
                "firstOccurrenceToPublish",
                "loadedFromOldDavLocation",
                "loading",
                "needToPersist",
                "noMetadata",
                "oldVersionNumber",
                "persisting",
                "publishState",
                "sasFormat",
                "subType",
                "type",
                "valid",
                "varInfoId",
                "varName",
                "versionNumber"
        })
        public static class VarRefDO
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
            @XmlElement(name = "Discrete", required = true)
            protected String discrete;
            @XmlElement(name = "FirstOccurrenceToPublish")
            protected byte firstOccurrenceToPublish;
            @XmlElement(name = "LoadedFromOldDavLocation", required = true)
            protected String loadedFromOldDavLocation;
            @XmlElement(name = "Loading", required = true)
            protected String loading;
            @XmlElement(name = "NeedToPersist", required = true)
            protected String needToPersist;
            @XmlElement(name = "NoMetadata", required = true)
            protected String noMetadata;
            @XmlElement(name = "OldVersionNumber", required = true)
            protected String oldVersionNumber;
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "SasFormat", required = true)
            protected String sasFormat;
            @XmlElement(name = "SubType", required = true)
            protected String subType;
            @XmlElement(name = "Type", required = true)
            protected String type;
            @XmlElement(name = "Valid", required = true)
            protected String valid;
            @XmlElement(name = "VarInfoId", required = true)
            protected String varInfoId;
            @XmlElement(name = "VarName", required = true)
            protected String varName;
            @XmlElement(name = "VersionNumber")
            protected float versionNumber;
            @XmlAttribute(name = "ConcreteClass")
            protected String concreteClass;
            @XmlAttribute(name = "objid")
            protected Short objid;

               
            public byte getArchivedPublishState() {
                return archivedPublishState;
            }

               
            public void setArchivedPublishState(byte value) {
                this.archivedPublishState = value;
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

               
            public String getDiscrete() {
                return discrete;
            }

               
            public void setDiscrete(String value) {
                this.discrete = value;
            }

               
            public byte getFirstOccurrenceToPublish() {
                return firstOccurrenceToPublish;
            }

               
            public void setFirstOccurrenceToPublish(byte value) {
                this.firstOccurrenceToPublish = value;
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

               
            public String getNeedToPersist() {
                return needToPersist;
            }

               
            public void setNeedToPersist(String value) {
                this.needToPersist = value;
            }

               
            public String getNoMetadata() {
                return noMetadata;
            }

               
            public void setNoMetadata(String value) {
                this.noMetadata = value;
            }

               
            public String getOldVersionNumber() {
                return oldVersionNumber;
            }

               
            public void setOldVersionNumber(String value) {
                this.oldVersionNumber = value;
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

               
            public String getSasFormat() {
                return sasFormat;
            }

               
            public void setSasFormat(String value) {
                this.sasFormat = value;
            }

               
            public String getSubType() {
                return subType;
            }

               
            public void setSubType(String value) {
                this.subType = value;
            }

               
            public String getType() {
                return type;
            }

               
            public void setType(String value) {
                this.type = value;
            }

               
            public String getValid() {
                return valid;
            }

               
            public void setValid(String value) {
                this.valid = value;
            }

               
            public String getVarInfoId() {
                return varInfoId;
            }

               
            public void setVarInfoId(String value) {
                this.varInfoId = value;
            }

               
            public String getVarName() {
                return varName;
            }

               
            public void setVarName(String value) {
                this.varName = value;
            }

               
            public float getVersionNumber() {
                return versionNumber;
            }

               
            public void setVersionNumber(float value) {
                this.versionNumber = value;
            }

               
            public String getConcreteClass() {
                return concreteClass;
            }

               
            public void setConcreteClass(String value) {
                this.concreteClass = value;
            }

               
            public Short getObjid() {
                return objid;
            }

               
            public void setObjid(Short value) {
                this.objid = value;
            }

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
            "outputNodeNames"
    })
    public static class SystemExtraValues
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "OutputNodeName")
        protected List<String> outputNodeNames;

           
        public List<String> getOutputNodeNames() {
            if (outputNodeNames == null) {
                outputNodeNames = new ArrayList<String>();
            }
            return this.outputNodeNames;
        }

    }

}