/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents a condition step within a decision flow.
 */
public class ConditionStep {
  @JsonProperty("lhsTerm")
  private Term lhsTerm = null;

  @JsonProperty("rhsTerm")
  private Term rhsTerm = null;

  @JsonProperty("rhsConstant")
  private String rhsConstant = null;

  @JsonProperty("operator")
  private String operator = null;

  @JsonProperty("onTrue")
  private ConditionBranch onTrue = null;

  @JsonProperty("onFalse")
  private ConditionBranch onFalse = null;

  public ConditionStep lhsTerm(Term lhsTerm) {
    this.lhsTerm = lhsTerm;
    return this;
  }

  /**
   * Term to compare the rhsTerm or rhsConstant to.
   *
   * @return lhsTerm
   **/
  public Term getLhsTerm() {
    return lhsTerm;
  }

  public void setLhsTerm(Term lhsTerm) {
    this.lhsTerm = lhsTerm;
  }

  public ConditionStep rhsTerm(Term rhsTerm) {
    this.rhsTerm = rhsTerm;
    return this;
  }

  /**
   * Term to compare the lhsTerm to.
   *
   * @return rhsTerm
   **/
  public Term getRhsTerm() {
    return rhsTerm;
  }

  public void setRhsTerm(Term rhsTerm) {
    this.rhsTerm = rhsTerm;
  }

  public ConditionStep rhsConstant(String rhsConstant) {
    this.rhsConstant = rhsConstant;
    return this;
  }

  /**
   * Specifies the constant to compare the lhsTerm to.
   *
   * @return rhsConstant
   **/
  public String getRhsConstant() {
    return rhsConstant;
  }

  public void setRhsConstant(String rhsConstant) {
    this.rhsConstant = rhsConstant;
  }

  public ConditionStep operator(String operator) {
    this.operator = operator;
    return this;
  }

  /**
   * Specifies the comparison operation to use when to compare the lhsTerm to rhsTerm or rhsConstant.
   *
   * @return operator
   **/
  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public ConditionStep onTrue(ConditionBranch onTrue) {
    this.onTrue = onTrue;
    return this;
  }

  /**
   * Steps to execute if the provided condition evaluates to true.
   *
   * @return onTrue
   **/
  public ConditionBranch getOnTrue() {
    return onTrue;
  }

  public void setOnTrue(ConditionBranch onTrue) {
    this.onTrue = onTrue;
  }

  public ConditionStep onFalse(ConditionBranch onFalse) {
    this.onFalse = onFalse;
    return this;
  }

  /**
   * Steps to execute if the provided condition evaluates to false.
   *
   * @return onFalse
   **/
  public ConditionBranch getOnFalse() {
    return onFalse;
  }

  public void setOnFalse(ConditionBranch onFalse) {
    this.onFalse = onFalse;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConditionStep conditionStep = (ConditionStep) o;
    return Objects.equals(this.lhsTerm, conditionStep.lhsTerm) &&
            Objects.equals(this.rhsTerm, conditionStep.rhsTerm) &&
            Objects.equals(this.rhsConstant, conditionStep.rhsConstant) &&
            Objects.equals(this.operator, conditionStep.operator) &&
            Objects.equals(this.onTrue, conditionStep.onTrue) &&
            Objects.equals(this.onFalse, conditionStep.onFalse);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lhsTerm, rhsTerm, rhsConstant, operator, onTrue, onFalse);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConditionStep {\n");

    sb.append("    lhsTerm: ").append(toIndentedString(lhsTerm)).append("\n");
    sb.append("    rhsTerm: ").append(toIndentedString(rhsTerm)).append("\n");
    sb.append("    rhsConstant: ").append(toIndentedString(rhsConstant)).append("\n");
    sb.append("    operator: ").append(toIndentedString(operator)).append("\n");
    sb.append("    onTrue: ").append(toIndentedString(onTrue)).append("\n");
    sb.append("    onFalse: ").append(toIndentedString(onFalse)).append("\n");
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

