package co.touchlab.kampkit.timer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TimerViewModel internal constructor(
    private val timer: Timer
) {

    val timeString = timer.timeString
    val isActive = MutableStateFlow(false)

    fun toggle() {
        val currentState = isActive.value
        isActive.update { it.not() }
        if (currentState) {
            timer.stop()
        } else {
            timer.start()
        }
    }
}
