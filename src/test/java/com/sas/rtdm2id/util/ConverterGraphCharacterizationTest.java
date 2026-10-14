package com.sas.rtdm2id.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.BranchCase;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.Mapping;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.model.rtdm.CampaignDO;
import com.sas.rtdm2id.model.rtdm.CellNodeDataDO;
import com.sas.rtdm2id.model.rtdm.MultiSelectNodeDataDO;
import com.sas.rtdm2id.model.rtdm.ProcessNodeDataDO;
import com.sas.rtdm2id.model.rtdm.ReplyNodeDataDO;
import com.sas.rtdm2id.model.rtdm.SplitNodeDataDO;
import com.sas.rtdm2id.model.rtdm.StartNodeDataDO;
import com.sas.rtdm2id.util.model.RTDM2IDConstants;
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
import com.sas.rtdm2id.util.tree.impl.GenericTree;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Characterization tests for the RTDM graph to ID flow assembly. Each shape is converted end to end
 * (TreeUtil.createTree + Converter.createDecision) with stubbed node converters and the serialized Decision
 * is compared with a golden JSON file captured from the original tree-expanding implementation.
 * Run with -Dconverter.golden.update=true to rewrite the golden files.
 */
class ConverterGraphCharacterizationTest {

    private static final Path GOLDEN_DIR = Paths.get("src", "test", "resources", "converter-golden");
    private static final Pattern UUID_PATTERN =
            Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");

    static Stream<Arguments> shapes() {
        Map<String, Supplier<Graph>> shapes = new LinkedHashMap<>();
        shapes.put("linear", () -> new Graph()
                .start("c1").cell("c1", "p1").process("p1", "pp2").process("pp2", "r1").reply("r1"));
        shapes.put("split", () -> new Graph()
                .start("p0").process("p0", "s1").split("s1", "c", "a", "b", "c")
                .cell("a", "p1").cell("b", "p2").cell("c", "r3")
                .process("p1", "r1").process("p2", "r2").reply("r1").reply("r2").reply("r3"));
        shapes.put("fanIn", () -> new Graph()
                .start("s1").split("s1", "b", "a", "b")
                .cell("a", "pa").cell("b", "m1").process("pa", "m1").process("m1", "r1").reply("r1"));
        shapes.put("nestedSharedMerge", () -> new Graph()
                .start("s1").split("s1", "b", "a", "b")
                .cell("a", "s2").cell("b", "pb").split("s2", "d", "c", "d")
                .cell("c", "pc").cell("d", "ppm").process("pc", "ppm").process("pb", "ppm")
                .process("ppm", "r1").reply("r1"));
        shapes.put("diamonds3", () -> diamonds(3));
        shapes.put("filterWithRemainder", () -> new Graph()
                .start("f1").filter("f1", "e", "t", "e")
                .cell("t", "pt").cell("e", "pe").process("pt", "m").process("pe", "m")
                .process("m", "r").reply("r"));
        shapes.put("filterNoRemainder", () -> new Graph()
                .start("f1").filter("f1", null, "t").cell("t", "pt").process("pt", "r").reply("r"));
        shapes.put("splitDirectToMerge", () -> new Graph()
                .start("s1").split("s1", "c", "a", "b", "c")
                .cell("a", "ppm").cell("b", "ppm").cell("c", "ppm").process("ppm", "r").reply("r"));
        shapes.put("mergeAcrossLevels", () -> new Graph()
                .start("s1").split("s1", "b", "a", "b")
                .cell("a", "pa").cell("b", "s2").process("pa", "m")
                .split("s2", "d", "c", "d").cell("c", "m").cell("d", "pd")
                .process("pd", "r2").process("m", "r1").reply("r1").reply("r2"));
        shapes.put("filterInsideSplit", () -> new Graph()
                .start("s1").split("s1", "b", "a", "b")
                .cell("a", "f1").cell("b", "m").filter("f1", "e", "t", "e")
                .cell("t", "pt").cell("e", "m").process("pt", "m").process("m", "r").reply("r"));

        List<Arguments> arguments = new ArrayList<>();
        shapes.forEach((name, graph) -> {
            arguments.add(Arguments.of(name, true, graph));
            arguments.add(Arguments.of(name, false, graph));
        });
        return arguments.stream();
    }

