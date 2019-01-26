package com.coopra.service.interfaces;

import com.coopra.data.Track;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TracksInterface {
    @GET("tracks")
    Call<List<Track>> getFeedTracks(@Query("client_id") String clientId);
}
