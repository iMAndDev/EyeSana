package ua.nure.maksymburym.eyesana.ui.diary

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.nure.maksymburym.eyesana.domain.ChartModel
import ua.nure.maksymburym.eyesana.storage.Prefs
import ua.nure.maksymburym.eyesana.storage.PrefsKeys.EYE_ACUITY
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val prefs: Prefs
): ViewModel() {

    private val eyeAcuityRate: Float = prefs.getFloat(EYE_ACUITY, default = 67f) // demonstrational
    private val _screenState = MutableStateFlow(
        ChartModel(
            valuePercent = eyeAcuityRate,
            centerText = getRateText()
        )
    )
    val screenState = _screenState.asStateFlow()

    private fun getRateText(): String {
        return when (eyeAcuityRate) {
            in 90f..100f -> "A"
            in 80f..89f -> "B"
            in 60f..79f -> "C"
            else -> "D"
        }
    }
}