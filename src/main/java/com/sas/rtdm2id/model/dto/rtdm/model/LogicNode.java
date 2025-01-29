package com.sas.rtdm2id.model.dto.rtdm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogicNode {

    private String logicNodeId;
    private List<String> childNodeIds;
    private String operator;

}
