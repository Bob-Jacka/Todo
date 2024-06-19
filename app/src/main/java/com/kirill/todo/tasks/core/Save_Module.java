package com.kirill.todo.tasks.core;

import static com.kirill.todo.tasks.data.GlobalSettings.SAVE_FILE_NAME;
import static com.kirill.todo.tasks.data.GlobalSettings.maxTasks;
import static com.kirill.todo.tasks.data.GlobalSettings.saveKey;
import static com.kirill.todo.tasks.data.GlobalSettings.tasks;
import static com.kirill.todo.tasks.data.GlobalSettings.today;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirill.todo.R;
import com.kirill.todo.tasks.data.AbstractTask;
import com.kirill.todo.tasks.pages.MainActivity;
import com.kirill.todo.tasks.tasksByTypes.EmptyTask;
import com.kirill.todo.tasks.tasksByTypes.ReadTask;
import com.kirill.todo.tasks.tasksByTypes.SportTask;
import com.kirill.todo.tasks.tasksByTypes.WorkTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Save_Module extends AppCompatActivity {

    public static String saveFileName;
    private File saveFile = new File(saveFileName);
    private static final ObjectMapper mapper = new ObjectMapper();

    void save_TaskBlocks() {
        try {
            if (saveFile.length() != 0) {
                saveFile.delete();
                save_TaskBlocks();
            }
            final BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            save_ControlVars(writer);
            writer.newLine();
            for (AbstractTask at : tasks) {
                if (at == null) {
                    writer.write("0");
                    writer.newLine();
                } else {
                    writer.write(mapper.writeValueAsString(at));
                    writer.newLine();
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Toast.makeText(MainActivity.taskList.getContext(), R.string.SaveError, Toast.LENGTH_SHORT).show();
        }
    }

    void load_TasksArray() {
        try {
            if (saveFile.length() != 0L && saveFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(saveFile));
                load_ControlVars(reader);
                tasks = new ArrayList<>();
                load_SaveFile(reader);
            }
        } catch (IOException e) {
            Toast.makeText(MainActivity.taskList.getContext(), R.string.LoadError, Toast.LENGTH_SHORT).show();
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    private void save_ControlVars(@NonNull BufferedWriter writer) {
        try {
            writer.write(saveKey);
            writer.write(" ");
            writer.write(SAVE_FILE_NAME);
            writer.write(" ");
            writer.write(today);
        } catch (IOException e) {
            Toast.makeText(MainActivity.taskList.getContext(), R.string.SaveError, Toast.LENGTH_SHORT).show();
        }
    }

    private void load_ControlVars(@NonNull BufferedReader reader) {
        try {
            String[] params = reader.readLine().split(" ");
            saveKey = params[0];
            SAVE_FILE_NAME = params[1];
        } catch (IOException e) {
            Toast.makeText(MainActivity.taskList.getContext(), R.string.LoadError, Toast.LENGTH_SHORT).show();
        }
    }

    private void load_SaveFile(@NonNull BufferedReader reader) {
        int increment = 0;
        String saveLine;
        try {
            do {
                saveLine = reader.readLine();
                if (saveLine != null && !saveLine.equals("0")) {
                    switch (mapper.readValue(saveLine, JsonNode.class).get("type").asText()) {
                        case "EMPTY":
                            tasks.add(mapper.readValue(saveLine, EmptyTask.class));
                            break;
                        case "READ":
                            tasks.add(mapper.readValue(saveLine, ReadTask.class));
                            break;
                        case "PHYSICAL":
                            tasks.add(mapper.readValue(saveLine, SportTask.class));
                            break;
                        case "WORK":
                            tasks.add(mapper.readValue(saveLine, WorkTask.class));
                            break;
                    }
                }
                increment++;
            } while (increment < maxTasks);
            reader.close();
        } catch (IOException e) {
            Toast.makeText(MainActivity.taskList.getContext(), R.string.LoadError, Toast.LENGTH_SHORT).show();
        }
    }
}
