package com.akjaw.timi.kmp.feature.stopwatch.presentation

import com.akjaw.timi.kmp.feature.stopwatch.domain.StopwatchListOrchestrator
import com.akjaw.timi.kmp.feature.task.domain.GetTasks
import com.akjaw.timi.kmp.feature.task.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combineTransform

class StopwatchViewModel internal constructor(
    private val getTasks: GetTasks,
    private val stopwatchListOrchestrator: StopwatchListOrchestrator,
) {

    val stopwatches: StateFlow<Map<Task, String>> = stopwatchListOrchestrator.ticker

    val availableTasks: Flow<List<Task>> = combineTransform(
        getTasks.execute(),
        stopwatches
    ) { tasks, stopwatches ->
        val availableTasks = tasks.filterNot { task ->
            stopwatches.containsKey(task)
        }
        emit(availableTasks)
    }

    fun start(task: Task) = stopwatchListOrchestrator.start(task)

    fun pause(task: Task) = stopwatchListOrchestrator.pause(task)

    fun stop(task: Task) = stopwatchListOrchestrator.stop(task)
}
