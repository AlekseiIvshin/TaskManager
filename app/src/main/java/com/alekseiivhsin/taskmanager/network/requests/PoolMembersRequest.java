package com.alekseiivhsin.taskmanager.network.requests;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.network.responses.PoolMembersResponse;
import com.alekseiivhsin.taskmanager.network.services.PoolApiService;
import com.octo.android.robospice.request.SpiceRequest;

import javax.inject.Inject;

/**
 * Created on 21/12/2015.
 */
public class PoolMembersRequest extends SpiceRequest<PoolMembersResponse> {

    @Inject
    PoolApiService poolApiService;

    public final String authToken;

    public PoolMembersRequest(String authToken) {
        super(PoolMembersResponse.class);
        this.authToken = authToken;
    }

    @Override
    public PoolMembersResponse loadDataFromNetwork() throws Exception {
        App.getObjectGraphInstance().inject(this);
        return poolApiService.getPoolMemberList(authToken).execute().body();
    }

}