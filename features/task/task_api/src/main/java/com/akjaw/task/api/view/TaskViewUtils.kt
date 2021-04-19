package com.akjaw.task.api.view

import androidx.compose.ui.graphics.Color
import com.akjaw.task.api.domain.Task

val tasksPreview = listOf(
    Task(name = "Task 1", backgroundColor = Color(132, 212, 240), isSelected = false),
    Task(name = "Task 2", backgroundColor = Color(230, 240, 132), isSelected = false),
    Task(name = "Task 3", backgroundColor = Color(132, 240, 161), isSelected = false),
)