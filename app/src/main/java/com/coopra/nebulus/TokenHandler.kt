package com.coopra.nebulus

import android.content.Context

class TokenHandler {
    fun saveToken(context: Context, token: String) {
        val sharedPref =
                context.getSharedPreferences(context.getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(context.getString(R.string.saved_token), token)
        editor.commit()
    }

    fun getToken(context: Context): String? {
        val sharedPref =
                context.getSharedPreferences(context.getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE)
        return sharedPref.getString(context.getString(R.string.saved_token), null)
    }
}