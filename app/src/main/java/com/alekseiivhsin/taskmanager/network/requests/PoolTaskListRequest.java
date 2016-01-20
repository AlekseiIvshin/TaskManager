package com.alekseiivhsin.taskmanager.network.requests;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.network.responses.PoolTaskListResponse;
import com.alekseiivhsin.taskmanager.network.services.TaskApiService;
import com.octo.android.robospice.request.SpiceRequest;

import javax.inject.Inject;

/**
 * Created on 21/12/2015.
 */
public class PoolTaskListRequest extends SpiceRequest<PoolTaskListResponse> {

    @Inject
    TaskApiService taskApiService;
    private final String authToken;

    public PoolTaskListRequest(String authToken) {
        super(PoolTaskListResponse.class);
        this.authToken = authToken;
    }

    @Override
    public PoolTaskListResponse loadDataFromNetwork() throws Exception {
        App.getObjectGraphInstance().inject(this);
        return taskApiService.getPoolTaskList(authToken).execute().body();
    }

}