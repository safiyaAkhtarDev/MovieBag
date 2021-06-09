package com.android.moviebag.presenter;

import android.content.Context;
import android.util.Log;

import com.android.moviebag.Models.PopularMovies;
import com.android.moviebag.repository.PopularMoviesRepo;
import com.android.moviebag.view.PopularMovieView;

import java.util.List;

public class PopularMoviePresenter implements PopularMovieView.Presenter {
    private PopularMovieView.View view;
    private Context context;
    PopularMoviesRepo popularMoviesRepo;

    public PopularMoviePresenter(Context context, PopularMovieView.View view, PopularMoviesRepo popularMoviesRepo) {

        this.view = view;
        this.context = context;
        this.popularMoviesRepo = popularMoviesRepo;
    }


    @Override
    public void loadMoviewList(int currentpage) {
        view.startLoading();
        Log.d("safiyas","loadMoviewList");
        popularMoviesRepo.getPopularMovieList(currentpage, callback);
    }

    @Override
    public void dropView() {
        view = null;

    }

    private final PopularMovieView.OnResponseCallback callback = new PopularMovieView.OnResponseCallback() {
        @Override
        public void onResponse(List<PopularMovies> movies, int total_pages) {
            view.showMovieList(movies,total_pages);
            view.stopLoading();
        }

        @Override
        public void onError(String errMsg) {
            view.stopLoading();
            view.showLoadingError(errMsg);
        }
    };
}
