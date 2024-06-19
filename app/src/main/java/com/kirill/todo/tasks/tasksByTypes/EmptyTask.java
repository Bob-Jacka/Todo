package com.kirill.todo.tasks.tasksByTypes;

import com.kirill.todo.tasks.data.AbstractTask;
import com.kirill.todo.tasks.data.TasksEnum;

public class EmptyTask extends AbstractTask {

    public EmptyTask() {
    }

    public EmptyTask(String creator, String taskName, int subtaskCount, String description, String type) {
        this.CREATOR = creator;
        this.taskName = taskName;
        this.subtaskCount = subtaskCount;
        this.description = description;
        this.type = TasksEnum.valueOf(type);
    }
}
