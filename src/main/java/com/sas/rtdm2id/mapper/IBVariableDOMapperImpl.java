/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.mapper;

import com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO.Value;
import com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO.Value.ValueTypeVarInfoDO;
import com.sas.rtdm2id.util.model.IBVariableDO;

public class IBVariableDOMapperImpl implements IBVariableDOMapper {

    @Override
    public IBVariableDO ibVariableDoGet(com.sas.rtdm2id.model.rtdm.StartNodeDataDO.Event.ReplyVars.IBVariableDO ibVariableDO) {
        if ( ibVariableDO == null ) {
            return null;
        }

        IBVariableDO iBVariableDO = new IBVariableDO();

        iBVariableDO.setArchivedPublishState( ibVariableDO.getArchivedPublishState() );
        iBVariableDO.setAttachments( ibVariableDO.getAttachments() );
        iBVariableDO.setCodeChangeUnpublished( ibVariableDO.getCodeChangeUnpublished() );
        iBVariableDO.setCodeEverBeenPublished( ibVariableDO.getCodeEverBeenPublished() );
        iBVariableDO.setContainsAllPossibleValues( ibVariableDO.getContainsAllPossibleValues() );
        iBVariableDO.setDefaultValueIsMissing( ibVariableDO.getDefaultValueIsMissing() );
        iBVariableDO.setDescription( ibVariableDO.getDescription() );
        iBVariableDO.setFirstOccurrenceToPublish( ibVariableDO.getFirstOccurrenceToPublish() );
        iBVariableDO.setForceOverwrite( ibVariableDO.getForceOverwrite() );
        iBVariableDO.setForced( ibVariableDO.getForced() );
        iBVariableDO.setHidden( ibVariableDO.getHidden() );
        iBVariableDO.setHideVariable( ibVariableDO.getHideVariable() );
        iBVariableDO.setId( ibVariableDO.getId() );
        iBVariableDO.setLevel( ibVariableDO.getLevel() );
        iBVariableDO.setLoadedFromOldDavLocation( ibVariableDO.getLoadedFromOldDavLocation() );
        iBVariableDO.setLoading( ibVariableDO.getLoading() );
        iBVariableDO.setLockedBy( ibVariableDO.getLockedBy() );
        iBVariableDO.setMetadataType( ibVariableDO.getMetadataType() );
        iBVariableDO.setName( ibVariableDO.getName() );
        iBVariableDO.setNeedToPersist( ibVariableDO.getNeedToPersist() );
        iBVariableDO.setNoWritePermission( ibVariableDO.getNoWritePermission() );
        iBVariableDO.setOldVersionNumber( ibVariableDO.getOldVersionNumber() );
        iBVariableDO.setPersistState( ibVariableDO.getPersistState() );
        iBVariableDO.setPersisting( ibVariableDO.getPersisting() );
        iBVariableDO.setPhysicalName( ibVariableDO.getPhysicalName() );
        iBVariableDO.setPresentInDS2Code( ibVariableDO.getPresentInDS2Code() );
        iBVariableDO.setProcessVariableName( ibVariableDO.getProcessVariableName() );
        iBVariableDO.setPublishState( ibVariableDO.getPublishState() );
        iBVariableDO.setReadOnly( ibVariableDO.getReadOnly() );
        iBVariableDO.setRequired( ibVariableDO.getRequired() );
        iBVariableDO.setSelected( ibVariableDO.getSelected() );
        iBVariableDO.setShared( ibVariableDO.getShared() );
        iBVariableDO.setValue( ibVariableDO.getValue() );
        iBVariableDO.setTestVarInfoId( ibVariableDO.getTestVarInfoId() );
        iBVariableDO.setType( ibVariableDO.getType() );
        iBVariableDO.setTypeDescription( ibVariableDO.getTypeDescription() );
        iBVariableDO.setVersionNumber( ibVariableDO.getVersionNumber() );
        iBVariableDO.setWhereClauseOp( ibVariableDO.getWhereClauseOp() );
        iBVariableDO.setWhoModified( ibVariableDO.getWhoModified() );
        iBVariableDO.setConcreteClass( ibVariableDO.getConcreteClass() );
        iBVariableDO.setListpos( ibVariableDO.getListpos() );
        iBVariableDO.setObjid( ibVariableDO.getObjid() );

        return iBVariableDO;
    }

