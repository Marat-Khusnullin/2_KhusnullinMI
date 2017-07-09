package com.example.rhymebyrhyme;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rhymebyrhyme.database.Dao;
import com.example.rhymebyrhyme.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainProfile extends AppCompatActivity {
    private TextView name;
    private TextView about;
    private SharedPreferences sPref;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);

        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();

        //FirebaseUser user = mAuth.getCurrentUser();
        mRef.child("users").child("0").child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String string = (String) dataSnapshot.getValue();
                name.setText(string);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //name.setText(mRef.child("users").child("0").child("name"));
    }



}
