package com.akjaw.task.api.domain

data class Task(
    val id: Long = 0,
    val name: String = "",
    val backgroundColor: TaskColor = TaskColor(),
    val isSelected: Boolean = false,
)

data class TaskColor(
    val argb: Int = -1
)
