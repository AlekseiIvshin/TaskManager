package com.alekseiivhsin.taskmanager.ioc;

import android.app.Application;

import com.alekseiivhsin.taskmanager.MainActivity;
import com.alekseiivhsin.taskmanager.authentication.AuthTokenLoader;
import com.alekseiivhsin.taskmanager.fragments.TaskDetailsFragment;
import com.alekseiivhsin.taskmanager.fragments.TaskListFragment;
import com.alekseiivhsin.taskmanager.network.requests.SignInRequest;
import com.alekseiivhsin.taskmanager.network.requests.TaskDetailsRequest;
import com.alekseiivhsin.taskmanager.network.requests.TaskListRequest;

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

    void inject(MainActivity mainActivity);

    void inject(SignInRequest signInRequest);

    void inject(TaskListRequest taskListRequest);

    void inject(TaskDetailsRequest taskDetailsRequest);

    void inject(TaskDetailsFragment taskDetailsFragment);

    final class Initializer {
        public static Graph init(Application app) {
            return DaggerGraph.builder()
                    .authModule(new AuthModule(app))
                    .networkModule(new NetworkModule())
                    .taskModule(new TaskModule())
                    .build();
        }
    }
}
