/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm;

import com.sas.rtdm2id.model.rtdm.extension.IBVariableDO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "archivedPublishState",
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
        "nodeDescription",
        "nodeId",
        "nodeName",
        "nodeState",
        "nodeTags",
        "nodeType",
        "oldVersionNumber",
        "outputNodes",
        "persisting",
        "publishState",
        "sortByList",
        "splitters",
        "subject",
        "subjectID",
        "totalCount",
        "userUpdatedProcessFlag",
        "validInputSubject",
        "validOutputSubject",
        "variables",
        "versionNumber",
        "x",
        "y",
        "systemExtraValues"
})
public class CHandRHNodeDataDO {

    @XmlElement(name = "ArchivedPublishState")
    protected byte archivedPublishState;
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
    protected CHandRHNodeDataDO.InputNodes inputNodes;
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
    protected CHandRHNodeDataDO.NodeTags nodeTags;
    @XmlElement(name = "NodeType")
    protected byte nodeType;
    @XmlElement(name = "OldVersionNumber", required = true)
    protected String oldVersionNumber;
    @XmlElement(name = "OutputNodes", required = true)
    protected CHandRHNodeDataDO.OutputNodes outputNodes;
    @XmlElement(name = "Persisting", required = true)
    protected String persisting;
    @XmlElement(name = "PublishState")
    protected byte publishState;
    @XmlElement(name = "SortByList", required = true)
    protected CHandRHNodeDataDO.SortByList sortByList;
    @XmlElement(name = "Splitters", required = true)
    protected CHandRHNodeDataDO.Splitters splitters;
    @XmlElement(name = "Subject", required = true)
    protected CHandRHNodeDataDO.Subject subject;
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
    @XmlElement(name = "Variables", required = true)
    protected CHandRHNodeDataDO.Variables variables;
    @XmlElement(name = "VersionNumber")
    protected float versionNumber;
    @XmlElement(name = "X")
    protected short x;
    @XmlElement(name = "Y")
    protected short y;
    @XmlElement(name = "SystemExtraValues", required = true)
    protected CHandRHNodeDataDO.SystemExtraValues systemExtraValues;
    @XmlAttribute(name = "objid")
    protected Short objid;

    /**
     * Gets the value of the archivedPublishState property.
     *
     */
    public byte getArchivedPublishState() {
        return archivedPublishState;
    }

    /**
     * Sets the value of the archivedPublishState property.
     *
     */
    public void setArchivedPublishState(byte value) {
        this.archivedPublishState = value;
    }

    /**
     * Gets the value of the channelCodesUpdated property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getChannelCodesUpdated() {
        return channelCodesUpdated;
    }

    /**
     * Sets the value of the channelCodesUpdated property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setChannelCodesUpdated(String value) {
        this.channelCodesUpdated = value;
    }

    /**
     * Gets the value of the codeChangeUnpublished property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCodeChangeUnpublished() {
        return codeChangeUnpublished;
    }

    /**
     * Sets the value of the codeChangeUnpublished property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCodeChangeUnpublished(String value) {
        this.codeChangeUnpublished = value;
    }

    /**
     * Gets the value of the codeEverBeenPublished property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCodeEverBeenPublished() {
        return codeEverBeenPublished;
    }

    /**
     * Sets the value of the codeEverBeenPublished property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCodeEverBeenPublished(String value) {
        this.codeEverBeenPublished = value;
    }

    /**
     * Gets the value of the controlKey property.
     *
     */
    public byte getControlKey() {
        return controlKey;
    }

    /**
     * Sets the value of the controlKey property.
     *
     */
    public void setControlKey(byte value) {
        this.controlKey = value;
    }

    /**
     * Gets the value of the dateModified property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDateModified() {
        return dateModified;
    }

    /**
     * Sets the value of the dateModified property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDateModified(String value) {
        this.dateModified = value;
    }

    /**
     * Gets the value of the droppedCount property.
     *
     */
    public byte getDroppedCount() {
        return droppedCount;
    }

