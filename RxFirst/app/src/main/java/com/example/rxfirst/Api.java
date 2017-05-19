package com.example.rxfirst;


import com.example.rxfirst.Values.Joke;

import retrofit2.http.GET;
import rx.Observable;

public interface Api {
    @GET("jokes/random")
    Observable<Joke> getJoke();
}
