package com.example.rhymebyrhyme.database;


import com.example.rhymebyrhyme.model.Poem;

import com.example.rhymebyrhyme.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Amir on 08.07.2017.
 */

public class Dao {


    private DatabaseReference mRef;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private User currentUser;


    public User getUserByEmail(String email){

        mRef = FirebaseDatabase.getInstance().getReference();

        mRef.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<User> genericTypeIndicator = new GenericTypeIndicator<User>() {};
                currentUser = dataSnapshot.getValue(genericTypeIndicator);

                currentUser = dataSnapshot.getValue(User.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        
        return currentUser;
    }



    public void writeUser(String userId, String name, String surname, String email, int year,
                           String link, String description){
        User newUser = new User(name, surname, email, year, link, description, null ,0);
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("users").child(userId).setValue(newUser);
    }

    private void writeUser(String userId, User newUser){
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("users").child(userId).setValue(newUser);
    }

    private void addNewPoem(String userId, Poem poem){
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("users").child(userId).child("poems").setValue(poem);
    }

}
