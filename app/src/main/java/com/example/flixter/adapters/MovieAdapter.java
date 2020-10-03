package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixter.DetailActivity;
import com.example.flixter.MainActivity;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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

    private void configureViewHolder1(final ViewHolder v1, int position) {
        final Movie movie = (Movie) movies.get(position);

        if (movie != null) {
            v1.tvOverview.setText(movie.getOverview());
            v1.tvTitle.setText(movie.getTitle());

            String imageUrl = movie.getPosterPath();
            int placeholder = R.drawable.placeholder;

            Glide.with(context).load(imageUrl).placeholder(placeholder).
                    transform(new RoundedCornersTransformation(30, 10)).into(v1.ivPoster);
            v1.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(intent);
                }
            });
        }
    }

    private void configureViewHolder2(final ViewHolderHighRating v2, int position) {
        final Movie movie = (Movie) movies.get(position);

        if (movie != null) {
            String imageUrl = movie.getBackdropPath();;
            int placeholder = R.drawable.backdrop_placeholder;

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                v2.tvOverview.setText(movie.getOverview());
                v2.tvTitle.setText(movie.getTitle());
            }

            Glide.with(context).load(imageUrl).placeholder(placeholder).
                    transform(new RoundedCornersTransformation(30, 10)).into(v2.ivPoster);
            v2.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        double rating  = movies.get(position).getRating();
        if (rating >= 7.0) {
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

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }
    }

    public class ViewHolderHighRating extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolderHighRating(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }
    }
}
