package model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao

public interface NoteDao {
    @Insert
    void insertNote (Note note);

    @Query("SELECT * FROM notes")
    List <Note> getAllNotes();

    @Query("SELECT * FROM notes WHERE category_id = :categoryId")
    List <Note> getAllNotesByCateg(int categoryId);

    @Query("SELECT * FROM notes WHERE note_title LIKE '%' || :text || '%' OR note_content LIKE '%' || :text || '%'")
    List <Note> getNoteByTitleOrContent(String text);
}
