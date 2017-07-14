package com.example.rhymebyrhyme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ImageView sign;
    ImageView registration;
    EditText login;
    EditText password;
    Context context;
    SharedPreferences sPref;
    final static String CURRENT_EMAIL = "current_email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        setTitle("Вход");
        mAuth = FirebaseAuth.getInstance();
        sign = (ImageView) findViewById(R.id.sign);
        registration = (ImageView) findViewById(R.id.registration);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);

        login.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        password.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    /*Intent intent = new Intent(context, MainProfile.class);
                    startActivity(intent);*/
                } else {
                    // User is signed out
                }
                // ...
            }
        };

            sign.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                        signing("" + login.getText(), "" + password.getText());


                }
            });

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //registration(login.getText().toString(), password.getText().toString());
                Intent intent = new Intent(context, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }


    public void signing(final String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(context, MainProfileActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }




}
