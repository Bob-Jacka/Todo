package com.kirill.todo.tasks.pages;

import static com.kirill.todo.tasks.core.TaskActionController.capitalizeString;
import static com.kirill.todo.tasks.core.TaskActionController.getTask;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kirill.todo.R;
import com.kirill.todo.tasks.core.TaskActionController;
import com.kirill.todo.tasks.data.AbstractTask;
import com.kirill.todo.tasks.data.TasksEnum;

public class TaskChange extends AppCompatActivity {

    private EditText taskName, taskDescription;
    private Button acceptBtn, newTaskTypeBtn;
    private AbstractTask taskToChange;
    private String newTaskName, newTaskDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_change);
        initAll();
        initFields();
        initListeners();
    }

    private void initAll() {
        taskName = findViewById(R.id.TaskName);
        newTaskTypeBtn = findViewById(R.id.TaskType);
        taskDescription = findViewById(R.id.TaskDescription);
        acceptBtn = findViewById(R.id.AcceptChange);
        taskToChange = getTask();
    }

    private void initFields() {
        taskName.setText(taskToChange.getTaskName());
        newTaskTypeBtn.setText(String.valueOf(taskToChange.getType()));
        taskDescription.setText(taskToChange.getDescription());
        registerForContextMenu(newTaskTypeBtn);
        newTaskName = taskName.getText().toString();
        newTaskDescription = taskDescription.getText().toString();
    }

    private void initListeners() {
        taskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newTaskName = editable.toString();
            }
        });
        taskDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newTaskDescription = editable.toString();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(R.string.SelectType);
        for (TasksEnum enumy : TasksEnum.values()) {
            menu.add(capitalizeString(String.valueOf(enumy)));
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        String val = String.valueOf(item.getTitle());
        switch (val) {
            case "Empty":
                contextClick(val, TasksEnum.EMPTY);
                break;
            case "Work":
                contextClick(val, TasksEnum.WORK);
                break;
            case "Physical":
                contextClick(val, TasksEnum.PHYSICAL);
                break;
            case "Read":
                contextClick(val, TasksEnum.READ);
                break;
        }
        return false;
    }

    public void acceptChanges(View v) {
        if (!newTaskDescription.equals("") && !newTaskName.equals("") && newTaskTypeBtn != null) {
            taskToChange.setDescription(newTaskDescription);
            taskToChange.setTaskName(newTaskName);
            TaskActionController.deleteTask();
            TaskActionController.addTask(taskToChange);
            TaskActionController.saveTasks();
            Intent goMain = new Intent(this, MainActivity.class);
            startActivity(goMain);
        }
    }

    private void contextClick(String val, TasksEnum type) {
        taskToChange.setType(type);
        newTaskTypeBtn.setBackgroundColor(Color.GREEN);
        newTaskTypeBtn.setText(val);
    }
}