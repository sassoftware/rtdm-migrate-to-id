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
 * Represents a term that is being recorded for auditing purposes during contact recording.
 */
@ApiModel(description = "Represents a term that is being recorded for auditing purposes during contact recording.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-11-06T21:19:27.553+03:00")


public class AuditTerm2 {
  @JsonProperty("name")
  private String name = null;

  public AuditTerm2 name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the term that is being referenced from the signature.
   *
   * @return name
   **/
  @ApiModelProperty(required = true, value = "Name of the term that is being referenced from the signature.")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuditTerm2 auditTerm2 = (AuditTerm2) o;
    return Objects.equals(this.name, auditTerm2.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuditTerm2 {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
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

