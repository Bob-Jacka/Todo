package com.kirill.todo.tasks.pages;

import static com.kirill.todo.tasks.core.TaskActionController.capitalizeString;
import static com.kirill.todo.tasks.data.GlobalSettings.factory;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kirill.todo.R;
import com.kirill.todo.tasks.core.TaskActionController;
import com.kirill.todo.tasks.data.AbstractTask;
import com.kirill.todo.tasks.data.TasksEnum;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {

    private AbstractTask currTask;
    private String newName, taskDescribe;
    private Button changeType;
    private TasksEnum newType;
    private EditText descriptionField, changeName;

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
                taskDescribe = editable.toString();
            }
        });
    }

    public void changeTask(View v) {
        currTask = factory.getTask(newType);
        currTask.setTaskName(newName);
        currTask.setType(newType);
        currTask.setSubtaskCount(0);
        if (taskDescribe != null) {
            currTask.setDescription(taskDescribe.isEmpty() ? " " : taskDescribe);
        }
        accept();
    }

    private void initActivity() {
        changeName = findViewById(R.id.ChangeName);
        changeType = findViewById(R.id.ChangeType);
        descriptionField = findViewById(R.id.DescriptionField);
        registerForContextMenu(changeType);
    }

    private void accept() {
        if (currTask != null && !newName.equals("")) {
            currTask.setCreatedAt(String.valueOf(Calendar.getInstance().getTime()));
            Intent goMain = new Intent(this, MainActivity.class);
            TaskActionController.addTask(currTask);
            TaskActionController.saveTasks();
            startActivity(goMain);
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
            case "Physical":
                contextClick(val, TasksEnum.PHYSICAL);
                break;
            case "Work":
                contextClick(val, TasksEnum.WORK);
                break;
            case "Empty":
                contextClick(val, TasksEnum.EMPTY);
                break;
        }
        return true;
    }

    private void contextClick(String val, TasksEnum type) {
        newType = type;
        changeType.setBackgroundColor(Color.GREEN);
        changeType.setText(val);
    }
}