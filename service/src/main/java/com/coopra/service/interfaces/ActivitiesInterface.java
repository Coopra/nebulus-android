package com.coopra.service.interfaces;

import com.coopra.data.DashboardActivityEnvelope;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ActivitiesInterface {
    @GET("me/activities/tracks.json?limit=50")
    Call<DashboardActivityEnvelope> getFeedTracks(@Query("oauth_token") String token);
}
