package com.sas.rtdm2id.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.BranchCase;
import com.sas.rtdm2id.model.id.decision.ConditionBranch;
import com.sas.rtdm2id.model.id.decision.ConditionDecisionStep;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.Mapping;
import com.sas.rtdm2id.model.id.decision.SignatureTerm;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.model.rtdm.CHandRHNodeDataDO;
import com.sas.rtdm2id.model.rtdm.MultiSelectNodeDataDO;
import com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO;
import com.sas.rtdm2id.model.rtdm.RemoveStagedTreatmentsNodeDataDO;
import com.sas.rtdm2id.model.rtdm.SplitNodeDataDO;
import com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO;
import com.sas.rtdm2id.util.model.RTDMObject;
import com.sas.rtdm2id.util.object.processing.AssignmentNodeConverter;
import com.sas.rtdm2id.util.object.processing.CommonProcessing;
import com.sas.rtdm2id.util.object.processing.ConfirmContactNodeConverter;
import com.sas.rtdm2id.util.object.processing.CrossTableNodeConverter;
import com.sas.rtdm2id.util.object.processing.MultiSelectNodeConverter;
import com.sas.rtdm2id.util.object.processing.ProcessNodeConverter;
import com.sas.rtdm2id.util.object.processing.ReplyNodeConverter;
import com.sas.rtdm2id.util.object.processing.SplitNodeConverter;
import com.sas.rtdm2id.util.object.processing.StagedTreatmentConverter;
import com.sas.rtdm2id.util.object.processing.SubDiagramNodeConverter;
import com.sas.rtdm2id.util.tree.impl.GenericTreeNode;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import jakarta.xml.bind.JAXBContext;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class ConverterTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void insertsTheNodesCalculatedStepOnceBeforeProcessInputs(boolean links) throws Exception {
        MapStorage storage = new MapStorage();
        storage.setUseCrossBranchLinks(links);
        RestTemplate template = mock(RestTemplate.class);
        TreeUtil tree = new TreeUtil();
        tree.getNodeIdInputNodesMap().put("consumer", "left,right");
        CommonProcessing processing = new CommonProcessing(template, storage, tree);
        ProcessNodeDataDO node = new ProcessNodeDataDO();
        node.setNodeId("consumer");
        node.setProcess(new ProcessNodeDataDO.Process());
        node.getProcess().setInputVariableList(new ProcessNodeDataDO.Process.InputVariableList());
        @SuppressWarnings("unchecked")
        Map<String, CalculatedItemDO> calculatedItems =
            (Map<String, CalculatedItemDO>) ReflectionTestUtils.getField(processing, "calculatedItemsByName");
        Decision decision = new Decision();
        for (String name : List.of("first", "second")) {
            CalculatedItemDO item = new CalculatedItemDO();
            item.setName(name);
            item.setCalculatedItemType("4");
            item.setExpression("1");
            calculatedItems.put(name, item);
            storage.getCalculatedVariableMap().put(name, name + "-node");
            storage.getCalculatedVariableDataTypeMap().put("calculatedDataItems." + name, "decimal");
            String resultName = processing.sanitizeVariableName("CalculatedDataItem." + name);
            SignatureTerm result = new SignatureTerm();
            result.setName(resultName);
            result.setDataType(SignatureTerm.DataTypeEnum.DECIMAL);
            result.setDirection(SignatureTerm.DirectionEnum.NONE);
            decision.addSignatureItem(result);
            ProcessNodeDataDO.Process.InputVariableList.IBVariableDO input =
                new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO();
            input.setName(name);
            input.setTypeDescription("double");
            input.setValue(new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value());
            com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO reference = new com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO();
            reference.setVarInfoId("calculatedDataItems." + name);
            reference.setVarName(name);
            reference.setVarInfoSource("CalculatedDataItem");
            input.getValue().setValueTypeVarInfoDO(reference);
            node.getProcess().getInputVariableList().getIbVariableDOs().add(input);
        }
        Step assignments = new Step();
        assignments.setRuleSet(new com.sas.rtdm2id.model.id.decision.RuleSetStep());
        assignments.getRuleSet().setName("node_input_values");
        storage.getRuleSetStepMap().put("node_input_values", assignments);
        storage.getRuleSetIsUpdated().put("node_input_values", true);
        storage.getRuleSetIsActionUpdated().put("node_input_values", true);

        List<Step> steps = new ProcessNodeConverter(template, processing, storage)
            .addCustomObjectStep(node, decision, (short) 1, "node");
        assertEquals(3, steps.size());
        MapStorage.CalculatedNodeGroup calculated = storage.getCalculatedNodeGroups().get("consumer");
        assertSame(calculated.step(), steps.get(0));
        assertEquals(List.of("first", "second"), List.copyOf(calculated.items()));
        assertSame(assignments, steps.get(1));
        assertEquals(links, calculated.step().getLinkLabel() != null);
        assertEquals(2, steps.get(2).getMappings().size());
        ConditionDecisionStep flow = new ConditionDecisionStep();
        flow.setFlow(steps);
        decision.setFlow(flow);
        invokeAddMissingVars(createConverter(tree, storage, processing), decision);
        assertTrue(decision.getSignature().stream().allMatch(term -> SignatureTerm.DataTypeEnum.DECIMAL.equals(term.getDataType())));
        verifyNoInteractions(template);
    }

    @Test
    void declaresCalculatedResultTypesBeforeFinalSignatureAssembly() throws Exception {
        MapStorage storage = new MapStorage();
        TreeUtil tree = new TreeUtil();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, tree);
        Converter converter = createConverter(tree, storage, processing);
        Batch batch = new Batch();
        batch.setLogicalUnit(new Batch.LogicalUnit());
        com.sas.rtdm2id.model.rtdm.FlowDO sourceFlow = mock(
            com.sas.rtdm2id.model.rtdm.FlowDO.class, org.mockito.Mockito.RETURNS_DEEP_STUBS);
        batch.getLogicalUnit().setFlowDO(sourceFlow);
        List<com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO> items = new ArrayList<>();
        String[][] cases = {
            {"isCodebtorInvolved", "5", "boolean"},
            {"CV_incomeCheckDebtorFlg", "3", "decimal"},
            {"CV_manualIncomeChkResultCd_Debtor", "0", "string"},
            {"CV_automaticIncomeChkResult_Debtor", "0", "string"},
            {"empCallCodebtorFlg", "5", "boolean"},
            {"012_DocCheckFlgLvCodebtor", "5", "boolean"},
            {"022_EmpRoutYLFlgLvCodebtor", "3", "decimal"},
            {"ResultDate", "1", "date"},
            {"ResultGrid", "7", "dataGrid"}
        };
        Decision decision = new Decision();
        SignatureTerm existing = new SignatureTerm();
        existing.setName("CalculatedDataItemisCodebtorInvolved");
        existing.setDataType(SignatureTerm.DataTypeEnum.ANY);
        existing.setDirection(SignatureTerm.DirectionEnum.INPUT);
        decision.addSignatureItem(existing);
        ConditionDecisionStep flow = new ConditionDecisionStep();
        flow.setFlow(new ArrayList<>());
        decision.setFlow(flow);
        for (String[] itemCase : cases) {
            com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO item =
                new com.sas.rtdm2id.model.rtdm.extension.CalculatedItemDO();
            item.setName(itemCase[0]);
            item.setCalculatedItemType(itemCase[1]);
            items.add(item);
            flow.addFlowItem(mappedStep(processing.sanitizeVariableName("CalculatedDataItem." + itemCase[0]),
                Mapping.DirectionEnum.OUTPUT));
        }
        when(sourceFlow.getCalculatedDataItemList().getCalculatedItemDOs()).thenReturn(items);
        Method declareTypes = Converter.class.getDeclaredMethod("addCalculatedItemSignatureTerms", Batch.class, Decision.class);
        declareTypes.setAccessible(true);

        declareTypes.invoke(converter, batch, decision);
        invokeAddMissingVars(converter, decision);

        assertEquals(cases.length, decision.getSignature().size());
        assertSame(existing, decision.getSignature().get(0));
        assertEquals(SignatureTerm.DirectionEnum.INOUT, existing.getDirection());
        for (String[] itemCase : cases) {
            String target = processing.sanitizeVariableName("CalculatedDataItem." + itemCase[0]);
            SignatureTerm term = decision.getSignature().stream()
                .filter(item -> target.equals(item.getName())).findFirst().orElseThrow();
            assertEquals(SignatureTerm.DataTypeEnum.fromValue(itemCase[2]), term.getDataType(), target);
        }
    }

    @Test
    void declaresReportedCalculatedTypesFromExtract() throws Exception {
        String extractPath = System.getProperty("calculatedExtract");
        assumeTrue(extractPath != null, "Set -DcalculatedExtract to DC_getDataForHabof_v7.xml");
        Batch batch = (Batch) JAXBContext.newInstance(Batch.class).createUnmarshaller().unmarshal(new File(extractPath));
        MapStorage storage = new MapStorage();
        TreeUtil tree = new TreeUtil();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, tree);
        Converter converter = createConverter(tree, storage, processing);
        Decision decision = new Decision();
        Map<SignatureTerm.DataTypeEnum, List<String>> reported = Map.of(
            SignatureTerm.DataTypeEnum.BOOLEAN, List.of("isCodebtorInvolved", "053_FraudManFinFlgLvApp",
                "012_DocCheckFlgLvCodebtor", "042_IncAutYLFlgLvCodebtor", "003_KihivFlgLvCodebtor",
                "062_IncManFinFlgLvCodebtor", "empCallCodebtorFlg"),
            SignatureTerm.DataTypeEnum.DECIMAL, List.of("022_EmpRoutYLFlgLvCodebtor", "032_EmpChkFinFlgLvCodebtor",
                "CV_incomeCheckDebtorFlg"),
            SignatureTerm.DataTypeEnum.STRING, List.of("CV_manualIncomeChkResultCd_Debtor",
                "CV_automaticIncomeChkResult_Debtor", "CV_munkKihManDecResultDebtor", "CV_munkKihManDecResultCodebtor"));
        ConditionDecisionStep flow = new ConditionDecisionStep();
        flow.setFlow(new ArrayList<>());
        decision.setFlow(flow);
        for (List<String> names : reported.values()) {
            for (String name : names) {
                String target = processing.sanitizeVariableName("CalculatedDataItem." + name);
                Step producer = mappedStep(target, Mapping.DirectionEnum.OUTPUT);
                producer.getMappings().get(0).setStepTermName(processing.sanitizeAndReduceVariableName(target));
                flow.addFlowItem(producer);
                flow.addFlowItem(mappedStep(target, Mapping.DirectionEnum.INPUT));
            }
        }
        Method declareTypes = Converter.class.getDeclaredMethod("addCalculatedItemSignatureTerms", Batch.class, Decision.class);
        declareTypes.setAccessible(true);

        declareTypes.invoke(converter, batch, decision);
        invokeAddMissingVars(converter, decision);

        reported.forEach((type, names) -> names.forEach(name -> {
            String target = processing.sanitizeVariableName("CalculatedDataItem." + name);
            SignatureTerm term = decision.getSignature().stream()
                .filter(item -> target.equals(item.getName())).findFirst().orElseThrow();
            assertEquals(type, term.getDataType(), target);
            assertEquals(SignatureTerm.DirectionEnum.INOUT, term.getDirection(), target);
        }));
    }

    @ParameterizedTest
    @ValueSource(strings = {"where expression", "reqSubprodCd", "uwBranchFlg", "afterBudgetFlg", "otpCustFlg", "cardproductIdIn", "offerTypCd", "runID"})
    void mapsProcessInputsThroughAssignedValues(String inputName) throws Exception {
        MapStorage storage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing processing = new CommonProcessing(restTemplate, storage, new TreeUtil());
        // One RuleSet per calculated item, as with rtdm2id.consolidate-calculated-rulesets=false
        ReflectionTestUtils.setField(processing, "consolidateCalculatedRuleSets", false);
        ProcessNodeConverter converter = new ProcessNodeConverter(restTemplate, processing, storage);
        ProcessNodeDataDO node = new ProcessNodeDataDO();
        node.setNodeId("process-node");
        ProcessNodeDataDO.Process process = new ProcessNodeDataDO.Process();
        node.setProcess(process);
        ProcessNodeDataDO.Process.InputVariableList inputs = new ProcessNodeDataDO.Process.InputVariableList();
        process.setInputVariableList(inputs);
        ProcessNodeDataDO.Process.InputVariableList.IBVariableDO input =
            new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO();
        input.setName(inputName);
        input.setTypeDescription("string");
        ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value =
            new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value();
        com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO reference =
            new com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO();
        reference.setVarInfoId("calculatedDataItems.sourceValue");
        reference.setVarName("sourceValue");
        reference.setVarInfoSource("CalculatedDataItem");
        boolean literalAssignment = "offerTypCd".equals(inputName);
        boolean unassignedInput = "runID".equals(inputName);
        if (literalAssignment) {
            value.setStringValue("ELFOGADOTT");
        } else {
            value.setValueTypeVarInfoDO(reference);
        }
        input.setValue(unassignedInput ? null : value);
        String extractPath = System.getProperty("unsetInputExtract");
        if (unassignedInput && extractPath != null) {
            Batch batch = (Batch) jakarta.xml.bind.JAXBContext.newInstance(Batch.class).createUnmarshaller()
                .unmarshal(new java.io.File(extractPath));
            input = batch.getLogicalUnit().getProcessNodeDataDOs().stream()
                .filter(source -> "DGHGYRH4Y3E3QS04".equals(source.getNodeId())).findFirst().orElseThrow()
                .getProcess().getInputVariableList().getIbVariableDOs().stream()
                .filter(variable -> "runID".equals(variable.getName())).findFirst().orElseThrow();
            assertTrue(input.getValue() == null, "The extract must preserve the unset runID assignment");
        }
        inputs.getIbVariableDOs().add(input);
        Step calculatedStep = new Step();
        com.sas.rtdm2id.model.id.decision.RuleSetStep calculatedRuleSet =
            new com.sas.rtdm2id.model.id.decision.RuleSetStep();
        calculatedRuleSet.setName("calculated");
        calculatedStep.setRuleSet(calculatedRuleSet);
        storage.getCalculatedVariableMap().put("sourceValue", "calculated-node");
        storage.getRuleSetStepMap().put(processing.makeCalculatedDataItemRuleSetName("sourceValue", "calculated-node"), calculatedStep);
        storage.getRuleSetIsUpdated().put("calculated", true);
        Step assignedStep = new Step();
        com.sas.rtdm2id.model.id.decision.RuleSetStep assignedRuleSet =
            new com.sas.rtdm2id.model.id.decision.RuleSetStep();
        assignedRuleSet.setName("node_input_values");
        assignedStep.setRuleSet(assignedRuleSet);
        storage.getRuleSetStepMap().put("node_input_values", assignedStep);
        storage.getRuleSetIsUpdated().put("node_input_values", true);
        storage.getRuleSetIsActionUpdated().put("node_input_values", true);
        Decision decision = new Decision();

        List<Step> steps = converter.addCustomObjectStep(node, decision, (short) 1, "node");
        ConditionDecisionStep flow = new ConditionDecisionStep();
        flow.setFlow(steps);
        decision.setFlow(flow);
        invokeAddMissingVars(createConverter(new TreeUtil(), storage, processing), decision);

        assertEquals(literalAssignment || unassignedInput ? 2 : 3, steps.size());
        if (!literalAssignment && !unassignedInput) {
            assertSame(calculatedStep, steps.get(0));
        }
        assertSame(assignedStep, steps.get(steps.size() - 2));
        String assignedName = processing.sanitizeVariableName(inputName);
        Step processStep = steps.get(steps.size() - 1);
        assertEquals(1, processStep.getMappings().size());
        Mapping mapping = processStep.getMappings().get(0);
        assertEquals(assignedName, mapping.getStepTermName());
        assertEquals(assignedName, mapping.getTargetDecisionTermName());
        assertEquals(Mapping.DirectionEnum.INPUT, mapping.getDirection());
        assertTrue(assignedStep.getMappings().stream().anyMatch(assignedMapping ->
            assignedName.equals(assignedMapping.getStepTermName())
                && mapping.getTargetDecisionTermName().equals(assignedMapping.getTargetDecisionTermName())
                && Mapping.DirectionEnum.INOUT.equals(assignedMapping.getDirection())));
        assertTrue(decision.getSignature().stream().anyMatch(term ->
            assignedName.equals(term.getName()) && SignatureTerm.DataTypeEnum.STRING.equals(term.getDataType())
                && SignatureTerm.DirectionEnum.INOUT.equals(term.getDirection())));
        verifyNoInteractions(restTemplate);
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void omitsInputRuleSetForProcessWithoutInputs(boolean emptyInputList) {
        MapStorage mapStorage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing commonProcessing = new CommonProcessing(restTemplate, mapStorage, new TreeUtil());
        ProcessNodeConverter processNodeConverter = new ProcessNodeConverter(restTemplate, commonProcessing, mapStorage);
        ProcessNodeDataDO.Process process = new ProcessNodeDataDO.Process();
        process.setProcessTypeDescription("Java code");
        if (emptyInputList) {
            process.setInputVariableList(new ProcessNodeDataDO.Process.InputVariableList());
        }
        ProcessNodeDataDO.Process.OutputVariableList outputs = new ProcessNodeDataDO.Process.OutputVariableList();
        for (String name : List.of("runtime", "generatedTime")) {
            ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO output =
                    new ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO();
            output.setName(name);
            output.setTypeDescription("string");
            outputs.getIbVariableDOs().add(output);
        }
        process.setOutputVariableList(outputs);
        ProcessNodeDataDO processNode = new ProcessNodeDataDO();
        processNode.setNodeId("FEDF1VQNB3HKRAYZ");
        processNode.setProcess(process);
        mapStorage.getRuleSetStepMap().put("GenerateStartTime_input_values", new Step());
        Decision decision = new Decision();

        List<Step> steps = processNodeConverter.addCustomObjectStep(
                processNode, decision, (short) 1, "GenerateStartTime");

        assertEquals(1, steps.size());
        assertEquals(Step.TypeEnum.CUSTOM_OBJECT, steps.get(0).getType());
        for (String name : List.of("runtime", "generatedTime")) {
            assertTrue(steps.get(0).getMappings().stream().anyMatch(mapping ->
                    name.equals(mapping.getTargetDecisionTermName())
                            && Mapping.DirectionEnum.OUTPUT.equals(mapping.getDirection())));
            assertTrue(decision.getSignature().stream().anyMatch(term -> name.equals(term.getName())));
        }
        verifyNoInteractions(restTemplate);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ReadDCL_Reserved_Amount_FCCDBVXCHBGVWYDB", "Real_LineType_HHBFBN5ZBJEWTMJU"})
    void mapsCharacterTransactionAssignmentWithoutUsingNumericDecisionTerm(String nodeName) {
        MapStorage storage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing processing = new CommonProcessing(restTemplate, storage, new TreeUtil());
        ProcessNodeConverter converter = new ProcessNodeConverter(restTemplate, processing, storage);
        ProcessNodeDataDO node = new ProcessNodeDataDO();
        node.setNodeId("transaction-process");
        ProcessNodeDataDO.Process process = new ProcessNodeDataDO.Process();
        node.setProcess(process);
        ProcessNodeDataDO.Process.InputVariableList inputs = new ProcessNodeDataDO.Process.InputVariableList();
        process.setInputVariableList(inputs);
        ProcessNodeDataDO.Process.InputVariableList.IBVariableDO input =
            new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO();
        input.setName("TRANSACTION_ID");
        input.setTypeDescription("string");
        ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value value =
            new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value();
        com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO reference =
            new com.sas.rtdm2id.model.rtdm.ValueTypeVarInfoDO();
        reference.setVarInfoId("events.Transaction ID");
        reference.setVarName("Transaction ID");
        reference.setVarInfoPhysicalName("Transaction_ID");
        reference.setVarInfoSource("Request");
        reference.setType("Char");
        value.setValueTypeVarInfoDO(reference);
        input.setValue(value);
        inputs.getIbVariableDOs().add(input);
        Step assignedStep = new Step();
        com.sas.rtdm2id.model.id.decision.RuleSetStep ruleSet =
            new com.sas.rtdm2id.model.id.decision.RuleSetStep();
        ruleSet.setName(nodeName + "_input_values");
        assignedStep.setRuleSet(ruleSet);
        storage.getRuleSetStepMap().put(ruleSet.getName(), assignedStep);
        storage.getRuleSetIsUpdated().put(ruleSet.getName(), true);
        storage.getRuleSetIsActionUpdated().put(ruleSet.getName(), true);
        Decision decision = new Decision();
        SignatureTerm numericTransaction = new SignatureTerm();
        numericTransaction.setName("TRANSACTION_ID");
        numericTransaction.setDataType(SignatureTerm.DataTypeEnum.DECIMAL);
        numericTransaction.setDirection(SignatureTerm.DirectionEnum.NONE);
        decision.getSignature().add(numericTransaction);

        List<Step> steps = converter.addCustomObjectStep(node, decision, (short) 1, nodeName);

        String assignedName = processing.sanitizeVariableName(ruleSet.getName() + "_TRANSACTION_ID");
        assertTrue(assignedStep.getMappings().stream().anyMatch(mapping ->
            "TRANSACTION_ID".equals(mapping.getStepTermName())
                && assignedName.equals(mapping.getTargetDecisionTermName())
                && Mapping.DirectionEnum.INOUT.equals(mapping.getDirection())));
        assertTrue(decision.getSignature().stream().anyMatch(term ->
            assignedName.equals(term.getName()) && SignatureTerm.DataTypeEnum.STRING.equals(term.getDataType())));
        Mapping sourceMapping = assignedStep.getMappings().stream()
            .filter(mapping -> "RequestTransactionID".equals(mapping.getStepTermName())).findFirst().orElseThrow();
        assertEquals("RequestTransactionID", sourceMapping.getTargetDecisionTermName());
        assertEquals(Mapping.DirectionEnum.INPUT, sourceMapping.getDirection());
        assertEquals("RequestTransactionID", steps.get(1).getMappings().get(0).getTargetDecisionTermName());
        assertTrue(decision.getSignature().stream().anyMatch(term ->
            "RequestTransactionID".equals(term.getName()) && SignatureTerm.DataTypeEnum.STRING.equals(term.getDataType())));
        assertEquals(SignatureTerm.DataTypeEnum.DECIMAL, numericTransaction.getDataType());
        verifyNoInteractions(restTemplate);
    }

    @Test
    void alignsExistingSignatureDirectionsWithStepMappings() throws Exception {
        Converter converter = createConverter(new TreeUtil(), new MapStorage());
        Decision decision = new Decision();

        SignatureTerm inputTerm = signatureTerm("BooleanInput");
        SignatureTerm outputTerm = signatureTerm("OutputBoolean");
        SignatureTerm inOutTerm = signatureTerm("SharedValue");
        decision.getSignature().add(inputTerm);
        decision.getSignature().add(outputTerm);
        decision.getSignature().add(inOutTerm);

        ConditionDecisionStep flow = new ConditionDecisionStep();
        flow.setFlow(new ArrayList<>());
        flow.addFlowItem(mappedStep("BooleanInput", Mapping.DirectionEnum.INPUT));
        flow.addFlowItem(mappedStep("OutputBoolean", Mapping.DirectionEnum.OUTPUT));
        flow.addFlowItem(mappedStep("SharedValue", Mapping.DirectionEnum.INPUT));
        flow.addFlowItem(mappedStep("SharedValue", Mapping.DirectionEnum.OUTPUT));
        decision.setFlow(flow);

        invokeAddMissingVars(converter, decision);

        assertEquals(SignatureTerm.DirectionEnum.INPUT, inputTerm.getDirection());
        assertEquals(SignatureTerm.DirectionEnum.OUTPUT, outputTerm.getDirection());
        assertEquals(SignatureTerm.DirectionEnum.INOUT, inOutTerm.getDirection());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void isolatesDataGridExtractionFieldsWithConflictingTypes(boolean numericFirst) throws Exception {
        MapStorage storage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing processing = new CommonProcessing(restTemplate, storage, new TreeUtil());
        ProcessNodeConverter converter = new ProcessNodeConverter(restTemplate, processing, storage);
        Decision decision = new Decision();
        Method processMapping = ProcessNodeConverter.class.getDeclaredMethod("processDataProcessMapping",
            String.class, ProcessNodeDataDO.Process.class, Step.class, Decision.class);
        processMapping.setAccessible(true);
        Map<SignatureTerm.DataTypeEnum, String> targets = new HashMap<>();
        for (boolean numeric : new boolean[] {numericFirst, !numericFirst}) {
            String processName = numeric ? "DP_get_S_OCA_PRELAK_LOAD_v2" : "DP_get_S_OCA_PRELAK_LOAD_SUBSIDY_v2";
            ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO output =
                new ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO();
            output.setName("SMS_SZOLGALTATO");
            output.setTypeDescription(numeric ? "double list" : "string list");
            ProcessNodeDataDO.Process process = new ProcessNodeDataDO.Process();
            process.setName(processName);
            process.setOutputVariableList(new ProcessNodeDataDO.Process.OutputVariableList());
            process.getOutputVariableList().getIbVariableDOs().add(output);
            Step processStep = new Step();
            SignatureTerm grid = (SignatureTerm) processMapping.invoke(converter, "Load", process, processStep, decision);
            Step extractStep = new Step();
            com.sas.rtdm2id.model.id.decision.RuleSetStep ruleSet =
                new com.sas.rtdm2id.model.id.decision.RuleSetStep();
            ruleSet.setName(processName + "_extract_values");
            extractStep.setRuleSet(ruleSet);
            storage.getRuleSetIsUpdated().put(ruleSet.getName(), true);
            storage.getRuleSetIsActionUpdated().put(ruleSet.getName(), true);

            converter.updateRuleSet(grid, extractStep, decision, "Load", processName);

            Mapping mapping = extractStep.getMappings().stream()
                .filter(item -> "SMS_SZOLGALTATO".equals(item.getStepTermName())).findFirst().orElseThrow();
            SignatureTerm.DataTypeEnum expectedType = numeric ? SignatureTerm.DataTypeEnum.DECIMAL : SignatureTerm.DataTypeEnum.STRING;
            SignatureTerm target = decision.getSignature().stream()
                .filter(term -> mapping.getTargetDecisionTermName().equals(term.getName())).findFirst().orElseThrow();
            assertEquals(expectedType, target.getDataType());
            assertEquals(Mapping.DirectionEnum.OUTPUT, mapping.getDirection());
            assertEquals("SMS_SZOLGALTATO", grid.getDataGridExtension().get(0).getName());
            assertEquals(expectedType.getValue(), grid.getDataGridExtension().get(0).getDataType().getValue());
            targets.put(expectedType, target.getName());
            // Each node's column is written under RTDM's output id <NodeName>.<ProcessName><column>, so same-named
            // columns of different processes (and types) never share a term
            assertEquals("Load" + processName + "SMS_SZOLGALTATO", target.getName());
        }
        org.junit.jupiter.api.Assertions.assertNotEquals(targets.get(SignatureTerm.DataTypeEnum.DECIMAL),
            targets.get(SignatureTerm.DataTypeEnum.STRING));
        for (Map.Entry<SignatureTerm.DataTypeEnum, String> target : targets.entrySet()) {
            assertEquals(target.getKey(), decision.getSignature().stream()
                .filter(term -> target.getValue().equals(term.getName())).findFirst().orElseThrow().getDataType());
        }
        verifyNoInteractions(restTemplate);
    }

    @Test
    void keepsGlobalVariableSignatureDirectionsAllowedByViya() throws Exception {
        Converter converter = createConverter(new TreeUtil(), new MapStorage());
        Decision decision = new Decision();

        SignatureTerm inputGlobal = signatureTerm("GlobalInput");
        inputGlobal.setGlobalVariableId("input-global-id");
        inputGlobal.setDirection(SignatureTerm.DirectionEnum.INPUT);
        SignatureTerm outputGlobal = signatureTerm("GlobalOutput");
        outputGlobal.setGlobalVariableId("output-global-id");
        outputGlobal.setDirection(SignatureTerm.DirectionEnum.INPUT);
        decision.getSignature().add(inputGlobal);
        decision.getSignature().add(outputGlobal);

        ConditionDecisionStep flow = new ConditionDecisionStep();
        flow.setFlow(new ArrayList<>());
        flow.addFlowItem(mappedStep("GlobalInput", Mapping.DirectionEnum.INPUT));
        flow.addFlowItem(mappedStep("GlobalOutput", Mapping.DirectionEnum.OUTPUT));
        flow.addFlowItem(mappedStep("GlobalOutput", Mapping.DirectionEnum.INPUT));
        decision.setFlow(flow);

        invokeAddMissingVars(converter, decision);

        assertEquals(SignatureTerm.DirectionEnum.NONE, inputGlobal.getDirection());
        assertEquals(SignatureTerm.DirectionEnum.OUTPUT, outputGlobal.getDirection());
    }

    @ParameterizedTest
    @ValueSource(strings = {"G_ORA_CustomerDetails", "RETENTION_OPT_KPI"})
    void mapsStandardSqlOutputsForNonDatagridDataProcesses(String processName) throws Exception {
        MapStorage mapStorage = new MapStorage();
        CommonProcessing commonProcessing = new CommonProcessing(mock(RestTemplate.class), mapStorage, new TreeUtil());
        ProcessNodeConverter processNodeConverter = new ProcessNodeConverter(
                mock(RestTemplate.class), commonProcessing, mapStorage);
        ProcessNodeDataDO.Process process = new ProcessNodeDataDO.Process();
        process.setName(processName);
        process.setProcessTypeDescription("read data");
        process.setReturnDatagridFlag("false");
        process.setOutputVariableList(new ProcessNodeDataDO.Process.OutputVariableList());
        for (String name : List.of("ARPU", "TENURE")) {
            ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO output =
                new ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO();
            output.setName(name);
            output.setTypeDescription("string");
            process.getOutputVariableList().getIbVariableDOs().add(output);
        }
        ProcessNodeDataDO processNode = new ProcessNodeDataDO();
        processNode.setNodeId("read-data-node");
        processNode.setNodeName("Read Data");
        processNode.setProcess(process);
        Decision decision = new Decision();

        List<Step> steps = processNodeConverter.addCustomObjectStep(processNode, decision, (short) 1, "node");

        ConditionDecisionStep flow = new ConditionDecisionStep();
        flow.setFlow(steps);
        decision.setFlow(flow);
        invokeAddMissingVars(createConverter(new TreeUtil(), mapStorage), decision);

        Step customObjectStep = steps.get(0);
        for (String name : List.of("ARPU", "TENURE")) {
            Mapping mapping = customObjectStep.getMappings().stream()
                .filter(item -> name.equals(item.getStepTermName()))
                .findFirst().orElseThrow();
            // Written under RTDM's output id "<NodeName>.<ProcessName><var>", the name later nodes reference it by
            assertEquals("ReadData" + processName + name, mapping.getTargetDecisionTermName());
            assertEquals(Mapping.DirectionEnum.OUTPUT, mapping.getDirection());
            SignatureTerm signatureTerm = decision.getSignature().stream()
                .filter(item -> mapping.getTargetDecisionTermName().equals(item.getName()))
                .findFirst().orElseThrow();
            assertEquals(SignatureTerm.DataTypeEnum.STRING, signatureTerm.getDataType());
            assertEquals(SignatureTerm.DirectionEnum.OUTPUT, signatureTerm.getDirection());
        }
        for (String variableName : Arrays.asList("returnCode", "rowCount")) {
            Mapping mapping = customObjectStep.getMappings().stream()
                    .filter(item -> variableName.equals(item.getTargetDecisionTermName()))
                    .findFirst().orElseThrow();
            assertEquals(Mapping.DirectionEnum.OUTPUT, mapping.getDirection());
            assertEquals(variableName, mapping.getStepTermName());
            SignatureTerm signatureTerm = decision.getSignature().stream()
                    .filter(item -> variableName.equals(item.getName()))
                    .findFirst().orElseThrow();
            assertEquals(SignatureTerm.DataTypeEnum.INTEGER, signatureTerm.getDataType());
            assertEquals(SignatureTerm.DirectionEnum.OUTPUT, signatureTerm.getDirection());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"DP_getSAlternativeOffersInput_v5", "ShortProcess", "DP_outOffers", "DP_getApplication"})
    void preservesSqlDatagridOutputTermName(String processName) throws Exception {
        MapStorage mapStorage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing commonProcessing = new CommonProcessing(restTemplate, mapStorage, new TreeUtil());
        ProcessNodeConverter processNodeConverter = new ProcessNodeConverter(restTemplate, commonProcessing, mapStorage);
        Step step = new Step();
        Decision decision = new Decision();
        Method processMapping = ProcessNodeConverter.class.getDeclaredMethod(
                "processDataProcessMapping", String.class, ProcessNodeDataDO.Process.class, Step.class, Decision.class);
        processMapping.setAccessible(true);
        ProcessNodeDataDO.Process process = new ProcessNodeDataDO.Process();
        process.setName(processName);
        process.setOutputVariableList(new ProcessNodeDataDO.Process.OutputVariableList());

        // Without a node name or DatagridName the grid keeps its process-based name
        SignatureTerm datagrid = (SignatureTerm) processMapping.invoke(processNodeConverter, null, process, step, decision);

        assertEquals(3, step.getMappings().size());
        Mapping mapping = step.getMappings().get(0);
        assertEquals(processName + "_dgo", mapping.getStepTermName());
        assertEquals(processName + "_out", mapping.getTargetDecisionTermName());
        assertEquals(Mapping.DirectionEnum.OUTPUT, mapping.getDirection());
        assertEquals(SignatureTerm.DataTypeEnum.DATAGRID, datagrid.getDataType());
        assertTrue(decision.getSignature().contains(datagrid));
        for (String variableName : List.of("returnCode", "rowCount")) {
            Mapping standardMapping = step.getMappings().stream()
                .filter(item -> variableName.equals(item.getStepTermName())).findFirst().orElseThrow();
            assertEquals(variableName, standardMapping.getTargetDecisionTermName());
            assertEquals(Mapping.DirectionEnum.OUTPUT, standardMapping.getDirection());
            SignatureTerm signatureTerm = decision.getSignature().stream()
                .filter(item -> variableName.equals(item.getName())).findFirst().orElseThrow();
            assertEquals(SignatureTerm.DataTypeEnum.INTEGER, signatureTerm.getDataType());
            assertEquals(SignatureTerm.DirectionEnum.OUTPUT, signatureTerm.getDirection());
        }
        verifyNoInteractions(restTemplate);
    }

    @Test
    void restoresParentBranchBeforeProcessingSibling() throws Exception {
        MapStorage mapStorage = mock(MapStorage.class);
        TreeUtil treeUtil = mock(TreeUtil.class);
        Map<Short, String> nodeIdByObjectId = new HashMap<>();
        nodeIdByObjectId.put((short) 1, "branch-a");
        nodeIdByObjectId.put((short) 2, "branch-b");
        nodeIdByObjectId.put((short) 3, "node-a");
        nodeIdByObjectId.put((short) 4, "node-b");

        Step stepA = new Step();
        Step stepB = new Step();
        Map<String, java.util.List<Step>> nodeSteps = new HashMap<>();
        nodeSteps.put("node-a", Collections.singletonList(stepA));
        nodeSteps.put("node-b", Collections.singletonList(stepB));

        ConditionBranch branchA = new ConditionBranch();
        branchA.setSteps(new ArrayList<>());
        ConditionBranch branchB = new ConditionBranch();
        branchB.setSteps(new ArrayList<>());
        Map<String, ConditionBranch> branches = new HashMap<>();
        branches.put("branch-a", branchA);
        branches.put("branch-b", branchB);

        when(treeUtil.getObjIdToNodeIdMap()).thenReturn(nodeIdByObjectId);
        when(mapStorage.getNodeIdStepMap()).thenReturn(nodeSteps);
        when(mapStorage.getBranchNodeIdConditionBranchMap()).thenReturn(branches);
        when(mapStorage.isUseCrossBranchLinks()).thenReturn(false);

        Converter converter = new Converter(
                mock(ProcessNodeConverter.class),
                mock(SubDiagramNodeConverter.class),
                mock(ReplyNodeConverter.class),
                mock(MultiSelectNodeConverter.class),
                mock(SplitNodeConverter.class),
                mock(ConfirmContactNodeConverter.class),
                mock(CommonProcessing.class),
                mock(AssignmentNodeConverter.class),
                mock(CrossTableNodeConverter.class),
                mock(StagedTreatmentConverter.class),
                treeUtil,
                mapStorage);

        GenericTreeNode<Short> root = new GenericTreeNode<>((short) 0);
        GenericTreeNode<Short> branchNodeA = new GenericTreeNode<>((short) 1);
        GenericTreeNode<Short> branchNodeB = new GenericTreeNode<>((short) 2);
        branchNodeA.addChild(new GenericTreeNode<>((short) 3));
        branchNodeB.addChild(new GenericTreeNode<>((short) 4));
        root.addChild(branchNodeA);
        root.addChild(branchNodeB);

        Method finalConnectionOfNodes = Converter.class.getDeclaredMethod(
                "finalConnectionOfNodes", GenericTreeNode.class, Deque.class);
        finalConnectionOfNodes.setAccessible(true);
        finalConnectionOfNodes.invoke(converter, root, new ArrayDeque<Short>());

        assertEquals(Collections.singletonList(stepA), branchA.getSteps());
        assertEquals(Collections.singletonList(stepB), branchB.getSteps());
    }

    @Test
    void doesNotAppendNestedConditionToParentBranch() throws Exception {
        MapStorage mapStorage = new MapStorage();
        TreeUtil treeUtil = new TreeUtil();
        Converter converter = createConverter(treeUtil, mapStorage);

        treeUtil.getObjIdToNodeIdMap().put((short) 1, "outer-branch");
        treeUtil.getObjIdToNodeIdMap().put((short) 2, "parent-condition");
        treeUtil.getObjIdToNodeIdMap().put((short) 3, "nested-condition");

        Step parentCondition = new Step();
        parentCondition.setType(Step.TypeEnum.CONDITION);
        Step nestedCondition = new Step();
        nestedCondition.setType(Step.TypeEnum.CONDITION);
        mapStorage.getNodeIdStepMap().put("parent-condition", Collections.singletonList(parentCondition));
        mapStorage.getNodeIdStepMap().put("nested-condition", Collections.singletonList(nestedCondition));

        ConditionBranch outerBranch = new ConditionBranch();
        outerBranch.setSteps(new ArrayList<>());
        mapStorage.getBranchNodeIdConditionBranchMap().put("outer-branch", outerBranch);

        GenericTreeNode<Short> parentNode = new GenericTreeNode<>((short) 2);
        parentNode.addChild(new GenericTreeNode<>((short) 3));
        Deque<Short> branchStack = new ArrayDeque<>();
        branchStack.push((short) 1);

        invokeFinalConnection(converter, parentNode, branchStack);

        assertTrue(outerBranch.getSteps().isEmpty());
    }

    @Test
    void addsMissingVariableOnceAndMergesCaseInsensitiveMappingDirections() throws Exception {
        Converter converter = createConverter(new TreeUtil(), new MapStorage());
        Decision decision = new Decision();
        ConditionDecisionStep flow = new ConditionDecisionStep();
        flow.setFlow(new ArrayList<>());
        flow.addFlowItem(mappedStep("DerivedValue", Mapping.DirectionEnum.OUTPUT));
        flow.addFlowItem(mappedStep("derivedvalue", Mapping.DirectionEnum.INPUT));
        decision.setFlow(flow);

        invokeAddMissingVars(converter, decision);
        invokeAddMissingVars(converter, decision);

        assertEquals(1, decision.getSignature().size());
        SignatureTerm term = decision.getSignature().get(0);
        assertEquals("DerivedValue", term.getName());
        assertEquals(SignatureTerm.DataTypeEnum.UNKNOWN, term.getDataType());
        assertEquals(SignatureTerm.DirectionEnum.INOUT, term.getDirection());
    }

    @Test
    void preservesExistingVariableTypeAndInOutDirectionForCaseInsensitiveMapping() throws Exception {
        Converter converter = createConverter(new TreeUtil(), new MapStorage());
        Decision decision = new Decision();
        SignatureTerm term = signatureTerm("ExistingValue");
        term.setDirection(SignatureTerm.DirectionEnum.INOUT);
        decision.getSignature().add(term);
        ConditionDecisionStep flow = new ConditionDecisionStep();
        flow.setFlow(Collections.singletonList(mappedStep("existingvalue", Mapping.DirectionEnum.INPUT)));
        decision.setFlow(flow);

        invokeAddMissingVars(converter, decision);

        assertEquals(1, decision.getSignature().size());
        assertSame(term, decision.getSignature().get(0));
        assertEquals(SignatureTerm.DataTypeEnum.BOOLEAN, term.getDataType());
        assertEquals(SignatureTerm.DirectionEnum.INOUT, term.getDirection());
    }

    @Test
    void ignoresIncompleteMappingsAndStepsWithoutMappings() throws Exception {
        Converter converter = createConverter(new TreeUtil(), new MapStorage());
        Decision decision = new Decision();
        ConditionDecisionStep flow = new ConditionDecisionStep();
        flow.setFlow(Arrays.asList(new Step(), mappedStep(null, Mapping.DirectionEnum.INPUT),
                mappedStep("NoDirection", null), mappedStep("Valid", Mapping.DirectionEnum.OUTPUT)));
        decision.setFlow(flow);

        invokeAddMissingVars(converter, decision);

        assertEquals(1, decision.getSignature().size());
        assertEquals("Valid", decision.getSignature().get(0).getName());
        assertEquals(SignatureTerm.DirectionEnum.OUTPUT, decision.getSignature().get(0).getDirection());
    }

    @Test
    void toleratesMissingAndEmptyDecisionFlows() throws Exception {
        Converter converter = createConverter(new TreeUtil(), new MapStorage());
        Decision decision = new Decision();
        SignatureTerm term = signatureTerm("ExistingValue");
        decision.getSignature().add(term);

        decision.setFlow(null);
        invokeAddMissingVars(converter, decision);
        ConditionDecisionStep flow = new ConditionDecisionStep();
        flow.setFlow(null);
        decision.setFlow(flow);
        invokeAddMissingVars(converter, decision);
        flow.setFlow(Collections.emptyList());
        invokeAddMissingVars(converter, decision);

        assertEquals(Collections.singletonList(term), decision.getSignature());
        assertEquals(SignatureTerm.DirectionEnum.NONE, term.getDirection());
    }

    @ParameterizedTest
    @ValueSource(strings = {"true", "false", "case", "default"})
    void backfillsNestedVariablesInEveryBranchKind(String branchKind) throws Exception {
        Converter converter = createConverter(new TreeUtil(), new MapStorage());
        Decision decision = new Decision();
        SignatureTerm existing = signatureTerm("ExistingValue");
        decision.getSignature().add(existing);
        Step nestedCondition = new Step().type(Step.TypeEnum.CONDITION);
        nestedCondition.setOnFalse(branchWithSteps(mappedStep("BranchOnly", Mapping.DirectionEnum.INPUT),
                mappedStep("existingvalue", Mapping.DirectionEnum.OUTPUT)));
        ConditionBranch nestedBranch = branchWithSteps(nestedCondition);
        Step parent = new Step();
        switch (branchKind) {
            case "true":
                parent.setOnTrue(nestedBranch);
                break;
            case "false":
                parent.setOnFalse(nestedBranch);
                break;
            case "case":
                BranchCase branchCase = new BranchCase();
                branchCase.setOnTrue(nestedBranch);
                parent.setBranchCases(Collections.singletonList(branchCase));
                break;
            case "default":
                parent.setDefaultCase(nestedBranch);
                break;
            default:
                throw new IllegalArgumentException(branchKind);
        }
        ConditionDecisionStep flow = new ConditionDecisionStep();
        flow.setFlow(Arrays.asList(mappedStep("ExistingValue", Mapping.DirectionEnum.INPUT), parent));
        decision.setFlow(flow);

        invokeAddMissingVars(converter, decision);
        invokeAddMissingVars(converter, decision);

        assertEquals(2, decision.getSignature().size());
        assertSame(existing, decision.getSignature().get(0));
        assertEquals(SignatureTerm.DataTypeEnum.BOOLEAN, existing.getDataType());
        assertEquals(SignatureTerm.DirectionEnum.INOUT, existing.getDirection());
        SignatureTerm branchOnly = decision.getSignature().get(1);
        assertEquals("BranchOnly", branchOnly.getName());
        assertEquals(SignatureTerm.DataTypeEnum.UNKNOWN, branchOnly.getDataType());
        assertEquals(SignatureTerm.DirectionEnum.INPUT, branchOnly.getDirection());
    }

    @Test
    void handlesSharedAndCyclicBranchesWithoutDuplicatingSignatureTerms() throws Exception {
        Converter converter = createConverter(new TreeUtil(), new MapStorage());
        Decision decision = new Decision();
        Step shared = mappedStep("SharedValue", Mapping.DirectionEnum.OUTPUT);
        shared.addMappingsItem(null);
        ConditionBranch sharedBranch = branchWithSteps(shared, null,
                mappedStep("sharedvalue", Mapping.DirectionEnum.INPUT));
        shared.setOnTrue(sharedBranch);
        Step parent = new Step();
        parent.setOnTrue(sharedBranch);
        parent.setOnFalse(sharedBranch);
        parent.setDefaultCase(new ConditionBranch());
        parent.setBranchCases(Arrays.asList(null, new BranchCase()));
        ConditionDecisionStep flow = new ConditionDecisionStep();
        flow.setFlow(Arrays.asList(parent, null));
        decision.setFlow(flow);

        invokeAddMissingVars(converter, decision);

        assertEquals(1, decision.getSignature().size());
        assertEquals("SharedValue", decision.getSignature().get(0).getName());
        assertEquals(SignatureTerm.DirectionEnum.INOUT, decision.getSignature().get(0).getDirection());
        assertSame(sharedBranch, shared.getOnTrue());
        assertEquals(3, sharedBranch.getSteps().size());
    }

    private ConditionBranch branchWithSteps(Step... steps) {
        ConditionBranch branch = new ConditionBranch();
        branch.setSteps(Arrays.asList(steps));
        return branch;
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void assemblesSharedNodeWithLinksOnlyWhenEnabled(boolean useCrossBranchLinks) throws Exception {
        MapStorage mapStorage = new MapStorage();
        mapStorage.setUseCrossBranchLinks(useCrossBranchLinks);
        TreeUtil treeUtil = new TreeUtil();
        Converter converter = createConverter(treeUtil, mapStorage);
        treeUtil.getObjIdToNodeIdMap().put((short) 1, "branch-a");
        treeUtil.getObjIdToNodeIdMap().put((short) 2, "branch-b");
        treeUtil.getObjIdToNodeIdMap().put((short) 3, "shared");
        treeUtil.getObjIdToNodeIdMap().put((short) 4, "downstream");
        Step shared = new Step().id("shared").type(Step.TypeEnum.RULESET).linkLabel("shared-label");
        Step downstream = new Step().id("downstream").type(Step.TypeEnum.RULESET);
        Step link = new Step().type(Step.TypeEnum.NODE_LINK);
        link.setDecisionNodeLinkTarget("shared-label");
        mapStorage.getNodeIdStepMap().put("shared", Collections.singletonList(shared));
        mapStorage.getNodeIdStepMap().put("downstream", Collections.singletonList(downstream));
        mapStorage.getCrossBranchLinkSteps().put("shared-label", Collections.singletonList(link));
        ConditionBranch branchA = new ConditionBranch();
        branchA.setSteps(new ArrayList<>());
        ConditionBranch branchB = new ConditionBranch();
        branchB.setSteps(new ArrayList<>());
        mapStorage.getBranchNodeIdConditionBranchMap().put("branch-a", branchA);
        mapStorage.getBranchNodeIdConditionBranchMap().put("branch-b", branchB);
        GenericTreeNode<Short> root = new GenericTreeNode<>((short) 0);
        for (short branchId : new short[] {1, 2}) {
            GenericTreeNode<Short> branch = new GenericTreeNode<>(branchId);
            GenericTreeNode<Short> sharedNode = new GenericTreeNode<>((short) 3);
            sharedNode.addChild(new GenericTreeNode<>((short) 4));
            branch.addChild(sharedNode);
            root.addChild(branch);
        }
        Deque<Short> branchStack = new ArrayDeque<>();

        invokeFinalConnection(converter, root, branchStack);

        assertEquals(Arrays.asList(shared, downstream), branchA.getSteps());
        assertEquals(useCrossBranchLinks ? Collections.singletonList(link) : Arrays.asList(shared, downstream),
                branchB.getSteps());
        assertEquals(useCrossBranchLinks ? Collections.singletonList("shared-label") : Collections.emptyList(),
                mapStorage.getDecisionNodeLinkMap());
        assertTrue(branchStack.isEmpty());
    }

    @Test
    void doesNotAddCrossBranchLinkPointingAtSameBranchLabel() throws Exception {
        MapStorage mapStorage = new MapStorage();
        mapStorage.setUseCrossBranchLinks(true);
        TreeUtil treeUtil = new TreeUtil();
        Converter converter = createConverter(treeUtil, mapStorage);
        treeUtil.getObjIdToNodeIdMap().put((short) 1, "branch");
        treeUtil.getObjIdToNodeIdMap().put((short) 2, "shared");
        Step shared = new Step().type(Step.TypeEnum.RULESET).linkLabel("shared-label");
        Step link = new Step().type(Step.TypeEnum.NODE_LINK);
        link.setDecisionNodeLinkTarget("shared-label");
        mapStorage.getNodeIdStepMap().put("shared", Collections.singletonList(shared));
        mapStorage.getCrossBranchLinkSteps().put("shared-label", Collections.singletonList(link));
        ConditionBranch branch = new ConditionBranch();
        branch.setSteps(new ArrayList<>());
        mapStorage.getBranchNodeIdConditionBranchMap().put("branch", branch);
        GenericTreeNode<Short> root = new GenericTreeNode<>((short) 0);
        GenericTreeNode<Short> sharedNode = new GenericTreeNode<>((short) 2);
        root.addChild(sharedNode);
        Deque<Short> branchStack = new ArrayDeque<>();
        branchStack.push((short) 1);

        invokeFinalConnection(converter, root, branchStack);
        invokeFinalConnection(converter, root, branchStack);

        assertEquals(Collections.singletonList(shared), branch.getSteps());
        assertEquals(1, branchStack.size());
        assertEquals(Short.valueOf((short) 1), branchStack.peek());
    }

    @Test
    void connectsContinuationInsideAlreadyAssembledSplit() throws Exception {
        MapStorage mapStorage = new MapStorage();
        mapStorage.setUseCrossBranchLinks(true);
        TreeUtil treeUtil = new TreeUtil();
        Converter converter = createConverter(treeUtil, mapStorage);
        treeUtil.getObjIdToNodeIdMap().put((short) 1, "split");
        treeUtil.getObjIdToNodeIdMap().put((short) 2, "cell");
        treeUtil.getObjIdToNodeIdMap().put((short) 3, "process");
        treeUtil.getObjIdToNodeIdMap().put((short) 4, "shared");
        Step process = new Step().id("process").type(Step.TypeEnum.RULESET);
        Step shared = new Step().id("shared").type(Step.TypeEnum.RULESET).linkLabel("shared-label");
        ConditionBranch branch = new ConditionBranch();
        branch.setSteps(new ArrayList<>(Collections.singletonList(process)));
        Step split = new Step().type(Step.TypeEnum.BRANCH);
        split.setDefaultCase(branch);
        mapStorage.getNodeIdStepMap().put("split", Collections.singletonList(split));
        mapStorage.getNodeIdStepMap().put("process", Collections.singletonList(process));
        mapStorage.getNodeIdStepMap().put("shared", Collections.singletonList(shared));
        mapStorage.getBranchNodeIdConditionBranchMap().put("cell", branch);
        GenericTreeNode<Short> root = new GenericTreeNode<>((short) 0);
        GenericTreeNode<Short> splitNode = new GenericTreeNode<>((short) 1);
        GenericTreeNode<Short> cellNode = new GenericTreeNode<>((short) 2);
        GenericTreeNode<Short> processNode = new GenericTreeNode<>((short) 3);
        processNode.addChild(new GenericTreeNode<>((short) 4));
        cellNode.addChild(processNode);
        splitNode.addChild(cellNode);
        splitNode.addChild(new GenericTreeNode<>((short) 5));
        root.addChild(splitNode);

        invokeFinalConnection(converter, root, new ArrayDeque<>());

        assertEquals(Arrays.asList(process, shared), branch.getSteps());
    }

    private void invokeAddMissingVars(Converter converter, Decision decision) throws Exception {
        Method addMissingVars = Converter.class.getDeclaredMethod("addMissingVars", Decision.class);
        addMissingVars.setAccessible(true);
        addMissingVars.invoke(converter, decision);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void connectsSharedTargetSeededInLaterBranch(boolean useCrossBranchLinks) throws Exception {
        MapStorage mapStorage = new MapStorage();
        mapStorage.setUseCrossBranchLinks(useCrossBranchLinks);
        TreeUtil treeUtil = new TreeUtil();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), mapStorage, treeUtil);
        Converter converter = createConverter(treeUtil, mapStorage);
        for (short objId = 1; objId <= 5; objId++) {
            treeUtil.getObjIdToNodeIdMap().put(objId, "node-" + objId);
        }
        treeUtil.getNodeIdObjIdMap().put("node-1", (short) 1);
        treeUtil.getNodeIdObjIdMap().put("node-2", (short) 2);
        treeUtil.getObjIdOutputNodesMap().put((short) 1, "node-3");
        treeUtil.getObjIdOutputNodesMap().put((short) 2, "node-4");
        treeUtil.getNodeIdInputNodesMap().put("node-3", "node-1");
        treeUtil.getNodeIdInputNodesMap().put("node-4", "node-3,node-2");
        Step process = new Step().id("process").type(Step.TypeEnum.RULESET);
        Step shared = new Step().id("shared").type(Step.TypeEnum.RULESET);
        Step downstream = new Step().id("downstream").type(Step.TypeEnum.RULESET);
        List<Step> processSteps = new ArrayList<>();
        List<Step> sharedSteps = new ArrayList<>();
        processing.addStep(processSteps, process, "node-3");
        processing.addStep(sharedSteps, shared, "node-4");
        mapStorage.getNodeIdStepMap().put("node-3", processSteps);
        mapStorage.getNodeIdStepMap().put("node-4", sharedSteps);
        mapStorage.getNodeIdStepMap().put("node-5", Collections.singletonList(downstream));
        ConditionBranch branchA = processing.createConditionBranch("node-1", true);
        ConditionBranch branchB = processing.createConditionBranch("node-2", true);
        mapStorage.getBranchNodeIdConditionBranchMap().put("node-1", branchA);
        mapStorage.getBranchNodeIdConditionBranchMap().put("node-2", branchB);
        GenericTreeNode<Short> root = new GenericTreeNode<>((short) 0);
        for (short branchId : new short[] {1, 2}) {
            GenericTreeNode<Short> branchNode = new GenericTreeNode<>(branchId);
            GenericTreeNode<Short> sharedNode = new GenericTreeNode<>((short) 4);
            sharedNode.addChild(new GenericTreeNode<>((short) 5));
            if (branchId == 1) {
                GenericTreeNode<Short> processNode = new GenericTreeNode<>((short) 3);
                processNode.addChild(sharedNode);
                branchNode.addChild(processNode);
            } else {
                branchNode.addChild(sharedNode);
            }
            root.addChild(branchNode);
        }

        invokeFinalConnection(converter, root, new ArrayDeque<>());

        assertEquals(Arrays.asList(shared, downstream), branchB.getSteps());
        if (useCrossBranchLinks) {
            assertEquals(2, branchA.getSteps().size());
            assertSame(process, branchA.getSteps().get(0));
            assertEquals(Step.TypeEnum.NODE_LINK, branchA.getSteps().get(1).getType());
            assertEquals(shared.getLinkLabel(), branchA.getSteps().get(1).getDecisionNodeLinkTarget());
            assertEquals(Collections.singletonList(shared.getLinkLabel()), mapStorage.getDecisionNodeLinkMap());
        } else {
            assertEquals(Arrays.asList(process, shared, downstream), branchA.getSteps());
        }
    }

    private Converter createConverter(TreeUtil treeUtil, MapStorage mapStorage) {
        return createConverter(treeUtil, mapStorage, mock(CommonProcessing.class));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void preservesDefaultTargetAndContinuationWhenCasesShareItsCell(boolean useCrossBranchLinks) throws Exception {
        MapStorage mapStorage = new MapStorage();
        mapStorage.setUseCrossBranchLinks(useCrossBranchLinks);
        TreeUtil treeUtil = new TreeUtil();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), mapStorage, treeUtil);
        Converter converter = createConverter(treeUtil, mapStorage, processing);
        treeUtil.getObjIdToNodeIdMap().put((short) 1, "split");
        treeUtil.getObjIdToNodeIdMap().put((short) 2, "cell");
        treeUtil.getObjIdToNodeIdMap().put((short) 3, "shared");
        treeUtil.getObjIdToNodeIdMap().put((short) 4, "reply");
        treeUtil.getNodeIdObjIdMap().put("cell", (short) 2);
        treeUtil.getObjIdOutputNodesMap().put((short) 2, "shared");
        treeUtil.getNodeIdInputNodesMap().put("shared", "cell,other-cell");
        Step shared = new Step().id("shared").type(Step.TypeEnum.RULESET);
        Step reply = new Step().id("reply").type(Step.TypeEnum.RULESET);
        List<Step> sharedSteps = new ArrayList<>();
        processing.addStep(sharedSteps, shared, "shared");
        mapStorage.getNodeIdStepMap().put("shared", sharedSteps);
        mapStorage.getNodeIdStepMap().put("reply", Collections.singletonList(reply));
        Step split = new Step().type(Step.TypeEnum.BRANCH);
        ConditionBranch originalDefault = processing.createConditionBranch("cell", true);
        split.setDefaultCase(originalDefault);
        BranchCase missingCase = new BranchCase();
        missingCase.setLabel("cell");
        BranchCase ignoreCase = new BranchCase();
        ignoreCase.setLabel("cell");
        split.setBranchCases(Arrays.asList(missingCase, ignoreCase));
        SplitNodeDataDO source = new SplitNodeDataDO();
        source.setLineItems(new SplitNodeDataDO.LineItems());
        SplitNodeDataDO.LineItems.SplitNodeLineItemDO remainder = new SplitNodeDataDO.LineItems.SplitNodeLineItemDO();
        remainder.setCellId("cell");
        remainder.setValue(com.sas.rtdm2id.util.model.RTDM2IDConstants.REMAINDER_VALUE);
        source.getLineItems().getSplitNodeLineItemDOs().add(remainder);
        treeUtil.getNodeIdObjectHashMap().put("split", new RTDMObject("split", source));
        mapStorage.getNodeIdStepMap().put("split", Collections.singletonList(split));
        Method fillBranch = Converter.class.getDeclaredMethod("fillBranchStepWithInnerSteps", Step.class, String.class);
        fillBranch.setAccessible(true);
        fillBranch.invoke(converter, split, "split");
        GenericTreeNode<Short> splitNode = new GenericTreeNode<>((short) 1);
        GenericTreeNode<Short> cellNode = new GenericTreeNode<>((short) 2);
        GenericTreeNode<Short> sharedNode = new GenericTreeNode<>((short) 3);
        sharedNode.addChild(new GenericTreeNode<>((short) 4));
        cellNode.addChild(sharedNode);
        splitNode.addChild(cellNode);

        invokeFinalConnection(converter, splitNode, new ArrayDeque<>());

        assertSame(originalDefault, split.getDefaultCase());
        assertSame(originalDefault, mapStorage.getBranchNodeIdConditionBranchMap().get("cell"));
        assertEquals(Arrays.asList(shared, reply), originalDefault.getSteps());
        for (BranchCase branchCase : split.getBranchCases()) {
            if (useCrossBranchLinks) {
                assertEquals(1, branchCase.getOnTrue().getSteps().size());
                assertEquals(shared.getLinkLabel(), branchCase.getOnTrue().getSteps().get(0).getDecisionNodeLinkTarget());
            } else {
                assertSame(originalDefault, branchCase.getOnTrue());
                assertEquals(Arrays.asList(shared, reply), branchCase.getOnTrue().getSteps());
            }
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void assemblesExtractWithEveryNodeReachableAndEveryLinkTargetUnique(boolean useCrossBranchLinks) throws Exception {
        String extractPath = System.getProperty("branchExtract");
        assumeTrue(extractPath != null, "Set -DbranchExtract to an RTDM extract for offline graph validation");
        Batch batch = (Batch) JAXBContext.newInstance(Batch.class).createUnmarshaller().unmarshal(new File(extractPath));
        MapStorage mapStorage = new MapStorage();
        mapStorage.setUseCrossBranchLinks(useCrossBranchLinks);
        TreeUtil treeUtil = new TreeUtil();
        GenericTreeNode<Short> root = treeUtil.createTree(batch.getLogicalUnit()).getRoot();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing processing = new CommonProcessing(restTemplate, mapStorage, treeUtil);
        Converter converter = createConverter(treeUtil, mapStorage, processing);
        List<String> expectedStepIds = new ArrayList<>();
        for (Map.Entry<String, RTDMObject> entry : treeUtil.getNodeIdObjectHashMap().entrySet()) {
            String nodeId = entry.getKey();
            RTDMObject object = entry.getValue();
            List<Step> steps = new ArrayList<>();
            mapStorage.getNodeIdStepMap().put(nodeId, steps);
            if ("start".equals(object.getObjectType()) || "cell".equals(object.getObjectType())) {
                continue;
            }
            if ("custom".equals(object.getObjectType())) {
                Step input = new Step().id(nodeId + "-input").type(Step.TypeEnum.RULESET);
                processing.addStep(steps, input, nodeId);
                expectedStepIds.add(input.getId());
            }
            Step step = new Step().id(nodeId).type(Step.TypeEnum.RULESET);
            if ("split".equals(object.getObjectType())) {
                step.setType(Step.TypeEnum.BRANCH);
                List<BranchCase> cases = new ArrayList<>();
                SplitNodeDataDO split = (SplitNodeDataDO) object.getObject();
                for (SplitNodeDataDO.LineItems.SplitNodeLineItemDO line : split.getLineItems().getSplitNodeLineItemDOs()) {
                    if (!com.sas.rtdm2id.util.model.RTDM2IDConstants.REMAINDER_VALUE.equals(line.getValue())) {
                        BranchCase branchCase = new BranchCase();
                        branchCase.setLabel(line.getCellId());
                        cases.add(branchCase);
                    }
                }
                step.setBranchCases(cases);
            }
            processing.addStep(steps, step, nodeId);
            expectedStepIds.add(nodeId);
        }
        Method connect = Converter.class.getDeclaredMethod("connectConditionsInsideMap", GenericTreeNode.class);
        connect.setAccessible(true);
        connect.invoke(converter, root);
        ConditionDecisionStep flow = new ConditionDecisionStep();
        Method fill = Converter.class.getDeclaredMethod("fillConditionDecisionStep", Short.class, ConditionDecisionStep.class);
        fill.setAccessible(true);
        fill.invoke(converter, root.getChildren().get(0).getData(), flow);

        invokeFinalConnection(converter, root, new ArrayDeque<>());

        JsonNode json = new ObjectMapper().valueToTree(flow);
        List<JsonNode> ids = json.findValues("id");
        for (String expectedId : expectedStepIds) {
            long occurrences = ids.stream().filter(id -> expectedId.equals(id.asText())).count();
            if (useCrossBranchLinks) {
                assertEquals(1, occurrences, "Missing or duplicated converted step " + expectedId);
            } else {
                assertTrue(occurrences >= 1, "Missing converted step " + expectedId);
            }
        }
        List<JsonNode> labels = json.findValues("linkLabel");
        List<JsonNode> targets = json.findValues("decisionNodeLinkTarget");
        if (useCrossBranchLinks) {
            assertTrue(targets.stream().anyMatch(JsonNode::isTextual), "Expected shared-node links in this extract");
            for (JsonNode target : targets) {
                if (target.isTextual()) {
                    assertEquals(1, labels.stream().filter(target::equals).count(), "Invalid link target " + target);
                }
            }
        } else {
            assertTrue(targets.stream().noneMatch(JsonNode::isTextual));
            assertTrue(labels.stream().noneMatch(JsonNode::isTextual));
        }
        verifyNoInteractions(restTemplate);
    }

    private Converter createConverter(TreeUtil treeUtil, MapStorage mapStorage, CommonProcessing processing) {
        return new Converter(
                mock(ProcessNodeConverter.class),
                mock(SubDiagramNodeConverter.class),
                mock(ReplyNodeConverter.class),
                mock(MultiSelectNodeConverter.class),
                mock(SplitNodeConverter.class),
                mock(ConfirmContactNodeConverter.class),
                processing,
                mock(AssignmentNodeConverter.class),
                mock(CrossTableNodeConverter.class),
                mock(StagedTreatmentConverter.class),
                treeUtil,
                mapStorage);
    }

    @Test
    void convertsRemoveStagedTreatmentWithoutFallingThroughToCrossTable() throws Exception {
        MapStorage mapStorage = new MapStorage();
        TreeUtil treeUtil = new TreeUtil();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), mapStorage, treeUtil);
        CrossTableNodeConverter crossTable = mock(CrossTableNodeConverter.class);
        Converter converter = new Converter(
                mock(ProcessNodeConverter.class),
                mock(SubDiagramNodeConverter.class),
                mock(ReplyNodeConverter.class),
                mock(MultiSelectNodeConverter.class),
                mock(SplitNodeConverter.class),
                mock(ConfirmContactNodeConverter.class),
                processing,
                mock(AssignmentNodeConverter.class),
                crossTable,
                new StagedTreatmentConverter(mapStorage, processing),
                treeUtil,
                mapStorage);
        RemoveStagedTreatmentsNodeDataDO node = new RemoveStagedTreatmentsNodeDataDO();
        node.setNodeId("remove");
        node.setNodeName("Remove");
        Method process = Converter.class.getDeclaredMethod("processRTDMObject", RTDMObject.class, Decision.class, Short.class);
        process.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<Step> steps = (List<Step>) process.invoke(converter, new RTDMObject("removeStagedTreatment", node),
                new Decision(), (short) 1);

        assertEquals(1, steps.size());
        assertEquals(Step.TypeEnum.CUSTOM_OBJECT, steps.get(0).getType());
        verifyNoInteractions(crossTable);
    }

    @Test
    void omitsMissingConfirmContactInputStepInsteadOfAddingNull() throws Exception {
        MapStorage mapStorage = new MapStorage();
        TreeUtil treeUtil = new TreeUtil();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), mapStorage, treeUtil);
        ConfirmContactNodeConverter confirmContact = mock(ConfirmContactNodeConverter.class);
        Step recordContact = new Step().type(Step.TypeEnum.RECORD_CONTACT);
        when(confirmContact.createRecordContactStep(any(), any())).thenReturn(recordContact);
        Converter converter = new Converter(
                mock(ProcessNodeConverter.class),
                mock(SubDiagramNodeConverter.class),
                mock(ReplyNodeConverter.class),
                mock(MultiSelectNodeConverter.class),
                mock(SplitNodeConverter.class),
                confirmContact,
                processing,
                mock(AssignmentNodeConverter.class),
                mock(CrossTableNodeConverter.class),
                mock(StagedTreatmentConverter.class),
                treeUtil,
                mapStorage);
        CHandRHNodeDataDO node = new CHandRHNodeDataDO();
        node.setNodeId("contact");
        node.setNodeName("Contact");
        Method process = Converter.class.getDeclaredMethod("processRTDMObject", RTDMObject.class, Decision.class, Short.class);
        process.setAccessible(true);

        @SuppressWarnings("unchecked")
        List<Step> steps = (List<Step>) process.invoke(converter, new RTDMObject("cHandRH", node), new Decision(), (short) 1);

        assertEquals(List.of(recordContact), steps);
    }

    @Test
    void rejectsFilterNodeWithMoreThanOneSelectedOutput() throws Exception {
        TreeUtil treeUtil = new TreeUtil();
        MultiSelectNodeDataDO filter = new MultiSelectNodeDataDO();
        filter.setNodeName("Filter");
        filter.setRemainderOutputCellID("remainder");
        treeUtil.getNodeIdObjectHashMap().put("filter", new RTDMObject("multiSelect", filter));
        Converter converter = createConverter(treeUtil, new MapStorage());
        Method fill = Converter.class.getDeclaredMethod("fillConditionStepWithInnerSteps", Step.class, String.class, List.class);
        fill.setAccessible(true);

        InvocationTargetException thrown = assertThrows(InvocationTargetException.class,
                () -> fill.invoke(converter, new Step(), "filter", List.of("first", "second", "remainder")));

        assertTrue(thrown.getCause() instanceof IllegalStateException);
        assertTrue(thrown.getCause().getMessage().contains("2 selected outputs"));
    }

    @Test
    void validatesReportedGlobalAndExtractionMappingsFromExtract() throws Exception {
        String extractPath = System.getProperty("mappingExtract");
        assumeTrue(extractPath != null, "Set -DmappingExtract to DC_getApplicationForPrelak_v16.xml");
        Batch batch = (Batch) JAXBContext.newInstance(Batch.class).createUnmarshaller().unmarshal(new File(extractPath));
        MapStorage storage = new MapStorage();
        RestTemplate restTemplate = mock(RestTemplate.class);
        CommonProcessing processing = org.mockito.Mockito.spy(new CommonProcessing(restTemplate, storage, new TreeUtil()));
        org.mockito.Mockito.doAnswer(invocation -> {
            String name = invocation.getArgument(0);
            Step step = new Step();
            com.sas.rtdm2id.model.id.decision.RuleSetStep ruleSet = new com.sas.rtdm2id.model.id.decision.RuleSetStep();
            ruleSet.setName(name);
            step.setRuleSet(ruleSet);
            storage.getRuleSetStepMap().put(name, step);
            storage.getRuleSetIsUpdated().put(name, false);
            storage.getRuleSetIsActionUpdated().put(name, false);
            return null;
        }).when(processing).findOrAddRuleSet(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
        org.mockito.Mockito.doNothing().when(processing).callPutRuleSet(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
        org.mockito.Mockito.doNothing().when(processing).addActionToRuleSet(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());

        processing.createRuleSets(batch);

        org.mockito.ArgumentCaptor<com.sas.rtdm2id.model.id.rules.RuleSet> captor =
            org.mockito.ArgumentCaptor.forClass(com.sas.rtdm2id.model.id.rules.RuleSet.class);
        org.mockito.Mockito.verify(processing, org.mockito.Mockito.atLeastOnce())
            .callPutRuleSet(org.mockito.ArgumentMatchers.any(), captor.capture());
        com.sas.rtdm2id.model.id.rules.RuleSet valuation = captor.getAllValues().stream()
            .filter(ruleSet -> "valuationStatusMsg_EEFE0HMSOFBGSQKC".equals(ruleSet.getName())).findFirst().orElseThrow();
        assertEquals("string", valuation.getSignature().stream()
            .filter(term -> "GV_STATUS_MSG_SUCCESS".equals(term.getName())).findFirst().orElseThrow().getDataType());
        assertTrue(storage.getRuleSetStepMap().get(valuation.getName()).getMappings().stream().anyMatch(mapping ->
            "GV_STATUS_MSG_SUCCESS".equals(mapping.getStepTermName())
                && "GlobalGV_STATUS_MSG_SUCCESS".equals(mapping.getTargetDecisionTermName())));
        ProcessNodeConverter converter = new ProcessNodeConverter(restTemplate, processing, storage);
        Method processMapping = ProcessNodeConverter.class.getDeclaredMethod("processDataProcessMapping",
            String.class, ProcessNodeDataDO.Process.class, Step.class, Decision.class);
        processMapping.setAccessible(true);
        Decision decision = new Decision();
        Set<String> targetNames = new HashSet<>();
        for (String nodeId : new String[] {"numeric", "AFHBMLJN3VD2XCT5"}) {
            ProcessNodeDataDO node = batch.getLogicalUnit().getProcessNodeDataDOs().stream()
                .filter(item -> "numeric".equals(nodeId) ? "DP_get_S_OCA_PRELAK_LOAD_v2".equals(item.getProcess().getName())
                    : nodeId.equals(item.getNodeId())).findFirst().orElseThrow();
            SignatureTerm grid = (SignatureTerm) processMapping.invoke(converter, node.getNodeName(), node.getProcess(),
                new Step(), decision);
            String ruleSetName = processing.makeProcessNodeRuleSetName(node) + "_extract_values";
            Step extractStep = storage.getRuleSetStepMap().get(ruleSetName);
            storage.getRuleSetIsUpdated().put(ruleSetName, true);
            storage.getRuleSetIsActionUpdated().put(ruleSetName, true);

            converter.updateRuleSet(grid, extractStep, decision, node.getNodeName(), node.getProcess().getName());

            Mapping sms = extractStep.getMappings().stream()
                .filter(mapping -> "SMS_SZOLGALTATO".equals(mapping.getStepTermName())).findFirst().orElseThrow();
            SignatureTerm target = decision.getSignature().stream()
                .filter(term -> sms.getTargetDecisionTermName().equals(term.getName())).findFirst().orElseThrow();
            assertEquals("numeric".equals(nodeId) ? SignatureTerm.DataTypeEnum.DECIMAL : SignatureTerm.DataTypeEnum.STRING,
                target.getDataType());
            // Written under the name later nodes read the column by (its identifier or <Node>.<Process><column>)
            assertEquals(processing.nodeOutputTermName(CommonProcessing.nodeOutputId(node.getNodeName(),
                node.getProcess().getName(), "SMS_SZOLGALTATO")), target.getName());
            targetNames.add(target.getName());
        }
        assertEquals(2, targetNames.size());
        verifyNoInteractions(restTemplate);
    }

    private void invokeFinalConnection(Converter converter, GenericTreeNode<Short> node,
                                       Deque<Short> branchStack) throws Exception {
        Method finalConnectionOfNodes = Converter.class.getDeclaredMethod(
                "finalConnectionOfNodes", GenericTreeNode.class, Deque.class);
        finalConnectionOfNodes.setAccessible(true);
        finalConnectionOfNodes.invoke(converter, node, branchStack);
    }

    private SignatureTerm signatureTerm(String name) {
        SignatureTerm signatureTerm = new SignatureTerm();
        signatureTerm.setName(name);
        signatureTerm.setDataType(SignatureTerm.DataTypeEnum.BOOLEAN);
        signatureTerm.setDirection(SignatureTerm.DirectionEnum.NONE);
        return signatureTerm;
    }

    private Step mappedStep(String targetName, Mapping.DirectionEnum direction) {
        Mapping mapping = new Mapping();
        mapping.setTargetDecisionTermName(targetName);
        mapping.setDirection(direction);
        Step step = new Step();
        step.addMappingsItem(mapping);
        return step;
    }
}