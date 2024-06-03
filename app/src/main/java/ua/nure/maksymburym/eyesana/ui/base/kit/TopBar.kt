package ua.nure.maksymburym.eyesana.ui.base.kit

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.nure.maksymburym.eyesana.ui.resources.getColorScheme
import ua.nure.maksymburym.eyesana.ui.resources.composableStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String? = null,
    backgroundColor: Color = getColorScheme().secondaryContainer,
    isStartNavIconVisible: Boolean = true,
    onStartClickListener: () -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = getColorScheme().primary,
        ),
        title = {
            Text(
                text = title ?: "",
                modifier = Modifier.padding(start = if (isStartNavIconVisible) 26.dp else 0.dp),
                fontSize = 16.sp
            )
        },
        navigationIcon = {
            if (isStartNavIconVisible.not()) return@TopAppBar
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(24.dp)
                    .padding(2.dp)
                    .clickable {
                        onStartClickListener()
                    },
                tint = getColorScheme().primary
            )
        }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun TopBarPreview() {
    TopBar(title = composableStrings().appName)
}