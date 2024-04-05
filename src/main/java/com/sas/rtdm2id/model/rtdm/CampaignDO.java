/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm;

import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class CampaignDO implements Serializable {

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
    @Getter
    @Setter
    public static class CampaignExecutionHistoryDO implements Serializable {

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

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    @Getter
    @Setter
    public static class CampaignMessages implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "checkListItemDOs"
    })
    @Getter
    public static class CheckListItems implements Serializable {

        private final static long serialVersionUID = -1L;

        @XmlElement(name = "CheckListItemDO")
        protected List<CheckListItems.CheckListItemDO> checkListItemDOs = new ArrayList<>();

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
        @Getter
        @Setter
        public static class CheckListItemDO implements Serializable {

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

        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    @Getter
    @Setter
    public static class CustomPageDetails implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

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
    @Getter
    @Setter
    public static class Definition implements Serializable {

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
           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "userAndGroupDOs"
        })
        @Getter
        @Setter
        public static class AccessControls implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "UserAndGroupDO")
            protected List<AccessControls.UserAndGroupDO> userAndGroupDOs = new ArrayList<>();
               
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
            @Getter
            @Setter
            public static class UserAndGroupDO implements Serializable {

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

                   
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "value"
                })
                @Getter
                @Setter
                public static class Members implements Serializable {

                    private final static long serialVersionUID = -1L;
                    @XmlValue
                    protected String value;
                    @XmlAttribute(name = "EmptyList")
                    protected String emptyList;


                }

            }

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        @Getter
        @Setter
        public static class AllPages implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlValue
            protected String value;
            @XmlAttribute(name = "ListDelimiter")
            protected String listDelimiter;
            @XmlAttribute(name = "ListTypes")
            protected String listTypes;

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "userAndGroupDOs"
        })
        @Getter
        public static class Approvers implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "UserAndGroupDO")
            protected List<Approvers.UserAndGroupDO> userAndGroupDOs = new ArrayList<>();

               
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
            @Getter
            @Setter
            public static class UserAndGroupDO implements Serializable {

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
                   
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "value"
                })
                @Getter
                @Setter
                public static class Members implements Serializable {

                    private final static long serialVersionUID = -1L;
                    @XmlValue
                    protected String value;
                    @XmlAttribute(name = "EmptyList")
                    protected String emptyList;
                    @XmlAttribute(name = "ListDelimiter")
                    protected String listDelimiter;
                    @XmlAttribute(name = "ListTypes")
                    protected String listTypes;

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
        @Getter
        @Setter
        public static class BriefData implements Serializable {

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
            @Getter
            @Setter
            public static class ChampionChallengerConfigDO implements Serializable {

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
            @Getter
            @Setter
            public static class ControlGroupConfigDO implements Serializable {

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

            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "value"
            })
            @Getter
            @Setter
            public static class UserDefinedFields implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlValue
                protected String value;
                @XmlAttribute(name = "EmptyList")
                protected String emptyList;

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
        @Getter
        @Setter
        public static class CheckList implements Serializable {

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

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "checkListStepDOs"
            })
            @Getter
            public static class Steps implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "CheckListStepDO")
                protected List<Steps.CheckListStepDO> checkListStepDOs = new ArrayList<>();


                   
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
                @Getter
                @Setter
                public static class CheckListStepDO implements Serializable {

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

                }

            }

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "checkListItemDOs"
        })
        @Getter
        public static class CheckListItems implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "CheckListItemDO")
            protected List<com.sas.rtdm2id.model.rtdm.CampaignDO.Definition.CheckListItems.CheckListItemDO> checkListItemDOs;


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
            @Getter
            @Setter
            public static class CheckListItemDO implements Serializable {

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

            }

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        @Getter
        @Setter
        public static class CustomDetailPages implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlValue
            protected String value;
            @XmlAttribute(name = "EmptyList")
            protected String emptyList;

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
        @Getter
        @Setter
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
        @Getter
        @Setter
        public static class ImportDetails implements Serializable {

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

        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "pageConfigDOs"
        })
        @Getter
        public static class PageConfigurations implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "PageConfigDO")
            protected List<PageConfigurations.PageConfigDO> pageConfigDOs;

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
            @Getter
            @Setter
            public static class PageConfigDO implements Serializable {

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

                   
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "pageDOs"
                })
                @Getter
                public static class DesignPages implements Serializable {

                    private final static long serialVersionUID = -1L;
                    @XmlElement(name = "PageDO")
                    protected List<DesignPages.PageDO> pageDOs = new ArrayList<>();

                       
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
                    @Getter
                    @Setter
                    public static class PageDO implements Serializable {

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

                    }

                }


                   
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "pageDOs"
                })
                @Getter
                public static class ProcessPages implements Serializable {

                    private final static long serialVersionUID = -1L;
                    @XmlElement(name = "PageDO")
                    protected List<ProcessPages.PageDO> pageDOs = new ArrayList<>();

                       
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
                    @Getter
                    @Setter
                    public static class PageDO implements Serializable {

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
        @Getter
        @Setter
        public static class Schedule implements Serializable {

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
            @Getter
            @Setter
            public static class ExecutionOffset implements Serializable {

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

            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "value"
            })
            @Getter
            @Setter
            public static class ScheduleActionList implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlValue
                protected String value;
                @XmlAttribute(name = "EmptyList")
                protected String emptyList;

            }

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "sharedDefinitionUseDO"
        })
        @Getter
        @Setter
        public static class SharedIn implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "SharedDefinitionUseDO", required = true)
            protected SharedIn.SharedDefinitionUseDO sharedDefinitionUseDO;

               
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
            @Getter
            @Setter
            public static class SharedDefinitionUseDO implements Serializable {

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

            }

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "statusDOs"
        })
        @Getter
        public static class StatusList implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "StatusDO")
            protected List<StatusList.StatusDO> statusDOs = new ArrayList<>();
               
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
            @Getter
            @Setter
            public static class StatusDO implements Serializable {

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
    @Getter
    @Setter
    public static class Folder implements Serializable {

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

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    @Getter
    @Setter
    public static class ProcessingEndDate implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "ConcreteClass")
        protected String concreteClass;

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    @Getter
    @Setter
    public static class ProcessingStartDate implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "ConcreteClass")
        protected String concreteClass;

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
    @Getter
    @Setter
    public static class PublishedObjects implements Serializable {

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

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    @Getter
    @Setter
    public static class UserRoles implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

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
    @Getter
    @Setter
    public static class VersionHistory implements Serializable {

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

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "versionEntryDOs"
        })
        public static class VersionEntries implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "VersionEntryDO")
            protected List<VersionEntries.VersionEntryDO> versionEntryDOs = new ArrayList<>();
               
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
            @Getter
            @Setter
            public static class VersionEntryDO implements Serializable {

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

            }

        }

    }

}