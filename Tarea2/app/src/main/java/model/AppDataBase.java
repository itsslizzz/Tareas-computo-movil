package model;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


    @Database(entities = {Category.class, Note.class},version = 1,exportSchema = false)
    public abstract class AppDataBase extends RoomDatabase {
        private static AppDataBase INSTANCE;
        public abstract CategoryDao categoryDao();
        public abstract NoteDao noteDao ();



        public static synchronized AppDataBase getInstance(Context context){
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDataBase.class,
                        "App_DataBase"
                ).allowMainThreadQueries().build();
            }

            return INSTANCE;
}
    }





