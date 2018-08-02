package com.example.maneeshsagar.favmovie;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.GridLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maneeshsagar.favmovie.Database.AppDatabase;
import com.example.maneeshsagar.favmovie.Model.MainViewModel;
import com.example.maneeshsagar.favmovie.Model.Movie;
import com.example.maneeshsagar.favmovie.Utilites.NetworkConstant;
import com.example.maneeshsagar.favmovie.adapter.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.app.ActivityOptions.*;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener{
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    AppDatabase mDb;
    List<Movie> list;
    int state;
    int key;
    final int POPULAR=1;
    final int TOP_RATED=2;
    final int FAV_MOVIE=3;
    final int DEFAULT=0;
    int retain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null)
        {
            state=savedInstanceState.getInt("state");
        }
        else {
            state=POPULAR;
        }
        check();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("state",state);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    void check()
    {
        if(isNetworkAvailable()){
            setContentView(R.layout.activity_main);
            setToolbar();
            GridLayoutManager gridLayoutManager;
            if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
            {
                gridLayoutManager=new GridLayoutManager(this,3);
            }
            else{
                gridLayoutManager=new GridLayoutManager(this,2);

            }
            setEverything(gridLayoutManager);

            updateUI(state);
        }else
        {
            setContentView(R.layout.net_error);
            setToolbar();
        }
    }

    void setToolbar()
    {
        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FavMovie");
    }
    void setEverything(GridLayoutManager gridLayoutManager)
    {
        mDb=AppDatabase.getsInstance(getApplicationContext());
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
     //   GridLayoutManager gridLayoutManager;


        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(false);
        //  Toast.makeText(this,NetworkConstant.URL_DATA,Toast.LENGTH_SHORT).show();
        key=getIntent().getIntExtra("key",DEFAULT);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int d=item.getItemId();
        if(d==R.id.top_rated)
        {
            Toast.makeText(this,"Top-Rated",Toast.LENGTH_SHORT).show();
            updateUI(R.id.top_rated);
        }
        else    if(d== R.id.popular)
        {
            Toast.makeText(this,"Popular",Toast.LENGTH_SHORT).show();
            updateUI(R.id.popular);
        }
        else if(d==R.id.refresh)
        {
            check();
            Toast.makeText(this,"Refreshed",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Favorite",Toast.LENGTH_SHORT).show();
            updateUI(R.id.fav);
        }
        return super.onOptionsItemSelected(item);
    }

    void updateUI(int type)
    {
        if(type!=POPULAR)
        {
//            list.clear();
        }
        list=new ArrayList<>();
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading Data");
        progressDialog.show();
        String URL1="";
        if(type==R.id.popular || type==POPULAR )
        {
            URL1=NetworkConstant.POPULAR;
            state=POPULAR;
        }
        else if(type==R.id.top_rated || type==TOP_RATED)
        {
            URL1=NetworkConstant.TOP_RATED;
            state=TOP_RATED;
        }
        else if(type==R.id.fav || type==FAV_MOVIE)
        {
            getFromDB( progressDialog);
            state=FAV_MOVIE;
            return;
        }
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray results=jsonObject.getJSONArray("results");

                            for(int i=0;i<results.length();i++)
                            {
                                JSONObject o=results.getJSONObject(i);
                                Movie item=new Movie(o.getInt("id"),
                                        o.getString("title"),
                                        o.getString("poster_path"), o.getString("overview"),
                                        o.getBoolean("adult"),o.getString("release_date"),
                                        o.getString("poster_path")
                                        ,o.getInt("vote_count"),o.getDouble("vote_average")
                                );
                                list.add(item);
                            }
                            adapter= new MovieAdapter(list,getApplicationContext(),MainActivity.this);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(getApplicationContext(),"Some Error Occued",Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }
    public void getFromDB(final ProgressDialog pg)
    {
       //final LiveData<List<Movie>> favMovie=mDb.queryDao().loadAllMovies();

        MainViewModel mainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> slist) {
                list=slist;
                adapter= new MovieAdapter(slist,getApplicationContext(),MainActivity.this);
                recyclerView.setAdapter(adapter);
                pg.dismiss();
            }
        });
      /*  list=mDb.queryDao().loadAllMovies();
        adapter= new MovieAdapter(list,getApplicationContext(),MainActivity.this);
        recyclerView.setAdapter(adapter);
        pg.dismiss();*/
    }

    Movie getMovie(int id)
    {
        final Movie[] mMovie = new Movie[1];


        StringRequest stringRequest=new StringRequest(Request.Method.GET,NetworkConstant.SEARCH_MOVIE
                +id+NetworkConstant.SEARCH_MOVIE_BACK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  progressDialog.dismiss();
                        try {
                            JSONObject o=new JSONObject(response);

                                 mMovie[0] =new Movie(o.getInt("id"),
                                        o.getString("title"),
                                        o.getString("poster_path"), o.getString("overview"),
                                        o.getBoolean("adult"),o.getString("release_date"),
                                        o.getString("poster_path")
                                        ,o.getInt("vote_count"),o.getDouble("vote_average")
                                );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



        return mMovie[0];
    }



    boolean isNetworkAvailable()
    {
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    @Override
    public void onItemClick(View v,int layoutPosition) {
        System.out.println("hiii");
        Intent startDetailActivityIntent = new Intent(this,DetailActivity.class);
        startDetailActivityIntent.putExtra("movie",list.get(layoutPosition));
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             startActivity(startDetailActivityIntent, makeSceneTransitionAnimation(this, v.findViewById(R.id.movie_poster), getString(R.string.tansition_poster)).toBundle());
         }
         else {
             startActivity(startDetailActivityIntent);
         }
    }
}
