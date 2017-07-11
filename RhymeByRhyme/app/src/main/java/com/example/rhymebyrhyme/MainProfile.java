package com.example.rhymebyrhyme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

public class MainProfile extends AppCompatActivity
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
    private CircleImageView imageView;
    private SharedPreferences sPref;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        context = this;

        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mainLayout =(LinearLayout)this.findViewById(R.id.mainlayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        this.setTitle("Мой профиль");


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

        changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserInfoActivity.class);
                startActivity(intent);
            }
        });



        setUserInformation();
        mainLayout.setVisibility(LinearLayout.GONE);
        progressBar.setVisibility(ProgressBar.VISIBLE);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
