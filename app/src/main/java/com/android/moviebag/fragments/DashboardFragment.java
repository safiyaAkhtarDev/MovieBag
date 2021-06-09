package com.android.moviebag.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;

import com.android.moviebag.Models.NowPlayingMovies;
import com.android.moviebag.Models.PopularMovies;
import com.android.moviebag.R;
import com.android.moviebag.adapters.NowPlayingMoviesAdapter;
import com.android.moviebag.adapters.PopularMovieAdapter;
import com.android.moviebag.classes.NowPlayingMoviesImpl;
import com.android.moviebag.classes.PopularMoviesImpl;
import com.android.moviebag.presenter.NowPlayingMoviePresenter;
import com.android.moviebag.presenter.PopularMoviePresenter;
import com.android.moviebag.util.Util;
import com.android.moviebag.view.NowPlayingMovieView;
import com.android.moviebag.view.PopularMovieView;
import com.google.android.material.tabs.TabLayout;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import it.gmariotti.recyclerview.adapter.ScaleInAnimatorAdapter;


public class DashboardFragment extends Fragment implements PopularMovieView.View, NowPlayingMovieView.NowPlayingView {


    private ViewPager mainPager;
    View view;
    private RecyclerView recyclerTrending;
    private ProgressBar loadMoreProgress;
    private NestedScrollView nestedScrollView;
    private PopularMovieAdapter popularMovieAdapter;
    private PopularMovieView.Presenter presenter;
    private NowPlayingMovieView.NowPlayingPresenter nowPlayingPresenter;
    int currentpage = 1;
    private int total_pages = 0;
    private NowPlayingMoviesAdapter nowPlayingMoviesAdapter;
    private int NUM_PAGES;
    private int currentPageSlider;
    private Timer swipeTimer;
    CirclePageIndicator mainViewPagerCountDots;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initViews();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initViews() {
        loadMoreProgress = view.findViewById(R.id.loadMoreProgress);
        recyclerTrending = (RecyclerView) view.findViewById(R.id.recyclerTrending); // list
        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        mainPager = view.findViewById(R.id.mainPager);
        mainViewPagerCountDots = view.findViewById(R.id.mainViewPagerCountDots);

        presenter = new PopularMoviePresenter(getContext(), this, new PopularMoviesImpl(getContext()));
        presenter.loadMoviewList(currentpage);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerTrending.setLayoutManager(linearLayoutManager); // for vertical liner list
        popularMovieAdapter = new PopularMovieAdapter(getContext());
        ScaleInAnimatorAdapter animatorAdapter = new ScaleInAnimatorAdapter
                (popularMovieAdapter, recyclerTrending);
        recyclerTrending.setAdapter(animatorAdapter);

        //Set circle indicator radius
        final float density = getContext().getResources().getDisplayMetrics().density;
        mainViewPagerCountDots.setRadius(4 * density);
        nowPlayingPresenter = new NowPlayingMoviePresenter(getContext(),
                this, new NowPlayingMoviesImpl(getContext()));
        nowPlayingPresenter.loadNowPlayingMoviewList();

        nowPlayingMoviesSlider();
        popularMoviesPagination();
    }

    private void popularMoviesPagination() {
        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = (View) nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
                int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView
                        .getScrollY()));
                if (diff == 0) {
                    //  pagination code
                    if (currentpage <= total_pages) {
                        currentpage++;
                        presenter.loadMoviewList(currentpage);
                    }

                }
            }
        });

    }

    @Override
    public void startLoading() {
//        loadMoreProgress.setVisibility(View.VISIBLE);
//        Util.showProgressDialog(getContext());
    }

    @Override
    public void stopLoading() {
//        loadMoreProgress.setVisibility(View.GONE);
//        Util.hideProgressDialog();
    }

    @Override
    public void showMovieList(List<PopularMovies> movies, int total_pages) {
        if (!movies.isEmpty()) {
            this.total_pages = total_pages;
            if (currentpage <= 1) {
                popularMovieAdapter.setList(movies);
            } else if (currentpage <= total_pages) {
                popularMovieAdapter.updateList(movies);
            }
        }
    }

    @Override
    public void showNowPlayingMovieList(List<NowPlayingMovies> movies) {
        if (!movies.isEmpty()) {
            nowPlayingMoviesAdapter.setList(movies);
            NUM_PAGES = movies.size();

            // Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    try {

                        if (currentPageSlider == NUM_PAGES) {
                            currentPageSlider = 0;
                        }
                        if (mainPager != null) {
                            mainPager.setCurrentItem(currentPageSlider++, true);
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            };
            mainViewPagerCountDots.setViewPager(mainPager);

            try {
                swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 3000, 3000);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void startNowPlayingLoading() {
        Util.showProgressDialog(getContext());
    }

    @Override
    public void stopNowPlayingLoading() {
        Util.hideProgressDialog();
    }

    @Override
    public void showLoadingError(String errMsg) {
        Util.errorDialog(getContext(), getString(R.string.error_movies),
                getString(R.string.error_movies_desc));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
        nowPlayingPresenter.dropView();
    }

    private void nowPlayingMoviesSlider() {
        nowPlayingMoviesAdapter = new NowPlayingMoviesAdapter(getContext());
        mainPager.setAdapter(nowPlayingMoviesAdapter);

        // Pager listener over indicator
        mainViewPagerCountDots.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPageSlider = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


    }
}