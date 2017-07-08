package com.example.rhymebyrhyme.database;

import com.example.rhymebyrhyme.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Amir on 08.07.2017.
 */

public class Dao {
    private DatabaseReference mRef;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private User currentUser;
    private User getUserByEmail(String email){
        mRef = FirebaseDatabase.getInstance().getReference();

        mRef.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return currentUser;
    }

    private void writeUser(String userId, String name, String surname, String email, byte year,
                           String link, String description, int poemCount, int rating){
        User newUser = new User(name, surname, email, year, link, description, 0, 0);
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("users").child(userId).setValue(newUser);
    }
}
