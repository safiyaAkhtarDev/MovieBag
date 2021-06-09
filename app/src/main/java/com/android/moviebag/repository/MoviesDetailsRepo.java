package com.android.moviebag.repository;


import com.android.moviebag.view.MovieDetailsView;
import com.android.moviebag.view.NowPlayingMovieView;

public interface MoviesDetailsRepo {
    void getMovieList(int movieid, MovieDetailsView.OnMovieDetailsResponseCallback callback);

    void getSimilarMovies(int movieid, MovieDetailsView.OnSimilarMovieResponseCallback callback);

}
