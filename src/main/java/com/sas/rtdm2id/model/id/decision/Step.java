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
 * A step within a decision.
 */
public class Step {
  /**
   * Type of step being referenced within the decision flow. Different members are required for each type of step.
   */
  public enum TypeEnum {
    MODEL("application/vnd.sas.decision.step.model"),

    RULESET("application/vnd.sas.decision.step.ruleset"),

    CONDITION("application/vnd.sas.decision.step.condition"),

    CUSTOM_OBJECT("application/vnd.sas.decision.step.custom.object"),

    RECORD_CONTACT("application/vnd.sas.decision.step.record.contact"),

    BRANCH("application/vnd.sas.decision.step.branch"),

    NODE_LINK("application/vnd.sas.decision.step.node.link");

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

  @JsonProperty("linkLabel")
  private String linkLabel = null;

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

  @JsonProperty("ruleset")
  private RuleSetStep ruleSet = null;

  @JsonProperty("model")
  private ModelStep model = null;

  @JsonProperty("customObject")
  private CustomObjectStep customObject = null;

  @JsonProperty("condition")
  private ConditionStep condition = null;

  @JsonProperty("branchCases")
  private List<BranchCase> branchCases = null;

  @JsonProperty("recordContact")
  private RecordContactStep recordContact = null;

  @JsonProperty("mappings")
  private List<Mapping> mappings = null;

  @JsonProperty("decisionNodeLinkTarget")
  private String decisionNodeLinkTarget = null;

  @JsonProperty("defaultCase")
  private ConditionBranch defaultCase = null;

  @JsonProperty("branchType")
  private String branchType = null;

  @JsonProperty("onTrue")
  private ConditionBranch onTrue = null;

  @JsonProperty("onFalse")
  private ConditionBranch onFalse = null;

  @JsonProperty("conditionExpression")
  private String conditionExpression = null;

  @JsonProperty("name")
  private String name = null;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getConditionExpression() {
    return conditionExpression;
  }

  public void setConditionExpression(String conditionExpression) {
    this.conditionExpression = conditionExpression;
  }

  public Step conditionExpression(String conditionExpression) {
    this.conditionExpression = conditionExpression;
    return this;
  }

