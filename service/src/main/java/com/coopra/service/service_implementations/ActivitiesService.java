package com.coopra.service.service_implementations;

import com.coopra.data.DashboardActivityEnvelope;
import com.coopra.service.RetrofitHelper;
import com.coopra.service.interfaces.ActivitiesInterface;

import retrofit2.Call;
import retrofit2.Callback;

public class ActivitiesService {
    public static void getFeedTracks(String token, Callback<DashboardActivityEnvelope> callback) {
        ActivitiesInterface service = RetrofitHelper.createRetrofit().create(ActivitiesInterface.class);

        Call<DashboardActivityEnvelope> call = service.getFeedTracks(token);
        call.enqueue(callback);
    }

    public static void getNextTracks(String token, String url, Callback<DashboardActivityEnvelope> callback) {
        ActivitiesInterface service = RetrofitHelper.createRetrofit().create(ActivitiesInterface.class);

        Call<DashboardActivityEnvelope> call = service.getNextTracks(url, token);
        call.enqueue(callback);
    }
}
