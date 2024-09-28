package com.kirill.todo.tasks.pages;

import static com.kirill.todo.tasks.core.ExtensionsKt.getTask;
import static com.kirill.todo.tasks.core.TaskActionController.addTask;
import static com.kirill.todo.tasks.core.TaskActionController.capitalizeString;
import static com.kirill.todo.tasks.core.TaskActionController.saveTasks;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kirill.todo.R;
import com.kirill.todo.tasks.data.AbstractTask;
import com.kirill.todo.tasks.data.TasksEnum;

import java.util.Calendar;
import java.util.List;

public class AddTask extends AppCompatActivity {

    private AbstractTask currTask;
    private String newName, newTaskDescribe;
    private Button changeType;
    private TasksEnum newType;
    private EditText descriptionField, changeName;
    private LinearLayout buttonGroup, addSteps;
    private List<String> whichDaysMap;

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
        addSteps.setOnClickListener((view) -> {
                    addSteps.addView(new TextView(this));
                }
        );
    }

    public void changeTask(View v) {
        currTask = getTask(newType);
        currTask.taskName(newName);
        currTask.type(newType);
        checkWhichDays();
        currTask.whichDaysOfWeek(whichDaysMap);
        if (newTaskDescribe != null) {
            currTask.description(newTaskDescribe);
        } else {
            currTask.description("");
        }
        accept();
    }

    private void initActivity() {
        changeName = findViewById(R.id.ChangeName);
        changeType = findViewById(R.id.ChangeType);
        descriptionField = findViewById(R.id.DescriptionField);
        buttonGroup = findViewById(R.id.ButtonGroup);
        addSteps = findViewById(R.id.AddStep);
        registerForContextMenu(changeType);
    }

    private void accept() {
        if (currTask != null && !newName.equals("")) {
            currTask.createdAt(String.valueOf(Calendar.getInstance().getTime()));
            addTask(currTask);
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
            case "Education":
                contextClick(val, TasksEnum.EDUCATION);
        }
        return true;
    }

    private void contextClick(String val, TasksEnum type) {
        newType = type;
        changeType.setBackgroundColor(Color.GREEN);
        changeType.setText(val);
    }

    private void checkWhichDays() {
        final List<String> whichDays = currTask.whichDaysOfWeek();
        for (int i = 1; i < buttonGroup.getChildCount(); i++) {
            RadioButton btn = (RadioButton) buttonGroup.getChildAt(i);
            if (btn.isChecked()) {
                whichDays.add(((String) btn.getText()).toUpperCase());
            }
        }
        whichDaysMap = whichDays;
    }
}