  public Step type(TypeEnum type) {
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

  public Step id(String id) {
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

  public Step linkLabel(String linkLabel) {
    this.linkLabel = linkLabel;
    return this;
  }

  /**
   * The label targeted by the decisionNodeLinkTarget of a decisionNodeLinkStep
   *
   * @return linkLabel
   **/
  public String getLinkLabel() {
    return linkLabel;
  }

  public void setLinkLabel(String linkLabel) {
    this.linkLabel = linkLabel;
  }

  public Step createdBy(String createdBy) {
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

  public Step creationTimeStamp(OffsetDateTime creationTimeStamp) {
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

  public Step modifiedBy(String modifiedBy) {
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

  public Step modifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
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

  public Step mappingDataGridName(String mappingDataGridName) {
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

  public Step ruleSet(RuleSetStep ruleSet) {
    this.ruleSet = ruleSet;
    return this;
  }

  /**
   * Specifies the rule set that is being referenced for this step. Required if type is set to &#39;application/vnd.sas.decision.step.ruleset&#39;.
   *
   * @return ruleSet
   **/
  public RuleSetStep getRuleSet() {
    return ruleSet;
  }

  public void setRuleSet(RuleSetStep ruleSet) {
    this.ruleSet = ruleSet;
  }

  public Step model(ModelStep model) {
    this.model = model;
    return this;
  }

  /**
   * Specifies the model that is being referenced for this step. Required if type is set to &#39;application/vnd.sas.decision.step.model&#39;.
   *
   * @return model
   **/
  public ModelStep getModel() {
    return model;
  }

  public void setModel(ModelStep model) {
    this.model = model;
  }

  public Step customObject(CustomObjectStep customObject) {
    this.customObject = customObject;
    return this;
  }

  /**
   * Specifies the custom object that is being referenced for this step. Required if type is set to &#39;application/vnd.sas.decision.step.custom.object&#39;.
   *
   * @return customObject
   **/
  public CustomObjectStep getCustomObject() {
    return customObject;
  }

  public void setCustomObject(CustomObjectStep customObject) {
    this.customObject = customObject;
  }

  public Step condition(ConditionStep condition) {
    this.condition = condition;
    return this;
  }

  /**
   * Specifies the condition. Required if type is set to &#39;application/vnd.sas.decision.step.condition&#39;.
   *
   * @return condition
   **/
  public ConditionStep getCondition() {
    return condition;
  }

  public void setCondition(ConditionStep condition) {
    this.condition = condition;
  }

  public Step branch(List<BranchCase> branch) {
    this.branchCases = branch;
    return this;
  }

  /**
   * Specifies a branch step. Required if type is set to &#39;application/vnd.sas.decision.step.branch&#39;.
   *
   * @return branch
   **/
  public List<BranchCase> getBranchCases() {
    return branchCases;
  }

  public void setBranchCases(List<BranchCase> branchCases) {
    this.branchCases = branchCases;
  }

  public Step recordContact(RecordContactStep recordContact) {
    this.recordContact = recordContact;
    return this;
  }

  /**
   * Specific contact recording details that should be taken at this step in the flow. Required if type is set to &#39;application/vnd.sas.decision.step.record.contact&#39;.
   *
   * @return recordContact
   **/
  public RecordContactStep getRecordContact() {
    return recordContact;
  }

  public void setRecordContact(RecordContactStep recordContact) {
    this.recordContact = recordContact;
  }

  public Step mappings(List<Mapping> mappings) {
    this.mappings = mappings;
    return this;
  }

  public Step addMappingsItem(Mapping mappingsItem) {
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
  public List<Mapping> getMappings() {
    return mappings;
  }

  public void setMappings(List<Mapping> mappings) {
    this.mappings = mappings;
  }

  public Step decisionNodeLinkTarget(String decisionNodeLinkTarget) {
    this.decisionNodeLinkTarget = decisionNodeLinkTarget;
    return this;
  }

  /**
   * The linkLabel of the decision step that a DecisionNodeLinkStep targets.
   *
   * @return decisionNodeLinkTarget
   **/
  public String getDecisionNodeLinkTarget() {
    return decisionNodeLinkTarget;
  }

  public void setDecisionNodeLinkTarget(String decisionNodeLinkTarget) {
    this.decisionNodeLinkTarget = decisionNodeLinkTarget;
  }

  public ConditionBranch getDefaultCase() {
    return defaultCase;
  }

  public void setDefaultCase(ConditionBranch defaultCase) {
    this.defaultCase = defaultCase;
  }

  public String getBranchType() {
    return branchType;
  }

  public void setBranchType(String branchType) {
    this.branchType = branchType;
  }

  public ConditionBranch getOnTrue() {
    return onTrue;
  }

  public void setOnTrue(ConditionBranch onTrue) {
    this.onTrue = onTrue;
  }

  public ConditionBranch getOnFalse() {
    return onFalse;
  }

  public void setOnFalse(ConditionBranch onFalse) {
    this.onFalse = onFalse;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Step step = (Step) o;
    return Objects.equals(this.type, step.type) &&
            Objects.equals(this.id, step.id) &&
            Objects.equals(this.linkLabel, step.linkLabel) &&
            Objects.equals(this.createdBy, step.createdBy) &&
            Objects.equals(this.creationTimeStamp, step.creationTimeStamp) &&
            Objects.equals(this.modifiedBy, step.modifiedBy) &&
            Objects.equals(this.modifiedTimeStamp, step.modifiedTimeStamp) &&
            Objects.equals(this.mappingDataGridName, step.mappingDataGridName) &&
            Objects.equals(this.ruleSet, step.ruleSet) &&
            Objects.equals(this.model, step.model) &&
            Objects.equals(this.customObject, step.customObject) &&
            Objects.equals(this.condition, step.condition) &&
            Objects.equals(this.branchCases, step.branchCases) &&
            Objects.equals(this.recordContact, step.recordContact) &&
            Objects.equals(this.mappings, step.mappings) &&
            Objects.equals(this.conditionExpression, step.conditionExpression) &&
            Objects.equals(this.decisionNodeLinkTarget, step.decisionNodeLinkTarget) &&
            Objects.equals(this.name, step.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, id, linkLabel, createdBy, creationTimeStamp, modifiedBy, modifiedTimeStamp, mappingDataGridName, ruleSet, model, customObject, condition, branchCases, recordContact, mappings, decisionNodeLinkTarget, conditionExpression, name);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Step {\n");

    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    linkLabel: ").append(toIndentedString(linkLabel)).append("\n");
    sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
    sb.append("    creationTimeStamp: ").append(toIndentedString(creationTimeStamp)).append("\n");
    sb.append("    modifiedBy: ").append(toIndentedString(modifiedBy)).append("\n");
    sb.append("    modifiedTimeStamp: ").append(toIndentedString(modifiedTimeStamp)).append("\n");
    sb.append("    mappingDataGridName: ").append(toIndentedString(mappingDataGridName)).append("\n");
    sb.append("    ruleSet: ").append(toIndentedString(ruleSet)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    customObject: ").append(toIndentedString(customObject)).append("\n");
    sb.append("    condition: ").append(toIndentedString(condition)).append("\n");
    sb.append("    branch: ").append(toIndentedString(branchCases)).append("\n");
    sb.append("    recordContact: ").append(toIndentedString(recordContact)).append("\n");
    sb.append("    mappings: ").append(toIndentedString(mappings)).append("\n");
    sb.append("    decisionNodeLinkTarget: ").append(toIndentedString(decisionNodeLinkTarget)).append("\n");
    sb.append("    conditionExpression: ").append(toIndentedString(conditionExpression)).append("\n");
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

