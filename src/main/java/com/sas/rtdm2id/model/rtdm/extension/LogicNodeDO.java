package com.sas.rtdm2id.model.rtdm.extension;

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

    public String getArchivedPublishState() {
        return archivedPublishState;
    }

    public void setArchivedPublishState(String archivedPublishState) {
        this.archivedPublishState = archivedPublishState;
    }

    public Children getChildren() {
        return children;
    }

    public void setChildren(Children children) {
        this.children = children;
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

    public String getExplicitGroup() {
        return explicitGroup;
    }

    public void setExplicitGroup(String explicitGroup) {
        this.explicitGroup = explicitGroup;
    }

    public String getFirstOccurrenceToPublish() {
        return firstOccurrenceToPublish;
    }

    public void setFirstOccurrenceToPublish(String firstOccurrenceToPublish) {
        this.firstOccurrenceToPublish = firstOccurrenceToPublish;
    }

    public String getHiddenGroup() {
        return hiddenGroup;
    }

    public void setHiddenGroup(String hiddenGroup) {
        this.hiddenGroup = hiddenGroup;
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

    public String getLogicNode() {
        return logicNode;
    }

    public void setLogicNode(String logicNode) {
        this.logicNode = logicNode;
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

    public String getPersisting() {
        return persisting;
    }

    public void setPersisting(String persisting) {
        this.persisting = persisting;
    }

    public String getPublishState() {
        return publishState;
    }

    public void setPublishState(String publishState) {
        this.publishState = publishState;
    }

    public String getRootNode() {
        return rootNode;
    }

    public void setRootNode(String rootNode) {
        this.rootNode = rootNode;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getConcreteClass() {
        return concreteClass;
    }

    public void setConcreteClass(String concreteClass) {
        this.concreteClass = concreteClass;
    }

    public String getObjid() {
        return objid;
    }

    public void setObjid(String objid) {
        this.objid = objid;
    }
}
