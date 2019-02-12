package com.coopra.nebulus.views.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.coopra.data.Track;
import com.coopra.nebulus.R;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlternativeAdapter extends RecyclerView.Adapter<AlternativeAdapter.MyViewHolder> {
    private List<Track> mTracks;

    public void setTracks(List<Track> tracks) {
        mTracks = tracks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Track current = mTracks.get(position);

        holder.titleView.setText(current.title);
        holder.artistNameView.setText(current.user.username);
        holder.playsView.setText(String.format(Locale.getDefault(), "%d", current.playback_count));

        if (!TextUtils.isEmpty(current.genre)) {
            holder.genreView.setText(holder.genreView.getResources().getString(R.string.genre_tag, current.genre));
        }

        if (!TextUtils.isEmpty(current.artwork_url)) {
            Glide.with(holder.itemView)
                    .load(current.artwork_url.replace("large", "t500x500"))
                    .into(holder.artworkView);
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView artistNameView;
        private final ImageView artworkView;
        private final TextView playsView;
        private final TextView genreView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title);
            artistNameView = itemView.findViewById(R.id.artist_name);
            artworkView = itemView.findViewById(R.id.artwork);
            playsView = itemView.findViewById(R.id.plays);
            genreView = itemView.findViewById(R.id.genre);
        }
    }
}
