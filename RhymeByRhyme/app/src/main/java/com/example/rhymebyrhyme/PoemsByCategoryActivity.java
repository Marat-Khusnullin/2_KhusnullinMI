package com.example.rhymebyrhyme;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rhymebyrhyme.adapter.CategoryListAdapter;
import com.example.rhymebyrhyme.adapter.PoemsByCategoryListAdapter;
import com.example.rhymebyrhyme.model.Poem;
import com.example.rhymebyrhyme.model.User;
import com.example.rhymebyrhyme.model.UserWithID;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class PoemsByCategoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    ActionBarDrawerToggle toggle;
    LinkedList<Poem> list = new LinkedList<>();;
    ArrayList<UserWithID> userList = new ArrayList<>();
    private DatabaseReference mRef;
    String category;
    int count =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poems_by_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        category = getIntent().getStringExtra("category");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerByCategory);
        setList();


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
            Intent intent = new Intent(PoemsByCategoryActivity.this, UsersListActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(PoemsByCategoryActivity.this, MainProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_top_authors){
            Intent intent = new Intent(PoemsByCategoryActivity.this, TopAuthorsActivity.class);
            startActivity(intent);
        } else if( id == R.id.nav_ownpoem) {
            Intent intent = new Intent(PoemsByCategoryActivity.this, OwnPoemActivity.class);
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

    private void setList() {
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eachUser: dataSnapshot.getChildren()){
                    User user = new User(eachUser.child("name").getValue().toString(),
                            eachUser.child("surname").getValue().toString(),
                            eachUser.child("email").getValue().toString(),
                            Integer.parseInt(eachUser.child("year").getValue().toString()),
                            eachUser.child("link").getValue().toString(),
                            eachUser.child("description").getValue().toString(),
                            Integer.parseInt(eachUser.child("poemCount").getValue().toString()),
                            Integer.parseInt(eachUser.child("rating").getValue().toString()));
                    UserWithID userWithID = new UserWithID(eachUser.getKey(), user);
                    userList.add(userWithID);
                }
                secondSetter();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void secondSetter() {

        if (count < userList.size()) {
            User user = userList.get(count).getUser();
            mRef = FirebaseDatabase.getInstance().getReference();
            if (user.getPoemCount() > 0) {
                mRef.child("poems").child(userList.get(count).getFirebaseID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Poem poem = postSnapshot.getValue(Poem.class);

                            if ( poem.getCategory()!=null &&poem.getCategory().equals(category)) {
                                list.add(poem);
                            }

                        }
                        count++;
                        secondSetter();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                count++;
                secondSetter();
            }

        }
        else {
            PoemsByCategoryListAdapter adapter = new PoemsByCategoryListAdapter(list, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setAdapter(adapter);

        }
    }
}