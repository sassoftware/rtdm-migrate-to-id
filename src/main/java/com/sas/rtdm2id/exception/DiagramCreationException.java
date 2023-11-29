/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.exception;

public class DiagramCreationException extends Rtdm2IdException {
    public DiagramCreationException(String message, Exception exception) {
        super(message, exception);
    }
}
