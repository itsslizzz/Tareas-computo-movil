package com.fic.tarea1.controller;

import android.content.Context;
import android.util.Log;

import com.fic.tarea1.model.Task;
import com.fic.tarea1.model.TaskDao;
import com.fic.tarea1.model.TaskDataBase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskController {

    private final TaskDao taskDao;
    private final ExecutorService executorService;

    public TaskController(Context context) {
        TaskDataBase db = TaskDataBase.getInstance(context);
        taskDao = db.taskDao();
        executorService = Executors.newSingleThreadExecutor(); // ExecutorService para hilos de fondo
    }

    // Método para agregar tarea
    public void addTask(String title, String description, boolean isCompleted) {
        executorService.execute(() -> {
            try {
                Task task = new Task();
                task.task_title = title;
                task.task_description = description;
                task.created_at = getCurrentDateTime();
                task.is_completed = isCompleted;

                taskDao.insert(task);  // Realiza la operación en un hilo de fondo
                Log.i("TASK_SAVE", "La tarea se guardó correctamente");
            } catch (Exception e) {
                Log.e("TASK_ERROR", "Error al guardar la tarea", e);
            }
        });
    }

    // Método para obtener la fecha/hora actual
    private String getCurrentDateTime() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date());
    }

    // Obtener todas las tareas
    public void getAllTasks(TaskCallback callback) {
        executorService.execute(() -> {
            try {
                List<Task> tasks = taskDao.getAllTasks();
                callback.onSuccess(tasks);  // Callback para actualizar UI en el hilo principal
            } catch (Exception e) {
                Log.e("TASK_ERROR", "Error al obtener las tareas", e);
                callback.onError(e);
            }
        });
    }

    // Método para obtener una tarea por ID
    public Task getTaskById(int id) {
        return taskDao.getTaskById(id); // Obtiene la tarea de la base de datos por su ID
    }

    // Método para actualizar una tarea
    public void updateTask(Task task) {
        executorService.execute(() -> {
            try {
                taskDao.update(task);  // Actualiza la tarea en la base de datos
                Log.i("TASK_UPDATE", "La tarea se actualizó correctamente");
            } catch (Exception e) {
                Log.e("TASK_ERROR", "Error al actualizar la tarea", e);
            }
        });
    }

    // Método para eliminar tarea
    public void deleteTask(Task task) {
        executorService.execute(() -> {
            try {
                taskDao.delete(task);  // Elimina la tarea en un hilo de fondo
                Log.i("TASK_DELETE", "Tarea eliminada correctamente");
            } catch (Exception e) {
                Log.e("TASK_ERROR", "Error al eliminar la tarea", e);
            }
        });
    }

    // Interfaz de callback para obtener tareas
    public interface TaskCallback {
        void onSuccess(List<Task> tasks);
        void onError(Exception e);
    }
}
