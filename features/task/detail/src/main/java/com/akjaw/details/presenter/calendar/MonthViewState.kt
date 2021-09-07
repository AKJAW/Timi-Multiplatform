package com.akjaw.details.presenter.calendar

internal data class MonthViewState(
    val monthName: String = "",
    val calendarDayRows: List<List<CalendarDay>> = listOf()
)
