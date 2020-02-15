package com.coopra.database.data_access_objects

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coopra.database.entities.Track

@Dao
interface TrackDao {
    @Query("SELECT * FROM track_table ORDER BY activity_created_at DESC")
    fun getAll(): DataSource.Factory<Int, Track>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tracks: Track)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(track: Track)

    @Query("DELETE FROM track_table")
    fun deleteAll()
}