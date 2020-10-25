package com.coopra.service.interfaces

import com.coopra.data.DashboardActivityEnvelope
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ActivitiesInterface {
    @GET("me/activities?limit=50")
    suspend fun getFeedTracks(@Query("oauth_token") token: String): DashboardActivityEnvelope

    @GET
    suspend fun getNextTracks(@Url url: String, @Query("oauth_token") token: String): DashboardActivityEnvelope
}