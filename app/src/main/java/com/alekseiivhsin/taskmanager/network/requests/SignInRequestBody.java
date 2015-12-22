package com.alekseiivhsin.taskmanager.network.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 21/12/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignInRequestBody {

    @JsonProperty("user_name")
    public final String username;
    @JsonProperty("password")
    public final String password;
    @JsonProperty("account_type")
    public final String accountType;

    public SignInRequestBody(String username, String password, String accountType) {
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }
}