    @ParameterizedTest(name = "{0} links={1}")
    @MethodSource("shapes")
    void producesSameDecisionAsOriginalTreeExpansion(String shape, boolean useCrossBranchLinks,
                                                     Supplier<Graph> graph) throws Exception {
        Decision decision = convert(graph.get().build(), useCrossBranchLinks);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        String json = normalizeLinkLabels(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(decision));

        Path golden = GOLDEN_DIR.resolve(shape + (useCrossBranchLinks ? "-links" : "-inline") + ".json");
        if (Boolean.getBoolean("converter.golden.update")) {
            Files.createDirectories(GOLDEN_DIR);
            Files.write(golden, json.getBytes(StandardCharsets.UTF_8));
        }
        String expected = new String(Files.readAllBytes(golden), StandardCharsets.UTF_8);
        assertEquals(expected.replace("\r\n", "\n"), json.replace("\r\n", "\n"), shape);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void convertsManySequentialDiamondsInLinearTime(boolean useCrossBranchLinks) {
        // Warm up Mockito's inline mock maker outside the timed section
        convert(diamonds(1).build(), useCrossBranchLinks);
        // The original tree expansion doubled the work per diamond (about 13-20 seconds for 22 diamonds)
        Batch batch = diamonds(24).build();
        Decision decision = assertTimeoutPreemptively(Duration.ofSeconds(3), () -> convert(batch, useCrossBranchLinks));
        assertNotNull(decision.getFlow());
        assertTrue(decision.getFlow().getFlow().size() > 0);
    }

    @org.junit.jupiter.api.Test
    void convertsStartInputVariableHoldingLiteralValue() {
        Batch batch = new Graph().start("r1").reply("r1").build();
        StartNodeDataDO.Event.RequestVars.IBVariableDO variable = new StartNodeDataDO.Event.RequestVars.IBVariableDO();
        variable.setName("channel");
        variable.setTypeDescription("string");
        ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value literal =
                new ProcessNodeDataDO.Process.InputVariableList.IBVariableDO.Value();
        literal.setStringValue("WEB");
        variable.setValue(literal);
        StartNodeDataDO.InputVariables inputVariables = new StartNodeDataDO.InputVariables();
        inputVariables.getIbVariableDOs().add(variable);
        batch.getLogicalUnit().getStartNodeDataDO().setInputVariables(inputVariables);

        Decision decision = convert(batch, true);

        assertTrue(decision.getSignature().stream().anyMatch(term -> term.getName().toLowerCase().contains("channel")),
                decision.getSignature().toString());
    }

    @org.junit.jupiter.api.Test
    void convertsDiagramWhoseStartHasNoOutputs() {
        Batch batch = new Graph().start().build();

        Decision decision = convert(batch, true);

        assertTrue(decision.getFlow().getFlow() == null || decision.getFlow().getFlow().isEmpty());
    }

    static Graph diamonds(int count) {
        Graph graph = new Graph().start("s1");
        for (int i = 1; i <= count; i++) {
            String next = i == count ? "r" : "s" + (i + 1);
            graph.split("s" + i, "b" + i, "a" + i, "b" + i)
                    .cell("a" + i, "pa" + i).cell("b" + i, "pb" + i)
                    .process("pa" + i, "m" + i).process("pb" + i, "m" + i)
                    .process("m" + i, next);
        }
        return graph.reply("r");
    }

    private Decision convert(Batch batch, boolean useCrossBranchLinks) {
        MapStorage mapStorage = new MapStorage();
        mapStorage.setUseCrossBranchLinks(useCrossBranchLinks);
        TreeUtil treeUtil = new TreeUtil();
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), mapStorage, treeUtil);

