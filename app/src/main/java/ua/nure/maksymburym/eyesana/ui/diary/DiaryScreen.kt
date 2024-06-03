package ua.nure.maksymburym.eyesana.ui.diary

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ua.nure.maksymburym.eyesana.ui.resources.getColorScheme

@Composable
fun DiaryScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            color = getColorScheme().primary,
            text = "Diary screen",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun DiaryScreenPreview() {
    DiaryScreen()
}