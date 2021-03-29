package com.example.timicompose.stopwatch.presentation

import com.example.timicompose.tasks.presentation.model.Task
import com.soywiz.klock.DateTime

data class StopwatchEntry(
    val task: Task,
    val startTimestampMilliseconds: Long = DateTime.nowUnixLong(),
    val timeString: String = "00:00",
)
