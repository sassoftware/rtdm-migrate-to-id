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
public class Decision2 {
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

  @JsonProperty("flow")
  private List<Step2> flow = null;

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

  public Decision2 id(String id) {
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

  public Decision2 name(String name) {
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

  public Decision2 description(String description) {
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

  public Decision2 majorRevision(Integer majorRevision) {
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

  public Decision2 locked(Boolean locked) {
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

  public Decision2 minorRevision(Integer minorRevision) {
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

  public Decision2 creationTimeStamp(OffsetDateTime creationTimeStamp) {
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

  public Decision2 createdBy(String createdBy) {
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

  public Decision2 modifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
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

  public Decision2 modifiedBy(String modifiedBy) {
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

  public Decision2 flow(List<Step2> flow) {
    this.flow = flow;
    return this;
  }

  public Decision2 addFlowItem(Step2 flowItem) {
    if (this.flow == null) {
      this.flow = new ArrayList<>();
    }
    this.flow.add(flowItem);
    return this;
  }

  /**
   * The sequence of rule sets, models, custom objects, branches, and conditions which represent the decision making flow object.
   *
   * @return flow
   **/
  public List<Step2> getFlow() {
    return flow;
  }

  public void setFlow(List<Step2> flow) {
    this.flow = flow;
  }

  public Decision2 signature(List<SignatureTerm> signature) {
    this.signature = signature;
    return this;
  }

  public Decision2 addSignatureItem(SignatureTerm signatureItem) {
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

  public Decision2 subjectLevel(String subjectLevel) {
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

  public Decision2 subjectId(SignatureTermReference subjectId) {
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

  public Decision2 folderType(String folderType) {
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

  public Decision2 links(List<Link> links) {
    this.links = links;
    return this;
  }

  public Decision2 addLinksItem(Link linksItem) {
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

  public Decision2 version(Integer version) {
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
    Decision2 decision2 = (Decision2) o;
    return Objects.equals(this.id, decision2.id) &&
            Objects.equals(this.name, decision2.name) &&
            Objects.equals(this.description, decision2.description) &&
            Objects.equals(this.majorRevision, decision2.majorRevision) &&
            Objects.equals(this.locked, decision2.locked) &&
            Objects.equals(this.minorRevision, decision2.minorRevision) &&
            Objects.equals(this.creationTimeStamp, decision2.creationTimeStamp) &&
            Objects.equals(this.createdBy, decision2.createdBy) &&
            Objects.equals(this.modifiedTimeStamp, decision2.modifiedTimeStamp) &&
            Objects.equals(this.modifiedBy, decision2.modifiedBy) &&
            Objects.equals(this.flow, decision2.flow) &&
            Objects.equals(this.signature, decision2.signature) &&
            Objects.equals(this.subjectLevel, decision2.subjectLevel) &&
            Objects.equals(this.subjectId, decision2.subjectId) &&
            Objects.equals(this.folderType, decision2.folderType) &&
            Objects.equals(this.links, decision2.links) &&
            Objects.equals(this.version, decision2.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, majorRevision, locked, minorRevision, creationTimeStamp, createdBy, modifiedTimeStamp, modifiedBy, flow, signature, subjectLevel, subjectId, folderType, links, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Decision2 {\n");

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