    /**
     * Sets the value of the droppedCount property.
     *
     */
    public void setDroppedCount(byte value) {
        this.droppedCount = value;
    }

    /**
     * Gets the value of the excludeFlag property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getExcludeFlag() {
        return excludeFlag;
    }

    /**
     * Sets the value of the excludeFlag property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setExcludeFlag(String value) {
        this.excludeFlag = value;
    }

    /**
     * Gets the value of the excludeIconPath property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getExcludeIconPath() {
        return excludeIconPath;
    }

    /**
     * Sets the value of the excludeIconPath property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setExcludeIconPath(String value) {
        this.excludeIconPath = value;
    }

    /**
     * Gets the value of the firstOccurrenceToPublish property.
     *
     */
    public byte getFirstOccurrenceToPublish() {
        return firstOccurrenceToPublish;
    }

    /**
     * Sets the value of the firstOccurrenceToPublish property.
     *
     */
    public void setFirstOccurrenceToPublish(byte value) {
        this.firstOccurrenceToPublish = value;
    }

    /**
     * Gets the value of the iconHtmlPath property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIconHtmlPath() {
        return iconHtmlPath;
    }

    /**
     * Sets the value of the iconHtmlPath property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIconHtmlPath(String value) {
        this.iconHtmlPath = value;
    }

    /**
     * Gets the value of the iconPath property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * Sets the value of the iconPath property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setIconPath(String value) {
        this.iconPath = value;
    }

    /**
     * Gets the value of the inputNodes property.
     *
     * @return
     *     possible object is
     *     {@link CHandRHNodeDataDO.InputNodes }
     *
     */
    public CHandRHNodeDataDO.InputNodes getInputNodes() {
        return inputNodes;
    }

    /**
     * Sets the value of the inputNodes property.
     *
     * @param value
     *     allowed object is
     *     {@link CHandRHNodeDataDO.InputNodes }
     *
     */
    public void setInputNodes(CHandRHNodeDataDO.InputNodes value) {
        this.inputNodes = value;
    }

    /**
     * Gets the value of the label property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the label property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLabel(String value) {
        this.label = value;
    }

    /**
     * Gets the value of the loadedFromOldDavLocation property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLoadedFromOldDavLocation() {
        return loadedFromOldDavLocation;
    }

    /**
     * Sets the value of the loadedFromOldDavLocation property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLoadedFromOldDavLocation(String value) {
        this.loadedFromOldDavLocation = value;
    }

    /**
     * Gets the value of the loading property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getLoading() {
        return loading;
    }

    /**
     * Sets the value of the loading property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setLoading(String value) {
        this.loading = value;
    }

    /**
     * Gets the value of the needToPersist property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNeedToPersist() {
        return needToPersist;
    }

    /**
     * Sets the value of the needToPersist property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNeedToPersist(String value) {
        this.needToPersist = value;
    }

    /**
     * Gets the value of the nodeDescription property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNodeDescription() {
        return nodeDescription;
    }

    /**
     * Sets the value of the nodeDescription property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNodeDescription(String value) {
        this.nodeDescription = value;
    }

    /**
     * Gets the value of the nodeId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * Sets the value of the nodeId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNodeId(String value) {
        this.nodeId = value;
    }

    /**
     * Gets the value of the nodeName property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * Sets the value of the nodeName property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setNodeName(String value) {
        this.nodeName = value;
    }

    /**
     * Gets the value of the nodeState property.
     *
     */
    public byte getNodeState() {
        return nodeState;
    }

    /**
     * Sets the value of the nodeState property.
     *
     */
    public void setNodeState(byte value) {
        this.nodeState = value;
    }

    /**
     * Gets the value of the nodeTags property.
     *
     * @return
     *     possible object is
     *     {@link CHandRHNodeDataDO.NodeTags }
     *
     */
    public CHandRHNodeDataDO.NodeTags getNodeTags() {
        return nodeTags;
    }