    @Override
    public IBVariableDO ibVariableDoGet(com.sas.rtdm2id.model.rtdm.StartNodeDataDO.Event.RequestVars.IBVariableDO ibVariableDO) {
        if ( ibVariableDO == null ) {
            return null;
        }

        IBVariableDO iBVariableDO = new IBVariableDO();

        iBVariableDO.setArchivedPublishState( ibVariableDO.getArchivedPublishState() );
        iBVariableDO.setAttachments( ibVariableDO.getAttachments() );
        iBVariableDO.setCodeChangeUnpublished( ibVariableDO.getCodeChangeUnpublished() );
        iBVariableDO.setCodeEverBeenPublished( ibVariableDO.getCodeEverBeenPublished() );
        iBVariableDO.setContainsAllPossibleValues( ibVariableDO.getContainsAllPossibleValues() );
        iBVariableDO.setDefaultValueIsMissing( ibVariableDO.getDefaultValueIsMissing() );
        iBVariableDO.setDescription( ibVariableDO.getDescription() );
        iBVariableDO.setFirstOccurrenceToPublish( ibVariableDO.getFirstOccurrenceToPublish() );
        iBVariableDO.setForceOverwrite( ibVariableDO.getForceOverwrite() );
        iBVariableDO.setForced( ibVariableDO.getForced() );
        iBVariableDO.setHidden( ibVariableDO.getHidden() );
        iBVariableDO.setHideVariable( ibVariableDO.getHideVariable() );
        iBVariableDO.setId( ibVariableDO.getId() );
        iBVariableDO.setLevel( ibVariableDO.getLevel() );
        iBVariableDO.setLoadedFromOldDavLocation( ibVariableDO.getLoadedFromOldDavLocation() );
        iBVariableDO.setLoading( ibVariableDO.getLoading() );
        iBVariableDO.setLockedBy( ibVariableDO.getLockedBy() );
        iBVariableDO.setMetadataType( ibVariableDO.getMetadataType() );
        iBVariableDO.setName( ibVariableDO.getName() );
        iBVariableDO.setNeedToPersist( ibVariableDO.getNeedToPersist() );
        iBVariableDO.setNoWritePermission( ibVariableDO.getNoWritePermission() );
        iBVariableDO.setOldVersionNumber( ibVariableDO.getOldVersionNumber() );
        iBVariableDO.setPersistState( ibVariableDO.getPersistState() );
        iBVariableDO.setPersisting( ibVariableDO.getPersisting() );
        iBVariableDO.setPhysicalName( ibVariableDO.getPhysicalName() );
        iBVariableDO.setPresentInDS2Code( ibVariableDO.getPresentInDS2Code() );
        iBVariableDO.setProcessVariableName( ibVariableDO.getProcessVariableName() );
        iBVariableDO.setPublishState( ibVariableDO.getPublishState() );
        iBVariableDO.setReadOnly( ibVariableDO.getReadOnly() );
        iBVariableDO.setRequired( ibVariableDO.getRequired() );
        iBVariableDO.setSelected( ibVariableDO.getSelected() );
        iBVariableDO.setShared( ibVariableDO.getShared() );
        iBVariableDO.setValue( ibVariableDO.getValue() );
        iBVariableDO.setTestVarInfoId( ibVariableDO.getTestVarInfoId() );
        iBVariableDO.setType( ibVariableDO.getType() );
        iBVariableDO.setTypeDescription( ibVariableDO.getTypeDescription() );
        iBVariableDO.setVersionNumber( ibVariableDO.getVersionNumber() );
        iBVariableDO.setWhereClauseOp( ibVariableDO.getWhereClauseOp() );
        iBVariableDO.setWhoModified( ibVariableDO.getWhoModified() );
        iBVariableDO.setConcreteClass( ibVariableDO.getConcreteClass() );
        iBVariableDO.setListpos( ibVariableDO.getListpos() );
        iBVariableDO.setObjid( ibVariableDO.getObjid() );

        return iBVariableDO;
    }

