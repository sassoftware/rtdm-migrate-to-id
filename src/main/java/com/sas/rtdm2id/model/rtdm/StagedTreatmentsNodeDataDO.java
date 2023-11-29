package com.sas.rtdm2id.model.rtdm;

import com.sas.rtdm2id.model.rtdm.extension.*;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "archivedPublishState",
        "channelCd",
        "channelCodesUpdated",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "controlKey",
        "dateModified",
        "droppedCount",
        "excludeFlag",
        "excludeIconPath",
        "firstOccurrenceToPublish",
        "iconHtmlPath",
        "iconPath",
        "inputNodes",
        "label",
        "loadedFromOldDavLocation",
        "loading",
        "needToPersist",
        "nodeId",
        "nodeName",
        "nodeState",
        "nodeTags",
        "nodeType",
        "oldVersionNumber",
        "outputNodes",
        "persisting",
        "publishState",
        "refreshNodeDataDO",
        "sortByList",
        "splitters",
        "stagingAudienceList",
        "subjectID",
        "subjectVariables",
        "totalCount",
        "validInputSubject",
        "validOutputSubject",
        "versionNumber",
        "x",
        "y",
        "systemExtraValues"
})
public class StagedTreatmentsNodeDataDO implements Serializable {

    private final static long serialVersionUID = -1L;

    @XmlElement(name = "ArchivedPublishState")
    protected byte archivedPublishState;
    @XmlElement(name = "ChannelCd")
    protected String channelCd;
    @XmlElement(name = "ChannelCodesUpdated", required = true)
    protected String channelCodesUpdated;
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
    @XmlElement(name = "ExcludeFlag", required = true)
    protected String excludeFlag;
    @XmlElement(name = "ExcludeIconPath", required = true)
    protected String excludeIconPath;
    @XmlElement(name = "FirstOccurrenceToPublish")
    protected byte firstOccurrenceToPublish;
    @XmlElement(name = "IconHtmlPath", required = true)
    protected String iconHtmlPath;
    @XmlElement(name = "IconPath", required = true)
    protected String iconPath;
    @XmlElement(name = "InputNodes", required = true)
    protected InputNodes inputNodes;
    @XmlElement(name = "Label", required = true)
    protected String label;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    protected String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    protected String loading;
    @XmlElement(name = "NeedToPersist", required = true)
    protected String needToPersist;
    @XmlElement(name = "NodeId", required = true)
    protected String nodeId;
    @XmlElement(name = "NodeName", required = true)
    protected String nodeName;
    @XmlElement(name = "NodeState")
    protected byte nodeState;
    @XmlElement(name = "NodeTags", required = true)
    protected NodeTags nodeTags;
    @XmlElement(name = "NodeType")
    protected byte nodeType;
    @XmlElement(name = "OldVersionNumber", required = true)
    protected String oldVersionNumber;
    @XmlElement(name = "OutputNodes", required = true)
    protected OutputNodes outputNodes;
    @XmlElement(name = "Persisting", required = true)
    protected String persisting;
    @XmlElement(name = "PublishState")
    protected byte publishState;
    @XmlElement(name = "RefreshNodeDataDO")
    private String refreshNodeDataDO;
    @XmlElement(name = "SortByList", required = true)
    protected SortByList sortByList;
    @XmlElement(name = "Splitters", required = true)
    protected Splitters splitters;
    @XmlElement(name = "StagingAudienceList", required = true)
    protected StagedTreatmentsNodeDataDO.StagingAudienceList stagingAudienceList;
    @XmlElement(name = "SubjectID", required = true)
    protected String subjectID;
    @XmlElement(name = "SubjectVariables", required = true)
    protected StagedTreatmentsNodeDataDO.SubjectVariables subjectVariables;
    @XmlElement(name = "TotalCount")
    protected byte totalCount;
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
    protected SystemExtraValues systemExtraValues;
    @XmlAttribute(name = "objid")
    protected Short objid;

    public byte getArchivedPublishState() {
        return archivedPublishState;
    }

    public void setArchivedPublishState(byte archivedPublishState) {
        this.archivedPublishState = archivedPublishState;
    }

    public String getChannelCd() {
        return channelCd;
    }

    public void setChannelCd(String channelCd) {
        this.channelCd = channelCd;
    }

    public String getChannelCodesUpdated() {
        return channelCodesUpdated;
    }

