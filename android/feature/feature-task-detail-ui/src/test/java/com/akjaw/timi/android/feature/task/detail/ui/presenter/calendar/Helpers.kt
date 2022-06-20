package com.akjaw.timi.android.feature.task.detail.ui.presenter.calendar

import com.akjaw.timi.kmp.core.shared.time.TimestampProvider
import com.akjaw.timi.kmp.feature.task.dependency.detail.domain.calendar.CalendarDaysCalculator
import com.akjaw.timi.kmp.feature.task.dependency.detail.presentation.calendar.CalendarViewModel

internal fun createCalendarViewModel(
    timestampProvider: TimestampProvider,
) = CalendarViewModel(
    timestampProvider = timestampProvider,
    calendarDaysCalculator = CalendarDaysCalculator()
)
