package com.fasta.stuntguard

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences("stuntguard_prefs", Context.MODE_PRIVATE)

    companion object {
        const val USER_LOGGED_IN = "user_logged_in"
        const val USER_ID = "user_id"
    }

    fun saveLoginState(isLoggedIn: Boolean, userId: String? = null) {
        val editor = prefs.edit()
        editor.putBoolean(USER_LOGGED_IN, isLoggedIn)
        userId?.let {
            editor.putString(USER_ID, it)
        }
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(USER_LOGGED_IN, false)
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}