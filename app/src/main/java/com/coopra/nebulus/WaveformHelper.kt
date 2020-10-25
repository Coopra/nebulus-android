package com.coopra.nebulus

import com.coopra.data.Waveform
import com.google.gson.Gson
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class WaveformHelper {
    private val gson = Gson()
    private val client = OkHttpClient()

    suspend fun getWaveform(waveformUrl: String): IntArray =
            suspendCancellableCoroutine { continuation ->
                val request = Request.Builder()
                        .url(waveformUrl.replace(".png", ".json"))
                        .build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        continuation.resumeWithException(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val waveform =
                                gson.fromJson(response.body()?.charStream(), Waveform::class.java)
                        continuation.resume(waveform.samples)
                    }
                })
            }
}