    @Override
    public IBVariableDO ibVariableDoGet(com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO ibVariableDO) {
        if ( ibVariableDO == null ) {
            return null;
        }

        IBVariableDO iBVariableDO = new IBVariableDO();

        iBVariableDO.setArchivedPublishState( ibVariableDO.getArchivedPublishState() );
        iBVariableDO.setAttachments( ibVariableDO.getAttachments() );
        iBVariableDO.setCodeChangeUnpublished( ibVariableDO.getCodeChangeUnpublished() );
        iBVariableDO.setCodeEverBeenPublished( ibVariableDO.getCodeEverBeenPublished() );
        iBVariableDO.setContainsAllPossibleValues( ibVariableDO.getContainsAllPossibleValues() );
        iBVariableDO.setDefaultValueIsMissing( ibVariableDO.getDefaultValueIsMissing() );
        iBVariableDO.setDescription( ibVariableDO.getDescription() );
        iBVariableDO.setFirstOccurrenceToPublish( ibVariableDO.getFirstOccurrenceToPublish() );
        iBVariableDO.setForceOverwrite( ibVariableDO.getForceOverwrite() );
        iBVariableDO.setForced( ibVariableDO.getForced() );
        iBVariableDO.setHidden( ibVariableDO.getHidden() );
        iBVariableDO.setHideVariable( ibVariableDO.getHideVariable() );
        iBVariableDO.setId( ibVariableDO.getId() );
        iBVariableDO.setLevel( ibVariableDO.getLevel() );
        iBVariableDO.setLoadedFromOldDavLocation( ibVariableDO.getLoadedFromOldDavLocation() );
        iBVariableDO.setLoading( ibVariableDO.getLoading() );
        iBVariableDO.setLockedBy( ibVariableDO.getLockedBy() );
        iBVariableDO.setMetadataType( ibVariableDO.getMetadataType() );
        iBVariableDO.setName( ibVariableDO.getName() );
        iBVariableDO.setNeedToPersist( ibVariableDO.getNeedToPersist() );
        iBVariableDO.setNoWritePermission( ibVariableDO.getNoWritePermission() );
        iBVariableDO.setOldVersionNumber( ibVariableDO.getOldVersionNumber() );
        iBVariableDO.setPersistState( ibVariableDO.getPersistState() );
        iBVariableDO.setPersisting( ibVariableDO.getPersisting() );
        iBVariableDO.setPhysicalName( ibVariableDO.getPhysicalName() );
        iBVariableDO.setPresentInDS2Code( ibVariableDO.getPresentInDS2Code() );
        iBVariableDO.setProcessVariableName( ibVariableDO.getProcessVariableName() );
        iBVariableDO.setPublishState( ibVariableDO.getPublishState() );
        iBVariableDO.setReadOnly( ibVariableDO.getReadOnly() );
        iBVariableDO.setRequired( ibVariableDO.getRequired() );
        iBVariableDO.setSelected( ibVariableDO.getSelected() );
        iBVariableDO.setShared( ibVariableDO.getShared() );
        iBVariableDO.setValue( ibVariableDO.getValue() );
        iBVariableDO.setTestVarInfoId( ibVariableDO.getTestVarInfoId() );
        iBVariableDO.setType( ibVariableDO.getType() );
        iBVariableDO.setTypeDescription( ibVariableDO.getTypeDescription() );
        iBVariableDO.setVersionNumber( ibVariableDO.getVersionNumber() );
        iBVariableDO.setWhereClauseOp( ibVariableDO.getWhereClauseOp() );
        iBVariableDO.setWhoModified( ibVariableDO.getWhoModified() );
        iBVariableDO.setConcreteClass( ibVariableDO.getConcreteClass() );
        iBVariableDO.setListpos( ibVariableDO.getListpos() );
        iBVariableDO.setObjid( ibVariableDO.getObjid() );

        return iBVariableDO;
    }

