/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm.extension;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

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
        "versionNumber",
        "concreteClass",
        "objid"
})
public class VarRef implements Serializable {
    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ArchivedPublishState", required = true)
    private String archivedPublishState;
    @XmlElement(name = "CodeChangeUnpublished", required = true)
    private String codeChangeUnpublished;
    @XmlElement(name = "CodeEverBeenPublished", required = true)
    private String codeEverBeenPublished;
    @XmlElement(name = "Discrete", required = true)
    private String discrete;
    @XmlElement(name = "FirstOccurrenceToPublish", required = true)
    private String firstOccurrenceToPublish;
    @XmlElement(name = "LoadedFromOldDavLocation", required = true)
    private String loadedFromOldDavLocation;
    @XmlElement(name = "Loading", required = true)
    private String loading;
    @XmlElement(name = "NeedToPersist", required = true)
    private String needToPersist;
    @XmlElement(name = "NoMetadata", required = true)
    private String noMetadata;
    @XmlElement(name = "OldVersionNumber", required = true)
    private String oldVersionNumber;
    @XmlElement(name = "Persisting", required = true)
    private String persisting;
    @XmlElement(name = "PublishState", required = true)
    private String publishState;
    @XmlElement(name = "SasFormat", required = true)
    private String sasFormat;
    @XmlElement(name = "SubType", required = true)
    private String subType;
    @XmlElement(name = "Type", required = true)
    private String type;
    @XmlElement(name = "Valid", required = true)
    private String valid;
    @XmlElement(name = "VarInfoId", required = true)
    private String varInfoId;
    @XmlElement(name = "VarName", required = true)
    private String varName;
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

    public String getDiscrete() {
        return discrete;
    }

    public void setDiscrete(String discrete) {
        this.discrete = discrete;
    }

    public String getFirstOccurrenceToPublish() {
        return firstOccurrenceToPublish;
    }

    public void setFirstOccurrenceToPublish(String firstOccurrenceToPublish) {
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

    public String getNeedToPersist() {
        return needToPersist;
    }

    public void setNeedToPersist(String needToPersist) {
        this.needToPersist = needToPersist;
    }

    public String getNoMetadata() {
        return noMetadata;
    }

    public void setNoMetadata(String noMetadata) {
        this.noMetadata = noMetadata;
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

    public String getSasFormat() {
        return sasFormat;
    }

    public void setSasFormat(String sasFormat) {
        this.sasFormat = sasFormat;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getVarInfoId() {
        return varInfoId;
    }

    public void setVarInfoId(String varInfoId) {
        this.varInfoId = varInfoId;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
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
