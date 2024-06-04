package ua.nure.maksymburym.eyesana.ui.dashboard

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.nure.maksymburym.eyesana.ui.exercises.base.BaseExerciseActivity
import ua.nure.maksymburym.eyesana.ui.exercises.base.ExerciseType
import ua.nure.maksymburym.eyesana.ui.exercises.base.LeftRightExerciseActivity
import ua.nure.maksymburym.eyesana.ui.resources.getColorScheme

@Composable
inline fun DashboardScreen(
    modifier: Modifier = Modifier,
    crossinline startActivityCallback: (Class<out ComponentActivity>) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = getColorScheme().secondaryContainer)
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Exercises",
            color = getColorScheme().primary,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 32.dp)
        )
        LazyVerticalGrid(
            modifier = Modifier.padding(top = 18.dp),
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(top = 6.dp, bottom = 10.dp), // space for shadows
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(ExerciseType.entries) { type ->
                ExerciseItem(type, startActivityCallback)
            }
        }
    }
}

@Composable
inline fun ExerciseItem(
    type: ExerciseType,
    crossinline startActivityCallback: (Class<out ComponentActivity>) -> Unit = {}
) {
    val destination: Class<out BaseExerciseActivity>? = when (type) {
        ExerciseType.LEFT_RIGHT -> LeftRightExerciseActivity::class.java
        ExerciseType.CLOSER_FURTHER -> null
        ExerciseType.CURTAINS -> null
        ExerciseType.EIGHT -> null
        ExerciseType.NIGHT -> null
    }

    Surface(
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        onClick = {
            destination?.let { startActivityCallback(it) }
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = getColorScheme().surface)
                .padding(all = 12.dp)
        ) {
            Text(
                text = stringResource(id = type.titleRes),
                color = getColorScheme().secondary,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            )
            Image(
                imageVector = Icons.Filled.PlayArrow,
                colorFilter = ColorFilter.tint(color = getColorScheme().secondary),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = getColorScheme().secondaryContainer,
                        shape = RoundedCornerShape(100)
                    )
                    .padding(all = 4.dp)
            )
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DashboardScreenPreview() {
    DashboardScreen()
}