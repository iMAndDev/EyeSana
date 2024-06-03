package ua.nure.maksymburym.eyesana.ui.exercises.base

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ua.nure.maksymburym.eyesana.ui.base.BaseComposeActivity
import ua.nure.maksymburym.eyesana.ui.base.kit.TopBar
import ua.nure.maksymburym.eyesana.ui.resources.getColorScheme

abstract class BaseExerciseActivity : BaseComposeActivity() {

    abstract val exerciseTitle: String
    protected open val isStartNavIconVisible: Boolean = true

    @Composable
    abstract fun SetExercise()

    @Composable
    override fun SetContent() {
        Column {
            TopBar(
                title = null,
                isStartNavIconVisible = isStartNavIconVisible,
                onStartClickListener = { finish() }
            )
            Text(
                text = exerciseTitle,
                color = getColorScheme().primary,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            )
            SetExercise()
        }
    }
}