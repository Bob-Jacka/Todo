package com.kirill.todo.tasks.pages;

import static com.kirill.todo.tasks.core.Save_Module.saveFileName;
import static com.kirill.todo.tasks.core.TaskActionController.loadTasks;
import static com.kirill.todo.tasks.core.TaskActionController.saveTasks;
import static com.kirill.todo.tasks.core.TaskActionController.taskViewPointer;
import static com.kirill.todo.tasks.data.GlobalSettings.SAVE_FILE_NAME;
import static com.kirill.todo.tasks.data.GlobalSettings.serializeKey;
import static com.kirill.todo.tasks.data.GlobalSettings.tasks;
import static com.kirill.todo.tasks.data.GlobalSettings.today;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kirill.todo.R;
import com.kirill.todo.tasks.core.TaskActionController;
import com.kirill.todo.tasks.data.AbstractTask;

import java.time.LocalDateTime;
import java.util.Iterator;

@SuppressLint("StaticFieldLeak")
public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addButton;
    private SwipeRefreshLayout srl;

    @SuppressLint("StaticFieldLeak")
    public static LinearLayout taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveFileName = getApplicationContext().getFilesDir().getAbsolutePath() + SAVE_FILE_NAME;
        taskList = findViewById(R.id.TasksList);
        addButton = findViewById(R.id.addButton);
        srl = findViewById(R.id.swiperefresh);
        loadTasks();
        initTasks();
        srl.setOnRefreshListener(() -> taskList.refreshDrawableState());
    }

    @Override
    protected void onStop() {
        saveTasks();
        super.onStop();
    }

    private void initTasks() {
        if (tasks.size() != 0) {
            final String dayOfWeek = LocalDateTime.now().getDayOfWeek().toString();
            final Iterator<AbstractTask> taskIterator = tasks.iterator();
            for (int i = 0; i < tasks.size(); i++) {
                if (taskIterator.hasNext()) {
                    AbstractTask taskToLoad = taskIterator.next();
                    if (taskToLoad.whichDaysOfWeek().contains(dayOfWeek)) {
                        LinearLayout taskBlock = (LinearLayout) taskList.getChildAt(i);
                        taskBlock.setVisibility(View.VISIBLE);

                        CheckBox checkBox = (CheckBox) taskBlock.getChildAt(0);
                        if (taskToLoad.whenActivated() == today && taskToLoad.checked()) {
                            checkBox.toggle();
                        }

                        TextView txtv1 = (TextView) taskBlock.getChildAt(1);
                        txtv1.setText(taskToLoad.taskName());

                        TextView txtv2 = (TextView) taskBlock.getChildAt(2);
                        txtv2.setText(String.valueOf(taskToLoad.type()));

                        registerForContextMenu(taskBlock);
                        checkBox.setOnClickListener((this::taskComplete));
                        taskBlock.setOnClickListener(view -> {
                            Intent intent = new Intent(this, ViewTaskDetailed.class);
                            intent.putExtra(serializeKey, tasks.get(taskList.indexOfChild(view)));
                            startActivity(intent);
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
                Intent intent = new Intent(this, TaskChange.class);
                intent.putExtra(serializeKey, tasks.get(taskViewPointer));
                startActivity(intent);
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
        LinearLayout block = (LinearLayout) view.getParent();
        LinearLayout list = (LinearLayout) block.getParent();
        AbstractTask logicTask = tasks.get(list.indexOfChild(block));
        final CheckBox checkBox = (CheckBox) view;
        if (checkBox.isChecked() && logicTask.whenActivated() != today) {
            logicTask.whenActivated(today);
            logicTask.checked(true);
        } else {
            logicTask.whenActivated(0);
            logicTask.checked(false);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            finalize();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        super.onBackPressed();
    }
}