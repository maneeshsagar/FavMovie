package com.example.maneeshsagar.favmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maneeshsagar.favmovie.Model.Movie;
import com.example.maneeshsagar.favmovie.Model.Videos;
import com.example.maneeshsagar.favmovie.R;
import com.example.maneeshsagar.favmovie.Utilites.NetworkConstant;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by maneeshsagar on 28-07-2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter <TrailerAdapter.MovieHolder> {

    List<Videos> videoList;
    Context context;
    MovieClickListener movieClickListener;

    public TrailerAdapter(List<Videos> list, Context context, MovieClickListener movieClickListener)
    {
        videoList=list;
        this.context=context;
        this.movieClickListener=movieClickListener;
    }
    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_trailer,parent,false);

        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Videos video=videoList.get(position);
        Picasso.with(context)
                .load(NetworkConstant.YOUTUBE_BASE+video.getKey()+NetworkConstant.YOUTUBE_BACK)
                .into(holder.thumb);
  //    holder.thumb.setImageResource(R.drawable.bk);
        holder.title.setText(video.getName());
      //  System.out.println(movie.getTitle());

    }

    public  interface MovieClickListener{
        void onItemClick(View v, int layoutPosition);
    }


    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.imv_video_thumb)
                ImageView thumb;
        @BindView(R.id.txv_trailer_title)
                TextView title;
        @BindView(R.id.imv_share)
                ImageView share;
        @BindView(R.id.imv_play_thumb)
                ImageView play;
        @BindView(R.id.textView8)
        TextView textView;

        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
              play.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            System.out.println("hello");
            movieClickListener.onItemClick(v,getAdapterPosition());
        }

    }
}

