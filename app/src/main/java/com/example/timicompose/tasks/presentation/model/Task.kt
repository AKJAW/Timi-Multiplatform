package com.example.timicompose.tasks.presentation.model

import androidx.compose.ui.graphics.Color

data class Task(
    val name: String,
    val backgroundColor: Color, //TODO change the type?
    val isSelected: Boolean,
)
