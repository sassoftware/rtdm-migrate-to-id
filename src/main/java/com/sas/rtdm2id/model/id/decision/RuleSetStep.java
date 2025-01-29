/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Specifies the rule set that is being referenced for this step. Required if type is set to &#39;application/vnd.sas.decision.step.ruleset&#39;.
 */
public class RuleSetStep {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("versionId")
  private String versionId = null;

  @JsonProperty("versionName")
  private String versionName = null;

  public RuleSetStep name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the Rule Set being referenced.
   *
   * @return name
   **/
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RuleSetStep id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of the rule set that is being referenced.
   *
   * @return id
   **/
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public RuleSetStep versionId(String versionId) {
    this.versionId = versionId;
    return this;
  }

  /**
   * Identifier of the rule set version that is being referenced.
   *
   * @return versionId
   **/
  public String getVersionId() {
    return versionId;
  }

  public void setVersionId(String versionId) {
    this.versionId = versionId;
  }

  public RuleSetStep versionName(String versionName) {
    this.versionName = versionName;
    return this;
  }

  /**
   * Name of the version that is being referenced.
   *
   * @return versionName
   **/
  public String getVersionName() {
    return versionName;
  }

  public void setVersionName(String versionName) {
    this.versionName = versionName;
  }


  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
      RuleSetStep ruleSetStep = (RuleSetStep) o;
      return Objects.equals(this.name, ruleSetStep.name);
//            && Objects.equals(this.id, ruleSetStep.id)
//            && Objects.equals(this.versionId, ruleSetStep.versionId)
//            && Objects.equals(this.versionName, ruleSetStep.versionName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id, versionId, versionName);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RuleSetStep {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    versionId: ").append(toIndentedString(versionId)).append("\n");
    sb.append("    versionName: ").append(toIndentedString(versionName)).append("\n");
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

