package com.example.rhymebyrhyme;

import android.content.Intent;
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
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rhymebyrhyme.model.Poem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PoemProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView title;
    TextView date;
    TextView text;
    TextView likes;
    ImageView likeImage;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private DatabaseReference mRef2;
    Poem poem;

    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        title = (TextView) findViewById(R.id.poemProfileTitle);
        date = (TextView) findViewById(R.id.poemProfileDate);
        text = (TextView) findViewById(R.id.poemProfileText);
        likes = (TextView) findViewById(R.id.poemProfileLikes);
        likeImage = (ImageView) findViewById(R.id.heartLikes);

        title.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-BoldCondensed.ttf"));
        text.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        date.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-LightItalic.ttf"));
        likes.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));

        likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        poem = (Poem) getIntent().getSerializableExtra("poem");
        title.setText(poem.getTitle());
        text.setText(Html.fromHtml(poem.getText()));
        date.setText(poem.getDate());
        likes.setText("" + poem.getLikes());

        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mRef2 = FirebaseDatabase.getInstance().getReference();

        if (poem.isLike()) {
            likeImage.setImageResource(R.drawable.blackheart);
        }

        likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!poem.isLike()) {
                    mRef.child("users").child(poem.getuId()).child("rating").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int oldRating = Integer.parseInt("" + dataSnapshot.getValue());

                            mRef2.child("users").child(poem.getuId()).child("rating").setValue(++oldRating);
                            likeImage.setImageResource(R.drawable.blackheart);
                            poem.setLike(true);
                            mRef2 = FirebaseDatabase.getInstance().getReference();
                            mRef2.child("poems").child(""+ poem.getId()).child("likesAuthors").child(mAuth.getCurrentUser().getUid()).child("like")
                                    .setValue("true");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    mRef = FirebaseDatabase.getInstance().getReference();
                    mRef.child("poems").child("" + poem.getId()).child("likesAuthors").child(mAuth.getCurrentUser().getUid())
                            .child("like").setValue("false");
                    mRef = FirebaseDatabase.getInstance().getReference();
                    mRef2 = FirebaseDatabase.getInstance().getReference();
                    mRef.child("users").child(poem.getuId()).child("rating").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int oldRating = Integer.parseInt("" + dataSnapshot.getValue());
                            mRef2.child("users").child(poem.getuId()).child("rating").setValue(--oldRating);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    mRef = FirebaseDatabase.getInstance().getReference();
                    mRef.child("poems").child(""+ poem.getId()).child("likesAuthors").child(mAuth.getCurrentUser().getUid()).child("like")
                            .setValue("false");
                    likeImage.setImageResource(R.drawable.heart);
                    poem.setLike(false);

                }


            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            Intent intent = new Intent(PoemProfileActivity.this, UsersListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(PoemProfileActivity.this, MainProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_top_authors){
            Intent intent = new Intent(PoemProfileActivity.this, TopAuthorsActivity.class);
            startActivity(intent);
        } else if( id == R.id.nav_ownpoem) {
            Intent intent = new Intent(PoemProfileActivity.this, OwnPoemActivity.class);
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

