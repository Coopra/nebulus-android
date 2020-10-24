package com.coopra.service.interfaces

import com.coopra.data.Origin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TracksInterface {
    @GET("tracks")
    fun getRandomTracks(@Query("client_id") clientId: String): Call<List<Origin>>
}