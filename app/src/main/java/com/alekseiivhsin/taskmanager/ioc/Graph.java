package com.alekseiivhsin.taskmanager.ioc;

import android.app.Application;

import com.alekseiivhsin.taskmanager.authentication.AuthTokenLoader;
import com.alekseiivhsin.taskmanager.fragments.TaskListFragment;
import com.alekseiivhsin.taskmanager.services.TasksService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created on 07/12/2015.
 */
@Singleton
@Component(
        modules = {
                NetworkModule.class,
                AuthModule.class,
                TaskModule.class})
public interface Graph {

    void inject(AuthTokenLoader authTokenLoader);

    void inject(TaskListFragment taskListFragment);

    void inject(TasksService tasksService);

    final class Initializer {
        public static Graph init(Application app) {
            return DaggerGraph.builder().authModule(new AuthModule(app)).build();
        }
    }
}
