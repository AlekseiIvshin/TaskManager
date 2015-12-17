package com.alekseiivhsin.taskmanager.network;

import com.alekseiivhsin.taskmanager.model.LoginResponse;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created on 07/12/2015.
 */
public interface AuthApiService {

    @POST("/api/login")
    LoginResponse login(@Query("username") String username,
                      @Query("password") String password,
                      @Query("accountType") String accountType);
}
