package com.sas.rtdm2id.model.rtdm.extension;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "outputNodeName"
})
public class SystemExtraValues {

    @XmlElement(name = "OutputNodeName", required = true)
    protected String outputNodeName;

    public String getOutputNodeName() {
        return outputNodeName;
    }

    public void setOutputNodeName(String outputNodeName) {
        this.outputNodeName = outputNodeName;
    }
}
