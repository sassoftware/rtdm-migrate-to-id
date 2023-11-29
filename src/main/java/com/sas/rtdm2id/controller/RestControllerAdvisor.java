/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.controller;

import com.sas.rtdm2id.exception.Rtdm2IdException;
import com.sas.rtdm2id.model.dto.Rtdm2IdResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestControllerAdvisor {

    @ExceptionHandler(value = {Rtdm2IdException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Rtdm2IdResponse<Void> handlePetHelperException(RuntimeException ex) {
        return Rtdm2IdResponse.error(ex.getMessage(), null);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected Rtdm2IdResponse<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return Rtdm2IdResponse.error(errors.toString(), null);
    }
}
