package ua.nure.maksymburym.eyesana.ui.base.kit

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ua.nure.maksymburym.eyesana.ui.resources.getColorScheme

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    name: String,
    isEnable: Boolean = true,
    icon: @Composable (() -> Unit)? = null,
    buttonColor: Color = getColorScheme().primary,
    disabledButtonColor: Color = getColorScheme().onPrimary,
    textColor: Color = getColorScheme().surface,
    disabledTextColor: Color = getColorScheme().inverseSurface,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        enabled = isEnable,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            disabledContainerColor = disabledButtonColor,
            contentColor = textColor,
            disabledContentColor = disabledTextColor
        ),
        shape = RoundedCornerShape(size = 16.dp),
        modifier = modifier.zIndex(1f)
    ) {
        icon?.invoke()
        Text(
            text = name,
            modifier = Modifier
                .wrapContentSize()
                .padding(all = 6.dp)
        )
    }
}