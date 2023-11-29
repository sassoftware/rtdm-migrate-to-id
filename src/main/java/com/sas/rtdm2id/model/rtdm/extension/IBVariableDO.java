/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm.extension;

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
public class IBVariableDO
        implements Serializable {

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


    public ErrorValue getErrorValue() {
        return errorValue;
    }

       
    public void setErrorValue(ErrorValue value) {
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

    
    public String getIdentifiersRemoved() {
        return identifiersRemoved;
    }

       
    public void setIdentifiersRemoved(String value) {
        this.identifiersRemoved = value;
    }


       
    public IBVariableDO.Identifier getIdentifier() {
        return identifier;
    }

       
    public void setIdentifier(IBVariableDO.Identifier value) {
        this.identifier = value;
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

       
    public Value getValue() {
        return value;
    }

       
    public void setValue(Value value) {
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
    public static class Identifier
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

           
        public String getShared() {
            return shared;
        }

           
        public void setShared(String value) {
            this.shared = value;
        }

           
        public SharedIn getSharedIn() {
            return sharedIn;
        }

           
        public void setSharedIn(SharedIn value) {
            this.sharedIn = value;
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
        protected ValueTypeVarInfoDO valueTypeVarInfoDO;
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
    
           
        public ValueTypeVarInfoDO getValueTypeVarInfoDO() {
            return valueTypeVarInfoDO;
        }
    
           
        public void setValueTypeVarInfoDO(ValueTypeVarInfoDO value) {
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

}