package ua.nure.maksymburym.eyesana.ui.resources

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import ua.nure.maksymburym.eyesana.domain.Themes
import ua.nure.maksymburym.eyesana.utils.SystemConfig

@Composable
fun EyeSanaTheme(
    isDynamicColor: Boolean = true, // Dynamic colors are available on A12+
    content: @Composable () -> Unit
) {
    val colorScheme = getColorScheme(isDynamicColor)
    val view = LocalView.current
    val systemConfig by SystemConfig.systemConfig.collectAsState()
    val currentTheme = systemConfig.appTheme
    val isDarkTheme =
        if (currentTheme == Themes.THEME_SYSTEM) isSystemInDarkTheme()
        else currentTheme == Themes.THEME_DARK

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun getColorScheme(
    isDynamicColor: Boolean = true,
): ColorScheme {
    val systemConfig by SystemConfig.systemConfig.collectAsState()
    val currentTheme = systemConfig.appTheme
    val isDarkTheme =
        if (currentTheme == Themes.THEME_SYSTEM) isSystemInDarkTheme()
        else currentTheme == Themes.THEME_DARK

    return when {
        isDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }

        else -> MaterialTheme.colorScheme
    }
}

fun getAppColors() = Colors
