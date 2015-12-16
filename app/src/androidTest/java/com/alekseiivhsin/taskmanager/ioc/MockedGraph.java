package com.alekseiivhsin.taskmanager.ioc;

import com.alekseiivhsin.taskmanager.authentication.AuthTokenLoader;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created on 07/12/2015.
 */
@Singleton
@Component(modules = MockAuthModule.class)
public interface MockedGraph extends Graph{

    final class MockedInitializer {
        public static Graph init(MockAuthModule mockModule) {
            return DaggerGraph.builder().authModule(mockModule).build();
        }
    }
}
