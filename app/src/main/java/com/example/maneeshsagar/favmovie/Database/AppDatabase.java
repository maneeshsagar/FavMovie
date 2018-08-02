package com.example.maneeshsagar.favmovie.Database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.maneeshsagar.favmovie.Model.Movie;

/**
 * Created by maneeshsagar on 27-07-2018.
 */
@Database(entities ={ Movie.class},version = 1,exportSchema = false)
public  abstract class AppDatabase extends RoomDatabase {

    public static final  Object lock=new Object();
    public static AppDatabase sInstance;
    public static final String DATABASE_NAME="movie";


    public  static AppDatabase getsInstance(Context context)
    {
        if(sInstance==null)
        {
            synchronized (lock){

                sInstance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,
                        AppDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();

            }
        }
        return sInstance;
    }
    public abstract QueryDao queryDao();
}
