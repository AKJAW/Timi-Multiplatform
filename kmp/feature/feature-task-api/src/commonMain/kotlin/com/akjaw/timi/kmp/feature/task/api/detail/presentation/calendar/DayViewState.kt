package com.akjaw.timi.kmp.feature.task.api.detail.presentation.calendar

import com.akjaw.timi.kmp.core.shared.date.CalendarDay

data class DayViewState(
    val day: Int,
    val month: Int = 0,
    val year: Int = 0,
    val isSelected: Boolean = false
)

fun DayViewState.toCalendarDay(): CalendarDay
    = CalendarDay(day = day, month = month, year = year)
