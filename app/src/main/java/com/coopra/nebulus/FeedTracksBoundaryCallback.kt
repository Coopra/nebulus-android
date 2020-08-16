package com.coopra.nebulus

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.coopra.data.DashboardActivityEnvelope
import com.coopra.data.Waveform
import com.coopra.database.entities.Track
import com.coopra.database.entities.User
import com.coopra.nebulus.enums.NetworkStates
import com.coopra.service.service_implementations.ActivitiesService
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedTracksBoundaryCallback(
        private val repository: TrackRepository,
        private val token: String,
        private val networkState: MutableLiveData<NetworkStates>) : PagedList.BoundaryCallback<Track>() {
    private val activitiesService = ActivitiesService()
    private var isLoading = false
    private val gson = Gson()
    private val client = OkHttpClient()

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

        MainScope().launch {
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

            isLoading = false
            networkState.postValue(NetworkStates.NORMAL)
        }
    }

    private suspend fun getWaveform(waveformUrl: String): IntArray {
        val request = Request.Builder()
                .url(waveformUrl)
                .build()

        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            val waveform = gson.fromJson(response.body()?.charStream(), Waveform::class.java)
            waveform.samples
        }
    }
}