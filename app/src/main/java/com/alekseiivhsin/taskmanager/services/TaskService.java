package com.alekseiivhsin.taskmanager.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.network.TaskApiService;

import javax.inject.Inject;

public class TaskService extends Service {

    @Inject
    TaskApiService mApiService;

    public TaskService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((App)getApplication()).getObjectGraph().inject(this);
    }
}
