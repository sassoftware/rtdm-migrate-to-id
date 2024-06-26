/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * A link to a related operation or resource.
 */
public class Link {
  @JsonProperty("method")
  private String method = null;

  @JsonProperty("rel")
  private String rel = null;

  @JsonProperty("uri")
  private String uri = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("title")
  private String title = null;

  @JsonProperty("type")
  private String type = null;

  @JsonProperty("itemType")
  private String itemType = null;

  @JsonProperty("responseType")
  private String responseType = null;

  @JsonProperty("responseItemType")
  private String responseItemType = null;

  public Link method(String method) {
    this.method = method;
    return this;
  }

  /**
   * The HTTP method for the link.
   *
   * @return method
   **/
  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public Link rel(String rel) {
    this.rel = rel;
    return this;
  }

  /**
   * The relationship of the link to the resource.
   *
   * @return rel
   **/
  public String getRel() {
    return rel;
  }

  public void setRel(String rel) {
    this.rel = rel;
  }

  public Link uri(String uri) {
    this.uri = uri;
    return this;
  }

  /**
   * The relative URI for the link.
   *
   * @return uri
   **/
  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public Link href(String href) {
    this.href = href;
    return this;
  }

  /**
   * The URL for the link.
   *
   * @return href
   **/
  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public Link title(String title) {
    this.title = title;
    return this;
  }

  /**
   * The title for the link.
   *
   * @return title
   **/
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Link type(String type) {
    this.type = type;
    return this;
  }

  /**
   * The media type or link type for the link.
   *
   * @return type
   **/
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Link itemType(String itemType) {
    this.itemType = itemType;
    return this;
  }

  /**
   * If this is a link to a container, &#x60;itemType&#x60; is the media type or link type for the items in the container.
   *
   * @return itemType
   **/
  public String getItemType() {
    return itemType;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public Link responseType(String responseType) {
    this.responseType = responseType;
    return this;
  }

  /**
   * The media type or link type of the response body for a &#x60;PUT&#x60;, &#x60;POST&#x60;, or &#x60;PATCH&#x60; operation.
   *
   * @return responseType
   **/
  public String getResponseType() {
    return responseType;
  }

  public void setResponseType(String responseType) {
    this.responseType = responseType;
  }

  public Link responseItemType(String responseItemType) {
    this.responseItemType = responseItemType;
    return this;
  }

  /**
   * The media type or link type of the items in the response body for a &#x60;PUT&#x60;, &#x60;POST&#x60;, or &#x60;PATCH&#x60; operation.
   *
   * @return responseItemType
   **/
  public String getResponseItemType() {
    return responseItemType;
  }

  public void setResponseItemType(String responseItemType) {
    this.responseItemType = responseItemType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Link link = (Link) o;
    return Objects.equals(this.method, link.method) &&
            Objects.equals(this.rel, link.rel) &&
            Objects.equals(this.uri, link.uri) &&
            Objects.equals(this.href, link.href) &&
            Objects.equals(this.title, link.title) &&
            Objects.equals(this.type, link.type) &&
            Objects.equals(this.itemType, link.itemType) &&
            Objects.equals(this.responseType, link.responseType) &&
            Objects.equals(this.responseItemType, link.responseItemType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(method, rel, uri, href, title, type, itemType, responseType, responseItemType);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Link {\n");

    sb.append("    method: ").append(toIndentedString(method)).append("\n");
    sb.append("    rel: ").append(toIndentedString(rel)).append("\n");
    sb.append("    uri: ").append(toIndentedString(uri)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    itemType: ").append(toIndentedString(itemType)).append("\n");
    sb.append("    responseType: ").append(toIndentedString(responseType)).append("\n");
    sb.append("    responseItemType: ").append(toIndentedString(responseItemType)).append("\n");
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

