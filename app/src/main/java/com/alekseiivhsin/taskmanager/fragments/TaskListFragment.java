package com.alekseiivhsin.taskmanager.fragments;

import android.accounts.Account;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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
import com.alekseiivhsin.taskmanager.loaders.TaskListLoader;
import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.views.adapters.TaskListAdapter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TaskListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Task>> {

    public static final int TASK_LIST_LOADER_ID = 0;
    private static final String TAG = TaskListFragment.class.getSimpleName();

    @Bind(R.id.list_tasks)
    RecyclerView mTasksList;

    @Bind(R.id.add_new_task)
    FloatingActionButton mAddNewTask;

    TaskListAdapter mTaskListAdapter;

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
        View rootView = inflater.inflate(R.layout.fragment_task_list, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mTaskListAdapter = new TaskListAdapter();
        mTasksList.setLayoutManager(layoutManager);
        mTasksList.setAdapter(mTaskListAdapter);

        if (!isNeedShowNewTaskButton()) {
            mAddNewTask.setVisibility(View.GONE);
        }

        getLoaderManager().initLoader(TASK_LIST_LOADER_ID, null, this);

        return rootView;
    }

    protected boolean isNeedShowNewTaskButton() {
        Account[] accounts = mAuthHelper.getAccounts();

        Log.v(TAG, "Accounts count = " + accounts.length);
        for (Account account : accounts) {
            if (mAuthHelper.hasAccountRights(account, UserRights.CAN_CREATE_TASK)) {
                Log.v(TAG, "There is account with need rights: UserRights.CAN_CREATE_TASK=" + UserRights.CAN_CREATE_TASK);
                return true;
            }
            Log.v(TAG, String.format("Account %s, rights = %d don't has need rights: UserRights.CAN_CREATE_TASK = %d", account, mAuthHelper.getUserRights(account), UserRights.CAN_CREATE_TASK));
        }
        Log.v(TAG, "There is not account with need rights: UserRights.CAN_CREATE_TASK=" + UserRights.CAN_CREATE_TASK);
        return false;
    }

    @Override
    public Loader<List<Task>> onCreateLoader(int id, Bundle args) {
        return new TaskListLoader(getActivity(), mAuthHelper.getAuthToken(mAuthHelper.getAccounts()[0]));
    }

    @Override
    public void onLoadFinished(Loader<List<Task>> loader, List<Task> data) {
        mTaskListAdapter.setTaskList(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Task>> loader) {
        mTaskListAdapter.setTaskList(Collections.EMPTY_LIST);
    }
}
