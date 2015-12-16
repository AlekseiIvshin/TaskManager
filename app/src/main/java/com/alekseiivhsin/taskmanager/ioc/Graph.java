package com.alekseiivhsin.taskmanager.ioc;

import android.app.Application;

import com.alekseiivhsin.taskmanager.App;
import com.alekseiivhsin.taskmanager.authentication.AuthTokenLoader;
import com.alekseiivhsin.taskmanager.fragments.TaskListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created on 07/12/2015.
 */
@Singleton
@Component(
        modules = {
                AuthModule.class})
public interface Graph {

    void inject(AuthTokenLoader authTokenLoader);

    void inject(TaskListFragment taskListFragment);

    final class Initializer {
        public static Graph init(Application app) {
            return DaggerGraph.builder().authModule(new AuthModule(app)).build();
        }
    }
}
