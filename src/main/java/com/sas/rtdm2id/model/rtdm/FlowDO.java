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

import com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "annotationXmlForFlex",
        "archivedPublishState",
        "autoUpdateCounts",
        "calculatedDataItemList",
        "campaignId",
        "campaignTreatmentSetsReferenced",
        "cellsDynamicWhenRefdByLink",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "commDynamicForExecuteComm",
        "configuredNotifications",
        "contactHistoryMapped",
        "countType",
        "creationDate",
        "creator",
        "dataType",
        "deployedDSFlowName",
        "deploymentStatus",
        "description",
        "doNotPublishWhenNoComms",
        "editorList",
        "errorReplyCount",
        "executing",
        "firstOccurrenceToPublish",
        "fixupStatus",
        "flowType",
        "folderDO",
        "forceExecutionAllSQLNodes",
        "forced",
        "globalVariableReferences",
        "hidden",
        "id",
        "identifierReferences",
        "implicitProcessReferences",
        "lastModDate",
        "lastModUser",
        "layoutDirection",
        "liveCount",
        "liveSinceLastClearCount",
        "loadedFromOldDavLocation",
        "loading",
        "lockedBy",
        "markedForDeploymentVersion",
        "maxNodeGroupNbr",
        "maxPrioritizeCellSuffix",
        "maxProcessNodeGeneratedCellSuffix",
        "maxTestSplitNodeGeneratedCellSuffix",
        "metadataType",
        "name",
        "needToPersist",
        "noWritePermission",
        "nodeGroupList",
        "noteList",
        "oldVersionNumber",
        "owner",
        "persisting",
        "publishState",
        "readOnly",
        "readyForExecution",
        "responseTableMapped",
        "saved",
        "scale",
        "schedule",
        "scheduleFlowId",
        "scheduleUUID",
        "seedListsMigrated",
        "shared",
        "standaloneDiagram",
        "standardReply",
        "tempScheduleMD5Sum",
        "testCases",
        "userRoles",
        "versionNumber",
        "viewerList",
        "zOrderIdList"
})
public class FlowDO
        implements Serializable {

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "AnnotationXmlForFlex", required = true)
    protected String annotationXmlForFlex;
    @XmlElement(name = "ArchivedPublishState")
    protected byte archivedPublishState;
    @XmlElement(name = "AutoUpdateCounts", required = true)
    protected String autoUpdateCounts;
    @XmlElement(name = "CalculatedDataItemList", required = true)
    protected FlowDO.CalculatedDataItemList calculatedDataItemList;
    @XmlElement(name = "CampaignId", required = true)
    protected String campaignId;
    @XmlElement(name = "CampaignTreatmentSetsReferenced", required = true)
    protected FlowDO.CampaignTreatmentSetsReferenced campaignTreatmentSetsReferenced;
    @XmlElement(name = "CellsDynamicWhenRefdByLink", required = true)
    protected String cellsDynamicWhenRefdByLink;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    protected String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    protected String codeEverBeenPublished;
    @XmlElement(name = "CommDynamicForExecuteComm", required = true)
    protected String commDynamicForExecuteComm;
    @XmlElement(name = "ConfiguredNotifications", required = true)
    protected FlowDO.ConfiguredNotifications configuredNotifications;
    @XmlElement(name = "ContactHistoryMapped", required = true)
    protected String contactHistoryMapped;
    @XmlElement(name = "CountType", required = true)
    protected String countType;
    @XmlElement(name = "CreationDate", required = true)
    protected String creationDate;
    @XmlElement(name = "Creator", required = true)
    protected String creator;
    @XmlElement(name = "DataType", required = true)
    protected String dataType;
    @XmlElement(name = "DeployedDSFlowName", required = true)
    protected String deployedDSFlowName;
    @XmlElement(name = "DeploymentStatus", required = true)
    protected String deploymentStatus;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "DoNotPublishWhenNoComms", required = true)
    protected String doNotPublishWhenNoComms;
    @XmlElement(name = "EditorList", required = true)
    protected FlowDO.EditorList editorList;
    @XmlElement(name = "ErrorReplyCount")
    protected byte errorReplyCount;
    @XmlElement(name = "Executing", required = true)
    protected String executing;
    @XmlElement(name = "FirstOccurrenceToPublish")
    protected byte firstOccurrenceToPublish;
    @XmlElement(name = "FixupStatus", required = true)
    protected FlowDO.FixupStatus fixupStatus;
    @XmlElement(name = "FlowType", required = true)
    protected String flowType;
    @XmlElement(name = "FolderDO", required = true)
    protected FlowDO.FolderDO folderDO;
    @XmlElement(name = "ForceExecutionAllSQLNodes", required = true)
    protected String forceExecutionAllSQLNodes;
    @XmlElement(name = "Forced", required = true)
    protected String forced;
    @XmlElement(name = "GlobalVariableReferences", required = true)
    protected FlowDO.GlobalVariableReferences globalVariableReferences;
    @XmlElement(name = "Hidden", required = true)
    protected String hidden;
    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "IdentifierReferences", required = true)
    protected FlowDO.IdentifierReferences identifierReferences;
    @XmlElement(name = "ImplicitProcessReferences", required = true)
    protected FlowDO.ImplicitProcessReferences implicitProcessReferences;
    @XmlElement(name = "LastModDate", required = true)
    protected String lastModDate;
    @XmlElement(name = "LastModUser", required = true)
    protected String lastModUser;
    @XmlElement(name = "LayoutDirection", required = true)
    protected String layoutDirection;
    @XmlElement(name = "LiveCount")
    protected byte liveCount;
    @XmlElement(name = "LiveSinceLastClearCount")
    protected byte liveSinceLastClearCount;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    protected String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    protected String loading;
    @XmlElement(name = "LockedBy", required = true)
    protected String lockedBy;
    @XmlElement(name = "MarkedForDeploymentVersion")
    protected byte markedForDeploymentVersion;
    @XmlElement(name = "MaxNodeGroupNbr")
    protected byte maxNodeGroupNbr;
    @XmlElement(name = "MaxPrioritizeCellSuffix")
    protected byte maxPrioritizeCellSuffix;
    @XmlElement(name = "MaxProcessNodeGeneratedCellSuffix")
    protected byte maxProcessNodeGeneratedCellSuffix;
    @XmlElement(name = "MaxTestSplitNodeGeneratedCellSuffix")
    protected byte maxTestSplitNodeGeneratedCellSuffix;
    @XmlElement(name = "MetadataType", required = true)
    protected String metadataType;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "NeedToPersist", required = true)
    protected String needToPersist;
    @XmlElement(name = "NoWritePermission", required = true)
    protected String noWritePermission;
    @XmlElement(name = "NodeGroupList", required = true)
    protected FlowDO.NodeGroupList nodeGroupList;
    @XmlElement(name = "NoteList", required = true)
    protected FlowDO.NoteList noteList;
    @XmlElement(name = "OldVersionNumber", required = true)
    protected String oldVersionNumber;
    @XmlElement(name = "Owner", required = true)
    protected String owner;
    @XmlElement(name = "Persisting", required = true)
    protected String persisting;
    @XmlElement(name = "PublishState")
    protected byte publishState;
    @XmlElement(name = "ReadOnly", required = true)
    protected String readOnly;
    @XmlElement(name = "ReadyForExecution", required = true)
    protected String readyForExecution;
    @XmlElement(name = "ResponseTableMapped", required = true)
    protected String responseTableMapped;
    @XmlElement(name = "Saved", required = true)
    protected String saved;
    @XmlElement(name = "Scale")
    protected float scale;
    @XmlElement(name = "Schedule", required = true)
    protected FlowDO.Schedule schedule;
    @XmlElement(name = "ScheduleFlowId", required = true)
    protected String scheduleFlowId;
    @XmlElement(name = "ScheduleUUID", required = true)
    protected String scheduleUUID;
    @XmlElement(name = "SeedListsMigrated", required = true)
    protected String seedListsMigrated;
    @XmlElement(name = "Shared", required = true)
    protected String shared;
    @XmlElement(name = "StandaloneDiagram", required = true)
    protected String standaloneDiagram;
    @XmlElement(name = "StandardReply", required = true)
    protected FlowDO.StandardReply standardReply;
    @XmlElement(name = "TempScheduleMD5Sum", required = true)
    protected String tempScheduleMD5Sum;
    @XmlElement(name = "TestCases", required = true)
    protected FlowDO.TestCases testCases;
    @XmlElement(name = "UserRoles", required = true)
    protected FlowDO.UserRoles userRoles;
    @XmlElement(name = "VersionNumber")
    protected float versionNumber;
    @XmlElement(name = "ViewerList", required = true)
    protected FlowDO.ViewerList viewerList;
    @XmlElement(name = "ZOrderIdList", required = true)
    protected FlowDO.ZOrderIdList zOrderIdList;
    @XmlAttribute(name = "objid")
    protected Short objid;

       
    public String getAnnotationXmlForFlex() {
        return annotationXmlForFlex;
    }

       
    public void setAnnotationXmlForFlex(String value) {
        this.annotationXmlForFlex = value;
    }

       
    public byte getArchivedPublishState() {
        return archivedPublishState;
    }

       
    public void setArchivedPublishState(byte value) {
        this.archivedPublishState = value;
    }

       
    public String getAutoUpdateCounts() {
        return autoUpdateCounts;
    }

       
    public void setAutoUpdateCounts(String value) {
        this.autoUpdateCounts = value;
    }

       
    public FlowDO.CalculatedDataItemList getCalculatedDataItemList() {
        return calculatedDataItemList;
    }

       
    public void setCalculatedDataItemList(FlowDO.CalculatedDataItemList value) {
        this.calculatedDataItemList = value;
    }

       
    public String getCampaignId() {
        return campaignId;
    }

       
    public void setCampaignId(String value) {
        this.campaignId = value;
    }

       
    public FlowDO.CampaignTreatmentSetsReferenced getCampaignTreatmentSetsReferenced() {
        return campaignTreatmentSetsReferenced;
    }

       
    public void setCampaignTreatmentSetsReferenced(FlowDO.CampaignTreatmentSetsReferenced value) {
        this.campaignTreatmentSetsReferenced = value;
    }

       
    public String getCellsDynamicWhenRefdByLink() {
        return cellsDynamicWhenRefdByLink;
    }

       
    public void setCellsDynamicWhenRefdByLink(String value) {
        this.cellsDynamicWhenRefdByLink = value;
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

       
    public String getCommDynamicForExecuteComm() {
        return commDynamicForExecuteComm;
    }

       
    public void setCommDynamicForExecuteComm(String value) {
        this.commDynamicForExecuteComm = value;
    }

       
    public FlowDO.ConfiguredNotifications getConfiguredNotifications() {
        return configuredNotifications;
    }

       
    public void setConfiguredNotifications(FlowDO.ConfiguredNotifications value) {
        this.configuredNotifications = value;
    }

       
    public String getContactHistoryMapped() {
        return contactHistoryMapped;
    }

       
    public void setContactHistoryMapped(String value) {
        this.contactHistoryMapped = value;
    }

       
    public String getCountType() {
        return countType;
    }

       
    public void setCountType(String value) {
        this.countType = value;
    }

       
    public String getCreationDate() {
        return creationDate;
    }

       
    public void setCreationDate(String value) {
        this.creationDate = value;
    }

       
    public String getCreator() {
        return creator;
    }

       
    public void setCreator(String value) {
        this.creator = value;
    }

       
    public String getDataType() {
        return dataType;
    }

       
    public void setDataType(String value) {
        this.dataType = value;
    }

       
    public String getDeployedDSFlowName() {
        return deployedDSFlowName;
    }

       
    public void setDeployedDSFlowName(String value) {
        this.deployedDSFlowName = value;
    }

       
    public String getDeploymentStatus() {
        return deploymentStatus;
    }

       
    public void setDeploymentStatus(String value) {
        this.deploymentStatus = value;
    }

       
    public String getDescription() {
        return description;
    }

       
    public void setDescription(String value) {
        this.description = value;
    }

       
    public String getDoNotPublishWhenNoComms() {
        return doNotPublishWhenNoComms;
    }

       
    public void setDoNotPublishWhenNoComms(String value) {
        this.doNotPublishWhenNoComms = value;
    }

       
    public FlowDO.EditorList getEditorList() {
        return editorList;
    }

       
    public void setEditorList(FlowDO.EditorList value) {
        this.editorList = value;
    }

       
    public byte getErrorReplyCount() {
        return errorReplyCount;
    }

       
    public void setErrorReplyCount(byte value) {
        this.errorReplyCount = value;
    }

       
    public String getExecuting() {
        return executing;
    }

       
    public void setExecuting(String value) {
        this.executing = value;
    }

       
    public byte getFirstOccurrenceToPublish() {
        return firstOccurrenceToPublish;
    }

       
    public void setFirstOccurrenceToPublish(byte value) {
        this.firstOccurrenceToPublish = value;
    }

       
    public FlowDO.FixupStatus getFixupStatus() {
        return fixupStatus;
    }

       
    public void setFixupStatus(FlowDO.FixupStatus value) {
        this.fixupStatus = value;
    }

       
    public String getFlowType() {
        return flowType;
    }

       
    public void setFlowType(String value) {
        this.flowType = value;
    }

       
    public FlowDO.FolderDO getFolderDO() {
        return folderDO;
    }

       
    public void setFolderDO(FlowDO.FolderDO value) {
        this.folderDO = value;
    }

       
    public String getForceExecutionAllSQLNodes() {
        return forceExecutionAllSQLNodes;
    }

       
    public void setForceExecutionAllSQLNodes(String value) {
        this.forceExecutionAllSQLNodes = value;
    }

       
    public String getForced() {
        return forced;
    }

       
    public void setForced(String value) {
        this.forced = value;
    }

       
    public FlowDO.GlobalVariableReferences getGlobalVariableReferences() {
        return globalVariableReferences;
    }

       
    public void setGlobalVariableReferences(FlowDO.GlobalVariableReferences value) {
        this.globalVariableReferences = value;
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

       
    public FlowDO.IdentifierReferences getIdentifierReferences() {
        return identifierReferences;
    }

       
    public void setIdentifierReferences(FlowDO.IdentifierReferences value) {
        this.identifierReferences = value;
    }

       
    public FlowDO.ImplicitProcessReferences getImplicitProcessReferences() {
        return implicitProcessReferences;
    }

       
    public void setImplicitProcessReferences(FlowDO.ImplicitProcessReferences value) {
        this.implicitProcessReferences = value;
    }

       
    public String getLastModDate() {
        return lastModDate;
    }

       
    public void setLastModDate(String value) {
        this.lastModDate = value;
    }

       
    public String getLastModUser() {
        return lastModUser;
    }

       
    public void setLastModUser(String value) {
        this.lastModUser = value;
    }

       
    public String getLayoutDirection() {
        return layoutDirection;
    }

       
    public void setLayoutDirection(String value) {
        this.layoutDirection = value;
    }

       
    public byte getLiveCount() {
        return liveCount;
    }

       
    public void setLiveCount(byte value) {
        this.liveCount = value;
    }

       
    public byte getLiveSinceLastClearCount() {
        return liveSinceLastClearCount;
    }

       
    public void setLiveSinceLastClearCount(byte value) {
        this.liveSinceLastClearCount = value;
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

       
    public byte getMarkedForDeploymentVersion() {
        return markedForDeploymentVersion;
    }

       
    public void setMarkedForDeploymentVersion(byte value) {
        this.markedForDeploymentVersion = value;
    }

       
    public byte getMaxNodeGroupNbr() {
        return maxNodeGroupNbr;
    }

       
    public void setMaxNodeGroupNbr(byte value) {
        this.maxNodeGroupNbr = value;
    }

       
    public byte getMaxPrioritizeCellSuffix() {
        return maxPrioritizeCellSuffix;
    }

       
    public void setMaxPrioritizeCellSuffix(byte value) {
        this.maxPrioritizeCellSuffix = value;
    }

       
    public byte getMaxProcessNodeGeneratedCellSuffix() {
        return maxProcessNodeGeneratedCellSuffix;
    }

       
    public void setMaxProcessNodeGeneratedCellSuffix(byte value) {
        this.maxProcessNodeGeneratedCellSuffix = value;
    }

       
    public byte getMaxTestSplitNodeGeneratedCellSuffix() {
        return maxTestSplitNodeGeneratedCellSuffix;
    }

       
    public void setMaxTestSplitNodeGeneratedCellSuffix(byte value) {
        this.maxTestSplitNodeGeneratedCellSuffix = value;
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

       
    public FlowDO.NodeGroupList getNodeGroupList() {
        return nodeGroupList;
    }

       
    public void setNodeGroupList(FlowDO.NodeGroupList value) {
        this.nodeGroupList = value;
    }

       
    public FlowDO.NoteList getNoteList() {
        return noteList;
    }

       
    public void setNoteList(FlowDO.NoteList value) {
        this.noteList = value;
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

       
    public String getReadyForExecution() {
        return readyForExecution;
    }

       
    public void setReadyForExecution(String value) {
        this.readyForExecution = value;
    }

       
    public String getResponseTableMapped() {
        return responseTableMapped;
    }

       
    public void setResponseTableMapped(String value) {
        this.responseTableMapped = value;
    }

       
    public String getSaved() {
        return saved;
    }

       
    public void setSaved(String value) {
        this.saved = value;
    }

       
    public float getScale() {
        return scale;
    }

       
    public void setScale(float value) {
        this.scale = value;
    }

       
    public FlowDO.Schedule getSchedule() {
        return schedule;
    }

       
    public void setSchedule(FlowDO.Schedule value) {
        this.schedule = value;
    }

       
    public String getScheduleFlowId() {
        return scheduleFlowId;
    }

       
    public void setScheduleFlowId(String value) {
        this.scheduleFlowId = value;
    }

       
    public String getScheduleUUID() {
        return scheduleUUID;
    }

       
    public void setScheduleUUID(String value) {
        this.scheduleUUID = value;
    }

       
    public String getSeedListsMigrated() {
        return seedListsMigrated;
    }

       
    public void setSeedListsMigrated(String value) {
        this.seedListsMigrated = value;
    }

       
    public String getShared() {
        return shared;
    }

       
    public void setShared(String value) {
        this.shared = value;
    }

       
    public String getStandaloneDiagram() {
        return standaloneDiagram;
    }

       
    public void setStandaloneDiagram(String value) {
        this.standaloneDiagram = value;
    }

       
    public FlowDO.StandardReply getStandardReply() {
        return standardReply;
    }

       
    public void setStandardReply(FlowDO.StandardReply value) {
        this.standardReply = value;
    }

       
    public String getTempScheduleMD5Sum() {
        return tempScheduleMD5Sum;
    }

       
    public void setTempScheduleMD5Sum(String value) {
        this.tempScheduleMD5Sum = value;
    }

       
    public FlowDO.TestCases getTestCases() {
        return testCases;
    }

       
    public void setTestCases(FlowDO.TestCases value) {
        this.testCases = value;
    }

       
    public FlowDO.UserRoles getUserRoles() {
        return userRoles;
    }

       
    public void setUserRoles(FlowDO.UserRoles value) {
        this.userRoles = value;
    }

       
    public float getVersionNumber() {
        return versionNumber;
    }

       
    public void setVersionNumber(float value) {
        this.versionNumber = value;
    }

       
    public FlowDO.ViewerList getViewerList() {
        return viewerList;
    }

       
    public void setViewerList(FlowDO.ViewerList value) {
        this.viewerList = value;
    }

       
    public FlowDO.ZOrderIdList getZOrderIdList() {
        return zOrderIdList;
    }

       
    public void setZOrderIdList(FlowDO.ZOrderIdList value) {
        this.zOrderIdList = value;
    }

       
    public Short getObjid() {
        return objid;
    }

       
    public void setObjid(Short value) {
        this.objid = value;
    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "calculatedItemDOs"
    })
    public static class CalculatedDataItemList
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "CalculatedItemDO")
        protected List<CalculatedItemDO> calculatedItemDOs;

        public List<CalculatedItemDO> getCalculatedItemDOs() {
            if (calculatedItemDOs == null) {
                calculatedItemDOs = new ArrayList<>();
            }
            return this.calculatedItemDOs;
        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class CampaignTreatmentSetsReferenced
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
            "notificationDOs"
    })
    public static class ConfiguredNotifications
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "NotificationDO")
        protected List<ConfiguredNotifications.NotificationDO> notificationDOs;

           
        public List<ConfiguredNotifications.NotificationDO> getNotificationDOs() {
            if (notificationDOs == null) {
                notificationDOs = new ArrayList<ConfiguredNotifications.NotificationDO>();
            }
            return this.notificationDOs;
        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "archivedPublishState",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "events",
                "firstOccurrenceToPublish",
                "loadedFromOldDavLocation",
                "loading",
                "name",
                "needToPersist",
                "oldVersionNumber",
                "persisting",
                "publishState",
                "type",
                "versionNumber"
        })
        public static class NotificationDO
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
            @XmlElement(name = "Events", required = true)
            protected NotificationDO.Events events;
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
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "Type", required = true)
            protected String type;
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

               
            public NotificationDO.Events getEvents() {
                return events;
            }

               
            public void setEvents(NotificationDO.Events value) {
                this.events = value;
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
                    "notificationEventDOs"
            })
            public static class Events
                    implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "NotificationEventDO")
                protected List<Events.NotificationEventDO> notificationEventDOs;

                   
                public List<Events.NotificationEventDO> getNotificationEventDOs() {
                    if (notificationEventDOs == null) {
                        notificationEventDOs = new ArrayList<Events.NotificationEventDO>();
                    }
                    return this.notificationEventDOs;
                }


                   
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "archivedPublishState",
                        "ccList",
                        "codeChangeUnpublished",
                        "codeEverBeenPublished",
                        "comment",
                        "enabled",
                        "firstOccurrenceToPublish",
                        "loadedFromOldDavLocation",
                        "loading",
                        "name",
                        "needToPersist",
                        "notificationName",
                        "notificationType",
                        "oldVersionNumber",
                        "persisting",
                        "publishState",
                        "substitutableSubject",
                        "toList",
                        "type",
                        "versionNumber"
                })
                public static class NotificationEventDO
                        implements Serializable {

                    private final static long serialVersionUID = -1L;
                    @XmlElement(name = "ArchivedPublishState")
                    protected byte archivedPublishState;
                    @XmlElement(name = "CcList", required = true)
                    protected NotificationEventDO.CcList ccList;
                    @XmlElement(name = "CodeChangeUnpublished", required = true)
                    protected String codeChangeUnpublished;
                    @XmlElement(name = "CodeEverBeenPublished", required = true)
                    protected String codeEverBeenPublished;
                    @XmlElement(name = "Comment", required = true)
                    protected String comment;
                    @XmlElement(name = "Enabled", required = true)
                    protected String enabled;
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
                    @XmlElement(name = "NotificationName", required = true)
                    protected String notificationName;
                    @XmlElement(name = "NotificationType", required = true)
                    protected String notificationType;
                    @XmlElement(name = "OldVersionNumber", required = true)
                    protected String oldVersionNumber;
                    @XmlElement(name = "Persisting", required = true)
                    protected String persisting;
                    @XmlElement(name = "PublishState")
                    protected byte publishState;
                    @XmlElement(name = "SubstitutableSubject", required = true)
                    protected NotificationEventDO.SubstitutableSubject substitutableSubject;
                    @XmlElement(name = "ToList", required = true)
                    protected NotificationEventDO.ToList toList;
                    @XmlElement(name = "Type", required = true)
                    protected String type;
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

                       
                    public NotificationEventDO.CcList getCcList() {
                        return ccList;
                    }

                       
                    public void setCcList(NotificationEventDO.CcList value) {
                        this.ccList = value;
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

                       
                    public String getComment() {
                        return comment;
                    }

                       
                    public void setComment(String value) {
                        this.comment = value;
                    }

                       
                    public String getEnabled() {
                        return enabled;
                    }

                       
                    public void setEnabled(String value) {
                        this.enabled = value;
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

                       
                    public String getNotificationName() {
                        return notificationName;
                    }

                       
                    public void setNotificationName(String value) {
                        this.notificationName = value;
                    }

                       
                    public String getNotificationType() {
                        return notificationType;
                    }

                       
                    public void setNotificationType(String value) {
                        this.notificationType = value;
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

                       
                    public NotificationEventDO.SubstitutableSubject getSubstitutableSubject() {
                        return substitutableSubject;
                    }

                       
                    public void setSubstitutableSubject(NotificationEventDO.SubstitutableSubject value) {
                        this.substitutableSubject = value;
                    }

                       
                    public NotificationEventDO.ToList getToList() {
                        return toList;
                    }

                       
                    public void setToList(NotificationEventDO.ToList value) {
                        this.toList = value;
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
                    public static class CcList
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
                            "rawValue",
                            "versionNumber"
                    })
                    public static class SubstitutableSubject
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
                        @XmlElement(name = "RawValue", required = true)
                        protected String rawValue;
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

                           
                        public String getRawValue() {
                            return rawValue;
                        }

                           
                        public void setRawValue(String value) {
                            this.rawValue = value;
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
                    public static class ToList
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

        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class EditorList
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
            "needsCellAudienceMigration",
            "needsCodeFixup",
            "needsFixupOnLoad",
            "needsNodeIDFixup",
            "needsResetStatus",
            "needsRoleFixup",
            "needsSKFixup",
            "needsScheduleFixup",
            "oldVersionNumber",
            "persisting",
            "publishState",
            "versionNumber"
    })
    public static class FixupStatus
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
        @XmlElement(name = "NeedsCellAudienceMigration", required = true)
        protected String needsCellAudienceMigration;
        @XmlElement(name = "NeedsCodeFixup", required = true)
        protected String needsCodeFixup;
        @XmlElement(name = "NeedsFixupOnLoad", required = true)
        protected String needsFixupOnLoad;
        @XmlElement(name = "NeedsNodeIDFixup", required = true)
        protected String needsNodeIDFixup;
        @XmlElement(name = "NeedsResetStatus", required = true)
        protected String needsResetStatus;
        @XmlElement(name = "NeedsRoleFixup", required = true)
        protected String needsRoleFixup;
        @XmlElement(name = "NeedsSKFixup", required = true)
        protected String needsSKFixup;
        @XmlElement(name = "NeedsScheduleFixup", required = true)
        protected String needsScheduleFixup;
        @XmlElement(name = "OldVersionNumber", required = true)
        protected String oldVersionNumber;
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

           
        public String getNeedToPersist() {
            return needToPersist;
        }

           
        public void setNeedToPersist(String value) {
            this.needToPersist = value;
        }

           
        public String getNeedsCellAudienceMigration() {
            return needsCellAudienceMigration;
        }

           
        public void setNeedsCellAudienceMigration(String value) {
            this.needsCellAudienceMigration = value;
        }

           
        public String getNeedsCodeFixup() {
            return needsCodeFixup;
        }

           
        public void setNeedsCodeFixup(String value) {
            this.needsCodeFixup = value;
        }

           
        public String getNeedsFixupOnLoad() {
            return needsFixupOnLoad;
        }

           
        public void setNeedsFixupOnLoad(String value) {
            this.needsFixupOnLoad = value;
        }

           
        public String getNeedsNodeIDFixup() {
            return needsNodeIDFixup;
        }

           
        public void setNeedsNodeIDFixup(String value) {
            this.needsNodeIDFixup = value;
        }

           
        public String getNeedsResetStatus() {
            return needsResetStatus;
        }

           
        public void setNeedsResetStatus(String value) {
            this.needsResetStatus = value;
        }

           
        public String getNeedsRoleFixup() {
            return needsRoleFixup;
        }

           
        public void setNeedsRoleFixup(String value) {
            this.needsRoleFixup = value;
        }

           
        public String getNeedsSKFixup() {
            return needsSKFixup;
        }

           
        public void setNeedsSKFixup(String value) {
            this.needsSKFixup = value;
        }

           
        public String getNeedsScheduleFixup() {
            return needsScheduleFixup;
        }

           
        public void setNeedsScheduleFixup(String value) {
            this.needsScheduleFixup = value;
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
    public static class FolderDO
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
            "value"
    })
    public static class GlobalVariableReferences
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
    public static class IdentifierReferences
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
    public static class ImplicitProcessReferences
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
    public static class NodeGroupList
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
    public static class NoteList
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
            "dateScheduleLastModifiedInCampaign",
            "endDate",
            "executionOffset",
            "firstOccurrenceToPublish",
            "loadedFromOldDavLocation",
            "loading",
            "needToPersist",
            "oldVersionNumber",
            "persisting",
            "publishState",
            "recurrenceEveryPeriod",
            "recurrenceMonthDay",
            "recurrencePeriodEndAfter",
            "recurrencePeriodType",
            "recurrenceType",
            "scheduleActionList",
            "scheduleType",
            "startDate",
            "updated",
            "versionNumber"
    })
    public static class Schedule
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;
        @XmlElement(name = "CodeChangeUnpublished", required = true)
        protected String codeChangeUnpublished;
        @XmlElement(name = "CodeEverBeenPublished", required = true)
        protected String codeEverBeenPublished;
        @XmlElement(name = "DateScheduleLastModifiedInCampaign", required = true)
        protected String dateScheduleLastModifiedInCampaign;
        @XmlElement(name = "EndDate", required = true)
        protected String endDate;
        @XmlElement(name = "ExecutionOffset", required = true)
        protected Schedule.ExecutionOffset executionOffset;
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
        @XmlElement(name = "RecurrenceEveryPeriod")
        protected byte recurrenceEveryPeriod;
        @XmlElement(name = "RecurrenceMonthDay")
        protected byte recurrenceMonthDay;
        @XmlElement(name = "RecurrencePeriodEndAfter")
        protected byte recurrencePeriodEndAfter;
        @XmlElement(name = "RecurrencePeriodType", required = true)
        protected String recurrencePeriodType;
        @XmlElement(name = "RecurrenceType", required = true)
        protected String recurrenceType;
        @XmlElement(name = "ScheduleActionList", required = true)
        protected Schedule.ScheduleActionList scheduleActionList;
        @XmlElement(name = "ScheduleType", required = true)
        protected String scheduleType;
        @XmlElement(name = "StartDate", required = true)
        protected String startDate;
        @XmlElement(name = "Updated", required = true)
        protected String updated;
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

           
        public String getDateScheduleLastModifiedInCampaign() {
            return dateScheduleLastModifiedInCampaign;
        }

           
        public void setDateScheduleLastModifiedInCampaign(String value) {
            this.dateScheduleLastModifiedInCampaign = value;
        }

           
        public String getEndDate() {
            return endDate;
        }

           
        public void setEndDate(String value) {
            this.endDate = value;
        }

           
        public Schedule.ExecutionOffset getExecutionOffset() {
            return executionOffset;
        }

           
        public void setExecutionOffset(Schedule.ExecutionOffset value) {
            this.executionOffset = value;
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

           
        public byte getRecurrenceEveryPeriod() {
            return recurrenceEveryPeriod;
        }

           
        public void setRecurrenceEveryPeriod(byte value) {
            this.recurrenceEveryPeriod = value;
        }

           
        public byte getRecurrenceMonthDay() {
            return recurrenceMonthDay;
        }

           
        public void setRecurrenceMonthDay(byte value) {
            this.recurrenceMonthDay = value;
        }

           
        public byte getRecurrencePeriodEndAfter() {
            return recurrencePeriodEndAfter;
        }

           
        public void setRecurrencePeriodEndAfter(byte value) {
            this.recurrencePeriodEndAfter = value;
        }

           
        public String getRecurrencePeriodType() {
            return recurrencePeriodType;
        }

           
        public void setRecurrencePeriodType(String value) {
            this.recurrencePeriodType = value;
        }

           
        public String getRecurrenceType() {
            return recurrenceType;
        }

           
        public void setRecurrenceType(String value) {
            this.recurrenceType = value;
        }

           
        public Schedule.ScheduleActionList getScheduleActionList() {
            return scheduleActionList;
        }

           
        public void setScheduleActionList(Schedule.ScheduleActionList value) {
            this.scheduleActionList = value;
        }

           
        public String getScheduleType() {
            return scheduleType;
        }

           
        public void setScheduleType(String value) {
            this.scheduleType = value;
        }

           
        public String getStartDate() {
            return startDate;
        }

           
        public void setStartDate(String value) {
            this.startDate = value;
        }

           
        public String getUpdated() {
            return updated;
        }

           
        public void setUpdated(String value) {
            this.updated = value;
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
                "dateScheduleLastModifiedInCampaign",
                "firstOccurrenceToPublish",
                "loadedFromOldDavLocation",
                "loading",
                "needToPersist",
                "offset",
                "oldVersionNumber",
                "persisting",
                "publishState",
                "scheduleType",
                "units",
                "versionNumber"
        })
        public static class ExecutionOffset
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
            @XmlElement(name = "DateScheduleLastModifiedInCampaign", required = true)
            protected String dateScheduleLastModifiedInCampaign;
            @XmlElement(name = "FirstOccurrenceToPublish")
            protected byte firstOccurrenceToPublish;
            @XmlElement(name = "LoadedFromOldDavLocation", required = true)
            protected String loadedFromOldDavLocation;
            @XmlElement(name = "Loading", required = true)
            protected String loading;
            @XmlElement(name = "NeedToPersist", required = true)
            protected String needToPersist;
            @XmlElement(name = "Offset")
            protected byte offset;
            @XmlElement(name = "OldVersionNumber", required = true)
            protected String oldVersionNumber;
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "ScheduleType", required = true)
            protected String scheduleType;
            @XmlElement(name = "Units", required = true)
            protected String units;
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

               
            public String getDateScheduleLastModifiedInCampaign() {
                return dateScheduleLastModifiedInCampaign;
            }

               
            public void setDateScheduleLastModifiedInCampaign(String value) {
                this.dateScheduleLastModifiedInCampaign = value;
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

               
            public byte getOffset() {
                return offset;
            }

               
            public void setOffset(byte value) {
                this.offset = value;
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

               
            public String getScheduleType() {
                return scheduleType;
            }

               
            public void setScheduleType(String value) {
                this.scheduleType = value;
            }

               
            public String getUnits() {
                return units;
            }

               
            public void setUnits(String value) {
                this.units = value;
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
        public static class ScheduleActionList
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
            "cellAvailableForLinking",
            "cellCode",
            "cellNodeData",
            "codeChangeUnpublished",
            "codeEverBeenPublished",
            "contactHistoryMapped",
            "contactHistoryVariables",
            "count",
            "description",
            "eventReply",
            "firstOccurrenceToPublish",
            "liveCount",
            "liveSinceLastClearCount",
            "loadedFromOldDavLocation",
            "loading",
            "name",
            "needToPersist",
            "oldVersionNumber",
            "packageDO",
            "persisting",
            "publishState",
            "replyCode",
            "subjectVariables",
            "surrogateKeyNbr",
            "trackingPresentedTreatments",
            "updateContactHistory",
            "versionNumber"
    })
    public static class StandardReply
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;
        @XmlElement(name = "CellAvailableForLinking", required = true)
        protected String cellAvailableForLinking;
        @XmlElement(name = "CellCode", required = true)
        protected String cellCode;
        @XmlElement(name = "CellNodeData", required = true)
        protected StandardReply.CellNodeData cellNodeData;
        @XmlElement(name = "CodeChangeUnpublished", required = true)
        protected String codeChangeUnpublished;
        @XmlElement(name = "CodeEverBeenPublished", required = true)
        protected String codeEverBeenPublished;
        @XmlElement(name = "ContactHistoryMapped", required = true)
        protected String contactHistoryMapped;
        @XmlElement(name = "ContactHistoryVariables", required = true)
        protected StandardReply.ContactHistoryVariables contactHistoryVariables;
        @XmlElement(name = "Count")
        protected byte count;
        @XmlElement(name = "Description", required = true)
        protected String description;
        @XmlElement(name = "EventReply", required = true)
        protected StandardReply.EventReply eventReply;
        @XmlElement(name = "FirstOccurrenceToPublish")
        protected byte firstOccurrenceToPublish;
        @XmlElement(name = "LiveCount")
        protected byte liveCount;
        @XmlElement(name = "LiveSinceLastClearCount")
        protected byte liveSinceLastClearCount;
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
        @XmlElement(name = "PackageDO", required = true)
        protected StandardReply.PackageDO packageDO;
        @XmlElement(name = "Persisting", required = true)
        protected String persisting;
        @XmlElement(name = "PublishState")
        protected byte publishState;
        @XmlElement(name = "ReplyCode", required = true)
        protected String replyCode;
        @XmlElement(name = "SubjectVariables", required = true)
        protected StandardReply.SubjectVariables subjectVariables;
        @XmlElement(name = "SurrogateKeyNbr")
        protected short surrogateKeyNbr;
        @XmlElement(name = "TrackingPresentedTreatments", required = true)
        protected String trackingPresentedTreatments;
        @XmlElement(name = "UpdateContactHistory", required = true)
        protected String updateContactHistory;
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

           
        public String getCellAvailableForLinking() {
            return cellAvailableForLinking;
        }

           
        public void setCellAvailableForLinking(String value) {
            this.cellAvailableForLinking = value;
        }

           
        public String getCellCode() {
            return cellCode;
        }

           
        public void setCellCode(String value) {
            this.cellCode = value;
        }

           
        public StandardReply.CellNodeData getCellNodeData() {
            return cellNodeData;
        }

           
        public void setCellNodeData(StandardReply.CellNodeData value) {
            this.cellNodeData = value;
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

           
        public StandardReply.ContactHistoryVariables getContactHistoryVariables() {
            return contactHistoryVariables;
        }

           
        public void setContactHistoryVariables(StandardReply.ContactHistoryVariables value) {
            this.contactHistoryVariables = value;
        }

           
        public byte getCount() {
            return count;
        }

           
        public void setCount(byte value) {
            this.count = value;
        }

           
        public String getDescription() {
            return description;
        }

           
        public void setDescription(String value) {
            this.description = value;
        }

           
        public StandardReply.EventReply getEventReply() {
            return eventReply;
        }

           
        public void setEventReply(StandardReply.EventReply value) {
            this.eventReply = value;
        }

           
        public byte getFirstOccurrenceToPublish() {
            return firstOccurrenceToPublish;
        }

           
        public void setFirstOccurrenceToPublish(byte value) {
            this.firstOccurrenceToPublish = value;
        }

           
        public byte getLiveCount() {
            return liveCount;
        }

           
        public void setLiveCount(byte value) {
            this.liveCount = value;
        }

           
        public byte getLiveSinceLastClearCount() {
            return liveSinceLastClearCount;
        }

           
        public void setLiveSinceLastClearCount(byte value) {
            this.liveSinceLastClearCount = value;
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

           
        public StandardReply.PackageDO getPackageDO() {
            return packageDO;
        }

           
        public void setPackageDO(StandardReply.PackageDO value) {
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

           
        public String getReplyCode() {
            return replyCode;
        }

           
        public void setReplyCode(String value) {
            this.replyCode = value;
        }

           
        public StandardReply.SubjectVariables getSubjectVariables() {
            return subjectVariables;
        }

           
        public void setSubjectVariables(StandardReply.SubjectVariables value) {
            this.subjectVariables = value;
        }

           
        public short getSurrogateKeyNbr() {
            return surrogateKeyNbr;
        }

           
        public void setSurrogateKeyNbr(short value) {
            this.surrogateKeyNbr = value;
        }

           
        public String getTrackingPresentedTreatments() {
            return trackingPresentedTreatments;
        }

           
        public void setTrackingPresentedTreatments(String value) {
            this.trackingPresentedTreatments = value;
        }

           
        public String getUpdateContactHistory() {
            return updateContactHistory;
        }

           
        public void setUpdateContactHistory(String value) {
            this.updateContactHistory = value;
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
                "ancestorLinkNodes",
                "archivedPublishState",
                "cellAvailableForLinking",
                "cellControlGroupNumber",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "controlGroup",
                "controlKey",
                "droppedCount",
                "excludeFlag",
                "excludeIconPath",
                "firstOccurrenceToPublish",
                "iconPath",
                "label",
                "loadedFromOldDavLocation",
                "loading",
                "marketingCell",
                "needToPersist",
                "nodeState",
                "nodeTags",
                "nodeType",
                "oldVersionNumber",
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
                "y"
        })
        public static class CellNodeData
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "AncestorLinkNodes", required = true)
            protected CellNodeData.AncestorLinkNodes ancestorLinkNodes;
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
            @XmlElement(name = "NodeState")
            protected byte nodeState;
            @XmlElement(name = "NodeTags", required = true)
            protected CellNodeData.NodeTags nodeTags;
            @XmlElement(name = "NodeType")
            protected byte nodeType;
            @XmlElement(name = "OldVersionNumber", required = true)
            protected String oldVersionNumber;
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "RemainderCell", required = true)
            protected String remainderCell;
            @XmlElement(name = "SortByList", required = true)
            protected CellNodeData.SortByList sortByList;
            @XmlElement(name = "Splitters", required = true)
            protected CellNodeData.Splitters splitters;
            @XmlElement(name = "SubjectID", required = true)
            protected String subjectID;
            @XmlElement(name = "SurrogateKeyNbr")
            protected short surrogateKeyNbr;
            @XmlElement(name = "TotalCount")
            protected byte totalCount;
            @XmlElement(name = "ValidInputSubject", required = true)
            protected String validInputSubject;
            @XmlElement(name = "ValidOutputSubject", required = true)
            protected String validOutputSubject;
            @XmlElement(name = "VersionNumber")
            protected float versionNumber;
            @XmlElement(name = "X")
            protected byte x;
            @XmlElement(name = "Y")
            protected byte y;
            @XmlAttribute(name = "ConcreteClass")
            protected String concreteClass;
            @XmlAttribute(name = "objid")
            protected Short objid;

               
            public CellNodeData.AncestorLinkNodes getAncestorLinkNodes() {
                return ancestorLinkNodes;
            }

               
            public void setAncestorLinkNodes(CellNodeData.AncestorLinkNodes value) {
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

               
            public byte getNodeState() {
                return nodeState;
            }

               
            public void setNodeState(byte value) {
                this.nodeState = value;
            }

               
            public CellNodeData.NodeTags getNodeTags() {
                return nodeTags;
            }

               
            public void setNodeTags(CellNodeData.NodeTags value) {
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

               
            public CellNodeData.SortByList getSortByList() {
                return sortByList;
            }

               
            public void setSortByList(CellNodeData.SortByList value) {
                this.sortByList = value;
            }

               
            public CellNodeData.Splitters getSplitters() {
                return splitters;
            }

               
            public void setSplitters(CellNodeData.Splitters value) {
                this.splitters = value;
            }

               
            public String getSubjectID() {
                return subjectID;
            }

               
            public void setSubjectID(String value) {
                this.subjectID = value;
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

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        public static class ContactHistoryVariables
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
                    protected IBVariableDO.ErrorValue errorValue;
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

                       
                    public IBVariableDO.ErrorValue getErrorValue() {
                        return errorValue;
                    }

                       
                    public void setErrorValue(IBVariableDO.ErrorValue value) {
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

                }

            }

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

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "testCaseDO"
    })
    public static class TestCases
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "TestCaseDO", required = true)
        protected TestCases.TestCaseDO testCaseDO;

           
        public TestCases.TestCaseDO getTestCaseDO() {
            return testCaseDO;
        }

           
        public void setTestCaseDO(TestCases.TestCaseDO value) {
            this.testCaseDO = value;
        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "archivedPublishState",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "description",
                "firstOccurrenceToPublish",
                "flowType",
                "globalVars",
                "id",
                "lastRunBy",
                "loadedFromOldDavLocation",
                "loading",
                "name",
                "needToPersist",
                "oldVersionNumber",
                "persisting",
                "publishState",
                "replyVars",
                "requestVars",
                "results",
                "returnedType",
                "timeZoneId",
                "type",
                "versionNumber"
        })
        public static class TestCaseDO
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
            @XmlElement(name = "Description", required = true)
            protected String description;
            @XmlElement(name = "FirstOccurrenceToPublish")
            protected byte firstOccurrenceToPublish;
            @XmlElement(name = "FlowType", required = true)
            protected String flowType;
            @XmlElement(name = "GlobalVars", required = true)
            protected TestCaseDO.GlobalVars globalVars;
            @XmlElement(name = "Id", required = true)
            protected String id;
            @XmlElement(name = "LastRunBy", required = true)
            protected String lastRunBy;
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
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "ReplyVars", required = true)
            protected TestCaseDO.ReplyVars replyVars;
            @XmlElement(name = "RequestVars", required = true)
            protected TestCaseDO.RequestVars requestVars;
            @XmlElement(name = "Results", required = true)
            protected TestCaseDO.Results results;
            @XmlElement(name = "ReturnedType", required = true)
            protected String returnedType;
            @XmlElement(name = "TimeZoneId", required = true)
            protected String timeZoneId;
            @XmlElement(name = "Type", required = true)
            protected String type;
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

               
            public String getFlowType() {
                return flowType;
            }

               
            public void setFlowType(String value) {
                this.flowType = value;
            }

               
            public TestCaseDO.GlobalVars getGlobalVars() {
                return globalVars;
            }

               
            public void setGlobalVars(TestCaseDO.GlobalVars value) {
                this.globalVars = value;
            }

               
            public String getId() {
                return id;
            }

               
            public void setId(String value) {
                this.id = value;
            }

               
            public String getLastRunBy() {
                return lastRunBy;
            }

               
            public void setLastRunBy(String value) {
                this.lastRunBy = value;
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

               
            public TestCaseDO.ReplyVars getReplyVars() {
                return replyVars;
            }

               
            public void setReplyVars(TestCaseDO.ReplyVars value) {
                this.replyVars = value;
            }

               
            public TestCaseDO.RequestVars getRequestVars() {
                return requestVars;
            }

               
            public void setRequestVars(TestCaseDO.RequestVars value) {
                this.requestVars = value;
            }

               
            public TestCaseDO.Results getResults() {
                return results;
            }

               
            public void setResults(TestCaseDO.Results value) {
                this.results = value;
            }

               
            public String getReturnedType() {
                return returnedType;
            }

               
            public void setReturnedType(String value) {
                this.returnedType = value;
            }

               
            public String getTimeZoneId() {
                return timeZoneId;
            }

               
            public void setTimeZoneId(String value) {
                this.timeZoneId = value;
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
            public static class GlobalVars
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
            public static class ReplyVars
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
            public static class RequestVars
                    implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "IBVariableDO")
                protected List<RequestVars.IBVariableDO> ibVariableDOs;

                   
                public List<RequestVars.IBVariableDO> getIBVariableDOs() {
                    if (ibVariableDOs == null) {
                        ibVariableDOs = new ArrayList<RequestVars.IBVariableDO>();
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
                    protected com.sas.rtdm2id.model.rtdm.FlowDO.TestCases.TestCaseDO.RequestVars.IBVariableDO.SharedIn sharedIn;
                    @XmlElement(name = "TestVarInfoId", required = true)
                    protected String testVarInfoId;
                    @XmlElement(name = "Type")
                    protected byte type;
                    @XmlElement(name = "TypeDescription", required = true)
                    protected String typeDescription;
                    @XmlElement(name = "Value")
                    protected com.sas.rtdm2id.model.rtdm.FlowDO.TestCases.TestCaseDO.RequestVars.IBVariableDO.Value value;
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

                       
                    public com.sas.rtdm2id.model.rtdm.FlowDO.TestCases.TestCaseDO.RequestVars.IBVariableDO.SharedIn getSharedIn() {
                        return sharedIn;
                    }

                       
                    public void setSharedIn(com.sas.rtdm2id.model.rtdm.FlowDO.TestCases.TestCaseDO.RequestVars.IBVariableDO.SharedIn value) {
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

                       
                    public com.sas.rtdm2id.model.rtdm.FlowDO.TestCases.TestCaseDO.RequestVars.IBVariableDO.Value getValue() {
                        return value;
                    }

                       
                    public void setValue(com.sas.rtdm2id.model.rtdm.FlowDO.TestCases.TestCaseDO.RequestVars.IBVariableDO.Value value) {
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
                            "stringValue",
                            "type",
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
                        @XmlElement(name = "StringValue", required = true)
                        protected String stringValue;
                        @XmlElement(name = "Type")
                        protected byte type;
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

                           
                        public String getStringValue() {
                            return stringValue;
                        }

                           
                        public void setStringValue(String value) {
                            this.stringValue = value;
                        }

                           
                        public byte getType() {
                            return type;
                        }

                           
                        public void setType(byte value) {
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


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "archivedPublishState",
                    "codeChangeUnpublished",
                    "codeEverBeenPublished",
                    "executedNodePath",
                    "firstOccurrenceToPublish",
                    "index",
                    "loadedFromOldDavLocation",
                    "loading",
                    "needToPersist",
                    "oldVersionNumber",
                    "persisting",
                    "publishState",
                    "resultsStatus",
                    "testDuration",
                    "versionNumber"
            })
            public static class Results
                    implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "ArchivedPublishState")
                protected byte archivedPublishState;
                @XmlElement(name = "CodeChangeUnpublished", required = true)
                protected String codeChangeUnpublished;
                @XmlElement(name = "CodeEverBeenPublished", required = true)
                protected String codeEverBeenPublished;
                @XmlElement(name = "ExecutedNodePath", required = true)
                protected Results.ExecutedNodePath executedNodePath;
                @XmlElement(name = "FirstOccurrenceToPublish")
                protected byte firstOccurrenceToPublish;
                @XmlElement(name = "Index")
                protected byte index;
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
                @XmlElement(name = "ResultsStatus", required = true)
                protected String resultsStatus;
                @XmlElement(name = "TestDuration")
                protected float testDuration;
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

                   
                public Results.ExecutedNodePath getExecutedNodePath() {
                    return executedNodePath;
                }

                   
                public void setExecutedNodePath(Results.ExecutedNodePath value) {
                    this.executedNodePath = value;
                }

                   
                public byte getFirstOccurrenceToPublish() {
                    return firstOccurrenceToPublish;
                }

                   
                public void setFirstOccurrenceToPublish(byte value) {
                    this.firstOccurrenceToPublish = value;
                }

                   
                public byte getIndex() {
                    return index;
                }

                   
                public void setIndex(byte value) {
                    this.index = value;
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

                   
                public String getResultsStatus() {
                    return resultsStatus;
                }

                   
                public void setResultsStatus(String value) {
                    this.resultsStatus = value;
                }

                   
                public float getTestDuration() {
                    return testDuration;
                }

                   
                public void setTestDuration(float value) {
                    this.testDuration = value;
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
                        "value"
                })
                public static class ExecutedNodePath
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

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "userAndGroupDOs"
    })
    public static class UserRoles
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "UserAndGroupDO")
        protected List<UserRoles.UserAndGroupDO> userAndGroupDOs;

           
        public List<UserRoles.UserAndGroupDO> getUserAndGroupDOs() {
            if (userAndGroupDOs == null) {
                userAndGroupDOs = new ArrayList<UserRoles.UserAndGroupDO>();
            }
            return this.userAndGroupDOs;
        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "approve",
                "archivedPublishState",
                "canBeEdited",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "displayName",
                "edit",
                "firstOccurrenceToPublish",
                "id",
                "isAGroup",
                "isInherited",
                "loadedFromOldDavLocation",
                "loading",
                "memberOfCurrentBC",
                "members",
                "name",
                "needToPersist",
                "oldVersionNumber",
                "persisting",
                "publishState",
                "read",
                "versionNumber",
                "viewInPortal"
        })
        public static class UserAndGroupDO
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "Approve", required = true)
            protected String approve;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "CanBeEdited", required = true)
            protected String canBeEdited;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
            @XmlElement(name = "DisplayName", required = true)
            protected String displayName;
            @XmlElement(name = "Edit", required = true)
            protected String edit;
            @XmlElement(name = "FirstOccurrenceToPublish")
            protected byte firstOccurrenceToPublish;
            @XmlElement(name = "Id", required = true)
            protected String id;
            @XmlElement(name = "IsAGroup", required = true)
            protected String isAGroup;
            @XmlElement(name = "IsInherited", required = true)
            protected String isInherited;
            @XmlElement(name = "LoadedFromOldDavLocation", required = true)
            protected String loadedFromOldDavLocation;
            @XmlElement(name = "Loading", required = true)
            protected String loading;
            @XmlElement(name = "MemberOfCurrentBC", required = true)
            protected String memberOfCurrentBC;
            @XmlElement(name = "Members", required = true)
            protected UserAndGroupDO.Members members;
            @XmlElement(name = "Name", required = true)
            protected String name;
            @XmlElement(name = "NeedToPersist", required = true)
            protected String needToPersist;
            @XmlElement(name = "OldVersionNumber", required = true)
            protected String oldVersionNumber;
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "Read", required = true)
            protected String read;
            @XmlElement(name = "VersionNumber")
            protected float versionNumber;
            @XmlElement(name = "ViewInPortal", required = true)
            protected String viewInPortal;
            @XmlAttribute(name = "ConcreteClass")
            protected String concreteClass;
            @XmlAttribute(name = "listpos")
            protected Byte listpos;
            @XmlAttribute(name = "objid")
            protected Short objid;

               
            public String getApprove() {
                return approve;
            }

               
            public void setApprove(String value) {
                this.approve = value;
            }

               
            public byte getArchivedPublishState() {
                return archivedPublishState;
            }

               
            public void setArchivedPublishState(byte value) {
                this.archivedPublishState = value;
            }

               
            public String getCanBeEdited() {
                return canBeEdited;
            }

               
            public void setCanBeEdited(String value) {
                this.canBeEdited = value;
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

               
            public String getDisplayName() {
                return displayName;
            }

               
            public void setDisplayName(String value) {
                this.displayName = value;
            }

               
            public String getEdit() {
                return edit;
            }

               
            public void setEdit(String value) {
                this.edit = value;
            }

               
            public byte getFirstOccurrenceToPublish() {
                return firstOccurrenceToPublish;
            }

               
            public void setFirstOccurrenceToPublish(byte value) {
                this.firstOccurrenceToPublish = value;
            }

               
            public String getId() {
                return id;
            }

               
            public void setId(String value) {
                this.id = value;
            }

               
            public String getIsAGroup() {
                return isAGroup;
            }

               
            public void setIsAGroup(String value) {
                this.isAGroup = value;
            }

               
            public String getIsInherited() {
                return isInherited;
            }

               
            public void setIsInherited(String value) {
                this.isInherited = value;
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

               
            public String getMemberOfCurrentBC() {
                return memberOfCurrentBC;
            }

               
            public void setMemberOfCurrentBC(String value) {
                this.memberOfCurrentBC = value;
            }

               
            public UserAndGroupDO.Members getMembers() {
                return members;
            }

               
            public void setMembers(UserAndGroupDO.Members value) {
                this.members = value;
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

               
            public String getRead() {
                return read;
            }

               
            public void setRead(String value) {
                this.read = value;
            }

               
            public float getVersionNumber() {
                return versionNumber;
            }

               
            public void setVersionNumber(float value) {
                this.versionNumber = value;
            }

               
            public String getViewInPortal() {
                return viewInPortal;
            }

               
            public void setViewInPortal(String value) {
                this.viewInPortal = value;
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
            public static class Members
                    implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlValue
                protected String value;
                @XmlAttribute(name = "EmptyList")
                protected String emptyList;
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

                   
                public String getEmptyList() {
                    return emptyList;
                }

                   
                public void setEmptyList(String value) {
                    this.emptyList = value;
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

        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class ViewerList
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
    public static class ZOrderIdList
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

}