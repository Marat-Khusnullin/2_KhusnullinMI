package com.example.rhymebyrhyme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class MainProfileActivity extends AppCompatActivity
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
    private TextView changeInfo;
    private TextView exit;
    private CircleImageView imageView;
    private SharedPreferences sPref;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        mainLayout =(LinearLayout)this.findViewById(R.id.mainlayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

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
        imageView = (CircleImageView) findViewById(R.id.imageview);
        changeInfo = (TextView) findViewById(R.id.changeinfo);
        exit = (TextView) findViewById(R.id.exit);

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
        changeInfo.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        exit.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));

        changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserInfoActivity.class);
                startActivity(intent);
            }
        });

        watchPoems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainPoemsListActivity.class);
                intent.putExtra("id","" +  user.getUid() );
                startActivity(intent);
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUserInformation();
        mainLayout.setVisibility(LinearLayout.GONE);
        progressBar.setVisibility(ProgressBar.VISIBLE);
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

        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_about) {

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
                mainLayout.setVisibility(LinearLayout.VISIBLE);
                progressBar.setVisibility(ProgressBar.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

        mStorageRef.child("images/" + user.getUid()).getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                String path = storageMetadata.getDownloadUrl().toString();
                Picasso.with(context).load(path).resize(200,200).centerCrop().into(imageView);
            }
        });


        progressBar.setVisibility(ProgressBar.GONE);
        mainLayout.setVisibility(LinearLayout.VISIBLE);
    }
}