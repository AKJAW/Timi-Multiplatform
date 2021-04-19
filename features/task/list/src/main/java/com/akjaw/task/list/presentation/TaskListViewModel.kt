package com.akjaw.task.list.presentation

import androidx.lifecycle.ViewModel
import com.akjaw.task.api.data.TaskRepository
import com.akjaw.task.api.domain.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
internal class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {

    val tasks: Flow<List<Task>> = taskRepository.tasks

    fun toggleTask(toggledTask: Task) {
        //TODO
//        val newTasks = tasks.value.map { task ->
//            if (task == toggledTask) {
//                task.copy(isSelected = task.isSelected.not())
//            } else {
//                task
//            }
//        }
//        tasks.value = newTasks
    }

    fun deleteTasks(tasksToBeDeleted: List<Task>) {
        taskRepository.deleteTasks(tasksToBeDeleted)
    }

    fun addTask(taskToBeAdded: Task) {
        taskRepository.addTask(taskToBeAdded)
    }
}
