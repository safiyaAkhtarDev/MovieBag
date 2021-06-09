package com.android.moviebag.view;

import com.android.moviebag.Models.PopularMovies;

import java.util.List;

public interface PopularMovieView {

    interface View  {

        void startLoading();

        void stopLoading();

        void showMovieList(List<PopularMovies> movies, int total_pages);

        void showLoadingError(String errMsg);
    }
    interface Presenter {
        void loadMoviewList(int currentpage);
        void dropView();
    }
    interface OnResponseCallback {
        void onResponse(List<PopularMovies> movies, int total_pages);

        void onError(String errMsg);
    }

}