package com.alekseiivhsin.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 07/12/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResult {
    @JsonProperty("auth_token")
    public String authToken;
    @JsonProperty("refresh_token")
    public String refreshToken;
    @JsonProperty("user_type")
    public int userRights;
    @JsonProperty("expires_in")
    public int expiresIn;
}
