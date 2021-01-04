package com.coopra.nebulus

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class TokenHandler {
    fun saveToken(context: Context, token: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit(commit = true) {
            putString(TOKEN_KEY, token)
        }
    }

    fun getToken(context: Context): String? {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(TOKEN_KEY, null)
    }

    companion object {
        private const val TOKEN_KEY = "saved_token"
    }
}