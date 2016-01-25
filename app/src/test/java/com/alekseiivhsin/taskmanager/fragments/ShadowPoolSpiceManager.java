package com.alekseiivhsin.taskmanager.fragments;

import com.alekseiivhsin.taskmanager.models.PoolMember;
import com.alekseiivhsin.taskmanager.network.requests.PoolMembersRequest;
import com.alekseiivhsin.taskmanager.network.responses.PoolMembersResponse;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

import java.util.ArrayList;

import static com.alekseiivhsin.taskmanager.shadows.MyShadowAccountManager.STUB_SUCCESS_AUTH_TOKEN;

/**
 * Created by Aleksei Ivshin
 * on 24.01.2016.
 */
@Implements(SpiceManager.class)
public class ShadowPoolSpiceManager {

    private static final String TAG = ShadowPoolSpiceManager.class.getSimpleName();
    public static final int POOL_MEMBER_COUNT = 3;

    @RealObject
    private SpiceManager realObject;

    @Implementation
    public <T> void execute(final SpiceRequest<T> request, final RequestListener<T> requestListener) {
        if (request instanceof PoolMembersRequest) {
            execute((PoolMembersRequest) request, (RequestListener<PoolMembersResponse>) requestListener);
            return;
        }
        throw new IllegalStateException("Such request not supported!");
    }

    @Implementation
    public void execute(final PoolMembersRequest request, final RequestListener<PoolMembersResponse> requestListener) {
        if (STUB_SUCCESS_AUTH_TOKEN.equals(request.authToken)) {
            requestListener.onRequestSuccess(generatePoolMembers(POOL_MEMBER_COUNT));
        } else {
            requestListener.onRequestFailure(new SpiceException("SHADOW EXCEPTION"));
        }
    }

    private PoolMembersResponse generatePoolMembers(int count) {
        PoolMembersResponse response = new PoolMembersResponse();
        response.poolMemberList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            response.poolMemberList.add(new PoolMember(i, "Pool", "Member " + i));
        }
        return response;
    }
}
