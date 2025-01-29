/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents the code file signature term.
 */
public class CodeFileSignatureTerm {
  @JsonProperty("name")
  private String name = null;

  /**
   * Data type of the code file signature term.
   */
  public enum DataTypeEnum {
    STRING("string"),

    DECIMAL("decimal"),

    INTEGER("integer"),

    DATAGRID("dataGrid"),

    DATE("date"),

    DATETIME("datetime"),

    UNKNOWN("unknown");

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

  /**
   * Direction of the code file signature term.
   */
  public enum DirectionEnum {
    INPUT("input"),

    INOUT("inOut");

    private String value;

    DirectionEnum(String value) {
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
    public static DirectionEnum fromValue(String text) {
      for (DirectionEnum b : DirectionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("direction")
  private DirectionEnum direction = null;

  @JsonProperty("length")
  private Integer length = null;

  @JsonProperty("dataGridExtension")
  private List<CodeFileSignatureTermDataGridExtension> dataGridExtension = null;

  public CodeFileSignatureTerm name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the code file signature term.
   *
   * @return name
   **/
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CodeFileSignatureTerm dataType(DataTypeEnum dataType) {
    this.dataType = dataType;
    return this;
  }

  /**
   * Data type of the code file signature term.
   *
   * @return dataType
   **/
  public DataTypeEnum getDataType() {
    return dataType;
  }

  public void setDataType(DataTypeEnum dataType) {
    this.dataType = dataType;
  }

  public CodeFileSignatureTerm direction(DirectionEnum direction) {
    this.direction = direction;
    return this;
  }

  /**
   * Direction of the code file signature term.
   *
   * @return direction
   **/
  public DirectionEnum getDirection() {
    return direction;
  }

  public void setDirection(DirectionEnum direction) {
    this.direction = direction;
  }

  public CodeFileSignatureTerm length(Integer length) {
    this.length = length;
    return this;
  }

  /**
   * Length of the signature term. Valid for string data type.
   *
   * @return length
   **/
  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public CodeFileSignatureTerm dataGridExtension(List<CodeFileSignatureTermDataGridExtension> dataGridExtension) {
    this.dataGridExtension = dataGridExtension;
    return this;
  }

  public CodeFileSignatureTerm addDataGridExtensionItem(CodeFileSignatureTermDataGridExtension dataGridExtensionItem) {
    if (this.dataGridExtension == null) {
      this.dataGridExtension = new ArrayList<>();
    }
    this.dataGridExtension.add(dataGridExtensionItem);
    return this;
  }

  /**
   * Metadata information about data grid columns.
   *
   * @return dataGridExtension
   **/
  public List<CodeFileSignatureTermDataGridExtension> getDataGridExtension() {
    return dataGridExtension;
  }

  public void setDataGridExtension(List<CodeFileSignatureTermDataGridExtension> dataGridExtension) {
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
    CodeFileSignatureTerm codeFileSignatureTerm = (CodeFileSignatureTerm) o;
    return Objects.equals(this.name, codeFileSignatureTerm.name) &&
            Objects.equals(this.dataType, codeFileSignatureTerm.dataType) &&
            Objects.equals(this.direction, codeFileSignatureTerm.direction) &&
            Objects.equals(this.length, codeFileSignatureTerm.length) &&
            Objects.equals(this.dataGridExtension, codeFileSignatureTerm.dataGridExtension);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, dataType, direction, length, dataGridExtension);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CodeFileSignatureTerm {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    direction: ").append(toIndentedString(direction)).append("\n");
    sb.append("    length: ").append(toIndentedString(length)).append("\n");
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

