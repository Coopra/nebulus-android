package com.coopra.nebulus.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.coopra.data.DashboardActivityEnvelope
import com.coopra.database.entities.Track
import com.coopra.nebulus.FeedHelper
import com.coopra.nebulus.TokenHandler
import com.coopra.nebulus.TrackRepository
import com.coopra.nebulus.enums.NetworkStates
import com.coopra.service.service_implementations.ActivitiesService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {
    private val allTracks: LiveData<PagedList<Track>>
    private val repository: TrackRepository
    private val networkState = MutableLiveData<NetworkStates>()
    private val activitiesService = ActivitiesService()
    private val tokenHandler = TokenHandler()
    private val feedHelper: FeedHelper

    init {
        repository = TrackRepository(application, networkState)
        feedHelper = FeedHelper(repository)
        allTracks = repository.getAll()
    }

    fun getAllTracks(): LiveData<PagedList<Track>> {
        return allTracks
    }

    fun getNetworkState(): MutableLiveData<NetworkStates> {
        return networkState
    }

    fun refreshFeed() {
        networkState.value = NetworkStates.LOADING

        viewModelScope.launch(Dispatchers.IO) {
            try {
                handleSuccessfulNetworkCall(activitiesService.getFeedTracks(tokenHandler.getToken(
                        getApplication())!!))
            } catch (ex: Exception) {
                networkState.postValue(NetworkStates.NORMAL)
            }
        }
    }

    private suspend fun handleSuccessfulNetworkCall(activityEnvelope: DashboardActivityEnvelope) {
        feedHelper.processActivityEnvelope(activityEnvelope)
        networkState.postValue(NetworkStates.NORMAL)
    }
}