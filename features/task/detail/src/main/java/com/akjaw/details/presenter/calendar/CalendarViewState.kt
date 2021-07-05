package com.akjaw.details.presenter.calendar

data class CalendarViewState(
    val monthName: String = "",
    val dayRows: List<List<Day>> = listOf()
)
