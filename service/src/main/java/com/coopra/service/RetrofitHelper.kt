package com.coopra.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {
    fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.soundcloud.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}