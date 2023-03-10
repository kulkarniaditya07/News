package com.example.news3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// api key= 40e1ce31327744f19c6c243def956918

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickinterface{

  private RecyclerView newsRV,categoryRV;
  private ProgressBar loadinPB;
  private ArrayList<Articles>articlesArrayList;
  private ArrayList<CategoryRVModel>categoryRVModelArrayList;
  CategoryRVAdapter categoryRVAdapter;
  NewsRVAdapter newsRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRV=findViewById(R.id.idRVnews);
        categoryRV=findViewById(R.id.idRVCategories);
        loadinPB=findViewById(R.id.idPBloading);
        articlesArrayList=new ArrayList<>();
        categoryRVModelArrayList=new ArrayList<>();
        newsRVAdapter=new NewsRVAdapter(articlesArrayList,this);
        categoryRVAdapter=new CategoryRVAdapter(categoryRVModelArrayList,this,this::onCategoryclick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getnews("All");
        newsRVAdapter.notifyDataSetChanged();
    }

    private void getnews(String category) {
        loadinPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryUrl="https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apiKey=40e1ce31327744f19c6c243def956918";
        String url="https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apikey=40e1ce31327744f19c6c243def956918";
        String BASE_URL="https://newsapi.org/";
        Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofaceAPI retrofaceAPI= retrofit.create(RetrofaceAPI.class);
        Call<NewsModel> call;
        if(category.equals("All")){
          call=retrofaceAPI.getAllnews(url);
        }else {
            call=retrofaceAPI.getNewsByCategory(categoryUrl);
        }
    call.enqueue(new Callback<NewsModel>() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
            NewsModel newsModel=response.body();
            loadinPB.setVisibility(View.GONE);
            assert newsModel != null;
            ArrayList<Articles>articles=newsModel.getArticles();
            for(int i=0;i<articles.size();i++){
                articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent()));
            }newsRVAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<NewsModel> call, Throwable t) {
            Toast.makeText(MainActivity.this, "Failed To Get News", Toast.LENGTH_SHORT).show();
        }
    });
    }

    private void getCategories(){
        categoryRVModelArrayList.add(new CategoryRVModel("All","https://images.unsplash.com/photo-1546422904-90eab23c3d7e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8bmV3c3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60\n"));
        categoryRVModelArrayList.add(new CategoryRVModel("Technology","https://images.unsplash.com/photo-1605810230434-7631ac76ec81?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mjl8fHRlY2hub2xvZ3l8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60\n"));
        categoryRVModelArrayList.add(new CategoryRVModel("Science","https://images.unsplash.com/photo-1614935151651-0bea6508db6b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fHNjaWVuY2V8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60\n"));
        categoryRVModelArrayList.add(new CategoryRVModel("Sports","https://images.unsplash.com/photo-1607962837359-5e7e89f86776?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mjl8fHNwb3J0c3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60\n"));
        categoryRVModelArrayList.add(new CategoryRVModel("General","https://images.unsplash.com/photo-1567965178163-9ef765357c15?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NzV8fEdlbmVyYWx8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60\n"));
        categoryRVModelArrayList.add(new CategoryRVModel("Business","https://images.unsplash.com/photo-1661956600684-97d3a4320e45?ixlib=rb-4.0.3&ixid=MnwxMjA3fDF8MHxzZWFyY2h8MTV8fGJ1c2luZXNzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60\n"));
        categoryRVModelArrayList.add(new CategoryRVModel("Entertainment","https://images.unsplash.com/photo-1603190287605-e6ade32fa852?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8ZW50ZXJ0YWlubWVudHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60\n"));
        categoryRVModelArrayList.add(new CategoryRVModel("Health","https://images.unsplash.com/photo-1532938911079-1b06ac7ceec7?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fGhlYWx0aHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60\n"));
       categoryRVAdapter.notifyDataSetChanged();
    }



    @Override
    public void onCategoryclick(int position) {
       String category=categoryRVModelArrayList.get(position).getCategory();
       getnews(category);
    }


}