package com.example.timicompose.tasks.presentation

import com.example.timicompose.tasks.data.TaskRepository
import com.example.timicompose.tasks.presentation.model.Task
import kotlinx.coroutines.flow.MutableStateFlow

class TasksViewModelImpl : TaskViewModel() {

    override val tasks: MutableStateFlow<List<Task>> = MutableStateFlow(TaskRepository.tasks)

    override fun toggleTask(toggledTask: Task) {
        val newTasks = tasks.value.map { task ->
            if (task == toggledTask) {
                task.copy(isSelected = task.isSelected.not())
            } else {
                task
            }
        }
        tasks.value = newTasks
    }
}
