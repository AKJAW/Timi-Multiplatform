package com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.calendar

data class MonthViewState(
    val monthName: String = "",
    val calendarDayRows: List<List<DayViewState>> = listOf()
)
