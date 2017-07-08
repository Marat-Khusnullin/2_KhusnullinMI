package com.example.rhymebyrhyme.model;

import java.util.List;

/**
 * Created by Amir on 08.07.2017.
 */

public class User {
    private String name;
    private String surname;
    private String email;
    private byte year;
    private String link;
    private String description;
    private List<Poem> poems;
    private int rating;

    public User() {
    }

    public User(String name, String surname, String email, byte year, String link, String description, List<Poem> poems, int rating) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.year = year;
        this.link = link;
        this.description = description;
        this.poems = poems;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte getYear() {
        return year;
    }

    public void setYear(byte year) {
        this.year = year;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Poem> getPoems() {
        return poems;
    }

    public void setPoems(List<Poem> poems) {
        this.poems = poems;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
