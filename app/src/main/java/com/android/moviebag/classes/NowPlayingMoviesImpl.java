package com.android.moviebag.classes;

import android.content.Context;
import android.util.Log;

import com.android.moviebag.Models.NowPlayingMovies;
import com.android.moviebag.Models.PopularMovies;
import com.android.moviebag.repository.NowPlayingMoviesRepo;
import com.android.moviebag.repository.PopularMoviesRepo;
import com.android.moviebag.util.Constants;
import com.android.moviebag.view.NowPlayingMovieView;
import com.android.moviebag.view.PopularMovieView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public final class NowPlayingMoviesImpl implements NowPlayingMoviesRepo {

    private Context context;
    private Gson gson;
    GsonBuilder gsonBuilder;


    public NowPlayingMoviesImpl(Context context) {
        this.context = context;
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    @Override
    public void getMovieList(NowPlayingMovieView.OnNowPlayingResponseCallback callback) {
        Log.d("safiyas url", Constants.GET_NOW_PLAYING);
        StringRequest request = new StringRequest(Constants.GET_NOW_PLAYING, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    List<NowPlayingMovies> nowPlayingMoviesList = Arrays.asList(gson.fromJson
                            (String.valueOf(jsonObject.getJSONArray("results")),
                                    NowPlayingMovies[].class));
                    Log.d("safiyas pages", jsonObject.getString("total_pages"));
                    callback.onResponse(nowPlayingMoviesList);

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
