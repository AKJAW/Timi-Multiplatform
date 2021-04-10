package com.akjaw.stopwatch.domain.utilities

import com.akjaw.core.common.domain.TimestampProvider
import com.akjaw.core.common.domain.model.TimestampMilliseconds
import com.akjaw.core.common.domain.model.toTimestampMilliseconds
import com.akjaw.stopwatch.domain.model.StopwatchState
import javax.inject.Inject

class ElapsedTimeCalculator @Inject constructor(
    private val timestampProvider: TimestampProvider,
) {

    fun calculate(state: StopwatchState.Running): TimestampMilliseconds {
        val currentTimestamp = timestampProvider.getMilliseconds()
        val timePassedSinceStart = if (currentTimestamp.value > state.startTime.value) {
            currentTimestamp - state.startTime
        } else {
            0.toTimestampMilliseconds()
        }
        return timePassedSinceStart + state.elapsedTime
    }
}
