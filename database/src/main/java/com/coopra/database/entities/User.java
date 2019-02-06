package com.coopra.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {
    public User() {

    }

    public User(com.coopra.data.User serverUser) {
        this.id = serverUser.id;
        this.username = serverUser.username;
        this.avatarUrl = serverUser.avatar_url;
        this.country = serverUser.country;
        this.fullName = serverUser.full_name;
        this.city = serverUser.city;
        this.description = serverUser.description;
        this.trackCount = serverUser.track_count;
        this.playlistCount = serverUser.playlist_count;
        this.followersCount = serverUser.followers_count;
        this.followingsCount = serverUser.followings_count;
        this.publicFavoritesCount = serverUser.public_favorites_count;
    }

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "avatar_url")
    public String avatarUrl;

    @ColumnInfo(name = "country")
    public String country;

    @ColumnInfo(name = "full_name")
    public String fullName;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "track_count")
    public int trackCount;

    @ColumnInfo(name = "playlist_count")
    public int playlistCount;

    @ColumnInfo(name = "followers_count")
    public int followersCount;

    @ColumnInfo(name = "followings_count")
    public int followingsCount;

    @ColumnInfo(name = "public_favorites_count")
    public int publicFavoritesCount;
}
