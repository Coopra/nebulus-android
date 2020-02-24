package com.coopra.nebulus.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.coopra.data.DashboardActivityEnvelope
import com.coopra.database.entities.Track
import com.coopra.database.entities.User
import com.coopra.nebulus.TokenHandler
import com.coopra.nebulus.TrackRepository
import com.coopra.nebulus.enums.NetworkStates
import com.coopra.service.service_implementations.ActivitiesService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedViewModel(application: Application) : AndroidViewModel(application) {
    private val allTracks: LiveData<PagedList<Track>>
    private val repository: TrackRepository
    private val networkState = MutableLiveData<NetworkStates>()
    private val activitiesService = ActivitiesService()
    private val tokenHandler = TokenHandler()

    init {
        repository = TrackRepository(application, networkState)
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

        activitiesService.getFeedTracks(tokenHandler.getToken(getApplication())!!, object : Callback<DashboardActivityEnvelope> {
            override fun onResponse(call: Call<DashboardActivityEnvelope>, response: Response<DashboardActivityEnvelope>) {
                handleSuccessfulNetworkCall(response)
                networkState.postValue(NetworkStates.NORMAL)
            }

            override fun onFailure(call: Call<DashboardActivityEnvelope>, t: Throwable) {
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
            viewModelScope.launch {
                repository.insertAll(TrackRepository.TrackParameters(tracks, users))
            }
        }
    }
}