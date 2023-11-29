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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "archivedPublishState",
        "audienceList",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "contactHistoryMapped",
        "contactHistoryVariables",
        "controlKey",
        "dateModified",
        "definition",
        "droppedCount",
        "eventReply",
        "excludeFlag",
        "excludeIconPath",
        "firstOccurrenceToPublish",
        "iconPath",
        "inputNodes",
        "label",
        "loadedFromOldDavLocation",
        "loading",
        "needToPersist",
        "nodeCode",
        "nodeDescription",
        "nodeId",
        "nodeName",
        "nodeState",
        "nodeTags",
        "nodeType",
        "oldVersionNumber",
        "outputNodes",
        "persisting",
        "publishState",
        "sortByList",
        "splitters",
        "subjectID",
        "subjectVariables",
        "surrogateKeyNbr",
        "totalCount",
        "trackingPresentedTreatmentsEnabled",
        "treatmentReplyVariables",
        "updateContactHistory",
        "validInputSubject",
        "validOutputSubject",
        "versionNumber",
        "x",
        "y",
        "systemExtraValues"
})
public class ReplyNodeDataDO
        implements Serializable {

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ArchivedPublishState")
    protected byte archivedPublishState;
    @XmlElement(name = "AudienceList", required = true)
    protected ReplyNodeDataDO.AudienceList audienceList;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    protected String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    protected String codeEverBeenPublished;
    @XmlElement(name = "ContactHistoryMapped", required = true)
    protected String contactHistoryMapped;
    @XmlElement(name = "ContactHistoryVariables", required = true)
    protected ReplyNodeDataDO.ContactHistoryVariables contactHistoryVariables;
    @XmlElement(name = "ControlKey")
    protected byte controlKey;
    @XmlElement(name = "DateModified", required = true)
    protected String dateModified;
    @XmlElement(name = "Definition", required = true)
    protected ReplyNodeDataDO.Definition definition;
    @XmlElement(name = "DroppedCount")
    protected byte droppedCount;
    @XmlElement(name = "EventReply", required = true)
    protected ReplyNodeDataDO.EventReply eventReply;
    @XmlElement(name = "ExcludeFlag", required = true)
    protected String excludeFlag;
    @XmlElement(name = "ExcludeIconPath", required = true)
    protected String excludeIconPath;
    @XmlElement(name = "FirstOccurrenceToPublish")
    protected byte firstOccurrenceToPublish;
    @XmlElement(name = "IconPath", required = true)
    protected String iconPath;
    @XmlElement(name = "InputNodes", required = true)
    protected ReplyNodeDataDO.InputNodes inputNodes;
    @XmlElement(name = "Label", required = true)
    protected String label;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    protected String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    protected String loading;
    @XmlElement(name = "NeedToPersist", required = true)
    protected String needToPersist;
    @XmlElement(name = "NodeCode", required = true)
    protected String nodeCode;
    @XmlElement(name = "NodeDescription", required = true)
    protected String nodeDescription;
    @XmlElement(name = "NodeId", required = true)
    protected String nodeId;
    @XmlElement(name = "NodeName", required = true)
    protected String nodeName;
    @XmlElement(name = "NodeState")
    protected byte nodeState;
    @XmlElement(name = "NodeTags", required = true)
    protected ReplyNodeDataDO.NodeTags nodeTags;
    @XmlElement(name = "NodeType")
    protected byte nodeType;
    @XmlElement(name = "OldVersionNumber", required = true)
    protected String oldVersionNumber;
    @XmlElement(name = "OutputNodes", required = true)
    protected ReplyNodeDataDO.OutputNodes outputNodes;
    @XmlElement(name = "Persisting", required = true)
    protected String persisting;
    @XmlElement(name = "PublishState")
    protected byte publishState;
    @XmlElement(name = "SortByList", required = true)
    protected ReplyNodeDataDO.SortByList sortByList;
    @XmlElement(name = "Splitters", required = true)
    protected ReplyNodeDataDO.Splitters splitters;
    @XmlElement(name = "SubjectID", required = true)
    protected String subjectID;
    @XmlElement(name = "SubjectVariables", required = true)
    protected ReplyNodeDataDO.SubjectVariables subjectVariables;
    @XmlElement(name = "SurrogateKeyNbr")
    protected short surrogateKeyNbr;
    @XmlElement(name = "TotalCount")
    protected byte totalCount;
    @XmlElement(name = "TrackingPresentedTreatmentsEnabled", required = true)
    protected String trackingPresentedTreatmentsEnabled;
    @XmlElement(name = "TreatmentReplyVariables", required = false)
    protected TreatmentReplyVariables treatmentReplyVariables;
    @XmlElement(name = "UpdateContactHistory", required = true)
    protected String updateContactHistory;
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
    protected String systemExtraValues;
    @XmlAttribute(name = "objid")
    protected Short objid;

       
    public byte getArchivedPublishState() {
        return archivedPublishState;
    }

       
    public void setArchivedPublishState(byte value) {
        this.archivedPublishState = value;
    }

       
    public ReplyNodeDataDO.AudienceList getAudienceList() {
        return audienceList;
    }

       
    public void setAudienceList(ReplyNodeDataDO.AudienceList value) {
        this.audienceList = value;
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

       
    public String getContactHistoryMapped() {
        return contactHistoryMapped;
    }

       
    public void setContactHistoryMapped(String value) {
        this.contactHistoryMapped = value;
    }

       
    public ReplyNodeDataDO.ContactHistoryVariables getContactHistoryVariables() {
        return contactHistoryVariables;
    }

       
    public void setContactHistoryVariables(ReplyNodeDataDO.ContactHistoryVariables value) {
        this.contactHistoryVariables = value;
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

       
    public ReplyNodeDataDO.Definition getDefinition() {
        return definition;
    }

       
    public void setDefinition(ReplyNodeDataDO.Definition value) {
        this.definition = value;
    }

       
    public byte getDroppedCount() {
        return droppedCount;
    }

       
    public void setDroppedCount(byte value) {
        this.droppedCount = value;
    }

       
    public ReplyNodeDataDO.EventReply getEventReply() {
        return eventReply;
    }

       
    public void setEventReply(ReplyNodeDataDO.EventReply value) {
        this.eventReply = value;
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

       
    public ReplyNodeDataDO.InputNodes getInputNodes() {
        return inputNodes;
    }

       
    public void setInputNodes(ReplyNodeDataDO.InputNodes value) {
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

       
    public ReplyNodeDataDO.NodeTags getNodeTags() {
        return nodeTags;
    }

       
    public void setNodeTags(ReplyNodeDataDO.NodeTags value) {
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

       
    public ReplyNodeDataDO.OutputNodes getOutputNodes() {
        return outputNodes;
    }

       
    public void setOutputNodes(ReplyNodeDataDO.OutputNodes value) {
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

       
    public ReplyNodeDataDO.SortByList getSortByList() {
        return sortByList;
    }

       
    public void setSortByList(ReplyNodeDataDO.SortByList value) {
        this.sortByList = value;
    }

       
    public ReplyNodeDataDO.Splitters getSplitters() {
        return splitters;
    }

       
    public void setSplitters(ReplyNodeDataDO.Splitters value) {
        this.splitters = value;
    }

       
    public String getSubjectID() {
        return subjectID;
    }

       
    public void setSubjectID(String value) {
        this.subjectID = value;
    }

       
    public ReplyNodeDataDO.SubjectVariables getSubjectVariables() {
        return subjectVariables;
    }

       
    public void setSubjectVariables(ReplyNodeDataDO.SubjectVariables value) {
        this.subjectVariables = value;
    }

       
    public short getSurrogateKeyNbr() {
        return surrogateKeyNbr;
    }

       
    public void setSurrogateKeyNbr(short value) {
        this.surrogateKeyNbr = value;
    }

       
    public byte getTotalCount() {
        return totalCount;
    }

       
    public void setTotalCount(byte value) {
        this.totalCount = value;
    }

       
    public String getTrackingPresentedTreatmentsEnabled() {
        return trackingPresentedTreatmentsEnabled;
    }

       
    public void setTrackingPresentedTreatmentsEnabled(String value) {
        this.trackingPresentedTreatmentsEnabled = value;
    }


    public TreatmentReplyVariables getTreatmentReplyVariables() {
        return treatmentReplyVariables;
    }

       
    public void setTreatmentReplyVariables(TreatmentReplyVariables treatmentReplyVariables) {
        this.treatmentReplyVariables = treatmentReplyVariables;
    }

       
    public String getUpdateContactHistory() {
        return updateContactHistory;
    }

       
    public void setUpdateContactHistory(String value) {
        this.updateContactHistory = value;
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

       
    public String getSystemExtraValues() {
        return systemExtraValues;
    }

       
    public void setSystemExtraValues(String value) {
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
            "audienceDO"
    })
    public static class AudienceList
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "AudienceDO", required = true)
        protected AudienceList.AudienceDO audienceDO;

           
        public AudienceList.AudienceDO getAudienceDO() {
            return audienceDO;
        }

           
        public void setAudienceDO(AudienceList.AudienceDO value) {
            this.audienceDO = value;
        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "archivedPublishState",
                "businessContextID",
                "cellCode",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "controlGroupType",
                "description",
                "firstOccurrenceToPublish",
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
                "nodeID",
                "nodeName",
                "oldVersionNumber",
                "packageDO",
                "persisting",
                "publishState",
                "readOnly",
                "removeStagedTreatmentsChannels",
                "removeStagedTreatmentsMethod",
                "removeStaging",
                "skipDedupe",
                "stagingPriority",
                "totalCount",
                "versionNumber"
        })
        public static class AudienceDO
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "BusinessContextID")
            protected byte businessContextID;
            @XmlElement(name = "CellCode", required = true)
            protected String cellCode;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
            @XmlElement(name = "ControlGroupType", required = true)
            protected String controlGroupType;
            @XmlElement(name = "Description", required = true)
            protected String description;
            @XmlElement(name = "FirstOccurrenceToPublish")
            protected byte firstOccurrenceToPublish;
            @XmlElement(name = "Forced", required = true)
            protected String forced;
            @XmlElement(name = "Hidden", required = true)
            protected String hidden;
            @XmlElement(name = "Id", required = true)
            protected String id;
            @XmlElement(name = "LoadedFromOldDavLocation", required = true)
            protected String loadedFromOldDavLocation;
            @XmlElement(name = "Loading", required = true)
            protected String loading;
            @XmlElement(name = "LockedBy", required = true)
            protected String lockedBy;
            @XmlElement(name = "MetadataType", required = true)
            protected String metadataType;
            @XmlElement(name = "Name", required = true)
            protected String name;
            @XmlElement(name = "NeedToPersist", required = true)
            protected String needToPersist;
            @XmlElement(name = "NoWritePermission", required = true)
            protected String noWritePermission;
            @XmlElement(name = "NodeID", required = true)
            protected String nodeID;
            @XmlElement(name = "NodeName", required = true)
            protected String nodeName;
            @XmlElement(name = "OldVersionNumber", required = true)
            protected String oldVersionNumber;
            @XmlElement(name = "PackageDO", required = true)
            protected AudienceDO.PackageDO packageDO;
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "ReadOnly", required = true)
            protected String readOnly;
            @XmlElement(name = "RemoveStagedTreatmentsChannels", required = true)
            protected AudienceDO.RemoveStagedTreatmentsChannels removeStagedTreatmentsChannels;
            @XmlElement(name = "RemoveStagedTreatmentsMethod")
            protected byte removeStagedTreatmentsMethod;
            @XmlElement(name = "RemoveStaging", required = true)
            protected String removeStaging;
            @XmlElement(name = "SkipDedupe", required = true)
            protected String skipDedupe;
            @XmlElement(name = "StagingPriority")
            protected byte stagingPriority;
            @XmlElement(name = "TotalCount")
            protected byte totalCount;
            @XmlElement(name = "VersionNumber")
            protected float versionNumber;
            @XmlAttribute(name = "ConcreteClass")
            protected String concreteClass;
            @XmlAttribute(name = "listpos")
            protected Byte listpos;
            @XmlAttribute(name = "objid")
            protected Short objid;

               
            public byte getArchivedPublishState() {
                return archivedPublishState;
            }

               
            public void setArchivedPublishState(byte value) {
                this.archivedPublishState = value;
            }

               
            public byte getBusinessContextID() {
                return businessContextID;
            }

               
            public void setBusinessContextID(byte value) {
                this.businessContextID = value;
            }

               
            public String getCellCode() {
                return cellCode;
            }

               
            public void setCellCode(String value) {
                this.cellCode = value;
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

               
            public String getControlGroupType() {
                return controlGroupType;
            }

               
            public void setControlGroupType(String value) {
                this.controlGroupType = value;
            }

               
            public String getDescription() {
                return description;
            }

               
            public void setDescription(String value) {
                this.description = value;
            }

               
            public byte getFirstOccurrenceToPublish() {
                return firstOccurrenceToPublish;
            }

               
            public void setFirstOccurrenceToPublish(byte value) {
                this.firstOccurrenceToPublish = value;
            }

               
            public String getForced() {
                return forced;
            }

               
            public void setForced(String value) {
                this.forced = value;
            }

               
            public String getHidden() {
                return hidden;
            }

               
            public void setHidden(String value) {
                this.hidden = value;
            }

               
            public String getId() {
                return id;
            }

               
            public void setId(String value) {
                this.id = value;
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

               
            public String getLockedBy() {
                return lockedBy;
            }

               
            public void setLockedBy(String value) {
                this.lockedBy = value;
            }

               
            public String getMetadataType() {
                return metadataType;
            }

               
            public void setMetadataType(String value) {
                this.metadataType = value;
            }

               
            public String getName() {
                return name;
            }

               
            public void setName(String value) {
                this.name = value;
            }

               
            public String getNeedToPersist() {
                return needToPersist;
            }

               
            public void setNeedToPersist(String value) {
                this.needToPersist = value;
            }

               
            public String getNoWritePermission() {
                return noWritePermission;
            }

               
            public void setNoWritePermission(String value) {
                this.noWritePermission = value;
            }

               
            public String getNodeID() {
                return nodeID;
            }

               
            public void setNodeID(String value) {
                this.nodeID = value;
            }

               
            public String getNodeName() {
                return nodeName;
            }

               
            public void setNodeName(String value) {
                this.nodeName = value;
            }

               
            public String getOldVersionNumber() {
                return oldVersionNumber;
            }

               
            public void setOldVersionNumber(String value) {
                this.oldVersionNumber = value;
            }

               
            public AudienceDO.PackageDO getPackageDO() {
                return packageDO;
            }

               
            public void setPackageDO(AudienceDO.PackageDO value) {
                this.packageDO = value;
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

               
            public String getReadOnly() {
                return readOnly;
            }

               
            public void setReadOnly(String value) {
                this.readOnly = value;
            }

               
            public AudienceDO.RemoveStagedTreatmentsChannels getRemoveStagedTreatmentsChannels() {
                return removeStagedTreatmentsChannels;
            }

               
            public void setRemoveStagedTreatmentsChannels(AudienceDO.RemoveStagedTreatmentsChannels value) {
                this.removeStagedTreatmentsChannels = value;
            }

               
            public byte getRemoveStagedTreatmentsMethod() {
                return removeStagedTreatmentsMethod;
            }

               
            public void setRemoveStagedTreatmentsMethod(byte value) {
                this.removeStagedTreatmentsMethod = value;
            }

               
            public String getRemoveStaging() {
                return removeStaging;
            }

               
            public void setRemoveStaging(String value) {
                this.removeStaging = value;
            }

               
            public String getSkipDedupe() {
                return skipDedupe;
            }

               
            public void setSkipDedupe(String value) {
                this.skipDedupe = value;
            }

               
            public byte getStagingPriority() {
                return stagingPriority;
            }

               
            public void setStagingPriority(byte value) {
                this.stagingPriority = value;
            }

               
            public byte getTotalCount() {
                return totalCount;
            }

               
            public void setTotalCount(byte value) {
                this.totalCount = value;
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
                    "archivedPublishState",
                    "codeChangeUnpublished",
                    "codeEverBeenPublished",
                    "execPublished",
                    "firstOccurrenceToPublish",
                    "loadedFromOldDavLocation",
                    "loading",
                    "mandatoryForOptimization",
                    "mandatoryOptExcludeConstraints",
                    "mandatoryOptExcludeContactPolicy",
                    "needToPersist",
                    "oldVersionNumber",
                    "optimizationPriority",
                    "packageCode",
                    "persisting",
                    "publishState",
                    "surrogateKeyNbr",
                    "versionNumber"
            })
            public static class PackageDO
                    implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "ArchivedPublishState")
                protected byte archivedPublishState;
                @XmlElement(name = "CodeChangeUnpublished", required = true)
                protected String codeChangeUnpublished;
                @XmlElement(name = "CodeEverBeenPublished", required = true)
                protected String codeEverBeenPublished;
                @XmlElement(name = "ExecPublished", required = true)
                protected String execPublished;
                @XmlElement(name = "FirstOccurrenceToPublish")
                protected byte firstOccurrenceToPublish;
                @XmlElement(name = "LoadedFromOldDavLocation", required = true)
                protected String loadedFromOldDavLocation;
                @XmlElement(name = "Loading", required = true)
                protected String loading;
                @XmlElement(name = "MandatoryForOptimization")
                protected byte mandatoryForOptimization;
                @XmlElement(name = "MandatoryOptExcludeConstraints")
                protected byte mandatoryOptExcludeConstraints;
                @XmlElement(name = "MandatoryOptExcludeContactPolicy")
                protected byte mandatoryOptExcludeContactPolicy;
                @XmlElement(name = "NeedToPersist", required = true)
                protected String needToPersist;
                @XmlElement(name = "OldVersionNumber", required = true)
                protected String oldVersionNumber;
                @XmlElement(name = "OptimizationPriority")
                protected byte optimizationPriority;
                @XmlElement(name = "PackageCode", required = true)
                protected String packageCode;
                @XmlElement(name = "Persisting", required = true)
                protected String persisting;
                @XmlElement(name = "PublishState")
                protected byte publishState;
                @XmlElement(name = "SurrogateKeyNbr")
                protected short surrogateKeyNbr;
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

                   
                public String getExecPublished() {
                    return execPublished;
                }

                   
                public void setExecPublished(String value) {
                    this.execPublished = value;
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

                   
                public byte getMandatoryForOptimization() {
                    return mandatoryForOptimization;
                }

                   
                public void setMandatoryForOptimization(byte value) {
                    this.mandatoryForOptimization = value;
                }

                   
                public byte getMandatoryOptExcludeConstraints() {
                    return mandatoryOptExcludeConstraints;
                }

                   
                public void setMandatoryOptExcludeConstraints(byte value) {
                    this.mandatoryOptExcludeConstraints = value;
                }

                   
                public byte getMandatoryOptExcludeContactPolicy() {
                    return mandatoryOptExcludeContactPolicy;
                }

                   
                public void setMandatoryOptExcludeContactPolicy(byte value) {
                    this.mandatoryOptExcludeContactPolicy = value;
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

                   
                public byte getOptimizationPriority() {
                    return optimizationPriority;
                }

                   
                public void setOptimizationPriority(byte value) {
                    this.optimizationPriority = value;
                }

                   
                public String getPackageCode() {
                    return packageCode;
                }

                   
                public void setPackageCode(String value) {
                    this.packageCode = value;
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

                   
                public short getSurrogateKeyNbr() {
                    return surrogateKeyNbr;
                }

                   
                public void setSurrogateKeyNbr(short value) {
                    this.surrogateKeyNbr = value;
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


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "value"
            })
            public static class RemoveStagedTreatmentsChannels
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

        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "ibVariableDOs"
    })
    public static class ContactHistoryVariables
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "IBVariableDO")
        protected List<ContactHistoryVariables.IBVariableDO> ibVariableDOs;

           
        public List<ContactHistoryVariables.IBVariableDO> getIBVariableDOs() {
            if (ibVariableDOs == null) {
                ibVariableDOs = new ArrayList<ContactHistoryVariables.IBVariableDO>();
            }
            return this.ibVariableDOs;
        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "archivedPublishState",
                "attachments",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "containsAllPossibleValues",
                "defaultValueIsMissing",
                "description",
                "firstOccurrenceToPublish",
                "forceOverwrite",
                "forced",
                "hidden",
                "hideVariable",
                "id",
                "level",
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
                "presentInDS2Code",
                "processVariableName",
                "publishState",
                "readOnly",
                "required",
                "selected",
                "shared",
                "sharedIn",
                "testVarInfoId",
                "type",
                "typeDescription",
                "versionNumber",
                "whereClauseOp",
                "whoModified"
        })
        public static class IBVariableDO
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "Attachments", required = true)
            protected String attachments;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
            @XmlElement(name = "ContainsAllPossibleValues", required = true)
            protected String containsAllPossibleValues;
            @XmlElement(name = "DefaultValueIsMissing", required = true)
            protected String defaultValueIsMissing;
            @XmlElement(name = "Description", required = true)
            protected String description;
            @XmlElement(name = "FirstOccurrenceToPublish")
            protected byte firstOccurrenceToPublish;
            @XmlElement(name = "ForceOverwrite", required = true)
            protected String forceOverwrite;
            @XmlElement(name = "Forced", required = true)
            protected String forced;
            @XmlElement(name = "Hidden", required = true)
            protected String hidden;
            @XmlElement(name = "HideVariable", required = true)
            protected String hideVariable;
            @XmlElement(name = "Id", required = true)
            protected String id;
            @XmlElement(name = "Level", required = true)
            protected String level;
            @XmlElement(name = "LoadedFromOldDavLocation", required = true)
            protected String loadedFromOldDavLocation;
            @XmlElement(name = "Loading", required = true)
            protected String loading;
            @XmlElement(name = "LockedBy", required = true)
            protected String lockedBy;
            @XmlElement(name = "MetadataType", required = true)
            protected String metadataType;
            @XmlElement(name = "Name", required = true)
            protected String name;
            @XmlElement(name = "NeedToPersist", required = true)
            protected String needToPersist;
            @XmlElement(name = "NoWritePermission", required = true)
            protected String noWritePermission;
            @XmlElement(name = "OldVersionNumber", required = true)
            protected String oldVersionNumber;
            @XmlElement(name = "PersistState", required = true)
            protected String persistState;
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "PhysicalName", required = true)
            protected String physicalName;
            @XmlElement(name = "PresentInDS2Code", required = true)
            protected String presentInDS2Code;
            @XmlElement(name = "ProcessVariableName", required = true)
            protected String processVariableName;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "ReadOnly", required = true)
            protected String readOnly;
            @XmlElement(name = "Required", required = true)
            protected String required;
            @XmlElement(name = "Selected", required = true)
            protected String selected;
            @XmlElement(name = "Shared", required = true)
            protected String shared;
            @XmlElement(name = "SharedIn", required = true)
            protected IBVariableDO.SharedIn sharedIn;
            @XmlElement(name = "TestVarInfoId", required = true)
            protected String testVarInfoId;
            @XmlElement(name = "Type")
            protected byte type;
            @XmlElement(name = "TypeDescription", required = true)
            protected String typeDescription;
            @XmlElement(name = "VersionNumber")
            protected float versionNumber;
            @XmlElement(name = "WhereClauseOp")
            protected byte whereClauseOp;
            @XmlElement(name = "WhoModified", required = true)
            protected String whoModified;
            @XmlAttribute(name = "ConcreteClass")
            protected String concreteClass;
            @XmlAttribute(name = "listpos")
            protected Byte listpos;
            @XmlAttribute(name = "objid")
            protected Short objid;

               
            public byte getArchivedPublishState() {
                return archivedPublishState;
            }

               
            public void setArchivedPublishState(byte value) {
                this.archivedPublishState = value;
            }

               
            public String getAttachments() {
                return attachments;
            }

               
            public void setAttachments(String value) {
                this.attachments = value;
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

               
            public String getContainsAllPossibleValues() {
                return containsAllPossibleValues;
            }

               
            public void setContainsAllPossibleValues(String value) {
                this.containsAllPossibleValues = value;
            }

               
            public String getDefaultValueIsMissing() {
                return defaultValueIsMissing;
            }

               
            public void setDefaultValueIsMissing(String value) {
                this.defaultValueIsMissing = value;
            }

               
            public String getDescription() {
                return description;
            }

               
            public void setDescription(String value) {
                this.description = value;
            }

               
            public byte getFirstOccurrenceToPublish() {
                return firstOccurrenceToPublish;
            }

               
            public void setFirstOccurrenceToPublish(byte value) {
                this.firstOccurrenceToPublish = value;
            }

               
            public String getForceOverwrite() {
                return forceOverwrite;
            }

               
            public void setForceOverwrite(String value) {
                this.forceOverwrite = value;
            }

               
            public String getForced() {
                return forced;
            }

               
            public void setForced(String value) {
                this.forced = value;
            }

               
            public String getHidden() {
                return hidden;
            }

               
            public void setHidden(String value) {
                this.hidden = value;
            }

               
            public String getHideVariable() {
                return hideVariable;
            }

               
            public void setHideVariable(String value) {
                this.hideVariable = value;
            }

               
            public String getId() {
                return id;
            }

               
            public void setId(String value) {
                this.id = value;
            }

               
            public String getLevel() {
                return level;
            }

               
            public void setLevel(String value) {
                this.level = value;
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

               
            public String getLockedBy() {
                return lockedBy;
            }

               
            public void setLockedBy(String value) {
                this.lockedBy = value;
            }

               
            public String getMetadataType() {
                return metadataType;
            }

               
            public void setMetadataType(String value) {
                this.metadataType = value;
            }

               
            public String getName() {
                return name;
            }

               
            public void setName(String value) {
                this.name = value;
            }

               
            public String getNeedToPersist() {
                return needToPersist;
            }

               
            public void setNeedToPersist(String value) {
                this.needToPersist = value;
            }

               
            public String getNoWritePermission() {
                return noWritePermission;
            }

               
            public void setNoWritePermission(String value) {
                this.noWritePermission = value;
            }

               
            public String getOldVersionNumber() {
                return oldVersionNumber;
            }

               
            public void setOldVersionNumber(String value) {
                this.oldVersionNumber = value;
            }

               
            public String getPersistState() {
                return persistState;
            }

               
            public void setPersistState(String value) {
                this.persistState = value;
            }

               
            public String getPersisting() {
                return persisting;
            }

               
            public void setPersisting(String value) {
                this.persisting = value;
            }

               
            public String getPhysicalName() {
                return physicalName;
            }

               
            public void setPhysicalName(String value) {
                this.physicalName = value;
            }

               
            public String getPresentInDS2Code() {
                return presentInDS2Code;
            }

               
            public void setPresentInDS2Code(String value) {
                this.presentInDS2Code = value;
            }

               
            public String getProcessVariableName() {
                return processVariableName;
            }

               
            public void setProcessVariableName(String value) {
                this.processVariableName = value;
            }

               
            public byte getPublishState() {
                return publishState;
            }

               
            public void setPublishState(byte value) {
                this.publishState = value;
            }

               
            public String getReadOnly() {
                return readOnly;
            }

               
            public void setReadOnly(String value) {
                this.readOnly = value;
            }

               
            public String getRequired() {
                return required;
            }

               
            public void setRequired(String value) {
                this.required = value;
            }

               
            public String getSelected() {
                return selected;
            }

               
            public void setSelected(String value) {
                this.selected = value;
            }

               
            public String getShared() {
                return shared;
            }

               
            public void setShared(String value) {
                this.shared = value;
            }

               
            public IBVariableDO.SharedIn getSharedIn() {
                return sharedIn;
            }

               
            public void setSharedIn(IBVariableDO.SharedIn value) {
                this.sharedIn = value;
            }

               
            public String getTestVarInfoId() {
                return testVarInfoId;
            }

               
            public void setTestVarInfoId(String value) {
                this.testVarInfoId = value;
            }

               
            public byte getType() {
                return type;
            }

               
            public void setType(byte value) {
                this.type = value;
            }

               
            public String getTypeDescription() {
                return typeDescription;
            }

               
            public void setTypeDescription(String value) {
                this.typeDescription = value;
            }

               
            public float getVersionNumber() {
                return versionNumber;
            }

               
            public void setVersionNumber(float value) {
                this.versionNumber = value;
            }

               
            public byte getWhereClauseOp() {
                return whereClauseOp;
            }

               
            public void setWhereClauseOp(byte value) {
                this.whereClauseOp = value;
            }

               
            public String getWhoModified() {
                return whoModified;
            }

               
            public void setWhoModified(String value) {
                this.whoModified = value;
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
            public static class SharedIn
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

        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "archivedPublishState",
            "attachments",
            "businessContextId",
            "channel",
            "codeAutoGenerated",
            "codeChangeUnpublished",
            "codeEditable",
            "codeEverBeenPublished",
            "codeSettingFromBC",
            "countsEditable",
            "dateModified",
            "description",
            "firstOccurrenceToPublish",
            "folder",
            "forced",
            "hidden",
            "id",
            "importDetails",
            "loadedFromOldDavLocation",
            "loading",
            "lockedBy",
            "metadataType",
            "name",
            "needToPersist",
            "noWritePermission",
            "oldVersionNumber",
            "persisting",
            "publishState",
            "readOnly",
            "responseList",
            "shared",
            "sharedIn",
            "trackingPresentedTreatments",
            "versionNumber",
            "whoModified"
    })
    public static class Definition
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;
        @XmlElement(name = "Attachments", required = true)
        protected String attachments;
        @XmlElement(name = "BusinessContextId", required = true)
        protected String businessContextId;
        @XmlElement(name = "Channel", required = true)
        protected String channel;
        @XmlElement(name = "CodeAutoGenerated", required = true)
        protected String codeAutoGenerated;
        @XmlElement(name = "CodeChangeUnpublished", required = true)
        protected String codeChangeUnpublished;
        @XmlElement(name = "CodeEditable", required = true)
        protected String codeEditable;
        @XmlElement(name = "CodeEverBeenPublished", required = true)
        protected String codeEverBeenPublished;
        @XmlElement(name = "CodeSettingFromBC", required = true)
        protected String codeSettingFromBC;
        @XmlElement(name = "CountsEditable", required = true)
        protected String countsEditable;
        @XmlElement(name = "DateModified", required = true)
        protected String dateModified;
        @XmlElement(name = "Description", required = true)
        protected String description;
        @XmlElement(name = "FirstOccurrenceToPublish")
        protected byte firstOccurrenceToPublish;
        @XmlElement(name = "Folder", required = true)
        protected Definition.Folder folder;
        @XmlElement(name = "Forced", required = true)
        protected String forced;
        @XmlElement(name = "Hidden", required = true)
        protected String hidden;
        @XmlElement(name = "Id", required = true)
        protected String id;
        @XmlElement(name = "ImportDetails", required = true)
        protected Definition.ImportDetails importDetails;
        @XmlElement(name = "LoadedFromOldDavLocation", required = true)
        protected String loadedFromOldDavLocation;
        @XmlElement(name = "Loading", required = true)
        protected String loading;
        @XmlElement(name = "LockedBy", required = true)
        protected String lockedBy;
        @XmlElement(name = "MetadataType", required = true)
        protected String metadataType;
        @XmlElement(name = "Name", required = true)
        protected String name;
        @XmlElement(name = "NeedToPersist", required = true)
        protected String needToPersist;
        @XmlElement(name = "NoWritePermission", required = true)
        protected String noWritePermission;
        @XmlElement(name = "OldVersionNumber", required = true)
        protected String oldVersionNumber;
        @XmlElement(name = "Persisting", required = true)
        protected String persisting;
        @XmlElement(name = "PublishState")
        protected byte publishState;
        @XmlElement(name = "ReadOnly", required = true)
        protected String readOnly;
        @XmlElement(name = "ResponseList", required = true)
        protected Definition.ResponseList responseList;
        @XmlElement(name = "Shared", required = true)
        protected String shared;
        @XmlElement(name = "SharedIn", required = true)
        protected Definition.SharedIn sharedIn;
        @XmlElement(name = "TrackingPresentedTreatments", required = true)
        protected String trackingPresentedTreatments;
        @XmlElement(name = "VersionNumber")
        protected float versionNumber;
        @XmlElement(name = "WhoModified", required = true)
        protected String whoModified;
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

           
        public String getAttachments() {
            return attachments;
        }

           
        public void setAttachments(String value) {
            this.attachments = value;
        }

           
        public String getBusinessContextId() {
            return businessContextId;
        }

           
        public void setBusinessContextId(String value) {
            this.businessContextId = value;
        }

           
        public String getChannel() {
            return channel;
        }

           
        public void setChannel(String value) {
            this.channel = value;
        }

           
        public String getCodeAutoGenerated() {
            return codeAutoGenerated;
        }

           
        public void setCodeAutoGenerated(String value) {
            this.codeAutoGenerated = value;
        }

           
        public String getCodeChangeUnpublished() {
            return codeChangeUnpublished;
        }

           
        public void setCodeChangeUnpublished(String value) {
            this.codeChangeUnpublished = value;
        }

           
        public String getCodeEditable() {
            return codeEditable;
        }

           
        public void setCodeEditable(String value) {
            this.codeEditable = value;
        }

           
        public String getCodeEverBeenPublished() {
            return codeEverBeenPublished;
        }

           
        public void setCodeEverBeenPublished(String value) {
            this.codeEverBeenPublished = value;
        }

           
        public String getCodeSettingFromBC() {
            return codeSettingFromBC;
        }

           
        public void setCodeSettingFromBC(String value) {
            this.codeSettingFromBC = value;
        }

           
        public String getCountsEditable() {
            return countsEditable;
        }

           
        public void setCountsEditable(String value) {
            this.countsEditable = value;
        }

           
        public String getDateModified() {
            return dateModified;
        }

           
        public void setDateModified(String value) {
            this.dateModified = value;
        }

           
        public String getDescription() {
            return description;
        }

           
        public void setDescription(String value) {
            this.description = value;
        }

           
        public byte getFirstOccurrenceToPublish() {
            return firstOccurrenceToPublish;
        }

           
        public void setFirstOccurrenceToPublish(byte value) {
            this.firstOccurrenceToPublish = value;
        }

           
        public Definition.Folder getFolder() {
            return folder;
        }

           
        public void setFolder(Definition.Folder value) {
            this.folder = value;
        }

           
        public String getForced() {
            return forced;
        }

           
        public void setForced(String value) {
            this.forced = value;
        }

           
        public String getHidden() {
            return hidden;
        }

           
        public void setHidden(String value) {
            this.hidden = value;
        }

           
        public String getId() {
            return id;
        }

           
        public void setId(String value) {
            this.id = value;
        }

           
        public Definition.ImportDetails getImportDetails() {
            return importDetails;
        }

           
        public void setImportDetails(Definition.ImportDetails value) {
            this.importDetails = value;
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

           
        public String getLockedBy() {
            return lockedBy;
        }

           
        public void setLockedBy(String value) {
            this.lockedBy = value;
        }

           
        public String getMetadataType() {
            return metadataType;
        }

           
        public void setMetadataType(String value) {
            this.metadataType = value;
        }

           
        public String getName() {
            return name;
        }

           
        public void setName(String value) {
            this.name = value;
        }

           
        public String getNeedToPersist() {
            return needToPersist;
        }

           
        public void setNeedToPersist(String value) {
            this.needToPersist = value;
        }

           
        public String getNoWritePermission() {
            return noWritePermission;
        }

           
        public void setNoWritePermission(String value) {
            this.noWritePermission = value;
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

           
        public String getReadOnly() {
            return readOnly;
        }

           
        public void setReadOnly(String value) {
            this.readOnly = value;
        }

           
        public Definition.ResponseList getResponseList() {
            return responseList;
        }

           
        public void setResponseList(Definition.ResponseList value) {
            this.responseList = value;
        }

           
        public String getShared() {
            return shared;
        }

           
        public void setShared(String value) {
            this.shared = value;
        }

           
        public Definition.SharedIn getSharedIn() {
            return sharedIn;
        }

           
        public void setSharedIn(Definition.SharedIn value) {
            this.sharedIn = value;
        }

           
        public String getTrackingPresentedTreatments() {
            return trackingPresentedTreatments;
        }

           
        public void setTrackingPresentedTreatments(String value) {
            this.trackingPresentedTreatments = value;
        }

           
        public float getVersionNumber() {
            return versionNumber;
        }

           
        public void setVersionNumber(float value) {
            this.versionNumber = value;
        }

           
        public String getWhoModified() {
            return whoModified;
        }

           
        public void setWhoModified(String value) {
            this.whoModified = value;
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
        public static class Folder
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
            @XmlElement(name = "FirstOccurrenceToPublish")
            protected byte firstOccurrenceToPublish;
            @XmlElement(name = "LoadedFromOldDavLocation", required = true)
            protected String loadedFromOldDavLocation;
            @XmlElement(name = "Loading", required = true)
            protected String loading;
            @XmlElement(name = "Name", required = true)
            protected String name;
            @XmlElement(name = "NeedToPersist", required = true)
            protected String needToPersist;
            @XmlElement(name = "OldVersionNumber", required = true)
            protected String oldVersionNumber;
            @XmlElement(name = "Owner", required = true)
            protected String owner;
            @XmlElement(name = "ParentFolder", required = true)
            protected String parentFolder;
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "PublishState")
            protected byte publishState;
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

               
            public String getName() {
                return name;
            }

               
            public void setName(String value) {
                this.name = value;
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

               
            public String getOwner() {
                return owner;
            }

               
            public void setOwner(String value) {
                this.owner = value;
            }

               
            public String getParentFolder() {
                return parentFolder;
            }

               
            public void setParentFolder(String value) {
                this.parentFolder = value;
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


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "archivedPublishState",
                "clientType",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "dateImported",
                "firstOccurrenceToPublish",
                "loadedFromOldDavLocation",
                "loading",
                "needToPersist",
                "oldVersionNumber",
                "openedPostMigration",
                "persisting",
                "publishState",
                "versionNumber"
        })
        public static class ImportDetails
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "ClientType")
            protected byte clientType;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
            @XmlElement(name = "DateImported", required = true)
            protected String dateImported;
            @XmlElement(name = "FirstOccurrenceToPublish")
            protected byte firstOccurrenceToPublish;
            @XmlElement(name = "LoadedFromOldDavLocation", required = true)
            protected String loadedFromOldDavLocation;
            @XmlElement(name = "Loading", required = true)
            protected String loading;
            @XmlElement(name = "NeedToPersist", required = true)
            protected String needToPersist;
            @XmlElement(name = "OldVersionNumber", required = true)
            protected String oldVersionNumber;
            @XmlElement(name = "OpenedPostMigration", required = true)
            protected String openedPostMigration;
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "PublishState")
            protected byte publishState;
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

               
            public byte getClientType() {
                return clientType;
            }

               
            public void setClientType(byte value) {
                this.clientType = value;
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

               
            public String getDateImported() {
                return dateImported;
            }

               
            public void setDateImported(String value) {
                this.dateImported = value;
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

               
            public String getOldVersionNumber() {
                return oldVersionNumber;
            }

               
            public void setOldVersionNumber(String value) {
                this.oldVersionNumber = value;
            }

               
            public String getOpenedPostMigration() {
                return openedPostMigration;
            }

               
            public void setOpenedPostMigration(String value) {
                this.openedPostMigration = value;
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


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        public static class ResponseList
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
        public static class SharedIn
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

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "archivedPublishState",
            "codeChangeUnpublished",
            "codeEverBeenPublished",
            "eventName",
            "firstOccurrenceToPublish",
            "loadedFromOldDavLocation",
            "loading",
            "needToPersist",
            "oldVersionNumber",
            "persisting",
            "publishState",
            "replyVars",
            "versionNumber"
    })
    public static class EventReply
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;
        @XmlElement(name = "CodeChangeUnpublished", required = true)
        protected String codeChangeUnpublished;
        @XmlElement(name = "CodeEverBeenPublished", required = true)
        protected String codeEverBeenPublished;
        @XmlElement(name = "EventName", required = true)
        protected String eventName;
        @XmlElement(name = "FirstOccurrenceToPublish")
        protected byte firstOccurrenceToPublish;
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
        @XmlElement(name = "PublishState")
        protected byte publishState;
        @XmlElement(name = "ReplyVars", required = true)
        protected EventReply.ReplyVars replyVars;
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

           
        public String getEventName() {
            return eventName;
        }

           
        public void setEventName(String value) {
            this.eventName = value;
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

           
        public EventReply.ReplyVars getReplyVars() {
            return replyVars;
        }

           
        public void setReplyVars(EventReply.ReplyVars value) {
            this.replyVars = value;
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


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "ibVariableDOs"
        })
        public static class ReplyVars
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "IBVariableDO")
            protected List<ReplyVars.IBVariableDO> ibVariableDOs;

               
            public List<ReplyVars.IBVariableDO> getIBVariableDOs() {
                if (ibVariableDOs == null) {
                    ibVariableDOs = new ArrayList<ReplyVars.IBVariableDO>();
                }
                return this.ibVariableDOs;
            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "archivedPublishState",
                    "attachments",
                    "codeChangeUnpublished",
                    "codeEverBeenPublished",
                    "containsAllPossibleValues",
                    "defaultValueIsMissing",
                    "description",
                    "errorValue",
                    "firstOccurrenceToPublish",
                    "forceOverwrite",
                    "forced",
                    "hidden",
                    "hideVariable",
                    "id",
                    "level",
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
                    "presentInDS2Code",
                    "processVariableName",
                    "publishState",
                    "readOnly",
                    "required",
                    "selected",
                    "shared",
                    "sharedIn",
                    "testVarInfoId",
                    "type",
                    "typeDescription",
                    "value",
                    "versionNumber",
                    "whereClauseOp",
                    "whoModified"
            })
            public static class IBVariableDO
                    implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "ArchivedPublishState")
                protected byte archivedPublishState;
                @XmlElement(name = "Attachments", required = true)
                protected String attachments;
                @XmlElement(name = "CodeChangeUnpublished", required = true)
                protected String codeChangeUnpublished;
                @XmlElement(name = "CodeEverBeenPublished", required = true)
                protected String codeEverBeenPublished;
                @XmlElement(name = "ContainsAllPossibleValues", required = true)
                protected String containsAllPossibleValues;
                @XmlElement(name = "DefaultValueIsMissing", required = true)
                protected String defaultValueIsMissing;
                @XmlElement(name = "Description", required = true)
                protected String description;
                @XmlElement(name = "ErrorValue")
                protected com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO.ErrorValue errorValue;
                @XmlElement(name = "FirstOccurrenceToPublish")
                protected byte firstOccurrenceToPublish;
                @XmlElement(name = "ForceOverwrite", required = true)
                protected String forceOverwrite;
                @XmlElement(name = "Forced", required = true)
                protected String forced;
                @XmlElement(name = "Hidden", required = true)
                protected String hidden;
                @XmlElement(name = "HideVariable", required = true)
                protected String hideVariable;
                @XmlElement(name = "Id", required = true)
                protected String id;
                @XmlElement(name = "Level", required = true)
                protected String level;
                @XmlElement(name = "LoadedFromOldDavLocation", required = true)
                protected String loadedFromOldDavLocation;
                @XmlElement(name = "Loading", required = true)
                protected String loading;
                @XmlElement(name = "LockedBy", required = true)
                protected String lockedBy;
                @XmlElement(name = "MetadataType", required = true)
                protected String metadataType;
                @XmlElement(name = "Name", required = true)
                protected String name;
                @XmlElement(name = "NeedToPersist", required = true)
                protected String needToPersist;
                @XmlElement(name = "NoWritePermission", required = true)
                protected String noWritePermission;
                @XmlElement(name = "OldVersionNumber", required = true)
                protected String oldVersionNumber;
                @XmlElement(name = "PersistState", required = true)
                protected String persistState;
                @XmlElement(name = "Persisting", required = true)
                protected String persisting;
                @XmlElement(name = "PhysicalName", required = true)
                protected String physicalName;
                @XmlElement(name = "PresentInDS2Code", required = true)
                protected String presentInDS2Code;
                @XmlElement(name = "ProcessVariableName", required = true)
                protected String processVariableName;
                @XmlElement(name = "PublishState")
                protected byte publishState;
                @XmlElement(name = "ReadOnly", required = true)
                protected String readOnly;
                @XmlElement(name = "Required", required = true)
                protected String required;
                @XmlElement(name = "Selected", required = true)
                protected String selected;
                @XmlElement(name = "Shared", required = true)
                protected String shared;
                @XmlElement(name = "SharedIn", required = true)
                protected com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO.SharedIn sharedIn;
                @XmlElement(name = "TestVarInfoId", required = true)
                protected String testVarInfoId;
                @XmlElement(name = "Type")
                protected byte type;
                @XmlElement(name = "TypeDescription", required = true)
                protected String typeDescription;
                @XmlElement(name = "Value")
                protected com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO.Value value;
                @XmlElement(name = "VersionNumber")
                protected float versionNumber;
                @XmlElement(name = "WhereClauseOp")
                protected byte whereClauseOp;
                @XmlElement(name = "WhoModified", required = true)
                protected String whoModified;
                @XmlAttribute(name = "ConcreteClass")
                protected String concreteClass;
                @XmlAttribute(name = "listpos")
                protected Byte listpos;
                @XmlAttribute(name = "objid")
                protected Short objid;

                   
                public byte getArchivedPublishState() {
                    return archivedPublishState;
                }

                   
                public void setArchivedPublishState(byte value) {
                    this.archivedPublishState = value;
                }

                   
                public String getAttachments() {
                    return attachments;
                }

                   
                public void setAttachments(String value) {
                    this.attachments = value;
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

                   
                public String getContainsAllPossibleValues() {
                    return containsAllPossibleValues;
                }

                   
                public void setContainsAllPossibleValues(String value) {
                    this.containsAllPossibleValues = value;
                }

                   
                public String getDefaultValueIsMissing() {
                    return defaultValueIsMissing;
                }

                   
                public void setDefaultValueIsMissing(String value) {
                    this.defaultValueIsMissing = value;
                }

                   
                public String getDescription() {
                    return description;
                }

                   
                public void setDescription(String value) {
                    this.description = value;
                }

                   
                public com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO.ErrorValue getErrorValue() {
                    return errorValue;
                }

                   
                public void setErrorValue(com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO.ErrorValue value) {
                    this.errorValue = value;
                }

                   
                public byte getFirstOccurrenceToPublish() {
                    return firstOccurrenceToPublish;
                }

                   
                public void setFirstOccurrenceToPublish(byte value) {
                    this.firstOccurrenceToPublish = value;
                }

                   
                public String getForceOverwrite() {
                    return forceOverwrite;
                }

                   
                public void setForceOverwrite(String value) {
                    this.forceOverwrite = value;
                }

                   
                public String getForced() {
                    return forced;
                }

                   
                public void setForced(String value) {
                    this.forced = value;
                }

                   
                public String getHidden() {
                    return hidden;
                }

                   
                public void setHidden(String value) {
                    this.hidden = value;
                }

                   
                public String getHideVariable() {
                    return hideVariable;
                }

                   
                public void setHideVariable(String value) {
                    this.hideVariable = value;
                }

                   
                public String getId() {
                    return id;
                }

                   
                public void setId(String value) {
                    this.id = value;
                }

                   
                public String getLevel() {
                    return level;
                }

                   
                public void setLevel(String value) {
                    this.level = value;
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

                   
                public String getLockedBy() {
                    return lockedBy;
                }

                   
                public void setLockedBy(String value) {
                    this.lockedBy = value;
                }

                   
                public String getMetadataType() {
                    return metadataType;
                }

                   
                public void setMetadataType(String value) {
                    this.metadataType = value;
                }

                   
                public String getName() {
                    return name;
                }

                   
                public void setName(String value) {
                    this.name = value;
                }

                   
                public String getNeedToPersist() {
                    return needToPersist;
                }

                   
                public void setNeedToPersist(String value) {
                    this.needToPersist = value;
                }

                   
                public String getNoWritePermission() {
                    return noWritePermission;
                }

                   
                public void setNoWritePermission(String value) {
                    this.noWritePermission = value;
                }

                   
                public String getOldVersionNumber() {
                    return oldVersionNumber;
                }

                   
                public void setOldVersionNumber(String value) {
                    this.oldVersionNumber = value;
                }

                   
                public String getPersistState() {
                    return persistState;
                }

                   
                public void setPersistState(String value) {
                    this.persistState = value;
                }

                   
                public String getPersisting() {
                    return persisting;
                }

                   
                public void setPersisting(String value) {
                    this.persisting = value;
                }

                   
                public String getPhysicalName() {
                    return physicalName;
                }

                   
                public void setPhysicalName(String value) {
                    this.physicalName = value;
                }

                   
                public String getPresentInDS2Code() {
                    return presentInDS2Code;
                }

                   
                public void setPresentInDS2Code(String value) {
                    this.presentInDS2Code = value;
                }

                   
                public String getProcessVariableName() {
                    return processVariableName;
                }

                   
                public void setProcessVariableName(String value) {
                    this.processVariableName = value;
                }

                   
                public byte getPublishState() {
                    return publishState;
                }

                   
                public void setPublishState(byte value) {
                    this.publishState = value;
                }

                   
                public String getReadOnly() {
                    return readOnly;
                }

                   
                public void setReadOnly(String value) {
                    this.readOnly = value;
                }

                   
                public String getRequired() {
                    return required;
                }

                   
                public void setRequired(String value) {
                    this.required = value;
                }

                   
                public String getSelected() {
                    return selected;
                }

                   
                public void setSelected(String value) {
                    this.selected = value;
                }

                   
                public String getShared() {
                    return shared;
                }

                   
                public void setShared(String value) {
                    this.shared = value;
                }

                   
                public com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO.SharedIn getSharedIn() {
                    return sharedIn;
                }

                   
                public void setSharedIn(com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO.SharedIn value) {
                    this.sharedIn = value;
                }

                   
                public String getTestVarInfoId() {
                    return testVarInfoId;
                }

                   
                public void setTestVarInfoId(String value) {
                    this.testVarInfoId = value;
                }

                   
                public byte getType() {
                    return type;
                }

                   
                public void setType(byte value) {
                    this.type = value;
                }

                   
                public String getTypeDescription() {
                    return typeDescription;
                }

                   
                public void setTypeDescription(String value) {
                    this.typeDescription = value;
                }

                   
                public com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO.Value getValue() {
                    return value;
                }

                   
                public void setValue(com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO.Value value) {
                    this.value = value;
                }

                   
                public float getVersionNumber() {
                    return versionNumber;
                }

                   
                public void setVersionNumber(float value) {
                    this.versionNumber = value;
                }

                   
                public byte getWhereClauseOp() {
                    return whereClauseOp;
                }

                   
                public void setWhereClauseOp(byte value) {
                    this.whereClauseOp = value;
                }

                   
                public String getWhoModified() {
                    return whoModified;
                }

                   
                public void setWhoModified(String value) {
                    this.whoModified = value;
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
                        "archivedPublishState",
                        "codeChangeUnpublished",
                        "codeEverBeenPublished",
                        "firstOccurrenceToPublish",
                        "loadedFromOldDavLocation",
                        "loading",
                        "needToPersist",
                        "oldVersionNumber",
                        "persisting",
                        "publishState",
                        "type",
                        "valueTypeVarInfoDO",
                        "versionNumber"
                })
                public static class ErrorValue
                        implements Serializable {

                    private final static long serialVersionUID = -1L;
                    @XmlElement(name = "ArchivedPublishState")
                    protected byte archivedPublishState;
                    @XmlElement(name = "CodeChangeUnpublished", required = true)
                    protected String codeChangeUnpublished;
                    @XmlElement(name = "CodeEverBeenPublished", required = true)
                    protected String codeEverBeenPublished;
                    @XmlElement(name = "FirstOccurrenceToPublish")
                    protected byte firstOccurrenceToPublish;
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
                    @XmlElement(name = "PublishState")
                    protected byte publishState;
                    @XmlElement(name = "Type")
                    protected byte type;
                    @XmlElement(name = "ValueTypeVarInfoDO", required = true)
                    protected ErrorValue.ValueTypeVarInfoDO valueTypeVarInfoDO;
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

                       
                    public byte getType() {
                        return type;
                    }

                       
                    public void setType(byte value) {
                        this.type = value;
                    }

                       
                    public ErrorValue.ValueTypeVarInfoDO getValueTypeVarInfoDO() {
                        return valueTypeVarInfoDO;
                    }

                       
                    public void setValueTypeVarInfoDO(ErrorValue.ValueTypeVarInfoDO value) {
                        this.valueTypeVarInfoDO = value;
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


                       
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "archivedPublishState",
                            "codeChangeUnpublished",
                            "codeEverBeenPublished",
                            "firstOccurrenceToPublish",
                            "loadedFromOldDavLocation",
                            "loading",
                            "needToPersist",
                            "oldVersionNumber",
                            "persisting",
                            "publishState",
                            "varInfoArray",
                            "varInfoId",
                            "varInfoName",
                            "varInfoPhysicalName",
                            "varInfoSource",
                            "varInfoSubtype",
                            "varInfoType",
                            "versionNumber"
                    })
                    public static class ValueTypeVarInfoDO
                            implements Serializable {

                        private final static long serialVersionUID = -1L;
                        @XmlElement(name = "ArchivedPublishState")
                        protected byte archivedPublishState;
                        @XmlElement(name = "CodeChangeUnpublished", required = true)
                        protected String codeChangeUnpublished;
                        @XmlElement(name = "CodeEverBeenPublished", required = true)
                        protected String codeEverBeenPublished;
                        @XmlElement(name = "FirstOccurrenceToPublish")
                        protected byte firstOccurrenceToPublish;
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
                        @XmlElement(name = "PublishState")
                        protected byte publishState;
                        @XmlElement(name = "VarInfoArray", required = true)
                        protected String varInfoArray;
                        @XmlElement(name = "VarInfoId", required = true)
                        protected String varInfoId;
                        @XmlElement(name = "VarInfoName", required = true)
                        protected String varInfoName;
                        @XmlElement(name = "VarInfoPhysicalName", required = true)
                        protected String varInfoPhysicalName;
                        @XmlElement(name = "VarInfoSource", required = true)
                        protected String varInfoSource;
                        @XmlElement(name = "VarInfoSubtype", required = true)
                        protected String varInfoSubtype;
                        @XmlElement(name = "VarInfoType", required = true)
                        protected String varInfoType;
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

                           
                        public String getVarInfoArray() {
                            return varInfoArray;
                        }

                           
                        public void setVarInfoArray(String value) {
                            this.varInfoArray = value;
                        }

                           
                        public String getVarInfoId() {
                            return varInfoId;
                        }

                           
                        public void setVarInfoId(String value) {
                            this.varInfoId = value;
                        }

                           
                        public String getVarInfoName() {
                            return varInfoName;
                        }

                           
                        public void setVarInfoName(String value) {
                            this.varInfoName = value;
                        }

                           
                        public String getVarInfoPhysicalName() {
                            return varInfoPhysicalName;
                        }

                           
                        public void setVarInfoPhysicalName(String value) {
                            this.varInfoPhysicalName = value;
                        }

                           
                        public String getVarInfoSource() {
                            return varInfoSource;
                        }

                           
                        public void setVarInfoSource(String value) {
                            this.varInfoSource = value;
                        }

                           
                        public String getVarInfoSubtype() {
                            return varInfoSubtype;
                        }

                           
                        public void setVarInfoSubtype(String value) {
                            this.varInfoSubtype = value;
                        }

                           
                        public String getVarInfoType() {
                            return varInfoType;
                        }

                           
                        public void setVarInfoType(String value) {
                            this.varInfoType = value;
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
                public static class SharedIn
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
                        "firstOccurrenceToPublish",
                        "loadedFromOldDavLocation",
                        "loading",
                        "needToPersist",
                        "oldVersionNumber",
                        "persisting",
                        "publishState",
                        "booleanValue",
                        "dateValue",
                        "dateValueString",
                        "doubleValue",
                        "longValue",
                        "stringValue",
                        "type",
                        "valueTypeVarInfoDO",
                        "versionNumber"
                })
                public static class Value
                        implements Serializable {

                    private final static long serialVersionUID = -1L;
                    @XmlElement(name = "ArchivedPublishState")
                    protected byte archivedPublishState;
                    @XmlElement(name = "CodeChangeUnpublished", required = true)
                    protected String codeChangeUnpublished;
                    @XmlElement(name = "CodeEverBeenPublished", required = true)
                    protected String codeEverBeenPublished;
                    @XmlElement(name = "FirstOccurrenceToPublish")
                    protected byte firstOccurrenceToPublish;
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
                    @XmlElement(name = "PublishState")
                    protected byte publishState;
                    @XmlElement(name = "StringValue")
                    protected String stringValue;
                    @XmlElement(name = "BooleanValue")
                    protected String booleanValue;
                    @XmlElement(name = "DateValue")
                    protected String dateValue;
                    @XmlElement(name = "DateValueString")
                    protected String dateValueString;   
                    @XmlElement(name = "DoubleValue")
                    protected String doubleValue;
                    @XmlElement(name = "LongValue")
                    protected String longValue;
                    @XmlElement(name = "Type")
                    protected byte type;
                    @XmlElement(name = "ValueTypeVarInfoDO", required = true)
                    protected Value.ValueTypeVarInfoDO valueTypeVarInfoDO;
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

                    public String getBooleanValue() {
                        return booleanValue;
                    }

                    public void setBooleanValue(String booleanValue) {
                        this.booleanValue = booleanValue;
                    }

                    public String getDateValue() {
                        return dateValue;
                    }

                    public void setDateValue(String dateValue) {
                        this.dateValue = dateValue;
                    }

                    public void setDateValueString(String dateValueString) {
                        this.dateValueString = dateValueString;
                    }
                
                    public String getDateValueString() {
                        return dateValueString;
                    }
                    
                    public String getDoubleValue() {
                        return doubleValue;
                    }

                    public void setDoubleValue(String doubleValue) {
                        this.doubleValue = doubleValue;
                    }

                    public String getLongValue() {
                        return longValue;
                    }

                    public void setLongValue(String longValue) {
                        this.longValue = longValue;
                    }

                    public String getStringValue() {
                        return stringValue;
                    }

                    public void setStringValue(String stringValue) {
                        this.stringValue = stringValue;
                    }

                       
                    public byte getType() {
                        return type;
                    }

                       
                    public void setType(byte value) {
                        this.type = value;
                    }

                       
                    public Value.ValueTypeVarInfoDO getValueTypeVarInfoDO() {
                        return valueTypeVarInfoDO;
                    }

                       
                    public void setValueTypeVarInfoDO(Value.ValueTypeVarInfoDO value) {
                        this.valueTypeVarInfoDO = value;
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


                       
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "archivedPublishState",
                            "codeChangeUnpublished",
                            "codeEverBeenPublished",
                            "firstOccurrenceToPublish",
                            "loadedFromOldDavLocation",
                            "loading",
                            "needToPersist",
                            "oldVersionNumber",
                            "persisting",
                            "publishState",
                            "varInfoArray",
                            "varInfoId",
                            "varName",
                            "varInfoPhysicalName",
                            "varInfoSource",
                            "varInfoSourceName",
                            "varInfoSourceNodeId",
                            "varInfoSubtype",
                            "type",
                            "versionNumber"
                    })
                    public static class ValueTypeVarInfoDO
                            implements Serializable {

                        private final static long serialVersionUID = -1L;
                        @XmlElement(name = "ArchivedPublishState")
                        protected byte archivedPublishState;
                        @XmlElement(name = "CodeChangeUnpublished", required = true)
                        protected String codeChangeUnpublished;
                        @XmlElement(name = "CodeEverBeenPublished", required = true)
                        protected String codeEverBeenPublished;
                        @XmlElement(name = "FirstOccurrenceToPublish")
                        protected byte firstOccurrenceToPublish;
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
                        @XmlElement(name = "PublishState")
                        protected byte publishState;
                        @XmlElement(name = "VarInfoArray", required = true)
                        protected String varInfoArray;
                        @XmlElement(name = "VarInfoId", required = true)
                        protected String varInfoId;
                        @XmlElement(name = "VarInfoName", required = true)
                        protected String varName;
                        @XmlElement(name = "VarInfoPhysicalName", required = true)
                        protected String varInfoPhysicalName;
                        @XmlElement(name = "VarInfoSource", required = true)
                        protected String varInfoSource;
                        @XmlElement(name = "VarInfoSourceName")
                        protected String varInfoSourceName;
                        @XmlElement(name = "VarInfoSourceNodeId")
                        protected String varInfoSourceNodeId;
                        @XmlElement(name = "VarInfoSubtype", required = true)
                        protected String varInfoSubtype;
                        @XmlElement(name = "VarInfoType", required = true)
                        protected String type;
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

                           
                        public String getVarInfoArray() {
                            return varInfoArray;
                        }

                           
                        public void setVarInfoArray(String value) {
                            this.varInfoArray = value;
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

                           
                        public String getVarInfoPhysicalName() {
                            return varInfoPhysicalName;
                        }

                           
                        public void setVarInfoPhysicalName(String value) {
                            this.varInfoPhysicalName = value;
                        }

                           
                        public String getVarInfoSource() {
                            return varInfoSource;
                        }

                           
                        public void setVarInfoSource(String value) {
                            this.varInfoSource = value;
                        }

                           
                        public String getVarInfoSourceName() {
                            return varInfoSourceName;
                        }

                           
                        public void setVarInfoSourceName(String value) {
                            this.varInfoSourceName = value;
                        }

                           
                        public String getVarInfoSourceNodeId() {
                            return varInfoSourceNodeId;
                        }

                           
                        public void setVarInfoSourceNodeId(String value) {
                            this.varInfoSourceNodeId = value;
                        }

                           
                        public String getVarInfoSubtype() {
                            return varInfoSubtype;
                        }

                           
                        public void setVarInfoSubtype(String value) {
                            this.varInfoSubtype = value;
                        }

                           
                        public String getType() {
                            return type;
                        }

                           
                        public void setType(String value) {
                            this.type = value;
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

            }

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
            "value"
    })
    public static class SubjectVariables
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
            "ibVariableDOs"
    })
    public static class TreatmentReplyVariables
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "IBVariableDO")
        protected List<ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO> ibVariableDOs;

            
        public List<ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO> getIBVariableDOs() {
            if (ibVariableDOs == null) {
                ibVariableDOs = new ArrayList<ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO>();
            }
            return this.ibVariableDOs;
        }
    }

}