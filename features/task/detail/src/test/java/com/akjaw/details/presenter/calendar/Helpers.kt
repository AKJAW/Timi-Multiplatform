package com.akjaw.details.presenter.calendar

import com.akjaw.details.domain.calendar.CalendarDaysCalculator
import com.akjaw.details.presentation.calendar.CalendarViewModel
import com.akjaw.timi.kmp.core.shared.time.TimestampProvider

internal fun createCalendarViewModel(
    timestampProvider: TimestampProvider,
) = CalendarViewModel(
    timestampProvider = timestampProvider,
    calendarDaysCalculator = CalendarDaysCalculator()
)
