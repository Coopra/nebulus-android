package com.coopra.nebulus.views.adapters;

import androidx.paging.PagedListAdapter;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coopra.database.entities.Track;
import com.coopra.nebulus.R;

public class TrackListAdapter extends PagedListAdapter<Track, TrackListAdapter.TrackViewHolder> {
    private final LayoutInflater mInflater;

    public TrackListAdapter(Context context) {
        super(DIFF_CALLBACK);
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
        Track current = getItem(i);
        if (current != null) {
            trackViewHolder.titleView.setText(current.title);
            //trackViewHolder.artistNameView.setText(current.users.username);

            if (!TextUtils.isEmpty(current.artworkUrl)) {
                Glide.with(trackViewHolder.itemView)
                        .load(current.artworkUrl.replace("large", "t500x500"))
                        .into(trackViewHolder.artworkView);
            }
        } else {
            // Covers the case of data not being ready yet
            trackViewHolder.titleView.setText("No tracks");
        }
    }

    class TrackViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView artistNameView;
        private final ImageView artworkView;

        TrackViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            artistNameView = itemView.findViewById(R.id.artist_name);
            artworkView = itemView.findViewById(R.id.artwork);
        }
    }

    private static DiffUtil.ItemCallback<Track> DIFF_CALLBACK = new DiffUtil.ItemCallback<Track>() {
        @Override
        public boolean areItemsTheSame(@NonNull Track oldItem, @NonNull Track newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Track oldItem, @NonNull Track newItem) {
            return oldItem.equals(newItem);
        }
    };
}
