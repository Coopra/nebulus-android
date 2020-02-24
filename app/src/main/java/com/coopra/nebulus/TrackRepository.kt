package com.coopra.nebulus

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.coopra.database.AppDatabase
import com.coopra.database.data_access_objects.TrackDao
import com.coopra.database.data_access_objects.UserDao
import com.coopra.database.entities.Track
import com.coopra.database.entities.User
import com.coopra.nebulus.enums.NetworkStates

class TrackRepository(application: Application, networkState: MutableLiveData<NetworkStates>) {
    private val trackDao: TrackDao
    private val userDao: UserDao
    private val allTracks: LiveData<PagedList<Track>>

    init {
        val database = AppDatabase.getDatabase(application)
        trackDao = database.trackDao()
        userDao = database.userDao()

        val myPagingConfig = PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(false)
                .build()

        val tokenHandler = TokenHandler()
        allTracks = LivePagedListBuilder(trackDao.getAll(), myPagingConfig)
                .setBoundaryCallback(FeedTracksBoundaryCallback(this,
                        tokenHandler.getToken(application)!!,
                        networkState))
                .build()
    }

    fun getAll(): LiveData<PagedList<Track>> {
        return allTracks
    }

    suspend fun insertAll(vararg parameters: TrackParameters) {
        userDao.insertAll(*parameters[0].users.toTypedArray())
        trackDao.insertAll(*parameters[0].tracks.toTypedArray())
    }

    data class TrackParameters(val tracks: List<Track>, val users: List<User>)
}