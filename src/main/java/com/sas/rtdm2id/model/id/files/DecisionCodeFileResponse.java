/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.files;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecisionCodeFileResponse {
    private String createdBy;
    private String modifiedBy;
    private String id;
    private String name;
    private String description;
    private String type;
    private String fileUri;
    private String status;
    private int version;
}
