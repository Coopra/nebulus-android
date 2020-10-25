package com.coopra.nebulus

import com.coopra.data.DashboardActivityEnvelope
import com.coopra.database.entities.Track
import com.coopra.database.entities.User

class FeedHelper(private val repository: TrackRepository) {
    private val waveformHelper = WaveformHelper()

    suspend fun processActivityEnvelope(activityEnvelope: DashboardActivityEnvelope) {
        val tracks = mutableListOf<Track>()
        val users = mutableListOf<User>()

        for (activity in activityEnvelope.collection) {
            val origin = activity.origin ?: continue
            if (origin.kind != "track" || origin.waveform_url.isNullOrEmpty()) {
                continue
            }

            tracks.add(Track(origin,
                    activityEnvelope.next_href,
                    activity.created_at,
                    waveformHelper.getWaveform(origin.waveform_url!!)))
            users.add(User(origin.user))
        }

        if (tracks.size > 0) {
            repository.insertAll(TrackRepository.TrackParameters(tracks, users))
        }
    }
}