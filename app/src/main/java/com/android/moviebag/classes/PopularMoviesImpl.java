package com.android.moviebag.classes;

import android.content.Context;
import android.util.Log;

import com.android.moviebag.util.Constants;
import com.android.moviebag.Models.PopularMovies;
import com.android.moviebag.repository.PopularMoviesRepo;
import com.android.moviebag.util.Util;
import com.android.moviebag.view.PopularMovieView;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public final class PopularMoviesImpl implements PopularMoviesRepo {

    private Context context;
    private Gson gson;
    GsonBuilder gsonBuilder;


    public PopularMoviesImpl(Context context) {
        this.context = context;
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    @Override
    public void getPopularMovieList(int currentpage, PopularMovieView.OnResponseCallback callback) {
        Log.d("safiyas url", Constants.GET_Popular_MOVIES + currentpage);
        StringRequest request = new StringRequest(Constants.GET_Popular_MOVIES + currentpage, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    List<PopularMovies> popularMoviesList = Arrays.asList(gson.fromJson(String.valueOf(jsonObject.getJSONArray("results")), PopularMovies[].class));
                    Log.d("safiyas pages", jsonObject.getString("total_pages"));
                    callback.onResponse(popularMoviesList, Integer.parseInt(jsonObject.getString("total_pages")));

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
