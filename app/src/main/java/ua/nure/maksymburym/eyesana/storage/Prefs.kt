package ua.nure.maksymburym.eyesana.storage

import android.content.SharedPreferences

interface Prefs {

    val sharedPrefs: SharedPreferences

    fun saveValue(key: String, obj: Any)

    fun getBoolean(key: String, default: Boolean = false): Boolean

    fun getInt(key: String, default: Int = 0): Int

    fun getFloat(key: String, default: Float = 0f): Float

    fun getLong(key: String, default: Long = 0): Long

    fun getString(key: String, default: String? = null): String?

    fun remove(vararg keys: String)

    fun getAppTheme(): Int
}