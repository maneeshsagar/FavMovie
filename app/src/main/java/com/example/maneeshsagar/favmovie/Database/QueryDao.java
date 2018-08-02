package com.example.maneeshsagar.favmovie.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.maneeshsagar.favmovie.Model.Movie;

import java.util.List;

/**
 * Created by maneeshsagar on 27-07-2018.
 */
@Dao
public interface QueryDao {
    @Query("SELECT * FROM movielist")
   LiveData<List<Movie>> loadAllMovies();

    @Query("SELECT * FROM movielist WHERE id LIKE:id")
    Movie getMovieID(int id);

    @Insert
    void insertMovie(Movie aMovie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie aMovie);

    @Delete
    void deleteMovie(Movie aMovie);

}
