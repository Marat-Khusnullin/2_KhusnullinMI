package com.example.chucknorris.AsyncWork;

import android.os.AsyncTask;

import com.example.chucknorris.API.ChuckNorrisApi;
import com.example.chucknorris.Values.Joke;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Марат on 23.12.2016.
 */

public class AsyncClass extends AsyncTask<Void, Void, Void>  {
    TaskListener mTaskListener;
    String joke;

    public AsyncClass (TaskListener taskListener) {
        mTaskListener = taskListener;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mTaskListener.onTaskStarted();
    }

    protected Void doInBackground(Void ... strings) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.icndb.com/").addConverterFactory(GsonConverterFactory.create())
                .build();

        ChuckNorrisApi server = retrofit.create(ChuckNorrisApi.class);
        Call<Joke> list = server.getJoke();
        try {
            joke = list.execute().body().getValue().getJoke();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }



    @Override
    protected void onPostExecute(Void integer) {
        super.onPostExecute(integer);

       mTaskListener.onTaskFinish(joke);

    }


    public void newListener(TaskListener taskListener){
        mTaskListener = taskListener;

    }

}
