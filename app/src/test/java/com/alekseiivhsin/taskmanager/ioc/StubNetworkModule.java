package com.alekseiivhsin.taskmanager.ioc;

import dagger.Module;

/**
 * Created on 21/12/2015.
 */
@Module
public class StubNetworkModule extends NetworkModule {

    public String stubbedApiBaseUrl;

    @Override
    public String provideApiBaseUrl() {
        return stubbedApiBaseUrl;
    }
}
