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
 * Wrapper to file resource which contains the signature of the code. This represents application/vnd.sas.decision.code.file media type (version 5).
 */
public class CodeFile {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("defaultValue")
  private String defaultValue = null;

  @JsonProperty("createdBy")
  private String createdBy = null;

  @JsonProperty("creationTimeStamp")
  private OffsetDateTime creationTimeStamp = null;

  @JsonProperty("modifiedBy")
  private String modifiedBy = null;

  @JsonProperty("modifiedTimeStamp")
  private OffsetDateTime modifiedTimeStamp = null;

  @JsonProperty("majorRevision")
  private Integer majorRevision = null;

  @JsonProperty("minorRevision")
  private Integer minorRevision = null;

  @JsonProperty("locked")
  private Boolean locked = null;

  @JsonProperty("dataType")
  private String datatype = null;

  /**
   * Type of the code.
   */
  public enum TypeEnum {
    DECISIONDS2CODEFILE("decisionDS2CodeFile"),

    DECISIONSQLCODEFILE("decisionSQLCodeFile"),

    DECISIONPYTHONFILE("decisionPythonFile");

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

  @JsonProperty("fileUri")
  private String fileUri = null;

  /**
   * Status of the code content.
   */
  public enum StatusEnum {
    VALID("valid"),

    ERROR("error");

    private String value;

    StatusEnum(String value) {
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
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("status")
  private StatusEnum status = null;

  @JsonProperty("errorCode")
  private Integer errorCode = null;

  @JsonProperty("errorMessage")
  private String errorMessage = null;

  @JsonProperty("signature")
  private List<CodeFileSignatureTerm> signature = null;

  @JsonProperty("folderType")
  private String folderType = null;

  @JsonProperty("sourceRevisionUri")
  private String sourceRevisionUri = null;

  @JsonProperty("copyTimeStamp")
  private OffsetDateTime copyTimeStamp = null;

  @JsonProperty("links")
  private List<Link> links = null;

  @JsonProperty("version")
  private Integer version = null;

  public CodeFile id(String id) {
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

  public CodeFile name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The code file name
   *
   * @return name
   **/
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CodeFile description(String description) {
    this.description = description;
    return this;
  }

  /**
   * The code file description
   *
   * @return description
   **/
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public CodeFile createdBy(String createdBy) {
    this.createdBy = createdBy;
    return this;
  }

  /**
   * The user who created the code file.
   *
   * @return createdBy
   **/
  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getDatatype() {
    return datatype;
  }

  public void setDatatype(String datatype) {
    this.datatype = datatype;
  }

  public CodeFile creationTimeStamp(OffsetDateTime creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
    return this;
  }

  /**
   * The date and time that the code file was created.
   *
   * @return creationTimeStamp
   **/
  public OffsetDateTime getCreationTimeStamp() {
    return creationTimeStamp;
  }

  public void setCreationTimeStamp(OffsetDateTime creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
  }

  public CodeFile modifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
    return this;
  }

  /**
   * The userId of the authenticated user who last updated the code file.
   *
   * @return modifiedBy
   **/
  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public CodeFile modifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
    this.modifiedTimeStamp = modifiedTimeStamp;
    return this;
  }

  /**
   * The date and time that the code file was last modified.
   *
   * @return modifiedTimeStamp
   **/
  public OffsetDateTime getModifiedTimeStamp() {
    return modifiedTimeStamp;
  }

  public void setModifiedTimeStamp(OffsetDateTime modifiedTimeStamp) {
    this.modifiedTimeStamp = modifiedTimeStamp;
  }

  public CodeFile majorRevision(Integer majorRevision) {
    this.majorRevision = majorRevision;
    return this;
  }

  /**
   * Major version number of the revision being viewed of the code file. This value is assigned by the service, the user only has control on whether the major number or minor number is incremented when creating a new revision. This is a derived field which is generated on POST.
   *
   * @return majorRevision
   **/
  public Integer getMajorRevision() {
    return majorRevision;
  }

  public void setMajorRevision(Integer majorRevision) {
    this.majorRevision = majorRevision;
  }

  public CodeFile minorRevision(Integer minorRevision) {
    this.minorRevision = minorRevision;
    return this;
  }

  /**
   * Minor version number of the current revision of the code file. This value is assigned by the service, the user only has control on whether the major number or minor number is incremented when creating a new revision. This is a derived field which is generated on POST.
   *
   * @return minorRevision
   **/
  public Integer getMinorRevision() {
    return minorRevision;
  }

  public void setMinorRevision(Integer minorRevision) {
    this.minorRevision = minorRevision;
  }

  public CodeFile locked(Boolean locked) {
    this.locked = locked;
    return this;
  }

  /**
   * Flag which indicates if the content of the revision being viewed is locked or editable.
   *
   * @return locked
   **/
  public Boolean isLocked() {
    return locked;
  }

  public void setLocked(Boolean locked) {
    this.locked = locked;
  }

  public CodeFile type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Type of the code.
   *
   * @return type
   **/
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public CodeFile fileUri(String fileUri) {
    this.fileUri = fileUri;
    return this;
  }

  /**
   * Uri of the file resource which contains code.
   *
   * @return fileUri
   **/
  public String getFileUri() {
    return fileUri;
  }

  public void setFileUri(String fileUri) {
    this.fileUri = fileUri;
  }

  public CodeFile status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Status of the code content.
   *
   * @return status
   **/
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public CodeFile errorCode(Integer errorCode) {
    this.errorCode = errorCode;
    return this;
  }

