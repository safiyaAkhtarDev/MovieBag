package com.android.moviebag.classes;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.android.moviebag.Models.MovieDetails;
import com.android.moviebag.Models.MovieProvider;
import com.android.moviebag.Models.NowPlayingMovies;
import com.android.moviebag.Models.PopularMovies;
import com.android.moviebag.repository.MoviesDetailsRepo;
import com.android.moviebag.repository.NowPlayingMoviesRepo;
import com.android.moviebag.util.Constants;
import com.android.moviebag.view.MovieDetailsView;
import com.android.moviebag.view.NowPlayingMovieView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public final class MoviesDetailsImpl implements MoviesDetailsRepo {

    private Context context;
    private Gson gson;
    GsonBuilder gsonBuilder;


    public MoviesDetailsImpl(Context context) {
        this.context = context;
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    @Override
    public void getMovieList(int movieid, MovieDetailsView.OnMovieDetailsResponseCallback callback) {
        Log.d("safiyas url", Constants.getMovieDetailURL(movieid));
        StringRequest request = new StringRequest(Constants.getMovieDetailURL(movieid)
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    List<MovieDetails> movieDetailsList = Arrays.asList(gson.fromJson
                            (s, MovieDetails.class));
                    callback.onResponse(movieDetailsList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError(volleyError);
            }
        });
        VolleyConf.getVolleySingleton(context).addToRequestQueue(request);
    }
    @Override
    public void getSimilarMovies(int movieid, MovieDetailsView.OnSimilarMovieResponseCallback callback) {
        Log.d("safiyas url", Constants.getGetSimilarMoviesURL(movieid));
        StringRequest request = new StringRequest(Constants.getGetSimilarMoviesURL(movieid), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    List<PopularMovies> similarMoviesList = Arrays.asList(gson.fromJson
                            (String.valueOf(jsonObject.getJSONArray("results")), PopularMovies[].class));
                    callback.onResponse(similarMoviesList);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError(volleyError);
            }
        });
        VolleyConf.getVolleySingleton(context).addToRequestQueue(request);
    }


}
