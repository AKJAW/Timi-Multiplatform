package com.akjaw.task.api.view

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.akjaw.timi.kmp.feature.task.domain.model.Task
import com.akjaw.timi.kmp.feature.task.domain.model.TaskColor

val tasksPreview = listOf(
    Task(
        name = "Task 1",
        backgroundColor = TaskColor(Color(132, 212, 240).toArgb()),
        isSelected = false
    ),
    Task(
        name = "Task 2",
        backgroundColor = TaskColor(Color(230, 240, 132).toArgb()),
        isSelected = false
    ),
    Task(
        name = "Task 3",
        backgroundColor = TaskColor(Color(132, 240, 161).toArgb()),
        isSelected = false
    ),
)

@Deprecated("View and Presentation layer should have its own data structure")
fun TaskColor.toComposeColor() = Color(this.argb)