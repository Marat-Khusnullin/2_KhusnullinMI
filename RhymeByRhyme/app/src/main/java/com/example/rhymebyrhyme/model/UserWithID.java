package com.example.rhymebyrhyme.model;

/**
 * Created by Amir on 12.07.2017.
 */

public class UserWithID {
    User user;
    String firebaseID;

    public UserWithID() {
    }

    public UserWithID(String firebaseID, User user) {
        this.user = user;
        this.firebaseID = firebaseID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirebaseID() {
        return firebaseID;
    }

    public void setFirebaseID(String firebaseID) {
        this.firebaseID = firebaseID;
    }
}
