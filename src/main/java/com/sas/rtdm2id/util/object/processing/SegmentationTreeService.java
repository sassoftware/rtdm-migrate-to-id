/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.CustomObjectStep;
import com.sas.rtdm2id.model.id.decision.Link;
import com.sas.rtdm2id.model.id.segmentation.SegmentationTree;
import com.sas.rtdm2id.model.id.segmentation.SegmentationTreeCollection;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO;
import com.sas.rtdm2id.util.ViyaApi;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SegmentationTreeService {
    private static final String SEGMENTATION_TREE_PATH = "/decisions/segmentationTrees";
    private static final String SEGMENTATION_TREE_TYPE = "segmentationTree";

    private final RestTemplate restTemplate;
    private final MapStorage mapStorage;
    private final SegmentationTreeConverter treeConverter;

    public SegmentationTreeService(RestTemplate restTemplate, MapStorage mapStorage,
            SegmentationTreeConverter treeConverter) {
        this.restTemplate = restTemplate;
        this.mapStorage = mapStorage;
        this.treeConverter = treeConverter;
    }

    public void createOrReuse(List<CrossTableNodeDataDO> crossTableNodes) throws URISyntaxException {
        if (crossTableNodes == null) {
            return;
        }
        for (CrossTableNodeDataDO crossTableNode : crossTableNodes) {
            SegmentationTree tree = treeConverter.convert(crossTableNode);
            SegmentationTree existingTree = findTree(tree.getName());
            SegmentationTree persistedTree = existingTree == null ? createTree(tree) : existingTree;
            List<SegmentationTree.SignatureTerm> signature = persistedTree.getSignature();
            if (signature == null || signature.isEmpty()) {
                signature = tree.getSignature();
            }
            if (signature == null || signature.isEmpty()) {
                throw new IllegalStateException("Segmentation Tree signature is missing for " + tree.getName());
            }
            for (SegmentationTree.SignatureTerm term : signature) {
                for (SegmentationTree.SignatureTerm generated : tree.getSignature() == null
                        ? java.util.Collections.<SegmentationTree.SignatureTerm>emptyList() : tree.getSignature()) {
                    if (generated.getName().equals(term.getName())) {
                        term.setSourceVariableName(generated.getSourceVariableName());
                        term.setSourceDataType(generated.getSourceDataType());
                        term.setDerivationExpression(generated.getDerivationExpression());
                    }
                }
            }
            mapStorage.getSegmentationTreeSignatureMap().put(crossTableNode.getObjid(), new ArrayList<>(signature));
            mapStorage.getCustomObjectStepHashMap().put(crossTableNode.getObjid(),
                    createCustomObjectStep(tree.getName(), revisionUri(persistedTree)));
        }
    }

    private SegmentationTree findTree(String name) throws URISyntaxException {
        ResponseEntity<SegmentationTreeCollection> response = restTemplate.exchange(
                ViyaApi.createUriWithParams(mapStorage.getBaseIp(), SEGMENTATION_TREE_PATH,
                        "name", name, mapStorage.getProtocol()),
                HttpMethod.GET,
                ViyaApi.createGetSegmentationTrees(mapStorage.getAccessToken()),
                SegmentationTreeCollection.class);
        if (response == null || response.getBody() == null || response.getBody().getItems() == null) {
            return null;
        }
        for (SegmentationTree tree : response.getBody().getItems()) {
            if (tree != null && name.equals(tree.getName())) {
                return tree;
            }
        }
        return null;
    }

    private SegmentationTree createTree(SegmentationTree tree) throws URISyntaxException {
        String path = SEGMENTATION_TREE_PATH;
        if (mapStorage.getParentFolderUri() != null) {
            path += "?parentFolderUri=" + mapStorage.getParentFolderUri();
        }
        ResponseEntity<SegmentationTree> response = restTemplate.postForEntity(
                ViyaApi.createUri(mapStorage.getBaseIp(), path, mapStorage.getProtocol()),
                ViyaApi.createSegmentationTree(tree, mapStorage.getAccessToken()),
                SegmentationTree.class);
        if (response == null || response.getBody() == null) {
            throw new IllegalStateException("Viya did not return the created Segmentation Tree");
        }
        return response.getBody();
    }

    private CustomObjectStep createCustomObjectStep(String name, String revisionUri) {
        if (revisionUri == null || revisionUri.isEmpty()) {
            throw new IllegalStateException("Viya did not return a Segmentation Tree revision URI for " + name);
        }
        CustomObjectStep customObjectStep = new CustomObjectStep();
        customObjectStep.setName(name);
        customObjectStep.setUri(revisionUri);
        customObjectStep.setType(SEGMENTATION_TREE_TYPE);
        customObjectStep.setRestDNT(true);
        return customObjectStep;
    }

    private String revisionUri(SegmentationTree tree) {
        if (tree.getRevisionUri() != null && !tree.getRevisionUri().isEmpty()) {
            return tree.getRevisionUri();
        }
        if (tree.getId() != null && tree.getRevisionId() != null) {
            return SEGMENTATION_TREE_PATH + "/" + tree.getId() + "/revisions/" + tree.getRevisionId();
        }
        if (tree.getLinks() != null) {
            for (Link link : tree.getLinks()) {
                if (link == null) {
                    continue;
                }
                String uri = link.getUri() != null ? link.getUri() : link.getHref();
                if (uri != null && uri.contains("/revisions/")) {
                    return uri;
                }
            }
        }
        return null;
    }
}