package com.example.rhymebyrhyme.model;

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
    private int poemCount;
    private int rating;

    public User() {
    }

    public User(String name, String surname, String email, byte year, String link, String description, int poemCount, int rating) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.year = year;
        this.link = link;
        this.description = description;
        this.poemCount = poemCount;
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

    public int getPoemCount() {
        return poemCount;
    }

    public void setPoemCount(int poemCount) {
        this.poemCount = poemCount;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
