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

    private val USER_ROLE = "userRole"
    private val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    fun saveToken(token: String, role: String) {
        editor.putString(TOKEN_KEY, token)
        editor.putString(USER_ROLE, role)
        editor.apply()
    }
    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }
    fun getUserRole(): String? {
        return sharedPreferences.getString(USER_ROLE, null)
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
        editor.remove(USER_ROLE)
        editor.apply()
    }
}