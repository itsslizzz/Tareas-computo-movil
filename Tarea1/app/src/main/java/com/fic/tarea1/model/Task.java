package com.fic.tarea1.model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity (tableName = "Tasks")
public class Task {
    @PrimaryKey (autoGenerate = true)
    public int id;

    @ColumnInfo (name = "task_title")
    public String task_title;

    @ColumnInfo (name = "task_description")
    public String task_description;

    @ColumnInfo (name = "created_at")
    public String created_at;

    @ColumnInfo (name = "is_completed")
    public boolean is_completed;

}
