package com.alekseiivhsin.taskmanager.ioc;

import android.content.Context;

import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.network.services.AuthApiService;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

/**
 * Created by Aleksei Ivshin
 * on 20.09.2015.
 */
@Module
public class AuthModule {

    private final Context mContext;

    public AuthModule(Context application) {
        mContext = application;
    }

    @Provides
    public AuthApiService provideAuthService(Retrofit retrofit) {
        return retrofit.create(AuthApiService.class);
    }

    @Provides
    public AuthHelper provideAuthHelper() {
        return AuthHelper.get(mContext.getApplicationContext());
    }

}
