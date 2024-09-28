package com.kirill.todo.tasks.pages;

import static com.kirill.todo.tasks.core.TaskActionController.addTask;
import static com.kirill.todo.tasks.core.TaskActionController.capitalizeString;
import static com.kirill.todo.tasks.core.TaskActionController.deleteTask;
import static com.kirill.todo.tasks.core.TaskActionController.saveTasks;
import static com.kirill.todo.tasks.data.GlobalSettings.serializeKey;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kirill.todo.R;
import com.kirill.todo.tasks.data.AbstractTask;
import com.kirill.todo.tasks.data.TasksEnum;

public class TaskChange extends AppCompatActivity {

    private EditText taskName, taskDescription;
    private Button acceptBtn, newTaskTypeBtn;
    private AbstractTask taskToChange;
    private String newTaskName, newTaskDescription;
    private LinearLayout buttonGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_change);
        initAll();
        initFields();
        initListeners();
        initWhichDays();
    }

    private void initAll() {
        taskName = findViewById(R.id.TaskName);
        newTaskTypeBtn = findViewById(R.id.TaskType);
        taskDescription = findViewById(R.id.TaskDescription);
        acceptBtn = findViewById(R.id.AcceptChange);
        buttonGroup = findViewById(R.id.ButtonGroup);
        taskToChange = (AbstractTask) getIntent().getExtras().getSerializable(serializeKey);
    }

    private void initFields() {
        taskName.setText(taskToChange.taskName());
        newTaskTypeBtn.setText(String.valueOf(taskToChange.type()));
        taskDescription.setText(taskToChange.description());
        registerForContextMenu(newTaskTypeBtn);
        newTaskName = taskName.getText().toString();
        newTaskDescription = taskToChange.description();
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
        for (int i = 1; i < 7; i++) {
            ((RadioButton) buttonGroup.getChildAt(i)).setOnClickListener(view -> {
                taskToChange.whichDaysOfWeek().remove((String) (((RadioButton) view).getText()));
            });
        }
    }

    private void initWhichDays() {
        int daysCounter = 0;
        while (daysCounter < taskToChange.whichDaysOfWeek().size()) {
            String day = capitalizeString(taskToChange.whichDaysOfWeek().get(daysCounter));
            for (int i = 1; i < 7; i++) {
                RadioButton button = (RadioButton) buttonGroup.getChildAt(i);
                if (button.getText().equals(day)) {
                    button.toggle();
                }
            }
            daysCounter++;
        }
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
        TasksEnum val = TasksEnum.valueOf(item.getTitle().toString().toUpperCase());
        switch (val) {
            case OTHER:
                contextClick(TasksEnum.OTHER);
                break;
            case WORK:
                contextClick(TasksEnum.WORK);
                break;
            case SPORT:
                contextClick(TasksEnum.SPORT);
                break;
            case READ:
                contextClick(TasksEnum.READ);
                break;
            case MEDICAL:
                contextClick(TasksEnum.MEDICAL);
                break;
            case EDUCATION:
                contextClick(TasksEnum.EDUCATION);
                break;
        }
        return false;
    }

    public void acceptChanges(View v) {
        if (!newTaskName.equals("") && newTaskTypeBtn != null) {
            taskToChange.description(newTaskDescription);
            taskToChange.taskName(newTaskName);
            deleteTask();
            addTask(taskToChange);
            saveTasks();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void contextClick(TasksEnum type) {
        taskToChange.type(type);
        newTaskTypeBtn.setBackgroundColor(Color.GREEN);
        newTaskTypeBtn.setText(String.valueOf(type));
    }
}