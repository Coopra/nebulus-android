package com.coopra.service.service_implementations;

import android.support.annotation.NonNull;

import com.coopra.data.Track;
import com.coopra.service.Constants;
import com.coopra.service.RetrofitHelper;
import com.coopra.service.interfaces.TracksInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TracksService {
    public static void getFeedTracks() {
        TracksInterface service = RetrofitHelper.createRetrofit().create(TracksInterface.class);

        Call<List<Track>> call = service.getFeedTracks(Constants.CLIENT_ID);
        call.enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(@NonNull Call<List<Track>> call, @NonNull Response<List<Track>> response) {

            }

            @Override
            public void onFailure(@NonNull Call<List<Track>> call, @NonNull Throwable t) {

            }
        });
    }
}
