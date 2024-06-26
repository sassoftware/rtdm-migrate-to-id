/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Request that contains hints to generate analysis code using the Scoring Execution API.
 */
public class AnalysisCodeGenerationRequest {
  @JsonProperty("scoreExecutionId")
  private String scoreExecutionId = null;

  /**
   * Type of analysis that needs to be performed.
   */
  public enum AnalysisTypeEnum {
    RULEFIREDDETAILS("ruleFiredDetails"),

    RULEFIREDSUMMARY("ruleFiredSummary"),

    DECISIONPATHNODES("decisionPathNodes"),

    DECISIONPATHFREQUENCY("decisionPathFrequency"),

    DECISIONNODESCOUNTS("decisionNodesCounts");

    private String value;

    AnalysisTypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AnalysisTypeEnum fromValue(String text) {
      for (AnalysisTypeEnum b : AnalysisTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("analysisType")
  private AnalysisTypeEnum analysisType = null;

  @JsonProperty("hints")
  private Map<String, String> hints = null;

  @JsonProperty("version")
  private Integer version = null;

  public AnalysisCodeGenerationRequest scoreExecutionId(String scoreExecutionId) {
    this.scoreExecutionId = scoreExecutionId;
    return this;
  }

  /**
   * Score execution ID.
   *
   * @return scoreExecutionId
   **/
  public String getScoreExecutionId() {
    return scoreExecutionId;
  }

  public void setScoreExecutionId(String scoreExecutionId) {
    this.scoreExecutionId = scoreExecutionId;
  }

  public AnalysisCodeGenerationRequest analysisType(AnalysisTypeEnum analysisType) {
    this.analysisType = analysisType;
    return this;
  }

  /**
   * Type of analysis that needs to be performed.
   *
   * @return analysisType
   **/
  public AnalysisTypeEnum getAnalysisType() {
    return analysisType;
  }

  public void setAnalysisType(AnalysisTypeEnum analysisType) {
    this.analysisType = analysisType;
  }

  public AnalysisCodeGenerationRequest hints(Map<String, String> hints) {
    this.hints = hints;
    return this;
  }

  public AnalysisCodeGenerationRequest putHintsItem(String key, String hintsItem) {
    if (this.hints == null) {
      this.hints = new HashMap<>();
    }
    this.hints.put(key, hintsItem);
    return this;
  }

  /**
   * Hints for generating analysis code.
   *
   * @return hints
   **/
  public Map<String, String> getHints() {
    return hints;
  }

  public void setHints(Map<String, String> hints) {
    this.hints = hints;
  }

  public AnalysisCodeGenerationRequest version(Integer version) {
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
    AnalysisCodeGenerationRequest analysisCodeGenerationRequest = (AnalysisCodeGenerationRequest) o;
    return Objects.equals(this.scoreExecutionId, analysisCodeGenerationRequest.scoreExecutionId) &&
            Objects.equals(this.analysisType, analysisCodeGenerationRequest.analysisType) &&
            Objects.equals(this.hints, analysisCodeGenerationRequest.hints) &&
            Objects.equals(this.version, analysisCodeGenerationRequest.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scoreExecutionId, analysisType, hints, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AnalysisCodeGenerationRequest {\n");

    sb.append("    scoreExecutionId: ").append(toIndentedString(scoreExecutionId)).append("\n");
    sb.append("    analysisType: ").append(toIndentedString(analysisType)).append("\n");
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

