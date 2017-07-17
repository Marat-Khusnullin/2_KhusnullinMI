package com.example.rhymebyrhyme;

import android.content.Context;
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

public class UsersListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle toggle;
    DatabaseReference mRef;

    RecyclerView recyclerView;
    ArrayList<UserWithID> userList = new ArrayList<>();
    ProgressBar progressBar;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        progressBar = (ProgressBar) findViewById(R.id.users_list_progress);
        progressBar.setVisibility(View.VISIBLE);

        mRef = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = (RecyclerView) findViewById(R.id.users_list_recycler);

        mRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eachUser : dataSnapshot.getChildren()) {
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
                        return userWithID.getUser().getName().compareTo(t1.getUser().getName());
                    }
                });

                UserWithID specialThing = new UserWithID("key", new User());
                specialThing.getUser().setName(String.valueOf(Character.toUpperCase(userList.get(0).getUser().getName().charAt(0))));
                specialThing.getUser().setSurname("");
                specialThing.getUser().setDescription("SpecialThing");
                userList.add(0, specialThing);
                int i = 1;
                while (i < userList.size() - 1) {
                    UserWithID userInList = userList.get(i);
                    if (i != 0) {
                        UserWithID nextUserInList = userList.get(i + 1);
                        char firstLetterOfFirstUserName = userInList.getUser().getName().charAt(0);
                        char firstLetterOfSecondUserName = nextUserInList.getUser().getName().charAt(0);
                        if (firstLetterOfFirstUserName != firstLetterOfSecondUserName) {
                            specialThing = new UserWithID("key", new User());
                            specialThing.getUser().setName(String.valueOf(Character.toUpperCase(firstLetterOfSecondUserName)));
                            specialThing.getUser().setSurname("");
                            specialThing.getUser().setDescription("SpecialThing");
                            userList.add(i + 1, specialThing);
                            i++;
                        }
                    }
                    i++;
                }
                UsersListAdapter mAdapter = new UsersListAdapter(userList, UsersListActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                recyclerView.setAdapter(mAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private class UsersListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<UserWithID> users;
        private Context context;

        public UsersListAdapter(ArrayList<UserWithID> users, Context context) {
            this.users = users;
            this.context = context;
        }

        @Override
        public int getItemViewType(int position) {
            UserWithID user = userList.get(position);
            if (user.getUser().getDescription().equals("SpecialThing")){
                return 0;
            }
            else {
                return 1;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType){
                case 0:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_list_special_item, parent, false);
                    return new UsersListSpecialItemViewHolder(view);
                case 1:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_list_item, parent, false);
                    return new UsersListViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final UserWithID user = users.get(position);
            mRef = FirebaseDatabase.getInstance().getReference();

            if (holder instanceof UsersListViewHolder){
                UsersListViewHolder ulHolder = (UsersListViewHolder) holder;
                ulHolder.name.setText(user.getUser().getName() + " " + user.getUser().getSurname());
                ulHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent;
                        if(!user.getFirebaseID().equals(mUser.getUid())) {
                            intent = new Intent(UsersListActivity.this, UserProfileActivity.class);
                            intent.putExtra("userID", user.getFirebaseID());
                        }
                        else {
                            intent = new Intent(UsersListActivity.this, MainProfileActivity.class);
                        }
                        startActivity(intent);
                    }
                });
            }
            else if (holder instanceof UsersListSpecialItemViewHolder){
                UsersListSpecialItemViewHolder ulHolder = (UsersListSpecialItemViewHolder) holder;
                ulHolder.name.setText(user.getUser().getName() + " " + user.getUser().getSurname());
            }
        }

        @Override
        public int getItemCount() {
            return users == null ? 0 : users.size();
        }

        class UsersListViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            View view;

            public UsersListViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                name = (TextView) itemView.findViewById(R.id.users_list_item_name);
                name.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf"));
            }
        }

        class UsersListSpecialItemViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            View view;

            public UsersListSpecialItemViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                name = (TextView) itemView.findViewById(R.id.users_list_special_item_letter);
                name.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-BoldCondensed.ttf"));
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
            Intent intent = new Intent(UsersListActivity.this, PoemsCategoriesListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_authors) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(UsersListActivity.this, MainProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_top_authors){
            Intent intent = new Intent(UsersListActivity.this, TopAuthorsActivity.class);
            startActivity(intent);
        } else if( id == R.id.nav_ownpoem) {
            Intent intent = new Intent(UsersListActivity.this, OwnPoemActivity.class);
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
