package com.example.rxfirst;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;

import com.example.rxfirst.Values.Joke;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class RxFragment extends Fragment {
    TaskListener mTaskListener;
    Observable<Joke> joke;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTaskListener = (TaskListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mTaskListener = (TaskListener) activity;
    }

    public void request() {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://api.icndb.com/")
                .build();
        Api api = retrofit.create(Api.class);
        joke = api.getJoke();
        joke.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Joke>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Joke joke) {
                        mTaskListener.onTaskFinish(joke.getValue().getJoke());
                    }
                });
    }
}
