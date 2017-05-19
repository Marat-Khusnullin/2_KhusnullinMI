package com.example.rxfirst;


import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rxfirst.Values.Joke;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements TaskListener{
    TextView textView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.tv);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        RxFragment rxFragment =(RxFragment) getRxFragment();
        rxFragment.onAttach(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");
                progressBar.setVisibility(View.VISIBLE);
                rxFragment.request();
            }
        });

        if(savedInstanceState!=null) {
            textView.setText(savedInstanceState.getString("jokeText"));
            if (savedInstanceState.getInt("visibility") == View.VISIBLE) {
                progressBar.setVisibility(View.VISIBLE); } else {
                progressBar.setVisibility(View.GONE);
            }
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private Fragment getRxFragment() {
        RxFragment fragment = (RxFragment)getFragmentManager().findFragmentByTag(RxFragment.class.getName());
        if(fragment == null) {
            fragment = new RxFragment();
        }
        return fragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("visibility", progressBar.getVisibility());
        outState.putString("jokeText", textView.getText().toString());
    }

    @Override
    public void onTaskFinish(String joke) {
        progressBar.setVisibility(View.GONE);
        textView.setText(joke);
    }

    @Override
    public void onTaskStarted() {

    }
}
