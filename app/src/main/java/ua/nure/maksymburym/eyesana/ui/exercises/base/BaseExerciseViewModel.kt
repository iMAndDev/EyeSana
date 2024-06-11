package ua.nure.maksymburym.eyesana.ui.exercises.base

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.nure.maksymburym.eyesana.domain.TimerModel
import ua.nure.maksymburym.eyesana.domain.TimerState
import ua.nure.maksymburym.eyesana.storage.Prefs
import ua.nure.maksymburym.eyesana.storage.PrefsKeys.EYE_ACUITY
import ua.nure.maksymburym.eyesana.utils.Timer
import javax.inject.Inject

@HiltViewModel
open class BaseExerciseViewModel @Inject constructor(
    private val prefs: Prefs
) : ViewModel() {

    private var _timer: CountDownTimer? = null
    private val _timerDataFlow = MutableStateFlow(TimerModel(state = TimerState.IDLE))
    val timerDataFlow = _timerDataFlow.asStateFlow()

    private val _finishExerciseFlow = MutableStateFlow<Unit?>(null)
    val finishExerciseFlow = _finishExerciseFlow.asStateFlow()

    fun setupExercise(minutes: Int) {
        val millisTotal: Long = minutes * 60L * 1000L
        updateTimerData {
            millisUntilFinished = millisTotal
            timeUntilFinished = millisTotal
            state = TimerState.RUNNING
        }
    }

    fun launchExercise() {
        viewModelScope.launch(Dispatchers.IO) {
            // Wait for animation
            delay(500L)
            withContext(Dispatchers.Main) {
                _timer = Timer.getInstance(
                    millisTotal = _timerDataFlow.value.millisUntilFinished,
                    onTick = { timeLeft: Long ->
                        updateTimerData {
                            timeUntilFinished = timeLeft
                            state = TimerState.RUNNING
                        }
                    },
                    onFinish = {
                        onExerciseFinished()
                    }
                )
                _timer?.start()
            }
        }
    }

    private fun updateTimerData(flowValue: TimerModel.() -> Unit) {
        _timerDataFlow.value = _timerDataFlow.value.copy().also { it.flowValue() }
    }

    private fun onExerciseFinished() {
        viewModelScope.launch(Dispatchers.IO) {
            updateTimerData {
                state = TimerState.FINISHED
            }
            with(prefs) {
                var rate = getFloat(EYE_ACUITY, 67f)
                rate += 10f
                if (rate > 100f) rate = 100f
                saveValue(EYE_ACUITY, rate)
            }
            _finishExerciseFlow.emit(Unit)
        }
    }

    fun proceedExerciseFinish() {
        _finishExerciseFlow.value = null
    }

    fun pauseExercise() = _timer?.cancel()
    fun continueExercise() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _timer = Timer.getInstance(
                    // Set 'timeUntilFinished' as initial value
                    millisTotal = _timerDataFlow.value.timeUntilFinished,
                    onTick = { timeLeft: Long ->
                        updateTimerData {
                            timeUntilFinished = timeLeft
                            state = TimerState.RUNNING
                        }
                    },
                    onFinish = {
                        onExerciseFinished()
                    }
                )
                _timer?.start()
            }
        }
    }
}