package com.alekseiivhsin.taskmanager.network.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 07/12/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignInResponse {
    @JsonProperty("auth_token")
    public String authToken;
    @JsonProperty("user_rights")
    public int userRights;
}
