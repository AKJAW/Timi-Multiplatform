package com.example.timicompose.stopwatch.domain

import com.example.timicompose.common.composition.DispatcherQualifiers
import com.example.timicompose.stopwatch.domain.model.StopwatchState
import com.example.timicompose.tasks.presentation.model.Task
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class StopwatchListOrchestrator @Inject constructor(
    private val stopwatchStateHolderFactory: StopwatchStateHolderFactory,
    @DispatcherQualifiers private val scope: CoroutineScope,
) {

    private var job: Job? = null
    private var stopwatchStateHolders: MutableMap<Task, StopwatchStateHolder> = mutableMapOf()
    val ticker = MutableStateFlow<Map<Task, String>>(mapOf())

    fun start(task: Task) {
        if (job == null) startJob()
        val stopwatchForTask = stopwatchStateHolders.getOrPut(task) {
            stopwatchStateHolderFactory.create()
        }
        stopwatchForTask.start()
    }

    private fun startJob() {
        scope.launch {
            while (true) {
                val newValues = stopwatchStateHolders.map { (task, stateHolder) ->
                    //TODO should only be called when changed?
                    task to stateHolder.getStringTimeRepresentation()
                }.toMap()
                ticker.value = newValues
                delay(20)
            }
        }
    }

    fun pause(task: Task) {
        val stopwatchForTask = stopwatchStateHolders.getOrPut(task) {
            stopwatchStateHolderFactory.create()
        }
        stopwatchForTask.pause()
        val areAllStopwatchesPaused = stopwatchStateHolders.values.all { stateHolder ->
            stateHolder.currentState is StopwatchState.Paused
        }
        if (areAllStopwatchesPaused) stopJob()
    }

    fun stop(task: Task) {
        stopwatchStateHolders.remove(task)
        if (stopwatchStateHolders.isEmpty()) {
            stopJob()
            clearValues()
        }
    }

    //TODO this probably should be preserved on config changes, right...?
    fun destroy() {
        stopJob()
        clearValues()
    }

    private fun stopJob() {
        scope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearValues() {
        stopwatchStateHolders.clear()
        ticker.value = mapOf()
    }
}
