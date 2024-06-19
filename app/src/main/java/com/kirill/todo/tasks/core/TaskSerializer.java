package com.kirill.todo.tasks.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirill.todo.tasks.data.AbstractTask;

public abstract class TaskSerializer {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static String serialize(AbstractTask task) throws JsonProcessingException {
        return objectMapper.writeValueAsString(task);
    }

    public static AbstractTask deserialize(String string) throws JsonProcessingException {
        return objectMapper.readValue(string, AbstractTask.class);
    }
}
