package com.fic.tarea1.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert void insert(Task task);

    @Delete void delete(Task task);

    @Update void update(Task task);

    @Query("SELECT * FROM Tasks WHERE id = :id")
    Task getTaskById(int id);

    @Query("SELECT * FROM Tasks")
    List<Task> getAllTasks();
}
