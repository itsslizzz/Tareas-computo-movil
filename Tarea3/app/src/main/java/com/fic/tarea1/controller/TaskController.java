package com.fic.tarea1.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
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
    private final Handler mainHandler;
    // IMPORTANTE: Definir HistoryController
    private final HistoryController historyController;

    public TaskController(Context context) {
        TaskDataBase db = TaskDataBase.getInstance(context);
        taskDao = db.taskDao();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
        historyController = new HistoryController(context);
    }

    public interface OperationCallback {
        void onSuccess();
        void onError(Exception e);
    }

    public interface TaskCallback {
        void onSuccess(List<Task> tasks);
        void onError(Exception e);
    }

    public interface SingleTaskCallback {
        void onSuccess(Task task);
        void onError(Exception e);
    }

    public void añadirTarea(String title, String description, boolean isCompleted, OperationCallback callback) {
        executorService.execute(() -> {
            try {
                Task task = new Task();
                task.task_title = title;
                task.task_description = description;
                task.created_at = getCurrentDateTime();
                task.is_completed = isCompleted;

                taskDao.insert(task);
                historyController.insertarHistorial("insert_task", "Título: " + title);

                mainHandler.post(() -> callback.onSuccess());
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e));
            }
        });
    }


    public void obtenerTodasTareas(TaskCallback callback) {
        executorService.execute(() -> {
            try {
                List<Task> tasks = taskDao.getAllTasks();
                mainHandler.post(() -> callback.onSuccess(tasks));
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e));
            }
        });
    }

    public void obtenerPorId(int id, SingleTaskCallback callback) {
        executorService.execute(() -> {
            try {
                Task task = taskDao.getTaskById(id);
                mainHandler.post(() -> callback.onSuccess(task));
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e));
            }
        });
    }

    public void actualizarTarea(Task task, OperationCallback callback) {
        executorService.execute(() -> {
            try {
                taskDao.update(task);
                historyController.insertarHistorial("update_task", "Título: " + task.task_title);
                mainHandler.post(() -> callback.onSuccess());
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e));
            }
        });
    }

    public void eliminarTarea(Task task, OperationCallback callback) {
        executorService.execute(() -> {
            try {
                String titulo = task.task_title;
                taskDao.delete(task);
                historyController.insertarHistorial("delete_task", "Título: " + titulo);
                mainHandler.post(() -> callback.onSuccess());
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e));
            }
        });
    }

    private String getCurrentDateTime() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date());
    }
}