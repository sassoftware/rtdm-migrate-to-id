/*
Copyright © 2023, SAS Institute Inc., Cary, NC, USA.  All Rights Reserved.
SPDX-License-Identifier: Apache-2.0
*/
package com.sas.rtdm2id.model.dto.rtdm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClientIdResponse {
    @JsonProperty("scope")
    private List<String> scope;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("resource_ids")
    private List<String> resourceIds;
    @JsonProperty("authorized_grant_types")
    private List<String> authorizedGrantTypes;
    @JsonProperty("autoapprove")
    private List<String> autoapprove;
    @JsonProperty("authorities")
    private List<String> authorities;
    @JsonProperty("last_modified")
    private Long lastModified;
    @JsonProperty("required_user_groups")
    private List<String> requiredUserGroups;
}
