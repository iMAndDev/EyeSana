package ua.nure.maksymburym.eyesana.ui.exercises

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kotlinx.coroutines.delay
import ua.nure.maksymburym.eyesana.R
import ua.nure.maksymburym.eyesana.ui.base.kit.AppButton
import ua.nure.maksymburym.eyesana.ui.base.kit.Particles
import ua.nure.maksymburym.eyesana.ui.resources.getColorScheme
import ua.nure.maksymburym.eyesana.utils.getStringRes

@Composable
inline fun ExerciseSuccessScreen(
    crossinline closeScreenCallback: () -> Unit
) {
    var showParticles by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(500)
        showParticles = true
    }

    ConstraintLayout(
        modifier = Modifier
            .background(color = getColorScheme().secondaryContainer)
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        val (title, likeEmoji, anim, btnContinue) = createRefs()
        Text(
            modifier = Modifier
                .padding(top = 100.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    width = Dimension.matchParent
                },
            text = getStringRes(id = R.string.success_cap),
            color = getColorScheme().primary,
            textAlign = TextAlign.Center,
            fontSize = 21.sp
        )
        Text(
            modifier = Modifier
                .constrainAs(likeEmoji) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            text = "\uD83D\uDC4D", // thumbs up 👍
            color = getColorScheme().primary,
            textAlign = TextAlign.Center,
            fontSize = 80.sp
        )
        Particles(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .constrainAs(anim) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            quantity = 24,
            emoji = "\uD83D\uDD25", // fire emoji 🔥
            visible = showParticles
        )

        AppButton(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .constrainAs(btnContinue) {
                    bottom.linkTo(parent.bottom)
                    width = Dimension.matchParent
                },
            name = getStringRes(id = R.string.common_continue),
        ) {
            closeScreenCallback()
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExerciseSuccessScreenPreview() {
    ExerciseSuccessScreen(closeScreenCallback = { /* preview, do nothing */ })
}