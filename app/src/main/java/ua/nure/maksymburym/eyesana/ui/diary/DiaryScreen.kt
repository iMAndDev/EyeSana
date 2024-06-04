package ua.nure.maksymburym.eyesana.ui.diary

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import ua.nure.maksymburym.eyesana.ui.base.kit.ChartCirclePie
import ua.nure.maksymburym.eyesana.ui.resources.getColorScheme

@Composable
fun DiaryScreen(
    modifier: Modifier = Modifier,
    viewModel: DiaryViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsState()
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(color = getColorScheme().secondaryContainer)
            .padding(horizontal = 24.dp)
    ) {
        val (title, chart) = createRefs()
        Text(
            color = getColorScheme().primary,
            text = "Eye acuity",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(top = 30.dp)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    width = Dimension.matchParent
                }
        )

        ChartCirclePie(
            model = screenState,
            startAngle = 180f,
            modifier = Modifier
                .size(200.dp)
                .padding(top = 18.dp, end = 10.dp)
                .constrainAs(chart) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DiaryScreenPreview() {
    DiaryScreen()
}