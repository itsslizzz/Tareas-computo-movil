package com.fic.tarea1.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fic.tarea1.R;
import com.fic.tarea1.controller.HistoryController;
import com.fic.tarea1.controller.HistoryController.HistoryListCallback;
import java.util.List;
import java.util.Locale;

public class History extends AppCompatActivity {

    private HistoryController controller;
    private HistoryAdapter adapter;
    private RecyclerView rvHistory;
    private Spinner spnAccion;
    private CalendarView calendarView;
    private String selectedDateFilter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        controller = new HistoryController(this);
        rvHistory = findViewById(R.id.rvHistory);
        spnAccion = findViewById(R.id.SpnAccion);
        calendarView = findViewById(R.id.calendarView);

        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter();
        rvHistory.setAdapter(adapter);

        setupActionSpinner();
        setupCalendarView();
        recargarLista(null, null);
    }

    private void setupActionSpinner() {
        String[] acciones = {"Todas las acciones", "Insertar", "Actualizar", "Eliminar"};
        final String[] internalCodes = {null, "insert_task", "update_task", "delete_task"};

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, acciones);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAccion.setAdapter(spinnerAdapter);

        spnAccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String actionCode = internalCodes[position];
                recargarLista(actionCode, selectedDateFilter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupCalendarView() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String monthStr = String.format(Locale.getDefault(), "%02d", month + 1);
            String dayStr = String.format(Locale.getDefault(), "%02d", dayOfMonth);
            selectedDateFilter = year + "-" + monthStr + "-" + dayStr;

            String[] internalCodes = {null, "insert_task", "update_task", "delete_task"};
            recargarLista(internalCodes[spnAccion.getSelectedItemPosition()], selectedDateFilter);
        });
    }

    private void recargarLista(String actionCode, String dateFilter) {
        if (dateFilter != null) {
            controller.obtenerHistorialPorFecha(dateFilter, new HistoryListCallback() {
                @Override
                public void onSuccess(List<com.fic.tarea1.model.History> historyList) {
                    adapter.setHistoryList(historyList);
                }
                @Override
                public void onError(Exception e) {
                    Toast.makeText(History.this, "Error al cargar historial por fecha", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (actionCode != null) {
            controller.obtenerHistorialPorAccion(actionCode, new HistoryListCallback() {
                @Override
                public void onSuccess(List<com.fic.tarea1.model.History> historyList) {
                    adapter.setHistoryList(historyList);
                }
                @Override
                public void onError(Exception e) {
                    Toast.makeText(History.this, "Error al cargar historial por acción", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            controller.obtenerTodoHistorial(new HistoryListCallback() {
                public void onSuccess(List<com.fic.tarea1.model.History> historyList) {
                    adapter.setHistoryList(historyList);
                }
                @Override
                public void onError(Exception e) {
                    Toast.makeText(History.this, "Error al cargar historial", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}