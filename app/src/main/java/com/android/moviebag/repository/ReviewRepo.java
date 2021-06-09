package com.android.moviebag.repository;


import com.android.moviebag.view.ReviewView;

public interface ReviewRepo {
    void getReviewList(int movieid, ReviewView.OnReviewResponseCallback callback);


}
