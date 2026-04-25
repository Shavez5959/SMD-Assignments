package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    Context context;
    ArrayList<Movie> movies;

    OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Movie movie);
    }

    public MovieAdapter(Context context, ArrayList<Movie> movies, OnBookClickListener listener) {
        this.context= context;
        this.movies= movies;
        this.listener= listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context)
                .inflate(R.layout.movie_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie= movies.get(position);
        holder.moviePoster.setImageResource(movie.getPoster());
        holder.movieName.setText(movie.getName());
        holder.movieGenre.setText(movie.getGenre());

        holder.btnBook.setOnClickListener(view->{

            if (listener!=null)
            {
                listener.onBookClick(movie);
            }

        });

        holder.btnTrailer.setOnClickListener(view->{
            String url = movie.getTrailer();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            view.getContext().startActivity(i);
        });


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder
    {

        ImageView moviePoster;
        TextView  movieName;
        TextView movieGenre;
        Button btnBook;
        Button btnTrailer;



        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            moviePoster=itemView.findViewById(R.id.moviePoster);
            movieName=itemView.findViewById(R.id.movieName);
            movieGenre=itemView.findViewById(R.id.movieGenre);
            btnBook= itemView.findViewById(R.id.btnBook);
            btnTrailer=itemView.findViewById(R.id.btnTrailer);

        }
    }
}
