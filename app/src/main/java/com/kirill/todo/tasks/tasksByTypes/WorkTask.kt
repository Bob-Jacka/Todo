package com.kirill.todo.tasks.tasksByTypes;

import com.kirill.todo.tasks.data.AbstractTask;
import com.kirill.todo.tasks.data.TasksEnum;

public class WorkTask extends AbstractTask {

    public WorkTask() {
    }

    public WorkTask(String creator, String taskName, int subtaskCount, String description, String type) {
        this.CREATOR = creator;
        this.taskName = taskName;
        this.stepsCount = subtaskCount;
        this.description = description;
        this.type = TasksEnum.valueOf(type);
    }
}
