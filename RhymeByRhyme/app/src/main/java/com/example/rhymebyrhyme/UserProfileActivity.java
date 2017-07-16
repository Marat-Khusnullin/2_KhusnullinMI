package com.example.rhymebyrhyme;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private LinearLayout mainLayout;
    private ProgressBar progressBar;
    private TextView poems;
    private TextView poemsCount;
    private TextView rating;
    private TextView ratingNumber;
    private TextView subscriptions;
    private TextView subscriptionsNumber;
    private TextView subscribers;
    private TextView subscribersNumber;
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
    private Button subscribeButton;
    private CircleImageView imageView;
    private DatabaseReference mRef;
    ActionBarDrawerToggle toggle;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setTitle("Профиль");

        mRef = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mainLayout =(LinearLayout)this.findViewById(R.id.user_profile_main_layout);
        progressBar = (ProgressBar) findViewById(R.id.user_profile_progressBar);
        context = this;


        poems = (TextView) findViewById(R.id.user_profile_poems);
        poemsCount = (TextView) findViewById(R.id.user_profile_poemcount);
        rating = (TextView) findViewById(R.id.user_profile_rating);
        ratingNumber = (TextView) findViewById(R.id.user_profile_ratingNumber);
        userEmail = (TextView) findViewById(R.id.user_profile_useremail);
        name = (TextView) findViewById(R.id.user_profile_name);
        userName = (TextView) findViewById(R.id.user_profile_username);
        surname = (TextView) findViewById(R.id.user_profile_surname);
        userSurname = (TextView) findViewById(R.id.user_profile_usersurname);
        link = (TextView) findViewById(R.id.user_profile_link);
        userLink = (TextView) findViewById(R.id.user_profile_userlink);
        about = (TextView) findViewById(R.id.user_profile_about);
        userAbout = (TextView) findViewById(R.id.user_profile_userabout);
        watchPoems = (TextView) findViewById(R.id.user_profile_watchpoems);
        imageView = (CircleImageView) findViewById(R.id.user_profile_imageview);
        subscribers = (TextView) findViewById(R.id.user_profile_subscribers);
        subscribersNumber = (TextView) findViewById(R.id.user_profile_subscribers_number);
        subscriptions = (TextView) findViewById(R.id.user_profile_subscriptions);
        subscriptionsNumber = (TextView) findViewById(R.id.user_profile_subscriptions_number);
        subscribeButton = (Button) findViewById(R.id.user_profile_subscribe);

        poems.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        poemsCount.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-BoldCondensed.ttf"));
        rating.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        ratingNumber.setTypeface(Typeface.createFromAsset(
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
        subscribers.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Black.ttf"));
        subscribersNumber.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Black.ttf"));
        subscriptions.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Black.ttf"));
        subscriptionsNumber.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Black.ttf"));
        subscribeButton.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Black.ttf"));

        setCorrectSubscribeButton(subscribeButton, getIntent().getStringExtra("userID"), currentUser.getUid());

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subscribeButton.getText().equals("ПОДПИСАТЬСЯ")){
                    subscribeToUser(getIntent().getStringExtra("userID"), currentUser.getUid());
                }
                else {
                    unsubscribeFrom(getIntent().getStringExtra("userID"), currentUser.getUid());
                }
            }
        });

        subscribersNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, SubscribersActivity.class);
                intent.putExtra("Activity_Key", "subscribers");
                intent.putExtra("userID", getIntent().getStringExtra("userID"));
                startActivity(intent);
            }
        });

        subscriptionsNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, SubscribersActivity.class);
                intent.putExtra("Activity_Key", "subscriptions");
                intent.putExtra("userID", getIntent().getStringExtra("userID"));
                startActivity(intent);
            }
        });

        watchPoems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, MainPoemsListActivity.class);
                intent.putExtra("id","" +  getIntent().getStringExtra("userID") );
                startActivity(intent);
            }
        });

        setUserInformation();
        mainLayout.setVisibility(LinearLayout.GONE);
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void setCorrectSubscribeButton(final Button button, String userID, final String subscriberID){
        mRef.child("subs").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(subscriberID)){
                    button.setText("ПОДПИСАТЬСЯ");
                    button.setTextColor(Color.parseColor("#F4F4F4"));
                    button.setBackground(ContextCompat.getDrawable(context, R.drawable.roundedbutton));
                }
                else {
                    button.setText("ОТПИСАТЬСЯ");
                    button.setTextColor(Color.parseColor("#222222"));
                    button.setBackground(ContextCompat.getDrawable(context, R.drawable.roundedgreybutton));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setUserInformation(){
        mRef.child("subs").child(getIntent().getStringExtra("userID")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subscribersNumber.setText(String.valueOf(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRef.child("subs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int subs = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    if (data.hasChild(getIntent().getStringExtra("userID"))){
                        subs++;
                    }
                }
                subscriptionsNumber.setText(String.valueOf(subs));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRef.child("users").child(getIntent().getStringExtra("userID")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                poemsCount.setText(""+ dataSnapshot.child("poemCount").getValue());
                ratingNumber.setText(""+ dataSnapshot.child("rating").getValue());
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

        mStorageRef.child("images/" + getIntent().getStringExtra("userID")).getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                String path = storageMetadata.getDownloadUrl().toString();
                Picasso.with(context).load(path).resize(200,200).centerCrop().into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/rhymebyrhyme.appspot.com/o/24649396.png?alt=media&token=374987c9-3582-4ee7-8861-5962af10cee0").resize(200,200).centerCrop().into(imageView);
            }
        });


        progressBar.setVisibility(ProgressBar.GONE);
        mainLayout.setVisibility(LinearLayout.VISIBLE);
    }

    private void subscribeToUser(final String userID, final String subscriberID){
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("subs").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(subscriberID)){
                    FirebaseDatabase.getInstance().getReference().child("subs").child(userID).child(subscriberID).setValue(subscriberID);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        subscribeButton.setText("ОТПИСАТЬСЯ");
        subscribeButton.setTextColor(Color.parseColor("#222222"));
        subscribeButton.setBackground(ContextCompat.getDrawable(context, R.drawable.roundedgreybutton));
        subscribersNumber.setText(String.valueOf(Integer.parseInt(subscribersNumber.getText().toString()) + 1));
    }

    private void unsubscribeFrom(final String userID, final String subscriberID){
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("subs").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(subscriberID)){
                    FirebaseDatabase.getInstance().getReference().child("subs").child(userID).child(subscriberID).removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        subscribeButton.setText("ПОДПИСАТЬСЯ");
        subscribeButton.setTextColor(Color.parseColor("#F4F4F4"));
        subscribeButton.setBackground(ContextCompat.getDrawable(context, R.drawable.roundedbutton));
        subscribersNumber.setText(String.valueOf(Integer.parseInt(subscribersNumber.getText().toString()) - 1));
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
            Intent intent = new Intent(UserProfileActivity.this, UsersListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(UserProfileActivity.this, MainProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_top_authors){
            Intent intent = new Intent(UserProfileActivity.this, TopAuthorsActivity.class);
            startActivity(intent);
        } else if( id == R.id.nav_ownpoem) {
            Intent intent = new Intent(UserProfileActivity.this, OwnPoemActivity.class);
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
