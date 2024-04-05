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
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "controlKey",
        "customNode",
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
        "needToPersist",
        "nodeDescription",
        "nodeId",
        "nodeName",
        "nodeState",
        "nodeTags",
        "nodeType",
        "oldVersionNumber",
        "outputNodes",
        "persisting",
        "process",
        "publishState",
        "sortByList",
        "splitters",
        "subjectID",
        "totalCount",
        "userUpdatedProcessFlag",
        "validInputSubject",
        "validOutputSubject",
        "versionNumber",
        "x",
        "y",
        "systemExtraValues"
})
@Getter
@Setter
public class ProcessNodeDataDO implements Serializable {

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ArchivedPublishState")
    protected byte archivedPublishState;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    protected String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    protected String codeEverBeenPublished;
    @XmlElement(name = "ControlKey")
    protected byte controlKey;
    @XmlElement(name = "CustomNode", required = true)
    protected String customNode;
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
    protected ProcessNodeDataDO.InputNodes inputNodes;
    @XmlElement(name = "Label", required = true)
    protected String label;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    protected String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    protected String loading;
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
    protected ProcessNodeDataDO.NodeTags nodeTags;
    @XmlElement(name = "NodeType")
    protected byte nodeType;
    @XmlElement(name = "OldVersionNumber", required = true)
    protected String oldVersionNumber;
    @XmlElement(name = "OutputNodes", required = true)
    protected ProcessNodeDataDO.OutputNodes outputNodes;
    @XmlElement(name = "Persisting", required = true)
    protected String persisting;
    @XmlElement(name = "Process", required = true)
    protected ProcessNodeDataDO.Process process;
    @XmlElement(name = "PublishState")
    protected byte publishState;
    @XmlElement(name = "SortByList", required = true)
    protected ProcessNodeDataDO.SortByList sortByList;
    @XmlElement(name = "Splitters", required = true)
    protected ProcessNodeDataDO.Splitters splitters;
    @XmlElement(name = "SubjectID", required = true)
    protected String subjectID;
    @XmlElement(name = "TotalCount")
    protected byte totalCount;
    @XmlElement(name = "UserUpdatedProcessFlag", required = true)
    protected String userUpdatedProcessFlag;
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
    protected ProcessNodeDataDO.SystemExtraValues systemExtraValues;
    @XmlAttribute(name = "objid")
    protected Short objid;
       
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
    public static class OutputNodes
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "ListTypes")
        protected String listTypes;
    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "arbitrationUDFSelectionTags",
            "arbitrationUDFSelectionType",
            "archivedPublishState",
            "attachments",
            "brmInfo",
            "businessContextId",
            "codeChangeUnpublished",
            "codeEverBeenPublished",
            "ds2code",
            "datagridIdentifier",
            "datagridMaxRows",
            "datagridName",
            "dateModified",
            "description",
            "firstOccurrenceToPublish",
            "folder",
            "forceOverwrite",
            "forced",
            "hidden",
            "id",
            "inputVariableList",
            "libName",
            "loadedFromOldDavLocation",
            "loading",
            "lockedBy",
            "metadataType",
            "methodInfo",
            "migratedTo61WithMultipleBCs",
            "modelManagerPublishedModelName",
            "name",
            "needToPersist",
            "noWritePermission",
            "oldVersionNumber",
            "outputVariableList",
            "persistState",
            "persisting",
            "physicalActivityMethodName",
            "physicalActivityName",
            "physicalName",
            "processType",
            "processTypeDescription",
            "publishState",
            "readOnly",
            "returnDatagridFlag",
            "ruleSetCount",
            "shared",
            "sharedIn",
            "tableName",
            "tempProcess",
            "type",
            "typeDescription",
            "usage",
            "useBIWebService",
            "versionNumber",
            "whoModified"
    })
    @Getter
    @Setter
    // TODO: Refactor this into a separate class so it can be shared with other classes
    public static class Process implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "ArbitrationUDFSelectionTags", required = true)
        protected Process.ArbitrationUDFSelectionTags arbitrationUDFSelectionTags;
        @XmlElement(name = "ArbitrationUDFSelectionType")
        protected byte arbitrationUDFSelectionType;
        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;
        @XmlElement(name = "Attachments", required = true)
        protected String attachments;
        @XmlElement(name = "BrmInfo", required = true)
        protected Process.BrmInfo brmInfo;
        @XmlElement(name = "BusinessContextId", required = true)
        protected String businessContextId;
        @XmlElement(name = "CodeChangeUnpublished", required = true)
        protected String codeChangeUnpublished;
        @XmlElement(name = "CodeEverBeenPublished", required = true)
        protected String codeEverBeenPublished;
        @XmlElement(name = "Ds2Code")
        protected String ds2code;
        @XmlElement(name = "DatagridIdentifier")
        protected Process.DatagridIdentifier datagridIdentifier;
        @XmlElement(name = "DatagridMaxRows")
        protected byte datagridMaxRows;
        @XmlElement(name = "DatagridName", required = true)
        protected String datagridName;
        @XmlElement(name = "DateModified", required = true)
        protected String dateModified;
        @XmlElement(name = "Description", required = true)
        protected String description;
        @XmlElement(name = "FirstOccurrenceToPublish")
        protected byte firstOccurrenceToPublish;
        @XmlElement(name = "Folder", required = true)
        protected Process.Folder folder;
        @XmlElement(name = "ForceOverwrite", required = true)
        protected String forceOverwrite;
        @XmlElement(name = "Forced", required = true)
        protected String forced;
        @XmlElement(name = "Hidden", required = true)
        protected String hidden;
        @XmlElement(name = "Id", required = true)
        protected String id;
        @XmlElement(name = "InputVariableList", required = true)
        protected Process.InputVariableList inputVariableList;
        @XmlElement(name = "LibName", required = true)
        protected String libName;
        @XmlElement(name = "LoadedFromOldDavLocation", required = true)
        protected String loadedFromOldDavLocation;
        @XmlElement(name = "Loading", required = true)
        protected String loading;
        @XmlElement(name = "LockedBy", required = true)
        protected String lockedBy;
        @XmlElement(name = "MetadataType", required = true)
        protected String metadataType;
        @XmlElement(name = "MethodInfo", required = true)
        protected Process.MethodInfo methodInfo;
        @XmlElement(name = "MigratedTo61WithMultipleBCs", required = true)
        protected String migratedTo61WithMultipleBCs;
        @XmlElement(name = "ModelManagerPublishedModelName", required = true)
        protected String modelManagerPublishedModelName;
        @XmlElement(name = "Name", required = true)
        protected String name;
        @XmlElement(name = "NeedToPersist", required = true)
        protected String needToPersist;
        @XmlElement(name = "NoWritePermission", required = true)
        protected String noWritePermission;
        @XmlElement(name = "OldVersionNumber", required = true)
        protected String oldVersionNumber;
        @XmlElement(name = "OutputVariableList", required = true)
        protected Process.OutputVariableList outputVariableList;
        @XmlElement(name = "PersistState", required = true)
        protected String persistState;
        @XmlElement(name = "Persisting", required = true)
        protected String persisting;
        @XmlElement(name = "PhysicalActivityMethodName", required = true)
        protected String physicalActivityMethodName;
        @XmlElement(name = "PhysicalActivityName", required = true)
        protected String physicalActivityName;
        @XmlElement(name = "PhysicalName", required = true)
        protected String physicalName;
        @XmlElement(name = "ProcessType")
        protected byte processType;
        @XmlElement(name = "ProcessTypeDescription", required = true)
        protected String processTypeDescription;
        @XmlElement(name = "PublishState")
        protected byte publishState;
        @XmlElement(name = "ReadOnly", required = true)
        protected String readOnly;
        @XmlElement(name = "ReturnDatagridFlag", required = true)
        protected String returnDatagridFlag;
        @XmlElement(name = "RuleSetCount")
        protected byte ruleSetCount;
        @XmlElement(name = "Shared", required = true)
        protected String shared;
        @XmlElement(name = "SharedIn", required = true)
        protected Process.SharedIn sharedIn;
        @XmlElement(name = "TableName", required = true)
        protected String tableName;
        @XmlElement(name = "TempProcess", required = true)
        protected String tempProcess;
        @XmlElement(name = "Type")
        protected byte type;
        @XmlElement(name = "TypeDescription", required = true)
        protected String typeDescription;
        @XmlElement(name = "Usage", required = true)
        protected String usage;
        @XmlElement(name = "UseBIWebService", required = true)
        protected String useBIWebService;
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
                "value"
        })
        @Getter
        @Setter
        public static class ArbitrationUDFSelectionTags implements Serializable {

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
                "currentDescription",
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
        public static class BrmInfo implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
            @XmlElement(name = "CurrentDescription", required = true)
            protected String currentDescription;
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
            protected Short objid;

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "archivedPublishState",
                "attachments",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "description",
                "firstOccurrenceToPublish",
                "forceOverwrite",
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
                "oldVersionNumber",
                "persistState",
                "persisting",
                "physicalName",
                "publishState",
                "readOnly",
                "shared",
                "sharedIn",
                "type",
                "typeDescription",
                "versionNumber",
                "whoModified"
        })
        @Getter
        @Setter
        public static class DatagridIdentifier implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "Attachments", required = true)
            protected String attachments;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
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
            @XmlElement(name = "OldVersionNumber", required = true)
            protected String oldVersionNumber;
            @XmlElement(name = "PersistState", required = true)
            protected String persistState;
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "PhysicalName", required = true)
            protected String physicalName;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "ReadOnly", required = true)
            protected String readOnly;
            @XmlElement(name = "Shared", required = true)
            protected String shared;
            @XmlElement(name = "SharedIn", required = true)
            protected DatagridIdentifier.SharedIn sharedIn;
            @XmlElement(name = "Type")
            protected byte type;
            @XmlElement(name = "TypeDescription", required = true)
            protected String typeDescription;
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
        public static class Folder implements Serializable {

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
            protected Short objid;

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "ibVariableDOs"
        })
        @Getter
        @Setter
        public static class InputVariableList implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "IBVariableDO")
            protected List<InputVariableList.IBVariableDO> ibVariableDOs = new ArrayList<>();
            @XmlAttribute(name = "EmptyList")
            protected String emptyList;
               
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
                    "identifier",
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
                @XmlElement(name = "Identifier")
                protected IBVariableDO.Identifier identifier;
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
                @XmlElement(name = "Value")
                protected IBVariableDO.Value value;
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
                        "attachments",
                        "codeChangeUnpublished",
                        "codeEverBeenPublished",
                        "description",
                        "firstOccurrenceToPublish",
                        "forceOverwrite",
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
                        "oldVersionNumber",
                        "persistState",
                        "persisting",
                        "physicalName",
                        "publishState",
                        "readOnly",
                        "shared",
                        "sharedIn",
                        "type",
                        "typeDescription",
                        "versionNumber",
                        "whoModified"
                })
                @Getter
                @Setter
                public static class Identifier implements Serializable {

                    private final static long serialVersionUID = -1L;
                    @XmlElement(name = "ArchivedPublishState")
                    protected byte archivedPublishState;
                    @XmlElement(name = "Attachments", required = true)
                    protected String attachments;
                    @XmlElement(name = "CodeChangeUnpublished", required = true)
                    protected String codeChangeUnpublished;
                    @XmlElement(name = "CodeEverBeenPublished", required = true)
                    protected String codeEverBeenPublished;
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
                    @XmlElement(name = "OldVersionNumber", required = true)
                    protected String oldVersionNumber;
                    @XmlElement(name = "PersistState", required = true)
                    protected String persistState;
                    @XmlElement(name = "Persisting", required = true)
                    protected String persisting;
                    @XmlElement(name = "PhysicalName", required = true)
                    protected String physicalName;
                    @XmlElement(name = "PublishState")
                    protected byte publishState;
                    @XmlElement(name = "ReadOnly", required = true)
                    protected String readOnly;
                    @XmlElement(name = "Shared", required = true)
                    protected String shared;
                    @XmlElement(name = "SharedIn", required = true)
                    protected Identifier.SharedIn sharedIn;
                    @XmlElement(name = "Type")
                    protected byte type;
                    @XmlElement(name = "TypeDescription", required = true)
                    protected String typeDescription;
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
                        "listValue",
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
                    @XmlElement(name = "StringValue")
                    protected String stringValue;
                    @XmlElement(name = "ListValue")
                    protected String listValue;
                    @XmlElement(name = "Type")
                    protected byte type;
                    @XmlElement(name = "ValueTypeVarInfoDO", required = true)
                    protected ValueTypeVarInfoDO valueTypeVarInfoDO;
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
                "firstOccurrenceToPublish",
                "loadedFromOldDavLocation",
                "loading",
                "modelDescription",
                "modelManagerModelModifiedUser",
                "needToPersist",
                "oldVersionNumber",
                "persisting",
                "publishState",
                "reportModelName",
                "reportModelVersion",
                "scoreCode",
                "versionNumber"
        })
        @Getter
        @Setter
        public static class MethodInfo implements Serializable {

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
            @XmlElement(name = "ModelDescription", required = true)
            protected String modelDescription;
            @XmlElement(name = "ModelManagerModelModifiedUser", required = true)
            protected String modelManagerModelModifiedUser;
            @XmlElement(name = "NeedToPersist", required = true)
            protected String needToPersist;
            @XmlElement(name = "OldVersionNumber", required = true)
            protected String oldVersionNumber;
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "ReportModelName", required = true)
            protected String reportModelName;
            @XmlElement(name = "ReportModelVersion", required = true)
            protected String reportModelVersion;
            @XmlElement(name = "ScoreCode", required = true)
            protected String scoreCode;
            @XmlElement(name = "VersionNumber")
            protected float versionNumber;
            @XmlAttribute(name = "ConcreteClass")
            protected String concreteClass;
            @XmlAttribute(name = "objid")
            protected Short objid;

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "ibVariableDOs"
        })
        @Getter
        public static class OutputVariableList implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "IBVariableDO")
            protected List<OutputVariableList.IBVariableDO> ibVariableDOs = new ArrayList<>();
               
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
                protected com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO.SharedIn sharedIn;
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
                protected Short listpos;
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
            "outputNodeName"
    })
    @Getter
    @Setter
    public static class SystemExtraValues implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "OutputNodeName", required = true)
        protected String outputNodeName;

    }

}