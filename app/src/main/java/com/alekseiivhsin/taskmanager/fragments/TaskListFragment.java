package com.alekseiivhsin.taskmanager.fragments;

import android.accounts.AccountManager;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.alekseiivhsin.taskmanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TaskListFragment extends Fragment {

    @Bind(R.id.list_tasks)
    RecyclerView mTasksList;

    @Bind(R.id.add_new_task)
    Button mAddNewTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_list,container,false);

        ButterKnife.bind(this,rootView);


        return rootView;
    }
}
