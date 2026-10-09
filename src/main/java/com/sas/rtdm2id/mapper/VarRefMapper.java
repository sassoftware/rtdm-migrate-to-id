/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.mapper;

import com.sas.rtdm2id.model.rtdm.SplitNodeDataDO;
import com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO;
import com.sas.rtdm2id.model.rtdm.extension.VarRef;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VarRefMapper {
    VarRefMapper VAR_REF_MAPPER = Mappers.getMapper(VarRefMapper.class);

    VarRef varRefGet(SplitNodeDataDO.SplitOnNodeDataDO.VarRefDO varRefDO);

    @Mapping(target = "discrete", ignore = true)
    @Mapping(target = "noMetadata", ignore = true)
    @Mapping(target = "sasFormat", ignore = true)
    @Mapping(target = "subType", ignore = true)
    @Mapping(target = "valid", ignore = true)
    VarRef varRefGet(ValueTypeVarInfoDO varRefDO);
}
