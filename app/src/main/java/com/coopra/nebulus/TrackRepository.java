package com.coopra.nebulus;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.coopra.database.AppDatabase;
import com.coopra.database.data_access_objects.TrackDao;
import com.coopra.database.entities.Track;

import java.util.List;

public class TrackRepository {
    private TrackDao mTrackDao;
    private LiveData<List<Track>> mAllTracks;

    public TrackRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mTrackDao = db.trackDao();
        mAllTracks = mTrackDao.getAll();
    }

    public LiveData<List<Track>> getAll() {
        return mAllTracks;
    }

    public void insert(Track track) {
        new insertAsyncTask(mTrackDao).execute(track);
    }

    private static class insertAsyncTask extends AsyncTask<Track, Void, Void> {
        private TrackDao mAsyncTaskDao;

        insertAsyncTask(TrackDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Track... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
