package com.akjaw.details.presenter.calendar

internal data class CalendarViewState(
    val currentMonth: MonthViewState = MonthViewState(),
    val previousMonth: MonthViewState = MonthViewState(),
    val nextMonth: MonthViewState = MonthViewState()
)
