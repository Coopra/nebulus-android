package com.coopra.data

/**
 * A SoundCloud track
 */
data class Track(
        /**
         * Track ID
         */
        val id: Int,

        /**
         * Timestamp of creation
         */
        val created_at: String,

        /**
         * User ID of the owner
         */
        val user_id: Int,

        /**
         * Mini user representation of the owner
         */
        val user: User,

        /**
         * Track title
         */
        val title: String,

        /**
         * URL to a JPEG image
         */
        val artwork_url: String,

        /**
         * Duration in milliseconds
         */
        val duration: Long,

        /**
         * Genre
         */
        val genre: String,

        /**
         * Link to 128kbps mp3 stream
         */
        val stream_url: String,

        /**
         * Track play count
         */
        val playback_count: Int,

        /**
         * Track favorite count
         */
        val favoritings_count: Int,

        /**
         * Indicates if the track is a favorite of the current user
         */
        val user_favorite: Boolean
)