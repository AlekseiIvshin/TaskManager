package com.alekseiivhsin.taskmanager.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.network.TaskApiService;

import java.util.List;

import javax.inject.Inject;

/**
 * Created on 17/12/2015.
 */
public class TaskListLoader extends AsyncTaskLoader<List<Task>> {

    @Inject
    TaskApiService mApiService;

    private final String mAuthToken;

    public TaskListLoader(Context context, String authToken) {
        super(context);
        this.mAuthToken = authToken;
    }

    @Override
    public List<Task> loadInBackground() {
        App.getObjectGraphInstance().inject(this);
        List<Task> result = mApiService.getTaskList(mAuthToken);
        deliverResult(result);
        return null;
    }


}
