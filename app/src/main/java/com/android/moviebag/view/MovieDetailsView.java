package com.android.moviebag.view;

import com.android.moviebag.Models.MovieDetails;
import com.android.moviebag.Models.MovieProvider;
import com.android.moviebag.Models.NowPlayingMovies;
import com.android.moviebag.Models.PopularMovies;

import java.util.List;

public interface MovieDetailsView {

    interface View {
        void showMovieDetails(List<MovieDetails> movies);
        void showSimilarMovieDetails(List<PopularMovies> movies);
        void startLoading();

        void stopLoading();
        void showLoadingError(String errMsg);
    }

    interface MovieDetailsPresenter {
        void loadMovieDetailList(int movieid);

        void dropView();
    }

    interface OnMovieDetailsResponseCallback {
        void onResponse(List<MovieDetails> movies);

        void onError(String errMsg);
    }

    interface OnMovieProviderResponseCallback {
        void onResponse(List<MovieProvider> movies);

        void onError(String errMsg);
    }

    interface OnSimilarMovieResponseCallback {
        void onResponse(List<PopularMovies> movies);

        void onError(String errMsg);
    }

}

