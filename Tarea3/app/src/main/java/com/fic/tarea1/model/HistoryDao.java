package com.fic.tarea1.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {

    @Insert void insert(History history);

    @Query( "SELECT * FROM History ORDER BY created_at DESC")
    List <History> getAllHistory();

    @Query("SELECT * FROM history WHERE `action` = :action ORDER BY created_at DESC")
    List<History> getHistoryByAction(String action);

    @Query("SELECT * FROM history WHERE created_at LIKE :date || '%' ORDER BY created_at DESC")
    List<History> getHistoryByDate(String date);
}