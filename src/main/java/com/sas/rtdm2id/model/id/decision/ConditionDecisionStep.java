/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.decision;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO;
import com.sas.rtdm2id.util.object.processing.CommonProcessing;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ConditionDecisionStep {
    @JsonProperty("steps")
    private List<Step> flow = null;

    public List<Step> getFlow() {
        return flow;
    }

    public void setFlow(List<Step> flow) {
        this.flow = flow;
    }

    public ConditionDecisionStep addFlowItem(Step flowItem) {
        if (this.flow == null) {
            this.flow = new ArrayList<>();
        }
        this.flow.add(flowItem);
        return this;
    }
}
