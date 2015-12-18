package com.alekseiivhsin.taskmanager.ioc;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

/**
 * Created by Aleksei Ivshin
 * on 20.09.2015.
 */
@Module
public class NetworkModule {

    private static final String BASE_URL = "http://localhost/";

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
    }
}
