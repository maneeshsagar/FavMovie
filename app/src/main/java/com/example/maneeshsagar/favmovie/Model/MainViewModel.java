package com.example.maneeshsagar.favmovie.Model;

import android.app.Application;
import android.app.ListActivity;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.example.maneeshsagar.favmovie.Database.AppDatabase;

import java.util.List;

/**
 * Created by maneeshsagar on 30-07-2018.
 */

public class MainViewModel extends AndroidViewModel{
    private LiveData<List<Movie>> list;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase mDb=AppDatabase.getsInstance(this.getApplication());
        list=mDb.queryDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getList() {
        return list;
    }
}
