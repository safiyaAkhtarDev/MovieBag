package com.android.moviebag.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.moviebag.MainActivity;
import com.android.moviebag.Models.PopularMovies;
import com.android.moviebag.R;
import com.android.moviebag.fragments.MovieDetailsFragment;
import com.android.moviebag.util.Constants;
import com.android.moviebag.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.MovieHolder> {

    private final Context context;
    private final List<PopularMovies> movStrings = new ArrayList<>();
    private static final String TAG = "TrendingMovieAdapter";

    public PopularMovieAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_popular_list, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {

        try {
            Log.e(TAG, "onBindViewHolder: " + position);
            holder.txtMovieName.setText(movStrings.get(position).getTitle());
            Locale loc = new Locale(movStrings.get(position).getOriginal_language());
            holder.txtLanguageValue.setText(loc.getDisplayLanguage(loc));
            if (movStrings.get(position).getRelease_date() != null
                    && !movStrings.get(position).getRelease_date().equalsIgnoreCase("")) {
                holder.txtReleaseValue.setText(Util.dateFormat(movStrings.get(position).getRelease_date()));
            } else {
                holder.txtReleaseValue.setText("N/A");
            }
            holder.txtRatingText.setText(movStrings.get(position).getVote_average() + "");
            String imagePath = Constants.URL_IMAGE_185 + movStrings.get(position).getPoster_path();

            Picasso.get().load(imagePath).placeholder(R.drawable.default_image).fit().into(holder.imgMovie);

            holder.ratingBar.setRating(movStrings.get(position).getVote_average());

            holder.cardParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(movStrings.get(position).getId()));
                    ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .add(R.id.container, MovieDetailsFragment.class, bundle)
                            .addToBackStack(null)
                            .commit();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setList(List<PopularMovies> list) {
        movStrings.clear();
        movStrings.addAll(list);
        notifyDataSetChanged();

    }

    public void updateList(List<PopularMovies> list) {
//        movStrings.clear();
        ArrayList<PopularMovies> list1 = new ArrayList<>();
        list1 = (ArrayList<PopularMovies>) movStrings;
        movStrings.addAll(list);
        notifyItemRangeInserted(list1.size(), movStrings.size());

    }


    @Override
    public int getItemCount() {
        return movStrings.size();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {
        AppCompatTextView txtMovieName;
        AppCompatTextView txtLanguageValue;
        AppCompatTextView txtReleaseValue;
        AppCompatTextView txtRatingText;
        RatingBar ratingBar;
        CardView cardParent;
        AppCompatImageView imgMovie;


        public MovieHolder(View v) {
            super(v);
            cardParent = v.findViewById(R.id.cardParent);
            imgMovie = v.findViewById(R.id.imgMovie);
            txtMovieName = v.findViewById(R.id.txtMovieName);
            txtLanguageValue = v.findViewById(R.id.txtLanguageValue);
            txtReleaseValue = v.findViewById(R.id.txtReleaseValue);
            txtRatingText = v.findViewById(R.id.txtRatingText);
            ratingBar = v.findViewById(R.id.ratingBar);

        }
    }
}
