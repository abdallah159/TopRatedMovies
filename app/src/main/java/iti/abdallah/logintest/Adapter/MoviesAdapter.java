package iti.abdallah.logintest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import iti.abdallah.logintest.R;
import model.Movies;

/**
 * Created by abdallah on 08/09/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movies>movies;
    private int rowLayout;
    private Context context;





    public static class MovieViewHolder extends RecyclerView.ViewHolder{

        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView releasYear;
        TextView rating;
        ImageView image;


        public MovieViewHolder(View itemView) {
            super(itemView);
            moviesLayout=(LinearLayout)itemView.findViewById(R.id.movies_layout);
            movieTitle=(TextView)itemView.findViewById(R.id.title);
            releasYear=(TextView)itemView.findViewById(R.id.releasyear);
            rating=(TextView)itemView.findViewById(R.id.rating);
            image=(ImageView)itemView.findViewById(R.id.poster);



        }
    }

    public MoviesAdapter(List<Movies>movies, int rowLayout, Context context){
        this.movies=movies;
        this.rowLayout=rowLayout;
        this.context=context;

    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.releasYear.setText(movies.get(position).getReleaseYear());
        holder.rating.setText(movies.get(position).getRating().toString());
        Glide.with(context)
                .load(movies.get(position).getImage())
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }





}
