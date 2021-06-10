package com.android.moviebag.view;

import com.android.moviebag.Models.Cast;
import com.android.moviebag.Models.Review;
import com.android.volley.VolleyError;

import java.util.List;

public interface ReviewView {

    interface View {
        void showReviewDetails(List<Review> movies);
        void showLoadingError(VolleyError errMsg);
        void startLoading();

        void stopLoading();
    }

    interface ReviewPresenter {
        void loadReview(int movieid);
        void dropView();
    }

    interface OnReviewResponseCallback {
        void onResponse(List<Review> movies);

        void onError(VolleyError errMsg);
    }

}

