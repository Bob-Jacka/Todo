package com.kirill.todo.tasks.core;

import static com.kirill.todo.tasks.core.TaskActionController.addTask;
import static com.kirill.todo.tasks.core.TaskSerializer.INSTANCE;
import static com.kirill.todo.tasks.data.GlobalSettings.empty_line;
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
import java.util.Arrays;
import java.util.List;

/**
 * Entity that responsible for saving and loading objects from memory
 */
public class Save_Module extends AppCompatActivity {

    public static String saveFileName;
    private final File saveFile = new File(saveFileName);
    private final TaskSerializer serializer = INSTANCE;

    /**
     * Save tasks in file by writing to memory
     */
    void save_TaskBlocks() {
        try {
            if (saveFile.length() != 0) {
                if (saveFile.delete()) {
                    save_TaskBlocks();
                } else {
                    Toast.makeText(this, "Error in delete save file", Toast.LENGTH_SHORT).show();
                }
            }
            final BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            for (AbstractTask at : tasks) {
                if (at == null) {
                    writer.write(empty_line);
                    writer.newLine();
                } else {
                    writer.write(serializer.task_serialize(at));
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            Toast.makeText(MainActivity.taskList.getContext(), R.string.SaveError, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(MainActivity.taskList.getContext(), "Error in save module", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Load tasks directly from memory
     */
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
            final List<Object> file_list = Arrays.asList(reader.lines().toArray());
            do {
                saveLine = (String) file_list.get(increment);
                if (saveLine != null && !saveLine.equals(empty_line)) {
                    AbstractTask task = serializer.task_deserialize(saveLine);
                    addTask(task);
                }
                increment++;
            } while (increment < file_list.size());
            reader.close();
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            Toast.makeText(MainActivity.taskList.getContext(), R.string.LoadError, Toast.LENGTH_SHORT).show();
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    private void load_task_list() {
        //
    }

    private void save_task_list() {
        //
    }
}