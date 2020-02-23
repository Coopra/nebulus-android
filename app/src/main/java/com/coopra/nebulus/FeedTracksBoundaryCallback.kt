package com.coopra.nebulus

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.coopra.data.DashboardActivityEnvelope
import com.coopra.database.entities.Track
import com.coopra.database.entities.User
import com.coopra.nebulus.enums.NetworkStates
import com.coopra.service.service_implementations.ActivitiesService
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedTracksBoundaryCallback(
        private val repository: TrackRepository,
        private val token: String,
        private val networkState: MutableLiveData<NetworkStates>) : PagedList.BoundaryCallback<Track>() {
    private val activitiesService = ActivitiesService()
    private var isLoading = false

    /**
     * Requests initial data from the network, replacing all content currently in the database.
     */
    override fun onZeroItemsLoaded() {
        if (isLoading) {
            return
        }

        isLoading = true
        networkState.postValue(NetworkStates.LOADING)

        activitiesService.getFeedTracks(token, object : Callback<DashboardActivityEnvelope> {
            override fun onResponse(
                    call: Call<DashboardActivityEnvelope>,
                    response: Response<DashboardActivityEnvelope>) {
                handleSuccessfulNetworkCall(response)
                isLoading = false
                networkState.postValue(NetworkStates.NORMAL)
            }

            override fun onFailure(call: Call<DashboardActivityEnvelope>, t: Throwable) {
                isLoading = false
                networkState.postValue(NetworkStates.NORMAL)
            }
        })
    }

    override fun onItemAtEndLoaded(itemAtEnd: Track) {
        if (isLoading) {
            return
        }

        isLoading = true
        networkState.postValue(NetworkStates.LOADING)

        activitiesService.getNextTracks(token,
                itemAtEnd.nextToken,
                object : Callback<DashboardActivityEnvelope> {
                    override fun onResponse(
                            call: Call<DashboardActivityEnvelope>,
                            response: Response<DashboardActivityEnvelope>) {
                        handleSuccessfulNetworkCall(response)
                        isLoading = false
                        networkState.postValue(NetworkStates.NORMAL)
                    }

                    override fun onFailure(call: Call<DashboardActivityEnvelope>, t: Throwable) {
                        isLoading = false
                        networkState.postValue(NetworkStates.NORMAL)
                    }
                })
    }

    private fun handleSuccessfulNetworkCall(response: Response<DashboardActivityEnvelope>) {
        val activityEnvelope = response.body() ?: return
        val tracks = mutableListOf<Track>()
        val users = mutableListOf<User>()

        for (activity in activityEnvelope.collection) {
            val origin = activity.origin ?: continue
            tracks.add(Track(origin, activityEnvelope.next_href, activity.created_at))
            users.add(User(origin.user))
        }

        if (tracks.size > 0) {
            MainScope().launch {
                repository.insertAll(TrackRepository.TrackParameters(tracks, users))
            }
        }
    }
}