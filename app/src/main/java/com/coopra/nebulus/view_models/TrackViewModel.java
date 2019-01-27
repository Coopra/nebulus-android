package com.coopra.nebulus.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.coopra.database.entities.Track;
import com.coopra.nebulus.TrackRepository;
import com.coopra.service.service_implementations.TracksService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackViewModel extends AndroidViewModel {
    private LiveData<List<Track>> mAllTracks;
    private TrackRepository mRepository;

    public TrackViewModel(Application application) {
        super(application);
        mRepository = new TrackRepository(application);
        mAllTracks = mRepository.getAll();
    }

    public LiveData<List<Track>> getAllTracks() {
        return mAllTracks;
    }

    public void populateDatabase() {
        TracksService.getFeedTracks(new Callback<List<com.coopra.data.Track>>() {
            @Override
            public void onResponse(@NonNull Call<List<com.coopra.data.Track>> call, @NonNull Response<List<com.coopra.data.Track>> response) {
                if (response.body() != null) {
                    for (com.coopra.data.Track track : response.body()) {
                        Track databaseTrack = new Track();
                        databaseTrack.id = track.id;
                        databaseTrack.userFavorite = track.user_favorite;
                        databaseTrack.favoritingsCount = track.favoritings_count;
                        databaseTrack.playbackCount = track.playback_count;
                        databaseTrack.streamUrl = track.stream_url;
                        databaseTrack.genre = track.genre;
                        databaseTrack.duration = track.duration;
                        databaseTrack.artworkUrl = track.artwork_url;
                        databaseTrack.title = track.title;
                        databaseTrack.userId = track.user_id;
                        databaseTrack.createdAt = track.created_at;

                        mRepository.insert(databaseTrack);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<com.coopra.data.Track>> call, @NonNull Throwable t) {

            }
        });
    }
}
