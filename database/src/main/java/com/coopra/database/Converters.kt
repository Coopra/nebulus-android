package com.coopra.database

import androidx.room.TypeConverter
import com.coopra.database.entities.User
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromUser(user: User): String {
        val gson = Gson()
        return gson.toJson(user)
    }

    @TypeConverter
    fun toUser(userJson: String): User {
        val gson = Gson()
        return gson.fromJson(userJson, User::class.java)
    }

    @TypeConverter
    fun fromWaveform(waveform: IntArray): String {
        val gson = Gson()
        return gson.toJson(waveform)
    }

    @TypeConverter
    fun toWaveform(waveformJson: String): IntArray {
        val gson = Gson()
        return gson.fromJson(waveformJson, IntArray::class.java)
    }
}