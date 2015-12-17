package com.alekseiivhsin.taskmanager.ioc;

import android.app.Application;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.network.AuthService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

/**
 * Created by Aleksei Ivshin
 * on 20.09.2015.
 */
@Module
public class AuthModule {

    private final Application mApp;

    public AuthModule(Application application) {
        mApp = application;
    }

    @Provides
    public AuthService provideAuthService(Retrofit retrofit) {
        return retrofit.create(AuthService.class);
    }

    @Provides
    public AuthHelper provideAuthHelper() {
        return AuthHelper.get(mApp.getApplicationContext());
    }
}
