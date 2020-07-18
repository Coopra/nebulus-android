package com.coopra.service.interfaces

import com.coopra.data.DashboardActivityEnvelope
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ActivitiesInterface {
    @GET("me/activities?limit=50")
    fun getFeedTracks(@Query("oauth_token") token: String): Call<DashboardActivityEnvelope>

    @GET
    fun getNextTracks(@Url url: String, @Query("oauth_token") token: String): Call<DashboardActivityEnvelope>
}