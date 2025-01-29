/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.files;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DecisionCodeFile {
    private String fileUri;
    private String type;

    @Override
    public String toString() {
        return "DecisionCodeFile{" +
                "fileUri='" + fileUri + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