    /**
     * Sets the value of the nodeTags property.
     *
     * @param value
     *     allowed object is
     *     {@link CHandRHNodeDataDO.NodeTags }
     *
     */
    public void setNodeTags(CHandRHNodeDataDO.NodeTags value) {
        this.nodeTags = value;
    }

    /**
     * Gets the value of the nodeType property.
     *
     */
    public byte getNodeType() {
        return nodeType;
    }

    /**
     * Sets the value of the nodeType property.
     *
     */
    public void setNodeType(byte value) {
        this.nodeType = value;
    }

    /**
     * Gets the value of the oldVersionNumber property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOldVersionNumber() {
        return oldVersionNumber;
    }

    /**
     * Sets the value of the oldVersionNumber property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOldVersionNumber(String value) {
        this.oldVersionNumber = value;
    }

    /**
     * Gets the value of the outputNodes property.
     *
     * @return
     *     possible object is
     *     {@link CHandRHNodeDataDO.OutputNodes }
     *
     */
    public CHandRHNodeDataDO.OutputNodes getOutputNodes() {
        return outputNodes;
    }

    /**
     * Sets the value of the outputNodes property.
     *
     * @param value
     *     allowed object is
     *     {@link CHandRHNodeDataDO.OutputNodes }
     *
     */
    public void setOutputNodes(CHandRHNodeDataDO.OutputNodes value) {
        this.outputNodes = value;
    }

    /**
     * Gets the value of the persisting property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPersisting() {
        return persisting;
    }

    /**
     * Sets the value of the persisting property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPersisting(String value) {
        this.persisting = value;
    }

    /**
     * Gets the value of the publishState property.
     *
     */
    public byte getPublishState() {
        return publishState;
    }

    /**
     * Sets the value of the publishState property.
     *
     */
    public void setPublishState(byte value) {
        this.publishState = value;
    }

    /**
     * Gets the value of the sortByList property.
     *
     * @return
     *     possible object is
     *     {@link CHandRHNodeDataDO.SortByList }
     *
     */
    public CHandRHNodeDataDO.SortByList getSortByList() {
        return sortByList;
    }

    /**
     * Sets the value of the sortByList property.
     *
     * @param value
     *     allowed object is
     *     {@link CHandRHNodeDataDO.SortByList }
     *
     */
    public void setSortByList(CHandRHNodeDataDO.SortByList value) {
        this.sortByList = value;
    }

    /**
     * Gets the value of the splitters property.
     *
     * @return
     *     possible object is
     *     {@link CHandRHNodeDataDO.Splitters }
     *
     */
    public CHandRHNodeDataDO.Splitters getSplitters() {
        return splitters;
    }

    /**
     * Sets the value of the splitters property.
     *
     * @param value
     *     allowed object is
     *     {@link CHandRHNodeDataDO.Splitters }
     *
     */
    public void setSplitters(CHandRHNodeDataDO.Splitters value) {
        this.splitters = value;
    }

    /**
     * Gets the value of the subject property.
     *
     * @return
     *     possible object is
     *     {@link CHandRHNodeDataDO.Subject }
     *
     */
    public CHandRHNodeDataDO.Subject getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     *
     * @param value
     *     allowed object is
     *     {@link CHandRHNodeDataDO.Subject }
     *
     */
    public void setSubject(CHandRHNodeDataDO.Subject value) {
        this.subject = value;
    }

    /**
     * Gets the value of the subjectID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSubjectID() {
        return subjectID;
    }

    /**
     * Sets the value of the subjectID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSubjectID(String value) {
        this.subjectID = value;
    }

    /**
     * Gets the value of the totalCount property.
     *
     */
    public byte getTotalCount() {
        return totalCount;
    }

    /**
     * Sets the value of the totalCount property.
     *
     */
    public void setTotalCount(byte value) {
        this.totalCount = value;
    }

