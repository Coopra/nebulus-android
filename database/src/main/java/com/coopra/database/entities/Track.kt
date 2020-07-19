package com.coopra.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.coopra.data.Track

@Entity(tableName = "track_table",
        foreignKeys = [ForeignKey(entity = User::class,
                parentColumns = ["id"],
                childColumns = ["user_id"])])
data class Track(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "created_at") val createdAt: String,
        @ColumnInfo(name = "user_id", index = true) val userId: Int,
        @ColumnInfo(name = "user") val user: User,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "artwork_url") val artworkUrl: String?,
        @ColumnInfo(name = "default") val duration: Long,
        @ColumnInfo(name = "genre") val genre: String?,
        @ColumnInfo(name = "stream_url") val streamUrl: String?,
        @ColumnInfo(name = "playback_count") val playbackCount: Int,
        @ColumnInfo(name = "user_favorite") val userFavorite: Boolean,
        @ColumnInfo(name = "likes_count") val likesCount: Int,
        @ColumnInfo(name = "next_href") val nextToken: String,
        @ColumnInfo(name = "activity_created_at") val activityCreatedAt: String
) {
    constructor(serverTrack: Track,
                nextToken: String,
                activityCreatedAt: String) : this(serverTrack.id,
            serverTrack.created_at,
            serverTrack.user_id,
            User(serverTrack.user),
            serverTrack.title,
            serverTrack.artwork_url,
            serverTrack.duration,
            serverTrack.genre,
            serverTrack.stream_url,
            serverTrack.playback_count,
            serverTrack.user_favorite,
            serverTrack.likes_count,
            nextToken,
            activityCreatedAt)
}
