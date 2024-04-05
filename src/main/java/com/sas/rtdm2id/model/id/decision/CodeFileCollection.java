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
 * One page of decision representations, with optional links to first, prev, next, and last page of the collection.
 */
public class CodeFileCollection extends BaseCollection2 {
  @JsonProperty("items")
  private List<CodeFile> items = new ArrayList<>();

  @JsonProperty("version")
  private Integer version = null;

  public CodeFileCollection items(List<CodeFile> items) {
    this.items = items;
    return this;
  }

  public CodeFileCollection addItemsItem(CodeFile itemsItem) {
    this.items.add(itemsItem);
    return this;
  }

  /**
   * Array consisting of one page of Decision resources.
   *
   * @return items
   **/
  public List<CodeFile> getItems() {
    return items;
  }

  public void setItems(List<CodeFile> items) {
    this.items = items;
  }

  public CodeFileCollection version(Integer version) {
    this.version = version;
    return this;
  }

  /**
   * This media type&#39;s schema version number. This representation is version 1.
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
    CodeFileCollection codeFileCollection = (CodeFileCollection) o;
    return Objects.equals(this.items, codeFileCollection.items) &&
            Objects.equals(this.version, codeFileCollection.version) &&
            super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, version, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CodeFileCollection {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
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

