package com.coopra.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.coopra.data.User

@Entity(tableName = "user_table")
data class User(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "username") val username: String,
        @ColumnInfo(name = "avatar_url") val avatarUrl: String,
        @ColumnInfo(name = "country") val country: String?,
        @ColumnInfo(name = "full_name") val fullName: String?,
        @ColumnInfo(name = "city") val city: String?,
        @ColumnInfo(name = "description") val description: String?,
        @ColumnInfo(name = "track_count") val trackCount: Int,
        @ColumnInfo(name = "playlist_count") val playlistCount: Int,
        @ColumnInfo(name = "followers_count") val followersCount: Int,
        @ColumnInfo(name = "followings_count") val followingsCount: Int,
        @ColumnInfo(name = "public_favorites_count") val publicFavoritesCount: Int
) {
    constructor(serverUser: User) : this(serverUser.id,
            serverUser.username,
            serverUser.avatar_url,
            serverUser.country,
            serverUser.full_name,
            serverUser.city,
            serverUser.description,
            serverUser.track_count,
            serverUser.playlist_count,
            serverUser.followers_count,
            serverUser.followings_count,
            serverUser.public_favorites_count)
}