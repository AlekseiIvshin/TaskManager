package com.alekseiivhsin.taskmanager.ioc;

import android.app.Application;

import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.network.AuthService;
import com.alekseiivhsin.taskmanager.network.TaskApiService;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

/**
 * Created by Aleksei Ivshin
 * on 20.09.2015.
 */
@Module
public class TaskModule {

    @Provides
    public TaskApiService provideTaskApiService(Retrofit retrofit) {
        return retrofit.create(TaskApiService.class);
    }
}
