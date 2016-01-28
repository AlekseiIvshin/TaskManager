package com.alekseiivhsin.taskmanager.ioc;

import com.alekseiivhsin.taskmanager.App;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created on 07/12/2015.
 */
@Singleton
@Component(modules = MockAuthModule.class)
public interface MockedGraph extends Graph {

    class MockGraphBuilder {

        NetworkModule stubNetworkModule;
        AuthModule stubAuthModule;
        TaskModule stubTaskModule;

        public static MockGraphBuilder begin() {
            return new MockGraphBuilder();
        }

        public Graph build() {
            if (stubNetworkModule == null) {
                stubNetworkModule = new NetworkModule();
            }
            if (stubTaskModule == null) {
                stubTaskModule = new TaskModule();
            }
            if (stubAuthModule == null) {
                stubAuthModule = new AuthModule(App.getInstance());
            }
            return DaggerGraph.builder()
                    .authModule(stubAuthModule)
                    .taskModule(stubTaskModule)
                    .networkModule(stubNetworkModule)
                    .build();
        }

        public MockGraphBuilder setStubAuthModule(AuthModule stubAuthModule) {
            this.stubAuthModule = stubAuthModule;
            return this;
        }

        public MockGraphBuilder setStubNetworkModule(NetworkModule mockModule) {
            this.stubNetworkModule = mockModule;
            return this;
        }

        public MockGraphBuilder setStubTaskModule(TaskModule mockModule) {
            this.stubTaskModule = mockModule;
            return this;
        }

    }
}
