/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm.extension;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

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
public class Value implements Serializable {

    private static final long serialVersionUID = -1L;

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


    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
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
