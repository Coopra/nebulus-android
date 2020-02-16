package com.coopra.data

/**
 * A SoundCloud user.
 */
data class User(
        val id: Int,
        val username: String,
        val avatar_url: String,
        val country: String,
        val full_name: String,
        val city: String,
        val description: String,

        /**
         * Number of public tracks
         */
        val track_count: Int,

        /**
         * Number of public playlists
         */
        val playlist_count: Int,

        /**
         * Number of followers
         */
        val followers_count: Int,

        /**
         * Number of followed users
         */
        val followings_count: Int,

        /**
         * Number of favorited public tracks
         */
        val public_favorites_count: Int
)