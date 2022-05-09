package com.youtubelist.task.utils

import android.content.SharedPreferences

private const val LIGHT_STATUS_COLOR = "LIGHT_COLOR_STATUS"

class PrefUtils(private val prefs: SharedPreferences) {

    fun clearAll() = prefs.edit().clear().apply()

    var lightStatusColor: Int
        get() = prefs.getInt(LIGHT_STATUS_COLOR, 0)
        set(value) = prefs.edit().putInt(LIGHT_STATUS_COLOR, value).apply()
}