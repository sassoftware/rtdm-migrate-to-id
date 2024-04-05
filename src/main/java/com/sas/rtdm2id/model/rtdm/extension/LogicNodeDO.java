package com.sas.rtdm2id.model.rtdm.extension;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "archivedPublishState",
        "children",
        "codeChangeUnpublished",
        "codeEverBeenPublished",
        "explicitGroup",
        "firstOccurrenceToPublish",
        "hiddenGroup",
        "loadedFromOldDavLocation",
        "loading",
        "logicNode",
        "needToPersist",
        "oldVersionNumber",
        "persisting",
        "publishState",
        "rootNode",
        "rule",
        "type",
        "versionNumber",
        "concreteClass",
        "objid"
})
@Getter
@Setter
public class LogicNodeDO implements Serializable {

    private final static long serialVersionUID = -1L;

    @XmlElement(name = "ArchivedPublishState", required = true)
    private String archivedPublishState;
    @XmlElement(name = "Children")
    private Children children;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    private String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    private String codeEverBeenPublished;
    @XmlElement(name = "ExplicitGroup", required = true)
    private String explicitGroup;
    @XmlElement(name = "FirstOccurrenceToPublish", required = true)
    private String firstOccurrenceToPublish;
    @XmlElement(name = "HiddenGroup", required = true)
    private String hiddenGroup;
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
    @XmlElement(name = "Persisting", required = true)
    private String persisting;
    @XmlElement(name = "PublishState", required = true)
    private String publishState;
    @XmlElement(name = "RootNode", required = true)
    private String rootNode;
    @XmlElement(name = "Rule", required = true)
    private String rule;
    @XmlElement(name = "Type", required = true)
    private String type;
    @XmlElement(name = "VersionNumber", required = true)
    private String versionNumber;
    @XmlAttribute(name = "ConcreteClass")
    private String concreteClass;
    @XmlAttribute(name = "objid")
    private String objid;

}
