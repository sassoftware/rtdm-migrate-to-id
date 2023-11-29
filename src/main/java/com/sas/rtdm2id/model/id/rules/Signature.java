/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.id.rules;

import com.sas.rtdm2id.model.id.decision.DecisionStepCodeSignatureTermDataGridExtension;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Signature {
    private Date creationTimeStamp;
    private Date modifiedTimeStamp;
    private String createdBy;
    private String modifiedBy;
    private String id;
    private String name;
    private String dataType;
    private String direction;
    private Integer length;
    private String description;
    private List<DecisionStepCodeSignatureTermDataGridExtension> dataGridExtension;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Signature signature = (Signature) o;
        return Objects.equals(name, signature.name) && Objects.equals(dataType, signature.dataType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dataType);
    }
}
