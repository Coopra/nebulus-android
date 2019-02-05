package com.coopra.nebulus;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
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
    private LiveData<PagedList<Track>> mAllTracks;

    public TrackRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mTrackDao = db.trackDao();
        mUserDao = db.userDao();

        PagedList.Config myPagingConfig = new PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(false)
                .build();

        mAllTracks = new LivePagedListBuilder<>(mTrackDao.getAll(), myPagingConfig)
                .setBoundaryCallback(new FeedTracksBoundaryCallback(this, TokenHandler.getToken(application)))
                .build();
    }

    public LiveData<PagedList<Track>> getAll() {
        return mAllTracks;
    }

    public void insertAll(TrackParameters parameters) {
        new insertAllAsyncTask(mTrackDao, mUserDao).execute(parameters);
    }

    private static class insertAllAsyncTask extends AsyncTask<TrackParameters, Void, Void> {
        private TrackDao mAsyncTrackDao;
        private UserDao mAsyncUserDao;

        insertAllAsyncTask(TrackDao trackDao, UserDao userDao) {
            mAsyncTrackDao = trackDao;
            mAsyncUserDao = userDao;
        }

        @Override
        protected Void doInBackground(final TrackParameters... params) {
            mAsyncUserDao.insertAll(params[0].users.toArray(new User[0]));
            mAsyncTrackDao.insertAll(params[0].tracks.toArray(new Track[0]));
            return null;
        }
    }

    public static class TrackParameters {
        List<Track> tracks;
        List<User> users;

        public TrackParameters(List<Track> track, List<User> user) {
            this.tracks = track;
            this.users = user;
        }
    }
}
