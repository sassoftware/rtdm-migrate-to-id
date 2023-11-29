/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.dao;

import com.sas.rtdm2id.model.id.decision.ConditionBranch;
import com.sas.rtdm2id.model.id.decision.CustomObjectStep;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.globals.GlobalVariable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Getter
@Setter
public class MapStorage {
    private String accessToken;
    private String baseIp;
    private String protocol;
    private String parentFolderUri;

    private final List<String> existingNodeIds = new ArrayList<>();

    private final Map<String, Step> ruleSetStepMap = new HashMap<>();
    private final Map<String, GlobalVariable> globalVariableMap = new HashMap<>();
    private final Map<String, Date> ruleSetModifiedDate = new HashMap<>();
    private final Map<String, Boolean> ruleSetIsUpdated = new HashMap<>();
    private final Map<String, Boolean> ruleSetIsActionUpdated = new HashMap<>();
    private final Map<String, List<Step>> nodeIdStepMap = new HashMap<>();
    private final Map<String, ConditionBranch> branchNodeIdConditionBranchMap = new HashMap<>();
    private final Map<Short, CustomObjectStep> customObjectStepHashMap = new HashMap<>();
    private final Map<String, String> calculatedVariableMap = new HashMap<>();
    private final Set<Step> calculatedVariableRulesetNodes = new HashSet<>();
    private final Map<String, String> varInfoIdToVariableNameMap = new HashMap<>();

    public void clearListsAndMaps() {
        ruleSetStepMap.clear();
        globalVariableMap.clear();
        ruleSetModifiedDate.clear();
        ruleSetIsUpdated.clear();
        ruleSetIsActionUpdated.clear();
        nodeIdStepMap.clear();
        branchNodeIdConditionBranchMap.clear();
        customObjectStepHashMap.clear();
        existingNodeIds.clear();
        calculatedVariableMap.clear();
        calculatedVariableRulesetNodes.clear();
        varInfoIdToVariableNameMap.clear();
    }

}
