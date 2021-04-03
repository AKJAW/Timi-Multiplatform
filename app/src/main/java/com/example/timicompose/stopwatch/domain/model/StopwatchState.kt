package com.example.timicompose.stopwatch.domain.model

import com.example.timicompose.common.domain.model.TimestampMilliseconds

sealed class StopwatchState {
    data class Running(
        val startTime: TimestampMilliseconds,
        val elapsedTime: TimestampMilliseconds
    ) : StopwatchState()
    data class Paused(
        val elapsedTime: TimestampMilliseconds
    ) : StopwatchState()
    //TODO Stopped?
//        data class Stopped()
}
