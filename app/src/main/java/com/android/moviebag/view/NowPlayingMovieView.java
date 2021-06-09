package com.android.moviebag.view;

import com.android.moviebag.Models.NowPlayingMovies;
import com.android.moviebag.Models.PopularMovies;

import java.util.List;

public interface NowPlayingMovieView {

    interface NowPlayingView {

        void showNowPlayingMovieList(List<NowPlayingMovies> movies);
        void startNowPlayingLoading();

        void stopNowPlayingLoading();
        void showLoadingError(String errMsg);
    }

    interface NowPlayingPresenter {
        void loadNowPlayingMoviewList();

        void dropView();
    }

    interface OnNowPlayingResponseCallback {
        void onResponse(List<NowPlayingMovies> movies);

        void onError(String errMsg);
    }

}

