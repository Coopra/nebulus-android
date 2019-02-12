package com.coopra.nebulus.view_models;

import android.app.Application;
import android.os.AsyncTask;

import com.coopra.data.Track;
import com.coopra.nebulus.TrackRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AlternativeViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Track>> mTracks;

    public AlternativeViewModel(@NonNull Application application) {
        super(application);
        mTracks = new MutableLiveData<>();
    }

    public LiveData<List<Track>> getTracks() {
        return mTracks;
    }

    public void retrieveTracks() {
        new GetTracksTask(mTracks).execute();
    }

    private static class GetTracksTask extends AsyncTask<Void, Void, List<Track>> {
        private final MutableLiveData<List<Track>> mTracksLiveData;

        GetTracksTask(MutableLiveData<List<Track>> tracksLiveData) {
            mTracksLiveData = tracksLiveData;
        }

        @Override
        protected List<Track> doInBackground(Void... voids) {
            return TrackRepository.alternativeGetTracks();
        }

        @Override
        protected void onPostExecute(List<Track> tracks) {
            mTracksLiveData.setValue(tracks);
        }
    }
}