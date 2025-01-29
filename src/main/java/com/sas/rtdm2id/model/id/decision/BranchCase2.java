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
public class BranchCase2 {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("label")
  private String label = null;

  @JsonProperty("simpleCondition")
  private SimpleBranchCondition2 simpleCondition = null;

  @JsonProperty("compoundCondition")
  private CompoundBranchCondition2 compoundCondition = null;

  @JsonProperty("onTrue")
  private ConditionBranch2 onTrue = null;

  public BranchCase2 id(String id) {
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

  public BranchCase2 label(String label) {
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

  public BranchCase2 simpleCondition(SimpleBranchCondition2 simpleCondition) {
    this.simpleCondition = simpleCondition;
    return this;
  }

  /**
   * Specifies a simpleCondition to evaluate.  A simpleCondition must be defined if compoundCondition is not defined.  When executing the step if the condition evaluates true the onTrue \&quot;steps\&quot; path is followed.
   *
   * @return simpleCondition
   **/
  public SimpleBranchCondition2 getSimpleCondition() {
    return simpleCondition;
  }

  public void setSimpleCondition(SimpleBranchCondition2 simpleCondition) {
    this.simpleCondition = simpleCondition;
  }

  public BranchCase2 compoundCondition(CompoundBranchCondition2 compoundCondition) {
    this.compoundCondition = compoundCondition;
    return this;
  }

  /**
   * Specifies a compoundCondition to evaluate. A compoundCondition must be defined if simpleCondition is not defined.  When executing the step if the condition evaluates true the onTrue \&quot;steps\&quot; path is followed.
   *
   * @return compoundCondition
   **/
  public CompoundBranchCondition2 getCompoundCondition() {
    return compoundCondition;
  }

  public void setCompoundCondition(CompoundBranchCondition2 compoundCondition) {
    this.compoundCondition = compoundCondition;
  }

  public BranchCase2 onTrue(ConditionBranch2 onTrue) {
    this.onTrue = onTrue;
    return this;
  }

  /**
   * Specifies the steps to execute if the simpleCondition or compoundCondition evaluates to true.
   *
   * @return onTrue
   **/
  public ConditionBranch2 getOnTrue() {
    return onTrue;
  }

  public void setOnTrue(ConditionBranch2 onTrue) {
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
    BranchCase2 branchCase2 = (BranchCase2) o;
    return Objects.equals(this.id, branchCase2.id) &&
            Objects.equals(this.label, branchCase2.label) &&
            Objects.equals(this.simpleCondition, branchCase2.simpleCondition) &&
            Objects.equals(this.compoundCondition, branchCase2.compoundCondition) &&
            Objects.equals(this.onTrue, branchCase2.onTrue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, label, simpleCondition, compoundCondition, onTrue);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BranchCase2 {\n");

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

