/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
/*
 * Decisions
 * The Decisions API supports the life cycle of decision data.
 *
 * OpenAPI spec version: 7
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * Represents a step containing a custom implemented step within the decision flow. Treatment groups, code files, and other decisions can be referenced using a custom object step.
 */
@ApiModel(description = "Represents a step containing a custom implemented step within the decision flow. Treatment groups, code files, and other decisions can be referenced using a custom object step.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-11-06T21:19:27.553+03:00")


public class CustomObjectStep2 {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("uri")
  private String uri = null;

  @JsonProperty("type")
  private String type = null;

  public CustomObjectStep2 name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the custom object being referenced.
   *
   * @return name
   **/
  @ApiModelProperty(required = true, value = "Name of the custom object being referenced.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CustomObjectStep2 uri(String uri) {
    this.uri = uri;
    return this;
  }

  /**
   * Uri of the custom object that is being referenced. URIs to code files, treatment groups, and other decisions are currently supported.
   *
   * @return uri
   **/
  @ApiModelProperty(required = true, value = "Uri of the custom object that is being referenced. URIs to code files, treatment groups, and other decisions are currently supported.")
  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public CustomObjectStep2 type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Type of object that is being referenced. Known supported types treatmentGroup, decision, decisionDs2CodeFile, decisionSQLCodeFile, and decisionPythonFile.
   *
   * @return type
   **/
  @ApiModelProperty(value = "Type of object that is being referenced. Known supported types treatmentGroup, decision, decisionDs2CodeFile, decisionSQLCodeFile, and decisionPythonFile.")
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
    CustomObjectStep2 customObjectStep2 = (CustomObjectStep2) o;
    return Objects.equals(this.name, customObjectStep2.name) &&
            Objects.equals(this.uri, customObjectStep2.uri) &&
            Objects.equals(this.type, customObjectStep2.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, uri, type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomObjectStep2 {\n");

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

