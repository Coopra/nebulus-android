package com.coopra.database.data_access_objects;

import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.coopra.database.entities.Track;

@Dao
public interface TrackDao {
    @Query("SELECT * FROM track_table ORDER BY activity_created_at DESC")
    DataSource.Factory<Integer, Track> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Track... tracks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Track track);

    @Query("DELETE FROM track_table")
    void deleteAll();
}
