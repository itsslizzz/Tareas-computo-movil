package model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity (tableName = "Notes", foreignKeys = @ForeignKey
        (entity = Category.class, parentColumns = "category_id",
        childColumns = "category_id"))
public class Note {
    @PrimaryKey (autoGenerate = true)
    public int note_id;

    @ColumnInfo (name = "note_title")
    public String note_title;
    @ColumnInfo (name = "category_id")
    public int category_id;
    @ColumnInfo (name = "note_content")
    public String note_content;

    @ColumnInfo (name = "created_at")
    public  String created_at;


}
