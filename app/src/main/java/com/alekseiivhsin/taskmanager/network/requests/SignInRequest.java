package com.alekseiivhsin.taskmanager.network.requests;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.network.responses.SignInResponse;
import com.alekseiivhsin.taskmanager.network.services.AuthApiService;
import com.octo.android.robospice.request.SpiceRequest;

import javax.inject.Inject;

/**
 * Created on 21/12/2015.
 */
public class SignInRequest extends SpiceRequest<SignInResponse> {

    @Inject
    AuthApiService authApiService;

    public final String username;
    public final String password;
    public final String accountType;

    public SignInRequest(String username, String password, String accountType) {
        super(SignInResponse.class);
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    @Override
    public SignInResponse loadDataFromNetwork() throws Exception {
        App.getObjectGraphInstance().inject(this);
        return authApiService.signIn(new SignInRequestBody(username, password, accountType)).execute().body();
    }
}
