package com.akjaw.details.presenter.calendar

import com.akjaw.details.domain.calendar.CalendarDaysCalculator
import com.akjaw.details.presentation.calendar.CalendarViewModel
import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import kotlinx.coroutines.CoroutineDispatcher

internal fun createCalendarViewModel(
    backgroundDispatcher: CoroutineDispatcher,
    timestampProvider: TimestampProvider,
) = CalendarViewModel(
    backgroundDispatcher = backgroundDispatcher,
    timestampProvider = timestampProvider,
    calendarDaysCalculator = CalendarDaysCalculator()
)
