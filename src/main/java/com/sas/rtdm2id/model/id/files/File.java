/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.files;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class File {
    private String contentDisposition;
    private String description;
    private String documentType;
    private String name;
    private String parentUri;
    private Map<String, String> properties = new HashMap<>();

    @Override
    public String toString() {
        return "File{" +
                "contentDisposition='" + contentDisposition + '\'' +
                ", description='" + description + '\'' +
                ", documentType='" + documentType + '\'' +
                ", name='" + name + '\'' +
                ", parentUri='" + parentUri + '\'' +
                ", properties=" + properties +
                '}';
    }
}
