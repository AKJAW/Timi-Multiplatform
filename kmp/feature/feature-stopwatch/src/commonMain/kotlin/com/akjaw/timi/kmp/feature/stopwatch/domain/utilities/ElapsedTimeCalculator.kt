package com.akjaw.timi.kmp.feature.stopwatch.domain.utilities

import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import com.akjaw.timi.kmp.core.shared.time.model.TimestampMilliseconds
import com.akjaw.timi.kmp.core.shared.time.model.toTimestampMilliseconds
import com.akjaw.timi.kmp.feature.stopwatch.domain.model.StopwatchState

internal class ElapsedTimeCalculator(
    private val timestampProvider: TimestampProvider
) {

    // TODO pass in the timestamp?
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
