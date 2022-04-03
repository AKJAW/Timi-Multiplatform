package com.akjaw.task.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akjaw.task.list.presentation.selection.TaskSelectionTracker
import com.akjaw.task.list.presentation.selection.TaskSelectionTrackerFactory
import com.akjaw.timi.kmp.core.shared.coroutines.DispatcherProvider
import com.akjaw.timi.kmp.feature.task.api.AddTask
import com.akjaw.timi.kmp.feature.task.api.DeleteTasks
import com.akjaw.timi.kmp.feature.task.api.GetTasks
import com.akjaw.timi.kmp.feature.task.api.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

internal class TaskListViewModel (
    private val getTasks: GetTasks,
    private val deleteTasks: DeleteTasks,
    private val addTask: AddTask,
    private val dispatcherProvider: DispatcherProvider,
    taskSelectionTrackerFactory: TaskSelectionTrackerFactory,
) : ViewModel() {

    private val selectionTracker: TaskSelectionTracker =
        taskSelectionTrackerFactory.create(originalTaskFlow = getTasks.execute())
    val tasks: Flow<List<Task>> = selectionTracker.tasksWithSelection

    fun toggleTask(toggledTask: Task) {
        selectionTracker.toggleTask(toggledTask)
    }

    fun deleteTasks(tasksToBeDeleted: List<Task>) = viewModelScope.launch(dispatcherProvider.io) {
        deleteTasks.execute(tasksToBeDeleted)
    }

    fun addTask(taskToBeAdded: Task) = viewModelScope.launch(dispatcherProvider.io) {
        addTask.execute(taskToBeAdded)
    }
}
