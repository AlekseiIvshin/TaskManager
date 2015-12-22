package com.alekseiivhsin.taskmanager.network.requests;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.network.responses.TaskListResponse;
import com.alekseiivhsin.taskmanager.network.services.TaskApiService;
import com.octo.android.robospice.request.SpiceRequest;

import javax.inject.Inject;

/**
 * Created on 21/12/2015.
 */
public class TaskListRequest extends SpiceRequest<TaskListResponse> {

    @Inject
    TaskApiService taskApiService;
    private final String authToken;

    public TaskListRequest(String authToken) {
        super(TaskListResponse.class);
        this.authToken = authToken;
    }

    @Override
    public TaskListResponse loadDataFromNetwork() throws Exception {
        App.getObjectGraphInstance().inject(this);
        return taskApiService.getTaskList(authToken).execute().body();
    }

}