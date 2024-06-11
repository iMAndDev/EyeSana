package ua.nure.maksymburym.eyesana.utils

import android.os.CountDownTimer

object Timer {

    const val TIMER_STEP = 1000L

    inline fun getInstance(
        millisTotal: Long,
        crossinline onTick: (Long) -> Unit,
        crossinline onFinish: () -> Unit
    ): CountDownTimer {
        return object : CountDownTimer(millisTotal, TIMER_STEP) {
            override fun onTick(millisUntilFinished: Long) {
                onTick(millisUntilFinished)
            }

            override fun onFinish() {
                onFinish()
            }
        }
    }
}