    public void setChannelCodesUpdated(String channelCodesUpdated) {
        this.channelCodesUpdated = channelCodesUpdated;
    }

    public String getCodeChangeUnpublished() {
        return codeChangeUnpublished;
    }

    public void setCodeChangeUnpublished(String codeChangeUnpublished) {
        this.codeChangeUnpublished = codeChangeUnpublished;
    }

    public String getCodeEverBeenPublished() {
        return codeEverBeenPublished;
    }

    public void setCodeEverBeenPublished(String codeEverBeenPublished) {
        this.codeEverBeenPublished = codeEverBeenPublished;
    }

    public byte getControlKey() {
        return controlKey;
    }

    public void setControlKey(byte controlKey) {
        this.controlKey = controlKey;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public byte getDroppedCount() {
        return droppedCount;
    }

    public void setDroppedCount(byte droppedCount) {
        this.droppedCount = droppedCount;
    }

    public String getExcludeFlag() {
        return excludeFlag;
    }

    public void setExcludeFlag(String excludeFlag) {
        this.excludeFlag = excludeFlag;
    }

    public String getExcludeIconPath() {
        return excludeIconPath;
    }

    public void setExcludeIconPath(String excludeIconPath) {
        this.excludeIconPath = excludeIconPath;
    }

    public byte getFirstOccurrenceToPublish() {
        return firstOccurrenceToPublish;
    }

    public void setFirstOccurrenceToPublish(byte firstOccurrenceToPublish) {
        this.firstOccurrenceToPublish = firstOccurrenceToPublish;
    }

    public String getIconHtmlPath() {
        return iconHtmlPath;
    }

    public void setIconHtmlPath(String iconHtmlPath) {
        this.iconHtmlPath = iconHtmlPath;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public InputNodes getInputNodes() {
        return inputNodes;
    }

    public void setInputNodes(InputNodes inputNodes) {
        this.inputNodes = inputNodes;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLoadedFromOldDavLocation() {
        return loadedFromOldDavLocation;
    }

    public void setLoadedFromOldDavLocation(String loadedFromOldDavLocation) {
        this.loadedFromOldDavLocation = loadedFromOldDavLocation;
    }

    public String getLoading() {
        return loading;
    }

    public void setLoading(String loading) {
        this.loading = loading;
    }

    public String getNeedToPersist() {
        return needToPersist;
    }

    public void setNeedToPersist(String needToPersist) {
        this.needToPersist = needToPersist;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public byte getNodeState() {
        return nodeState;
    }

    public void setNodeState(byte nodeState) {
        this.nodeState = nodeState;
    }

    public NodeTags getNodeTags() {
        return nodeTags;
    }

    public void setNodeTags(NodeTags nodeTags) {
        this.nodeTags = nodeTags;
    }

    public byte getNodeType() {
        return nodeType;
    }

    public void setNodeType(byte nodeType) {
        this.nodeType = nodeType;
    }

    public String getOldVersionNumber() {
        return oldVersionNumber;
    }

    public void setOldVersionNumber(String oldVersionNumber) {
        this.oldVersionNumber = oldVersionNumber;
    }

    public OutputNodes getOutputNodes() {
        return outputNodes;
    }

    public void setOutputNodes(OutputNodes outputNodes) {
        this.outputNodes = outputNodes;
    }

    public String getPersisting() {
        return persisting;
    }

    public void setPersisting(String persisting) {
        this.persisting = persisting;
    }

    public byte getPublishState() {
        return publishState;
    }

    public void setPublishState(byte publishState) {
        this.publishState = publishState;
    }

    public String getRefreshNodeDataDO() {
        return refreshNodeDataDO;
    }

    public void setRefreshNodeDataDO(String refreshNodeDataDO) {
        this.refreshNodeDataDO = refreshNodeDataDO;
    }

    public SortByList getSortByList() {
        return sortByList;
    }

    public void setSortByList(SortByList sortByList) {
        this.sortByList = sortByList;
    }

    public Splitters getSplitters() {
        return splitters;
    }

    public void setSplitters(Splitters splitters) {
        this.splitters = splitters;
    }

    public StagingAudienceList getStagingAudienceList() {
        return stagingAudienceList;
    }

    public void setStagingAudienceList(StagingAudienceList stagingAudienceList) {
        this.stagingAudienceList = stagingAudienceList;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public SubjectVariables getSubjectVariables() {
        return subjectVariables;
    }

    public void setSubjectVariables(SubjectVariables subjectVariables) {
        this.subjectVariables = subjectVariables;
    }

    public byte getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(byte totalCount) {
        this.totalCount = totalCount;
    }

    public String getValidInputSubject() {
        return validInputSubject;
    }

    public void setValidInputSubject(String validInputSubject) {
        this.validInputSubject = validInputSubject;
    }

    public String getValidOutputSubject() {
        return validOutputSubject;
    }

    public void setValidOutputSubject(String validOutputSubject) {
        this.validOutputSubject = validOutputSubject;
    }

    public float getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(float versionNumber) {
        this.versionNumber = versionNumber;
    }

    public short getX() {
        return x;
    }

    public void setX(short x) {
        this.x = x;
    }

    public short getY() {
        return y;
    }

    public void setY(short y) {
        this.y = y;
    }

    public SystemExtraValues getSystemExtraValues() {
        return systemExtraValues;
    }

    public void setSystemExtraValues(SystemExtraValues systemExtraValues) {
        this.systemExtraValues = systemExtraValues;
    }

    public Short getObjid() {
        return objid;
    }

    public void setObjid(Short objid) {
        this.objid = objid;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "ibVariableDO"
    })
    public static class SubjectVariables implements Serializable {

        private final static long serialVersionUID = -1L;

        @XmlElement(name = "IBVariableDO")
        private List<IBVariableDO> ibVariableDO = new ArrayList<>();

        public List<IBVariableDO> getIbVariableDO() {
            return ibVariableDO;
        }

        public void setIbVariableDO(List<IBVariableDO> ibVariableDO) {
            this.ibVariableDO = ibVariableDO;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "audienceDO"
    })
    public static class StagingAudienceList implements Serializable {

        private final static long serialVersionUID = -1L;

        @XmlElement(name = "AudienceDO")
        private StagedTreatmentsNodeDataDO.AudienceDO audienceDO;

        public AudienceDO getAudienceDO() {
            return audienceDO;
        }

        public void setAudienceDO(AudienceDO audienceDO) {
            this.audienceDO = audienceDO;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "abTestEnabled",
            "archivedPublishState",
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
            "removeStagedTreatmentsChannelCds",
            "removeStagedTreatmentsChannels",
            "removeStagedTreatmentsMethod",
            "removeStaging",
            "skipDedupe",
            "stagingPriority",
            "totalCount",
            "versionNumber"
    })
    public static class AudienceDO implements Serializable {

        private final static long serialVersionUID = -1L;

        @XmlElement(name = "AbTestEnabled")
        protected String abTestEnabled;
        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;
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
        @XmlElement(name = "removeStagedTreatmentsChannelCds", required = true)
        protected AudienceDO.RemoveStagedTreatmentsChannelCds removeStagedTreatmentsChannelCds;
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

        public String getAbTestEnabled() {
            return abTestEnabled;
        }

        public void setAbTestEnabled(String abTestEnabled) {
            this.abTestEnabled = abTestEnabled;
        }

        public byte getArchivedPublishState() {
            return archivedPublishState;
        }

        public void setArchivedPublishState(byte archivedPublishState) {
            this.archivedPublishState = archivedPublishState;
        }

        public String getCellCode() {
            return cellCode;
        }

        public void setCellCode(String cellCode) {
            this.cellCode = cellCode;
        }

        public String getCodeChangeUnpublished() {
            return codeChangeUnpublished;
        }

        public void setCodeChangeUnpublished(String codeChangeUnpublished) {
            this.codeChangeUnpublished = codeChangeUnpublished;
        }

        public String getCodeEverBeenPublished() {
            return codeEverBeenPublished;
        }

        public void setCodeEverBeenPublished(String codeEverBeenPublished) {
            this.codeEverBeenPublished = codeEverBeenPublished;
        }

        public String getControlGroupType() {
            return controlGroupType;
        }

        public void setControlGroupType(String controlGroupType) {
            this.controlGroupType = controlGroupType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public byte getFirstOccurrenceToPublish() {
            return firstOccurrenceToPublish;
        }

        public void setFirstOccurrenceToPublish(byte firstOccurrenceToPublish) {
            this.firstOccurrenceToPublish = firstOccurrenceToPublish;
        }

        public String getForced() {
            return forced;
        }

        public void setForced(String forced) {
            this.forced = forced;
        }

        public String getHidden() {
            return hidden;
        }

        public void setHidden(String hidden) {
            this.hidden = hidden;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLoadedFromOldDavLocation() {
            return loadedFromOldDavLocation;
        }

        public void setLoadedFromOldDavLocation(String loadedFromOldDavLocation) {
            this.loadedFromOldDavLocation = loadedFromOldDavLocation;
        }

        public String getLoading() {
            return loading;
        }

        public void setLoading(String loading) {
            this.loading = loading;
        }

        public String getLockedBy() {
            return lockedBy;
        }

        public void setLockedBy(String lockedBy) {
            this.lockedBy = lockedBy;
        }

        public String getMetadataType() {
            return metadataType;
        }

        public void setMetadataType(String metadataType) {
            this.metadataType = metadataType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNeedToPersist() {
            return needToPersist;
        }

        public void setNeedToPersist(String needToPersist) {
            this.needToPersist = needToPersist;
        }

        public String getNoWritePermission() {
            return noWritePermission;
        }

        public void setNoWritePermission(String noWritePermission) {
            this.noWritePermission = noWritePermission;
        }

        public String getNodeID() {
            return nodeID;
        }

        public void setNodeID(String nodeID) {
            this.nodeID = nodeID;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public String getOldVersionNumber() {
            return oldVersionNumber;
        }

        public void setOldVersionNumber(String oldVersionNumber) {
            this.oldVersionNumber = oldVersionNumber;
        }

        public PackageDO getPackageDO() {
            return packageDO;
        }

        public void setPackageDO(PackageDO packageDO) {
            this.packageDO = packageDO;
        }

        public String getPersisting() {
            return persisting;
        }

        public void setPersisting(String persisting) {
            this.persisting = persisting;
        }

        public byte getPublishState() {
            return publishState;
        }

        public void setPublishState(byte publishState) {
            this.publishState = publishState;
        }

        public String getReadOnly() {
            return readOnly;
        }

        public void setReadOnly(String readOnly) {
            this.readOnly = readOnly;
        }

        public RemoveStagedTreatmentsChannelCds getRemoveStagedTreatmentsChannelCds() {
            return removeStagedTreatmentsChannelCds;
        }

        public void setRemoveStagedTreatmentsChannelCds(RemoveStagedTreatmentsChannelCds removeStagedTreatmentsChannelCds) {
            this.removeStagedTreatmentsChannelCds = removeStagedTreatmentsChannelCds;
        }

        public RemoveStagedTreatmentsChannels getRemoveStagedTreatmentsChannels() {
            return removeStagedTreatmentsChannels;
        }

        public void setRemoveStagedTreatmentsChannels(RemoveStagedTreatmentsChannels removeStagedTreatmentsChannels) {
            this.removeStagedTreatmentsChannels = removeStagedTreatmentsChannels;
        }

        public byte getRemoveStagedTreatmentsMethod() {
            return removeStagedTreatmentsMethod;
        }

        public void setRemoveStagedTreatmentsMethod(byte removeStagedTreatmentsMethod) {
            this.removeStagedTreatmentsMethod = removeStagedTreatmentsMethod;
        }

        public String getRemoveStaging() {
            return removeStaging;
        }

        public void setRemoveStaging(String removeStaging) {
            this.removeStaging = removeStaging;
        }

        public String getSkipDedupe() {
            return skipDedupe;
        }

        public void setSkipDedupe(String skipDedupe) {
            this.skipDedupe = skipDedupe;
        }

        public byte getStagingPriority() {
            return stagingPriority;
        }

        public void setStagingPriority(byte stagingPriority) {
            this.stagingPriority = stagingPriority;
        }

        public byte getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(byte totalCount) {
            this.totalCount = totalCount;
        }

        public float getVersionNumber() {
            return versionNumber;
        }

        public void setVersionNumber(float versionNumber) {
            this.versionNumber = versionNumber;
        }

        public String getConcreteClass() {
            return concreteClass;
        }

        public void setConcreteClass(String concreteClass) {
            this.concreteClass = concreteClass;
        }

        public Byte getListpos() {
            return listpos;
        }

        public void setListpos(Byte listpos) {
            this.listpos = listpos;
        }

        public Short getObjid() {
            return objid;
        }

        public void setObjid(Short objid) {
            this.objid = objid;
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
                "persisting",
                "publishState",
                "versionNumber"
        })
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
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "SurrogateKeyNbr")
            protected float versionNumber;
            @XmlAttribute(name = "ConcreteClass")
            protected String concreteClass;
            @XmlAttribute(name = "objid")
            protected Short objid;

            public byte getArchivedPublishState() {
                return archivedPublishState;
            }

            public void setArchivedPublishState(byte archivedPublishState) {
                this.archivedPublishState = archivedPublishState;
            }

            public String getCodeChangeUnpublished() {
                return codeChangeUnpublished;
            }

            public void setCodeChangeUnpublished(String codeChangeUnpublished) {
                this.codeChangeUnpublished = codeChangeUnpublished;
            }

            public String getCodeEverBeenPublished() {
                return codeEverBeenPublished;
            }

            public void setCodeEverBeenPublished(String codeEverBeenPublished) {
                this.codeEverBeenPublished = codeEverBeenPublished;
            }

            public String getExecPublished() {
                return execPublished;
            }

            public void setExecPublished(String execPublished) {
                this.execPublished = execPublished;
            }

            public byte getFirstOccurrenceToPublish() {
                return firstOccurrenceToPublish;
            }

            public void setFirstOccurrenceToPublish(byte firstOccurrenceToPublish) {
                this.firstOccurrenceToPublish = firstOccurrenceToPublish;
            }

            public String getLoadedFromOldDavLocation() {
                return loadedFromOldDavLocation;
            }

            public void setLoadedFromOldDavLocation(String loadedFromOldDavLocation) {
                this.loadedFromOldDavLocation = loadedFromOldDavLocation;
            }

            public String getLoading() {
                return loading;
            }

            public void setLoading(String loading) {
                this.loading = loading;
            }

            public byte getMandatoryForOptimization() {
                return mandatoryForOptimization;
            }

            public void setMandatoryForOptimization(byte mandatoryForOptimization) {
                this.mandatoryForOptimization = mandatoryForOptimization;
            }

            public byte getMandatoryOptExcludeConstraints() {
                return mandatoryOptExcludeConstraints;
            }

            public void setMandatoryOptExcludeConstraints(byte mandatoryOptExcludeConstraints) {
                this.mandatoryOptExcludeConstraints = mandatoryOptExcludeConstraints;
            }

            public byte getMandatoryOptExcludeContactPolicy() {
                return mandatoryOptExcludeContactPolicy;
            }

            public void setMandatoryOptExcludeContactPolicy(byte mandatoryOptExcludeContactPolicy) {
                this.mandatoryOptExcludeContactPolicy = mandatoryOptExcludeContactPolicy;
            }

            public String getNeedToPersist() {
                return needToPersist;
            }

            public void setNeedToPersist(String needToPersist) {
                this.needToPersist = needToPersist;
            }

            public String getOldVersionNumber() {
                return oldVersionNumber;
            }

            public void setOldVersionNumber(String oldVersionNumber) {
                this.oldVersionNumber = oldVersionNumber;
            }

            public byte getOptimizationPriority() {
                return optimizationPriority;
            }

            public void setOptimizationPriority(byte optimizationPriority) {
                this.optimizationPriority = optimizationPriority;
            }

            public String getPersisting() {
                return persisting;
            }

            public void setPersisting(String persisting) {
                this.persisting = persisting;
            }

            public byte getPublishState() {
                return publishState;
            }

            public void setPublishState(byte publishState) {
                this.publishState = publishState;
            }

            public float getVersionNumber() {
                return versionNumber;
            }

            public void setVersionNumber(float versionNumber) {
                this.versionNumber = versionNumber;
            }

            public String getConcreteClass() {
                return concreteClass;
            }

            public void setConcreteClass(String concreteClass) {
                this.concreteClass = concreteClass;
            }

            public Short getObjid() {
                return objid;
            }

            public void setObjid(Short objid) {
                this.objid = objid;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        public static class RemoveStagedTreatmentsChannelCds implements Serializable {

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

            public void setEmptyList(String emptyList) {
                this.emptyList = emptyList;
            }

        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        public static class RemoveStagedTreatmentsChannels implements Serializable {

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

            public void setEmptyList(String emptyList) {
                this.emptyList = emptyList;
            }
        }

    }


}
