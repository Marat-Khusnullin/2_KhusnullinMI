package com.example.rhymebyrhyme.model;

/**
 * Created by Amir on 08.07.2017.
 */

public class Poem {
    private String title;
    private String text;
    private int likes;
    private int id;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Poem() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Poem(int id, String title, String text, int likes, String date) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.likes = likes;
        this.date = date;

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
