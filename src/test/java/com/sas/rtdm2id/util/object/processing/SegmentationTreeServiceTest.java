package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.CustomObjectStep;
import com.sas.rtdm2id.model.id.segmentation.SegmentationTree;
import com.sas.rtdm2id.model.id.segmentation.SegmentationTreeCollection;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import org.mockito.ArgumentCaptor;

class SegmentationTreeServiceTest {

    @Test
    void reusesExistingTreeRevisionWithoutCreatingAnotherTree() throws Exception {
        RestTemplate restTemplate = mock(RestTemplate.class);
        MapStorage mapStorage = configuredMapStorage();
        SegmentationTreeConverter converter = mock(SegmentationTreeConverter.class);
        SegmentationTree tree = tree("CrossTable_1");
        SegmentationTree existingTree = tree("CrossTable_1");
        existingTree.setSignature(Collections.singletonList(signatureTerm("PersistedInput", "decimal", "input")));
        existingTree.setRevisionUri("/decisions/segmentationTrees/tree-1/revisions/revision-1");
        SegmentationTreeCollection collection = new SegmentationTreeCollection();
        collection.setCount(1);
        collection.setItems(Collections.singletonList(existingTree));
        CrossTableNodeDataDO node = node((short) 1);

        when(converter.convert(node)).thenReturn(tree);
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class),
                eq(SegmentationTreeCollection.class))).thenReturn(ResponseEntity.ok(collection));

        new SegmentationTreeService(restTemplate, mapStorage, converter)
                .createOrReuse(Collections.singletonList(node));

        verify(restTemplate, never()).postForEntity(any(URI.class), any(HttpEntity.class),
                eq(SegmentationTree.class));
        CustomObjectStep reference = mapStorage.getCustomObjectStepHashMap().get((short) 1);
        assertEquals("/decisions/segmentationTrees/tree-1/revisions/revision-1", reference.getUri());
        assertEquals("segmentationTree", reference.getType());
        assertTrue(reference.getRestDNT());
        assertEquals(existingTree.getSignature(), mapStorage.getSegmentationTreeSignatureMap().get((short) 1));
    }

    @Test
    void createsMissingTreeWithTreeMediaTypeAndParentFolder() throws Exception {
        RestTemplate restTemplate = mock(RestTemplate.class);
        MapStorage mapStorage = configuredMapStorage();
        SegmentationTreeConverter converter = mock(SegmentationTreeConverter.class);
        SegmentationTree tree = tree("CrossTable_1");
        tree.setSignature(Collections.singletonList(signatureTerm("ConvertedInput", "integer", "input")));
        tree.getSignature().get(0).setSourceVariableName("SourceDate");
        tree.getSignature().get(0).setSourceDataType("datetime");
        tree.getSignature().get(0).setDerivationExpression("day(datepart(SourceDate))");
        SegmentationTree createdTree = tree("CrossTable_1");
        createdTree.setSignature(Collections.singletonList(signatureTerm("ConvertedInput", "integer", "input")));
        createdTree.setId("tree-1");
        createdTree.setRevisionId("revision-1");
        SegmentationTreeCollection collection = new SegmentationTreeCollection();
        collection.setCount(0);
        collection.setItems(new ArrayList<SegmentationTree>());
        CrossTableNodeDataDO node = node((short) 1);

        when(converter.convert(node)).thenReturn(tree);
        when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class),
                eq(SegmentationTreeCollection.class))).thenReturn(ResponseEntity.ok(collection));
        when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), eq(SegmentationTree.class)))
                .thenReturn(new ResponseEntity<>(createdTree, HttpStatus.CREATED));

        new SegmentationTreeService(restTemplate, mapStorage, converter)
                .createOrReuse(Collections.singletonList(node));

        ArgumentCaptor<URI> uriCaptor = ArgumentCaptor.forClass(URI.class);
        ArgumentCaptor<HttpEntity> requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate).postForEntity(uriCaptor.capture(), requestCaptor.capture(), eq(SegmentationTree.class));
        assertTrue(uriCaptor.getValue().toString().contains("parentFolderUri=/folders/folders/target"));
        assertEquals(MediaType.parseMediaType("application/vnd.sas.decision.segmentation.tree+json"),
                requestCaptor.getValue().getHeaders().getContentType());
        assertEquals(tree, requestCaptor.getValue().getBody());
        assertEquals("/decisions/segmentationTrees/tree-1/revisions/revision-1",
                mapStorage.getCustomObjectStepHashMap().get((short) 1).getUri());
        SegmentationTree.SignatureTerm stored = mapStorage.getSegmentationTreeSignatureMap().get((short) 1).get(0);
        assertEquals("ConvertedInput", stored.getName());
        assertEquals("SourceDate", stored.getSourceVariableName());
        assertEquals("datetime", stored.getSourceDataType());
        assertEquals("day(datepart(SourceDate))", stored.getDerivationExpression());
    }

    private MapStorage configuredMapStorage() {
        MapStorage mapStorage = new MapStorage();
        mapStorage.setBaseIp("viya.example");
        mapStorage.setProtocol("https");
        mapStorage.setAccessToken("token");
        mapStorage.setParentFolderUri("/folders/folders/target");
        return mapStorage;
    }

    private SegmentationTree tree(String name) {
        SegmentationTree tree = new SegmentationTree();
        tree.setName(name);
        return tree;
    }

        private SegmentationTree.SignatureTerm signatureTerm(String name, String dataType, String direction) {
                SegmentationTree.SignatureTerm term = new SegmentationTree.SignatureTerm();
                term.setName(name);
                term.setDataType(dataType);
                term.setDirection(direction);
                return term;
        }

    private CrossTableNodeDataDO node(short objId) {
        CrossTableNodeDataDO node = new CrossTableNodeDataDO();
        node.setObjid(objId);
        return node;
    }
}