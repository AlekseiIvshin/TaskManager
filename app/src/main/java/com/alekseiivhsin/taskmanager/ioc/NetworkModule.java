package com.alekseiivhsin.taskmanager.ioc;

import com.alekseiivhsin.taskmanager.robospice.TaskSpiceService;
import com.octo.android.robospice.SpiceManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.JacksonConverterFactory;
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
    public SpiceManager getSpiceManager(){
        return new SpiceManager(TaskSpiceService.class);
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(@Named("apiBaseUrl") String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named("apiBaseUrl")
    public String provideApiBaseUrl(){
        return DEFAULT_BASE_URL;
    }
}
