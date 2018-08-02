package com.example.maneeshsagar.favmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maneeshsagar.favmovie.Model.Reviews;
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

public class ReviewAdapter extends RecyclerView.Adapter <ReviewAdapter.MovieHolder> {

    List<Reviews> reviewsList;
    Context context;
    MovieClickListener movieClickListener;

    public ReviewAdapter(List<Reviews> list, Context context, MovieClickListener movieClickListener)
    {
        reviewsList=list;
        this.context=context;
        this.movieClickListener=movieClickListener;
    }
    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_review,parent,false);

        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        Reviews review=reviewsList.get(position);
        holder.person.setText(review.getAuthor());
        holder.body.setText(review.getContent());
        //  System.out.println(movie.getTitle());

    }

    public  interface MovieClickListener{
        void onItemClick(View v, int layoutPosition);
    }


    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

       TextView person;

       TextView body;

        public MovieHolder(View itemView) {
            super(itemView);
       //     ButterKnife.bind(this,itemView);
            person=itemView.findViewById(R.id.review_person);
            body=itemView.findViewById(R.id.review_body);

        }

    }
}
