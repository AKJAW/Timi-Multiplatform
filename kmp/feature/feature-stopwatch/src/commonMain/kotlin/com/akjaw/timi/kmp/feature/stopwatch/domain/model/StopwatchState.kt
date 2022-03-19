package com.akjaw.timi.kmp.feature.stopwatch.domain.model

import com.akjaw.core.common.domain.model.TimestampMilliseconds

sealed class StopwatchState {
    data class Running(
        val startTime: TimestampMilliseconds,
        val elapsedTime: TimestampMilliseconds
    ) : StopwatchState()
    data class Paused(
        val elapsedTime: TimestampMilliseconds
    ) : StopwatchState()
}
