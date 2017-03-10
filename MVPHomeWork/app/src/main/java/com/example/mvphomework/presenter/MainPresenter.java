package com.example.mvphomework.presenter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mvphomework.ListInterface;
import com.example.mvphomework.ModelUser;
import com.example.mvphomework.UserAdapter;

public class MainPresenter {
    ListInterface listInterface;
    RecyclerView rv;
    ModelUser modelUser;
    UserAdapter userAdapter;
    Context context;

    public MainPresenter(ListInterface listInterface, RecyclerView rv, Context context) {
        this.listInterface = listInterface;
        this.rv = rv;
        modelUser = ModelUser.getInstance();
        this.context = context;

    }

    public void setList() {

        userAdapter = new UserAdapter(modelUser.getUsers(), this.context);
        rv.setAdapter(userAdapter);
    }





}
