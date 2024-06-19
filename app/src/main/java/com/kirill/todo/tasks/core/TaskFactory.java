package com.kirill.todo.tasks.core;

import com.kirill.todo.tasks.data.AbstractTask;
import com.kirill.todo.tasks.data.TasksEnum;
import com.kirill.todo.tasks.tasksByTypes.EmptyTask;
import com.kirill.todo.tasks.tasksByTypes.ReadTask;
import com.kirill.todo.tasks.tasksByTypes.SportTask;
import com.kirill.todo.tasks.tasksByTypes.WorkTask;

public class TaskFactory {

    private static final TaskFactory taskFactory = new TaskFactory();

    private TaskFactory() {
    }

    public static TaskFactory getInstance() {
        return taskFactory;
    }

    public AbstractTask getTask(TasksEnum type) {
        switch (type) {
            case EMPTY:
                return new EmptyTask();
            case READ:
                return new ReadTask();
            case PHYSICAL:
                return new SportTask();
            case WORK:
                return new WorkTask();
        }
        return null;
    }

    public AbstractTask getEmptyTask(String creator, String taskName, int subTaskCount, String description, String type) {
        return new EmptyTask(creator, taskName, subTaskCount, description, type);
    }

    public AbstractTask getReadTask(String creator, String taskName, int subTaskCount, String description, String type) {
        return new ReadTask(creator, taskName, subTaskCount, description, type);
    }

    public AbstractTask getPhysicalTask(String creator, String taskName, int subTaskCount, String description, String type) {
        return new SportTask(creator, taskName, subTaskCount, description, type);
    }

    public AbstractTask getWorkTask(String creator, String taskName, int subTaskCount, String description, String type) {
        return new WorkTask(creator, taskName, subTaskCount, description, type);
    }
}
