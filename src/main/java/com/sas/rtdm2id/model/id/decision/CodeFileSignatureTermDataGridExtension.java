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
 * Code File Signature Term DataGrid Extension is used to store the column information of a DataGrid variable.
 */
public class CodeFileSignatureTermDataGridExtension {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("length")
  private Integer length = null;

  /**
   * Data type of the dataGrid column.
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

  public CodeFileSignatureTermDataGridExtension name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Name of the dataGrid column.
   *
   * @return name
   **/
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CodeFileSignatureTermDataGridExtension length(Integer length) {
    this.length = length;
    return this;
  }

  /**
   * Length of the value in the dataGrid column. Valid for dataType string.
   *
   * @return length
   **/
  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public CodeFileSignatureTermDataGridExtension dataType(DataTypeEnum dataType) {
    this.dataType = dataType;
    return this;
  }

  /**
   * Data type of the dataGrid column.
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
    CodeFileSignatureTermDataGridExtension codeFileSignatureTermDataGridExtension = (CodeFileSignatureTermDataGridExtension) o;
    return Objects.equals(this.name, codeFileSignatureTermDataGridExtension.name) &&
            Objects.equals(this.length, codeFileSignatureTermDataGridExtension.length) &&
            Objects.equals(this.dataType, codeFileSignatureTermDataGridExtension.dataType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, length, dataType);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CodeFileSignatureTermDataGridExtension {\n");

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