    /**
     * Gets the value of the userUpdatedProcessFlag property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getUserUpdatedProcessFlag() {
        return userUpdatedProcessFlag;
    }

    /**
     * Sets the value of the userUpdatedProcessFlag property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setUserUpdatedProcessFlag(String value) {
        this.userUpdatedProcessFlag = value;
    }

    /**
     * Gets the value of the validInputSubject property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getValidInputSubject() {
        return validInputSubject;
    }

    /**
     * Sets the value of the validInputSubject property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setValidInputSubject(String value) {
        this.validInputSubject = value;
    }

    /**
     * Gets the value of the validOutputSubject property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getValidOutputSubject() {
        return validOutputSubject;
    }

    /**
     * Sets the value of the validOutputSubject property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setValidOutputSubject(String value) {
        this.validOutputSubject = value;
    }

    /**
     * Gets the value of the variables property.
     *
     * @return
     *     possible object is
     *     {@link CHandRHNodeDataDO.Variables }
     *
     */
    public CHandRHNodeDataDO.Variables getVariables() {
        return variables;
    }

    /**
     * Sets the value of the variables property.
     *
     * @param value
     *     allowed object is
     *     {@link CHandRHNodeDataDO.Variables }
     *
     */
    public void setVariables(CHandRHNodeDataDO.Variables value) {
        this.variables = value;
    }

    /**
     * Gets the value of the versionNumber property.
     *
     */
    public float getVersionNumber() {
        return versionNumber;
    }

    /**
     * Sets the value of the versionNumber property.
     *
     */
    public void setVersionNumber(float value) {
        this.versionNumber = value;
    }

    /**
     * Gets the value of the x property.
     *
     */
    public short getX() {
        return x;
    }

    /**
     * Sets the value of the x property.
     *
     */
    public void setX(short value) {
        this.x = value;
    }

    /**
     * Gets the value of the y property.
     *
     */
    public short getY() {
        return y;
    }

    /**
     * Sets the value of the y property.
     *
     */
    public void setY(short value) {
        this.y = value;
    }

    /**
     * Gets the value of the systemExtraValues property.
     *
     * @return
     *     possible object is
     *     {@link CHandRHNodeDataDO.SystemExtraValues }
     *
     */
    public CHandRHNodeDataDO.SystemExtraValues getSystemExtraValues() {
        return systemExtraValues;
    }

    /**
     * Sets the value of the systemExtraValues property.
     *
     * @param value
     *     allowed object is
     *     {@link CHandRHNodeDataDO.SystemExtraValues }
     *
     */
    public void setSystemExtraValues(CHandRHNodeDataDO.SystemExtraValues value) {
        this.systemExtraValues = value;
    }

    /**
     * Gets the value of the objid property.
     *
     * @return
     *     possible object is
     *     {@link Short }
     *
     */
    public Short getObjid() {
        return objid;
    }

    /**
     * Sets the value of the objid property.
     *
     * @param value
     *     allowed object is
     *     {@link Short }
     *
     */
    public void setObjid(Short value) {
        this.objid = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="ListTypes" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class InputNodes {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "ListTypes")
        protected String listTypes;

        /**
         * Gets the value of the value property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the listTypes property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getListTypes() {
            return listTypes;
        }

        /**
         * Sets the value of the listTypes property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setListTypes(String value) {
            this.listTypes = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="EmptyList" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class NodeTags {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

        /**
         * Gets the value of the value property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the emptyList property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getEmptyList() {
            return emptyList;
        }

        /**
         * Sets the value of the emptyList property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setEmptyList(String value) {
            this.emptyList = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="ListTypes" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class OutputNodes {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "ListTypes")
        protected String listTypes;

        /**
         * Gets the value of the value property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the listTypes property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getListTypes() {
            return listTypes;
        }

        /**
         * Sets the value of the listTypes property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setListTypes(String value) {
            this.listTypes = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="EmptyList" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class SortByList {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

        /**
         * Gets the value of the value property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the emptyList property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getEmptyList() {
            return emptyList;
        }

        /**
         * Sets the value of the emptyList property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setEmptyList(String value) {
            this.emptyList = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="EmptyList" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class Splitters {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "EmptyList")
        protected String emptyList;

        /**
         * Gets the value of the value property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the emptyList property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getEmptyList() {
            return emptyList;
        }

        /**
         * Sets the value of the emptyList property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setEmptyList(String value) {
            this.emptyList = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="ConcreteClass" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="objid" type="{http://www.w3.org/2001/XMLSchema}short" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "value"
    })
    public static class Subject {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "ConcreteClass")
        protected String concreteClass;
        @XmlAttribute(name = "objid")
        protected Short objid;

        /**
         * Gets the value of the value property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the concreteClass property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getConcreteClass() {
            return concreteClass;
        }

        /**
         * Sets the value of the concreteClass property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setConcreteClass(String value) {
            this.concreteClass = value;
        }

        /**
         * Gets the value of the objid property.
         *
         * @return
         *     possible object is
         *     {@link Short }
         *
         */
        public Short getObjid() {
            return objid;
        }

        /**
         * Sets the value of the objid property.
         *
         * @param value
         *     allowed object is
         *     {@link Short }
         *
         */
        public void setObjid(Short value) {
            this.objid = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="OutputNodeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "outputNodeName"
    })
    public static class SystemExtraValues {

