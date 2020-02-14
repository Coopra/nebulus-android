package com.coopra.service.service_implementations

import com.coopra.data.DashboardActivityEnvelope
import com.coopra.service.RetrofitHelper
import com.coopra.service.interfaces.ActivitiesInterface
import retrofit2.Callback

class ActivitiesService {
    fun getFeedTracks(token: String, callback: Callback<DashboardActivityEnvelope>) {
        val service = RetrofitHelper.createRetrofit().create(ActivitiesInterface::class.java)
        val call = service.getFeedTracks(token)
        call.enqueue(callback)
    }

    fun getNextTracks(token: String, url: String, callback: Callback<DashboardActivityEnvelope>) {
        val service = RetrofitHelper.createRetrofit().create(ActivitiesInterface::class.java)
        val call = service.getNextTracks(url, token)
        call.enqueue(callback)
    }
}