package com.kirill.todo.tasks.data;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractTask implements Serializable {

    protected String CREATOR;
    protected String taskName;
    protected int stepsCount;
    protected String description;
    protected TasksEnum type;
    protected boolean isActivated;
    protected String createdAt;
    protected int checkedWhen;

    protected List<String> whichDaysOfWeek;
    protected List<String> steps;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getStepsCount() {
        return stepsCount;
    }

    public void setStepsCount(int stepsCount) {
        this.stepsCount = stepsCount;
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

    public int getCheckedWhen() {
        return checkedWhen;
    }

    public void setCheckedWhen(int checkedWhen) {
        this.checkedWhen = checkedWhen;
    }

    public List<String> getWhichDaysOfWeek() {
        return whichDaysOfWeek;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public List<String> getSteps() {
        return steps;
    }
}


