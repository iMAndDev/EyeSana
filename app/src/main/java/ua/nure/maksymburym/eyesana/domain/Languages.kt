package ua.nure.maksymburym.eyesana.domain

import androidx.annotation.StringRes
import ua.nure.maksymburym.eyesana.R

enum class Languages(
    val locale: String,
    @StringRes val langNameRes: Int
) {
    ENGLISH("en", R.string.lang_english),
    UKRAINIAN("uk", R.string.lang_ukrainian);

    companion object {
        fun fromLocale(locale: String): Languages {
            return when (locale) {
                UKRAINIAN.locale -> UKRAINIAN
                else -> ENGLISH
            }
        }
    }
}