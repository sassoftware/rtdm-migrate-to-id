/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.dao;

import com.sas.rtdm2id.model.id.decision.ConditionBranch;
import com.sas.rtdm2id.model.id.decision.CustomObjectStep;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.files.CodeFileSignature;
import com.sas.rtdm2id.model.id.globals.GlobalVariable;
import com.sas.rtdm2id.model.id.segmentation.SegmentationTree;
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
    private boolean useCrossBranchLinks;

    private final List<String> existingNodeIds = new ArrayList<>();
    private final List<String> crossBranchLinksAdded = new ArrayList<>();
    private final List<String> decisionNodeLinkMap = new ArrayList<>();

    private final Map<String, Step> ruleSetStepMap = new HashMap<>();
    private final Map<String, GlobalVariable> globalVariableMap = new HashMap<>();
    private final Map<String, Date> ruleSetModifiedDate = new HashMap<>();
    private final Map<String, Boolean> ruleSetIsUpdated = new HashMap<>();
    private final Map<String, Boolean> ruleSetIsActionUpdated = new HashMap<>();
    private final Map<String, List<Step>> nodeIdStepMap = new HashMap<>();
    private final Map<String, ConditionBranch> branchNodeIdConditionBranchMap = new HashMap<>();
    private final Map<Short, CustomObjectStep> customObjectStepHashMap = new HashMap<>();
    private final Map<Short, List<SegmentationTree.SignatureTerm>> segmentationTreeSignatureMap = new HashMap<>();
    private final Map<String, String> calculatedVariableMap = new HashMap<>();
    private final Map<String, String> calculatedVariableDataTypeMap = new HashMap<>();
    // RTDM process/subdiagram output id ("<Node>.<Process><var>") -> the name it is written under in the Decision
    private final Map<String, String> nodeOutputReferences = new HashMap<>();
    // Lower-cased sanitized Decision term a process/subdiagram output is written under -> its ID data type
    private final Map<String, String> nodeOutputDataTypes = new HashMap<>();
    // Process node objid -> the signature of the Viya code file its step calls (created or reused)
    private final Map<Short, List<CodeFileSignature.Term>> codeFileSignatures = new HashMap<>();
    private final Set<Step> calculatedVariableRulesetNodes = Collections.newSetFromMap(new IdentityHashMap<>());
    // Consumer node id -> the one RuleSet step computing the calculated items that node uses (with their calculated
    // dependencies), in the order they are computed
    private final Map<String, CalculatedNodeGroup> calculatedNodeGroups = new LinkedHashMap<>();
    private final Map<String, String> varInfoIdToVariableNameMap = new HashMap<>();
    private final Map<String, String> nodeIdLinkLabelMap = new HashMap<>();
    private final Map<String, List<Step>> crossBranchLinkSteps = new HashMap<>();

    public void clearListsAndMaps() {
        // request context must not outlive the request that set it
        accessToken = null;
        baseIp = null;
        protocol = null;
        parentFolderUri = null;
        ruleSetStepMap.clear();
        globalVariableMap.clear();
        ruleSetModifiedDate.clear();
        ruleSetIsUpdated.clear();
        ruleSetIsActionUpdated.clear();
        nodeIdStepMap.clear();
        branchNodeIdConditionBranchMap.clear();
        customObjectStepHashMap.clear();
        segmentationTreeSignatureMap.clear();
        existingNodeIds.clear();
        calculatedVariableMap.clear();
        calculatedVariableDataTypeMap.clear();
        nodeOutputReferences.clear();
        nodeOutputDataTypes.clear();
        codeFileSignatures.clear();
        calculatedVariableRulesetNodes.clear();
        calculatedNodeGroups.clear();
        varInfoIdToVariableNameMap.clear();
        nodeIdLinkLabelMap.clear();
        crossBranchLinksAdded.clear();
        decisionNodeLinkMap.clear();
        crossBranchLinkSteps.clear();
    }

    public record CalculatedNodeGroup(Step step, Set<String> items) { }

}
