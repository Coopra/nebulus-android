package com.coopra.nebulus;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.coopra.database.AppDatabase;
import com.coopra.database.data_access_objects.TrackDao;
import com.coopra.database.data_access_objects.UserDao;
import com.coopra.database.entities.Track;
import com.coopra.database.entities.User;

import java.util.List;

public class TrackRepository {
    private TrackDao mTrackDao;
    private UserDao mUserDao;
    private LiveData<List<Track>> mAllTracks;

    public TrackRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mTrackDao = db.trackDao();
        mUserDao = db.userDao();
        mAllTracks = mTrackDao.getAll();
    }

    public LiveData<List<Track>> getAll() {
        return mAllTracks;
    }

    public void insert(TrackParameters parameters) {
        new insertAsyncTask(mTrackDao, mUserDao).execute(parameters);
    }

    private static class insertAsyncTask extends AsyncTask<TrackParameters, Void, Void> {
        private TrackDao mAsyncTrackDao;
        private UserDao mAsyncUserDao;

        insertAsyncTask(TrackDao trackDao, UserDao userDao) {
            mAsyncTrackDao = trackDao;
            mAsyncUserDao = userDao;
        }

        @Override
        protected Void doInBackground(final TrackParameters... params) {
            mAsyncUserDao.insert(params[0].user);
            mAsyncTrackDao.insert(params[0].track);
            return null;
        }
    }

    public static class TrackParameters {
        Track track;
        User user;

        public TrackParameters(Track track, User user) {
            this.track = track;
            this.user = user;
        }
    }
}
