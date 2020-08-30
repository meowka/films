package com.example.films.API;

import com.example.films.POJO.Films;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("sequeniatesttask/films.json")
    Call<Films> doGetListResources();

}
