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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The representation of an error.
 */
@ApiModel(description = "The representation of an error.")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-11-06T21:19:27.553+03:00")


public class Error2 {
  @JsonProperty("message")
  private String message = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("errorCode")
  private Integer errorCode = null;

  @JsonProperty("httpStatusCode")
  private Integer httpStatusCode = null;

  @JsonProperty("details")
  private List<String> details = null;

  @JsonProperty("remediation")
  private String remediation = null;

  @JsonProperty("errors")
  private List<Error2> errors = null;

  @JsonProperty("links")
  private List<Link> links = null;

  @JsonProperty("version")
  private Integer version = null;

  public Error2 message(String message) {
    this.message = message;
    return this;
  }

  /**
   * The message for the error.
   *
   * @return message
   **/
  @ApiModelProperty(value = "The message for the error.")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Error2 id(String id) {
    this.id = id;
    return this;
  }

  /**
   * The string ID for the error.
   *
   * @return id
   **/
  @ApiModelProperty(value = "The string ID for the error.")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Error2 errorCode(Integer errorCode) {
    this.errorCode = errorCode;
    return this;
  }

  /**
   * The numeric ID for the error.
   *
   * @return errorCode
   **/
  @ApiModelProperty(value = "The numeric ID for the error.")
  public Integer getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(Integer errorCode) {
    this.errorCode = errorCode;
  }

  public Error2 httpStatusCode(Integer httpStatusCode) {
    this.httpStatusCode = httpStatusCode;
    return this;
  }

  /**
   * The HTTP status code for the error.
   *
   * @return httpStatusCode
   **/
  @ApiModelProperty(required = true, value = "The HTTP status code for the error.")
  public Integer getHttpStatusCode() {
    return httpStatusCode;
  }

  public void setHttpStatusCode(Integer httpStatusCode) {
    this.httpStatusCode = httpStatusCode;
  }

  public Error2 details(List<String> details) {
    this.details = details;
    return this;
  }

  public Error2 addDetailsItem(String detailsItem) {
    if (this.details == null) {
      this.details = new ArrayList<>();
    }
    this.details.add(detailsItem);
    return this;
  }

  /**
   * Messages that provide additional details about the cause of the error.
   *
   * @return details
   **/
  @ApiModelProperty(value = "Messages that provide additional details about the cause of the error.")
  public List<String> getDetails() {
    return details;
  }

  public void setDetails(List<String> details) {
    this.details = details;
  }

  public Error2 remediation(String remediation) {
    this.remediation = remediation;
    return this;
  }

  /**
   * A message that describes how to resolve the error.
   *
   * @return remediation
   **/
  @ApiModelProperty(value = "A message that describes how to resolve the error.")
  public String getRemediation() {
    return remediation;
  }

  public void setRemediation(String remediation) {
    this.remediation = remediation;
  }

  public Error2 errors(List<Error2> errors) {
    this.errors = errors;
    return this;
  }

  public Error2 addErrorsItem(Error2 errorsItem) {
    if (this.errors == null) {
      this.errors = new ArrayList<>();
    }
    this.errors.add(errorsItem);
    return this;
  }

  /**
   * Any additional errors that occurred.
   *
   * @return errors
   **/
  @ApiModelProperty(value = "Any additional errors that occurred.")
  public List<Error2> getErrors() {
    return errors;
  }

  public void setErrors(List<Error2> errors) {
    this.errors = errors;
  }

  public Error2 links(List<Link> links) {
    this.links = links;
    return this;
  }

  public Error2 addLinksItem(Link linksItem) {
    if (this.links == null) {
      this.links = new ArrayList<>();
    }
    this.links.add(linksItem);
    return this;
  }

  /**
   * The links that apply to the error.
   *
   * @return links
   **/
  @ApiModelProperty(value = "The links that apply to the error.")
  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public Error2 version(Integer version) {
    this.version = version;
    return this;
  }

  /**
   * The version number of the error representation. This representation is version 2.
   *
   * @return version
   **/
  @ApiModelProperty(required = true, value = "The version number of the error representation. This representation is version 2.")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Error2 error2 = (Error2) o;
    return Objects.equals(this.message, error2.message) &&
            Objects.equals(this.id, error2.id) &&
            Objects.equals(this.errorCode, error2.errorCode) &&
            Objects.equals(this.httpStatusCode, error2.httpStatusCode) &&
            Objects.equals(this.details, error2.details) &&
            Objects.equals(this.remediation, error2.remediation) &&
            Objects.equals(this.errors, error2.errors) &&
            Objects.equals(this.links, error2.links) &&
            Objects.equals(this.version, error2.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, id, errorCode, httpStatusCode, details, remediation, errors, links, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error2 {\n");

    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
    sb.append("    httpStatusCode: ").append(toIndentedString(httpStatusCode)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
    sb.append("    remediation: ").append(toIndentedString(remediation)).append("\n");
    sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

