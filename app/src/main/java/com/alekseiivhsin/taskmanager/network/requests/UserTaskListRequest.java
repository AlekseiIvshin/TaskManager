package com.alekseiivhsin.taskmanager.network.requests;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.network.responses.UserTaskListResponse;
import com.alekseiivhsin.taskmanager.network.services.TaskApiService;
import com.octo.android.robospice.request.SpiceRequest;

import javax.inject.Inject;

/**
 * Created on 21/12/2015.
 */
public class UserTaskListRequest extends SpiceRequest<UserTaskListResponse> {

    @Inject
    TaskApiService taskApiService;
    private final String authToken;

    public UserTaskListRequest(String authToken) {
        super(UserTaskListResponse.class);
        this.authToken = authToken;
    }

    @Override
    public UserTaskListResponse loadDataFromNetwork() throws Exception {
        App.getObjectGraphInstance().inject(this);
        return taskApiService.getUserTaskList(authToken).execute().body();
    }

}