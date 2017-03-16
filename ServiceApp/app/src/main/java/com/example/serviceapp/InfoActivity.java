package com.example.serviceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.serviceapp.interfaces.InfoInterface;
import com.example.serviceapp.model.User;
import com.google.gson.Gson;

public class InfoActivity extends AppCompatActivity implements InfoInterface {
    SharedPreferences sharedPreferences;
    TextView name;
    TextView surname;
    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Intent intent = getIntent();

        name = (TextView) findViewById(R.id.name);
        surname = (TextView) findViewById(R.id.surname);
        email = (TextView) findViewById(R.id.email);
        setInfo(intent.getIntExtra("position", 0));

    }


    private void setInfo(int id) {
        sharedPreferences =  getSharedPreferences("main",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("list" +id,"");
        User user = gson.fromJson(json, User.class);
        System.out.println(user.getResults().get(0).getName().getFirst());
        name.setText(user.getResults().get(0).getName().getFirst());
        surname.setText(user.getResults().get(0).getName().getLast());
        email.setText(user.getResults().get(0).getEmail());

    }
}
