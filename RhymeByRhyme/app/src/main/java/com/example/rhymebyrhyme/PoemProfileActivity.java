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
import android.widget.TextView;

import com.example.rhymebyrhyme.model.Poem;

public class PoemProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView title;
    TextView date;
    TextView text;
    TextView likes;

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

        title.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-BoldCondensed.ttf"));
        text.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        date.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-LightItalic.ttf"));
        likes.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));

        Poem poem =(Poem) getIntent().getSerializableExtra("poem");
        title.setText(poem.getTitle());
        text.setText(Html.fromHtml(poem.getText()));
        date.setText(poem.getDate());
        likes.setText(""+ poem.getLikes());




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

