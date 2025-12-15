package com.fic.tarea1.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "History")
public class History {
@PrimaryKey (autoGenerate = true)
    public int id;

@ColumnInfo (name = "action")
    public String action;

@ColumnInfo (name = "created_at")
    public String created_at;

@ColumnInfo (name = "details")
    public String details;
}
