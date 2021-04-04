package com.example.timicompose.stopwatch.presentation

import androidx.lifecycle.ViewModel
import com.example.timicompose.stopwatch.domain.StopwatchListOrchestrator
import com.example.timicompose.tasks.data.TaskRepository
import com.example.timicompose.tasks.presentation.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combineTransform
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val stopwatchListOrchestrator: StopwatchListOrchestrator,
) : ViewModel() {

    val stopwatches: StateFlow<Map<Task, String>> = stopwatchListOrchestrator.ticker

    val availableTasks: Flow<List<Task>> = combineTransform(
        MutableStateFlow(taskRepository.tasks),
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

    // TODO is this correct?
    override fun onCleared() {
        super.onCleared()
        stopwatchListOrchestrator.destroy()
    }
}
