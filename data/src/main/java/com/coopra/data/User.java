package com.coopra.data;

/**
 * A SoundCloud user.
 */
public class User {
    public int id;

    public String username;

    public String avatar_url;

    public String country;

    public String full_name;

    public String city;

    public String description;

    /**
     * Number of public tracks
     */
    public int track_count;

    /**
     * Number of public playlists
     */
    public int playlist_count;

    /**
     * Number of followers
     */
    public int followers_count;

    /**
     * Number of followed users
     */
    public int followings_count;

    /**
     * Number of favorited public tracks
     */
    public int public_favorites_count;
}
