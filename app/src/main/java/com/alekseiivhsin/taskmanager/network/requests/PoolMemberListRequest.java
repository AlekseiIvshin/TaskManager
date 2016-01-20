package com.alekseiivhsin.taskmanager.network.requests;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.network.responses.PoolMemberListResponse;
import com.alekseiivhsin.taskmanager.network.services.PoolApiService;
import com.octo.android.robospice.request.SpiceRequest;

import javax.inject.Inject;

/**
 * Created on 21/12/2015.
 */
public class PoolMemberListRequest extends SpiceRequest<PoolMemberListResponse> {

    @Inject
    PoolApiService poolApiService;

    private final String authToken;

    public PoolMemberListRequest(String authToken) {
        super(PoolMemberListResponse.class);
        this.authToken = authToken;
    }

    @Override
    public PoolMemberListResponse loadDataFromNetwork() throws Exception {
        App.getObjectGraphInstance().inject(this);
        return poolApiService.getPoolMemberList(authToken).execute().body();
    }

}