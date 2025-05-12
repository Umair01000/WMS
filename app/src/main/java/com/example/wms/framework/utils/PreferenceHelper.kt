package com.example.wms.framework.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {

    private const val PREF_NAME = "app_prefs"
    private const val KEY_USER_ROLE = "user_role"

    private fun Context.getPreferences(): SharedPreferences =
        getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveUserRole(context: Context, role: String) {
        context.getPreferences().edit().apply {
            putString(KEY_USER_ROLE, role)
            apply()
        }
    }

    fun getUserRole(context: Context): String {
        return context.getPreferences().getString(KEY_USER_ROLE, "user") ?: "user"
    }

    fun clearUserRole(context: Context) {
        context.getPreferences().edit().apply {
            remove(KEY_USER_ROLE)
            apply()
        }
    }
}
