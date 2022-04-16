package com.akjaw.timi.kmp.feature.task.api.domain.model

data class Task(
    val id: Long = 0,
    val name: String = "",
    val backgroundColor: TaskColor = TaskColor(),
    val isSelected: Boolean = false,
)