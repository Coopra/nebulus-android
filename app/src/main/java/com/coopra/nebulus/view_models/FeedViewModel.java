package com.coopra.nebulus.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.coopra.database.entities.Track;
import com.coopra.nebulus.TrackRepository;

public class FeedViewModel extends AndroidViewModel {
    private LiveData<PagedList<Track>> mAllTracks;

    public FeedViewModel(Application application) {
        super(application);
        TrackRepository repository = new TrackRepository(application);
        mAllTracks = repository.getAll();
    }

    public LiveData<PagedList<Track>> getAllTracks() {
        return mAllTracks;
    }
}