    @Override
    public IBVariableDO ibVariableDoGet(com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO ibVariableDO) {
        if ( ibVariableDO == null ) {
            return null;
        }

        IBVariableDO iBVariableDO = new IBVariableDO();

        iBVariableDO.setArchivedPublishState( ibVariableDO.getArchivedPublishState() );
        iBVariableDO.setAttachments( ibVariableDO.getAttachments() );
        iBVariableDO.setCodeChangeUnpublished( ibVariableDO.getCodeChangeUnpublished() );
        iBVariableDO.setCodeEverBeenPublished( ibVariableDO.getCodeEverBeenPublished() );
        iBVariableDO.setContainsAllPossibleValues( ibVariableDO.getContainsAllPossibleValues() );
        iBVariableDO.setDefaultValueIsMissing( ibVariableDO.getDefaultValueIsMissing() );
        iBVariableDO.setDescription( ibVariableDO.getDescription() );
        iBVariableDO.setFirstOccurrenceToPublish( ibVariableDO.getFirstOccurrenceToPublish() );
        iBVariableDO.setForceOverwrite( ibVariableDO.getForceOverwrite() );
        iBVariableDO.setForced( ibVariableDO.getForced() );
        iBVariableDO.setHidden( ibVariableDO.getHidden() );
        iBVariableDO.setHideVariable( ibVariableDO.getHideVariable() );
        iBVariableDO.setId( ibVariableDO.getId() );
        iBVariableDO.setLevel( ibVariableDO.getLevel() );
        iBVariableDO.setLoadedFromOldDavLocation( ibVariableDO.getLoadedFromOldDavLocation() );
        iBVariableDO.setLoading( ibVariableDO.getLoading() );
        iBVariableDO.setLockedBy( ibVariableDO.getLockedBy() );
        iBVariableDO.setMetadataType( ibVariableDO.getMetadataType() );
        iBVariableDO.setName( ibVariableDO.getName() );
        iBVariableDO.setNeedToPersist( ibVariableDO.getNeedToPersist() );
        iBVariableDO.setNoWritePermission( ibVariableDO.getNoWritePermission() );
        iBVariableDO.setOldVersionNumber( ibVariableDO.getOldVersionNumber() );
        iBVariableDO.setPersistState( ibVariableDO.getPersistState() );
        iBVariableDO.setPersisting( ibVariableDO.getPersisting() );
        iBVariableDO.setPhysicalName( ibVariableDO.getPhysicalName() );
        iBVariableDO.setPresentInDS2Code( ibVariableDO.getPresentInDS2Code() );
        iBVariableDO.setProcessVariableName( ibVariableDO.getProcessVariableName() );
        iBVariableDO.setPublishState( ibVariableDO.getPublishState() );
        iBVariableDO.setReadOnly( ibVariableDO.getReadOnly() );
        iBVariableDO.setRequired( ibVariableDO.getRequired() );
        iBVariableDO.setSelected( ibVariableDO.getSelected() );
        iBVariableDO.setShared( ibVariableDO.getShared() );
        iBVariableDO.setTestVarInfoId( ibVariableDO.getTestVarInfoId() );
        iBVariableDO.setType( ibVariableDO.getType() );
        iBVariableDO.setTypeDescription( ibVariableDO.getTypeDescription() );
        iBVariableDO.setVersionNumber( ibVariableDO.getVersionNumber() );
        iBVariableDO.setWhereClauseOp( ibVariableDO.getWhereClauseOp() );
        iBVariableDO.setWhoModified( ibVariableDO.getWhoModified() );
        iBVariableDO.setConcreteClass( ibVariableDO.getConcreteClass() );
        if ( ibVariableDO.getListpos() != null ) {
            iBVariableDO.setListpos( ibVariableDO.getListpos().byteValue() );
        }
        iBVariableDO.setObjid( ibVariableDO.getObjid() );

        return iBVariableDO;
    }

