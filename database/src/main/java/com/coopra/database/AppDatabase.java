package com.coopra.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import android.content.Context;

import com.coopra.database.data_access_objects.TrackDao;
import com.coopra.database.data_access_objects.UserDao;
import com.coopra.database.entities.Track;
import com.coopra.database.entities.User;

@Database(entities = {Track.class, User.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "nebulus_database")
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract TrackDao trackDao();

    public abstract UserDao userDao();
}
