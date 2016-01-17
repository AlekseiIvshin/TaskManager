package com.alekseiivhsin.taskmanager.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.alekseiivhsin.taskmanager.SpicedActivity;
import com.octo.android.robospice.SpiceManager;

/**
 * Created on 22/12/2015.
 */
public class SpicedFragment extends Fragment {

    protected SpiceManager spiceManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SpicedActivity) {
            spiceManager = ((SpicedActivity) context).spiceManager;
        }
    }

    @Override
    public void onDetach() {
        spiceManager = null;
        super.onDetach();
    }
}
