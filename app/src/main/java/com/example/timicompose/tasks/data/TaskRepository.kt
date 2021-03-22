package com.example.timicompose.tasks.data

import com.example.timicompose.tasks.presentation.model.HexColor
import com.example.timicompose.tasks.presentation.model.Task
import javax.inject.Inject

class TaskRepository @Inject constructor() {
    val tasks = listOf(
        Task("Task 1", HexColor("#80d5ed"), false),
        Task("Task 2", HexColor("#e5f087"), false),
        Task("Task 3", HexColor("#80f0a3"), false),
    )
}