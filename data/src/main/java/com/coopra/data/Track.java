package com.coopra.data;

/**
 * A SoundCloud track
 */
public class Track {
    /**
     * Track ID
     */
    public int id;

    /**
     * Timestamp of creation
     */
    public String created_at;

    /**
     * User ID of the owner
     */
    public int user_id;

    /**
     * Mini user representation of the owner
     */
    public User user;

    /**
     * Track title
     */
    public String title;

    /**
     * URL to a JPEG image
     */
    public String artwork_url;

    /**
     * Duration in milliseconds
     */
    public long duration;

    /**
     * Genre
     */
    public String genre;

    /**
     * Link to 128kbps mp3 stream
     */
    public String stream_url;

    /**
     * Track play count
     */
    public int playback_count;

    /**
     * Track favorite count
     */
    public int favoritings_count;

    /**
     * Indicates if the track is a favorite of the current user
     */
    public boolean user_favorite;
}
