package com.alekseiivhsin.taskmanager.functional.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.authentication.UserRights;
import com.alekseiivhsin.taskmanager.network.requests.UserTaskListRequest;
import com.alekseiivhsin.taskmanager.network.responses.UserTaskListResponse;
import com.alekseiivhsin.taskmanager.views.adapters.UserTaskListAdapter;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserTaskListFragment extends SpicedFragment {

    private static final String TAG = UserTaskListFragment.class.getSimpleName();


    @Bind(R.id.list_tasks)
    RecyclerView mTasksList;

    @Bind(R.id.add_new_task)
    FloatingActionButton mAddNewTask;

    UserTaskListAdapter mPoolTaskListAdapter;

    @Inject
    AuthHelper mAuthHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getObjectGraphInstance().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_task_list, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mPoolTaskListAdapter = new UserTaskListAdapter();
        mTasksList.setLayoutManager(layoutManager);
        mTasksList.setAdapter(mPoolTaskListAdapter);

        if (!isNeedShowNewTaskButton()) {
            mAddNewTask.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        UserTaskListRequest request = new UserTaskListRequest(mAuthHelper.getAuthToken());
        spiceManager.execute(request, new RequestListener<UserTaskListResponse>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Log.v(TAG, "Error while load tasks ", spiceException);
                mPoolTaskListAdapter.setTaskList(Collections.EMPTY_LIST);
            }

            @Override
            public void onRequestSuccess(UserTaskListResponse poolTaskListResponse) {
                mPoolTaskListAdapter.setTaskList(poolTaskListResponse.tasks);
            }
        });

    }

    protected boolean isNeedShowNewTaskButton() {
        return mAuthHelper.hasAccountRights(UserRights.CAN_CREATE_TASK);
    }
}
