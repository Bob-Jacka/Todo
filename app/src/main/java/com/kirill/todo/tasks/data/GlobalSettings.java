package com.kirill.todo.tasks.data;

import com.kirill.todo.tasks.core.TaskFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class GlobalSettings {

    public static String saveKey = "task";
    public static int maxTasks = 10;
    public static List<AbstractTask> tasks = new ArrayList<>();
    public static String SAVE_FILE_NAME = "/saveTasks";
    public static final TaskFactory factory = TaskFactory.getInstance();
    public static final int today = LocalDateTime.now().getDayOfYear();
}
