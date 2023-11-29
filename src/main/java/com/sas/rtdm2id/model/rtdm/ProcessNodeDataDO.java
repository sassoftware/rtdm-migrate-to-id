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
public class ProcessNodeDataDO
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

       
    public String getCustomNode() {
        return customNode;
    }

       
    public void setCustomNode(String value) {
        this.customNode = value;
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

       
    public ProcessNodeDataDO.InputNodes getInputNodes() {
        return inputNodes;
    }

       
    public void setInputNodes(ProcessNodeDataDO.InputNodes value) {
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

       
    public ProcessNodeDataDO.NodeTags getNodeTags() {
        return nodeTags;
    }

       
    public void setNodeTags(ProcessNodeDataDO.NodeTags value) {
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

       
    public ProcessNodeDataDO.OutputNodes getOutputNodes() {
        return outputNodes;
    }

       
    public void setOutputNodes(ProcessNodeDataDO.OutputNodes value) {
        this.outputNodes = value;
    }

       
    public String getPersisting() {
        return persisting;
    }

       
    public void setPersisting(String value) {
        this.persisting = value;
    }

       
    public ProcessNodeDataDO.Process getProcess() {
        return process;
    }

       
    public void setProcess(ProcessNodeDataDO.Process value) {
        this.process = value;
    }

       
    public byte getPublishState() {
        return publishState;
    }

       
    public void setPublishState(byte value) {
        this.publishState = value;
    }

       
    public ProcessNodeDataDO.SortByList getSortByList() {
        return sortByList;
    }

       
    public void setSortByList(ProcessNodeDataDO.SortByList value) {
        this.sortByList = value;
    }

       
    public ProcessNodeDataDO.Splitters getSplitters() {
        return splitters;
    }

       
    public void setSplitters(ProcessNodeDataDO.Splitters value) {
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

       
    public String getUserUpdatedProcessFlag() {
        return userUpdatedProcessFlag;
    }

       
    public void setUserUpdatedProcessFlag(String value) {
        this.userUpdatedProcessFlag = value;
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

       
    public ProcessNodeDataDO.SystemExtraValues getSystemExtraValues() {
        return systemExtraValues;
    }

       
    public void setSystemExtraValues(ProcessNodeDataDO.SystemExtraValues value) {
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
    // TODO: Refactor this into a separate class so it can be shared with other classes
    public static class Process
            implements Serializable {

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

           
        public Process.ArbitrationUDFSelectionTags getArbitrationUDFSelectionTags() {
            return arbitrationUDFSelectionTags;
        }

           
        public void setArbitrationUDFSelectionTags(Process.ArbitrationUDFSelectionTags value) {
            this.arbitrationUDFSelectionTags = value;
        }

           
        public byte getArbitrationUDFSelectionType() {
            return arbitrationUDFSelectionType;
        }

           
        public void setArbitrationUDFSelectionType(byte value) {
            this.arbitrationUDFSelectionType = value;
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

           
        public Process.BrmInfo getBrmInfo() {
            return brmInfo;
        }

           
        public void setBrmInfo(Process.BrmInfo value) {
            this.brmInfo = value;
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

        public String getDs2code() {
            return ds2code;
        }

        public void setDs2code(String ds2code) {
            this.ds2code = ds2code;
        }

           
        public Process.DatagridIdentifier getDatagridIdentifier() {
            return datagridIdentifier;
        }

           
        public void setDatagridIdentifier(Process.DatagridIdentifier value) {
            this.datagridIdentifier = value;
        }

           
        public byte getDatagridMaxRows() {
            return datagridMaxRows;
        }

           
        public void setDatagridMaxRows(byte value) {
            this.datagridMaxRows = value;
        }

           
        public String getDatagridName() {
            return datagridName;
        }

           
        public void setDatagridName(String value) {
            this.datagridName = value;
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

           
        public Process.Folder getFolder() {
            return folder;
        }

           
        public void setFolder(Process.Folder value) {
            this.folder = value;
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

           
        public Process.InputVariableList getInputVariableList() {
            return inputVariableList;
        }

           
        public void setInputVariableList(Process.InputVariableList value) {
            this.inputVariableList = value;
        }

           
        public String getLibName() {
            return libName;
        }

           
        public void setLibName(String value) {
            this.libName = value;
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

           
        public Process.MethodInfo getMethodInfo() {
            return methodInfo;
        }

           
        public void setMethodInfo(Process.MethodInfo value) {
            this.methodInfo = value;
        }

           
        public String getMigratedTo61WithMultipleBCs() {
            return migratedTo61WithMultipleBCs;
        }

           
        public void setMigratedTo61WithMultipleBCs(String value) {
            this.migratedTo61WithMultipleBCs = value;
        }

           
        public String getModelManagerPublishedModelName() {
            return modelManagerPublishedModelName;
        }

           
        public void setModelManagerPublishedModelName(String value) {
            this.modelManagerPublishedModelName = value;
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

           
        public Process.OutputVariableList getOutputVariableList() {
            return outputVariableList;
        }

           
        public void setOutputVariableList(Process.OutputVariableList value) {
            this.outputVariableList = value;
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

           
        public String getPhysicalActivityMethodName() {
            return physicalActivityMethodName;
        }

           
        public void setPhysicalActivityMethodName(String value) {
            this.physicalActivityMethodName = value;
        }

           
        public String getPhysicalActivityName() {
            return physicalActivityName;
        }

           
        public void setPhysicalActivityName(String value) {
            this.physicalActivityName = value;
        }

           
        public String getPhysicalName() {
            return physicalName;
        }

           
        public void setPhysicalName(String value) {
            this.physicalName = value;
        }

           
        public byte getProcessType() {
            return processType;
        }

           
        public void setProcessType(byte value) {
            this.processType = value;
        }

           
        public String getProcessTypeDescription() {
            return processTypeDescription;
        }

           
        public void setProcessTypeDescription(String value) {
            this.processTypeDescription = value;
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

           
        public String getReturnDatagridFlag() {
            return returnDatagridFlag;
        }

           
        public void setReturnDatagridFlag(String value) {
            this.returnDatagridFlag = value;
        }

           
        public byte getRuleSetCount() {
            return ruleSetCount;
        }

           
        public void setRuleSetCount(byte value) {
            this.ruleSetCount = value;
        }

           
        public String getShared() {
            return shared;
        }

           
        public void setShared(String value) {
            this.shared = value;
        }

           
        public Process.SharedIn getSharedIn() {
            return sharedIn;
        }

           
        public void setSharedIn(Process.SharedIn value) {
            this.sharedIn = value;
        }

           
        public String getTableName() {
            return tableName;
        }

           
        public void setTableName(String value) {
            this.tableName = value;
        }

           
        public String getTempProcess() {
            return tempProcess;
        }

           
        public void setTempProcess(String value) {
            this.tempProcess = value;
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

           
        public String getUsage() {
            return usage;
        }

           
        public void setUsage(String value) {
            this.usage = value;
        }

           
        public String getUseBIWebService() {
            return useBIWebService;
        }

           
        public void setUseBIWebService(String value) {
            this.useBIWebService = value;
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
                "value"
        })
        public static class ArbitrationUDFSelectionTags
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
        public static class BrmInfo
                implements Serializable {

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

               
            public String getCurrentDescription() {
                return currentDescription;
            }

               
            public void setCurrentDescription(String value) {
                this.currentDescription = value;
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
        public static class DatagridIdentifier
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

               
            public DatagridIdentifier.SharedIn getSharedIn() {
                return sharedIn;
            }

               
            public void setSharedIn(DatagridIdentifier.SharedIn value) {
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

               
            public Short getObjid() {
                return objid;
            }

               
            public void setObjid(Short value) {
                this.objid = value;
            }

        }


           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "ibVariableDOs"
        })
        public static class InputVariableList
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "IBVariableDO")
            protected List<InputVariableList.IBVariableDO> ibVariableDOs;
            @XmlAttribute(name = "EmptyList")
            protected String emptyList;

               
            public List<InputVariableList.IBVariableDO> getIBVariableDOs() {
                if (ibVariableDOs == null) {
                    ibVariableDOs = new ArrayList<InputVariableList.IBVariableDO>();
                }
                return this.ibVariableDOs;
            }

               
            public String getEmptyList() {
                return emptyList;
            }

               
            public void setEmptyList(String value) {
                this.emptyList = value;
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

                   
                public IBVariableDO.Value getValue() {
                    return value;
                }

                   
                public void setValue(IBVariableDO.Value value) {
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

                       
                    public Identifier.SharedIn getSharedIn() {
                        return sharedIn;
                    }

                       
                    public void setSharedIn(Identifier.SharedIn value) {
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
                        "listValue",
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

                    public void setStringValue(String stringValue) {
                        this.stringValue = stringValue;
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

                    public String getListValue() {
                        return listValue;
                    }

                    public void setListValue(String listValue) {
                        this.listValue = listValue;
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
        public static class MethodInfo
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

               
            public String getModelDescription() {
                return modelDescription;
            }

               
            public void setModelDescription(String value) {
                this.modelDescription = value;
            }

               
            public String getModelManagerModelModifiedUser() {
                return modelManagerModelModifiedUser;
            }

               
            public void setModelManagerModelModifiedUser(String value) {
                this.modelManagerModelModifiedUser = value;
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

               
            public String getReportModelName() {
                return reportModelName;
            }

               
            public void setReportModelName(String value) {
                this.reportModelName = value;
            }

               
            public String getReportModelVersion() {
                return reportModelVersion;
            }

               
            public void setReportModelVersion(String value) {
                this.reportModelVersion = value;
            }

               
            public String getScoreCode() {
                return scoreCode;
            }

               
            public void setScoreCode(String value) {
                this.scoreCode = value;
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
                "ibVariableDOs"
        })
        public static class OutputVariableList
                implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "IBVariableDO")
            protected List<OutputVariableList.IBVariableDO> ibVariableDOs;

               
            public List<OutputVariableList.IBVariableDO> getIBVariableDOs() {
                if (ibVariableDOs == null) {
                    ibVariableDOs = new ArrayList<OutputVariableList.IBVariableDO>();
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

                   
                public com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO.SharedIn getSharedIn() {
                    return sharedIn;
                }

                   
                public void setSharedIn(com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO.SharedIn value) {
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

                   
                public Short getListpos() {
                    return listpos;
                }

                   
                public void setListpos(Short value) {
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

}