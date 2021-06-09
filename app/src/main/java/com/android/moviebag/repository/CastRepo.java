package com.android.moviebag.repository;


import com.android.moviebag.view.CastView;

public interface CastRepo {
    void getCastList(int movieid, CastView.OnCastResponseCallback callback);


}
