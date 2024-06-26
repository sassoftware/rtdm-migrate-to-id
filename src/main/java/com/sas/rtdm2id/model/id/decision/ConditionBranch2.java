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
 * Represents a branch of a decision condition.
 */
public class ConditionBranch2 {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("createdBy")
  private String createdBy = null;

  @JsonProperty("creationTimeStamp")
  private OffsetDateTime creationTimeStamp = null;

  @JsonProperty("modifiedBy")
  private String modifiedBy = null;

  @JsonProperty("modifiedTimeStamp")
  private OffsetDateTime modifiedTimeStamp = null;

  @JsonProperty("steps")
  private List<Step2> steps = null;

  public ConditionBranch2 id(String id) {
    this.id = id;
    return this;
  }

  /**
   * The system-assigned unique ID for this object
   *
   * @return id
   **/
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ConditionBranch2 createdBy(String createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  /**
   * The user who created the onTrue path.
   *
   * @return createdBy
   **/
  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public ConditionBranch2 creationTimeStamp(OffsetDateTime creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
    return this;
  }

  /**
   * The date and time that the onTrue path was created.
   *
   * @return creationTimeStamp
   **/
  public OffsetDateTime getCreationTimeStamp() {
    return creationTimeStamp;
  }

  public void setCreationTimeStamp(OffsetDateTime creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
  }

  public ConditionBranch2 modifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
    return this;
  }

  /**
   * The userId of the authenticated user who last updated the onTrue path.
   *
   * @return modifiedBy
   **/
  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public ConditionBranch2 modifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
    this.modifiedTimeStamp = modifiedTimeStamp;
    return this;
  }

  /**
   * The date and time that the onTrue path was last modified.
   *
   * @return modifiedTimeStamp
   **/
  public OffsetDateTime getModifiedTimeStamp() {
    return modifiedTimeStamp;
  }

  public void setModifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
    this.modifiedTimeStamp = modifiedTimeStamp;
  }

  public ConditionBranch2 steps(List<Step2> steps) {
    this.steps = steps;
    return this;
  }

  public ConditionBranch2 addStepsItem(Step2 stepsItem) {
    if (this.steps == null) {
      this.steps = new ArrayList<>();
    }
    this.steps.add(stepsItem);
    return this;
  }

  /**
   * Get steps
   *
   * @return steps
   **/
  public List<Step2> getSteps() {
    return steps;
  }

  public void setSteps(List<Step2> steps) {
    this.steps = steps;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConditionBranch2 conditionBranch2 = (ConditionBranch2) o;
    return Objects.equals(this.id, conditionBranch2.id) &&
            Objects.equals(this.createdBy, conditionBranch2.createdBy) &&
            Objects.equals(this.creationTimeStamp, conditionBranch2.creationTimeStamp) &&
            Objects.equals(this.modifiedBy, conditionBranch2.modifiedBy) &&
            Objects.equals(this.modifiedTimeStamp, conditionBranch2.modifiedTimeStamp) &&
            Objects.equals(this.steps, conditionBranch2.steps);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdBy, creationTimeStamp, modifiedBy, modifiedTimeStamp, steps);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConditionBranch2 {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
    sb.append("    creationTimeStamp: ").append(toIndentedString(creationTimeStamp)).append("\n");
    sb.append("    modifiedBy: ").append(toIndentedString(modifiedBy)).append("\n");
    sb.append("    modifiedTimeStamp: ").append(toIndentedString(modifiedTimeStamp)).append("\n");
    sb.append("    steps: ").append(toIndentedString(steps)).append("\n");
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