        @XmlElement(name = "OutputNodeName", required = true)
        protected String outputNodeName;

        /**
         * Gets the value of the outputNodeName property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getOutputNodeName() {
            return outputNodeName;
        }

        /**
         * Sets the value of the outputNodeName property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setOutputNodeName(String value) {
            this.outputNodeName = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="IBVariableDO" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ArchivedPublishState" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                   &lt;element name="Attachments" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="CodeChangeUnpublished" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="CodeEverBeenPublished" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ContainsAllPossibleValues" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="DefaultValueIsMissing" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="FirstOccurrenceToPublish" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                   &lt;element name="ForceOverwrite" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Forced" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Hidden" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="HideVariable" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Level" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="LoadedFromOldDavLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Loading" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="LockedBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="MetadataType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="NeedToPersist" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="NoWritePermission" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="OldVersionNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="PersistState" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Persisting" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="PhysicalName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="PresentInDS2Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ProcessVariableName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="PublishState" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                   &lt;element name="ReadOnly" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Required" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Selected" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Shared" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="SharedIn">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="EmptyList" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="TestVarInfoId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                   &lt;element name="TypeDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Value">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ArchivedPublishState" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                             &lt;element name="CodeChangeUnpublished" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="CodeEverBeenPublished" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="FirstOccurrenceToPublish" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                             &lt;element name="LoadedFromOldDavLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="Loading" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="NeedToPersist" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="OldVersionNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="Persisting" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="PublishState" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                             &lt;element name="VarInfoArray" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VarInfoId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VarInfoName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VarInfoPhysicalName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VarInfoSource" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VarInfoSourceName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VarInfoSubtype" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VarInfoType" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VersionNumber" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                           &lt;/sequence>
     *                           &lt;attribute name="ConcreteClass" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="objid" type="{http://www.w3.org/2001/XMLSchema}short" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="VersionNumber" type="{http://www.w3.org/2001/XMLSchema}float"/>
     *                   &lt;element name="WhereClauseOp" type="{http://www.w3.org/2001/XMLSchema}byte"/>
     *                   &lt;element name="WhoModified" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="ConcreteClass" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="listpos" type="{http://www.w3.org/2001/XMLSchema}byte" />
     *                 &lt;attribute name="objid" type="{http://www.w3.org/2001/XMLSchema}short" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "ibVariableDO"
    })
    public static class Variables {

        @XmlElement(name = "IBVariableDO")
        protected List<IBVariableDO> ibVariableDO;

        /**
         * Gets the value of the ibVariableDO property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ibVariableDO property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIBVariableDO().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link IBVariableDO }
         *
         *
         */
        public List<IBVariableDO> getIBVariableDO() {
            if (ibVariableDO == null) {
                ibVariableDO = new ArrayList<IBVariableDO>();
            }
            return this.ibVariableDO;
        }

    }

}
