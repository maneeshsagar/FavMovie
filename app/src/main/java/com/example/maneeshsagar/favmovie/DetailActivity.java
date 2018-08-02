package com.example.maneeshsagar.favmovie;

import android.app.ProgressDialog;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.example.maneeshsagar.favmovie.Database.AppDatabase;
import com.example.maneeshsagar.favmovie.Model.Movie;
import com.example.maneeshsagar.favmovie.Model.Reviews;
import com.example.maneeshsagar.favmovie.Model.Videos;
import com.example.maneeshsagar.favmovie.Utilites.NetworkConstant;
import com.example.maneeshsagar.favmovie.adapter.MovieAdapter;
import com.example.maneeshsagar.favmovie.adapter.ReviewAdapter;
import com.example.maneeshsagar.favmovie.adapter.TrailerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.MovieClickListener,ReviewAdapter.MovieClickListener {

    AppDatabase mDb;
    TextView title;
    TextView description;
    TextView realeaseDate;
    TextView VoteAvg;
    TextView Vote;
    RatingBar ratingBar;
    ImageView backdrop;
    Movie movie;
    CircularProgressBar progressBar;

    RecyclerView rvTrailer;
    RecyclerView.Adapter adapter;

    RecyclerView rvReview;
    RecyclerView.Adapter radapter;
    List<Reviews> rList;

    ProgressDialog progressDialog;
    List<Videos> list;
    int k;
    Toolbar toolbar;
     FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDb=AppDatabase.getsInstance(getApplicationContext());

        title=(TextView)findViewById(R.id.title);
        fab = findViewById(R.id.fab_favorite);
        description=(TextView)findViewById(R.id.desc);
        backdrop=(ImageView)findViewById(R.id.back_drop);
        realeaseDate=(TextView)findViewById(R.id.release_date);
        toolbar=(Toolbar)findViewById(R.id.toolbar1);
        Vote=(TextView)findViewById(R.id.vote);
        VoteAvg=(TextView)findViewById(R.id.avg_vote);

        rvTrailer=(RecyclerView)findViewById(R.id.trailer);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this
                ,LinearLayoutManager.HORIZONTAL,false);
        rvTrailer.setLayoutManager(layoutManager);
        rvTrailer.setHasFixedSize(false);
        list=new ArrayList<>();

        rvReview=(RecyclerView)findViewById(R.id.reviews);
        LinearLayoutManager rLinearLayoutManager=new LinearLayoutManager(this);
        rvReview.setLayoutManager(rLinearLayoutManager);
        rvReview.setHasFixedSize(false);
        rList=new ArrayList<>();
        movie=(Movie)getIntent().getSerializableExtra("movie");
        Picasso.with(this)
                .load(Movie.rootimg+movie.getPosterPath())
                .into(backdrop);
        title.setText(movie.getTitle());
        description.setText(movie.getOverView());
        realeaseDate.setText(movie.getRealseDate());
        VoteAvg.setText(Double.toString(movie.getVoteAvg()));
        Vote.setText(Integer.toString(movie.getVoteCount()));

        if(mDb.queryDao().getMovieID(movie.getId())==null)
        {
            fab.setImageResource(R.drawable.ic_heart_white_60dp);
        }
        else {
            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
        }

        loadReviews();
        loadTrailer();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mDb.queryDao().getMovieID(movie.getId())==null)
                {
                    fab.setImageResource(R.drawable.ic_favorite_black_24dp);
                    mDb.queryDao().insertMovie(movie);
                    Snackbar.make(view, "Added as Favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    fab.setImageResource(R.drawable.ic_heart_white_60dp);
                    mDb.queryDao().deleteMovie(movie);
                    Snackbar.make(view, "Removed from Favorite", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
    }

    void loadTrailer()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.GET
                ,NetworkConstant.TRAILER_ID_LINK+movie.getId()+NetworkConstant.TRAILER_ID_LINK_BACK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray results=jsonObject.getJSONArray("results");

                            for(int i=0;i<results.length();i++)
                            {
                                JSONObject o=results.getJSONObject(i);
                                Videos item=new Videos(o.getString("id"), o.getString("key"),
                                        o.getString("name"), o.getString("site"), o.getInt("size")
                                        , o.getString("type"));
                                list.add(item);
                            }
                            adapter= new TrailerAdapter(list,getApplicationContext(),DetailActivity.this);
                            rvTrailer.setAdapter(adapter);

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

    }


    void loadReviews()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.GET
                ,NetworkConstant.REVIEW_BASE+movie.getId()+NetworkConstant.REVIEw_BACK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  progressDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray results=jsonObject.getJSONArray("results");

                            for(int i=0;i<results.length();i++)
                            {
                                JSONObject o=results.getJSONObject(i);
                                Reviews item=new Reviews(o.getString("author"), o.getString("content"),
                                        o.getString("id"), o.getString("url"));
                                rList.add(item);
                            }
                            radapter= new ReviewAdapter(rList,getApplicationContext(),DetailActivity.this);
                            rvReview.setAdapter(radapter);

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
    }

    @Override
    public void onItemClick(View v, int layoutPosition) {

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v="+ list.get(layoutPosition).getKey()));
        startActivity(intent);
    }
}
