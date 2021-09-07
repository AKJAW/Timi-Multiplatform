package com.akjaw.details.presenter.calendar

data class CalendarViewState(
    val monthName: String = "",
    val calendarDayRows: List<List<CalendarDay>> = listOf(),
    val previousMonth: MonthViewState = MonthViewState(),
    val nextMonth: MonthViewState = MonthViewState()
)

data class MonthViewState(
    val monthName: String = "",
    val calendarDayRows: List<List<CalendarDay>> = listOf()
)
