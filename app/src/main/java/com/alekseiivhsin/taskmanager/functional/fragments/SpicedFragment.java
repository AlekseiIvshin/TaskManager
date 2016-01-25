package com.alekseiivhsin.taskmanager.functional.fragments;

import android.support.v4.app.Fragment;

import com.octo.android.robospice.SpiceManager;

import javax.inject.Inject;

/**
 * Created on 22/12/2015.
 */
public class SpicedFragment extends Fragment {

    @Inject
    public SpiceManager spiceManager;

    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(getActivity());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

}
