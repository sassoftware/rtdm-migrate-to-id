/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents a link withing a decision flow which directs the execution flow of the decision to an arbitrary node based on the linkLabel of the target node.
 */
public class DecisionNodeLinkStep {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("decisionNodeLinkTarget")
  private String decisionNodeLinkTarget = null;

  public DecisionNodeLinkStep id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of the decisionNodeLinkStep that is being referenced.
   *
   * @return id
   **/
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DecisionNodeLinkStep decisionNodeLinkTarget(String decisionNodeLinkTarget) {
    this.decisionNodeLinkTarget = decisionNodeLinkTarget;
    return this;
  }

  /**
   * The linkLabel of the node to which execution flow should be directed.
   *
   * @return decisionNodeLinkTarget
   **/
  public String getDecisionNodeLinkTarget() {
    return decisionNodeLinkTarget;
  }

  public void setDecisionNodeLinkTarget(String decisionNodeLinkTarget) {
    this.decisionNodeLinkTarget = decisionNodeLinkTarget;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DecisionNodeLinkStep decisionNodeLinkStep = (DecisionNodeLinkStep) o;
    return Objects.equals(this.id, decisionNodeLinkStep.id) &&
            Objects.equals(this.decisionNodeLinkTarget, decisionNodeLinkStep.decisionNodeLinkTarget);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, decisionNodeLinkTarget);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DecisionNodeLinkStep {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    decisionNodeLinkTarget: ").append(toIndentedString(decisionNodeLinkTarget)).append("\n");
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

