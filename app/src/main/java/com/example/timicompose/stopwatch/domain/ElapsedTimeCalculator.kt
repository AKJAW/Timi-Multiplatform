package com.example.timicompose.stopwatch.domain

import com.example.timicompose.common.domain.TimestampProvider
import com.example.timicompose.common.domain.model.TimestampMilliseconds
import com.example.timicompose.common.domain.model.toTimestampMilliseconds
import com.example.timicompose.stopwatch.domain.model.StopwatchState
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
