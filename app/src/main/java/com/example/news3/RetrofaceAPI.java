package com.example.news3;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofaceAPI {

    @GET
    Call<NewsModel>getAllnews(@Url String url);

    @GET
     Call<NewsModel>getNewsByCategory(@Url String url);
}
