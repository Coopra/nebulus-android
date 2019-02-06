package com.coopra.nebulus.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.coopra.data.DashboardActivity;
import com.coopra.data.DashboardActivityEnvelope;
import com.coopra.database.entities.Track;
import com.coopra.database.entities.User;
import com.coopra.nebulus.TokenHandler;
import com.coopra.nebulus.TrackRepository;
import com.coopra.nebulus.enums.NetworkStates;
import com.coopra.service.service_implementations.ActivitiesService;

import java.util.ArrayList;
import java.util.List;

public class FeedViewModel extends AndroidViewModel {
    private LiveData<PagedList<Track>> mAllTracks;
    private MutableLiveData<NetworkStates> mNetworkState;
    private TrackRepository mRepository;

    public FeedViewModel(Application application) {
        super(application);
        mRepository = new TrackRepository(application, getNetworkState());
        mAllTracks = mRepository.getAll();
    }

    public LiveData<PagedList<Track>> getAllTracks() {
        return mAllTracks;
    }

    public MutableLiveData<NetworkStates> getNetworkState() {
        if (mNetworkState == null) {
            mNetworkState = new MutableLiveData<>();
        }

        return mNetworkState;
    }

    public void refreshFeed() {
        getNetworkState().postValue(NetworkStates.LOADING);

        ActivitiesService.getFeedTracks(TokenHandler.getToken(getApplication()), new Callback<DashboardActivityEnvelope>() {
            @Override
            public void onResponse(@NonNull Call<DashboardActivityEnvelope> call, @NonNull Response<DashboardActivityEnvelope> response) {
                handleSuccessfulNetworkCall(response);
                getNetworkState().postValue(NetworkStates.NORMAL);
            }

            @Override
            public void onFailure(@NonNull Call<DashboardActivityEnvelope> call, @NonNull Throwable t) {
                getNetworkState().postValue(NetworkStates.NORMAL);
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
