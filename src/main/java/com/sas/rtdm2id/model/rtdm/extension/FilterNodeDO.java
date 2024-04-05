package com.sas.rtdm2id.model.rtdm.extension;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "archivedPublishState",
        "children",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "dateType",
        "firstOccurrenceToPublish",
        "formattedValues",
        "loadedFromOldDavLocation",
        "loading",
        "logicNode",
        "needToPersist",
        "oldVersionNumber",
        "operator",
        "pattern",
        "persisting",
        "publishState",
        "rootNode",
        "rule",
        "subjectId",
        "versionNumber",
        "concreteClass",
        "objid",
        "values",
        "varRef"
})
@Getter
@Setter
public class FilterNodeDO implements Serializable {

    private final static long serialVersionUID = -1L;

    @XmlElement(name = "ArchivedPublishState", required = true)
    private String archivedPublishState;
    @XmlElement(name = "Children")
    private Children children;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    private String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    private String codeEverBeenPublished;
    @XmlElement(name = "DateType")
    private String dateType;
    @XmlElement(name = "FirstOccurrenceToPublish", required = true)
    private String firstOccurrenceToPublish;
    @XmlElement(name = "FormattedValues", required = true)
    private String formattedValues;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    private String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    private String loading;
    @XmlElement(name = "LogicNode", required = true)
    private String logicNode;
    @XmlElement(name = "NeedToPersist", required = true)
    private String needToPersist;
    @XmlElement(name = "OldVersionNumber", required = true)
    private String oldVersionNumber;
    @XmlElement(name = "Operator", required = true)
    private String operator;
    @XmlElement(name = "Pattern", required = true)
    private String pattern;
    @XmlElement(name = "Persisting", required = true)
    private String persisting;
    @XmlElement(name = "PublishState", required = true)
    private String publishState;
    @XmlElement(name = "RootNode", required = true)
    private String rootNode;
    @XmlElement(name = "Rule", required = true)
    private String rule;
    @XmlElement(name = "SubjectId", required = true)
    private String subjectId;
    @XmlElement(name = "VersionNumber", required = true)
    private String versionNumber;
    @XmlAttribute(name = "ConcreteClass")
    private String concreteClass;
    @XmlAttribute(name = "objid")
    private String objid;
    @XmlElement(name = "Values", required = true)
    private Values values;
    @XmlElement(name = "VarRef", required = true)
    private VarRef varRef;

}
