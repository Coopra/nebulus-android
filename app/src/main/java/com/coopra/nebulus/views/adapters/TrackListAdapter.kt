package com.coopra.nebulus.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.coopra.database.entities.Track
import com.coopra.nebulus.R

class TrackListAdapter(private val selectionListener: SelectionListener) : PagedListAdapter<Track, TrackListAdapter.TrackViewHolder>(
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
            holder.likesView.text = current.likesCount.toString()
            holder.genreView.text =
                    holder.genreView.context.resources.getString(R.string.genre_tag, current.genre)
            holder.artworkView.load(current.artworkUrl?.replace("large", "t500x500"))
            holder.itemView.setOnClickListener { selectionListener.onClick(current) }
        } else {
            // Covers the case of data not being ready yet
            holder.titleView.text = "No tracks"
        }
    }

    inner class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val titleView: TextView = itemView.findViewById(R.id.title)
        internal val artistNameView: TextView = itemView.findViewById(R.id.artist_name)
        internal val artworkView: ImageView = itemView.findViewById(R.id.artwork)
        internal val likesView: TextView = itemView.findViewById(R.id.likes)
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

    interface SelectionListener {
        fun onClick(track: Track)
    }
}