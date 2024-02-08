/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.controller;

import com.sas.rtdm2id.model.dto.Meta;
import com.sas.rtdm2id.model.dto.Rtdm2IdResponse;
import com.sas.rtdm2id.model.dto.rtdm.model.ClientIdResponse;
import com.sas.rtdm2id.model.rtdm.Batch;
import com.sas.rtdm2id.service.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/rtdm2id")
@Api(tags = "Application methods")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping(value = "create-diagram", consumes = MediaType.APPLICATION_XML_VALUE)
    @ApiOperation(value = "Create diagram",
            notes = "Create diagram inside ID")
    public ResponseEntity<Rtdm2IdResponse<String>> createDiagram(
            @ApiParam(value = "Base ip of Viya server", required = true) @RequestParam String baseIp,
            @ApiParam(value = "RTDM XML", required = true) @RequestBody Batch xml,
            @ApiParam(value = "login") @RequestParam String login,
            @ApiParam(value = "password") @RequestParam String password,
            @RequestParam(value = "parentFolderUri", defaultValue = "/folders/folders/@myFolder", required = false) String parentFolderUri,
            @RequestParam(value = "protocol", defaultValue = "http", required = false) String protocol,
            @RequestParam(value = "useCrossBranchLinks", defaultValue = "true", required = false) String useCrossBranchLinks) {
        return new ResponseEntity<>(new Rtdm2IdResponse<>(new Meta(0, "OK"), applicationService.createDiagram(baseIp, xml, login, password, protocol, parentFolderUri, useCrossBranchLinks))
                , HttpStatus.CREATED);
    }

    @PostMapping("register-client")
    public ResponseEntity<Rtdm2IdResponse<ClientIdResponse>> registerClient(@ApiParam(value = "Base IP of Viya server", required = true)
                                                                            @RequestParam String baseIp,
                                                                            @RequestParam(value = "protocol", defaultValue = "http", required = false) String protocol,
                                                                            @ApiParam(value = "Consul token got from Viya server", required = true)
                                                                            @RequestParam String consulToken,
                                                                            @ApiParam(value = "Id of new user", required = true)
                                                                            @RequestParam String clientId,
                                                                            @ApiParam(value = "Secret of new user", required = true)
                                                                            @RequestParam String clientSecret) {
        return new ResponseEntity<>(new Rtdm2IdResponse<>(new Meta(0, "OK"), applicationService.registerClient(baseIp, consulToken, clientId, clientSecret,protocol)),
                HttpStatus.CREATED);
    }

}
