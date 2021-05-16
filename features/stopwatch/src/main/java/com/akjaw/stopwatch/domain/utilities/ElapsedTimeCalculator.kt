package com.akjaw.stopwatch.domain.utilities

import com.akjaw.core.common.domain.TimestampProvider
import com.akjaw.stopwatch.domain.model.StopwatchState
import javax.inject.Inject

internal class ElapsedTimeCalculator @Inject constructor(
    private val timestampProvider: TimestampProvider,
) {

    fun calculate(state: StopwatchState.Running): Long {
        val currentTimestamp = timestampProvider.getMilliseconds()
        val timePassedSinceStart = if (currentTimestamp > state.startTime) {
            currentTimestamp - state.startTime
        } else {
            0
        }
        return timePassedSinceStart + state.elapsedTime
    }
}
