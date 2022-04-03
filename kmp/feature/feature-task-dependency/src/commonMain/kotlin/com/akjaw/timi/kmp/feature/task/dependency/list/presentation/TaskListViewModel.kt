package com.akjaw.timi.kmp.feature.task.dependency.list.presentation

import com.akjaw.timi.kmp.core.shared.coroutines.DispatcherProvider
import com.akjaw.timi.kmp.feature.task.api.AddTask
import com.akjaw.timi.kmp.feature.task.api.DeleteTasks
import com.akjaw.timi.kmp.feature.task.api.GetTasks
import com.akjaw.timi.kmp.feature.task.api.model.Task
import com.akjaw.timi.kmp.feature.task.dependency.list.presentation.selection.TaskSelectionTracker
import com.akjaw.timi.kmp.feature.task.dependency.list.presentation.selection.TaskSelectionTrackerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// TODO make internal
class TaskListViewModel(
    private val getTasks: GetTasks,
    private val deleteTasks: DeleteTasks,
    private val addTask: AddTask,
    private val dispatcherProvider: DispatcherProvider,
    taskSelectionTrackerFactory: TaskSelectionTrackerFactory,
) {

    // TODO extract?
    private val viewModelScope = CoroutineScope(SupervisorJob() + dispatcherProvider.main)

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
