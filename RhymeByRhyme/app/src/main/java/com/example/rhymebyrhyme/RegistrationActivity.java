package com.example.rhymebyrhyme;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rhymebyrhyme.database.Dao;
import com.example.rhymebyrhyme.model.Poem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class RegistrationActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    EditText passwordAgain;
    ImageView reg;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Context context;
    private DatabaseReference mRef;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.passwordReg);
        passwordAgain = (EditText) findViewById(R.id.passwordRegAgain);
        reg = (ImageView) findViewById(R.id.registration);
        context = this;
        mRef = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(RegistrationActivity.this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                } else {
                    // User is signed out

                }
                // ...
            }
        };

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Загрузка...");
                progressDialog.show();
                if(password.getText().toString().equals(passwordAgain.getText().toString())) {
                    registration(email.getText().toString(), password.getText().toString());
                } else {
                    Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void registration(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    saveUser();
                    Intent intent = new Intent(context, UserInfoActivity.class);
                    intent.putExtra("Reference", mAuth.getCurrentUser().getUid());
                    progressDialog.dismiss();
                    startActivity(intent);
                } else {
                    Toast.makeText(context,"" + task.getException(), Toast.LENGTH_SHORT).show();
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

    private void saveUser() {
        Dao dao = new Dao();
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString());
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        dao.writeUser(firebaseUser.getUid(), "Не указано", "Не указано","Не указано", 0 ,"Не указано","Не указано");
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("poems").child(firebaseUser.getUid()).child("0").setValue(new Poem(0,"","",0,""+ new Date().getDate()));
    }

}
