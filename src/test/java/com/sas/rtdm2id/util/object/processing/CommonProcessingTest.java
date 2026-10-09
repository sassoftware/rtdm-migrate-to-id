package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.CodeFile;
import com.sas.rtdm2id.model.id.decision.CodeFileCollection;
import com.sas.rtdm2id.model.id.decision.ConditionBranch;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.decision.SignatureTerm;
import com.sas.rtdm2id.model.id.globals.GlobalVariable;
import com.sas.rtdm2id.model.id.globals.GlobalVariableCollection;
import com.sas.rtdm2id.model.id.globals.Item;
import com.sas.rtdm2id.model.rtdm.extension.VarRef;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import org.mockito.ArgumentCaptor;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.STRING_CONSTANT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommonProcessingTest {

    @Test
    void typesCalculatedAssignmentSourcesIndependentlyOfListDestinations() {
        MapStorage storage = new MapStorage();
        CommonProcessing processing = org.mockito.Mockito.spy(
            new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
        org.mockito.Mockito.doNothing().when(processing).findOrAddRuleSet(any(), any());
        org.mockito.Mockito.doNothing().when(processing).callPutRuleSet(any(), any());
        org.mockito.Mockito.doNothing().when(processing).addActionToRuleSet(any(), any());
        com.sas.rtdm2id.model.rtdm.Batch batch = new com.sas.rtdm2id.model.rtdm.Batch();
        batch.setLogicalUnit(new com.sas.rtdm2id.model.rtdm.Batch.LogicalUnit());
        com.sas.rtdm2id.model.rtdm.FlowDO flow = mock(
            com.sas.rtdm2id.model.rtdm.FlowDO.class, org.mockito.Mockito.RETURNS_DEEP_STUBS);
        batch.getLogicalUnit().setFlowDO(flow);
        java.util.List<com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO> items = new ArrayList<>();
        String[][] cases = {
            {"isCodebtorInvolved", "5", "boolean list", "boolean"},
            {"022_EmpRoutYLFlgLvCodebtor", "3", "boolean list", "decimal"},
            {"CV_manualIncomeChkResultCd_Codebtor", "0", "double list", "string"}
        };
        for (String[] itemCase : cases) {
            com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO item =
                new com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO();
            item.setName(itemCase[0]);
            item.setCalculatedItemType(itemCase[1]);
            item.setNodeId("source-node");
            item.setExpression("Missing");
            items.add(item);
            String name = processing.makeCalculatedDataItemRuleSetName(item);
            Step producer = new Step();
            producer.setRuleSet(new com.sas.rtdm2id.model.id.decision.RuleSetStep().name(name));
            storage.getRuleSetStepMap().put(name, producer);
            storage.getRuleSetIsUpdated().put(name, true);
            storage.getRuleSetIsActionUpdated().put(name, true);
        }
        when(flow.getCalculatedDataItemList().getCalculatedItemDOs()).thenReturn(items);
        processing.createRuleSets(batch);

        for (String[] itemCase : cases) {
            com.sas.rtdm2id.util.model.IBVariableDO input = new com.sas.rtdm2id.util.model.IBVariableDO();
            input.setName("assignedValue");
            input.setTypeDescription(itemCase[2]);
            input.setValue(new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value());
            com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO reference = new com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO();
            reference.setVarInfoId("calculatedDataItems." + itemCase[0]);
            reference.setVarInfoSource("CalculatedDataItem");
            reference.setVarName(itemCase[0]);
            reference.setType("0".equals(itemCase[1]) ? "Char" : "Numeric");
            input.getValue().setValueTypeVarInfoDO(reference);
            java.util.List<com.sas.rtdm2id.model.id.rules.Signature> signatures = new ArrayList<>();
            java.util.List<com.sas.rtdm2id.model.id.rules.Action> actions = new ArrayList<>();

            processing.addAction(input, actions, signatures, new Step(), new Decision());

            assertEquals(1, signatures.size());
            assertEquals(itemCase[3], signatures.get(0).getDataType(), itemCase[0]);
            assertEquals(processing.sanitizeAndReduceVariableName("CalculatedDataItem." + itemCase[0]),
                signatures.get(0).getName());
            assertEquals("input", signatures.get(0).getDirection());
        }
        storage.clearListsAndMaps();
        assertTrue(storage.getCalculatedVariableDataTypeMap().isEmpty());
    }

    @Test
    void typesCalculatedSourcesInHabofProcessInputRuleSets() throws Exception {
        String extractPath = System.getProperty("calculatedExtract");
        org.junit.jupiter.api.Assumptions.assumeTrue(extractPath != null,
            "Set -DcalculatedExtract to DC_getDataForHabof_v7.xml");
        com.sas.rtdm2id.model.rtdm.Batch batch = (com.sas.rtdm2id.model.rtdm.Batch)
            jakarta.xml.bind.JAXBContext.newInstance(com.sas.rtdm2id.model.rtdm.Batch.class)
                .createUnmarshaller().unmarshal(new java.io.File(extractPath));
        MapStorage storage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing processing = org.mockito.Mockito.spy(new CommonProcessing(restTemplate, storage, new TreeUtil()));
        org.mockito.Mockito.doAnswer(invocation -> {
            String name = invocation.getArgument(0);
            Step step = new Step();
            step.setRuleSet(new com.sas.rtdm2id.model.id.decision.RuleSetStep().name(name));
            storage.getRuleSetStepMap().put(name, step);
            storage.getRuleSetIsUpdated().put(name, false);
            storage.getRuleSetIsActionUpdated().put(name, false);
            return null;
        }).when(processing).findOrAddRuleSet(any(), any());
        java.util.Map<String, com.sas.rtdm2id.model.id.rules.RuleSet> written = new java.util.HashMap<>();
        org.mockito.Mockito.doAnswer(invocation -> {
            com.sas.rtdm2id.model.id.decision.RuleSetStep step = invocation.getArgument(0);
            written.put(step.getName(), invocation.getArgument(1));
            return null;
        }).when(processing).callPutRuleSet(any(), any());
        org.mockito.Mockito.doNothing().when(processing).addActionToRuleSet(any(), any());
        processing.createRuleSets(batch);
        ProcessNodeConverter converter = new ProcessNodeConverter(restTemplate, processing, storage);
        java.util.Set<String> reportedNodes = java.util.Set.of("CBEGXXEQRNENQ3LX", "GHDAXTVZVZBQRGJN",
            "FCAAJQTE5ZAKU1XY", "AGFG1DSUOJEYVTZ3", "BGHCYXS3ANF1XPMG", "ABEFUEQGPFESVCGL",
            "AGCAMGKHYFAJW14V", "HHHDIQ0NARECRVYZ", "ACGF5I44QFBMQAAP", "GEFCK2WTKBCJVY0Z");
        int checkedNodes = 0;
        for (com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO node : batch.getLogicalUnit().getProcessNodeDataDOs()) {
            if (!reportedNodes.contains(node.getNodeId())) {
                continue;
            }
            String name = processing.makeProcessNodeRuleSetName(node) + "_input_values";
            Step step = storage.getRuleSetStepMap().get(name);

            converter.updateRuleSet(node.getProcess().getInputVariableList(), step, new Decision());

            for (com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO input
                    : node.getProcess().getInputVariableList().getIbVariableDOs()) {
                com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO reference =
                    input.getValue() == null ? null : input.getValue().getValueTypeVarInfoDO();
                if (reference == null || reference.getVarInfoId() == null
                        || !reference.getVarInfoId().startsWith("calculatedDataItems.")) {
                    continue;
                }
                String sourceName = processing.sanitizeAndReduceVariableName("CalculatedDataItem."
                    + reference.getVarInfoId().substring("calculatedDataItems.".length()));
                com.sas.rtdm2id.model.id.rules.Signature source = written.get(name).getSignature().stream()
                    .filter(item -> sourceName.equals(item.getName())).findFirst().orElseThrow();
                assertEquals(storage.getCalculatedVariableDataTypeMap().get(reference.getVarInfoId()),
                    source.getDataType(), name + ": " + sourceName);
                assertEquals("input", source.getDirection());
            }
            checkedNodes++;
        }
        assertEquals(reportedNodes.size(), checkedNodes);
        com.sas.rtdm2id.model.id.rules.RuleSet codebtor = written.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith("CV_incomeCheckCodebtorFlg_"))
            .findFirst().orElseThrow().getValue();
        for (String suffix : java.util.List.of("CV_manualIncomeChkResultCd_Codebtor", "CV_automaticIncomeChkResult_Codebtor")) {
            String sourceName = processing.sanitizeAndReduceVariableName(suffix);
            assertTrue(codebtor.getSignature().stream().anyMatch(item -> sourceName.equals(item.getName())
                && "string".equals(item.getDataType()) && "input".equals(item.getDirection())));
        }
        org.mockito.Mockito.verifyNoInteractions(restTemplate);
    }

    @Test
    void sanitizesQualifiedProcessOutputMappingNames() {
        CommonProcessing processing = new CommonProcessing(
            mock(RestTemplate.class), new MapStorage(), new TreeUtil());
        com.sas.rtdm2id.util.model.IBVariableDO output = new com.sas.rtdm2id.util.model.IBVariableDO();
        output.setName("ENGLISH_DESCRIPTION");
        for (boolean reply : java.util.List.of(false, true)) {
            Step step = new Step();

            processing.addOutputMappingForProcess(output, "output", step, reply, new Decision(),
                "Read CMS_NBE_BEST_OFFERS ODS");

            assertEquals(1, step.getMappings().size());
            assertEquals("ProcessReadCMS_NBE_BEST_OFFERSODSENGLISH_DESCRIPTION",
                step.getMappings().get(0).getTargetDecisionTermName());
            assertEquals("ENGLISH_DESCRIPTION", step.getMappings().get(0).getStepTermName());
            assertEquals(com.sas.rtdm2id.model.id.decision.Mapping.DirectionEnum.OUTPUT,
                step.getMappings().get(0).getDirection());
        }
    }

    @Test
    void usesDeclaredRequestTypesForCalculatedExpressionInputs() {
        for (boolean treatmentCampaign : java.util.List.of(false, true)) {
            MapStorage storage = new MapStorage();
            CommonProcessing processing = org.mockito.Mockito.spy(
                new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
            com.sas.rtdm2id.model.rtdm.Batch batch = new com.sas.rtdm2id.model.rtdm.Batch();
            com.sas.rtdm2id.model.rtdm.Batch.LogicalUnit unit = new com.sas.rtdm2id.model.rtdm.Batch.LogicalUnit();
            batch.setLogicalUnit(unit);
            com.sas.rtdm2id.model.rtdm.StartNodeDataDO start = new com.sas.rtdm2id.model.rtdm.StartNodeDataDO();
            unit.setStartNodeDataDO(start);
            java.util.List<com.sas.rtdm2id.model.rtdm.StartNodeDataDO.Event.RequestVars.IBVariableDO> variables;
            if (treatmentCampaign) {
                start.setInputVariables(new com.sas.rtdm2id.model.rtdm.StartNodeDataDO.InputVariables());
                variables = start.getInputVariables().getIbVariableDOs();
            } else {
                start.setEvent(new com.sas.rtdm2id.model.rtdm.StartNodeDataDO.Event());
                start.getEvent().setRequestVars(new com.sas.rtdm2id.model.rtdm.StartNodeDataDO.Event.RequestVars());
                variables = start.getEvent().getRequestVars().getIbVariableDOs();
            }
            for (String name : java.util.List.of("Reference Event Name", "Offer Type", "Customer Age")) {
                com.sas.rtdm2id.model.rtdm.StartNodeDataDO.Event.RequestVars.IBVariableDO variable =
                    new com.sas.rtdm2id.model.rtdm.StartNodeDataDO.Event.RequestVars.IBVariableDO();
                variable.setName(name);
                variable.setTypeDescription("Customer Age".equals(name) ? "integer" : "string");
                variables.add(variable);
            }
            com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO item =
                new com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO();
            item.setName("OfferTypeVar");
            item.setNodeId("HEHE3MFYENF1RNHW");
            item.setCalculatedItemType("0");
            item.setExpression("IIF(<<events.Reference Event Name>> EQ 'Postpaid_Post2Pree_PS_Offers'"
                + " & <<events.Offer Type>> EQ 'AutoReg' & <<inputVariables.Customer Age>> > 18"
                + " & MISSING(<<events.Unknown Input>>) & <<globals.GV_STATUS_MSG_SUCCESS>> EQ 'SUCCESS'"
                + " & <<calculatedDataItems.isCodebtorInvolved>> EQ 1, 'Regular', <<events.Offer Type>>)");
            com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO dependency =
                new com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO();
            dependency.setName("isCodebtorInvolved");
            dependency.setNodeId("CBEGXXEQRNENQ3LX");
            dependency.setCalculatedItemType("5");
            dependency.setExpression("1");
            com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO globalConsumer = new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO();
            globalConsumer.setNodeId("global-consumer");
            globalConsumer.setProcess(new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process());
            globalConsumer.getProcess().setInputVariableList(
                new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList());
            com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO globalInput =
                new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO();
            globalInput.setName("status");
            globalInput.setTypeDescription("string");
            globalInput.setValue(new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value());
            com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO globalReference = new com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO();
            globalReference.setVarInfoId("globals.GV_STATUS_MSG_SUCCESS");
            globalReference.setType("Char");
            globalInput.getValue().setValueTypeVarInfoDO(globalReference);
            globalConsumer.getProcess().getInputVariableList().getIbVariableDOs().add(globalInput);
            unit.getProcessNodeDataDOs().add(globalConsumer);
            com.sas.rtdm2id.model.rtdm.FlowDO flow = mock(
                com.sas.rtdm2id.model.rtdm.FlowDO.class, org.mockito.Mockito.RETURNS_DEEP_STUBS);
            when(flow.getCalculatedDataItemList().getCalculatedItemDOs()).thenReturn(java.util.List.of(item, dependency));
            unit.setFlowDO(flow);
            String name = processing.makeCalculatedDataItemRuleSetName(item);
            Step step = new Step();
            com.sas.rtdm2id.model.id.decision.RuleSetStep ruleSetStep =
                new com.sas.rtdm2id.model.id.decision.RuleSetStep();
            ruleSetStep.setName(name);
            step.setRuleSet(ruleSetStep);
            storage.getRuleSetStepMap().put(name, step);
            storage.getRuleSetIsUpdated().put(name, false);
            storage.getRuleSetIsActionUpdated().put(name, false);
            String dependencyName = processing.makeCalculatedDataItemRuleSetName(dependency);
            Step dependencyStep = new Step();
            com.sas.rtdm2id.model.id.decision.RuleSetStep dependencyRuleSet =
                new com.sas.rtdm2id.model.id.decision.RuleSetStep();
            dependencyRuleSet.setName(dependencyName);
            dependencyStep.setRuleSet(dependencyRuleSet);
            storage.getRuleSetStepMap().put(dependencyName, dependencyStep);
            storage.getRuleSetIsUpdated().put(dependencyName, true);
            storage.getRuleSetIsActionUpdated().put(dependencyName, true);
            org.mockito.Mockito.doNothing().when(processing).findOrAddRuleSet(any(), any());
            org.mockito.Mockito.doNothing().when(processing).callPutRuleSet(any(), any());
            org.mockito.Mockito.doNothing().when(processing).addActionToRuleSet(any(), any());

            processing.createRuleSets(batch);

            ArgumentCaptor<com.sas.rtdm2id.model.id.rules.RuleSet> captor =
                ArgumentCaptor.forClass(com.sas.rtdm2id.model.id.rules.RuleSet.class);
            verify(processing).callPutRuleSet(eq(ruleSetStep), captor.capture());
            for (String[] expected : new String[][] {
                {"ReferenceEventName", "string"}, {"OfferType", "string"},
                {"CustomerAge", "integer"}, {"UnknownInput", "any"}, {"GV_STATUS_MSG_SUCCESS", "string"},
                {"isCodebtorInvolved", processing.getCalculatedItemDataType("5")}
            }) {
                assertTrue(captor.getValue().getSignature().stream().anyMatch(signature ->
                    expected[0].equals(signature.getName()) && expected[1].equals(signature.getDataType())
                        && "input".equals(signature.getDirection())));
                assertTrue(step.getMappings().stream().anyMatch(mapping ->
                    expected[0].equals(mapping.getStepTermName())
                        && (("GV_STATUS_MSG_SUCCESS".equals(expected[0]) ? "Global"
                            : "isCodebtorInvolved".equals(expected[0]) ? "CalculatedDataItem" : "Request") + expected[0])
                            .equals(mapping.getTargetDecisionTermName())));
            }
        }
    }

    @Test
    void mapsAssignedProcessValuesForNewAndReusedRuleSets() {
        for (boolean existing : java.util.List.of(false, true)) {
            MapStorage storage = new MapStorage();
            CommonProcessing processing = org.mockito.Mockito.spy(
                new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
            org.mockito.Mockito.doNothing().when(processing).callPutRuleSet(any(), any());
            org.mockito.Mockito.doNothing().when(processing).addActionToRuleSet(any(), any());
            String name = "process_input_values";
            storage.getRuleSetIsUpdated().put(name, existing);
            storage.getRuleSetIsActionUpdated().put(name, existing);
            Step step = new Step();
            com.sas.rtdm2id.model.id.decision.RuleSetStep ruleSet =
                new com.sas.rtdm2id.model.id.decision.RuleSetStep();
            ruleSet.setName(name);
            step.setRuleSet(ruleSet);
            Decision decision = new Decision();
            com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList inputs =
                new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList();
            String[][] cases = {
                {"where expression", "whereUwDetail", "string"},
                {"where expression", "whereMultiRef", "string"},
                {"where expression", "whereOfferSaveOffers", "string"},
                {"reqSubprodCd", "00_reqSubprodCd", "string"},
                {"uwBranchFlg", "00_uwBranchFlg", "integer"},
                {"afterBudgetFlg", "afterBudgetFlg", "integer"},
                {"otpCustFlg", "01_otpCustFlgToInt", "integer"},
                {"cardproductIdIn", "00_cardproductId", "integer"},
                {"offerTypCd", null, "string"}
            };
            for (String[] inputCase : cases) {
                com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO input =
                    new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO();
                input.setName(inputCase[0]);
                input.setTypeDescription(inputCase[2]);
                com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value =
                    new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value();
                com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO reference =
                    new com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO();
                if (inputCase[1] == null) {
                    value.setStringValue("ELFOGADOTT");
                } else {
                    reference.setVarInfoSource("CalculatedDataItem");
                    reference.setVarInfoId("calculatedDataItems." + inputCase[1]);
                    reference.setVarName(inputCase[1]);
                    value.setValueTypeVarInfoDO(reference);
                }
                input.setValue(value);
                inputs.getIbVariableDOs().add(input);
            }

            new ProcessNodeConverter(mock(RestTemplate.class), processing, storage)
                .updateRuleSet(inputs, step, decision);

            for (String[] inputCase : cases) {
                String assignedName = processing.sanitizeVariableName(inputCase[0]);
                assertTrue(step.getMappings().stream().anyMatch(mapping ->
                    assignedName.equals(mapping.getStepTermName())
                        && assignedName.equals(mapping.getTargetDecisionTermName())
                        && com.sas.rtdm2id.model.id.decision.Mapping.DirectionEnum.INOUT.equals(mapping.getDirection())));
                assertTrue(decision.getSignature().stream().anyMatch(term ->
                    assignedName.equals(term.getName())
                        && processing.getDatatypeOfVar(inputCase[2]).equals(term.getDataType())));
            }
            if (existing) {
                verify(processing, never()).callPutRuleSet(any(), any());
                verify(processing, never()).addActionToRuleSet(any(), any());
            } else {
                ArgumentCaptor<com.sas.rtdm2id.model.id.rules.Rule> ruleCaptor =
                    ArgumentCaptor.forClass(com.sas.rtdm2id.model.id.rules.Rule.class);
                verify(processing).addActionToRuleSet(eq(ruleSet), ruleCaptor.capture());
                assertTrue(ruleCaptor.getValue().getActions().stream().anyMatch(action ->
                    "offerTypCd = 'ELFOGADOTT'".equals(action.getExpression())));
            }
        }
    }

    @Test
    void mapsCalculatedItemInputsAndOutputsForNewAndExistingRuleSets() {
        for (boolean existing : java.util.List.of(false, true)) {
            MapStorage mapStorage = new MapStorage();
            CommonProcessing commonProcessing = org.mockito.Mockito.spy(
                new CommonProcessing(mock(RestTemplate.class), mapStorage, new TreeUtil()));
            com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO item =
                new com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO();
            item.setName("BD_NEW_NUM_4");
            item.setId("BD_NEW_NUM_4");
            item.setNodeId("GCFELW2ZB3CQSIKM");
            item.setCalculatedItemType("4");
            item.setExpression("COALESCE(<<inputVariables.BD_NEW_NUM_4>>,0)");
            String ruleSetName = commonProcessing.makeCalculatedDataItemRuleSetName(item);
            Step step = new Step();
            com.sas.rtdm2id.model.id.decision.RuleSetStep ruleSetStep =
                new com.sas.rtdm2id.model.id.decision.RuleSetStep();
            ruleSetStep.setName(ruleSetName);
            step.setRuleSet(ruleSetStep);
            mapStorage.getRuleSetStepMap().put(ruleSetName, step);
            mapStorage.getRuleSetIsUpdated().put(ruleSetName, existing);
            mapStorage.getRuleSetIsActionUpdated().put(ruleSetName, existing);
            org.mockito.Mockito.doNothing().when(commonProcessing).findOrAddRuleSet(any(), any());
            org.mockito.Mockito.doNothing().when(commonProcessing).callPutRuleSet(any(), any());
            org.mockito.Mockito.doNothing().when(commonProcessing).addActionToRuleSet(any(), any());

            commonProcessing.addRulesetForCalculatedItem(item);
            if (!existing) {
                ArgumentCaptor<com.sas.rtdm2id.model.id.rules.RuleSet> ruleSetCaptor =
                    ArgumentCaptor.forClass(com.sas.rtdm2id.model.id.rules.RuleSet.class);
                verify(commonProcessing).callPutRuleSet(eq(ruleSetStep), ruleSetCaptor.capture());
                assertEquals(2, ruleSetCaptor.getValue().getSignature().size());
                assertTrue(ruleSetCaptor.getValue().getSignature().stream().anyMatch(signature ->
                    "BD_NEW_NUM_4".equals(signature.getName()) && "input".equals(signature.getDirection())));
                ArgumentCaptor<com.sas.rtdm2id.model.id.rules.Rule> ruleCaptor =
                    ArgumentCaptor.forClass(com.sas.rtdm2id.model.id.rules.Rule.class);
                verify(commonProcessing).addActionToRuleSet(eq(ruleSetStep), ruleCaptor.capture());
                assertEquals("CalculatedDataItemBD_NEW_NUM_4=COALESCE(BD_NEW_NUM_4,0)",
                    ruleCaptor.getValue().getActions().get(0).getExpression());
                mapStorage.getRuleSetIsUpdated().put(ruleSetName, true);
            }
            commonProcessing.updateRuleSet("CalculatedDataItemBD_NEW_NUM_4", "double", step);

            assertEquals(2, step.getMappings().size());
            assertTrue(step.getMappings().stream().anyMatch(mapping ->
                "BD_NEW_NUM_4".equals(mapping.getStepTermName())
                    && "RequestBD_NEW_NUM_4".equals(mapping.getTargetDecisionTermName())
                    && com.sas.rtdm2id.model.id.decision.Mapping.DirectionEnum.INPUT.equals(mapping.getDirection())));
            assertTrue(step.getMappings().stream().anyMatch(mapping ->
                "CalculatedDataItemBD_NEW_NUM_4".equals(mapping.getStepTermName())
                    && "CalculatedDataItemBD_NEW_NUM_4".equals(mapping.getTargetDecisionTermName())
                    && com.sas.rtdm2id.model.id.decision.Mapping.DirectionEnum.OUTPUT.equals(mapping.getDirection())));
            if (existing) {
                verify(commonProcessing, never()).callPutRuleSet(any(), any());
                verify(commonProcessing, never()).addActionToRuleSet(any(), any());
            }
        }
    }

    @Test
    void createsProcessInputRuleSetsOnlyWhenInputsExist() {
    RestTemplate restTemplate = mock(RestTemplate.class);
    MapStorage mapStorage = new MapStorage();
    CommonProcessing commonProcessing = org.mockito.Mockito.spy(
        new CommonProcessing(restTemplate, mapStorage, new TreeUtil()));
    org.mockito.Mockito.doNothing().when(commonProcessing).findOrAddRuleSet(any(), any());
    com.sas.rtdm2id.model.rtdm.Batch batch = new com.sas.rtdm2id.model.rtdm.Batch();
    com.sas.rtdm2id.model.rtdm.Batch.LogicalUnit logicalUnit =
        new com.sas.rtdm2id.model.rtdm.Batch.LogicalUnit();
    com.sas.rtdm2id.model.rtdm.FlowDO flow = mock(
        com.sas.rtdm2id.model.rtdm.FlowDO.class, org.mockito.Mockito.RETURNS_DEEP_STUBS);
    when(flow.getCalculatedDataItemList().getCalculatedItemDOs()).thenReturn(Collections.emptyList());
    logicalUnit.setFlowDO(flow);
    batch.setLogicalUnit(logicalUnit);
    com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO node = new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO();
    node.setNodeId("FEDF1VQNB3HKRAYZ");
    com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process process =
        new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process();
    process.setProcessTypeDescription("Java code");
    node.setProcess(process);
    logicalUnit.getProcessNodeDataDOs().add(node);

    commonProcessing.createRuleSets(batch);
    process.setInputVariableList(new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList());
    commonProcessing.createRuleSets(batch);

    verify(commonProcessing, never()).findOrAddRuleSet(any(), any());
    process.getInputVariableList().getIbVariableDOs().add(
        new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO());
    commonProcessing.createRuleSets(batch);

    verify(commonProcessing).findOrAddRuleSet(
        commonProcessing.makeProcessNodeRuleSetName(node) + "_input_values", node.getNodeDescription());
    org.mockito.Mockito.verifyNoInteractions(restTemplate);
    }

    @Test
    void rebuildsInputMappingsWhenProcessRuleSetAlreadyExists() throws Exception {
    RestTemplate restTemplate = mock(RestTemplate.class);
    MapStorage mapStorage = new MapStorage();
    mapStorage.setBaseIp("localhost:8080");
    mapStorage.setProtocol("http");
    mapStorage.setAccessToken("access-token");
    String ruleSetName = "process_input_values";
    mapStorage.getRuleSetIsUpdated().put(ruleSetName, true);
    mapStorage.getRuleSetIsActionUpdated().put(ruleSetName, true);
    CodeFile existingGlobal = new CodeFile();
    existingGlobal.setId("global-id");
    CodeFileCollection collection = new CodeFileCollection();
    collection.setCount(1L);
    collection.setItems(Collections.singletonList(existingGlobal));
    when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
        .thenReturn(new ResponseEntity<>(collection, HttpStatus.OK));
    GlobalVariable global = new GlobalVariable();
    global.setId("global-id");
    global.setName("GlobalurlConsultQuotaInformationWsdl");
    global.setDataType("string");
    global.setStatus("developing");
    global.setActiveVersionId("active-id");
    when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(GlobalVariable.class)))
        .thenReturn(new ResponseEntity<>(global, HttpStatus.OK));
    CommonProcessing commonProcessing = new CommonProcessing(restTemplate, mapStorage, new TreeUtil());
    ProcessNodeConverter converter = new ProcessNodeConverter(restTemplate, commonProcessing, mapStorage);
    com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList inputs =
        new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList();
    com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO input =
        new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO();
    input.setName("urlWsdlMw");
    input.setTypeDescription(STRING_CONSTANT);
    com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value =
        new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value();
    com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO reference = new com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO();
    reference.setVarInfoSource("Global");
    reference.setVarInfoId("globals.urlConsultQuotaInformationWsdl");
    reference.setVarName("urlConsultQuotaInformationWsdl");
    reference.setType(STRING_CONSTANT);
    value.setValueTypeVarInfoDO(reference);
    input.setValue(value);
    inputs.getIbVariableDOs().add(input);
    Step step = new Step();
    com.sas.rtdm2id.model.id.decision.RuleSetStep ruleSet = new com.sas.rtdm2id.model.id.decision.RuleSetStep();
    ruleSet.setId("rule-set-id");
    ruleSet.setName(ruleSetName);
    step.setRuleSet(ruleSet);
    Decision decision = new Decision();

    converter.updateRuleSet(inputs, step, decision);

    assertTrue(step.getMappings().stream().anyMatch(mapping ->
        "GlobalurlConsultQuotaIn795250248".equals(mapping.getStepTermName())
            && global.getName().equals(mapping.getTargetDecisionTermName())));
    assertTrue(decision.getSignature().stream().anyMatch(term -> "global-id".equals(term.getGlobalVariableId())));
    verify(restTemplate, org.mockito.Mockito.atLeastOnce()).exchange(
        any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class));
    verify(restTemplate, org.mockito.Mockito.atLeastOnce()).exchange(
        any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(GlobalVariable.class));
    org.mockito.Mockito.verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void keepsReplySourceTypesIndependentOfIntegerOutputs() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        MapStorage storage = new MapStorage();
        storage.getCalculatedVariableDataTypeMap().put("calculatedDataItems.calcMiniDecReadyflg", "decimal");
        CommonProcessing processing = org.mockito.Mockito.spy(new CommonProcessing(restTemplate, storage, new TreeUtil()));
        org.mockito.Mockito.doReturn(false).when(processing).checkForGlobalVariable(any(VarRef.class), any(), any(), any(), eq(true));
        String xml = "<ReplyNodeDataDO><NodeId>CFDCKGCUZ3CRTMPC</NodeId><NodeName>Reply</NodeName>"
            + "<EventReply><ReplyVars>"
            + "<IBVariableDO><Name>miniDecReadyFlg</Name><TypeDescription>integer</TypeDescription><Value><ValueTypeVarInfoDO>"
            + "<VarInfoId>calculatedDataItems.calcMiniDecReadyflg</VarInfoId><VarInfoSource>CalculatedDataItem</VarInfoSource>"
            + "<VarInfoName>calcMiniDecReadyflg</VarInfoName><VarInfoType>Numeric</VarInfoType></ValueTypeVarInfoDO></Value></IBVariableDO>"
            + "<IBVariableDO><Name>statusCd</Name><TypeDescription>integer</TypeDescription><Value><ValueTypeVarInfoDO>"
            + "<VarInfoId>globals.GV_STATUS_CD_SUCCESS</VarInfoId><VarInfoSource>Global</VarInfoSource>"
            + "<VarInfoName>GV_STATUS_CD_SUCCESS</VarInfoName><VarInfoType>Numeric</VarInfoType></ValueTypeVarInfoDO></Value></IBVariableDO>"
            + "</ReplyVars></EventReply></ReplyNodeDataDO>";
        try {
            com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO reply = jakarta.xml.bind.JAXBContext.newInstance(
                com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.class).createUnmarshaller().unmarshal(
                new javax.xml.transform.stream.StreamSource(new java.io.StringReader(xml)),
                com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO.class).getValue();
            String extractPath = System.getProperty("replyExtract");
            if (extractPath != null) {
                com.sas.rtdm2id.model.rtdm.Batch batch = (com.sas.rtdm2id.model.rtdm.Batch) jakarta.xml.bind.JAXBContext.newInstance(
                    com.sas.rtdm2id.model.rtdm.Batch.class).createUnmarshaller().unmarshal(new java.io.File(extractPath));
                reply = batch.getLogicalUnit().getReplyNodeDataDOs().stream()
                    .filter(node -> "CFDCKGCUZ3CRTMPC".equals(node.getNodeId())).findFirst().orElseThrow();
                batch.getLogicalUnit().getFlowDO().getCalculatedDataItemList().getCalculatedItemDOs().forEach(item ->
                    storage.getCalculatedVariableDataTypeMap().put("calculatedDataItems." + item.getName(),
                        processing.getCalculatedItemDataType(item.getCalculatedItemType())));
            }
            Step step = new Step();
            com.sas.rtdm2id.model.id.rules.RuleSet ruleSet = new com.sas.rtdm2id.model.id.rules.RuleSet();
            com.sas.rtdm2id.model.id.rules.Rule rule = new com.sas.rtdm2id.model.id.rules.Rule();

            processing.processVariableToCreateRuleSet(step, new Decision(), ruleSet, rule, reply.getEventReply().getReplyVars().getIbVariableDOs());

            for (String source : java.util.List.of("CalculatedDataItem.calcMiniDecReadyflg", "Global.GV_STATUS_CD_SUCCESS")) {
                String alias = processing.sanitizeAndReduceVariableName(source);
                com.sas.rtdm2id.model.id.rules.Signature input = ruleSet.getSignature().stream().filter(term -> alias.equals(term.getName())).findFirst().orElseThrow();
                assertEquals("decimal", input.getDataType(), source);
                assertEquals("input", input.getDirection());
                assertTrue(step.getMappings().stream().anyMatch(mapping -> alias.equals(mapping.getStepTermName())
                    && processing.sanitizeVariableName(source).equals(mapping.getTargetDecisionTermName())
                    && com.sas.rtdm2id.model.id.decision.Mapping.DirectionEnum.INPUT.equals(mapping.getDirection())));
            }
            for (String output : java.util.List.of("miniDecReadyFlg", "statusCd")) {
                com.sas.rtdm2id.model.id.rules.Signature term = ruleSet.getSignature().stream().filter(signature -> output.equals(signature.getName())).findFirst().orElseThrow();
                assertEquals("integer", term.getDataType());
                assertEquals("output", term.getDirection());
            }
            org.mockito.Mockito.verifyNoInteractions(restTemplate);
        } catch (jakarta.xml.bind.JAXBException exception) {
            throw new AssertionError(exception);
        }
    }

    @Test
    void globalInputMappingsUseTheSameReducedNamesAsRuleSetSignatures() {
        CommonProcessing commonProcessing = new CommonProcessing(new RestTemplate(), new MapStorage(), new TreeUtil());
        for (String name : java.util.List.of("Global.urlConsultQuotaInformationWsdl",
                "Global.GlobalConsultaSaldoUserWsdlMw", "Global.GlobalConsultaSaldoPswWsdlMw")) {
            com.sas.rtdm2id.util.model.IBVariableDO variable = new com.sas.rtdm2id.util.model.IBVariableDO();
                com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value =
                    new com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value();
                com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO reference =
                    new com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO();
            reference.setVarInfoSource("Global");
            reference.setVarInfoId("globals." + name.substring(name.indexOf('.') + 1));
            reference.setVarName(name.substring(name.indexOf('.') + 1));
            value.setValueTypeVarInfoDO(reference);
            variable.setValue(value);
            com.sas.rtdm2id.model.id.decision.Mapping mapping = new com.sas.rtdm2id.model.id.decision.Mapping();

            commonProcessing.setMappingNameFields(mapping, variable);

                assertEquals(commonProcessing.sanitizeAndReduceVariableName(name),
                    mapping.getStepTermName());
                assertEquals(commonProcessing.sanitizeVariableName(name),
                    mapping.getTargetDecisionTermName());
        }
    }

    @Test
    void keepsConvertedNodeWhenConditionOutputIsAnotherCondition() {
        MapStorage mapStorage = new MapStorage();
        TreeUtil treeUtil = new TreeUtil();
        CommonProcessing commonProcessing = new CommonProcessing(new RestTemplate(), mapStorage, treeUtil);

        Step nestedCondition = new Step();
        nestedCondition.setType(Step.TypeEnum.CONDITION);
        mapStorage.getNodeIdStepMap().put("nested-condition", Collections.singletonList(nestedCondition));
        treeUtil.getNodeIdObjIdMap().put("nested-condition", (short) 1);
        treeUtil.getObjIdOutputNodesMap().put((short) 1, "downstream-cell");

        ConditionBranch branch = commonProcessing.createConditionBranch("nested-condition", true);

        assertSame(nestedCondition, branch.getSteps().get(0));
    }

    @Test
    void linksTerminalFanInNodeForSharedReply() {
        MapStorage mapStorage = new MapStorage();
        TreeUtil treeUtil = new TreeUtil();
        CommonProcessing commonProcessing = new CommonProcessing(new RestTemplate(), mapStorage, treeUtil);
        mapStorage.setUseCrossBranchLinks(true);
        treeUtil.getNodeIdInputNodesMap().put("reply", "first,second");
        treeUtil.getNodeIdObjIdMap().put("reply", (short) 2);
        treeUtil.getObjIdOutputNodesMap().put((short) 2, "");

        Step replyStep = new Step();
        ArrayList<Step> steps = new ArrayList<>();
        commonProcessing.addStep(steps, replyStep, "reply");

        assertSame(replyStep, steps.get(0));
        assertNotNull(replyStep.getLinkLabel());
        assertSame(replyStep.getLinkLabel(), mapStorage.getNodeIdLinkLabelMap().get("reply"));
        assertTrue(mapStorage.getCrossBranchLinkSteps().containsKey(replyStep.getLinkLabel()));
    }

    @Test
    void createsReferencedGlobalAndActivatesItsRevision() throws Exception {
        RestTemplate restTemplate = mock(RestTemplate.class);
        CodeFileCollection missingGlobal = new CodeFileCollection();
        missingGlobal.setCount(0L);
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
            .thenReturn(new ResponseEntity<>(missingGlobal, HttpStatus.OK));

        CodeFile createdGlobal = new CodeFile();
        createdGlobal.setId("global-id");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setETag("\"etag\"");
        when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(CodeFile.class)))
            .thenReturn(new ResponseEntity<>(createdGlobal, responseHeaders, HttpStatus.CREATED));
        when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(GlobalVariable.class)))
            .thenReturn(new ResponseEntity<>(new GlobalVariable(), HttpStatus.CREATED));

        Item revision = new Item();
        revision.setId("revision-id");
        GlobalVariableCollection revisions = new GlobalVariableCollection();
        revisions.setItems(Collections.singletonList(revision));
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(GlobalVariableCollection.class)))
            .thenReturn(new ResponseEntity<>(revisions, HttpStatus.OK));
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(String.class)))
            .thenReturn(new ResponseEntity<>("revision-id", HttpStatus.OK));

        MapStorage mapStorage = new MapStorage();
        mapStorage.setBaseIp("localhost:8080");
        mapStorage.setProtocol("http");
        mapStorage.setAccessToken("access-token");
        CommonProcessing commonProcessing = new CommonProcessing(restTemplate, mapStorage, new TreeUtil());
        Decision decision = new Decision();

        VarRef globalVariableRef = new VarRef();
        globalVariableRef.setVarInfoId("globals.ApplicationScore");
        globalVariableRef.setType(STRING_CONSTANT);
        assertFalse(commonProcessing.checkForGlobalVariable(
            globalVariableRef, null, "ApplicationScore", decision, false));

        assertEquals(1, decision.getSignature().size());
        SignatureTerm signatureTerm = decision.getSignature().get(0);
        assertEquals("global-id", signatureTerm.getGlobalVariableId());
        ArgumentCaptor<HttpEntity> requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).postForEntity(any(URI.class), requestCaptor.capture(), eq(CodeFile.class));
        java.util.Map<?, ?> requestBody = (java.util.Map<?, ?>) requestCaptor.getValue().getBody();
        assertEquals(3, requestBody.size());
        assertEquals("GlobalApplicationScore", requestBody.get("name"));
        assertEquals("string", requestBody.get("dataType"));
        assertEquals("", requestBody.get("defaultValue"));
        ArgumentCaptor<URI> activationUriCaptor = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<HttpEntity> activationRequestCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).exchange(activationUriCaptor.capture(), eq(HttpMethod.PUT), activationRequestCaptor.capture(), eq(String.class));
        assertEquals("http://localhost:8080/referenceData/globalVariables/global-id/active", activationUriCaptor.getValue().toString());
        assertEquals("revision-id", activationRequestCaptor.getValue().getBody());
        assertEquals("\"etag\"", activationRequestCaptor.getValue().getHeaders().getFirst(HttpHeaders.IF_MATCH));
        ArgumentCaptor<URI> draftUriCaptor = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<HttpEntity> draftRequestCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).postForEntity(draftUriCaptor.capture(), draftRequestCaptor.capture(), eq(GlobalVariable.class));
        assertEquals("http://localhost:8080/referenceData/globalVariables/global-id/revisions", draftUriCaptor.getValue().toString());
        assertEquals(3, ((java.util.Map<?, ?>) draftRequestCaptor.getValue().getBody()).size());
        GlobalVariable migratedGlobal = mapStorage.getGlobalVariableMap().values().iterator().next();
        assertEquals("revision-id", migratedGlobal.getActiveVersionId());
        assertEquals("developing", migratedGlobal.getStatus());
        assertFalse(migratedGlobal.isLocked());
    }

    @Test
    void activatesExistingGlobalWhenItHasNoActiveVersion() throws Exception {
        RestTemplate restTemplate = mock(RestTemplate.class);
        CodeFile existingGlobal = new CodeFile();
        existingGlobal.setId("global-id");
        CodeFileCollection existingGlobals = new CodeFileCollection();
        existingGlobals.setCount(1L);
        existingGlobals.setItems(Collections.singletonList(existingGlobal));
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
            .thenReturn(new ResponseEntity<>(existingGlobals, HttpStatus.OK));

        GlobalVariable globalDetails = new GlobalVariable();
        globalDetails.setId("global-id");
        globalDetails.setName("GlobalApplicationScore");
        globalDetails.setDataType("string");
        globalDetails.setDefaultValue("");
        HttpHeaders globalHeaders = new HttpHeaders();
        globalHeaders.setETag("\"global-etag\"");
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(GlobalVariable.class)))
            .thenReturn(new ResponseEntity<>(globalDetails, globalHeaders, HttpStatus.OK));

        Item revision = new Item();
        revision.setId("revision-id");
        GlobalVariableCollection revisions = new GlobalVariableCollection();
        revisions.setItems(Collections.singletonList(revision));
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(GlobalVariableCollection.class)))
            .thenReturn(new ResponseEntity<>(revisions, HttpStatus.OK));
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(String.class)))
            .thenReturn(new ResponseEntity<>("revision-id", HttpStatus.OK));
        when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(GlobalVariable.class)))
            .thenReturn(new ResponseEntity<>(new GlobalVariable(), HttpStatus.CREATED));

        MapStorage mapStorage = new MapStorage();
        mapStorage.setBaseIp("localhost:8080");
        mapStorage.setProtocol("http");
        mapStorage.setAccessToken("access-token");
        CommonProcessing commonProcessing = new CommonProcessing(restTemplate, mapStorage, new TreeUtil());
        Decision decision = new Decision();
        VarRef globalVariableRef = new VarRef();
        globalVariableRef.setVarInfoId("globals.ApplicationScore");
        globalVariableRef.setType(STRING_CONSTANT);

        assertFalse(commonProcessing.checkForGlobalVariable(
            globalVariableRef, null, "ApplicationScore", decision, false));

        verify(restTemplate).exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(String.class));
        GlobalVariable migratedGlobal = mapStorage.getGlobalVariableMap().values().iterator().next();
        assertEquals("revision-id", migratedGlobal.getActiveVersionId());
        assertEquals("developing", migratedGlobal.getStatus());
        assertFalse(migratedGlobal.isLocked());
    }

    @Test
    void createsEditableRevisionForExistingProductionGlobal() throws Exception {
        RestTemplate restTemplate = mock(RestTemplate.class);
        CodeFile existingGlobal = new CodeFile();
        existingGlobal.setId("global-id");
        CodeFileCollection existingGlobals = new CodeFileCollection();
        existingGlobals.setCount(1L);
        existingGlobals.setItems(Collections.singletonList(existingGlobal));
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(CodeFileCollection.class)))
            .thenReturn(new ResponseEntity<>(existingGlobals, HttpStatus.OK));

        GlobalVariable globalDetails = new GlobalVariable();
        globalDetails.setId("global-id");
        globalDetails.setName("GlobalApplicationScore");
        globalDetails.setDataType("string");
        globalDetails.setDefaultValue("");
        globalDetails.setActiveVersionId("active-revision-id");
        globalDetails.setStatus("production");
        globalDetails.setLocked(true);
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(GlobalVariable.class)))
            .thenReturn(new ResponseEntity<>(globalDetails, HttpStatus.OK));
        when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(GlobalVariable.class)))
            .thenReturn(new ResponseEntity<>(new GlobalVariable(), HttpStatus.CREATED));

        MapStorage mapStorage = new MapStorage();
        mapStorage.setBaseIp("localhost:8080");
        mapStorage.setProtocol("http");
        mapStorage.setAccessToken("access-token");
        CommonProcessing commonProcessing = new CommonProcessing(restTemplate, mapStorage, new TreeUtil());
        Decision decision = new Decision();
        VarRef globalVariableRef = new VarRef();
        globalVariableRef.setVarInfoId("globals.ApplicationScore");
        globalVariableRef.setType(STRING_CONSTANT);

        assertFalse(commonProcessing.checkForGlobalVariable(
            globalVariableRef, null, "ApplicationScore", decision, false));

        verify(restTemplate).postForEntity(any(URI.class), any(HttpEntity.class), eq(GlobalVariable.class));
        verify(restTemplate, never()).exchange(any(URI.class), eq(HttpMethod.PUT), any(HttpEntity.class), eq(String.class));
        GlobalVariable migratedGlobal = mapStorage.getGlobalVariableMap().values().iterator().next();
        assertEquals("active-revision-id", migratedGlobal.getActiveVersionId());
        assertEquals("developing", migratedGlobal.getStatus());
        assertFalse(migratedGlobal.isLocked());
    }
}