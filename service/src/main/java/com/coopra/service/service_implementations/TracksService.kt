package com.coopra.service.service_implementations

import com.coopra.data.Origin
import com.coopra.service.BuildConfig
import com.coopra.service.RetrofitHelper
import com.coopra.service.interfaces.TracksInterface
import retrofit2.Callback

class TracksService {
    private val retrofitHelper = RetrofitHelper()

    fun getRandomTracks(callback: Callback<List<Origin>>) {
        val service = retrofitHelper.createRetrofit().create(TracksInterface::class.java)
        val call = service.getRandomTracks(BuildConfig.CLIENT_ID)
        call.enqueue(callback)
    }
}