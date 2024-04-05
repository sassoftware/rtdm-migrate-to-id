/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents a simple condition.  The left hand side (lhs), right hand side (rhs) and operator of the condition are evaualted to produce a boolean result of \&quot;true\&quot; or \&quot;false\&quot;.
 */
public class SimpleBranchCondition2 {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("lhsTerm")
  private Term2 lhsTerm = null;

  @JsonProperty("rhsTerm")
  private Term2 rhsTerm = null;

  @JsonProperty("rhsConstant")
  private String rhsConstant = null;

  @JsonProperty("operator")
  private String operator = null;

  public SimpleBranchCondition2 id(String id) {
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

  public SimpleBranchCondition2 lhsTerm(Term2 lhsTerm) {
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

  public SimpleBranchCondition2 rhsTerm(Term2 rhsTerm) {
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

  public SimpleBranchCondition2 rhsConstant(String rhsConstant) {
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

  public SimpleBranchCondition2 operator(String operator) {
    this.operator = operator;
    return this;
  }

  /**
   * Specifies the comparison operation or operator to use when to compare the lhsTerm to rhsTerm or rhsConstant.
   *
   * @return operator
   **/
  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SimpleBranchCondition2 simpleBranchCondition2 = (SimpleBranchCondition2) o;
    return Objects.equals(this.id, simpleBranchCondition2.id) &&
            Objects.equals(this.lhsTerm, simpleBranchCondition2.lhsTerm) &&
            Objects.equals(this.rhsTerm, simpleBranchCondition2.rhsTerm) &&
            Objects.equals(this.rhsConstant, simpleBranchCondition2.rhsConstant) &&
            Objects.equals(this.operator, simpleBranchCondition2.operator);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, lhsTerm, rhsTerm, rhsConstant, operator);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SimpleBranchCondition2 {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    lhsTerm: ").append(toIndentedString(lhsTerm)).append("\n");
    sb.append("    rhsTerm: ").append(toIndentedString(rhsTerm)).append("\n");
    sb.append("    rhsConstant: ").append(toIndentedString(rhsConstant)).append("\n");
    sb.append("    operator: ").append(toIndentedString(operator)).append("\n");
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

