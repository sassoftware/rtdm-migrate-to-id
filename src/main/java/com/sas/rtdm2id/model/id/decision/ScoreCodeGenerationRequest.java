/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Request that contains hints to generate mapped code using the Scoring Definition by Score Object.
 */
public class ScoreCodeGenerationRequest {
  @JsonProperty("scoreDefinitionId")
  private String scoreDefinitionId = null;

  @JsonProperty("hints")
  private Map<String, String> hints = null;

  @JsonProperty("version")
  private Integer version = null;

  public ScoreCodeGenerationRequest scoreDefinitionId(String scoreDefinitionId) {
    this.scoreDefinitionId = scoreDefinitionId;
    return this;
  }

  /**
   * Score definition ID that is used for generating mapped code. Should be used only if scoreDefinition is not used.
   *
   * @return scoreDefinitionId
   **/
  public String getScoreDefinitionId() {
    return scoreDefinitionId;
  }

  public void setScoreDefinitionId(String scoreDefinitionId) {
    this.scoreDefinitionId = scoreDefinitionId;
  }

  public ScoreCodeGenerationRequest hints(Map<String, String> hints) {
    this.hints = hints;
    return this;
  }

  public ScoreCodeGenerationRequest putHintsItem(String key, String hintsItem) {
    if (this.hints == null) {
      this.hints = new HashMap<>();
    }
    this.hints.put(key, hintsItem);
    return this;
  }

  /**
   * Hints for generating mapped code. The mapped code request is forwarded to the Score object of the score definition. The Score object should understand these hints.
   *
   * @return hints
   **/
  public Map<String, String> getHints() {
    return hints;
  }

  public void setHints(Map<String, String> hints) {
    this.hints = hints;
  }

  public ScoreCodeGenerationRequest version(Integer version) {
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
    ScoreCodeGenerationRequest scoreCodeGenerationRequest = (ScoreCodeGenerationRequest) o;
    return Objects.equals(this.scoreDefinitionId, scoreCodeGenerationRequest.scoreDefinitionId) &&
            Objects.equals(this.hints, scoreCodeGenerationRequest.hints) &&
            Objects.equals(this.version, scoreCodeGenerationRequest.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scoreDefinitionId, hints, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ScoreCodeGenerationRequest {\n");

    sb.append("    scoreDefinitionId: ").append(toIndentedString(scoreDefinitionId)).append("\n");
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

