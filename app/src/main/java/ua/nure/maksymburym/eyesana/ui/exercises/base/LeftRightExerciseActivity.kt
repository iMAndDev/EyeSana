package ua.nure.maksymburym.eyesana.ui.exercises.base

import android.content.res.Configuration
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import ua.nure.maksymburym.eyesana.R
import ua.nure.maksymburym.eyesana.domain.TimerModel
import ua.nure.maksymburym.eyesana.domain.TimerState
import ua.nure.maksymburym.eyesana.ui.resources.getColorScheme

@AndroidEntryPoint
class LeftRightExerciseActivity : BaseExerciseActivity() {

    override val viewModel: BaseExerciseViewModel by viewModels()
    override val exerciseMinutes = 1
    @StringRes
    override val titleRes = R.string.left_and_right
    @StringRes
    override val descriptionRes = R.string.left_and_right_desc

    @Composable
    override fun Exercise(modifier: Modifier) {

        var startFromLeft by remember { mutableStateOf(true) }
        var isVisible by remember { mutableStateOf(false) }

        val infiniteTransition = rememberInfiniteTransition(label = "InfiniteTransition")
        val fadeOutAlpha by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, delayMillis = 3000),
                repeatMode = RepeatMode.Restart
            ),
            label = "FadeOutAlpha"
        )

        LaunchedEffect(Unit) {
            while (true) {
                isVisible = true
                delay(1000) // 1 second fade in
                isVisible = false
                delay(1000) // 1 second fade out
                startFromLeft = !startFromLeft // rotate the icon and start again
            }
        }
        AnimatedVisibility(
            modifier = modifier.padding(top = 120.dp),
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(1000))
                    + slideInHorizontally(
                        initialOffsetX = { if (startFromLeft) -it else it },
                        animationSpec = tween(1000)
                    ),
            exit = fadeOut(animationSpec = tween(1000))
        ) {
            Image(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = getColorScheme().tertiary),
                modifier = Modifier
                    .size(140.dp)
                    .graphicsLayer {
                        alpha = if (isVisible) 1f else fadeOutAlpha
                        rotationZ = if (startFromLeft) 0f else 180f
                    }
            )
        }
    }

    @Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    private fun ExercisePreview() {
        ExerciseScreen(model = TimerModel(state = TimerState.IDLE))
    }
}