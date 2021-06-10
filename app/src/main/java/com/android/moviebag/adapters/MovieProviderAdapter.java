package com.android.moviebag.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.moviebag.Models.MovieProvider;
import com.android.moviebag.Models.MovieProvider;
import com.android.moviebag.R;
import com.android.moviebag.util.Constants;
import com.android.moviebag.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MovieProviderAdapter extends RecyclerView.Adapter<MovieProviderAdapter.MovieHolder> {

    private final Context context;
    private final List<MovieProvider> movStrings = new ArrayList<>();
    private static final String TAG = "MovieProviderAdapter";

    public MovieProviderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_production_house_list, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {
        try {
            String imagePath = Constants.URL_IMAGE_500 + movStrings.get(position).getLogo_path();
            Picasso.get().load(imagePath)
                    .placeholder(R.drawable.default_image)
                    .fit().into(holder.imgProductionaHouse);

            holder.txtProviderName.setText(movStrings.get(position).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setList(List<MovieProvider> list) {
        movStrings.clear();
        movStrings.addAll(list);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return movStrings.size();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imgProductionaHouse;
        AppCompatTextView txtProviderName;


        public MovieHolder(View v) {
            super(v);
            imgProductionaHouse = v.findViewById(R.id.imgProductionaHouse);
            txtProviderName = v.findViewById(R.id.txtProviderName);


        }
    }
}
