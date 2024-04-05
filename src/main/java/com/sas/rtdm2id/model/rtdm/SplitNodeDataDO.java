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

import com.sas.rtdm2id.model.rtdm.extension.DiscreteLineItemDO;
import com.sas.rtdm2id.model.rtdm.extension.IntervalLineItemDO;
import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "archivedPublishState",
        "assignmentFunction",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "controlGroupCreated",
        "controlGroupPercent",
        "controlKey",
        "dateModified",
        "droppedCount",
        "enableSeed",
        "excludeFlag",
        "excludeIconPath",
        "firstOccurrenceToPublish",
        "haveSplitCriteria",
        "iconPath",
        "initialNodeDataUpdateDone",
        "inputNodes",
        "label",
        "limitGroups",
        "lineItems",
        "loadedFromOldDavLocation",
        "loading",
        "maxGroups",
        "needToPersist",
        "nodeDescription",
        "nodeId",
        "nodeName",
        "nodeState",
        "nodeTags",
        "nodeType",
        "numGroups",
        "oldVersionNumber",
        "outputCellsAvailableForLinking",
        "outputNodes",
        "persisting",
        "publishState",
        "readyToUpdateCounts",
        "rebuildCells",
        "seedVarRefDO",
        "sortByList",
        "splitCriteriaList",
        "splitOnNodeDataDO",
        "splitOnNodeID",
        "splitType",
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
@Getter
@Setter
public class SplitNodeDataDO implements Serializable {

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ArchivedPublishState")
    protected byte archivedPublishState;
    @XmlElement(name = "AssignmentFunction", required = true)
    protected String assignmentFunction;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    protected String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    protected String codeEverBeenPublished;
    @XmlElement(name = "ControlGroupCreated", required = true)
    protected String controlGroupCreated;
    @XmlElement(name = "ControlGroupPercent", required = true)
    protected String controlGroupPercent;
    @XmlElement(name = "ControlKey")
    protected int controlKey;
    @XmlElement(name = "DateModified", required = true)
    protected String dateModified;
    @XmlElement(name = "DroppedCount")
    protected byte droppedCount;
    @XmlElement(name = "EnableSeed", required = true)
    protected String enableSeed;
    @XmlElement(name = "ExcludeFlag", required = true)
    protected String excludeFlag;
    @XmlElement(name = "ExcludeIconPath", required = true)
    protected String excludeIconPath;
    @XmlElement(name = "FirstOccurrenceToPublish")
    protected byte firstOccurrenceToPublish;
    @XmlElement(name = "HaveSplitCriteria", required = true)
    protected String haveSplitCriteria;
    @XmlElement(name = "IconPath", required = true)
    protected String iconPath;
    @XmlElement(name = "InitialNodeDataUpdateDone", required = true)
    protected String initialNodeDataUpdateDone;
    @XmlElement(name = "InputNodes", required = true)
    protected SplitNodeDataDO.InputNodes inputNodes;
    @XmlElement(name = "Label", required = true)
    protected String label;
    @XmlElement(name = "LimitGroups", required = true)
    protected String limitGroups;
    @XmlElement(name = "LineItems", required = true)
    protected SplitNodeDataDO.LineItems lineItems;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    protected String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    protected String loading;
    @XmlElement(name = "MaxGroups")
    protected byte maxGroups;
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
    protected SplitNodeDataDO.NodeTags nodeTags;
    @XmlElement(name = "NodeType")
    protected byte nodeType;
    @XmlElement(name = "NumGroups")
    protected byte numGroups;
    @XmlElement(name = "OldVersionNumber", required = true)
    protected String oldVersionNumber;
    @XmlElement(name = "OutputCellsAvailableForLinking", required = true)
    protected String outputCellsAvailableForLinking;
    @XmlElement(name = "OutputNodes", required = true)
    protected SplitNodeDataDO.OutputNodes outputNodes;
    @XmlElement(name = "Persisting", required = true)
    protected String persisting;
    @XmlElement(name = "PublishState")
    protected byte publishState;
    @XmlElement(name = "ReadyToUpdateCounts", required = true)
    protected String readyToUpdateCounts;
    @XmlElement(name = "RebuildCells", required = true)
    protected String rebuildCells;
    @XmlElement(name = "SortByList", required = true)
    protected SplitNodeDataDO.SortByList sortByList;
    @XmlElement(name = "SeedVarRefDO")
    protected SplitOnNodeDataDO.VarRefDO seedVarRefDO;
    @XmlElement(name = "SplitCriteriaList", required = true)
    protected SplitNodeDataDO.SplitCriteriaList splitCriteriaList;
    @XmlElement(name = "SplitOnNodeDataDO", required = true)
    protected SplitNodeDataDO.SplitOnNodeDataDO splitOnNodeDataDO;
    @XmlElement(name = "SplitOnNodeID", required = true)
    protected String splitOnNodeID;
    @XmlElement(name = "SplitType", required = true)
    protected String splitType;
    @XmlElement(name = "Splitters", required = true)
    protected SplitNodeDataDO.Splitters splitters;
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
    protected short x;
    @XmlElement(name = "Y")
    protected short y;
    @XmlElement(name = "SystemExtraValues", required = true)
    protected SplitNodeDataDO.SystemExtraValues systemExtraValues;
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
            "splitNodeLineItemDOs"
    })
    @Getter
    public static class LineItems implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "SplitNodeLineItemDO")
        protected List<LineItems.SplitNodeLineItemDO> splitNodeLineItemDOs = new ArrayList<>();
           
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "actualCount",
                "actualPercent",
                "archivedPublishState",
                "cellCode",
                "cellId",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "controlGroup",
                "firstOccurrenceToPublish",
                "groupName",
                "lineItemId",
                "loadedFromOldDavLocation",
                "loading",
                "needToPersist",
                "oldVersionNumber",
                "persisting",
                "priority",
                "publishState",
                "remainder",
                "requiredCount",
                "requiredPercent",
                "uniqueCount",
                "value",
                "versionNumber"
        })
        @Getter
        @Setter
        public static class SplitNodeLineItemDO implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ActualCount")
            protected byte actualCount;
            @XmlElement(name = "ActualPercent")
            protected float actualPercent;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "CellCode", required = true)
            protected String cellCode;
            @XmlElement(name = "CellId", required = true)
            protected String cellId;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
            @XmlElement(name = "ControlGroup", required = true)
            protected String controlGroup;
            @XmlElement(name = "FirstOccurrenceToPublish")
            protected byte firstOccurrenceToPublish;
            @XmlElement(name = "GroupName", required = true)
            protected String groupName;
            @XmlElement(name = "LineItemId", required = true)
            protected String lineItemId;
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
            @XmlElement(name = "Priority")
            protected byte priority;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "Remainder", required = true)
            protected String remainder;
            @XmlElement(name = "RequiredCount", required = true)
            protected SplitNodeLineItemDO.RequiredCount requiredCount;
            @XmlElement(name = "RequiredPercent", required = true)
            protected SplitNodeLineItemDO.RequiredPercent requiredPercent;
            @XmlElement(name = "UniqueCount")
            protected byte uniqueCount;
            @XmlElement(name = "Value", required = true)
            protected String value;
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
            public static class RequiredCount implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlValue
                protected String value;
                @XmlAttribute(name = "StoredType")
                protected String storedType;

            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "value"
            })
            @Getter
            @Setter
            public static class RequiredPercent implements Serializable {

                private final static long serialVersionUID = -1L;
                @XmlValue
                protected String value;
                @XmlAttribute(name = "StoredType")
                protected String storedType;

            }

        }

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
    public static class SplitCriteriaList implements Serializable {

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
            "controlKey",
            "createMissingGroup",
            "dateType",
            "discreteVariable",
            "distanceCriteria",
            "droppedCount",
            "excludeFlag",
            "excludeIconPath",
            "firstOccurrenceToPublish",
            "hasNoMetaData",
            "iconPath",
            "label",
            "lineItems",
            "loadedFromOldDavLocation",
            "loading",
            "needToPersist",
            "needToSaveLineItems",
            "nodeId",
            "nodeName",
            "nodeState",
            "nodeTags",
            "nodeType",
            "numberOfGroups",
            "oldVersionNumber",
            "outOfSynch",
            "pattern",
            "persisting",
            "publishState",
            "selectedItemsCache",
            "selectedRule",
            "selectionMethod",
            "sortByList",
            "splitters",
            "subjectID",
            "totalCount",
            "validInputSubject",
            "validOutputSubject",
            "varInfoId",
            "varRefDO",
            "versionNumber",
            "wildCardUseAvailable",
            "x",
            "y"
    })
    @Getter
    @Setter
    public static class SplitOnNodeDataDO implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;
        @XmlElement(name = "CodeChangeUnpublished", required = true)
        protected String codeChangeUnpublished;
        @XmlElement(name = "CodeEverBeenPublished", required = true)
        protected String codeEverBeenPublished;
        @XmlElement(name = "ControlKey")
        protected byte controlKey;
        @XmlElement(name = "CreateMissingGroup", required = true)
        protected String createMissingGroup;
        @XmlElement(name = "DateType", required = true)
        protected String dateType;
        @XmlElement(name = "DiscreteVariable", required = true)
        protected String discreteVariable;
        @XmlElement(name = "DistanceCriteria", required = true)
        protected String distanceCriteria;
        @XmlElement(name = "DroppedCount")
        protected byte droppedCount;
        @XmlElement(name = "ExcludeFlag", required = true)
        protected String excludeFlag;
        @XmlElement(name = "ExcludeIconPath", required = true)
        protected String excludeIconPath;
        @XmlElement(name = "FirstOccurrenceToPublish")
        protected byte firstOccurrenceToPublish;
        @XmlElement(name = "HasNoMetaData", required = true)
        protected String hasNoMetaData;
        @XmlElement(name = "IconPath", required = true)
        protected String iconPath;
        @XmlElement(name = "Label", required = true)
        protected String label;
        @XmlElement(name = "LineItems", required = true)
        protected SplitOnNodeDataDO.LineItems lineItems;
        @XmlElement(name = "LoadedFromOldDavLocation", required = true)
        protected String loadedFromOldDavLocation;
        @XmlElement(name = "Loading", required = true)
        protected String loading;
        @XmlElement(name = "NeedToPersist", required = true)
        protected String needToPersist;
        @XmlElement(name = "NeedToSaveLineItems", required = true)
        protected String needToSaveLineItems;
        @XmlElement(name = "NodeId", required = true)
        protected String nodeId;
        @XmlElement(name = "NodeName", required = true)
        protected String nodeName;
        @XmlElement(name = "NodeState")
        protected byte nodeState;
        @XmlElement(name = "NodeTags", required = true)
        protected SplitOnNodeDataDO.NodeTags nodeTags;
        @XmlElement(name = "NodeType")
        protected byte nodeType;
        @XmlElement(name = "NumberOfGroups")
        protected byte numberOfGroups;
        @XmlElement(name = "OldVersionNumber", required = true)
        protected String oldVersionNumber;
        @XmlElement(name = "OutOfSynch", required = true)
        protected String outOfSynch;
        @XmlElement(name = "Pattern", required = true)
        protected String pattern;
        @XmlElement(name = "Persisting", required = true)
        protected String persisting;
        @XmlElement(name = "PublishState")
        protected byte publishState;
        @XmlElement(name = "SelectedItemsCache", required = true)
        protected SplitOnNodeDataDO.SelectedItemsCache selectedItemsCache;
        @XmlElement(name = "SelectedRule", required = true)
        protected String selectedRule;
        @XmlElement(name = "SelectionMethod", required = true)
        protected String selectionMethod;
        @XmlElement(name = "SortByList", required = true)
        protected SplitOnNodeDataDO.SortByList sortByList;
        @XmlElement(name = "Splitters", required = true)
        protected SplitOnNodeDataDO.Splitters splitters;
        @XmlElement(name = "SubjectID", required = true)
        protected String subjectID;
        @XmlElement(name = "TotalCount")
        protected byte totalCount;
        @XmlElement(name = "ValidInputSubject", required = true)
        protected String validInputSubject;
        @XmlElement(name = "ValidOutputSubject", required = true)
        protected String validOutputSubject;
        @XmlElement(name = "VarInfoId", required = true)
        protected String varInfoId;
        @XmlElement(name = "VarRefDO", required = true)
        protected SplitOnNodeDataDO.VarRefDO varRefDO;
        @XmlElement(name = "VersionNumber")
        protected float versionNumber;
        @XmlElement(name = "WildCardUseAvailable", required = true)
        protected String wildCardUseAvailable;
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
                "intervalLineItemDOs",
                "discreteLineItemDOs"
        })
        @Getter
        @Setter
        public static class LineItems implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "IntervalLineItemDO")
            protected List<IntervalLineItemDO> intervalLineItemDOs = new ArrayList<>();
            @XmlElement(name = "DiscreteLineItemDO")
            protected List<DiscreteLineItemDO> discreteLineItemDOs = new ArrayList<>();

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
        public static class SelectedItemsCache implements Serializable {

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
                "archivedPublishState",
                "codeChangeUnpublished",
                "codeEverBeenPublished",
                "discrete",
                "firstOccurrenceToPublish",
                "loadedFromOldDavLocation",
                "loading",
                "needToPersist",
                "noMetadata",
                "oldVersionNumber",
                "persisting",
                "publishState",
                "sasFormat",
                "subType",
                "type",
                "valid",
                "varInfoId",
                "varName",
                "versionNumber"
        })
        @Getter
        @Setter
        public static class VarRefDO implements Serializable {

            private final static long serialVersionUID = -1L;
            @XmlElement(name = "ArchivedPublishState")
            protected byte archivedPublishState;
            @XmlElement(name = "CodeChangeUnpublished", required = true)
            protected String codeChangeUnpublished;
            @XmlElement(name = "CodeEverBeenPublished", required = true)
            protected String codeEverBeenPublished;
            @XmlElement(name = "Discrete", required = true)
            protected String discrete;
            @XmlElement(name = "FirstOccurrenceToPublish")
            protected byte firstOccurrenceToPublish;
            @XmlElement(name = "LoadedFromOldDavLocation", required = true)
            protected String loadedFromOldDavLocation;
            @XmlElement(name = "Loading", required = true)
            protected String loading;
            @XmlElement(name = "NeedToPersist", required = true)
            protected String needToPersist;
            @XmlElement(name = "NoMetadata", required = true)
            protected String noMetadata;
            @XmlElement(name = "OldVersionNumber", required = true)
            protected String oldVersionNumber;
            @XmlElement(name = "Persisting", required = true)
            protected String persisting;
            @XmlElement(name = "PublishState")
            protected byte publishState;
            @XmlElement(name = "SasFormat", required = true)
            protected String sasFormat;
            @XmlElement(name = "SubType", required = true)
            protected String subType;
            @XmlElement(name = "Type", required = true)
            protected String type;
            @XmlElement(name = "Valid", required = true)
            protected String valid;
            @XmlElement(name = "VarInfoId", required = true)
            protected String varInfoId;
            @XmlElement(name = "VarName", required = true)
            protected String varName;
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
    public static class Splitters implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

    }


       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "outputNodeNames"
    })
    @Getter
    public static class SystemExtraValues implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "OutputNodeName")
        protected List<String> outputNodeNames = new ArrayList<>();

    }

}