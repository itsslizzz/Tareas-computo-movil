package com.fic.tarea1.view;

import android.os.Bundle;
import android.view.View;
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

    private EditText etNombreTask, etDescripcionTask;
    private CheckBox cbCompleted;
    private Button btnActualizar;

    private TaskController controller;
    private Task currentTask;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        etNombreTask = findViewById(R.id.etTaskTitle);
        etDescripcionTask = findViewById(R.id.etTaskDescription);
        cbCompleted = findViewById(R.id.cbTaskCompleted);
        btnActualizar = findViewById(R.id.btnSaveTask);

        controller = new TaskController(this);

        if (getIntent().hasExtra(EXTRA_TASK_ID)) {
            taskId = getIntent().getIntExtra(EXTRA_TASK_ID, -1);
        } else {
            finish();
            return;
        }

        cargarDatosDeTarea(taskId);

        btnActualizar.setOnClickListener(v -> guardarCambios());
    }

    private void cargarDatosDeTarea(int id) {
        controller.obtenerPorId(id, new TaskController.SingleTaskCallback() {
            @Override
            public void onSuccess(Task task) {
                currentTask = task;
                if (currentTask != null) {
                    etNombreTask.setText(currentTask.task_title);
                    etDescripcionTask.setText(currentTask.task_description);
                    cbCompleted.setChecked(currentTask.is_completed);
                } else {
                    Toast.makeText(UpdateTaskActivity.this, "Tarea no encontrada", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(UpdateTaskActivity.this, "Error al cargar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarCambios() {
        if (currentTask == null) return;

        String nuevoTitulo = etNombreTask.getText().toString().trim();
        if (nuevoTitulo.isEmpty()) {
            Toast.makeText(this, "El título es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }

        currentTask.task_title = nuevoTitulo;
        currentTask.task_description = etDescripcionTask.getText().toString().trim();
        currentTask.is_completed = cbCompleted.isChecked();

        controller.actualizarTarea(currentTask, new TaskController.OperationCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(UpdateTaskActivity.this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(UpdateTaskActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}