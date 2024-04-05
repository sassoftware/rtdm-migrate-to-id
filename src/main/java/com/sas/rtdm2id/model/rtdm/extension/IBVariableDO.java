/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm.extension;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

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
        "identifiersRemoved",
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
public class IBVariableDO implements Serializable {

    private static final long serialVersionUID = -1L;
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
    protected ErrorValue errorValue;
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
    @XmlElement(name = "IdentifiersRemoved")
    protected String identifiersRemoved;
    @XmlElement(name = "Identifier")
    protected Identifier identifier;
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
    protected SharedIn sharedIn;
    @XmlElement(name = "TestVarInfoId", required = true)
    protected String testVarInfoId;
    @XmlElement(name = "Type")
    protected byte type;
    @XmlElement(name = "TypeDescription", required = true)
    protected String typeDescription;
    @XmlElement(name = "Value")
    protected Value value;
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
        protected SharedIn sharedIn;
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
        protected ValueTypeVarInfoDO valueTypeVarInfoDO;
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

}