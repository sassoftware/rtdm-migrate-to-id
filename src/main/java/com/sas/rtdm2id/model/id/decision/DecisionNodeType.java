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
 * Contains information needed to extend the decision flow with a new node type
 */
public class DecisionNodeType {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("hasProperties")
  private Boolean hasProperties = null;

  @JsonProperty("hasInputs")
  private Boolean hasInputs = null;

  @JsonProperty("hasOutputs")
  private Boolean hasOutputs = null;

  @JsonProperty("inputDatagridMappable")
  private Boolean inputDatagridMappable = null;

  @JsonProperty("outputDatagridMappable")
  private Boolean outputDatagridMappable = null;

  @JsonProperty("inputDecisionTermMappable")
  private Boolean inputDecisionTermMappable = null;

  @JsonProperty("outputDecisionTermMappable")
  private Boolean outputDecisionTermMappable = null;

  @JsonProperty("independentMappings")
  private Boolean independentMappings = null;

  @JsonProperty("themeId")
  private String themeId = null;

  @JsonProperty("type")
  private String type = null;

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

  public DecisionNodeType id(String id) {
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

  public DecisionNodeType name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the DecisionNodeType used by the infrastructure to indentify this node type (no spaces or special characters).
   *
   * @return name
   **/
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DecisionNodeType description(String description) {
    this.description = description;
    return this;
  }

  /**
   * The description of the DecisionNodeType.
   *
   * @return description
   **/
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DecisionNodeType hasProperties(Boolean hasProperties) {
    this.hasProperties = hasProperties;
    return this;
  }

  /**
   * indicates whether or not the DecisionNodeType should be present a properties pane when included in a decision flow
   *
   * @return hasProperties
   **/
  public Boolean isHasProperties() {
    return hasProperties;
  }

  public void setHasProperties(Boolean hasProperties) {
    this.hasProperties = hasProperties;
  }

  public DecisionNodeType hasInputs(Boolean hasInputs) {
    this.hasInputs = hasInputs;
    return this;
  }

  /**
   * indicates that the DecisionNodeType has inputs which should be mapped to decision flow or datagrid variables
   *
   * @return hasInputs
   **/
  public Boolean isHasInputs() {
    return hasInputs;
  }

  public void setHasInputs(Boolean hasInputs) {
    this.hasInputs = hasInputs;
  }

  public DecisionNodeType hasOutputs(Boolean hasOutputs) {
    this.hasOutputs = hasOutputs;
    return this;
  }

  /**
   * indicates that the DecisionNodeType has outputs which should be mapped to decision flow or datagrid variables
   *
   * @return hasOutputs
   **/
  public Boolean isHasOutputs() {
    return hasOutputs;
  }

  public void setHasOutputs(Boolean hasOutputs) {
    this.hasOutputs = hasOutputs;
  }

  public DecisionNodeType inputDatagridMappable(Boolean inputDatagridMappable) {
    this.inputDatagridMappable = inputDatagridMappable;
    return this;
  }

  /**
   * indicates that the DecisionNodeType&#39;s inputs may be mapped to datagrid extensions
   *
   * @return inputDatagridMappable
   **/
  public Boolean isInputDatagridMappable() {
    return inputDatagridMappable;
  }

  public void setInputDatagridMappable(Boolean inputDatagridMappable) {
    this.inputDatagridMappable = inputDatagridMappable;
  }

  public DecisionNodeType outputDatagridMappable(Boolean outputDatagridMappable) {
    this.outputDatagridMappable = outputDatagridMappable;
    return this;
  }

  /**
   * indicates that the DecisionNodeType&#39;s outputs may be mapped to datagrid extensions
   *
   * @return outputDatagridMappable
   **/
  public Boolean isOutputDatagridMappable() {
    return outputDatagridMappable;
  }

  public void setOutputDatagridMappable(Boolean outputDatagridMappable) {
    this.outputDatagridMappable = outputDatagridMappable;
  }

  public DecisionNodeType inputDecisionTermMappable(Boolean inputDecisionTermMappable) {
    this.inputDecisionTermMappable = inputDecisionTermMappable;
    return this;
  }

  /**
   * indicates that the DecisionNodeType&#39;s inputs may be mapped to decision variables
   *
   * @return inputDecisionTermMappable
   **/
  public Boolean isInputDecisionTermMappable() {
    return inputDecisionTermMappable;
  }

  public void setInputDecisionTermMappable(Boolean inputDecisionTermMappable) {
    this.inputDecisionTermMappable = inputDecisionTermMappable;
  }

  public DecisionNodeType outputDecisionTermMappable(Boolean outputDecisionTermMappable) {
    this.outputDecisionTermMappable = outputDecisionTermMappable;
    return this;
  }

  /**
   * indicates that the DecisionNodeType&#39;s outputs may be mapped to decision variables
   *
   * @return outputDecisionTermMappable
   **/
  public Boolean isOutputDecisionTermMappable() {
    return outputDecisionTermMappable;
  }

  public void setOutputDecisionTermMappable(Boolean outputDecisionTermMappable) {
    this.outputDecisionTermMappable = outputDecisionTermMappable;
  }

  public DecisionNodeType independentMappings(Boolean independentMappings) {
    this.independentMappings = independentMappings;
    return this;
  }

  /**
   * indicates whether same-named inputs/outputs may be mapped independently
   *
   * @return independentMappings
   **/
  public Boolean isIndependentMappings() {
    return independentMappings;
  }

  public void setIndependentMappings(Boolean independentMappings) {
    this.independentMappings = independentMappings;
  }

  public DecisionNodeType themeId(String themeId) {
    this.themeId = themeId;
    return this;
  }

  /**
   * indicating a standard theme which will result in a unique icon and color.  The available themes are DNT_THEME1, DNT_THEME2, DNT_THEME3, DNT_THEME4, and DNT_THEME5
   *
   * @return themeId
   **/
  public String getThemeId() {
    return themeId;
  }

  public void setThemeId(String themeId) {
    this.themeId = themeId;
  }

  public DecisionNodeType type(String type) {
    this.type = type;
    return this;
  }

  /**
   * the type for this DecisionNodeType (static is only supported type as of now)
   *
   * @return type
   **/
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public DecisionNodeType createdBy(String createdBy) {
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

  public DecisionNodeType creationTimeStamp(OffsetDateTime creationTimeStamp) {
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

  public DecisionNodeType modifiedBy(String modifiedBy) {
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

  public DecisionNodeType modifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
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

  public DecisionNodeType links(List<Link> links) {
    this.links = links;
    return this;
  }

  public DecisionNodeType addLinksItem(Link linksItem) {
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

  public DecisionNodeType version(Integer version) {
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
    DecisionNodeType decisionNodeType = (DecisionNodeType) o;
    return Objects.equals(this.id, decisionNodeType.id) &&
            Objects.equals(this.name, decisionNodeType.name) &&
            Objects.equals(this.description, decisionNodeType.description) &&
            Objects.equals(this.hasProperties, decisionNodeType.hasProperties) &&
            Objects.equals(this.hasInputs, decisionNodeType.hasInputs) &&
            Objects.equals(this.hasOutputs, decisionNodeType.hasOutputs) &&
            Objects.equals(this.inputDatagridMappable, decisionNodeType.inputDatagridMappable) &&
            Objects.equals(this.outputDatagridMappable, decisionNodeType.outputDatagridMappable) &&
            Objects.equals(this.inputDecisionTermMappable, decisionNodeType.inputDecisionTermMappable) &&
            Objects.equals(this.outputDecisionTermMappable, decisionNodeType.outputDecisionTermMappable) &&
            Objects.equals(this.independentMappings, decisionNodeType.independentMappings) &&
            Objects.equals(this.themeId, decisionNodeType.themeId) &&
            Objects.equals(this.type, decisionNodeType.type) &&
            Objects.equals(this.createdBy, decisionNodeType.createdBy) &&
            Objects.equals(this.creationTimeStamp, decisionNodeType.creationTimeStamp) &&
            Objects.equals(this.modifiedBy, decisionNodeType.modifiedBy) &&
            Objects.equals(this.modifiedTimeStamp, decisionNodeType.modifiedTimeStamp) &&
            Objects.equals(this.links, decisionNodeType.links) &&
            Objects.equals(this.version, decisionNodeType.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, hasProperties, hasInputs, hasOutputs, inputDatagridMappable, outputDatagridMappable, inputDecisionTermMappable, outputDecisionTermMappable, independentMappings, themeId, type, createdBy, creationTimeStamp, modifiedBy, modifiedTimeStamp, links, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DecisionNodeType {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    hasProperties: ").append(toIndentedString(hasProperties)).append("\n");
    sb.append("    hasInputs: ").append(toIndentedString(hasInputs)).append("\n");
    sb.append("    hasOutputs: ").append(toIndentedString(hasOutputs)).append("\n");
    sb.append("    inputDatagridMappable: ").append(toIndentedString(inputDatagridMappable)).append("\n");
    sb.append("    outputDatagridMappable: ").append(toIndentedString(outputDatagridMappable)).append("\n");
    sb.append("    inputDecisionTermMappable: ").append(toIndentedString(inputDecisionTermMappable)).append("\n");
    sb.append("    outputDecisionTermMappable: ").append(toIndentedString(outputDecisionTermMappable)).append("\n");
    sb.append("    independentMappings: ").append(toIndentedString(independentMappings)).append("\n");
    sb.append("    themeId: ").append(toIndentedString(themeId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

