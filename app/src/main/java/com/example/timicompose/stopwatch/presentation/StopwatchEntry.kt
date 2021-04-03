package com.example.timicompose.stopwatch.presentation

import com.example.timicompose.tasks.presentation.model.Task

data class StopwatchEntry(
    val task: Task,
    val timeString: String = "00:00",
)
