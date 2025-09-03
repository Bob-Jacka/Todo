package com.kirill.todo.tasks.pages;

import static com.kirill.todo.tasks.core.ExtensionsKt.getTask;
import static com.kirill.todo.tasks.core.TaskActionController.addTask;
import static com.kirill.todo.tasks.core.TaskActionController.capitalizeString;
import static com.kirill.todo.tasks.core.TaskActionController.getData;
import static com.kirill.todo.tasks.core.TaskActionController.saveTasks;
import static com.kirill.todo.tasks.data.GlobalSettings.serializeKeyStep;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kirill.todo.R;
import com.kirill.todo.tasks.data.AbstractTask;
import com.kirill.todo.tasks.data.TasksEnum;

import java.util.List;

/**
 * Page for add task to list
 */
public class AddTask extends AppCompatActivity {

    private AbstractTask current_task;
    private String newName, newTaskDescribe;
    private Button changeType;
    private TasksEnum newType;
    private EditText descriptionField, changeName;
    private LinearLayout buttonGroup;
    private List<String> whichDaysMap;
    private Button addStep;
    private String step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        initActivity();
        changeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newName = editable.toString();
            }
        });
        descriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                newTaskDescribe = editable.toString();
            }
        });
    }

    public void changeTask(View v) {
        if (newType != null) {
            current_task = getTask(newType);
            current_task.taskName(newName);
            current_task.type(newType);
            checkWhichDays();
            current_task.whichDaysOfWeek(whichDaysMap);
            if (newTaskDescribe != null) {
                current_task.description(newTaskDescribe);
            } else {
                current_task.description("");
            }
            if (step != null) {
                if (!step.isEmpty()) {
                    current_task.steps().add(step);
                }
            }
            current_task.createdAt(getData());
            accept();
        } else {
            Toast.makeText(this, "error in type", Toast.LENGTH_SHORT).show();
        }
    }

    private void initActivity() {
        changeName = findViewById(R.id.ChangeName);
        changeType = findViewById(R.id.ChangeType);
        descriptionField = findViewById(R.id.DescriptionField);
        buttonGroup = findViewById(R.id.ButtonGroup);
        addStep = findViewById(R.id.AddSteps);
        registerForContextMenu(changeType);
        step = getStepIfExist();
        addStep.setOnClickListener(view -> {
            startActivity(new Intent(this, Steps.class));
        });
    }

    private void accept() {
        if (current_task != null && !newName.isEmpty()) {
            addTask(current_task);
            saveTasks();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, R.string.Change_task, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle(R.string.Task_type);
        for (TasksEnum type : TasksEnum.values()) {
            menu.add(capitalizeString(String.valueOf(type)));
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        String val = String.valueOf(item.getTitle());
        switch (val) {
            case "Read":
                contextClick(val, TasksEnum.READ);
                break;
            case "Sport":
                contextClick(val, TasksEnum.SPORT);
                break;
            case "Work":
                contextClick(val, TasksEnum.WORK);
                break;
            case "Other":
                contextClick(val, TasksEnum.OTHER);
                break;
            case "Medical":
                contextClick(val, TasksEnum.MEDICAL);
                break;
            case "Education":
                contextClick(val, TasksEnum.EDUCATION);
                break;
        }
        return true;
    }

    private void contextClick(String val, TasksEnum type) {
        newType = type;
        changeType.setBackgroundColor(Color.GREEN);
        changeType.setText(val);
    }

    private void checkWhichDays() {
        final List<String> whichDays = current_task.whichDaysOfWeek();
        for (int i = 1; i < buttonGroup.getChildCount(); i++) {
            RadioButton btn = (RadioButton) buttonGroup.getChildAt(i);
            if (btn.isChecked()) {
                whichDays.add(((String) btn.getText()).toUpperCase());
            }
        }
        whichDaysMap = whichDays;
    }

    @Nullable
    private String getStepIfExist() {
        try {
            final String step = (String) getIntent().getExtras().getSerializable(serializeKeyStep);
            if (step != null) {
                return step;
            }
        } catch (NullPointerException e) {
            return null;
        }
        return null;
    }
}