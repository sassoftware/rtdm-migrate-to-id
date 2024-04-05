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
@Getter
@Setter
public class ReplyNodeDataDO implements Serializable {

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


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "audienceDO"
    })
    @Getter
    @Setter
    public static class AudienceList implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "AudienceDO", required = true)
        protected AudienceList.AudienceDO audienceDO;
           
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
        @Getter
        @Setter
        public static class AudienceDO implements Serializable {

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
            public static class RemoveStagedTreatmentsChannels implements Serializable {

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
            "ibVariableDOs"
    })
    @Getter
    public static class ContactHistoryVariables implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "IBVariableDO")
        protected List<ContactHistoryVariables.IBVariableDO> ibVariableDOs = new ArrayList<>();

           
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
    @Getter
    @Setter
    public static class Definition implements Serializable {

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
            protected Short objid;

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        @Getter
        @Setter
        public static class ResponseList implements Serializable {

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
        public static class SharedIn implements Serializable {

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
    public static class InputNodes implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "ListTypes")
        protected String listTypes;

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
    public static class OutputNodes implements Serializable {

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

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "ibVariableDOs"
    })
    @Getter
    public static class TreatmentReplyVariables implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "IBVariableDO")
        protected List<ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO> ibVariableDOs = new ArrayList<>();

    }

}