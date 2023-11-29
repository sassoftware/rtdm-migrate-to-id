/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.rules;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rule {
    private Date creationTimeStamp;
    private Date modifiedTimeStamp;
    private String createdBy;
    private String modifiedBy;
    private int version;
    private String id;
    private String name;
    private String description;
    private Boolean ruleFiredTrackingEnabled;
    private String conditional;
    private List<Condition> conditions;
    private List<Action> actions;
    private String status;
}
