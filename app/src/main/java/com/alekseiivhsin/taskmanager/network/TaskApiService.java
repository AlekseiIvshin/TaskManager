package com.alekseiivhsin.taskmanager.network;

import com.alekseiivhsin.taskmanager.models.Task;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created on 07/12/2015.
 */
public interface TaskApiService {

    @GET("/api/task")
    Call<List<Task>> getTasksList(@Query("authToken") String authToken);
}
