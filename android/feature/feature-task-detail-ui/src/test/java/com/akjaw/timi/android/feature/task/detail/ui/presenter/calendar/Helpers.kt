package com.akjaw.timi.android.feature.task.detail.ui.presenter.calendar

import com.akjaw.timi.android.feature.task.detail.ui.presentation.calendar.CalendarViewModel
import com.akjaw.timi.kmp.core.shared.time.TimestampProvider

internal fun createCalendarViewModel(
    timestampProvider: TimestampProvider,
) = CalendarViewModel(
    timestampProvider = timestampProvider,
    calendarDaysCalculator = com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator()
)
