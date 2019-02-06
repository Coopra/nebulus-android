package com.coopra.nebulus.view_models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

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
