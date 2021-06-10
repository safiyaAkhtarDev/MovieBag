package com.android.moviebag.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.ScrollView;

import com.android.moviebag.MainActivity;
import com.android.moviebag.Models.MovieDetails;
import com.android.moviebag.Models.PopularMovies;
import com.android.moviebag.R;
import com.android.moviebag.adapters.MovieProviderAdapter;
import com.android.moviebag.adapters.SimilarMoviesAdapter;
import com.android.moviebag.classes.MoviesDetailsImpl;
import com.android.moviebag.presenter.MovieDetailsPresenter;
import com.android.moviebag.util.Constants;
import com.android.moviebag.util.Util;
import com.android.moviebag.view.MovieDetailsView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import it.gmariotti.recyclerview.adapter.ScaleInAnimatorAdapter;

public class MovieDetailsFragment extends Fragment implements MovieDetailsView.View {

    AppCompatImageView imgMovie;
    AppCompatTextView titleMovie;
    AppCompatTextView txtRatingText;
    AppCompatTextView txtLanguageValue;
    AppCompatTextView txtReleaseValue;
    AppCompatTextView txtMovieDescription;
    AppCompatTextView txtMovieProductionHouse;
    AppCompatTextView txtReleaseStatusValue;
    AppCompatTextView txtSimilarMovies;
    RecyclerView recyclerProductionHouse;
    RecyclerView recyclerSimilarMovies;
    CoordinatorLayout clParent;
    AppCompatButton btnCast;
    AppCompatButton btnReview;
    ScrollView scrollView;
    RatingBar ratingBar;
    private String movieid;
    View view;

    MovieDetailsView.MovieDetailsPresenter presenter;
    private MovieProviderAdapter movieProviderAdapter;
    private SimilarMoviesAdapter similarMoviesAdapter;
    private AppCompatImageView imgBack;


    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieid = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        initView();
        return view;
    }

    private void initView() {
        txtMovieProductionHouse = view.findViewById(R.id.txtMovieProductionHouse);
        ratingBar = view.findViewById(R.id.ratingBar);
        btnCast = view.findViewById(R.id.btnCast);
        btnReview = view.findViewById(R.id.btnReview);
        imgMovie = view.findViewById(R.id.imgMovie);
        titleMovie = view.findViewById(R.id.titleMovie);
        imgBack = view.findViewById(R.id.imgBack);
        clParent = view.findViewById(R.id.clParent);
        txtSimilarMovies = view.findViewById(R.id.txtSimilarMovies);
        recyclerProductionHouse = view.findViewById(R.id.recyclerProductionHouse);
        recyclerSimilarMovies = view.findViewById(R.id.recyclerSimilarMovies);

        txtMovieDescription = view.findViewById(R.id.txtMovieDescription);
        txtRatingText = view.findViewById(R.id.txtRatingText);
        txtReleaseValue = view.findViewById(R.id.txtReleaseValue);
        txtLanguageValue = view.findViewById(R.id.txtLanguageValue);
        txtReleaseStatusValue = view.findViewById(R.id.txtReleaseStatusValue);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).onBackPressed();
            }
        });

        recyclerProductionHouse.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerSimilarMovies.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        presenter = new MovieDetailsPresenter(getContext(), this, new MoviesDetailsImpl(getContext()));
        presenter.loadMovieDetailList(Integer.parseInt(movieid));

        movieProviderAdapter = new MovieProviderAdapter(getContext());
        ScaleInAnimatorAdapter animatorAdapter = new ScaleInAnimatorAdapter
                (movieProviderAdapter, recyclerProductionHouse);
        recyclerProductionHouse.setAdapter(animatorAdapter);

        similarMoviesAdapter = new SimilarMoviesAdapter(getContext());
        ScaleInAnimatorAdapter similarAdapterAnimation = new ScaleInAnimatorAdapter
                (similarMoviesAdapter, recyclerSimilarMovies);
        recyclerSimilarMovies.setAdapter(similarAdapterAnimation);


        btnCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", movieid);
                ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.container, CastFragment.class, bundle)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", movieid);
                ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .add(R.id.container, ReviewFragment.class, bundle)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void showMovieDetails(List<MovieDetails> movieDetailsList) {
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (movieDetailsList != null && movieDetailsList.size() > 0) {
                    MovieDetails movies = movieDetailsList.get(0);
                    titleMovie.setText(movies.getTitle());
                    txtReleaseStatusValue.setText(movies.getStatus());
                    Locale loc = new Locale(movies.getOriginal_language());
                    txtLanguageValue.setText(loc.getDisplayLanguage(loc));
                    txtReleaseValue.setText(Util.dateFormat(movies.getRelease_date()));
                    if (movies.getOverview() != "") {
                        txtMovieDescription.setText(movies.getOverview());
                    } else {
                        txtMovieDescription.setText(getString(R.string.not_available));
                    }

                    txtRatingText.setText(movies.getVote_average() + "");
                    ratingBar.setRating(movies.getVote_average());
                    String imagePath = Constants.URL_IMAGE_500 + movies.getPoster_path();
                    Picasso.get().load(imagePath).placeholder(R.drawable.default_image).fit().into(imgMovie);

                    if (movies.getProduction_companies() != null && movies.getProduction_companies().size() > 0) {
                        movieProviderAdapter.setList(movies.getProduction_companies());
                    } else {
                        txtMovieProductionHouse.setText(getString(R.string.production_house) + ": N/A");
                        recyclerProductionHouse.setVisibility(View.GONE);
                    }
                } else {
                    Util.errorDialog(getContext(), getString(R.string.moviedetail_title), getString(R.string.moviedesc));
                }
            }
        });


    }

    @Override
    public void showSimilarMovieDetails(List<PopularMovies> movies) {
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (movies != null && movies.size() > 0) {
                    similarMoviesAdapter.setList(movies);
                } else {
                    txtSimilarMovies.setText(getString(R.string.not_available));
                }
            }
        });

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
        Util.errorDialog(getContext(), getString(R.string.moviedetail_title), getString(R.string.moviedesc));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }
}
