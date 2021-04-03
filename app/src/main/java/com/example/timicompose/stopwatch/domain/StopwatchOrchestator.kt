package com.example.timicompose.stopwatch.domain

import com.example.timicompose.tasks.presentation.model.Task
import com.example.timicompose.ui.theme.tasksPreview
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class StopwatchOrchestator @Inject constructor(
    private val stopwatchStateHolderFactory: StopwatchStateHolderFactory,
) {

    private var job: Job? = null
    private var stopwatchStateHolders: MutableMap<Task, StopwatchStateHolder> =
        tasksPreview.map { it to stopwatchStateHolderFactory.create() }.toMap().toMutableMap() //TODO remove
    val ticker = MutableStateFlow<Map<Task, String>>(mapOf())

    init {
        job = GlobalScope.launch(Dispatchers.Default) {
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

    fun start(task: Task) {
        val stopwatchForTask = stopwatchStateHolders.getOrPut(task) {
            stopwatchStateHolderFactory.create()
        }
        stopwatchForTask.start()
    }

    fun pause(task: Task) {
        val stopwatchForTask = stopwatchStateHolders.getOrPut(task) {
            stopwatchStateHolderFactory.create()
        }
        stopwatchForTask.pause()
    }
}
