/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.model;

import com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IBVariableDO {
    protected byte archivedPublishState;
    protected String attachments;
    protected String codeChangeUnpublished;
    protected String codeEverBeenPublished;
    protected String containsAllPossibleValues;
    protected String defaultValueIsMissing;
    protected String description;
    protected byte firstOccurrenceToPublish;
    protected String forceOverwrite;
    protected String forced;
    protected String hidden;
    protected String hideVariable;
    protected String id;
    protected String level;
    protected String loadedFromOldDavLocation;
    protected String loading;
    protected String lockedBy;
    protected String metadataType;
    protected String name;
    protected String needToPersist;
    protected String noWritePermission;
    protected String oldVersionNumber;
    protected String persistState;
    protected String persisting;
    protected String physicalName;
    protected String presentInDS2Code;
    protected String processVariableName;
    protected byte publishState;
    protected String readOnly;
    protected String required;
    protected String selected;
    protected String shared;
    protected ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value;
    protected String testVarInfoId;
    protected byte type;
    protected String typeDescription;
    protected float versionNumber;
    protected byte whereClauseOp;
    protected String whoModified;
    protected String concreteClass;
    protected Byte listpos;
    protected Short objid;
}
