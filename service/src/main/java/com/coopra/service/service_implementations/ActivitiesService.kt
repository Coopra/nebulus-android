package com.coopra.service.service_implementations

import com.coopra.data.DashboardActivityEnvelope
import com.coopra.service.RetrofitHelper
import com.coopra.service.interfaces.ActivitiesInterface

class ActivitiesService {
    private val retrofitHelper = RetrofitHelper()
    private val service = retrofitHelper.createRetrofit().create(ActivitiesInterface::class.java)

    suspend fun getFeedTracks(token: String): DashboardActivityEnvelope {
        return service.getFeedTracks(token)
    }

    suspend fun getNextTracks(token: String, url: String): DashboardActivityEnvelope {
        return service.getNextTracks(url, token)
    }
}