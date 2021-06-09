package com.android.moviebag.classes;

import android.content.Context;
import android.util.Log;

import com.android.moviebag.Models.Cast;
import com.android.moviebag.Models.Review;
import com.android.moviebag.repository.CastRepo;
import com.android.moviebag.repository.ReviewRepo;
import com.android.moviebag.util.Constants;
import com.android.moviebag.view.CastView;
import com.android.moviebag.view.ReviewView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public final class ReviewImpl implements ReviewRepo {

    private Context context;
    private Gson gson;
    GsonBuilder gsonBuilder;


    public ReviewImpl(Context context) {
        this.context = context;
        gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }


    @Override
    public void getReviewList(int movieid, ReviewView.OnReviewResponseCallback callback) {
        Log.d("safiyas review url", Constants.getMovieReviewsURL(movieid));
        StringRequest request = new StringRequest(Constants.getMovieReviewsURL(movieid)
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    List<Review> reviewList = Arrays.asList(gson.fromJson
                            (String.valueOf(jsonObject.getJSONArray("results")), Review[].class));
                    callback.onResponse(reviewList);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError(volleyError.getMessage());
            }
        });
        VolleyConf.getVolleySingleton(context).addToRequestQueue(request);
    }
}
