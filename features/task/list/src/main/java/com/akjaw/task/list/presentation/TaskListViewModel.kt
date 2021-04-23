package com.akjaw.task.list.presentation

import androidx.lifecycle.ViewModel
import com.akjaw.task.api.data.AddTask
import com.akjaw.task.api.data.DeleteTasks
import com.akjaw.task.api.data.GetTasks
import com.akjaw.task.api.domain.Task
import com.akjaw.task.list.presentation.selection.TaskSelectionTracker
import com.akjaw.task.list.presentation.selection.TaskSelectionTrackerFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
internal class TaskListViewModel @Inject constructor(
    private val getTasks: GetTasks,
    private val deleteTasks: DeleteTasks,
    private val addTask: AddTask,
    taskSelectionTrackerFactory: TaskSelectionTrackerFactory,
) : ViewModel() {

    private val selectionTracker: TaskSelectionTracker =
        taskSelectionTrackerFactory.create(getTasks.execute())
    val tasks: Flow<List<Task>> = selectionTracker.taskWithSelection

    fun toggleTask(toggledTask: Task) {
        selectionTracker.toggleTask(toggledTask)
    }

    fun deleteTasks(tasksToBeDeleted: List<Task>) {
        deleteTasks.execute(tasksToBeDeleted)
    }

    fun addTask(taskToBeAdded: Task) {
        addTask.execute(taskToBeAdded)
    }
}
