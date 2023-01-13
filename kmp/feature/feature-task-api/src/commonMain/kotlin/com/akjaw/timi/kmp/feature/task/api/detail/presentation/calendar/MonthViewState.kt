package com.akjaw.timi.kmp.feature.task.api.detail.presentation.calendar

data class MonthViewState(
    val monthName: String = "",
    val calendarDayRows: List<List<DayViewState>> = listOf()
)
