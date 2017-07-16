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
import java.util.Collections;
import java.util.Comparator;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopAuthorsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle toggle;
    DatabaseReference mRef;
    ArrayList<UserWithID> userList = new ArrayList<>();
    RecyclerView recyclerView;
    FirebaseUser mUser;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_authors);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        progressBar = (ProgressBar) findViewById(R.id.top_authors_progress);
        progressBar.setVisibility(View.VISIBLE);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.top_authors_recycler);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
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
                Collections.sort(userList, new Comparator<UserWithID>() {
                    @Override
                    public int compare(UserWithID userWithID, UserWithID t1) {
                        return t1.getUser().getRating() - userWithID.getUser().getRating();
                    }
                });
                TopAuthorsListAdapter mAdapter = new TopAuthorsListAdapter(userList, TopAuthorsActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                recyclerView.setAdapter(mAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private class TopAuthorsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private ArrayList<UserWithID> users;
        private Context context;

        public TopAuthorsListAdapter(ArrayList<UserWithID> users, Context context) {
            this.users = users;
            this.context = context;
        }

        @Override
        public int getItemViewType(int position) {
            return position < 3 ? 0 : 1;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType){
                case 0:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_authors_top_users_item, parent, false);
                    return new TopAuthorsTopListViewHolder(view);
                case 1:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_authors_common_users_item, parent, false);
                    return new TopAuthorsCommonListViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final UserWithID user = users.get(position);
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            mRef = FirebaseDatabase.getInstance().getReference();
            if (holder instanceof TopAuthorsTopListViewHolder){
                final TopAuthorsTopListViewHolder mHolder = (TopAuthorsTopListViewHolder) holder;
                mHolder.name.setText(user.getUser().getName() + " " + user.getUser().getSurname());
                mHolder.place.setText(String.valueOf(position + 1));
                mHolder.rating.setText(String.valueOf(user.getUser().getRating()));

                mStorageRef.child("images/" + user.getFirebaseID()).getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                    @Override
                    public void onSuccess(StorageMetadata storageMetadata) {
                        String path = storageMetadata.getDownloadUrl().toString();
                        Picasso.with(context).load(path).resize(200,200).centerCrop().into(mHolder.image);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/rhymebyrhyme.appspot.com/o/24649396.png?alt=media&token=374987c9-3582-4ee7-8861-5962af10cee0")
                                .resize(200, 200)
                                .centerCrop()
                                .into(mHolder.image);
                    }
                });

                mHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                        if(!user.getFirebaseID().equals(mUser.getUid())) {
                            intent = new Intent(TopAuthorsActivity.this, UserProfileActivity.class);
                            intent.putExtra("userID", user.getFirebaseID());
                        }
                        else {
                            intent = new Intent(TopAuthorsActivity.this, MainProfileActivity.class);
                        }
                        startActivity(intent);
                    }
                });
            }
            else if (holder instanceof TopAuthorsCommonListViewHolder){
                final TopAuthorsCommonListViewHolder mHolder = (TopAuthorsCommonListViewHolder) holder;
                mHolder.name.setText(user.getUser().getName() + " " + user.getUser().getSurname());
                mHolder.place.setText(String.valueOf(position + 1));
                mHolder.rating.setText(String.valueOf(user.getUser().getRating()));

                mStorageRef.child("images/" + user.getFirebaseID()).getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                    @Override
                    public void onSuccess(StorageMetadata storageMetadata) {
                        String path = storageMetadata.getDownloadUrl().toString();
                        Picasso.with(context).load(path).resize(200,200).centerCrop().into(mHolder.image);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/rhymebyrhyme.appspot.com/o/24649396.png?alt=media&token=374987c9-3582-4ee7-8861-5962af10cee0")
                                .resize(200, 200)
                                .centerCrop()
                                .into(mHolder.image);
                    }
                });

                mHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                        if(!user.getFirebaseID().equals(mUser.getUid())) {
                            intent = new Intent(TopAuthorsActivity.this, UserProfileActivity.class);
                            intent.putExtra("userID", user.getFirebaseID());
                        }
                        else {
                            intent = new Intent(TopAuthorsActivity.this, MainProfileActivity.class);
                        }
                        startActivity(intent);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return users == null ? 0 : users.size();
        }

        class TopAuthorsTopListViewHolder extends RecyclerView.ViewHolder{
            CircleImageView image;
            TextView name;
            TextView rating;
            TextView place;
            View view;
            public TopAuthorsTopListViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                image = (CircleImageView) itemView.findViewById(R.id.top_authors_top_users_image);
                name = (TextView) itemView.findViewById(R.id.top_authors_top_users_textView);
                name.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
                rating = (TextView) itemView.findViewById(R.id.top_authors_top_users_count);
                rating.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
                place = (TextView) itemView.findViewById(R.id.top_authors_top_users_place);
                place.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
            }
        }

        class TopAuthorsCommonListViewHolder extends RecyclerView.ViewHolder{
            CircleImageView image;
            TextView name;
            TextView rating;
            TextView place;
            View view;
            public TopAuthorsCommonListViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                image = (CircleImageView) itemView.findViewById(R.id.top_authors_common_users_image);
                name = (TextView) itemView.findViewById(R.id.top_authors_common_users_textView);
                name.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
                rating = (TextView) itemView.findViewById(R.id.top_authors_common_users_count);
                rating.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
                place = (TextView) itemView.findViewById(R.id.top_authors_common_users_place);
                place.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
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

        } else if (id == R.id.nav_authors) {
            Intent intent = new Intent(TopAuthorsActivity.this, UsersListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(TopAuthorsActivity.this, MainProfileActivity.class);
            startActivity(intent);
        } else if( id == R.id.nav_ownpoem) {
            Intent intent = new Intent(TopAuthorsActivity.this, OwnPoemActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_top_authors){

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
