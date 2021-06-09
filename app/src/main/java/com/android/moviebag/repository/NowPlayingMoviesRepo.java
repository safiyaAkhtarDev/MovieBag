package com.android.moviebag.repository;


import com.android.moviebag.view.NowPlayingMovieView;

public interface NowPlayingMoviesRepo {
    void getMovieList( NowPlayingMovieView.OnNowPlayingResponseCallback callback);

}
