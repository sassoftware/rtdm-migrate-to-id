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
 * A paginated list of decision node type representations, with optional links to first, prev, next, and last page of the collection.
 */
public class DecisionNodeTypeCollection extends BaseCollection2 {
  @JsonProperty("items")
  private List<DecisionNodeType> items = new ArrayList<>();

  @JsonProperty("version")
  private Integer version = null;

  public DecisionNodeTypeCollection items(List<DecisionNodeType> items) {
    this.items = items;
    return this;
  }

  public DecisionNodeTypeCollection addItemsItem(DecisionNodeType itemsItem) {
    this.items.add(itemsItem);
    return this;
  }

  /**
   * Array consisting of one page of Decision Node Type resources.
   *
   * @return items
   **/
  public List<DecisionNodeType> getItems() {
    return items;
  }

  public void setItems(List<DecisionNodeType> items) {
    this.items = items;
  }

  public DecisionNodeTypeCollection version(Integer version) {
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
    DecisionNodeTypeCollection decisionNodeTypeCollection = (DecisionNodeTypeCollection) o;
    return Objects.equals(this.items, decisionNodeTypeCollection.items) &&
            Objects.equals(this.version, decisionNodeTypeCollection.version) &&
            super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, version, super.hashCode());
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DecisionNodeTypeCollection {\n");
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

