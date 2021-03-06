package com.example.maneeshsagar.favmovie.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maneeshsagar.favmovie.Model.Movie;
import com.example.maneeshsagar.favmovie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by maneeshsagar on 16-06-2018.
 */

public class MovieAdapter extends RecyclerView.Adapter <MovieAdapter.MovieHolder> {

    List<Movie> movieList;
    Context context;
    MovieClickListener movieClickListener;
    public MovieAdapter(List<Movie> list, Context context, MovieClickListener movieClickListener)
    {
        movieList=list;
        this.context=context;
        this.movieClickListener=movieClickListener;
    }
    @Override
    public MovieHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item,parent,false);

        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
       Movie movie=movieList.get(position);
     //   Toast.makeText(,movie.getPosterPath(),Toast.LENGTH_LONG).show();
        Picasso.with(context)
                .load(Movie.rootimg+movie.getPosterPath())
                .into(holder.poster);
        holder.title.setText(movie.getTitle());
        System.out.println(movie.getPosterPath());

    }

    public  interface MovieClickListener{
        void onItemClick(View v, int layoutPosition);
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView poster;
        TextView title;
        CardView cardView;

        public MovieHolder(View itemView) {
            super(itemView);
            poster=itemView.findViewById(R.id.movie_poster);
            title=itemView.findViewById(R.id.movie_title);
            cardView=itemView.findViewById(R.id.character_card);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           movieClickListener.onItemClick(v,getAdapterPosition());
        }
    }
}
