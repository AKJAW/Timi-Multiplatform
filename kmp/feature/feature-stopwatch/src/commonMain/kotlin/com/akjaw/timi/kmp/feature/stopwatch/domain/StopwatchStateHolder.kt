package com.akjaw.timi.kmp.feature.stopwatch.domain

import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.akjaw.timi.kmp.feature.stopwatch.domain.model.StopwatchState
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.ElapsedTimeCalculator
import com.akjaw.timi.kmp.feature.stopwatch.domain.utilities.TimestampMillisecondsFormatter

internal class StopwatchStateHolder(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timestampMillisecondsFormatter: TimestampMillisecondsFormatter
) {

    // TODO does this have to be thread-safe?
    var currentState: StopwatchState = StopwatchState.Paused(0.toTimestampMilliseconds())
        private set

    fun start() {
        currentState = stopwatchStateCalculator.calculateRunningState(currentState)
    }

    fun pause() {
        currentState = stopwatchStateCalculator.calculatePausedState(currentState)
    }

    // TODO move this out?
    //  it uses elapsedTimeCalculator which is also used in the StopwatchStateCalculator
    fun getStringTimeRepresentation(): String {
        val elapsedTime = when (val currentState = currentState) {
            is StopwatchState.Paused -> currentState.elapsedTime
            is StopwatchState.Running -> elapsedTimeCalculator.calculate(currentState)
        }
        return timestampMillisecondsFormatter.format(elapsedTime)
    }
}
