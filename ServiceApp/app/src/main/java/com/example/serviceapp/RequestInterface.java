package com.example.serviceapp;


import com.example.serviceapp.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestInterface {
    @GET("api/")
    Call<User> getUser();




}
