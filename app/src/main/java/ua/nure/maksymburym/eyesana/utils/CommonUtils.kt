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

/***
 * Returns time in `12:34:56` format.
 * If some unit is absent (for instance, minutes), it doesn't shown (ex., `12:33` or `57`)
 */
fun Long.formatTime(): String {
    val hour = (this / 3600000) % 24
    val min = (this / 60000) % 60
    val sec = (this / 1000) % 60

    val formattedHour = hour.twoDigitString()
    val formattedMin = min.twoDigitString()
    val formattedSec = sec.twoDigitString()

    return buildString {
        append(if (formattedHour == "00") "" else "$formattedHour:")
        append(if (formattedMin == "00") "" else "$formattedMin:")
        append(formattedSec)
    }
}

fun Long.twoDigitString() = if (this > 9) this else "0$this"