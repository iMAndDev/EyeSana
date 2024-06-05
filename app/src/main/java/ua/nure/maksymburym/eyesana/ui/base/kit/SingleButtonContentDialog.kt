package ua.nure.maksymburym.eyesana.ui.base.kit

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.nure.maksymburym.eyesana.R
import ua.nure.maksymburym.eyesana.utils.getStringRes

@Composable
fun SingleButtonContentDialog(
    title: String,
    @StringRes buttonTextRes: Int = R.string.cancel,
    onDismissAction: () -> Unit = {},
    dialogVisibility: MutableState<Boolean>,
    content: @Composable () -> Unit,
) {
    val dismissRequest = {
        dialogVisibility.value = false
        onDismissAction()
    }

    if (dialogVisibility.value) {
        AlertDialog(
            onDismissRequest = dismissRequest,
            title = {
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(text = title)
                }
            },
            text = content,
            confirmButton = {
                TextButton(onClick = dismissRequest) {
                    Text(getStringRes(buttonTextRes))
                }
            },
            modifier = Modifier.padding(vertical = 5.dp)
        )
    }
}