    @Override
    public IBVariableDO ibVariableDoGet(com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO ibVariableDO) {
        if ( ibVariableDO == null ) {
            return null;
        }

        IBVariableDO iBVariableDO = new IBVariableDO();

        iBVariableDO.setArchivedPublishState( ibVariableDO.getArchivedPublishState() );
        iBVariableDO.setAttachments( ibVariableDO.getAttachments() );
        iBVariableDO.setCodeChangeUnpublished( ibVariableDO.getCodeChangeUnpublished() );
        iBVariableDO.setCodeEverBeenPublished( ibVariableDO.getCodeEverBeenPublished() );
        iBVariableDO.setContainsAllPossibleValues( ibVariableDO.getContainsAllPossibleValues() );
        iBVariableDO.setDefaultValueIsMissing( ibVariableDO.getDefaultValueIsMissing() );
        iBVariableDO.setDescription( ibVariableDO.getDescription() );
        iBVariableDO.setFirstOccurrenceToPublish( ibVariableDO.getFirstOccurrenceToPublish() );
        iBVariableDO.setForceOverwrite( ibVariableDO.getForceOverwrite() );
        iBVariableDO.setForced( ibVariableDO.getForced() );
        iBVariableDO.setHidden( ibVariableDO.getHidden() );
        iBVariableDO.setHideVariable( ibVariableDO.getHideVariable() );
        iBVariableDO.setId( ibVariableDO.getId() );
        iBVariableDO.setLevel( ibVariableDO.getLevel() );
        iBVariableDO.setLoadedFromOldDavLocation( ibVariableDO.getLoadedFromOldDavLocation() );
        iBVariableDO.setLoading( ibVariableDO.getLoading() );
        iBVariableDO.setLockedBy( ibVariableDO.getLockedBy() );
        iBVariableDO.setMetadataType( ibVariableDO.getMetadataType() );
        iBVariableDO.setName( ibVariableDO.getName() );
        iBVariableDO.setNeedToPersist( ibVariableDO.getNeedToPersist() );
        iBVariableDO.setNoWritePermission( ibVariableDO.getNoWritePermission() );
        iBVariableDO.setOldVersionNumber( ibVariableDO.getOldVersionNumber() );
        iBVariableDO.setPersistState( ibVariableDO.getPersistState() );
        iBVariableDO.setPersisting( ibVariableDO.getPersisting() );
        iBVariableDO.setPhysicalName( ibVariableDO.getPhysicalName() );
        iBVariableDO.setPresentInDS2Code( ibVariableDO.getPresentInDS2Code() );
        iBVariableDO.setProcessVariableName( ibVariableDO.getProcessVariableName() );
        iBVariableDO.setPublishState( ibVariableDO.getPublishState() );
        iBVariableDO.setReadOnly( ibVariableDO.getReadOnly() );
        iBVariableDO.setRequired( ibVariableDO.getRequired() );
        iBVariableDO.setSelected( ibVariableDO.getSelected() );
        iBVariableDO.setShared( ibVariableDO.getShared() );
        iBVariableDO.setValue( valueToValue( ibVariableDO.getValue() ) );
        iBVariableDO.setTestVarInfoId( ibVariableDO.getTestVarInfoId() );
        iBVariableDO.setType( ibVariableDO.getType() );
        iBVariableDO.setTypeDescription( ibVariableDO.getTypeDescription() );
        iBVariableDO.setVersionNumber( ibVariableDO.getVersionNumber() );
        iBVariableDO.setWhereClauseOp( ibVariableDO.getWhereClauseOp() );
        iBVariableDO.setWhoModified( ibVariableDO.getWhoModified() );
        iBVariableDO.setConcreteClass( ibVariableDO.getConcreteClass() );
        iBVariableDO.setListpos( ibVariableDO.getListpos() );
        iBVariableDO.setObjid( ibVariableDO.getObjid() );

        return iBVariableDO;
    }

