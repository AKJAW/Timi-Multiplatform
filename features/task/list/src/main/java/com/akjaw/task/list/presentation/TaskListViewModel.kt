package com.akjaw.task.list.presentation

import androidx.lifecycle.ViewModel
import com.akjaw.task.api.data.TaskRepository
import com.akjaw.task.api.domain.Task
import com.akjaw.task.list.presentation.selection.TaskSelectionTracker
import com.akjaw.task.list.presentation.selection.TaskSelectionTrackerFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
internal class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    taskSelectionTrackerFactory: TaskSelectionTrackerFactory,
) : ViewModel() {

    private val selectionTracker: TaskSelectionTracker =
        taskSelectionTrackerFactory.create(taskRepository.tasks)
    val tasks: Flow<List<Task>> = selectionTracker.taskWithSelection

    fun toggleTask(toggledTask: Task) {
        selectionTracker.toggleTask(toggledTask)
    }

    fun deleteTasks(tasksToBeDeleted: List<Task>) {
        taskRepository.deleteTasks(tasksToBeDeleted)
    }

    fun addTask(taskToBeAdded: Task) {
        taskRepository.addTask(taskToBeAdded)
    }
}
