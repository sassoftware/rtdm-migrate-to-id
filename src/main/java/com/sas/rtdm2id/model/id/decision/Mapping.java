/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Represents the mapping between the terms input and output by a decision step and the signature entries of the decision.
 */
public class Mapping {
  @JsonProperty("stepTermName")
  private String stepTermName = null;

  /**
   * Describes whether mapping is an input, output, or both from the point of view of the decision step.
   */
  public enum DirectionEnum {
    INPUT("input"),

    OUTPUT("output"),

    INOUT("inOut");

    private String value;

    DirectionEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DirectionEnum fromValue(String text) {
      for (DirectionEnum b : DirectionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("direction")
  private DirectionEnum direction = null;

  @JsonProperty("targetDecisionTermName")
  private String targetDecisionTermName = null;

  @JsonProperty("targetDataGridTermName")
  private String targetDataGridTermName = null;

  public Mapping stepTermName(String stepTermName) {
    this.stepTermName = stepTermName;
    return this;
  }

  /**
   * The name of the step term to which the decision signature entry is mapped.
   *
   * @return stepTermName
   **/
  public String getStepTermName() {
    return stepTermName;
  }

  public void setStepTermName(String stepTermName) {
    this.stepTermName = stepTermName;
  }

  public Mapping direction(DirectionEnum direction) {
    this.direction = direction;
    return this;
  }

  /**
   * Describes whether mapping is an input, output, or both from the point of view of the decision step.
   *
   * @return direction
   **/
  public DirectionEnum getDirection() {
    return direction;
  }

  public void setDirection(DirectionEnum direction) {
    this.direction = direction;
  }

  public Mapping targetDecisionTermName(String targetDecisionTermName) {
    this.targetDecisionTermName = targetDecisionTermName;
    return this;
  }

  /**
   * The name of the decision signature entry to which a step term will be mapped.
   *
   * @return targetDecisionTermName
   **/
  public String getTargetDecisionTermName() {
    return targetDecisionTermName;
  }

  public void setTargetDecisionTermName(String targetDecisionTermName) {
    this.targetDecisionTermName = targetDecisionTermName;
  }

  public Mapping targetDataGridTermName(String targetDataGridTermName) {
    this.targetDataGridTermName = targetDataGridTermName;
    return this;
  }

  /**
   * The name of the term inside the mappedDataGrid term to which a step term will be mapped.
   *
   * @return targetDataGridTermName
   **/
  public String getTargetDataGridTermName() {
    return targetDataGridTermName;
  }

  public void setTargetDataGridTermName(String targetDataGridTermName) {
    this.targetDataGridTermName = targetDataGridTermName;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Mapping mapping = (Mapping) o;
    return Objects.equals(this.stepTermName, mapping.stepTermName) &&
            Objects.equals(this.direction, mapping.direction) &&
            Objects.equals(this.targetDecisionTermName, mapping.targetDecisionTermName) &&
            Objects.equals(this.targetDataGridTermName, mapping.targetDataGridTermName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(stepTermName, direction, targetDecisionTermName, targetDataGridTermName);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Mapping {\n");

    sb.append("    stepTermName: ").append(toIndentedString(stepTermName)).append("\n");
    sb.append("    direction: ").append(toIndentedString(direction)).append("\n");
    sb.append("    targetDecisionTermName: ").append(toIndentedString(targetDecisionTermName)).append("\n");
    sb.append("    targetDataGridTermName: ").append(toIndentedString(targetDataGridTermName)).append("\n");
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

