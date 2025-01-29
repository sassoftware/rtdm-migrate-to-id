package com.sas.rtdm2id.model.rtdm;

import com.sas.rtdm2id.model.rtdm.extension.*;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
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

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "ibVariableDO"
    })
    @Getter
    @Setter
    public static class SubjectVariables implements Serializable {

        private final static long serialVersionUID = -1L;

        @XmlElement(name = "IBVariableDO")
        private List<IBVariableDO> ibVariableDO = new ArrayList<>();

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "audienceDO"
    })
    @Getter
    @Setter
    public static class StagingAudienceList implements Serializable {

        private final static long serialVersionUID = -1L;

        @XmlElement(name = "AudienceDO")
        private StagedTreatmentsNodeDataDO.AudienceDO audienceDO;

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
    @Getter
    @Setter
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

        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "value"
        })
        @Getter
        @Setter
        public static class RemoveStagedTreatmentsChannelCds implements Serializable {

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
        public static class RemoveStagedTreatmentsChannels implements Serializable {

            private final static long serialVersionUID = -1L;

            @XmlValue
            protected String value;
            @XmlAttribute(name = "EmptyList")
            protected String emptyList;

        }

    }


}
