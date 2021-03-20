package com.example.timicompose.tasks.presentation

import androidx.lifecycle.ViewModel
import com.example.timicompose.tasks.presentation.model.Task
import kotlinx.coroutines.flow.StateFlow

abstract class TaskViewModel : ViewModel() {

    abstract val tasks: StateFlow<List<Task>>

    abstract fun toggleTask(toggledTask: Task)
}
