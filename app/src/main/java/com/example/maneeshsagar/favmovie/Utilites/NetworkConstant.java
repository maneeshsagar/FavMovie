package com.example.maneeshsagar.favmovie.Utilites;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maneeshsagar.favmovie.BuildConfig;
import com.example.maneeshsagar.favmovie.MainActivity;
import com.example.maneeshsagar.favmovie.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by maneeshsagar on 16-06-2018.
 */

public class NetworkConstant {
   public static final String YOUTUBE_BASE="https://img.youtube.com/vi/";
   public static final String YOUTUBE_BACK="/0.jpg";
   public static final String TRAILER_ID_LINK="http://api.themoviedb.org/3/movie/";
    public static final String TRAILER_ID_LINK_BACK="/videos?api_key="+BuildConfig.ApiKey;


    public static final String REVIEW_BASE="http://api.themoviedb.org/3/movie/";
    public static final String REVIEw_BACK="/reviews?api_key="+BuildConfig.ApiKey;

   public static final String SEARCH_MOVIE="http://api.themoviedb.org/3/movie/";
   public static final String SEARCH_MOVIE_BACK="?api_key="+BuildConfig.ApiKey;
   public static final String TOP_RATED="https://api.themoviedb.org/3/movie/top_rated?api_key="+BuildConfig.ApiKey+"&language=en-US&page=1";
   public static final String POPULAR="https://api.themoviedb.org/3/movie/popular?api_key="+BuildConfig.ApiKey+"&language=en-US&page=1";
   public static final String NOW_PLAYING="https://api.themoviedb.org/3/movie/now_playing?api_key="+BuildConfig.ApiKey+"&language=en-US";
}
