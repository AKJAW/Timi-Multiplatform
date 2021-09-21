package com.akjaw.details.presenter.calendar

import com.akjaw.core.common.domain.TimestampProvider
import com.akjaw.details.domain.calendar.CalendarDaysCalculator
import com.akjaw.details.presentation.calendar.CalendarViewModel
import kotlinx.coroutines.CoroutineDispatcher

internal fun createCalendarViewModel(
    backgroundDispatcher: CoroutineDispatcher,
    timestampProvider: TimestampProvider,
) = CalendarViewModel(
    backgroundDispatcher = backgroundDispatcher,
    timestampProvider = timestampProvider,
    calendarDaysCalculator = CalendarDaysCalculator()
)
