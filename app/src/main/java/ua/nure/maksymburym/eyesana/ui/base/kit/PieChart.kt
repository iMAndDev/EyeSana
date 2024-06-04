package ua.nure.maksymburym.eyesana.ui.base.kit

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.nure.maksymburym.eyesana.domain.ChartModel
import ua.nure.maksymburym.eyesana.ui.resources.getAppColors
import ua.nure.maksymburym.eyesana.ui.resources.getColorScheme

@Composable
fun ChartCirclePie(
    modifier: Modifier = Modifier,
    model: ChartModel,
    radius: Int = 200,
    fSize: Size = Size(radius * 2f, radius * 2f),
    startAngle: Float = 0f,
    strokeWidth: Dp = 16.dp
) {

    // Chart animation
    val animateFloat = remember { Animatable(0f) }
    val targetValue = (model.valuePercent / 100f)
    LaunchedEffect(animateFloat) {
        animateFloat.animateTo(
            targetValue = targetValue,
            animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
        )
    }

    /* Text props */
    val textColor = getColorScheme().primary
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(text = AnnotatedString(model.centerText))
    val textSize = textLayoutResult.size
    /* end */

    val chartColor = when (model.valuePercent) {
        in 90f..100f -> getAppColors().mint
        in 60f..89f -> getAppColors().canaryYellow
        else -> getAppColors().pastelRed
    }
    Canvas(modifier = modifier) {
        // Drawing the chart
        val targetAngle = (animateFloat.value * startAngle)
        val chartCenter = Offset(
            (size.width - radius * 2f) / 2f,
            (size.height - radius * 2f) / 2f
        )
        drawArc(
            color = chartColor,
            startAngle = startAngle,
            sweepAngle = targetAngle,
            useCenter = false,
            topLeft = chartCenter,
            size = fSize,
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )

        // Drawing the text
        drawText(
            textMeasurer = textMeasurer,
            text = model.centerText,
            style = TextStyle(
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            topLeft = Offset(
                (this.size.width - textSize.width) / 2f,
                (this.size.height - textSize.height) / 2f
            ),
        )
    }
}