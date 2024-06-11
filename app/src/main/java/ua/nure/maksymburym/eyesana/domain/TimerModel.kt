package ua.nure.maksymburym.eyesana.domain

data class TimerModel(
    var millisUntilFinished: Long = 0L,
    var timeUntilFinished: Long = 0L,
    var state: TimerState = TimerState.IDLE,
)
