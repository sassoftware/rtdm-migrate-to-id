/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The representation of a decision.
 */
public class Decision {
    @JsonProperty("id")
    private String id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("description")
    private String description = null;

    @JsonProperty("majorRevision")
    private Integer majorRevision = null;

    @JsonProperty("locked")
    private Boolean locked = null;

    @JsonProperty("minorRevision")
    private Integer minorRevision = null;

    @JsonProperty("creationTimeStamp")
    private OffsetDateTime creationTimeStamp = null;

    @JsonProperty("createdBy")
    private String createdBy = null;

    @JsonProperty("modifiedTimeStamp")
    private OffsetDateTime modifiedTimeStamp = null;

    @JsonProperty("modifiedBy")
    private String modifiedBy = null;

    @JsonProperty("nodeCount")
    private Integer nodeCount = null;

    @JsonProperty("sourceRevisionUri")
    private String sourceRevisionUri = null;

    @JsonProperty("copyTimeStamp")
    private OffsetDateTime copyTimeStamp = null;

    @JsonProperty("flow")
    private ConditionDecisionStep flow = null;

    @JsonProperty("signature")
    private List<SignatureTerm> signature = new ArrayList<>();

    @JsonProperty("subjectLevel")
    private String subjectLevel = null;

    @JsonProperty("subjectId")
    private SignatureTermReference subjectId = null;

    @JsonProperty("folderType")
    private String folderType = null;

    @JsonProperty("links")
    private List<Link> links = null;

    @JsonProperty("version")
    private Integer version = null;

    public Decision id(String id) {
        this.id = id;
        return this;
    }

    /**
     * The string ID for the decision.
     *
     * @return id
     **/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Decision name(String name) {
        this.name = name;
        return this;
    }

    /**
     * The name for the decision.
     *
     * @return name
     **/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Decision description(String description) {
        this.description = description;
        return this;
    }

    /**
     * The description for the decision.
     *
     * @return description
     **/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Decision majorRevision(Integer majorRevision) {
        this.majorRevision = majorRevision;
        return this;
    }

    /**
     * Major version number of the revision being viewed of the decision. This value is assigned by the service, the user only has control on whether the major number or minor number is incremented when creating a new revision.
     *
     * @return majorRevision
     **/
    public Integer getMajorRevision() {
        return majorRevision;
    }

    public void setMajorRevision(Integer majorRevision) {
        this.majorRevision = majorRevision;
    }

    public Decision locked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    /**
     * Flag which indicates if the content being viewed is locked or editable.
     *
     * @return locked
     **/
    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Decision minorRevision(Integer minorRevision) {
        this.minorRevision = minorRevision;
        return this;
    }

    /**
     * Minor version number of the current revision of the decision. This value is assigned by the service, the user only has control on whether the major number or minor number is incremented when creating a new revision.
     *
     * @return minorRevision
     **/
    public Integer getMinorRevision() {
        return minorRevision;
    }

    public void setMinorRevision(Integer minorRevision) {
        this.minorRevision = minorRevision;
    }

    public Decision creationTimeStamp(OffsetDateTime creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
        return this;
    }

    /**
     * The timestamp when the decision was created.
     *
     * @return creationTimeStamp
     **/
    public OffsetDateTime getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(OffsetDateTime creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public Decision createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    /**
     * The user ID who created the decision.
     *
     * @return createdBy
     **/
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Decision modifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
        this.modifiedTimeStamp = modifiedTimeStamp;
        return this;
    }

    /**
     * The timestamp when the decision properties was modified.
     *
     * @return modifiedTimeStamp
     **/
    public OffsetDateTime getModifiedTimeStamp() {
        return modifiedTimeStamp;
    }

