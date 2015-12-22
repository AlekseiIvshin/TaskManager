package com.alekseiivhsin.taskmanager.network.requests;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.network.services.TaskApiService;
import com.octo.android.robospice.request.SpiceRequest;

import javax.inject.Inject;

/**
 * Created on 22/12/2015.
 */
public class TaskDetailsRequest extends SpiceRequest<Task> {

    @Inject
    TaskApiService taskApiService;
    private final String authToken;
    private final int taskId;

    public TaskDetailsRequest(int taskId, String authToken) {
        super(Task.class);
        this.authToken = authToken;
        this.taskId = taskId;
    }

    @Override
    public Task loadDataFromNetwork() throws Exception {
        App.getObjectGraphInstance().inject(this);
        return taskApiService.getTask(taskId, authToken).execute().body();
    }
}
