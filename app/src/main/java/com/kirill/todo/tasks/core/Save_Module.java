package com.kirill.todo.tasks.core;

import static com.kirill.todo.tasks.core.TaskActionController.addTask;
import static com.kirill.todo.tasks.core.TaskSerializer.INSTANCE;
import static com.kirill.todo.tasks.data.GlobalSettings.maxTasks;
import static com.kirill.todo.tasks.data.GlobalSettings.tasks;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.kirill.todo.R;
import com.kirill.todo.tasks.data.AbstractTask;
import com.kirill.todo.tasks.pages.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Save_Module extends AppCompatActivity {

    public static String saveFileName;
    private final File saveFile = new File(saveFileName);
    private final TaskSerializer serializer = INSTANCE;

    void save_TaskBlocks() {
        try {
            if (saveFile.length() != 0) {
                saveFile.delete();
                save_TaskBlocks();
            }
            final BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            for (AbstractTask at : tasks) {
                if (at == null) {
                    writer.write("0");
                    writer.newLine();
                } else {
                    writer.write(serializer.serialize(at));
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            Toast.makeText(MainActivity.taskList.getContext(), R.string.SaveError, Toast.LENGTH_SHORT).show();
        }
    }

    void load_TasksArray() {
        try {
            if (saveFile.length() != 0L && saveFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(saveFile));
                tasks = new ArrayList<>();
                load_SaveFile(reader);
            }
        } catch (IOException e) {
            Toast.makeText(MainActivity.taskList.getContext(), R.string.LoadError, Toast.LENGTH_SHORT).show();
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    private void load_SaveFile(@NonNull BufferedReader reader) {
        int increment = 0;
        String saveLine;
        try {
            do {
                saveLine = reader.readLine();
                if (saveLine != null && !saveLine.equals("0")) {
                    AbstractTask task = serializer.deserialize(saveLine);
                    addTask(task);
                }
                increment++;
            } while (increment < maxTasks);
            reader.close();
        } catch (IOException e) {
            e.getStackTrace();
            Toast.makeText(MainActivity.taskList.getContext(), R.string.LoadError, Toast.LENGTH_SHORT).show();
        }
    }
}