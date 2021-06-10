package com.android.moviebag.classes;

import android.content.Context;
import android.util.Log;

import com.android.moviebag.Models.Cast;
import com.android.moviebag.Models.MovieDetails;
import com.android.moviebag.Models.PopularMovies;
import com.android.moviebag.repository.CastRepo;
import com.android.moviebag.repository.MoviesDetailsRepo;
import com.android.moviebag.util.Constants;
import com.android.moviebag.view.CastView;
import com.android.moviebag.view.MovieDetailsView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public final class CastImpl implements CastRepo {

    private Context context;
    private Gson gson;
    GsonBuilder gsonBuilder;


    public CastImpl(Context context) {
        this.context = context;
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    @Override
    public void getCastList(int movieid, CastView.OnCastResponseCallback callback) {
        Log.d("safiyas url", Constants.getMovieCastURL(movieid));
        StringRequest request = new StringRequest(Constants.getMovieCastURL(movieid)
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    List<Cast> castsList = Arrays.asList(gson.fromJson
                            (String.valueOf(jsonObject.getJSONArray("cast")), Cast[].class));
                    callback.onResponse(castsList);
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
