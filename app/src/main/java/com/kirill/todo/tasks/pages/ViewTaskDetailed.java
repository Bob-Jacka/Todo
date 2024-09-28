package com.kirill.todo.tasks.pages;

import static com.kirill.todo.tasks.core.TaskActionController.capitalizeString;
import static com.kirill.todo.tasks.data.GlobalSettings.serializeKey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kirill.todo.R;
import com.kirill.todo.tasks.data.AbstractTask;

public class ViewTaskDetailed extends AppCompatActivity {

    private TextView hereIsName;
    private TextView hereIsDescription;
    private TextView hereIsType;
    private TextView hereIsSteps;
    private TextView hereIsCreatedAt;
    private Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_detailed);
        initFields();
        writeValues();
    }

    private void initFields() {
        hereIsName = findViewById(R.id.hereIsName);
        hereIsDescription = findViewById(R.id.hereIsDescription);
        hereIsType = findViewById(R.id.hereIsType);
        hereIsSteps = findViewById(R.id.hereIsSteps);
        hereIsCreatedAt = findViewById(R.id.hereIsCreatedAt);
        goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
    }

    private void writeValues() {
        final AbstractTask task = (AbstractTask) getIntent().getExtras().getSerializable(serializeKey);
        hereIsName.setText(task.taskName());
        hereIsDescription.setText(task.description());
        hereIsType.setText(capitalizeString(String.valueOf(task.type())));
        if (task.steps().size() != 0) {
            if (task.steps() != null) {
                task.steps().forEach(x -> {
                    if (!x.equals("null")) {
                        hereIsSteps.append(x + "\n");
                    }
                });
            }
        } else {
            hereIsSteps.setVisibility(View.GONE);
        }
        hereIsCreatedAt.setText(task.createdAt());
    }
}