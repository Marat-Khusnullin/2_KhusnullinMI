package com.example.rhymebyrhyme;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainProfile extends AppCompatActivity {
    private LinearLayout mainLayout;
    private ProgressBar progressBar;
    private TextView poems;
    private TextView poemsCount;
    private TextView readers;
    private TextView readersCount;
    private TextView userEmail;
    private TextView name;
    private TextView userName;
    private TextView surname;
    private TextView userSurname;
    private TextView link;
    private TextView userLink;
    private TextView about;
    private TextView userAbout;
    private TextView watchPoems;
    private SharedPreferences sPref;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);

        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        mainLayout =(LinearLayout)this.findViewById(R.id.mainlayout);
        mainLayout.setVisibility(LinearLayout.GONE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        poems = (TextView) findViewById(R.id.poems);
        poemsCount = (TextView) findViewById(R.id.poemcount);
        readers = (TextView) findViewById(R.id.readers);
        readersCount = (TextView) findViewById(R.id.readersCount);
        userEmail = (TextView) findViewById(R.id.useremail);
        name = (TextView) findViewById(R.id.name);
        userName = (TextView) findViewById(R.id.username);
        surname = (TextView) findViewById(R.id.surname);
        userSurname = (TextView) findViewById(R.id.usersurname);
        link = (TextView) findViewById(R.id.link);
        userLink = (TextView) findViewById(R.id.userlink);
        about = (TextView) findViewById(R.id.about);
        userAbout = (TextView) findViewById(R.id.userabout);
        watchPoems = (TextView) findViewById(R.id.watchpoems);

        poems.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        poemsCount.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-BoldCondensed.ttf"));
        readers.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        readersCount.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-BoldCondensed.ttf"));
        userEmail.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-BoldCondensed.ttf"));
        name.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        userName.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        surname.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        userSurname.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        link.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        userLink.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        about.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        userAbout.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        watchPoems.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Black.ttf"));

        setUserInformation();



        //FirebaseUser user = mAuth.getCurrentUser();
        /*mRef.child("users").child("0").child("name").addListenerForSingleValueEvent(new ValueEventListener() {
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
    }*/


    }

    private void setUserInformation(){
        FirebaseUser user = mAuth.getCurrentUser();
        mRef.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                poemsCount.setText(""+ dataSnapshot.child("poemCount").getValue());
                readersCount.setText(""+ dataSnapshot.child("readersCount").getValue());
                userEmail.setText(""+ dataSnapshot.child("email").getValue());
                userName.setText(""+ dataSnapshot.child("name").getValue());
                userSurname.setText(""+ dataSnapshot.child("surname").getValue());
                userLink.setText(""+ dataSnapshot.child("link").getValue());
                userAbout.setText(""+ dataSnapshot.child("description").getValue());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        progressBar.setVisibility(ProgressBar.GONE);
        mainLayout.setVisibility(LinearLayout.VISIBLE);
    }
}
