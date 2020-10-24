package com.coopra.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.coopra.data.Origin

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
        @ColumnInfo(name = "activity_created_at") val activityCreatedAt: String,
        @ColumnInfo(name = "waveform") val waveform: IntArray
) {
    constructor(
            serverOrigin: Origin,
            nextToken: String,
            activityCreatedAt: String,
            waveform: IntArray) : this(serverOrigin.id,
            serverOrigin.created_at,
            serverOrigin.user_id,
            User(serverOrigin.user),
            serverOrigin.title,
            serverOrigin.artwork_url,
            serverOrigin.duration,
            serverOrigin.genre,
            serverOrigin.stream_url,
            serverOrigin.playback_count,
            serverOrigin.user_favorite,
            serverOrigin.likes_count,
            nextToken,
            activityCreatedAt,
            waveform)
}
