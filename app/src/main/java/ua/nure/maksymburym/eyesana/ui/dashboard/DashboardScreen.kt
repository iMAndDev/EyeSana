package ua.nure.maksymburym.eyesana.ui.dashboard

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ua.nure.maksymburym.eyesana.ui.resources.getColorScheme

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Dashboard",
            color = getColorScheme().primary,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
        )
        Button(onClick = onButtonClick) {
            Text(
                text = "Test",
                color = getColorScheme().primary,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun DashboardScreenPreview() {
    DashboardScreen()
}