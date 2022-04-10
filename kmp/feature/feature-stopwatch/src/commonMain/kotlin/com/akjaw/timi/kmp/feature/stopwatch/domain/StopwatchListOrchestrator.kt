package com.akjaw.timi.kmp.feature.stopwatch.domain

import com.akjaw.timi.kmp.feature.stopwatch.domain.model.StopwatchState
import com.akjaw.timi.kmp.feature.task.api.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

internal class StopwatchListOrchestrator(
    private val stopwatchStateHolderFactory: StopwatchStateHolderFactory,
    private val scope: CoroutineScope,
) {
    private var job: Job? = null
    // TODO was ConcurrentHashMap
    // TODO extract this to a separate class?
    private var stopwatchStateHolders = mutableMapOf<Task, StopwatchStateHolder>()
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
                stopwatchStateHolders
                val newValues = stopwatchStateHolders
                    // TODO bring back sorting / order the items
//                    .toMap()
//                    .toSortedMap(compareBy { task -> task.id })
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
