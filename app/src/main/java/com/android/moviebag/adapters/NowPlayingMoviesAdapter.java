package com.android.moviebag.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.android.moviebag.MainActivity;
import com.android.moviebag.Models.NowPlayingMovies;
import com.android.moviebag.R;
import com.android.moviebag.fragments.MovieDetailsFragment;
import com.android.moviebag.util.Constants;
import com.android.moviebag.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class NowPlayingMoviesAdapter extends PagerAdapter {

    List<NowPlayingMovies> nowPlayingMoviesArrayList = new ArrayList<>();

    private LayoutInflater inflater;
    private Context context;

    public NowPlayingMoviesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setList(List<NowPlayingMovies> list) {
        nowPlayingMoviesArrayList.clear();
        nowPlayingMoviesArrayList.addAll(list);
        notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        return nowPlayingMoviesArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View views = inflater.inflate(R.layout.now_playing_movie_pager, view, false);

        try {
            assert views != null;
            ConstraintLayout cardParent = (ConstraintLayout) views
                    .findViewById(R.id.cardParent);
            AppCompatImageView imageView = (AppCompatImageView) views
                    .findViewById(R.id.imgNowPlayingMovies);
            AppCompatTextView txtMovieName = (AppCompatTextView) views
                    .findViewById(R.id.txtMovieName);
            AppCompatTextView txtReleaseValue = (AppCompatTextView) views
                    .findViewById(R.id.txtReleaseValue);

            txtMovieName.setText(nowPlayingMoviesArrayList.get(position).getOriginal_title());
            txtReleaseValue.setText(Util.dateFormat(nowPlayingMoviesArrayList.get(position).getRelease_date()));

            Picasso.get()
                    .load(Constants.URL_IMAGE_500 + nowPlayingMoviesArrayList.get(position).getPoster_path())
                    .fit().centerInside()
                    .into(imageView);

            cardParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(nowPlayingMoviesArrayList.get(position).getId()));
                    ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                            .add(R.id.container, MovieDetailsFragment.class, bundle)
                            .addToBackStack(null)
                            .commit();
                }
            });

            view.addView(views, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return views;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}