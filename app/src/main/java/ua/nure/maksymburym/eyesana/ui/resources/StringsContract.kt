package ua.nure.maksymburym.eyesana.ui.resources

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import ua.nure.maksymburym.eyesana.app.App
import java.util.Locale

/* region "Strings definition" */
interface StringsContract {
    val appName: String
    val standWithUkraine: String
    val leftAndRight: String
}

private data object StringEng : StringsContract {
    override val appName = "EyeSana"
    override val standWithUkraine = "Stand with \uD83C\uDDFA\uD83C\uDDE6"
    override val leftAndRight = "Left and Right"
}

private data object StringUkr : StringsContract {
    override val appName = "EyeSana"
    override val standWithUkraine = "Підтримуй \uD83C\uDDFA\uD83C\uDDE6"
    override val leftAndRight = "Вліво та Вправо"
}
/* end */

/**
 * Accesses to app string resources. Used in composables
 */
@Composable
fun composableStrings(): StringsContract {
    val configuration = LocalConfiguration.current
    val currentLocale = getCurrentLocale(configuration)
    return when (currentLocale.displayLanguage) {
        LangCodes.UKRAINIAN.key -> StringUkr
        LangCodes.ENGLISH.key -> StringEng
        else -> StringEng
    }
}

/**
 * Accesses to app string resources. Used in common scenarios
 */
fun appStrings(): StringsContract {
    val configuration = App.getApplication().resources.configuration
    val currentLocale = getCurrentLocale(configuration)
    return when (currentLocale.displayLanguage) {
        LangCodes.UKRAINIAN.key -> StringUkr
        LangCodes.ENGLISH.key -> StringEng
        else -> StringEng
    }
}

private fun getCurrentLocale(configuration: Configuration): Locale {
    return ConfigurationCompat.getLocales(configuration)[0] ?: LocaleListCompat.getDefault()[0]!!
}

private enum class LangCodes(val key: String) {
    ENGLISH(key = "en"),
    UKRAINIAN(key = "ua");
}