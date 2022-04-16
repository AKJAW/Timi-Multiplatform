package com.akjaw.timi.android.task.detail.ui.presentation.calendar

internal data class MonthViewState(
    val monthName: String = "",
    val calendarDayRows: List<List<DayViewState>> = listOf()
)
