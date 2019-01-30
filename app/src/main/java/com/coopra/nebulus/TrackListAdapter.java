package com.coopra.nebulus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coopra.database.entities.Track;

import java.util.List;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.TrackViewHolder> {
    private final LayoutInflater mInflater;
    private List<Track> mTracks; // Cached copy of tracks

    TrackListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.item_track, viewGroup, false);
        return new TrackViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder trackViewHolder, int i) {
        if (mTracks != null) {
            Track current = mTracks.get(i);
            trackViewHolder.titleView.setText(current.title);
            //trackViewHolder.artistNameView.setText(current.user.username);

            if (!TextUtils.isEmpty(current.artworkUrl)) {
                Glide.with(trackViewHolder.itemView)
                        .load(current.artworkUrl.replace("large", "t500x500"))
                        .into(trackViewHolder.artworkView);
            }
        } else {
            // Covers the case of data not being ready yet
            trackViewHolder.titleView.setText("No track");
        }
    }

    @Override
    public int getItemCount() {
        if (mTracks != null) {
            return mTracks.size();
        } else {
            return 0;
        }
    }

    void setTracks(List<Track> tracks) {
        mTracks = tracks;
        notifyDataSetChanged();
    }

    class TrackViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView artistNameView;
        private final ImageView artworkView;

        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            artistNameView = itemView.findViewById(R.id.artist_name);
            artworkView = itemView.findViewById(R.id.artwork);
        }
    }
}
