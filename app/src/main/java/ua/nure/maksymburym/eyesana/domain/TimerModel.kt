package ua.nure.maksymburym.eyesana.domain

data class TimerModel(
    var millisUntilFinished: Long = 0L,
    var timeUntilFinished: String = "",
    var state: TimerState = TimerState.IDLE,
)
