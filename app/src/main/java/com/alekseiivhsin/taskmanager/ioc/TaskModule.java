package com.alekseiivhsin.taskmanager.ioc;

import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.network.TaskApiService;

import java.util.Arrays;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created on 17/12/2015.
 */
@Module
public class TaskModule {

    @Provides
    public TaskApiService provideTaskApiService(Retrofit retrofit) {
        return retrofit.create(TaskApiService.class);
    }

}
