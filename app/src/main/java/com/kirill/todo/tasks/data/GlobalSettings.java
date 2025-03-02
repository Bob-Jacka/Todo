package com.kirill.todo.tasks.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class GlobalSettings {

    public static List<AbstractTask> tasks = new ArrayList<>();
    public static final String SAVE_FILE_NAME = "/saveTasks";
    public static int today = LocalDateTime.now().getDayOfYear();

    public static final String empty_line = "0";

    public static final String serializeKeyTask = "taskToView";
    public static final String serializeKeyStep = "stepToView";
}
