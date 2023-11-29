/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.exception;

public class OAuthTokenException extends Rtdm2IdException {
    public OAuthTokenException() {
        super("Could not get OAuth token");
    }

    public OAuthTokenException(String message) {
        super(message);
    }

    public OAuthTokenException(Exception e) {
        super("Could not get OAuth token", e);
    }
}