  /**
   * Error code when parsing code content.
   *
   * @return errorCode
   **/
  public Integer getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(Integer errorCode) {
    this.errorCode = errorCode;
  }

  public CodeFile errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  /**
   * Error message when parsing code content.
   *
   * @return errorMessage
   **/
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public CodeFile signature(List<CodeFileSignatureTerm> signature) {
    this.signature = signature;
    return this;
  }

  public CodeFile addSignatureItem(CodeFileSignatureTerm signatureItem) {
    if (this.signature == null) {
      this.signature = new ArrayList<>();
    }
    this.signature.add(signatureItem);
    return this;
  }

  /**
   * Array of signature terms.
   *
   * @return signature
   **/
  public List<CodeFileSignatureTerm> getSignature() {
    return signature;
  }

  public void setSignature(List<CodeFileSignatureTerm> signature) {
    this.signature = signature;
  }

  public CodeFile folderType(String folderType) {
    this.folderType = folderType;
    return this;
  }

  /**
   * Indicates the kind of folder where the code file is housed.
   *
   * @return folderType
   **/
  public String getFolderType() {
    return folderType;
  }

  public void setFolderType(String folderType) {
    this.folderType = folderType;
  }

  public CodeFile sourceRevisionUri(String sourceRevisionUri) {
    this.sourceRevisionUri = sourceRevisionUri;
    return this;
  }

  /**
   * The URI of the code file revision this code file is being created from.
   *
   * @return sourceRevisionUri
   **/
  public String getSourceRevisionUri() {
    return sourceRevisionUri;
  }

  public void setSourceRevisionUri(String sourceRevisionUri) {
    this.sourceRevisionUri = sourceRevisionUri;
  }

  public CodeFile copyTimeStamp(OffsetDateTime copyTimeStamp) {
    this.copyTimeStamp = copyTimeStamp;
    return this;
  }

  /**
   * The time stamp when the code file revision was copied.
   *
   * @return copyTimeStamp
   **/
  public OffsetDateTime getCopyTimeStamp() {
    return copyTimeStamp;
  }

  public void setCopyTimeStamp(OffsetDateTime copyTimeStamp) {
    this.copyTimeStamp = copyTimeStamp;
  }

  public CodeFile links(List<Link> links) {
    this.links = links;
    return this;
  }

  public CodeFile addLinksItem(Link linksItem) {
    if (this.links == null) {
      this.links = new ArrayList<>();
    }
    this.links.add(linksItem);
    return this;
  }

  /**
   * Zero or more links to related resources or operations.
   *
   * @return links
   **/
  public List<Link> getLinks() {
    return links;
  }

  public void setLinks(List<Link> links) {
    this.links = links;
  }

  public CodeFile version(Integer version) {
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
    CodeFile codeFile = (CodeFile) o;
    return Objects.equals(this.id, codeFile.id) &&
            Objects.equals(this.name, codeFile.name) &&
            Objects.equals(this.description, codeFile.description) &&
            Objects.equals(this.createdBy, codeFile.createdBy) &&
            Objects.equals(this.creationTimeStamp, codeFile.creationTimeStamp) &&
            Objects.equals(this.modifiedBy, codeFile.modifiedBy) &&
            Objects.equals(this.modifiedTimeStamp, codeFile.modifiedTimeStamp) &&
            Objects.equals(this.majorRevision, codeFile.majorRevision) &&
            Objects.equals(this.minorRevision, codeFile.minorRevision) &&
            Objects.equals(this.locked, codeFile.locked) &&
            Objects.equals(this.type, codeFile.type) &&
            Objects.equals(this.fileUri, codeFile.fileUri) &&
            Objects.equals(this.status, codeFile.status) &&
            Objects.equals(this.errorCode, codeFile.errorCode) &&
            Objects.equals(this.errorMessage, codeFile.errorMessage) &&
            Objects.equals(this.signature, codeFile.signature) &&
            Objects.equals(this.folderType, codeFile.folderType) &&
            Objects.equals(this.sourceRevisionUri, codeFile.sourceRevisionUri) &&
            Objects.equals(this.copyTimeStamp, codeFile.copyTimeStamp) &&
            Objects.equals(this.links, codeFile.links) &&
            Objects.equals(this.version, codeFile.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, createdBy, creationTimeStamp, modifiedBy, modifiedTimeStamp, majorRevision, minorRevision, locked, type, fileUri, status, errorCode, errorMessage, signature, folderType, sourceRevisionUri, copyTimeStamp, links, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CodeFile {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
    sb.append("    creationTimeStamp: ").append(toIndentedString(creationTimeStamp)).append("\n");
    sb.append("    modifiedBy: ").append(toIndentedString(modifiedBy)).append("\n");
    sb.append("    modifiedTimeStamp: ").append(toIndentedString(modifiedTimeStamp)).append("\n");
    sb.append("    majorRevision: ").append(toIndentedString(majorRevision)).append("\n");
    sb.append("    minorRevision: ").append(toIndentedString(minorRevision)).append("\n");
    sb.append("    locked: ").append(toIndentedString(locked)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    fileUri: ").append(toIndentedString(fileUri)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
    sb.append("    folderType: ").append(toIndentedString(folderType)).append("\n");
    sb.append("    sourceRevisionUri: ").append(toIndentedString(sourceRevisionUri)).append("\n");
    sb.append("    copyTimeStamp: ").append(toIndentedString(copyTimeStamp)).append("\n");
    sb.append("    links: ").append(toIndentedString(links)).append("\n");
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