    protected com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO valueTypeVarInfoDOToValueTypeVarInfoDO(ValueTypeVarInfoDO valueTypeVarInfoDO) {
        if ( valueTypeVarInfoDO == null ) {
            return null;
        }

        com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO valueTypeVarInfoDO1 = new com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO();

        valueTypeVarInfoDO1.setArchivedPublishState( valueTypeVarInfoDO.getArchivedPublishState() );
        valueTypeVarInfoDO1.setCodeChangeUnpublished( valueTypeVarInfoDO.getCodeChangeUnpublished() );
        valueTypeVarInfoDO1.setCodeEverBeenPublished( valueTypeVarInfoDO.getCodeEverBeenPublished() );
        valueTypeVarInfoDO1.setFirstOccurrenceToPublish( valueTypeVarInfoDO.getFirstOccurrenceToPublish() );
        valueTypeVarInfoDO1.setLoadedFromOldDavLocation( valueTypeVarInfoDO.getLoadedFromOldDavLocation() );
        valueTypeVarInfoDO1.setLoading( valueTypeVarInfoDO.getLoading() );
        valueTypeVarInfoDO1.setNeedToPersist( valueTypeVarInfoDO.getNeedToPersist() );
        valueTypeVarInfoDO1.setOldVersionNumber( valueTypeVarInfoDO.getOldVersionNumber() );
        valueTypeVarInfoDO1.setPersisting( valueTypeVarInfoDO.getPersisting() );
        valueTypeVarInfoDO1.setPublishState( valueTypeVarInfoDO.getPublishState() );
        valueTypeVarInfoDO1.setVarInfoArray( valueTypeVarInfoDO.getVarInfoArray() );
        valueTypeVarInfoDO1.setVarInfoId( valueTypeVarInfoDO.getVarInfoId() );
        valueTypeVarInfoDO1.setVarName( valueTypeVarInfoDO.getVarName() );
        valueTypeVarInfoDO1.setVarInfoPhysicalName( valueTypeVarInfoDO.getVarInfoPhysicalName() );
        valueTypeVarInfoDO1.setVarInfoSource( valueTypeVarInfoDO.getVarInfoSource() );
        valueTypeVarInfoDO1.setVarInfoSourceName( valueTypeVarInfoDO.getVarInfoSourceName() );
        valueTypeVarInfoDO1.setVarInfoSubtype( valueTypeVarInfoDO.getVarInfoSubtype() );
        valueTypeVarInfoDO1.setType( valueTypeVarInfoDO.getType() );
        valueTypeVarInfoDO1.setVersionNumber( valueTypeVarInfoDO.getVersionNumber() );
        valueTypeVarInfoDO1.setConcreteClass( valueTypeVarInfoDO.getConcreteClass() );
        valueTypeVarInfoDO1.setObjid( valueTypeVarInfoDO.getObjid() );

        return valueTypeVarInfoDO1;
    }

