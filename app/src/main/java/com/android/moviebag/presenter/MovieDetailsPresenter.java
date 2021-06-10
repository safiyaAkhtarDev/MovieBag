package com.android.moviebag.presenter;

import android.content.Context;

import com.android.moviebag.Models.MovieDetails;
import com.android.moviebag.Models.MovieProvider;
import com.android.moviebag.Models.NowPlayingMovies;
import com.android.moviebag.Models.PopularMovies;
import com.android.moviebag.repository.MoviesDetailsRepo;
import com.android.moviebag.repository.NowPlayingMoviesRepo;
import com.android.moviebag.view.MovieDetailsView;
import com.android.moviebag.view.NowPlayingMovieView;
import com.android.volley.VolleyError;

import java.util.List;

public class MovieDetailsPresenter implements MovieDetailsView.MovieDetailsPresenter {
    private MovieDetailsView.View view;
    private Context context;
    MoviesDetailsRepo moviesDetailsRepo;

    public MovieDetailsPresenter(Context context, MovieDetailsView.View view, MoviesDetailsRepo moviesRepo) {

        this.view = view;
        this.context = context;
        this.moviesDetailsRepo = moviesRepo;
    }

    @Override
    public void loadMovieDetailList(int movieid) {
        view.startLoading();
        moviesDetailsRepo.getMovieList(movieid, callback);
        moviesDetailsRepo.getSimilarMovies(movieid, similarMovieResponseCallback);
    }

    @Override
    public void dropView() {
        view = null;

    }


    private final MovieDetailsView.OnSimilarMovieResponseCallback similarMovieResponseCallback = new MovieDetailsView.OnSimilarMovieResponseCallback() {
        @Override
        public void onResponse(List<PopularMovies> movies) {
            view.stopLoading();
            view.showSimilarMovieDetails(movies);

        }

        @Override
        public void onError(VolleyError errMsg) {
            view.stopLoading();
            view.showLoadingError(errMsg);

        }
    };
    private final MovieDetailsView.OnMovieDetailsResponseCallback callback = new MovieDetailsView.OnMovieDetailsResponseCallback() {
        @Override
        public void onResponse(List<MovieDetails> movies) {
            view.showMovieDetails(movies);

        }

        @Override
        public void onError(VolleyError errMsg) {

            view.showLoadingError(errMsg);
        }
    };
}
