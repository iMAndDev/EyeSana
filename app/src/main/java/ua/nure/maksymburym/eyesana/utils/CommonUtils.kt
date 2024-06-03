package ua.nure.maksymburym.eyesana.utils

import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import ua.nure.maksymburym.eyesana.app.CommonModule
import ua.nure.maksymburym.eyesana.domain.ThemesConstants

/* Application */

fun setAppTheme(theme: Int) {
    val mode: Int = when (theme) {
        ThemesConstants.THEME_LIGHT -> MODE_NIGHT_NO
        ThemesConstants.THEME_DARK -> MODE_NIGHT_YES
        else -> MODE_NIGHT_FOLLOW_SYSTEM
    }
    AppCompatDelegate.setDefaultNightMode(mode)
}

@Composable
fun isUsingDarkColorScheme() = when (AppCompatDelegate.getDefaultNightMode()) {
    MODE_NIGHT_YES -> true
    MODE_NIGHT_NO -> false
    else -> isSystemInDarkTheme()
}

/* end */

/* JSON */

inline fun <reified T> T.toJson(serializer: KSerializer<T>? = null): String {
    serializer ?: return CommonModule.json.encodeToString(this)
    return CommonModule.json.encodeToString(serializer, this)
}

inline fun <reified T> String.fromJson(serializer: KSerializer<T>? = null) : T {
    serializer ?: return CommonModule.json.decodeFromString(this)
    return CommonModule.json.decodeFromString(serializer, string = this)
}

/* end */