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
 * The list of links to top-level resources and operations available from the root of the API.
 */
public class Api {
  @JsonProperty("version")
  private Integer version = null;

  @JsonProperty("links")
  private List<Link> links = new ArrayList<>();

  public Api version(Integer version) {
    this.version = version;
    return this;
  }

  /**
   * The version number of the API representation. This is version 1.
   * minimum: 1
   *
   * @return version
   **/
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public Api links(List<Link> links) {
    this.links = links;
    return this;
  }

  public Api addLinksItem(Link linksItem) {
    this.links.add(linksItem);
    return this;
  }

  /**
   * The API&#39;s top-level links.
   *
   * @return links
   **/
  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Api api = (Api) o;
    return Objects.equals(this.version, api.version) &&
            Objects.equals(this.links, api.links);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, links);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Api {\n");

    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
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

