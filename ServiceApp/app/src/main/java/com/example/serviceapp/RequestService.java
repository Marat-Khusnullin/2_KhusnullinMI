package com.example.serviceapp;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.serviceapp.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RequestService extends IntentService {
    ArrayList<User> users;
    RequestInterface server;
    public RequestService() {
        super("first");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SharedPreferences sharedPreferences =  getSharedPreferences("main",MODE_PRIVATE);
        users = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me/").addConverterFactory(GsonConverterFactory.create())
                .build();

        server = retrofit.create(RequestInterface.class);
        users = new ArrayList<>();
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < 10; i++) {
            Call<User> user = server.getUser();
            try {
                String json = gson.toJson(user.execute().body());
                editor.putString("list"+i, json);
                editor.apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            Intent broadcastIntent = new Intent("forMain");
            broadcastIntent.putExtra(MainActivity.TASK, 1);
        //broadcastIntent.putExtra("list", users);
        sendBroadcast(broadcastIntent);



    }
}
