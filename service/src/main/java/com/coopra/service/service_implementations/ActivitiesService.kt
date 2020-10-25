package com.coopra.service.service_implementations

import com.coopra.data.DashboardActivityEnvelope
import com.coopra.service.RetrofitHelper
import com.coopra.service.interfaces.ActivitiesInterface

class ActivitiesService {
    private val retrofitHelper = RetrofitHelper()

    suspend fun getFeedTracks(token: String): DashboardActivityEnvelope {
        val service = retrofitHelper.createRetrofit().create(ActivitiesInterface::class.java)
        return service.getFeedTracks(token)
    }

    suspend fun getNextTracks(token: String, url: String): DashboardActivityEnvelope {
        val service = retrofitHelper.createRetrofit().create(ActivitiesInterface::class.java)
        return service.getNextTracks(url, token)
    }
}