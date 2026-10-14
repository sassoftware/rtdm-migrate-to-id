/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.rtdm.extension;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlMixed;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * A split value (interval bound or discrete value) that is either a literal, e.g.
 * {@code <LowerBound StoredType="Double">2.0</LowerBound>}, or a reference to another variable, written by RTDM as a
 * {@code VarInfoBriefDO}: {@code <LowerBound ConcreteClass="...VarInfoBriefDO"><Id>events.pubDouble</Id>
 * <Source>Request</Source><Name>pubDouble</Name>...</LowerBound>}. Mixed content keeps both forms.
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ReferenceableValue {
    @Getter
    @Setter
    @XmlAttribute(name = "StoredType")
    private String storedType;

    @XmlMixed
    @XmlAnyElement
    private List<Object> content = new ArrayList<>();

    /** The literal value; null when the element had no content. */
    public String getText() {
        if (content.isEmpty()) {
            return null;
        }
        StringBuilder text = new StringBuilder();
        content.stream().filter(String.class::isInstance).forEach(text::append);
        return text.toString();
    }

    public void setText(String text) {
        content = new ArrayList<>();
        if (text != null) {
            content.add(text);
        }
    }

    /** The referenced variable's id, e.g. {@code events.pubDouble}, or null for a literal value. */
    public String getReferenceId() {
        return childText("Id");
    }

    /** The referenced variable's source, e.g. {@code Request} or {@code Global}. */
    public String getReferenceSource() {
        return childText("Source");
    }

    /** The referenced variable's name, e.g. {@code pubDouble}. */
    public String getReferenceName() {
        return childText("Name");
    }

    /** The referenced variable's type, e.g. {@code Numeric} or {@code Char}. */
    public String getReferenceType() {
        return childText("VarType");
    }

    private String childText(String name) {
        for (Object part : content) {
            if (part instanceof Element element && name.equals(element.getLocalName() != null ? element.getLocalName() : element.getTagName())) {
                String text = element.getTextContent();
                return text == null || text.isBlank() ? null : text.trim();
            }
        }
        return null;
    }
}
