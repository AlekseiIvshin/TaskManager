package com.alekseiivhsin.taskmanager.network.services;

import com.alekseiivhsin.taskmanager.network.responses.PoolMemberListResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created on 07/12/2015.
 */
public interface PoolApiService {

    @GET("/api/pool/member")
    Call<PoolMemberListResponse> getPoolMemberList(@Query("authToken") String authToken);

}
