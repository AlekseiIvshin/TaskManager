package com.alekseiivhsin.taskmanager.shadows;

import com.alekseiivhsin.taskmanager.network.requests.PoolMembersRequest;
import com.alekseiivhsin.taskmanager.network.responses.PoolMembersResponse;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

import static com.alekseiivhsin.taskmanager.shadows.MyShadowAccountManager.STUB_AUTH_TOKEN;

/**
 * Created by Aleksei Ivshin
 * on 24.01.2016.
 */
@Implements(SpiceManager.class)
public class ShadowSpiceManager {

    private static final String TAG = ShadowSpiceManager.class.getSimpleName();

    @RealObject
    private SpiceManager realObject;

    @Implementation
    public <T> void execute(final SpiceRequest<T> request, final RequestListener<T> requestListener) {
        if (request instanceof PoolMembersRequest) {
            execute((PoolMembersRequest) request, (RequestListener<PoolMembersResponse>) requestListener);
            return;
        }
        throw new IllegalStateException("3");
    }

    @Implementation
    public void execute(final PoolMembersRequest request, final RequestListener<PoolMembersResponse> requestListener) {
        if (STUB_AUTH_TOKEN.equals(request.authToken)) {
            requestListener.onRequestSuccess(new PoolMembersResponse());
        } else {
            requestListener.onRequestFailure(new SpiceException("SHADOW EXCEPTION"));
        }
    }

}
