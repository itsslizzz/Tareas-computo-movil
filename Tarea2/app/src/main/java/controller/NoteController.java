package controller;

import android.content.Context;
import android.util.Log;

import java.util.List;

import model.AppDataBase;
import model.Category;
import model.Note;
import model.NoteDao;

public class NoteController {
    private  final String TAG = "NoteController";

    private  NoteDao noteDao;
    public NoteController (Context context){
        AppDataBase dataBase= AppDataBase.getInstance(context);
        noteDao= dataBase.noteDao();
    }

    public  void insertNote(String noteTitle, int categoryId, String noteContent, String createdAt) {
        try {
            Note note = new Note();
            note.note_title = noteTitle;
            note.category_id = categoryId;
            note.note_content = noteContent;
            note.created_at = createdAt;
            noteDao.insertNote(note);
            Log.i(TAG, "addNote: Nota guardada");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

public List <Note> getAllNotes(){
        return noteDao.getAllNotes();
}

public List<Note> getAllNotesByCateg(int categoryId) {
        return noteDao.getAllNotesByCateg(categoryId);
    }

public List<Note> getNoteByTitleOrContent (String text){
        return noteDao.getNoteByTitleOrContent(text);
}





}




