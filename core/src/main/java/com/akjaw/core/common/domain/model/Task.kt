package com.akjaw.core.common.domain.model

import androidx.compose.ui.graphics.Color
//TODO move to tasks:list-api
data class Task(
    val name: String,
    val backgroundColor: Color, //TODO change the type?
    val isSelected: Boolean = false,
)
