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
 * Represents a compound condition.  The left hand side (lhs), right hand side (rhs) and operator of the condition are evaualted to produce a boolean result of \&quot;true\&quot; or \&quot;false\&quot;.  A compound condition has a left hand side (lhsSimpleCondition or lhsCompoundCondition) a right hand side (rhsSimpleCondition or rhsCompoundCondition) and a boolean operator.
 */
public class CompoundBranchCondition2 {
  @JsonProperty("id")
  private String id = null;

  /**
   * Specifies the booleanOperator of the condition.
   */
  public enum BooleanOperatorEnum {
    AND("AND"),

    OR("OR");

    private String value;

    BooleanOperatorEnum(String value) {
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
    public static BooleanOperatorEnum fromValue(String text) {
      for (BooleanOperatorEnum b : BooleanOperatorEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("booleanOperator")
  private BooleanOperatorEnum booleanOperator = null;

  @JsonProperty("lhsSimpleCondition")
  private SimpleBranchCondition2 lhsSimpleCondition = null;

  @JsonProperty("lhsCompoundCondition")
  private CompoundBranchCondition2 lhsCompoundCondition = null;

  @JsonProperty("rhsSimpleCondition")
  private SimpleBranchCondition2 rhsSimpleCondition = null;

  @JsonProperty("rhsCompoundCondition")
  private CompoundBranchCondition2 rhsCompoundCondition = null;

  public CompoundBranchCondition2 id(String id) {
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

  public CompoundBranchCondition2 booleanOperator(BooleanOperatorEnum booleanOperator) {
    this.booleanOperator = booleanOperator;
    return this;
  }

  /**
   * Specifies the booleanOperator of the condition.
   *
   * @return booleanOperator
   **/
  public BooleanOperatorEnum getBooleanOperator() {
    return booleanOperator;
  }

  public void setBooleanOperator(BooleanOperatorEnum booleanOperator) {
    this.booleanOperator = booleanOperator;
  }

  public CompoundBranchCondition2 lhsSimpleCondition(SimpleBranchCondition2 lhsSimpleCondition) {
    this.lhsSimpleCondition = lhsSimpleCondition;
    return this;
  }

  /**
   * Specifies lhsSimpleCondition of the condition to evaluate.  If a lhsSimpleCondition is present the lhsCompoundCondition will not be present.
   *
   * @return lhsSimpleCondition
   **/
  public SimpleBranchCondition2 getLhsSimpleCondition() {
    return lhsSimpleCondition;
  }

  public void setLhsSimpleCondition(SimpleBranchCondition2 lhsSimpleCondition) {
    this.lhsSimpleCondition = lhsSimpleCondition;
  }

  public CompoundBranchCondition2 lhsCompoundCondition(CompoundBranchCondition2 lhsCompoundCondition) {
    this.lhsCompoundCondition = lhsCompoundCondition;
    return this;
  }

  /**
   * Specifies lhsCompoundCondition of the condition to evaluate.  If a lhsCompoundCondition is present the lhsSimpleCondition will not be present.
   *
   * @return lhsCompoundCondition
   **/
  public CompoundBranchCondition2 getLhsCompoundCondition() {
    return lhsCompoundCondition;
  }

  public void setLhsCompoundCondition(CompoundBranchCondition2 lhsCompoundCondition) {
    this.lhsCompoundCondition = lhsCompoundCondition;
  }

  public CompoundBranchCondition2 rhsSimpleCondition(SimpleBranchCondition2 rhsSimpleCondition) {
    this.rhsSimpleCondition = rhsSimpleCondition;
    return this;
  }

  /**
   * Specifies rhsSimpleCondition of the condition to evaluate.  If a rhsSimpleCondition is present the rhsCompoundCondition will not be present.
   *
   * @return rhsSimpleCondition
   **/
  public SimpleBranchCondition2 getRhsSimpleCondition() {
    return rhsSimpleCondition;
  }

  public void setRhsSimpleCondition(SimpleBranchCondition2 rhsSimpleCondition) {
    this.rhsSimpleCondition = rhsSimpleCondition;
  }

  public CompoundBranchCondition2 rhsCompoundCondition(CompoundBranchCondition2 rhsCompoundCondition) {
    this.rhsCompoundCondition = rhsCompoundCondition;
    return this;
  }

  /**
   * Specifies rhsCompoundCondition of the condition to evaluate.  If a rhsCompoundCondition is present the rhsSimpleCondition will not be present.
   *
   * @return rhsCompoundCondition
   **/
  public CompoundBranchCondition2 getRhsCompoundCondition() {
    return rhsCompoundCondition;
  }

  public void setRhsCompoundCondition(CompoundBranchCondition2 rhsCompoundCondition) {
    this.rhsCompoundCondition = rhsCompoundCondition;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompoundBranchCondition2 compoundBranchCondition2 = (CompoundBranchCondition2) o;
    return Objects.equals(this.id, compoundBranchCondition2.id) &&
            Objects.equals(this.booleanOperator, compoundBranchCondition2.booleanOperator) &&
            Objects.equals(this.lhsSimpleCondition, compoundBranchCondition2.lhsSimpleCondition) &&
            Objects.equals(this.lhsCompoundCondition, compoundBranchCondition2.lhsCompoundCondition) &&
            Objects.equals(this.rhsSimpleCondition, compoundBranchCondition2.rhsSimpleCondition) &&
            Objects.equals(this.rhsCompoundCondition, compoundBranchCondition2.rhsCompoundCondition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, booleanOperator, lhsSimpleCondition, lhsCompoundCondition, rhsSimpleCondition, rhsCompoundCondition);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompoundBranchCondition2 {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    booleanOperator: ").append(toIndentedString(booleanOperator)).append("\n");
    sb.append("    lhsSimpleCondition: ").append(toIndentedString(lhsSimpleCondition)).append("\n");
    sb.append("    lhsCompoundCondition: ").append(toIndentedString(lhsCompoundCondition)).append("\n");
    sb.append("    rhsSimpleCondition: ").append(toIndentedString(rhsSimpleCondition)).append("\n");
    sb.append("    rhsCompoundCondition: ").append(toIndentedString(rhsCompoundCondition)).append("\n");
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

