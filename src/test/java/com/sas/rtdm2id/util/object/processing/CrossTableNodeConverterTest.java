package com.sas.rtdm2id.util.object.processing;

import com.sas.rtdm2id.dao.MapStorage;
import com.sas.rtdm2id.model.rtdm.CrossTableNodeDataDO;
import com.sas.rtdm2id.model.rtdm.extension.IBVariableDO;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CrossTableNodeConverterTest {

    @Test
    void placeholderIncludesLikeSettingForColumnAndRowCriteria() throws Exception {
        MapStorage mapStorage = mock(MapStorage.class);
        CommonProcessing commonProcessing = mock(CommonProcessing.class);
        CrossTableNodeDataDO crossTableNodeDataDO = unmarshalCrossTableNode();
        when(commonProcessing.makeCrossTableNodeName(crossTableNodeDataDO)).thenReturn("cross_table");

        CrossTableNodeDataDO.OutputVariable outputVariable = crossTableNodeDataDO.getOutputVariable();
        IBVariableDO.Identifier identifier = outputVariable.getIdentifier();
        assertTrue(identifier != null);
        assertTrue("G_IN_STRING".equals(identifier.getName()));

        CrossTableNodeConverter converter = new CrossTableNodeConverter(mapStorage, commonProcessing);
        String ds2Code = converter.createCrossTablePlaceholderNode(crossTableNodeDataDO)
                .getProcess()
                .getDs2code();

        assertTrue(ds2Code.contains("Level:     Nominal"));
        assertTrue(ds2Code.contains("Default Value Is Missing: false"));
        assertTrue(ds2Code.contains("Identifier: G_IN_STRING"));

        int columnCriteriaStart = ds2Code.indexOf("Column Criteria:");
        int rowCriteriaStart = ds2Code.indexOf("Row Criteria:");
        String columnCriteriaCode = ds2Code.substring(columnCriteriaStart, rowCriteriaStart);
        String rowCriteriaCode = ds2Code.substring(rowCriteriaStart);

        assertTrue(columnCriteriaCode.contains("Like:      true"));
        assertTrue(rowCriteriaCode.contains("Like:      false"));
    }

    private CrossTableNodeDataDO unmarshalCrossTableNode() throws Exception {
        String xml = "<CrossTableNodeDataDO>"
                + "<ColumnCriteria><Like>true</Like><LineItems/></ColumnCriteria>"
                + "<CrossTable/>"
            + "<OutputVariable>"
            + "<Identifier><Id>G_IN_STRING</Id><Name>G_IN_STRING</Name></Identifier>"
            + "<DefaultValueIsMissing>false</DefaultValueIsMissing>"
            + "<Level>Nominal</Level><Name>OutputCharacterNominal</Name>"
            + "<PossibleValues/><Type>1</Type>"
            + "</OutputVariable>"
                + "<RowCriteria><Like>false</Like><LineItems/></RowCriteria>"
                + "</CrossTableNodeDataDO>";
        JAXBContext context = JAXBContext.newInstance(CrossTableNodeDataDO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        JAXBElement<CrossTableNodeDataDO> result = unmarshaller.unmarshal(
                new StreamSource(new StringReader(xml)), CrossTableNodeDataDO.class);
        return result.getValue();
    }
}