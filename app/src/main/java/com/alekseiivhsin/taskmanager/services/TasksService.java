package com.alekseiivhsin.taskmanager.services;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.os.IBinder;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.network.TaskApiService;
import com.alekseiivhsin.taskmanager.network.models.TaskListResponse;

import java.util.List;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class TasksService extends Service {

    @Inject
    TaskApiService mTaskApiService;

    public static final String GET_TASK_LIST_ACTION = "taskmanager.actions.GET_TASK_LIST_ACTION";

    public TasksService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        ((App)getApplication()).getObjectGraph().inject(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()) {
            case GET_TASK_LIST_ACTION:
                String authToken = intent.getExtras().getString(AccountManager.KEY_AUTHTOKEN);
                Call<List<Task>> taskListCall = mTaskApiService.getTasksList(authToken);
                taskListCall.enqueue(new Callback<List<Task>>() {
                    @Override
                    public void onResponse(Response<List<Task>> response, Retrofit retrofit) {
                        response.body();
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
                break;
        }
        return START_NOT_STICKY;
    }

    private void setToCache
}
