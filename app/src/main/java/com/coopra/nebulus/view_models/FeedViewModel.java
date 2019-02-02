package com.coopra.nebulus.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.coopra.data.DashboardActivity;
import com.coopra.data.DashboardActivityEnvelope;
import com.coopra.database.entities.Track;
import com.coopra.database.entities.User;
import com.coopra.nebulus.TokenHandler;
import com.coopra.nebulus.TrackRepository;
import com.coopra.service.service_implementations.ActivitiesService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedViewModel extends AndroidViewModel {
    private LiveData<List<Track>> mAllTracks;
    private TrackRepository mRepository;

    public FeedViewModel(Application application) {
        super(application);
        mRepository = new TrackRepository(application);
        mAllTracks = mRepository.getAll();
    }

    public LiveData<List<Track>> getAllTracks() {
        return mAllTracks;
    }

    public void populateDatabase(Context context) {
        ActivitiesService.getFeedTracks(TokenHandler.getToken(context), new Callback<DashboardActivityEnvelope>() {
            @Override
            public void onResponse(@NonNull Call<DashboardActivityEnvelope> call, @NonNull Response<DashboardActivityEnvelope> response) {
                if (response.body() != null) {
                    for (DashboardActivity activity : response.body().collection) {
                        Track databaseTrack = new Track(activity.origin);
                        User databaseUser = new User(activity.origin.user);
                        mRepository.insert(new TrackRepository.TrackParameters(databaseTrack, databaseUser));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DashboardActivityEnvelope> call, @NonNull Throwable t) {

            }
        });
    }
}
