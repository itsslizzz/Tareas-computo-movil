package com.fic.tarea1.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.tarea1.R;
import com.fic.tarea1.model.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final List<History> historyList = new ArrayList<>();

    public void setHistoryList(List<History> newHistoryList) {
        historyList.clear();
        if (newHistoryList != null) {
            historyList.addAll(newHistoryList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activiry_history_card, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        History history = historyList.get(position);

        holder.tvAccion.setText(actionSpinner(history.action));
        holder.tvTaskName.setText(history.details);
        holder.tvFecha.setText(history.created_at);
    }

    private String actionSpinner(String action) {
        switch (action) {
            case "insert_task":
                return "Insertar Tarea";
            case "update_task":
                return "Actualizar Tarea";
            case "delete_task":
                return "Eliminar Tarea";
            default:
                return action;
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvAccion, tvTaskName, tvFecha;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAccion = itemView.findViewById(R.id.tvAccion);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }
    }
}