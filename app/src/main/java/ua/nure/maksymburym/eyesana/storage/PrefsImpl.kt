package ua.nure.maksymburym.eyesana.storage

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.nure.maksymburym.eyesana.BuildConfig
import ua.nure.maksymburym.eyesana.domain.ThemesConstants
import ua.nure.maksymburym.eyesana.utils.fromJson
import ua.nure.maksymburym.eyesana.utils.toJson
import javax.inject.Inject

class PrefsImpl @Inject constructor(@ApplicationContext private val context: Context) : Prefs {

    private fun createEncryptedSharedPreferences() =
        EncryptedSharedPreferences.create(
            context,
            SETTINGS,
            MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    override val sharedPrefs: SharedPreferences = createEncryptedSharedPreferences()
    private val editor = sharedPrefs.edit()

    override fun saveValue(key: String, obj: Any) {
        when (obj) {
            is String -> editor.putString(key, obj)
            is Int -> editor.putInt(key, obj)
            is Float -> editor.putFloat(key, obj)
            is Long -> editor.putLong(key, obj)
            is Boolean -> editor.putBoolean(key, obj)
            else -> {
                if (BuildConfig.DEBUG){
                    throw NotImplementedError("can't define how to save $obj")
                }
            }
        }
        editor.apply()
    }

    override fun getBoolean(key: String, default: Boolean) = sharedPrefs.getBoolean(key, default)

    override fun getInt(key: String, default: Int) = sharedPrefs.getInt(key, default)

    override fun getFloat(key: String, default: Float) = sharedPrefs.getFloat(key, default)

    override fun getLong(key: String, default: Long) = sharedPrefs.getLong(key, default)

    override fun getString(key: String, default: String?) = sharedPrefs.getString(key, default)

    inline fun <reified T> get(key: String, default: T): T {
        val obj = sharedPrefs.getString(key, default.toJson())
        return obj?.fromJson() ?: default
    }

    override fun remove(vararg keys: String) {
        for (i in keys.indices) {
            editor.remove(keys[i])
        }
        editor.apply()
    }

    override fun getAppTheme(): Int {
        return getInt(
            APP_THEME,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) ThemesConstants.THEME_SYSTEM
            else ThemesConstants.THEME_LIGHT
        )
    }

    companion object {
        private const val SETTINGS = "settings"
        private const val APP_THEME = "theme"
    }
}