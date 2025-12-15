package com.fic.tarea1.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.tarea1.R;
import com.fic.tarea1.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public interface OnTaskActionListener {
        void onTaskClick(Task task);
        void onDeleteClick(Task task);
    }

    private final List<Task> tasks = new ArrayList<>();
    private final OnTaskActionListener listener;

    public TaskAdapter(OnTaskActionListener listener) {
        this.listener = listener;
    }

    public void setTasks(List<Task> newTasks) {
        tasks.clear();
        if (newTasks != null) {
            tasks.addAll(newTasks);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_task_card, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);

        holder.tvTitulo.setText(task.task_title);
        holder.tvDescripcion.setText(task.task_description);
        holder.tvFecha.setText(task.created_at);
        holder.tvIsCompleted.setText(task.is_completed ? "Completada" : "Pendiente");
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onTaskClick(task);
        });

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onTaskClick(task);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(task);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDescripcion, tvFecha, tvIsCompleted;
        ImageButton btnDelete, btnEdit;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvIsCompleted = itemView.findViewById(R.id.tvIsCompleted);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
