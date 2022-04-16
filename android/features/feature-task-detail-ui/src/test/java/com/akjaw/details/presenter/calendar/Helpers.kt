package com.akjaw.details.presenter.calendar

import com.akjaw.timi.android.task.detail.ui.domain.calendar.CalendarDaysCalculator
import com.akjaw.timi.android.task.detail.ui.presentation.calendar.CalendarViewModel
import com.akjaw.timi.kmp.core.shared.time.TimestampProvider

internal fun createCalendarViewModel(
    timestampProvider: TimestampProvider,
) = CalendarViewModel(
    timestampProvider = timestampProvider,
    calendarDaysCalculator = CalendarDaysCalculator()
)
