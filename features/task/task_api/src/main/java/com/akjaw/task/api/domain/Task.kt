package com.akjaw.task.api.domain

import androidx.compose.ui.graphics.Color

data class Task(
    val id: Long = 0,
    val name: String = "",
    val backgroundColor: Color = Color.White, // TODO change the type?
    val isSelected: Boolean = false,
)
