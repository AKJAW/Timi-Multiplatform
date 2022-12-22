package com.akjaw.timi.kmp.feature.task.api.presentation

import com.akjaw.timi.kmp.feature.task.api.domain.model.Task
import com.rickclephas.kmm.viewmodel.KMMViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

abstract class TaskListViewModel : KMMViewModel() {

    abstract val tasks: StateFlow<List<Task>>
    abstract fun toggleTask(toggledTask: Task)
    abstract fun deleteTasks(tasksToBeDeleted: List<Task>): Job
    abstract fun addTask(taskToBeAdded: Task): Job
}
