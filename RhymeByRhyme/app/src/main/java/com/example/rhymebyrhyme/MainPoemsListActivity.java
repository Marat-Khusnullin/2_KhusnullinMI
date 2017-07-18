package com.example.rhymebyrhyme;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rhymebyrhyme.adapter.PoemsListAdapter;
import com.example.rhymebyrhyme.model.Poem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class MainPoemsListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle toggle;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private Intent intent;
    private LinkedList<Poem> poems;
    private String currentId;
    private int poemsCount;
    private RecyclerView rv;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_poem);
        setTitle("Стихотворения");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = getIntent();
        currentId = intent.getStringExtra("id");
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        rv = (RecyclerView) findViewById(R.id.recycler);
        context = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mRef.child("users").child(currentId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                poemsCount = Integer.parseInt("" + dataSnapshot.child("poemCount").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        setPoems();

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
            Intent intent = new Intent(MainPoemsListActivity.this, PoemsCategoriesListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_feed) {
            Intent intent = new Intent(MainPoemsListActivity.this, FeedActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_authors) {
            Intent intent = new Intent(MainPoemsListActivity.this, UsersListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(MainPoemsListActivity.this, MainProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_top_authors){
            Intent intent = new Intent(MainPoemsListActivity.this, TopAuthorsActivity.class);
            startActivity(intent);
        } else if( id == R.id.nav_ownpoem) {
            Intent intent = new Intent(MainPoemsListActivity.this, OwnPoemActivity.class);
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

    private void setPoems() {
        poems = new LinkedList();
        DatabaseReference mRef;

            mRef = FirebaseDatabase.getInstance().getReference();
            mRef.child("poems").child(currentId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                       Poem poem = postSnapshot.getValue(Poem.class);


                        poems.add(poem);
                    }
                    PoemsListAdapter adapter = new PoemsListAdapter(poems, context);
                    rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
                    rv.setAdapter(adapter);

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




    }



}