    protected com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value valueToValue(Value value) {
        if ( value == null ) {
            return null;
        }

        com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value1 = new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value();

        value1.setArchivedPublishState( value.getArchivedPublishState() );
        value1.setCodeChangeUnpublished( value.getCodeChangeUnpublished() );
        value1.setCodeEverBeenPublished( value.getCodeEverBeenPublished() );
        value1.setFirstOccurrenceToPublish( value.getFirstOccurrenceToPublish() );
        value1.setLoadedFromOldDavLocation( value.getLoadedFromOldDavLocation() );
        value1.setLoading( value.getLoading() );
        value1.setNeedToPersist( value.getNeedToPersist() );
        value1.setOldVersionNumber( value.getOldVersionNumber() );
        value1.setPersisting( value.getPersisting() );
        value1.setPublishState( value.getPublishState() );
        value1.setStringValue( value.getStringValue() );
        value1.setDoubleValue(value.getDoubleValue());
        value1.setBooleanValue(value.getBooleanValue());
        value1.setLongValue(value.getLongValue());
        value1.setDateValue(value.getDateValue());
        value1.setDateValueString(value.getDateValueString());
        value1.setType( value.getType() );
        value1.setValueTypeVarInfoDO( valueTypeVarInfoDOToValueTypeVarInfoDO( value.getValueTypeVarInfoDO() ) );
        value1.setVersionNumber( value.getVersionNumber() );
        value1.setConcreteClass( value.getConcreteClass() );
        value1.setObjid( value.getObjid() );

        return value1;
    }
    @Override
    public IBVariableDO ibVariableDoGet(com.sas.rtdm2id.model.rtdm.extension.IBVariableDO ibVariableDO) {
         if ( ibVariableDO == null ) {
            return null;
        }

        IBVariableDO iBVariableDO = new IBVariableDO();

        iBVariableDO.setArchivedPublishState( ibVariableDO.getArchivedPublishState() );
        iBVariableDO.setAttachments( ibVariableDO.getAttachments() );
        iBVariableDO.setCodeChangeUnpublished( ibVariableDO.getCodeChangeUnpublished() );
        iBVariableDO.setCodeEverBeenPublished( ibVariableDO.getCodeEverBeenPublished() );
        iBVariableDO.setContainsAllPossibleValues( ibVariableDO.getContainsAllPossibleValues() );
        iBVariableDO.setDefaultValueIsMissing( ibVariableDO.getDefaultValueIsMissing() );
        iBVariableDO.setDescription( ibVariableDO.getDescription() );
        iBVariableDO.setFirstOccurrenceToPublish( ibVariableDO.getFirstOccurrenceToPublish() );
        iBVariableDO.setForceOverwrite( ibVariableDO.getForceOverwrite() );
        iBVariableDO.setForced( ibVariableDO.getForced() );
        iBVariableDO.setHidden( ibVariableDO.getHidden() );
        iBVariableDO.setHideVariable( ibVariableDO.getHideVariable() );
        iBVariableDO.setId( ibVariableDO.getId() );
        iBVariableDO.setLevel( ibVariableDO.getLevel() );
        iBVariableDO.setLoadedFromOldDavLocation( ibVariableDO.getLoadedFromOldDavLocation() );
        iBVariableDO.setLoading( ibVariableDO.getLoading() );
        iBVariableDO.setLockedBy( ibVariableDO.getLockedBy() );
        iBVariableDO.setMetadataType( ibVariableDO.getMetadataType() );
        iBVariableDO.setName( ibVariableDO.getName() );
        iBVariableDO.setNeedToPersist( ibVariableDO.getNeedToPersist() );
        iBVariableDO.setNoWritePermission( ibVariableDO.getNoWritePermission() );
        iBVariableDO.setOldVersionNumber( ibVariableDO.getOldVersionNumber() );
        iBVariableDO.setPersistState( ibVariableDO.getPersistState() );
        iBVariableDO.setPersisting( ibVariableDO.getPersisting() );
        iBVariableDO.setPhysicalName( ibVariableDO.getPhysicalName() );
        iBVariableDO.setPresentInDS2Code( ibVariableDO.getPresentInDS2Code() );
        iBVariableDO.setProcessVariableName( ibVariableDO.getProcessVariableName() );
        iBVariableDO.setPublishState( ibVariableDO.getPublishState() );
        iBVariableDO.setReadOnly( ibVariableDO.getReadOnly() );
        iBVariableDO.setRequired( ibVariableDO.getRequired() );
        iBVariableDO.setSelected( ibVariableDO.getSelected() );
        iBVariableDO.setShared( ibVariableDO.getShared() );
        iBVariableDO.setValue( valueToValue( ibVariableDO.getValue() ) );
        iBVariableDO.setTestVarInfoId( ibVariableDO.getTestVarInfoId() );
        iBVariableDO.setType( ibVariableDO.getType() );
        iBVariableDO.setTypeDescription( ibVariableDO.getTypeDescription() );
        iBVariableDO.setVersionNumber( ibVariableDO.getVersionNumber() );
        iBVariableDO.setWhereClauseOp( ibVariableDO.getWhereClauseOp() );
        iBVariableDO.setWhoModified( ibVariableDO.getWhoModified() );
        iBVariableDO.setConcreteClass( ibVariableDO.getConcreteClass() );
        iBVariableDO.setListpos( ibVariableDO.getListpos() );
        iBVariableDO.setObjid( ibVariableDO.getObjid() );

        return iBVariableDO;
    }

