package com.akjaw.task.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akjaw.core.common.composition.IoDispatcherQualifier
import com.akjaw.task.api.data.AddTask
import com.akjaw.task.api.data.DeleteTasks
import com.akjaw.task.api.data.GetTasks
import com.akjaw.task.api.domain.Task
import com.akjaw.task.list.presentation.selection.TaskSelectionTracker
import com.akjaw.task.list.presentation.selection.TaskSelectionTrackerFactory
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
    @IoDispatcherQualifier private val dispatcher: CoroutineDispatcher,
    taskSelectionTrackerFactory: TaskSelectionTrackerFactory,
) : ViewModel() {

    private val selectionTracker: TaskSelectionTracker =
        taskSelectionTrackerFactory.create(getTasks.execute())
    val tasks: Flow<List<Task>> = selectionTracker.taskWithSelection

    fun toggleTask(toggledTask: Task) {
        selectionTracker.toggleTask(toggledTask)
    }

    fun deleteTasks(tasksToBeDeleted: List<Task>) = viewModelScope.launch(dispatcher) {
        deleteTasks.execute(tasksToBeDeleted)
    }

    fun addTask(taskToBeAdded: Task) = viewModelScope.launch(dispatcher) {
        addTask.execute(taskToBeAdded)
    }
}
