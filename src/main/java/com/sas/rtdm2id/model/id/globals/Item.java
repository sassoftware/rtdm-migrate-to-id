/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.globals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private Date creationTimeStamp;
    private Date modifiedTimeStamp;
    private String createdBy;
    private String modifiedBy;
    private String id;
    private String activeVersionId;
    private int majorRevision;
    private int minorRevision;
    private String name;
    private String dataType;
    private String defaultValue;
    private boolean hidden;
    private String status;
    private boolean locked;
}
