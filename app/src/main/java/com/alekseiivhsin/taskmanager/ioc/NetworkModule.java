package com.alekseiivhsin.taskmanager.ioc;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Aleksei Ivshin
 * on 20.09.2015.
 */
@Module
public class NetworkModule {

    private static final String DEFAULT_BASE_URL = "http://localhost/";

    @Provides
    @Singleton
    public Retrofit provideRetrofit(@Named("apiBaseUrl") String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named("apiBaseUrl")
    public String provideApiBaseUrl(){
        return DEFAULT_BASE_URL;
    }
}
