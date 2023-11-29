/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "archivedPublishState",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "controlKey",
        "dateModified",
        "droppedCount",
        "event",
        "eventMarker",
        "eventPhysicalName",
        "excludeFlag",
        "excludeIconPath",
        "firstOccurrenceToPublish",
        "iconPath",
        "inputNodes",
        "inputVariables",
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
        "provideErrorValues",
        "publishState",
        "sortByList",
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
public class StartNodeDataDO
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
    @XmlElement(name = "DateModified", required = true)
    protected String dateModified;
    @XmlElement(name = "DroppedCount")
    protected byte droppedCount;
    @XmlElement(name = "Event", required = true)
    protected StartNodeDataDO.Event event;
    @XmlElement(name = "EventMarker")
    protected byte eventMarker;
    @XmlElement(name = "EventPhysicalName", required = true)
    protected String eventPhysicalName;
    @XmlElement(name = "ExcludeFlag", required = true)
    protected String excludeFlag;
    @XmlElement(name = "ExcludeIconPath", required = true)
    protected String excludeIconPath;
    @XmlElement(name = "FirstOccurrenceToPublish")
    protected byte firstOccurrenceToPublish;
    @XmlElement(name = "IconPath", required = true)
    protected String iconPath;
    @XmlElement(name = "InputNodes", required = true)
    protected StartNodeDataDO.InputNodes inputNodes;
    @XmlElement(name = "InputVariables", required = false)
    protected InputVariables inputVariables;
    @XmlElement(name = "Label", required = true)
    protected String label;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    protected String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    protected String loading;
    @XmlElement(name = "NeedToPersist", required = true)
    protected String needToPersist;
    @XmlElement(name = "NodeDescription")
    protected short nodeDescription;
    @XmlElement(name = "NodeId", required = true)
    protected String nodeId;
    @XmlElement(name = "NodeName", required = true)
    protected String nodeName;
    @XmlElement(name = "NodeState")
    protected byte nodeState;
    @XmlElement(name = "NodeTags", required = true)
    protected StartNodeDataDO.NodeTags nodeTags;
    @XmlElement(name = "NodeType")
    protected byte nodeType;
    @XmlElement(name = "OldVersionNumber", required = true)
    protected String oldVersionNumber;
    @XmlElement(name = "OutputNodes", required = true)
    protected StartNodeDataDO.OutputNodes outputNodes;
    @XmlElement(name = "Persisting", required = true)
    protected String persisting;
    @XmlElement(name = "ProvideErrorValues", required = true)
    protected String provideErrorValues;
    @XmlElement(name = "PublishState")
    protected byte publishState;
    @XmlElement(name = "SortByList", required = true)
    protected StartNodeDataDO.SortByList sortByList;
    @XmlElement(name = "Splitters", required = true)
    protected StartNodeDataDO.Splitters splitters;
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
    protected byte x;
    @XmlElement(name = "Y")
    protected byte y;
    @XmlElement(name = "SystemExtraValues", required = true)
    protected StartNodeDataDO.SystemExtraValues systemExtraValues;
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

       
    public StartNodeDataDO.Event getEvent() {
        return event;
    }

       
    public void setEvent(StartNodeDataDO.Event value) {
        this.event = value;
    }

       
    public byte getEventMarker() {
        return eventMarker;
    }

       
    public void setEventMarker(byte value) {
        this.eventMarker = value;
    }

       
    public String getEventPhysicalName() {
        return eventPhysicalName;
    }

       
    public void setEventPhysicalName(String value) {
        this.eventPhysicalName = value;
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

       
    public StartNodeDataDO.InputNodes getInputNodes() {
        return inputNodes;
    }

       
    public void setInputNodes(StartNodeDataDO.InputNodes value) {
        this.inputNodes = value;
    }


    public InputVariables getInputVariables() {
        return inputVariables;
    }

       
    public void setInputVariables(InputVariables inputVariables) {
        this.inputVariables = inputVariables;
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

       
    public short getNodeDescription() {
        return nodeDescription;
    }

       
    public void setNodeDescription(short value) {
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

       
    public StartNodeDataDO.NodeTags getNodeTags() {
        return nodeTags;
    }

       
    public void setNodeTags(StartNodeDataDO.NodeTags value) {
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

       
    public StartNodeDataDO.OutputNodes getOutputNodes() {
        return outputNodes;
    }

       
    public void setOutputNodes(StartNodeDataDO.OutputNodes value) {
        this.outputNodes = value;
    }

       
    public String getPersisting() {
        return persisting;
    }

       
    public void setPersisting(String value) {
        this.persisting = value;
    }

       
    public String getProvideErrorValues() {
        return provideErrorValues;
    }

       
    public void setProvideErrorValues(String value) {
        this.provideErrorValues = value;
    }

       
    public byte getPublishState() {
        return publishState;
    }

       
    public void setPublishState(byte value) {
        this.publishState = value;
    }

       
    public StartNodeDataDO.SortByList getSortByList() {
        return sortByList;
    }

       
    public void setSortByList(StartNodeDataDO.SortByList value) {
        this.sortByList = value;
    }

       
    public StartNodeDataDO.Splitters getSplitters() {
        return splitters;
    }

       
    public void setSplitters(StartNodeDataDO.Splitters value) {
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

       
    public StartNodeDataDO.SystemExtraValues getSystemExtraValues() {
        return systemExtraValues;
    }

       
    public void setSystemExtraValues(StartNodeDataDO.SystemExtraValues value) {
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
            "archivedPublishState",
            "attachments",
            "codeChangeUnpublished",
            "codeEverBeenPublished",
            "complexType",
            "dateModified",
            "defaultTimeout",
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
            "rdmDateModified",
            "readOnly",
            "replyVars",
            "requestVars",
            "shared",
            "sharedIn",
            "timeout",
            "timeoutEnabled",
            "type",
            "typeDescription",
            "versionNumber",
            "whoModified"
    })
    public static class Event
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
        @XmlElement(name = "ComplexType", required = true)
        protected String complexType;
        @XmlElement(name = "DateModified", required = true)
        protected String dateModified;
        @XmlElement(name = "DefaultTimeout")
        protected byte defaultTimeout;
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
        @XmlElement(name = "RdmDateModified", required = true, type = String.class)
        @XmlJavaTypeAdapter(Adapter1.class)
        @XmlSchemaType(name = "dateTime")
        protected Calendar rdmDateModified;
        @XmlElement(name = "ReadOnly", required = true)
        protected String readOnly;
        @XmlElement(name = "ReplyVars", required = true)
        protected Event.ReplyVars replyVars;
        @XmlElement(name = "RequestVars", required = true)
        protected Event.RequestVars requestVars;
        @XmlElement(name = "Shared", required = true)
        protected String shared;
        @XmlElement(name = "SharedIn", required = true)
        protected Event.SharedIn sharedIn;
        @XmlElement(name = "Timeout")
        protected byte timeout;
        @XmlElement(name = "TimeoutEnabled", required = true)
        protected String timeoutEnabled;
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

           
        public String getComplexType() {
            return complexType;
        }

           
        public void setComplexType(String value) {
            this.complexType = value;
        }

           
        public String getDateModified() {
            return dateModified;
        }

           
        public void setDateModified(String value) {
            this.dateModified = value;
        }

           
        public byte getDefaultTimeout() {
            return defaultTimeout;
        }

           
        public void setDefaultTimeout(byte value) {
            this.defaultTimeout = value;
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

           
        public Calendar getRdmDateModified() {
            return rdmDateModified;
        }

           
        public void setRdmDateModified(Calendar value) {
            this.rdmDateModified = value;
        }

           
        public String getReadOnly() {
            return readOnly;
        }

           
        public void setReadOnly(String value) {
            this.readOnly = value;
        }

           
        public Event.ReplyVars getReplyVars() {
            return replyVars;
        }

           
        public void setReplyVars(Event.ReplyVars value) {
            this.replyVars = value;
        }

           
        public Event.RequestVars getRequestVars() {
            return requestVars;
        }

           
        public void setRequestVars(Event.RequestVars value) {
            this.requestVars = value;
        }

           
        public String getShared() {
            return shared;
        }

           
        public void setShared(String value) {
            this.shared = value;
        }

           
        public Event.SharedIn getSharedIn() {
            return sharedIn;
        }

           
        public void setSharedIn(Event.SharedIn value) {
            this.sharedIn = value;
        }

           
        public byte getTimeout() {
            return timeout;
        }

           
        public void setTimeout(byte value) {
            this.timeout = value;
        }

           
        public String getTimeoutEnabled() {
            return timeoutEnabled;
        }

           
        public void setTimeoutEnabled(String value) {
            this.timeoutEnabled = value;
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
                @XmlElement(name = "Value")
                protected ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value;
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

                public ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value getValue() {
                    return value;
                }

                public void setValue(ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value) {
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
                protected com.sas.rtdm2id.model.rtdm.StartNodeDataDO.Event.RequestVars.IBVariableDO.SharedIn sharedIn;
                @XmlElement(name = "TestVarInfoId", required = true)
                protected String testVarInfoId;
                @XmlElement(name = "Type")
                protected byte type;
                @XmlElement(name = "TypeDescription", required = true)
                protected String typeDescription;
                @XmlElement(name = "Value")
                protected ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value;
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

                   
                public com.sas.rtdm2id.model.rtdm.StartNodeDataDO.Event.RequestVars.IBVariableDO.SharedIn getSharedIn() {
                    return sharedIn;
                }

                   
                public void setSharedIn(com.sas.rtdm2id.model.rtdm.StartNodeDataDO.Event.RequestVars.IBVariableDO.SharedIn value) {
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

                public ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value getValue() {
                    return value;
                }

                public void setValue(ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value) {
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
            "outputNodeName"
    })
    public static class SystemExtraValues
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "OutputNodeName", required = true)
        protected String outputNodeName;

           
        public String getOutputNodeName() {
            return outputNodeName;
        }

           
        public void setOutputNodeName(String value) {
            this.outputNodeName = value;
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "ibVariableDOs"
    })
    public static class InputVariables
            implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "IBVariableDO")
        protected List<StartNodeDataDO.Event.RequestVars.IBVariableDO> ibVariableDOs;

            
        public List<StartNodeDataDO.Event.RequestVars.IBVariableDO> getIBVariableDOs() {
            if (ibVariableDOs == null) {
                ibVariableDOs = new ArrayList<StartNodeDataDO.Event.RequestVars.IBVariableDO>();
            }
            return this.ibVariableDOs;
        }
    }

}