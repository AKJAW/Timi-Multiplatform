package com.akjaw.core.common.view

import androidx.compose.ui.graphics.Color
import com.akjaw.timi.kmp.feature.task.api.model.Task
import com.akjaw.timi.kmp.feature.task.api.model.TaskColor

val tasksPreview = listOf(
    Task(
        name = "Task 1",
        backgroundColor = TaskColor(132 / 255f, 212 / 255f, 240 / 255f),
        isSelected = false
    ),
    Task(
        name = "Task 2",
        backgroundColor = TaskColor(230 / 255f, 240 / 255f, 132 / 255f),
        isSelected = false
    ),
    Task(
        name = "Task 3",
        backgroundColor = TaskColor(132 / 255f, 240 / 255f, 161 / 255f),
        isSelected = false
    ),
)

fun TaskColor.toComposeColor() = Color(red, green, blue)

fun Color.toTaskColor(): TaskColor = TaskColor(red, green, blue)