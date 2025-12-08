package view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fic.tarea2.R;
import com.fic.tarea2.view.MainActivity;

import java.util.List;

import controller.CategoryController;
import model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList;
    private CategoryController categoryController;

    public NoteAdapter(List<Note> noteList, Context context) {
        this.noteList = noteList;
        this.categoryController = new CategoryController(context);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.tvTitle.setText(note.note_title);
        holder.tvContent.setText(note.note_content);
        holder.tvDate.setText(note.created_at);
        String nombreCat = categoryController.getCategoryNameById(note.category_id);
        holder.tvCategory.setText(nombreCat);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent, tvDate, tvCategory;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvContent = itemView.findViewById(R.id.tvNoteContent);
            tvDate = itemView.findViewById(R.id.tvCreatedAt);
            tvCategory = itemView.findViewById(R.id.tvCategoryName);
        }
    }
}