package com.alekseiivhsin.taskmanager.ioc;

import android.app.Application;

import com.alekseiivhsin.taskmanager.SpicedActivity;
import com.alekseiivhsin.taskmanager.authentication.AuthTokenLoader;
import com.alekseiivhsin.taskmanager.fragments.PoolTaskListFragment;
import com.alekseiivhsin.taskmanager.fragments.SpicedFragment;
import com.alekseiivhsin.taskmanager.fragments.TaskDetailsFragment;
import com.alekseiivhsin.taskmanager.network.requests.PoolMemberListRequest;
import com.alekseiivhsin.taskmanager.network.requests.SignInRequest;
import com.alekseiivhsin.taskmanager.network.requests.TaskDetailsRequest;
import com.alekseiivhsin.taskmanager.network.requests.PoolTaskListRequest;
import com.alekseiivhsin.taskmanager.network.requests.UserTaskListRequest;

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

    void inject(PoolTaskListFragment poolTaskListFragment);

    void inject(SpicedActivity spicedActivity);

    void inject(SignInRequest signInRequest);

    void inject(PoolTaskListRequest poolTaskListRequest);

    void inject(TaskDetailsRequest taskDetailsRequest);

    void inject(TaskDetailsFragment taskDetailsFragment);

    void inject(SpicedFragment spicedFragment);

    void inject(UserTaskListRequest userTaskListRequest);

    void inject(PoolMemberListRequest poolMemberListRequest);

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
