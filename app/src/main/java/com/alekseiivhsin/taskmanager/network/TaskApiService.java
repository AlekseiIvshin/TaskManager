package com.alekseiivhsin.taskmanager.network;

import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.network.model.TaskListResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created on 07/12/2015.
 */
public interface TaskApiService {

    @GET("/api/task")
    Call<TaskListResponse> getTaskList(@Query("authToken") String authToken);

    @GET("/api/task/{id}")
    Call<Task> getTask(@Path("id") String taskId, @Query("authToken") String authToken);
}
