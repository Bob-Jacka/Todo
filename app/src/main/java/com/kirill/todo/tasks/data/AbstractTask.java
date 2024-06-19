package com.kirill.todo.tasks.data;

import java.io.Serializable;

public abstract class AbstractTask implements Serializable {

    protected String CREATOR;
    protected String taskName;
    protected int subtaskCount;
    protected String description;
    protected TasksEnum type;
    protected boolean isActivated;
    protected String createdAt;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getSubtaskCount() {
        return subtaskCount;
    }

    public void setSubtaskCount(int subtaskCount) {
        this.subtaskCount = subtaskCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TasksEnum getType() {
        return type;
    }

    public void setType(TasksEnum type) {
        this.type = type;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public String isCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}


