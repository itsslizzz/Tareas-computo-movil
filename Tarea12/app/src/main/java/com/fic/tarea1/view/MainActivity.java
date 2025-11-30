package com.fic.tarea1.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.tarea1.R;
import com.fic.tarea1.controller.TaskController;
import com.fic.tarea1.model.Task;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TaskController controller;
    private TaskAdapter adapter;

    private RecyclerView rvTasks;
    private CardView cardAddTask;
    private ImageButton ibAgregar;
    private EditText etNombreTask, etDescripcionTask;
    private CheckBox cbCompleted;
    private Button btnAgregarTarea;

    private Task taskToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new TaskController(this);
        rvTasks = findViewById(R.id.rvTasks);
        cardAddTask = findViewById(R.id.cardAddTask);
        ibAgregar = findViewById(R.id.ibAgregar);
        etNombreTask = findViewById(R.id.etNombreTask);
        etDescripcionTask = findViewById(R.id.etDescripcionTask);
        cbCompleted = findViewById(R.id.cbIsCompleted);
        btnAgregarTarea = findViewById(R.id.btnAgregarTarea);

        rvTasks.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TaskAdapter(new TaskAdapter.OnTaskActionListener() {
            @Override
            public void onTaskClick(Task task) {
                // Pasar el ID de la tarea para editar
                Intent intent = new Intent(MainActivity.this, UpdateTaskActivity.class);
                intent.putExtra(UpdateTaskActivity.EXTRA_TASK_ID, task.id);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Task task) {
                controller.deleteTask(task);
                recargarLista();
            }
        });

        rvTasks.setAdapter(adapter);

        recargarLista(); // Cargar tareas al iniciar la actividad

        ibAgregar.setOnClickListener(v -> {
            if (cardAddTask.getVisibility() == View.VISIBLE) {
                cardAddTask.setVisibility(View.GONE);
            } else {
                cardAddTask.setVisibility(View.VISIBLE);
                etNombreTask.setText("");
                etDescripcionTask.setText("");
                cbCompleted.setChecked(false);
                btnAgregarTarea.setText("Agregar tarea");
                taskToEdit = null;
            }
        });

        btnAgregarTarea.setOnClickListener(v -> {
            String titulo = etNombreTask.getText().toString().trim();
            String descripcion = etDescripcionTask.getText().toString().trim();
            boolean isCompleted = cbCompleted.isChecked();

            if (titulo.isEmpty()) {
                Toast.makeText(this, "El título no puede ir vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            if (taskToEdit != null) {
                taskToEdit.task_title = titulo;
                taskToEdit.task_description = descripcion;
                taskToEdit.is_completed = isCompleted;

                controller.updateTask(taskToEdit);
                recargarLista();
                cardAddTask.setVisibility(View.GONE);
            } else {
                controller.addTask(titulo, descripcion, isCompleted);
                recargarLista();
                cardAddTask.setVisibility(View.GONE);
            }
        });
    }

    private void recargarLista() {
        controller.getAllTasks(new TaskController.TaskCallback() {
            @Override
            public void onSuccess(List<Task> tasks) {
                adapter.setTasks(tasks);  // Actualizamos el adaptador con las tareas más recientes
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(MainActivity.this, "Error al cargar las tareas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

