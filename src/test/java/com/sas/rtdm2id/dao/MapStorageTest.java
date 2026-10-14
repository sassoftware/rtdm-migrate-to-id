package com.sas.rtdm2id.dao;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapStorageTest {

    @Test
    void clearsRequestContextWithCollections() {
        MapStorage storage = new MapStorage();
        storage.setAccessToken("token");
        storage.setBaseIp("viya.example");
        storage.setProtocol("https");
        storage.setParentFolderUri("/folders/folders/target");
        storage.getNodeIdStepMap().put("node", Collections.emptyList());

        storage.clearListsAndMaps();

        assertNull(storage.getAccessToken());
        assertNull(storage.getBaseIp());
        assertNull(storage.getProtocol());
        assertNull(storage.getParentFolderUri());
        assertTrue(storage.getNodeIdStepMap().isEmpty());
    }
}
