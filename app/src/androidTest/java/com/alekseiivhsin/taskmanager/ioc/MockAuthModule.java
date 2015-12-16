package com.alekseiivhsin.taskmanager.ioc;

import android.app.Application;

import com.alekseiivhsin.taskmanager.authentication.AuthHelper;
import com.alekseiivhsin.taskmanager.network.AuthService;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;

/**
 * Created by Aleksei Ivshin
 * on 20.09.2015.
 */
@Module
public class MockAuthModule extends AuthModule {

    public AuthService mockAuthService = Mockito.mock(AuthService.class);
    public AuthHelper mockAuthHelper = Mockito.mock(AuthHelper.class);

    public MockAuthModule(Application app) {
        super(app);
    }

    @Provides
    @Singleton
    @Override
    public AuthService provideAuthService(Retrofit retrofit) {
        return mockAuthService;
    }

    @Provides
    @Singleton
    @Override
    public AuthHelper provideAuthHelper() {
        return mockAuthHelper;
    }
}
