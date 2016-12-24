package com.example.chucknorris.API;

import com.example.chucknorris.Values.Joke;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Марат on 21.12.2016.
 */

public interface ChuckNorrisApi {
    @GET("jokes/random")
    Call<Joke> getJoke();
}
