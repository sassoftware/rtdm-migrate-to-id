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
public class ConditionStep2 {
  @JsonProperty("lhsTerm")
  private Term2 lhsTerm = null;

  @JsonProperty("rhsTerm")
  private Term2 rhsTerm = null;

  @JsonProperty("rhsConstant")
  private String rhsConstant = null;

  @JsonProperty("operator")
  private String operator = null;

  @JsonProperty("onTrue")
  private ConditionBranch2 onTrue = null;

  @JsonProperty("onFalse")
  private ConditionBranch2 onFalse = null;

  public ConditionStep2 lhsTerm(Term2 lhsTerm) {
    this.lhsTerm = lhsTerm;
    return this;
  }

  /**
   * Term to compare the rhsTerm or rhsConstant to.
   *
   * @return lhsTerm
   **/
  public Term2 getLhsTerm() {
    return lhsTerm;
  }

  public void setLhsTerm(Term2 lhsTerm) {
    this.lhsTerm = lhsTerm;
  }

  public ConditionStep2 rhsTerm(Term2 rhsTerm) {
    this.rhsTerm = rhsTerm;
    return this;
  }

  /**
   * Term to compare the lhsTerm to.
   *
   * @return rhsTerm
   **/
  public Term2 getRhsTerm() {
    return rhsTerm;
  }

  public void setRhsTerm(Term2 rhsTerm) {
    this.rhsTerm = rhsTerm;
  }

  public ConditionStep2 rhsConstant(String rhsConstant) {
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

  public ConditionStep2 operator(String operator) {
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

  public ConditionStep2 onTrue(ConditionBranch2 onTrue) {
    this.onTrue = onTrue;
    return this;
  }

  /**
   * Steps to execute if the provided condition evaluates to true.
   *
   * @return onTrue
   **/
  public ConditionBranch2 getOnTrue() {
    return onTrue;
  }

  public void setOnTrue(ConditionBranch2 onTrue) {
    this.onTrue = onTrue;
  }

  public ConditionStep2 onFalse(ConditionBranch2 onFalse) {
    this.onFalse = onFalse;
    return this;
  }

  /**
   * Steps to execute if the provided condition evaluates to false.
   *
   * @return onFalse
   **/
  public ConditionBranch2 getOnFalse() {
    return onFalse;
  }

  public void setOnFalse(ConditionBranch2 onFalse) {
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
    ConditionStep2 conditionStep2 = (ConditionStep2) o;
    return Objects.equals(this.lhsTerm, conditionStep2.lhsTerm) &&
            Objects.equals(this.rhsTerm, conditionStep2.rhsTerm) &&
            Objects.equals(this.rhsConstant, conditionStep2.rhsConstant) &&
            Objects.equals(this.operator, conditionStep2.operator) &&
            Objects.equals(this.onTrue, conditionStep2.onTrue) &&
            Objects.equals(this.onFalse, conditionStep2.onFalse);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lhsTerm, rhsTerm, rhsConstant, operator, onTrue, onFalse);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConditionStep2 {\n");

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

