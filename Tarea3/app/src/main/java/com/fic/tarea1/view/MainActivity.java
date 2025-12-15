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
import com.fic.tarea1.controller.TaskController.OperationCallback;
import com.fic.tarea1.controller.TaskController.TaskCallback;
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
    private ImageButton ibHistorial;

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
        ibHistorial = findViewById(R.id.ibHistorial);

        rvTasks.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TaskAdapter(new TaskAdapter.OnTaskActionListener() {
            @Override
            public void onTaskClick(Task task) {
                Intent intent = new Intent(MainActivity.this, UpdateTaskActivity.class);
                intent.putExtra(UpdateTaskActivity.EXTRA_TASK_ID, task.id);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Task task) {
                controller.eliminarTarea(task, new OperationCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
                        recargarLista();
                    }
                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(MainActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        rvTasks.setAdapter(adapter);

        ibAgregar.setOnClickListener(v -> {
            cardAddTask.setVisibility(cardAddTask.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            limpiarFormulario();
        });

        ibHistorial.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, History.class);
            startActivity(intent);
        });

        btnAgregarTarea.setOnClickListener(v -> {
            String titulo = etNombreTask.getText().toString().trim();
            String descripcion = etDescripcionTask.getText().toString().trim();
            boolean isCompleted = cbCompleted.isChecked();

            if (titulo.isEmpty()) {
                Toast.makeText(this, "El título no puede ir vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            controller.añadirTarea(titulo, descripcion, isCompleted, new OperationCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(MainActivity.this, "Tarea guardada", Toast.LENGTH_SHORT).show();
                    recargarLista();
                    cardAddTask.setVisibility(View.GONE);
                    limpiarFormulario();
                }
                @Override
                public void onError(Exception e) {
                    Toast.makeText(MainActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recargarLista();
    }

    private void recargarLista() {
        controller.obtenerTodasTareas(new TaskCallback() {
            @Override
            public void onSuccess(List<Task> tasks) {
                adapter.setTasks(tasks);
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(MainActivity.this, "Error al cargar tareas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limpiarFormulario() {
        etNombreTask.setText("");
        etDescripcionTask.setText("");
        cbCompleted.setChecked(false);
    }
}