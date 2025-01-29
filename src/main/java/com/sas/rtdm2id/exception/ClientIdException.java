/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.exception;

public class ClientIdException extends RuntimeException {
    public ClientIdException() {
        super("Client ID cannot be created");
    }

    public ClientIdException(Exception e) {
        super("Client ID cannot be created", e);
    }
}
