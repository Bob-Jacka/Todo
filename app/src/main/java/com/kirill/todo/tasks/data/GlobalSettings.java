package com.kirill.todo.tasks.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class GlobalSettings {

    public static int maxTasks = 10;
    public static List<AbstractTask> tasks = new ArrayList<>();
    public static final String SAVE_FILE_NAME = "/saveTasks";
    public static int today = LocalDateTime.now().getDayOfYear();
    public static final String serializeKey = "taskToView";
}
