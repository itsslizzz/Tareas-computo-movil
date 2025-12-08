package model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Tabla para categoria

@Entity  (tableName = "Categories")
public class Category {

    @PrimaryKey (autoGenerate = true)
    public int category_id;

    @ColumnInfo (name = "category_name")
    public String category_name;
}
