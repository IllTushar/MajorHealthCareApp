package com.example.healthcareapp.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient
{
   private Retrofit retrofit;
   private String BASE_URL = "https://disease.sh/v3/covid-19/";
   public Retrofit getRetrofit(){
      if (retrofit==null){
         retrofit = new Retrofit.Builder()
                 .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
      }
      return retrofit;
   }
}
