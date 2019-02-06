package com.coopra.database.data_access_objects;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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
