package com.alekseiivhsin.taskmanager.ioc;

import com.alekseiivhsin.taskmanager.network.services.TaskApiService;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

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
