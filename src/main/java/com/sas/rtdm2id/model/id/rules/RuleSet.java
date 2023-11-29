/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RuleSet {
    private Date creationTimeStamp;
    private Date modifiedTimeStamp;
    private String createdBy;
    private String description;
    private String modifiedBy;
    private Integer version;
    private String id;
    private String name;
    private String ruleSetType;
    private Integer majorRevision;
    private Integer minorRevision;
    private String status;
    private String locked;
    private List<Signature> signature;
}
