package com.coopra.nebulus.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.coopra.data.DashboardActivityEnvelope
import com.coopra.data.Waveform
import com.coopra.database.entities.Track
import com.coopra.database.entities.User
import com.coopra.nebulus.TokenHandler
import com.coopra.nebulus.TrackRepository
import com.coopra.nebulus.enums.NetworkStates
import com.coopra.service.service_implementations.ActivitiesService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedViewModel(application: Application) : AndroidViewModel(application) {
    private val allTracks: LiveData<PagedList<Track>>
    private val repository: TrackRepository
    private val networkState = MutableLiveData<NetworkStates>()
    private val activitiesService = ActivitiesService()
    private val tokenHandler = TokenHandler()
    private val gson = Gson()
    private val client = OkHttpClient()

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

        activitiesService.getFeedTracks(tokenHandler.getToken(getApplication())!!,
                object : Callback<DashboardActivityEnvelope> {
                    override fun onResponse(
                            call: Call<DashboardActivityEnvelope>,
                            response: Response<DashboardActivityEnvelope>) {
                        handleSuccessfulNetworkCall(response)
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

        viewModelScope.launch {
            for (activity in activityEnvelope.collection) {
                val origin = activity.origin ?: continue
                if (origin.kind != "track" || origin.waveform_url.isNullOrEmpty()) {
                    continue
                }

                tracks.add(Track(origin,
                        activityEnvelope.next_href,
                        activity.created_at,
                        getWaveform(origin.waveform_url!!)))
                users.add(User(origin.user))
            }

            if (tracks.size > 0) {
                repository.insertAll(TrackRepository.TrackParameters(tracks, users))
            }

            networkState.postValue(NetworkStates.NORMAL)
        }
    }

    private suspend fun getWaveform(waveformUrl: String): IntArray {
        val request = Request.Builder()
                .url(waveformUrl.replace(".png", ".json"))
                .build()

        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            val waveform = gson.fromJson(response.body()?.charStream(), Waveform::class.java)
            waveform.samples
        }
    }
}