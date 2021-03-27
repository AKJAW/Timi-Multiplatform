package com.example.timicompose.tasks.data

import androidx.compose.ui.graphics.Color
import com.example.timicompose.tasks.presentation.model.Task
import javax.inject.Inject

class TaskRepository @Inject constructor() {
    val tasks = listOf(
        Task("Task 1", Color(132, 212, 240), false),
        Task("Task 2", Color(230, 240, 132), false),
        Task("Task 3", Color(132, 240, 161), false),
    )
}