    protected com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value valueToValue(com.sas.rtdm2id.model.rtdm.extension.Value value) {
        if ( value == null ) {
            return null;
        }

        com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value1 = new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value();

        value1.setArchivedPublishState( value.getArchivedPublishState() );
        value1.setCodeChangeUnpublished( value.getCodeChangeUnpublished() );
        value1.setCodeEverBeenPublished( value.getCodeEverBeenPublished() );
        value1.setFirstOccurrenceToPublish( value.getFirstOccurrenceToPublish() );
        value1.setLoadedFromOldDavLocation( value.getLoadedFromOldDavLocation() );
        value1.setLoading( value.getLoading() );
        value1.setNeedToPersist( value.getNeedToPersist() );
        value1.setOldVersionNumber( value.getOldVersionNumber() );
        value1.setPersisting( value.getPersisting() );
        value1.setPublishState( value.getPublishState() );
        value1.setStringValue( value.getStringValue() );
        value1.setDoubleValue(value.getDoubleValue());
        value1.setBooleanValue(value.getBooleanValue());
        value1.setLongValue(value.getLongValue());
        value1.setDateValue(value.getDateValue());
        value1.setDateValueString(value.getDateValueString());
        value1.setListValue(value.getListValue());
        value1.setType( value.getType() );
        value1.setType( value.getType() );
        value1.setValueTypeVarInfoDO( valueTypeVarInfoDOToValueTypeVarInfoDO( value.getValueTypeVarInfoDO() ) );
        value1.setVersionNumber( value.getVersionNumber() );
        value1.setConcreteClass( value.getConcreteClass() );
        value1.setObjid( value.getObjid() );

        return value1;
    }

    protected com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO valueTypeVarInfoDOToValueTypeVarInfoDO(com.sas.rtdm2id.model.rtdm.extension.ValueTypeVarInfoDO valueTypeVarInfoDO) {
        if ( valueTypeVarInfoDO == null ) {
            return null;
        }

        com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO valueTypeVarInfoDO1 = new com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO();

        valueTypeVarInfoDO1.setArchivedPublishState( valueTypeVarInfoDO.getArchivedPublishState() );
        valueTypeVarInfoDO1.setCodeChangeUnpublished( valueTypeVarInfoDO.getCodeChangeUnpublished() );
        valueTypeVarInfoDO1.setCodeEverBeenPublished( valueTypeVarInfoDO.getCodeEverBeenPublished() );
        valueTypeVarInfoDO1.setFirstOccurrenceToPublish( valueTypeVarInfoDO.getFirstOccurrenceToPublish() );
        valueTypeVarInfoDO1.setLoadedFromOldDavLocation( valueTypeVarInfoDO.getLoadedFromOldDavLocation() );
        valueTypeVarInfoDO1.setLoading( valueTypeVarInfoDO.getLoading() );
        valueTypeVarInfoDO1.setNeedToPersist( valueTypeVarInfoDO.getNeedToPersist() );
        valueTypeVarInfoDO1.setOldVersionNumber( valueTypeVarInfoDO.getOldVersionNumber() );
        valueTypeVarInfoDO1.setPersisting( valueTypeVarInfoDO.getPersisting() );
        valueTypeVarInfoDO1.setPublishState( valueTypeVarInfoDO.getPublishState() );
        valueTypeVarInfoDO1.setVarInfoArray( valueTypeVarInfoDO.getVarInfoArray() );
        valueTypeVarInfoDO1.setVarInfoId( valueTypeVarInfoDO.getVarInfoId() );
        valueTypeVarInfoDO1.setVarName( valueTypeVarInfoDO.getVarName() );
        valueTypeVarInfoDO1.setVarInfoPhysicalName( valueTypeVarInfoDO.getVarInfoPhysicalName() );
        valueTypeVarInfoDO1.setVarInfoSource( valueTypeVarInfoDO.getVarInfoSource() );
        valueTypeVarInfoDO1.setVarInfoSourceName( valueTypeVarInfoDO.getVarInfoSourceName() );
        valueTypeVarInfoDO1.setVarInfoSubtype( valueTypeVarInfoDO.getVarInfoSubtype() );
        valueTypeVarInfoDO1.setType( valueTypeVarInfoDO.getType() );
        valueTypeVarInfoDO1.setVersionNumber( valueTypeVarInfoDO.getVersionNumber() );
        valueTypeVarInfoDO1.setConcreteClass( valueTypeVarInfoDO.getConcreteClass() );
        valueTypeVarInfoDO1.setObjid( valueTypeVarInfoDO.getObjid() );

        return valueTypeVarInfoDO1;
    }
}
