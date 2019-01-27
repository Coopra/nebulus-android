package com.coopra.service.service_implementations;

import com.coopra.data.Track;
import com.coopra.service.Constants;
import com.coopra.service.RetrofitHelper;
import com.coopra.service.interfaces.TracksInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class TracksService {
    public static void getFeedTracks(Callback<List<Track>> callback) {
        TracksInterface service = RetrofitHelper.createRetrofit().create(TracksInterface.class);

        Call<List<Track>> call = service.getFeedTracks(Constants.CLIENT_ID);
        call.enqueue(callback);
    }
}
