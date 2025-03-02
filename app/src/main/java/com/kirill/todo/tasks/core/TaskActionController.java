package com.kirill.todo.tasks.core;

import static com.kirill.todo.tasks.data.GlobalSettings.tasks;
import static com.kirill.todo.tasks.pages.MainActivity.taskList;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Toast;

import com.kirill.todo.tasks.data.AbstractTask;
import com.kirill.todo.tasks.pages.MainActivity;

import java.util.Calendar;

public final class TaskActionController {

    @SuppressLint("StaticFieldLeak")
    public static int taskViewPointer;
    private static final Save_Module saveModule = new Save_Module();

    /**
     * Wrapper method for saving task blocks.
     */
    public static void saveTasks() {
        saveModule.save_TaskBlocks();
    }

    /**
     * Wrapper method for loading task blocks.
     */
    public static void loadTasks() {
        saveModule.load_TasksArray();
    }

    public static void deleteTask() {
        try {
            tasks.remove(taskViewPointer);
            taskList.getChildAt(taskViewPointer).setVisibility(View.GONE);
        } catch (IndexOutOfBoundsException e) {
            Toast.makeText(
                    MainActivity.taskList.getContext(),
                    "Error occurred in deleting task",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static void addTask(AbstractTask task) {
        if (task != null) {
            tasks.add(task);
        }
    }

    public static String capitalizeString(final String str) {
        return str.charAt(0) + str.substring(1).toLowerCase();
    }

    public static String getData() {
        return String.valueOf(Calendar.getInstance().getTime());
    }
}
