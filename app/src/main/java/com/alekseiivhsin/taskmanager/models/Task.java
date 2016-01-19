package com.alekseiivhsin.taskmanager.models;

import com.alekseiivhsin.taskmanager.network.serializers.CustomDateDeserializer;
import com.alekseiivhsin.taskmanager.network.serializers.CustomDateSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.joda.time.DateTime;

/**
 * Created on 14/12/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    public final String name;
    public final int priority;
    public final int status;
    public final DateTime deadline;
    public final String description;

    @JsonCreator
    public Task(@JsonProperty("name") String name,
                @JsonProperty("priority") int priority,
                @JsonProperty("status") int status,
                @JsonProperty("deadline")
                @JsonSerialize(using = CustomDateSerializer.class)
                @JsonDeserialize(using = CustomDateDeserializer.class) DateTime deadline,
                @JsonProperty("description") String description) {
        this.name = name;
        this.priority = priority;
        this.status = status;
        this.deadline = deadline;
        this.description = description;
    }
}
