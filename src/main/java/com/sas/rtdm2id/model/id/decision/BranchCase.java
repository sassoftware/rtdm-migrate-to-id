/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents a single case within a branch step of a decision flow.  A case may contain a simpleCondition or compoundCondition, but not both.
 */
public class BranchCase {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("label")
  private String label = null;

  @JsonProperty("simpleCondition")
  private SimpleBranchCondition simpleCondition = null;

  @JsonProperty("compoundCondition")
  private CompoundBranchCondition compoundCondition = null;

  @JsonProperty("onTrue")
  private ConditionBranch onTrue = null;

  public BranchCase id(String id) {
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

  public BranchCase label(String label) {
    this.label = label;
    return this;
  }

  /**
   * A label for the branch case.
   *
   * @return label
   **/
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public BranchCase simpleCondition(SimpleBranchCondition simpleCondition) {
    this.simpleCondition = simpleCondition;
    return this;
  }

  /**
   * Specifies a simpleCondition to evaluate.  A simpleCondition must be defined if compoundCondition is not defined.  When executing the step if the condition evaluates true the onTrue \&quot;steps\&quot; path is followed.
   *
   * @return simpleCondition
   **/
  public SimpleBranchCondition getSimpleCondition() {
    return simpleCondition;
  }

  public void setSimpleCondition(SimpleBranchCondition simpleCondition) {
    this.simpleCondition = simpleCondition;
  }

  public BranchCase compoundCondition(CompoundBranchCondition compoundCondition) {
    this.compoundCondition = compoundCondition;
    return this;
  }

  /**
   * Specifies a compoundCondition to evaluate. A compoundCondition must be defined if simpleCondition is not defined.  When executing the step if the condition evaluates true the onTrue \&quot;steps\&quot; path is followed.
   *
   * @return compoundCondition
   **/
  public CompoundBranchCondition getCompoundCondition() {
    return compoundCondition;
  }

  public void setCompoundCondition(CompoundBranchCondition compoundCondition) {
    this.compoundCondition = compoundCondition;
  }

  public BranchCase onTrue(ConditionBranch onTrue) {
    this.onTrue = onTrue;
    return this;
  }

  /**
   * Specifies the steps to execute if the simpleCondition or compoundCondition evaluates to true.
   *
   * @return onTrue
   **/
  public ConditionBranch getOnTrue() {
    return onTrue;
  }

  public void setOnTrue(ConditionBranch onTrue) {
    this.onTrue = onTrue;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BranchCase branchCase = (BranchCase) o;
    return Objects.equals(this.id, branchCase.id) &&
            Objects.equals(this.label, branchCase.label) &&
            Objects.equals(this.simpleCondition, branchCase.simpleCondition) &&
            Objects.equals(this.compoundCondition, branchCase.compoundCondition) &&
            Objects.equals(this.onTrue, branchCase.onTrue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, label, simpleCondition, compoundCondition, onTrue);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BranchCase {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    simpleCondition: ").append(toIndentedString(simpleCondition)).append("\n");
    sb.append("    compoundCondition: ").append(toIndentedString(compoundCondition)).append("\n");
    sb.append("    onTrue: ").append(toIndentedString(onTrue)).append("\n");
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

