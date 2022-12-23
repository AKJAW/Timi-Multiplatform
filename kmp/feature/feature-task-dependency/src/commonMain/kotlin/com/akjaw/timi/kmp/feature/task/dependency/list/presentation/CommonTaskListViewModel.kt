package com.akjaw.timi.kmp.feature.task.dependency.list.presentation

import com.akjaw.timi.kmp.core.shared.coroutines.DispatcherProvider
import com.akjaw.timi.kmp.feature.task.api.domain.AddTask
import com.akjaw.timi.kmp.feature.task.api.domain.DeleteTasks
import com.akjaw.timi.kmp.feature.task.api.domain.GetTasks
import com.akjaw.timi.kmp.feature.task.api.domain.model.Task
import com.akjaw.timi.kmp.feature.task.api.presentation.TaskListViewModel
import com.akjaw.timi.kmp.feature.task.dependency.list.presentation.selection.TaskSelectionTracker
import com.akjaw.timi.kmp.feature.task.dependency.list.presentation.selection.TaskSelectionTrackerFactory
import com.rickclephas.kmm.viewmodel.ViewModelScope
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmm.viewmodel.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

internal class CommonTaskListViewModel(
    private val getTasks: GetTasks,
    private val deleteTasks: DeleteTasks,
    private val addTask: AddTask,
    private val dispatcherProvider: DispatcherProvider,
    taskSelectionTrackerFactory: TaskSelectionTrackerFactory
) : TaskListViewModel() {

    private val selectionTracker: TaskSelectionTracker = taskSelectionTrackerFactory.create(
        originalTaskFlow = getTasks.execute(),
    )
    override val tasks: StateFlow<List<Task>> = selectionTracker.tasksWithSelection
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    override fun toggleTask(toggledTask: Task) {
        selectionTracker.toggleTask(toggledTask)
    }

    override fun deleteTasks(tasksToBeDeleted: List<Task>) =
        viewModelScope.coroutineScope.launch(dispatcherProvider.io) {
            deleteTasks.execute(tasksToBeDeleted)
        }

    override fun addTask(taskToBeAdded: Task) = viewModelScope.coroutineScope.launch(dispatcherProvider.io) {
        addTask.execute(taskToBeAdded)
    }
}
