package com.coopra.data

/**
 * A SoundCloud track
 */
data class Origin(
        /**
         * Origin ID
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
         * Indicates if the track is a favorite of the current user
         */
        val user_favorite: Boolean,

        /**
         * Track likes count
         */
        val likes_count: Int,

        /**
         * Link to get JSON representation of waveform
         */
        val waveform_url: String?,

        /**
         * Type of origin, e.g. track or playlist
         */
        val kind: String
)