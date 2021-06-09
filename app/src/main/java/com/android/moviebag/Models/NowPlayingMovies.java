package com.android.moviebag.Models;

public class NowPlayingMovies {
    public String getPoster_path() {
        return poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    private String poster_path;

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    private String original_title;
    private String release_date;
}
