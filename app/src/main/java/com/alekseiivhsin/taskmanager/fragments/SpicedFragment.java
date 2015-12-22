package com.alekseiivhsin.taskmanager.fragments;

import android.support.v4.app.Fragment;

import com.alekseiivhsin.taskmanager.robospice.TaskSpiceService;
import com.octo.android.robospice.SpiceManager;

/**
 * Created on 22/12/2015.
 */
public class SpicedFragment extends Fragment {

    public SpiceManager spiceManager = new SpiceManager(TaskSpiceService.class);

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
