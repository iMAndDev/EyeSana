package ua.nure.maksymburym.eyesana.ui.exercises.base

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ua.nure.maksymburym.eyesana.R
import ua.nure.maksymburym.eyesana.domain.TimerModel
import ua.nure.maksymburym.eyesana.domain.TimerState
import ua.nure.maksymburym.eyesana.ui.base.BaseComposeActivity
import ua.nure.maksymburym.eyesana.ui.base.kit.AppButton
import ua.nure.maksymburym.eyesana.ui.base.kit.TopBar
import ua.nure.maksymburym.eyesana.ui.resources.getColorScheme

abstract class BaseExerciseActivity : BaseComposeActivity() {

    abstract override val viewModel: BaseExerciseViewModel

    @Composable
    abstract fun Exercise(modifier: Modifier)
    abstract val exerciseMinutes: Int
    abstract val titleRes: Int
    abstract val descriptionRes: Int

    @Composable
    override fun SetContent() {
        val screenState by viewModel.timerDataFlow.collectAsState()
        ExerciseScreen(screenState)
    }

    @Composable
    protected fun ExerciseScreen(model: TimerModel) {
        Column {
            TopBar(
                title = null,
                isStartNavIconVisible = true,
                onStartClickListener = { finish() },
                modifier = Modifier
                    .background(color = getColorScheme().secondaryContainer)
                    .systemBarsPadding()
                    .fillMaxWidth()
            )
            ConstraintLayout(
                modifier = Modifier
                    .background(color = getColorScheme().secondaryContainer)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .weight(1f)
            ) {
                val (title, description, timerText, animation, btnDone) = createRefs()

                var transitionFinished by remember { mutableStateOf(false) }
                val alphaAnim: Float by animateFloatAsState(
                    targetValue = if (model.state == TimerState.IDLE) 1f else 0f,
                    animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
                    label = "AlphaAnim",
                    finishedListener = {
                        transitionFinished = true
                    }
                )
                val secondAlphaAnim: Float by animateFloatAsState(
                    targetValue = if (transitionFinished) 1f else 0f,
                    animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
                    label = "SecondAlphaAnim",
                    finishedListener = {
                        viewModel.launchExercise()
                    }
                )

                // Title
                Text(
                    text = stringResource(id = titleRes),
                    color = getColorScheme().primary,
                    textAlign = TextAlign.Center,
                    fontSize = 21.sp,
                    modifier = Modifier
                        .alpha(alphaAnim)
                        .padding(top = 30.dp)
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                            width = Dimension.matchParent
                        }
                )
                // Description
                Text(
                    text = stringResource(id = descriptionRes),
                    color = getColorScheme().secondary,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .alpha(alphaAnim)
                        .padding(top = 16.dp)
                        .constrainAs(description) {
                            top.linkTo(title.bottom)
                            width = Dimension.matchParent
                        }
                )
                // Time left
                Text(
                    text = model.timeUntilFinished,
                    color = getColorScheme().primary,
                    textAlign = TextAlign.Center,
                    fontSize = 21.sp,
                    modifier = Modifier
                        .alpha(secondAlphaAnim)
                        .padding(top = 40.dp)
                        .constrainAs(timerText) {
                            top.linkTo(parent.top)
                            width = Dimension.matchParent
                        }
                )

                Exercise(
                    modifier = Modifier.constrainAs(animation) {
                        top.linkTo(description.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.wrapContent
                    }
                )

                AppButton(
                    name = stringResource(id = R.string.start),
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .constrainAs(btnDone) {
                            bottom.linkTo(parent.bottom)
                            width = Dimension.matchParent
                        }
                ) {
                    viewModel.setupExercise(minutes = exerciseMinutes)
                }
            }
        }
    }
}