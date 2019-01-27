package com.coopra.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "track_table")
public class Track {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "created_at")
    public String createdAt;

    @ColumnInfo(name = "user_id")
    public String userId;

    //public User user;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "artwork_url")
    public String artworkUrl;

    @ColumnInfo(name = "default")
    public long duration;

    @ColumnInfo(name = "genre")
    public String genre;

    @ColumnInfo(name = "stream_url")
    public String streamUrl;

    @ColumnInfo(name = "playback_count")
    public int playbackCount;

    @ColumnInfo(name = "favoritings_count")
    public int favoritingsCount;

    @ColumnInfo(name = "user_favorite")
    public boolean userFavorite;
}
