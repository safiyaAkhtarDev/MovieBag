package com.android.moviebag.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.moviebag.MainActivity;
import com.android.moviebag.Models.PopularMovies;
import com.android.moviebag.R;
import com.android.moviebag.fragments.MovieDetailsFragment;
import com.android.moviebag.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.MovieHolder> {

    private final Context context;
    private final List<PopularMovies> movStrings = new ArrayList<>();
    private static final String TAG = "PopularMoviesAdapter";

    public SimilarMoviesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_similar_movie_list, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {
        try {
            String imagePath = Constants.URL_IMAGE_500 + movStrings.get(position).getPoster_path();

            Picasso.get().load(imagePath).placeholder(R.drawable.default_image).fit().into(holder.imgSimilarMovies);

            holder.imgSimilarMovies.setOnClickListener(new View.OnClickListener() {
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

        AppCompatImageView imgSimilarMovies;


        public MovieHolder(View v) {
            super(v);
            imgSimilarMovies = v.findViewById(R.id.imgSimilarMovies);


        }
    }
}
