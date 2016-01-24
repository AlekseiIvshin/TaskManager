package com.alekseiivhsin.taskmanager.ioc;

import com.alekseiivhsin.taskmanager.network.services.PoolApiService;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

/**
 * Created on 17/12/2015.
 */
@Module
public class PoolModule {

    @Provides
    public PoolApiService provideTaskApiService(Retrofit retrofit) {
        return retrofit.create(PoolApiService.class);
    }

}
