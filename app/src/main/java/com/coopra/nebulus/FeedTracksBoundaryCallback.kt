package com.coopra.nebulus

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.coopra.data.DashboardActivityEnvelope
import com.coopra.database.entities.Track
import com.coopra.nebulus.enums.NetworkStates
import com.coopra.service.service_implementations.ActivitiesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class FeedTracksBoundaryCallback(
        repository: TrackRepository,
        private val token: String,
        private val networkState: MutableLiveData<NetworkStates>) : PagedList.BoundaryCallback<Track>() {
    private val activitiesService = ActivitiesService()
    private var isLoading = false
    private val feedHelper = FeedHelper(repository)

    /**
     * Requests initial data from the network, replacing all content currently in the database.
     */
    override fun onZeroItemsLoaded() {
        if (isLoading) {
            return
        }

        isLoading = true
        networkState.postValue(NetworkStates.LOADING)

        MainScope().launch(Dispatchers.IO) {
            try {
                handleSuccessfulNetworkCall(activitiesService.getFeedTracks(token))
            } catch (ex: Exception) {
                isLoading = false
                networkState.postValue(NetworkStates.NORMAL)
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Track) {
        if (isLoading) {
            return
        }

        isLoading = true
        networkState.postValue(NetworkStates.LOADING)

        MainScope().launch(Dispatchers.IO) {
            try {
                handleSuccessfulNetworkCall(activitiesService.getNextTracks(token,
                        itemAtEnd.nextToken))
            } catch (ex: Exception) {
                isLoading = false
                networkState.postValue(NetworkStates.NORMAL)
            }
        }
    }

    private suspend fun handleSuccessfulNetworkCall(activityEnvelope: DashboardActivityEnvelope) {
        feedHelper.processActivityEnvelope(activityEnvelope)

        isLoading = false
        networkState.postValue(NetworkStates.NORMAL)
    }
}