package com.sas.rtdm2id.model.rtdm.extension;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "filterNodeDOs",
        "logicNodeDOs"
})
@Getter
@Setter
public class Children implements Serializable {

    private final static long serialVersionUID = -1L;

    @XmlElement(name = "FilterNodeDO")
    private List<FilterNodeDO> filterNodeDOs = new ArrayList<>();

    @XmlElement(name = "LogicNodeDO")
    private List<LogicNodeDO> logicNodeDOs = new ArrayList<>();

}
