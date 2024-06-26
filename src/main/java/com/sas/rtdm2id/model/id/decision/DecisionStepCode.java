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
 * Contains the structure of code that is needed to generate the decision code.
 */
public class DecisionStepCode {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("createdBy")
  private String createdBy = null;

  @JsonProperty("creationTimeStamp")
  private OffsetDateTime creationTimeStamp = null;

  @JsonProperty("modifiedBy")
  private String modifiedBy = null;

  @JsonProperty("modifiedTimeStamp")
  private OffsetDateTime modifiedTimeStamp = null;

  @JsonProperty("code")
  private String code = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("signature")
  private List<DecisionStepCodeSignatureTerm> signature = null;

  @JsonProperty("links")
  private List<Link> links = null;

  @JsonProperty("version")
  private Integer version = null;

  public DecisionStepCode id(String id) {
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

  public DecisionStepCode name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the object used in decision step.
   *
   * @return name
   **/
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DecisionStepCode description(String description) {
    this.description = description;
    return this;
  }

  /**
   * The description of the object used in decision step.
   *
   * @return description
   **/
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DecisionStepCode createdBy(String createdBy) {
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

  public DecisionStepCode creationTimeStamp(OffsetDateTime creationTimeStamp) {
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

  public DecisionStepCode modifiedBy(String modifiedBy) {
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

  public DecisionStepCode modifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
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

  public DecisionStepCode code(String code) {
    this.code = code;
    return this;
  }

  /**
   * The code of the object used in the decision step.
   *
   * @return code
   **/
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public DecisionStepCode type(String type) {
    this.type = type;
    return this;
  }

  /**
   * The object type of the object used in the decision step.
   *
   * @return type
   **/
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public DecisionStepCode signature(List<DecisionStepCodeSignatureTerm> signature) {
    this.signature = signature;
    return this;
  }

  public DecisionStepCode addSignatureItem(DecisionStepCodeSignatureTerm signatureItem) {
    if (this.signature == null) {
      this.signature = new ArrayList<>();
    }
    this.signature.add(signatureItem);
    return this;
  }

  /**
   * Signature variables of the object used in the decision step.
   *
   * @return signature
   **/
  public List<DecisionStepCodeSignatureTerm> getSignature() {
    return signature;
  }

  public void setSignature(List<DecisionStepCodeSignatureTerm> signature) {
    this.signature = signature;
  }

  public DecisionStepCode links(List<Link> links) {
    this.links = links;
    return this;
  }

  public DecisionStepCode addLinksItem(Link linksItem) {
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

  public DecisionStepCode version(Integer version) {
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
    DecisionStepCode decisionStepCode = (DecisionStepCode) o;
    return Objects.equals(this.id, decisionStepCode.id) &&
            Objects.equals(this.name, decisionStepCode.name) &&
            Objects.equals(this.description, decisionStepCode.description) &&
            Objects.equals(this.createdBy, decisionStepCode.createdBy) &&
            Objects.equals(this.creationTimeStamp, decisionStepCode.creationTimeStamp) &&
            Objects.equals(this.modifiedBy, decisionStepCode.modifiedBy) &&
            Objects.equals(this.modifiedTimeStamp, decisionStepCode.modifiedTimeStamp) &&
            Objects.equals(this.code, decisionStepCode.code) &&
            Objects.equals(this.type, decisionStepCode.type) &&
            Objects.equals(this.signature, decisionStepCode.signature) &&
            Objects.equals(this.links, decisionStepCode.links) &&
            Objects.equals(this.version, decisionStepCode.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, createdBy, creationTimeStamp, modifiedBy, modifiedTimeStamp, code, type, signature, links, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DecisionStepCode {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
    sb.append("    creationTimeStamp: ").append(toIndentedString(creationTimeStamp)).append("\n");
    sb.append("    modifiedBy: ").append(toIndentedString(modifiedBy)).append("\n");
    sb.append("    modifiedTimeStamp: ").append(toIndentedString(modifiedTimeStamp)).append("\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
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

