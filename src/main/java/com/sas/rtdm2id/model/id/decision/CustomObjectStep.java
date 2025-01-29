/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Objects;

/**
 * Represents a step containing a custom implemented step within the decision flow. Treatment groups, code files, and other decisions can be referenced using a custom object step.
 */
public class CustomObjectStep {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("uri")
  private String uri = null;

  @JsonProperty("type")
  private String type = null;

  public CustomObjectStep name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the custom object being referenced.
   *
   * @return name
   **/
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CustomObjectStep uri(String uri) {
    this.uri = uri;
    return this;
  }

  /**
   * Uri of the custom object that is being referenced. URIs to code files, treatment groups, and other decisions are currently supported.
   *
   * @return uri
   **/
  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public CustomObjectStep type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Type of object that is being referenced. Known supported types treatmentGroup, decision, decisionDs2CodeFile, decisionSQLCodeFile, and decisionPythonFile.
   *
   * @return type
   **/
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomObjectStep customObjectStep = (CustomObjectStep) o;
    return Objects.equals(this.name, customObjectStep.name) &&
//            Objects.equals(this.uri, customObjectStep.uri) &&
            Objects.equals(this.type, customObjectStep.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, uri, type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomObjectStep {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    uri: ").append(toIndentedString(uri)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

