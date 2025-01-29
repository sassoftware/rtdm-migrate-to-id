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
 * Represents a term that is used for a condition comparison within a decision.
 */
public class Term {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("defaultValue")
  private Object defaultValue = null;

  @JsonProperty("length")
  private Integer length = null;

  @JsonProperty("createdBy")
  private String createdBy = null;

  @JsonProperty("creationTimeStamp")
  private OffsetDateTime creationTimeStamp = null;

  @JsonProperty("modifiedBy")
  private String modifiedBy = null;

  @JsonProperty("modifiedTimeStamp")
  private OffsetDateTime modifiedTimeStamp = null;

  /**
   * The type of data that the term is intended to be used with.
   */
  public enum DataTypeEnum {
    STRING("string"),

    DECIMAL("decimal"),

    INTEGER("integer"),

    DATE("date"),

    DATETIME("datetime"),

    DATAGRID("dataGrid");

    private String value;

    DataTypeEnum(String value) {
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
    public static DataTypeEnum fromValue(String text) {
      for (DataTypeEnum b : DataTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("dataType")
  private DataTypeEnum dataType = null;

  @JsonProperty("dataGridExtension")
  private List<TermExtension> dataGridExtension = null;

  public Term id(String id) {
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

  public Term name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the term that can be referenced. Term names must be unique within a rule set.
   *
   * @return name
   **/
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Term description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Description of the term
   *
   * @return description
   **/
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Term defaultValue(Object defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  /**
   * The initial value of the term.
   *
   * @return defaultValue
   **/
  public Object getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Object defaultValue) {
    this.defaultValue = defaultValue;
  }

  public Term length(Integer length) {
    this.length = length;
    return this;
  }

  /**
   * Suggested length for use when generating code.
   *
   * @return length
   **/
  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public Term createdBy(String createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  /**
   * The user who created the term.
   *
   * @return createdBy
   **/
  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Term creationTimeStamp(OffsetDateTime creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
    return this;
  }

  /**
   * The date and time that the term was created.
   *
   * @return creationTimeStamp
   **/
  public OffsetDateTime getCreationTimeStamp() {
    return creationTimeStamp;
  }

  public void setCreationTimeStamp(OffsetDateTime creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
  }

  public Term modifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
    return this;
  }

  /**
   * The userId of the authenticated user who last updated the term.
   *
   * @return modifiedBy
   **/
  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public Term modifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
    this.modifiedTimeStamp = modifiedTimeStamp;
    return this;
  }

  /**
   * The date and time that the term was last modified.
   *
   * @return modifiedTimeStamp
   **/
  public OffsetDateTime getModifiedTimeStamp() {
    return modifiedTimeStamp;
  }

  public void setModifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
    this.modifiedTimeStamp = modifiedTimeStamp;
  }

  public Term dataType(DataTypeEnum dataType) {
    this.dataType = dataType;
    return this;
  }

  /**
   * The type of data that the term is intended to be used with.
   *
   * @return dataType
   **/
  public DataTypeEnum getDataType() {
    return dataType;
  }

  public void setDataType(DataTypeEnum dataType) {
    this.dataType = dataType;
  }

  public Term dataGridExtension(List<TermExtension> dataGridExtension) {
    this.dataGridExtension = dataGridExtension;
    return this;
  }

  public Term addDataGridExtensionItem(TermExtension dataGridExtensionItem) {
    if (this.dataGridExtension == null) {
      this.dataGridExtension = new ArrayList<>();
    }
    this.dataGridExtension.add(dataGridExtensionItem);
    return this;
  }

  /**
   * Get dataGridExtension
   *
   * @return dataGridExtension
   **/
  public List<TermExtension> getDataGridExtension() {
    return dataGridExtension;
  }

  public void setDataGridExtension(List<TermExtension> dataGridExtension) {
    this.dataGridExtension = dataGridExtension;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Term term = (Term) o;
    return Objects.equals(this.id, term.id) &&
            Objects.equals(this.name, term.name) &&
            Objects.equals(this.description, term.description) &&
            Objects.equals(this.defaultValue, term.defaultValue) &&
            Objects.equals(this.length, term.length) &&
            Objects.equals(this.createdBy, term.createdBy) &&
            Objects.equals(this.creationTimeStamp, term.creationTimeStamp) &&
            Objects.equals(this.modifiedBy, term.modifiedBy) &&
            Objects.equals(this.modifiedTimeStamp, term.modifiedTimeStamp) &&
            Objects.equals(this.dataType, term.dataType) &&
            Objects.equals(this.dataGridExtension, term.dataGridExtension);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, defaultValue, length, createdBy, creationTimeStamp, modifiedBy, modifiedTimeStamp, dataType, dataGridExtension);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Term {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    defaultValue: ").append(toIndentedString(defaultValue)).append("\n");
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
    sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
    sb.append("    creationTimeStamp: ").append(toIndentedString(creationTimeStamp)).append("\n");
    sb.append("    modifiedBy: ").append(toIndentedString(modifiedBy)).append("\n");
    sb.append("    modifiedTimeStamp: ").append(toIndentedString(modifiedTimeStamp)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    dataGridExtension: ").append(toIndentedString(dataGridExtension)).append("\n");
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

