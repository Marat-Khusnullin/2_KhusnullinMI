package com.example.rhymebyrhyme;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
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
    private CircleImageView imageView;
    private DatabaseReference mRef;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Профиль");

        mRef = FirebaseDatabase.getInstance().getReference();
        mainLayout =(LinearLayout)this.findViewById(R.id.user_profile_main_layout);
        progressBar = (ProgressBar) findViewById(R.id.user_profile_progressBar);
        context = this;


        poems = (TextView) findViewById(R.id.user_profile_poems);
        poemsCount = (TextView) findViewById(R.id.user_profile_poemcount);
        readers = (TextView) findViewById(R.id.user_profile_readers);
        readersCount = (TextView) findViewById(R.id.user_profile_readersCount);
        userEmail = (TextView) findViewById(R.id.user_profile_useremail);
        name = (TextView) findViewById(R.id.user_profile_name);
        userName = (TextView) findViewById(R.id.user_profile_username);
        surname = (TextView) findViewById(R.id.user_profile_surname);
        userSurname = (TextView) findViewById(R.id.user_profile_usersurname);
        link = (TextView) findViewById(R.id.user_profile_link);
        userLink = (TextView) findViewById(R.id.user_profile_userlink);
        about = (TextView) findViewById(R.id.user_profile_about);
        userAbout = (TextView) findViewById(R.id.user_profile_userabout);
        watchPoems = (TextView) findViewById(R.id.user_profile_watchpoems);
        imageView = (CircleImageView) findViewById(R.id.user_profile_imageview);

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

        watchPoems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, MainPoemsListActivity.class);
                intent.putExtra("id","" +  getIntent().getStringExtra("userID") );
                startActivity(intent);
            }
        });

        setUserInformation();
        mainLayout.setVisibility(LinearLayout.GONE);
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void setUserInformation(){
        mRef.child("users").child(getIntent().getStringExtra("userID")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                poemsCount.setText(""+ dataSnapshot.child("poemCount").getValue());
                readersCount.setText(""+ dataSnapshot.child("readersCount").getValue());
                userEmail.setText(""+ dataSnapshot.child("email").getValue());
                userName.setText(""+ dataSnapshot.child("name").getValue());
                userSurname.setText(""+ dataSnapshot.child("surname").getValue());
                userLink.setText(""+ dataSnapshot.child("link").getValue());
                userAbout.setText(""+ dataSnapshot.child("description").getValue());
                mainLayout.setVisibility(LinearLayout.VISIBLE);
                progressBar.setVisibility(ProgressBar.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

        mStorageRef.child("images/" + getIntent().getStringExtra("userID")).getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                String path = storageMetadata.getDownloadUrl().toString();
                Picasso.with(context).load(path).resize(200,200).centerCrop().into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/rhymebyrhyme.appspot.com/o/24649396.png?alt=media&token=374987c9-3582-4ee7-8861-5962af10cee0").resize(200,200).centerCrop().into(imageView);
            }
        });


        progressBar.setVisibility(ProgressBar.GONE);
        mainLayout.setVisibility(LinearLayout.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_poems) {

        } else if (id == R.id.nav_authors) {
            Intent intent = new Intent(UserProfileActivity.this, UsersListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(UserProfileActivity.this, MainProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_top_authors){
            Intent intent = new Intent(UserProfileActivity.this, TopAuthorsActivity.class);
            startActivity(intent);
        } else if( id == R.id.nav_ownpoem) {
            Intent intent = new Intent(UserProfileActivity.this, OwnPoemActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }
}
