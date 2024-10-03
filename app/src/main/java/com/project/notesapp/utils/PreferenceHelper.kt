package com.project.notesapp.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class PreferenceHelper @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        private const val USER_ID = "userId"
        private const val USER_NAME = "userName"
        private const val USER_EMAIL = "userEmail"
        private const val LOGIN_USER_EMAIL = "loginUserEmail"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("note_preferences", Context.MODE_PRIVATE)

    private val loginPreferences: SharedPreferences =
        context.getSharedPreferences("login_preferences", Context.MODE_PRIVATE)


    fun getUserId(): String? {
        return sharedPreferences.getString(USER_ID, null)
    }

    fun setUserId(userId: String) {
        sharedPreferences.edit().putString(USER_ID, userId).apply()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME, null)
    }

    fun setUserName(userName: String) {
        sharedPreferences.edit().putString(USER_NAME, userName).apply()
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString(USER_EMAIL, null)
    }

    fun setUserEmail(userEmail: String) {
        sharedPreferences.edit().putString(USER_EMAIL, userEmail).apply()
    }

    fun getLoginEmail(): String? {
        return loginPreferences.getString(LOGIN_USER_EMAIL, null)
    }

    fun setLoginEmail(loginEmail: String) {
        loginPreferences.edit().putString(LOGIN_USER_EMAIL, loginEmail).apply()
    }

    fun clearSharedPreference() {
        sharedPreferences.edit().clear().apply()
    }

}