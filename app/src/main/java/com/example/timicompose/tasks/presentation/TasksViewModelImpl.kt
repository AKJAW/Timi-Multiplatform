package com.example.timicompose.tasks.presentation

import com.example.timicompose.tasks.data.TaskRepository
import com.example.timicompose.tasks.presentation.model.Task
import kotlinx.coroutines.flow.MutableStateFlow

class TasksViewModelImpl : TaskViewModel() {

    override val tasks: MutableStateFlow<List<Task>> = MutableStateFlow(TaskRepository.tasks)

}
