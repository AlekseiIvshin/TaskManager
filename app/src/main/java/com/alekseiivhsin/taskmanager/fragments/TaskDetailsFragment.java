package com.alekseiivhsin.taskmanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alekseiivhsin.taskmanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 17/12/2015.
 */
public class TaskDetailsFragment extends Fragment {

    @Bind(R.id.task_name)
    TextView mTaskName;
    @Bind(R.id.task_priority)
    TextView mTaskPriority;
    @Bind(R.id.task_deadline)
    TextView mTaskDeadline;
    @Bind(R.id.task_description)
    TextView mTaskDescription;
    @Bind(R.id.task_status)
    TextView mTaskStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_details, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
