/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.mapper;

import com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO;
import com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO;
import com.sas.rtdm2id.model.rtdm.StartNodeDataDO;
import com.sas.rtdm2id.util.model.IBVariableDO;
import org.mapstruct.Mapper;
//import org.mapstruct.factory.Mappers;

public interface IBVariableDOMapper {
    //IBVariableDOMapper VARIABLE_DO_MAPPER = Mappers.getMapper(IBVariableDOMapper.class);

    IBVariableDO ibVariableDoGet(StartNodeDataDO.Event.ReplyVars.IBVariableDO ibVariableDO);

    IBVariableDO ibVariableDoGet(StartNodeDataDO.Event.RequestVars.IBVariableDO ibVariableDO);

    IBVariableDO ibVariableDoGet(ProcessNodeDataDO.Process.InputVariableList.IBVariableDO ibVariableDO);

    IBVariableDO ibVariableDoGet(ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO ibVariableDO);

    IBVariableDO ibVariableDoGet(ReplyNodeDataDO.EventReply.ReplyVars.IBVariableDO ibVariableDO);

    IBVariableDO ibVariableDoGet(com.sas.rtdm2id.model.rtdm.extension.IBVariableDO ibVariableDO);

}
