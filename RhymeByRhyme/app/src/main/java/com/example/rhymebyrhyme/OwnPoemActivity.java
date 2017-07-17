package com.example.rhymebyrhyme;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhymebyrhyme.model.Poem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class OwnPoemActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    EditText title;
    EditText text;
    Spinner category;
    TextView categoryText;
    Button publishing;
    String [] categories = {"Жизнь", "Война", "Любовь", "Религия",
            "Политика", "Семья", "Дружба", "Мистика", "История"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_poem);
        title = (EditText) findViewById(R.id.newTitle);
        text = (EditText) findViewById(R.id.newText);
        category = (Spinner) findViewById(R.id.spinner);
        categoryText= (TextView) findViewById(R.id.categoryText);
        publishing = (Button) findViewById(R.id.publish);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        title.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-BoldCondensed.ttf"));
        text.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));
        publishing.setTypeface(Typeface.createFromAsset(
                getAssets(), "fonts/Roboto-Light.ttf"));

        publishing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                mRef = FirebaseDatabase.getInstance().getReference();
                final FirebaseUser firebaseUser = mAuth.getCurrentUser();

                DatabaseReference mRef2 = FirebaseDatabase.getInstance().getReference();
                mRef2.child("users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int id =  Integer.parseInt("" + dataSnapshot.child("poemCount").getValue());
                        String newTitle = title.getText().toString();
                        if(newTitle.equals("")) newTitle = "Без названия";
                        mRef.child("poems").child(firebaseUser.getUid()).child(""+ id).setValue(new Poem(firebaseUser.getUid(),
                                 Integer.parseInt(""+ dataSnapshot.child("poemCount").getValue())
                                ,newTitle,
                                Html.toHtml(text.getText()),0,""+DateFormat.format("dd.MM.yyyy",new Date()), categories[category.getSelectedItemPosition()]));
                        mRef = FirebaseDatabase.getInstance().getReference();
                       mRef.child("users").child(firebaseUser.getUid()).child("poemCount").setValue(++id);

                        Toast.makeText(OwnPoemActivity.this, "ПУБЛИКАЦИЯ ПРОШЛА", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OwnPoemActivity.this, MainProfileActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }
}
