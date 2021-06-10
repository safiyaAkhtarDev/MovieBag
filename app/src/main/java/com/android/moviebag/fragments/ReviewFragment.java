package com.android.moviebag.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.moviebag.MainActivity;
import com.android.moviebag.Models.Review;
import com.android.moviebag.R;
import com.android.moviebag.adapters.CastAdapter;
import com.android.moviebag.adapters.ReviewAdapter;
import com.android.moviebag.classes.CastImpl;
import com.android.moviebag.classes.ReviewImpl;
import com.android.moviebag.presenter.CastPresenter;
import com.android.moviebag.presenter.ReviewPresenter;
import com.android.moviebag.util.Util;
import com.android.moviebag.view.ReviewView;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;

import java.util.List;

import it.gmariotti.recyclerview.adapter.ScaleInAnimatorAdapter;

public class ReviewFragment extends Fragment implements ReviewView.View {

    private String movieid;
    RecyclerView recyclerReviews;
    ReviewAdapter reviewAdapter;
    AppCompatImageView imgback;
    AppCompatImageView imgNoData;

    View view;
    private ReviewView.ReviewPresenter presenter;

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieid = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_review, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        recyclerReviews = view.findViewById(R.id.recyclerReviews);
        imgNoData = view.findViewById(R.id.imgNoData);
        imgback = view.findViewById(R.id.imgBack);
        recyclerReviews.setLayoutManager(new LinearLayoutManager(getContext()));

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).onBackPressed();
            }
        });
        reviewAdapter = new ReviewAdapter(getContext());
        ScaleInAnimatorAdapter scaleInAnimatorAdapter = new ScaleInAnimatorAdapter(reviewAdapter, recyclerReviews);
        recyclerReviews.setAdapter(scaleInAnimatorAdapter);

        presenter = new ReviewPresenter(getContext(), this, new ReviewImpl(getContext()));
        presenter.loadReview(Integer.parseInt(movieid));
    }

    @Override
    public void showReviewDetails(List<Review> movies) {
        if (movies != null && movies.size() > 0) {
            reviewAdapter.setList(movies);
            imgNoData.setVisibility(View.GONE);
        } else {
            recyclerReviews.setVisibility(View.GONE);
            imgNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoadingError(VolleyError errMsg) {
        if (errMsg instanceof NoConnectionError) {
            Util.noInternetDialog(getContext());
        } else if (errMsg instanceof NetworkError) {
            Util.noInternetDialog(getContext());
        } else {
            Util.errorDialog(getContext(), getString(R.string.review_error_title), getString(R.string.review_error_desc));

        }
    }

    @Override
    public void startLoading() {
        Util.showProgressDialog(getContext());
    }

    @Override
    public void stopLoading() {
        Util.hideProgressDialog();
    }
}