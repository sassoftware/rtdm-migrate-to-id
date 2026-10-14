package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.Mapping;
import com.sas.rtdm2id.model.id.decision.RuleSetStep;
import com.sas.rtdm2id.model.id.decision.SignatureTerm;
import com.sas.rtdm2id.model.id.decision.SignatureTermExtension;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.id.files.CodeFileSignature;
import com.sas.rtdm2id.model.id.rules.Action;
import com.sas.rtdm2id.model.id.rules.Rule;
import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import jakarta.xml.bind.JAXBContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sas.rtdm2id.util.model.RTDM2IDConstants.PROCESS_TYPE_UPDATE_DATA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class ProcessNodeConverterTest {

    @Test
    void renamesOnlyThePackageDeclaration() {
        String ds2 = "/* uses MyPkg helper */\n"
            + "PACKAGE MyPkg /overwrite=yes;\n"
            + "   dcl package MyPkgHelper h();\n"
            + "   method execute(double x);\n"
            + "      h.call('MyPkg');\n"
            + "   end;\n"
            + "endpackage;\n";

        String converted = ProcessNodeConverter.convertDs2PackageToIdFormat(ds2, "process");

        assertTrue(converted.contains("PACKAGE \"${PACKAGE_NAME}\" /inline;"), converted);
        assertTrue(converted.contains("/* uses MyPkg helper */"), converted);
        assertTrue(converted.contains("dcl package MyPkgHelper h();"), converted);
        assertTrue(converted.contains("h.call('MyPkg');"), converted);
    }

    @Test
    void truncatesAfterEndpackageRegardlessOfCaseAndSpacing() {
        String ds2 = "package p / overwrite = yes ;\n method execute();\n end;\nENDPACKAGE ;\nrun;\nquit;\n";

        String converted = ProcessNodeConverter.convertDs2PackageToIdFormat(ds2, "process");

        assertTrue(converted.endsWith("ENDPACKAGE ;"), converted);
        assertTrue(converted.startsWith("package \"${PACKAGE_NAME}\" /inline ;"), converted);
    }

    @Test
    void convertsOnlyTheIntTypeKeyword() {
        String ds2 = "package p;\n method execute(int count, in_out INT total);\n"
            + "  dcl int printint;\n  put print ;\n end;\nendpackage;";

        String converted = ProcessNodeConverter.convertDs2PackageToIdFormat(ds2, "process");

        assertTrue(converted.contains("method execute(double count, in_out double total);"), converted);
        assertTrue(converted.contains("dcl double printint;"), converted);
        assertTrue(converted.contains("put print ;"), converted);
    }

    @Test
    void renamesListParametersOfTheExecuteMethodOnly() {
        String ds2 = "package p;\n"
            + " method init(varchar(10) unused);\n end;\n"
            + " method execute(varchar(100) name, in_out package tap_float_array scores, double x);\n end;\n"
            + "endpackage;";

        String converted = ProcessNodeConverter.convertDs2PackageToIdFormat(ds2, "process");

        assertTrue(converted.contains("method init(varchar(10) unused);"), converted);
        assertTrue(converted.contains(
            "method execute(varchar(100) name, in_out package datagrid scores_dgo, double x);"), converted);
    }

    @Test
    void failsClearlyWhenDs2StructureIsMissing() {
        IllegalArgumentException noPackage = assertThrows(IllegalArgumentException.class,
            () -> ProcessNodeConverter.convertDs2PackageToIdFormat("method execute(); end;", "Scoring"));
        assertTrue(noPackage.getMessage().contains("Scoring"));
        assertThrows(IllegalArgumentException.class,
            () -> ProcessNodeConverter.convertDs2PackageToIdFormat("package p;\n method execute(); end;", "Scoring"));
        assertThrows(IllegalArgumentException.class,
            () -> ProcessNodeConverter.convertDs2PackageToIdFormat(
                "package p;\n dcl package tap_int_array a;\n endpackage;", "Scoring"));
    }

    @ParameterizedTest
    @CsvSource({"decimal, DATAGRID_GETDOUBLE", "integer, DATAGRID_GETDOUBLE", "datetime, DATAGRID_GETDOUBLE",
        "string, DATAGRID_GETSTRING"})
    void readsDataGridColumnsWithTheGetterForTheirType(String type, String expected) throws Exception {
        Method method = ProcessNodeConverter.class.getDeclaredMethod("getAppropriateDataGridGet", String.class);
        method.setAccessible(true);

        assertEquals(expected, method.invoke(converter(), type));
    }

    private static ProcessNodeConverter converter() {
        MapStorage storage = new MapStorage();
        return new ProcessNodeConverter(mock(RestTemplate.class),
            new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()), storage);
    }

    private static String sql(String methodName, ProcessNodeDataDO.Process process) throws Exception {
        Method method = ProcessNodeConverter.class.getDeclaredMethod(methodName, ProcessNodeDataDO.Process.class);
        method.setAccessible(true);
        try {
            return (String) method.invoke(converter(), process);
        } catch (InvocationTargetException e) {
            throw (Exception) e.getCause();
        }
    }

    @Test
    void buildsSqlWithoutInputVariableList() throws Exception {
        ProcessNodeDataDO.Process process = new ProcessNodeDataDO.Process();
        process.setName("ReadCustomer");
        process.setTableName("CUSTOMER");
        ProcessNodeDataDO.Process.OutputVariableList outputs = new ProcessNodeDataDO.Process.OutputVariableList();
        ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO output = new ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO();
        output.setName("HHID");
        output.setTypeDescription("double");
        outputs.getIbVariableDOs().add(output);
        process.setOutputVariableList(outputs);

        String select = sql("buildSelectSql", process);
        assertTrue(select.endsWith("select HHID AS {:HHID:decimal} from \"CUSTOMER\""), select);
        assertFalse(select.contains("where"));
        assertEquals("insert into \"CUSTOMER\" () values ()", sql("buildInsertSql", process));
        // Without criteria an update applies to every row, as in RTDM
        process.setProcessType((byte) PROCESS_TYPE_UPDATE_DATA);
        assertEquals("update \"CUSTOMER\" set HHID={?:HHID_set:decimal}", sql("buildUpdateSql", process));

        process.setOutputVariableList(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> sql("buildSelectSql", process));
        assertTrue(exception.getMessage().contains("ReadCustomer"));
    }

    // Shapes below mirror the RTDM sample extracts RTDMDataProcessNodes.xml (G_ORA_AgeUpdate) and
    // RTDMDataProcessNodesSameColumn.xml: criteria in InputVariableList, update columns with their new Value in
    // OutputVariableList, WhereClauseOp always 0.
    private static final String UPDATE_PROCESS_XML = """
        <Batch><LogicalUnit><ProcessNodeDataDO><NodeId>update</NodeId><Process>
          <InputVariableList>
            <IBVariableDO><Name>CLIENTID</Name><TypeDescription>double</TypeDescription>
              <Value><DoubleValue>10001.0</DoubleValue></Value><WhereClauseOp>0</WhereClauseOp></IBVariableDO>
          </InputVariableList>
          <Name>UpdateDatabaseSameColumn</Name>
          <OutputVariableList>
            <IBVariableDO><Name>CLIENTID</Name><TypeDescription>double</TypeDescription>
              <Value><DoubleValue>10002.0</DoubleValue></Value><WhereClauseOp>0</WhereClauseOp></IBVariableDO>
            <IBVariableDO><Name>HHID</Name><TypeDescription>double</TypeDescription>
              <Value><ValueTypeVarInfoDO><VarInfoId>events.ID</VarInfoId><VarInfoSource>Request</VarInfoSource>
                <VarName>ID</VarName><Type>double</Type></ValueTypeVarInfoDO></Value>
              <WhereClauseOp>0</WhereClauseOp></IBVariableDO>
          </OutputVariableList>
          <ProcessType>3</ProcessType>
          <ProcessTypeDescription>updated date</ProcessTypeDescription>
          <TableName>CUSTOMER1</TableName>
        </Process></ProcessNodeDataDO></LogicalUnit></Batch>
        """;

    private static Batch updateBatch() throws Exception {
        return (Batch) JAXBContext.newInstance(Batch.class).createUnmarshaller().unmarshal(new StringReader(UPDATE_PROCESS_XML));
    }

    @Test
    void readsTheNewValueOfUpdateColumnsFromTheExtract() throws Exception {
        ProcessNodeDataDO.Process process = updateBatch().getLogicalUnit().getProcessNodeDataDOs().get(0).getProcess();
        List<ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO> columns = process.getOutputVariableList().getIbVariableDOs();

        assertEquals("10002.0", columns.get(0).getValue().getDoubleValue());
        assertEquals("events.ID", columns.get(1).getValue().getValueTypeVarInfoDO().getVarInfoId());
    }

    @Test
    void updatesOutputColumnsWhereTheInputCriteriaMatch() throws Exception {
        Batch batch = updateBatch();
        ProcessNodeDataDO.Process process = batch.getLogicalUnit().getProcessNodeDataDOs().get(0).getProcess();

        ProcessNodeConverter.normalizeUpdateDataProcesses(batch);
        ProcessNodeConverter.normalizeUpdateDataProcesses(batch);

        assertEquals("update \"CUSTOMER1\" set CLIENTID={?:CLIENTID_set:decimal}, HHID={?:HHID_set:decimal}"
            + " where CLIENTID={?:CLIENTID:decimal}", sql("buildUpdateSql", process));
        List<ProcessNodeDataDO.Process.InputVariableList.IBVariableDO> inputs = process.getInputVariableList().getIbVariableDOs();
        assertEquals(List.of("CLIENTID", "CLIENTID_set", "HHID_set"),
            inputs.stream().map(ProcessNodeDataDO.Process.InputVariableList.IBVariableDO::getName).toList());
        assertEquals("10001.0", inputs.get(0).getValue().getDoubleValue());
        assertEquals("10002.0", inputs.get(1).getValue().getDoubleValue());
        assertEquals("events.ID", inputs.get(2).getValue().getValueTypeVarInfoDO().getVarInfoId());
        assertTrue(process.getOutputVariableList().getIbVariableDOs().isEmpty());
    }

    @Test
    void mapsUpdateColumnsAsSqlInputsRatherThanOutputs() throws Exception {
        Batch batch = updateBatch();
        ProcessNodeConverter.normalizeUpdateDataProcesses(batch);
        ProcessNodeDataDO node = batch.getLogicalUnit().getProcessNodeDataDOs().get(0);
        MapStorage storage = new MapStorage();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil());
        ProcessNodeConverter converter = new ProcessNodeConverter(mock(RestTemplate.class), processing, storage);
        Step inputValues = new Step();
        RuleSetStep ruleSet = new RuleSetStep();
        ruleSet.setName("node_input_values");
        inputValues.setRuleSet(ruleSet);
        inputValues.setMappings(new ArrayList<>());
        storage.getRuleSetStepMap().put("node_input_values", inputValues);
        storage.getRuleSetIsUpdated().put("node_input_values", true);
        storage.getRuleSetIsActionUpdated().put("node_input_values", true);

        List<Step> steps = converter.addCustomObjectStep(node, new Decision(), (short) 1, "node");

        Step sqlStep = steps.get(steps.size() - 1);
        assertEquals(Step.TypeEnum.CUSTOM_OBJECT, sqlStep.getType());
        Set<String> inputs = sqlStep.getMappings().stream()
            .filter(mapping -> Mapping.DirectionEnum.INPUT.equals(mapping.getDirection()))
            .map(Mapping::getStepTermName).collect(Collectors.toSet());
        Set<String> outputs = sqlStep.getMappings().stream()
            .filter(mapping -> Mapping.DirectionEnum.OUTPUT.equals(mapping.getDirection()))
            .map(Mapping::getStepTermName).collect(Collectors.toSet());
        assertTrue(inputs.containsAll(Set.of("CLIENTID", "CLIENTID_set", "HHID_set")), inputs.toString());
        assertEquals(Set.of("returnCode", "rowCount"), outputs);
    }

    // Customer shapes: a read-data process returning a data grid that is copied to an identifier (DatagridIdentifier)
    // and whose column is referenced directly, and a code process with a list output linked to an identifier and a
    // list input that reads the grid identifier
    private static final String GRID_XML = """
        <Batch><LogicalUnit>
          <ProcessNodeDataDO><NodeId>read</NodeId><NodeName>Get App</NodeName><Process>
            <DatagridIdentifier><Id>DG_App</Id><Name>DG_App</Name></DatagridIdentifier>
            <DatagridName>DG_App</DatagridName><Name>DP_getApplicationWithAVeryLongProcessName_v2</Name>
            <OutputVariableList><IBVariableDO><Name>APP_ID</Name><TypeDescription>double</TypeDescription></IBVariableDO>
            </OutputVariableList><ProcessType>1</ProcessType><ProcessTypeDescription>read data</ProcessTypeDescription>
            <ReturnDatagridFlag>true</ReturnDatagridFlag></Process></ProcessNodeDataDO>
          <ProcessNodeDataDO><NodeId>calc</NodeId><NodeName>Calc</NodeName><Process>
            <InputVariableList><IBVariableDO><Name>source</Name><TypeDescription>double list</TypeDescription>
              <Value><ValueTypeVarInfoDO><VarInfoId>identifiers.DG_App</VarInfoId><VarInfoSource>Identifier</VarInfoSource>
                <VarName>DG_App</VarName></ValueTypeVarInfoDO></Value></IBVariableDO></InputVariableList>
            <Name>Groovy_Calc</Name>
            <OutputVariableList><IBVariableDO><Name>items</Name><TypeDescription>string list</TypeDescription>
              <Identifier><Id>itemList</Id><Name>itemList</Name></Identifier></IBVariableDO></OutputVariableList>
            <ProcessTypeDescription>Java code</ProcessTypeDescription></Process></ProcessNodeDataDO>
        </LogicalUnit></Batch>
        """;

    @Test
    void writesDataGridsAndListsUnderTheNamesLaterNodesReadThemBy() throws Exception {
        Batch batch = (Batch) JAXBContext.newInstance(Batch.class).createUnmarshaller().unmarshal(new StringReader(GRID_XML));
        MapStorage storage = new MapStorage();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil());
        processing.registerNodeOutputs(batch);
        ProcessNodeConverter converter = new ProcessNodeConverter(mock(RestTemplate.class), processing, storage);
        for (String ruleSet : List.of("read_extract_values", "calc_input_values")) {
            Step ruleSetStep = new Step();
            RuleSetStep reference = new RuleSetStep();
            reference.setName(ruleSet);
            ruleSetStep.setRuleSet(reference);
            ruleSetStep.setMappings(new ArrayList<>());
            storage.getRuleSetStepMap().put(ruleSet, ruleSetStep);
            storage.getRuleSetIsUpdated().put(ruleSet, true);
            storage.getRuleSetIsActionUpdated().put(ruleSet, true);
        }
        Decision decision = new Decision();

        List<Step> read = converter.addCustomObjectStep(batch.getLogicalUnit().getProcessNodeDataDOs().get(0), decision, (short) 1, "read");
        List<Step> calc = converter.addCustomObjectStep(batch.getLogicalUnit().getProcessNodeDataDOs().get(1), decision, (short) 2, "calc");

        // The grid is written to the identifier it is copied to; the extraction RuleSet reads it from there
        Step sql = read.get(0);
        assertTrue(sql.getMappings().stream().anyMatch(m -> "IdentifierDG_App".equals(m.getTargetDecisionTermName())
            && Mapping.DirectionEnum.OUTPUT.equals(m.getDirection())), sql.getMappings().toString());
        Step extract = read.get(1);
        assertTrue(extract.getMappings().stream().anyMatch(m -> "IdentifierDG_App".equals(m.getTargetDecisionTermName())
            && Mapping.DirectionEnum.INPUT.equals(m.getDirection())));
        // A column is written under RTDM's output id <NodeName>.<ProcessName><column>
        assertTrue(extract.getMappings().stream().anyMatch(m -> "APP_ID".equals(m.getStepTermName())
            && "GetAppDP_getApplicationWithAVeryLongProcessName_v2APP_ID".equals(m.getTargetDecisionTermName())));
        // A list output linked to an identifier is written there as a data grid; a list input reads what it references
        Step code = calc.get(calc.size() - 1);
        assertTrue(code.getMappings().stream().anyMatch(m -> "items_dgo".equals(m.getStepTermName())
            && "IdentifieritemList".equals(m.getTargetDecisionTermName()) && Mapping.DirectionEnum.OUTPUT.equals(m.getDirection())));
        assertTrue(code.getMappings().stream().anyMatch(m -> "source_dgo".equals(m.getStepTermName())
            && "IdentifierDG_App".equals(m.getTargetDecisionTermName()) && Mapping.DirectionEnum.INPUT.equals(m.getDirection())));
        assertTrue(decision.getSignature().stream().anyMatch(t -> "IdentifieritemList".equals(t.getName())
            && SignatureTerm.DataTypeEnum.DATAGRID.equals(t.getDataType())));
        // With only a list input there is nothing for an _input_values RuleSet to assign, so none is created or used
        assertFalse(CommonProcessing.hasInputValuesRuleSet(batch.getLogicalUnit().getProcessNodeDataDOs().get(1).getProcess()));
        assertEquals(List.of(code), calc);
    }

    @Test
    void assignsOnlyScalarAndCalculatedInputsInTheInputValuesRuleSet() throws Exception {
        // Inputs: an unvalued list, a list from a calculated item, a scalar, and a data grid from an identifier
        String xml = """
            <Batch><LogicalUnit><ProcessNodeDataDO><NodeId>n</NodeId><NodeName>N</NodeName><Process><InputVariableList>
              <IBVariableDO><Name>plain</Name><TypeDescription>string list</TypeDescription></IBVariableDO>
              <IBVariableDO><Name>calc</Name><TypeDescription>date list</TypeDescription>
                <Value><ValueTypeVarInfoDO><VarInfoId>calculatedDataItems.dates</VarInfoId><VarName>dates</VarName>
                </ValueTypeVarInfoDO></Value></IBVariableDO>
              <IBVariableDO><Name>scalar</Name><TypeDescription>double</TypeDescription></IBVariableDO>
              <IBVariableDO><Name>gridFromIdentifier</Name><TypeDescription>data grid</TypeDescription>
                <Value><ValueTypeVarInfoDO><VarInfoId>identifiers.grid</VarInfoId><VarName>grid</VarName>
                </ValueTypeVarInfoDO></Value></IBVariableDO>
            </InputVariableList><Name>P</Name></Process></ProcessNodeDataDO></LogicalUnit></Batch>
            """;
        Batch batch = (Batch) JAXBContext.newInstance(Batch.class).createUnmarshaller().unmarshal(new StringReader(xml));
        List<ProcessNodeDataDO.Process.InputVariableList.IBVariableDO> inputs
            = batch.getLogicalUnit().getProcessNodeDataDOs().get(0).getProcess().getInputVariableList().getIbVariableDOs();

        // A data grid read from an identifier is mapped on the code file step only: declaring it in the RuleSet as well
        // left an unmapped RuleSet term that ID rejects (customer case: "Mapping internationalRoamingPr... is invalid")
        assertEquals(List.of(false, true, true, false), inputs.stream().map(CommonProcessing::isAssignedInInputValues).toList());
    }

    private static CodeFileSignature.Term codeFileTerm(String name, String dataType, String direction) {
        CodeFileSignature.Term term = new CodeFileSignature.Term();
        term.setName(name);
        term.setDataType(dataType);
        term.setDirection(direction);
        return term;
    }

    private static Mapping mapping(String stepTerm, String target, Mapping.DirectionEnum direction) {
        Mapping mapping = new Mapping();
        mapping.setStepTermName(stepTerm);
        mapping.setTargetDecisionTermName(target);
        mapping.setDirection(direction);
        return mapping;
    }

    @Test
    void mapsCodeFileInputsTheStepDoesNotSetAndKeepsEveryExistingMapping() {
        // Customer case: an insert code file reused by name (created from another campaign's version of the process)
        // declared two more columns than this campaign sets, and ID rejected the step ("Mapping WHATSAPP_FLAG is
        // invalid"). Existing mappings, returnCode and rowCount included, must be kept exactly as they are.
        MapStorage storage = new MapStorage();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil());
        ProcessNodeConverter converter = new ProcessNodeConverter(mock(RestTemplate.class), processing, storage);
        List<Mapping> existing = List.of(
            mapping("ARPU_MIN", "ARPU_MIN", Mapping.DirectionEnum.INPUT),
            mapping("NOT_IN_CODE_FILE", "Somewhere", Mapping.DirectionEnum.INPUT),
            mapping("returnCode", "returnCode", Mapping.DirectionEnum.OUTPUT),
            mapping("rowCount", "rowCount", Mapping.DirectionEnum.OUTPUT));
        Step step = new Step();
        step.setMappings(new ArrayList<>(existing));
        List<CodeFileSignature.Term> signature = List.of(
            codeFileTerm("ARPU_MIN", "decimal", "input"),
            codeFileTerm("WHATSAPP_FLAG", "string", "input"),
            codeFileTerm("returnCode", "integer", "output"));
        Decision decision = new Decision();

        converter.alignWithCodeFileSignature(step, signature, decision, "node");

        assertEquals(existing, step.getMappings().subList(0, existing.size()));
        assertEquals(List.of("ARPU_MIN", "NOT_IN_CODE_FILE", "returnCode", "rowCount"),
            existing.stream().map(Mapping::getStepTermName).toList());
        assertEquals(existing.size() + 1, step.getMappings().size());
        Mapping added = step.getMappings().get(existing.size());
        assertEquals(List.of("WHATSAPP_FLAG", "WHATSAPP_FLAG", Mapping.DirectionEnum.INPUT),
            List.of(added.getStepTermName(), added.getTargetDecisionTermName(), added.getDirection()));
        assertTrue(decision.getSignature().stream().anyMatch(term -> "WHATSAPP_FLAG".equals(term.getName())
            && SignatureTerm.DataTypeEnum.STRING.equals(term.getDataType())
            && SignatureTerm.DirectionEnum.NONE.equals(term.getDirection())));
    }

    @Test
    void passesInOutParametersInAndWritesTheirResultWhereLaterNodesReadIt() throws Exception {
        // Customer case: the rules group RF_OfferCheck_v4 has VD11_ThmCheckDecisionCd as input and output ("in_out" in
        // its DS2). The step kept only the input mapping, so the result was never written and the calculation reading
        // <<RF_OfferCheck_v4.VD11_ThmCheckDecisionCd>> saw a blank
        MapStorage storage = new MapStorage();
        CommonProcessing processing = spy(new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil()));
        doNothing().when(processing).callPutRuleSet(any(), any());
        doNothing().when(processing).addActionToRuleSet(any(), any());
        ProcessNodeConverter converter = new ProcessNodeConverter(mock(RestTemplate.class), processing, storage);
        Batch batch = (Batch) JAXBContext.newInstance(Batch.class).createUnmarshaller().unmarshal(new StringReader("""
            <Batch><LogicalUnit><ProcessNodeDataDO><NodeId>rules</NodeId><NodeName>RF_Check</NodeName><Process>
              <Name>RF_Check_v4</Name><ProcessTypeDescription>rules group</ProcessTypeDescription>
              <InputVariableList>
                <IBVariableDO><Name>Decision</Name><TypeDescription>string</TypeDescription>
                  <Value><StringValue>N</StringValue></Value></IBVariableDO>
                <IBVariableDO><Name>Amount</Name><TypeDescription>double</TypeDescription></IBVariableDO>
              </InputVariableList>
              <OutputVariableList>
                <IBVariableDO><Name>Decision</Name><TypeDescription>string</TypeDescription></IBVariableDO>
                <IBVariableDO><Name>Result</Name><TypeDescription>string</TypeDescription></IBVariableDO>
              </OutputVariableList>
            </Process></ProcessNodeDataDO></LogicalUnit></Batch>
            """));
        processing.registerNodeOutputs(batch);
        Step inputValues = new Step();
        RuleSetStep reference = new RuleSetStep();
        reference.setName("RF_Check_rules_input_values");
        inputValues.setRuleSet(reference);
        storage.getRuleSetStepMap().put("RF_Check_rules_input_values", inputValues);
        storage.getRuleSetIsUpdated().put("RF_Check_rules_input_values", false);
        storage.getRuleSetIsActionUpdated().put("RF_Check_rules_input_values", false);

        List<Step> steps = converter.addCustomObjectStep(batch.getLogicalUnit().getProcessNodeDataDOs().get(0),
            new Decision(), (short) 1, "RF_Check_rules");

        assertEquals(List.of(inputValues), steps.subList(0, 1));
        Map<String, String> stepMappings = steps.get(1).getMappings().stream().collect(Collectors.toMap(
            Mapping::getStepTermName, mapping -> mapping.getDirection().getValue() + " " + mapping.getTargetDecisionTermName()));
        assertEquals(Map.of("Decision", "inOut RF_CheckDecision", "Amount", "input Amount", "Result", "output RF_CheckResult"),
            stepMappings);
        ArgumentCaptor<Rule> rule = ArgumentCaptor.forClass(Rule.class);
        verify(processing).addActionToRuleSet(any(), rule.capture());
        assertEquals(List.of("Decision = 'N'", "Decision_out = Decision", "Amount = ."),
            rule.getValue().getActions().stream().map(Action::getExpression).toList());
        assertTrue(inputValues.getMappings().stream().anyMatch(mapping -> "Decision_out".equals(mapping.getStepTermName())
            && "RF_CheckDecision".equals(mapping.getTargetDecisionTermName())
            && Mapping.DirectionEnum.OUTPUT.equals(mapping.getDirection())));
    }

    @ParameterizedTest
    @CsvSource({
        "READ - RET_OPTIMIZATION_MATRIX_VIEW, READ_-_RET_OPTIMIZATION_MATRIX_VIEW_dgo",
        "DP_getApplication_v2, DP_getApplication_v2_dgo",
        "Read v1.2, Read_v1_2_dgo"
    })
    void namesTheSqlOutputGridAsIdDoes(String processName, String expected) {
        // viya-decisions-framework CodeFileHelper.getAdjustedTermName: '.' and whitespace -> '_', plus "_dgo"
        assertEquals(expected, ProcessNodeConverter.sqlOutputGridTerm(processName));
    }

    @Test
    void extractsColumnsUsingTheGridNameTheRuleSetDeclares() throws Exception {
        // The grid's Decision term can be longer than a RuleSet term may be; actions must use the declared (reduced) name
        MapStorage storage = new MapStorage();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, new TreeUtil());
        RestTemplate rest = mock(RestTemplate.class);
        SignatureTerm grid = new SignatureTerm();
        grid.setName("GetApplicationDataDP_getApplicationWithAVeryLongProcessName_v2DG_App");
        SignatureTermExtension column = new SignatureTermExtension();
        column.setName("APP_ID");
        column.setDataType(SignatureTermExtension.DataTypeEnum.DECIMAL);
        grid.setDataGridExtension(List.of(column));
        Step extract = new Step();
        RuleSetStep reference = new RuleSetStep();
        reference.setName("x_extract_values");
        extract.setRuleSet(reference);
        storage.getRuleSetIsUpdated().put("x_extract_values", true);
        storage.getRuleSetIsActionUpdated().put("x_extract_values", false);
        CommonProcessing spiedProcessing = spy(processing);
        ProcessNodeConverter spied = new ProcessNodeConverter(rest, spiedProcessing, storage);
        ArgumentCaptor<Rule> rules = ArgumentCaptor.forClass(Rule.class);
        doNothing().when(spiedProcessing).addActionToRuleSet(any(), rules.capture());

        spied.updateRuleSet(grid, extract, new Decision(), "Get Application Data", "DP_getApplicationWithAVeryLongProcessName_v2");

        String gridTerm = processing.sanitizeAndReduceVariableName(grid.getName());
        assertTrue(gridTerm.length() <= 32, gridTerm);
        assertEquals("APP_ID = DATAGRID_GETDOUBLE(" + gridTerm + ",'APP_ID', 0 )", rules.getValue().getActions().get(0).getExpression());
    }

    @Test
    void failsOnUnknownWhereClauseOperator() throws Exception {
        ProcessNodeDataDO.Process process = updateBatch().getLogicalUnit().getProcessNodeDataDOs().get(0).getProcess();
        process.getInputVariableList().getIbVariableDOs().get(0).setWhereClauseOp((byte) 6);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> sql("buildUpdateSql", process));
        assertTrue(exception.getMessage().contains("WhereClauseOp 6"), exception.getMessage());
    }

    @Test
    void readsValidityWindowsWithTheirWhereClauseOperators() throws Exception {
        // Customer shape: a read-data process selecting the row valid at a date (VALID_FROM_DTTM op 5, VALID_TO_DTTM
        // op 3) and excluding a status code (op 1)
        ProcessNodeDataDO.Process process = new ProcessNodeDataDO.Process();
        process.setName("DP_getValidRow");
        process.setTableName("T");
        process.setInputVariableList(new ProcessNodeDataDO.Process.InputVariableList());
        String[][] criteria = {{"APP_ID", "0", "double"}, {"VALID_FROM_DTTM", "5", "date"}, {"VALID_TO_DTTM", "3", "date"},
            {"STS_CD", "1", "string"}};
        for (String[] criterion : criteria) {
            ProcessNodeDataDO.Process.InputVariableList.IBVariableDO input = new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO();
            input.setName(criterion[0]);
            input.setWhereClauseOp(Byte.parseByte(criterion[1]));
            input.setTypeDescription(criterion[2]);
            process.getInputVariableList().getIbVariableDOs().add(input);
        }
        process.setOutputVariableList(new ProcessNodeDataDO.Process.OutputVariableList());
        ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO output = new ProcessNodeDataDO.Process.OutputVariableList.IBVariableDO();
        output.setName("FLG");
        output.setTypeDescription("double");
        process.getOutputVariableList().getIbVariableDOs().add(output);

        String select = sql("buildSelectSql", process);

        assertTrue(select.contains(" where APP_ID={?:APP_ID:decimal} and VALID_FROM_DTTM<={?:VALID_FROM_DTTM:")
            && select.contains(" and VALID_TO_DTTM>={?:VALID_TO_DTTM:") && select.contains(" and STS_CD<>{?:STS_CD:string"), select);
    }
}
