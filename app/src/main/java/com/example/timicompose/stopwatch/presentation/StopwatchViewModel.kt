package com.example.timicompose.stopwatch.presentation

import androidx.lifecycle.ViewModel
import com.example.timicompose.stopwatch.domain.StopwatchListOrchestrator
import com.example.timicompose.tasks.presentation.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StopwatchViewModel @Inject constructor(
     private val stopwatchListOrchestrator: StopwatchListOrchestrator,
) : ViewModel() {
    //TODO better data structure?
    val stopwatches: StateFlow<Map<Task, String>> = stopwatchListOrchestrator.ticker

    fun start(task: Task) = stopwatchListOrchestrator.start(task)

    fun pause(task: Task) = stopwatchListOrchestrator.pause(task)

    fun stop(task: Task) = stopwatchListOrchestrator.stop(task)

    // TODO is this correct?
    override fun onCleared() {
        super.onCleared()
        stopwatchListOrchestrator.destroy()
    }
}
