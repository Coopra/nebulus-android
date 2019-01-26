package com.coopra.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    public static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.soundcloud.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