        ProcessNodeConverter processConverter = mock(ProcessNodeConverter.class);
        when(processConverter.addCustomObjectStep(any(ProcessNodeDataDO.class), any(), any(), any()))
                .thenAnswer(invocation -> {
                    String nodeId = ((ProcessNodeDataDO) invocation.getArgument(0)).getNodeId();
                    List<Step> steps = new LinkedList<>();
                    if (nodeId.startsWith("pp")) {
                        processing.addStep(steps, step(nodeId + "_input", Step.TypeEnum.RULESET), nodeId);
                    }
                    processing.addStep(steps, step(nodeId, Step.TypeEnum.CUSTOM_OBJECT), nodeId);
                    return steps;
                });
        ReplyNodeConverter replyConverter = mock(ReplyNodeConverter.class);
        when(replyConverter.createReply(any(), any())).thenAnswer(invocation -> {
            String nodeId = ((ReplyNodeDataDO) invocation.getArgument(0)).getNodeId();
            List<Step> steps = new LinkedList<>();
            processing.addStep(steps, step(nodeId, Step.TypeEnum.RULESET), nodeId);
            return steps;
        });
        SplitNodeConverter splitConverter = mock(SplitNodeConverter.class);
        when(splitConverter.createBranch(any(), any(), any())).thenAnswer(invocation -> {
            SplitNodeDataDO split = invocation.getArgument(0);
            Step step = step(split.getNodeId(), Step.TypeEnum.BRANCH);
            List<BranchCase> cases = new ArrayList<>();
            for (SplitNodeDataDO.LineItems.SplitNodeLineItemDO line : split.getLineItems().getSplitNodeLineItemDOs()) {
                if (RTDM2IDConstants.REMAINDER_VALUE.equals(line.getValue())) {
                    step.setDefaultCase(processing.createConditionBranch(line.getCellId(), true));
                } else {
                    BranchCase branchCase = new BranchCase();
                    branchCase.setLabel(line.getCellId());
                    cases.add(branchCase);
                }
            }
            step.setBranchCases(cases);
            List<Step> steps = new LinkedList<>();
            processing.addStep(steps, step, split.getNodeId());
            return steps;
        });
        MultiSelectNodeConverter filterConverter = mock(MultiSelectNodeConverter.class);
        when(filterConverter.createConditionByFilter(any(), any())).thenAnswer(invocation -> {
            String nodeId = ((MultiSelectNodeDataDO) invocation.getArgument(0)).getNodeId();
            Step step = step(nodeId, Step.TypeEnum.CONDITION);
            step.setConditionExpression(nodeId + " = 1");
            List<Step> steps = new LinkedList<>();
            processing.addStep(steps, step, nodeId);
            return steps;
        });

        Converter converter = new Converter(processConverter, mock(SubDiagramNodeConverter.class), replyConverter,
                filterConverter, splitConverter, mock(ConfirmContactNodeConverter.class), processing,
                mock(AssignmentNodeConverter.class), mock(CrossTableNodeConverter.class),
                mock(StagedTreatmentConverter.class), treeUtil, mapStorage);

