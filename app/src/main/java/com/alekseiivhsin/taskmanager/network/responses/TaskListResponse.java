package com.alekseiivhsin.taskmanager.network.responses;

import com.alekseiivhsin.taskmanager.models.Task;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created on 21/12/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskListResponse {

    @JsonProperty("tasks")
    public List<Task> taskList;
}
