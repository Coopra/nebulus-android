package com.coopra.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "track_table", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id"))
public class Track {
    public Track() {

    }

    public Track(@NonNull com.coopra.data.Track serverTrack, @NonNull String nextToken, @NonNull String activityCreatedAt) {
        this.id = serverTrack.id;
        this.userFavorite = serverTrack.user_favorite;
        this.favoritingsCount = serverTrack.favoritings_count;
        this.playbackCount = serverTrack.playback_count;
        this.streamUrl = serverTrack.stream_url;
        this.genre = serverTrack.genre;
        this.duration = serverTrack.duration;
        this.artworkUrl = serverTrack.artwork_url;
        this.title = serverTrack.title;
        this.userId = serverTrack.user_id;
        this.createdAt = serverTrack.created_at;
        this.nextToken = nextToken;
        this.activityCreatedAt = activityCreatedAt;
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

    @ColumnInfo(name = "next_href")
    public String nextToken;

    @ColumnInfo(name = "activity_created_at")
    public String activityCreatedAt;
}
