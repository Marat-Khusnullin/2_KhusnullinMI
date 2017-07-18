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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rhymebyrhyme.model.User;
import com.example.rhymebyrhyme.model.UserWithID;
import com.google.android.gms.tasks.OnFailureListener;
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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubscribersActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    RecyclerView recyclerView;
    StorageReference mStorageRef;
    ProgressBar progressBar;
    FirebaseUser currentUser;
    DatabaseReference mRef;
    List<UserWithID> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        progressBar = (ProgressBar) findViewById(R.id.subscribers_list_progress);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView) findViewById(R.id.subscribers_list_recycler);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference();
        userList = new ArrayList<>();

        if (getIntent().getStringExtra("Activity_Key").equals("subscribers")) {
            setTitle("Подписчики");

            final List<String> userIDs = new ArrayList<>();
            mRef.child("subs").child(getIntent().getStringExtra("userID")).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSn : dataSnapshot.getChildren()) {
                        userIDs.add(dataSn.getKey());
                    }
                    final long[] pendingLoadCount = {dataSnapshot.getChildrenCount()};
                    for (final String id : userIDs) {
                        mRef.child("users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot data) {
                                User user = new User(data.child("name").getValue().toString(),
                                        data.child("surname").getValue().toString(),
                                        data.child("email").getValue().toString(),
                                        Integer.parseInt(data.child("year").getValue().toString()),
                                        data.child("link").getValue().toString(),
                                        data.child("description").getValue().toString(),
                                        Integer.parseInt(data.child("poemCount").getValue().toString()),
                                        Integer.parseInt(data.child("rating").getValue().toString()));
                                pendingLoadCount[0] = pendingLoadCount[0] - 1;
                                UserWithID userWithID = new UserWithID(id, user);
                                userList.add(userWithID);
                                if (pendingLoadCount[0] < 1){
                                    SubscribersListAdapter mAdapter = new SubscribersListAdapter(userList, SubscribersActivity.this);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                                    recyclerView.setAdapter(mAdapter);
                                    progressBar.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    if (userIDs.size() == 0) {
                        SubscribersListAdapter mAdapter = new SubscribersListAdapter(userList, SubscribersActivity.this);
                        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                        recyclerView.setAdapter(mAdapter);
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            setTitle("Подписки");

            mRef.child("subs").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    final long[] pendingLoadCount = {dataSnapshot.getChildrenCount()};
                    for (final DataSnapshot data : dataSnapshot.getChildren()) {
                        pendingLoadCount[0] = pendingLoadCount[0] - 1;
                        if (data.hasChild(getIntent().getStringExtra("userID"))) {
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
                                        SubscribersListAdapter mAdapter = new SubscribersListAdapter(userList, SubscribersActivity.this);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                                        recyclerView.setAdapter(mAdapter);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                        else {
                            if (pendingLoadCount[0] < 1) {
                                SubscribersListAdapter mAdapter = new SubscribersListAdapter(userList, SubscribersActivity.this);
                                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                                recyclerView.setAdapter(mAdapter);
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private class SubscribersListAdapter extends RecyclerView.Adapter<SubscribersListAdapter.SubscribersListViewHolder> {
        private List<UserWithID> users;
        private Context context;

        public SubscribersListAdapter(List<UserWithID> users, Context context) {
            this.users = users;
            this.context = context;
        }

        @Override
        public SubscribersListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribers_list_item, parent, false);
            return new SubscribersListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SubscribersListViewHolder holder, int position) {
            final UserWithID userInList = users.get(position);
            holder.name.setText(userInList.getUser().getName() + " " + userInList.getUser().getSurname());
            mStorageRef.child("images/" + userInList.getFirebaseID()).getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                @Override
                public void onSuccess(StorageMetadata storageMetadata) {
                    String path = storageMetadata.getDownloadUrl().toString();
                    Picasso.with(context).load(path).resize(200, 200).centerCrop().into(holder.image);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/rhymebyrhyme.appspot.com/o/24649396.png?alt=media&token=374987c9-3582-4ee7-8861-5962af10cee0")
                            .resize(200, 200)
                            .centerCrop()
                            .into(holder.image);
                }
            });
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SubscribersActivity.this, UserProfileActivity.class);
                    intent.putExtra("userID", userInList.getFirebaseID());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return users == null ? 0 : users.size();
        }

        class SubscribersListViewHolder extends RecyclerView.ViewHolder {
            CircleImageView image;
            TextView name;
            View view;

            public SubscribersListViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                image = (CircleImageView) itemView.findViewById(R.id.subscribers_list_user_image);
                name = (TextView) itemView.findViewById(R.id.subscribers_list_user_name);
                name.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
            }
        }
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
            Intent intent = new Intent(SubscribersActivity.this, PoemsCategoriesListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_feed) {
            Intent intent = new Intent(SubscribersActivity.this, FeedActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_authors) {
            Intent intent = new Intent(SubscribersActivity.this, UsersListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(SubscribersActivity.this, MainProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_ownpoem) {
            Intent intent = new Intent(SubscribersActivity.this, OwnPoemActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_top_authors) {
            Intent intent = new Intent(SubscribersActivity.this, TopAuthorsActivity.class);
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
