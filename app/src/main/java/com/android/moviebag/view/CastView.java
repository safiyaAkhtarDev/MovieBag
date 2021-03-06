package com.android.moviebag.view;

import com.android.moviebag.Models.Cast;
import com.android.moviebag.Models.MovieDetails;
import com.android.moviebag.Models.MovieProvider;
import com.android.moviebag.Models.PopularMovies;
import com.android.volley.VolleyError;

import java.util.List;

public interface CastView {

    interface View {
        void showCastDetails(List<Cast> movies);
        void startLoading();

        void stopLoading();
        void showLoadingError(VolleyError errMsg);
    }

    interface CastPresenter {
        void loadCast(int movieid);

        void dropView();
    }

    interface OnCastResponseCallback {
        void onResponse(List<Cast> movies);

        void onError(VolleyError errMsg);
    }

}

