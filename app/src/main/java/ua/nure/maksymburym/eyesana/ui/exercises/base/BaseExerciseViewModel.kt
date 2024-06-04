package ua.nure.maksymburym.eyesana.ui.exercises.base

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
import ua.nure.maksymburym.eyesana.utils.Timer
import ua.nure.maksymburym.eyesana.utils.formatTime
import javax.inject.Inject

@HiltViewModel
open class BaseExerciseViewModel @Inject constructor() : ViewModel() {

    private val _timerDataFlow = MutableStateFlow(
        TimerModel(timeUntilFinished = "", state = TimerState.IDLE)
    )
    val timerDataFlow = _timerDataFlow.asStateFlow()

    fun setupExercise(minutes: Int) {
        val millisTotal: Long = minutes * 60L * 1000L
        updateTimerData {
            millisUntilFinished = millisTotal
            timeUntilFinished = millisTotal.formatTime()
            state = TimerState.RUNNING
        }
    }

    fun launchExercise() {
        viewModelScope.launch(Dispatchers.IO) {
            // Wait for animation
            delay(500L)
            withContext(Dispatchers.Main) {
                val timer = Timer.getInstance(
                    millisTotal = _timerDataFlow.value.millisUntilFinished,
                    onTick = { formattedTimeLeft: String ->
                        updateTimerData {
                            timeUntilFinished = formattedTimeLeft
                            state = TimerState.RUNNING
                        }
                    },
                    onFinish = {
                        updateTimerData {
                            state = TimerState.FINISHED
                        }
                    }
                )
                timer.start()
            }
        }
    }

    private fun updateTimerData(flowValue: TimerModel.() -> Unit) {
        _timerDataFlow.value = _timerDataFlow.value.copy().also { it.flowValue() }
    }
}