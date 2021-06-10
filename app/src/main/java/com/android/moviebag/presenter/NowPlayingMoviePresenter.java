package com.android.moviebag.presenter;

import android.content.Context;
import android.util.Log;

import com.android.moviebag.Models.NowPlayingMovies;
import com.android.moviebag.Models.PopularMovies;
import com.android.moviebag.repository.NowPlayingMoviesRepo;
import com.android.moviebag.view.NowPlayingMovieView;
import com.android.moviebag.view.PopularMovieView;

import java.util.List;

public class NowPlayingMoviePresenter implements NowPlayingMovieView.NowPlayingPresenter {
    private NowPlayingMovieView.NowPlayingView view;
    private Context context;
    NowPlayingMoviesRepo nowPlayingMoviesRepo;

    public NowPlayingMoviePresenter(Context context, NowPlayingMovieView.NowPlayingView view,
                                    NowPlayingMoviesRepo nowPlayingMoviesRepo) {

        this.view = view;
        this.context = context;
        this.nowPlayingMoviesRepo = nowPlayingMoviesRepo;
    }


    @Override
    public void loadNowPlayingMoviewList() {
        view.startNowPlayingLoading();
        nowPlayingMoviesRepo.getMovieList(callback);
    }

    @Override
    public void dropView() {
        view = null;

    }

    private final NowPlayingMovieView.OnNowPlayingResponseCallback callback =
            new NowPlayingMovieView.OnNowPlayingResponseCallback() {
        @Override
        public void onResponse(List<NowPlayingMovies> movies) {
            view.stopNowPlayingLoading();
            view.showNowPlayingMovieList(movies);
        }

        @Override
        public void onError(String errMsg) {
            view.stopNowPlayingLoading();
            view.showLoadingError(errMsg);
        }
    };
}
