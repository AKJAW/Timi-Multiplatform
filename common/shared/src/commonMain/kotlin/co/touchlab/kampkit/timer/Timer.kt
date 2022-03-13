package co.touchlab.kampkit.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

internal class Timer(
    private val scope: CoroutineScope
) {

    private var totalTimeElapsed = MutableStateFlow<Long>(0)
    val timeString: StateFlow<String> = totalTimeElapsed
        .map(::formatToText)
        .stateIn(scope, SharingStarted.Eagerly, "00:00")

    fun start() {
        scope.launch {
            while (isActive) {
                delay(1000)
                totalTimeElapsed.update { it + 1000 }
            }
        }
    }

    fun stop() {
        scope.coroutineContext.cancelChildren()
    }

    private fun formatToText(time: Long): String {
        val seconds = time / 1000
        val minutes = seconds / 60
        val secondsText = (seconds % 60).padWithZero()
        return "${minutes.padWithZero()}:$secondsText"
    }
    fun Number.padWithZero() = this.toString().padStart(2, '0')
}
