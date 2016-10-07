package com.example.gallery;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int [] cats;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setCats();
        rv = (RecyclerView) findViewById(R.id.recycler_view);
        rv.setLayoutManager(new GridLayoutManager(rv.getContext(),3 ));
        AdapterHolderAndStuff.Adapter mAdapter = new AdapterHolderAndStuff.Adapter(cats,getApplicationContext(),rv.getContext());
        rv.setAdapter(mAdapter);
    }


    private void setCats() {
        cats = new int[10];
        cats[0] = R.drawable.one;
        cats[1] = R.drawable.two;
        cats[2] = R.drawable.three;
        cats[3] = R.drawable.four;
        cats[4] = R.drawable.five;
        cats[5] = R.drawable.six;
        cats[6] = R.drawable.seven;
        cats[7] = R.drawable.eight;
        cats[8] = R.drawable.nine;
        cats[9] = R.drawable.ten;




        /*cats.add(R.drawable.one);
        cats.add(R.drawable.two);
        cats.add(R.drawable.three);
        cats.add(R.drawable.four);
        cats.add(R.drawable.five);
        cats.add(R.drawable.six);
        cats.add(R.drawable.seven);
        cats.add(R.drawable.eight);
        cats.add(R.drawable.nine);
        cats.add(R.drawable.ten);*/

        /*cats.add("one");
        cats.add("two");
        cats.add("three");
        cats.add("four");
        cats.add("five");
        cats.add("six");
        cats.add("seven");
        cats.add("eight");
        cats.add("nine");
        cats.add("ten");*/
    }
}
