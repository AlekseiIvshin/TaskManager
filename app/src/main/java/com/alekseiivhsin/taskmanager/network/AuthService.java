package com.alekseiivhsin.taskmanager.network;

import com.alekseiivhsin.taskmanager.network.models.LoginResponse;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created on 07/12/2015.
 */
public interface AuthService {

    @POST("/api/login")
    LoginResponse login(@Query("username") String username,
                      @Query("password") String password,
                      @Query("accountType") String accountType);
}
