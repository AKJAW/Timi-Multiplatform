package com.akjaw.stopwatch.domain

import com.akjaw.stopwatch.domain.model.StopwatchState
import com.akjaw.task.api.domain.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

internal class StopwatchListOrchestrator @Inject constructor(
    private val stopwatchStateHolderFactory: StopwatchStateHolderFactory,
    private val scope: CoroutineScope,
) {
    private var job: Job? = null
    private var stopwatchStateHolders = ConcurrentHashMap<Task, StopwatchStateHolder>()
    private val mutableTicker = MutableStateFlow<Map<Task, String>>(mapOf())
    val ticker: StateFlow<Map<Task, String>> = mutableTicker

    fun start(task: Task) {
        if (job == null) startJob()
        val stopwatchForTask = stopwatchStateHolders.getOrPut(task) {
            stopwatchStateHolderFactory.create()
        }
        stopwatchForTask.start()
    }

    private fun startJob() {
        job = scope.launch {
            while (isActive) {
                val newValues = stopwatchStateHolders
                    .toSortedMap(compareBy { task -> task.id })
                    .map { (task, stateHolder) ->
                        task to stateHolder.getStringTimeRepresentation()
                    }
                    .toMap()
                mutableTicker.value = newValues
                delay(20)
            }
        }
    }

    fun pause(task: Task) {
        val stopwatchForTask = stopwatchStateHolders[task] ?: return
        stopwatchForTask.pause()
        if (areAllStopwatchesPaused()) stopJob()
    }

    private fun areAllStopwatchesPaused(): Boolean =
        stopwatchStateHolders.values.all { stateHolder ->
            stateHolder.currentState is StopwatchState.Paused
        }

    fun stop(task: Task) {
        stopwatchStateHolders.remove(task)
        if (stopwatchStateHolders.isEmpty()) {
            stopJob()
            resetTicker()
        }
    }

    private fun stopJob() {
        scope.coroutineContext.cancelChildren()
        job = null
    }

    private fun resetTicker() {
        mutableTicker.value = mapOf()
    }
}
