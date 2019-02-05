package com.coopra.nebulus;

import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.coopra.data.DashboardActivity;
import com.coopra.data.DashboardActivityEnvelope;
import com.coopra.database.entities.Track;
import com.coopra.database.entities.User;
import com.coopra.service.service_implementations.ActivitiesService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedTracksBoundaryCallback extends PagedList.BoundaryCallback<Track> {
    private TrackRepository mRepository;
    private String mToken;
    private boolean mIsLoading;

    public FeedTracksBoundaryCallback(TrackRepository repository, String token) {
        mRepository = repository;
        mToken = token;
    }

    /**
     * Requests initial data from the network, replacing all content currently in the database.
     */
    @Override
    public void onZeroItemsLoaded() {
        if (mIsLoading) {
            return;
        }

        mIsLoading = true;

        ActivitiesService.getFeedTracks(mToken, new Callback<DashboardActivityEnvelope>() {
            @Override
            public void onResponse(@NonNull Call<DashboardActivityEnvelope> call, @NonNull Response<DashboardActivityEnvelope> response) {
                handleSuccessfulNetworkCall(response);
                mIsLoading = false;
            }

            @Override
            public void onFailure(@NonNull Call<DashboardActivityEnvelope> call, @NonNull Throwable t) {
                mIsLoading = false;
            }
        });
    }

    /**
     * Requests additional data from the network, appending the results to the end of the database's
     * existing data.
     */
    @Override
    public void onItemAtEndLoaded(@NonNull Track itemAtEnd) {
        if (mIsLoading) {
            return;
        }

        mIsLoading = true;

        ActivitiesService.getNextTracks(mToken, itemAtEnd.nextToken, new Callback<DashboardActivityEnvelope>() {
            @Override
            public void onResponse(@NonNull Call<DashboardActivityEnvelope> call, @NonNull Response<DashboardActivityEnvelope> response) {
                handleSuccessfulNetworkCall(response);
                mIsLoading = false;
            }

            @Override
            public void onFailure(@NonNull Call<DashboardActivityEnvelope> call, @NonNull Throwable t) {
                mIsLoading = false;
            }
        });
    }

    private void handleSuccessfulNetworkCall(@NonNull Response<DashboardActivityEnvelope> response) {
        if (response.body() != null) {
            List<Track> tracks = new ArrayList<>();
            List<User> users = new ArrayList<>();

            for (DashboardActivity activity : response.body().collection) {
                if (activity.origin != null) {
                    tracks.add(new Track(activity.origin, response.body().next_href, activity.created_at));
                    users.add(new User(activity.origin.user));
                }
            }

            if (tracks.size() > 0) {
                mRepository.insertAll(new TrackRepository.TrackParameters(tracks, users));
            }
        }
    }
}
