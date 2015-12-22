package com.alekseiivhsin.taskmanager.idlingresources;

import android.support.test.espresso.IdlingResource;

import com.octo.android.robospice.SpiceManager;

/**
 * Created on 22/12/2015.
 */
public class RobospiceIdlingResource implements IdlingResource {

    public final SpiceManager spiceManager;
    ResourceCallback resourceCallback;

    public RobospiceIdlingResource(SpiceManager spiceManager) {
        this.spiceManager = spiceManager;
    }

    @Override
    public String getName() {
        return RobospiceIdlingResource.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        boolean idle = idle();
        if (idle && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    private boolean idle() {
        return spiceManager == null || !spiceManager.isStarted() || spiceManager.getPendingRequestCount() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}
