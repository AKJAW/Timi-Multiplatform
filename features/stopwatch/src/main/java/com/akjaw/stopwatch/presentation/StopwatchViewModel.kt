package com.akjaw.stopwatch.presentation

import androidx.lifecycle.ViewModel
import com.akjaw.stopwatch.domain.StopwatchListOrchestrator
import com.akjaw.timi.kmp.feature.task.domain.GetTasks
import com.akjaw.timi.kmp.feature.task.domain.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combineTransform
import javax.inject.Inject

@HiltViewModel
internal class StopwatchViewModel @Inject constructor(
    private val getTasks: GetTasks,
    private val stopwatchListOrchestrator: StopwatchListOrchestrator,
) : ViewModel() {

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
