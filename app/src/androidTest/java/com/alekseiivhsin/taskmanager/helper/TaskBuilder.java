package com.alekseiivhsin.taskmanager.helper;

import com.alekseiivhsin.taskmanager.models.Task;

import org.joda.time.DateTime;

/**
 * Created by Aleksei Ivshin
 * on 19.01.2016.
 */
public class TaskBuilder {

    public String name = "";
    public int priority = 0;
    public int status = 0;
    public DateTime deadline = new DateTime();
    public String description = "";

    public static TaskBuilder newTask(){
        return new TaskBuilder();
    }

    public Task build() {
        return new Task(name, priority, status, deadline, description);
    }

    public TaskBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TaskBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public TaskBuilder setStatus(int status) {
        this.status = status;
        return this;
    }

    public TaskBuilder setDeadline(DateTime deadline) {
        this.deadline = deadline;
        return this;
    }

    public TaskBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

}
