package com.android.moviebag.util;

public class Constants {
    public static String API_KEY = "ace5e315c4273b413239ee5c9ab4dcb2";
    public static String URL_IMAGE_185 = "https://image.tmdb.org/t/p/w185";
    public static String URL_IMAGE_500 = "https://image.tmdb.org/t/p/w500";
    public static String GET_Popular_MOVIES = "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=en-US&page=";
    public static String GET_NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=en-US&page=1";

    public static String getMovieDetailURL(int movieid) {
        return "https://api.themoviedb.org/3/movie/" + movieid + "?api_key=" + API_KEY + "&language=en-US";
    }

    public static String getMovieCastURL(int movieid) {
        return "https://api.themoviedb.org/3/movie/" + movieid + "/credits?" +
                "api_key=" + API_KEY + "&language=en-US";
    }

    public static String getMovieReviewsURL(int movieid) {
        return "https://api.themoviedb.org/3/movie/" + movieid + "/reviews?" +
                "api_key=" + API_KEY + "&language=en-US";
    }

    public static String getGetSimilarMoviesURL(int movieid) {
        return "https://api.themoviedb.org/3/movie/" + movieid +
                "/similar?api_key=" + API_KEY + "&language=en-US&page=1";
    }
}
