package com.akjaw.stopwatch.presentation

import androidx.lifecycle.ViewModel
import com.akjaw.stopwatch.domain.StopwatchListOrchestrator
import com.akjaw.task.api.data.TaskRepository
import com.akjaw.task.api.domain.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combineTransform
import javax.inject.Inject

@HiltViewModel
internal class StopwatchViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val stopwatchListOrchestrator: StopwatchListOrchestrator,
) : ViewModel() {

    val stopwatches: StateFlow<Map<Task, String>> = stopwatchListOrchestrator.ticker

    val availableTasks: Flow<List<Task>> = combineTransform(
        taskRepository.tasks,
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
