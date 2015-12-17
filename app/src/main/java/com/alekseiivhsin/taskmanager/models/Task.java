package com.alekseiivhsin.taskmanager.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created on 14/12/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    public final String name;

    @JsonCreator
    public Task(@JsonProperty("name") String name) {
        this.name = name;
    }
}
