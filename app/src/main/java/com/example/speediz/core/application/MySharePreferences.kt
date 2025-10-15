package com.example.speediz.core.application

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MySharePreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val PREFERENCES_NAME = "MyPreferences"
    private val TOKEN_KEY = "token"
    private val USER_ID = "userId"
    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    fun saveToken(token: String) {
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }
    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }
    fun saveUserId(userId: Int) {
        editor.putInt(USER_ID, userId)
        editor.apply()
    }
    fun getUserId(): Int {
        return sharedPreferences.getInt(USER_ID, -1)
    }
    fun clearToken() {
        editor.remove(TOKEN_KEY)
        editor.remove(USER_ID)
        editor.apply()
    }
}