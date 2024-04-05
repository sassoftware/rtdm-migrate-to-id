/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A condition step within a decision.
 */
public class Step2 {
  /**
   * Type of step being referenced within the decision flow. Different members are required for each type of step.
   */
  public enum TypeEnum {
    MODEL("application/vnd.sas.decision.step.model"),

    RULESET("application/vnd.sas.decision.step.ruleset"),

    CONDITION("application/vnd.sas.decision.step.condition"),

    CUSTOM_OBJECT("application/vnd.sas.decision.step.custom.object"),

    RECORD_CONTACT("application/vnd.sas.decision.step.record.contact"),

    BRANCH("application/vnd.sas.decision.step.branch");

    private String value;

    TypeEnum(String value) {
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
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("type")
  private TypeEnum type = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("createdBy")
  private String createdBy = null;

  @JsonProperty("creationTimeStamp")
  private OffsetDateTime creationTimeStamp = null;

  @JsonProperty("modifiedBy")
  private String modifiedBy = null;

  @JsonProperty("modifiedTimeStamp")
  private OffsetDateTime modifiedTimeStamp = null;

  @JsonProperty("mappingDataGridName")
  private String mappingDataGridName = null;

  @JsonProperty("ruleSet")
  private RuleSetStep2 ruleSet = null;

  @JsonProperty("model")
  private ModelStep2 model = null;

  @JsonProperty("customObject")
  private CustomObjectStep2 customObject = null;

  @JsonProperty("condition")
  private ConditionStep2 condition = null;

  @JsonProperty("branch")
  private BranchStep2 branch = null;

  @JsonProperty("recordContact")
  private RecordContactStep2 recordContact = null;

  @JsonProperty("mappings")
  private List<Mapping2> mappings = null;

  public Step2 type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Type of step being referenced within the decision flow. Different members are required for each type of step.
   *
   * @return type
   **/
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public Step2 id(String id) {
    this.id = id;
    return this;
  }

  /**
   * The system-assigned unique ID for this object
   *
   * @return id
   **/
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Step2 createdBy(String createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  /**
   * The user who created the step.
   *
   * @return createdBy
   **/
  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Step2 creationTimeStamp(OffsetDateTime creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
    return this;
  }

  /**
   * The date and time that the step was created.
   *
   * @return creationTimeStamp
   **/
  public OffsetDateTime getCreationTimeStamp() {
    return creationTimeStamp;
  }

  public void setCreationTimeStamp(OffsetDateTime creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
  }

  public Step2 modifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
    return this;
  }

  /**
   * The user ID of the authenticated user who last modified the step.
   *
   * @return modifiedBy
   **/
  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public Step2 modifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
    this.modifiedTimeStamp = modifiedTimeStamp;
    return this;
  }

  /**
   * The date and time that the step was last modified.
   *
   * @return modifiedTimeStamp
   **/
  public OffsetDateTime getModifiedTimeStamp() {
    return modifiedTimeStamp;
  }

  public void setModifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
    this.modifiedTimeStamp = modifiedTimeStamp;
  }

  public Step2 mappingDataGridName(String mappingDataGridName) {
    this.mappingDataGridName = mappingDataGridName;
    return this;
  }

  /**
   * Datagrid signature term name which is used by mapping. Only valid for customObject, ruleSet and model steps.
   *
   * @return mappingDataGridName
   **/
  public String getMappingDataGridName() {
    return mappingDataGridName;
  }

  public void setMappingDataGridName(String mappingDataGridName) {
    this.mappingDataGridName = mappingDataGridName;
  }

  public Step2 ruleSet(RuleSetStep2 ruleSet) {
    this.ruleSet = ruleSet;
    return this;
  }

  /**
   * Specifies the rule set that is being referenced for this step. Required if type is set to &#39;application/vnd.sas.decision.step.ruleset&#39;.
   *
   * @return ruleSet
   **/
  public RuleSetStep2 getRuleSet() {
    return ruleSet;
  }

  public void setRuleSet(RuleSetStep2 ruleSet) {
    this.ruleSet = ruleSet;
  }

  public Step2 model(ModelStep2 model) {
    this.model = model;
    return this;
  }

  /**
   * Specifies the model that is being referenced for this step. Required if type is set to &#39;application/vnd.sas.decision.step.model&#39;.
   *
   * @return model
   **/
  public ModelStep2 getModel() {
    return model;
  }

  public void setModel(ModelStep2 model) {
    this.model = model;
  }

  public Step2 customObject(CustomObjectStep2 customObject) {
    this.customObject = customObject;
    return this;
  }

