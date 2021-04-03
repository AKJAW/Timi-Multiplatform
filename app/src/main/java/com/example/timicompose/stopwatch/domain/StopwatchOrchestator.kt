package com.example.timicompose.stopwatch.domain

import com.example.timicompose.tasks.presentation.model.Task
import com.example.timicompose.ui.theme.tasksPreview
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class StopwatchOrchestator @Inject constructor(
    private val stopwatchStateCalculator: StopwatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
) {

    //TODO does this have to be thread-safe?
    //TODO this is temp
    val ticker = MutableStateFlow<Map<Task, String>>(tasksPreview.map { it to "00:00" }.toMap())
    private var stopwatchStateHolders: MutableMap<Task, StopwatchStateHolder> = mutableMapOf()

    fun start(task: Task) {//TODO only the orchestrator should update the time
        val stopwatchForTask = stopwatchStateHolders.getOrPut(task) {
            StopwatchStateHolder(stopwatchStateCalculator, elapsedTimeCalculator)
        }
        stopwatchForTask.start { timerString ->
            val newTime = ticker.value.toMutableMap()
            newTime[task] = timerString
            ticker.value = newTime
        }
    }

    fun pause(task: Task) {
        val stopwatchForTask = stopwatchStateHolders.getOrPut(task) {
            StopwatchStateHolder(stopwatchStateCalculator, elapsedTimeCalculator)
        }
        stopwatchForTask.pause()
    }
}
