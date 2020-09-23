package com.example.flixter.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "MovieAdapter";

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder movieView;
        if (viewType == Movie.LOW_RATING) {
            View v1 = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
            movieView = new ViewHolder(v1);
        } else {
            View v2 = LayoutInflater.from(context).inflate(R.layout.high_rating_item_movie, parent, false);
            movieView = new ViewHolderHighRating(v2);
        }
        return movieView;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == Movie.LOW_RATING) {
            ViewHolder v1 = (ViewHolder) holder;
            configureViewHolder1(v1, position);
        } else {
            ViewHolderHighRating v2 = (ViewHolderHighRating) holder;
            configureViewHolder2(v2, position);
        }
    }

    private void configureViewHolder1(ViewHolder v1, int position) {
        Movie movie = (Movie) movies.get(position);

        if (movie != null) {
            v1.tvOverview.setText(movie.getOverview());
            v1.tvTitle.setText(movie.getTitle());

            String imageUrl;
            int placeholder;

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
                placeholder = R.drawable.backdrop_placeholder_background;
            } else {
                imageUrl = movie.getPosterPath();
                placeholder = R.drawable.poster_placeholder_background;
            }
            Glide.with(context).load(imageUrl).placeholder(placeholder).into(v1.ivPoster);
        }
    }

    private void configureViewHolder2(ViewHolderHighRating v2, int position) {
        Movie movie = (Movie) movies.get(position);
        String imageUrl;
        int placeholder;
        imageUrl = movie.getBackdropPath();
        placeholder = R.drawable.backdrop_placeholder_background;
        Glide.with(context).load(imageUrl).placeholder(placeholder).into(v2.ivPoster);
    }

    @Override
    public int getItemViewType(int position) {
        double rating  = movies.get(position).getRating();
        if (rating >= 7.5) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }
    }

    public class ViewHolderHighRating extends RecyclerView.ViewHolder {

        ImageView ivPoster;

        public ViewHolderHighRating(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }
    }
}
