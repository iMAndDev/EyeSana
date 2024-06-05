package ua.nure.maksymburym.eyesana.utils

import android.content.res.Resources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.nure.maksymburym.eyesana.domain.Languages
import ua.nure.maksymburym.eyesana.domain.SystemConfigModel
import ua.nure.maksymburym.eyesana.domain.Themes

object SystemConfig {

    private val _systemConfig = MutableStateFlow(
        // Initial value, updates on app launch
        SystemConfigModel(
            appTheme = Themes.THEME_SYSTEM,
            appLanguage = Languages.ENGLISH,
            resources = Resources.getSystem()
        )
    )
    val systemConfig = _systemConfig.asStateFlow()

    fun init(theme: Themes, language: Languages, resources: Resources) {
        _systemConfig.value = SystemConfigModel(theme, language, resources)
    }

    fun updateTheme(theme: Themes) {
        _systemConfig.value = _systemConfig.value.copy(appTheme = theme)
    }

    fun updateLanguage(language: Languages) {
        _systemConfig.value = _systemConfig.value.copy(appLanguage = language)
    }
}