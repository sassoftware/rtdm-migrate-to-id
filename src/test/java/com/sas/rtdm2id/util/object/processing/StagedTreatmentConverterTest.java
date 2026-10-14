package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.id.decision.CustomObjectStep;
import com.sas.rtdm2id.model.id.decision.Decision;
import com.sas.rtdm2id.model.id.decision.Step;
import com.sas.rtdm2id.util.tree.impl.TreeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

class StagedTreatmentConverterTest {

    @Test
    void stagedTreatmentPlaceholderWithFanInIsCrossBranchLinked() {
        MapStorage storage = new MapStorage();
        storage.setUseCrossBranchLinks(true);
        TreeUtil treeUtil = new TreeUtil();
        treeUtil.getObjIdToNodeIdMap().put((short) 3, "staged");
        treeUtil.getNodeIdInputNodesMap().put("staged", "left, right");
        CustomObjectStep codeFile = new CustomObjectStep();
        storage.getCustomObjectStepHashMap().put((short) 3, codeFile);
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, treeUtil);

        List<Step> steps = new StagedTreatmentConverter(storage, processing, treeUtil)
            .addCustomObjectStep(null, new Decision(), (short) 3, "Staged");

        assertEquals(1, steps.size());
        assertEquals(codeFile, steps.get(0).getCustomObject());
        assertNotNull(steps.get(0).getLinkLabel());
        assertEquals(steps.get(0).getLinkLabel(), storage.getNodeIdLinkLabelMap().get("staged"));
    }

    @Test
    void stagedTreatmentWithSingleInputHasNoLinkLabel() {
        MapStorage storage = new MapStorage();
        storage.setUseCrossBranchLinks(true);
        TreeUtil treeUtil = new TreeUtil();
        treeUtil.getObjIdToNodeIdMap().put((short) 3, "staged");
        treeUtil.getNodeIdInputNodesMap().put("staged", "left");
        CommonProcessing processing = new CommonProcessing(mock(RestTemplate.class), storage, treeUtil);

        List<Step> steps = new StagedTreatmentConverter(storage, processing, treeUtil)
            .addCustomObjectStep(null, new Decision(), (short) 3, "Staged");

        assertEquals(1, steps.size());
        assertNull(steps.get(0).getLinkLabel());
    }
}
