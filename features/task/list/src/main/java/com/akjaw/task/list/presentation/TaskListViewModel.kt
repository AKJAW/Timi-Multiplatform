package com.akjaw.task.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akjaw.timi.kmp.feature.task.domain.AddTask
import com.akjaw.timi.kmp.feature.task.domain.DeleteTasks
import com.akjaw.timi.kmp.feature.task.domain.GetTasks
import com.akjaw.timi.kmp.feature.task.domain.model.Task
import com.akjaw.task.list.presentation.selection.TaskSelectionTracker
import com.akjaw.task.list.presentation.selection.TaskSelectionTrackerFactory
import com.akjaw.timi.kmp.core.shared.coroutines.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class TaskListViewModel @Inject constructor(
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
