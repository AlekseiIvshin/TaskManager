package com.alekseiivhsin.taskmanager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.authentication.UserRights;
import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.network.requests.TaskDetailsRequest;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 17/12/2015.
 */
public class TaskDetailsFragment extends SpicedFragment {

    public static final String EXTRA_TASK_ID = "taskmanager.extras.EXTRA_TASK_ID";

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

    @Bind(R.id.edit_task)
    FloatingActionButton mEditTask;

    @Inject
    AuthHelper mAuthHelper;

    public static TaskDetailsFragment newInstance(int taskId) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_TASK_ID, taskId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getObjectGraphInstance().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_details, container, false);
        ButterKnife.bind(this, rootView);
        
        if (mAuthHelper.hasAccountRights(UserRights.CAN_UPDATE_TASK)) {
            mEditTask.setVisibility(View.VISIBLE);
        } else {
            mEditTask.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        final int taskId = getArguments().getInt(EXTRA_TASK_ID);
        spiceManager.execute(new TaskDetailsRequest(taskId, mAuthHelper.getAuthToken()), new RequestListener<Task>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(Task task) {
                mTaskName.setText(task.name);
            }
        });
    }
}
