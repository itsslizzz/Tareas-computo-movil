package com.fic.tarea1.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fic.tarea1.R;
import com.fic.tarea1.controller.TaskController;
import com.fic.tarea1.model.Task;

public class UpdateTaskActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID = "EXTRA_TASK_ID";
    private EditText etTaskTitle, etTaskDescription;
    private CheckBox cbTaskCompleted;
    private Button btnSaveTask;
    private TaskController taskController;
    private Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        // Inicializar vistas
        initViews();
        taskController = new TaskController(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            int taskId = intent.getIntExtra(EXTRA_TASK_ID, -1);
            if (taskId != -1) {
                // Obtener la tarea a editar
                currentTask = taskController.getTaskById(taskId);
            }
        }

        if (currentTask != null) {
            etTaskTitle.setText(currentTask.task_title);
            etTaskDescription.setText(currentTask.task_description);
            cbTaskCompleted.setChecked(currentTask.is_completed);
        } else {
            Toast.makeText(this, "Error al cargar la tarea", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Guardar cambios
        btnSaveTask.setOnClickListener(view -> {
            String title = etTaskTitle.getText().toString().trim();
            String description = etTaskDescription.getText().toString().trim();
            boolean isCompleted = cbTaskCompleted.isChecked();

            updateTask(title, description, isCompleted);
        });
    }

    // Método para actualizar la tarea
    private void updateTask(String title, String description, boolean isCompleted) {
        if (currentTask != null) {
            currentTask.task_title = title;
            currentTask.task_description = description;
            currentTask.is_completed = isCompleted;

            taskController.updateTask(currentTask);  // Actualiza la tarea en la base de datos

            Toast.makeText(this, "Tarea actualizada", Toast.LENGTH_SHORT).show();
            finish(); // Regresar a la pantalla anterior
        } else {
            Toast.makeText(this, "Error al actualizar la tarea", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {
        etTaskTitle = findViewById(R.id.etTaskTitle);
        etTaskDescription = findViewById(R.id.etTaskDescription);
        cbTaskCompleted = findViewById(R.id.cbTaskCompleted);
        btnSaveTask = findViewById(R.id.btnSaveTask);
    }
}
