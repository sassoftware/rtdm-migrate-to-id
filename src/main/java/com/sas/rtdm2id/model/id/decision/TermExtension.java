/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Signature Term Extension is used to store the column information of a DataGrid variable.
 */
public class TermExtension {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("length")
  private Integer length = null;

  /**
   * The type of data that the term is intended to be used with. Terms can be loosely typed with the &#39;any&#39; type, this type is only assigned by the service when rules POST&#39;ed with &#39;createVariables&#x3D;true&#39; and no type can be determined.
   */
  public enum DataTypeEnum {
    STRING("string"),

    DECIMAL("decimal"),

    INTEGER("integer"),

    DATE("date"),

    DATETIME("datetime");

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

  public TermExtension id(String id) {
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

  public TermExtension name(String name) {
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

  public TermExtension length(Integer length) {
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

  public TermExtension dataType(DataTypeEnum dataType) {
    this.dataType = dataType;
    return this;
  }

  /**
   * The type of data that the term is intended to be used with. Terms can be loosely typed with the &#39;any&#39; type, this type is only assigned by the service when rules POST&#39;ed with &#39;createVariables&#x3D;true&#39; and no type can be determined.
   *
   * @return dataType
   **/
  public DataTypeEnum getDataType() {
    return dataType;
  }

  public void setDataType(DataTypeEnum dataType) {
    this.dataType = dataType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TermExtension termExtension = (TermExtension) o;
    return Objects.equals(this.id, termExtension.id) &&
            Objects.equals(this.name, termExtension.name) &&
            Objects.equals(this.length, termExtension.length) &&
            Objects.equals(this.dataType, termExtension.dataType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, length, dataType);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TermExtension {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
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

