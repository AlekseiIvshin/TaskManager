package com.alekseiivhsin.taskmanager.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.R;
import com.alekseiivhsin.taskmanager.views.adapters.TaskListAdapter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TaskListFragment extends Fragment {

    public static final int TASK_LIST_LOADER_ID = 0;

    @Bind(R.id.list_tasks)
    RecyclerView mTasksList;

    @Bind(R.id.add_new_task)
    Button mAddNewTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_list, container, false);

        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        TaskListAdapter taskListAdapter = new TaskListAdapter(null);
        mTasksList.setLayoutManager(layoutManager);
        mTasksList.setAdapter(taskListAdapter);

        TaskListCursorLoader taskListCursorLoader = new TaskListCursorLoader(taskListAdapter);
        getLoaderManager().initLoader(TASK_LIST_LOADER_ID, new Bundle(), taskListCursorLoader);

        return rootView;
    }

    public static class TaskListCursorLoader implements LoaderManager.LoaderCallbacks<Cursor> {

        private final TaskListAdapter mAdapter;

        @Inject
        Loader<Cursor> mCursorLoader;

        public TaskListCursorLoader(TaskListAdapter taskListAdapter) {
            mAdapter = taskListAdapter;
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            App.getObjectGraphInstance().inject(this);
            return mCursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor newCursor) {
            mAdapter.changeCursor(newCursor);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mAdapter.changeCursor(null);
        }
    }

}
