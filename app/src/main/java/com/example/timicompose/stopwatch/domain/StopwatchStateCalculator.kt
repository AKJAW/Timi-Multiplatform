package com.example.timicompose.stopwatch.domain

import com.example.timicompose.common.domain.TimestampProvider
import com.example.timicompose.stopwatch.domain.model.StopwatchState
import com.example.timicompose.stopwatch.domain.model.toTimestampMilliseconds
import javax.inject.Inject

class StopwatchStateCalculator @Inject constructor(
    private val timestampProvider: TimestampProvider
) {

    fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running =
        when (oldState) {
            is StopwatchState.Running -> oldState
            is StopwatchState.Paused -> {
                StopwatchState.Running(
                    startTime = timestampProvider.getMilliseconds(),
                    elapsedTime = oldState.elapsedTime
                )
            }
        }

    fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused =
        when (oldState) {
            is StopwatchState.Running -> {
                val currentTimestamp = timestampProvider.getMilliseconds()
                val timePassedSinceStart = if (currentTimestamp.value > oldState.startTime.value) {
                    currentTimestamp - oldState.startTime
                } else {
                    0.toTimestampMilliseconds()
                }
                StopwatchState.Paused(
                    elapsedTime = timePassedSinceStart + oldState.elapsedTime
                )
            }
            is StopwatchState.Paused -> oldState
        }
}
