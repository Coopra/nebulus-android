package com.coopra.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "track_table", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"))
public class Track {
    public Track() {

    }

    public Track(com.coopra.data.Track serverTrack) {
        id = serverTrack.id;
        userFavorite = serverTrack.user_favorite;
        favoritingsCount = serverTrack.favoritings_count;
        playbackCount = serverTrack.playback_count;
        streamUrl = serverTrack.stream_url;
        genre = serverTrack.genre;
        duration = serverTrack.duration;
        artworkUrl = serverTrack.artwork_url;
        title = serverTrack.title;
        userId = serverTrack.user_id;
        createdAt = serverTrack.created_at;
    }

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "created_at")
    public String createdAt;

    @ColumnInfo(name = "user_id", index = true)
    public String userId;

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
