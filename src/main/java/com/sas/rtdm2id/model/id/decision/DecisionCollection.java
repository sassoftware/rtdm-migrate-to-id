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
 * A paginated list of decision representations, with optional links to first, prev, next, and last page of the collection.
 */
public class DecisionCollection extends BaseCollection2 {
  @JsonProperty("items")
  private List<Decision2> items = new ArrayList<>();

  @JsonProperty("version")
  private Integer version = null;

  public DecisionCollection items(List<Decision2> items) {
    this.items = items;
    return this;
  }

  public DecisionCollection addItemsItem(Decision2 itemsItem) {
    this.items.add(itemsItem);
    return this;
  }

  /**
   * Array consisting of one page of Decision resources.
   *
   * @return items
   **/
  public List<Decision2> getItems() {
    return items;
  }

  public void setItems(List<Decision2> items) {
    this.items = items;
  }

  public DecisionCollection version(Integer version) {
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
    DecisionCollection decisionCollection = (DecisionCollection) o;
    return Objects.equals(this.items, decisionCollection.items) &&
            Objects.equals(this.version, decisionCollection.version) &&
            super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, version, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DecisionCollection {\n");
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

