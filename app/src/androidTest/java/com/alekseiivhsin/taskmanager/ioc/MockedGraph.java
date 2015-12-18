package com.alekseiivhsin.taskmanager.ioc;

import javax.inject.Singleton;

import dagger.Component;

import static org.mockito.Mockito.mock;

/**
 * Created on 07/12/2015.
 */
@Singleton
@Component(modules = MockAuthModule.class)
public interface MockedGraph extends Graph {

    final class MockedInitializer {
        public static Graph init(MockAuthModule mockModule) {
            return DaggerGraph.builder().authModule(mockModule).build();
        }
    }

    class MockGraphBuilder {

        NetworkModule mockNetworkModule = mock(NetworkModule.class);
        AuthModule mockAuthModule = mock(AuthModule.class);
        TaskModule mockTaskModule = mock(TaskModule.class);

        public static MockGraphBuilder begin() {
            return new MockGraphBuilder();
        }

        public Graph build() {
            return DaggerGraph.builder()
                    .authModule(mockAuthModule)
                    .taskModule(mockTaskModule)
                    .networkModule(mockNetworkModule)
                    .build();
        }

        public MockGraphBuilder setMockAuthModule(AuthModule mockAuthModule) {
            this.mockAuthModule = mockAuthModule;
            return this;
        }

        public MockGraphBuilder setMockNetworkModule(NetworkModule mockModule) {
            this.mockNetworkModule = mockModule;
            return this;
        }

        public MockGraphBuilder setMockTaskModule(TaskModule mockModule) {
            this.mockTaskModule = mockModule;
            return this;
        }

    }
}
