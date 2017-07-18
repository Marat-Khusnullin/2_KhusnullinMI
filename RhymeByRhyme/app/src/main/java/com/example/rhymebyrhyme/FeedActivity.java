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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rhymebyrhyme.adapter.PoemsByCategoryListAdapter;
import com.example.rhymebyrhyme.model.Poem;
import com.example.rhymebyrhyme.model.User;
import com.example.rhymebyrhyme.model.UserWithID;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class FeedActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle toggle;
    FirebaseUser currentUser;
    RecyclerView recyclerView;
    PoemsByCategoryListAdapter mAdapter;
    DatabaseReference mRef;
    List<UserWithID> userList;
    LinkedList<Poem> list;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.feed_recycler);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userList = new ArrayList<>();
        list = new LinkedList<>();
        mRef = FirebaseDatabase.getInstance().getReference();

        mRef.child("subs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final long[] pendingLoadCount = {dataSnapshot.getChildrenCount()};
                for (final DataSnapshot data : dataSnapshot.getChildren()) {
                    pendingLoadCount[0] = pendingLoadCount[0] - 1;
                    if (data.hasChild(currentUser.getUid())) {
                        mRef.child("users").child(data.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot newData) {
                                User user = new User(newData.child("name").getValue().toString(),
                                        newData.child("surname").getValue().toString(),
                                        newData.child("email").getValue().toString(),
                                        Integer.parseInt(newData.child("year").getValue().toString()),
                                        newData.child("link").getValue().toString(),
                                        newData.child("description").getValue().toString(),
                                        Integer.parseInt(newData.child("poemCount").getValue().toString()),
                                        Integer.parseInt(newData.child("rating").getValue().toString()));
                                UserWithID userWithID = new UserWithID(data.getKey(), user);
                                userList.add(userWithID);
                                if (pendingLoadCount[0] < 1) {
                                    poemsSetter();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void poemsSetter() {

        if (count < userList.size()) {
            User user = userList.get(count).getUser();
            mRef = FirebaseDatabase.getInstance().getReference();
            if (user.getPoemCount() > 0) {
                mRef.child("poems").child(userList.get(count).getFirebaseID()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Poem poem = postSnapshot.getValue(Poem.class);
                            list.add(poem);
                        }
                        count++;
                        poemsSetter();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                count++;
                poemsSetter();
            }

        }
        else {
            Collections.sort(list, new Comparator<Poem>() {
                @Override
                public int compare(Poem poem, Poem t1) {
                    return t1.getDate().compareTo(poem.getDate());
                }
            });
            mAdapter = new PoemsByCategoryListAdapter(list, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setAdapter(mAdapter);

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_poems) {
            Intent intent = new Intent(FeedActivity.this, PoemsCategoriesListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_feed) {

        } else if (id == R.id.nav_authors) {
            Intent intent = new Intent(FeedActivity.this, UsersListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(FeedActivity.this, MainProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_top_authors) {
            Intent intent = new Intent(FeedActivity.this, TopAuthorsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_ownpoem) {
            Intent intent = new Intent(FeedActivity.this, OwnPoemActivity.class);
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
