package com.android.moviebag.presenter;

import android.content.Context;

import com.android.moviebag.Models.Cast;
import com.android.moviebag.Models.Review;
import com.android.moviebag.repository.CastRepo;
import com.android.moviebag.repository.ReviewRepo;
import com.android.moviebag.view.CastView;
import com.android.moviebag.view.ReviewView;

import java.util.List;

public class ReviewPresenter implements ReviewView.ReviewPresenter {
    private ReviewView.View view;
    private Context context;
    ReviewRepo reviewRepo;

    public ReviewPresenter(Context context, ReviewView.View view, ReviewRepo reviewRepo) {

        this.view = view;
        this.context = context;
        this.reviewRepo = reviewRepo;
    }

    @Override
    public void loadReview(int movieid) {
        view.startLoading();
        reviewRepo.getReviewList(movieid, reviewResponseCallback);
    }

    @Override
    public void dropView() {
        view = null;

    }


    private final ReviewView.OnReviewResponseCallback reviewResponseCallback = new ReviewView.OnReviewResponseCallback() {
        @Override
        public void onResponse(List<Review> movies) {
            view.stopLoading();
            view.showReviewDetails(movies);

        }

        @Override
        public void onError(String errMsg) {
            view.stopLoading();
            view.showLoadingError(errMsg);

        }
    };
}
