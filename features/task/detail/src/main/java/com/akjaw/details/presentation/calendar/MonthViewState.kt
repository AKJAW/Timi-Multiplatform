package com.akjaw.details.presentation.calendar

internal data class MonthViewState(
    val monthName: String = "",
    val calendarDayRows: List<List<DayViewState>> = listOf()
)
