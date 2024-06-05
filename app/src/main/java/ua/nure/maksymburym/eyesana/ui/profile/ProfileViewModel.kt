package ua.nure.maksymburym.eyesana.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.nure.maksymburym.eyesana.domain.Languages
import ua.nure.maksymburym.eyesana.domain.Themes
import ua.nure.maksymburym.eyesana.storage.Prefs
import ua.nure.maksymburym.eyesana.utils.SystemConfig
import ua.nure.maksymburym.eyesana.utils.setLanguage
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val storage: Prefs
) : ViewModel() {

    fun getAllThemes() = Themes.entries.toList()
    fun selectedTheme() = Themes.entries[storage.getAppTheme()]
    fun setTheme(option: Themes) {
        storage.saveAppTheme(option)
        SystemConfig.updateTheme(option)
    }

    fun getAllLanguages() = Languages.entries.toList()
    fun selectedLanguage() = storage.getAppLang()
    fun setLanguage(option: Languages) {
        context.resources.setLanguage(option)
        storage.saveAppLanguage(option)
        SystemConfig.updateLanguage(option)
    }
}