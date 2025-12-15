package com.fic.tarea1.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {History.class}, version = 1, exportSchema = false)
public abstract class HistoryDataBase extends RoomDatabase {
    private static HistoryDataBase INSTANCE;

    public abstract HistoryDao historyDao();

    public static synchronized HistoryDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    HistoryDataBase.class,
                    "history_database"
            ).build();
        }
        return INSTANCE;
    }
}