/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.exception;

public class Rtdm2IdException extends RuntimeException {
    public Rtdm2IdException(String message) {
        super(message);
    }

    public Rtdm2IdException(String message, Throwable cause) {
        super(message, cause);
    }
}
