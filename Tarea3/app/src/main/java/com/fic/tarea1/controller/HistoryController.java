package com.fic.tarea1.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.fic.tarea1.model.History;
import com.fic.tarea1.model.HistoryDao;
import com.fic.tarea1.model.HistoryDataBase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryController {
    private final HistoryDao historyDao;
    private final ExecutorService executorService;
    private final Handler mainHandler;

    public interface HistoryListCallback {
        void onSuccess(List<History> historyList);
        void onError(Exception e);
    }

    public HistoryController(Context context) {
        HistoryDataBase historyDataBase = HistoryDataBase.getInstance(context);
        historyDao = historyDataBase.historyDao();
        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    private String getCurrentDateTime() {
        SimpleDateFormat Fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return Fecha.format(new Date());
    }

    public void insertarHistorial(String accion, String details) {
        if (accion == null || accion.trim().isEmpty()) {
            return;
        }
        executorService.execute(() -> {
            try {
                History history = new History();
                history.action = accion.trim();
                history.created_at = getCurrentDateTime();
                history.details = (details != null ? details : "N/A");
                historyDao.insert(history);
                Log.i("HISTORY_SAVE", "Historia guardada: " + accion);
            } catch (Exception e) {
                Log.e("HISTORY_ERROR", "Error al guardar historial", e);
            }
        });
    }

    public void obtenerTodoHistorial(HistoryListCallback callback) {
        executorService.execute(() -> {
            try {
                List<History> historyList = historyDao.getAllHistory();
                mainHandler.post(() -> callback.onSuccess(historyList));
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e));
            }
        });
    }

    public void obtenerHistorialPorAccion(String action, HistoryListCallback callback) {
        executorService.execute(() -> {
            try {
                List<History> historyList = historyDao.getHistoryByAction(action);
                mainHandler.post(() -> callback.onSuccess(historyList));
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e));
            }
        });
    }

    public void obtenerHistorialPorFecha(String date, HistoryListCallback callback) {
        executorService.execute(() -> {
            try {
                List<History> historyList = historyDao.getHistoryByDate(date);
                mainHandler.post(() -> callback.onSuccess(historyList));
            } catch (Exception e) {
                mainHandler.post(() -> callback.onError(e));
            }
        });
    }
}