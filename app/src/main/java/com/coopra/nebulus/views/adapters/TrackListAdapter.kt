package com.coopra.nebulus.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coopra.database.entities.Track
import com.coopra.nebulus.R

class TrackListAdapter : PagedListAdapter<Track, TrackListAdapter.TrackViewHolder>(
        DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_track, parent, false)
        return TrackViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val current = getItem(position)
        if (current != null) {
            holder.titleView.text = current.title
            holder.artistNameView.text = current.user.username
            holder.playsView.text = current.playbackCount.toString()
            holder.genreView.text =
                    holder.genreView.context.resources.getString(R.string.genre_tag, current.genre)

            val artworkUrl = current.artworkUrl
            if (artworkUrl?.isNotEmpty() == true) {
                Glide.with(holder.itemView)
                        .load(artworkUrl.replace("large", "t500x500"))
                        .into(holder.artworkView)
            }
        } else {
            // Covers the case of data not being ready yet
            holder.titleView.text = "No tracks"
        }
    }

    inner class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val titleView: TextView = itemView.findViewById(R.id.title)
        internal val artistNameView: TextView = itemView.findViewById(R.id.artist_name)
        internal val artworkView: ImageView = itemView.findViewById(R.id.artwork)
        internal val playsView: TextView = itemView.findViewById(R.id.plays)
        internal val genreView: TextView = itemView.findViewById(R.id.genre)
    }

    private companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Track>() {
            override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
                return oldItem == newItem
            }

        }
    }
}