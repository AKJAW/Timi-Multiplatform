package com.akjaw.timi.android.feature.task.detail.ui.presentation.calendar

data class DayViewState(
    val day: Int,
    val month: Int = 0,
    val year: Int = 0,
    val isSelected: Boolean = false,
)
