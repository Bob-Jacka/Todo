package com.kirill.todo.tasks.pages;

import static com.kirill.todo.tasks.core.Save_Module.saveFileName;
import static com.kirill.todo.tasks.core.TaskActionController.loadTasks;
import static com.kirill.todo.tasks.core.TaskActionController.saveTasks;
import static com.kirill.todo.tasks.core.TaskActionController.taskViewPointer;
import static com.kirill.todo.tasks.data.GlobalSettings.SAVE_FILE_NAME;
import static com.kirill.todo.tasks.data.GlobalSettings.tasks;
import static com.kirill.todo.tasks.data.GlobalSettings.today;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kirill.todo.R;
import com.kirill.todo.tasks.core.TaskActionController;
import com.kirill.todo.tasks.data.AbstractTask;

import java.time.LocalDateTime;
import java.util.Iterator;

@SuppressLint("StaticFieldLeak")
public class MainActivity extends AppCompatActivity {

    private ImageButton addButton;

    @SuppressLint("StaticFieldLeak")
    public static LinearLayout taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveFileName = getApplicationContext().getFilesDir().getAbsolutePath() + SAVE_FILE_NAME;
        taskList = findViewById(R.id.TasksList);
        addButton = findViewById(R.id.addButton);
        loadTasks();
        initTasks();
    }

    @Override
    protected void onStop() {
        saveTasks();
        super.onStop();
    }

    private void initTasks() {
        if (tasks.size() != 0) {
            final int todayDate = LocalDateTime.now().getDayOfYear();
            final String dayOfWeek = LocalDateTime.now().getDayOfWeek().toString();
            final Iterator<AbstractTask> taskIterator = tasks.iterator();
            for (int i = 0; i < taskList.getChildCount(); i++) {
                if (taskIterator.hasNext()) {
                    AbstractTask task = taskIterator.next();
                    if (task.getWhichDaysOfWeek().contains(dayOfWeek)) {
                        LinearLayout taskBlock = (LinearLayout) taskList.getChildAt(i);
                        taskBlock.setVisibility(View.VISIBLE);

                        RadioButton radBtn = (RadioButton) taskBlock.getChildAt(0);
                        if (task.isActivated() && todayDate == today) {
                            radBtn.toggle();
                        }

                        TextView txtv1 = (TextView) taskBlock.getChildAt(1);
                        txtv1.setText(task.getTaskName());

                        TextView txtv2 = (TextView) taskBlock.getChildAt(2);
                        txtv2.setText(String.valueOf(task.getType()));

                        registerForContextMenu(taskBlock);
                        taskBlock.setOnClickListener(view -> {
                            startActivity(new Intent(this, ViewTaskDetailed.class));
                        });
                    }
                }
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        taskViewPointer = taskList.indexOfChild(v);
        addMenu(menu);
    }

    private void addMenu(ContextMenu menu) {
        menu.setHeaderTitle(R.string.Task_action);
        menu.add(R.string.Change_task);
        menu.add(R.string.Delete_task);
        menu.add(R.string.Close);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (String.valueOf(item.getTitle())) {
            case "Change task":
            case "Изменить задачу":
                startActivity(new Intent(this, TaskChange.class));
                break;
            case "Delete task":
            case "Удалить задачу":
                TaskActionController.deleteTask();
                break;
        }
        return true;
    }

    public void goAddTask(View view) {
        startActivity(new Intent(this, AddTask.class));
    }

    public void taskComplete(View view) {
        final LinearLayout block = (LinearLayout) view.getParent();
        final LinearLayout list = (LinearLayout) block.getParent();
        if (tasks.get(list.indexOfChild(block)).isActivated()) {
            tasks.get(list.indexOfChild(block)).setActivated(false);
        } else {
            tasks.get(list.indexOfChild(block)).setActivated(true);
        }
    }
}