package com.android.moviebag.presenter;

import android.content.Context;

import com.android.moviebag.Models.Cast;
import com.android.moviebag.Models.MovieDetails;
import com.android.moviebag.Models.PopularMovies;
import com.android.moviebag.repository.CastRepo;
import com.android.moviebag.repository.MoviesDetailsRepo;
import com.android.moviebag.view.CastView;
import com.android.moviebag.view.MovieDetailsView;
import com.android.volley.VolleyError;

import java.util.List;

public class CastPresenter implements CastView.CastPresenter {
    private CastView.View view;
    private Context context;
    CastRepo castRepo;

    public CastPresenter(Context context, CastView.View view, CastRepo castRepo) {

        this.view = view;
        this.context = context;
        this.castRepo = castRepo;
    }

    @Override
    public void loadCast(int movieid) {
        view.startLoading();
        castRepo.getCastList(movieid, castResponseCallback);
    }

    @Override
    public void dropView() {
        view = null;

    }


    private final CastView.OnCastResponseCallback castResponseCallback = new CastView.OnCastResponseCallback() {
        @Override
        public void onResponse(List<Cast> movies) {
            view.stopLoading();
            view.showCastDetails(movies);

        }

        @Override
        public void onError(VolleyError errMsg) {
            view.stopLoading();
            view.showLoadingError(errMsg);

        }
    };
}
