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
 * Represents the user specifying that contact information should be recorded at this step within the flow and details that should be recorded.
 */
public class RecordContactStep2 {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("ruleFiredTracking")
  private Boolean ruleFiredTracking = null;

  @JsonProperty("pathTracking")
  private Boolean pathTracking = null;

  @JsonProperty("treatmentDatagridTerm")
  private String treatmentDatagridTerm = null;

  @JsonProperty("responseTrackingVariableName")
  private String responseTrackingVariableName = null;

  @JsonProperty("excludeFromContactAggregation")
  private Boolean excludeFromContactAggregation = null;

  @JsonProperty("channelTerm")
  private String channelTerm = null;

  @JsonProperty("auditTerms")
  private List<AuditTerm2> auditTerms = null;

  public RecordContactStep2 name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Logical name of this node for business user identification.
   *
   * @return name
   **/
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RecordContactStep2 ruleFiredTracking(Boolean ruleFiredTracking) {
    this.ruleFiredTracking = ruleFiredTracking;
    return this;
  }

  /**
   * Flag which determines whether the current rule fired information will be recorded with the contact information being written.
   *
   * @return ruleFiredTracking
   **/
  public Boolean isRuleFiredTracking() {
    return ruleFiredTracking;
  }

  public void setRuleFiredTracking(Boolean ruleFiredTracking) {
    this.ruleFiredTracking = ruleFiredTracking;
  }

  public RecordContactStep2 pathTracking(Boolean pathTracking) {
    this.pathTracking = pathTracking;
    return this;
  }

  /**
   * Flag which determines whether the current path of steps that were taken in the decision up to this step will be recorded with the contact information being written.
   *
   * @return pathTracking
   **/
  public Boolean isPathTracking() {
    return pathTracking;
  }

  public void setPathTracking(Boolean pathTracking) {
    this.pathTracking = pathTracking;
  }

  public RecordContactStep2 treatmentDatagridTerm(String treatmentDatagridTerm) {
    this.treatmentDatagridTerm = treatmentDatagridTerm;
    return this;
  }

  /**
   * The name of the decision signature entry to which contains treatment information that should be recorded during the contact for future response tracking. Provided data grid term must have a &#39;treatmentId&#39; column extension.
   *
   * @return treatmentDatagridTerm
   **/
  public String getTreatmentDatagridTerm() {
    return treatmentDatagridTerm;
  }

  public void setTreatmentDatagridTerm(String treatmentDatagridTerm) {
    this.treatmentDatagridTerm = treatmentDatagridTerm;
  }

  public RecordContactStep2 responseTrackingVariableName(String responseTrackingVariableName) {
    this.responseTrackingVariableName = responseTrackingVariableName;
    return this;
  }

  /**
   * The name of the system generated variable that will be created in the output of this decision that will contain a unique response tracking code that can be used for updating contact information in the Subject Contact API that corresponds to this step. This value will be determined by the service.
   *
   * @return responseTrackingVariableName
   **/
  public String getResponseTrackingVariableName() {
    return responseTrackingVariableName;
  }

  public void setResponseTrackingVariableName(String responseTrackingVariableName) {
    this.responseTrackingVariableName = responseTrackingVariableName;
  }

  public RecordContactStep2 excludeFromContactAggregation(Boolean excludeFromContactAggregation) {
    this.excludeFromContactAggregation = excludeFromContactAggregation;
    return this;
  }

  /**
   * Flag that determines the setting of the &#39;excludeFromAggregation&#39; member when contacts from this step are recorded. If not set it defaults to false.
   *
   * @return excludeFromContactAggregation
   **/
  public Boolean isExcludeFromContactAggregation() {
    return excludeFromContactAggregation;
  }

  public void setExcludeFromContactAggregation(Boolean excludeFromContactAggregation) {
    this.excludeFromContactAggregation = excludeFromContactAggregation;
  }

  public RecordContactStep2 channelTerm(String channelTerm) {
    this.channelTerm = channelTerm;
    return this;
  }

  /**
   * The name of the decision signature entry to which contains channel that should be recorded during the contact for future response tracking. Provided term should be a &#39;string&#39; type.
   *
   * @return channelTerm
   **/
  public String getChannelTerm() {
    return channelTerm;
  }

  public void setChannelTerm(String channelTerm) {
    this.channelTerm = channelTerm;
  }

  public RecordContactStep2 auditTerms(List<AuditTerm2> auditTerms) {
    this.auditTerms = auditTerms;
    return this;
  }

  public RecordContactStep2 addAuditTermsItem(AuditTerm2 auditTermsItem) {
    if (this.auditTerms == null) {
      this.auditTerms = new ArrayList<>();
    }
    this.auditTerms.add(auditTermsItem);
    return this;
  }

  /**
   * Get auditTerms
   *
   * @return auditTerms
   **/
  public List<AuditTerm2> getAuditTerms() {
    return auditTerms;
  }

  public void setAuditTerms(List<AuditTerm2> auditTerms) {
    this.auditTerms = auditTerms;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecordContactStep2 recordContactStep2 = (RecordContactStep2) o;
    return Objects.equals(this.name, recordContactStep2.name) &&
            Objects.equals(this.ruleFiredTracking, recordContactStep2.ruleFiredTracking) &&
            Objects.equals(this.pathTracking, recordContactStep2.pathTracking) &&
            Objects.equals(this.treatmentDatagridTerm, recordContactStep2.treatmentDatagridTerm) &&
            Objects.equals(this.responseTrackingVariableName, recordContactStep2.responseTrackingVariableName) &&
            Objects.equals(this.excludeFromContactAggregation, recordContactStep2.excludeFromContactAggregation) &&
            Objects.equals(this.channelTerm, recordContactStep2.channelTerm) &&
            Objects.equals(this.auditTerms, recordContactStep2.auditTerms);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, ruleFiredTracking, pathTracking, treatmentDatagridTerm, responseTrackingVariableName, excludeFromContactAggregation, channelTerm, auditTerms);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecordContactStep2 {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    ruleFiredTracking: ").append(toIndentedString(ruleFiredTracking)).append("\n");
    sb.append("    pathTracking: ").append(toIndentedString(pathTracking)).append("\n");
    sb.append("    treatmentDatagridTerm: ").append(toIndentedString(treatmentDatagridTerm)).append("\n");
    sb.append("    responseTrackingVariableName: ").append(toIndentedString(responseTrackingVariableName)).append("\n");
    sb.append("    excludeFromContactAggregation: ").append(toIndentedString(excludeFromContactAggregation)).append("\n");
    sb.append("    channelTerm: ").append(toIndentedString(channelTerm)).append("\n");
    sb.append("    auditTerms: ").append(toIndentedString(auditTerms)).append("\n");
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