        GenericTree<Short> tree = treeUtil.createTree(batch.getLogicalUnit());
        return converter.createDecision(batch, tree);
    }

    private static Step step(String nodeId, Step.TypeEnum type) {
        Step step = new Step().id(nodeId).type(type);
        step.setName(nodeId);
        Mapping mapping = new Mapping();
        mapping.setStepTermName("out");
        mapping.setTargetDecisionTermName("v_" + nodeId);
        mapping.setDirection(Mapping.DirectionEnum.OUTPUT);
        step.addMappingsItem(mapping);
        return step;
    }

    static String normalizeLinkLabels(String json) {
        Map<String, String> labels = new LinkedHashMap<>();
        Matcher matcher = UUID_PATTERN.matcher(json);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String label = labels.computeIfAbsent(matcher.group(), uuid -> "link-" + (labels.size() + 1));
            matcher.appendReplacement(result, label);
        }
        matcher.appendTail(result);
        return result.toString();
    }

    /**
     * Small builder for RTDM logical units. InputNodes are derived from the declared output edges.
     */
    static final class Graph {
        private final Map<String, Object> nodes = new LinkedHashMap<>();
        private final Map<String, List<String>> outputs = new LinkedHashMap<>();
        private final Batch.LogicalUnit unit = new Batch.LogicalUnit();
        private short nextObjId = 1;

        Graph start(String... outs) {
            StartNodeDataDO start = new StartNodeDataDO();
            unit.setStartNodeDataDO(start);
            return add("start", start, outs);
        }

        Graph cell(String id, String... outs) {
            CellNodeDataDO cell = new CellNodeDataDO();
            unit.getCellNodeDataDOs().add(cell);
            return add(id, cell, outs);
        }

        Graph process(String id, String... outs) {
            ProcessNodeDataDO process = new ProcessNodeDataDO();
            unit.getProcessNodeDataDOs().add(process);
            return add(id, process, outs);
        }

        Graph reply(String id) {
            ReplyNodeDataDO reply = new ReplyNodeDataDO();
            unit.getReplyNodeDataDOs().add(reply);
            return add(id, reply);
        }

        Graph split(String id, String remainder, String... cells) {
            SplitNodeDataDO split = new SplitNodeDataDO();
            split.setLineItems(new SplitNodeDataDO.LineItems());
            for (String cell : cells) {
                SplitNodeDataDO.LineItems.SplitNodeLineItemDO line = new SplitNodeDataDO.LineItems.SplitNodeLineItemDO();
                line.setCellId(cell);
                line.setValue(cell.equals(remainder) ? RTDM2IDConstants.REMAINDER_VALUE : cell);
                split.getLineItems().getSplitNodeLineItemDOs().add(line);
            }
            unit.getSplitNodeDataDOs().add(split);
            return add(id, split, cells);
        }

        Graph filter(String id, String remainder, String... cells) {
            MultiSelectNodeDataDO filter = new MultiSelectNodeDataDO();
            filter.setRemainderOutputCellID(remainder);
            unit.getMultiSelectNodeDataDOs().add(filter);
            return add(id, filter, cells);
        }

        private Graph add(String id, Object node, String... outs) {
            invoke(node, "setObjid", Short.class, nextObjId++);
            invoke(node, "setNodeId", String.class, id);
            invoke(node, "setNodeName", String.class, "Node " + id);
            nodes.put(id, node);
            outputs.put(id, Arrays.asList(outs));
            return this;
        }

        Batch build() {
            Map<String, List<String>> inputs = new LinkedHashMap<>();
            outputs.forEach((source, targets) -> targets.forEach(target ->
                    inputs.computeIfAbsent(target, key -> new ArrayList<>()).add(source)));
            nodes.forEach((id, node) -> {
                setNodes(node, "setOutputNodes", String.join(", ", outputs.get(id)));
                if (!(node instanceof StartNodeDataDO)) {
                    setNodes(node, "setInputNodes", String.join(", ", inputs.getOrDefault(id, List.of())));
                }
            });
            CampaignDO campaign = new CampaignDO();
            campaign.setName("characterization");
            campaign.setId("campaign-id");
            unit.setCampaignDO(campaign);
            Batch batch = new Batch();
            batch.setLogicalUnit(unit);
            return batch;
        }

        private static void invoke(Object target, String name, Class<?> type, Object value) {
            try {
                target.getClass().getMethod(name, type).invoke(target, value);
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException(e);
            }
        }

        private static void setNodes(Object target, String setter, String value) {
            try {
                Method method = Arrays.stream(target.getClass().getMethods())
                        .filter(candidate -> candidate.getName().equals(setter) && candidate.getParameterCount() == 1)
                        .findFirst().orElseThrow();
                Object holder = method.getParameterTypes()[0].getDeclaredConstructor().newInstance();
                Field field = method.getParameterTypes()[0].getDeclaredField("value");
                field.setAccessible(true);
                field.set(holder, value);
                method.invoke(target, holder);
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
