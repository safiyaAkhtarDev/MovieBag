package com.android.moviebag.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.moviebag.Models.Cast;
import com.android.moviebag.Models.Cast;
import com.android.moviebag.R;
import com.android.moviebag.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MovieHolder> {

    private final Context context;
    private final List<Cast> castArrayList = new ArrayList<>();
    private static final String TAG = "CastAdapter";

    public CastAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_cast_list, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {
        try {
            String imagePath = Constants.URL_IMAGE_185 + castArrayList.get(position).getProfile_path();
            Picasso.get().load(imagePath).placeholder(R.drawable.default_image).fit().into(holder.imgCast);
            if (castArrayList.get(position).getJob() == null) {
                holder.txtJob.setText(castArrayList.get(position).getKnown_for_department());
            } else {
                holder.txtJob.setText(castArrayList.get(position).getJob());
            }
            holder.txtName.setText(castArrayList.get(position).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setList(List<Cast> list) {
        castArrayList.clear();
        castArrayList.addAll(list);
        notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return castArrayList.size();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imgCast;
        AppCompatTextView txtName;
        AppCompatTextView txtJob;


        public MovieHolder(View v) {
            super(v);
            imgCast = v.findViewById(R.id.imgCast);
            txtName = v.findViewById(R.id.txtName);
            txtJob = v.findViewById(R.id.txtJob);


        }
    }
}
