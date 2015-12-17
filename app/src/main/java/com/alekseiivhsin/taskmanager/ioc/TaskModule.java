package com.alekseiivhsin.taskmanager.ioc;

import com.alekseiivhsin.taskmanager.models.Task;
import com.alekseiivhsin.taskmanager.network.TaskApiService;

import java.util.Arrays;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import retrofit.Retrofit;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created on 17/12/2015.
 */
@Module
public class TaskModule {

    @Provides
    public TaskApiService provideTaskApiService(Retrofit retrofit) {
        return Stub.getStubService();
    }


    private static class Stub {

        private static final List<Task> TASKS = Arrays.asList(
                new Task("Task 1 "),
                new Task("Task 2"),
                new Task("Task 3")
        );

        public static TaskApiService getStubService() {
            return new TaskApiService() {
                @Override
                public List<Task> getTaskList(@Query("authToken") String authToken) {
                    return TASKS;
                }

                @Override
                public Task getTask(@Path("id") String taskId, @Query("authToken") String authToken) {
                    int pos = Integer.parseInt(taskId);
                    return TASKS.get(pos);
                }
            };
        }
    }
}
