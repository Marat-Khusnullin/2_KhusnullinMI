package com.example.rhymebyrhyme.model;

import java.io.Serializable;

/**
 * Created by Amir on 08.07.2017.
 */

public class Poem implements Serializable {
    private String title;
    private String text;
    private int likes;
    private int id;
    private String uId;
    private String date;
    private String category;
    private boolean like;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public Poem(String uId, int id, String title, String text, int likes, String date, String category) {
        this.uId = uId;
        this.id = id;
        this.title = title;
        this.text = text;
        this.likes = likes;
        this.date = date;
        this.category = category;

    }

    public Poem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
