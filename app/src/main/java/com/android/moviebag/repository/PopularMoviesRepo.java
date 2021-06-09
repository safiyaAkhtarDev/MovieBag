package com.android.moviebag.repository;


import com.android.moviebag.view.PopularMovieView;

public interface PopularMoviesRepo {
    void getPopularMovieList(int currentpage, PopularMovieView.OnResponseCallback callback);
}
