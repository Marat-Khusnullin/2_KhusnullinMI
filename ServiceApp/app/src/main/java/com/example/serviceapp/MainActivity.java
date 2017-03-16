package com.example.serviceapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.serviceapp.interfaces.ListInterface;
import com.example.serviceapp.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements ListInterface {
    RecyclerView recyclerView;
    UserAdapter adapter;
    ArrayList<User> users;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;

    public static String TASK = "list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context mainCon = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences =  getSharedPreferences("main",MODE_PRIVATE);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        if(savedInstanceState == null) {
            startRequest();
        } else {
            progressBar.setVisibility(ProgressBar.GONE);
            restoreData();
        }
        Receiver receiver = new Receiver();
        IntentFilter intentFilter = new IntentFilter("forMain");
        registerReceiver(receiver, intentFilter);

    }


    private void startRequest() {
        Intent intent = new Intent(this, RequestService.class);
        startService(intent);
    }

    public void setList() {
        users = new ArrayList<User>();
        Gson gson = new Gson();
        String json;
        for (int i = 0; i <10; i++) {
            json = sharedPreferences.getString("list" +i,"");
            User user = gson.fromJson(json, User.class);
            users.add(user);
        }


        adapter = new UserAdapter(users, this);

        recyclerView.setAdapter(adapter);
    }

    private class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            setList();
            progressBar.setVisibility(ProgressBar.GONE);
        }
    }

    private void restoreData() {
        setList();
    }



}
