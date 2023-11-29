/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rtdm2IdResponse<T> {
    private static final Integer OK_CODE = 0;
    private static final String OK_MSG = "OK";

    private static final Integer ERROR_CODE = 1;
    private static final String ERROR_MSG = "ERROR";

    private Meta meta;

    private T dataBlock;

    public static Rtdm2IdResponse<Void> ok() {
        return new Rtdm2IdResponse<>(new Meta(OK_CODE, OK_MSG), null);
    }

    public static <T> Rtdm2IdResponse<T> ok(T data) {
        return new Rtdm2IdResponse<>(new Meta(OK_CODE, OK_MSG), data);
    }

    public static Rtdm2IdResponse<Void> error() {
        return new Rtdm2IdResponse<>(new Meta(ERROR_CODE, ERROR_MSG), null);
    }

    public static <T> Rtdm2IdResponse<T> error(String message, T data) {
        return new Rtdm2IdResponse<>(new Meta(ERROR_CODE, message), data);
    }
}


