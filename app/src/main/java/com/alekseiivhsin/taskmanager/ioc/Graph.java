package com.alekseiivhsin.taskmanager.ioc;

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
                AuthModule.class,
                TaskListModule.class})
public interface Graph {

    void inject(AuthTokenLoader authTokenLoader);

    void inject(TaskListFragment.TaskListCursorLoader taskListCursorLoader);

    final class Initializer {
        public static Graph init() {
            return DaggerGraph.builder().authModule(new AuthModule()).taskListModule(new TaskListModule()).build();
        }
    }
}
