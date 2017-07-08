package com.example.rhymebyrhyme.model;

/**
 * Created by Amir on 08.07.2017.
 */

public class Poem {
    private String title;
    private String text;
    private int likes;

    public Poem() {
    }

    public Poem(String title, String text, int likes) {
        this.title = title;
        this.text = text;
        this.likes = likes;
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
