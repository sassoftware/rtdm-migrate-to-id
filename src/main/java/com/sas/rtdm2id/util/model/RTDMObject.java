/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.util.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RTDMObject {
    private String objectType;
    private Object object;

    @Override
    public String toString() {
        return "RTDMObject{" +
                "objectType='" + objectType + '\'' +
                '}';
    }
}
