package controller;

import android.content.Context;
import android.util.Log;

import java.util.List;

import model.AppDataBase;
import model.Category;
import model.CategoryDao;

public class CategoryController {
    private static final String TAG = "CategoryController";

private final CategoryDao categoryDao;
public CategoryController (Context context){
    AppDataBase dataBase= AppDataBase.getInstance(context);
    categoryDao= dataBase.categoryDao();

}

public void addCategory (String categoryName){
    try {
        Category category= new Category();
        category.category_name=categoryName;
        categoryDao.insertCategory(category);
        Log.i(TAG, "addCategory: Categoria guardada");

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

public List <Category> getAllCategories(){
    return categoryDao.getAllCategories();
}

    public String getCategoryNameById(int id) {
        Category cat = categoryDao.getCategoryById(id);
        return (cat != null) ? cat.category_name : "Sin categoría";
    }
}
