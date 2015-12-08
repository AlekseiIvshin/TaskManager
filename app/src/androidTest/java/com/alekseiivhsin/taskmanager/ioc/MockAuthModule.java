package com.alekseiivhsin.taskmanager.ioc;

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

    @Provides
    @Override
    public AuthService provideAuthService(Retrofit retrofit) {
        return mockAuthService;
    }
}
