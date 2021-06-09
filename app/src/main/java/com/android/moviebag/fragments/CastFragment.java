package com.android.moviebag.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.moviebag.MainActivity;
import com.android.moviebag.Models.Cast;
import com.android.moviebag.R;
import com.android.moviebag.adapters.CastAdapter;
import com.android.moviebag.classes.CastImpl;
import com.android.moviebag.classes.MoviesDetailsImpl;
import com.android.moviebag.presenter.CastPresenter;
import com.android.moviebag.presenter.MovieDetailsPresenter;
import com.android.moviebag.util.Util;
import com.android.moviebag.view.CastView;
import com.android.moviebag.view.MovieDetailsView;

import java.util.List;

import it.gmariotti.recyclerview.adapter.ScaleInAnimatorAdapter;

public class CastFragment extends Fragment implements CastView.View {

    private String movieid;
    AppCompatImageView imgCast;
    AppCompatImageView imgBack;
    AppCompatTextView txtName;
    AppCompatTextView txtJob;
    RecyclerView recyclerCast;
    private View view;

    CastAdapter adapter;
    CastView.CastPresenter presenter;


    public CastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieid = getArguments().getString("id");
        }
    }

    private void initViews() {
        imgCast = view.findViewById(R.id.imgCast);
        txtName = view.findViewById(R.id.txtName);
        txtJob = view.findViewById(R.id.txtJob);
        imgBack = view.findViewById(R.id.imgBack);
        recyclerCast = view.findViewById(R.id.recyclerCast);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).onBackPressed();
            }
        });

        recyclerCast.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new CastAdapter(getContext());
        ScaleInAnimatorAdapter scaleInAnimatorAdapter = new ScaleInAnimatorAdapter(adapter, recyclerCast);
        recyclerCast.setAdapter(scaleInAnimatorAdapter);

        presenter = new CastPresenter(getContext(), this, new CastImpl(getContext()));
        presenter.loadCast(Integer.parseInt(movieid));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cast, container, false);
        initViews();
        return view;
    }

    @Override
    public void showCastDetails(List<Cast> movies) {
        adapter.setList(movies);
    }

    @Override
    public void startLoading() {
        Util.showProgressDialog(getContext());
    }

    @Override
    public void stopLoading() {
        Util.hideProgressDialog();
    }

    @Override
    public void showLoadingError(String errMsg) {
        Util.errorDialog(getContext(), getString(R.string.couldnt_fetch), getString(R.string.cast_desc));
    }
}