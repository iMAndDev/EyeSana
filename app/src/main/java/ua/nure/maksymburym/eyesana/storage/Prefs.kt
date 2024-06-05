package ua.nure.maksymburym.eyesana.storage

import android.content.SharedPreferences
import ua.nure.maksymburym.eyesana.domain.Languages
import ua.nure.maksymburym.eyesana.domain.Themes

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

    fun saveAppTheme(theme: Themes)

    fun getAppLang(): Languages

    fun saveAppLanguage(lang: Languages)
}