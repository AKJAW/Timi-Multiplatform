package com.akjaw.task.api.data

import com.akjaw.task.api.domain.Task
import kotlinx.coroutines.flow.Flow

//TODO split to usecases?
interface TaskRepository {

    val tasks: Flow<List<Task>>

    fun addTask(taskToBeAdded: Task)

    fun deleteTasks(tasksToBeDeleted: List<Task>)
}