    public void setModifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
        this.modifiedTimeStamp = modifiedTimeStamp;
    }

    public Decision modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    /**
     * The user ID who modified the decision.
     *
     * @return modifiedBy
     **/
    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Decision nodeCount(Integer nodeCount) {
        this.nodeCount = nodeCount;
        return this;
    }

    /**
     * The total number of decision steps(nodes) in the decision flow.
     *
     * @return nodeCount
     **/
    public Integer getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(Integer nodeCount) {
        this.nodeCount = nodeCount;
    }

    public Decision sourceRevisionUri(String sourceRevisionUri) {
        this.sourceRevisionUri = sourceRevisionUri;
        return this;
    }

    /**
     * The URI of the decision revision this decision is being created from.
     *
     * @return sourceRevisionUri
     **/
    public String getSourceRevisionUri() {
        return sourceRevisionUri;
    }

    public void setSourceRevisionUri(String sourceRevisionUri) {
        this.sourceRevisionUri = sourceRevisionUri;
    }

    public Decision copyTimeStamp(OffsetDateTime copyTimeStamp) {
        this.copyTimeStamp = copyTimeStamp;
        return this;
    }

    /**
     * The time stamp when the decision revision was copied.
     *
     * @return copyTimeStamp
     **/
    public OffsetDateTime getCopyTimeStamp() {
        return copyTimeStamp;
    }

    public void setCopyTimeStamp(OffsetDateTime copyTimeStamp) {
        this.copyTimeStamp = copyTimeStamp;
    }

    public Decision flow(ConditionDecisionStep flow) {
        this.flow = flow;
        return this;
    }

    public Decision addFlowItem(Step flowItem) {
        if (this.flow == null) {
            List<Step> stepList = new ArrayList<>();
            this.flow = new ConditionDecisionStep(stepList);
        }
        this.flow.getFlow().add(flowItem);
        return this;
    }

    /**
     * The sequence of rule sets, models, custom objects, branches, and conditions which represent the decision making flow object.
     *
     * @return flow
     **/
    public ConditionDecisionStep getFlow() {
        return flow;
    }

    public void setFlow(ConditionDecisionStep flow) {
        this.flow = flow;
    }

    public Decision signature(List<SignatureTerm> signature) {
        this.signature = signature;
        return this;
    }

    public Decision addSignatureItem(SignatureTerm signatureItem) {
        this.signature.add(signatureItem);
        return this;
    }

    /**
     * The set of terms local to this decision with input and output behavior.
     *
     * @return signature
     **/
    public List<SignatureTerm> getSignature() {
        return signature;
    }

    public void setSignature(List<SignatureTerm> signature) {
        this.signature = signature;
    }

    public Decision subjectLevel(String subjectLevel) {
        this.subjectLevel = subjectLevel;
        return this;
    }

    /**
     * Indicates the kind of subject Id.
     *
     * @return subjectLevel
     **/
    public String getSubjectLevel() {
        return subjectLevel;
    }

    public void setSubjectLevel(String subjectLevel) {
        this.subjectLevel = subjectLevel;
    }

    public Decision subjectId(SignatureTermReference subjectId) {
        this.subjectId = subjectId;
        return this;
    }

    /**
     * The reference for the term used to identify the subject.
     *
     * @return subjectId
     **/
    public SignatureTermReference getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(SignatureTermReference subjectId) {
        this.subjectId = subjectId;
    }

    public Decision folderType(String folderType) {
        this.folderType = folderType;
        return this;
    }

    /**
     * Indicates the kind of folder where the decision is housed.
     *
     * @return folderType
     **/
    public String getFolderType() {
        return folderType;
    }

    public void setFolderType(String folderType) {
        this.folderType = folderType;
    }

    public Decision links(List<Link> links) {
        this.links = links;
        return this;
    }

    public Decision addLinksItem(Link linksItem) {
        if (this.links == null) {
            this.links = new ArrayList<>();
        }
        this.links.add(linksItem);
        return this;
    }

    /**
     * Links to related resources or operations.
     *
     * @return links
     **/
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Decision version(Integer version) {
        this.version = version;
        return this;
    }

    /**
     * The version number of the domain representation. This representation is version 1.
     *
     * @return version
     **/
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Decision decision = (Decision) o;
        return Objects.equals(this.id, decision.id) &&
                Objects.equals(this.name, decision.name) &&
                Objects.equals(this.description, decision.description) &&
                Objects.equals(this.majorRevision, decision.majorRevision) &&
                Objects.equals(this.locked, decision.locked) &&
                Objects.equals(this.minorRevision, decision.minorRevision) &&
                Objects.equals(this.creationTimeStamp, decision.creationTimeStamp) &&
                Objects.equals(this.createdBy, decision.createdBy) &&
                Objects.equals(this.modifiedTimeStamp, decision.modifiedTimeStamp) &&
                Objects.equals(this.modifiedBy, decision.modifiedBy) &&
                Objects.equals(this.nodeCount, decision.nodeCount) &&
                Objects.equals(this.sourceRevisionUri, decision.sourceRevisionUri) &&
                Objects.equals(this.copyTimeStamp, decision.copyTimeStamp) &&
                Objects.equals(this.flow, decision.flow) &&
                Objects.equals(this.signature, decision.signature) &&
                Objects.equals(this.subjectLevel, decision.subjectLevel) &&
                Objects.equals(this.subjectId, decision.subjectId) &&
                Objects.equals(this.folderType, decision.folderType) &&
                Objects.equals(this.links, decision.links) &&
                Objects.equals(this.version, decision.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, majorRevision, locked, minorRevision, creationTimeStamp, createdBy, modifiedTimeStamp, modifiedBy, nodeCount, sourceRevisionUri, copyTimeStamp, flow, signature, subjectLevel, subjectId, folderType, links, version);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Decision {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    majorRevision: ").append(toIndentedString(majorRevision)).append("\n");
        sb.append("    locked: ").append(toIndentedString(locked)).append("\n");
        sb.append("    minorRevision: ").append(toIndentedString(minorRevision)).append("\n");
        sb.append("    creationTimeStamp: ").append(toIndentedString(creationTimeStamp)).append("\n");
        sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
        sb.append("    modifiedTimeStamp: ").append(toIndentedString(modifiedTimeStamp)).append("\n");
        sb.append("    modifiedBy: ").append(toIndentedString(modifiedBy)).append("\n");
        sb.append("    nodeCount: ").append(toIndentedString(nodeCount)).append("\n");
        sb.append("    sourceRevisionUri: ").append(toIndentedString(sourceRevisionUri)).append("\n");
        sb.append("    copyTimeStamp: ").append(toIndentedString(copyTimeStamp)).append("\n");
        sb.append("    flow: ").append(toIndentedString(flow)).append("\n");
        sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
        sb.append("    subjectLevel: ").append(toIndentedString(subjectLevel)).append("\n");
        sb.append("    subjectId: ").append(toIndentedString(subjectId)).append("\n");
        sb.append("    folderType: ").append(toIndentedString(folderType)).append("\n");
        sb.append("    links: ").append(toIndentedString(links)).append("\n");
        sb.append("    version: ").append(toIndentedString(version)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}

