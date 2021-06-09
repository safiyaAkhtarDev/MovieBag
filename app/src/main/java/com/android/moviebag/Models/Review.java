package com.android.moviebag.Models;

import org.json.JSONObject;

import java.nio.channels.FileLock;

public class Review {
    private String name;
    private String avatar_path;
    private String content;
    private String created_at;

    public AuthorDetails getAuthor_details() {
        return author_details;
    }

    public void setAuthor_details(AuthorDetails author_details) {
        this.author_details = author_details;
    }

    private AuthorDetails author_details;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    private Float rating;
}
