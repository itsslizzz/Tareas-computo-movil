package model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insertCategory (Category category);
    @Query( "SELECT * FROM categories")
    List <Category> getAllCategories();

    @Query("SELECT * FROM categories WHERE category_id = :id")
    Category getCategoryById(int id);






}
