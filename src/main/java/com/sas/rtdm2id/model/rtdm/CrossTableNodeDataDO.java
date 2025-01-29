/*
Copyright © 2025, SAS Institute Inc., Cary, NC, USA. All Rights Reserved.
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

import com.sas.rtdm2id.model.rtdm.SplitNodeDataDO.SplitOnNodeDataDO.VarRefDO;
import com.sas.rtdm2id.model.rtdm.extension.InputNodes;
import com.sas.rtdm2id.model.rtdm.extension.NodeTags;
import com.sas.rtdm2id.model.rtdm.extension.OutputNodes;
import com.sas.rtdm2id.model.rtdm.extension.SortByList;
import com.sas.rtdm2id.model.rtdm.extension.Splitters;
import com.sas.rtdm2id.model.rtdm.extension.SystemExtraValues;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "archivedPublishState",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "columnCriteria",
        "controlKey",
        "crossTable",
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
        "outputVariable",
        "persisting",
        "publishState",
        "refreshNodeDataDO",
        "rowCriteria",
        "sortByList",
        "splitters",
        "subjectID",
        "totalCount",
        "usingSingleInputVariable",
        "validInputSubject",
        "validOutputSubject",
        "versionNumber",
        "x",
        "y",
        "systemExtraValues"
})
@Getter
@Setter
public class CrossTableNodeDataDO implements Serializable {

    private final static long serialVersionUID = -1L;

    @XmlElement(name = "ArchivedPublishState")
    protected byte archivedPublishState;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    protected String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    protected String codeEverBeenPublished;
    @XmlElement(name = "ColumnCriteria", required = true)
    protected ColumnCriteria columnCriteria;
    @XmlElement(name = "ControlKey")
    protected byte controlKey;
    @XmlElement(name = "CrossTable", required = true)
    protected CrossTable crossTable;
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
    @XmlElement(name = "OutputVariable", required = true)
    protected OutputVariable outputVariable;
    @XmlElement(name = "Persisting", required = true)
    protected String persisting;
    @XmlElement(name = "PublishState")
    protected byte publishState;
    @XmlElement(name = "RefreshNodeDataDO", required = true)
    protected String refreshNodeDataDO;
    @XmlElement(name = "RowCriteria", required = true)
    protected RowCriteria rowCriteria;
    @XmlElement(name = "SortByList", required = true)
    protected SortByList sortByList;
    @XmlElement(name = "Splitters", required = true)
    protected Splitters splitters;
    @XmlElement(name = "SubjectID", required = true)
    protected String subjectID;
    @XmlElement(name = "TotalCount")
    protected byte totalCount;
    @XmlElement(name = "UsingSingleInputVariable", required = true)
    protected String usingSingleInputVariable;
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
            "lineItems",
            "varRefDO"
    })
    @Getter
    @Setter
    public static class ColumnCriteria implements Serializable {
        private final static long serialVersionUID = -1L;

        @XmlElement(name = "LineItems", required = true)
        protected LineItems lineItems;

        @XmlElement(name = "VarRefDO", required = true)
        protected VarRefDO varRefDO;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "lineItems",
            "varRefDO"
    })
    @Getter
    @Setter
    public static class RowCriteria implements Serializable {
        private final static long serialVersionUID = -1L;

        @XmlElement(name = "LineItems", required = true)
        protected LineItems lineItems;

        @XmlElement(name = "VarRefDO", required = true)
        protected VarRefDO varRefDO;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "crossTableLineItemDOs"
    })
    @Getter
    @Setter
    public static class LineItems implements Serializable {
        private final static long serialVersionUID = -1L;

        @XmlElement(name = "CrossTableLineItemDO")
        protected List<CrossTableLineItemDO> crossTableLineItemDOs = new ArrayList<>();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "lists"
    })
    @Getter
    @Setter
    public static class CrossTable implements Serializable {
        private final static long serialVersionUID = -1L;
        @XmlElement(name = "List")
        protected List<ListOfValues> lists = new ArrayList<>();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "values"
    })
    @Getter
    @Setter
    public static class ListOfValues implements Serializable {
        private final static long serialVersionUID = -1L;
        @XmlElement(name = "IBVariableValueDO")
        protected List<IBVariableValueDO> values = new ArrayList<>();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "archivedPublishState",
            "codeChangeUnpublished",
            "codeEverBeenPublished",
            "booleanValue",
            "dateValue",
            "dateValueString",
            "doubleValue",
            "longValue",
            "stringValue",
            "listValue",
            "type",
            "versionNumber"
    })
    @Getter
    @Setter
    public static class IBVariableValueDO implements Serializable {
        private final static long serialVersionUID = -1L;
        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;
        @XmlElement(name = "CodeChangeUnpublished", required = true)
        protected String codeChangeUnpublished;
        @XmlElement(name = "CodeEverBeenPublished", required = true)
        protected String codeEverBeenPublished;
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
        protected int type;
        @XmlElement(name = "VersionNumber")
        protected float versionNumber;
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
            "identifiersRemoved",
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
            "possibleValuesList",
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
    public static class OutputVariable implements Serializable {
        private final static long serialVersionUID = -1L;

        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;

        @XmlElement(name = "Attachments")
        protected boolean attachments;

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

        @XmlElement(name = "IdentifiersRemoved")
        protected String identifiersRemoved;

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

        @XmlElement(name = "PossibleValues", required = true)
        protected PossibleValuesList possibleValuesList;

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

        @XmlElement(name = "SharedIn")
        protected String sharedIn;

        @XmlElement(name = "TestVarInfoId", required = true)
        protected String testVarInfoId;

        @XmlElement(name = "Type")
        protected int type;

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

        @XmlAttribute(name = "objid")
        protected Short objid;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "possibleValues"
    })
    @Getter
    @Setter
    public static class PossibleValuesList implements Serializable {
        private final static long serialVersionUID = -1L;

        @XmlElement(name = "IBVariableValueDO")
        protected List<IBVariableValueDO> possibleValues = new ArrayList<>();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "archivedPublishState",
            "codeChangeUnpublished",
            "codeEverBeenPublished",
            "discrete",
            "firstOccurrenceToPublish",
            "formattedValue",
            "loadedFromOldDavLocation",
            "loading",
            "maxInclusive",
            "maxValue",
            "minInclusive",
            "minValue",
            "missing",
            "needToPersist",
            "oldVersionNumber",
            "persisting",
            "publishState",
            "remainder",
            "selected",
            "versionNumber"
    })
    @Getter
    @Setter
    public static class CrossTableLineItemDO implements Serializable {

        private final static long serialVersionUID = -1L;

        @XmlElement(name = "ArchivedPublishState")
        protected byte archivedPublishState;

        @XmlElement(name = "CodeChangeUnpublished", required = true)
        protected String codeChangeUnpublished;

        @XmlElement(name = "CodeEverBeenPublished", required = true)
        protected String codeEverBeenPublished;

        @XmlElement(name = "Discrete", required = true)
        protected boolean discrete;

        @XmlElement(name = "FirstOccurrenceToPublish")
        protected byte firstOccurrenceToPublish;

        @XmlElement(name = "FormattedValue")
        protected String formattedValue;

        @XmlElement(name = "LoadedFromOldDavLocation", required = true)
        protected boolean loadedFromOldDavLocation;

        @XmlElement(name = "Loading", required = true)
        protected boolean loading;

        @XmlElement(name = "MaxInclusive")
        protected boolean maxInclusive;

        @XmlElement(name = "MaxValue")
        protected MaxValue maxValue;

        @XmlElement(name = "MinInclusive")
        protected boolean minInclusive;

        @XmlElement(name = "MinValue")
        protected MinValue minValue;

        @XmlElement(name = "Missing", required = true)
        protected boolean missing;

        @XmlElement(name = "NeedToPersist", required = true)
        protected boolean needToPersist;

        @XmlElement(name = "OldVersionNumber")
        protected String oldVersionNumber;

        @XmlElement(name = "Persisting", required = true)
        protected boolean persisting;

        @XmlElement(name = "PublishState")
        protected byte publishState;

        @XmlElement(name = "Remainder", required = true)
        protected boolean remainder;

        @XmlElement(name = "Selected", required = true)
        protected boolean selected;

        @XmlElement(name = "VersionNumber")
        protected float versionNumber;

        @XmlAttribute(name = "ConcreteClass")
        protected String concreteClass;

        @XmlAttribute(name = "listpos")
        protected Byte listpos;

        @XmlAttribute(name = "objid")
        protected Short objid;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    @Getter
    @Setter
    public static class MinValue implements Serializable {

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
    public static class MaxValue implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlValue
        protected String value;
        @XmlAttribute(name = "StoredType")
        protected String storedType;

    }

}
