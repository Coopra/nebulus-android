package com.coopra.nebulus.view_models

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.coopra.database.entities.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    private val imageLoader = ImageLoader.Builder(application)
            .build()

    var activeTrack: Track? = null
    val artwork: LiveData<Drawable>
        get() = _artwork
    private val _artwork = MutableLiveData<Drawable>()

    fun getArtwork() {
        viewModelScope.launch(Dispatchers.IO) {
            val request = ImageRequest.Builder(getApplication() as Application)
                    .data(activeTrack?.artworkUrl?.replace("large", "t500x500"))
                    .build()
            val result = imageLoader.execute(request)
            _artwork.postValue(result.drawable)
        }
    }
}