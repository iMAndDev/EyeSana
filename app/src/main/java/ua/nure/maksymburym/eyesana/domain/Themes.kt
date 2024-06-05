package ua.nure.maksymburym.eyesana.domain

import androidx.annotation.StringRes
import ua.nure.maksymburym.eyesana.R

enum class Themes(@StringRes val nameRes: Int) {
    THEME_LIGHT(nameRes = R.string.theme_light),
    THEME_DARK(nameRes = R.string.theme_dark),
    THEME_SYSTEM(nameRes = R.string.system_default);

    companion object {
        fun fromOrdinal(ordinal: Int): Themes {
            return if (ordinal in Themes.entries.indices) Themes.entries[ordinal]
            else THEME_SYSTEM
        }
    }
}