/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

/**
 * Request containing hints and mappings to generate Mapped Code not bound to a score definition.
 */
public class UnboundScoreCodeGenerationRequest {
  @JsonProperty("mappings")
  private List<Mapping2> mappings = null;

  @JsonProperty("hints")
  private Map<String, String> hints = null;

  @JsonProperty("version")
  private Integer version = null;

  public UnboundScoreCodeGenerationRequest mappings(List<Mapping2> mappings) {
    this.mappings = mappings;
    return this;
  }

  public UnboundScoreCodeGenerationRequest addMappingsItem(Mapping2 mappingsItem) {
    if (this.mappings == null) {
      this.mappings = new ArrayList<>();
    }
    this.mappings.add(mappingsItem);
    return this;
  }

  /**
   * Array of mappings between Score Object variables and the table the code will be executed against.
   *
   * @return mappings
   **/
  public List<Mapping2> getMappings() {
    return mappings;
  }

  public void setMappings(List<Mapping2> mappings) {
    this.mappings = mappings;
  }

  public UnboundScoreCodeGenerationRequest hints(Map<String, String> hints) {
    this.hints = hints;
    return this;
  }

  public UnboundScoreCodeGenerationRequest putHintsItem(String key, String hintsItem) {
    if (this.hints == null) {
      this.hints = new HashMap<>();
    }
    this.hints.put(key, hintsItem);
    return this;
  }

  /**
   * Hints for generating the code. Hints are specific to the type of object which the code is being requested for.
   *
   * @return hints
   **/
  public Map<String, String> getHints() {
    return hints;
  }

  public void setHints(Map<String, String> hints) {
    this.hints = hints;
  }

  public UnboundScoreCodeGenerationRequest version(Integer version) {
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
    UnboundScoreCodeGenerationRequest unboundScoreCodeGenerationRequest = (UnboundScoreCodeGenerationRequest) o;
    return Objects.equals(this.mappings, unboundScoreCodeGenerationRequest.mappings) &&
            Objects.equals(this.hints, unboundScoreCodeGenerationRequest.hints) &&
            Objects.equals(this.version, unboundScoreCodeGenerationRequest.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mappings, hints, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UnboundScoreCodeGenerationRequest {\n");

    sb.append("    mappings: ").append(toIndentedString(mappings)).append("\n");
    sb.append("    hints: ").append(toIndentedString(hints)).append("\n");
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

