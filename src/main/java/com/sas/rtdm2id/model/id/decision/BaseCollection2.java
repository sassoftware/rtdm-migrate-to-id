/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This is a base schema used to define paginated collections of resources. This base schema is extended by other schemas in APIs by adding an &#39;items&#39; array property. These extensions define the application/vnd.sas.collection media type (version 2)
 */
public class BaseCollection2 {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("start")
  private Long start = null;

  @JsonProperty("limit")
  private Integer limit = null;

  @JsonProperty("count")
  private Long count = null;

  @JsonProperty("accept")
  private String accept = null;

  @JsonProperty("links")
  private List<Link> links = null;

  @JsonProperty("version")
  private Integer version = null;

  public BaseCollection2 name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the collection.
   *
   * @return name
   **/
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BaseCollection2 start(Long start) {
    this.start = start;
    return this;
  }

  /**
   * The zero-based index of the first item in the collection.
   *
   * @return start
   **/
  public Long getStart() {
    return start;
  }

  public void setStart(Long start) {
    this.start = start;
  }

  public BaseCollection2 limit(Integer limit) {
    this.limit = limit;
    return this;
  }

  /**
   * The number of items that were requested for the collection.
   *
   * @return limit
   **/
  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public BaseCollection2 count(Long count) {
    this.count = count;
    return this;
  }

  /**
   * If populated indicates the number of items in the collection.
   *
   * @return count
   **/
  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  public BaseCollection2 accept(String accept) {
    this.accept = accept;
    return this;
  }

  /**
   * A space-delimited list of media types from which an &#x60;Accept&#x60; header may be constructed.
   *
   * @return accept
   **/
  public String getAccept() {
    return accept;
  }

  public void setAccept(String accept) {
    this.accept = accept;
  }

  public BaseCollection2 links(List<Link> links) {
    this.links = links;
    return this;
  }

  public BaseCollection2 addLinksItem(Link linksItem) {
    if (this.links == null) {
      this.links = new ArrayList<>();
    }
    this.links.add(linksItem);
    return this;
  }

  /**
   * The links that apply to the collection.
   *
   * @return links
   **/
  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public BaseCollection2 version(Integer version) {
    this.version = version;
    return this;
  }

  /**
   * The version number of the collection representation. This representation is version 2.
   *
   * @return version
   **/
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
    BaseCollection2 baseCollection2 = (BaseCollection2) o;
    return Objects.equals(this.name, baseCollection2.name) &&
            Objects.equals(this.start, baseCollection2.start) &&
            Objects.equals(this.limit, baseCollection2.limit) &&
            Objects.equals(this.count, baseCollection2.count) &&
            Objects.equals(this.accept, baseCollection2.accept) &&
            Objects.equals(this.links, baseCollection2.links) &&
            Objects.equals(this.version, baseCollection2.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, start, limit, count, accept, links, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BaseCollection2 {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    start: ").append(toIndentedString(start)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    accept: ").append(toIndentedString(accept)).append("\n");
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

