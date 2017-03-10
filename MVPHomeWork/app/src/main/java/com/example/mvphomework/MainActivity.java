package com.example.mvphomework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mvphomework.presenter.MainPresenter;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements ListInterface {
RecyclerView rv;
    MainPresenter mainPresenter;
    UserAdapter userAdapter;
    LinkedList<User> trtr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        mainPresenter = new MainPresenter(this, rv, this);


        mainPresenter.setList();


    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.setList();
    }




}
