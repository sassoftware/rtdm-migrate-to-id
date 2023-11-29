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
        "activationState",
        "archivedPublishState",
        "attachments",
        "automaticLayoutNeeded",
        "businessContextId",
        "campaignExecutionHistoryDO",
        "campaignMessages",
        "campaignType",
        "checkListItems",
        "code",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "customPageDetails",
        "dateCreated",
        "dateModified",
        "dateModifiedCorrect",
        "decisionCampaignType",
        "definition",
        "description",
        "documentOnExecute",
        "executionClientType",
        "executionPublishDone",
        "firstOccurrenceToPublish",
        "flowType",
        "folder",
        "forceCleanPublish",
        "forceIncrementalPublish",
        "forced",
        "hasAudience",
        "hasCellRefs",
        "hasCells",
        "hasCommunications",
        "hasDiagram",
        "hidden",
        "id",
        "isScheduleUsed",
        "lastDeployDate",
        "lastRunDate",
        "lastSavedDate",
        "loadedFromOldDavLocation",
        "loading",
        "lockedBy",
        "markDeployPublish",
        "maxBudget",
        "maxNumContacts",
        "maxNumOffers",
        "memberOfOptimizeGroup",
        "metadataType",
        "migratedDiagram",
        "minBudget",
        "minNumOffers",
        "modifiedUser",
        "name",
        "needToPersist",
        "noWritePermission",
        "oldVersionNumber",
        "openAsReadOnly",
        "optimizable",
        "optimizationMethod",
        "optimizationReadOnly",
        "optimizeStatus",
        "owner",
        "parentScheduleFlowId",
        "persisting",
        "processingEndDate",
        "processingStartDate",
        "publishAll",
        "publishForExecution",
        "publishOnly",
        "publishState",
        "publishedObjects",
        "readOnly",
        "repositoryID",
        "requiresCampaignPublish",
        "saveForExecution",
        "saved",
        "scheduleUUID",
        "shared",
        "standaloneDiagram",
        "suppressPublishOnSave",
        "surrogateKeyNbr",
        "surrogateKeyNbrSaved",
        "tempScheduleMD5Sum",
        "thisVersionPublished",
        "totalCount",
        "userRoles",
        "userUIRole",
        "versionHistory",
        "versionHistoryMigrated",
        "versionNumber"
})
public class CampaignDO
        implements Serializable {

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ActivationState")
    protected byte activationState;
    @XmlElement(name = "ArchivedPublishState")
    protected byte archivedPublishState;
    @XmlElement(name = "Attachments", required = true)
    protected String attachments;
    @XmlElement(name = "AutomaticLayoutNeeded", required = true)
    protected String automaticLayoutNeeded;
    @XmlElement(name = "BusinessContextId", required = true)
    protected String businessContextId;
    @XmlElement(name = "CampaignExecutionHistoryDO", required = true)
    protected CampaignDO.CampaignExecutionHistoryDO campaignExecutionHistoryDO;
    @XmlElement(name = "CampaignMessages", required = true)
    protected CampaignDO.CampaignMessages campaignMessages;
    @XmlElement(name = "CampaignType", required = true)
    protected String campaignType;
    @XmlElement(name = "CheckListItems", required = true)
    protected CampaignDO.CheckListItems checkListItems;
    @XmlElement(name = "Code", required = true)
    protected String code;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    protected String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    protected String codeEverBeenPublished;
    @XmlElement(name = "CustomPageDetails", required = true)
    protected CampaignDO.CustomPageDetails customPageDetails;
    @XmlElement(name = "DateCreated", required = true)
    protected String dateCreated;
    @XmlElement(name = "DateModified", required = true)
    protected String dateModified;
    @XmlElement(name = "DateModifiedCorrect", required = true)
    protected String dateModifiedCorrect;
    @XmlElement(name = "DecisionCampaignType", required = true)
    protected String decisionCampaignType;
    @XmlElement(name = "Definition", required = true)
    protected CampaignDO.Definition definition;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "DocumentOnExecute", required = true)
    protected String documentOnExecute;
    @XmlElement(name = "ExecutionClientType")
    protected byte executionClientType;
    @XmlElement(name = "ExecutionPublishDone", required = true)
    protected String executionPublishDone;
    @XmlElement(name = "FirstOccurrenceToPublish")
    protected byte firstOccurrenceToPublish;
    @XmlElement(name = "FlowType", required = true)
    protected String flowType;
    @XmlElement(name = "Folder", required = true)
    protected CampaignDO.Folder folder;
    @XmlElement(name = "ForceCleanPublish", required = true)
    protected String forceCleanPublish;
    @XmlElement(name = "ForceIncrementalPublish", required = true)
    protected String forceIncrementalPublish;
    @XmlElement(name = "Forced", required = true)
    protected String forced;
    @XmlElement(name = "HasAudience", required = true)
    protected String hasAudience;
    @XmlElement(name = "HasCellRefs", required = true)
    protected String hasCellRefs;
    @XmlElement(name = "HasCells", required = true)
    protected String hasCells;
    @XmlElement(name = "HasCommunications", required = true)
    protected String hasCommunications;
    @XmlElement(name = "HasDiagram", required = true)
    protected String hasDiagram;
    @XmlElement(name = "Hidden", required = true)
    protected String hidden;
    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "IsScheduleUsed", required = true)
    protected String isScheduleUsed;
    @XmlElement(name = "LastDeployDate", required = true)
    protected String lastDeployDate;
    @XmlElement(name = "LastRunDate", required = true)
    protected String lastRunDate;
    @XmlElement(name = "LastSavedDate", required = true)
    protected String lastSavedDate;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    protected String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    protected String loading;
    @XmlElement(name = "LockedBy", required = true)
    protected String lockedBy;
    @XmlElement(name = "MarkDeployPublish", required = true)
    protected String markDeployPublish;
    @XmlElement(name = "MaxBudget")
    protected float maxBudget;
    @XmlElement(name = "MaxNumContacts")
    protected byte maxNumContacts;
    @XmlElement(name = "MaxNumOffers")
    protected byte maxNumOffers;
    @XmlElement(name = "MemberOfOptimizeGroup", required = true)
    protected String memberOfOptimizeGroup;
    @XmlElement(name = "MetadataType", required = true)
    protected String metadataType;
    @XmlElement(name = "MigratedDiagram", required = true)
    protected String migratedDiagram;
    @XmlElement(name = "MinBudget")
    protected float minBudget;
    @XmlElement(name = "MinNumOffers")
    protected byte minNumOffers;
    @XmlElement(name = "ModifiedUser", required = true)
    protected String modifiedUser;
    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "NeedToPersist", required = true)
    protected String needToPersist;
    @XmlElement(name = "NoWritePermission", required = true)
    protected String noWritePermission;
    @XmlElement(name = "OldVersionNumber", required = true)
    protected String oldVersionNumber;
    @XmlElement(name = "OpenAsReadOnly", required = true)
    protected String openAsReadOnly;
    @XmlElement(name = "Optimizable", required = true)
    protected String optimizable;
    @XmlElement(name = "OptimizationMethod", required = true)
    protected String optimizationMethod;
    @XmlElement(name = "OptimizationReadOnly", required = true)
    protected String optimizationReadOnly;
    @XmlElement(name = "OptimizeStatus", required = true)
    protected String optimizeStatus;
    @XmlElement(name = "Owner", required = true)
    protected String owner;
    @XmlElement(name = "ParentScheduleFlowId", required = true)
    protected String parentScheduleFlowId;
    @XmlElement(name = "Persisting", required = true)
    protected String persisting;
    @XmlElement(name = "ProcessingEndDate", required = true)
    protected CampaignDO.ProcessingEndDate processingEndDate;
    @XmlElement(name = "ProcessingStartDate", required = true)
    protected CampaignDO.ProcessingStartDate processingStartDate;
    @XmlElement(name = "PublishAll", required = true)
    protected String publishAll;
    @XmlElement(name = "PublishForExecution", required = true)
    protected String publishForExecution;
    @XmlElement(name = "PublishOnly", required = true)
    protected String publishOnly;
    @XmlElement(name = "PublishState")
    protected byte publishState;
    @XmlElement(name = "PublishedObjects", required = true)
    protected CampaignDO.PublishedObjects publishedObjects;
    @XmlElement(name = "ReadOnly", required = true)
    protected String readOnly;
    @XmlElement(name = "RepositoryID", required = true)
    protected String repositoryID;
    @XmlElement(name = "RequiresCampaignPublish", required = true)
    protected String requiresCampaignPublish;
    @XmlElement(name = "SaveForExecution", required = true)
    protected String saveForExecution;
    @XmlElement(name = "Saved", required = true)
    protected String saved;
    @XmlElement(name = "ScheduleUUID", required = true)
    protected String scheduleUUID;
    @XmlElement(name = "Shared", required = true)
    protected String shared;
    @XmlElement(name = "StandaloneDiagram", required = true)
    protected String standaloneDiagram;
    @XmlElement(name = "SuppressPublishOnSave", required = true)
    protected String suppressPublishOnSave;
    @XmlElement(name = "SurrogateKeyNbr")
    protected short surrogateKeyNbr;
    @XmlElement(name = "SurrogateKeyNbrSaved")
    protected short surrogateKeyNbrSaved;
    @XmlElement(name = "TempScheduleMD5Sum", required = true)
    protected String tempScheduleMD5Sum;
    @XmlElement(name = "ThisVersionPublished", required = true)
    protected String thisVersionPublished;
    @XmlElement(name = "TotalCount")
    protected byte totalCount;
    @XmlElement(name = "UserRoles", required = true)
    protected CampaignDO.UserRoles userRoles;
    @XmlElement(name = "UserUIRole", required = true)
    protected String userUIRole;
    @XmlElement(name = "VersionHistory", required = true)
    protected CampaignDO.VersionHistory versionHistory;
    @XmlElement(name = "VersionHistoryMigrated", required = true)
    protected String versionHistoryMigrated;
    @XmlElement(name = "VersionNumber")
    protected float versionNumber;
    @XmlAttribute(name = "objid")
    protected Byte objid;

       
    public byte getActivationState() {
        return activationState;
    }

       
    public void setActivationState(byte value) {
        this.activationState = value;
    }

       
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

       
    public String getAutomaticLayoutNeeded() {
        return automaticLayoutNeeded;
    }

       
    public void setAutomaticLayoutNeeded(String value) {
        this.automaticLayoutNeeded = value;
    }

       
    public String getBusinessContextId() {
        return businessContextId;
    }

       
    public void setBusinessContextId(String value) {
        this.businessContextId = value;
    }

       
    public CampaignDO.CampaignExecutionHistoryDO getCampaignExecutionHistoryDO() {
        return campaignExecutionHistoryDO;
    }

       
    public void setCampaignExecutionHistoryDO(CampaignDO.CampaignExecutionHistoryDO value) {
        this.campaignExecutionHistoryDO = value;
    }

       
    public CampaignDO.CampaignMessages getCampaignMessages() {
        return campaignMessages;
    }

       
    public void setCampaignMessages(CampaignDO.CampaignMessages value) {
        this.campaignMessages = value;
    }

       
    public String getCampaignType() {
        return campaignType;
    }

       
    public void setCampaignType(String value) {
        this.campaignType = value;
    }

       
    public CampaignDO.CheckListItems getCheckListItems() {
        return checkListItems;
    }

       
    public void setCheckListItems(CampaignDO.CheckListItems value) {
        this.checkListItems = value;
    }

       
    public String getCode() {
        return code;
    }

       
    public void setCode(String value) {
        this.code = value;
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

       
    public CampaignDO.CustomPageDetails getCustomPageDetails() {
        return customPageDetails;
    }

       
    public void setCustomPageDetails(CampaignDO.CustomPageDetails value) {
        this.customPageDetails = value;
    }

       
    public String getDateCreated() {
        return dateCreated;
    }

       
    public void setDateCreated(String value) {
        this.dateCreated = value;
    }

       
    public String getDateModified() {
        return dateModified;
    }

       
    public void setDateModified(String value) {
        this.dateModified = value;
    }

       
    public String getDateModifiedCorrect() {
        return dateModifiedCorrect;
    }

       
    public void setDateModifiedCorrect(String value) {
        this.dateModifiedCorrect = value;
    }

       
    public String getDecisionCampaignType() {
        return decisionCampaignType;
    }

       
    public void setDecisionCampaignType(String value) {
        this.decisionCampaignType = value;
    }

       
    public CampaignDO.Definition getDefinition() {
        return definition;
    }

       
    public void setDefinition(CampaignDO.Definition value) {
        this.definition = value;
    }

       
    public String getDescription() {
        return description;
    }

       
    public void setDescription(String value) {
        this.description = value;
    }

       
    public String getDocumentOnExecute() {
        return documentOnExecute;
    }

       
    public void setDocumentOnExecute(String value) {
        this.documentOnExecute = value;
    }

       
    public byte getExecutionClientType() {
        return executionClientType;
    }

       
    public void setExecutionClientType(byte value) {
        this.executionClientType = value;
    }

       
    public String getExecutionPublishDone() {
        return executionPublishDone;
    }

       
    public void setExecutionPublishDone(String value) {
        this.executionPublishDone = value;
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

       
    public CampaignDO.Folder getFolder() {
        return folder;
    }

       
    public void setFolder(CampaignDO.Folder value) {
        this.folder = value;
    }

       
    public String getForceCleanPublish() {
        return forceCleanPublish;
    }

       
    public void setForceCleanPublish(String value) {
        this.forceCleanPublish = value;
    }

       
    public String getForceIncrementalPublish() {
        return forceIncrementalPublish;
    }

       
    public void setForceIncrementalPublish(String value) {
        this.forceIncrementalPublish = value;
    }

       
    public String getForced() {
        return forced;
    }

       
    public void setForced(String value) {
        this.forced = value;
    }

       
    public String getHasAudience() {
        return hasAudience;
    }

       
    public void setHasAudience(String value) {
        this.hasAudience = value;
    }

       
    public String getHasCellRefs() {
        return hasCellRefs;
    }

       
    public void setHasCellRefs(String value) {
        this.hasCellRefs = value;
    }

       
    public String getHasCells() {
        return hasCells;
    }

       
    public void setHasCells(String value) {
        this.hasCells = value;
    }

       
    public String getHasCommunications() {
        return hasCommunications;
    }

       
    public void setHasCommunications(String value) {
        this.hasCommunications = value;
    }

       
    public String getHasDiagram() {
        return hasDiagram;
    }

       
    public void setHasDiagram(String value) {
        this.hasDiagram = value;
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

       
    public String getIsScheduleUsed() {
        return isScheduleUsed;
    }

       
    public void setIsScheduleUsed(String value) {
        this.isScheduleUsed = value;
    }

       
    public String getLastDeployDate() {
        return lastDeployDate;
    }

       
    public void setLastDeployDate(String value) {
        this.lastDeployDate = value;
    }

       
    public String getLastRunDate() {
        return lastRunDate;
    }

       
    public void setLastRunDate(String value) {
        this.lastRunDate = value;
    }

       
    public String getLastSavedDate() {
        return lastSavedDate;
    }

       
    public void setLastSavedDate(String value) {
        this.lastSavedDate = value;
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

       
    public String getMarkDeployPublish() {
        return markDeployPublish;
    }

       
    public void setMarkDeployPublish(String value) {
        this.markDeployPublish = value;
    }

       
    public float getMaxBudget() {
        return maxBudget;
    }

       
    public void setMaxBudget(float value) {
        this.maxBudget = value;
    }

       
    public byte getMaxNumContacts() {
        return maxNumContacts;
    }

       
    public void setMaxNumContacts(byte value) {
        this.maxNumContacts = value;
    }

       
    public byte getMaxNumOffers() {
        return maxNumOffers;
    }

       
    public void setMaxNumOffers(byte value) {
        this.maxNumOffers = value;
    }

       
    public String getMemberOfOptimizeGroup() {
        return memberOfOptimizeGroup;
    }

       
    public void setMemberOfOptimizeGroup(String value) {
        this.memberOfOptimizeGroup = value;
    }

       
    public String getMetadataType() {
        return metadataType;
    }

       
    public void setMetadataType(String value) {
        this.metadataType = value;
    }

       
    public String getMigratedDiagram() {
        return migratedDiagram;
    }

       
    public void setMigratedDiagram(String value) {
        this.migratedDiagram = value;
    }

       
    public float getMinBudget() {
        return minBudget;
    }

       
    public void setMinBudget(float value) {
        this.minBudget = value;
    }

       
    public byte getMinNumOffers() {
        return minNumOffers;
    }

       
    public void setMinNumOffers(byte value) {
        this.minNumOffers = value;
    }

       
    public String getModifiedUser() {
        return modifiedUser;
    }

       
    public void setModifiedUser(String value) {
        this.modifiedUser = value;
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

       
    public String getOpenAsReadOnly() {
        return openAsReadOnly;
    }

       
    public void setOpenAsReadOnly(String value) {
        this.openAsReadOnly = value;
    }

       
    public String getOptimizable() {
        return optimizable;
    }

       
    public void setOptimizable(String value) {
        this.optimizable = value;
    }

       
    public String getOptimizationMethod() {
        return optimizationMethod;
    }

       
    public void setOptimizationMethod(String value) {
        this.optimizationMethod = value;
    }

       
    public String getOptimizationReadOnly() {
        return optimizationReadOnly;
    }

       
    public void setOptimizationReadOnly(String value) {
        this.optimizationReadOnly = value;
    }

       
    public String getOptimizeStatus() {
        return optimizeStatus;
    }

       
    public void setOptimizeStatus(String value) {
        this.optimizeStatus = value;
    }

       
    public String getOwner() {
        return owner;
    }

       
    public void setOwner(String value) {
        this.owner = value;
    }

       
    public String getParentScheduleFlowId() {
        return parentScheduleFlowId;
    }

       
    public void setParentScheduleFlowId(String value) {
        this.parentScheduleFlowId = value;
    }

       
    public String getPersisting() {
        return persisting;
    }

       
    public void setPersisting(String value) {
        this.persisting = value;
    }

       
    public CampaignDO.ProcessingEndDate getProcessingEndDate() {
        return processingEndDate;
    }

       
    public void setProcessingEndDate(CampaignDO.ProcessingEndDate value) {
        this.processingEndDate = value;
    }

       
    public CampaignDO.ProcessingStartDate getProcessingStartDate() {
        return processingStartDate;
    }

       
    public void setProcessingStartDate(CampaignDO.ProcessingStartDate value) {
        this.processingStartDate = value;
    }

       
    public String getPublishAll() {
        return publishAll;
    }

       
    public void setPublishAll(String value) {
        this.publishAll = value;
    }

       
    public String getPublishForExecution() {
        return publishForExecution;
    }

       
    public void setPublishForExecution(String value) {
        this.publishForExecution = value;
    }

       
    public String getPublishOnly() {
        return publishOnly;
    }

       
    public void setPublishOnly(String value) {
        this.publishOnly = value;
    }

       
    public byte getPublishState() {
        return publishState;
    }

       
    public void setPublishState(byte value) {
        this.publishState = value;
    }

       
    public CampaignDO.PublishedObjects getPublishedObjects() {
        return publishedObjects;
    }

       
    public void setPublishedObjects(CampaignDO.PublishedObjects value) {
        this.publishedObjects = value;
    }

       
    public String getReadOnly() {
        return readOnly;
    }

       
    public void setReadOnly(String value) {
        this.readOnly = value;
    }

       
    public String getRepositoryID() {
        return repositoryID;
    }

       
    public void setRepositoryID(String value) {
        this.repositoryID = value;
    }

       
    public String getRequiresCampaignPublish() {
        return requiresCampaignPublish;
    }

       
    public void setRequiresCampaignPublish(String value) {
        this.requiresCampaignPublish = value;
    }

       
    public String getSaveForExecution() {
        return saveForExecution;
    }

       
    public void setSaveForExecution(String value) {
        this.saveForExecution = value;
    }

       
    public String getSaved() {
        return saved;
    }

       
    public void setSaved(String value) {
        this.saved = value;
    }

       
    public String getScheduleUUID() {
        return scheduleUUID;
    }

       
    public void setScheduleUUID(String value) {
        this.scheduleUUID = value;
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

       
    public String getSuppressPublishOnSave() {
        return suppressPublishOnSave;
    }

       
    public void setSuppressPublishOnSave(String value) {
        this.suppressPublishOnSave = value;
    }

       
    public short getSurrogateKeyNbr() {
        return surrogateKeyNbr;
    }

       
    public void setSurrogateKeyNbr(short value) {
        this.surrogateKeyNbr = value;
    }

       
    public short getSurrogateKeyNbrSaved() {
        return surrogateKeyNbrSaved;
    }

       
    public void setSurrogateKeyNbrSaved(short value) {
        this.surrogateKeyNbrSaved = value;
    }

       
    public String getTempScheduleMD5Sum() {
        return tempScheduleMD5Sum;
    }

       
    public void setTempScheduleMD5Sum(String value) {
        this.tempScheduleMD5Sum = value;
    }

       
    public String getThisVersionPublished() {
        return thisVersionPublished;
    }

       
    public void setThisVersionPublished(String value) {
        this.thisVersionPublished = value;
    }

       
    public byte getTotalCount() {
        return totalCount;
    }

       
    public void setTotalCount(byte value) {
        this.totalCount = value;
    }

       
    public CampaignDO.UserRoles getUserRoles() {
        return userRoles;
    }

       
    public void setUserRoles(CampaignDO.UserRoles value) {
        this.userRoles = value;
    }

       
    public String getUserUIRole() {
        return userUIRole;
    }

       
    public void setUserUIRole(String value) {
        this.userUIRole = value;
    }

       
    public CampaignDO.VersionHistory getVersionHistory() {
        return versionHistory;
    }

       
    public void setVersionHistory(CampaignDO.VersionHistory value) {
        this.versionHistory = value;
    }

       
    public String getVersionHistoryMigrated() {
        return versionHistoryMigrated;
    }

       
    public void setVersionHistoryMigrated(String value) {
        this.versionHistoryMigrated = value;
    }

       
    public float getVersionNumber() {
        return versionNumber;
    }

       
    public void setVersionNumber(float value) {
        this.versionNumber = value;
    }

       
    public Byte getObjid() {
        return objid;
    }

       
    public void setObjid(Byte value) {
        this.objid = value;
    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "archivedPublishState",
            "atLeastPartialSuccess",
            "codeChangeUnpublished",
            "codeEverBeenPublished",
            "firstOccurrenceToPublish",
            "loadedFromOldDavLocation",
            "loading",
            "needToPersist",
            "oldVersionNumber",
            "persisting",
            "publishState",
            "usedOptimizationData",
            "versionNumber"
    })
    public static class CampaignExecutionHistoryDO
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;
        @XmlElement(name = "AtLeastPartialSuccess", required = true)
        protected String atLeastPartialSuccess;
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
        @XmlElement(name = "UsedOptimizationData", required = true)
        protected String usedOptimizationData;
        @XmlElement(name = "VersionNumber")
        protected float versionNumber;
        @XmlAttribute(name = "ConcreteClass")
        protected String concreteClass;
        @XmlAttribute(name = "objid")
        protected Byte objid;

           
        public byte getArchivedPublishState() {
            return archivedPublishState;
        }

           
        public void setArchivedPublishState(byte value) {
            this.archivedPublishState = value;
        }

           
        public String getAtLeastPartialSuccess() {
            return atLeastPartialSuccess;
        }

           
        public void setAtLeastPartialSuccess(String value) {
            this.atLeastPartialSuccess = value;
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

           
        public String getUsedOptimizationData() {
            return usedOptimizationData;
        }

           
        public void setUsedOptimizationData(String value) {
            this.usedOptimizationData = value;
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

           
        public Byte getObjid() {
            return objid;
        }

           
        public void setObjid(Byte value) {
            this.objid = value;
        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class CampaignMessages
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
            "checkListItemDOs"
    })
    public static class CheckListItems
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "CheckListItemDO")
        protected List<CheckListItems.CheckListItemDO> checkListItemDOs;

           
        public List<CheckListItems.CheckListItemDO> getCheckListItemDOs() {
            if (checkListItemDOs == null) {
                checkListItemDOs = new ArrayList<CheckListItems.CheckListItemDO>();
            }
            return this.checkListItemDOs;
        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "archivedPublishState",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "firstOccurrenceToPublish",
                "followUpFlag",
                "itemType",
                "label",
                "loadedFromOldDavLocation",
                "loading",
                "needToPersist",
                "oldVersionNumber",
                "pageId",
                "persisting",
                "position",
                "publishState",
                "required",
                "status",
                "stepType",
                "versionNumber"
        })
        public static class CheckListItemDO
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
            @XmlElement(name = "FollowUpFlag", required = true)
            protected String followUpFlag;
            @XmlElement(name = "ItemType", required = true)
            protected String itemType;
            @XmlElement(name = "Label", required = true)
            protected String label;
            @XmlElement(name = "LoadedFromOldDavLocation", required = true)
            protected String loadedFromOldDavLocation;
            @XmlElement(name = "Loading", required = true)
            protected String loading;
            @XmlElement(name = "NeedToPersist", required = true)
            protected String needToPersist;
            @XmlElement(name = "OldVersionNumber", required = true)
            protected String oldVersionNumber;
            @XmlElement(name = "PageId", required = true)
            protected String pageId;
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "Position")
            protected float position;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "Required", required = true)
            protected String required;
            @XmlElement(name = "Status", required = true)
            protected String status;
            @XmlElement(name = "StepType", required = true)
            protected String stepType;
            @XmlElement(name = "VersionNumber")
            protected float versionNumber;
            @XmlAttribute(name = "ConcreteClass")
            protected String concreteClass;
            @XmlAttribute(name = "listpos")
            protected Byte listpos;
            @XmlAttribute(name = "objid")
            protected Byte objid;

               
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

               
            public String getFollowUpFlag() {
                return followUpFlag;
            }

               
            public void setFollowUpFlag(String value) {
                this.followUpFlag = value;
            }

               
            public String getItemType() {
                return itemType;
            }

               
            public void setItemType(String value) {
                this.itemType = value;
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

               
            public String getOldVersionNumber() {
                return oldVersionNumber;
            }

               
            public void setOldVersionNumber(String value) {
                this.oldVersionNumber = value;
            }

               
            public String getPageId() {
                return pageId;
            }

               
            public void setPageId(String value) {
                this.pageId = value;
            }

               
            public String getPersisting() {
                return persisting;
            }

               
            public void setPersisting(String value) {
                this.persisting = value;
            }

               
            public float getPosition() {
                return position;
            }

               
            public void setPosition(float value) {
                this.position = value;
            }

               
            public byte getPublishState() {
                return publishState;
            }

               
            public void setPublishState(byte value) {
                this.publishState = value;
            }

               
            public String getRequired() {
                return required;
            }

               
            public void setRequired(String value) {
                this.required = value;
            }

               
            public String getStatus() {
                return status;
            }

               
            public void setStatus(String value) {
                this.status = value;
            }

               
            public String getStepType() {
                return stepType;
            }

               
            public void setStepType(String value) {
                this.stepType = value;
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

               
            public Byte getObjid() {
                return objid;
            }

               
            public void setObjid(Byte value) {
                this.objid = value;
            }

        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class CustomPageDetails
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
            "accessControls",
            "allPages",
            "approvalCampaign",
            "approvalRequired",
            "approvers",
            "archivedPublishState",
            "attachments",
            "briefData",
            "businessContextId",
            "campaignType",
            "cellsDynamicWhenRefdByLink",
            "checkList",
            "checkListItems",
            "codeChangeUnpublished",
            "codeEverBeenPublished",
            "codeSettingFromBC",
            "codeType",
            "commDynamicForExecuteComm",
            "customDetailPages",
            "dateModified",
            "decisionCampaignType",
            "definitionType",
            "description",
            "firstOccurrenceToPublish",
            "flowType",
            "folder",
            "forceExecutionAllSQLNodes",
            "forced",
            "hidden",
            "id",
            "importDetails",
            "includeInitiatingStatus",
            "loadedFromOldDavLocation",
            "loading",
            "lockedBy",
            "metadataType",
            "name",
            "needToPersist",
            "noWritePermission",
            "oldVersionNumber",
            "optimizationGroup",
            "pageConfigurations",
            "persisting",
            "publishState",
            "readOnly",
            "restrictedCampaign",
            "schedule",
            "shared",
            "sharedId",
            "sharedIn",
            "statusList",
            "versionNumber",
            "whoModified"
    })
    public static class Definition
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "AccessControls", required = true)
        protected Definition.AccessControls accessControls;
        @XmlElement(name = "AllPages", required = true)
        protected Definition.AllPages allPages;
        @XmlElement(name = "ApprovalCampaign", required = true)
        protected String approvalCampaign;
        @XmlElement(name = "ApprovalRequired", required = true)
        protected String approvalRequired;
        @XmlElement(name = "Approvers", required = true)
        protected Definition.Approvers approvers;
        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;
        @XmlElement(name = "Attachments", required = true)
        protected String attachments;
        @XmlElement(name = "BriefData", required = true)
        protected Definition.BriefData briefData;
        @XmlElement(name = "BusinessContextId", required = true)
        protected String businessContextId;
        @XmlElement(name = "CampaignType", required = true)
        protected String campaignType;
        @XmlElement(name = "CellsDynamicWhenRefdByLink", required = true)
        protected String cellsDynamicWhenRefdByLink;
        @XmlElement(name = "CheckList", required = true)
        protected Definition.CheckList checkList;
        @XmlElement(name = "CheckListItems", required = true)
        protected Definition.CheckListItems checkListItems;
        @XmlElement(name = "CodeChangeUnpublished", required = true)
        protected String codeChangeUnpublished;
        @XmlElement(name = "CodeEverBeenPublished", required = true)
        protected String codeEverBeenPublished;
        @XmlElement(name = "CodeSettingFromBC", required = true)
        protected String codeSettingFromBC;
        @XmlElement(name = "CodeType", required = true)
        protected String codeType;
        @XmlElement(name = "CommDynamicForExecuteComm", required = true)
        protected String commDynamicForExecuteComm;
        @XmlElement(name = "CustomDetailPages", required = true)
        protected Definition.CustomDetailPages customDetailPages;
        @XmlElement(name = "DateModified", required = true)
        protected String dateModified;
        @XmlElement(name = "DecisionCampaignType", required = true)
        protected String decisionCampaignType;
        @XmlElement(name = "DefinitionType", required = true)
        protected String definitionType;
        @XmlElement(name = "Description", required = true)
        protected String description;
        @XmlElement(name = "FirstOccurrenceToPublish")
        protected byte firstOccurrenceToPublish;
        @XmlElement(name = "FlowType", required = true)
        protected String flowType;
        @XmlElement(name = "Folder", required = true)
        protected Definition.Folder folder;
        @XmlElement(name = "ForceExecutionAllSQLNodes", required = true)
        protected String forceExecutionAllSQLNodes;
        @XmlElement(name = "Forced", required = true)
        protected String forced;
        @XmlElement(name = "Hidden", required = true)
        protected String hidden;
        @XmlElement(name = "Id", required = true)
        protected String id;
        @XmlElement(name = "ImportDetails", required = true)
        protected Definition.ImportDetails importDetails;
        @XmlElement(name = "IncludeInitiatingStatus", required = true)
        protected String includeInitiatingStatus;
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
        @XmlElement(name = "OptimizationGroup", required = true)
        protected String optimizationGroup;
        @XmlElement(name = "PageConfigurations", required = true)
        protected Definition.PageConfigurations pageConfigurations;
        @XmlElement(name = "Persisting", required = true)
        protected String persisting;
        @XmlElement(name = "PublishState")
        protected byte publishState;
        @XmlElement(name = "ReadOnly", required = true)
        protected String readOnly;
        @XmlElement(name = "RestrictedCampaign", required = true)
        protected String restrictedCampaign;
        @XmlElement(name = "Schedule", required = true)
        protected Definition.Schedule schedule;
        @XmlElement(name = "Shared", required = true)
        protected String shared;
        @XmlElement(name = "SharedId", required = true)
        protected String sharedId;
        @XmlElement(name = "SharedIn", required = true)
        protected Definition.SharedIn sharedIn;
        @XmlElement(name = "StatusList", required = true)
        protected Definition.StatusList statusList;
        @XmlElement(name = "VersionNumber")
        protected float versionNumber;
        @XmlElement(name = "WhoModified", required = true)
        protected String whoModified;
        @XmlAttribute(name = "ConcreteClass")
        protected String concreteClass;
        @XmlAttribute(name = "objid")
        protected Byte objid;

           
        public Definition.AccessControls getAccessControls() {
            return accessControls;
        }

           
        public void setAccessControls(Definition.AccessControls value) {
            this.accessControls = value;
        }

           
        public Definition.AllPages getAllPages() {
            return allPages;
        }

           
        public void setAllPages(Definition.AllPages value) {
            this.allPages = value;
        }

           
        public String getApprovalCampaign() {
            return approvalCampaign;
        }

           
        public void setApprovalCampaign(String value) {
            this.approvalCampaign = value;
        }

           
        public String getApprovalRequired() {
            return approvalRequired;
        }

           
        public void setApprovalRequired(String value) {
            this.approvalRequired = value;
        }

           
        public Definition.Approvers getApprovers() {
            return approvers;
        }

           
        public void setApprovers(Definition.Approvers value) {
            this.approvers = value;
        }

           
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

           
        public Definition.BriefData getBriefData() {
            return briefData;
        }

           
        public void setBriefData(Definition.BriefData value) {
            this.briefData = value;
        }

           
        public String getBusinessContextId() {
            return businessContextId;
        }

           
        public void setBusinessContextId(String value) {
            this.businessContextId = value;
        }

           
        public String getCampaignType() {
            return campaignType;
        }

           
        public void setCampaignType(String value) {
            this.campaignType = value;
        }

           
        public String getCellsDynamicWhenRefdByLink() {
            return cellsDynamicWhenRefdByLink;
        }

           
        public void setCellsDynamicWhenRefdByLink(String value) {
            this.cellsDynamicWhenRefdByLink = value;
        }

           
        public Definition.CheckList getCheckList() {
            return checkList;
        }

           
        public void setCheckList(Definition.CheckList value) {
            this.checkList = value;
        }

           
        public Definition.CheckListItems getCheckListItems() {
            return checkListItems;
        }

           
        public void setCheckListItems(Definition.CheckListItems value) {
            this.checkListItems = value;
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

           
        public String getCodeSettingFromBC() {
            return codeSettingFromBC;
        }

           
        public void setCodeSettingFromBC(String value) {
            this.codeSettingFromBC = value;
        }

           
        public String getCodeType() {
            return codeType;
        }

           
        public void setCodeType(String value) {
            this.codeType = value;
        }

           
        public String getCommDynamicForExecuteComm() {
            return commDynamicForExecuteComm;
        }

           
        public void setCommDynamicForExecuteComm(String value) {
            this.commDynamicForExecuteComm = value;
        }

           
        public Definition.CustomDetailPages getCustomDetailPages() {
            return customDetailPages;
        }

           
        public void setCustomDetailPages(Definition.CustomDetailPages value) {
            this.customDetailPages = value;
        }

           
        public String getDateModified() {
            return dateModified;
        }

           
        public void setDateModified(String value) {
            this.dateModified = value;
        }

           
        public String getDecisionCampaignType() {
            return decisionCampaignType;
        }

           
        public void setDecisionCampaignType(String value) {
            this.decisionCampaignType = value;
        }

           
        public String getDefinitionType() {
            return definitionType;
        }

           
        public void setDefinitionType(String value) {
            this.definitionType = value;
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

           
        public Definition.Folder getFolder() {
            return folder;
        }

           
        public void setFolder(Definition.Folder value) {
            this.folder = value;
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

           
        public String getIncludeInitiatingStatus() {
            return includeInitiatingStatus;
        }

           
        public void setIncludeInitiatingStatus(String value) {
            this.includeInitiatingStatus = value;
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

           
        public String getOptimizationGroup() {
            return optimizationGroup;
        }

           
        public void setOptimizationGroup(String value) {
            this.optimizationGroup = value;
        }

           
        public Definition.PageConfigurations getPageConfigurations() {
            return pageConfigurations;
        }

           
        public void setPageConfigurations(Definition.PageConfigurations value) {
            this.pageConfigurations = value;
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

           
        public String getRestrictedCampaign() {
            return restrictedCampaign;
        }

           
        public void setRestrictedCampaign(String value) {
            this.restrictedCampaign = value;
        }

           
        public Definition.Schedule getSchedule() {
            return schedule;
        }

           
        public void setSchedule(Definition.Schedule value) {
            this.schedule = value;
        }

           
        public String getShared() {
            return shared;
        }

           
        public void setShared(String value) {
            this.shared = value;
        }

           
        public String getSharedId() {
            return sharedId;
        }

           
        public void setSharedId(String value) {
            this.sharedId = value;
        }

           
        public Definition.SharedIn getSharedIn() {
            return sharedIn;
        }

           
        public void setSharedIn(Definition.SharedIn value) {
            this.sharedIn = value;
        }

           
        public Definition.StatusList getStatusList() {
            return statusList;
        }

           
        public void setStatusList(Definition.StatusList value) {
            this.statusList = value;
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

           
        public Byte getObjid() {
            return objid;
        }

           
        public void setObjid(Byte value) {
            this.objid = value;
        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "userAndGroupDOs"
        })
        public static class AccessControls
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "UserAndGroupDO")
            protected List<AccessControls.UserAndGroupDO> userAndGroupDOs;

               
            public List<AccessControls.UserAndGroupDO> getUserAndGroupDOs() {
                if (userAndGroupDOs == null) {
                    userAndGroupDOs = new ArrayList<AccessControls.UserAndGroupDO>();
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
                protected Byte objid;

                   
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

                   
                public Byte getObjid() {
                    return objid;
                }

                   
                public void setObjid(Byte value) {
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
                "value"
        })
        public static class AllPages
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
                "userAndGroupDOs"
        })
        public static class Approvers
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "UserAndGroupDO")
            protected List<Approvers.UserAndGroupDO> userAndGroupDOs;

               
            public List<Approvers.UserAndGroupDO> getUserAndGroupDOs() {
                if (userAndGroupDOs == null) {
                    userAndGroupDOs = new ArrayList<Approvers.UserAndGroupDO>();
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
                protected com.sas.rtdm2id.model.rtdm.CampaignDO.Definition.Approvers.UserAndGroupDO.Members members;
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
                protected Byte objid;

                   
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

                   
                public com.sas.rtdm2id.model.rtdm.CampaignDO.Definition.Approvers.UserAndGroupDO.Members getMembers() {
                    return members;
                }

                   
                public void setMembers(com.sas.rtdm2id.model.rtdm.CampaignDO.Definition.Approvers.UserAndGroupDO.Members value) {
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

                   
                public Byte getObjid() {
                    return objid;
                }

                   
                public void setObjid(Byte value) {
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
                "archivedPublishState",
                "challengerTooSmallBehavior",
                "championChallengerConfigDO",
                "codeAutoGenerated",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "controlGroupConfigDO",
                "controlGroupTooSmallBehavior",
                "editable",
                "firstOccurrenceToPublish",
                "loadedFromOldDavLocation",
                "loading",
                "needToPersist",
                "oldVersionNumber",
                "persisting",
                "publishState",
                "required",
                "userDefinedFields",
                "versionNumber"
        })
        public static class BriefData
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "ChallengerTooSmallBehavior")
            protected byte challengerTooSmallBehavior;
            @XmlElement(name = "ChampionChallengerConfigDO", required = true)
            protected BriefData.ChampionChallengerConfigDO championChallengerConfigDO;
            @XmlElement(name = "CodeAutoGenerated", required = true)
            protected String codeAutoGenerated;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
            @XmlElement(name = "ControlGroupConfigDO", required = true)
            protected BriefData.ControlGroupConfigDO controlGroupConfigDO;
            @XmlElement(name = "ControlGroupTooSmallBehavior")
            protected byte controlGroupTooSmallBehavior;
            @XmlElement(name = "Editable", required = true)
            protected String editable;
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
            @XmlElement(name = "Required", required = true)
            protected String required;
            @XmlElement(name = "UserDefinedFields", required = true)
            protected BriefData.UserDefinedFields userDefinedFields;
            @XmlElement(name = "VersionNumber")
            protected float versionNumber;
            @XmlAttribute(name = "ConcreteClass")
            protected String concreteClass;
            @XmlAttribute(name = "objid")
            protected Byte objid;

               
            public byte getArchivedPublishState() {
                return archivedPublishState;
            }

               
            public void setArchivedPublishState(byte value) {
                this.archivedPublishState = value;
            }

               
            public byte getChallengerTooSmallBehavior() {
                return challengerTooSmallBehavior;
            }

               
            public void setChallengerTooSmallBehavior(byte value) {
                this.challengerTooSmallBehavior = value;
            }

               
            public BriefData.ChampionChallengerConfigDO getChampionChallengerConfigDO() {
                return championChallengerConfigDO;
            }

               
            public void setChampionChallengerConfigDO(BriefData.ChampionChallengerConfigDO value) {
                this.championChallengerConfigDO = value;
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

               
            public String getCodeEverBeenPublished() {
                return codeEverBeenPublished;
            }

               
            public void setCodeEverBeenPublished(String value) {
                this.codeEverBeenPublished = value;
            }

               
            public BriefData.ControlGroupConfigDO getControlGroupConfigDO() {
                return controlGroupConfigDO;
            }

               
            public void setControlGroupConfigDO(BriefData.ControlGroupConfigDO value) {
                this.controlGroupConfigDO = value;
            }

               
            public byte getControlGroupTooSmallBehavior() {
                return controlGroupTooSmallBehavior;
            }

               
            public void setControlGroupTooSmallBehavior(byte value) {
                this.controlGroupTooSmallBehavior = value;
            }

               
            public String getEditable() {
                return editable;
            }

               
            public void setEditable(String value) {
                this.editable = value;
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

               
            public String getRequired() {
                return required;
            }

               
            public void setRequired(String value) {
                this.required = value;
            }

               
            public BriefData.UserDefinedFields getUserDefinedFields() {
                return userDefinedFields;
            }

               
            public void setUserDefinedFields(BriefData.UserDefinedFields value) {
                this.userDefinedFields = value;
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

               
            public Byte getObjid() {
                return objid;
            }

               
            public void setObjid(Byte value) {
                this.objid = value;
            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "archivedPublishState",
                    "challengerCount",
                    "challengerPercentage",
                    "challengerSizingType",
                    "codeChangeUnpublished",
                    "codeEverBeenPublished",
                    "firstOccurrenceToPublish",
                    "loadedFromOldDavLocation",
                    "loading",
                    "needToPersist",
                    "oldVersionNumber",
                    "persisting",
                    "publishState",
                    "versionNumber"
            })
            public static class ChampionChallengerConfigDO
                    implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "ArchivedPublishState")
                protected byte archivedPublishState;
                @XmlElement(name = "ChallengerCount")
                protected byte challengerCount;
                @XmlElement(name = "ChallengerPercentage")
                protected float challengerPercentage;
                @XmlElement(name = "ChallengerSizingType")
                protected byte challengerSizingType;
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
                @XmlElement(name = "VersionNumber")
                protected float versionNumber;
                @XmlAttribute(name = "ConcreteClass")
                protected String concreteClass;
                @XmlAttribute(name = "objid")
                protected Byte objid;

                   
                public byte getArchivedPublishState() {
                    return archivedPublishState;
                }

                   
                public void setArchivedPublishState(byte value) {
                    this.archivedPublishState = value;
                }

                   
                public byte getChallengerCount() {
                    return challengerCount;
                }

                   
                public void setChallengerCount(byte value) {
                    this.challengerCount = value;
                }

                   
                public float getChallengerPercentage() {
                    return challengerPercentage;
                }

                   
                public void setChallengerPercentage(float value) {
                    this.challengerPercentage = value;
                }

                   
                public byte getChallengerSizingType() {
                    return challengerSizingType;
                }

                   
                public void setChallengerSizingType(byte value) {
                    this.challengerSizingType = value;
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

                   
                public Byte getObjid() {
                    return objid;
                }

                   
                public void setObjid(Byte value) {
                    this.objid = value;
                }

            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "archivedPublishState",
                    "codeChangeUnpublished",
                    "codeEverBeenPublished",
                    "count",
                    "enabled",
                    "firstOccurrenceToPublish",
                    "loadedFromOldDavLocation",
                    "loading",
                    "needToPersist",
                    "oldVersionNumber",
                    "percentage",
                    "persisting",
                    "publishState",
                    "sizingType",
                    "versionNumber"
            })
            public static class ControlGroupConfigDO
                    implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "ArchivedPublishState")
                protected byte archivedPublishState;
                @XmlElement(name = "CodeChangeUnpublished", required = true)
                protected String codeChangeUnpublished;
                @XmlElement(name = "CodeEverBeenPublished", required = true)
                protected String codeEverBeenPublished;
                @XmlElement(name = "Count")
                protected byte count;
                @XmlElement(name = "Enabled", required = true)
                protected String enabled;
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
                @XmlElement(name = "Percentage")
                protected float percentage;
                @XmlElement(name = "Persisting", required = true)
                protected String persisting;
                @XmlElement(name = "PublishState")
                protected byte publishState;
                @XmlElement(name = "SizingType")
                protected byte sizingType;
                @XmlElement(name = "VersionNumber")
                protected float versionNumber;
                @XmlAttribute(name = "ConcreteClass")
                protected String concreteClass;
                @XmlAttribute(name = "objid")
                protected Byte objid;

                   
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

                   
                public byte getCount() {
                    return count;
                }

                   
                public void setCount(byte value) {
                    this.count = value;
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

                   
                public float getPercentage() {
                    return percentage;
                }

                   
                public void setPercentage(float value) {
                    this.percentage = value;
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

                   
                public byte getSizingType() {
                    return sizingType;
                }

                   
                public void setSizingType(byte value) {
                    this.sizingType = value;
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

                   
                public Byte getObjid() {
                    return objid;
                }

                   
                public void setObjid(Byte value) {
                    this.objid = value;
                }

            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "value"
            })
            public static class UserDefinedFields
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
                "firstOccurrenceToPublish",
                "loadedFromOldDavLocation",
                "loading",
                "needToPersist",
                "oldVersionNumber",
                "persisting",
                "publishState",
                "steps",
                "versionNumber"
        })
        public static class CheckList
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
            @XmlElement(name = "Steps", required = true)
            protected CheckList.Steps steps;
            @XmlElement(name = "VersionNumber")
            protected float versionNumber;
            @XmlAttribute(name = "ConcreteClass")
            protected String concreteClass;
            @XmlAttribute(name = "objid")
            protected Byte objid;

               
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

               
            public CheckList.Steps getSteps() {
                return steps;
            }

               
            public void setSteps(CheckList.Steps value) {
                this.steps = value;
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

               
            public Byte getObjid() {
                return objid;
            }

               
            public void setObjid(Byte value) {
                this.objid = value;
            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "checkListStepDOs"
            })
            public static class Steps
                    implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "CheckListStepDO")
                protected List<Steps.CheckListStepDO> checkListStepDOs;

                   
                public List<Steps.CheckListStepDO> getCheckListStepDOs() {
                    if (checkListStepDOs == null) {
                        checkListStepDOs = new ArrayList<Steps.CheckListStepDO>();
                    }
                    return this.checkListStepDOs;
                }


                   
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "archivedPublishState",
                        "codeChangeUnpublished",
                        "codeEverBeenPublished",
                        "firstOccurrenceToPublish",
                        "followupFlag",
                        "id",
                        "loadedFromOldDavLocation",
                        "loading",
                        "name",
                        "needToPersist",
                        "oldVersionNumber",
                        "persisting",
                        "publishState",
                        "showAsLink",
                        "status",
                        "type",
                        "versionNumber"
                })
                public static class CheckListStepDO
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
                    @XmlElement(name = "FollowupFlag", required = true)
                    protected String followupFlag;
                    @XmlElement(name = "Id", required = true)
                    protected String id;
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
                    @XmlElement(name = "ShowAsLink", required = true)
                    protected String showAsLink;
                    @XmlElement(name = "Status", required = true)
                    protected String status;
                    @XmlElement(name = "Type", required = true)
                    protected String type;
                    @XmlElement(name = "VersionNumber")
                    protected float versionNumber;
                    @XmlAttribute(name = "ConcreteClass")
                    protected String concreteClass;
                    @XmlAttribute(name = "listpos")
                    protected Byte listpos;
                    @XmlAttribute(name = "objid")
                    protected Byte objid;

                       
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

                       
                    public String getFollowupFlag() {
                        return followupFlag;
                    }

                       
                    public void setFollowupFlag(String value) {
                        this.followupFlag = value;
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

                       
                    public String getShowAsLink() {
                        return showAsLink;
                    }

                       
                    public void setShowAsLink(String value) {
                        this.showAsLink = value;
                    }

                       
                    public String getStatus() {
                        return status;
                    }

                       
                    public void setStatus(String value) {
                        this.status = value;
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

                       
                    public Byte getObjid() {
                        return objid;
                    }

                       
                    public void setObjid(Byte value) {
                        this.objid = value;
                    }

                }

            }

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "checkListItemDOs"
        })
        public static class CheckListItems
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "CheckListItemDO")
            protected List<com.sas.rtdm2id.model.rtdm.CampaignDO.Definition.CheckListItems.CheckListItemDO> checkListItemDOs;

               
            public List<com.sas.rtdm2id.model.rtdm.CampaignDO.Definition.CheckListItems.CheckListItemDO> getCheckListItemDOs() {
                if (checkListItemDOs == null) {
                    checkListItemDOs = new ArrayList<com.sas.rtdm2id.model.rtdm.CampaignDO.Definition.CheckListItems.CheckListItemDO>();
                }
                return this.checkListItemDOs;
            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "archivedPublishState",
                    "codeChangeUnpublished",
                    "codeEverBeenPublished",
                    "firstOccurrenceToPublish",
                    "followUpFlag",
                    "itemType",
                    "label",
                    "loadedFromOldDavLocation",
                    "loading",
                    "needToPersist",
                    "oldVersionNumber",
                    "pageId",
                    "persisting",
                    "position",
                    "publishState",
                    "required",
                    "status",
                    "stepType",
                    "versionNumber"
            })
            public static class CheckListItemDO
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
                @XmlElement(name = "FollowUpFlag", required = true)
                protected String followUpFlag;
                @XmlElement(name = "ItemType", required = true)
                protected String itemType;
                @XmlElement(name = "Label", required = true)
                protected String label;
                @XmlElement(name = "LoadedFromOldDavLocation", required = true)
                protected String loadedFromOldDavLocation;
                @XmlElement(name = "Loading", required = true)
                protected String loading;
                @XmlElement(name = "NeedToPersist", required = true)
                protected String needToPersist;
                @XmlElement(name = "OldVersionNumber", required = true)
                protected String oldVersionNumber;
                @XmlElement(name = "PageId", required = true)
                protected String pageId;
                @XmlElement(name = "Persisting", required = true)
                protected String persisting;
                @XmlElement(name = "Position")
                protected float position;
                @XmlElement(name = "PublishState")
                protected byte publishState;
                @XmlElement(name = "Required", required = true)
                protected String required;
                @XmlElement(name = "Status", required = true)
                protected String status;
                @XmlElement(name = "StepType", required = true)
                protected String stepType;
                @XmlElement(name = "VersionNumber")
                protected float versionNumber;
                @XmlAttribute(name = "ConcreteClass")
                protected String concreteClass;
                @XmlAttribute(name = "listpos")
                protected Byte listpos;
                @XmlAttribute(name = "objid")
                protected Byte objid;

                   
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

                   
                public String getFollowUpFlag() {
                    return followUpFlag;
                }

                   
                public void setFollowUpFlag(String value) {
                    this.followUpFlag = value;
                }

                   
                public String getItemType() {
                    return itemType;
                }

                   
                public void setItemType(String value) {
                    this.itemType = value;
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

                   
                public String getOldVersionNumber() {
                    return oldVersionNumber;
                }

                   
                public void setOldVersionNumber(String value) {
                    this.oldVersionNumber = value;
                }

                   
                public String getPageId() {
                    return pageId;
                }

                   
                public void setPageId(String value) {
                    this.pageId = value;
                }

                   
                public String getPersisting() {
                    return persisting;
                }

                   
                public void setPersisting(String value) {
                    this.persisting = value;
                }

                   
                public float getPosition() {
                    return position;
                }

                   
                public void setPosition(float value) {
                    this.position = value;
                }

                   
                public byte getPublishState() {
                    return publishState;
                }

                   
                public void setPublishState(byte value) {
                    this.publishState = value;
                }

                   
                public String getRequired() {
                    return required;
                }

                   
                public void setRequired(String value) {
                    this.required = value;
                }

                   
                public String getStatus() {
                    return status;
                }

                   
                public void setStatus(String value) {
                    this.status = value;
                }

                   
                public String getStepType() {
                    return stepType;
                }

                   
                public void setStepType(String value) {
                    this.stepType = value;
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

                   
                public Byte getObjid() {
                    return objid;
                }

                   
                public void setObjid(Byte value) {
                    this.objid = value;
                }

            }

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        public static class CustomDetailPages
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
                "dateModified",
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
            @XmlElement(name = "DateModified", required = true)
            protected String dateModified;
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
            protected Byte objid;

               
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

               
            public String getDateModified() {
                return dateModified;
            }

               
            public void setDateModified(String value) {
                this.dateModified = value;
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

               
            public Byte getObjid() {
                return objid;
            }

               
            public void setObjid(Byte value) {
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
            protected Byte objid;

               
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

               
            public Byte getObjid() {
                return objid;
            }

               
            public void setObjid(Byte value) {
                this.objid = value;
            }

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "pageConfigDOs"
        })
        public static class PageConfigurations
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "PageConfigDO")
            protected List<PageConfigurations.PageConfigDO> pageConfigDOs;

               
            public List<PageConfigurations.PageConfigDO> getPageConfigDOs() {
                if (pageConfigDOs == null) {
                    pageConfigDOs = new ArrayList<PageConfigurations.PageConfigDO>();
                }
                return this.pageConfigDOs;
            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "archivedPublishState",
                    "codeChangeUnpublished",
                    "codeEverBeenPublished",
                    "designPages",
                    "firstOccurrenceToPublish",
                    "loadedFromOldDavLocation",
                    "loading",
                    "needToPersist",
                    "oldVersionNumber",
                    "persisting",
                    "processPages",
                    "publishState",
                    "roleId",
                    "sameAsEditor",
                    "versionNumber"
            })
            public static class PageConfigDO
                    implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "ArchivedPublishState")
                protected byte archivedPublishState;
                @XmlElement(name = "CodeChangeUnpublished", required = true)
                protected String codeChangeUnpublished;
                @XmlElement(name = "CodeEverBeenPublished", required = true)
                protected String codeEverBeenPublished;
                @XmlElement(name = "DesignPages", required = true)
                protected PageConfigDO.DesignPages designPages;
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
                @XmlElement(name = "ProcessPages", required = true)
                protected PageConfigDO.ProcessPages processPages;
                @XmlElement(name = "PublishState")
                protected byte publishState;
                @XmlElement(name = "RoleId", required = true)
                protected String roleId;
                @XmlElement(name = "SameAsEditor", required = true)
                protected String sameAsEditor;
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

                   
                public PageConfigDO.DesignPages getDesignPages() {
                    return designPages;
                }

                   
                public void setDesignPages(PageConfigDO.DesignPages value) {
                    this.designPages = value;
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

                   
                public PageConfigDO.ProcessPages getProcessPages() {
                    return processPages;
                }

                   
                public void setProcessPages(PageConfigDO.ProcessPages value) {
                    this.processPages = value;
                }

                   
                public byte getPublishState() {
                    return publishState;
                }

                   
                public void setPublishState(byte value) {
                    this.publishState = value;
                }

                   
                public String getRoleId() {
                    return roleId;
                }

                   
                public void setRoleId(String value) {
                    this.roleId = value;
                }

                   
                public String getSameAsEditor() {
                    return sameAsEditor;
                }

                   
                public void setSameAsEditor(String value) {
                    this.sameAsEditor = value;
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
                        "pageDOs"
                })
                public static class DesignPages
                        implements Serializable {

                    private final static long serialVersionUID = -1L;
                    @XmlElement(name = "PageDO")
                    protected List<DesignPages.PageDO> pageDOs;

                       
                    public List<DesignPages.PageDO> getPageDOs() {
                        if (pageDOs == null) {
                            pageDOs = new ArrayList<DesignPages.PageDO>();
                        }
                        return this.pageDOs;
                    }


                       
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "archivedPublishState",
                            "codeChangeUnpublished",
                            "codeEverBeenPublished",
                            "firstOccurrenceToPublish",
                            "followupFlag",
                            "hiddenDefault",
                            "loadedFromOldDavLocation",
                            "loading",
                            "needToPersist",
                            "oldVersionNumber",
                            "pageId",
                            "pageLabel",
                            "pageStatus",
                            "persisting",
                            "publishState",
                            "versionNumber"
                    })
                    public static class PageDO
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
                        @XmlElement(name = "FollowupFlag", required = true)
                        protected String followupFlag;
                        @XmlElement(name = "HiddenDefault", required = true)
                        protected String hiddenDefault;
                        @XmlElement(name = "LoadedFromOldDavLocation", required = true)
                        protected String loadedFromOldDavLocation;
                        @XmlElement(name = "Loading", required = true)
                        protected String loading;
                        @XmlElement(name = "NeedToPersist", required = true)
                        protected String needToPersist;
                        @XmlElement(name = "OldVersionNumber", required = true)
                        protected String oldVersionNumber;
                        @XmlElement(name = "PageId", required = true)
                        protected String pageId;
                        @XmlElement(name = "PageLabel", required = true)
                        protected String pageLabel;
                        @XmlElement(name = "PageStatus", required = true)
                        protected String pageStatus;
                        @XmlElement(name = "Persisting", required = true)
                        protected String persisting;
                        @XmlElement(name = "PublishState")
                        protected byte publishState;
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

                           
                        public byte getFirstOccurrenceToPublish() {
                            return firstOccurrenceToPublish;
                        }

                           
                        public void setFirstOccurrenceToPublish(byte value) {
                            this.firstOccurrenceToPublish = value;
                        }

                           
                        public String getFollowupFlag() {
                            return followupFlag;
                        }

                           
                        public void setFollowupFlag(String value) {
                            this.followupFlag = value;
                        }

                           
                        public String getHiddenDefault() {
                            return hiddenDefault;
                        }

                           
                        public void setHiddenDefault(String value) {
                            this.hiddenDefault = value;
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

                           
                        public String getPageId() {
                            return pageId;
                        }

                           
                        public void setPageId(String value) {
                            this.pageId = value;
                        }

                           
                        public String getPageLabel() {
                            return pageLabel;
                        }

                           
                        public void setPageLabel(String value) {
                            this.pageLabel = value;
                        }

                           
                        public String getPageStatus() {
                            return pageStatus;
                        }

                           
                        public void setPageStatus(String value) {
                            this.pageStatus = value;
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

                    }

                }


                   
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "pageDOs"
                })
                public static class ProcessPages
                        implements Serializable {

                    private final static long serialVersionUID = -1L;
                    @XmlElement(name = "PageDO")
                    protected List<ProcessPages.PageDO> pageDOs;

                       
                    public List<ProcessPages.PageDO> getPageDOs() {
                        if (pageDOs == null) {
                            pageDOs = new ArrayList<ProcessPages.PageDO>();
                        }
                        return this.pageDOs;
                    }


                       
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "archivedPublishState",
                            "codeChangeUnpublished",
                            "codeEverBeenPublished",
                            "firstOccurrenceToPublish",
                            "followupFlag",
                            "hiddenDefault",
                            "loadedFromOldDavLocation",
                            "loading",
                            "needToPersist",
                            "oldVersionNumber",
                            "pageId",
                            "pageLabel",
                            "pageStatus",
                            "persisting",
                            "publishState",
                            "versionNumber"
                    })
                    public static class PageDO
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
                        @XmlElement(name = "FollowupFlag", required = true)
                        protected String followupFlag;
                        @XmlElement(name = "HiddenDefault", required = true)
                        protected String hiddenDefault;
                        @XmlElement(name = "LoadedFromOldDavLocation", required = true)
                        protected String loadedFromOldDavLocation;
                        @XmlElement(name = "Loading", required = true)
                        protected String loading;
                        @XmlElement(name = "NeedToPersist", required = true)
                        protected String needToPersist;
                        @XmlElement(name = "OldVersionNumber", required = true)
                        protected String oldVersionNumber;
                        @XmlElement(name = "PageId", required = true)
                        protected String pageId;
                        @XmlElement(name = "PageLabel", required = true)
                        protected String pageLabel;
                        @XmlElement(name = "PageStatus", required = true)
                        protected String pageStatus;
                        @XmlElement(name = "Persisting", required = true)
                        protected String persisting;
                        @XmlElement(name = "PublishState")
                        protected byte publishState;
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

                           
                        public byte getFirstOccurrenceToPublish() {
                            return firstOccurrenceToPublish;
                        }

                           
                        public void setFirstOccurrenceToPublish(byte value) {
                            this.firstOccurrenceToPublish = value;
                        }

                           
                        public String getFollowupFlag() {
                            return followupFlag;
                        }

                           
                        public void setFollowupFlag(String value) {
                            this.followupFlag = value;
                        }

                           
                        public String getHiddenDefault() {
                            return hiddenDefault;
                        }

                           
                        public void setHiddenDefault(String value) {
                            this.hiddenDefault = value;
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

                           
                        public String getPageId() {
                            return pageId;
                        }

                           
                        public void setPageId(String value) {
                            this.pageId = value;
                        }

                           
                        public String getPageLabel() {
                            return pageLabel;
                        }

                           
                        public void setPageLabel(String value) {
                            this.pageLabel = value;
                        }

                           
                        public String getPageStatus() {
                            return pageStatus;
                        }

                           
                        public void setPageStatus(String value) {
                            this.pageStatus = value;
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

                    }

                }

            }

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "archivedPublishState",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "dateScheduleLastModifiedInCampaign",
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
                "sharedDefinitionUseDO"
        })
        public static class SharedIn
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "SharedDefinitionUseDO", required = true)
            protected SharedIn.SharedDefinitionUseDO sharedDefinitionUseDO;

               
            public SharedIn.SharedDefinitionUseDO getSharedDefinitionUseDO() {
                return sharedDefinitionUseDO;
            }

               
            public void setSharedDefinitionUseDO(SharedIn.SharedDefinitionUseDO value) {
                this.sharedDefinitionUseDO = value;
            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "archivedPublishState",
                    "businessContextId",
                    "codeChangeUnpublished",
                    "codeEverBeenPublished",
                    "dateShared",
                    "firstOccurrenceToPublish",
                    "loadedFromOldDavLocation",
                    "loading",
                    "needToPersist",
                    "oldVersionNumber",
                    "persisting",
                    "publishState",
                    "sharedBy",
                    "versionNumber"
            })
            public static class SharedDefinitionUseDO
                    implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "ArchivedPublishState")
                protected byte archivedPublishState;
                @XmlElement(name = "BusinessContextId", required = true)
                protected String businessContextId;
                @XmlElement(name = "CodeChangeUnpublished", required = true)
                protected String codeChangeUnpublished;
                @XmlElement(name = "CodeEverBeenPublished", required = true)
                protected String codeEverBeenPublished;
                @XmlElement(name = "DateShared", required = true)
                protected String dateShared;
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
                @XmlElement(name = "SharedBy", required = true)
                protected String sharedBy;
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

                   
                public String getBusinessContextId() {
                    return businessContextId;
                }

                   
                public void setBusinessContextId(String value) {
                    this.businessContextId = value;
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

                   
                public String getDateShared() {
                    return dateShared;
                }

                   
                public void setDateShared(String value) {
                    this.dateShared = value;
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

                   
                public String getSharedBy() {
                    return sharedBy;
                }

                   
                public void setSharedBy(String value) {
                    this.sharedBy = value;
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

            }

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "statusDOs"
        })
        public static class StatusList
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "StatusDO")
            protected List<StatusList.StatusDO> statusDOs;

               
            public List<StatusList.StatusDO> getStatusDOs() {
                if (statusDOs == null) {
                    statusDOs = new ArrayList<StatusList.StatusDO>();
                }
                return this.statusDOs;
            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "archivedPublishState",
                    "codeChangeUnpublished",
                    "codeEverBeenPublished",
                    "description",
                    "firstOccurrenceToPublish",
                    "forced",
                    "hidden",
                    "id",
                    "loadedFromOldDavLocation",
                    "loading",
                    "lockedBy",
                    "metadataType",
                    "method",
                    "name",
                    "needToPersist",
                    "noWritePermission",
                    "oldVersionNumber",
                    "persisting",
                    "publishState",
                    "readOnly",
                    "type",
                    "userDefinedStatus44",
                    "valid",
                    "versionNumber"
            })
            public static class StatusDO
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
                @XmlElement(name = "Method", required = true)
                protected String method;
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
                @XmlElement(name = "Type")
                protected byte type;
                @XmlElement(name = "UserDefined_status_44", required = true)
                protected String userDefinedStatus44;
                @XmlElement(name = "Valid", required = true)
                protected String valid;
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

                   
                public String getMethod() {
                    return method;
                }

                   
                public void setMethod(String value) {
                    this.method = value;
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

                   
                public byte getType() {
                    return type;
                }

                   
                public void setType(byte value) {
                    this.type = value;
                }

                   
                public String getUserDefinedStatus44() {
                    return userDefinedStatus44;
                }

                   
                public void setUserDefinedStatus44(String value) {
                    this.userDefinedStatus44 = value;
                }

                   
                public String getValid() {
                    return valid;
                }

                   
                public void setValid(String value) {
                    this.valid = value;
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

            }

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
            "value"
    })
    public static class ProcessingEndDate
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "ConcreteClass")
        protected String concreteClass;

           
        public String getValue() {
            return value;
        }

           
        public void setValue(String value) {
            this.value = value;
        }

           
        public String getConcreteClass() {
            return concreteClass;
        }

           
        public void setConcreteClass(String value) {
            this.concreteClass = value;
        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class ProcessingStartDate
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "ConcreteClass")
        protected String concreteClass;

           
        public String getValue() {
            return value;
        }

           
        public void setValue(String value) {
            this.value = value;
        }

           
        public String getConcreteClass() {
            return concreteClass;
        }

           
        public void setConcreteClass(String value) {
            this.concreteClass = value;
        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "archivedPublishState",
            "campaignId",
            "codeChangeUnpublished",
            "codeEverBeenPublished",
            "firstOccurrenceToPublish",
            "loadedFromOldDavLocation",
            "loading",
            "needToPersist",
            "newCampaign",
            "oldVersionNumber",
            "persisting",
            "publishState",
            "publishedOnSave",
            "versionNumber"
    })
    public static class PublishedObjects
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;
        @XmlElement(name = "CampaignId", required = true)
        protected String campaignId;
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
        @XmlElement(name = "NewCampaign", required = true)
        protected String newCampaign;
        @XmlElement(name = "OldVersionNumber", required = true)
        protected String oldVersionNumber;
        @XmlElement(name = "Persisting", required = true)
        protected String persisting;
        @XmlElement(name = "PublishState")
        protected byte publishState;
        @XmlElement(name = "PublishedOnSave", required = true)
        protected String publishedOnSave;
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

           
        public String getCampaignId() {
            return campaignId;
        }

           
        public void setCampaignId(String value) {
            this.campaignId = value;
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

           
        public String getNewCampaign() {
            return newCampaign;
        }

           
        public void setNewCampaign(String value) {
            this.newCampaign = value;
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

           
        public String getPublishedOnSave() {
            return publishedOnSave;
        }

           
        public void setPublishedOnSave(String value) {
            this.publishedOnSave = value;
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
    public static class UserRoles
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
            "executionPublished",
            "firstOccurrenceToPublish",
            "fullPublished",
            "loadedFromOldDavLocation",
            "loading",
            "markedForDeploymentVersion",
            "needToPersist",
            "oldVersionNumber",
            "persisting",
            "publishState",
            "published",
            "publishedBy",
            "versionEntries",
            "versionNumber"
    })
    public static class VersionHistory
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;
        @XmlElement(name = "CodeChangeUnpublished", required = true)
        protected String codeChangeUnpublished;
        @XmlElement(name = "CodeEverBeenPublished", required = true)
        protected String codeEverBeenPublished;
        @XmlElement(name = "ExecutionPublished", required = true)
        protected String executionPublished;
        @XmlElement(name = "FirstOccurrenceToPublish")
        protected byte firstOccurrenceToPublish;
        @XmlElement(name = "FullPublished", required = true)
        protected String fullPublished;
        @XmlElement(name = "LoadedFromOldDavLocation", required = true)
        protected String loadedFromOldDavLocation;
        @XmlElement(name = "Loading", required = true)
        protected String loading;
        @XmlElement(name = "MarkedForDeploymentVersion")
        protected byte markedForDeploymentVersion;
        @XmlElement(name = "NeedToPersist", required = true)
        protected String needToPersist;
        @XmlElement(name = "OldVersionNumber", required = true)
        protected String oldVersionNumber;
        @XmlElement(name = "Persisting", required = true)
        protected String persisting;
        @XmlElement(name = "PublishState")
        protected byte publishState;
        @XmlElement(name = "Published", required = true)
        protected String published;
        @XmlElement(name = "PublishedBy", required = true)
        protected String publishedBy;
        @XmlElement(name = "VersionEntries", required = true)
        protected VersionHistory.VersionEntries versionEntries;
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

           
        public String getExecutionPublished() {
            return executionPublished;
        }

           
        public void setExecutionPublished(String value) {
            this.executionPublished = value;
        }

           
        public byte getFirstOccurrenceToPublish() {
            return firstOccurrenceToPublish;
        }

           
        public void setFirstOccurrenceToPublish(byte value) {
            this.firstOccurrenceToPublish = value;
        }

           
        public String getFullPublished() {
            return fullPublished;
        }

           
        public void setFullPublished(String value) {
            this.fullPublished = value;
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

           
        public byte getMarkedForDeploymentVersion() {
            return markedForDeploymentVersion;
        }

           
        public void setMarkedForDeploymentVersion(byte value) {
            this.markedForDeploymentVersion = value;
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

           
        public String getPublished() {
            return published;
        }

           
        public void setPublished(String value) {
            this.published = value;
        }

           
        public String getPublishedBy() {
            return publishedBy;
        }

           
        public void setPublishedBy(String value) {
            this.publishedBy = value;
        }

           
        public VersionHistory.VersionEntries getVersionEntries() {
            return versionEntries;
        }

           
        public void setVersionEntries(VersionHistory.VersionEntries value) {
            this.versionEntries = value;
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
                "versionEntryDOs"
        })
        public static class VersionEntries
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "VersionEntryDO")
            protected List<VersionEntries.VersionEntryDO> versionEntryDOs;

               
            public List<VersionEntries.VersionEntryDO> getVersionEntryDOs() {
                if (versionEntryDOs == null) {
                    versionEntryDOs = new ArrayList<VersionEntries.VersionEntryDO>();
                }
                return this.versionEntryDOs;
            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "actionId",
                    "archivedPublishState",
                    "codeChangeUnpublished",
                    "codeEverBeenPublished",
                    "comment",
                    "dateModified",
                    "executionPublished",
                    "firstOccurrenceToPublish",
                    "fullPublished",
                    "loadedFromOldDavLocation",
                    "loading",
                    "markedForDeploymentVersion",
                    "needToPersist",
                    "oldVersionNumber",
                    "persisting",
                    "prevStatusId",
                    "publishState",
                    "published",
                    "publishedBy",
                    "statusComment",
                    "statusId",
                    "userName",
                    "versionIndex",
                    "versionNumber"
            })
            public static class VersionEntryDO
                    implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "ActionId", required = true)
                protected String actionId;
                @XmlElement(name = "ArchivedPublishState")
                protected byte archivedPublishState;
                @XmlElement(name = "CodeChangeUnpublished", required = true)
                protected String codeChangeUnpublished;
                @XmlElement(name = "CodeEverBeenPublished", required = true)
                protected String codeEverBeenPublished;
                @XmlElement(name = "Comment", required = true)
                protected String comment;
                @XmlElement(name = "DateModified", required = true)
                protected String dateModified;
                @XmlElement(name = "ExecutionPublished", required = true)
                protected String executionPublished;
                @XmlElement(name = "FirstOccurrenceToPublish")
                protected byte firstOccurrenceToPublish;
                @XmlElement(name = "FullPublished", required = true)
                protected String fullPublished;
                @XmlElement(name = "LoadedFromOldDavLocation", required = true)
                protected String loadedFromOldDavLocation;
                @XmlElement(name = "Loading", required = true)
                protected String loading;
                @XmlElement(name = "MarkedForDeploymentVersion")
                protected byte markedForDeploymentVersion;
                @XmlElement(name = "NeedToPersist", required = true)
                protected String needToPersist;
                @XmlElement(name = "OldVersionNumber", required = true)
                protected String oldVersionNumber;
                @XmlElement(name = "Persisting", required = true)
                protected String persisting;
                @XmlElement(name = "PrevStatusId")
                protected String prevStatusId;
                @XmlElement(name = "PublishState")
                protected byte publishState;
                @XmlElement(name = "Published", required = true)
                protected String published;
                @XmlElement(name = "PublishedBy", required = true)
                protected String publishedBy;
                @XmlElement(name = "StatusComment", required = true)
                protected String statusComment;
                @XmlElement(name = "StatusId", required = true)
                protected String statusId;
                @XmlElement(name = "UserName", required = true)
                protected String userName;
                @XmlElement(name = "VersionIndex")
                protected byte versionIndex;
                @XmlElement(name = "VersionNumber")
                protected float versionNumber;
                @XmlAttribute(name = "ConcreteClass")
                protected String concreteClass;
                @XmlAttribute(name = "listpos")
                protected Byte listpos;
                @XmlAttribute(name = "objid")
                protected Short objid;

                   
                public String getActionId() {
                    return actionId;
                }

                   
                public void setActionId(String value) {
                    this.actionId = value;
                }

                   
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

                   
                public String getComment() {
                    return comment;
                }

                   
                public void setComment(String value) {
                    this.comment = value;
                }

                   
                public String getDateModified() {
                    return dateModified;
                }

                   
                public void setDateModified(String value) {
                    this.dateModified = value;
                }

                   
                public String getExecutionPublished() {
                    return executionPublished;
                }

                   
                public void setExecutionPublished(String value) {
                    this.executionPublished = value;
                }

                   
                public byte getFirstOccurrenceToPublish() {
                    return firstOccurrenceToPublish;
                }

                   
                public void setFirstOccurrenceToPublish(byte value) {
                    this.firstOccurrenceToPublish = value;
                }

                   
                public String getFullPublished() {
                    return fullPublished;
                }

                   
                public void setFullPublished(String value) {
                    this.fullPublished = value;
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

                   
                public byte getMarkedForDeploymentVersion() {
                    return markedForDeploymentVersion;
                }

                   
                public void setMarkedForDeploymentVersion(byte value) {
                    this.markedForDeploymentVersion = value;
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

                   
                public String getPrevStatusId() {
                    return prevStatusId;
                }

                   
                public void setPrevStatusId(String value) {
                    this.prevStatusId = value;
                }

                   
                public byte getPublishState() {
                    return publishState;
                }

                   
                public void setPublishState(byte value) {
                    this.publishState = value;
                }

                   
                public String getPublished() {
                    return published;
                }

                   
                public void setPublished(String value) {
                    this.published = value;
                }

                   
                public String getPublishedBy() {
                    return publishedBy;
                }

                   
                public void setPublishedBy(String value) {
                    this.publishedBy = value;
                }

                   
                public String getStatusComment() {
                    return statusComment;
                }

                   
                public void setStatusComment(String value) {
                    this.statusComment = value;
                }

                   
                public String getStatusId() {
                    return statusId;
                }

                   
                public void setStatusId(String value) {
                    this.statusId = value;
                }

                   
                public String getUserName() {
                    return userName;
                }

                   
                public void setUserName(String value) {
                    this.userName = value;
                }

                   
                public byte getVersionIndex() {
                    return versionIndex;
                }

                   
                public void setVersionIndex(byte value) {
                    this.versionIndex = value;
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

            }

        }

    }

}