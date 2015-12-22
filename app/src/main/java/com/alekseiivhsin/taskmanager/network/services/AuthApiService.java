package com.alekseiivhsin.taskmanager.network.services;

import com.alekseiivhsin.taskmanager.network.requests.SignInRequestBody;
import com.alekseiivhsin.taskmanager.network.responses.SignInResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created on 07/12/2015.
 */
public interface AuthApiService {

    @POST("/api/signin")
    Call<SignInResponse> signIn(@Body SignInRequestBody requestBody);
}
