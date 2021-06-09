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

import com.android.moviebag.Models.Review;
import com.android.moviebag.R;
import com.android.moviebag.util.Constants;
import com.android.moviebag.util.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MovieHolder> {

    private final Context context;
    private final List<Review> reviewArrayList = new ArrayList<>();
    private static final String TAG = "ReviewAdapter";

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_review_list, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {
        try {
            String imagePath = Constants.URL_IMAGE_185 + reviewArrayList.get(position).getAuthor_details().getAvatar_path();
            Picasso.get().load(imagePath).placeholder(R.drawable.default_image).fit().into(holder.imgReviewer);
            if (reviewArrayList.get(position).getContent() == null) {
                holder.txtReview.setText("N/A");
            } else {
                holder.txtReview.setText(reviewArrayList.get(position).getContent());
            }
            holder.txtReviewerName.setText(reviewArrayList.get(position).getAuthor_details().getUsername());
            if (reviewArrayList.get(position).getAuthor_details().getRating() == null) {
                holder.txtRatingText.setText("0.0");
                holder.ratingBar.setRating(0);
            } else {
                holder.ratingBar.setRating(Float.parseFloat(reviewArrayList.get(position).getAuthor_details().getRating()));
                holder.txtRatingText.setText(reviewArrayList.get(position).getAuthor_details().getRating());
            }
            holder.txtReviewDate.setText(Util.dateFormat(reviewArrayList.get(position).getCreated_at()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setList(List<Review> list) {
        reviewArrayList.clear();
        reviewArrayList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imgReviewer;
        AppCompatTextView txtReviewerName;
        AppCompatTextView txtRatingText;
        AppCompatTextView txtReviewDate;
        AppCompatTextView txtReview;
        RatingBar ratingBar;


        public MovieHolder(View v) {
            super(v);
            imgReviewer = v.findViewById(R.id.imgReviewer);
            txtReviewerName = v.findViewById(R.id.txtReviewerName);
            ratingBar = v.findViewById(R.id.ratingBar);
            txtRatingText = v.findViewById(R.id.txtRatingText);
            txtReview = v.findViewById(R.id.txtReview);
            txtReviewDate = v.findViewById(R.id.txtReviewDate);


        }
    }
}
