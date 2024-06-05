package ua.nure.maksymburym.eyesana.domain

import android.content.res.Resources

data class SystemConfigModel(
    val appTheme: Themes,
    val appLanguage: Languages,
    val resources: Resources
)