  /**
   * Specifies the custom object that is being referenced for this step. Required if type is set to &#39;application/vnd.sas.decision.step.custom.object&#39;.
   *
   * @return customObject
   **/
  public CustomObjectStep2 getCustomObject() {
    return customObject;
  }

  public void setCustomObject(CustomObjectStep2 customObject) {
    this.customObject = customObject;
  }

  public Step2 condition(ConditionStep2 condition) {
    this.condition = condition;
    return this;
  }

  /**
   * Specifies the condition. Required if type is set to &#39;application/vnd.sas.decision.step.condition&#39;.
   *
   * @return condition
   **/
  public ConditionStep2 getCondition() {
    return condition;
  }

  public void setCondition(ConditionStep2 condition) {
    this.condition = condition;
  }

  public Step2 branch(BranchStep2 branch) {
    this.branch = branch;
    return this;
  }

  /**
   * Specifies a branch step. Required if type is set to &#39;application/vnd.sas.decision.step.branch&#39;.
   *
   * @return branch
   **/
  public BranchStep2 getBranch() {
    return branch;
  }

  public void setBranch(BranchStep2 branch) {
    this.branch = branch;
  }

  public Step2 recordContact(RecordContactStep2 recordContact) {
    this.recordContact = recordContact;
    return this;
  }

  /**
   * Specific contact recording details that should be taken at this step in the flow. Required if type is set to &#39;application/vnd.sas.decision.step.record.contact&#39;.
   *
   * @return recordContact
   **/
  public RecordContactStep2 getRecordContact() {
    return recordContact;
  }

  public void setRecordContact(RecordContactStep2 recordContact) {
    this.recordContact = recordContact;
  }

  public Step2 mappings(List<Mapping2> mappings) {
    this.mappings = mappings;
    return this;
  }

  public Step2 addMappingsItem(Mapping2 mappingsItem) {
    if (this.mappings == null) {
      this.mappings = new ArrayList<>();
    }
    this.mappings.add(mappingsItem);
    return this;
  }

  /**
   * Variable mappings for the model or rule set being referenced. Required if type is set to &#39;application/vnd.sas.decision.step.model&#39; or &#39;application/vnd.sas.decision.step.ruleset&#39;.
   *
   * @return mappings
   **/
  public List<Mapping2> getMappings() {
    return mappings;
  }

  public void setMappings(List<Mapping2> mappings) {
    this.mappings = mappings;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Step2 step2 = (Step2) o;
    return Objects.equals(this.type, step2.type) &&
            Objects.equals(this.id, step2.id) &&
            Objects.equals(this.createdBy, step2.createdBy) &&
            Objects.equals(this.creationTimeStamp, step2.creationTimeStamp) &&
            Objects.equals(this.modifiedBy, step2.modifiedBy) &&
            Objects.equals(this.modifiedTimeStamp, step2.modifiedTimeStamp) &&
            Objects.equals(this.mappingDataGridName, step2.mappingDataGridName) &&
            Objects.equals(this.ruleSet, step2.ruleSet) &&
            Objects.equals(this.model, step2.model) &&
            Objects.equals(this.customObject, step2.customObject) &&
            Objects.equals(this.condition, step2.condition) &&
            Objects.equals(this.branch, step2.branch) &&
            Objects.equals(this.recordContact, step2.recordContact) &&
            Objects.equals(this.mappings, step2.mappings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, id, createdBy, creationTimeStamp, modifiedBy, modifiedTimeStamp, mappingDataGridName, ruleSet, model, customObject, condition, branch, recordContact, mappings);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Step2 {\n");

    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
    sb.append("    creationTimeStamp: ").append(toIndentedString(creationTimeStamp)).append("\n");
    sb.append("    modifiedBy: ").append(toIndentedString(modifiedBy)).append("\n");
    sb.append("    modifiedTimeStamp: ").append(toIndentedString(modifiedTimeStamp)).append("\n");
    sb.append("    mappingDataGridName: ").append(toIndentedString(mappingDataGridName)).append("\n");
    sb.append("    ruleSet: ").append(toIndentedString(ruleSet)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    customObject: ").append(toIndentedString(customObject)).append("\n");
    sb.append("    condition: ").append(toIndentedString(condition)).append("\n");
    sb.append("    branch: ").append(toIndentedString(branch)).append("\n");
    sb.append("    recordContact: ").append(toIndentedString(recordContact)).append("\n");
    sb.append("    mappings: ").append(toIndentedString(mappings)).append("\n");
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

