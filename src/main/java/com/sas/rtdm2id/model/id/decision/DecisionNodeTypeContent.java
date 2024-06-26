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
 * Contains information needed to extend the decision flow with a new node type including the decision node type&#39;s signature and provision for acquiring decision step code for the associated decision node type.
 */
public class DecisionNodeTypeContent {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("nodeTypeId")
  private String nodeTypeId = null;

  @JsonProperty("contentType")
  private String contentType = null;

  @JsonProperty("staticContent")
  private String staticContent = null;

  @JsonProperty("nodeTypeSignatureTerms")
  private List<DecisionStepCodeSignatureTerm> nodeTypeSignatureTerms = null;

  @JsonProperty("createdBy")
  private String createdBy = null;

  @JsonProperty("creationTimeStamp")
  private OffsetDateTime creationTimeStamp = null;

  @JsonProperty("modifiedBy")
  private String modifiedBy = null;

  @JsonProperty("modifiedTimeStamp")
  private OffsetDateTime modifiedTimeStamp = null;

  @JsonProperty("links")
  private List<Link> links = null;

  @JsonProperty("version")
  private Integer version = null;

  public DecisionNodeTypeContent id(String id) {
    this.id = id;
    return this;
  }

  /**
   * The system-assigned unique ID for this object.
   *
   * @return id
   **/
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DecisionNodeTypeContent nodeTypeId(String nodeTypeId) {
    this.nodeTypeId = nodeTypeId;
    return this;
  }

  /**
   * The id of the decision node type associated with this decision node type content
   *
   * @return nodeTypeId
   **/
  public String getNodeTypeId() {
    return nodeTypeId;
  }

  public void setNodeTypeId(String nodeTypeId) {
    this.nodeTypeId = nodeTypeId;
  }

  public DecisionNodeTypeContent contentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

  /**
   * The type of logic to be executed when the associated decision node type is included in a decision flow (DS2 is only support contentType as of now)
   *
   * @return contentType
   **/
  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public DecisionNodeTypeContent staticContent(String staticContent) {
    this.staticContent = staticContent;
    return this;
  }

  /**
   * The logic to be executed when a decision node type of type \&quot;static\&quot; is is executed.  If the decision node type is not of type \&quot;static\&quot; this property should be null
   *
   * @return staticContent
   **/
  public String getStaticContent() {
    return staticContent;
  }

  public void setStaticContent(String staticContent) {
    this.staticContent = staticContent;
  }

  public DecisionNodeTypeContent nodeTypeSignatureTerms(List<DecisionStepCodeSignatureTerm> nodeTypeSignatureTerms) {
    this.nodeTypeSignatureTerms = nodeTypeSignatureTerms;
    return this;
  }

  public DecisionNodeTypeContent addNodeTypeSignatureTermsItem(DecisionStepCodeSignatureTerm nodeTypeSignatureTermsItem) {
    if (this.nodeTypeSignatureTerms == null) {
      this.nodeTypeSignatureTerms = new ArrayList<>();
    }
    this.nodeTypeSignatureTerms.add(nodeTypeSignatureTermsItem);
    return this;
  }

  /**
   * Signature variables of the decision node type.
   *
   * @return nodeTypeSignatureTerms
   **/
  public List<DecisionStepCodeSignatureTerm> getNodeTypeSignatureTerms() {
    return nodeTypeSignatureTerms;
  }

  public void setNodeTypeSignatureTerms(List<DecisionStepCodeSignatureTerm> nodeTypeSignatureTerms) {
    this.nodeTypeSignatureTerms = nodeTypeSignatureTerms;
  }

  public DecisionNodeTypeContent createdBy(String createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  /**
   * The user who created the object used in decision step.
   *
   * @return createdBy
   **/
  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public DecisionNodeTypeContent creationTimeStamp(OffsetDateTime creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
    return this;
  }

  /**
   * The date and time that the object used in the decision step was created.
   *
   * @return creationTimeStamp
   **/
  public OffsetDateTime getCreationTimeStamp() {
    return creationTimeStamp;
  }

  public void setCreationTimeStamp(OffsetDateTime creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
  }

  public DecisionNodeTypeContent modifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
    return this;
  }

  /**
   * The user ID of the authenticated user who last updated the object used in the decision step.
   *
   * @return modifiedBy
   **/
  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public DecisionNodeTypeContent modifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
    this.modifiedTimeStamp = modifiedTimeStamp;
    return this;
  }

  /**
   * The date and time that the object used in the decision step was last modified.
   *
   * @return modifiedTimeStamp
   **/
  public OffsetDateTime getModifiedTimeStamp() {
    return modifiedTimeStamp;
  }

  public void setModifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
    this.modifiedTimeStamp = modifiedTimeStamp;
  }

  public DecisionNodeTypeContent links(List<Link> links) {
    this.links = links;
    return this;
  }

  public DecisionNodeTypeContent addLinksItem(Link linksItem) {
    if (this.links == null) {
      this.links = new ArrayList<>();
    }
    this.links.add(linksItem);
    return this;
  }

  /**
   * Zero or more links to related resources or operations.
   *
   * @return links
   **/
  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public DecisionNodeTypeContent version(Integer version) {
    this.version = version;
    return this;
  }

  /**
   * This media type&#39;s schema version number. This representation is version 1.
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
    DecisionNodeTypeContent decisionNodeTypeContent = (DecisionNodeTypeContent) o;
    return Objects.equals(this.id, decisionNodeTypeContent.id) &&
            Objects.equals(this.nodeTypeId, decisionNodeTypeContent.nodeTypeId) &&
            Objects.equals(this.contentType, decisionNodeTypeContent.contentType) &&
            Objects.equals(this.staticContent, decisionNodeTypeContent.staticContent) &&
            Objects.equals(this.nodeTypeSignatureTerms, decisionNodeTypeContent.nodeTypeSignatureTerms) &&
            Objects.equals(this.createdBy, decisionNodeTypeContent.createdBy) &&
            Objects.equals(this.creationTimeStamp, decisionNodeTypeContent.creationTimeStamp) &&
            Objects.equals(this.modifiedBy, decisionNodeTypeContent.modifiedBy) &&
            Objects.equals(this.modifiedTimeStamp, decisionNodeTypeContent.modifiedTimeStamp) &&
            Objects.equals(this.links, decisionNodeTypeContent.links) &&
            Objects.equals(this.version, decisionNodeTypeContent.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nodeTypeId, contentType, staticContent, nodeTypeSignatureTerms, createdBy, creationTimeStamp, modifiedBy, modifiedTimeStamp, links, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DecisionNodeTypeContent {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nodeTypeId: ").append(toIndentedString(nodeTypeId)).append("\n");
    sb.append("    contentType: ").append(toIndentedString(contentType)).append("\n");
    sb.append("    staticContent: ").append(toIndentedString(staticContent)).append("\n");
    sb.append("    nodeTypeSignatureTerms: ").append(toIndentedString(nodeTypeSignatureTerms)).append("\n");
    sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
    sb.append("    creationTimeStamp: ").append(toIndentedString(creationTimeStamp)).append("\n");
    sb.append("    modifiedBy: ").append(toIndentedString(modifiedBy)).append("\n");
    sb.append("    modifiedTimeStamp: ").append(toIndentedString(modifiedTimeStamp)).append("\n");
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

