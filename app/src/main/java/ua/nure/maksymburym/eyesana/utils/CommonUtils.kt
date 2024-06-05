package ua.nure.maksymburym.eyesana.utils

import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import ua.nure.maksymburym.eyesana.app.CommonModule
import ua.nure.maksymburym.eyesana.domain.Languages
import ua.nure.maksymburym.eyesana.ui.resources.Colors
import java.util.Locale

/* Application */

@Composable
fun getStringRes(@StringRes id: Int): String {
    val systemConfig by SystemConfig.systemConfig.collectAsState()
    return systemConfig.resources.getString(id)
}

@SuppressWarnings("Deprecated")
fun Resources.setLanguage(language: Languages) {
    val langLocale = Locale(language.locale.uppercase())
    val configuration = this.configuration
    configuration.setLocale(langLocale)
    this.updateConfiguration(configuration, displayMetrics)
}

/* end */

/* JSON */

inline fun <reified T> T.toJson(serializer: KSerializer<T>? = null): String {
    serializer ?: return CommonModule.json.encodeToString(this)
    return CommonModule.json.encodeToString(serializer, this)
}

inline fun <reified T> String.fromJson(serializer: KSerializer<T>? = null): T {
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

/* Modifier utils */
@Composable
fun Modifier.clickWithRipple(
    cornerRadius: Dp = 0.dp,
    onLongClick: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
): Modifier = composed {
    val shape = RoundedCornerShape(cornerRadius)
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val viewConfiguration = LocalViewConfiguration.current

    LaunchedEffect(interactionSource) {
        var isLongClick = false

        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    isLongClick = false
                    delay(viewConfiguration.longPressTimeoutMillis)
                    isLongClick = true
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongClick?.invoke()
                }

                is PressInteraction.Release -> if (isLongClick.not()) onClick?.invoke()
            }
        }
    }

    this
        .clip(shape)
        .clickable(
            interactionSource = interactionSource,
            indication = rememberRipple(color = Colors.ripple.copy(alpha = 1f)),
            role = Role.Button,
            onClick = {}
        )

}
/* end */