package com.example.healthcareapp.Api;

import com.example.healthcareapp.Model.covidModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices
{
@GET("all")
Call<covidModel> getValue();
}
