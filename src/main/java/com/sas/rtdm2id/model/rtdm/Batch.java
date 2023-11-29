/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm;

import com.sas.rtdm2id.model.rtdm.extension.*;

import javax.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "resultsSummary",
        "extractedFrom",
        "logicalUnit"
})
@XmlRootElement(name = "Batch")
public class Batch implements Serializable {

    private final static long serialVersionUID = -1L;
    @XmlElement(name = "ResultsSummary", required = true)
    protected ResultsSummary resultsSummary;
    @XmlElement(name = "ExtractedFrom", required = true)
    protected ExtractedFrom extractedFrom;
    @XmlElement(name = "LogicalUnit", required = true)
    protected LogicalUnit logicalUnit;

    public ResultsSummary getResultsSummary() {
        return resultsSummary;
    }

    public void setResultsSummary(ResultsSummary resultsSummary) {
        this.resultsSummary = resultsSummary;
    }

    public ExtractedFrom getExtractedFrom() {
        return extractedFrom;
    }

    public void setExtractedFrom(ExtractedFrom extractedFrom) {
        this.extractedFrom = extractedFrom;
    }

    public LogicalUnit getLogicalUnit() {
        return logicalUnit;
    }

    public void setLogicalUnit(LogicalUnit logicalUnit) {
        this.logicalUnit = logicalUnit;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "businessContext",
            "businessContextId",
            "rootDataFolder"
    })
    public static class ExtractedFrom implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "BusinessContext", required = true)
        protected String businessContext;
        @XmlElement(name = "BusinessContextId", required = true)
        protected String businessContextId;
        @XmlElement(name = "RootDataFolder", required = true)
        protected String rootDataFolder;

        public String getBusinessContext() {
            return businessContext;
        }

        public void setBusinessContext(String businessContext) {
            this.businessContext = businessContext;
        }

        public String getBusinessContextId() {
            return businessContextId;
        }

        public void setBusinessContextId(String businessContextId) {
            this.businessContextId = businessContextId;
        }

        public String getRootDataFolder() {
            return rootDataFolder;
        }

        public void setRootDataFolder(String rootDataFolder) {
            this.rootDataFolder = rootDataFolder;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "campaignDO",
            "flowDO",
            "startNodeDataDO",
            "replyNodeDataDOs",
            "splitNodeDataDOs",
            "cellNodeDataDOs",
            "processNodeDataDOs",
            "multiSelectNodeDataDOs",
            "subDiagramNodeDataDOs",
            "assignmentNodeDataDOs",
            "cHandRHNodeDataDOs",
            "addStagedTreatmentsNodeDataDOs",
            "removeStagedTreatmentsNodeDataDOs"
    })
    public static class LogicalUnit implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "CampaignDO", required = true)
        protected CampaignDO campaignDO;
        @XmlElement(name = "FlowDO", required = true)
        protected FlowDO flowDO;
        @XmlElement(name = "StartNodeDataDO", required = true)
        protected StartNodeDataDO startNodeDataDO;
        @XmlElement(name = "ReplyNodeDataDO")
        protected List<ReplyNodeDataDO> replyNodeDataDOs = new ArrayList<>();
        @XmlElement(name = "SplitNodeDataDO", required = true)
        protected List<SplitNodeDataDO> splitNodeDataDOs = new ArrayList<>();
        @XmlElement(name = "CellNodeDataDO")
        protected List<CellNodeDataDO> cellNodeDataDOs = new ArrayList<>();
        @XmlElement(name = "ProcessNodeDataDO")
        protected List<ProcessNodeDataDO> processNodeDataDOs = new ArrayList<>();
        @XmlElement(name = "MultiSelectNodeDataDO")
        protected List<MultiSelectNodeDataDO> multiSelectNodeDataDOs = new ArrayList<>();
        @XmlElement(name = "SubDiagramNodeDataDO")
        protected List<SubDiagramNodeDataDO> subDiagramNodeDataDOs = new ArrayList<>();
        @XmlElement(name = "AssignmentNodeDataDO")
        protected List<AssignmentNodeDataDO> assignmentNodeDataDOs = new ArrayList<>();
        @XmlElement(name = "CHandRHNodeDataDO")
        protected List<CHandRHNodeDataDO> cHandRHNodeDataDOs = new ArrayList<>();
        @XmlElement(name = "AddStagedTreatmentsNodeDataDO")
        protected List<StagedTreatmentsNodeDataDO> addStagedTreatmentsNodeDataDOs = new ArrayList<>();
        @XmlElement(name = "RemoveStagedTreatmentsNodeDataDO")
        protected List<RemoveStagedTreatmentsNodeDataDO> removeStagedTreatmentsNodeDataDOs = new ArrayList<>();

        public CampaignDO getCampaignDO() {
            return campaignDO;
        }

        public void setCampaignDO(CampaignDO campaignDO) {
            this.campaignDO = campaignDO;
        }

        public FlowDO getFlowDO() {
            return flowDO;
        }

        public void setFlowDO(FlowDO flowDO) {
            this.flowDO = flowDO;
        }

        public StartNodeDataDO getStartNodeDataDO() {
            return startNodeDataDO;
        }

        public void setStartNodeDataDO(StartNodeDataDO startNodeDataDO) {
            this.startNodeDataDO = startNodeDataDO;
        }

        public List<ReplyNodeDataDO> getReplyNodeDataDOs() {
            return replyNodeDataDOs;
        }

        public void setReplyNodeDataDOs(List<ReplyNodeDataDO> replyNodeDataDOs) {
            this.replyNodeDataDOs = replyNodeDataDOs;
        }

        public List<SplitNodeDataDO> getSplitNodeDataDOs() {
            return splitNodeDataDOs;
        }

        public void setSplitNodeDataDOs(List<SplitNodeDataDO> splitNodeDataDOs) {
            this.splitNodeDataDOs = splitNodeDataDOs;
        }

        public List<CellNodeDataDO> getCellNodeDataDOs() {
            return cellNodeDataDOs;
        }

        public void setCellNodeDataDOs(List<CellNodeDataDO> cellNodeDataDOs) {
            this.cellNodeDataDOs = cellNodeDataDOs;
        }

        public List<ProcessNodeDataDO> getProcessNodeDataDOs() {
            return processNodeDataDOs;
        }

        public void setProcessNodeDataDOs(List<ProcessNodeDataDO> processNodeDataDOs) {
            this.processNodeDataDOs = processNodeDataDOs;
        }

        public List<MultiSelectNodeDataDO> getMultiSelectNodeDataDOs() {
            return multiSelectNodeDataDOs;
        }

        public void setMultiSelectNodeDataDOs(List<MultiSelectNodeDataDO> multiSelectNodeDataDOs) {
            this.multiSelectNodeDataDOs = multiSelectNodeDataDOs;
        }

        public List<SubDiagramNodeDataDO> getSubDiagramNodeDataDOs() {
            return subDiagramNodeDataDOs;
        }

        public void setSubDiagramNodeDataDOs(List<SubDiagramNodeDataDO> subDiagramNodeDataDOs) {
            this.subDiagramNodeDataDOs = subDiagramNodeDataDOs;
        }

        public List<AssignmentNodeDataDO> getAssignmentNodeDataDOs() {
            return assignmentNodeDataDOs;
        }

        public void setAssignmentNodeDataDOs(List<AssignmentNodeDataDO> assignmentNodeDataDOs) {
            this.assignmentNodeDataDOs = assignmentNodeDataDOs;
        }

        public List<CHandRHNodeDataDO> getCHandRHNodeDataDOs() {
            return cHandRHNodeDataDOs;
        }

        public void setCHandRHNodeDataDOs(List<CHandRHNodeDataDO> cHandRHNodeDataDOs) {
            this.cHandRHNodeDataDOs = cHandRHNodeDataDOs;
        }

        public List<StagedTreatmentsNodeDataDO> getAddStagedTreatmentsNodeDataDOs() {
            return addStagedTreatmentsNodeDataDOs;
        }

        public void setAddStagedTreatmentsNodeDataDOs(List<StagedTreatmentsNodeDataDO> addStagedTreatmentsNodeDataDOs) {
            this.addStagedTreatmentsNodeDataDOs = addStagedTreatmentsNodeDataDOs;
        }

        public List<RemoveStagedTreatmentsNodeDataDO> getRemoveStagedTreatmentsNodeDataDOs() {
            return removeStagedTreatmentsNodeDataDOs;
        }

        public void setRemoveStagedTreatmentsNodeDataDOs(List<RemoveStagedTreatmentsNodeDataDO> removeStagedTreatmentsNodeDataDOs) {
            this.removeStagedTreatmentsNodeDataDOs = removeStagedTreatmentsNodeDataDOs;
        }
    }
       
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "extractCount",
            "errorCount"
    })
    public static class ResultsSummary implements Serializable {

        private final static long serialVersionUID = -1L;
        @XmlElement(name = "ExtractCount")
        protected byte extractCount;
        @XmlElement(name = "ErrorCount")
        protected byte errorCount;

        public byte getExtractCount() {
            return extractCount;
        }

        public void setExtractCount(byte extractCount) {
            this.extractCount = extractCount;
        }

        public byte getErrorCount() {
            return errorCount;
        }

        public void setErrorCount(byte errorCount) {
            this.errorCount = errorCount;
        }
    }

}
