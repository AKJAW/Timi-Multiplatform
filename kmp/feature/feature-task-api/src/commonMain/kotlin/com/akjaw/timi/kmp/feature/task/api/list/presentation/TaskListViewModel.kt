package com.akjaw.timi.kmp.feature.task.api.list.presentation

import com.akjaw.timi.kmp.feature.task.api.list.domain.model.Task
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface TaskListViewModel {
    val tasks: Flow<List<Task>>
    fun toggleTask(toggledTask: Task)
    fun deleteTasks(tasksToBeDeleted: List<Task>): Job
    fun addTask(taskToBeAdded: Task): Job
}
