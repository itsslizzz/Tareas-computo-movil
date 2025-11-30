package com.fic.tarea1.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class TaskDataBase extends RoomDatabase {
    private static TaskDataBase INSTANCE;
    public abstract TaskDao taskDao();

    public static synchronized TaskDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    TaskDataBase.class,
                    "task_database"
            ).build();  // No usar allowMainThreadQueries()
        }
        return INSTANCE;
    }
}
