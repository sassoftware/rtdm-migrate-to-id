package com.sas.rtdm2id.model.rtdm.extension;

import lombok.Getter;
import lombok.Setter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "outputNodeName"
})
@Getter
@Setter
public class SystemExtraValues {

    @XmlElement(name = "OutputNodeName", required = true)
    protected String outputNodeName;

}
