package com.example.maneeshsagar.favmovie.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by maneeshsagar on 16-06-2018.
 */
@Entity(tableName = "movielist")
public class Movie implements Serializable{
    public static String rootimg="https://image.tmdb.org/t/p/w1280";;
    public String backdroproot="https://image.tmdb.org/t/p/w1280";
     int mid=0;
    @PrimaryKey
     int id;

    String title;
    String posterPath;
    String overView;
    Boolean adult;
    String realseDate;
    String backdropPath;
    int voteCount;
    Double voteAvg;
    int genre;

    public Movie(int id,String title, String posterPath, String overView, Boolean adult, String realseDate, String backdropPath, int voteCount,Double voteAvg) {
        this.id=id;
        this.title = title;
        this.posterPath =posterPath;
        this.overView = overView;
        this.adult = adult;
        this.realseDate = realseDate;
        this.backdropPath = backdroproot+backdropPath;
        this.voteCount = voteCount;
        this.voteAvg = voteAvg;

    }
    @Ignore
    public Movie(String title)
    {
        this.title=title;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id=id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }
    public Boolean getAdult() {
        return adult;
    }
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }
    public String getRealseDate() {
        return realseDate;
    }
    public void setRealseDate(String realseDate) {
        this.realseDate = realseDate;
    }
    public String getBackdropPath() {
        return backdropPath;
    }
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }
    public int getVoteCount() {
        return voteCount;
    }
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
    public Double getVoteAvg() {
        return voteAvg;
    }
    public void setVoteAvg(Double voteAvg) {
        this.voteAvg = voteAvg;
    }
    public int getGenre() {
        return genre;
    }
    public void setGenre(int genre) {
        this.genre = genre;
    }

}
