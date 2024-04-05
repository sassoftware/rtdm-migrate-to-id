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
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class FlowDO implements Serializable {

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


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "calculatedItemDOs"
    })
    @Getter
    public static class CalculatedDataItemList implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "CalculatedItemDO")
        protected List<CalculatedItemDO> calculatedItemDOs = new ArrayList<>();

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    @Getter
    @Setter
    public static class CampaignTreatmentSetsReferenced implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "notificationDOs"
    })
    @Getter
    public static class ConfiguredNotifications implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "NotificationDO")
        protected List<ConfiguredNotifications.NotificationDO> notificationDOs = new ArrayList<>();
           
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
        @Getter
        @Setter
        public static class NotificationDO implements Serializable {

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

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "notificationEventDOs"
            })
            @Getter
            public static class Events implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "NotificationEventDO")
                protected List<Events.NotificationEventDO> notificationEventDOs = new ArrayList<>();

                   
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
                @Getter
                @Setter
                public static class NotificationEventDO implements Serializable {

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
                       
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "value"
                    })
                    @Getter
                    @Setter
                    public static class CcList implements Serializable {

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
                    @Getter
                    @Setter
                    public static class SubstitutableSubject implements Serializable {

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

                    }


                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "value"
                    })
                    @Getter
                    @Setter
                    public static class ToList implements Serializable {

                        private final static long serialVersionUID = -1L;
                        @XmlValue
                        protected String value;
                        @XmlAttribute(name = "EmptyList")
                        protected String emptyList;

                    }

                }

            }

        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    @Getter
    @Setter
    public static class EditorList implements Serializable {

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
    @Getter
    @Setter
    public static class FixupStatus implements Serializable {

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
    public static class FolderDO implements Serializable {

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
    public static class GlobalVariableReferences implements Serializable {

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
            "value"
    })
    @Getter
    @Setter
    public static class IdentifierReferences implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    @Getter
    @Setter
    public static class ImplicitProcessReferences implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    @Getter
    @Setter
    public static class NodeGroupList implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;
    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    @Getter
    @Setter
    public static class NoteList implements Serializable {

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
    @Getter
    @Setter
    public static class StandardReply implements Serializable {

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
        @Getter
        @Setter
        public static class CellNodeData implements Serializable {

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
               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "value"
            })
            @Getter
            @Setter
            public static class AncestorLinkNodes implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlValue
                protected String value;
                @XmlAttribute(name = "EmptyList")
                protected String emptyList;

            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "value"
            })
            @Getter
            @Setter
            public static class NodeTags implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlValue
                protected String value;
                @XmlAttribute(name = "EmptyList")
                protected String emptyList;

            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "value"
            })
            @Getter
            @Setter
            public static class SortByList implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlValue
                protected String value;
                @XmlAttribute(name = "EmptyList")
                protected String emptyList;

            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "value"
            })
            @Getter
            @Setter
            public static class Splitters implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlValue
                protected String value;
                @XmlAttribute(name = "EmptyList")
                protected String emptyList;
            }

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        @Getter
        @Setter
        public static class ContactHistoryVariables implements Serializable {

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
        @Getter
        @Setter
        public static class EventReply implements Serializable {

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
               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "ibVariableDOs"
            })
            @Getter
            public static class ReplyVars implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "IBVariableDO")
                protected List<ReplyVars.IBVariableDO> ibVariableDOs = new ArrayList<>();

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
                @Getter
                @Setter
                public static class IBVariableDO implements Serializable {

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
                    @Getter
                    @Setter
                    public static class ErrorValue implements Serializable {

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
                        @Getter
                        @Setter
                        public static class ValueTypeVarInfoDO implements Serializable {

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

                        }

                    }


                       
                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "value"
                    })
                    @Getter
                    @Setter
                    public static class SharedIn implements Serializable {

                        private final static long serialVersionUID = -1L;
                        @XmlValue
                        protected String value;
                        @XmlAttribute(name = "EmptyList")
                        protected String emptyList;

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
        @Getter
        @Setter
        public static class PackageDO implements Serializable {

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

        }


        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        @Getter
        @Setter
        public static class SubjectVariables implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlValue
            protected String value;
            @XmlAttribute(name = "EmptyList")
            protected String emptyList;

        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "testCaseDO"
    })
    @Getter
    @Setter
    public static class TestCases implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "TestCaseDO", required = true)
        protected TestCases.TestCaseDO testCaseDO;
           
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
        @Getter
        @Setter
        public static class TestCaseDO implements Serializable {

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
               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "value"
            })
            @Getter
            @Setter
            public static class GlobalVars implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlValue
                protected String value;
                @XmlAttribute(name = "EmptyList")
                protected String emptyList;

            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "value"
            })
            @Getter
            @Setter
            public static class ReplyVars implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlValue
                protected String value;
                @XmlAttribute(name = "EmptyList")
                protected String emptyList;

            }


               
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "ibVariableDOs"
            })
            @Getter
            public static class RequestVars implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlElement(name = "IBVariableDO")
                protected List<RequestVars.IBVariableDO> ibVariableDOs = new ArrayList<>();
                   
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
                @Getter
                @Setter
                public static class IBVariableDO implements Serializable {

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


                    @XmlAccessorType(XmlAccessType.FIELD)
                    @XmlType(name = "", propOrder = {
                            "value"
                    })
                    @Getter
                    @Setter
                    public static class SharedIn implements Serializable {

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
                    @Getter
                    @Setter
                    public static class Value implements Serializable {

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
            @Getter
            @Setter
            public static class Results implements Serializable {

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
                   
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                        "value"
                })
                @Getter
                @Setter
                public static class ExecutedNodePath implements Serializable {

                    private final static long serialVersionUID = -1L;
                    @XmlValue
                    protected String value;
                    @XmlAttribute(name = "EmptyList")
                    protected String emptyList;

                }

            }

        }

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "userAndGroupDOs"
    })
    @Getter
    public static class UserRoles implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "UserAndGroupDO")
        protected List<UserRoles.UserAndGroupDO> userAndGroupDOs = new ArrayList<>();
           
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
            protected Short objid;


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
            "value"
    })
    @Getter
    @Setter
    public static class ViewerList implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

    }

       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    @Getter
    @Setter
    public static class ZOrderIdList implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "ListDelimiter")
        protected String listDelimiter;
        @XmlAttribute(name = "ListTypes")
        protected String listTypes;

    }

}