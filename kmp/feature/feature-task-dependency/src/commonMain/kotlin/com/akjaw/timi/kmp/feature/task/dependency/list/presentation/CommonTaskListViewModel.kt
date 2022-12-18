package com.akjaw.timi.kmp.feature.task.dependency.list.presentation

import com.akjaw.timi.kmp.core.shared.coroutines.DispatcherProvider
import com.akjaw.timi.kmp.feature.task.api.domain.AddTask
import com.akjaw.timi.kmp.feature.task.api.domain.DeleteTasks
import com.akjaw.timi.kmp.feature.task.api.domain.GetTasks
import com.akjaw.timi.kmp.feature.task.api.domain.model.Task
import com.akjaw.timi.kmp.feature.task.api.presentation.TaskListViewModel
import com.akjaw.timi.kmp.feature.task.dependency.list.presentation.selection.TaskSelectionTracker
import com.akjaw.timi.kmp.feature.task.dependency.list.presentation.selection.TaskSelectionTrackerFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

internal class CommonTaskListViewModel(
    private val getTasks: GetTasks,
    private val deleteTasks: DeleteTasks,
    private val addTask: AddTask,
    private val dispatcherProvider: DispatcherProvider,
    taskSelectionTrackerFactory: TaskSelectionTrackerFactory
) : TaskListViewModel {

    // TODO extract?
    private val viewModelScope = CoroutineScope(SupervisorJob() + dispatcherProvider.main)

    private val selectionTracker: TaskSelectionTracker =
        taskSelectionTrackerFactory.create(originalTaskFlow = getTasks.execute())
    override val tasks: Flow<List<Task>> = selectionTracker.tasksWithSelection

    override fun toggleTask(toggledTask: Task) {
        selectionTracker.toggleTask(toggledTask)
    }

    override fun deleteTasks(tasksToBeDeleted: List<Task>) =
        viewModelScope.launch(dispatcherProvider.io) {
            deleteTasks.execute(tasksToBeDeleted)
        }

    override fun addTask(taskToBeAdded: Task) = viewModelScope.launch(dispatcherProvider.io) {
        addTask.execute(taskToBeAdded)
    }
}
