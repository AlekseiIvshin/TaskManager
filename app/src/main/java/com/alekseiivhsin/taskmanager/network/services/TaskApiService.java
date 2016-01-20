package com.alekseiivhsin.taskmanager.network.services;

import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.network.responses.PoolTaskListResponse;
import com.alekseiivhsin.taskmanager.network.responses.UserTaskListResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created on 07/12/2015.
 */
public interface TaskApiService {

    @GET("/api/task/pool")
    Call<PoolTaskListResponse> getPoolTaskList(@Query("authToken") String authToken);

    @GET("/api/task/user")
    Call<UserTaskListResponse> getUserTaskList(@Query("authToken") String authToken);

    @GET("/api/task/{id}")
    Call<Task> getTask(@Path("id") int taskId, @Query("authToken") String authToken);
}
